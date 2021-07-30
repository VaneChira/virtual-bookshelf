package com.project.bookstore.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserBookKey implements Serializable {
    @Column(name = "user_id")
    Long userId;

    @Column(name = "book_id")
    Long bookId;

}
