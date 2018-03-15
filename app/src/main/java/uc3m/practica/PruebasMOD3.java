package uc3m.practica;

import android.util.Log;

import javax.crypto.SecretKey;

/**
 * Created by Jaime-4 on 15/03/2018.
 */

public class PruebasMOD3 {
    SecretKey key;


    public void ejecucion(){
        String pass="pruebas";
        byte [] salt=Crypto.generateSalt();
        byte [] iv=Crypto.generateIv(32);
        key=deriveKey(pass,salt); // con esto se supone que tendremos la clave de cifrado. Hay que seguir desde aqui.
    }




    String getRawKey() {
        if (key == null) {
            return null;
        }
        return Crypto.toHex(key.getEncoded());
    }
    public SecretKey deriveKey(String password, byte[] salt) {
        return Crypto.deriveKeyPbkdf2(salt, password);
    }
    public String encrypt(String plaintext, String password) {
        byte[] salt = Crypto.generateSalt();
        key = deriveKey(password, salt);
        Log.d("Crypto", "Generated key: " + getRawKey());
        return Crypto.encrypt(plaintext, key, salt);
    }
    public String decrypt(String ciphertext, String password) {
        return Crypto.decryptPbkdf2(ciphertext, password);
    }
}
