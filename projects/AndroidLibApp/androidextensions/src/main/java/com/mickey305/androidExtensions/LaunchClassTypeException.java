package com.mickey305.androidExtensions;

/**
 * LaunchClassTypeExceptionクラス
 *
 * 特定の型以外のクラスが処理されるときに例外を発生させる.
 */
public class LaunchClassTypeException extends Exception {
    public static final String TAG = "LaunchClassTypeException";

    private ERROR_CODE mErrorCode;

    /**
     * LaunchClassTypeExceptionのエラーコード（例外識別コード）
     */
    public enum ERROR_CODE{
        SET     (0x00000001),
        CANCEL  (0x00000002);

        private int mValue;

        ERROR_CODE(int value) {
            mValue = value;
        }

        public int getValue() {
            return mValue;
        }
    }


    /**
     * コンストラクタ
     * @param errorCode エラーコード（例外識別コード）
     * @param message エラーメッセージ（Logcatなどに表示するメッセージ）
     */
    public LaunchClassTypeException(ERROR_CODE errorCode, String message) {
        super(message);
        mErrorCode = errorCode;
    }

    public ERROR_CODE getErrorCode() {
        return mErrorCode;
    }

    public void setErrorCode(ERROR_CODE errorCode) {
        mErrorCode = errorCode;
    }
}
