package com.example.ppo.review;

public enum Rating {
    AWFUL(1),
    BAD(2),
    NORMAL(3),
    GOOD(4),
    AWESOME(5);

    private final int numericValue;

    Rating(int numericValue) {
        this.numericValue = numericValue;
    }

    public int getNumericValue() {
        return numericValue;
    }

    public static Rating fromValue(int value) {
        for (Rating r : values()) {
            if (r.numericValue == value) {
                return r;
            }
        }
        throw new IllegalArgumentException("Invalid rating value: " + value);
    }
}