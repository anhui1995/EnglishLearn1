package xin.xiaoa.englishlearn.fragment_lexicon;

import java.util.List;

public class GroupsItem {

    private List<ChildsItem> childsLists;
    private String strName;
    private boolean isFirst;

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }


    public GroupsItem() {
        this.isFirst = false;
    }

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public List<ChildsItem> getChilds() {
        return childsLists;
    }

    public void setChilds(List<ChildsItem> childs) {
        this.childsLists = childs;
    }

}
