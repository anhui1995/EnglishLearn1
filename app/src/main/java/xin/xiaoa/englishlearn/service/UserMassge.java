package xin.xiaoa.englishlearn.service;

public class UserMassge {
    private String username;
    private String email;
    private int consecutive;//连续天数
    private int all;   //所有单词
    private int unknow;   //计划单词
    private int learned;  //已学单词
    private int remainder;  //剩余单词
    private int know; //熟知单词
    private int fuzzy;  //模糊单词
    private int stubborn;  //顽固单词
    private String tip;


    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getConsecutive() {
        return consecutive;
    }

    public void setConsecutive(int consecutive) {
        this.consecutive = consecutive;
    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public int getUnknow() {
        return unknow;
    }

    public void setUnknow(int unknow) {
        this.unknow = unknow;
    }

    public int getLearned() {
        return learned;
    }

    public void setLearned(int learned) {
        this.learned = learned;
    }

    public int getRemainder() {
        return remainder;
    }

    public void setRemainder(int remainder) {
        this.remainder = remainder;
    }

    public int getKnow() {
        return know;
    }

    public void setKnow(int know) {
        this.know = know;
    }

    public int getFuzzy() {
        return fuzzy;
    }

    public void setFuzzy(int fuzzy) {
        this.fuzzy = fuzzy;
    }

    public int getStubborn() {
        return stubborn;
    }

    public void setStubborn(int stubborn) {
        this.stubborn = stubborn;
    }

}
