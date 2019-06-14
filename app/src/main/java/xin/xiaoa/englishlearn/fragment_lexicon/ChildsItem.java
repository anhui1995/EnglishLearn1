package xin.xiaoa.englishlearn.fragment_lexicon;

public class ChildsItem {

    private String key;
    private String name;

    public ChildsItem(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public ChildsItem() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return key;
    }

    public void setContent(String key) {
        this.key = key;
    }


}
