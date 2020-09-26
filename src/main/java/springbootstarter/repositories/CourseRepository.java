package springbootstarter.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import springbootstarter.entities.Course;
import springbootstarter.entities.Topic;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends PagingAndSortingRepository<Course,String>, JpaSpecificationExecutor<Course> {
    List<Course> findAll();
    List<Course> findAllByTopic(Optional<Topic> topic);
    Optional<Course> findCourseByName(String name);
    Optional<Course> findCourseByCourseId(Long courseId);
    void deleteByCourseId(Long courseId);
}
