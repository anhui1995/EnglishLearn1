package xin.xiaoa.englishlearn;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Window;

import java.io.File;
import java.sql.ResultSet;

import xin.xiaoa.englishlearn.service.ELApplication;
import xin.xiaoa.englishlearn.service.MySqlServer;
import xin.xiaoa.englishlearn.service.SQLiteService;
import xin.xiaoa.englishlearn.service.ToastUtil;

public class LaunchActivity extends Activity {


    private Context mContext;
    ResultSet rsList, rsList1;
    // private BroadcastReceiver receiver;
    //private LoadingDialog loadDialog;


    void initSQLite(){
        final SQLiteService sqLiteService = new SQLiteService(this);
        System.err.println("开始SQLite");
        sqLiteService.init();
        SQLiteDatabase db = sqLiteService.getDb();
        ELApplication.setDb(db);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("class_MainActivity");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mContext = this;
        ELApplication.setSql(new MySqlServer());
        //loadDialog=new LoadingDialog(this);
        //loadDialog.setTitle("请稍候...");
        // loadDialog.show();
        File externalFilesDir = getExternalFilesDir(null);
        //System.out.println("文件"+externalFilesDir.getPath());
        if (externalFilesDir.getPath() != null){
            ELApplication.setRootPath(externalFilesDir.getPath() + "/db/");
            ELApplication.setWordPath(externalFilesDir.getPath() + "/wordmp3/");
        }


        new Thread() {
            public void run() {
                ELApplication.getSql().initSql();
                init();
            }
        }.start();
    }

    private void init() {

        initSQLite();
        //getUnitWordList();
        makeRootDirectory(ELApplication.getRootPath());
        makeRootDirectory(ELApplication.getWordPath());
          doLogin();



    }

    protected void onStart() {
        super.onStart();
    }

    void doLogin() {


       /* if(ELApplication.getSql() ==null)
            System.out.println("数据库已");
        else System.out.println("数据库断");*/

        while (ELApplication.getSql() == null) {
            System.out.println("数据库未初始化");
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                System.out.println("数据库错误getSql()" + e);
            }
        }

        while (!ELApplication.getSql().sqlStation()) {

            System.out.println("数据库未登录");
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                System.out.println("数据库错误getSql()" + e);
            }
        }

        if (!ELApplication.getSql().sqlStation())//数据库已经连接
            System.out.println("数据库断开连接");


        if (ELApplication.getSql().sqlStation()) {//登录成功
            System.out.println("登录成功");
            //getUnitWordList();
            Intent intent2 = new Intent(mContext, BottomNavigationBarActivity.class);
            startActivity(intent2);
        } else {
            System.out.println("登录失败");
            ToastUtil.showShortToast(mContext, "登录失败，请检您的网络是否正常以及用户名和密码是否正确");
        }


    }

    //生成文件夹
    void makeRootDirectory(String filePath) {
        File file;

        try {
            file = new File(filePath);
            if (!file.exists()) {//判断指定的路径或者指定的目录文件是否已经存在。
                if (file.mkdir())//建立文件夹
                    System.out.println("新建文件夹成功,路径为：" + filePath);
                else System.out.println("新建文件夹失败");
            }
        } catch (Exception e) {
            System.out.println("makeRootDirectory异常" + e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //  unregisterReceiver(receiver);
    }
}
