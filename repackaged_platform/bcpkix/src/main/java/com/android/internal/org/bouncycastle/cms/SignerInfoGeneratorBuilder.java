/* GENERATED SOURCE. DO NOT MODIFY. */
package com.android.internal.org.bouncycastle.cms;

import com.android.internal.org.bouncycastle.asn1.DEROctetString;
import com.android.internal.org.bouncycastle.asn1.cms.IssuerAndSerialNumber;
import com.android.internal.org.bouncycastle.asn1.cms.SignerIdentifier;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.internal.org.bouncycastle.cert.X509CertificateHolder;
import com.android.internal.org.bouncycastle.operator.ContentSigner;
import com.android.internal.org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import com.android.internal.org.bouncycastle.operator.DigestAlgorithmIdentifierFinder;
import com.android.internal.org.bouncycastle.operator.DigestCalculator;
import com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider;
import com.android.internal.org.bouncycastle.operator.OperatorCreationException;

/**
 * Builder for SignerInfo generator objects.
 * @hide This class is not part of the Android public SDK API
 */
public class SignerInfoGeneratorBuilder
{
    private final DigestAlgorithmIdentifierFinder digAlgFinder = new DefaultDigestAlgorithmIdentifierFinder();

    private DigestCalculatorProvider digestProvider;
    private boolean directSignature;
    private CMSAttributeTableGenerator signedGen;
    private CMSAttributeTableGenerator unsignedGen;
    private CMSSignatureEncryptionAlgorithmFinder sigEncAlgFinder;
    private AlgorithmIdentifier contentDigest;

    /**
     *  Base constructor.
     *
     * @param digestProvider  a provider of digest calculators for the algorithms required in the signature and attribute calculations.
     */
    public SignerInfoGeneratorBuilder(DigestCalculatorProvider digestProvider)
    {
        this(digestProvider, new DefaultCMSSignatureEncryptionAlgorithmFinder());
    }

    /**
     * Base constructor with a particular finder for signature algorithms.
     *
     * @param digestProvider a provider of digest calculators for the algorithms required in the signature and attribute calculations.
     * @param sigEncAlgFinder finder for algorithm IDs to store for the signature encryption/signature algorithm field.
     */
    public SignerInfoGeneratorBuilder(DigestCalculatorProvider digestProvider, CMSSignatureEncryptionAlgorithmFinder sigEncAlgFinder)
    {
        this.digestProvider = digestProvider;
        this.sigEncAlgFinder = sigEncAlgFinder;
    }

    /**
     * If the passed in flag is true, the signer signature will be based on the data, not
     * a collection of signed attributes, and no signed attributes will be included.
     *
     * @return the builder object
     */
    public SignerInfoGeneratorBuilder setDirectSignature(boolean hasNoSignedAttributes)
    {
        this.directSignature = hasNoSignedAttributes;

        return this;
    }

    /**
     * Set the algorithm identifier for the contentDigest to be used for processing the data.
     *
     * @return the builder object
     */
    public SignerInfoGeneratorBuilder setContentDigest(AlgorithmIdentifier contentDigest)
    {
        this.contentDigest = contentDigest;

        return this;
    }

    /**
     *  Provide a custom signed attribute generator.
     *
     * @param signedGen a generator of signed attributes.
     * @return the builder object
     */
    public SignerInfoGeneratorBuilder setSignedAttributeGenerator(CMSAttributeTableGenerator signedGen)
    {
        this.signedGen = signedGen;

        return this;
    }

    /**
     * Provide a generator of unsigned attributes.
     *
     * @param unsignedGen  a generator for signed attributes.
     * @return the builder object
     */
    public SignerInfoGeneratorBuilder setUnsignedAttributeGenerator(CMSAttributeTableGenerator unsignedGen)
    {
        this.unsignedGen = unsignedGen;

        return this;
    }

    /**
     * Build a generator with the passed in certHolder issuer and serial number as the signerIdentifier.
     *
     * @param contentSigner  operator for generating the final signature in the SignerInfo with.
     * @param certHolder  carrier for the X.509 certificate related to the contentSigner.
     * @return  a SignerInfoGenerator
     * @throws OperatorCreationException   if the generator cannot be built.
     */
    public SignerInfoGenerator build(ContentSigner contentSigner, X509CertificateHolder certHolder)
        throws OperatorCreationException
    {
        SignerIdentifier sigId = new SignerIdentifier(new IssuerAndSerialNumber(certHolder.toASN1Structure()));

        SignerInfoGenerator sigInfoGen = createGenerator(contentSigner, sigId);

        sigInfoGen.setAssociatedCertificate(certHolder);

        return sigInfoGen;
    }

    /**
     * Build a generator with the passed in subjectKeyIdentifier as the signerIdentifier. If used  you should
     * try to follow the calculation described in RFC 5280 section 4.2.1.2.
     *
     * @param contentSigner  operator for generating the final signature in the SignerInfo with.
     * @param subjectKeyIdentifier    key identifier to identify the public key for verifying the signature.
     * @return  a SignerInfoGenerator
     * @throws OperatorCreationException if the generator cannot be built.
     */
    public SignerInfoGenerator build(ContentSigner contentSigner, byte[] subjectKeyIdentifier)
        throws OperatorCreationException
    {
        SignerIdentifier sigId = new SignerIdentifier(new DEROctetString(subjectKeyIdentifier));

        return createGenerator(contentSigner, sigId);
    }

    private SignerInfoGenerator createGenerator(ContentSigner contentSigner, SignerIdentifier sigId)
        throws OperatorCreationException
    {
        DigestCalculator digester;
        if (contentDigest != null)
        {
            digester = digestProvider.get(contentDigest);
        }
        else
        {
            digester = digestProvider.get(digAlgFinder.find(contentSigner.getAlgorithmIdentifier()));
        }

        if (directSignature)
        {
            return new SignerInfoGenerator(sigId, contentSigner, digester.getAlgorithmIdentifier(), sigEncAlgFinder);
        }

        if (signedGen != null || unsignedGen != null)
        {
            if (signedGen == null)
            {
                signedGen = new DefaultSignedAttributeTableGenerator();
            }

            return new SignerInfoGenerator(sigId, contentSigner, digester, sigEncAlgFinder, signedGen, unsignedGen);
        }
        
        return new SignerInfoGenerator(sigId, contentSigner, digester, sigEncAlgFinder, new DefaultSignedAttributeTableGenerator(), null);
    }
}
