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
     * List of strings that should throw an InvalidField exception
     *
     * @return array of bad strings to process
     */
    @DataProvider
    public Object[][] badFields() {
        return new Object[][] {
            { "blah blah \r", "" }, { "blah blah \n", "" }
        };
    }


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
     * List of strings that contain backslashes and pipes and how they should look after being
     * escaped.
     *
     * @return the grouping of strings to process
     */
    @DataProvider
    public Object[][] slashPipeFields() {
        return TestHelpers.genEscapeStrings( "\\||\\||\\", "\\\\\\|\\|\\\\\\|\\|\\\\" );
    }


    /**
     * Verify a bad field throws an exception if it's attempted to be escaped
     *
     * @param unquotedStr
     *            the string to quote
     * @param quotedStr
     *            what the string should be transformed to by the function
     * @throws InvalidField
     *             if the field contains an invalid character
     */
    @Test(
        dataProvider       = "badFields",
        expectedExceptions = InvalidField.class
    )
    public void testBadEscapeField( final String unquotedStr, final String quotedStr ) throws InvalidField {
        StringUtils.escapeField( unquotedStr );
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
    public void testFieldNormal( final String unquotedStr, final String quotedStr ) throws InvalidField {
        Assert.assertEquals( quotedStr, StringUtils.escapeField( unquotedStr ) );
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
    public void testFieldSlash( final String unquotedStr, final String quotedStr ) throws InvalidField {
        Assert.assertEquals( quotedStr, StringUtils.escapeField( unquotedStr ) );
    }


    /**
     * Makes sure the pipes and slash character is properly escaped in a field.
     *
     * @param unquotedStr
     *            the string to quote
     * @param quotedStr
     *            what the string should be transformed to by the function
     * @throws InvalidField
     *             if the field contains an invalid character
     */
    @Test(dataProvider = "slashPipeFields")
    public void testFieldSlashPipes( final String unquotedStr, final String quotedStr ) throws InvalidField {
        Assert.assertEquals( quotedStr, StringUtils.escapeField( unquotedStr ) );
    }


    /**
     * Makes sure the escape method is thread safe.
     *
     * @param unquotedStr
     *            the string to quote
     * @param quotedStr
     *            what the string should be transformed to by the function
     * @throws InvalidField
     *             if the field contains an invalid character
     */
    @Test(
        dataProvider    = "slashPipeFields",
        threadPoolSize  = 50,
        invocationCount = 50
    )
    public void testFieldSlashPipesThreads( final String unquotedStr, final String quotedStr ) throws InvalidField {
        Assert.assertEquals( quotedStr, StringUtils.escapeField( unquotedStr ) );
    }


    /**
     * Verify that an invalid field is marked as such
     */
    @Test
    public void testInvalidField() {
        Assert.assertFalse( StringUtils.isValidField( "Normal st\nring with a bunch of stuff \\||\\|\\|\t\t\t\t" ) );
        Assert.assertFalse( StringUtils.isValidField( "Normal st\rring with a bunch of stuff \\||\\|\\|\t\t\t\t" ) );
    }


    /**
     * Verify that a valid field is marked as such
     */
    @Test
    public void testValidField() {
        Assert.assertTrue( StringUtils.isValidField( "Normal string with a bunch of stuff \\||\\|\\|\t\t\t\t" ) );
    }
}
