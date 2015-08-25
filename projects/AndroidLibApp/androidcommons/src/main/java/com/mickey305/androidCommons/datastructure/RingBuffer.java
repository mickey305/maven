/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 K.Misaki
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.mickey305.androidCommons.datastructure;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.*;

/**
 *
 * @param <ET> is the generics (the Class to cache)
 */
public class RingBuffer<ET> implements Cloneable {

    /** Class Name */
    public static final String TAG = RingBuffer.class.getName();

    /** Object Array */
    private ET[] queue;

    /** Array Header */
    private int index;

    /** Object filled */
    private boolean fill;

    /** Origin Callback */
    private Callback callback = null;

    /**
     *
     * @param <ET> is the generics Object
     */
    public interface Callback<ET> {

        /**
         *
         * @param leakedList is the leaked-list of RingBuffer
         */
        void onLeakedObjects(List<ET> leakedList);
    }

    /**
     * enum defined queue size
     */
    public enum SIZE {
        MIN     (8),
        SHORT   (32),
        DEFAULT (128),
        LONG    (512),
        MAX     (2048);

        SIZE(int num) { this.num = num; }
        final int num;
    }

    /**
     * enum list queue mode
     */
    public enum MODE {
        FIFO    (0),
        LIFO    (1);

        MODE(int code) { this.code = code; }
        final int code;
    }

    /**
     *
     * @param n is queue size
     */
    public RingBuffer(int n) {
        this.createQueue(n);
    }

    /**
     *
     * @param n is queue size
     * @param list is Object list
     */
    public RingBuffer(int n, List<ET> list) {
        this(n);
        this.set(list);
    }

    /**
     *
     * @param size is queue size
     */
    public RingBuffer(SIZE size) {
        this.createQueue(size);
    }

    /**
     *
     * @param size is queue size
     * @param list is Object list
     */
    public RingBuffer(SIZE size, List<ET> list) {
        this(size);
        this.set(list);
    }

    /**
     *
     * @param callback is the origin interface
     */
    public void setCallback(Callback<ET> callback) {
        this.callback = callback;
    }

    /**
     *
     * @return true or false
     */
    public boolean isFill() {
        return fill;
    }

    /**
     *
     * @return Header point
     */
    public int getIndex() {
        return index;
    }

    /**
     *
     * @return Header point
     */
    public int getHeader() {
        return this.getIndex();
    }

    /**
     *
     * @param e is object params
     */
    @SafeVarargs
    public final void set(ET... e) {
        for (ET object: e) {
            this.enqueue(object);
        }
    }

    /**
     *
     * @param list is Object list
     */
    public void set(List<ET> list) {
        // list.forEach(this::enqueue);
        for(ET e: list) {
            this.enqueue(e);
        }
    }

    /**
     *
     * @param e is object params
     */
    @SafeVarargs
    public final void push(ET... e) {
        this.set(e);
    }

    /**
     *
     * @param list is object list
     */
    public void push(List<ET> list) {
        this.set(list);
    }

    /**
     *
     * @return Object or null pointer
     */
    public ET remove() {
        return this.dequeue();
    }

    /**
     *
     * @param size is delete size number
     * @return List of Object or null pointer
     */
    public List<ET> remove(int size) {
        List<ET> list = new ArrayList<>();
        for(int i=0; i < size; i++) {
            list.add(this.remove());
        }
        return list;
    }

    /**
     *
     * @return List
     */
    public List<ET> removeAll() {
        return this.remove(this.insertedSize());
    }

    /**
     *
     * @return Object or null pointer
     */
    public ET poll() {
        return this.remove();
    }

    /**
     *
     * @param size is delete size number
     * @return List of Object or null pointer
     */
    public List<ET> poll(int size) {
        return this.remove(size);
    }

    /**
     *
     * @return List
     */
    public List<ET> pollAll() {
        return this.removeAll();
    }

    /**
     *
     * @param num is queue size
     * @param mode is queue mode
     * @return List
     */
    public List<ET> getList(int num, MODE mode) {
        final List<ET> list = new ArrayList<>();
        num = (!fill && num > insertedSize())? insertedSize(): num;
        int p = index;
        for(int i=0; i < num; i++) {
            p = (--p < 0)? queue.length -1: p;
            list.add(queue[p]);
        }
        if(mode == MODE.FIFO) {
            Collections.reverse(list);
        }
        return (list);
    }

