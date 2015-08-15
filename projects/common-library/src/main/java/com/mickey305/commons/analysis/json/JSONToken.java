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
package com.mickey305.commons.analysis.json;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONToken implements Cloneable {

    private Value value;
    private TYPE type;
    private int indexNumber;

    /**
     * information: JSONKey, JSONValue or JSONTokenSymbol
     */
    public class Value implements Cloneable {
        private String s;

        public Value(String s) {
            setS(s);
        }

        @Override
        public Value clone() {
            Value scope = null;
            try {
                scope = (Value) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return scope;
        }

        private String getS() {
            return s;
        }

        public void setS(String s) {
            this.s = s;
        }

        public int size() {
            return getS().length();
        }

        public int length() {
            return this.size();
        }

        public String to_s() {
            return getS();
        }

        public int to_i() throws NumberFormatException {
            return Integer.parseInt(getS());
        }

        public float to_f() throws NumberFormatException {
            return Float.parseFloat(getS());
        }

        public JSONObject to_jsonObject() throws JSONException {
            return new JSONObject(getS());
        }

        public JSONArray to_jsonArray() throws JSONException {
            return new JSONArray(getS());
        }
    }

    /**
     *
     * @param type
     * @param ch
     */
    public JSONToken(TYPE type, char ch) {
        this(type, String.valueOf(ch));
    }

    /**
     *
     * @param type
     * @param num
     */
    public JSONToken(TYPE type, int num) {
        this(type, String.valueOf(num));
    }

    /**
     *
     * @param type
     * @param num
     */
    public JSONToken(TYPE type, float num) {
        this(type, String.valueOf(num));
    }

    /**
     *
     * @param type
     * @param str
     */
    public JSONToken(TYPE type, String str) {
        setType(type);
        setValue(str);
    }

    /**
     * enum - JSON TYPE
     */
    public enum TYPE {
        START_ARRAY         ( 0, true),     // [
        END_ARRAY           ( 1, true),     // ]
        START_OBJECT        ( 2, true),     // {
        END_OBJECT          ( 3, true),     // }
        FIELD_NAME          ( 4, false),    // json key
        VALUE_STRING        ( 5, false),    // json value
        VALUE_NULL          ( 6, false),    // json value
        VALUE_NUMBER_F      ( 7, false),    // json value
        VALUE_NUMBER_I      ( 8, false),    // json value
        VALUE_TRUE          ( 9, false),    // json value
        VALUE_FALSE         (10, false),    // json value
        VALUE_JSON_OBJECT   (11, false),    // json value
        VALUE_JSON_ARRAY    (12, false);    // json value

        TYPE(int number, boolean symbol) {
            this.number = number;
            this.symbol = symbol;
        }

        public int getNumber() { return number; }
        public boolean isSymbol() { return symbol; }

        private final int number;
        private final boolean symbol;
    }

    /**
     *
     * @return
     */
    @Override
    public JSONToken clone() {
        JSONToken scope = null;
        try {
            scope = (JSONToken) super.clone();
            scope.value = this.value.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return scope;
    }

    /**
     *
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {
        return EqualsBuilder.reflectionEquals(this, object);
    }

    /**
     *
     * @return
     */
    public Value getValue() {
        return value;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     *
     * @param str
     */
    public void setValue(String str) {
        this.value = new Value(str);
    }

    /**
     *
     * @return
     */
    public TYPE getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(TYPE type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public int getIndexNumber() {
        return indexNumber;
    }

    /**
     *
     * @param indexNumber
     */
    public void setIndexNumber(int indexNumber) {
        this.indexNumber = indexNumber;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
