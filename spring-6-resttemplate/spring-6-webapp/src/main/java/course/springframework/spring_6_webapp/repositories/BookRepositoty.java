package course.springframework.spring_6_webapp.repositories;

import org.springframework.data.repository.CrudRepository;
import course.springframework.spring_6_webapp.domain.Book;

public interface BookRepositoty extends CrudRepository<Book, Long> {

}
