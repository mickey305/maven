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
package com.mickey305.androidExtensions;

import android.content.Context;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * MultipleSchedulerクラス
 *
 * {@link Scheduler}クラスの拡張
 */
public class MultipleScheduler extends Scheduler {
    public static final String TAG = "MultipleScheduler";

    private Callback mCallback;

    /**
     * コールバック
     */
    public interface Callback {
        /**
         * 登録処理が失敗した場合に実行する
         * @param failedId 失敗したID
         * @param e LaunchClassTypeException
         */
        void onFailToRegister(int failedId, LaunchClassTypeException e);

        /**
         * 取消処理が失敗した場合に実行する
         * @param failedId 失敗したID
         * @param e LaunchClassTypeException
         */
        void onFailToCancel(int failedId, LaunchClassTypeException e);
    }

    public MultipleScheduler(Context context, TimeZone timeZone) {
        super(context, timeZone);
    }

    public MultipleScheduler(Context context) {
        super(context);
    }

    protected Calendar createCalendar(int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        return cal;
    }

    protected Calendar createCalendar(int day, int hour, int minute, int second) {
        Calendar cal = this.createCalendar(hour, minute, second);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal;
    }

    /**
     * カレンダーを生成する
     * @param month 月（1~12）
     * @param day 日（1~28,29,30,31）
     * @param hour 時（0~23）
     * @param minute 分（0~59）
     * @param second 秒（0~59）
     * @return
     */
    protected Calendar createCalendar(int month, int day, int hour, int minute, int second) {
        Calendar cal = this.createCalendar(day, hour, minute, second);
        cal.set(Calendar.MONTH, month - 1);
        return cal;
    }

    protected Calendar createCalendar(int year, int month, int day, int hour, int minute, int second) {
        Calendar cal = this.createCalendar(month, day, hour, minute, second);
        cal.set(Calendar.YEAR, year);
        return cal;
    }

    /**
     * スケジュールを設定する
     * @param clz 起動するクラス
     * @param id リクエストコード（ユニークなID）
     * @param calendar 起動する時刻のカレンダー
     * @param interval 起動間隔（ミリ秒）
     * @param <T> 総称型（起動クラス）
     * @throws LaunchClassTypeException
     */
    public <T> void setByCalendar(
            Class<T> clz, int id, Calendar calendar, long interval) throws LaunchClassTypeException {
        // 目標日時
        calendar.setTimeZone(super.getTimeZone());
        final long targetTimeInMillis = calendar.getTimeInMillis();
        super.set(clz, id, targetTimeInMillis, interval);
    }

    public <T> void setByCalendar(
            Class<T> clz, int id, Calendar calendar) throws LaunchClassTypeException {
        this.setByCalendar(clz, id, calendar, 0);
    }

    /**
     * スケジュールを一括で設定する
     * @param clz 起動するクラス
     * @param idArray リクエストコード（ユニークなID）の配列
     * @param calendarArray 起動する時刻のカレンダーの配列
     * @param <T> 総称型（起動クラス）
     * @throws MatchSizeException
     */
    public <T> void setByCalendar(
            Class<T> clz, Integer[] idArray, Calendar[] calendarArray) throws MatchSizeException {
        // 配列サイズをチェックする
        if(idArray.length != calendarArray.length) {
            throw new MatchSizeException(
                    "different length: idArray - "+idArray.length+" NOT EQUAL calendarArray - "+calendarArray.length);
        }

        int index = 0;
        for(Calendar cal: calendarArray) {
            try {
                this.setByCalendar(clz, idArray[index], cal);
            } catch (LaunchClassTypeException e) {
                e.printStackTrace();
                if(mCallback != null) { mCallback.onFailToRegister(idArray[index], e); }
                return;
            }
            ++index;
        }
    }

    public <T> void setByCalendar(
            Class<T> clz, List<Integer> idList, List<Calendar> calendarList) throws MatchSizeException {
        Integer[] idArray = (Integer[]) idList.toArray();
        Calendar[] targetCalendarArray = (Calendar[]) calendarList.toArray();
        this.setByCalendar(clz, idArray, targetCalendarArray);
    }

    /**
     * スケジュールを設定する
     * @param clz 起動するクラス
     * @param id リクエストコード（ユニークなID）
     * @param hour 起動時刻（時）
     * @param minute 起動時刻（分）
     * @param second 起動時刻（秒）
     * @param interval 起動間隔（ミリ秒）
     * @param <T> 総称型（起動クラス）
     * @throws LaunchClassTypeException
     */
    public <T> void setByTime(
            Class<T> clz, int id, int hour, int minute, int second, long interval) throws LaunchClassTypeException {
        // 目標時刻のカレンダー
        Calendar calTarget = this.createCalendar(hour, minute, second);

        this.setByCalendar(clz, id, calTarget, interval);
    }

    public <T> void setByTime(Class<T> clz, int id, int hour, int minute, int second) throws LaunchClassTypeException {
        this.setByTime(clz, id, hour, minute, second, 0);
    }

    /**
     * スケジュールを一括で取り消す
     * @param clz 取り消すクラス
     * @param idArray リクエストコード（ユニークなID）の配列
     * @param <T> 総称型（取消クラス）
     */
    public <T> void cancel(Class<T> clz, Integer[] idArray) {
        for(int id:idArray) {
            try {
                super.cancel(clz, id);
            } catch (LaunchClassTypeException e) {
                e.printStackTrace();
                if(mCallback != null) { mCallback.onFailToCancel(id, e); }
                return;
            }
        }
    }

    public <T> void cancel(Class<T> clz, List<Integer> idList) {
        Integer[] idArray = (Integer[]) idList.toArray();
        this.cancel(clz, idArray);
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    /**
     * MatchSizeExceptionクラス
     */
    public class MatchSizeException extends Exception {
        public static final String TAG = "MatchSizeException";

        public MatchSizeException(String message) { super(message); }
    }
}
