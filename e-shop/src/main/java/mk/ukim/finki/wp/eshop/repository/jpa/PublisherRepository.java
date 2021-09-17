package mk.ukim.finki.wp.eshop.repository.jpa;

import mk.ukim.finki.wp.eshop.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
