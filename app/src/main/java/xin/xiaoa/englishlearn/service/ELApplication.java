package xin.xiaoa.englishlearn.service;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Connection;
import android.os.Handler;

public class ELApplication extends Application {

    private static Connection con = null;
    private static MySqlServer sql = null;
    private static String wordPath;
    private static String rootPath;
    private static SQLiteDatabase db;

    private static Handler lexiconFragmentHandle;

    public static void setLexiconFragmentHandle(android.os.Handler handler) {
        ELApplication.lexiconFragmentHandle = handler;
    }


    public static Handler getLexiconFragmentHandle() {
        return lexiconFragmentHandle;
    }

    public static SQLiteDatabase getDb() {
        return db;
    }

    public static void setDb(SQLiteDatabase db) {
        ELApplication.db = db;
    }

    @SuppressWarnings("unused")
    public static String getRootPath() {
        return rootPath;
    }

    @SuppressWarnings("unused")
    public static void setRootPath(String rootPath) {
        ELApplication.rootPath = rootPath;
    }

    @SuppressWarnings("unused")
    public static Connection getCon() {
        return con;
    }

    @SuppressWarnings("unused")
    public static void setCon(Connection con) {
        ELApplication.con = con;
    }

    @SuppressWarnings("unused")
    public static MySqlServer getSql() {
        return sql;
    }

    @SuppressWarnings("unused")
    public static void setSql(MySqlServer sql) {
        ELApplication.sql = sql;
    }

    @SuppressWarnings("unused")
    public static String getWordPath() {
        return wordPath;
    }

    @SuppressWarnings("unused")
    public static void setWordPath(String tmp) {
        ELApplication.wordPath = tmp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化图片加载器相关配置

    }

}
