package com.example.se2Assignment.model;

import java.util.Arrays;

public enum SeatType {
    REGULAR,
    VIP,
    VVIP;

    public static String[] names() {
        return Arrays.stream(SeatType.values())
                .map(Enum::name)
                .toArray(String[]::new);
    }

    }
