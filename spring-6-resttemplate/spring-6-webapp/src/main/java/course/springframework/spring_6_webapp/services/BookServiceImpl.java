package course.springframework.spring_6_webapp.services;
import course.springframework.spring_6_webapp.domain.Book;
import course.springframework.spring_6_webapp.repositories.BookRepositoty;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepositoty bookRepository;

    public BookServiceImpl(BookRepositoty bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }
}
