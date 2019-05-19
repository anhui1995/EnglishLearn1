package xin.xiaoa.englishlearn.fragment_other;

public class ArtListViewItem {
    private String title;
    private String subtitle;
    private String id;

    public ArtListViewItem(String title, String subtitle, String id) {
        this.title = title;
        this.subtitle = subtitle;
        this.id = id;
    }
    
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
