package mk.ukim.finki.wp.eshop.service;

import mk.ukim.finki.wp.eshop.model.VideoGame;

import java.util.List;
import java.util.Optional;

public interface VideoGamesService {

    List<VideoGame> findAll();
    Optional<VideoGame> findById(Long id);
    Optional<VideoGame> findByName(String name);
    Optional<VideoGame> save(String name, Double price, Long category, Long manufacturer);
    Optional<VideoGame> save(String name, String imgUrl, Double price, String description, Long categoryId, Long manufacturerId);
    void deleteById(Long id);
}
