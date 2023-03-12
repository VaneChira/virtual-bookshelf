package com.project.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "genres")
@Table(name = "genres")
public class Genre {

    @ManyToMany(mappedBy = "genresInBooks", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("genresInBooks")
    Set<Book> booksForGenre;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    @Override
    public String toString() {
        return type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();
    }
}
