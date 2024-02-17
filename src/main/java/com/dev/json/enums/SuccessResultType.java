package com.dev.json.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessResultType {
    PERSON_CREATED("person_created", "Человек успешно создан");

    private final String title;
    private final String description;
}
