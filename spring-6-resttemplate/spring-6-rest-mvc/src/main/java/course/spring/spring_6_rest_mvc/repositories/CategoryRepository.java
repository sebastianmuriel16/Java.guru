package course.spring.spring_6_rest_mvc.repositories;

import course.spring.spring_6_rest_mvc.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

}

