/*
 * Copyright (c) Andrey Kolchanov, 2017.
 */

package com.andrey4623.conceptwrapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.andrey4623.conceptwrapper.concept.Concept;
import com.andrey4623.conceptwrapper.concept.Entity;
import com.andrey4623.conceptwrapper.concept.Link;
import com.andrey4623.conceptwrapper.concept.Twitter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class WrapperTest {

  private static final String ILLEGAL_ARGUMENT_EXCEPTION_MSG = "isConceptBorderEligible is false";

  @Test
  void emptyStringNoConcepts() {
    final String actual = Wrapper.wrap("", new ArrayList<>());
    assertEquals("", actual);
  }

  @Test
  void stringOneCharacterNoConcepts() {
    final String actual = Wrapper.wrap("S", new ArrayList<>());
    assertEquals("S", actual);
  }

  @Test
  void stringTwoCharactersNoConcepts() {
    final String actual = Wrapper.wrap("St", new ArrayList<>());
    assertEquals("St", actual);
  }

  @Test
  void stringNoConcepts() {
    final String actual = Wrapper.wrap("Some string", new ArrayList<>());
    assertEquals("Some string", actual);
  }

  @Test
  void emptyStringHasConcepts() {
    final List<Concept> concepts = new ArrayList<>();
    concepts.add(new Entity(new Concept.Border(14, 22)));

    final Throwable exception = assertThrows(IllegalArgumentException.class,
        () -> Wrapper.wrap("", concepts));

    assertEquals(ILLEGAL_ARGUMENT_EXCEPTION_MSG, exception.getMessage());
  }

  @Test
  void stringOneCharacterHasConcepts() {
    final List<Concept> concepts = new ArrayList<>();
    concepts.add(new Entity(new Concept.Border(14, 22)));

    final Throwable exception = assertThrows(IllegalArgumentException.class,
        () -> Wrapper.wrap("S", concepts));

    assertEquals(ILLEGAL_ARGUMENT_EXCEPTION_MSG, exception.getMessage());
  }

  @Test
  void stringTwoCharactersHasConcepts() {
    final List<Concept> concepts = new ArrayList<>();
    concepts.add(new Entity(new Concept.Border(14, 22)));

    final Throwable exception = assertThrows(IllegalArgumentException.class,
        () -> Wrapper.wrap("St", concepts));

    assertEquals(ILLEGAL_ARGUMENT_EXCEPTION_MSG, exception.getMessage());
  }

  @Test
  void conceptIsBetweenText() {
    final List<Concept> concepts = new ArrayList<>();
    concepts.add(new Link(new Concept.Border(5, 22)));

    final String consolidate = Wrapper.wrap("some http://bit.ly/xyz text", concepts);

    final String expected = "some <a href=”http://bit.ly/xyz”>http://bit.ly/xyz</a> text";
    assertEquals(expected, consolidate);
  }

  @Test
  void conceptIsBeforeText() {
    final List<Concept> concepts = new ArrayList<>();
    concepts.add(new Link(new Concept.Border(0, 17)));

    final String consolidate = Wrapper.wrap("http://bit.ly/xyz text", concepts);

    final String expected = "<a href=”http://bit.ly/xyz”>http://bit.ly/xyz</a> text";
    assertEquals(expected, consolidate);
  }

  @Test
  void conceptIsAfterText() {
    final List<Concept> concepts = new ArrayList<>();
    concepts.add(new Link(new Concept.Border(5, 22)));

    final String consolidate = Wrapper.wrap("text http://bit.ly/xyz", concepts);

    final String expected = "text <a href=”http://bit.ly/xyz”>http://bit.ly/xyz</a>";
    assertEquals(expected, consolidate);
  }

  @Test
  void onlyConceptNoSurroundText() {
    final List<Concept> concepts = new ArrayList<>();
    concepts.add(new Link(new Concept.Border(0, 17)));

    final String consolidate = Wrapper.wrap("http://bit.ly/xyz", concepts);

    final String expected = "<a href=”http://bit.ly/xyz”>http://bit.ly/xyz</a>";
    assertEquals(expected, consolidate);
  }

  @Test
  void multipleConceptsTooBigBorder() {
    final String source = "Alex visited Facebook headquarters: http://bit.ly/xyz @andrey4623";

    final List<Concept> concepts = new ArrayList<>();
    concepts.add(new Entity(new Concept.Border(13, 20)));
    concepts.add(new Entity(new Concept.Border(0, 4)));
    concepts.add(new Twitter(new Concept.Border(55, 67)));
    concepts.add(new Link(new Concept.Border(36, 53)));

    final Throwable exception = assertThrows(IllegalArgumentException.class,
        () -> Wrapper.wrap(source, concepts));

    assertEquals(ILLEGAL_ARGUMENT_EXCEPTION_MSG, exception.getMessage());
  }

  @Test
  void multipleConcepts() {
    final String source = "Alex visited Facebook headquarters: http://bit.ly/xyz @andrey4623";

    final List<Concept> concepts = new ArrayList<>();
    concepts.add(new Entity(new Concept.Border(13, 21)));
    concepts.add(new Entity(new Concept.Border(0, 4)));
    concepts.add(new Twitter(new Concept.Border(54, 65)));
    concepts.add(new Link(new Concept.Border(36, 53)));

    final String actual = Wrapper.wrap(source, concepts);
    final String expected = "<strong>Alex</strong> visited <strong>Facebook</strong> headquarters: <a href=”http://bit.ly/xyz”>http://bit.ly/xyz</a> @ <a href=”http://twitter.com/andrey4623”>andrey4623</a>";
    assertEquals(expected, actual);
  }
}