    /**
     *
     * @param mode is queue mode
     * @return List
     */
    public List<ET> getList(MODE mode) {
        return this.getList(queue.length, mode);
    }

    /**
     *
     * @param n is queue index
     * @param mode is queue mode
     * @return Object or null pointer
     */
    public ET get(int n, MODE mode) {
        if(fill) {
            return getObject(n, mode);
        } else {
            List<ET> tmpList = getList(mode);
            if(tmpList.size() == 0) {
                return null;
            }
            n %= tmpList.size();
            return tmpList.get(n);
        }
    }

    /**
     *
     * @return queue size
     */
    public int length() {
        return this.size();
    }

    /**
     *
     * @return queue size
     */
    public int size() {
        return queue.length;
    }

    /**
     *
     * @return Object inserted size
     */
    public int insertedLength() {
        return this.insertedSize();
    }

    /**
     *
     * @return Object inserted size
     */
    public int insertedSize() {
        int cnt = 0;
        for(ET e: this.queue) {
            if(e != null) cnt++;
        }
        return (cnt);
    }

    /**
     * initialize this field
     * @param n is queue size
     */
    @SuppressWarnings("unchecked")
    public void createQueue(int n) {
        this.queue = (ET[]) new Object[n];
        this.index = 0;
        this.fill = false;
    }

    /**
     *
     * @param size is defined size
     */
    public void createQueue(SIZE size) {
        this.createQueue(size.num);
    }

    /**
     *
     * @param n is queue index
     * @param mode is queue mode
     * @return is true or false
     */
    public boolean isEmpty(int n, MODE mode) {
        return getObject(n, mode) == null;
    }

    /**
     *
     * @return is true or false
     */
    public boolean isEmpty() {
        return insertedLength() == 0;
    }

    /**
     * get this Object (same pointer)
     * @return myself
     */
    public RingBuffer<ET> getInstance() {
        return this;
    }

    /**
     *
     * @param n is queue size
     */
    public void resize(int n) {
        if(queue.length == n) { return; }
        final List<ET> tmpList = this.getList(MODE.FIFO);
        this.createQueue(n);
        this.set(tmpList);
        if(callback != null && tmpList.size() > n) {
            List<ET> leakedList = new ArrayList<>();
            for(int i=0; i < tmpList.size() -n; i++) {
                leakedList.add(tmpList.get(i));
            }
            callback.onLeakedObjects(leakedList);
        }
    }

    /**
     *
     * @param size is defined size
     */
    public void resize(SIZE size) {
        this.resize(size.num);
    }

    /**
     * Object queue data reversing
     * @return true or false
     */
    public boolean reverse() {
        if(this.isEmpty()) { return false; }
        int tmpHeader = this.index;
        List<ET> list = this.getList(MODE.FIFO);
        Collections.reverse(list);
        this.createQueue(this.size());
        this.set(list);
        this.moveHeader(tmpHeader);
        return true;
    }

    /**
     *
     * @param point is reset-header-point
     */
    public void moveHeader(int point) {
        List<ET> list = this.removeAll();
        this.index = point;
        for(int i=0; i < list.size(); i++) {
            this.index--;
            this.index = (index < 0)? queue.length +index: index;
        }
        push(list);
    }

