package com.aliyun;

import java.util.*;

/**
 * @auther wy
 * @create 2021/10/29 18:52
 */
class Trie {
    public Map<Character, Trie> getChildren() {
        return children;
    }

    public void setChildren(Map<Character, Trie> children) {
        this.children = children;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public char getC() {
        return c;
    }

    public void setC(char c) {
        this.c = c;
    }

    private Map<Character, Trie> children;
    private boolean isEnd;
    private  char c;
    public Trie(char c) {
        children = new HashMap<>();
        isEnd = false;
        this.c = c;
    }
    public void insert(String word) {
        Trie node = this;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);

            if (!node.children.containsKey(ch)) {
                node.children.put(ch, new Trie(ch));
            }
            node = node.children.get(ch);
        }
        node.isEnd = true;
    }

    public boolean search(String word) {
        Trie node = searchPrefix(word);
        return node != null && node.isEnd;
    }

    public boolean startsWith(String prefix) {
        return searchPrefix(prefix) != null;
    }

    public Trie searchPrefix(String prefix) {
        if(prefix==""){
            return null;
        }
        Trie node = this;
        for (int i = 0; i < prefix.length(); i++) {
            char ch = prefix.charAt(i);

            if (!node.children.containsKey(ch)) {
                return null;
            }
            node = node.children.get(ch);
        }
        return node;
    }

    public List<String> getResultByPrefix(ListStack<TrieAndPrefix> stack){
        List<String> result = new ArrayList<>();
        while (!stack.isEmpty()){
            TrieAndPrefix temp = stack.pop();
            Trie tempTrie = temp.getTrie();
            if(tempTrie==null){
                System.out.println();
            }
            if(tempTrie.isEnd){
                result.add(temp.getPrefix());
            }
//            for(int i=0;i<52;i++){
//                if(tempTrie.children[i]!=null){
//                    String resultPrefix = "";
//                    if(i<26){
//                        resultPrefix = temp.getPrefix()+String.valueOf((char)('a'+i));
//                    }else{
//                        resultPrefix = temp.getPrefix()+String.valueOf((char)('A'+(i-26)));
//                    }
//                    stack.push(new TrieAndPrefix(tempTrie.children[i], resultPrefix));
//                }
//            }
            for(char c:tempTrie.children.keySet()){

                String resultPrefix = "";
                char ch = tempTrie.children.get(c).c;
                resultPrefix = temp.getPrefix()+String.valueOf(ch);
                stack.push(new TrieAndPrefix(tempTrie.children.get(c), resultPrefix));
            }
        }
        return result;
    }
    public static void main(String[] args) {
        Trie t = new Trie(' ');
        t.insert("apple");
        t.insert("abbb");
        t.insert("appae");
        t.insert("aPple");
        t.insert("aPpbb");
        t.insert("aPpae");
        t.insert("aBpae");
        t.insert("bPpae");
        t.insert("app");
        String []prefixList =new String[]{"abb","app"};
        for(String prefix : prefixList) {
            Trie trie = t.searchPrefix(prefix);
            if(trie==null){
                continue;
            }
            ListStack<TrieAndPrefix> stack = new ListStack<>();
            stack.push(new TrieAndPrefix(trie, prefix));
//            for(char ch:trie.children.keySet()){
//                stack.push(new TrieAndPrefix(trie.children.get(ch), prefix+trie.children.get(ch).c));
//            }
            List<String> result = t.getResultByPrefix(stack);
            for (String s : result) {
                System.out.println(s);
            }
            System.out.println();
        }
    }
}
