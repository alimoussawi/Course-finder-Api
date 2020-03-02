package springbootstarter.lesson;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springbootstarter.course.Course;
import springbootstarter.topic.Topic;


import java.util.List;


@RestController
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @RequestMapping("/topics/{id}/courses/{courseId}/lessons")
    public List<Lesson> getAllLessons(@PathVariable String id,@PathVariable String courseId){

        return lessonService.getAllLessons(id,courseId);
    }


    @RequestMapping("/topics/{topicId}/courses/{courseId}/lessons/{lessonId}")
    public Lesson getLesson(@PathVariable String lessonId){

        return lessonService.getLesson(lessonId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/topics/{topicId}/courses/{courseId}/lessons")
    public void addLesson(@RequestBody Lesson lesson, @PathVariable String topicId,@PathVariable String courseId){
        lesson.setTopic(new Topic(topicId,"",""));
        lesson.setCourse(new Course(courseId,"","",topicId));
        lessonService.addLesson(lesson);
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/topics/{topicId}/courses/{courseId}/lessons/{id}")
    public void updateLesson(@RequestBody Lesson lesson,@PathVariable String topicId,@PathVariable String courseId, @PathVariable String id){
        lesson.setTopic(new Topic(topicId,"",""));
lesson.setCourse(new Course(courseId,"","",topicId));
        lessonService.updateLesson(lesson);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/topics/{topicId}/courses/{courseId}/lessons/{id}")
    public void deleteLesson(@PathVariable String id){
        lessonService.deleteLesson(id);
    }
}
