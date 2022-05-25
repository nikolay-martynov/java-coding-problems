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
            for (; leftWordStart < source.length() && Character.isWhitespace(source.charAt(leftWordStart)); leftWordStart++) {
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
            for (; leftWordEnd < source.length() && !Character.isWhitespace(source.charAt(leftWordEnd)); leftWordEnd++) {
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

    private static final Set<Character> CONSONANTS = Collections.unmodifiableSet(IntStream.rangeClosed('a', 'z').mapToObj(c -> (char) c).filter(c -> !VOWELS.contains(c)).collect(Collectors.toSet()));

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

    /**
     * Generates all permutations of a given string.
     *
     * @param source String from which to generate permutations.
     * @return Iterator that returns all (not only unique) permutations of a given string or returns nothing
     * if the given string is null or empty.
     * <p>
     * Task 10.
     */
    public static Iterator<String> generatePermutations(String source) {
        if (source == null || source.isEmpty()) {
            return new Iterator<String>() {
                @Override
                public boolean hasNext() {
                    return false;
                }

                @Override
                public String next() {
                    throw new NoSuchElementException("No permutations for null or empty string");
                }
            };
        }
        return new Iterator<String>() {
            /**
             * We can take all permutations of 0123...(source.length()-1)
             * and treat number in each position as an index in the source string.
             * Then we can re-compose each permutation of the original string from those indexes.
             *
             * Each indexes[i] contains a character position in the source string
             * that should be used in the i-th position of the current permutation.
             * Means, permutation.charAt(i)==source.charAt(indexes[i]).
             *
             * We'll generate permutations of indexes in lexicographical order.
             * The first permutation would be 0123...(source.length()-1) - this is just a copy of the source string.
             * The last permutation would be (source.length()-1)...3210.
             */
            final int[] indexes;

            /**
             * Indicates if we have already returned the last permutation or not yet.
             *
             * Reminder: last permutation corresponds to (source.length()-1)...3210 indexes since we use
             * lexicographical order.
             */
            boolean hasMore;

            {
                // Reminder: first permutation is 0123...(source.length()-1) - the same as source string.
                indexes = new int[source.length()];
                for (int i = 0; i < source.length(); i++) {
                    indexes[i] = i;
                }
                // We've already covered the case with an empty string, so we always have at least one permutation
                // in the beginning - the original string itself.
                // Even when it is a single character.
                hasMore = true;
            }

            @Override
            public boolean hasNext() {
                return hasMore;
            }

            @Override
            public String next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more permutations");
                }
                // We reconstruct and return string from permutation of indices
                // that has been generated either during initialization or previous iteration.
                StringBuilder result = new StringBuilder(source.length());
                for (int i = 0; i < source.length(); i++) {
                    result.append(source.charAt(indexes[i]));
                }
                // Now we can advance to the next permutation.
                // We're moving from 0123456789 to 9876543210 in the lexicographical order.
                // This would look like the following:
                // 0123456789
                // 0123456798
                // 0123456879
                // 0123456897
                // 0123456978
                // 0123456987
                // 0123457689
                // 0123457698
                // 0123457869
                // 0123457896
                // 0123457968
                // 0123457986
                // ...
                // 9876543210
                // When we have some decreasing sequence at the end,
                // to go to the next permutation in the lexicographical order,
                // we need to overflow to the next place to the left
                // and start from the smallest value to the right of this position.
                // For example:
                // 0123456987
                // 0123457689
                // The decreasing sequence was 987, and before it was 6.
                // We can now swap 6 with 7 to get 7986.
                // Notice that what we have after 7 is still a decreasing sequence.
                // This is the highest value: we cannot increase 986 without another overflow.
                // To make the smallest value in those places,
                // we can just reverse this right part after 7 so 986 becomes 689.
                // That's how we've advanced from
                // 0123456987 to
                // 0123457689.
                // First, we find the beginning of the decreasing sequence in the end.
                int k = source.length() - 2;
                while (k >= 0 && indexes[k] > indexes[k + 1]) {
                    k--;
                }
                if (k < 0) {
                    // It looks like we have 9876543210 - this is the last permutation.
                    hasMore = false;
                    return result.toString();
                }
                // Now k is the position before decreasing sequence.
                // This means k is the position we need to overflow to by swapping with
                // a higher value than we currently have in k that is to the right of k
                // and which is closest to k.
                // Since numbers there are a decreasing sequence,
                // we search for the first one that isn't greater than k.
                int t = k + 1;
                while (t < source.length() - 1 && indexes[k] < indexes[t + 1]) {
                    t++;
                }
                // Now we can swap k-th and t-th elements.
                {
                    int tmp = indexes[t];
                    indexes[t] = indexes[k];
                    indexes[k] = tmp;
                }
                // And reverse the rest of numbers to the right of k to reset it from the highest to the lowest value.
                {
                    int i;
                    int j;
                    for (i = k + 1, j = source.length() - 1; i < j; i++, j--) {
                        int tmp = indexes[j];
                        indexes[j] = indexes[i];
                        indexes[i] = tmp;
                    }
                }
                return result.toString();
            }
        };
    }

    /**
     * Finds most occurring character in a given string.
     *
     * @param source String where to search for most occurring characters.
     * @return Most occurring character or an empty value if given string is null or empty.
     * <p>
     * Task 14.
     */
    static public Optional<Character> findMostOccurringCharacter(String source) {
        if (source == null || source.isEmpty()) {
            return Optional.empty();
        }
        Map<Character, Integer> occurrences = new HashMap<>();
        for (int i = 0; i < source.length(); i++) {
            occurrences.compute(source.charAt(i), (k, v) -> v == null ? 1 : ++v);
        }
        Optional<Character> mostOccurringCharacter = Optional.empty();
        int maxOccurrences = 0;
        for (Map.Entry<Character, Integer> characterInfo : occurrences.entrySet()) {
            if (characterInfo.getValue() > maxOccurrences) {
                maxOccurrences = characterInfo.getValue();
                mostOccurringCharacter = Optional.of(characterInfo.getKey());
            }
        }
        return mostOccurringCharacter;
    }

    /**
     * Counts substring in a given string.
     *
     * @param source    String where to search for a substring.
     * @param substring Substring to count.
     * @return Number of all occurrences of substring in a string or 0 if source or substring are null or empty.
     * All occurrences' means that all overlapping substrings will be counted separately.
     * For example,
     * countSubstring('aaaa', 'aa') == 3
     * <p>
     * Task 17 part 2.
     */
    static public int countSubstring(String source, String substring) {
        if (source == null || source.isEmpty() || substring == null || substring.isEmpty()) {
            return 0;
        }
        int count = 0;
        int start = 0;
        while ((start = source.indexOf(substring, start)) >= 0) {
            count++;
            start++;
        }
        return count;
    }

    /**
     * Checks if a given string is a repetition of some other string.
     *
     * @param source String to check for repetition.
     * @return false if a given string is null or shorter than two characters and is not a repetition of some substring.
     * <ul>
     *     <li>null -> false</li>
     *     <li>"" -> false</li>
     *     <li>"a" -> false</li>
     *     <li>"aa" -> true</li>
     *     <li>"ab" -> false</li>
     *     <li>"aba" -> false</li>
     *     <li>"abab" -> true</li>
     * </ul>
     *
     * Task 20 part 2.
     */
    static public boolean isRepeatingSubstrings(String source) {
        if (source == null || source.isEmpty()) {
            return false;
        }
        List<Integer> possibleSubstringsLengths = new LinkedList<>();
        for (int substringLength = 1; substringLength <= source.length(); substringLength++) {
            if (possibleSubstringsLengths.isEmpty() && substringLength > source.length() / 2) {
                break;
            }
            {
                ListIterator<Integer> possibleSubstringsIterator = possibleSubstringsLengths.listIterator();
                while (possibleSubstringsIterator.hasNext()) {
                    int possibleSubstringLength = possibleSubstringsIterator.next();
                    boolean substringStillRepeats =
                            source.charAt(substringLength - 1)
                                    == source.charAt((substringLength - 1) % possibleSubstringLength);
                    if (!substringStillRepeats) {
                        possibleSubstringsIterator.remove();
                    }
                }
            }
            boolean lengthIsMatching = source.length() % substringLength == 0;
            if (lengthIsMatching && substringLength < source.length()) {
                boolean prefixIsRepetitionOfOtherSubstring = false;
                {
                    ListIterator<Integer> candidatesIterator = possibleSubstringsLengths.listIterator();
                    while (candidatesIterator.hasNext() && !prefixIsRepetitionOfOtherSubstring) {
                        int otherCandidateLength = candidatesIterator.next();
                        if (substringLength % otherCandidateLength != 0) {
                            continue;
                        }
                        prefixIsRepetitionOfOtherSubstring = true;
                        int indexInOther = 0;
                        for (int indexInCurrent = 0; indexInCurrent < substringLength; indexInCurrent++) {
                            if (source.charAt(indexInCurrent) != source.charAt(indexInOther)) {
                                prefixIsRepetitionOfOtherSubstring = false;
                                break;
                            }
                            indexInOther = (indexInOther + 1) % otherCandidateLength;
                        }
                    }
                }
                if (!prefixIsRepetitionOfOtherSubstring) {
                    possibleSubstringsLengths.add(substringLength);
                }
            }
        }
        return !possibleSubstringsLengths.isEmpty();
    }

}
