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
import springbootstarter.entities.Lesson;
import springbootstarter.entities.Topic;
import springbootstarter.repositories.LessonRepository;
import springbootstarter.utils.CoursePagingResponse;
import springbootstarter.utils.LessonPagingResponse;
import springbootstarter.utils.PagingHeaders;
import springbootstarter.utils.PagingResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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


    /**
     * get element using Criteria.
     *
     * @param spec    *
     * @param headers pagination data
     * @param sort    sort criteria
     * @return retrieve elements with pagination
     */
    public LessonPagingResponse get(Specification<Lesson> spec, HttpHeaders headers, Sort sort) {
        if (isRequestPaged(headers)) {
            return get(spec, buildPageRequest(headers, sort));
        } else {
            final List<Lesson> entities = get(spec, sort);
            return new LessonPagingResponse((long) entities.size(), 0L, 0L, 0L, 0L, entities);
        }
    }
    public LessonPagingResponse get(Specification<Lesson> spec, Pageable pageable) {
        Page<Lesson> page = lessonRepository.findAll(spec, pageable);
        List<Lesson> content = page.getContent();
        return new LessonPagingResponse(page.getTotalElements(), (long) page.getNumber(), (long) page.getNumberOfElements(), pageable.getOffset(), (long) page.getTotalPages(), content);
    }
    public List<Lesson> get(Specification<Lesson> spec, Sort sort) {
        return lessonRepository.findAll(spec, sort);
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
