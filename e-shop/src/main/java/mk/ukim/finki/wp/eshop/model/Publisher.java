package mk.ukim.finki.wp.eshop.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "publisher")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Publisher() {
    }

    public Publisher(String name) {
        this.name = name;
    }
}
