package com.aliyun;

import java.util.LinkedList;

/**
 * @auther wy
 * @create 2021/10/29 19:04
 */

class ListStack<T> {
    private LinkedList<T> ll = new LinkedList<>();

    //入栈
    public void push(T t) {
        ll.addFirst(t);
    }

    //出栈
    public T pop() {
        return ll.removeFirst();
    }

    //栈顶元素
    public T peek() {
        T t = null;
        //直接取元素会报异常，需要先判断是否为空
        if (!ll.isEmpty())
            t = ll.getFirst();
        return t;
    }

    //栈空
    public boolean isEmpty() {
        return ll.isEmpty();
    }

}