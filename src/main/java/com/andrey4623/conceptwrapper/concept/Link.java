/*
 * Copyright (c) Andrey Kolchanov, 2017.
 */

package com.andrey4623.conceptwrapper.concept;

public class Link extends Concept {

  public Link(Border border) {
    super(ConceptType.LINK, border);
  }

  @Override
  protected String getFormatString() {
    return "<a href=”%1$s”>%1$s</a>";
  }
}
