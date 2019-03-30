package xin.xiaoa.englishlearn.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;

public class WordQuery {



    private int suchFlog=0;
    private ResultSet sqlResultSet;
    private Context context;
    private SQLiteDatabase db;
    private WordItem wordItem = null;
    private String word;

    public WordQuery(Context context) {
        this.context = context;
    }


    WordItem such(String wordTmp) {
        word = wordTmp;
        suchFlog=0;
        if(word.equals("")) {
            Toast.makeText(
                    context,
                    "请输入单词", Toast.LENGTH_SHORT)
                    .show();
            return null;
        }
        try { //链接数据库 if(name.equals("admin")){
            if (!ELApplication.getSql().sqlStation())
                System.out.println("数据库连接连接已经断开。");
            sqlResultSet = ELApplication.getSql().sel("SELECT * FROM word WHERE english='"+word+"'"); //查询
            System.out.println("已经获取结果集");

        } catch(Exception e) { System.out.println("结果集获取失败问题"+e); }
        openRs();

        return wordItem;
    }
    @SuppressLint("SetTextI18n")
    void openRs(){

        //开始解析结果集
        try {

            if(sqlResultSet.next()) {
                wordItem = new WordItem();
                wordItem.setId(sqlResultSet.getInt("id"));
                wordItem.setEnglish(sqlResultSet.getString("english"));
                wordItem.setN(sqlResultSet.getString("n"));
                wordItem.setV(sqlResultSet.getString("v"));
                wordItem.setAdj(sqlResultSet.getString("adj"));
                wordItem.setAdv(sqlResultSet.getString("adv"));
                wordItem.setOther(sqlResultSet.getString("other"));
                wordItem.setYinbiao("[ "+ sqlResultSet.getString("yinbiao")+" ]");
                wordItem.setFayin(sqlResultSet.getString("fayin"));
            }
            else{
                suchFlog++;

                if(suchFlog==3){
                    Toast.makeText(
                            context,
                            "单词未收录", Toast.LENGTH_SHORT)
                            .show();
                }
                else{
                    upword(word);
                }

            }


        } catch(Exception e) { System.out.println("结果集解析问题问题"+e); }
    }
    public void upword(String suchWord) {
        String uuu="http://dict-co.iciba.com/api/dictionary.php?w="+suchWord+"&type=json&key=24BF8398B2971CFC01004C0715A08D95";

        BufferedReader reader ;

        StringBuilder stringBuilder = new StringBuilder();
        try {
            System.out.println(uuu);
            //将字符串形式的path,转换成一个url
            URL url = new URL(uuu);
            //得到url之后，将要开始连接网络，以为是连接网络的具体代码
            //首先，实例化一个HTTP连接对象conn
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //定义请求方式为GET，其中GET的大小写不要搞错了。
            conn.setRequestMethod("GET");
            //定义请求时间，在ANDROID中最好是不好超过10秒。否则将被系统回收。
            conn.setConnectTimeout(6 * 1000);
            //请求成功之后，服务器会返回一个响应码。如果是GET方式请求，服务器返回的响应码是200，post请求服务器返回的响应码是206（貌似）。
            if (conn.getResponseCode() == 200) {
                //返回码为真
                //从服务器传递过来数据，是一个输入的动作。定义一个输入流，获取从服务器返回的数据
                //InputStream input = conn.getInputStream();
                InputStream is = conn.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String strRead ;
                while ((strRead = reader.readLine()) != null) {
                    stringBuilder.append(strRead);
                    stringBuilder.append("\n");
                }
                reader.close();
                String result = stringBuilder.toString();
                JSONObject jsonObj = new JSONObject(result);
                is.close();
                openJson(jsonObj);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    void openJson(JSONObject json){
        try{
            String ph_en,ph_en_mp3,strWord;
            StringBuilder strN= new StringBuilder();
            StringBuilder strV= new StringBuilder();
            StringBuilder strAdv= new StringBuilder();
            StringBuilder strAdj= new StringBuilder();
            StringBuilder strOther= new StringBuilder();
            JSONObject symbols;
            JSONArray parts;
            symbols=json.getJSONArray("symbols").getJSONObject(0);
            strWord = json.getString("word_name");
            ph_en = symbols.getString("ph_en");
            // ph_am = symbols.getString("ph_am");
            ph_en_mp3 = symbols.getString("ph_en_mp3");
            //  ph_am_mp3 = symbols.getString("ph_am_mp3");
            parts = symbols.getJSONArray("parts");
            JSONObject jsonTmp;
            JSONArray arrMeans;

            for(int i=0;i<parts.length();i++){
                StringBuilder strMeanTmp= new StringBuilder();
                jsonTmp = parts.getJSONObject(i);
                arrMeans = jsonTmp.getJSONArray("means");
                for(int j=0;j<arrMeans.length();j++){
                    strMeanTmp.append(arrMeans.getString(j));
                    strMeanTmp.append(",");
                }
                switch(jsonTmp.getString("part")){
                    case "n.":strN.append(strMeanTmp);break;
                    //case "n.":strN = strN + strMeanTmp;break;
                    case "vi.":strV.append(strMeanTmp);break;
                    case "vt.":strV.append(strMeanTmp);break;
                    case "v.":strV.append(strMeanTmp);break;
                    case "adj.":strAdj.append(strMeanTmp);break;
                    case "ajv.":strAdv.append(strMeanTmp);break;
                    default :strOther.append(strMeanTmp);break;
                }
            }
            //String checkStr = strN + strV + strAdv + strAdj + strOther;

            StringBuilder stress= new StringBuilder();
            stress.append(strN);
            stress.append(strV);
            stress.append(strV);
            stress.append(strAdj);
            stress.append(strOther);
            String checkStr= String.valueOf(strN);


            if(checkStr.equals("")) return;
            String tmp="INSERT INTO word (english, yinbiao, fayin, n, v, adj ,adv ,other ,times)VALUES ( '"+strWord+"','"+ph_en.replace("'","''")+"','"+ph_en_mp3+"','"+strN+"','"+strV+"','"+strAdv+"','"+strAdj+"','"+strOther+"','1')";

            try { //链接数据库 if(name.equals("admin")){
                if (!ELApplication.getSql().sqlStation())
                    System.out.println("数据库连接连接已经断开。");
                ELApplication.getSql().up(tmp);//已经获取结果集
                sqlResultSet = ELApplication.getSql().sel("SELECT * FROM word WHERE english='"+word+"'"); //查询

            } catch(Exception e) { System.out.println("结果集获取失败问题"+e); }
            openRs();
			/*

			System.out.println(" ");
			System.out.println("单词-"+strWord);
			System.out.println(" ");
			System.out.println("发音-"+ph_en+ph_en_mp3+"\n"+ph_am+ph_am_mp3+"\n发音结束\n");
			System.out.println(" ");
			System.out.println("意思"+strN+strV+strAdv+strAdj+strOther+"\n意思结束\n");
*/
        }catch(Exception e){
            System.out.println("openJson错误-"+e);
        }
    }


}
