package com.example.lagarwal.lock_library;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptPassword {

    private static final String PREFS_NAME = "AnDeShPr",
            PASSPHRASE = "PassPhrase",
            IVECTOR = "IVect",
            SALT="Salt",
            ENCPASS= "EncPass";

    Cipher cipher;
    SharedPreferences settingsSharedPref;
    String strPassPhrase, strIV, strSalt, strEncPass;
    int iterationCount = 1024;
    //int iterationCount = 100;
    int keyStrength = 256;
    SecretKey secretKySpec;
    //String passPhrase="ABCDEFGHI";
    IvParameterSpec ivParmSpec;

    EncryptPassword(Context context) throws Exception{
        settingsSharedPref =  context.getSharedPreferences(PREFS_NAME, 0);
        strPassPhrase= settingsSharedPref.getString(PASSPHRASE, "");
        strSalt= settingsSharedPref.getString(SALT, "");
        strIV = settingsSharedPref.getString(IVECTOR, "");
        SharedPreferences.Editor editSharedPref = settingsSharedPref.edit();

        if (strPassPhrase.length() == 0) {
           char[] CHARSET_AZ_09 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
            strPassPhrase= getPassPhrase(CHARSET_AZ_09, 6);
            editSharedPref.putString(PASSPHRASE, strPassPhrase);
        }

        if (strIV.length() == 0) {
            strIV=Base64.encodeToString(getIV(), 0);
            editSharedPref.putString(IVECTOR, strIV);
        }

        if (strSalt.length() == 0) {
            strSalt= Base64.encodeToString(getSalt(), 0);
            editSharedPref.putString(SALT, strSalt);
        }

        editSharedPref.commit();

        //Log.e("strPassPhrase", strPassPhrase);
        //Log.e("strIV", strIV);
        //Log.e("strSalt", strSalt);

        ivParmSpec = new IvParameterSpec(Base64.decode(strIV, 0));

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(strPassPhrase.toCharArray(), Base64.decode(strSalt, 0), iterationCount, keyStrength);
        SecretKey tmp = factory.generateSecret(spec);
        secretKySpec = new SecretKeySpec(tmp.getEncoded(), "AES");
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    }

    public Boolean isPasswordEqual(String encryptedUsePassword){
        Log.e("EnteredPass", encryptedUsePassword);
        Log.e("Old Pass", strEncPass);
        if (encryptedUsePassword.equals(strEncPass)){
            return true;
        }else{
            return false;
        }
    }

    public Boolean encrypt(String dataToEncrypt, StringBuilder strbDataEncrypted) throws Exception {

        strEncPass= settingsSharedPref.getString(strEncPass, "");

        strbDataEncrypted.setLength(0);

        // If the input string is null, no encryption is performed.
        if (dataToEncrypt.length() > 0) {
            byte[] utf8EncryptedData;
            cipher.init(Cipher.ENCRYPT_MODE, secretKySpec, ivParmSpec);
            utf8EncryptedData = cipher.doFinal(dataToEncrypt.getBytes("UTF-8"));
            strbDataEncrypted.append(Base64.encodeToString(utf8EncryptedData, 0));
        }

        if(strEncPass.length()==0){
            strEncPass= strbDataEncrypted.toString();
            SharedPreferences.Editor editSharedPref = settingsSharedPref.edit();
            editSharedPref.putString(ENCPASS, strEncPass).commit();
        }
        return true;
    } // encrypt

    public Boolean decrypt(String dataToDecrypt, StringBuilder strbDataDecrypted) throws Exception {

        strbDataDecrypted.setLength(0);

        // If the input string is null, no decryption is performed.
        if (dataToDecrypt.length() > 0) {
            byte[] decryptedData;
            byte[] utf8;
            String strOut;

            cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKySpec, ivParmSpec);
            decryptedData = Base64.decode(dataToDecrypt, 0);
            utf8 = cipher.doFinal(decryptedData);
            strOut = new String(utf8, "UTF8");

            strbDataDecrypted.append(strOut);
        }

        return true;
    } // decrypt

    public static String getPassPhrase(char[] characterSet, int length) {
        Random random = new SecureRandom();
        char[] result = new char[length];
        for (int i = 0; i < result.length; i++) {
            // picks a random index out of character set > random character
            int randomCharIndex = random.nextInt(characterSet.length);
            result[i] = characterSet[randomCharIndex];
        }
        return new String(result);
    }

    private static byte[] getIV() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] iv = new byte[16];
        sr.nextBytes(iv);
        return iv;
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[32];
        sr.nextBytes(salt);
        return salt;
    }
}

