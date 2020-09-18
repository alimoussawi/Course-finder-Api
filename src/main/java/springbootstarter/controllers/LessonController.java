package springbootstarter.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import springbootstarter.entities.Course;
import springbootstarter.entities.Lesson;
import springbootstarter.services.CourseService;
import springbootstarter.services.LessonService;
import springbootstarter.entities.Topic;
import springbootstarter.services.TopicService;


import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class LessonController {

    @Autowired
    private LessonService lessonService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private CourseService courseService;

    @GetMapping("/topics/{topicId}/courses/{courseId}/lessons")
    public List<Lesson> getAllCourseLessons(@PathVariable Long topicId, @PathVariable Long courseId) {
        Optional<Course> course = courseService.findCourseByCourseId(courseId);
        course.filter(course1 -> course1.getTopic().getTopicId().equals(topicId)).or(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
        return lessonService.findLessonsByCourse(course);
    }


    @GetMapping("/topics/{topicId}/courses/{courseId}/lessons/{lessonId}")
    public Optional<Lesson> getLesson(@PathVariable Long lessonId) {
        return lessonService.findLessonById(lessonId);
    }
    @GetMapping("/lesson")
    public Optional<Lesson> getLessonByName(@RequestParam("lessonName") String lessonName){
        Optional<Lesson> lesson =lessonService.findLessonByName(lessonName);
        return  lesson.or(()->{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "lesson with name: " + lessonName +" not found");
        });
    }
    @PostMapping("/topics/{topicId}/courses/{courseId}/lessons")
    public void addLesson(@RequestBody Lesson lesson, @PathVariable Long topicId, @PathVariable Long courseId) {
        lessonService.save(lesson);
    }


    @PutMapping("/topics/{topicId}/courses/{courseId}/lessons/{lessonId}")
    public void updateLesson(@RequestBody Lesson lesson, @PathVariable String topicId, @PathVariable String courseId, @PathVariable String lessonId) {

        lessonService.save(lesson);
    }

    @DeleteMapping(value = "/topics/{topicId}/courses/{courseId}/lessons/{lessonId}")
    public void deleteLesson(@PathVariable Long lessonId) {
        lessonService.delete(lessonId);
    }
}
