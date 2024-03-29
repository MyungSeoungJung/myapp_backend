package com.msj.myapp.auth.util;

//gradle 외부라이브러리 추가및 동기화
import at.favre.lib.crypto.bcrypt.BCrypt;

public class Hash {
    public String createHash(String cipherText) {
        // 'hashToString': Salt와 함께 해시를 생성
        // hash create 할때는 salt를 랜덤으로 생성해서 저장
        return BCrypt
                .withDefaults()
                .hashToString(12, cipherText.toCharArray());
    }

    // 문자열을 받아서 salt와 함께 hash가 맞는지 확인
    // ciphertext(암호화안된 문자열)
    // plaintext(구조가없는 문자열)

    // hash: $버전$라운드횟수$22바이트salt+해시문자열
    public boolean verifyHash(String ciphertext, String hash) {
        // hash를 verifying 할 때는 이미 있는 salt값으로
        // ciphertext를 결합하여 hash와 맞는지 확인
        return BCrypt
                .verifyer()
                .verify(ciphertext.toCharArray(), hash)
                .verified;
    }
}