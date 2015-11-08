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
