package ecma.ai.transferapp.controller;

import ecma.ai.transferapp.entity.Outcome;
import ecma.ai.transferapp.payload.ApiResponse;
import ecma.ai.transferapp.payload.OutcomeDto;
import ecma.ai.transferapp.service.OutcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController

@RequestMapping("/api/outcome")

public class OutcomeController {

    @Autowired
    OutcomeService outcomeService;

    @GetMapping
    public HttpEntity<?> getOutcome(HttpServletRequest httpServletRequest) {
        ApiResponse response = outcomeService.getOutcome(httpServletRequest);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("{id}")
    public HttpEntity<?> getById(@PathVariable Integer id) {
        Outcome byId = outcomeService.findById(id);
        if (byId != null) {
            return ResponseEntity.ok(byId);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Such outcome id not found!", false));
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody OutcomeDto outcomeDto, HttpServletRequest httpServletRequest) {

        ApiResponse response = outcomeService.add(outcomeDto, httpServletRequest);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);

    }

    @DeleteMapping("{id}")
    public HttpEntity<?> deleteById(@PathVariable Integer id) {
        ApiResponse delete = outcomeService.delete(id);
        return ResponseEntity.status(delete.isSuccess() ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND).body(delete);
    }

}
