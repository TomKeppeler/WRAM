/**
 * @outhor Tom
 * @vision 1.0
 * @Zuletzt bearbeiret: 18.11.22 by Salah
 */
package org.hbrs.project.wram.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

/** Diese Klasse dient der Verschlüsselung */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Encryption {

    /**
     * Verschlüssele daten
     * @param value
     * @return
     */
    public static String sha256(String value) {
        return DigestUtils.sha256Hex(value);
    }
}

