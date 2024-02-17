package com.dev.json.config;

import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Configuration
public class JsonValidationConfig {

    @Value("${json-schemas.personCreateRqSchema}")
    public String personCreateRqSchema;

    @Value("${json-schemas.personCreateRsSchema}")
    public String personCreateRsSchema;

    @Bean(name = "personCreateRqSchema")
    public JsonSchema personCreateRqSchema() throws IOException, ProcessingException {
        InputStream schemaStream = getClass().getResourceAsStream(personCreateRqSchema);
        if (schemaStream == null) {
            throw new RuntimeException("Не найдена JSON-схема по указанному пути: " + personCreateRqSchema);
        }
        JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
        return factory.getJsonSchema(JsonLoader.fromReader(new InputStreamReader(schemaStream)));
    }

    @Bean(name = "personCreateRsSchema")
    public JsonSchema personCreateRsSchema() throws IOException, ProcessingException {
        InputStream schemaStream = getClass().getResourceAsStream(personCreateRsSchema);
        if (schemaStream == null) {
            throw new RuntimeException("Не найдена JSON-схема по указанному пути: " + personCreateRsSchema);
        }
        JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
        return factory.getJsonSchema(JsonLoader.fromReader(new InputStreamReader(schemaStream)));
    }
}
