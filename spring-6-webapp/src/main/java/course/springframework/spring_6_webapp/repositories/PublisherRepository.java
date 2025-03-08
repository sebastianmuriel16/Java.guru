package course.springframework.spring_6_webapp.repositories;

import course.springframework.spring_6_webapp.domain.Publisher;
import org.springframework.data.repository.CrudRepository;


public interface PublisherRepository extends CrudRepository<Publisher,Long> {
}
