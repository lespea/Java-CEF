/**
 * StringUtils.java    2011-07-19
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//~--- JDK imports ------------------------------------------------------------

import java.util.regex.Matcher;
import java.util.regex.Pattern;


//~--- classes ----------------------------------------------------------------

/**
 * Utility functions that manipulate strings to work correctly with the CEF format
 *
 * @version 1.0, 2011-07-19
 * @author Adam Lesperance
 */
public final class StringUtils {

    /**
     * Logger object
     */
    private static final Logger LOG = LoggerFactory.getLogger( StringUtils.class );

    /**
     * Pattern used to determine if a field has any invalid characters
     * <p>
     * <code>/[\r\n]/</code>
     */
    private static final Pattern INVALID_FIELD_PATTERN = Pattern.compile( "[\r\n]" );

    /**
     * Pattern used to determine if a field has any invalid characters
     * <p>
     * <code>/\s/</code>
     */
    private static final Pattern INVALID_EXTENSION_KEY_PATTERN = Pattern.compile( "\\s" );

    /**
     * Pattern used to escape any of the characters that require escaping in the field part of a CEF
     * string
     */
    private static final Pattern ESCAPE_FIELD_PATTERN = Pattern.compile( "([|\\\\])" );

    /**
     * Pattern used to escape any of the characters that require escaping in the extension value
     * part of a CEF string
     */
    private static final Pattern ESCAPE_EXTENSION_VALUE_PATTERN = Pattern.compile( "[\\\\\r\n]" );


    //~--- constructors -------------------------------------------------------

    /**
     * Null constructor for utility class
     */
    private StringUtils() {}


    //~--- methods ------------------------------------------------------------

    /**
     * Every field in a CEF string (minus the extension) must escape the backslash
     * <code>("\")</code> character and translate any newline (<code>"\r" OR "\n"</code>) characters
     * into their respective string representative.
     * <p>
     * Null strings return null for now.
     *
     * @param valueStr
     *            the text of the extension value that requires escaping
     * @return the escaped version of the extension value string
     */
    public static String escapeExtensionValue( final String valueStr ) {
        if (valueStr == null) {
            StringUtils.LOG.warn( "Tried to escape a null CEF extension value" );

            return null;
        }


        final Matcher      matcher       = StringUtils.ESCAPE_EXTENSION_VALUE_PATTERN.matcher( valueStr );
        final StringBuffer escapedStrBuf = new StringBuffer( valueStr.length() );

        while (matcher.find()) {
            final char letter = matcher.group( 0 ).charAt( 0 );
            String     replacement;

            switch (letter) {
            case '\r' :
                replacement = "\\\\r";
                break;

            case '\n' :
                replacement = "\\\\n";
                break;

            case '\\' :
                replacement = "\\\\\\\\";
                break;

            default :
                replacement = "\\\\" + letter;
            }


            matcher.appendReplacement( escapedStrBuf, replacement );
        }


        matcher.appendTail( escapedStrBuf );

        final String escapedStr = escapedStrBuf.toString();

        StringUtils.LOG.debug( "The CEF field \"{}\" was escaped to \"{}\"", valueStr, escapedStr );

        return escapedStr;
    }


    // TODO: Investigate using String.getBytes(“UTF-8″) to coerce the string
    // into UTF-8 (not-super important atm since I probably don't even have to do this)

    /**
     * Every field in a CEF string (minus the extension) must escape the bar <code>("|")</code>
     * character as well as the backslash <code>("\")</code>.
     * <p>
     * Additionally, the field string may not contain a vertical newline character and, if one is
     * found, then an IllegalArgument exception is thrown!
     * <p>
     * Null strings return null for now.
     *
     * @param fieldStr
     *            the text of the field that requires escaping
     * @return the escaped version of the field string
     * @throws InvalidField
     *             if the string to be escaped is invalid according to the CEF spec
     */
    public static String escapeField( final String fieldStr ) throws InvalidField {
        if (fieldStr == null) {
            StringUtils.LOG.warn( "Tried to escape a null CEF field" );

            return null;
        }


        if (StringUtils.INVALID_FIELD_PATTERN.matcher( fieldStr ).find()) {
            StringUtils.LOG.error( "The field string contained an invalid character" );

            throw new InvalidField( "The field string " + fieldStr + " contained an invalid character" );
        }


        final String escapedStr = StringUtils.ESCAPE_FIELD_PATTERN.matcher( fieldStr ).replaceAll( "\\\\$1" );

        StringUtils.LOG.debug( "The CEF field \"{}\" was escaped to \"{}\"", fieldStr, escapedStr );

        return escapedStr;
    }


    //~--- get methods --------------------------------------------------------

    /**
     * Tests if the provided string is a valid extension key string.
     * <p>
     * A field is not valid if it contains a whitespace character
     *
     * @param extensionKeyStr
     *            the extension key string to test
     * @return if the extension key string contains a whitespace character
     */
    public static Boolean isValidExtensionKey( final String extensionKeyStr ) {
        final Boolean isValid;

        if (extensionKeyStr == null) {
            StringUtils.LOG.warn( "Tried to detect if a null string was a valid extension key string" );

            isValid = false;
        }
        else if (StringUtils.INVALID_EXTENSION_KEY_PATTERN.matcher( extensionKeyStr ).find()) {
            isValid = false;
        }
        else {
            isValid = true;
        }


        StringUtils.LOG.debug( "The extension key string \"{}\" is {}valid", extensionKeyStr, isValid
                ? ""
                : "not " );

        return isValid;
    }


    /**
     * Tests if the provided string is a valid extension value string
     * <p>
     * Essentially everything is valid except for nulls
     *
     * @param extensionValueStr
     *            the extension value to test
     * @return if the extension value string is null or not
     */
    public static Boolean isValidExtensionValue( final String extensionValueStr ) {
        if (extensionValueStr == null) {
            StringUtils.LOG.warn( "Tried to detect if a null string was a valid extension value string" );

            return false;
        }


        StringUtils.LOG.debug( "The extension key string \"{}\" is valid", extensionValueStr );

        return true;
    }


    /**
     * Tests if the provided string is a valid field string.
     * <p>
     * A field is not valid if it contains a vertical newline character
     *
     * @param fieldStr
     *            the field string to test
     * @return if the field string contains a vertical newline character
     */
    public static Boolean isValidField( final String fieldStr ) {
        final Boolean isValid;

        if (fieldStr == null) {
            StringUtils.LOG.warn( "Tried to detect if a null string was a valid field string" );

            isValid = false;
        }
        else if (StringUtils.INVALID_FIELD_PATTERN.matcher( fieldStr ).find()) {
            isValid = false;
        }
        else {
            isValid = true;
        }


        StringUtils.LOG.debug( "The field string \"{}\" is {}valid", fieldStr, isValid
                ? ""
                : "not " );

        return isValid;
    }
}
