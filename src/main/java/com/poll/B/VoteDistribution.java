package com.poll.B;


public class VoteDistribution {

    private long poll_id;
    private int yes;
    private int no;

    public VoteDistribution() {}

    public VoteDistribution(long poll_id, int yes, int no) {
        this.poll_id = poll_id;
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

    public long getPoll_id() {
        return poll_id;
    }
}
