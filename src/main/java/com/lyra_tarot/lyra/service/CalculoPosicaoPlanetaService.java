package com.lyra_tarot.lyra.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyra_tarot.lyra.model.PosicaoPlaneta;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CalculoPosicaoPlanetaService implements ICalculoPosicaoPlanetaService {

    @Value("${astrology.api.url}")
    private String apiUrl;

    @Value("${astrology.api.apiKey}")
    private String apiKey;

    @Override 
    public List<PosicaoPlaneta> calcularPosicoesPlanetas() {
        RestTemplate restTemplate = new RestTemplate();
        List<PosicaoPlaneta> posicoesAstrologicas = new ArrayList<>(); 

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-astrologyapi-key", apiKey);

        LocalDateTime agora = LocalDateTime.now();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("day", agora.getDayOfMonth());
        requestBody.put("month", agora.getMonthValue());
        requestBody.put("year", agora.getYear());
        requestBody.put("hour", agora.getHour());
        requestBody.put("min", agora.getMinute());
        requestBody.put("lat", -23.55);
        requestBody.put("lon", -46.63);
        requestBody.put("tzone", -3.0);
        requestBody.put("house_type", "placidus");

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    request,
                    String.class 
            );

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            
            JsonNode transitHouses = root.path("transit_house");

            List<String> planetasNecessarios = List.of(
                "Sun", "Moon", "Mercury", "Venus", "Mars", 
                "Jupiter", "Saturn", "Uranus", "Neptune", "Pluto"
            );
            
            if (transitHouses.isArray()) {
                for (JsonNode node : transitHouses) {
                    
                    String nomePlanetaAPI = node.path("planet").asText();

                    if (planetasNecessarios.contains(nomePlanetaAPI)) {
                        
                        PosicaoPlaneta planeta = new PosicaoPlaneta();

                        planeta.setNomePlaneta(nomePlanetaAPI); 
                        planeta.setSigno(node.path("natal_sign").asText());
                        planeta.setCasaTransito(node.path("transit_house").asText());
                        planeta.setRetrogrado(node.path("retrograde").asBoolean());
                        
                        posicoesAstrologicas.add(planeta);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erro na requisição ou conversão: " + e.getMessage());
        }

        return posicoesAstrologicas; 
    }
}