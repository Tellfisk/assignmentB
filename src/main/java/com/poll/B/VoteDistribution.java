package com.poll.B;


public class VoteDistribution {

    private int yes;
    private int no;

    public VoteDistribution() {}

    public VoteDistribution(int yes, int no) {
        this.yes = yes;
        this.no = no;
    }

    public int getYes() {
        return yes;
    }

    public void setYes(int yes) {
        this.yes = yes;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }
}
