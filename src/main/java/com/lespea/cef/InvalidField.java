/**
 * InvalidField.java    2011-07-20
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

/**
 * Exception thrown when an invalid field string is used
 * <p>
 * Nothing specific is done here besides calling super() for the various constructor calls
 *
 * @author Adam Lesperance
 *
 */
public final class InvalidField extends Exception {

    /**
     * Default serial ID
     */
    private static final long serialVersionUID = 1L;


    //~--- constructors -------------------------------------------------------

    /**
     * Don't do anything if no args are provided
     */
    public InvalidField() {}


    /**
     * @param message the message to be thrown
     */
    public InvalidField( String message ) {
        super( message );
    }


    /**
     * @param cause the exception that caused the issue
     */
    public InvalidField( Throwable cause ) {
        super( cause );
    }


    /**
     * @param message the message to be thrown
     * @param cause the exception that caused the issue
     */
    public InvalidField( String message, Throwable cause ) {
        super( message, cause );
    }
}
