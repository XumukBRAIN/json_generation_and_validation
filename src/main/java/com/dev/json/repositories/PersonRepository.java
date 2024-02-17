package com.dev.json.repositories;

import com.dev.json.models.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonRepository {
    /**
     * mock database
     */
    private final List<Person> people = new ArrayList<>();

    /**
     * Сохранение нового человека
     *
     * @param person Данные человека
     */
    public void save(Person person) {
        people.add(person);
    }
}
