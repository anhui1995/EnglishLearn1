package xin.xiaoa.englishlearn.fragment_other;

public class TLHistoryListViewItem {
    private String text;
    private String result;
    private String fromLanguage;
    private String toLanguage;
    private int fromNum;
    private int toNum;


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getFromNum() {
        return fromNum;
    }

    public void setFromNum(int fromNum) {
        this.fromNum = fromNum;
    }

    public int getToNum() {
        return toNum;
    }

    public void setToNum(int toNum) {
        this.toNum = toNum;
    }

    public TLHistoryListViewItem(String text, String result, String fromLanguage, int fromNum,  String toLanguage, int toNum) {
        this.text = text;
        this.result = result;
        this.fromLanguage = fromLanguage;
        this.toLanguage = toLanguage;
        this.fromNum = fromNum;
        this.toNum = toNum;

    }

    public TLHistoryListViewItem() {}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFromLanguage() {
        return fromLanguage;
    }

    public void setFromLanguage(String fromLanguage) {
        this.fromLanguage = fromLanguage;
    }

    public String getToLanguage() {
        return toLanguage;
    }

    public void setToLanguage(String toLanguage) {
        this.toLanguage = toLanguage;
    }
}
