package ecma.ai.transferapp.service;

import ecma.ai.transferapp.entity.Income;
import ecma.ai.transferapp.payload.ApiResponse;
import ecma.ai.transferapp.repository.IncomeRepository;
import ecma.ai.transferapp.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class IncomeService {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    IncomeRepository incomeRepository;

    public ApiResponse getIncomesAll(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("Authorization");
        token = token.substring(7);
        String username = jwtProvider.getUsernameFromToken(token);

        if (username.equals("admin")){
            List<Income> incomeList = incomeRepository.findAll();
            if (incomeList.isEmpty()){
                return new ApiResponse("Transaksiya amalga oshmagan!",false);

            }
            return new ApiResponse("Successfully",true);
        }
        List<Income> incomes = incomeRepository.findIncomes(username);
        if (incomes.isEmpty()){
            return new ApiResponse("Transaksiya amalga oshirilmagan",false);

        }
        return new ApiResponse("Successfully!",true);
    }
}
