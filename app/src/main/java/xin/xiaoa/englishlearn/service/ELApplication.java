package xin.xiaoa.englishlearn.service;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Connection;
import android.os.Handler;

import xin.xiaoa.englishlearn.fragment_all.MyFragment;
import xin.xiaoa.englishlearn.fragment_all.OtherFragment;

public class ELApplication extends Application {

    private static Connection con = null;
    private static MySqlServer sql = null;
    private static String wordPath;
    private static String rootPath;
    private static SQLiteDatabase db;
    private static Handler lexiconFragmentHandle;
    private static String prefix;
    private static UserMassge userMassge;
    private static String login;
    private static Handler studyFragmentHandler;
    private static int usernameId;
    private static Handler myFragmentHandler;
    private static OtherFragment otherFragment;



    public static OtherFragment getOtherFragment() {
        return otherFragment;
    }

    public static void setOtherFragment(OtherFragment otherFragment) {
        ELApplication.otherFragment = otherFragment;
    }

    public static Handler getMyFragmentHandler() {
        return myFragmentHandler;
    }

    public static void setMyFragmentHandler(Handler myFragmentHandler) {
        ELApplication.myFragmentHandler = myFragmentHandler;
    }

    public static int getUsernameId() {
        return usernameId;
    }

    public static void setUsernameId(int usernameId) {
        ELApplication.usernameId = usernameId;
    }

    public static Handler getStudyFragmentHandler() {
        return studyFragmentHandler;
    }

    public static void setStudyFragmentHandler(Handler studyFragmentHandler) {
        ELApplication.studyFragmentHandler = studyFragmentHandler;
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        ELApplication.login = login;
    }

    public static UserMassge getUserMassge() {
        return userMassge;
    }

    public static void setUserMassge(UserMassge userMassge) {
        ELApplication.userMassge = userMassge;
    }


    public static String getPrefix() {
        return prefix;
    }

    public static void setPrefix(String prefix) {
        ELApplication.prefix = prefix;
    }

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
