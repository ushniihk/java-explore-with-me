package ru.practicum.exploreWithMe.hit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "HITS")
@Entity
public class Hit {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "APP")
    private String app;
    @Column(name = "URI")
    private String uri;
    @Column(name = "IP")
    private String ip;
    @Column(name = "TIMESTAMP")
    private LocalDateTime timestamp;
}
