package com.github.marceloleite2604.cryptotrader.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validator;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ValidationUtilTest {

  @InjectMocks
  private ValidationUtil validationUtil;

  @Mock
  private Validator validator;

  @Test
  void shouldNotThrowExceptionIfObjectIsNull() {
    final var message = "validation message";
    assertDoesNotThrow(() -> validationUtil.throwIllegalArgumentExceptionIfNotValid(null, message));
  }

  @SuppressWarnings("unchecked")
  @Test
  void shouldThrowIllegalArgumentExceptionWhenObjectIsInvalid() {

    final var object = "validated object";

    final var path = mock(Path.class);
    when(path.toString()).thenReturn("myField");

    final ConstraintViolation<String> constraintViolation = mock(ConstraintViolation.class);
    when(constraintViolation.getPropertyPath()).thenReturn(path);

    when(validator.validate(object)).thenReturn(Collections.singleton(constraintViolation));

    final var message = "validation message";
    assertThrows(IllegalArgumentException.class, () -> validationUtil.throwIllegalArgumentExceptionIfNotValid(object, message));
  }

  @Test
  void shouldNotThrowIllegalArgumentExceptionWhenObjectIsValid() {

    final var object = "validated object";

    when(validator.validate(object)).thenReturn(Collections.emptySet());

    final var message = "validation message";
    assertDoesNotThrow(() -> validationUtil.throwIllegalArgumentExceptionIfNotValid(object, message));
  }
}