package xin.xiaoa.englishlearn.bottomnavigation;


import android.app.Dialog;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import xin.xiaoa.englishlearn.R;
import xin.xiaoa.englishlearn.activity.AddWordActivity;
import xin.xiaoa.englishlearn.activity.SearchActivity;
import xin.xiaoa.englishlearn.activity.TestActivity;
import xin.xiaoa.englishlearn.bottomnavigation.util.DensityUtil;


public class BottomNavigationDialog {

    private Button butSearch;
    private Button butNewWordsBook;
    private Button butAddNewWords;
    private Button butArticle2Words;
    private Button butTranslate;
    private Button butExample;
    private Button butArticle;

    private Dialog bottomDialog;
    private Context context;
    public BottomNavigationDialog(Context cont) {
        context = cont;
    }

    void viewInit(View view,int now){
        switch (now){
            case 1 : {
                butSearch = view.findViewById(R.id.bn_dialog_study_Search);
                butSearch.setOnClickListener(new MyClickListener());
            }break;
            case 2 : {
                butNewWordsBook = view.findViewById(R.id.bn_dialog_lexicon_NewWordsBook);
                butAddNewWords = view.findViewById(R.id.bn_dialog_lexicon_AddNewWords);
                butArticle2Words = view.findViewById(R.id.bn_dialog_lexicon_Article2Words);

                butNewWordsBook.setOnClickListener(new MyClickListener());
                butAddNewWords.setOnClickListener(new MyClickListener());
                butArticle2Words.setOnClickListener(new MyClickListener());
            }break;
            case 3 : {
                butTranslate = view.findViewById(R.id.bn_dialog_other_Translate);
                butExample = view.findViewById(R.id.bn_dialog_other_Example);
                butArticle = view.findViewById(R.id.bn_dialog_other_Article);

                butTranslate.setOnClickListener(new MyClickListener());
                butExample.setOnClickListener(new MyClickListener());
                butArticle.setOnClickListener(new MyClickListener());
            }break;

        }
    }


    public void show(int loction,int latout) {

        bottomDialog = new Dialog(context, R.style.BottomDialog);
        View contentView = LayoutInflater.from(context).inflate(latout, null);
        viewInit(contentView,loction);
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        int DisplayMetricsX = (context.getResources().getDisplayMetrics().widthPixels);
        layoutParams.width = DisplayMetricsX/4;
        contentView.setLayoutParams(layoutParams);
        Window window = bottomDialog.getWindow();
        WindowManager.LayoutParams lp =  window.getAttributes();
        lp.x = DisplayMetricsX/8-DisplayMetricsX/2+(loction-1)*DisplayMetricsX/4;
        lp.y = DensityUtil.dp2px(context,50);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setAttributes(lp);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();

    }

    private void funbutSearch(){
        Intent intent = new Intent();
        intent.setClass(context, SearchActivity.class);
        context.startActivity(intent);
    }
    private void funbutNewWordsBook(){

    }
    private void funbutAddNewWords(){
        Intent intent = new Intent();
        intent.setClass(context, AddWordActivity.class);
        context.startActivity(intent);
    }
    private void funbutArticle2Words(){

    }
    private void funbutTranslate(){
        Intent intent = new Intent();
        intent.setClass(context, TestActivity.class);
        context.startActivity(intent);
    }
    private void funbutExample(){

    }
    private void funbutArticle(){

    }

    class MyClickListener implements View.OnClickListener {

        public void onClick(View arg0) {
            bottomDialog.hide();
            switch (arg0.getId()) {
                case R.id.bn_dialog_study_Search:
                    funbutSearch();break;
                case R.id.bn_dialog_lexicon_NewWordsBook:
                    funbutNewWordsBook();break;
                case R.id.bn_dialog_lexicon_AddNewWords:
                    funbutAddNewWords();break;
                case R.id.bn_dialog_lexicon_Article2Words:
                    funbutArticle2Words();break;
                case R.id.bn_dialog_other_Translate:
                    funbutTranslate();break;
                case R.id.bn_dialog_other_Example:
                    funbutExample();break;
                case R.id.bn_dialog_other_Article:
                    funbutArticle();break;
            }
        }
    }


}
