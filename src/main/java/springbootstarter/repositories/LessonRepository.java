package springbootstarter.repositories;

import org.springframework.data.repository.CrudRepository;
import springbootstarter.entities.Course;
import springbootstarter.entities.Lesson;
import springbootstarter.entities.Topic;

import java.util.List;
import java.util.Optional;


public interface LessonRepository extends CrudRepository<Lesson,String> {
     Optional<Lesson> findLessonByLessonId(Long lessonId);
     List<Lesson> findAll();
     void deleteByLessonId(Long id);
     List<Lesson> findLessonsByCourse(Optional<Course> course);
     Optional<Lesson> findLessonByName(String lessonName);

}
