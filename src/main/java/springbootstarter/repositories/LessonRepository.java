package springbootstarter.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import springbootstarter.entities.Course;
import springbootstarter.entities.Lesson;
import springbootstarter.entities.Topic;

import java.util.List;
import java.util.Optional;


public interface LessonRepository extends PagingAndSortingRepository<Lesson,String>, JpaSpecificationExecutor<Lesson> {
     Optional<Lesson> findLessonByLessonId(Long lessonId);
     List<Lesson> findAll();
     void deleteByLessonId(Long id);
     List<Lesson> findLessonsByCourse(Optional<Course> course);
     Optional<Lesson> findLessonByName(String lessonName);

}
