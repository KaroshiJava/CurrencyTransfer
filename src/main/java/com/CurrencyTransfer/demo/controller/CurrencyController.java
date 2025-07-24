package com.CurrencyTransfer.demo.controller;

import com.CurrencyTransfer.demo.service.CurrencyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/convert")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService){
        this.currencyService = currencyService;
    }


    @GetMapping
    public double convert(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam double amount
    ) {
        return currencyService.convert(from, to, amount);
    }
}
