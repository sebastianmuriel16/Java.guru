package course.springframework.spring_6_webapp.services;

import course.springframework.spring_6_webapp.domain.Book;

public interface BookService {

    Iterable<Book> findAll();

}
