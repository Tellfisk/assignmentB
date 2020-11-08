package com.poll.B;

public class VotedInThisPoll {
    private boolean hasVoted;

    public VotedInThisPoll() {}

    public VotedInThisPoll(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }

    public boolean isHasVoted() {
        return hasVoted;
    }

    public void setHasVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }
}