    /**
     * get this Object (difference pointer)
     * @return cloned RingBuffer Object
     */
    @Override
    public RingBuffer<ET> clone() {
        RingBuffer<ET> scope = null;
        try {
            scope = (RingBuffer<ET>) super.clone();
            scope.queue = this.queue.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return scope;
    }

    /**
     * Object queue sorting
     * @param comparator is Interface to compare
     * @return true or false
     */
    public boolean sort(Comparator<ET> comparator) {
        if(this.isEmpty()) { return false; }
        int tmpHeader = this.index;
        List<ET> list = this.getList(MODE.FIFO);
        Collections.sort(list, comparator);
        this.createQueue(this.size());
        this.set(list);
        this.moveHeader(tmpHeader);
        return true;
    }

    /**
     *
     * @param i is Buffer Index
     * @param j is Buffer Index
     * @return true or false
     */
    public boolean swap(int i, int j) {
        if(this.isEmpty()) { return false; }
        if(i%insertedSize() == j%insertedSize()) { return false; }
        List<ET> list = this.getList(MODE.FIFO);
        Collections.swap(list, i % list.size(), j % list.size());
        final int tmpHeader = this.index;
        this.createQueue(this.size());
        this.set(list);
        this.moveHeader(tmpHeader);
        return true;
    }

    /**
     *
     * @param indexParams is the parameter of Object queue Indexes
     */
    public void swap(Map<Integer, Integer> indexParams) {
        if(this.isEmpty()) { return; }
        List<ET> list = this.getList(MODE.FIFO);
        int cnt = 0;
        for(Map.Entry entry: indexParams.entrySet()) {
            final int i = (int) entry.getKey();
            final int j = (int) entry.getValue();
            if(i%insertedSize() == j%insertedSize()) { continue; }
            Collections.swap(list, i%list.size(), j%list.size());
            cnt++;
        }
        if(cnt == 0) { return; }
        final int tmpHeader = this.index;
        this.createQueue(this.size());
        this.set(list);
        this.moveHeader(tmpHeader);
    }

    /**
     * Object queue data shuffling
     * @return true or false
     */
    public boolean shuffle() {
        if(this.isEmpty()) { return false; }
        int tmpHeader = this.index;
        List<ET> list = this.getList(MODE.FIFO);
        Collections.shuffle(list);
        this.createQueue(this.size());
        this.set(list);
        this.moveHeader(tmpHeader);
        return true;
    }

    /**
     * debug method
     * - show field, method, interface and more of this Object
     * @return String
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     *
     * @return hashed code number
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     *
     * @param object is RingBuffer etc.
     * @return same object judge
     */
    @Override
    public boolean equals(Object object) {
        return EqualsBuilder.reflectionEquals(this, object);
    }

    /**
     *
     * @param e is Object
     * @return true or false
     */
    public boolean contains(ET e) {
        return this.indexOf(e) >= 0;
    }

    /**
     *
     * @param e is Object
     * @return index number
     */
    public int indexOf(ET e) {
        if(e == null) { return -1; }
        final List<ET> list = this.getList(MODE.FIFO);
        for(int i=0; i < list.size(); i++) {
            if(e.equals(list.get(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     *
     * @return Object or null pointer
     */
    public ET peek() {
        return this.get(0, MODE.LIFO);
    }

    /**
     *
     * @return Object or null pointer
     */
    public ET pop() {
        ET object;
        index = (--index < 0)? queue.length +index: index;
        object = this.queue[index];
        if(object == null) { return null; }
        this.queue[index] = null;
        fill = false;
        return object;
    }

    /**
     *
     * @param size is pop size number
     * @return List of Object or null pointer
     */
    public List<ET> pop(int size) {
        List<ET> list = new ArrayList<>();
        for(int i=0; i < size; i++) {
            list.add(this.pop());
        }
        return (list);
    }

    /**
     *
     * @return List
     */
    public List<ET> popAll() {
        return this.pop(insertedSize());
    }

    /**
     * insert Object in queue
     * @param et is Object
     */
    protected void enqueue(ET et) {
        this.queue[index++] = et;
        index %= this.queue.length;
        if(!fill && this.queue[index] != null) {
            fill = true;
        }
    }

    /**
     *
     * @return Object or null pointer
     */
    protected ET dequeue() {
        ET object;
        if(fill) {
            object = this.queue[this.index];
            this.queue[this.index] = null;
            fill = false;
        } else {
            int tmpIndex = this.index;
            while(this.queue[tmpIndex] == null) {
                tmpIndex++;
                tmpIndex %= this.queue.length;
                if(tmpIndex == this.index) {
                    return null;
                }
            }
            object = this.queue[tmpIndex];
            this.queue[tmpIndex] = null;
        }
        return object;
    }

    /**
     *
     * @param n is queue index
     * @param mode is queue mode
     * @return Object or null pointer
     */
    private ET getObject(int n, MODE mode) {
        int p = (fill)? index +n: n;
        final int cnt = p / queue.length;
        p = (p > queue.length -1)? p -(queue.length)*cnt: p;
        if(mode == MODE.LIFO) {
            int tmp = (n > queue.length -1)? n%(queue.length): n;
            p += (queue.length -1 -tmp*2);
            p %= (queue.length);
            p = (p < 0)? queue.length +p: p;
        }
        return (queue[p]);
    }

}
