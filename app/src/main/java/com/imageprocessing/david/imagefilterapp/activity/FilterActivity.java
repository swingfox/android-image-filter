package com.imageprocessing.david.imagefilterapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpicker.Config;
import com.gun0912.tedpicker.ImagePickerActivity;
import com.imageprocessing.david.imagefilterapp.R;
import com.imageprocessing.david.imagefilterapp.models.ImageInfo;

import java.io.IOException;
import java.util.ArrayList;

import utils.ImageArrayAdapter;
import utils.ImageUtils;
import utils.database.GalleryAdapter;
import utils.imageprocessing.ImageFilters;

/**
 * Created by David on 02/06/2016.
 */
public class FilterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int INTENT_REQUEST_GET_IMAGES = 13;
    private ImageView imageView;
    private ListView listFilters;
    final Context context = this;

    ArrayList<Uri> image_uris = new ArrayList<Uri>();
    private String[] list = {"Original","Greyscale","Gamma","Engrave","BlackFilter", "Gaussian","Emboss", "Flip","Invert","Brightness","RoundCorner","FleaEffect","BlackFilter","HueFilter"};
    private String IMG_TYPE = ".jpg";

    private Bitmap process;
    private Bitmap original;
    private ImageButton save;
    private String currentFilter;
    private ImageInfo imgInfo;
    private boolean update;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        setupWidget();
        save.setOnClickListener(this);

        Bundle b = this.getIntent().getExtras();
        if(b!=null){
            imgInfo = new ImageInfo();
            imgInfo.setId(b.getInt("_id"));
            imgInfo.setImage(b.getByteArray("image"));
            imgInfo.setName(b.getString("name"));
            imgInfo.setFilter(b.getString("filter"));
            imgInfo.setType(b.getString("type"));
            original = ImageUtils.getImage(imgInfo.getImage());
            imageView.setImageBitmap(original);
            save.setEnabled(true);
            update = true;
        }
        else{
            Bitmap m = BitmapFactory.decodeResource(this.getResources(),R.drawable.img_filter);
            imageView.setImageBitmap(m);
            Toast.makeText(context,"Bundle is NULL!", Toast.LENGTH_LONG).show();
            update = false;
        }

        listFilters.setAdapter(new ImageArrayAdapter(this,list));
        listFilters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(context,((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                if (original != null) {
                    switch (position) {
                        case 0:
                            process = original;
                            break;
                        case 1:
                            process = ImageFilters.doGreyscale(original);
                            break;
                        case 2:
                            process = ImageFilters.doGamma(original, 1.8, 1.8, 1.8);
                            break;
                        case 3:
                            process = ImageFilters.engrave(original);
                            break;
                        case 4:
                            process = ImageFilters.applyBlackFilter(original);
                            break;
                        case 5:
                            process = ImageFilters.applyGaussianBlur(original);
                            break;
                        case 6:
                            process = ImageFilters.emboss(original);
                            break;
                        case 7:
                            process = ImageFilters.flip(original, ImageFilters.FLIP_HORIZONTAL);
                            break;
                        case 8:
                            process = ImageFilters.doInvert(original);
                            break;
                        case 9:
                            process = ImageFilters.doBrightness(original, 50);
                            break;
                        case 10:
                            process = ImageFilters.roundCorner(original, 30);
                            break;
                        case 11:
                            process = ImageFilters.applyFleaEffect(original);
                            break;
                        case 12:
                            process = ImageFilters.applyBlackFilter(original);
                            break;
                        case 13:
                            process = ImageFilters.applyHueFilter(original,50);
                            break;

                    }
                    imageView.setImageBitmap(process);
                    currentFilter = list[position];
                }
            }
        });
    }

    public void setupWidget(){
        this.imageView = (ImageView)this.findViewById(R.id.imageView);
        ImageButton capture = (ImageButton) this.findViewById(R.id.btnCapture);
        save = (ImageButton) findViewById(R.id.btnSave);
        save.setEnabled(false);
        listFilters = (ListView) findViewById(R.id.listFilters);
        capture.setOnClickListener(this);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INTENT_REQUEST_GET_IMAGES && resultCode == Activity.RESULT_OK ) {
             image_uris = data.getParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);
             if (image_uris != null) {
                 imageView.setImageURI(image_uris.get(0));
                 Bitmap bmp = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                 try {
                     original = MediaStore.Images.Media.getBitmap(this.getContentResolver(),Uri.parse("file://" + image_uris.get(0)));
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.btnCapture:
                Config config = new Config();
                config.setCameraHeight(R.dimen.app_camera_height);
                config.setSelectionMin(1);
                config.setSelectionLimit(1);
                ImagePickerActivity.setConfig(config);
                Intent intent  = new Intent(this, ImagePickerActivity.class);
                startActivityForResult(intent,INTENT_REQUEST_GET_IMAGES);
                save.setEnabled(true);
                break;

            case R.id.btnSave:
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.dialog, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                final Bitmap bmp = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                final GalleryAdapter galleryAdapter = new GalleryAdapter(this);
                final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);

                alertDialogBuilder.setView(promptsView);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        String name = userInput.getText().toString();
                                        galleryAdapter.open();
                                        if(false==update) {
                                            galleryAdapter.insertImage(name, currentFilter, IMG_TYPE, ImageUtils.getBytes(bmp));
                                            Toast.makeText(context,"Image Saved to Database!",Toast.LENGTH_LONG).show();
                                        }else {
                                            galleryAdapter.updateImage(imgInfo.getId(), name, currentFilter, IMG_TYPE, ImageUtils.getBytes(bmp));
                                            Toast.makeText(context,"Image Updated to Database!",Toast.LENGTH_LONG).show();
                                            Intent i = new Intent(context,HomeActivity.class);
                                            startActivity(i);
                                        }
                                        galleryAdapter.close();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
        }
    }
}
