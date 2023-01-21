package com.example.imageencrypterdecrypter.Utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.imageencrypterdecrypter.MainActivity;
import com.example.imageencrypterdecrypter.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MyEncrypt {

    private static  final int READ_WRITE_BLOCK_BUFFER=1024;
    private static  final String ALGO_IMAGE_ENCRYPTOR="AES/CBC/PKCS5Padding";
    private static  final String ALGO_SECRET_KEY="AES";


    public static void encrypttoFile(String keystr,String specstr,InputStream in,OutputStream out) throws Exception{

        try{

            IvParameterSpec iv=new IvParameterSpec(specstr.getBytes("UTF-8"));
            SecretKeySpec keyspec=new SecretKeySpec(keystr.getBytes("UTF-8"),ALGO_SECRET_KEY);

            Cipher cipher=Cipher.getInstance(ALGO_IMAGE_ENCRYPTOR);
            cipher.init(Cipher.ENCRYPT_MODE,keyspec,iv);
            out=new CipherOutputStream(out,cipher);
            int count=0;

            byte[] buffer=new byte[READ_WRITE_BLOCK_BUFFER];
            while((count=in.read(buffer))>=0)
                out.write(buffer,0,count);

        }
        finally {
            out.close();
        }


    }
    public static void decrypttoFile(String keystr,String specstr,InputStream in,OutputStream out) throws Exception{

        try{
            IvParameterSpec iv=new IvParameterSpec(specstr.getBytes("UTF-8"));
            SecretKeySpec keyspec=new SecretKeySpec(keystr.getBytes("UTF-8"),ALGO_SECRET_KEY);

            Cipher cipher=Cipher.getInstance(ALGO_IMAGE_ENCRYPTOR);
            cipher.init(Cipher.DECRYPT_MODE,keyspec,iv);
            out=new CipherOutputStream(out,cipher);
            int count=0;

            byte[] buffer=new byte[READ_WRITE_BLOCK_BUFFER];
            while((count=in.read(buffer))>=0){
                out.write(buffer,0,count);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

