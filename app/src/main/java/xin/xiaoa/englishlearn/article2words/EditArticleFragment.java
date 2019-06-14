package xin.xiaoa.englishlearn.article2words;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import xin.xiaoa.englishlearn.R;

public class EditArticleFragment extends Fragment {

    Context context;
    View view;
    TextView textViewArticle;

     String content  = "kolya is one of the richest films i've seen in some time." +
            "zdenek sverak plays a confirmed old bachelor ( who's likely to" +
            " remain so ) , who finds his life as a czech cellist increasingly " +
            "impacted by the five-year old boy that he's taking care of . though " +
            "it ends rather abruptly-- and i'm whining , 'cause i wanted to spend " +
            "more time with these characters-- the acting , writing , and production " +
            "values are as high as , if not higher than , comparable american dramas. " +
            "this father-and-son delight-- sverak also wrote the script , while his son , " +
            "jan , directed-- won a golden globe for best foreign language film and , " +
            "a couple days after i saw it , walked away an oscar . in czech and russian , " +
            "with english subtitles . ";

    @SuppressLint("ValidFragment")
    public EditArticleFragment(Context con) {
        context = con;

    }

    public String getArtilce(){
        return textViewArticle.getText().toString();
    }

    public EditArticleFragment() {}

    public void setArticle(String article){
        textViewArticle.setText(article);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.a2w_fragment_edit_article, container, false);
        textViewArticle = view.findViewById(R.id.a2w_f_edit_art_article);
        //textViewArticle.setText("");
        textViewArticle.clearFocus();
        return view;
    }


}
