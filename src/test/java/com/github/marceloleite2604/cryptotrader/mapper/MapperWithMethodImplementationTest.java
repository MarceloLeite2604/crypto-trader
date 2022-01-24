package com.github.marceloleite2604.cryptotrader.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MapperWithMethodImplementationTest {

  private Mapper<Object, Object> mapper;

  @BeforeEach
  void setUp() {
    mapper = new MapperWithoutImplementation();
  }

  @Test
  void shouldThrowUnsupportedOperationWhenMapToIsNotImplemented() {
    assertThrows(UnsupportedOperationException.class, () -> mapper.mapTo("input"));
  }

  @Test
  void shouldThrowUnsupportedOperationWhenMapFromIsNotImplemented() {
    assertThrows(UnsupportedOperationException.class, () -> mapper.mapFrom("input"));
  }

  @Test
  void shouldThrowUnsupportedOperationWhenMapAllToIsExecutedWithoutMapToImplementation() {
    final List<Object> input = List.of("input");

    assertThrows(UnsupportedOperationException.class, () -> mapper.mapAllTo(input));
  }

  @Test
  void shouldThrowUnsupportedOperationWhenMapAllFromIsExecutedWithoutMapFromImplementation() {
    final List<Object> input = List.of("input");

    assertThrows(UnsupportedOperationException.class, () -> mapper.mapAllFrom(input));
  }

  public static class MapperWithoutImplementation implements Mapper<Object, Object> {
  }
}