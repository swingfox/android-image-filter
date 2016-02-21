package utils.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by David on 02/07/2016.
 */
public class GalleryHelper extends SQLiteOpenHelper {
    // declare constants fields
    private static final String DB_NAME = "gallery_db";
    private static final int DB_VERSION = 1;



    // declared constant SQL Expression
    private static final String DB_CREATE =
            "CREATE TABLE tbl_image (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "image_data BLOB)";

    private static final String DB_DESTROY =
            "DROP TABLE IF EXISTS tbl_image";

    /*
     * constructor
     */
    public GalleryHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /*
     * (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    /*
     * (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DB_DESTROY);
        onCreate(db);
    }
}
