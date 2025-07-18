/* GENERATED SOURCE. DO NOT MODIFY. */
package com.android.internal.org.bouncycastle.math.ec.custom.sec;

import com.android.internal.org.bouncycastle.math.ec.ECCurve;
import com.android.internal.org.bouncycastle.math.ec.ECFieldElement;
import com.android.internal.org.bouncycastle.math.ec.ECPoint;
import com.android.internal.org.bouncycastle.math.raw.Nat;
import com.android.internal.org.bouncycastle.math.raw.Nat256;

/**
 * @hide This class is not part of the Android public SDK API
 */
public class SecP256K1Point extends ECPoint.AbstractFp
{
    SecP256K1Point(ECCurve curve, ECFieldElement x, ECFieldElement y)
    {
        super(curve, x, y);
    }

    SecP256K1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs)
    {
        super(curve, x, y, zs);
    }

    protected ECPoint detach()
    {
        return new SecP256K1Point(null, getAffineXCoord(), getAffineYCoord());
    }

    // B.3 pg 62
    public ECPoint add(ECPoint b)
    {
        if (this.isInfinity())
        {
            return b;
        }
        if (b.isInfinity())
        {
            return this;
        }
        if (this == b)
        {
            return twice();
        }

        ECCurve curve = this.getCurve();

        SecP256K1FieldElement X1 = (SecP256K1FieldElement)this.x, Y1 = (SecP256K1FieldElement)this.y;
        SecP256K1FieldElement X2 = (SecP256K1FieldElement)b.getXCoord(), Y2 = (SecP256K1FieldElement)b.getYCoord();

        SecP256K1FieldElement Z1 = (SecP256K1FieldElement)this.zs[0];
        SecP256K1FieldElement Z2 = (SecP256K1FieldElement)b.getZCoord(0);

        int c;
        int[] tt0 = Nat256.createExt();
        int[] tt1 = Nat256.createExt();
        int[] t2 = Nat256.create();
        int[] t3 = Nat256.create();
        int[] t4 = Nat256.create();

        boolean Z1IsOne = Z1.isOne();
        int[] U2, S2;
        if (Z1IsOne)
        {
            U2 = X2.x;
            S2 = Y2.x;
        }
        else
        {
            S2 = t3;
            SecP256K1Field.square(Z1.x, S2, tt0);

            U2 = t2;
            SecP256K1Field.multiply(S2, X2.x, U2, tt0);

            SecP256K1Field.multiply(S2, Z1.x, S2, tt0);
            SecP256K1Field.multiply(S2, Y2.x, S2, tt0);
        }

        boolean Z2IsOne = Z2.isOne();
        int[] U1, S1;
        if (Z2IsOne)
        {
            U1 = X1.x;
            S1 = Y1.x;
        }
        else
        {
            S1 = t4;
            SecP256K1Field.square(Z2.x, S1, tt0);

            U1 = tt1;
            SecP256K1Field.multiply(S1, X1.x, U1, tt0);

            SecP256K1Field.multiply(S1, Z2.x, S1, tt0);
            SecP256K1Field.multiply(S1, Y1.x, S1, tt0);
        }

        int[] H = Nat256.create();
        SecP256K1Field.subtract(U1, U2, H);

        int[] R = t2;
        SecP256K1Field.subtract(S1, S2, R);

        // Check if b == this or b == -this
        if (Nat256.isZero(H))
        {
            if (Nat256.isZero(R))
            {
                // this == b, i.e. this must be doubled
                return this.twice();
            }

            // this == -b, i.e. the result is the point at infinity
            return curve.getInfinity();
        }

        int[] HSquared = t3;
        SecP256K1Field.square(H, HSquared, tt0);

        int[] G = Nat256.create();
        SecP256K1Field.multiply(HSquared, H, G, tt0);

        int[] V = t3;
        SecP256K1Field.multiply(HSquared, U1, V, tt0);

        SecP256K1Field.negate(G, G);
        Nat256.mul(S1, G, tt1);

        c = Nat256.addBothTo(V, V, G);
        SecP256K1Field.reduce32(c, G);

        SecP256K1FieldElement X3 = new SecP256K1FieldElement(t4);
        SecP256K1Field.square(R, X3.x, tt0);
        SecP256K1Field.subtract(X3.x, G, X3.x);

        SecP256K1FieldElement Y3 = new SecP256K1FieldElement(G);
        SecP256K1Field.subtract(V, X3.x, Y3.x);
        SecP256K1Field.multiplyAddToExt(Y3.x, R, tt1);
        SecP256K1Field.reduce(tt1, Y3.x);

        SecP256K1FieldElement Z3 = new SecP256K1FieldElement(H);
        if (!Z1IsOne)
        {
            SecP256K1Field.multiply(Z3.x, Z1.x, Z3.x, tt0);
        }
        if (!Z2IsOne)
        {
            SecP256K1Field.multiply(Z3.x, Z2.x, Z3.x, tt0);
        }

        ECFieldElement[] zs = new ECFieldElement[] { Z3 };

        return new SecP256K1Point(curve, X3, Y3, zs);
    }

    // B.3 pg 62
    public ECPoint twice()
    {
        if (this.isInfinity())
        {
            return this;
        }

        ECCurve curve = this.getCurve();

        SecP256K1FieldElement Y1 = (SecP256K1FieldElement)this.y;
        if (Y1.isZero())
        {
            return curve.getInfinity();
        }

        SecP256K1FieldElement X1 = (SecP256K1FieldElement)this.x, Z1 = (SecP256K1FieldElement)this.zs[0];

        int c;
        int[] tt0 = Nat256.createExt();

        int[] Y1Squared = Nat256.create();
        SecP256K1Field.square(Y1.x, Y1Squared, tt0);

        int[] T = Nat256.create();
        SecP256K1Field.square(Y1Squared, T, tt0);

        int[] M = Nat256.create();
        SecP256K1Field.square(X1.x, M, tt0);
        c = Nat256.addBothTo(M, M, M);
        SecP256K1Field.reduce32(c, M);

        int[] S = Y1Squared;
        SecP256K1Field.multiply(Y1Squared, X1.x, S, tt0);
        c = Nat.shiftUpBits(8, S, 2, 0);
        SecP256K1Field.reduce32(c, S);

        int[] t1 = Nat256.create();
        c = Nat.shiftUpBits(8, T, 3, 0, t1);
        SecP256K1Field.reduce32(c, t1);

        SecP256K1FieldElement X3 = new SecP256K1FieldElement(T);
        SecP256K1Field.square(M, X3.x, tt0);
        SecP256K1Field.subtract(X3.x, S, X3.x);
        SecP256K1Field.subtract(X3.x, S, X3.x);

        SecP256K1FieldElement Y3 = new SecP256K1FieldElement(S);
        SecP256K1Field.subtract(S, X3.x, Y3.x);
        SecP256K1Field.multiply(Y3.x, M, Y3.x, tt0);
        SecP256K1Field.subtract(Y3.x, t1, Y3.x);

        SecP256K1FieldElement Z3 = new SecP256K1FieldElement(M);
        SecP256K1Field.twice(Y1.x, Z3.x);
        if (!Z1.isOne())
        {
            SecP256K1Field.multiply(Z3.x, Z1.x, Z3.x, tt0);
        }

        return new SecP256K1Point(curve, X3, Y3, new ECFieldElement[] { Z3 });
    }

    public ECPoint twicePlus(ECPoint b)
    {
        if (this == b)
        {
            return threeTimes();
        }
        if (this.isInfinity())
        {
            return b;
        }
        if (b.isInfinity())
        {
            return twice();
        }

        ECFieldElement Y1 = this.y;
        if (Y1.isZero())
        {
            return b;
        }

        return twice().add(b);
    }

    public ECPoint threeTimes()
    {
        if (this.isInfinity() || this.y.isZero())
        {
            return this;
        }

        // NOTE: Be careful about recursions between twicePlus and threeTimes
        return twice().add(this);
    }

    public ECPoint negate()
    {
        if (this.isInfinity())
        {
            return this;
        }

        return new SecP256K1Point(curve, this.x, this.y.negate(), this.zs);
    }
}
