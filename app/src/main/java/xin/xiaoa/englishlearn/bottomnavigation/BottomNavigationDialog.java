package xin.xiaoa.englishlearn.bottomnavigation;


import android.app.Dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;

import java.util.List;

import xin.xiaoa.englishlearn.R;
import xin.xiaoa.englishlearn.activity.A2WActivity;
import xin.xiaoa.englishlearn.activity.AddWordActivity;
import xin.xiaoa.englishlearn.activity.SearchActivity;
import xin.xiaoa.englishlearn.activity.TestActivity;
import xin.xiaoa.englishlearn.bottomnavigation.util.DensityUtil;
import xin.xiaoa.englishlearn.click_word.SearchWord;
import xin.xiaoa.englishlearn.fragment_all.LexiconFragment;
import xin.xiaoa.englishlearn.service.ELApplication;


public class BottomNavigationDialog {

    private Button butSearch;
    private Button butNewWordsBook;
   // private Button butAddNewWords;
    private Button butArticle2Words;
    private Button butTranslate;
    private Button butExample;
    private Button butArticle;

    List<Fragment>  fragmentList;

    private Dialog bottomDialog;
    private Context context;
    private BottomNavigationBar bottomNavigationBar;
    private ViewPager viewPager;
    public BottomNavigationDialog(Context cont,List<Fragment>  fragmentList,BottomNavigationBar bottomNavigationBar, ViewPager viewPager) {
        context = cont;
        this.fragmentList = fragmentList;
        this.bottomNavigationBar = bottomNavigationBar;
        this.viewPager = viewPager;
    }

    void viewInit(View view,int now){
        switch (now){
            case 1 : {
                butSearch = view.findViewById(R.id.bn_dialog_study_Search);
                butSearch.setOnClickListener(new MyClickListener());
            }break;
            case 2 : {
                butNewWordsBook = view.findViewById(R.id.bn_dialog_lexicon_NewWordsBook);
                //butAddNewWords = view.findViewById(R.id.bn_dialog_lexicon_AddNewWords);
                butArticle2Words = view.findViewById(R.id.bn_dialog_lexicon_Article2Words);

                butNewWordsBook.setOnClickListener(new MyClickListener());
//                butAddNewWords.setOnClickListener(new MyClickListener());
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
//        Intent intent = new Intent();
//        intent.setClass(context, SearchActivity.class);
//        context.startActivity(intent);
        new SearchWord(context);
    }
    private void funbutNewWordsBook(){
        System.out.println("xindanci asdsadas生词本");
        LexiconFragment lexiconFragment = (LexiconFragment) fragmentList.get(1);
        Handler handle = lexiconFragment.getHandler();
        ELApplication.setLexiconFragmentHandle(handle);
        Intent intent = new Intent();
        intent.setClass(context, A2WActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("cmd", "new_word");
        bundle.putString("key", "个人生词本");
        bundle.putString("title", "个人生词本");
        intent.putExtras(bundle);
        context.startActivity(intent);

    }
//    private void funbutAddNewWords(){
//        Intent intent = new Intent();
//        intent.setClass(context, AddWordActivity.class);
//        context.startActivity(intent);
//    }
    private void funbutArticle2Words(){
        LexiconFragment lexiconFragment = (LexiconFragment) fragmentList.get(1);
        Handler handle = lexiconFragment.getHandler();
        ELApplication.setLexiconFragmentHandle(handle);
        Intent intent = new Intent();
        intent.setClass(context, A2WActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("cmd", "add");
        bundle.putString("key", "");
        bundle.putString("title", "我的词库");
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    private void funbutTranslate(){
        bottomNavigationBar.selectTab(2);
        viewPager.setCurrentItem(2);
        ELApplication.getOtherFragment().funButTranslate();
    }
    private void funbutExample(){
        bottomNavigationBar.selectTab(2);
        viewPager.setCurrentItem(2);
        ELApplication.getOtherFragment().funButExample();
    }
    private void funbutArticle(){
        bottomNavigationBar.selectTab(2);
        viewPager.setCurrentItem(2);
        ELApplication.getOtherFragment().funButArticle();
    }

    class MyClickListener implements View.OnClickListener {

        public void onClick(View arg0) {
            bottomDialog.hide();
            switch (arg0.getId()) {
                case R.id.bn_dialog_study_Search:
                    funbutSearch();break;
                case R.id.bn_dialog_lexicon_NewWordsBook:
                    funbutNewWordsBook();break;
//                case R.id.bn_dialog_lexicon_AddNewWords:
//                    funbutAddNewWords();break;
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
