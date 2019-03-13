package xin.xiaoa.englishlearn.bottomnavigation;


import android.app.Dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import xin.xiaoa.englishlearn.R;
import xin.xiaoa.englishlearn.bottomnavigation.util.DensityUtil;


public class BottomNavigationDialog {

    Context context;
    public BottomNavigationDialog(Context cont) {
        context = cont;
    }
    public void show(int loction,int latout) {

        Dialog bottomDialog = new Dialog(context, R.style.BottomDialog);
        View contentView = LayoutInflater.from(context).inflate(latout, null);
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

}
