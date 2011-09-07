/**
 * CEF.java    2011-09-07
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

//~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;


//~--- classes ----------------------------------------------------------------

/**
 * Object that holds all the values of a CEF object.
 * <p>
 * This object is immutable and once created no changes can be made!
 *
 * @version 1.0, 2011-09-07
 * @author Adam Lesperance
 */
public class CEF implements Serializable {

    /**
     * The default CEF version that is used while creating a CEF object. This should always be used!
     * <p>
     * <code>0</code>
     */
    public static final int DEFAULT_CEF_VERSION = 0;

    /** Field description */
    private static final long serialVersionUID = 1L;

    //~--- fields -------------------------------------------------------------

    /** Holds the computed string output of the CEF object */
    private final String asString;

    /** The version of the CEF format */
    private final int cefVersion;

    /** The CEF extension field */
    private final Extension extension;

    /** Holds the computed hashCode for the CEF object */
    private final int hashCode;

    /** The CEF id field */
    private final String id;

    /** The CEF name field */
    private final String name;

    /** The CEF product field */
    private final String product;

    /** The CEF severity field */
    private final int severity;

    /** The CEF vendor field */
    private final String vendor;

    /** The CEF version field */
    private final String version;


    //~--- constructors -------------------------------------------------------

    /**
     * Cast the id as a string
     *
     * @param vendor
     *            part of the group of strings that uniquely identify the type of sending device. No
     *            two products may use the same device-vendor and device- product pair.
     * @param product
     *            part of the group of strings that uniquely identify the type of sending device. No
     *            two products may use the same device-vendor and device- product pair.
     * @param version
     *            part of the group of strings that uniquely identify the type of sending device. No
     *            two products may use the same device-vendor and device- product pair.
     * @param id
     *            a unique identifier per event-type. This can be a string or an integer. Signature
     *            ID identifies the type of event reported. In the intrusion detection system (IDS)
     *            world, each signature or rule that detects certain activity has a unique signature
     *            ID assigned. This is a requirement for other types of devices as well, and helps
     *            correlation engines deal with the events.
     * @param name
     *            a string representing a human-readable and understandable description of the
     *            event. The event name should not contain information that is specifically
     *            mentioned in other fields. For example: "Port scan from 10.0.0.1 targeting
     *            20.1.1.1" is not a good event name. It should be: "Port scan". The other
     *            information is redundant and can be picked up from the other fields.
     * @param severity
     *            an integer and reflects the importance of the event. Only numbers from 0 to 10 are
     *            allowed, where 10 indicates the most important event.
     * @param extension
     *            a collection of key-value pairs. The keys are part of a predefined set. The
     *            standard allows for including additional keys as outlined later.
     * @throws InvalidField
     *             if any of the provided fields are invalid
     *
     * @see <a href="http://www.arcsight.com/collateral/CEFstandards.pdf">PDF for the CEF
     *      standard</a>
     */
    public CEF( final String vendor, final String product, final String version, final int id, final String name,
                final int severity, final Extension extension )
            throws InvalidField {
        this( vendor, product, version, Integer.toString( id ), name, severity, extension );
    }


    /**
     * Use the default CEF version
     *
     * @param vendor
     *            part of the group of strings that uniquely identify the type of sending device. No
     *            two products may use the same device-vendor and device- product pair.
     * @param product
     *            part of the group of strings that uniquely identify the type of sending device. No
     *            two products may use the same device-vendor and device- product pair.
     * @param version
     *            part of the group of strings that uniquely identify the type of sending device. No
     *            two products may use the same device-vendor and device- product pair.
     * @param id
     *            a unique identifier per event-type. This can be a string or an integer. Signature
     *            ID identifies the type of event reported. In the intrusion detection system (IDS)
     *            world, each signature or rule that detects certain activity has a unique signature
     *            ID assigned. This is a requirement for other types of devices as well, and helps
     *            correlation engines deal with the events.
     * @param name
     *            a string representing a human-readable and understandable description of the
     *            event. The event name should not contain information that is specifically
     *            mentioned in other fields. For example: "Port scan from 10.0.0.1 targeting
     *            20.1.1.1" is not a good event name. It should be: "Port scan". The other
     *            information is redundant and can be picked up from the other fields.
     * @param severity
     *            an integer and reflects the importance of the event. Only numbers from 0 to 10 are
     *            allowed, where 10 indicates the most important event.
     * @param extension
     *            a collection of key-value pairs. The keys are part of a predefined set. The
     *            standard allows for including additional keys as outlined later.
     * @throws InvalidField
     *             if any of the provided fields are invalid
     *
     * @see <a href="http://www.arcsight.com/collateral/CEFstandards.pdf">PDF for the CEF
     *      standard</a>
     */
    public CEF( final String vendor, final String product, final String version, final String id, final String name,
                final int severity, final Extension extension )
            throws InvalidField {
        this( CEF.DEFAULT_CEF_VERSION, vendor, product, version, id, name, severity, extension );
    }


