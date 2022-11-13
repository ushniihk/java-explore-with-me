package ru.practicum.ewm.hit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
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

    public Hit(String app, String uri, String ip, LocalDateTime timestamp) {
        this.app = app;
        this.uri = uri;
        this.ip = ip;
        this.timestamp = timestamp;
    }
}
