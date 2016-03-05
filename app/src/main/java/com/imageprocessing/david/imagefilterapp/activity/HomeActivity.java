package com.imageprocessing.david.imagefilterapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.imageprocessing.david.imagefilterapp.R;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button gallery;
    private Button filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        filter = (Button) findViewById(R.id.btnFilterImage);
        gallery = (Button) findViewById(R.id.btnGallery);

        filter.setOnClickListener(this);
        gallery.setOnClickListener(this);
        this.setTitle("Image Filter Application");

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id) {
            case R.id.btnFilterImage:
                Intent i = new Intent(this,FilterActivity.class);
                startActivity(i);
                break;
            case R.id.btnGallery:
                Intent j = new Intent(this,GalleryActivity.class);
                startActivity(j);
                break;
        }
    }
}
