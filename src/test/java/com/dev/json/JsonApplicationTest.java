package com.dev.json;

import com.dev.json.enums.ErrorType;
import com.dev.json.enums.SuccessResultType;
import com.dev.json.services.PersonService;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootTest(classes = {JsonApplication.class})
@ActiveProfiles("test")
public class JsonApplicationTest {

    @Autowired
    private PersonService service;

    private String validRequest;
    private String invalidRequest;

    @BeforeEach
    void configure() throws IOException {
        validRequest = Files.readString(Path.of("src/test/resources/data/ValidRequest.json"));
        invalidRequest = Files.readString(Path.of("src/test/resources/data/InvalidRequest.json"));
    }

    @Test
    void successValidation() throws ProcessingException {
        PersonCreateRs createRs = service.create("61f0c404-5cb3-11e7-907b-a6006ad3dba0", validRequest);
        Assertions.assertEquals(createRs, buildSuccessResult());
    }

    @Test
    void failureValidation() throws ProcessingException {
        PersonCreateRs createRs = service.create("61f0c404-5cb3-11e7-907b-a6006ad3dba0", invalidRequest);
        Assertions.assertEquals(createRs, buildFailureResult());
    }

    private static PersonCreateRs buildSuccessResult() {
        PersonCreateRs createRs = new PersonCreateRs();
        createRs.setRequestId("61f0c404-5cb3-11e7-907b-a6006ad3dba0");
        createRs.setStatus(SuccessResultType.PERSON_CREATED.getTitle());
        createRs.setDescription(SuccessResultType.PERSON_CREATED.getDescription());
        return createRs;
    }

    private static PersonCreateRs buildFailureResult() {
        PersonCreateRs createRs = new PersonCreateRs();
        createRs.setRequestId("61f0c404-5cb3-11e7-907b-a6006ad3dba0");
        createRs.setStatus(ErrorType.REQ_VALIDATION_ERROR.getTitle());
        createRs.setDescription(ErrorType.REQ_VALIDATION_ERROR.getDescription() + ": "
                + "\n Ошибка: object has missing required properties ([\"age\",\"name\"])");
        return createRs;
    }
}
