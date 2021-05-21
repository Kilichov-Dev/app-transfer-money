package ecma.ai.transferapp.service;

import ecma.ai.transferapp.entity.Card;
import ecma.ai.transferapp.payload.ApiResponse;
import ecma.ai.transferapp.payload.CardDto;
import ecma.ai.transferapp.repository.CardRepository;
import ecma.ai.transferapp.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    JwtProvider jwtProvider;


    public ApiResponse add(CardDto cardDto, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");

        token = token.substring(7);

        String username = jwtProvider.getUsernameFromToken(token);//username

        if (cardRepository.existsByCardNumber(cardDto.getCardNumber())) return new ApiResponse("Already exist!", false);
        Card card = new Card();
        card.setUsername(username);
        card.setCardNumber(cardDto.getCardNumber());
        card.setExpiredDate(cardDto.getExpiredDate());

        cardRepository.save(card);
        return new ApiResponse("New card added!", true);
    }


    public ApiResponse edit(Integer id, CardDto cardDto, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");

        token = token.substring(7);

        String username = jwtProvider.getUsernameFromToken(token);//username

        Optional<Card> optionalCard = cardRepository.findById(id);
        if (!optionalCard.isPresent()) return new ApiResponse("Not Found Card Id", false);

        if (!username.equals(optionalCard.get().getUsername()))
            return new ApiResponse("Sizda bunaqa karta yo'q!", false);

        Card card = optionalCard.get();
        if (card.isActive()) {
            if (cardDto.getBalance() > 0) {
                card.setBalance(cardDto.getBalance());
            } else {
                return new ApiResponse("Tranzaksiya summasi notogri!", false);
            }
            if (cardDto.getExpiredDate() != null) {
                card.setExpiredDate(cardDto.getExpiredDate());
            }
        } else {
            return new ApiResponse("Karta bloklangan!", false);
        }
        cardRepository.save(card);
        return new ApiResponse("Card Edited!", true);
    }


    public ApiResponse deleteCard(Integer id) {
        try {
            cardRepository.deleteById(id);
            return new ApiResponse("Card Deleted!", true);
        } catch (Exception e) {
            return new ApiResponse("Card not found!", false);
        }
    }

    public List<Card> getCard() {
        List<Card> all = cardRepository.findAll();
        return all;
    }
}
