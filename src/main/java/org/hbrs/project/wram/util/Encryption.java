package org.hbrs.project.wram.util;

import org.apache.commons.codec.digest.DigestUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/** Diese Klasse dient der Verschlüsselung */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Encryption {
    public static String sha256(String value){
        return DigestUtils.sha256Hex(value);
    }
}

