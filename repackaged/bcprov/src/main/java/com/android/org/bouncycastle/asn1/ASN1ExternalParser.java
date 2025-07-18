/* GENERATED SOURCE. DO NOT MODIFY. */
package com.android.org.bouncycastle.asn1;

import java.io.IOException;

/**
 * Parser DER EXTERNAL tagged objects.
 * @hide This class is not part of the Android public SDK API
 */
public interface ASN1ExternalParser
    extends ASN1Encodable, InMemoryRepresentable
{
    /**
     * Read the next object in the parser.
     *
     * @return an ASN1Encodable
     * @throws IOException on a parsing or decoding error.
     */
    ASN1Encodable readObject()
        throws IOException;
}
