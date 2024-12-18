package com.dev.json.controllers;

import com.dev.json.services.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/person")
public class PersonController {

    private final PersonService service;
    private final ObjectMapper mapper;

    @PostMapping("/create")
    public ResponseEntity<String> create(
            @RequestHeader String requestId,
            @RequestBody String request
    ) throws IOException {
        log.info("Пришел запрос на создание человека, requestId = {}", requestId);
        return ResponseEntity.ok(
                mapper.writeValueAsString(service.create(requestId, request))
        );
    }
}
