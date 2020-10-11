package springbootstarter.controllers;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
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
import springbootstarter.entities.Course;
import springbootstarter.entities.Lesson;
import springbootstarter.services.CourseService;
import springbootstarter.services.LessonService;
import springbootstarter.entities.Topic;
import springbootstarter.services.TopicService;
import springbootstarter.utils.LessonPagingResponse;
import springbootstarter.utils.PagingHeaders;
import springbootstarter.utils.TopicPagingResponse;


import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class LessonController {

    @Autowired
    private LessonService lessonService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private CourseService courseService;


    /*
     * the api implicit prams and request params
     * with name and description are meant for swagger UI to display them
     * TODO:add the paging param to Swagger UI
     * */
    @GetMapping("/lessons")
    @ApiImplicitParams(@ApiImplicitParam(dataType = "Sort", paramType = "query",name = "sort"))
    public ResponseEntity<List<Lesson>> getLessons(
            @And({
                    @Spec(path = "name", params = "name", spec = Like.class) ,
                    @Spec(path = "description", params = "description", spec = Like.class)
            }) Specification<Lesson> spec,
            Sort sort,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestHeader HttpHeaders headers) {
        final LessonPagingResponse response = lessonService.get(spec, headers, sort);
        return new ResponseEntity<>(response.getLessons(), returnHttpHeaders(response), HttpStatus.OK);
    }

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

    public HttpHeaders returnHttpHeaders(LessonPagingResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(PagingHeaders.COUNT.getName(), String.valueOf(response.getCount()));
        headers.set(PagingHeaders.PAGE_SIZE.getName(), String.valueOf(response.getPageSize()));
        headers.set(PagingHeaders.PAGE_OFFSET.getName(), String.valueOf(response.getPageOffset()));
        headers.set(PagingHeaders.PAGE_NUMBER.getName(), String.valueOf(response.getPageNumber()));
        headers.set(PagingHeaders.PAGE_TOTAL.getName(), String.valueOf(response.getPageTotal()));
        return headers;
    }
}
