package com.wangzhe.common;

import java.io.*;
import java.math.*;
import java.security.*;
import java.security.interfaces.*;
import java.security.spec.*;

import javax.crypto.*;


public class RSA
{
    public int KEY_SIZE = 512;
    //65537
    public byte[] pubPubExpBytes = new BigInteger("65537").toByteArray();

    public byte[] pubModBytes;

    public byte[] priModBytes;

    public byte[] priPriExpBytes;

    public static RSAPublicKey pubKey = null;
    public static RSAPrivateKey priKey = null;


    /**
     * 生成密钥对

     * @return KeyPair

     * @throws Exception

     */

    public KeyPair generateKeyPair() throws Exception
    {

        try
        {

            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");

            keyPairGen.initialize(KEY_SIZE, new SecureRandom());

            KeyPair keyPair = keyPairGen.genKeyPair();

            return keyPair;

        }
        catch (Exception e)
        {

            throw new Exception(e.getMessage());

        }

    }

    public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) throws Exception
    {

        KeyFactory keyFac = null;

        try
        {

            keyFac = KeyFactory.getInstance("RSA");

        }
        catch (NoSuchAlgorithmException ex)
        {

            throw new Exception(ex.getMessage());

        }

        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(publicExponent));

        try
        {

            return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);

        }
        catch (InvalidKeySpecException ex)
        {

            throw new Exception(ex.getMessage());

        }

    }

    public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) throws Exception
    {

        KeyFactory keyFac = null;

        try
        {

            keyFac = KeyFactory.getInstance("RSA");

        }
        catch (NoSuchAlgorithmException ex)
        {

            throw new Exception(ex.getMessage());

        }

        RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus), new BigInteger(privateExponent));

        try
        {

            return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);

        }
        catch (InvalidKeySpecException ex)
        {

            throw new Exception(ex.getMessage());

        }

    }

    /**
     * 加密
     * @param data 待加密的明文数据
     * @return 加密后的数据
     * @throws Exception
     */
    public byte[] encrypt(byte[] data) throws Exception
    {
        Cipher cipher = Cipher.getInstance("RSA");

        cipher.init(Cipher.ENCRYPT_MODE, pubKey);

        return cipher.doFinal(data);
    }

    /**
     * 解密
     * @param raw 已经加密的数据
     * @return 解密后的明文
     * @throws Exception
     */
    public byte[] decrypt(byte[] raw) throws Exception
    {

        try
        {

            Cipher cipher = Cipher.getInstance("RSA");

            cipher.init(Cipher.DECRYPT_MODE, priKey);

            return cipher.doFinal(raw);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;

    }

}
