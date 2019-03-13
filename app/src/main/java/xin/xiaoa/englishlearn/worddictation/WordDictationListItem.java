package xin.xiaoa.englishlearn.worddictation;

public class WordDictationListItem {

    private String english;
    private String show;
    private String fayin;
    private String yinbiao;
    private long id;
    private boolean flog;

    private String n = "", v = "", adj = "", adv = "", other = "";


    public void setOther(String str) {
        other = str;
    }

    public String getOther() {
        return other;
    }

    public void setV(String str) {
        v = str;
    }

    public String getV() {
        return v;
    }

    public void setN(String str) {
        n = str;
    }

    public String getN() {
        return n;
    }

    public void setAdv(String str) {
        adv = str;
    }

    public String getAdv() {
        return adv;
    }

    public void setAdj(String str) {
        adj = str;
    }

    public String getAdj() {
        return adj;
    }


    public void setFlog(boolean tmp) {
        flog = tmp;
    }

    public boolean getFlog() {
        return flog;
    }

    public void setFayin(String str) {
        fayin = str;
    }

    public String getFayin() {
        return fayin;
    }

    public void setYinbiao(String str) {
        yinbiao = str;
    }

    public String getYinbiao() {
        return yinbiao;
    }

    public void setId(long tmp) {
        id = tmp;
    }

    public long getId() {
        return id;
    }

    public void setEnglish(String str) {
        english = str;
    }

    public String getEnglish() {
        return english;
    }

    public String getMeaning() {
        return n + v + adj + adv + other;
    }


    public void setShow(String str) {
        show = str;
    }

    public String getShow() {
        return show;
    }


}
