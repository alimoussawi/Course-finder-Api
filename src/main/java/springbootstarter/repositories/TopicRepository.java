package springbootstarter.repositories;

import org.springframework.data.repository.CrudRepository;
import springbootstarter.entities.Course;
import springbootstarter.entities.Topic;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends CrudRepository<Topic,String> {
    List<Topic> findAll();

    Optional<Topic> findTopicByName(String name);

    Optional<Topic> findTopicByTopicId(Long topicId);
    void deleteByTopicId(Long id);
}
