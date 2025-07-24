package com.CurrencyTransfer.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.Exchanger;

@Service
public class CurrencyService {

    private final RestTemplate restTemplate = new RestTemplate();

    public double convert (String from, String to, double amount){

        URI uri = UriComponentsBuilder
                .fromUriString("https://api.frankfurter.app/latest")
                .queryParam("from", from)
                .queryParam("to", to)
                .queryParam("amount", amount)
                .build()
                .toUri();

        System.out.println("📡 Отправляем запрос: " + uri);

        FrankfurterResponse response = restTemplate.getForObject(uri, FrankfurterResponse.class);

        if (response == null || response.getRates() == null || !response.getRates().containsKey(to)) {
            throw new RuntimeException("Ошибка при получении курса валют");
        }

        double result = response.getRates().get(to);
        System.out.println("✅ Конвертировано: " + amount + " " + from + " = " + result + " " + to);
        return result;
    }
    // Вложенный класс для разбора JSON-ответа
    public static class FrankfurterResponse {
        private double amount;
        private String base;
        private String date;
        private Map<String, Double> rates;

        public double getAmount() {
            return amount;
        }

        public String getBase() {
            return base;
        }

        public String getDate() {
            return date;
        }

        public Map<String, Double> getRates() {
            return rates;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public void setBase(String base) {
            this.base = base;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setRates(Map<String, Double> rates) {
            this.rates = rates;
        }
    }
}
