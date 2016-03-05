package utils.database;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import com.imageprocessing.david.imagefilterapp.models.ImageInfo;


public class GalleryAdapter {
    public static final String TBL_INFO = "tbl_image";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_FILTER = "filter";
    public static final String COL_TYPE = "type";
    public static final String COL_IMAGE = "image";

    private static final String[] PROJECTION_ALL = new String[] {
            COL_ID, COL_NAME, COL_FILTER, COL_TYPE, COL_IMAGE
    };

    private Context mContext;
    private SQLiteDatabase mDb;
    private GalleryHelper mDbHelper;

    public GalleryAdapter(Context c) {
        mContext = c;
    }

    public void open() throws SQLException {
        mDbHelper = new GalleryHelper(mContext);
        mDb = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public long insertImage(String name, String filter, String type, byte[] image) {
        return mDb.insert(TBL_INFO, null, createContentValues(name, filter, type, image));
    }

    public long updateImage(int id, String name, String filter, String type, byte[] image) {
        return mDb.update(TBL_INFO, createContentValues(name, filter, type, image), COL_ID + "=" + id, null);
    }

    public long deleteImage(int id) {
        return mDb.delete(TBL_INFO, COL_ID + "=" + id, null);
    }

    public List<ImageInfo> fetchAllImages() {
        Cursor queryCursor = null;
        try {
            queryCursor = mDb.query(TBL_INFO, PROJECTION_ALL, null, null, null, null, null);
        }
        catch(SQLiteException e){
            Toast.makeText(mContext,"SQL EXCEPTION: " +e.getMessage(),Toast.LENGTH_LONG).show();
        }
        if(queryCursor == null) {
            Log.d("fetch ", "UserDbAdapter.fetchAllImage(): queryCursor = null ");
            return null;
        }
        List<ImageInfo> listImage = new ArrayList<ImageInfo>();
        if(queryCursor.moveToFirst()) {
            while (queryCursor.isAfterLast() == false) {
                listImage.add(new ImageInfo(
                                queryCursor.getInt(queryCursor.getColumnIndexOrThrow(COL_ID)),
                                queryCursor.getString(queryCursor.getColumnIndexOrThrow(COL_NAME)),
                                queryCursor.getString(queryCursor.getColumnIndexOrThrow(COL_FILTER)),
                                queryCursor.getString(queryCursor.getColumnIndexOrThrow(COL_TYPE)),
                                queryCursor.getBlob(queryCursor.getColumnIndexOrThrow(COL_IMAGE))
                        )
                );
                queryCursor.moveToNext();
            }
        }
        if(queryCursor != null && !queryCursor.isClosed()) {
            queryCursor.close();
        }
        Log.d("fetch ", "UserDbAdapter.fetchAllImage(): listImage.size() = " + listImage.size());
        return listImage;
    }

    public Object fetchSingleImage(int id) {
        Cursor c = mDb.query(true, TBL_INFO, PROJECTION_ALL, COL_ID + "=" + id, null, null, null, null, null);
        if(c == null) {
            return null;
        }
        Object objOut = null;
        if(c.moveToFirst()) {
            ImageInfo user_info = new ImageInfo(id,
                    c.getString(c.getColumnIndexOrThrow(COL_NAME)),
                    c.getString(c.getColumnIndexOrThrow(COL_FILTER)),
                    c.getString(c.getColumnIndexOrThrow(COL_TYPE)),
                    c.getBlob(c.getColumnIndexOrThrow(COL_IMAGE)));

            objOut = user_info;
            c.close();
        }
        return objOut;
    }

    private ContentValues createContentValues(String name, String filter,String type, byte[] image) {
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_FILTER, filter);
        cv.put(COL_TYPE,type);
        cv.put(COL_IMAGE,image);
        return cv;
    }
}