/*
 * Copyright (c) Andrey Kolchanov, 2017.
 */

package com.andrey4623.conceptwrapper.concept;

import java.util.Arrays;

public class Image extends Concept {

  public Image(Border imgUrl, Border alt) {
    super(Arrays.asList(imgUrl, alt));
  }

  @Override
  protected String getFormatString() {
    return "<img src=\"%1$s\" alt=\"%2$s\">";
  }
}
