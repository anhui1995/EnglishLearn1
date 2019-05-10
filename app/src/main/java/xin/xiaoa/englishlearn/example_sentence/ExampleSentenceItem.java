package xin.xiaoa.englishlearn.example_sentence;

import static android.view.View.INVISIBLE;

public class ExampleSentenceItem {
    private String strEnglish;
    private String strChinese;
    private int visiable;

    public int getVisiable() {
        return visiable;
    }

    public void setVisiable(int visiable) {
        this.visiable = visiable;
    }

    public ExampleSentenceItem(String strEnglish, String strChinese) {
        this.strEnglish = strEnglish;
        this.strChinese = strChinese;
        this.visiable = INVISIBLE;
    }

    public ExampleSentenceItem() {
        this.visiable = INVISIBLE;
    }

    public String getStrEnglish() {
        return strEnglish;
    }

    public void setStrEnglish(String strEnglish) {
        this.strEnglish = strEnglish;
    }

    public String getStrChinese() {
        return strChinese;
    }

    public void setStrChinese(String strChinese) {
        this.strChinese = strChinese;
    }

}
