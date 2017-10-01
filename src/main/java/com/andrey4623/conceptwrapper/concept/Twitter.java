/*
 * Copyright (c) Andrey Kolchanov, 2017.
 */

package com.andrey4623.conceptwrapper.concept;

public class Twitter extends Concept {

  public Twitter(Border border) {
    super(border);
  }

  @Override
  protected String getFormatString() {
    return "@ <a href=”http://twitter.com/%1$s”>%1$s</a>";
  }

  @Override
  protected String[] preprocess(String[] source) {
    source[0] = source[0].substring(1);
    return source;
  }
}
