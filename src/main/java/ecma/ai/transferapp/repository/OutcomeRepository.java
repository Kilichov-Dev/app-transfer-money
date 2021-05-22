package ecma.ai.transferapp.repository;

import ecma.ai.transferapp.entity.Card;
import ecma.ai.transferapp.entity.Outcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutcomeRepository extends JpaRepository<Outcome, Integer> {
    @Query(value = "select o.id,o.from_card_id,o.to_card_id, o.amount, o.date,\n" +
            "       o.commission_amount from outcome o\n" +
            "        inner join card c\n" +
            "               on c.username =:user where c.id=o.from_card_id", nativeQuery = true)
    List<Outcome> findOutcomes(String user);
}
