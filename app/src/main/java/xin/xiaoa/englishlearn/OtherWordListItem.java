package xin.xiaoa.englishlearn;

public class OtherWordListItem {
    private String english;
    private String show;
    private String pronunciation;
    private String yinbiao;
    private long id;
    private boolean flog;

    private String n="",v="",adj="",adv="",other="";


    void setOther(String str){
        other=str;
    }
    String getOther(){
        return other;
    }

    void setV(String str){
        v=str;
    }
    String getV(){
        return v;
    }

    void setN(String str){
        n=str;
    }
    String getN(){
        return n;
    }

    void setAdv(String str){
        adv=str;
    }
    String getAdv(){
        return adv;
    }
    void setAdj(String str){
        adj=str;
    }
    String getAdj(){
        return adj;
    }


    void setFlog(boolean tmp){
        flog=tmp;
    }
    boolean getFlog(){
        return flog;
    }
    void setPronunciation(String str){
        pronunciation =str;
    }
    String getPronunciation(){
        return pronunciation;
    }

    void setYinbiao(String str){
        yinbiao=str;
    }
    String getYinbiao(){
        return yinbiao;
    }

    void setId(long tmp){
        id=tmp;
    }
    long getId(){
        return id;
    }
    void setEnglish(String str){
        english=str;
    }
    String getEnglish(){
        return english;
    }
    String getMeaning(){
        return n+v+adj+adv+other;
    }



    void setShow(String str){
        show=str;
    }
    String getShow(){
        return show;
    }

}
