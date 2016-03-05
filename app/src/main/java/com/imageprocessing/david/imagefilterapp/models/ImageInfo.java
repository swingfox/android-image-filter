package com.imageprocessing.david.imagefilterapp.models;

/**
 * Created by David on 02/07/2016.
 */
public class ImageInfo {
    private String name;
    private String filter;
    private String type;
    private int id;
    private byte[] image;

    public ImageInfo(){}
    public ImageInfo(int id,String name, String filter, String type, byte[] image){
        this.id = id;
        this.name = name;
        this.filter = filter;
        this.image = image;
        this.type = type;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type;}

    public void setId(int id) {
        this.id = id;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
