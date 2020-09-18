package springbootstarter.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootstarter.entities.Course;
import springbootstarter.entities.Topic;
import springbootstarter.repositories.CourseRepository;

import java.util.ArrayList;
import java.util.List;
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
}
