package xin.xiaoa.englishlearn.fragment_other;

public class TLLanguageListItem {

    private String languageString;
    private String languageName;

    public TLLanguageListItem(String languageName, String languageString) {
        this.languageString = languageString;
        this.languageName = languageName;
    }


    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageString() {
        return languageString;
    }

    public void setLanguageString(String languageString) {
        this.languageString = languageString;
    }
}
