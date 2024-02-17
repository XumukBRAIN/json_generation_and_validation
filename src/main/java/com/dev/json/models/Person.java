package com.dev.json.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person {
    /**
     * Идентификатор
     */
    private UUID id;
    /**
     * Имя
     */
    private String name;
    /**
     * Возраст
     */
    private Integer age;
    /**
     * Пол
     */
    private String gender;
    /**
     * Дата рождения
     */
    private LocalDate birthdate;
}
