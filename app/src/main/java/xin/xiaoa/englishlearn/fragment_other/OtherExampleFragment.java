package xin.xiaoa.englishlearn.fragment_other;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import xin.xiaoa.englishlearn.R;
import xin.xiaoa.englishlearn.click_word.ClickEachWord;

public class OtherExampleFragment extends Fragment {
    String text1 = "In 1972 Thomas Kuhn hurled an ashtray at Errol Morris. Already renowned" +
            " for&nbsp;<em><b>The Structure of Scientific Revolutions</b></em>, published a decade earlier, " +
            "Kuhn was at the Institute for Advanced Study in Princeton, and Morris was his graduate " +
            "student in history and philosophy of science.";
    String text2 = "about information is maybe ok no are you sure ?";
    String text3 = "<span style=\"caret-color:#000000\"><span style=\"font-style:normal\"><span style=\"font-variant-caps:normal\">" +
            "<span style=\"font-weight:normal\"><span style=\"letter-spacing:normal\"><span style=\"orphans:auto\">" +
            "<span style=\"text-transform:none\"><span style=\"white-space:normal\"><span style=\"widows:auto\">" +
            "<span style=\"word-spacing:0px\"><span style=\"-webkit-text-size-adjust:auto\">" +
            "In 1972 Thomas Kuhn hurled an ashtray at Errol Morris. Already renowned for&nbsp;" +
            "<em>The Structure of Scientific Revolutions</em>, published a decade earlier, Kuhn was at the Institute for" +
            " Advanced Study in Princeton, and Morris was his graduate student in history and philosophy of science." +
            "</span></span></span></span></span></span></span></span></span></span></span>";
    View view;
    Context context;
    TextView textView1,textView2;
    SpannableStringBuilder spannable;

    @SuppressLint("ValidFragment")
    public OtherExampleFragment(Context cont) {
        context = cont;
    }
    public OtherExampleFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.other_example_fragment, container, false);
        textView1 = view.findViewById(R.id.ttttttextView1);
//        textView1.setText(Html.fromHtml(text1));
        textView2 = view.findViewById(R.id.ttttttextView2);
       // textView2.setText(text2);

        //textView2.setMovementMethod(LinkMovementMethod.getInstance());
//
//        String[] sourceStrArray = text2.split(" ");
//        SpannableStringBuilder spannable = new SpannableStringBuilder(sourceStrArray);
//        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#0000FF"));
//
//        spannable.setSpan(new TextClick(),1,3 , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannable.setSpan(foregroundColorSpan, 1, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView1.setText(Html.fromHtml(text1), TextView.BufferType.SPANNABLE);
        textView2.setText(text2, TextView.BufferType.SPANNABLE);

        new ClickEachWord(context,textView1);
        //new ClickEachWord(context,textView2);

        //点击每个单词响应
        //getEachWord(textView2);

//        textView2.setMovementMethod(MyLinkMovementMethod.getInstance());
//        textView1.setMovementMethod(MyLinkMovementMethod.getInstance());
        return view;
    }



}