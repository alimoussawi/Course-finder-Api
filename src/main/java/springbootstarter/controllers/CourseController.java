package springbootstarter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.WebContentGenerator;
import springbootstarter.entities.Course;
import springbootstarter.services.CourseService;
import springbootstarter.entities.Topic;
import springbootstarter.services.TopicService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private TopicService topicService;

    @GetMapping("/topics/{topicId}/courses")
    public List<Course> getAllTopicCourses(@PathVariable Long topicId) {
        Optional<Topic> topic = topicService.findTopicById(topicId);
        return courseService.findAllByTopic(topic);
    }

    @GetMapping("/topics/{topicId}/courses/{courseId}")
    public Optional<Course> getCourse(@PathVariable Long topicId, @PathVariable Long courseId) {
        Optional <Course> course=courseService.findCourseByCourseId(courseId);
        course.or(()->{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "provided course does not exist");
        });
        return courseService.findCourseByCourseId(courseId);
    }
    @GetMapping("/course")
    public Optional<Course> getCourseByName(@RequestParam("courseName") String courseName){
        Optional<Course> course =courseService.findCourseByName(courseName);
        return  course.or(()->{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course with name: " + courseName +" not found");
        });
    }

    @PostMapping("/topics/{topicId}/courses")
    public void addCourse(@RequestBody Course course, @PathVariable Long topicId) {
        Optional<Topic> topic = topicService.findTopicById(topicId);
        topic.ifPresentOrElse(resTopic -> {
            course.setTopic(resTopic);
            courseService.save(course);
        }, () -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "provided topic does not exist");
        });
    }

    @PutMapping("/topics/{topicId}/courses/{id}")
    public void updateCourse(@RequestBody Course course, @PathVariable String topicId, @PathVariable String id) {
        courseService.save(course);
    }

    @DeleteMapping("/topics/{topicId}/courses/{id}")
    public void deleteCourse(@PathVariable Long id) {
        courseService.delete(id);
    }
}