    /**
     * Construct an immutable CEF object.
     *
     *
     * @param cefVersion
     *            an integer and identifies the version of the CEF format. Event consumers use this
     *            information to determine what the following fields represent. Currently only
     *            version 0 (zero) is established in the above format. Experience may show that
     *            other fields need to be added to the “prefix” and therefore require a version
     *            number change. Adding new formats is handled through the standards body.
     * @param vendor
     *            part of the group of strings that uniquely identify the type of sending device. No
     *            two products may use the same device-vendor and device- product pair.
     * @param product
     *            part of the group of strings that uniquely identify the type of sending device. No
     *            two products may use the same device-vendor and device- product pair.
     * @param version
     *            part of the group of strings that uniquely identify the type of sending device. No
     *            two products may use the same device-vendor and device- product pair.
     * @param id
     *            a unique identifier per event-type. This can be a string or an integer. Signature
     *            ID identifies the type of event reported. In the intrusion detection system (IDS)
     *            world, each signature or rule that detects certain activity has a unique signature
     *            ID assigned. This is a requirement for other types of devices as well, and helps
     *            correlation engines deal with the events.
     * @param name
     *            a string representing a human-readable and understandable description of the
     *            event. The event name should not contain information that is specifically
     *            mentioned in other fields. For example: "Port scan from 10.0.0.1 targeting
     *            20.1.1.1" is not a good event name. It should be: "Port scan". The other
     *            information is redundant and can be picked up from the other fields.
     * @param severity
     *            an integer and reflects the importance of the event. Only numbers from 0 to 10 are
     *            allowed, where 10 indicates the most important event.
     * @param extension
     *            a collection of key-value pairs. The keys are part of a predefined set. The
     *            standard allows for including additional keys as outlined later.
     * @throws InvalidField
     *             if any of the provided fields are invalid
     *
     * @see <a href="http://www.arcsight.com/collateral/CEFstandards.pdf">PDF for the CEF
     *      standard</a>
     */
    public CEF( final int cefVersion, final String vendor, final String product, final String version, final String id,
                final String name, final int severity, final Extension extension )
            throws InvalidField {
        this.cefVersion = cefVersion;
        this.vendor     = vendor;
        this.product    = product;
        this.version    = version;
        this.id         = id;
        this.name       = name;
        this.severity   = severity;
        this.extension  = extension;

        assert vendor != null : "The vendor cannot be null";
        assert product != null : "The product cannot be null";
        assert version != null : "The version cannot be null";
        assert id != null : "The id cannot be null";
        assert name != null : "The name cannot be null";
        assert extension != null : "The extension cannot be null";
        assert ((severity >= 0) && (severity <= 10)) : "The severity must be between 0 and 10";

        final StringBuilder sb = new StringBuilder( 50 + extension.toString().length() );

        sb.append( "CEF:" );
        sb.append( cefVersion );
        sb.append( "|" );
        sb.append( StringUtils.escapeField( vendor ) );
        sb.append( "|" );
        sb.append( StringUtils.escapeField( product ) );
        sb.append( "|" );
        sb.append( StringUtils.escapeField( version ) );
        sb.append( "|" );
        sb.append( StringUtils.escapeField( id ) );
        sb.append( "|" );
        sb.append( StringUtils.escapeField( name ) );
        sb.append( "|" );
        sb.append( severity );
        sb.append( "|" );
        sb.append( extension );

        asString = sb.toString();
        hashCode = asString.hashCode();
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
        else if (getClass() != obj.getClass()) {
            return false;
        }


        final CEF other = (CEF) obj;

        if (cefVersion != other.cefVersion) {
            return false;
        }
        else if (!id.equals( other.id )) {
            return false;
        }
        else if (!name.equals( other.name )) {
            return false;
        }
        else if (!product.equals( other.product )) {
            return false;
        }
        else if (severity != other.severity) {
            return false;
        }
        else if (!vendor.equals( other.vendor )) {
            return false;
        }
        else if (!version.equals( other.version )) {
            return false;
        }
        else if (!extension.equals( other.extension )) {
            return false;
        }


        return true;
    }


    @Override
    public int hashCode() {
        return hashCode;
    }


    @Override
    public String toString() {
        return asString;
    }


    //~--- get methods --------------------------------------------------------

    /**
     * @return the cefVersion
     */
    public int getCefVersion() {
        return cefVersion;
    }


    /**
     * @return the extension
     */
    public Extension getExtension() {
        return extension;
    }


    /**
     * @return the id
     */
    public String getId() {
        return id;
    }


    /**
     * @return the name
     */
    public String getName() {
        return name;
    }


    /**
     * @return the product
     */
    public String getProduct() {
        return product;
    }


    /**
     * @return the severity
     */
    public int getSeverity() {
        return severity;
    }


    /**
     * @return the vendor
     */
    public String getVendor() {
        return vendor;
    }


    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }
}
