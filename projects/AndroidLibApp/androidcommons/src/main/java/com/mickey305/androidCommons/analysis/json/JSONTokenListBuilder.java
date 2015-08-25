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
package com.mickey305.androidCommons.analysis.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

public class JSONTokenListBuilder {

    public static final String TAG = JSONTokenListBuilder.class.getName();

    /**
     *
     * @param jsonObject
     * @param output
     * @return
     */
    public static List<JSONToken> build(JSONObject jsonObject, List<JSONToken> output) {
        return build(jsonObject.toString(), output);
    }

    /**
     *
     * @param jsonArray
     * @param output
     * @return
     */
    public static List<JSONToken> build(JSONArray jsonArray, List<JSONToken> output) {
        return build(jsonArray.toString(), output);
    }

    /**
     *
     * @param jsonString
     * @param output
     * @return
     */
    protected static List<JSONToken> build(String jsonString, List<JSONToken> output) {
        output.clear();
        Iterator<JSONToken> tokenizer = new JSONTokenizer(jsonString);
        while (tokenizer.hasNext()) {
            JSONToken token = tokenizer.next();
            output.add(token);
        }
        return output;
    }

}
