package springbootstarter.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import springbootstarter.entities.Course;
import springbootstarter.entities.Topic;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface TopicRepository extends PagingAndSortingRepository<Topic,String>, JpaSpecificationExecutor<Topic> {
    List<Topic> findAll();

    Optional<Topic> findTopicByName(String name);

    Optional<Topic> findTopicByTopicId(Long topicId);
    @Transactional
    void deleteByTopicId(Long id);
}
