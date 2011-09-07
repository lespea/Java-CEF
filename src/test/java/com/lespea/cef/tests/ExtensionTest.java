/**
 * ExtensionTest.java    2011-09-06
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



package com.lespea.cef.tests;

//~--- non-JDK imports --------------------------------------------------------

import com.lespea.cef.Extension;
import com.lespea.cef.InvalidExtensionKey;

import junit.framework.Assert;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.Map;


//~--- classes ----------------------------------------------------------------

/**
 * Make sure the extension object conforms to expectations
 *
 * @version 1.0, 2011-09-06
 * @author Adam Lesperance
 */
public class ExtensionTest {

    /**
     * @return values that a normal extension should produce
     */
    @DataProvider
    public Object[][] okayExtensions() {
        return new Object[][] {
            { "key1~value1", "key1=value1" }, { "key=1~value=1", "key\\=1=value\\=1" },
            { "key1~value2\n", "key1=value2\\n" }, { "key1~value2\r", "key1=value2\\r" },
            { "key1~value2\t", "key1=value2\t" }, { "key1~value1 plus some spaces", "key1=value1 plus some spaces" },
            { "key2~and some more!", "key2=and some more!" }
        };
    }


    /**
     * @return values that a normal extension with multiple fields should produce
     */
    @DataProvider
    public Object[][] okayMultiExtensions() {
        return new Object[][] {
            { "key1~value1|key2~value2", "key1=value1 key2=value2|key2=value2 key1=value1" },
            { "key1~value1\n|key2~value2\r", "key1=value1\\n key2=value2\\r|key2=value2\\r key1=value1\\n" },
            { "key1~value1 plus some spaces|key2~and some more!",
              "key1=value1 plus some spaces key2=and some more!|key2=and some more! key1=value1 plus some spaces" },
        };
    }


    /**
     * Verify that an invalid extension key throws an exception when escaped
     *
     * @param keyStr
     *            string that will be used to create the extension object
     * @param wantedStr
     *            how the extension object's string should appear
     *
     * @throws InvalidExtensionKey
     *             if an invalid key is escaped
     */
    @Test(
        dataProvider       = "okayExtensions",
        expectedExceptions = InvalidExtensionKey.class
    )
    public void testBadExtension( final String keyStr, final String wantedStr ) throws InvalidExtensionKey {
        final Map<String, String> extensionMap = new HashMap<String, String>();
        final String[]            parts        = keyStr.split( "~" );

        extensionMap.put( parts[0] + " ", parts[1] );

        @SuppressWarnings("unused") final Extension extension = new Extension( extensionMap );
    }


    /**
     * Verify an extension created with a blank map is created okay
     *
     * @throws InvalidExtensionKey
     *             if an invalid key is escaped
     */
    @Test
    public void testBlankExtension() throws InvalidExtensionKey {
        final Extension ext = new Extension( new HashMap<String, String>() );

        Assert.assertEquals( "", ext.toString() );
    }


    /**
     * Verify that changing the cloned extension fields won't change the object
     *
     * @throws InvalidExtensionKey
     *             if an invalid key is escaped
     */
    @Test
    public void testClonedFields() throws InvalidExtensionKey {
        final Extension           ext = new Extension( new HashMap<String, String>() );
        final Map<String, String> t   = ext.getFields();

        t.put( "test", "test" );
        Assert.assertEquals( "", ext.toString() );
    }


    /**
     * Verify that valid extensions have the correct strings
     *
     * @param keyStr
     *            string that will be used to create the extension object
     * @param wantedStr
     *            how the extension object's string should appear
     * @throws InvalidExtensionKey
     *             if an invalid key is escaped
     */
    @Test(dataProvider = "okayExtensions")
    public void testOkayExtension( final String keyStr, final String wantedStr ) throws InvalidExtensionKey {
        final Map<String, String> extensionMap = new HashMap<String, String>();
        final String[]            parts        = keyStr.split( "~" );

        extensionMap.put( parts[0], parts[1] );

        final Extension extension = new Extension( extensionMap );

        Assert.assertEquals( extension.toString(), wantedStr );
    }


    /**
     * Verify that valid extensions have the correct strings with multiple key/value pairs
     *
     * @param keyStr
     *            string that will be used to create the extension object
     * @param wantedStr
     *            how the extension object's string should appear
     * @throws InvalidExtensionKey
     *             if an invalid key is escaped
     */
    @Test(dataProvider = "okayMultiExtensions")
    public void testOkayMultiExtension( final String keyStr, final String wantedStr ) throws InvalidExtensionKey {
        final Map<String, String> extensionMap = new HashMap<String, String>();

        for (final String parts : keyStr.split( "[|]" )) {
            final String[] piece = parts.split( "~" );

            extensionMap.put( piece[0], piece[1] );
        }


        final Extension extension = new Extension( extensionMap );
        final String    str       = extension.toString();
        final String[]  wanted    = wantedStr.split( "[|]" );

        // Sorry but can't tell the order the string will be created with maps
        Assert.assertEquals( str, str.equals( wanted[0] )
                                  ? wanted[0]
                                  : str.equals( wanted[1] )
                                    ? wanted[1]
                                    : wantedStr );
    }
}
