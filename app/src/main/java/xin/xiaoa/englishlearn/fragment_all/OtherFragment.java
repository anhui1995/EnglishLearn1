package xin.xiaoa.englishlearn.fragment_all;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import xin.xiaoa.englishlearn.R;
import xin.xiaoa.englishlearn.fragment_other.OtherArticleFragment;
import xin.xiaoa.englishlearn.fragment_other.OtherExampleFragment;
import xin.xiaoa.englishlearn.fragment_other.OtherTranslateFragment;
import xin.xiaoa.englishlearn.fragment_other.OtherViewPagerAdapter;
import xin.xiaoa.englishlearn.service.ELApplication;


/**
 * A simple {@link Fragment} subclass.
 */
public class OtherFragment extends Fragment {
    View view;
    ViewPager viewPager;
    Context context;
    AppCompatActivity superActivity;
    Button butExample;
    Button butArticle;
    Button butTranslate;
    private List<Fragment> mList; //ViewPager的数据源

    @SuppressLint("ValidFragment")
    public OtherFragment(Context con,AppCompatActivity activity) {
        superActivity = activity;
        context = con;
    }
    public OtherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_other, container, false);
        ELApplication.setOtherFragment(this);
        buttonInit();
        viewPagerInit();

        return view;
    }

    void buttonInit(){
        butArticle = view.findViewById(R.id.other_button_article);
        butExample = view.findViewById(R.id.other_button_example);
        butTranslate = view.findViewById(R.id.other_button_translate);

        butArticle.setOnClickListener(new MyButtonClickListener());
        butExample.setOnClickListener(new MyButtonClickListener());
        butTranslate.setOnClickListener(new MyButtonClickListener());

    }
    void setButtonBackground(int position){

        if(position == 0) butTranslate.setBackgroundResource(R.drawable.other_button_background_select);
        else butTranslate.setBackgroundResource(R.drawable.other_button_background_noselect);

        if(position == 1) butExample.setBackgroundResource(R.drawable.other_button_background_select);
        else butExample.setBackgroundResource(R.drawable.other_button_background_noselect);

        if(position == 2) butArticle.setBackgroundResource(R.drawable.other_button_background_select);
        else butArticle.setBackgroundResource(R.drawable.other_button_background_noselect);

    }
    public void funButTranslate(){
        viewPager.setCurrentItem(0);
        setButtonBackground(0);
    }
    public void funButExample(){
        viewPager.setCurrentItem(1);
        setButtonBackground(1);
    }
    public void funButArticle(){
        viewPager.setCurrentItem(2);
        setButtonBackground(2);
    }

    void viewPagerInit(){
        mList = new ArrayList<>();
        mList.add(new OtherTranslateFragment(context));
        mList.add(new OtherExampleFragment(context));
        mList.add(new OtherArticleFragment(context));
        setButtonBackground(0);
        viewPager = view.findViewById(R.id.other_viewPager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new VPChangeListener());
        OtherViewPagerAdapter mainAdapter = new OtherViewPagerAdapter(superActivity.getSupportFragmentManager(), mList);
        viewPager.setAdapter(mainAdapter); //视图加载适配器
    }
    class VPChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // bottomNavigationBar.selectTab(position);
            //System.out.println("ScrolledselectTab"+position);
        }

        @Override
        public void onPageSelected(int position) {
            //ViewPager滑动
            //bottomNavigationBar.selectTab(position);
            setButtonBackground(position);
            System.out.println("selectTab"+position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            //System.out.println("onPageScrollStateChanged");
        }
    }

    class MyButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View arg0) {
            switch (arg0.getId()) {
                case R.id.other_button_article:
                    funButArticle();break;
                case R.id.other_button_example:
                    funButExample();break;
                case R.id.other_button_translate:
                    funButTranslate();break;
            }
        }
    }

}
