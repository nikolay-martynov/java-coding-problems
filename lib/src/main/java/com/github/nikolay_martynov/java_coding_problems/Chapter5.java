package com.github.nikolay_martynov.java_coding_problems;

import java.util.*;
import java.util.function.Supplier;

public class Chapter5 {

    /**
     * Set based on a suffix tree.
     * <p>
     * This set could require less space if many similarly starting strings are stored.
     * <p>
     * {@link #add}, {@link #remove}, {@link #contains} complexity is proportional to string length.
     * <p>
     * This implementation is not thread safe and requires external synchronization.
     * <p>
     * This implementation does not permit null elements.
     * <p>
     * Iterator provided by this implementation is read-only and does not support element removal.
     * Additionally, this implementation does not permit modifications while iterating using an iterator.
     * Using an old iterator after a collection has been modified will throw {@link ConcurrentModificationException}.
     * <p>
     * Task 124.
     */
    static public class SuffixTree extends AbstractSet<String> implements Set<String> {

        private int size;

        private Node root;

        private int modCount;

        private final Supplier<Map<Character, Node>> characterMapSupplier;

        /**
         * Creates a new instance.
         */
        public SuffixTree() {
            this(HashMap::new);
        }

        /**
         * Creates a new instance.
         *
         * @param characterMapSupplier Supplier of new character maps for tree nodes.
         * @throws NullPointerException If characterMapSupplier is null.
         */
        public SuffixTree(Supplier<Map<Character, Node>> characterMapSupplier) {
            this.characterMapSupplier = Objects.requireNonNull(characterMapSupplier,
                    "characterMapSupplier must not be null");
        }

        @Override
        public boolean contains(Object o) {
            String s = (String) Objects.requireNonNull(o);
            if (root == null) {
                return false;
            }
            Node currentNode = root;
            for (int i = 0; i < s.length(); i++) {
                Node child = currentNode.children.get(s.charAt(i));
                if (child == null) {
                    return false;
                }
                currentNode = child;
            }
            return currentNode.endOfWord;
        }

        @Override
        public boolean add(String s) {
            Objects.requireNonNull(s);
            if (root == null) {
                root = createNode();
            }
            boolean changed = false;
            Node currentNode = root;
            for (int i = 0; i < s.length(); i++) {
                Node child = currentNode.children.get(s.charAt(i));
                if (child == null) {
                    child = createNode();
                    currentNode.children.put(s.charAt(i), child);
                    changed = true;
                }
                currentNode = child;
            }
            if (!currentNode.endOfWord) {
                currentNode.endOfWord = true;
                changed = true;
            }
            if (changed) {
                size++;
                modCount++;
            }
            return changed;
        }

        @Override
        public boolean remove(Object o) {
            String s = (String) Objects.requireNonNull(o);
            if (root == null) {
                return false;
            }
            Node currentNode = root;
            Node lastBranchNode = root;
            int lastBranchIndex = -1;
            for (int i = 0; i < s.length(); i++) {
                Node child = currentNode.children.get(s.charAt(i));
                if (child == null) {
                    // Word to remove is not found.
                    return false;
                }
                if (currentNode.children.size() > 1) {
                    lastBranchNode = currentNode;
                    lastBranchIndex = i;
                }
                currentNode = child;
            }
            if (!currentNode.endOfWord) {
                // We have a longer word but not this given shorter one.
                return false;
            }
            currentNode.endOfWord = false;
            if (currentNode.children.isEmpty() && lastBranchIndex >= 0) {
                lastBranchNode.children.remove(s.charAt(lastBranchIndex));
            }
            if (root.children.isEmpty()) {
                root = null;
            }
            size--;
            modCount++;
            return true;
        }

        @Override
        public void clear() {
            size = 0;
            root = null;
            modCount++;
        }

        /**
         * {@inheritDoc}
         * <p>
         * Supplied iterator does not support {@link Iterator#remove}.
         */
        @Override
        public Iterator<String> iterator() {
            return new SuffixTreeIterator();
        }

        @Override
        public int size() {
            return size;
        }

        private Node createNode() {
            Node node = new Node();
            node.children = characterMapSupplier.get();
            return node;
        }

        private static class Node {
            Map<Character, Node> children;
            boolean endOfWord;

            @Override
            public String toString() {
                return new StringJoiner(", ", "[", "]")
                        .add(String.valueOf(endOfWord))
                        .add(Objects.toString(children))
                        .toString();
            }
        }

        private class SuffixTreeIterator implements Iterator<String> {

            private record Branch(Node node, Iterator<Map.Entry<Character, Node>> childrenIterator) {
            }

            private int createdAt;
            private Deque<Branch> path;
            private StringBuilder prefix;
            private Node lastWordPrefixNode;
            private Character lastWordPrefixCharacter;

            public SuffixTreeIterator() {
                this.createdAt = modCount;
                if (root != null) {
                    path = new LinkedList<>();
                    path.add(new Branch(root, root.children.entrySet().iterator()));
                    prefix = new StringBuilder();
                }
            }

            private void push(char character, Node node) {
                path.addFirst(new Branch(node, node.children.entrySet().iterator()));
                prefix.append(character);
            }

            private Branch head() {
                return path.getFirst();
            }

            private void pop() {
                lastWordPrefixNode = path.removeFirst().node;
                if (!prefix.isEmpty()) {
                    // Could be an empty string in root.
                    lastWordPrefixCharacter = prefix.charAt(prefix.length() - 1);
                    prefix.deleteCharAt(prefix.length() - 1);
                }
            }

            private void assertNotModified() {
                if (modCount != createdAt) {
                    throw new ConcurrentModificationException(
                            "The suffix tree has been modified since creation of this iterator");
                }
            }

            @Override
            public boolean hasNext() {
                assertNotModified();
                return path != null && !path.isEmpty();
            }

            @Override
            public String next() {
                assertNotModified();
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                // Advance to the end of the longest word.
                while (head().childrenIterator.hasNext()) {
                    Map.Entry<Character, Node> child = head().childrenIterator.next();
                    push(child.getKey(), child.getValue());
                }
                if (!head().node.endOfWord) {
                    throw new IllegalStateException(
                            "Expected deepest child or top of stack" +
                                    " to be end of word but it was not");
                }
                String word = prefix.toString();
                // Get back to where we have siblings or end of word.
                pop();
                while (!path.isEmpty() && !head().node.endOfWord && !head().childrenIterator.hasNext()) {
                    pop();
                }
                return word;
            }

        }

    }

}
