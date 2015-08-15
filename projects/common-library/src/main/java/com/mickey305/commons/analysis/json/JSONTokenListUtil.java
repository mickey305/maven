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

import java.util.LinkedList;
import java.util.Stack;

public class JSONTokenListUtil {

    /**
     *
     * @param tokenList
     * @param initScanPosition
     * @return
     * @throws Exception
     */
    public static int getPairSymbolPoint(
            final LinkedList<JSONToken> tokenList,
            final int initScanPosition) throws Exception {
        if(!tokenList.get(initScanPosition).getType().isSymbol()) {
            throw new Exception("Expected Token: please check JSONToken and signature");
        }
        Stack<JSONToken> symbolList = new Stack<>();
        JSONToken pairToken = null;
        int output = initScanPosition;
        for(; output < tokenList.size(); output++) {
            JSONToken nowToken = tokenList.get(output);
            if(output == initScanPosition) {
                // first loop
                if (nowToken.getType() == JSONToken.TYPE.START_ARRAY) {
                    pairToken = new JSONToken(JSONToken.TYPE.END_ARRAY, JSONTokenUtil.CHAR_END_ARRAY);
                    symbolList.push(nowToken);
                } else if(nowToken.getType() == JSONToken.TYPE.START_OBJECT) {
                    pairToken = new JSONToken(JSONToken.TYPE.END_OBJECT, JSONTokenUtil.CHAR_END_OBJECT);
                    symbolList.push(nowToken);
                } else {
                    // error
                }
            } else {
                // not first loop
                if(pairToken.getType() == JSONToken.TYPE.END_ARRAY) {
                    if (nowToken.getType() == JSONToken.TYPE.START_ARRAY) {
                        symbolList.push(nowToken);
                    } else if (nowToken.getType() == JSONToken.TYPE.END_ARRAY) {
                        symbolList.pop();
                        if (symbolList.isEmpty()) { break; }
                    }
                } else if(pairToken.getType() == JSONToken.TYPE.END_OBJECT) {
                    if (nowToken.getType() == JSONToken.TYPE.START_OBJECT) {
                        symbolList.push(nowToken);
                    } else if (nowToken.getType() == JSONToken.TYPE.END_OBJECT) {
                        symbolList.pop();
                        if (symbolList.isEmpty()) { break; }
                    }
                }
            }
        }
        return (output);
    }

    /**
     *
     * @param token
     * @param pairToken
     * @return
     * @throws Exception
     */
    public static JSONToken getPairSymbol(final JSONToken token, JSONToken pairToken) throws Exception {
        if(token.getType().isSymbol()) {
            throw new Exception("Expected Token: please check JSONToken and signature");
        }
        if(token.getType() == JSONToken.TYPE.START_OBJECT) {
            pairToken = new JSONToken(JSONToken.TYPE.END_OBJECT, JSONTokenUtil.CHAR_END_OBJECT);
        }
        if(token.getType() == JSONToken.TYPE.END_OBJECT) {
            pairToken = new JSONToken(JSONToken.TYPE.START_OBJECT, JSONTokenUtil.CHAR_START_OBJECT);
        }
        if(token.getType() == JSONToken.TYPE.START_ARRAY) {
            pairToken = new JSONToken(JSONToken.TYPE.END_ARRAY, JSONTokenUtil.CHAR_END_ARRAY);
        }
        if(token.getType() == JSONToken.TYPE.END_ARRAY) {
            pairToken = new JSONToken(JSONToken.TYPE.START_ARRAY, JSONTokenUtil.CHAR_START_ARRAY);
        }
        return (pairToken);
    }
}
