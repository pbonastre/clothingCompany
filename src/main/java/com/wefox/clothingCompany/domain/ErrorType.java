package com.wefox.clothingCompany.domain;

import lombok.NonNull;

import java.util.stream.Stream;

public enum ErrorType {
    DATABASE, NETWORK, OTHER;

     @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
