package ir.ac.kntu.utility;

import java.util.*;

public class Trie {

    private final TrieNode root = new TrieNode();

    private static char changeCase(char c) {
        if (c >= 'a' && c <= 'z') {
            return (char) (c + 'A' - 'a');
        } else if (c >= 'A' && c <= 'Z') {
            return (char) (c - 'A' + 'a');
        }
        return 0;
    }

    public void insert(String word, int index) {
        TrieNode current = root;

        for (char c : word.toCharArray()) {
            current.children.putIfAbsent(c, new TrieNode());
            current = current.children.get(c);
        }

        current.isEndOfWord = true;
        current.indexes.add(index);
    }

    public void remove(String word, int index) {
        // Find the node corresponding to the last character of the word
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            if (!current.children.containsKey(c)) {
                return; // Word not found
            }
            current = current.children.get(c);
        }

        // Remove the word from the trie
        if (current.indexes.size() == 1) {
            current.isEndOfWord = false;
            current.indexes.clear(); // Or whatever value represents "not set"
            removeNode(current);
        } else {
            current.indexes.remove(index);
        }
    }

    private void removeNode(TrieNode node) {
        if (node == root || (!node.isEndOfWord && node.children.isEmpty())) {
            // This node is the root or has no children and is not an end-of-word, so it can't be deleted
            return;
        }
        TrieNode parent = getParent(node);
        if (parent != null) {
            parent.children.remove(getChar(node));
            removeNode(parent);
        }
    }

    private TrieNode getParent(TrieNode node) {
        // To get the parent node, we need to traverse the trie again starting from the root
        TrieNode current = root;
        LinkedList<TrieNode> queue = new LinkedList<>();
        queue.addLast(current);
        while (!queue.isEmpty()) {
            current = queue.removeFirst();
            for (TrieNode child : current.children.values()) {
                if (child == node) {
                    return current;
                }
                queue.addLast(child);
            }
        }
        return null; // Should never happen if the trie is correctly constructed
    }

    private char getChar(TrieNode node) {
        // To get the character that leads to this node, we need to traverse the trie again starting from the root
        TrieNode current = root;
        LinkedList<Character> queue = new LinkedList<>();
        queue.addLast(' ');
        while (!queue.isEmpty()) {
            char c = queue.removeFirst();
            current = current.children.get(c);
            if (current == node) {
                return c;
            }
            for (char childChar : current.children.keySet()) {
                queue.addLast(childChar);
            }
        }
        return ' '; // Should never happen if the trie is correctly constructed
    }

    public List<Integer> searchPrefix(String prefix) {
        TrieNode current = root;
        StringBuilder newPrefix = new StringBuilder();
        for (char c : prefix.toCharArray()) {
            if (!current.children.containsKey(c)) {
                if (!current.children.containsKey(changeCase(c))) {
                    return Collections.emptyList();
                } else {
                    current = current.children.get(changeCase(c));
                    newPrefix.append(changeCase(c));
                }
            } else {
                current = current.children.get(c);
                newPrefix.append(c);
            }
        }

        List<Integer> results = new ArrayList<>();
        collectWords(current, newPrefix.toString(), results);

        return results;
    }

    private void collectWords(TrieNode node, String prefix, List<Integer> results) {
        if (node.isEndOfWord) {
            results.addAll(node.indexes);
        }
        for (char c : node.children.keySet()) {
            collectWords(node.children.get(c), prefix + c, results);
        }
    }

    private static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();

        boolean isEndOfWord;

        List<Integer> indexes = new ArrayList<>();
    }
}