package com.github.nikolay_martynov.java_coding_problems

import spock.lang.Specification

class Chapter1Test extends Specification {
    def "CountSymbols"() {
        expect:
        Map<Character, Integer> expected = new HashMap<>(c.collectEntries { k, v -> [(k.charAt(0)): v] })
        Map<Character, Integer> result = new HashMap<>(Chapter1.countSymbols(s))
        expected == result
        where:
        s              | c
        null           | [:]
        ''             | [:]
        'a'            | [a: 1]
        'aa'           | [a: 2]
        'abcabcdefabc' | [a: 3, b: 3, c: 3, d: 1, e: 1, f: 1]
    }

    def "FindNonRepeatingSymbol"() {
        expect:
        Chapter1.findNonRepeatingSymbol(s) == c
        where:
        s      | c
        null   | Optional.empty()
        ''     | Optional.empty()
        'a'    | Optional.of((char) 'a')
        'aaa'  | Optional.empty()
        'abc'  | Optional.of((char) 'a')
        'abca' | Optional.of((char) 'b')
    }

    def "reverseLettersInWords"() {
        expect:
        Chapter1.reverseLettersInWords(s) == r
        where:
        s            | r
        null         | null
        ''           | ''
        'abc def'    | 'cba fed'
        'ab+_cd-#ef' | 'ba+_dc-#fe'
        'ab=-'       | 'ba=-'
        '=-ab'       | '=-ba'
        '-a+'        | '-a+'
    }

    def "ReverseWords"() {
        expect:
        Chapter1.reverseWords(s) == r
        where:
        s                  | r
        null               | null
        ''                 | ''
        'abc def'          | 'def abc'
        ' ab  cd   ef    ' | ' ef  cd   ab    '
        'ab  '             | 'ab  '
        '  ab'             | '  ab'
        ' a b '            | ' b a '
        'a'                | 'a'
        ' '                | ' '
    }

    def "IsDigitsOnly"() {
        expect:
        Chapter1.isDigitsOnly(s) == r
        where:
        s      | r
        null   | false
        ''     | false
        '123'  | true
        '12 3' | false
        'abc'  | false
    }

    def "CountVowelsAndConsonants"() {
        expect:
        with(Chapter1.countVowelsAndConsonants(s)) {
            it.vowels() == r[0]
            it.consonants() == r[1]
        }
        where:
        s            | r
        null         | [0, 0]
        ''           | [0, 0]
        'abc'        | [1, 2]
        'aaa'        | [3, 0]
        'modern car' | [3, 6]
    }

    def "DeleteWhitespace"() {
        expect:
        Chapter1.deleteWhitespace(s) == r
        where:
        s               | r
        null            | ''
        ''              | ''
        'a'             | 'a'
        '   '           | ''
        ' a  b   c    ' | 'abc'
    }

    def "GeneratePermutations"() {
        expect:
        Chapter1.generatePermutations(s).collect() ==~ p
        where:
        s      | p
        null   | []
        ''     | []
        'a'    | ['a']
        'ab'   | ['ab', 'ba']
        'abc'  | ['abc', 'acb', 'bac', 'cba', 'bca', 'cab']
        'abcd' | ['abcd',
                  'abdc',
                  'acbd',
                  'acdb',
                  'adbc',
                  'adcb',
                  'bacd',
                  'badc',
                  'bcad',
                  'bcda',
                  'bdac',
                  'bdca',
                  'cabd',
                  'cadb',
                  'cbad',
                  'cbda',
                  'cdab',
                  'cdba',
                  'dabc',
                  'dacb',
                  'dbac',
                  'dbca',
                  'dcab',
                  'dcba',
        ]
    }

    def "FindMostOccurringCharacter"() {
        expect:
        Chapter1.findMostOccurringCharacter(s) == c
        where:
        s         | c
        null      | Optional.empty()
        ''        | Optional.empty()
        'a'       | Optional.of('a' as char)
        'abbccba' | Optional.of('b' as char)
    }

    def "CountSubstring"() {
        expect:
        Chapter1.countSubstring(source, substring) == count
        where:
        source | substring | count
        null   | 'a'       | 0
        'a'    | null      | 0
        ''     | 'a'       | 0
        'a'    | ''        | 0
        'a'    | 'b'       | 0
        'ab'   | 'a'       | 1
        'aaaa' | 'aa'      | 3
    }

    def "IsRepeatingSubstrings"() {
        expect:
        Chapter1.isRepeatingSubstrings(s) == r
        where:
        s                    | r
        null                 | false
        ''                   | false
        'a'                  | false
        'aa'                 | true
        'aba'                | false
        'abab'               | true
        'aaaaaaab'           | false
        '111112345111112345' | true
    }
}
