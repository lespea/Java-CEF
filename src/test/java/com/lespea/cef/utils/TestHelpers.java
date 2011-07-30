/**
 * TestHelpers.java    2011-07-29
 *
 * Copyright 2011, Adam Lesperance
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */



package com.lespea.cef.utils;

/**
 * Helper functions to assist with testing
 *
 * @author Adam Lesperance
 */
public final class TestHelpers {

    /** Field description */
    private static final String UNICODE_STRING = "«á℅£¢®©»";


    //~--- methods ------------------------------------------------------------

    /**
     * Helper function to create strings that require escaping
     *
     * @param str2Escape
     *            the string that should be escaped
     * @param escapedStr
     *            how the string will appear when it is escaped
     * @return collection of strings to be used by TestNG
     */
    public static Object[][] genEscapeStrings( final String str2Escape, final String escapedStr ) {
        final Object[][] generatedStrs = new Object[5][2];

        generatedStrs[0] = new Object[] { "Contains one bad string" + str2Escape,
                                          "Contains one bad string" + escapedStr };
        generatedStrs[1] = new Object[] { str2Escape + "Contains two " + str2Escape + " bad strings",
                                          escapedStr + "Contains two " + escapedStr + " bad strings" };
        generatedStrs[2] = new Object[] { str2Escape + "Contains two bad strings",
                                          escapedStr + "Contains two bad strings" };
        generatedStrs[3] = new Object[] {
            str2Escape + str2Escape + "Contains many bad strings" + str2Escape + str2Escape,
            escapedStr + escapedStr + "Contains many bad strings" + escapedStr + escapedStr };
        generatedStrs[4] = new Object[] {
            "Let's do unicde " + TestHelpers.UNICODE_STRING + str2Escape + " ending string",
            "Let's do unicde " + TestHelpers.UNICODE_STRING + escapedStr + " ending string" };

        return generatedStrs;
    }
}
