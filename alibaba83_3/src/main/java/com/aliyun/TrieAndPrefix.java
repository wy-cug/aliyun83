package com.aliyun;

/**
 * @auther wy
 * @create 2021/10/29 19:11
 */
public class TrieAndPrefix {
    private Trie trie;
    private String prefix;
    public TrieAndPrefix(Trie trie, String prefix){
        this.trie = trie;
        this.prefix = prefix;
    }

    public Trie getTrie() {
        return trie;
    }

    public void setTrie(Trie trie) {
        this.trie = trie;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
