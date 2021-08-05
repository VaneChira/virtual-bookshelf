package com.project.bookstore.model;

public enum BookStateEnum {
    NONE,
    WISHLIST,
    CURRENTLY_READING,
    READ;

    public static int fromEnumToInt(BookStateEnum bookStateEnum) {
        switch (bookStateEnum) {
            case NONE:
                return 0;
            case WISHLIST:
                return 1;
            case CURRENTLY_READING:
                return 2;
            case READ:
                return 3;
        }
        return -1;
    }

}
