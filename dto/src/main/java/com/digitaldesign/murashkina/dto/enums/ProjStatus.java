package com.digitaldesign.murashkina.dto.enums;

import lombok.Getter;

public enum ProjStatus {
    DRAFT(1),
    DEVELOP(2),
    TEST(3),
    COMPLETED(4);
    @Getter
    private final int statusNumber;


    ProjStatus(int statusNumber) {
        this.statusNumber = statusNumber;
    }
}
