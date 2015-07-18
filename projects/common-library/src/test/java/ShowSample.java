import com.mickey305.commons.datastructure.RingBuffer;

public class ShowSample {
    public static final String TAG = ShowSample.class.getName();

    public ShowSample() {}

    public void debugRingBuffer() {
        final int LOOP_SIZE = 14;
        RingBuffer<Integer> buf = new RingBuffer<Integer>(6);

        Log.d(TAG, "buf size is      "+buf.size());
        Log.d(TAG, "InsertedSize is  " + buf.insertedLength());
        Log.d(TAG, "filled status is " + buf.isFill());
        Log.d(TAG, "header point is  "+buf.getIndex());
        Log.d(TAG, "------- FIFO ------ LIFO -------");
        for(int i=0; i < LOOP_SIZE; i++) {
            Log.d(TAG, i+"\t\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.FIFO)
                    +"\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.LIFO));
        }
        Log.d(TAG, "");

        buf.set(1, 2, 3);
        Log.d(TAG, "Insert ->        1, 2, 3");
        Log.d(TAG, "buf size is      "+buf.size());
        Log.d(TAG, "InsertedSize is  "+buf.insertedLength());
        Log.d(TAG, "filled status is " + buf.isFill());
        Log.d(TAG, "header point is  " + buf.getIndex());
        Log.d(TAG, "------- FIFO ------ LIFO -------");
        for(int i=0; i < LOOP_SIZE; i++) {
            Log.d(TAG, i+"\t\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.FIFO)
                    +"\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.LIFO));
        }
        Log.d(TAG, "");

        buf.push(4);
        buf.set(5, 6, 7);
        Log.d(TAG, "Insert ->        4, 5, 6, 7");
        Log.d(TAG, "buf size is      "+buf.size());
        Log.d(TAG, "InsertedSize is  "+buf.insertedLength());
        Log.d(TAG, "filled status is " + buf.isFill());
        Log.d(TAG, "header point is  " + buf.getIndex());
        Log.d(TAG, "------- FIFO ------ LIFO -------");
        for(int i=0; i < LOOP_SIZE; i++) {
            Log.d(TAG, i+"\t\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.FIFO)
                    +"\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.LIFO));
        }
        Log.d(TAG, "");

        Log.d(TAG, "delete ->        "+buf.remove()+", "+buf.remove()+", "+buf.remove()+", "+buf.remove());
        Log.d(TAG, "buf size is      "+buf.size());
        Log.d(TAG, "InsertedSize is  "+buf.insertedLength());
        Log.d(TAG, "filled status is " + buf.isFill());
        Log.d(TAG, "header point is  "+buf.getIndex());
        Log.d(TAG, "------- FIFO ------ LIFO -------");
        for(int i=0; i < LOOP_SIZE; i++) {
            Log.d(TAG, i+"\t\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.FIFO)
                    +"\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.LIFO));
        }
        Log.d(TAG, "");

        buf.set(10, 20, 30);
        Log.d(TAG, "Insert ->        10, 20, 30");
        Log.d(TAG, "buf size is      "+buf.size());
        Log.d(TAG, "InsertedSize is  "+buf.insertedLength());
        Log.d(TAG, "filled status is "+buf.isFill());
        Log.d(TAG, "header point is  " + buf.getIndex());
        Log.d(TAG, "------- FIFO ------ LIFO -------");
        for(int i=0; i < LOOP_SIZE; i++) {
            Log.d(TAG, i+"\t\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.FIFO)
                    +"\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.LIFO));
        }
        Log.d(TAG, "");

        Log.d(TAG, "delete ->        "+buf.remove(2).toString());
        Log.d(TAG, "buf size is      "+buf.size());
        Log.d(TAG, "InsertedSize is  "+buf.insertedLength());
        Log.d(TAG, "filled status is "+ buf.isFill());
        Log.d(TAG, "header point is  "+buf.getIndex());
        Log.d(TAG, "------- FIFO ------ LIFO -------");
        for(int i=0; i < LOOP_SIZE; i++) {
            Log.d(TAG, i+"\t\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.FIFO)
                    +"\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.LIFO));
        }
        Log.d(TAG, "");

        buf.set(11, 21, 31, 41, 51, 61, 71);
        Log.d(TAG, "Insert ->        11, 21, 31, 41, 51, 61, 71");
        Log.d(TAG, "buf size is      "+buf.size());
        Log.d(TAG, "InsertedSize is  "+buf.insertedLength());
        Log.d(TAG, "filled status is "+buf.isFill());
        Log.d(TAG, "header point is  " + buf.getIndex());
        Log.d(TAG, "------- FIFO ------ LIFO -------");
        for(int i=0; i < LOOP_SIZE; i++) {
            Log.d(TAG, i+"\t\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.FIFO)
                    +"\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.LIFO));
        }
        Log.d(TAG, "");

        buf.resize(4);
        Log.d(TAG, "buf size is      "+buf.size());
        Log.d(TAG, "InsertedSize is  "+buf.insertedLength());
        Log.d(TAG, "filled status is "+buf.isFill());
        Log.d(TAG, "header point is  "+buf.getIndex());
        Log.d(TAG, "------- FIFO ------ LIFO -------");
        for(int i=0; i < LOOP_SIZE; i++) {
            Log.d(TAG, i+"\t\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.FIFO)
                    +"\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.LIFO));
        }
        Log.d(TAG, "");

        Log.d(TAG, "pop ->           " + buf.pop(3).toString());
        Log.d(TAG, "buf size is      "+buf.size());
        Log.d(TAG, "InsertedSize is  "+buf.insertedLength());
        Log.d(TAG, "filled status is "+ buf.isFill());
        Log.d(TAG, "header point is  "+buf.getIndex());
        Log.d(TAG, "------- FIFO ------ LIFO -------");
        for(int i=0; i < LOOP_SIZE; i++) {
            Log.d(TAG, i+"\t\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.FIFO)
                    +"\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.LIFO));
        }
        Log.d(TAG, "");

        Log.d(TAG, "pop ->           " + buf.pop(5).toString());
        Log.d(TAG, "buf size is      "+buf.size());
        Log.d(TAG, "InsertedSize is  "+buf.insertedLength());
        Log.d(TAG, "filled status is "+ buf.isFill());
        Log.d(TAG, "header point is  "+buf.getIndex());
        Log.d(TAG, "");

    }

}

class Log {
    public static void d(String TAG, String msg) {
        System.out.println(TAG + "\t" + msg);
    }
}
