package xin.xiaoa.englishlearn.fragment_lexicon;

import java.util.ArrayList;
import java.util.List;

public class GroupsItem {

    private List<ChildsItem> childsLists = null;
    private String strName;
    private boolean isFirst;

    public GroupsItem() {
        this.isFirst = false;
    }

    public GroupsItem(String strName, ChildsItem childsItem) {
        childsLists = new ArrayList<>();
        childsLists.add(childsItem);
        this.strName = strName;
        this.isFirst = false;
    }

    public GroupsItem(String strName, ChildsItem childsItem,boolean isFirst) {
        childsLists = new ArrayList<>();
        childsLists.add(childsItem);
        this.strName = strName;
        this.isFirst = isFirst;
    }
    public GroupsItem(String strName,List<ChildsItem> childs, boolean isFirst) {
        this.childsLists = childs;
        this.strName = strName;
        this.isFirst = isFirst;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
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

    public void addChildsItem(ChildsItem childsItem) {
        if(childsLists==null) childsLists = new ArrayList<>();
        childsLists.add(childsItem);
    }
}
