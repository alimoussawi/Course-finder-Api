package springbootstarter.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.WebContentGenerator;
import springbootstarter.entities.Course;
import springbootstarter.services.CourseService;
import springbootstarter.entities.Topic;
import springbootstarter.services.TopicService;
import springbootstarter.utils.CoursePagingResponse;
import springbootstarter.utils.PagingHeaders;
import springbootstarter.utils.TopicPagingResponse;

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

    /*
    * the api implicit prams and request params
    * with name and description are meant for swagger UI to display them
    * TODO:add the paging param to Swagger UI
    * */
    @GetMapping("/courses")
    @ApiImplicitParams(@ApiImplicitParam(dataType = "Sort", paramType = "query",name = "sort"))
    public ResponseEntity<List<Course>> getCourses(
            @And({
                    @Spec(path = "name", params = "name", spec = Like.class),
                    @Spec(path = "description", params = "description", spec = Like.class)
            }) Specification<Course> spec,
            Sort sort,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestHeader HttpHeaders headers) {
        final CoursePagingResponse response = courseService.get(spec, headers, sort);
        return new ResponseEntity<>(response.getCourses(), returnHttpHeaders(response), HttpStatus.OK);
    }

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

    public HttpHeaders returnHttpHeaders(CoursePagingResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(PagingHeaders.COUNT.getName(), String.valueOf(response.getCount()));
        headers.set(PagingHeaders.PAGE_SIZE.getName(), String.valueOf(response.getPageSize()));
        headers.set(PagingHeaders.PAGE_OFFSET.getName(), String.valueOf(response.getPageOffset()));
        headers.set(PagingHeaders.PAGE_NUMBER.getName(), String.valueOf(response.getPageNumber()));
        headers.set(PagingHeaders.PAGE_TOTAL.getName(), String.valueOf(response.getPageTotal()));
        return headers;
    }

}
