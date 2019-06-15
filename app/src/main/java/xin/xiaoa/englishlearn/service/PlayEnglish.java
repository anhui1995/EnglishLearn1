package xin.xiaoa.englishlearn.service;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PlayEnglish {


    private MediaPlayer mMediaPlayer = new MediaPlayer();
    private String mp3path = ELApplication.getWordPath();
    private Context context;
    private void showToast() {
        Toast mToast =Toast.makeText(context, null, Toast.LENGTH_SHORT);
        mToast.setText("不存在发音");
        mToast.show();
    }

    public PlayEnglish(String playFayin, String playEnglish, Context con){
        context = con;
        if (playFayin.equals("")) {
            showToast();
        }
        final String name = playEnglish;
        final String fayin = playFayin;
        if (fileIsExists(mp3path + playEnglish + ".mp3")) {
            playMusic(mp3path + playEnglish + ".mp3");
        } else {
            new Thread() {
                public void run() {
                    if (downloadFile(mp3path, name + ".mp3", fayin) == 1)
                        System.out.println("下载音频错误");
                    else playMusic(mp3path + name + ".mp3");
                }
            }.start();
        }
    }

    private int downloadFile(String dirName, String fileName, String urlStr) {


        OutputStream output = null;
        try {
            //将字符串形式的path,转换成一个url
            URL url = new URL(urlStr);
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
                InputStream input = conn.getInputStream();
                File file = createFile(dirName + fileName);
                output = new FileOutputStream(file);
                //读取大文件
                byte[] buffer = new byte[1024 * 20];
                //记录读取内容

                int len;
                //从输入六中读取数据,读到缓冲区中
                while ((len = input.read(buffer)) > 0) {
                    output.write(buffer, 0, len);
                }

                output.flush();
                input.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
                return 0;
            } catch (Exception e) {
                System.out.println("fail");
                e.printStackTrace();
            }
        }
        return 1;
    }
    /**
     * 判断文件是否存在
     */
    private boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }
    /**
     * 在SD卡的指定目录上创建文件
     *
     *
     */
    private File createFile(String fileName) {
        File file = new File(fileName);
        try {
            if (file.createNewFile())
                System.out.println("createFile_OK");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    // 播放指定路径中的音乐
    private void playMusic(String path) {
        try {
            //MusicName();
            // 重置多媒体
            mMediaPlayer.reset();
            //读取mp3文件
            mMediaPlayer.setDataSource(path);
            //准备播放
            mMediaPlayer.prepare();
            //开始播放
            mMediaPlayer.start();
            //更新进度条时间
            // 循环播放
            mMediaPlayer.setLooping(false);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        } catch (Exception e) {
            System.out.println("播放问题" + e);
        }
    }
}
