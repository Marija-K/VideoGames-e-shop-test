package mk.ukim.finki.wp.eshop.repository.jpa;

import mk.ukim.finki.wp.eshop.model.VideoGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoGameRepository extends JpaRepository<VideoGame, Long> {
    Optional<VideoGame> findByName(String name);
    void deleteByName(String name);
}
