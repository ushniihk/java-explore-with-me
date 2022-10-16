package ru.practicum.exploreWithMe.compilation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "compilations")
@Entity
public class Compilation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "pined", nullable = false)
    private boolean pinned;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "eventId", nullable = false)
    private long eventId;
}