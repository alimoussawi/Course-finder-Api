package springbootstarter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springbootstarter.entities.Course;
import springbootstarter.entities.Lesson;
import springbootstarter.entities.Topic;
import springbootstarter.repositories.CourseRepository;
import springbootstarter.repositories.LessonRepository;
import springbootstarter.repositories.TopicRepository;

import java.util.Arrays;

@SpringBootApplication
public class CourseApiApp {
    @Autowired
    TopicRepository topicRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    LessonRepository lessonRepository;


    public static void main(String[] args) {
        SpringApplication.run(CourseApiApp.class ,args);
    }

    @Bean
    CommandLineRunner runner(){
        return args -> {
            Topic topic1=new Topic("topic 1","topic 1 description");
            Topic topic2=new Topic("topic 2","topic 2 description");
            Topic topic3=new Topic("topic 3","topic 3 description");
            Course course1=new Course("course 1","course 1 description");
            Course course2=new Course("course 2","course 2 description");
            Course course3=new Course("course 3","course 3 description");
            Lesson lesson1 =new Lesson("lesson 1","lesson 1 description");
            Lesson lesson2 =new Lesson("lesson 2","lesson 2 description");
            Lesson lesson3 =new Lesson("lesson 3","lesson 3 description");

            // making the relationships
          //  topic1.setCourses(Arrays.asList(course1,course2));
            course1.setTopic(topic1);
            course2.setTopic(topic1);
            ///////
           // topic2.setCourses(Arrays.asList(course3));
            course3.setTopic(topic2);
            /////////
          //  course1.setLessons(Arrays.asList(lesson1,lesson2,lesson3));
           lesson1.setCourse(course1);
            lesson2.setCourse(course1);
            lesson3.setCourse(course1);

            //saving
            topicRepository.save(topic1);
            topicRepository.save(topic2);
            topicRepository.save(topic3);
            //
            courseRepository.save(course1);
            courseRepository.save(course2);
            courseRepository.save(course3);
            //
            lessonRepository.save(lesson1);
            lessonRepository.save(lesson2);
            lessonRepository.save(lesson3);
        };
    }
}
