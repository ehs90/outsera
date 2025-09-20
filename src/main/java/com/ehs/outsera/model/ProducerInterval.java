package com.ehs.outsera.model;

public record ProducerInterval(
        String producer,
        int interval,
        int previousWin,
        int followingWin
) {
    public int getInterval() {
        return interval;
    }
}
