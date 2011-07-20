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

import java.util.regex.Pattern;


//~--- classes ----------------------------------------------------------------

/**
 * Utility functions that manipulate strings to work correctly with the CEF format
 *
 * @version 1.0, 2011-07-19
 * @author Adam Lesperance
 */
public class StringUtils {

    /**
     * Logger object
     */
    private static final Logger Log = LoggerFactory.getLogger( StringUtils.class );

    /**
     * Pattern used to escape any of the characters that require escaping in the field part of a CEF
     * string
     */
    private static final Pattern escapeFieldPattern = Pattern.compile( "([|\\\\])" );

    /**
     * Pattern used to determine if a field has any invalid characters
     */
    private static final Pattern invalidFieldPattern = Pattern.compile( ".*[\r\n].*" );


    //~--- methods ------------------------------------------------------------

    // TODO: Investigate using String.getBytes(“UTF-8″) to coerce the string
    // into UTF-8 (not-super important atm)

    /**
     * Every field in a CEF string (minus the extension) must escape the bar <code>("|")</code>
     * character as well as the backslash <code>("\")</code>
     * <p>
     * Additionally, the field string may not contain a vertical newline character and, if one is
     * found, then an IllegalArgument exception is thrown!
     *
     * @param fieldStr
     *            the text of the field that requires escaping
     * @return the escaped version of the field string
     * @throws InvalidField
     *             if the string to be escaped is invalid according to the CEF spec
     */
    public static final String escapeField( final String fieldStr ) throws InvalidField {
        if (fieldStr == null) {
            Log.warn( "Tried to escape a null CEF field" );

            return null;
        }


        if (invalidFieldPattern.matcher( fieldStr ).matches()) {
            Log.error( "The field string contained an invalid character" );

            throw new InvalidField( "The field string " + fieldStr + " contained an invalid character" );
        }


        final String escapedStr = escapeFieldPattern.matcher( fieldStr ).replaceAll( "\\\\$1" );

        Log.debug( "The CEF field \"{}\" was escaped to \"{}\"", fieldStr, escapedStr );

        return escapedStr;
    }
}
