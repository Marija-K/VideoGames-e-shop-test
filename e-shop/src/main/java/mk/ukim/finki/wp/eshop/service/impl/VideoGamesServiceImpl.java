package mk.ukim.finki.wp.eshop.service.impl;

import mk.ukim.finki.wp.eshop.model.Category;
import mk.ukim.finki.wp.eshop.model.Publisher;
import mk.ukim.finki.wp.eshop.model.VideoGame;
import mk.ukim.finki.wp.eshop.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.wp.eshop.model.exceptions.PublisherNotFoundException;
import mk.ukim.finki.wp.eshop.repository.jpa.CategoryRepository;
import mk.ukim.finki.wp.eshop.repository.jpa.PublisherRepository;
import mk.ukim.finki.wp.eshop.repository.jpa.VideoGameRepository;
import mk.ukim.finki.wp.eshop.service.VideoGamesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VideoGamesServiceImpl implements VideoGamesService {

    private final VideoGameRepository videoGameRepository;
    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;

    public VideoGamesServiceImpl(VideoGameRepository videoGameRepository,
                                 PublisherRepository publisherRepository,
                                 CategoryRepository categoryRepository) {
        this.videoGameRepository = videoGameRepository;
        this.publisherRepository = publisherRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<VideoGame> findAll() {
        return this.videoGameRepository.findAll();
    }

    @Override
    public Optional<VideoGame> findById(Long id) {
        return this.videoGameRepository.findById(id);
    }

    @Override
    public Optional<VideoGame> findByName(String name) {
        return this.videoGameRepository.findByName(name);
    }

    @Override
    @Transactional
    public Optional<VideoGame> save(String name, Double price, Long categoryId, Long manufacturerId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        Publisher publisher = this.publisherRepository.findById(manufacturerId)
                .orElseThrow(() -> new PublisherNotFoundException(manufacturerId));

        this.videoGameRepository.deleteByName(name);
        return Optional.of(this.videoGameRepository.save(new VideoGame(name, price, category, publisher)));
    }

    @Override
    @Transactional
    public Optional<VideoGame> save(String name, String imgUrl, Double price, String description, Long categoryId, Long manufacturerId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        Publisher publisher = this.publisherRepository.findById(manufacturerId)
                .orElseThrow(() -> new PublisherNotFoundException(manufacturerId));

        this.videoGameRepository.deleteByName(name);
        return Optional.of(this.videoGameRepository.save(new VideoGame(name, imgUrl, price, description, category, publisher)));
    }

    @Override
    public void deleteById(Long id) {
        this.videoGameRepository.deleteById(id);
    }
}
