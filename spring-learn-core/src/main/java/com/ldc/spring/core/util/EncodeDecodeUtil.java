package com.ldc.spring.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * created by liudacheng on 2018/9/7.
 */
public class EncodeDecodeUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(EncodeDecodeUtil.class);
    /**
     * 向量iv，可增加加密算法的强度
     */
    private final static String IVPARAMETER = "8b991e525526bc73";

    /**
     * 加密的方式
     */
    private final static String AESPADDING = "AES/CBC/PKCS5Padding";

    /**
     * 内容编码类型
     */
    private final static String CHARESET = "utf-8";

    /**
     * 加密类型
     */
    private final static String SECRET_KEY = "AES";

    /**
     * 默认的32位密钥
     */
    private final static String DEFAULT_KEY_32 = "j8uoqR0AaDrBh$fpm9a1ViN44549nO&2";

    /**
     * 从KMS的获取32位密钥
     */
    private final static String KEY_32;

    static {
        //从KMS获取密钥
        KEY_32 = getKey32();
        /*AES 加密默认128位的key，这里改成256位的*/
        KeyStrengthPolicy.ensure();
    }

    /**
     * 加密字符串
     * @param key 加解密使用的key，应该为16/32位
     * @param source 待加密的字符串
     */
    public static String encryptWithAesAlgorithm(String key,String source) {
        try {
            Cipher cipher = Cipher.getInstance(AESPADDING);
            byte[] raw = key.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, SECRET_KEY);
            IvParameterSpec iv = new IvParameterSpec(IVPARAMETER.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(source.getBytes(CHARESET));
            return new BASE64Encoder().encode(encrypted);
        } catch (Exception e) {
            throw new RuntimeException(String.format("加密异常, 待加密串:%s", source), e);
        }
    }

    /**
     * 解密字符串
     * @param key 加解密使用的key，应该为16/32位
     * @param source 待解密的字符串
     */
    public static String decryptWithAesAlgorithm(String key,String source) {
        try {
            byte[] raw = key.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, SECRET_KEY);
            Cipher cipher = Cipher.getInstance(AESPADDING);
            IvParameterSpec iv = new IvParameterSpec(IVPARAMETER.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(source);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, CHARESET);
        } catch (Exception e) {
            throw new RuntimeException(String.format("解密异常, 待解密串:%s", source), e);
        }
    }

    /**
     * 加密字符串
     * @param source
     * @return
     */
    public static String encryptWithAesAlgorithm(String source) {
        try {
            Cipher cipher = Cipher.getInstance(AESPADDING);
            byte[] raw = KEY_32.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, SECRET_KEY);
            IvParameterSpec iv = new IvParameterSpec(IVPARAMETER.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(source.getBytes(CHARESET));
            return new BASE64Encoder().encode(encrypted);
        } catch (Exception e) {
            throw new RuntimeException(String.format("加密异常, 待加密串:%s", source), e);
        }
    }

    /**
     * 解密字符串
     * @param source
     * @return
     */
    public static String decryptWithAesAlgorithm(String source) {
        try {
            byte[] raw = KEY_32.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, SECRET_KEY);
            Cipher cipher = Cipher.getInstance(AESPADDING);
            IvParameterSpec iv = new IvParameterSpec(IVPARAMETER.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(source);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, CHARESET);
        } catch (Exception e) {
            throw new RuntimeException(String.format("解密异常, 待解密串:%s", source), e);
        }
    }

    private static  String getKey32(){
        String key32 = DEFAULT_KEY_32;
        try{
            // todo 后续可以从外部获取密钥
//            LOGGER.info("从KMS获取{}位密钥",key32.length());
        }catch(Exception e){
            return key32;
        }
        if(key32==null || key32.length()!=32){
            return key32;
        }
        return key32;
    }

}
