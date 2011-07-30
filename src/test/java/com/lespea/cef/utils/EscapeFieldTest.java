/**
 * EscapeFieldTest.java    2011-07-29
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

import com.lespea.cef.InvalidField;

import junit.framework.Assert;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


//~--- classes ----------------------------------------------------------------

/**
 * Make sure the CEF fields are properly escaped under all circumstances.
 *
 * @author Adam Lesperance
 */
public class EscapeFieldTest {

    /**
     * List of strings that shouldn't be escaped at all.
     *
     * @return the grouping of strings to process
     */
    @DataProvider
    public Object[][] normalFields() {
        return TestHelpers.genEscapeStrings( "blah blah blah", "blah blah blah" );
    }


    /**
     * List of strings that contain pipes and how they should look after being escaped.
     *
     * @return the grouping of strings to process
     */
    @DataProvider
    public Object[][] pipeFields() {
        return TestHelpers.genEscapeStrings( "|", "\\|" );
    }


    /**
     * List of strings that contain backslashes and how they should look after being escaped.
     *
     * @return the grouping of strings to process
     */
    @DataProvider
    public Object[][] slashFields() {
        return TestHelpers.genEscapeStrings( "\\", "\\\\" );
    }


    /**
     * Makes sure the pipe character is properly escaped in a field.
     *
     * @param unquotedStr
     *            the string to quote
     * @param quotedStr
     *            what the string should be transformed to by the function
     * @throws InvalidField
     *             if the field contains an invalid character
     */
    @Test(dataProvider = "pipeFields")
    public void testFieldPipes( final String unquotedStr, final String quotedStr ) throws InvalidField {
        Assert.assertEquals( quotedStr, StringUtils.escapeField( unquotedStr ) );
    }


    /**
     * Makes sure that normal characters are not escaped in a field.
     *
     * @param unquotedStr
     *            the string to quote
     * @param quotedStr
     *            what the string should be transformed to by the function
     * @throws InvalidField
     *             if the field contains an invalid character
     */
    @Test(dataProvider = "normalFields")
    public void testNormalPipes( final String unquotedStr, final String quotedStr ) throws InvalidField {
        Assert.assertEquals( quotedStr, StringUtils.escapeField( unquotedStr ) );
    }


    /**
     * Makes sure that backslash characters are not escaped in a field.
     *
     * @param unquotedStr
     *            the string to quote
     * @param quotedStr
     *            what the string should be transformed to by the function
     * @throws InvalidField
     *             if the field contains an invalid character
     */
    @Test(dataProvider = "slashFields")
    public void testSlashPipes( final String unquotedStr, final String quotedStr ) throws InvalidField {
        Assert.assertEquals( quotedStr, StringUtils.escapeField( unquotedStr ) );
    }
}
