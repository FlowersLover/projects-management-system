package com.digitaldesign.murashkina.dto.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

public enum TaskStatus {
    NEW(1), IN_PROGRESS(2), COMPLETED(3), CLOSED(4);
    @Getter
    private final int statusNumber;

    TaskStatus(int statusNumber) {
        this.statusNumber = statusNumber;
    }
}
