/*
 * Copyright (c) Andrey Kolchanov, 2017.
 */

package com.andrey4623.conceptwrapper.concept;

public class Entity extends Concept {

  public Entity(Concept.Border border) {
    super(ConceptType.ENTITY, border);
  }

  @Override
  public String getFormatString() {
    return "<strong>%1$s</strong>";
  }
}
