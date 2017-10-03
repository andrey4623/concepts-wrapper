/*
 * Copyright (c) Andrey Kolchanov, 2017.
 */

package com.andrey4623.conceptwrapper;

import com.andrey4623.conceptwrapper.concept.Concept;
import java.util.Comparator;
import java.util.List;

public class Wrapper {

  public static String wrap(final String source, final List<Concept> concepts) {
    validateBorders(concepts, source.length());
    sort(concepts);
    return doWrap(source.toCharArray(), concepts);
  }

  private static void validateBorders(final List<Concept> concepts,
      final int sourceStringLength) {
    if (concepts.isEmpty()) {
      return;
    }

    validateBorders(findMinLeftBorderConcept(concepts), sourceStringLength);
    validateBorders(findMaxRightBorderConcept(concepts), sourceStringLength);
  }

  private static void sort(final List<Concept> concepts) {
    concepts.sort(Comparator.comparingInt(Concept::getMinLeftPosition));
  }

  private static String doWrap(final char[] arr, final List<Concept> concepts) {
    final StringBuilder out = new StringBuilder();

    int pos = 0;
    int idxConcept = 0;
    while (pos < arr.length) {
      if (idxConcept >= concepts.size()) {
        // No more concepts in the string.
        copy(pos, arr.length, arr, out);
        break;
      } else {
        // There are more concepts in the string.
        if (concepts.get(idxConcept).getMinLeftPosition() > pos) {
          // There are some text before the next concept.
          copy(pos, concepts.get(idxConcept).getMinLeftPosition(), arr, out);
          pos = concepts.get(idxConcept).getMinLeftPosition();
        } else {
          // The next concept is right here.
          out.append(concepts.get(idxConcept).format(arr));
          pos = concepts.get(idxConcept).getMaxRightPosition();
          idxConcept++;
        }
      }
    }

    return out.toString();
  }

  private static void validateBorders(final Concept concept, final int sourceStringLength) {
    if (!areConceptBordersValid(concept, sourceStringLength)) {
      throw new IllegalArgumentException(getErrorMessage(concept, sourceStringLength));
    }
  }

  private static Concept findMinLeftBorderConcept(final List<Concept> concepts) {
    return concepts.stream()
        .min(Comparator.comparingInt(Concept::getMinLeftPosition)).orElse(null);
  }

  private static Concept findMaxRightBorderConcept(final List<Concept> concepts) {
    return concepts.stream()
        .max(Comparator.comparingInt(Concept::getMaxRightPosition)).orElse(null);
  }

  private static void copy(final int from, final int to, final char[] arr,
      final StringBuilder out) {
    for (int i = from; i < to; i++) {
      out.append(arr[i]);
    }
  }

  private static boolean areConceptBordersValid(final Concept concept, final int sourceStringLength) {
    return concept.getMinLeftPosition() >= 0 && concept.getMinLeftPosition() < sourceStringLength
        && concept.getMaxRightPosition() >= concept.getMinLeftPosition()
        && concept.getMaxRightPosition() <= sourceStringLength;
  }

  private static String getErrorMessage(final Concept concept, final int sourceStringLength) {
    return String.format(
        "Border(s) is not valid in concept %s: the min left position is %d; "
            + "the max right position is %d; the source string length: %d",
        concept.getClass().getName(), concept.getMinLeftPosition(), concept.getMaxRightPosition(),
        sourceStringLength);
  }
}
