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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JSONTokenUtil {

    public static final char CHAR_DOT   = 46; // .
    public static final char CHAR_PLUS  = 43; // +
    public static final char CHAR_MINUS = 45; // -

    public static final char CHAR_COMMA = 44; // ,
    public static final char CHAR_COLON = 58; // :

    public static final char CHAR_START_ARRAY   = 91;  // [
    public static final char CHAR_END_ARRAY     = 93;  // ]
    public static final char CHAR_START_OBJECT  = 123; // {
    public static final char CHAR_END_OBJECT    = 125; // }
    public static final char CHAR_DOUBLE_QUOTES = 34;  // "

    public static final String STRING_NULL  = "null";
    public static final String STRING_TRUE  = "true";
    public static final String STRING_FALSE = "false";

    public static final Character[] figurePartsArray = {
            CHAR_DOT, CHAR_PLUS, CHAR_MINUS
    };
    public static final Character[] operatorArray = {
            CHAR_PLUS, CHAR_MINUS
    };

    public static final List<Character> figurePartsList = Arrays.asList(figurePartsArray);
    public static final List<Character> operatorList = Arrays.asList(operatorArray);

    /**
     *
     * @param ch
     * @return
     */
    public static boolean isFigureParts(char ch) {
        return figurePartsList.contains(ch);
    }

    /**
     *
     * @param ch
     * @return
     */
    public static boolean isOperator(char ch) {
        return operatorList.contains(ch);
    }

    /**
     *
     * @param token
     * @return
     */
    public static boolean isTypeValue(JSONToken token) {
        if(
                token.getType() == JSONToken.TYPE.VALUE_STRING   ||
                token.getType() == JSONToken.TYPE.VALUE_NUMBER_I ||
                token.getType() == JSONToken.TYPE.VALUE_NUMBER_F ||
                token.getType() == JSONToken.TYPE.VALUE_TRUE     ||
                token.getType() == JSONToken.TYPE.VALUE_FALSE    ||
                token.getType() == JSONToken.TYPE.VALUE_NULL )
        {
            return true;
        }
        return false;
    }
    public static boolean isTypeEnbeddedValue(JSONToken token) {
        if(
                token.getType() == JSONToken.TYPE.VALUE_JSON_ARRAY ||
                token.getType() == JSONToken.TYPE.VALUE_JSON_OBJECT )
        {
            return true;
        }
        return false;
    }

    /**
     *
     * @param seed
     * @throws Exception
     */
    protected static void checkStringInfo(String seed) throws Exception {
        ArrayList<Boolean> isExceptionList = new ArrayList<>();
        try {
            new JSONObject(seed);
            isExceptionList.add(false);
        } catch (JSONException e) {
            isExceptionList.add(true);
        }
        try {
            new JSONArray(seed);
            isExceptionList.add(false);
        } catch (JSONException e) {
            isExceptionList.add(true);
        }
        if(!isExceptionList.contains(false)) {
            throw new Exception("Unexpected String:\n"
                    + "\tplease insert the sentence to be able to transform from String to JSONObject or JSONArray");
        }
    }
}
