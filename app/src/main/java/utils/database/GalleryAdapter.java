package utils.database;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.imageprocessing.david.imagefilterapp.models.ImageInfo;

import utils.database.GalleryHelper;

public class GalleryAdapter {

    // declare database fields
    public static final String TBL_INFO = "tbl_info";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_AGE = "age";
    public static final String COL_CITY = "city";

    // projection on all columns
    private static final String[] PROJECTION_ALL = new String[] {
            COL_ID, COL_NAME, COL_AGE, COL_CITY
    };

    // query output type
    public static final int QUERY_TYPE_STRING_ARRAY = 0x01;
    public static final int QUERY_TYPE_USERINFO_OBJ = 0x02;

    // declared fields
    private Context mContext;
    private SQLiteDatabase mDb;
    private GalleryHelper mDbHelper;

    /*
     * constructor
     */
    public GalleryAdapter(Context c) {
        mContext = c;
    }

    /*
     * open database connection
     */
    public GalleryAdapter open() throws SQLException {
        mDbHelper = new GalleryHelper(mContext);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    /*
     * close database connection
     */
    public void close() {
        mDbHelper.close();
    }

    /*
     * insert a record to db
     */
    public long insertUser(String name, int age, String city) {
        return mDb.insert(TBL_INFO, null, createContentValues(name, age, city));
    }

    /*
     * update a record to db
     */
    public long updateUser(int id, String name, int age, String city) {
        return mDb.update(TBL_INFO, createContentValues(name, age, city), COL_ID + "=" + id, null);
    }

    /*
     * delete a record from db
     */
    public long deleteUser(int id) {
        return mDb.delete(TBL_INFO, COL_ID + "=" + id, null);
    }

    /*
     * query all records
     */
  /*  public List<ImageInfo> fetchAllUsers() {
        // get query cursor
        Cursor queryCursor = mDb.query(TBL_INFO, PROJECTION_ALL, null, null, null, null, null);
        // just return null if cursor null
        if(queryCursor == null) {
            Log.d("fetch ", "UserDbAdapter.fetchAllUsers(): queryCursor = null ");
            return null;
        }
        // init list to hold user info
        List<ImageInfo> listUsers = new ArrayList<ImageInfo>();
        // set cursor to the first element
        queryCursor.moveToFirst();
        // if cursor is not the last element
        while(queryCursor.isAfterLast() == false) {
            // add new user info
            listUsers.add(new UserInfo(
                            // get user id from cursor
                            queryCursor.getInt(queryCursor.getColumnIndexOrThrow(COL_ID)),
                            // get user name from cursor
                            queryCursor.getString(queryCursor.getColumnIndexOrThrow(COL_NAME)),
                            // get user age from cursor
                            queryCursor.getInt(queryCursor.getColumnIndexOrThrow(COL_AGE)),
                            // get user city from cursor
                            queryCursor.getString(queryCursor.getColumnIndexOrThrow(COL_CITY))
                    )
            );
            // move cursor to next item
            queryCursor.moveToNext();
        }
        // check if cursor is still opened and not null
        if(queryCursor != null && !queryCursor.isClosed()) {
            // close it to avoid memory leak
            queryCursor.close();
        }
        Log.d("fetch ", "UserDbAdapter.fetchAllUsers(): listUsers.size() = " + listUsers.size());
        // return user list
        return listUsers;
    }
*/
    /*
     * query one record
     */
    public Object fetchSingleUser(int id, int type) {
        // query a cursor on identified user
        Cursor c = mDb.query(true, TBL_INFO, PROJECTION_ALL, COL_ID + "=" + id, null, null, null, null, null);
        // return null if no record avaiable
        if(c == null) {
            return null;
        }

        Object objOut = null;

        if(type == QUERY_TYPE_STRING_ARRAY) {
            // create array to hold user info
            String[] user_info = new String[4];
            user_info[0] = String.valueOf(id);
            user_info[1] = c.getString(c.getColumnIndexOrThrow(COL_NAME));
            user_info[2] = c.getString(c.getColumnIndexOrThrow(COL_AGE));
            user_info[3] = c.getString(c.getColumnIndexOrThrow(COL_CITY));
            objOut = user_info;
        } else {
            // create UserInfo object
       /*     UserInfo user_info = new UserInfo(
                    id,
                    c.getString(c.getColumnIndexOrThrow(COL_NAME)),
                    c.getInt(c.getColumnIndexOrThrow(COL_AGE)),
                    c.getString(c.getColumnIndexOrThrow(COL_CITY))
            );
            objOut = user_info;*/
        }
        // close cursor
        c.close();

        // return user info
        return objOut;
    }

    /*
     * create ContentValues object to use for db transaction
     */
    private ContentValues createContentValues(String name, int age, String city) {
        // init a ContentValues object
        ContentValues cv = new ContentValues();
        // put data
        cv.put(COL_NAME, name);
        cv.put(COL_AGE, age);
        cv.put(COL_CITY, city);
        // return object
        return cv;
    }
}