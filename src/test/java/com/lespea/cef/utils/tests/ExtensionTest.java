/**
 * ExtensionTest.java    2011-07-30
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



package com.lespea.cef.utils.tests;

//~--- non-JDK imports --------------------------------------------------------

import com.lespea.cef.utils.StringUtils;

import junit.framework.Assert;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


//~--- classes ----------------------------------------------------------------

/**
 * Class description
 *
 *
 * @version 1.0, 2011-07-30
 * @author Adam Lesperance
 */
public class ExtensionTest {

    /**
     * List of strings that should throw an InvalidField exception
     *
     * @return array of bad strings to process
     */
    @DataProvider
    public Object[][] badKeyStrings() {
        return new Object[][] {
            { "blahblah\r", "" }, { "blahblah\n", "" }, { "blahblah\t", "" }, { "blahblah ", "" }
        };
    }


    /**
     * List of strings that contain carriage returns and how they should look after being escaped.
     *
     * @return the grouping of strings to process
     */
    @DataProvider
    public Object[][] carriageValues() {
        return TestHelpers.genEscapeStrings( "\r", "\\r" );
    }


    /**
     * List of strings that contain carriage returns and how they should look after being escaped.
     *
     * @return the grouping of strings to process
     */
    @DataProvider
    public Object[][] mixedValues() {
        return TestHelpers.genEscapeStrings( "\r\n\n\\\\\r", "\\r\\n\\n\\\\\\\\\\r" );
    }


    /**
     * List of strings that contain newlines and how they should look after being escaped.
     *
     * @return the grouping of strings to process
     */
    @DataProvider
    public Object[][] newlineValues() {
        return TestHelpers.genEscapeStrings( "\n", "\\n" );
    }


    /**
     * List of strings that shouldn't be escaped at all.
     *
     * @return the grouping of strings to process
     */
    @DataProvider
    public Object[][] normalKeyStrings() {
        return new Object[][] {
            { TestHelpers.UNICODE_STRING + "blahblahblah" + TestHelpers.UNICODE_STRING,
              TestHelpers.UNICODE_STRING + "blahblahblah" + TestHelpers.UNICODE_STRING }
        };
    }


    /**
     * List of strings that contain backslashes and how they should look after being escaped.
     *
     * @return the grouping of strings to process
     */
    @DataProvider
    public Object[][] slashValues() {
        return TestHelpers.genEscapeStrings( "\\", "\\\\" );
    }


    /**
     * Verify that an invalid extension key is marked as such
     *
     * @param badKey
     *            string that should be an invalid extension key
     * @param ignoreString
     *            *not used
     */
    @Test(dataProvider = "badKeyStrings")
    public void testInvalidExtensionKey( final String badKey, final String ignoreString ) {
        Assert.assertFalse( StringUtils.isValidExtensionKey( badKey ) );
    }


    /**
     * Makes sure the key test method is thread safe.
     *
     * @param okKey
     *            string that should be a valid extension key
     * @param ignoreString
     *            *not used
     */
    @Test(
        dataProvider    = "normalKeyStrings",
        threadPoolSize  = 100,
        invocationCount = 50
    )
    public void testKeyThreads( final String okKey, final String ignoreString ) {
        Assert.assertTrue( StringUtils.isValidExtensionKey( okKey ) );
    }


    /**
     * Verify that a null extension key is processed correctly
     */
    @Test
    public void testNullExtensionKey() {
        Assert.assertFalse( StringUtils.isValidExtensionKey( null ) );
    }


    /**
     * Verify that a null extension key is processed correctly
     */
    @Test
    public void testNullExtensionValue() {
        Assert.assertFalse( StringUtils.isValidExtensionValue( null ) );
        Assert.assertNull( StringUtils.escapeExtensionValue( null ) );
    }


    /**
     * Verify that a valid extension key is marked as such
     *
     * @param okKey
     *            string that should be a valid extension key
     * @param ignoreString
     *            *not used
     */
    @Test(dataProvider = "normalKeyStrings")
    public void testValidExtensionKey( final String okKey, final String ignoreString ) {
        Assert.assertTrue( StringUtils.isValidExtensionKey( okKey ) );
    }


    /**
     * Verify that a valid extension key is marked as such
     */
    @Test
    public void testValidExtensionValue() {
        final int          maxChar = 16 * 16 * 16 * 16;
        final StringBuffer okVal   = new StringBuffer( maxChar + 1 );

        for (int i = 0; i <= maxChar; i++) {
            okVal.append( (char) i );
        }


        Assert.assertTrue( StringUtils.isValidExtensionValue( okVal.toString() ) );
    }


    /**
     * Makes sure the carriage return character is properly escaped in an extension value.
     *
     * @param unquotedStr
     *            the string to quote
     * @param quotedStr
     *            what the string should be transformed to by the function
     */
    @Test(dataProvider = "carriageValues")
    public void testValueCarriage( final String unquotedStr, final String quotedStr ) {
        Assert.assertEquals( quotedStr, StringUtils.escapeExtensionValue( unquotedStr ) );
    }


    /**
     * Makes sure that mixed characters are properly escaped in an extension value.
     *
     * @param unquotedStr
     *            the string to quote
     * @param quotedStr
     *            what the string should be transformed to by the function
     */
    @Test(dataProvider = "mixedValues")
    public void testValueMixed( final String unquotedStr, final String quotedStr ) {
        Assert.assertEquals( quotedStr, StringUtils.escapeExtensionValue( unquotedStr ) );
    }


    /**
     * Makes sure the newline character is properly escaped in an extension value.
     *
     * @param unquotedStr
     *            the string to quote
     * @param quotedStr
     *            what the string should be transformed to by the function
     */
    @Test(dataProvider = "newlineValues")
    public void testValueNewlines( final String unquotedStr, final String quotedStr ) {
        Assert.assertEquals( quotedStr, StringUtils.escapeExtensionValue( unquotedStr ) );
    }


    /**
     * Makes sure the backslash character is properly escaped in an extension value.
     *
     * @param unquotedStr
     *            the string to quote
     * @param quotedStr
     *            what the string should be transformed to by the function
     */
    @Test(dataProvider = "slashValues")
    public void testValueSlashes( final String unquotedStr, final String quotedStr ) {
        Assert.assertEquals( quotedStr, StringUtils.escapeExtensionValue( unquotedStr ) );
    }


    /**
     * Makes sure the extension value methods are thread safe.
     *
     * @param quotedStr
     *            string that should be a valid extension value
     * @param unquotedStr
     *            what the string should be escaped to
     */
    @Test(
        dataProvider    = "mixedValues",
        threadPoolSize  = 100,
        invocationCount = 50
    )
    public void testValueThreads( final String unquotedStr, final String quotedStr ) {
        Assert.assertTrue( StringUtils.isValidExtensionValue( unquotedStr ) );
        Assert.assertEquals( quotedStr, StringUtils.escapeExtensionValue( unquotedStr ) );
    }
}
