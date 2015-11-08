package com.mickey305.androidExtensions;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.TimeZone;

/**
 * Schedulerクラス
 *
 * Android端末に起動するアクション（Activity・Service・BroadcastReceiverの何れかもしくは、
 * これらのサブクラス）を登録・削除する。
 */
public class Scheduler implements AlarmParts {
    public static final String TAG = "Scheduler";

    private Context mContext;
    private TimeZone mTimeZone;

    /**
     * コンストラクタ
     * @param context コンテキスト（e.g.アプリケーションコンテキスト）
     * @param timeZone タイムゾーン
     */
    public Scheduler(Context context, TimeZone timeZone) {
        mContext = context;
        mTimeZone = timeZone;
    }

    /**
     * コンストラクタ
     * @param context コンテキスト（e.g.アプリケーションコンテキスト）
     */
    public Scheduler(Context context) {
        this(context, TimeZone.getTimeZone(DEFAULT_TIME_ZONE));
    }

    /**
     * PendingIntentの種類を選択する
     * @param clz クラスの型
     * @param id リクエストコード（ユニークなID）
     * @param intent インテント
     * @param flags フラグ
     * @param <T> 総称型（取消クラス）Activity・Service・BroadcastReceiverまたは、これらのサブクラスの何れか
     * @return 総称型に対応するアクションまたはNull
     * @throws NullPointerException
     */
    protected <T> PendingIntent selectAction(Class<T> clz, int id, Intent intent, int flags) {
        PendingIntent action;
        if(Activity.class.isAssignableFrom(clz)) {
            action = PendingIntent.getActivity(mContext, id, intent, flags);
        } else if(Service.class.isAssignableFrom(clz)) {
            action = PendingIntent.getService(mContext, id, intent, flags);
        } else if(BroadcastReceiver.class.isAssignableFrom(clz)) {
            action = PendingIntent.getBroadcast(mContext, id, intent, flags);
        } else {
            // 総称型のClass引数が想定されていない型であった場合
            action = null;
        }
        return action;
    }

    /**
     * アクションを設定する
     * @param action 起動する予定のインテント
     * @param trigger 起動時刻（ミリ秒）
     * @param interval
     */
    protected void set(PendingIntent action, long trigger, long interval) {
        final AlarmManager am = (AlarmManager) mContext.getSystemService(mContext.ALARM_SERVICE);
        if(interval != 0) {
            am.setRepeating(AlarmManager.RTC, trigger, interval, action);
        } else {
            am.set(AlarmManager.RTC_WAKEUP, trigger, action);
        }
    }

    /**
     * アクションを設定する
     * @param action 起動する予定のインテント
     * @param trigger 起動時刻（ミリ秒）
     */
    protected void set(PendingIntent action, long trigger) {
        this.set(action, trigger, 0);
    }

    /**
     * スケジュールを設定する
     * @param clz 起動するクラス
     * @param id リクエストコード（ユニークなID）
     *           複数の同一クラスを登録する場合の識別IDである。IDが重なる場合は、先行予約のスケジュールが上書きされる。
     * @param trigger 起動時刻（ミリ秒）
     * @param interval 起動間隔（ミリ秒）
     * @param <T> 総称型（取消クラス）Activity・Service・BroadcastReceiverまたは、これらのサブクラスの何れか
     * @throws LaunchClassTypeException
     */
    @Override
    public <T> void set(Class<T> clz, int id, long trigger, long interval) throws LaunchClassTypeException {
        final Intent intent = new Intent(mContext, clz);
        final int flag = PendingIntent.FLAG_CANCEL_CURRENT;
        final PendingIntent action = this.selectAction(clz, id, intent, flag);
        if(action == null) {
            throw new LaunchClassTypeException(LaunchClassTypeException.ERROR_CODE.SET, "Type of a given class, is not supported. Please specify one of the following classes: [Activity, Service or BroadcastReceiver] or subclasses of those classes.");
        }

        this.set(action, trigger, interval);
    }

    /**
     * スケジュールを設定する
     * @param clz 起動するクラス
     * @param id リクエストコード（ユニークなID）
     * @param trigger 起動時刻（ミリ秒）
     * @param <T> 総称型（取消クラス）Activity・Service・BroadcastReceiverまたは、これらのサブクラスの何れか
     * @throws LaunchClassTypeException
     */
    public <T> void set(Class<T> clz, int id, long trigger) throws LaunchClassTypeException {
        this.set(clz, id, trigger, 0);
    }

    /**
     * スケジュールを取り消す
     * @param clz 取り消すクラス
     * @param id リクエストコード（ユニークなID）
     *           複数の同一クラスを登録する場合の識別IDである。IDが重なる場合は、先行予約のスケジュールが上書きされる。
     * @param <T> 総称型（取消クラス）Activity・Service・BroadcastReceiverまたは、これらのサブクラスの何れか
     * @throws LaunchClassTypeException
     */
    @Override
    public <T> void cancel(Class<T> clz, int id) throws LaunchClassTypeException {
        final Intent intent = new Intent(mContext, clz);
        final int flag = PendingIntent.FLAG_UPDATE_CURRENT;
        final PendingIntent action = this.selectAction(clz, id, intent, flag);
        if(action == null) {
            throw new LaunchClassTypeException(LaunchClassTypeException.ERROR_CODE.CANCEL, "Type of a given class, is not supported. Please specify one of the following classes: [Activity, Service or BroadcastReceiver] or subclasses of those classes.");
        }
        final AlarmManager am = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        am.cancel(action);
    }

    public Context getContext() {
        return mContext;
    }

    public TimeZone getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        mTimeZone = timeZone;
    }
}
