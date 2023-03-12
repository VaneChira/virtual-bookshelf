package com.project.bookstore.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_book")
public class BookProgress {
    @EmbeddedId
    BookProgressKey bookProgressKey;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    Book book;

    @Column(name="progress_pages")
    Long progressPage;

    @Column(name = "book_state")
    Integer bookState;
}
