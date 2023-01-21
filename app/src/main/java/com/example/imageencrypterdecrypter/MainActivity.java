package com.example.imageencrypterdecrypter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.imageencrypterdecrypter.Utils.MyEncrypt;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME_ENC ="ImageEncrypted" ;
    private static final String FILE_NAME_DEC = "ImageDecrypted.jpeg";
    Button encode, decode;
    ImageView imageView;
    File myDir;
    static Key key;
    static byte[] encrypted;
    private String my_key="EtZqHXzvXOtYXR5c";
    private String  my_spec_key="qakQQrpZ0DQnDeDo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        encode = findViewById(R.id.btEnc);
        decode = findViewById(R.id.btdnc);
        imageView=(ImageView) findViewById(R.id.imageView);
//        myDir=new File(Environment.getExternalStorageDirectory().toString()+"/saved_images");
        myDir= new File(getExternalFilesDir(null)+"/"+"My_Images");
        if(!myDir.exists()){
            myDir.mkdir();
        }
        Dexter.withActivity(this).withPermissions(new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }).withListener(new MultiplePermissionsListener() {


            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                encode.setEnabled(true);
                decode.setEnabled(true);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                Toast.makeText(MainActivity.this,"You must enable permission",Toast.LENGTH_SHORT).show();
            }
        }).check();

        decode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    File outputFileDec=new File(myDir,FILE_NAME_DEC);
                    File encFile=new File(myDir,FILE_NAME_ENC);
                    try{
                        MyEncrypt.decrypttoFile(my_key,my_spec_key,new FileInputStream(encFile),new FileOutputStream(outputFileDec));
                        {
                            MyEncrypt.decrypttoFile(my_key,my_spec_key,new FileInputStream(encFile),new FileOutputStream(outputFileDec));

                            imageView.setImageURI(Uri.fromFile(outputFileDec));
                            Toast.makeText(MainActivity.this,"Decrypted Successfully",Toast.LENGTH_SHORT).show();

                        }
                        imageView.setImageURI(Uri.fromFile(outputFileDec));
                        Toast.makeText(MainActivity.this,"Decrypted Successfully",Toast.LENGTH_SHORT).show();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

            }
        });


        encode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Drawable drawable=ContextCompat.getDrawable(MainActivity.this,R.drawable.image);
                BitmapDrawable bitmapDrawable=(BitmapDrawable) drawable;
                Bitmap bitmap=bitmapDrawable.getBitmap();
                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                InputStream is=new ByteArrayInputStream(stream.toByteArray());

                File outputFileEnc=new File(myDir,FILE_NAME_ENC);
                try{
//                    Toast.makeText(MainActivity.this,"Encrypted Successfully",Toast.LENGTH_SHORT).show();
                    MyEncrypt.encrypttoFile(my_key,my_spec_key,is,new FileOutputStream(outputFileEnc));
                    Toast.makeText(MainActivity.this,"Encrypted Successfully",Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }
}


