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
     * Escape the backslash character ("\")
     */
    @Test
    public void testEscapeBackslash() {
        final String[]   onePipe    = new String[] { "Hi Mr. Backslash \\", "Hi Mr. Backslash \\\\" };
        final String[]   twoPipes1  = new String[] { "Hi Mr. \\ Backslash \\", "Hi Mr. \\\\ Backslash \\\\" };
        final String[]   twoPipes2  = new String[] { "Hi Mr. Backslash \\\\", "Hi Mr. Backslash \\\\\\\\" };
        final String[][] strsToTest = new String[][] {
            onePipe, twoPipes1, twoPipes2
        };

        for (String[] strToTest : strsToTest) {
            Assert.assertEquals( strToTest[1], StringUtils.escapeField( strToTest[0] ) );
        }
    }


    /**
     * Escape the pipe character ("|")
     */
    @Test
    public void testEscapePipe() {
        final String[]   oneSlash   = new String[] { "Hi Mr. Pipe |", "Hi Mr. Pipe \\|" };
        final String[]   twoSlash1  = new String[] { "Hi Mr. | Pipe |", "Hi Mr. \\| Pipe \\|" };
        final String[]   twoSlash2  = new String[] { "Hi Mr. Pipe ||", "Hi Mr. Pipe \\|\\|" };
        final String[][] strsToTest = new String[][] {
            oneSlash, twoSlash1, twoSlash2
        };

        for (String[] strToTest : strsToTest) {
            Assert.assertEquals( strToTest[1], StringUtils.escapeField( strToTest[0] ) );
        }
    }


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


    // TODO: Should nulls throw an exception VS returning null?

    /**
     * Null strings should return null
     */
    @Test
    public void testNullEscapes() {
        Assert.assertNull( StringUtils.escapeField( null ) );
    }
}
