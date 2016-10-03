package com.example.yasi27.final2;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by yasi27 on 30.9.2016.
 */
public class bellysize extends Activity {

    Button button4;
    ImageView photo;
    private static final int REQUEST_CAPTURE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bellysize);

        button4 = (Button) findViewById(R.id.button4);
        photo = (ImageView) findViewById(R.id.photo);

        //to check if android device has camera or not
        if (!hasCamera()){
            button4.setEnabled(false);
        }

    }

    public boolean hasCamera(){

//it will get the packagemanager and check if that feature is available or not
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    public void launchCamera(View v){
        Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cam, REQUEST_CAPTURE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap image = (Bitmap) extras.get("data");

            photo.setImageBitmap(image);

        }
    }
}


