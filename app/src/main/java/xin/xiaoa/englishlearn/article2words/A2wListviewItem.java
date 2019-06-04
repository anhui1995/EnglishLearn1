package xin.xiaoa.englishlearn.article2words;

public class A2wListviewItem {

    private String name;
    private int freq;


    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public A2wListviewItem(String name,int freq) {
        this.name = name;
        this.freq = freq;
    }
}
