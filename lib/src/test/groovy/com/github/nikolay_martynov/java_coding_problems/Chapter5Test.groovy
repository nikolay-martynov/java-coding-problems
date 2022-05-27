package com.github.nikolay_martynov.java_coding_problems

import spock.lang.Specification

class Chapter5Test extends Specification {

    def "SuffixTree initially empty"() {
        when:
        Chapter5.SuffixTree tree = new Chapter5.SuffixTree()
        then:
        tree.size() == 0
        !tree.remove("abc")
        !tree.contains("def")
    }

    def "SuffixTree one element flow"() {
        given:
        Chapter5.SuffixTree tree = new Chapter5.SuffixTree()
        when:
        boolean added = tree.add('abc')
        then:
        added
        tree.size() == 1
        tree.contains('abc')
        !tree.contains('ab')
        !tree.contains('abd')
        !tree.contains('abcd')
        when:
        boolean addedAgain = tree.add('abc')
        then:
        !addedAgain
        tree.size() == 1
        tree.contains('abc')
        !tree.contains('ab')
        !tree.contains('abd')
        !tree.contains('abcd')
        when:
        boolean deleted = tree.remove('abc')
        then:
        deleted
        tree.size() == 0
        !tree.contains('abc')
        !tree.contains('ab')
        !tree.contains('abd')
        !tree.contains('abcd')
        when:
        boolean deletedAgain = tree.remove('abc')
        then:
        !deletedAgain
        tree.size() == 0
        !tree.contains('abc')
        !tree.contains('ab')
        !tree.contains('abd')
        !tree.contains('abcd')
    }

    def "SuffixTree empty string"() {
        given:
        Chapter5.SuffixTree tree = new Chapter5.SuffixTree()
        when:
        boolean added = tree.add('')
        then:
        added
        tree.size() == 1
        tree.contains('')
        when:
        boolean addedAgain = tree.add('')
        then:
        !addedAgain
        tree.size() == 1
        tree.contains('')
        when:
        boolean deleted = tree.remove('')
        then:
        deleted
        tree.size() == 0
        !tree.contains('')
        when:
        boolean deletedAgain = tree.remove('')
        then:
        !deletedAgain
        tree.size() == 0
        !tree.contains('')
    }

    def "SuffixTree operate on longer word"() {
        given:
        Chapter5.SuffixTree tree = new Chapter5.SuffixTree()
        String shorter = 'cat'
        String longer = 'catalog'
        tree.add(shorter)
        when:
        boolean added = tree.add(longer)
        then:
        added
        tree.size() == 2
        tree.contains(shorter)
        tree.contains(longer)
        when:
        boolean addedAgain = tree.add(longer)
        then:
        !addedAgain
        tree.size() == 2
        tree.contains(shorter)
        tree.contains(longer)
        when:
        boolean deleted = tree.remove(longer)
        then:
        deleted
        tree.size() == 1
        tree.contains(shorter)
        !tree.contains(longer)
        when:
        boolean deletedAgain = tree.remove(longer)
        then:
        !deletedAgain
        tree.size() == 1
        tree.contains(shorter)
        !tree.contains(longer)
    }

    def "SuffixTree operate on shorter word"() {
        given:
        Chapter5.SuffixTree tree = new Chapter5.SuffixTree()
        String shorter = 'cat'
        String longer = 'catalog'
        tree.add(longer)
        when:
        boolean added = tree.add(shorter)
        then:
        added
        tree.size() == 2
        tree.contains(shorter)
        tree.contains(longer)
        when:
        boolean addedAgain = tree.add(shorter)
        then:
        !addedAgain
        tree.size() == 2
        tree.contains(shorter)
        tree.contains(longer)
        when:
        boolean deleted = tree.remove(shorter)
        then:
        deleted
        tree.size() == 1
        !tree.contains(shorter)
        tree.contains(longer)
        when:
        boolean deletedAgain = tree.remove(shorter)
        then:
        !deletedAgain
        tree.size() == 1
        !tree.contains(shorter)
        tree.contains(longer)
    }

    def "SuffixTree iterator empty"() {
        when:
        Chapter5.SuffixTree tree = new Chapter5.SuffixTree()
        then:
        !tree.iterator().hasNext()
        when:
        tree.iterator().next()
        then:
        thrown(NoSuchElementException)
    }

    def "SuffixTree iterator one element"() {
        given:
        Chapter5.SuffixTree tree = new Chapter5.SuffixTree()
        tree.add('cat')
        when:
        Iterator<String> iterator = tree.iterator()
        then:
        iterator.hasNext()
        iterator.next() == 'cat'
        !iterator.hasNext()
    }

    def "SuffixTree iterator empty string"() {
        given:
        Chapter5.SuffixTree tree = new Chapter5.SuffixTree()
        tree.add('')
        when:
        Iterator<String> iterator = tree.iterator()
        then:
        iterator.hasNext()
        iterator.next() == ''
        !iterator.hasNext()
    }

    def "SuffixTree iterator long and short"() {
        given:
        Chapter5.SuffixTree tree = new Chapter5.SuffixTree()
        tree.add('cat')
        tree.add('catalog')
        when:
        List<String> content = tree.iterator().toList()
        then:
        content ==~ ['cat', 'catalog']
    }

    def "SuffixTree iterator common prefix"() {
        given:
        Chapter5.SuffixTree tree = new Chapter5.SuffixTree()
        tree.add('catastrophe')
        tree.add('catalog')
        when:
        List<String> content = tree.iterator().toList()
        then:
        content ==~ ['catastrophe', 'catalog']
    }

    def "SuffixTree iterator no common prefix"() {
        given:
        Chapter5.SuffixTree tree = new Chapter5.SuffixTree()
        tree.add('cat')
        tree.add('dog')
        when:
        List<String> content = tree.iterator().toList()
        then:
        content ==~ ['cat', 'dog']
    }
}
