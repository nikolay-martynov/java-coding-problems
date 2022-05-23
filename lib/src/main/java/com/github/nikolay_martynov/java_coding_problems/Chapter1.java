package com.github.nikolay_martynov.java_coding_problems;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Chapter1 {

    /**
     * Counts symbols in a given string.
     *
     * @param source String where to count symbols.
     * @return Map where keys are symbols from the given string and value is the number of occurrences of each symbol
     * or an empty map if the given string is null or empty.
     * <p>
     * Task 1.
     */
    public static Map<Character, Integer> countSymbols(String source) {
        if (source == null || source.length() == 0) {
            return Collections.emptyMap();
        }
        Map<Character, Integer> counts = new HashMap<>();
        for (int i = 0; i < source.length(); i++) {
            counts.compute(source.charAt(i), (k, v) -> v == null ? 1 : ++v);
        }
        return counts;
    }

    /**
     * Finds first non-repeating symbol in a given string.
     *
     * @param source String where to find a non-repeating symbol.
     * @return First non-repeating (there is only one such symbol in the string)
     * symbol or an empty value if the given string is null or empty.
     * <p>
     * Task 2.
     */
    public static Optional<Character> findNonRepeatingSymbol(String source) {
        if (source == null || source.length() == 0) {
            return Optional.empty();
        }
        LinkedHashMap<Character, Boolean> seenOnce = new LinkedHashMap<>();
        for (int i = 0; i < source.length(); i++) {
            seenOnce.compute(source.charAt(i), (k, v) -> v == null);
        }
        return seenOnce.entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).findFirst();
    }

    /**
     * Reverses letters in all words in a given string.
     * <p>
     * Word is a contiguous sequence of letters as per {@link Character#isLetter}.
     * Word boundaries are any non-letter symbols.
     *
     * @param source String where to reverse letters in words.
     * @return String with letters reversed inside words.
     * <ul>
     *     <li>null -> null</li>
     *     <li>"" -> ""</li>
     *     <li>"abc def" -> "cba fed"</li>
     *     <li>"ab+_cd-#ef" -> "ba+_dc-#fe"</li>
     * </ul>
     * <p>
     * Task 3 part 1.
     */
    public static String reverseLettersInWords(String source) {
        if (source == null || source.isEmpty()) {
            return source;
        }
        StringBuilder result = new StringBuilder(source.length());
        int wordStart = 0;
        while (wordStart < source.length()) {
            for (; wordStart < source.length() && !Character.isLetter(source.charAt(wordStart)); wordStart++) {
                result.append(source.charAt(wordStart));
            }
            int wordEnd = wordStart;
            for (; wordEnd < source.length() && Character.isLetter(source.charAt(wordEnd)); wordEnd++) {
            }
            for (int letter = wordEnd - 1; letter >= wordStart; letter--) {
                result.append(source.charAt(letter));
            }
            wordStart = wordEnd;
        }
        return result.toString();
    }

    /**
     * Reverses words in a given string.
     * <p>
     * Word is a contiguous sequence of characters that are white space as per {@link Character#isWhitespace}.
     * Symbols inside words are not changed.
     * Whitespaces are not changed.
     * Only order of words is reversed.
     *
     * @param source String which words to reverse.
     * @return String with words being in reverse order.
     * <ul>
     *     <li>null -> null</li>
     *     <li>"" -> ""</li>
     *     <li>"abc def" -> "def abc"</li>
     *     <li>" ab  cd   ef    " -> " ef  cd   ab    "</li>
     * </ul>
     * <p>
     * Task 3 part 2.
     */
    static public String reverseWords(String source) {
        if (source == null || source.isEmpty()) {
            return source;
        }
        StringBuilder result = new StringBuilder(source.length());
        int leftWordStart = 0;
        int rightWordEnd = source.length() - 1;
        while (leftWordStart < source.length()) {
            for (; leftWordStart < source.length() && Character.isWhitespace(source.charAt(leftWordStart));
                 leftWordStart++) {
                result.append(source.charAt(leftWordStart));
            }
            if (leftWordStart == source.length()) {
                break;
            }
            for (; rightWordEnd >= 0 && Character.isWhitespace(source.charAt(rightWordEnd)); rightWordEnd--) {
            }
            int rightWordStart = rightWordEnd;
            for (; rightWordStart >= 0 && !Character.isWhitespace(source.charAt(rightWordStart)); rightWordStart--) {
            }
            for (int letter = rightWordStart + 1; letter <= rightWordEnd; letter++) {
                result.append(source.charAt(letter));
            }
            rightWordEnd = rightWordStart;
            int leftWordEnd = leftWordStart;
            for (; leftWordEnd < source.length() && !Character.isWhitespace(source.charAt(leftWordEnd));
                 leftWordEnd++) {
            }
            leftWordStart = leftWordEnd;
        }
        return result.toString();
    }

    /**
     * Checks if a given string contains only digits.
     *
     * @param source String to check.
     * @return true if all characters of the given string are digits (as per {@link Character#isDigit}).
     * Returns false if the given string is null or empty.
     * <p>
     * Task 4.
     */
    static public boolean isDigitsOnly(String source) {
        if (source == null || source.isEmpty()) {
            return false;
        }
        for (int i = 0; i < source.length(); i++) {
            if (!Character.isDigit(source.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Represents numbers of vowels and consonants in a string.
     *
     * @param vowels     Number of vowels.
     * @param consonants Number of consonants.
     */
    public record VowelsAndConsonants(int vowels, int consonants) {
    }

    private static final Set<Character> VOWELS = Set.of('a', 'e', 'i', 'o', 'u');

    private static final Set<Character> CONSONANTS = Collections.unmodifiableSet(
            IntStream.rangeClosed('a', 'z')
                    .mapToObj(c -> (char) c)
                    .filter(c -> !VOWELS.contains(c))
                    .collect(Collectors.toSet()));

    /**
     * Counts vowels and consonants in a given string.
     * <p>
     * Counts only latin symbols.
     *
     * @param source String where to count vowels and consonants.
     * @return Number of vowels and consonants in a given string or (0,0) if given string is null or empty.
     * <p>
     * Task 5.
     */
    static public VowelsAndConsonants countVowelsAndConsonants(String source) {
        int vowels = 0;
        int consonants = 0;
        for (int i = 0; source != null && i < source.length(); i++) {
            char c = source.charAt(i);
            if (VOWELS.contains(c)) {
                vowels++;
            } else if (CONSONANTS.contains(c)) {
                consonants++;
            }
        }
        return new VowelsAndConsonants(vowels, consonants);
    }

    /**
     * Deletes all whitespaces from a given string.
     *
     * @param source String from which to delete whitespaces.
     * @return String from which whitespaces were removed (as per {@link Character#isWhitespace}) or an empty string
     * if the given string was null
     * <p>
     * Task 8.
     */
    static public String deleteWhitespace(String source) {
        if (source == null || source.isEmpty()) {
            return "";
        }
        StringBuilder result = new StringBuilder(source.length());
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            if (!Character.isWhitespace(c)) {
                result.append(c);
            }
        }
        return result.toString();
    }

}
