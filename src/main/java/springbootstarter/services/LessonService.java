package springbootstarter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootstarter.entities.Course;
import springbootstarter.entities.Lesson;
import springbootstarter.entities.Topic;
import springbootstarter.repositories.LessonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LessonService {


    @Autowired
    private LessonRepository lessonRepository;

    public List<Lesson> findLessonsByCourse(Optional<Course> course) {
        return lessonRepository.findLessonsByCourse(course);
    }
    public Optional<Lesson> findLessonById(Long id){
        return lessonRepository.findLessonByLessonId(id);
    }
    public Optional<Lesson> findLessonByName(String lessonName){
        return lessonRepository.findLessonByName(lessonName);
    }
    public void save(Lesson lesson){
        lessonRepository.save(lesson);
    }
    public void delete(Long id) {
        lessonRepository.deleteByLessonId(id);
    }
}
