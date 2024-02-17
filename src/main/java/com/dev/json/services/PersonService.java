package com.dev.json.services;

import com.dev.json.PersonCreateRq;
import com.dev.json.PersonCreateRs;
import com.dev.json.converters.Converter;
import com.dev.json.enums.ErrorType;
import com.dev.json.models.Person;
import com.dev.json.repositories.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Сервис по работе с людьми
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    /**
     * mock database
     */
    private final List<Person> people = new ArrayList<>();

    private final ObjectMapper mapper;
    private final Converter converter;
    private final PersonRepository repository;

    private JsonSchema schemaRq;

    @Autowired
    @Qualifier("personCreateRqSchema")
    public void setSchemaRq(JsonSchema schemaRq) {
        this.schemaRq = schemaRq;
    }

    /**
     * Создание человека
     *
     * @param requestId Идентификатор запроса
     * @param request   Тело запроса
     * @return Результат выполнения запроса
     */
    public PersonCreateRs create(String requestId, String request) throws ProcessingException {
        try {
            MDC.put("requestId", requestId);
            ProcessingReport report = schemaRq.validate(JsonLoader.fromString(request));
            if (!report.isSuccess()) {
                return buildValidationErrorRs(requestId, report);
            }
            log.info("Валидация запроса с идентификатором {} прошла успешно", requestId);

            PersonCreateRq createRq = mapper.readValue(request, PersonCreateRq.class);
            Person person = converter.buildPerson(createRq);
            repository.save(person);
            log.info("Человек успешно создан. Идентификатор запроса = {}", requestId);
        } catch (IOException e) {
            log.error("Ошибка парсинга запроса c идентификатором = {}", requestId, e);
            return converter.createErrorRs(requestId, ErrorType.REQ_PARSE_ERROR, e.getMessage());
        } finally {
            MDC.clear();
        }

        return converter.buildPersonCreateRs(requestId);
    }

    private PersonCreateRs buildValidationErrorRs(String requestId, ProcessingReport report) {
        log.error("Ошибка валидации запроса с идентификатором = {}", requestId);
        StringBuilder validationErrorMessages = new StringBuilder();
        for (ProcessingMessage processingMessage : report) {
            String message = processingMessage.getMessage();
            log.error(message);
            validationErrorMessages.append("\n Ошибка: ")
                    .append(message);
        }
        return converter.createErrorRs(requestId, ErrorType.REQ_VALIDATION_ERROR, validationErrorMessages.toString());
    }
}
