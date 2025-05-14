package com.dci.full_mvc.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int releaseYear;
    private int duration;
    private String genre;
    private String language;
    private boolean wonOscars;

    @ManyToOne
    @JoinColumn(name="director_id")
    private Director director;

}
