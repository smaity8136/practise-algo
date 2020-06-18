package com.practice.test;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {

    public class DlinkedNoode {
        int key;
        int val;

        DlinkedNoode pre;
        DlinkedNoode post;

        public void add(DlinkedNoode node, DlinkedNoode head) {
            node.pre = head;
            node.post = head.post;
            node.post.pre = node;
        }

        public void delete(DlinkedNoode node) {
            DlinkedNoode pre = node.pre;
            DlinkedNoode post = node.post;
            post.pre = pre;
            pre.post = post;
        }

        public DlinkedNoode popTail(DlinkedNoode tail) {
            DlinkedNoode node = tail.pre;
            this.delete(node);

            return node;
        }

        public void moveToHead(DlinkedNoode node, DlinkedNoode head) {
            this.delete(node);
            this.add(node, head);
        }

    }
       Map<Integer, DlinkedNoode>  cache;
       int count =0,capacity = 0;
        DlinkedNoode head;
        DlinkedNoode tail;

    public LRUCache(int capacity){

        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.head =  new DlinkedNoode();
        this.tail = new DlinkedNoode();
        this.head.pre = null;
        this.head.post = tail;
        this.tail.post = null;
        this.tail.pre = head;
    }


    public int get(int key){
        DlinkedNoode node =  this.cache.get(key);
        if(node == null) return -1;
        this.head.moveToHead(node,this.head);
        return node.val;
    }

    public void put(int key,int val){

        DlinkedNoode node = this.cache.get(key);
        if(node == null){
            DlinkedNoode nNode = new DlinkedNoode();
            nNode.key = key;
            nNode.val = val;
            this.head.add(nNode,this.head);
            cache.put(key, nNode);
            count++;
            if(count>this.capacity){
                DlinkedNoode rNode =  this.head.popTail(this.tail);
                cache.remove(rNode.key);
                count--;
            }

        }else{
            node.val = val;
            this.head.moveToHead(node,this.head);
        }

    }

    public static void main(String[] args) {

        LRUCache cache = new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.get(1);       // returns 1
        cache.put(3, 3);    // evicts key 2
        cache.get(2);       // returns -1 (not found)
        cache.put(4, 4);    // evicts key 1
        cache.get(1);       // returns -1 (not found)
        cache.get(3);       // returns 3
        cache.get(4);


    }

}
