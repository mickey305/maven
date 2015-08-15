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

import com.mickey305.commons.analysis.string.ScannerLine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class JSONTokenizer implements Iterator, Cloneable {

    public static final String TAG = JSONTokenizer.class.getName();

    private ScannerLine scannerLine;
    private JSONToken prevToken;
    private Stack<Boolean> nowInArrayList;

    public JSONTokenizer(String jsonString) {
        try {
            this.init(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return
     */
    @Override
    public JSONTokenizer clone() {
        JSONTokenizer scope = null;
        try {
            scope = (JSONTokenizer) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return scope;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean hasNext() {
        this.cutCode();
        return scannerLine.hasNext();
    }

    /**
     *
     * @return
     */
    @Override
    public JSONToken next() {
        JSONToken token = null;
        int index = scannerLine.getIndex();
        char ch = scannerLine.peek();

        if(Character.isDigit(ch) || JSONTokenUtil.isOperator(ch)) {
            // Figure
            String param = new String();
            while (
                    scannerLine.hasNext() &&
                    ( Character.isDigit(ch) || JSONTokenUtil.isFigureParts(ch)) )
            {
                param += scannerLine.next();
                ch = scannerLine.peek();
            }
            try {
                token = new JSONToken(JSONToken.TYPE.VALUE_NUMBER_I, Integer.parseInt(param));
            } catch (NumberFormatException e) {
                token = new JSONToken(JSONToken.TYPE.VALUE_NUMBER_F, Float.parseFloat(param));
            }
        } else if(Character.isLetter(ch)) {
            // Letter: JSONNullValue or JSONBooleanValue
            StringBuilder sb = new StringBuilder();
            while (scannerLine.hasNext() && Character.isLetter(ch)) {
                ch = scannerLine.next();
                sb.append(ch);
                ch = scannerLine.peek();
            }
            String str = sb.toString();
            if(str.equals(JSONTokenUtil.STRING_NULL)) {
                token = new JSONToken(JSONToken.TYPE.VALUE_NULL, str);
            } else if(str.equals(JSONTokenUtil.STRING_TRUE)) {
                token = new JSONToken(JSONToken.TYPE.VALUE_TRUE, str);
            } else if(str.equals(JSONTokenUtil.STRING_FALSE)) {
                token = new JSONToken(JSONToken.TYPE.VALUE_FALSE, str);
            } else {
                // json syntax error
            }
        } else {
            // Other: Symbol, JSONField or JSONStringValue
            switch (ch) {
                case JSONTokenUtil.CHAR_START_OBJECT:
                    nowInArrayList.push(false);
                    token = new JSONToken(JSONToken.TYPE.START_OBJECT, scannerLine.next());
                    break;
                case JSONTokenUtil.CHAR_START_ARRAY:
                    nowInArrayList.push(true);
                    token = new JSONToken(JSONToken.TYPE.START_ARRAY, scannerLine.next());
                    break;
                case JSONTokenUtil.CHAR_END_OBJECT:
                    nowInArrayList.pop();
                    token = new JSONToken(JSONToken.TYPE.END_OBJECT, scannerLine.next());
                    break;
                case JSONTokenUtil.CHAR_END_ARRAY:
                    nowInArrayList.pop();
                    token = new JSONToken(JSONToken.TYPE.END_ARRAY, scannerLine.next());
                    break;
                case JSONTokenUtil.CHAR_DOUBLE_QUOTES:
                    // JSONField or JSONStringValue
                    JSONToken.TYPE type = null;
                    if(!nowInArrayList.peek()) {
                        // Index: in JSONObject
                        if(
                                prevToken.getType() == JSONToken.TYPE.START_OBJECT   ||
                                prevToken.getType() == JSONToken.TYPE.END_OBJECT     ||
                                prevToken.getType() == JSONToken.TYPE.END_ARRAY      ||
                                JSONTokenUtil.isTypeValue(prevToken) )
                        {
                            type = JSONToken.TYPE.FIELD_NAME;
                        } else if(prevToken.getType() == JSONToken.TYPE.FIELD_NAME) {
                            type = JSONToken.TYPE.VALUE_STRING;
                        } else {
                            // json syntax error
                        }
                    } else {
                        // Index in JSONArray
                        type = JSONToken.TYPE.VALUE_STRING;
                    }
                    String param = new String();
                    scannerLine.next();
                    ch = scannerLine.peek();
                    while (scannerLine.hasNext() && ch != JSONTokenUtil.CHAR_DOUBLE_QUOTES) {
                        param += scannerLine.next();
                        ch = scannerLine.peek();
                    }
                    scannerLine.next();
                    token = new JSONToken(type, param);
                    break;
                default: // json syntax error
                    break;
            }
        }
        token.setIndexNumber(index);

        // copy JSONToken
        prevToken = token;

        return (token);
    }

    /**
     *
     * @param jsonString
     * @throws Exception
     */
    public void init(String jsonString) throws Exception {
        JSONTokenUtil.checkStringInfo(jsonString);
        scannerLine = new ScannerLine(jsonString);
        prevToken = null;
        nowInArrayList = new Stack<>();
    }

    /**
     *
     * @return
     * @throws NullPointerException
     */
    public JSONToken previous() throws NullPointerException {
        if(this.prevToken == null) {
            throw new NullPointerException("Previous Token Null: please do the next position");
        }
        return this.prevToken;
    }

    /**
     *
     */
    protected void cutCode() {
        ExtensionList<Boolean> hasCodeList = new ExtensionList<>();
        do {
            hasCodeList.clear();
            hasCodeList.add(scannerLine.cutWhitespace());
            hasCodeList.add(
                    scannerLine.cutCharacter(JSONTokenUtil.CHAR_COLON, JSONTokenUtil.CHAR_COMMA)
            );
        } while (hasCodeList.contains(true));
    }

    protected class ExtensionList<E> extends ArrayList<E> {

        /**
         *
         * @param eList
         */
        public void add(E... eList) {
            for(E e: eList) super.add(e);
        }

    }

}
