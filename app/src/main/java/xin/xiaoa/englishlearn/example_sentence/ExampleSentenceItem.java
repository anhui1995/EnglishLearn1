package xin.xiaoa.englishlearn.example_sentence;

public class ExampleSentenceItem {
    private String strEnglish;
    private String strChinese;

    public ExampleSentenceItem(String strEnglish, String strChinese) {
        this.strEnglish = strEnglish;
        this.strChinese = strChinese;
    }

    public ExampleSentenceItem() {

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
