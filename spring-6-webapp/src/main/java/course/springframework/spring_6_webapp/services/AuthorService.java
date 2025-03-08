package course.springframework.spring_6_webapp.services;

import course.springframework.spring_6_webapp.domain.Author;
import course.springframework.spring_6_webapp.domain.Book;

public interface AuthorService {

    Iterable<Author> findAll();
}
