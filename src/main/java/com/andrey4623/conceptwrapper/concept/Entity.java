/*
 * Copyright (c) Andrey Kolchanov, 2017.
 */

package com.andrey4623.conceptwrapper.concept;

public class Entity extends Concept {

  public Entity(final Concept.Border border) {
    super(border);
  }

  @Override
  public String getFormatString() {
    return "<strong>%1$s</strong>";
  }
}
