package xin.xiaoa.englishlearn.fragment_lexicon;

import java.util.List;

public class GroupsItem {

    private List<ChildsItem> childsLists;

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    private String strName;
    public List<ChildsItem> getChilds() {
        return childsLists;
    }

    public void setChilds(List<ChildsItem> childs) {
        this.childsLists = childs;
    }

}
