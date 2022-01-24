package com.github.marceloleite2604.cryptotrader.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MapperWithoutMethodImplementationTest {

  private Mapper<Object, Object> mapper;

  @BeforeEach
  void setUp() {
    mapper = new MapperWithImplementation();
  }

  @Test
  void shouldReturnExpectedOutputWhenMapToIsImplemented() {
    final var input = "input";
    final var expected = "input";
    final var actual = mapper.mapTo(input);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void shouldReturnExpectedOutputWhenMapFromIsImplemented() {
    final var input = "input";
    final var expected = "input";
    final var actual = mapper.mapFrom(input);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void shouldReturnExpectedOutputListWhenMapToIsImplemented() {
    final List<Object> input = List.of("input");
    final List<Object> expected = List.of("input");
    final var actual = mapper.mapAllTo(input);
    assertThat(actual).containsAll(expected);
  }

  @Test
  void shouldReturnExpectedOutputListWhenMapFromIsImplemented() {
    final List<Object> input = List.of("input");
    final List<Object> expected = List.of("input");
    final var actual = mapper.mapAllFrom(input);
    assertThat(actual).containsAll(expected);
  }

  @Test
  void shouldReturnEmptyListWhenMapAllToIsExecutedWithEmptyList() {
    final List<Object> input = Collections.emptyList();
    final var actual = mapper.mapAllTo(input);
    assertThat(actual).isEmpty();
  }

  @Test
  void shouldReturnEmptyListWhenMapAllFromIsExecutedWithEmptyList() {
    final List<Object> input = Collections.emptyList();
    final var actual = mapper.mapAllFrom(input);
    assertThat(actual).isEmpty();
  }

  @Test
  void shouldReturnEmptyListWhenMapAllToIsExecutedWithNullObject() {
    final var actual = mapper.mapAllTo(null);
    assertThat(actual).isEmpty();
  }

  @Test
  void shouldReturnEmptyListWhenMapAllFromIsExecutedWithNullObject() {
    final var actual = mapper.mapAllFrom(null);
    assertThat(actual).isEmpty();
  }

  public static class MapperWithImplementation implements Mapper<Object, Object> {
    @Override
    public Object mapTo(Object input) {
      return input;
    }

    @Override
    public Object mapFrom(Object input) {
      return input;
    }
  }

}