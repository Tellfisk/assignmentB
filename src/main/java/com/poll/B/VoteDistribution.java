package com.poll.B;

import java.util.List;

public class VoteDistribution {

    private long poll_id;
    private int yes;
    private int no;
    private boolean closed;

    public VoteDistribution() {}

    public VoteDistribution(long poll_id, List<Vote> votes, boolean closed) {
        this.poll_id = poll_id;
        int yes = 0;
        int no = 0;
        for (Vote v : votes) {
            if (v.isYes()) {
                yes++;
            } else {
                no++;
            }
        }

        this.yes = yes;
        this.no = no;
        this.closed = closed;
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

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
