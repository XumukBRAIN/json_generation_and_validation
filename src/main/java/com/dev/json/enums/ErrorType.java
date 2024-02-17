package com.dev.json.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    REQ_PARSE_ERROR("req_parse_error", "Ошибка парсинга запроса"),
    RES_PARSE_ERROR("res_parse_error", "Ошибка парсинга ответа"),
    REQ_VALIDATION_ERROR("req_validation_error", "Ошибка валидации запроса"),
    RES_VALIDATION_ERROR("res_validation_error", "Ошибка валидации ответа");

    private final String title;
    private final String description;
}
