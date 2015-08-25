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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * JSONPicker is the Utility Class to search the JSONObject or JSONArray Information.
 *
 * e.g.
 * JSONArray: [{"name":{"first":"hoge","last":"fuga"}},{}]
 * ---------------------------------------------------
 * JSONKey is name, first and last
 * JSONValue is {"first":"hoge","last":"fuga"}, hoge, fuga, {} and {"name":{"first":"hoge","last":"fuga"}}
 * JSONTokenSymbol is {, }, [ and ]
 *
 * @param <T> generics: JSONObject or JSONArray
 */
public class JSONPicker<T> implements Cloneable {

    public static final String TAG = JSONPicker.class.getName();

    /**
     * JSON Token List
     */
    private LinkedList<JSONToken> tokenList;

    /**
     *
     * @param JsonObjectJsonArray
     */
    public JSONPicker(final T JsonObjectJsonArray) {
        tokenList = new LinkedList<>();
        this.buildTokenList(JsonObjectJsonArray);
    }

    /**
     *
     * @param inst is JSONObject or JSONArray
     */
    public synchronized void buildTokenList(final T inst) {
        if(inst instanceof JSONObject) {
            JSONTokenListBuilder.build((JSONObject) inst, this.tokenList);
        } else if(inst instanceof JSONArray) {
            JSONTokenListBuilder.build((JSONArray) inst, this.tokenList);
        } else {
            // error
            try {
                throw new Exception("Excepted Generics: please insert JSONObject or JSONArray");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * clone this Object
     * @return Object
     */
    @Override
    public JSONPicker<T> clone() {
        JSONPicker<T> scope = null;
        try {
            scope = (JSONPicker<T>) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return (scope);
    }

    /**
     * Not-Pair Value List creator
     * @param tokenList is JSON Token List
     * @return Value Token List
     */
    protected LinkedList<JSONToken> createNonKeyValueList(LinkedList<JSONToken> tokenList) {
        LinkedList<JSONToken> outValueList = new LinkedList<>();
        int embeddedIndex = 0;
        final int size = tokenList.size();
        for(; embeddedIndex < size; embeddedIndex++) {
            JSONToken token = tokenList.get(embeddedIndex);
            if(embeddedIndex == 0 && token.getType() != JSONToken.TYPE.START_ARRAY) { break; }
            if(embeddedIndex == 0) { continue; }
            if(token.getType() == JSONToken.TYPE.START_OBJECT) {
                try {
                    JSONToken value = this.generateEmbeddedValue(
                            JSONToken.TYPE.VALUE_JSON_OBJECT, tokenList, embeddedIndex);
                    outValueList.add(value);
                    embeddedIndex = JSONTokenListUtil.getPairSymbolPoint(tokenList, embeddedIndex);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if(token.getType() == JSONToken.TYPE.START_ARRAY) {
                try {
                    JSONToken value = this.generateEmbeddedValue(
                            JSONToken.TYPE.VALUE_JSON_ARRAY, tokenList, embeddedIndex);
                    outValueList.add(value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return (outValueList);
    }

    /**
     *
     * @param object
     * @return
     */
    public boolean equals(Object object) {
        return EqualsBuilder.reflectionEquals(this, object);
    }

    /**
     *
     * @return All Key List contained in JSONObject or JSONArray
     */
    public LinkedList<JSONToken> getAllKeyList() {
        LinkedList<JSONToken> keyList = new LinkedList<>();
        // this.tokenList.stream().filter(
        //         token -> token.getType() == JSONToken.TYPE.FIELD_NAME
        // ).forEach(keyList::push);
        for(JSONToken token: this.tokenList) {
            if(token.getType() == JSONToken.TYPE.FIELD_NAME) {
                keyList.push(token);
            }
        }
        return (keyList);
    }

    /**
     *
     * @return All Key Hashed List
     */
    public LinkedList<JSONToken> getAllKeyHashList() {
        LinkedList<JSONToken> keyHashList = new LinkedList<>();
        HashSet<String> hashSet = this.getAllKeyHashSet();
        Iterator<String> iterator = hashSet.iterator();
        while (iterator.hasNext()) {
            JSONToken token = new JSONToken(JSONToken.TYPE.FIELD_NAME, iterator.next());
            keyHashList.add(token);
        }
        return (keyHashList);
    }

    /**
     *
     * @return All Key HashSet
     */
    public HashSet<String> getAllKeyHashSet() {
        LinkedList<JSONToken> allKeyList = this.getAllKeyList();
        LinkedList<String> strAllKeyList = new LinkedList<>();
        while (!allKeyList.isEmpty()) {
            strAllKeyList.push(allKeyList.remove().getValue().to_s());
        }
        HashSet<String> allKeyHashSet = new HashSet<>();
        allKeyHashSet.addAll(strAllKeyList);
        return (allKeyHashSet);
    }

    /**
     *
     * @return All Value List contained in JSONObject or JSONArray
     */
    public LinkedList<JSONToken> getAllValueList() {
        // add Value-List of Hash-JSONKey
        LinkedList<JSONToken> valueList = new LinkedList<>();
        LinkedList<JSONToken> allKeyHashList = this.getAllKeyHashList();
        while(!allKeyHashList.isEmpty()) {
            valueList.addAll(this.getValues(allKeyHashList.remove().getValue().to_s()));
        }
        // add Value-List of Not-Contains JSONValue
        // LinkedList<JSONToken> tmpValueList = this.tokenList.stream().filter(
        //         token -> JSONTokenUtil.isTypeValue(token) && !valueList.contains(token)
        // ).collect(Collectors.toCollection(() -> new LinkedList<>()));
        LinkedList<JSONToken> tmpValueList = new LinkedList<>();
        for(JSONToken token: this.tokenList) {
            if(JSONTokenUtil.isTypeValue(token) && !valueList.contains(token)) {
                tmpValueList.add(token);
            }
        }
        valueList.addAll(tmpValueList);
        LinkedList<JSONToken> tmpTokenList = (LinkedList<JSONToken>) this.tokenList.clone();
        tmpValueList = (LinkedList<JSONToken>) this.createNonKeyValueList(tmpTokenList);
        valueList.addAll(tmpValueList);
        return (valueList);
    }

    /**
     *
     * @return All Value Hashed List
     */
    public LinkedList<JSONToken> getAllValueHashList() {
        LinkedList<JSONToken> allValueList = this.getAllValueList();
        LinkedList<JSONToken> valueHashList = new LinkedList<>();
        HashSet<String> hashSet = this.getAllValueHashSet();
        Iterator<String> iterator = hashSet.iterator();
        while (iterator.hasNext()) {
            final String value = iterator.next();
            JSONToken.TYPE type = null;
            for(JSONToken token: allValueList) {
                if(token.getValue().to_s().equals(value)) {
                    type = token.getType();
                }
            }
            JSONToken token = new JSONToken(type, value);
            valueHashList.add(token);
        }
        return (valueHashList);
    }

    /**
     *
     * @return All Value HashSet
     */
    public HashSet<String> getAllValueHashSet() {
        LinkedList<JSONToken> allValueList = this.getAllValueList();
        LinkedList<String> strAllValueList = new LinkedList<>();
        while (!allValueList.isEmpty()) {
            strAllValueList.push(allValueList.remove().getValue().to_s());
        }
        HashSet<String> allValueHashSet = new HashSet<>();
        allValueHashSet.addAll(strAllValueList);
        return (allValueHashSet);
    }

    /*
     * The Tutorial of getValues and searchValues method
     * e.g.
     * registered json ----------------------------------------------------
     * [{
     *  "name" : { "first" : "taro", "last" : "suzuki" },
     *  "mail" : "taro@example.jp",
     *  "todoList" : { "work" : "report", "limit" : "1994/05/13" }
     * },
     * {
     *  "name" : { "first" : "satoshi", "last" : "maeda" },
     *  "mail" : "satoshi0612@example.jp",
     *  "todoList" : { "work" : "test", "limit" : "2015/06/12" }
     * },
     * {
     *  "name" : { "first" : "hanako", "last" : "tanaka" },
     *  "mail" : "hanako_teacher@example.jp",
     *  "birthday" : "1985/02/13",
     *  "todoList" : { "work" : "book the hotel", "limit" : "2005/03/31" }
     * }]
     *
     * called method ------------------------------------------------------
     * input -->
     *  keyList.add("name");
     *  keyList.add("last");
     *  OR
     *  method signature : "name", "last"
     *  OR
     *  keyList.add("last");
     * output -->
     *  suzuki
     *  maeda
     *  tanaka as LinkedList<JSONToken>
     *
     * input -->
     *  keyList.add("last");
     *  keyList.add("name");
     *  OR
     *  method signature : "last", "name"
     * output -->
     *  LinkedList<JSONToken> is Empty(Exception Occurred)
     *
     * input -->
     *  keyList.add("birthday");
     * output -->
     *  1985/02/13 as LinkedList<JSONToken>
     *
     * input -->
     *  keyList.add("name");
     * output -->
     *  { "first" : "taro", "last" : "suzuki" }
     *  { "first" : "satoshi", "last" : "maeda" }
     *  { "first" : "hanako", "last" : "tanaka" } as LinkedList<JSONToken>
     */

    /**
     *
     * @param key is pair of JSONValue to search
     * @param tokenList JSON Token List
     * @return Matched JSONValue List
     */
    protected LinkedList<JSONToken> getValues(final String key, LinkedList<JSONToken> tokenList) {
        LinkedList<JSONToken> outList = new LinkedList<>();
        JSONToken token;
        final int size = tokenList.size();
        for (int i=0; i < size; i++) {
            token = tokenList.remove();
            if(
                    token.getType() == JSONToken.TYPE.FIELD_NAME && (token.getValue().to_s()).equals(key) )
            {
                token = tokenList.peek();
                if(JSONTokenUtil.isTypeValue(token)) {
                    outList.add(token);
                } else if(token.getType() == JSONToken.TYPE.START_OBJECT) {
                    try {
                        JSONToken value = this.generateEmbeddedValue(JSONToken.TYPE.VALUE_JSON_OBJECT, tokenList, 0);
                        outList.add(value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if(token.getType() == JSONToken.TYPE.START_ARRAY) {
                    try {
                        JSONToken value = this.generateEmbeddedValue(JSONToken.TYPE.VALUE_JSON_ARRAY, tokenList, 0);
                        outList.add(value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if(outList.isEmpty()) {
            try {
                throw new Exception("Not Found: JSON values");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (outList);
    }

    /**
     *
     * @param key
     * @return
     */
    public LinkedList<JSONToken> getValues(final String key) {
        LinkedList<JSONToken> tokenList = (LinkedList<JSONToken>) this.tokenList.clone();
        return this.getValues(key, tokenList);
    }

    /**
     *
     * @param keyList
     * @return
     */
    public LinkedList<JSONToken> getValues(final LinkedList<String> keyList) {
        LinkedList<JSONToken> jsonValueList = this.getValues(keyList.remove());
        while (!keyList.isEmpty()) {
            String key = keyList.remove();
            LinkedList<JSONToken> tmpJsonValueList = new LinkedList<>();
            while (!jsonValueList.isEmpty()) {
                final String jsonString = jsonValueList.remove().getValue().to_s();
                LinkedList<JSONToken> tmpTokenList = new LinkedList<>();
                JSONTokenListBuilder.build(jsonString, tmpTokenList);
                tmpJsonValueList.addAll(this.getValues(key, tmpTokenList));
            }
            jsonValueList = tmpJsonValueList;
        }
        return (jsonValueList);
    }

    /**
     *
     * @param keys
     * @return
     */
    public LinkedList<JSONToken> getValues(final String... keys) {
        LinkedList<String> keyList = new LinkedList<>();
        for(String key: keys) {
            keyList.add(key);
        }
        try {
            return this.getValues(keyList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
     * @param key
     * @return
     */
    public boolean isExistKey(final String key) {
        for(JSONToken token: this.tokenList) {
            if(token.getType() == JSONToken.TYPE.FIELD_NAME && token.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param keys
     * @return
     */
    public boolean isExistAllKeys(final LinkedList<String> keys) {
        final int size = keys.size();
        int count = 0;
        for(String key: keys) {
            if(this.isExistKey(key)) { count++; }
        }
        return size == count;
    }

    /**
     *
     * @param keys
     * @return
     */
    public boolean isExistAllKeys(final String... keys) {
        LinkedList<String> keyList = new LinkedList<>();
        for(String key: keys) {
            keyList.add(key);
        }
        return this.isExistAllKeys(keyList);
    }

    /**
     * the same function with getValues
     * @param key
     * @return
     */
    public LinkedList<JSONToken> searchValues(final String key) {
        return this.getValues(key);
    }

    /**
     *
     * @param keyList
     * @return
     */
    public LinkedList<JSONToken> searchValues(final LinkedList<String> keyList) {
        return this.getValues(keyList);
    }

    /**
     *
     * @param keys
     * @return
     */
    public LinkedList<JSONToken> searchValues(final String... keys) {
        return this.getValues(keys);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * Create Embedded-JSONValue(JSONObject or JSONArray)
     * @param embeddedType is JSON_ARRAY or JSON_OBJECT {@link com.mickey305.androidCommons.analysis.json.JSONToken.TYPE}
     * @param tokenList is JSON Token List
     * @param tokenListStartPoint is START Embedded-Symbol Index
     * @return Embedded JSONToken(Embedded Value)
     * @throws Exception
     */
    protected JSONToken generateEmbeddedValue(
            JSONToken.TYPE embeddedType,
            final LinkedList<JSONToken> tokenList,
            final int tokenListStartPoint) throws Exception
    {
        if(!(embeddedType == JSONToken.TYPE.VALUE_JSON_ARRAY ||
                embeddedType == JSONToken.TYPE.VALUE_JSON_OBJECT))
        {
            throw new Exception("Excepted JSON-Type: TYPE is only VALUE_JSONArray or VALUE_JSONObject");
        }
        JSONToken token;
        String value = "";
        int start_symbol = 0;
        int end_symbol = 0;
        final int size = tokenList.size();
        for (int k = tokenListStartPoint; k < size; k++) {
            String tmpValue;
            token = tokenList.get(k);
            final JSONToken.TYPE startType = (embeddedType == JSONToken.TYPE.VALUE_JSON_ARRAY)?
                    JSONToken.TYPE.START_ARRAY: JSONToken.TYPE.START_OBJECT;
            final JSONToken.TYPE endType = (embeddedType == JSONToken.TYPE.VALUE_JSON_ARRAY)?
                    JSONToken.TYPE.END_ARRAY: JSONToken.TYPE.END_OBJECT;

            if(token.getType() == startType) { start_symbol++; }
            if(token.getType() == endType) { end_symbol++; }

            // create token-string and added double quotes
            if(
                    token.getType() == JSONToken.TYPE.VALUE_STRING ||
                    token.getType() == JSONToken.TYPE.FIELD_NAME )
            {
                tmpValue = JSONTokenUtil.CHAR_DOUBLE_QUOTES
                        + token.getValue().to_s()
                        + JSONTokenUtil.CHAR_DOUBLE_QUOTES;
            } else {
                tmpValue = token.getValue().to_s();
            }

            // added colon
            if(token.getType() == JSONToken.TYPE.FIELD_NAME) {
                tmpValue += JSONTokenUtil.CHAR_COLON;
            }

            // added comma
            if(
                    JSONTokenUtil.isTypeValue(token)            ||
                    token.getType() == JSONToken.TYPE.END_ARRAY ||
                    token.getType() == JSONToken.TYPE.END_OBJECT )
            {
                try {
                    JSONToken.TYPE nextType = tokenList.get(k +1).getType();
                    if(!(nextType == JSONToken.TYPE.END_ARRAY ||
                            nextType == JSONToken.TYPE.END_OBJECT))
                    {
                        if(start_symbol != end_symbol) tmpValue += JSONTokenUtil.CHAR_COMMA;
                    }
                } catch (IndexOutOfBoundsException e) {
                    // exception
                }
            }

            value += tmpValue;
            if(start_symbol == end_symbol) { break; }
        }
        if(value.isEmpty()) {
            throw new Exception("Not Found: Embedded-Value is empty. please check signature of this method");
        }
        return new JSONToken(embeddedType, value);
    }

}
