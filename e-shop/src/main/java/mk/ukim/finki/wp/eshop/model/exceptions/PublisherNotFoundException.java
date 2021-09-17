package mk.ukim.finki.wp.eshop.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PublisherNotFoundException extends RuntimeException{

    public PublisherNotFoundException(Long id) {
        super(String.format("Publisher with id: %d was not found", id));
    }
}
