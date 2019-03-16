package xin.xiaoa.englishlearn;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import xin.xiaoa.englishlearn.bottomnavigation.BottomNavigationAdapter;
import xin.xiaoa.englishlearn.bottomnavigation.BottomNavigationDialog;
import xin.xiaoa.englishlearn.fragment_all.MyFragment;
import xin.xiaoa.englishlearn.fragment_all.StudyFragment;
import xin.xiaoa.englishlearn.fragment_all.OtherFragment;
import xin.xiaoa.englishlearn.fragment_all.LexiconFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener, ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private BottomNavigationBar bottomNavigationBar;
    //private BadgeItem badgeItem; //添加角标
    private List<Fragment> mList; //ViewPager的数据源
//    MyItemLongListener myItemLongListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.test);
        initBottomNavigationBar();
        setBottomNavigationItem(bottomNavigationBar, 0, 29, 13);
        initViewPager();

    }

    //初始化ViewPager
    private void initViewPager() {
        mList = new ArrayList<>();
        mList.add(new StudyFragment(this));
        mList.add(new LexiconFragment(this));
        mList.add(new OtherFragment());
        mList.add(new MyFragment());

        viewPager = findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(3);
//        viewPager.setOnPageChangeListener(this);
        viewPager.addOnPageChangeListener(this);
        BottomNavigationAdapter mainAdapter = new BottomNavigationAdapter(getSupportFragmentManager(), mList);
        viewPager.setAdapter(mainAdapter); //视图加载适配器
    }

    //初始化底部导航条
    private void initBottomNavigationBar() {
        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
//        bottomNavigationBar.setOnTouchListener(new MyOnLongClickListener());
        bottomNavigationBar.setTabSelectedListener(this);
//        badgeItem = new BadgeItem()
//                .setHideOnSelect(true) //设置被选中时隐藏角标
//                .setBackgroundColor(Color.RED)
//                .setText("99");
        /**
         * 设置模式
         * 1、BottomNavigationBar.MODE_DEFAULT 默认
         * 如果Item的个数<=3就会使用MODE_FIXED模式，否则使用MODE_SHIFTING模式
         *
         * 2、BottomNavigationBar.MODE_FIXED 固定大小
         * 填充模式，未选中的Item会显示文字，没有换挡动画。
         *
         * 3、BottomNavigationBar.MODE_SHIFTING 不固定大小
         * 换挡模式，未选中的Item不会显示文字，选中的会显示文字。在切换的时候会有一个像换挡的动画
         */
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        /**
         * 设置背景的样式
         * 1、BottomNavigationBar.BACKGROUND_STYLE_DEFAULT 默认
         * 如果设置的Mode为MODE_FIXED，将使用BACKGROUND_STYLE_STATIC 。
         * 如果Mode为MODE_SHIFTING将使用BACKGROUND_STYLE_RIPPLE。
         *
         * 2、BottomNavigationBar.BACKGROUND_STYLE_STATIC 导航条的背景色是白色，
         * 加上setBarBackgroundColor（）可以设置成你所需要的任何背景颜色
         * 点击的时候没有水波纹效果
         *
         * 3、BottomNavigationBar.BACKGROUND_STYLE_RIPPLE 导航条的背景色是你设置的处于选中状态的
         * Item的颜色（ActiveColor），也就是setActiveColorResource这个设置的颜色
         * 点击的时候有水波纹效果
         */
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        //设置导航条背景颜色
        //在BACKGROUND_STYLE_STATIC下，表示整个容器的背景色。
        // 而在BACKGROUND_STYLE_RIPPLE下，表示选中Item的图标和文本颜色。默认 Color.WHITE
        bottomNavigationBar.setBarBackgroundColor(R.color.whites);

        //选中时的颜色,在BACKGROUND_STYLE_STATIC下，表示选中Item的图标和文本颜色。
        // 而在BACKGROUND_STYLE_RIPPLE下，表示整个容器的背景色。默认Theme's Primary Color
        bottomNavigationBar.setActiveColor(R.color.bnbar_select);

        //未选中时的颜色，表示未选中Item中的图标和文本颜色。默认为 Color.LTGRAY
        bottomNavigationBar.setInActiveColor(R.color.bnbar_noselect);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.bottom_navigation_bar_study, "学习"))
                .addItem(new BottomNavigationItem(R.drawable.bottom_navigation_bar_lexicon, "词库"))
                .addItem(new BottomNavigationItem(R.drawable.bottom_navigation_bar_other, "其它"))
                .addItem(new BottomNavigationItem(R.drawable.bottom_navigation_bar_my, "个人"))
                .setFirstSelectedPosition(0)
                .initialise(); //所有的设置需在调用该方法前完成

        //如果使用颜色的变化不足以展示一个选项Item的选中与非选中状态，
        // 可以使用BottomNavigationItem.setInActiveIcon()方法来设置。
