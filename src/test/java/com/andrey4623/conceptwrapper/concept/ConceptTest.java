/*
 * Copyright (c) Andrey Kolchanov, 2017.
 */

package com.andrey4623.conceptwrapper.concept;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.andrey4623.conceptwrapper.concept.Concept.Border;
import java.util.List;
import org.junit.jupiter.api.Test;

class ConceptTest {

  @Test
  void getMinLeftPosition() {
    Entity e = new Entity(new Concept.Border(1, 5));
    assertEquals(1, e.getMinLeftPosition());

    List<Border> borders = e.getBorders();
    borders.clear();
    borders.add(new Concept.Border(1, 5));
    borders.add(new Concept.Border(3, 20));
    assertEquals(1, e.getMinLeftPosition());

    borders.clear();
    borders.add(new Concept.Border(3, 5));
    borders.add(new Concept.Border(1, 20));
    assertEquals(1, e.getMinLeftPosition());
  }

  @Test
  void getMaxRightPosition() {
    Entity e = new Entity(new Concept.Border(1, 5));
    assertEquals(5, e.getMaxRightPosition());

    List<Border> borders = e.getBorders();
    borders.clear();
    borders.add(new Concept.Border(1, 5));
    borders.add(new Concept.Border(3, 20));
    assertEquals(20, e.getMaxRightPosition());

    borders.clear();
    borders.add(new Concept.Border(3, 5));
    borders.add(new Concept.Border(1, 20));
    assertEquals(20, e.getMaxRightPosition());
  }
}
