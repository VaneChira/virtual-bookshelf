package com.project.bookstore.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class BookProgressKey implements Serializable {
    @Column(name = "user_id")
    Long userId;

    @Column(name = "book_id")
    Long bookId;

    public BookProgressKey(Long userId, Long bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookProgressKey that = (BookProgressKey) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(bookId, that.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, bookId);
    }
}
