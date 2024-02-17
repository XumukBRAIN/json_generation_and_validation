package com.dev.json.converters;

import com.dev.json.PersonCreateRq;
import com.dev.json.PersonCreateRs;
import com.dev.json.enums.ErrorType;
import com.dev.json.enums.SuccessResultType;
import com.dev.json.models.Person;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class Converter {

    /**
     * Создание успешного ответа на запрос создания человека
     *
     * @param requestId Идентификатор запроса
     * @return Успешный ответ
     */
    public PersonCreateRs buildPersonCreateRs(String requestId) {
        PersonCreateRs createRs = new PersonCreateRs();
        createRs.setRequestId(requestId);
        createRs.setStatus(SuccessResultType.PERSON_CREATED.getTitle());
        createRs.setDescription(SuccessResultType.PERSON_CREATED.getDescription());

        return createRs;
    }

    /**
     * Создание человека на основе запроса
     *
     * @param createRq Тело запроса
     * @return Человек
     */
    public Person buildPerson(PersonCreateRq createRq) {
        return Person.builder()
                .id(UUID.randomUUID())
                .name(createRq.getName())
                .age(createRq.getAge())
                .gender(createRq.getGender())
                .birthdate(LocalDate.parse(createRq.getBirthdate()))
                .build();
    }

    /**
     * Создание провального ответа на запрос создания человека
     *
     * @param requestId Идентификатор запроса
     * @return Провальный ответ
     */
    public PersonCreateRs createErrorRs(String requestId, ErrorType errorType, String validationErrorMessages) {
        PersonCreateRs errorRs = new PersonCreateRs();
        errorRs.setRequestId(requestId);
        errorRs.setStatus(errorType.getTitle());
        errorRs.setDescription(errorType.getDescription() + ": " + validationErrorMessages);
        return errorRs;
    }
}
