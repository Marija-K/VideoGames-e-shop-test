package mk.ukim.finki.wp.eshop.service.impl;

import mk.ukim.finki.wp.eshop.model.Publisher;
import mk.ukim.finki.wp.eshop.repository.jpa.PublisherRepository;
import mk.ukim.finki.wp.eshop.service.PublisherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public Optional<Publisher> findById(Long id) {
        return this.publisherRepository.findById(id);
    }

    @Override
    public List<Publisher> findAll() {
        return this.publisherRepository.findAll();
    }

    @Override
    public Optional<Publisher> save(String name) {
        return Optional.of(this.publisherRepository.save(new Publisher(name)));
    }

    @Override
    public void deleteById(Long id) {
        this.publisherRepository.deleteById(id);
    }
}
