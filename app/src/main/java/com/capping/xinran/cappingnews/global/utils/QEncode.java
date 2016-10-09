package com.capping.xinran.cappingnews.global.utils;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Created by qixinh on 16/9/21.
 */
public class QEncode {
    /**
     * ================================================================
     * 一下是不可逆的加密 MD5和SHA
     * ================================================================
     */
    public static String encodeMD5(String data) throws Exception {

        return encodeMD5orSHA(data, "MD5");
    }

    public static String encodeSHA(String data) throws Exception {

        return encodeMD5orSHA(data, "SHA");
    }

    /**
     * @param data
     * @param type 分为 MD5和SHA两种
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encodeMD5orSHA(String data, String type) throws UnsupportedEncodingException {

        MessageDigest md = null;
        try {
            if (type == null || type.equals("")) {
                md = MessageDigest.getInstance("MD5");
            } else if (type.equalsIgnoreCase("MD5") || type.equalsIgnoreCase("SHA")) {
                md = MessageDigest.getInstance(type);
            } else {
                md = MessageDigest.getInstance("MD5");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        byte[] byteArray = data.getBytes("UTF-8");
        byte[] md5Bytes = md.digest(byteArray);

        return bytesToHex(md5Bytes);
    }

    public static String fileToMD5(File file) {

        return fileToMD5OrSHA(file, "MD5");
    }

    public static String fileToSHA(File file) {

        return fileToMD5OrSHA(file, "SHA");
    }

    /**
     * @param file 需要获取md5的文件
     * @param type 加密类型  MD5和SHA
     * @return
     */
    public static String fileToMD5OrSHA(File file, String type) {
        if (file == null) {
            return null;
        }
        if (file.exists() == false) {
            return null;
        }
        if (file.isFile() == false) {
            return null;
        }
        FileInputStream fis = null;
        try {
            //创建一个提供信息摘要算法的对象，初始化为md5算法对象
            MessageDigest md = null;
            if (type == null || type.equals("")) {
                md = MessageDigest.getInstance("MD5");
            } else if (type.equalsIgnoreCase("MD5") || type.equalsIgnoreCase("SHA")) {
                md = MessageDigest.getInstance(type);
            } else {
                md = MessageDigest.getInstance("MD5");
            }
            fis = new FileInputStream(file);
            byte[] buff = new byte[1024];
            int len = 0;
            while (true) {
                len = fis.read(buff, 0, buff.length);
                if (len == -1) {
                    break;
                }
                //每次循环读取一定的字节都更新
                md.update(buff, 0, len);
            }
            //关闭流
            fis.close();
            //返回md5字符串
            return bytesToHex(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把字节数组转成16进位制数
     *
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder md5str = new StringBuilder();
        //把数组每一字节换成16进制连成md5字符串
        for (int i = 0; i < bytes.length; i++) {

            int digital = ((int) bytes[i]) & 0xff;
            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString().toUpperCase();
    }

    /**
     * 加密和解密算法一样 执行一次加密，在调用一次就解密了...
     * 可以作为简单的加密算法也可以在MD5后再利用这个加密一次
     */
    public static String encodeOrDecodeString(String inStr) {

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }
/**
 * ================================================================
 * 一下是可逆的加密 DES和RSA
 *
 * 其中RSA算法是一种非对称密码算法，所谓非对称，就是指该算法需要一对密钥，使用其中一个加密，则需要用另一个才能解密。
 * ================================================================
 */
    /**
     * 加密
     *
     * @param datasource 需要加密的数据
     * @param password   加密key字节数一定要大于8
     * @return 返回密文
     */
    public static byte[] encodeDES(byte[] datasource, String password) {

        try {
            //创建一个可信任的随机数据源
            SecureRandom random = new SecureRandom();
            /** @throws InvalidKeyException
            password.getBytes()==if the length of the specified key data is less than 8.
             */
            DESKeySpec desKey = new DESKeySpec(password.getBytes());

            //创建一个密匙工厂，然后用它把DESKeySpec转换成SecretKey对象

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

            SecretKey securekey = keyFactory.generateSecret(desKey);

            //Cipher对象实际完成加密操作

            Cipher cipher = Cipher.getInstance("DES");

            //用密匙初始化Cipher对象

            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);

            //现在，获取数据并加密

            //正式执行加密操作

            return cipher.doFinal(datasource);

        } catch (Throwable e) {

            e.printStackTrace();

        }
        return null;

    }

    /**
     * 解密
     *
     * @param datasource 需要解密的数据
     * @param password   解密用的key要跟加密时的一样
     * @return 返回解密后的明文
     */
    public static byte[] decodeDES(byte[] datasource, String password) {
        try {
            SecureRandom radom = new SecureRandom();//产生一个安全的随机数据源
            DESKeySpec desKeySpec = new DESKeySpec(password.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, radom);
            return cipher.doFinal(datasource);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final String KEY_ALGORTHM = "RSA";//
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    public static final String PUBLIC_KEY = "RSAPublicKey";//公钥
    public static final String PRIVATE_KEY = "RSAPrivateKey";//私钥


    /**
     * 初始化密钥:
     * <p/>
     * RSA加密解密的实现，需要有一对公私密钥，公私密钥的初始化如下
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initKeyRSA() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORTHM);
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        //公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        //私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);

        return keyMap;

    }

    /**
     * 从map中取得公钥，并转化为String类型
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKeyRSA(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 从map取得私钥，并转化为String类型
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKeyRSA(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 用获取的私钥加密：数字签名技术是将摘要信息用发送者的私钥加密，与原文一起传送给接收者。
     * 接收者只有用发送者的公钥才能解密被加密的摘要信息，公对私  私对公  成对出现
     *
     * @param data 加密数据
     * @param key  密钥
     * @return
     * @throws Exception
     */
    public static byte[] encodeByPrivateKeyRSA(byte[] data, String key) throws Exception {
        //解密密钥
        byte[] keyBytes = decryptBASE64(key);
        //取私钥
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        //对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 用获取的公钥解密
     *
     * @param data 加密数据
     * @param key  密钥
     * @return
     * @throws Exception
     */
    public static byte[] decodeByPublicKeyRSA(byte[] data, String key) throws Exception {
        //对私钥解密
        byte[] keyBytes = decryptBASE64(key);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        //对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * 用获取的公钥加密
     *
     * @param data 加密数据
     * @param key  密钥
     * @return
     * @throws Exception
     */
    public static byte[] encodeByPublicKeyRSA(byte[] data, String key) throws Exception {
        //对公钥解密
        byte[] keyBytes = decryptBASE64(key);
        //取公钥
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        //对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * 用获取的私钥解密
     * * @param data    加密数据
     *
     * @param key 密钥
     * @return
     * @throws Exception
     */
    public static byte[] decodeByPrivateKeyRSA(byte[] data, String key) throws Exception {
        //对私钥解密
        byte[] keyBytes = decryptBASE64(key);

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 通过RSA加密解密算法，我们可以实现数字签名的功能。我们可以用私钥对信息生成数字签名，再用公钥来校验数字签名，
     * <p/>
     * 用私钥对信息生成数字签名
     *
     * @param data       //加密数据
     * @param privateKey //私钥
     * @return
     * @throws Exception
     */
    public static String signWithPrivateKeyRSA(byte[] data, String privateKey) throws Exception {
        //解密私钥
        byte[] keyBytes = decryptBASE64(privateKey);
        //构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        //指定加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        //取私钥匙对象
        PrivateKey privateKey2 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey2);
        signature.update(data);

        return encryptBASE64(signature.sign());
    }

    /**
     * 公钥校验数字签名
     *
     * @param data      加密数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return
     * @throws Exception
     */
    public static boolean verifyWithublicKeyRSA(byte[] data, String publicKey, String sign) throws Exception {
        //解密公钥
        byte[] keyBytes = decryptBASE64(publicKey);
        //构造X509EncodedKeySpec对象
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        //指定加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        //取公钥匙对象
        PublicKey publicKey2 = keyFactory.generatePublic(x509EncodedKeySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey2);
        signature.update(data);
        //验证签名是否正常
        return signature.verify(decryptBASE64(sign));

    }

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {

        return Base64.decode(key, Base64.DEFAULT);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return Base64.encodeToString(key, Base64.DEFAULT);
    }

}
