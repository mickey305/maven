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