//        new BottomNavigationItem(R.drawable.ic_home_white_24dp, "公交")//这里表示选中的图片
//                .setInactiveIcon(ContextCompat.getDrawable(this,R.mipmap.ic_launcher));//非选中的图片
    }
    boolean BottomNavigationItemLongClick(int all,int now){
        //System.out.println("底部导航栏长按监听,all:"+all+"now:"+now);

        switch (now){
            case 1 : {
                BottomNavigationDialog bottomNavigationDialog = new BottomNavigationDialog(this);
                bottomNavigationDialog.show(now,R.layout.bottom_navigation_dialog_study);
            }break;
            case 2 : {
                BottomNavigationDialog bottomNavigationDialog = new BottomNavigationDialog(this);
                bottomNavigationDialog.show(now,R.layout.bottom_navigation_dialog_lexicon);
            }break;
            case 3 : {
                BottomNavigationDialog bottomNavigationDialog = new BottomNavigationDialog(this);
                bottomNavigationDialog.show(now,R.layout.bottom_navigation_dialog_other);
            }break;
            default: return false;
        }


        return true;
    }


    /**
     @param bottomNavigationBar，需要修改的 BottomNavigationBar
     @param space 图片与文字之间的间距
     @param imgLen 单位：dp，图片大小，应 <= 36dp
     @param textSize 单位：dp，文字大小，应 <= 20dp

     使用方法：直接调用setBottomNavigationItem(bottomNavigationBar, 6, 26, 10);
     代表将bottomNavigationBar的文字大小设置为10dp，图片大小为26dp，二者间间距为6dp
     **/

    private void setBottomNavigationItem(BottomNavigationBar bottomNavigationBar, int space, int imgLen, int textSize){
        Class barClass = bottomNavigationBar.getClass();
        Field[] fields = barClass.getDeclaredFields();
        for(int i = 0; i < fields.length; i++){
            Field field = fields[i];
            field.setAccessible(true);
            if(field.getName().equals("mTabContainer")){
                try{
                    //反射得到 mTabContainer
                    final LinearLayout mTabContainer = (LinearLayout) field.get(bottomNavigationBar);
                    final int mTabContainer_ChildCount = mTabContainer.getChildCount();
                    for(int j = 0; j < mTabContainer_ChildCount; j++){

                        //获取到容器内的各个Tab
                        View view = mTabContainer.getChildAt(j);
                        final int finalJ = j;
                        view.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                return BottomNavigationItemLongClick(mTabContainer_ChildCount, finalJ+1);
                            }
                        });
                        //获取到Tab内的各个显示控件
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(56));
                        FrameLayout container = (FrameLayout) view.findViewById(R.id.fixed_bottom_navigation_container);
                        container.setLayoutParams(params);
                        container.setPadding(dip2px(2), dip2px(0), dip2px(2), dip2px(0));

                        //获取到Tab内的文字控件
                        TextView labelView = (TextView) view.findViewById(com.ashokvarma.bottomnavigation.R.id.fixed_bottom_navigation_title);
                        //计算文字的高度DP值并设置，setTextSize为设置文字正方形的对角线长度，所以：文字高度（总内容高度减去间距和图片高度）*根号2即为对角线长度，此处用DP值，设置该值即可。
                        labelView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
                        labelView.setIncludeFontPadding(false);
                       // labelView.setPadding(0,0,0,dip2px(15-textSize - space/2));
                        labelView.setPadding(0,0,0,dip2px(2));
                        //获取到Tab内的图像控件
                        ImageView iconView = (ImageView) view.findViewById(com.ashokvarma.bottomnavigation.R.id.fixed_bottom_navigation_icon);
                        //设置图片参数，其中，MethodUtils.dip2px()：换算dp值
                        params = new FrameLayout.LayoutParams(dip2px(imgLen), dip2px(imgLen));
                        params.setMargins(0,dip2px(4),0,space/2);
                       // params.setMargins(0,dip2px(4),0,0);
                        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                        iconView.setLayoutParams(params);
                    }
                } catch (IllegalAccessException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public int dip2px(float dpValue) {
        final float scale = getApplication().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    @Override
    public void onTabSelected(int position) {
        //tab被选中
        viewPager.setCurrentItem(position);
        System.out.println("onTabSelected");
    }

    @Override
    public void onTabUnselected(int position) {
        System.out.println("onTabUnselected");
    }

    @Override
    public void onTabReselected(int position) {
        System.out.println("onTabReselected");
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
       // bottomNavigationBar.selectTab(position);
        System.out.println("ScrolledselectTab"+position);
    }

    @Override
    public void onPageSelected(int position) {
        //ViewPager滑动
        bottomNavigationBar.selectTab(position);
        System.out.println("selectTab"+position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        System.out.println("onPageScrollStateChanged");
    }
}
