package springbootstarter.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootstarter.entities.Topic;
import springbootstarter.repositories.TopicRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public List<Topic> findAll() {
        return topicRepository.findAll();
    }
    public Optional<Topic> findTopicByName(String name){
        return topicRepository.findTopicByName(name);
    }
    public Optional<Topic> findTopicById(Long id){
        return topicRepository.findTopicByTopicId(id);}
    public void save(Topic topic){
        topicRepository.save(topic);
    }
    public void delete(Long id) {
        topicRepository.deleteByTopicId(id);
    }
}
