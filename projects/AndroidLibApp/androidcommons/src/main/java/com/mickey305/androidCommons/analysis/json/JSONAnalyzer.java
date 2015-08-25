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

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class JSONAnalyzer {

    /**
     *
     * @param out
     * @param json1
     * @param json2
     * @param <T>
     * @return
     */
    public static <T> List<JSONToken> matchedKeyList(List<JSONToken> out, T json1, T json2) {
        out.clear();
        JSONPicker picker1 = new JSONPicker(json1);
        JSONPicker picker2 = new JSONPicker(json2);
        LinkedList<JSONToken> hashedKeyList = picker1.getAllKeyHashList();
        HashSet<String> keyHashSet = picker2.getAllKeyHashSet();
        // out.addAll(hashedKeyList.stream().filter(
        //         key -> keyHashSet.contains(key.getValue().to_s())
        // ).collect(Collectors.toList()));
        for(JSONToken key: hashedKeyList) {
            if(keyHashSet.contains(key.getValue().to_s())) {
                out.add(key);
            }
        }
        return (out);
    }

    /**
     *
     * @param out
     * @param json1
     * @param json2
     * @param <T>
     * @return
     */
    public static <T> List<JSONToken> matchedValueList(List<JSONToken> out, T json1, T json2) {
        out.clear();
        JSONPicker picker1 = new JSONPicker(json1);
        JSONPicker picker2 = new JSONPicker(json2);
        LinkedList<JSONToken> hashedValueList = picker1.getAllValueHashList();
        HashSet<String> valueHashSet = picker2.getAllValueHashSet();
        // out.addAll(hashedValueList.stream().filter(
        //         key -> valueHashSet.contains(key.getValue().to_s())
        // ).collect(Collectors.toList()));
        for(JSONToken value: hashedValueList) {
            if(valueHashSet.contains(value.getValue().to_s())) {
                out.add(value);
            }
        }
        return (out);
    }

    /**
     *
     * @param out
     * @param json
     * @param <T>
     * @return
     */
    public static <T> int[] getKeyNumber(int[] out, T json) {
        JSONPicker picker = new JSONPicker(json);
        out[0] = picker.getAllKeyList().size();
        out[1] = picker.getAllKeyHashSet().size();
        return (out);
    }

    /**
     *
     * @param out
     * @param json
     * @param <T>
     * @return
     */
    public static <T> int[] getValueNumber(int[] out, T json) {
        JSONPicker picker = new JSONPicker(json);
        out[0] = picker.getAllValueList().size();
        out[1] = picker.getAllValueHashSet().size();
        return (out);
    }

    /**
     *
     * @param out
     * @param json
     * @param <T>
     * @return
     */
    public static <T> int[] getTreeLayerDeepest(int[] out, T json) {
        int deep = 0, deepMax = 0;
        Iterator<JSONToken> iterator = new JSONTokenizer(json.toString());
        while (iterator.hasNext()) {
            JSONToken token = iterator.next();
            if (token.getType().isSymbol()) {
                if (
                        token.getType() == JSONToken.TYPE.START_OBJECT ||
                        token.getType() == JSONToken.TYPE.START_ARRAY )
                {
                    deep ++;
                } else {
                    deepMax = Math.max(deepMax, deep);
                    deep --;
                }
            }
        }
        out[0] = deepMax;
        return (out);
    }

    /**
     *
     * @param out
     * @param json
     * @param <T>
     * @return
     */
    public static <T> List<Integer> getTreeLayerList(List<Integer> out, T json) {
        int deep = 0;
        Iterator<JSONToken> iterator = new JSONTokenizer(json.toString());
        while (iterator.hasNext()) {
            JSONToken token = iterator.next();
            if (token.getType().isSymbol()) {
                if (
                        token.getType() == JSONToken.TYPE.START_OBJECT ||
                        token.getType() == JSONToken.TYPE.START_ARRAY )
                {
                    deep ++;
                } else {
                    deep --;
                }
                out.add(deep);
            }
        }
        out.remove(out.size() -1);
        return (out);
    }

    /**
     *
     * @param out
     * @param json
     * @param <T>
     * @return
     */
    public static <T> List<JSONToken> createSymbolList(List<JSONToken> out, T json) {
        Iterator<JSONToken> iterator = new JSONTokenizer(json.toString());
        while (iterator.hasNext()) {
            JSONToken token = iterator.next();
            if (token.getType().isSymbol()) {
                out.add(token);
            }
        }
        return (out);
    }

    /**
     *
     * @param json1
     * @param json2
     * @param <T>
     * @return
     */
    public static <T> boolean sameStructure(T json1, T json2) {
        LinkedList<JSONToken>[] symbolsArray = new LinkedList[2];
        String[] symbolArray = new String[2];
        for(int i=0; i < symbolArray.length; i++) {
            symbolArray[i] = "";
            symbolsArray[i] = new LinkedList<>();
            JSONAnalyzer.createSymbolList(symbolsArray[i], i==0? json1: json2);
            for (JSONToken token : symbolsArray[i]) {
                symbolArray[i] += token.getValue().to_s();
            }
        }
        return symbolArray[0].equals(symbolArray[1]);
    }

}
