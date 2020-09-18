package springbootstarter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import springbootstarter.entities.Topic;
import springbootstarter.services.TopicService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping("/topics")
    public List<Topic> getAllTopics() {
        return topicService.findAll();
    }

    @GetMapping("/topics/{topicId}")
    public Optional<Topic> getTopic(@PathVariable Long topicId) {
        return topicService.findTopicById(topicId).or(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "topic with ID: " + topicId + " does not exist");
        });
    }
    @GetMapping("/topic")
    public Optional<Topic> getTopicByName(@RequestParam("topicName") String topicName){
        Optional<Topic> topic =topicService.findTopicByName(topicName);
        return  topic.or(()->{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "topic with name: " + topicName +" not found");
        });
    }
    @PostMapping("/topics")
    public void addTopic(@RequestBody @Valid Topic topic, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getFieldErrors().toString());
        }
        topicService.save(topic);
    }

    @PatchMapping("/topics/{topicId}")
    public void updateTopic(@RequestBody @Valid Topic topic, BindingResult bindingResult, @PathVariable Long topicId) {
        Optional<Topic> topicToUpdate = topicService.findTopicById(topicId);
        topicToUpdate.ifPresentOrElse(topic1 -> {
            if (bindingResult.hasErrors()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getFieldErrors().toString());
            }
            topic.setTopicId(topicId);
            topicService.save(topic);
        }, () -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "provided topic does not exist");
        });
    }

    @DeleteMapping("/topics/{topicId}")
    public void deleteTopic(@PathVariable Long topicId) {
        topicService.delete(topicId);
    }

}
