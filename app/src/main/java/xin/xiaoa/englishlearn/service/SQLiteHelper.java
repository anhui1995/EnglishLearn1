package xin.xiaoa.englishlearn.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "word";
    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_NAME = "ONLY_WORD";


    SQLiteDatabase sqLiteDB;

    public SQLiteDatabase getSqLiteDB() {
        return sqLiteDB;
    }


    //构造函数，创建数据库
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //建表
    public void onCreate(SQLiteDatabase db) {
        sqLiteDB = db;

//        String sql = "CREATE TABLE " + TABLE_NAME
//                + "(_id INTEGER PRIMARY KEY,"
//                + " BookName VARCHAR(30)  NOT NULL,"
//                + " Author VARCHAR(20),"
//                + " Publisher VARCHAR(30))";
//        db.execSQL(sql);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

//    Cursor cursor = sqliteDatabase.query("user", new String[] { "id",
//            "name" }, "id=?", new String[] { "1" }, null, null, null);

    // 第一个参数String：表名
    // 第二个参数String[]:要查询的列名
    // 第三个参数String：查询条件
    // 第四个参数String[]：查询条件的参数
    // 第五个参数String:对查询的结果进行分组
    // 第六个参数String：对分组的结果进行限制
    // 第七个参数String：对查询的结果进行排序
//    cursor = db.query("word",       // 第一个参数String：表名
//            new String[] { "id","name" },  // 第二个参数String[]:要查询的列名
//            "id=?",             // 第三个参数String：查询条件
//            new String[] { "1" },          // 第四个参数String[]：查询条件的参数
//            null,               // 第五个参数String:对查询的结果进行分组
//            null,                 // 第六个参数String：对分组的结果进行限制
//            "ORDER BY RAND()");  // 第七个参数String：对查询的结果进行排序
    public Cursor select() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }

    //插入一条记录
    public long insert(String bookName,String author,String publisher ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("BookName", bookName);
        cv.put("Author", author);
        cv.put("Publisher", publisher);
        long row = db.insert(TABLE_NAME, null, cv);
        return row;
    }

    public long myinsert(String bookName ) {
        String tableName = "ONLY_WORD";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("word", bookName);
        long row = db.insert(tableName, null, cv);
        return row;
    }

    //根据条件查询
    public Cursor query(String[] args,SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("select word from ONLY_WORD where word like ?", args);
        return cursor;
    }

    //删除记录
    public void delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where ="_id = ?";
        String[] whereValue = { Integer.toString(id) };
        db.delete(TABLE_NAME, where, whereValue);
    }

    //更新记录
    public void update(int id, String bookName,String author,String publisher) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "_id = ?";
        String[] whereValue = { Integer.toString(id) };
        ContentValues cv = new ContentValues();
        cv.put("BookName", bookName);
        cv.put("Author", author);
        cv.put("Publisher", publisher);
        db.update(TABLE_NAME, cv, where, whereValue);
    }
}
