/*
 * Copyright (c) Andrey Kolchanov, 2017.
 */

package com.andrey4623.conceptwrapper.concept;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

public abstract class Concept {

  private final List<Border> borders;

  {
    borders = new ArrayList<>();
  }

  // Constructor for a basic concept: one border.
  public Concept(final Border border) {
    borders.add(border);
  }

  // Constructor for complex concepts: multiple borders.
  public Concept(final List<Border> borders) {
    this.borders.addAll(borders);
  }

  public String format(final char[] source) {
    if (borders.isEmpty()) {
      throw new IllegalStateException("No borders");
    }

    String[] input = getStringsArrayFromBorders(source);
    input = preprocess(input);
    final String formatted = doFormat(input);

    return postprocess(formatted);
  }

  private String[] getStringsArrayFromBorders(final char[] source) {
    final String[] res = new String[borders.size()];
    final StringBuilder sb = new StringBuilder();

    for (int i = 0; i < borders.size(); i++) {
      for (int j = borders.get(i).getPosStart(); j < borders.get(i).getPosEnd(); j++) {
        sb.append(source[j]);
      }

      res[i] = sb.toString();

      // Clear StringBuilder.
      sb.setLength(0);
    }

    return res;
  }

  /*
  Override this method to add some preprocessing work.
  For example, for twitter the first symbol "@" could be removed.
   */
  protected String[] preprocess(final String[] source) {
    return source;
  }

  private String doFormat(final String[] input) {
    return String.format(getFormatString(), input);
  }

  /*
  Override this method to add some postprocessing work.
   */
  protected String postprocess(final String postprocess) {
    return postprocess;
  }

  protected abstract String getFormatString();

  public List<Border> getBorders() {
    return borders;
  }

  public int getMinLeftPosition() {
    final OptionalInt min = borders.stream().mapToInt(b -> b.posStart).min();
    if (min.isPresent()) {
      return min.getAsInt();
    } else {
      return 0;
    }
  }

  public int getMaxRightPosition() {
    final OptionalInt max = borders.stream().mapToInt(b -> b.posEnd).max();
    if (max.isPresent()) {
      return max.getAsInt();
    } else {
      return 0;
    }
  }

  public static class Border {

    private int posStart;
    private int posEnd;

    public Border(final int posStart, final int posEnd) {
      this.posStart = posStart;
      this.posEnd = posEnd;
    }

    public int getPosStart() {
      return posStart;
    }

    public void setPosStart(final int posStart) {
      this.posStart = posStart;
    }

    public int getPosEnd() {
      return posEnd;
    }

    public void setPosEnd(final int posEnd) {
      this.posEnd = posEnd;
    }
  }
}
