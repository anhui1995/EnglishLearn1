package xin.xiaoa.englishlearn.review;

public class ReviewListItem {

    private String id;
    private String english;
    private int memoryDatabase;
    private String nextdate;

    private boolean isFirstTimes;
    private int memoryLocal;

    public ReviewListItem(String id, String english, int memoryDatabase, String nextdate) {
        this.id = id;
        this.english = english;
        this.memoryDatabase = memoryDatabase;
        this.memoryLocal = memoryDatabase;
        this.nextdate = nextdate;
        this.isFirstTimes = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public int getMemoryDatabase() {
        return memoryDatabase;
    }

    public void setMemoryDatabase(int memoryDatabase) {
        this.memoryDatabase = memoryDatabase;
    }

    public String getNextdate() {
        return nextdate;
    }

    public void setNextdate(String nextdate) {
        this.nextdate = nextdate;
    }

    public boolean isFirstTimes() {
        return isFirstTimes;
    }

    public void setFirstTimes(boolean firstTimes) {
        isFirstTimes = firstTimes;
    }

    public int getMemoryLocal() {
        return memoryLocal;
    }

    public void setMemoryLocal(int memoryLocal) {
        if(memoryLocal<1) memoryLocal = 1;
        this.memoryLocal = memoryLocal;
    }

}
