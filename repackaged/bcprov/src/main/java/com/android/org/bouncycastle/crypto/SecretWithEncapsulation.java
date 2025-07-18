/* GENERATED SOURCE. DO NOT MODIFY. */
package com.android.org.bouncycastle.crypto;

import javax.security.auth.Destroyable;

/**
 * Interface describing secret with encapsulation details.
 * @hide This class is not part of the Android public SDK API
 */
public interface SecretWithEncapsulation
    extends Destroyable
{
    /**
     * Return the secret associated with the encapsulation.
     *
     * @return the secret the encapsulation is for.
     */
    byte[] getSecret();

    /**
     * Return the data that carries the secret in its encapsulated form.
     *
     * @return the encapsulation of the secret.
     */
    byte[] getEncapsulation();
}
