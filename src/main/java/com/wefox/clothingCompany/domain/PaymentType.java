package com.wefox.clothingCompany.domain;

import lombok.NonNull;

import java.util.stream.Stream;

public enum PaymentType {
    ONLINE, OFFLINE;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
