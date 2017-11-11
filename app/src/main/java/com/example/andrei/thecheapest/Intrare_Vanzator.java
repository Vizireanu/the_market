package com.example.andrei.thecheapest;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Intrare_Vanzator extends AppCompatActivity {

    EditText edtName, edtPrice;
    Button btnChoose, btnAdd, btnList;

    String imagePath ;
    Bitmap imageBitmap;
    ImageView imageView;
    int tag=1;

    final int REQUEST_CODE_GALLERY=999;
    int CAMERA_PIC_REQUEST=1;
    public static final int GPS_REQUEST = 1;
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intrare__vanzator);


        DBManager dbManager = new DBManager(this);




        init();


        testGPS();

        if (AppStatus.getInstance(this).isOnline())
        {

        }
        else
        {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            // Setting Dialog Title
            alertDialog.setTitle("INTERNET OR WI-FI settings");

            // Setting Dialog Message
            alertDialog.setMessage("Internet/Wi-Fi is not enabled. Do you want to go to settings menu?");

            // On pressing Settings button
            alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {
                    Intent settingsIntent = new Intent(Settings.ACTION_SETTINGS);
                    startActivity(settingsIntent);
                }
            });

            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            alertDialog.show();
        }



    }

    public void addProduct(View v){

        ContentValues values = new ContentValues();
        values.put(DBManager.ColNume,edtName.getText().toString());
        //values.put(DBManager.C)

    }

    public void takePicture(View v){
        CheckUserPermsions();
    }


    void CheckUserPermsions(){
        if ( Build.VERSION.SDK_INT >= 23){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED  ){
                requestPermissions(new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return ;
            }
        }

        TakePicture();// init the contact list

    }
    //get acces to location permsion
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    TakePicture();// init the contact list
                } else {
                    // Permission Denied
                    Toast.makeText(this,"your message" , Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == GPS_REQUEST)
        {
            testGPS();
        }
    }
    private String pictureImagePath = "";
    void TakePicture()
    {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss.SSS").format(new Date());
        String imageFileName = timeStamp + ".jpg";
        File storageDir = Environment.getExternalStoragePublicDirectory("/com.example.andrei.thecheapest/imagini_produse");
        if(!storageDir.exists()){
            storageDir.mkdir();
        }

        pictureImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
        File file = new File(pictureImagePath);
        //Uri outputFileUri = Uri.fromFile(file);
        Uri photoURI = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".my.package.name.provider", file);

        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(intent,tag);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==tag && resultCode==RESULT_OK)
        {
            /*Bundle b=data.getExtras();
            Bitmap img= (Bitmap)b.get("data");
            imageView.setImageBitmap(img);*/

            File imgFile = new  File(pictureImagePath);
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                //ImageView myImage = (ImageView) findViewById(R.id.imageviewTest);
                try {
                    ExifInterface exif = new ExifInterface(imgFile.getAbsolutePath());
                    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);


                    Matrix matrix = new Matrix();

                    if(orientation==1)
                        matrix.postRotate(0);
                    else if(orientation==3)
                        matrix.postRotate(180);
                    else if(orientation==6)
                        matrix.postRotate(90);
                    else if(orientation==8)
                        matrix.postRotate(270);

                    Bitmap rotatedBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight(), matrix, true);
                    imageView.setImageBitmap(rotatedBitmap); Log.v("CALCULATOR", "index= " + orientation);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        if(requestCode == 2){
            if(resultCode == RESULT_OK){
                String lat = data.getStringExtra("Lat");
                String lng = data.getStringExtra("Long");

                System.out.println(lat + "   " + lng);
            }
        }

    }

    private void testGPS()
    {

        gps = new GPSTracker(this);
        if (gps.canGetLocation())
        {
        }
        else
        {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            // Setting Dialog Title
            alertDialog.setTitle("GPS settings");

            // Setting Dialog Message
            alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

            // On pressing Settings button
            alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    //    Context ct=ac;
                    startActivityForResult(intent, GPS_REQUEST);//.startActivity(intent);
                }
            });

            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            alertDialog.show();
        }

    }


    private byte[] imageViewToByte(ImageView image)
    {
        Bitmap bitmap=((BitmapDrawable) image.getDrawable()).getBitmap();

        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray=stream.toByteArray();

        return byteArray;
    }


    private void init()
    {
        edtName=(EditText) findViewById(R.id.edtName);
        edtPrice=(EditText) findViewById(R.id.edtPrice);
        btnChoose=(Button) findViewById(R.id.btnChoose);
        btnAdd=(Button) findViewById(R.id.btnAdd);
        imageView=(ImageView) findViewById(R.id.imageView);
    }
    public void setLocation (View v)
    {
        Intent i = new Intent(getBaseContext(), SetLocation.class);
        startActivityForResult(i, 2);
    }

}