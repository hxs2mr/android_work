package microtech.hxswork.com.android_work.mysql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by microtech on 2017/11/8.
 */

public class system_sql extends SQLiteOpenHelper {

    private static final  String System = "create table system("
            +"id integer primary key autoincrement,"
             +"title text,"
            +"content text,"
            +"time text,"
            +"url text)";
    public system_sql(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(System);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
