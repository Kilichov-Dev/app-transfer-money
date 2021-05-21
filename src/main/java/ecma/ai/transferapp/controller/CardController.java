package ecma.ai.transferapp.controller;

import ecma.ai.transferapp.entity.Card;
import ecma.ai.transferapp.payload.ApiResponse;
import ecma.ai.transferapp.payload.CardDto;
import ecma.ai.transferapp.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/card")
public class CardController {

    @Autowired
    CardService cardService;

    @GetMapping()
    public HttpEntity<?> getCard() {
        List<Card> card = cardService.getCard();
        return ResponseEntity.ok(card);
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody CardDto dto, HttpServletRequest httpServletRequest) {

        ApiResponse response = cardService.add(dto, httpServletRequest);

        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PutMapping("/{id}")
    HttpEntity<?> edit(@PathVariable Integer id, @RequestBody CardDto cardDto, HttpServletRequest httpServletRequest) {
        ApiResponse response = cardService.edit(id, cardDto, httpServletRequest);

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @DeleteMapping("{id}")
    HttpEntity<?> delete(@PathVariable Integer id) {
        ApiResponse apiResponse = cardService.deleteCard(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
