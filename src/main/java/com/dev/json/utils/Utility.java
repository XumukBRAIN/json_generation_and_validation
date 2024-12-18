package com.dev.json.utils;

import com.jayway.jsonpath.JsonPath;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class Utility {

    private final static String JSON_PATH = "$.age";

    public static String parseRomanAge(String jsonString) {
        log.info("Поиск значения age с помощью JsonPath");
        String ageRoman = JsonPath.read(jsonString, JSON_PATH);
        log.info("Преобразование римского числа в арабское");
        int ageInt = romanToInt(ageRoman);

        return JsonPath.parse(jsonString)
                .set(JSON_PATH, ageInt)
                .jsonString();
    }

    private static int romanToInt(String s) {
        int len = s.length();
        int prevVal = 0;
        int total = 0;

        for (int i = len - 1; i >= 0; i--) {
            char ch = s.charAt(i);
            int currVal;

            switch(ch) {
                case 'M' -> currVal = 1000;
                case 'D' -> currVal = 500;
                case 'C' -> currVal = 100;
                case 'L' -> currVal = 50;
                case 'X' -> currVal = 10;
                case 'V' -> currVal = 5;
                default -> currVal = 1;
            }

            if (prevVal > currVal)
                total -= currVal;
            else
                total += currVal;

            prevVal = currVal;
        }

        return total;
    }
}
