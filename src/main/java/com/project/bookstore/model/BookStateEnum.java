package com.project.bookstore.model;

public enum BookStateEnum {
    NONE,
    WISHLIST,
    CURRENTLY_READING,
    READ;

    public static int fromEnumToInt(BookStateEnum bookStateEnum) {
        return switch (bookStateEnum) {
            case NONE -> 0;
            case WISHLIST -> 1;
            case CURRENTLY_READING -> 2;
            case READ -> 3;
        };
    }
}
