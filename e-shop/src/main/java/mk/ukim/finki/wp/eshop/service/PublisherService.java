package mk.ukim.finki.wp.eshop.service;

import mk.ukim.finki.wp.eshop.model.Publisher;

import java.util.List;
import java.util.Optional;

public interface PublisherService {

    Optional<Publisher> findById(Long id);
    List<Publisher> findAll();
    Optional<Publisher> save(String name);
    void deleteById(Long id);
}
