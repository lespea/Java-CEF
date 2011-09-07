/**
 * Extension.java    2011-09-06
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



package com.lespea.cef;

//~--- non-JDK imports --------------------------------------------------------

import com.lespea.cef.utils.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


//~--- classes ----------------------------------------------------------------

/**
 * Object that holds the mapping of all the elements that are part of the CEF extension.
 * <p>
 * This object is immutable and once created no changes can be made!
 *
 * @version 1.0, 2011-09-06
 * @author Adam Lesperance
 */
public class Extension implements Serializable {

    /**
     * Logger object
     */
    private static final Logger LOG = LoggerFactory.getLogger( Extension.class );

    /** Field description */
    private static final long serialVersionUID = 1L;

    //~--- fields -------------------------------------------------------------

    /** Field description */
    private final String asString;

    /** Field description */
    private final Map<String, String> fields;


    //~--- constructors -------------------------------------------------------

    /**
     * Create a new extension object using the provided map. All of the key/value pairs are checked
     * to ensure they are valid and the map is made read-only.
     *
     * @param extensionFields
     *            the mapping of extension keys and their values
     * @throws InvalidExtensionKey
     *             if one of the provided keys is invalid
     */
    Extension( final Map<String, String> extensionFields ) throws InvalidExtensionKey {
        final StringBuilder sb    = new StringBuilder();
        Boolean             first = true;

        for (final Entry<String, String> entry : extensionFields.entrySet()) {
            if (first) {
                first = false;
            }
            else {
                sb.append( " " );
            }


            sb.append( StringUtils.escapeExtensionKey( entry.getKey() ) );
            sb.append( "=" );
            sb.append( StringUtils.escapeExtensionValue( entry.getValue() ) );
        }


        Extension.LOG.debug( "The map was valid and turned into an unmodifiable collection" );

        fields = Collections.unmodifiableMap( extensionFields );

        Extension.LOG.debug( "The extension's string was calculated as {}", sb.toString() );

        asString = sb.toString();
    }


    //~--- methods ------------------------------------------------------------

    @Override
    public boolean equals( final Object obj ) {
        if (this == obj) {
            return true;
        }
        else if (obj == null) {
            return false;
        }
        else if (this.getClass() != obj.getClass()) {
            return false;
        }
        else if (!fields.equals( ((Extension) obj).getFields() )) {
            return false;
        }


        return true;
    }


    @Override
    public int hashCode() {
        return fields.hashCode();
    }


    @Override
    public String toString() {
        return asString;
    }


    //~--- get methods --------------------------------------------------------

    /**
     * @return a copy of the fields present in the extension
     */
    public Map<String, String> getFields() {
        return new HashMap<String, String>( fields );
    }
}
