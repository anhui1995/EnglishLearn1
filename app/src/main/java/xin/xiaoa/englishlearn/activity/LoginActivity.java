package xin.xiaoa.englishlearn.activity;



import android.annotation.SuppressLint;
import android.app.*;
import android.content.*;
import android.os.*;
import android.text.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.widget.*;
import java.sql.*;

import xin.xiaoa.englishlearn.BottomNavigationBarActivity;
import xin.xiaoa.englishlearn.R;
import xin.xiaoa.englishlearn.service.ELApplication;
import xin.xiaoa.englishlearn.service.PreferencesUtils;
import xin.xiaoa.englishlearn.service.ToastUtil;
import xin.xiaoa.englishlearn.service.UserMassge;


public class LoginActivity extends Activity{
	private Context mContext;
	private RelativeLayout rl_user;
	private Button mLogin;
	private Button register;
	//private TextURLView mTextViewURL;
	ResultSet rs,rsList,rs2,rsList1;
	private EditText account,password;
	private LoadingDialog loadDialog;
	String strUsername,strPasswd,strEmail,strTip;
	
	
	String username;//用户名
	String pwd;//密码
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
				case 1:loginCheck();break;
				case 2:setAuto();break;
				default : break;
			}

		}
	};


	void setAuto(){
		account.setText(username);
		password.setText(pwd);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		mContext=this;
		loadDialog=new LoadingDialog(this);
		loadDialog.setTitle("正在登录...");
		findView();
		
		init();
		
		
	}
	
	

	private void findView(){
		rl_user=(RelativeLayout) findViewById(R.id.rl_user);
		mLogin=(Button) findViewById(R.id.login);
		register=(Button) findViewById(R.id.login_register);

		account=findViewById(R.id.account);
		password= findViewById(R.id.password);
		String username=PreferencesUtils.getSharePreStr(this, "username");//用户名
		String pwd=PreferencesUtils.getSharePreStr(this, "pwd");//密码
		account.setText(username);
		password.setText(pwd);
	}

	private void init(){
		Animation anim=AnimationUtils.loadAnimation(mContext, R.anim.login_anim);
		anim.setFillAfter(true);
		rl_user.startAnimation(anim);
		mLogin.setOnClickListener(loginOnClickListener);
		register.setOnClickListener(registerOnClickListener);
	}

	
	/**
	 * 登录 register
	 */
	private OnClickListener loginOnClickListener=new OnClickListener() {
		@Override
		public void onClick(View v) {
			doLogin();
		}
	};

	/**
	 * 注册 register
	 */
	private OnClickListener registerOnClickListener=new OnClickListener() {
		@Override
		public void onClick(View v) {
			doRegisterDialog();
		}
	};

	void doRegister(){
		if("".equals(strPasswd)) return;
		if("".equals(strUsername)) return;
		username = strUsername;
		pwd = strPasswd;

		final String creats1 = "CREATE TABLE _"+username+"_a2w_words (" +
				" id int(20) unsigned NOT NULL AUTO_INCREMENT," +
				" article_id int(11) DEFAULT NULL," +
				" word varchar(50) DEFAULT NULL," +
				" PRIMARY KEY (id) )";

		final String creats2 = "CREATE TABLE _"+username+"_a2w_article (" +
				" id int(10) unsigned NOT NULL AUTO_INCREMENT," +
				" article TEXT, " +
				" title TEXT, " +
				" PRIMARY KEY (id) )";


		new Thread() {
			public void run() {
				try { //链接数据库 if(name.equals("admin")){
					if (!ELApplication.getSql().sqlStation())
						System.out.println("数据库连接连接已经断开。");
					ELApplication.getSql().up("INSERT INTO user (username,passwd,email,tip,prefix) VALUES ('"+
							username+"', '"+
							pwd+"', '"+
							strEmail+"', '"+
							strTip+"', '_"+
							username+"_' )");

					ELApplication.getSql().creatTable(creats2);
					ELApplication.getSql().creatTable(creats1);
				} catch (Exception e) {
					System.out.println("unit结果集获取失败问题" + e);
				}
				handler.sendEmptyMessage(2);
			}
		}.start();

	}
	private void doRegisterDialog(){
		//    System.out.println("tapped on:" + str);

		View dialogView = LayoutInflater.from(this).inflate(
				R.layout.register, null);

		final TextView tvUsername = dialogView.findViewById(R.id.editText_username);
		final TextView tvPasswd = dialogView.findViewById(R.id.editText2_passwd);
		final TextView tvEmail =  dialogView.findViewById(R.id.editText4_email);
		final TextView tvTip = dialogView.findViewById(R.id.editText_tip);

		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setView(dialogView);

		dialog.setNegativeButton("确认注册", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which)
				{
					strEmail = tvEmail.getText().toString();
					strPasswd = tvPasswd.getText().toString();
					strUsername = tvUsername.getText().toString();
					strTip = tvTip.getText().toString();

					doRegister();
				}
			});


		dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which)
			{
				// TODO Auto-generated method stub

			}
		});

		dialog.show();
