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
package com.mickey305.androidCommons.analysis.string;

public class ScannerLine {

    private String line;
    private int currentPosition;
    private int maxPosition;

    /**
     *
     * @param input
     */
    public ScannerLine(String input) {
        line = input;
        currentPosition = 0;
        if (line != null) maxPosition = line.length();
    }

    /**
     *
     * @return
     */
    public char next() {
        if (!hasNext()) { return (char) -1; }
        return line.charAt(currentPosition++);
    }

    /**
     *
     * @return
     */
    public char peek() {
        if (!hasNext()) { return (char) -1; }
        return line.charAt(currentPosition);
    }

    /**
     *
     * @return
     */
    public int getIndex() {
        return currentPosition;
    }

    /**
     *
     * @return
     */
    public boolean hasNext() {
        return (line != null) && (maxPosition != currentPosition);
    }

    /**
     *
     * @return
     */
    public boolean cutWhitespace() {
        boolean hasWhiteSpace = false;
        char ch = peek();
        while (Character.isWhitespace(ch)) {
            this.next();
            ch = peek();
            hasWhiteSpace = true;
        }
        return hasWhiteSpace;
    }

    /**
     *
     * @return
     */
    public boolean cutSpaceChar() {
        boolean hasSpaceChar = false;
        char ch = peek();
        while (Character.isSpaceChar(ch)) {
            this.next();
            ch = peek();
            hasSpaceChar = true;
        }
        return hasSpaceChar;
    }

    /**
     *
     * @param inputChar
     * @return
     */
    public boolean cutCharacter(char inputChar) {
        boolean hasCharacter = false;
        char ch = peek();
        while (ch == inputChar) {
            this.next();
            ch = peek();
            hasCharacter = true;
        }
        return hasCharacter;
    }

    /**
     *
     * @param ch
     * @return
     */
    public Boolean[] cutCharacter(char... ch) {
        Boolean[] hasCharacter = new Boolean[ch.length];
        int i = 0;
        for (char c: ch) {
            hasCharacter[i++] = this.cutCharacter(c);
        }
        return hasCharacter;
    }

}
