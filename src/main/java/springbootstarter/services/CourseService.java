package springbootstarter.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import springbootstarter.entities.Course;
import springbootstarter.entities.Topic;
import springbootstarter.repositories.CourseRepository;
import springbootstarter.utils.CoursePagingResponse;
import springbootstarter.utils.PagingHeaders;
import springbootstarter.utils.TopicPagingResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;
    
    public List<Course> findAllByTopic(Optional<Topic> topic) { return courseRepository.findAllByTopic(topic); }
    public Optional<Course> findCourseByCourseId(Long id){
        return courseRepository.findCourseByCourseId(id);
    }
    public Optional<Course> findCourseByName(String name){
        return courseRepository.findCourseByName(name);
    }
    public void save(Course course){
        courseRepository.save(course);
    }
    public void delete(Long id) {
        courseRepository.deleteByCourseId(id);
    }


    /**
     * get element using Criteria.
     *
     * @param spec    *
     * @param headers pagination data
     * @param sort    sort criteria
     * @return retrieve elements with pagination
     */
    public CoursePagingResponse get(Specification<Course> spec, HttpHeaders headers, Sort sort) {
        if (isRequestPaged(headers)) {
            return get(spec, buildPageRequest(headers, sort));
        } else {
            final List<Course> entities = get(spec, sort);
            return new CoursePagingResponse((long) entities.size(), 0L, 0L, 0L, 0L, entities);
        }
    }
    public CoursePagingResponse get(Specification<Course> spec, Pageable pageable) {
        Page<Course> page = courseRepository.findAll(spec, pageable);
        List<Course> content = page.getContent();
        return new CoursePagingResponse(page.getTotalElements(), (long) page.getNumber(), (long) page.getNumberOfElements(), pageable.getOffset(), (long) page.getTotalPages(), content);
    }
    public List<Course> get(Specification<Course> spec, Sort sort) {
        return courseRepository.findAll(spec, sort);
    }

    private boolean isRequestPaged(HttpHeaders headers) {
        return headers.containsKey(PagingHeaders.PAGE_NUMBER.getName()) && headers.containsKey(PagingHeaders.PAGE_SIZE.getName());
    }
    private Pageable buildPageRequest(HttpHeaders headers, Sort sort) {
        int page = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_NUMBER.getName())).get(0));
        int size = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_SIZE.getName())).get(0));
        return PageRequest.of(page, size, sort);
    }

}
