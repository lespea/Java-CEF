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

//~--- JDK imports ------------------------------------------------------------

import java.util.regex.Pattern;


//~--- classes ----------------------------------------------------------------

/**
 * Utility functions that manipulate strings to work correctly with the CEF
 * format
 *
 * @version 1.0, 2011-07-19
 * @author Adam Lesperance
 */
public class StringUtils {

    /**
     * Pattern used to escape any of the characters that require escaping in the
     * field part of a CEF string
     */
    private static final Pattern escapeFieldPattern = Pattern.compile( "([|])" );


    //~--- methods ------------------------------------------------------------

    /**
     * Every field in a CEF string (minus the extension) must escape the bar
     * <code>("|")</code> character as well as the backslash <code>("\")</code>
     * <p>
     * Additionally, the field string may not contain a vertical newline
     * character and, if one is found, then an IllegalArgument exception is
     * thrown!
     *
     * @param fieldStr
     *            the text of the field that requires escaping
     * @return the escaped version of the field string
     */
    public static final String escapeField( final String fieldStr ) {
        if (fieldStr == null) {
            return null;
        }


        final String escapedStr = escapeFieldPattern.matcher( fieldStr ).replaceAll( "\\\\$1" );

        return escapedStr;
    }
}
