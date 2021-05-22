package ecma.ai.transferapp.controller;

import ecma.ai.transferapp.payload.ApiResponse;
import ecma.ai.transferapp.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/income")
public class IncomeController {
    @Autowired
    IncomeService incomeService;

    @GetMapping
    public HttpEntity<?> getAll(HttpServletRequest httpServletRequest) {
        ApiResponse response = incomeService.getIncomesAll(httpServletRequest);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}
