package com.imageprocessing.david.imagefilterapp.activity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.imageprocessing.david.imagefilterapp.models.ImageInfo;

import java.util.List;

import utils.ImageArrayAdapter;
import utils.database.GalleryAdapter;

/**
 * Created by David on 02/07/2016.
 */
public class GalleryActivity extends ListActivity implements AdapterView.OnItemLongClickListener {
    private GalleryAdapter galleryAdapter;
    private List<ImageInfo> list;
    private ImageInfo selectedImage;
    private ListAdapter adapter;
    private Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        galleryAdapter = new GalleryAdapter(this);
        galleryAdapter.open();
        list = galleryAdapter.fetchAllImages();
        galleryAdapter.close();
        String[] names = new String[list.size()];
        for(int i = 0;i < list.size();i++) {
            names[i] = list.get(i).getName();
        }
        adapter = new ImageArrayAdapter(this, names);
        setListAdapter(adapter);
        this.getListView().setOnItemLongClickListener(this);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String selectedValue = (String) getListAdapter().getItem(position);
        Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();
        Bundle b = new Bundle();
        galleryAdapter.open();
        selectedImage = (ImageInfo) galleryAdapter.fetchSingleImage(list.get(position).getId());
        galleryAdapter.close();
        b.putInt("_id", selectedImage.getId());
        b.putString("name", selectedImage.getName());
        b.putString("filter", selectedImage.getFilter());
        b.putString("type", selectedImage.getType());
        b.putByteArray("image", selectedImage.getImage());
        Intent i = new Intent(this,FilterActivity.class);
        i.putExtras(b);
        startActivity(i);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        String selectedValue = (String) getListAdapter().getItem(position);
        final int pos = position;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Delete item?");
        alertDialogBuilder
                .setMessage("Are you sure you want to delete " + selectedValue + " ?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        galleryAdapter.open();
                        ImageInfo selected = list.get(pos);
                        list.remove(pos);
                        String[] names = new String[list.size()];
                        for(int i = 0;i < list.size();i++) {
                            names[i] = list.get(i).getName();
                        }
                        adapter = new ImageArrayAdapter(context, names);
                        setListAdapter(adapter);
                        galleryAdapter.deleteImage(selected.getId());
                        galleryAdapter.close();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return false;
    }
}
