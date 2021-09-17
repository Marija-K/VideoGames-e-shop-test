package mk.ukim.finki.wp.eshop.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class VideoGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String imgUrl;

    private Double price;

    //private Integer quantity;

    private String description;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Publisher publisher;

    private boolean Mature = false;

    public VideoGame() {
    }

    public VideoGame(String name, Double price, Category category, Publisher publisher) {
        this.name = name;
        this.price = price;
        //this.quantity = quantity;
        this.category = category;
        this.publisher = publisher;
    }

    public VideoGame(String name, String imgUrl, Double price, String description, Category category, Publisher publisher) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
        //this.quantity = quantity;
        this.description = description;
        this.category = category;
        this.publisher = publisher;
    }


}
