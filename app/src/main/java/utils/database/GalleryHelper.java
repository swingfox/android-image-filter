package utils.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by David on 02/07/2016.
 */
public class GalleryHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "filtersDb";
    private static final int DB_VERSION = 1;

    private static final String DB_CREATE =
            "CREATE TABLE IF NOT EXISTS tbl_image (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, name varchar(25) not null,filter varchar(25) not null, type varchar(10) not null, " +
            "image BLOB)";

    private static final String DB_DESTROY = "DROP TABLE IF EXISTS tbl_image";

    public GalleryHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DB_DESTROY);
        onCreate(db);
    }
}
