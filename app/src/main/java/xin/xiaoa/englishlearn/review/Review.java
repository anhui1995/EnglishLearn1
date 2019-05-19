package xin.xiaoa.englishlearn.review;


import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Review {
    private List<ReviewListItem> reviewLists ;
//    private ReviewListItem reviewListItem;
    private Calendar rightNow;
    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public Review() {
        rightNow = Calendar.getInstance();
    }

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
    private String calculateNextDate(int memory,int selectValue){

        switch (selectValue){
            case 3:  //认识
                rightNow.add(Calendar.DAY_OF_YEAR,memory); break;
            case 2:  //模糊
                rightNow.add(Calendar.DAY_OF_YEAR,memory); break;
            case 1:  //忘记
                rightNow.add(Calendar.DAY_OF_YEAR,memory); break;
        }

        return dateFormat.format(rightNow.getTime());
    }
    private void write2DB(ReviewListItem reviewListItem){


    }
    public void setMemory(int selectValue){
        ReviewListItem reviewListItem = reviewLists.get(0);
        switch (selectValue){
            case 3: { //认识
                if(reviewListItem.isFirstTimes()){
                    reviewListItem.setFirstTimes(false);
                    reviewListItem.setNextdate(calculateNextDate(reviewListItem.getMemoryDatabase(),selectValue));
                    reviewListItem.setMemoryDatabase(reviewListItem.getMemoryDatabase()+1);
                    reviewListItem.setMemoryLocal(reviewListItem.getMemoryLocal()+8);
                }
                reviewListItem.setMemoryLocal(reviewListItem.getMemoryLocal()+3);
                if(reviewListItem.getMemoryLocal()>10){
                    write2DB(reviewListItem);
                }
                else{
                    if(reviewLists.size()>5){
                        reviewLists.add(6,reviewLists.get(0));
                    }
                    else reviewLists.add(reviewLists.size(),reviewLists.get(0));
                }
                reviewLists.remove(0);
            }break;
            case 2: {  //模糊
                if(reviewListItem.isFirstTimes()){
                    reviewListItem.setFirstTimes(false);
                    reviewListItem.setNextdate(calculateNextDate(reviewListItem.getMemoryDatabase(),selectValue));
                }
                reviewListItem.setMemoryLocal(reviewListItem.getMemoryLocal()+1);

                if(reviewLists.size()>3){
                    reviewLists.add(4,reviewLists.get(0));
                }
                else reviewLists.add(reviewLists.size(),reviewLists.get(0));
                reviewLists.remove(0);
            }break;
            case 1: {  //忘记
                if(reviewListItem.isFirstTimes()){
                    reviewListItem.setFirstTimes(false);
                    reviewListItem.setNextdate(calculateNextDate(reviewListItem.getMemoryDatabase(),selectValue));
                    reviewListItem.setMemoryDatabase(reviewListItem.getMemoryDatabase()-2);
                    reviewListItem.setMemoryLocal(reviewListItem.getMemoryLocal()-3);
                }
                reviewListItem.setMemoryLocal(reviewListItem.getMemoryLocal()-3);
                if(reviewListItem.getMemoryLocal()<0) reviewListItem.setMemoryLocal(0);


                if(reviewLists.size()>1){
                    reviewLists.add(2,reviewLists.get(0));
                }
                else reviewLists.add(reviewLists.size(),reviewLists.get(0));
                reviewLists.remove(0);
            }break;
        }
    }

}














