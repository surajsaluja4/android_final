package com.compscitutorials.basigarcia.navigationdrawervideotutorial;

/**
 * Created by suraj on 13-11-2017.
 */
public class bookings {
    private String prev_stat,curr_stat;

    public bookings(String curr_stats,String prev_stats){
     this.setCurr_stat(curr_stats);
     this.setPrev_stat(prev_stats);
    }
    public String getCurr_stat() {
        return curr_stat;
    }

    public void setCurr_stat(String curr_stat) {
        this.curr_stat = curr_stat;
    }

    public String getPrev_stat() {
        return prev_stat;
    }
    public void setPrev_stat(String prev_stat) {
        this.prev_stat = prev_stat;
    }


}
