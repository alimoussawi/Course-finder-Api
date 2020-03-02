package springbootstarter.lesson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootstarter.course.Course;
import springbootstarter.course.CourseRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class LessonService {


    @Autowired
    private LessonRepository lessonRepository;

    public List<Lesson> getAllLessons(String topicId,String CourseId) {

        List<Lesson> lessons =new ArrayList<>();
        lessonRepository.findByTopicIdAndCourseId(topicId,CourseId).forEach(lessons::add);

        return lessons;
    }
    public Lesson getLesson(String id){

        return lessonRepository.findById(id).orElse(null);
    }

    public void addLesson(Lesson lesson){
        lessonRepository.save(lesson);
    }

    public void updateLesson(Lesson lesson) {
        lessonRepository.save(lesson);
    }

    public void deleteLesson(String id) {
        lessonRepository.deleteById(id);
    }
}
