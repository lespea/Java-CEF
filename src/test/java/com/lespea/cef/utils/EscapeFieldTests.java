/**
 * EscapeFieldTests.java    2011-07-19
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

//~--- non-JDK imports --------------------------------------------------------

import junit.framework.Assert;

import org.junit.Test;


//~--- classes ----------------------------------------------------------------

/**
 * Make sure the CEF fields are properly escaped under all circumstances
 *
 * @author Adam Lesperance
 *
 */
public class EscapeFieldTests {

    /**
     * Strings that should be the same coming out as going in
     */
    @Test
    public void testNoEscapes() {
        final String   blankString   = "";
        final String   normalString  = "This is a string";
        final String   unicodeString = "Hi there, let's try out some unicode shall we? «á℅£¢®©»";
        final String[] strsToTest    = new String[] { blankString, normalString, unicodeString };

        for (String strToTest : strsToTest) {
            Assert.assertEquals( strToTest, StringUtils.escapeField( strToTest ) );
        }
    }


    /**
     * Null strings should return null
     */
    @Test
    public void testNullEscapes() {
        Assert.assertNull( StringUtils.escapeField( null ) );
    }
}
