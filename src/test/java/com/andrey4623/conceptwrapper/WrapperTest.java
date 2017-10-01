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
    String actual = Wrapper.wrap("", new ArrayList<>());
    assertEquals("", actual);
  }

  @Test
  void stringOneCharacterNoConcepts() {
    String actual = Wrapper.wrap("S", new ArrayList<>());
    assertEquals("S", actual);
  }

  @Test
  void stringTwoCharactersNoConcepts() {
    String actual = Wrapper.wrap("St", new ArrayList<>());
    assertEquals("St", actual);
  }

  @Test
  void stringNoConcepts() {
    String actual = Wrapper.wrap("Some string", new ArrayList<>());
    assertEquals("Some string", actual);
  }

  @Test
  void emptyStringHasConcepts() {
    List<Concept> concepts = new ArrayList<>();
    concepts.add(new Entity(new Concept.Border(14, 22)));

    Throwable exception = assertThrows(IllegalArgumentException.class,
        () -> Wrapper.wrap("", concepts));

    assertEquals(ILLEGAL_ARGUMENT_EXCEPTION_MSG, exception.getMessage());
  }

  @Test
  void stringOneCharacterHasConcepts() {
    List<Concept> concepts = new ArrayList<>();
    concepts.add(new Entity(new Concept.Border(14, 22)));

    Throwable exception = assertThrows(IllegalArgumentException.class,
        () -> Wrapper.wrap("S", concepts));

    assertEquals(ILLEGAL_ARGUMENT_EXCEPTION_MSG, exception.getMessage());
  }

  @Test
  void stringTwoCharactersHasConcepts() {
    List<Concept> concepts = new ArrayList<>();
    concepts.add(new Entity(new Concept.Border(14, 22)));

    Throwable exception = assertThrows(IllegalArgumentException.class,
        () -> Wrapper.wrap("St", concepts));

    assertEquals(ILLEGAL_ARGUMENT_EXCEPTION_MSG, exception.getMessage());
  }

  @Test
  void conceptIsBetweenText() {
    List<Concept> concepts = new ArrayList<>();
    concepts.add(new Link(new Concept.Border(5, 22)));

    String consolidate = Wrapper.wrap("some http://bit.ly/xyz text", concepts);

    String expected = "some <a href=”http://bit.ly/xyz”>http://bit.ly/xyz</a> text";
    assertEquals(expected, consolidate);
  }

  @Test
  void conceptIsBeforeText() {
    List<Concept> concepts = new ArrayList<>();
    concepts.add(new Link(new Concept.Border(0, 17)));

    String consolidate = Wrapper.wrap("http://bit.ly/xyz text", concepts);

    String expected = "<a href=”http://bit.ly/xyz”>http://bit.ly/xyz</a> text";
    assertEquals(expected, consolidate);
  }

  @Test
  void conceptIsAfterText() {
    List<Concept> concepts = new ArrayList<>();
    concepts.add(new Link(new Concept.Border(5, 22)));

    String consolidate = Wrapper.wrap("text http://bit.ly/xyz", concepts);

    String expected = "text <a href=”http://bit.ly/xyz”>http://bit.ly/xyz</a>";
    assertEquals(expected, consolidate);
  }

  @Test
  void onlyConceptNoSurroundText() {
    List<Concept> concepts = new ArrayList<>();
    concepts.add(new Link(new Concept.Border(0, 17)));

    String consolidate = Wrapper.wrap("http://bit.ly/xyz", concepts);

    String expected = "<a href=”http://bit.ly/xyz”>http://bit.ly/xyz</a>";
    assertEquals(expected, consolidate);
  }

  @Test
  void multipleConceptsTooBigBorder() {
    String source = "Alex visited Facebook headquarters: http://bit.ly/xyz @andrey4623";

    List<Concept> concepts = new ArrayList<>();
    concepts.add(new Entity(new Concept.Border(13, 20)));
    concepts.add(new Entity(new Concept.Border(0, 4)));
    concepts.add(new Twitter(new Concept.Border(55, 67)));
    concepts.add(new Link(new Concept.Border(36, 53)));

    Throwable exception = assertThrows(IllegalArgumentException.class,
        () -> Wrapper.wrap(source, concepts));

    assertEquals(ILLEGAL_ARGUMENT_EXCEPTION_MSG, exception.getMessage());
  }

  @Test
  void multipleConcepts() {
    String source = "Alex visited Facebook headquarters: http://bit.ly/xyz @andrey4623";

    List<Concept> concepts = new ArrayList<>();
    concepts.add(new Entity(new Concept.Border(13, 21)));
    concepts.add(new Entity(new Concept.Border(0, 4)));
    concepts.add(new Twitter(new Concept.Border(54, 65)));
    concepts.add(new Link(new Concept.Border(36, 53)));

    String actual = Wrapper.wrap(source, concepts);
    String expected = "<strong>Alex</strong> visited <strong>Facebook</strong> headquarters: <a href=”http://bit.ly/xyz”>http://bit.ly/xyz</a> @ <a href=”http://twitter.com/andrey4623”>andrey4623</a>";
    assertEquals(expected, actual);
  }
}
