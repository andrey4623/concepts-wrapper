/*
 * Copyright (c) Andrey Kolchanov, 2017.
 */

package com.andrey4623.conceptwrapper.concept;

public class Hashtag extends Concept {

  public Hashtag(Border border) {
    super(border);
  }

  @Override
  protected String getFormatString() {
    return "#%1$s";
  }
}
