/* GENERATED SOURCE. DO NOT MODIFY. */
package com.android.internal.org.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;

import com.android.internal.org.bouncycastle.math.ec.ECFieldElement;
import com.android.internal.org.bouncycastle.math.raw.Nat256;
import com.android.internal.org.bouncycastle.util.Arrays;
import com.android.internal.org.bouncycastle.util.encoders.Hex;

/**
 * @hide This class is not part of the Android public SDK API
 */
public class SecP256K1FieldElement extends ECFieldElement.AbstractFp
{
    public static final BigInteger Q = new BigInteger(1,
        Hex.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFC2F"));

    protected int[] x;

    public SecP256K1FieldElement(BigInteger x)
    {
        if (x == null || x.signum() < 0 || x.compareTo(Q) >= 0)
        {
            throw new IllegalArgumentException("x value invalid for SecP256K1FieldElement");
        }

        this.x = SecP256K1Field.fromBigInteger(x);
    }

    public SecP256K1FieldElement()
    {
        this.x = Nat256.create();
    }

    protected SecP256K1FieldElement(int[] x)
    {
        this.x = x;
    }

    public boolean isZero()
    {
        return Nat256.isZero(x);
    }

    public boolean isOne()
    {
        return Nat256.isOne(x);
    }

    public boolean testBitZero()
    {
        return Nat256.getBit(x, 0) == 1;
    }

    public BigInteger toBigInteger()
    {
        return Nat256.toBigInteger(x);
    }

    public String getFieldName()
    {
        return "SecP256K1Field";
    }

    public int getFieldSize()
    {
        return Q.bitLength();
    }

    public ECFieldElement add(ECFieldElement b)
    {
        int[] z = Nat256.create();
        SecP256K1Field.add(x, ((SecP256K1FieldElement)b).x, z);
        return new SecP256K1FieldElement(z);
    }

    public ECFieldElement addOne()
    {
        int[] z = Nat256.create();
        SecP256K1Field.addOne(x, z);
        return new SecP256K1FieldElement(z);
    }

    public ECFieldElement subtract(ECFieldElement b)
    {
        int[] z = Nat256.create();
        SecP256K1Field.subtract(x, ((SecP256K1FieldElement)b).x, z);
        return new SecP256K1FieldElement(z);
    }

    public ECFieldElement multiply(ECFieldElement b)
    {
        int[] z = Nat256.create();
        SecP256K1Field.multiply(x, ((SecP256K1FieldElement)b).x, z);
        return new SecP256K1FieldElement(z);
    }

    public ECFieldElement divide(ECFieldElement b)
    {
//        return multiply(b.invert());
        int[] z = Nat256.create();
        SecP256K1Field.inv(((SecP256K1FieldElement)b).x, z);
        SecP256K1Field.multiply(z, x, z);
        return new SecP256K1FieldElement(z);
    }

    public ECFieldElement negate()
    {
        int[] z = Nat256.create();
        SecP256K1Field.negate(x, z);
        return new SecP256K1FieldElement(z);
    }

    public ECFieldElement square()
    {
        int[] z = Nat256.create();
        SecP256K1Field.square(x, z);
        return new SecP256K1FieldElement(z);
    }

    public ECFieldElement invert()
    {
//        return new SecP256K1FieldElement(toBigInteger().modInverse(Q));
        int[] z = Nat256.create();
        SecP256K1Field.inv(x, z);
        return new SecP256K1FieldElement(z);
    }

    // D.1.4 91
    /**
     * return a sqrt root - the routine verifies that the calculation returns the right value - if
     * none exists it returns null.
     */
    public ECFieldElement sqrt()
    {
        /*
         * Raise this element to the exponent 2^254 - 2^30 - 2^7 - 2^6 - 2^5 - 2^4 - 2^2
         *
         * Breaking up the exponent's binary representation into "repunits", we get:
         * { 223 1s } { 1 0s } { 22 1s } { 4 0s } { 2 1s } { 2 0s }
         *
         * Therefore we need an addition chain containing 2, 22, 223 (the lengths of the repunits)
         * We use: 1, [2], 3, 6, 9, 11, [22], 44, 88, 176, 220, [223]
         */

        int[] x1 = this.x;
        if (Nat256.isZero(x1) || Nat256.isOne(x1))
        {
            return this;
        }

        int[] tt0 = Nat256.createExt();

        int[] x2 = Nat256.create();
        SecP256K1Field.square(x1, x2, tt0);
        SecP256K1Field.multiply(x2, x1, x2, tt0);
        int[] x3 = Nat256.create();
        SecP256K1Field.square(x2, x3, tt0);
        SecP256K1Field.multiply(x3, x1, x3, tt0);
        int[] x6 = Nat256.create();
        SecP256K1Field.squareN(x3, 3, x6, tt0);
        SecP256K1Field.multiply(x6, x3, x6, tt0);
        int[] x9 = x6;
        SecP256K1Field.squareN(x6, 3, x9, tt0);
        SecP256K1Field.multiply(x9, x3, x9, tt0);
        int[] x11 = x9;
        SecP256K1Field.squareN(x9, 2, x11, tt0);
        SecP256K1Field.multiply(x11, x2, x11, tt0);
        int[] x22 = Nat256.create();
        SecP256K1Field.squareN(x11, 11, x22, tt0);
        SecP256K1Field.multiply(x22, x11, x22, tt0);
        int[] x44 = x11;
        SecP256K1Field.squareN(x22, 22, x44, tt0);
        SecP256K1Field.multiply(x44, x22, x44, tt0);
        int[] x88 = Nat256.create();
        SecP256K1Field.squareN(x44, 44, x88, tt0);
        SecP256K1Field.multiply(x88, x44, x88, tt0);
        int[] x176 = Nat256.create();
        SecP256K1Field.squareN(x88, 88, x176, tt0);
        SecP256K1Field.multiply(x176, x88, x176, tt0);
        int[] x220 = x88;
        SecP256K1Field.squareN(x176, 44, x220, tt0);
        SecP256K1Field.multiply(x220, x44, x220, tt0);
        int[] x223 = x44;
        SecP256K1Field.squareN(x220, 3, x223, tt0);
        SecP256K1Field.multiply(x223, x3, x223, tt0);

        int[] t1 = x223;
        SecP256K1Field.squareN(t1, 23, t1, tt0);
        SecP256K1Field.multiply(t1, x22, t1, tt0);
        SecP256K1Field.squareN(t1, 6, t1, tt0);
        SecP256K1Field.multiply(t1, x2, t1, tt0);
        SecP256K1Field.squareN(t1, 2, t1, tt0);

        int[] t2 = x2;
        SecP256K1Field.square(t1, t2, tt0);

        return Nat256.eq(x1, t2) ? new SecP256K1FieldElement(t1) : null;
    }

    public boolean equals(Object other)
    {
        if (other == this)
        {
            return true;
        }

        if (!(other instanceof SecP256K1FieldElement))
        {
            return false;
        }

        SecP256K1FieldElement o = (SecP256K1FieldElement)other;
        return Nat256.eq(x, o.x);
    }

    public int hashCode()
    {
        return Q.hashCode() ^ Arrays.hashCode(x, 0, 8);
    }
}
