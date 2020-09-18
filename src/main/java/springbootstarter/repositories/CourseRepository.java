package springbootstarter.repositories;

import org.springframework.data.repository.CrudRepository;
import springbootstarter.entities.Course;
import springbootstarter.entities.Topic;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends CrudRepository<Course,String> {
    List<Course> findAll();
    List<Course> findAllByTopic(Optional<Topic> topic);
    Optional<Course> findCourseByName(String name);
    Optional<Course> findCourseByCourseId(Long courseId);
    void deleteByCourseId(Long courseId);
}
