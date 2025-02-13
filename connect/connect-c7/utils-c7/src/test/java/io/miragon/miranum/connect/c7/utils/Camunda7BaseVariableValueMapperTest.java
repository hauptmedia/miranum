package io.miragon.miranum.connect.c7.utils;

import org.camunda.community.rest.client.dto.VariableValueDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;
import java.util.Map;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Camunda7BaseVariableValueMapperTest {

    private final Camunda7BaseVariableValueMapper mapper = new Camunda7BaseVariableValueMapper();

    @Test
    public void testMap_ShouldReturnMappedVariables() {
        final String KEY_1 = "var1";
        final String KEY_2 = "var2";
        final String VALUE_1 = "value2";
        final Integer VALUE_2 = 2;

        // Given
        Map<String, Object> variables = Map.ofEntries(
                entry(KEY_1, VALUE_1),
                entry(KEY_2, VALUE_2)
        );

        // When
        Map<String, VariableValueDto> result = mapper.map(variables);

        // Then
        assertEquals(2, result.size());
        assertEquals(VALUE_1, result.get(KEY_1).getValue());
        assertEquals(VALUE_2, result.get(KEY_2).getValue());
    }

    @Test
    public void testMap_EmptyInput_ShouldReturnEmptyMap() {
        // Given
        Map<String, Object> variables = Collections.emptyMap();

        // When
        Map<String, VariableValueDto> result = mapper.map(variables);

        // Then
        assertTrue(result.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"some value"})
    public void testCreateValueWithStrings_ShouldReturnCorrectValue(String value) {
        // When
        VariableValueDto result = mapper.createValue(value);

        // Then
        assertEquals(value, result.getValue());
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MAX_VALUE, Integer.MAX_VALUE})
    public void testCreateValueWithStrings_ShouldReturnCorrectValue(Integer value) {
        // When
        VariableValueDto result = mapper.createValue(value);

        // Then
        assertEquals(value, result.getValue());
    }
}