//        dialogDis = dialog.show();
	}
	
	void loginCheck(){
		
		openRs2();
		
	}
	
//	protected void onStart() {
//		super.onStart();
//		int ii=0;
//		String username=PreferencesUtils.getSharePreStr(this, "username");//用户名
//		String pwd=PreferencesUtils.getSharePreStr(this, "pwd");//密码
//		if(!TextUtils.isEmpty(username)){
//			account.setText(username);
//			ii++;
//		}
//		if(!TextUtils.isEmpty(pwd)){
//			password.setText(pwd);
//			ii++;
//		}
//		if(!lication.back.equals("ok"))
//		if(ii>1){
//
//			loadDialog.show();
//
//
//			new Thread(){
//				public void run(){
//					while(!lication.sql.sqlStation()){
//						try{
//							Thread.sleep(50);
//							System.out.println("等待数据库连接");
//						}catch(Exception e){}
//					}
//					System.out.println("自动登录");
//					autoLogin();
//				}
//			}.start();
//		}
//	}
	
	
//	void autoLogin(){
//		doLogin();
//	}
	void doLogin(){
		username=account.getText().toString();//用户名
		pwd=password.getText().toString();//密码
		if(TextUtils.isEmpty(username)){
			ToastUtil.showShortToast(mContext, "请输入您的账号");
			return;
		}
		if(TextUtils.isEmpty(pwd)){
			ToastUtil.showShortToast(mContext, "请输入您的密码");
			return;
		}
		PreferencesUtils.putSharePre(mContext, "username", username);
		PreferencesUtils.putSharePre(mContext, "pwd", pwd);
		loadDialog.show();
		
		login();
		
		
	}
	
	
	
	void login(){
		System.out.println("获取unit");

		new Thread() {
			public void run() {
				try { //链接数据库 if(name.equals("admin")){
					if (!ELApplication.getSql().sqlStation())
						System.out.println("数据库连接连接已经断开。");
					rs =  ELApplication.getSql().sel("SELECT *  FROM user where username= '"+username+"' and passwd = '"+pwd+"' LIMIT 1"); //查询
				} catch (Exception e) {
					System.out.println("unit结果集获取失败问题" + e);
				}
				handler.sendEmptyMessage(1);
			}
		}.start();
	}
	
	
	void openRs2(){
		try { 
		
		if(rs==null){
			System.out.println("结果集为空");
			return;
		}
			if(rs.next()) {

				UserMassge userMassge = new UserMassge();
				userMassge.setConsecutive(rs.getInt("consecutive"));
				userMassge.setAll(rs.getInt("_all"));
				userMassge.setUnknow(rs.getInt("unknow"));
				userMassge.setLearned(rs.getInt("learned"));
				userMassge.setRemainder(rs.getInt("remainder"));
				userMassge.setKnow(rs.getInt("know"));
				userMassge.setFuzzy(rs.getInt("fuzzy"));
				userMassge.setStubborn(rs.getInt("stubborn"));

				userMassge.setUsername(rs.getString("username"));
				userMassge.setEmail(rs.getString("email"));
				userMassge.setTip(rs.getString("tip"));


				PreferencesUtils.putSharePre(mContext, "username", userMassge.getUsername());
				PreferencesUtils.putSharePre(mContext, "email", userMassge.getEmail());
				PreferencesUtils.putSharePre(mContext, "tip", userMassge.getTip());



				ELApplication.setUserMassge(userMassge);
				ELApplication.setPrefix(rs.getString("prefix"));
				ELApplication.setLogin("ok");
				PreferencesUtils.putSharePre(mContext, "login", "ok");
				ELApplication.setUsernameId(rs.getInt("id"));
                PreferencesUtils.putSharePre(mContext, "id", ELApplication.getUsernameId());
                PreferencesUtils.putSharePre(mContext, "prefix", ELApplication.getPrefix());


				loadDialog.hide();
				Intent intent = new Intent(); 
				intent.setClass(LoginActivity.this,BottomNavigationBarActivity.class);
				startActivity(intent);
			}
			else{
				//登录错误
				System.out.println("登录错误？");
				loadDialog.hide();
				
				Toast.makeText( LoginActivity.this,"用户名密码错误，请检查。", Toast.LENGTH_SHORT).show();  
				
			}
		} catch(Exception e) { System.out.println("结unit果集解析问题问题"+e); }
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO: Implement this method

		if (keyCode == event.KEYCODE_BACK)
		{
				moveTaskToBack(true);
				return true;
		}

		return super.onKeyDown(keyCode, event);
	}
	
	/*
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//unregisterReceiver(receiver);
	}*/
	
}
