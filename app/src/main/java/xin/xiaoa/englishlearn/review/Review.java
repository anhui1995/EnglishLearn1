package xin.xiaoa.englishlearn.review;

import java.util.ArrayList;
import java.util.List;

public class Review {
    private List<ReviewListItem> reviewLists ;
    private ReviewListItem reviewListItem;

    public String getNext(){
        if(reviewLists==null) return "";
        if(reviewLists.size()==0) return "";
        return reviewLists.get(0).getEnglish();
    }
    int getLength(){
        if(reviewLists==null) return 0;
        return reviewLists.size();
    }
    public void setReviewLists(List<ReviewListItem> reviewLists){
        this.reviewLists = reviewLists;

    }
    public void getReviewLists(){

    }
    public void setMemory(int val){
        switch (val){
            case 3: {
                reviewLists.remove(0);
            }break;
            case 2: {
                if(reviewLists.size()>4){
                    reviewLists.add(5,reviewLists.get(0));
                }
                else reviewLists.add(reviewLists.size(),reviewLists.get(0));
                reviewLists.remove(0);
            }break;
            case 1: {
                if(reviewLists.size()>3){
                    reviewLists.add(2,reviewLists.get(0));
                }
                else reviewLists.add(reviewLists.size(),reviewLists.get(0));
                reviewLists.remove(0);
            }break;
        }
    }

}














