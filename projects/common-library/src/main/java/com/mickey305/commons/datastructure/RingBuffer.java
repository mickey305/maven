package com.mickey305.commons.datastructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RingBuffer<ET> {
    public static final String TAG = RingBuffer.class.getName();

    /** Object Array */
    private ET[] queue;

    /** Array Header */
    private int index;

    /** Object filled */
    private boolean fill;

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
        this.create(n);
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
        this.create(size);
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
        for (ET tmp: list) {
            this.enqueue(tmp);
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
     * @return Object
     */
    public ET remove() {
        return this.dequeue();
    }

    /**
     *
     * @param size is delete size number
     * @return List
     */
    public List<ET> remove(int size) {
        List<ET> list = new ArrayList<>();
        for(int i=0; i < size; i++) {
            list.add(remove());
        }
        return list;
    }

    /**
     *
     * @return List
     */
    public List<ET> removeAll() {
        return this.remove(insertedSize());
    }

    /**
     *
     * @return Object
     */
    public ET poll() {
        return this.remove();
    }

    /**
     *
     * @param size is delete size number
     * @return List
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
     * @return Object
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
        for(ET e: queue) {
            if(e != null) cnt++;
        }
        return (cnt);
    }

    /**
     * initialize this field
     * @param n is queue size
     */
    @SuppressWarnings("unchecked")
    public void create(int n) {
        this.queue = (ET[]) new Object[n];
        this.index = 0;
        this.fill = false;
    }

    /**
     *
     * @param size is defined size
     */
    public void create(SIZE size) {
        this.create(size.num);
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
     *
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
        this.create(n);
        this.set(tmpList);
    }

    /**
     *
     * @param size is defined size
     */
    public void resize(SIZE size) {
        this.resize(size.num);
    }

    /**
     *
     * @return Object
     */
    public ET pop() {
        ET object;
        index = (--index < 0)? queue.length +index: index;
        object = this.queue[index];
        if(object == null) {
            index++;
            return null;
        }
        this.queue[index] = null;
        fill = false;
        return object;
    }

    /**
     *
     * @param size is pop size number
     * @return List
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
     *
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
     * @return Object
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
     * @return Object
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
