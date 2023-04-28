/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package com.alibaba.excel.analysis.v07.handlers.sax;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.excel.cache.ReadCache;
import com.alibaba.excel.constant.ExcelXmlConstants;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Sax read sharedStringsTable.xml
 *
 * @author Jiaju Zhuang
 */
public class SharedStringsTableHandler extends DefaultHandler {

    private static final Pattern UTF_PATTTERN = Pattern.compile("_x([0-9A-Fa-f]{4})_");

    /**
     * The final piece of data
     */
    private StringBuilder currentData;
    /**
     * Current element data
     */
    private StringBuilder currentElementData;

    private final ReadCache readCache;
    /**
     * Some fields in the T tag need to be ignored
     */
    private boolean ignoreTagt = false;
    /**
     * The only time you need to read the characters in the T tag is when it is used
     */
    private boolean isTagt = false;

    public SharedStringsTableHandler(ReadCache readCache) {
        this.readCache = readCache;
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) {
        if (name == null) {
            return;
        }
        switch (name) {
            case ExcelXmlConstants.SHAREDSTRINGS_T_TAG:
            case ExcelXmlConstants.SHAREDSTRINGS_X_T_TAG:
            case ExcelXmlConstants.SHAREDSTRINGS_NS2_T_TAG:
                currentElementData = null;
                isTagt = true;
                break;
            case ExcelXmlConstants.SHAREDSTRINGS_SI_TAG:
            case ExcelXmlConstants.SHAREDSTRINGS_X_SI_TAG:
            case ExcelXmlConstants.SHAREDSTRINGS_NS2_SI_TAG:
                currentData = null;
                break;
            case ExcelXmlConstants.SHAREDSTRINGS_RPH_TAG:
            case ExcelXmlConstants.SHAREDSTRINGS_X_RPH_TAG:
            case ExcelXmlConstants.SHAREDSTRINGS_NS2_RPH_TAG:
                ignoreTagt = true;
                break;
            default:
                // ignore
        }
    }

    @Override
    public void endElement(String uri, String localName, String name) {
        if (name == null) {
            return;
        }
        switch (name) {
            case ExcelXmlConstants.SHAREDSTRINGS_T_TAG:
            case ExcelXmlConstants.SHAREDSTRINGS_X_T_TAG:
            case ExcelXmlConstants.SHAREDSTRINGS_NS2_T_TAG:
                if (currentElementData != null) {
                    if (currentData == null) {
                        currentData = new StringBuilder();
                    }
                    currentData.append(currentElementData);
                }
                isTagt = false;
                break;
            case ExcelXmlConstants.SHAREDSTRINGS_SI_TAG:
            case ExcelXmlConstants.SHAREDSTRINGS_X_SI_TAG:
            case ExcelXmlConstants.SHAREDSTRINGS_NS2_SI_TAG:
                if (currentData == null) {
                    readCache.put(null);
                } else {
                    readCache.put(utfDecode(currentData.toString()));
                }
                break;
            case ExcelXmlConstants.SHAREDSTRINGS_RPH_TAG:
            case ExcelXmlConstants.SHAREDSTRINGS_X_RPH_TAG:
            case ExcelXmlConstants.SHAREDSTRINGS_NS2_RPH_TAG:
                ignoreTagt = false;
                break;
            default:
                // ignore
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (!isTagt || ignoreTagt) {
            return;
        }
        if (currentElementData == null) {
            currentElementData = new StringBuilder();
        }
        currentElementData.append(ch, start, length);
    }

    /**
     * from poi XSSFRichTextString
     *
     * @param value the string to decode
     * @return the decoded string or null if the input string is null
     * <p>
     * For all characters which cannot be represented in XML as defined by the XML 1.0 specification,
     * the characters are escaped using the Unicode numerical character representation escape character
     * format _xHHHH_, where H represents a hexadecimal character in the character's value.
     * <p>
     * Example: The Unicode character 0D is invalid in an XML 1.0 document,
     * so it shall be escaped as <code>_x000D_</code>.
     * </p>
     * See section 3.18.9 in the OOXML spec.
     * @see org.apache.poi.xssf.usermodel.XSSFRichTextString#utfDecode(String)
     */
    static String utfDecode(String value) {
        if (value == null || !value.contains("_x")) {
            return value;
        }

        StringBuilder buf = new StringBuilder();
        Matcher m = UTF_PATTTERN.matcher(value);
        int idx = 0;
        while (m.find()) {
            int pos = m.start();
            if (pos > idx) {
                buf.append(value, idx, pos);
            }

            String code = m.group(1);
            int icode = Integer.decode("0x" + code);
            buf.append((char)icode);

            idx = m.end();
        }

        // small optimization: don't go via StringBuilder if not necessary,
        // the encodings are very rare, so we should almost always go via this shortcut.
        if (idx == 0) {
            return value;
        }

        buf.append(value.substring(idx));
        return buf.toString();
    }
}
