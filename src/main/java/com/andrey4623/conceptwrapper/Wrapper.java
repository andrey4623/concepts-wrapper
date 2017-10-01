/*
 * Copyright (c) Andrey Kolchanov, 2017.
 */

package com.andrey4623.conceptwrapper;

import com.andrey4623.conceptwrapper.concept.Concept;
import java.util.Comparator;
import java.util.List;

public class Wrapper {

  public static String wrap(String source, List<Concept> concepts) {
    sort(concepts);
    char[] arr = source.toCharArray();
    validateConcepts(arr, concepts);
    return doWrap(arr, concepts);
  }

  private static void sort(List<Concept> concepts) {
    concepts.sort(Comparator.comparingInt(Concept::getMinLeftPosition));
  }

  private static void validateConcepts(char[] sourceCharArray, List<Concept> concepts) {
    if (concepts.isEmpty()) {
      return;
    }

    // Verify the first and the last concepts.
    if (!isConceptBorderEligible(concepts.get(0), sourceCharArray.length) ||
        !isConceptBorderEligible(concepts.get(concepts.size() - 1), sourceCharArray.length)) {
      throw new IllegalArgumentException("isConceptBorderEligible is false");
    }
  }

  private static void copy(int from, int to, char[] arr, StringBuilder out) {
    for (int i = from; i < to; i++) {
      out.append(arr[i]);
    }
  }

  private static String doWrap(char[] arr, List<Concept> concepts) {
    StringBuilder out = new StringBuilder();

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

  private static boolean isConceptBorderEligible(Concept concept, int sourceStringLength) {
    return concept.getMinLeftPosition() >= 0 && concept.getMinLeftPosition() < sourceStringLength
        && concept.getMaxRightPosition() >= concept.getMinLeftPosition()
        && concept.getMaxRightPosition() <= sourceStringLength;
  }
}
