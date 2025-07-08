package course.springframework.spring_6_webapp.boostrap;

import course.springframework.spring_6_webapp.domain.Author;
import course.springframework.spring_6_webapp.domain.Book;
import course.springframework.spring_6_webapp.domain.Publisher;
import course.springframework.spring_6_webapp.repositories.AuthorRepository;
import course.springframework.spring_6_webapp.repositories.BookRepositoty;
import course.springframework.spring_6_webapp.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BoostrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepositoty bookRepositoty;
    private final PublisherRepository publisherRepository;

    public BoostrapData(AuthorRepository authorRepository, BookRepositoty bookRepositoty,
                        PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepositoty = bookRepositoty;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Author eric = new Author();
        eric.setFirstName("Eric");
        eric.setLastName("Evans");

        Book ddd = new Book();
        ddd.setTitle("Domain Driven Design");
        ddd.setIsbn("123456");

        Author ericSaved = authorRepository.save(eric);
        Book dddSaved = bookRepositoty.save(ddd);

        Author martin = new Author();
        martin.setFirstName("Martin");
        martin.setLastName("Fowler");

        Book vampyr = new Book();
        vampyr.setTitle("Carmina nocturna");
        vampyr.setIsbn("789123");

        Author martinSaved =  authorRepository.save(martin);
        Book vampyrSaved = bookRepositoty.save(vampyr);

        ericSaved.getBooks().add(dddSaved);
        martinSaved.getBooks().add(vampyrSaved);
        dddSaved.getAuthors().add(ericSaved);
        vampyrSaved.getAuthors().add(martinSaved);

        Publisher publisher = new Publisher();
        publisher.setPublisherName("My publisher");
        publisher.setAddress("123 main");
        publisherRepository.save(publisher);

        dddSaved.setPublisher(publisher);
        vampyrSaved.setPublisher(publisher);


        authorRepository.save(ericSaved);
        authorRepository.save(martinSaved);
        bookRepositoty.save(dddSaved);
        bookRepositoty.save(vampyrSaved);

        System.out.println("In Boostrap");
        System.out.println("Author count: "+ authorRepository.count());
        System.out.println("Book count: " + bookRepositoty.count() );
        System.out.println("Publisher count " + publisherRepository.count());


    }
}
