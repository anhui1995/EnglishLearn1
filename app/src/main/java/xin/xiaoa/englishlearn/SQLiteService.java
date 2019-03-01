package xin.xiaoa.englishlearn;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.IOException;

public class SQLiteService {

    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase db;
    private Cursor cursor; //查询数据的返回结果
    private SQLiteHelper dbHelper;
    Context context;


    public SQLiteDatabase getDb() {
        return db;
    }

    public SQLiteService(Context cont) {
        context = cont;
        sqLiteHelper = new SQLiteHelper(context);
//        cursor = sqLiteHelper.select();
    }

    void init(){
/*
                getReadableDatabase()和getWritableDatabase()都是创建或打开数据库
                如果数据库不存在则创建数据库，如果数据库存在直接打开数据库
                默认情况下这两个函数都表示打开或创建可读可写的数据库对象，如果磁盘已满或者是
                数据库本身权限等情况下getReadableDatabase()打开的是只读数据库
                 */
        /* 初始化并创建数据库 */
        dbHelper = new SQLiteHelper(context);
        /* 创建表 */
       // db = dbHelper.getWritableDatabase();	//调用SQLiteHelper.OnCreate()

        System.err.println("建数据库");
        File file = new File(ELApplication.getRootPath()+"/word.db");

        try{
            if(!file.exists()){
                file.createNewFile();
            }
        } catch (Exception e) {
            System.err.println("创建数据库错误"+e);
        }
        System.err.println("建数据库1");
        db = SQLiteDatabase.openOrCreateDatabase(file,null);
        ELApplication.setDb(db);
        System.err.println("建数据库2");

        //db = dbHelper.getWritableDatabase();
//        String TABLE_NAME = "Book";
//        String sql = "CREATE TABLE " + TABLE_NAME
//                + "(_id INTEGER PRIMARY KEY,"
//                + " BookName VARCHAR(30)  NOT NULL,"
//                + " Author VARCHAR(20),"
//                + " Publisher VARCHAR(30))";

        String TABLE_NAME = "ONLY_WORD";
        String sql = "CREATE TABLE if not exists " + TABLE_NAME
                + "(_id INTEGER PRIMARY KEY,"
                + " word VARCHAR(30)   NOT NULL)";
        db.execSQL(sql);
      //  inseart();
        System.err.println("chaxun");
      //  select(db);
    }
    void inseart(String name){
       // dbHelper.insert("纳兰词","纳兰性德","中国文史出版社");
      //  dbHelper.insert("离散数学","十二五","人民邮电出版社");
        dbHelper.myinsert(name);
      //  System.err.println("插入数据成功");
    }

    void select(SQLiteDatabase db){
        String args[] = new String[]{"%ab%"};
        cursor = dbHelper.query(args,db);
        cursor.moveToFirst();
        System.err.println("数据："+cursor.getString( cursor.getColumnIndex("word") ));
       // dbHelper.query();
    }

}
