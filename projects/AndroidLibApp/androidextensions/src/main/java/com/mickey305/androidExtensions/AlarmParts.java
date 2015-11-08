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

public interface AlarmParts {
    String DEFAULT_TIME_ZONE = "Asia/Tokyo";

    /**
     * 端末にスケジュールを設定する
     *
     * @param clz 起動するクラス
     * @param id リクエストコード（ユニークなID）
     *           複数の同一クラスを登録する場合の識別IDである。IDが重なる場合は、先行予約のスケジュールが上書きされる。
     * @param trigger 起動時刻（ミリ秒）
     * @param interval 起動間隔（ミリ秒）
     * @param <T> 総称型（起動クラス）Activity・Service・BroadcastReceiverまたは、これらのサブクラスの何れか
     * @throws LaunchClassTypeException
     */
    <T> void set(Class<T> clz, int id, long trigger, long interval) throws LaunchClassTypeException;

    /**
     * 端末からスケジュールを取り消す
     *
     * @param clz 取り消すクラス
     * @param id リクエストコード（ユニークなID）
     *           複数の同一クラスを登録する場合の識別IDである。IDが重なる場合は、先行予約のスケジュールが上書きされる。
     * @param <T> 総称型（取消クラス）Activity・Service・BroadcastReceiverまたは、これらのサブクラスの何れか
     * @throws LaunchClassTypeException
     */
    <T> void cancel(Class<T> clz, int id) throws LaunchClassTypeException;

}
