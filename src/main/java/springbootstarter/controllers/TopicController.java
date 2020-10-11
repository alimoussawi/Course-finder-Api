package springbootstarter.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import springbootstarter.entities.Topic;
import springbootstarter.services.TopicService;
import springbootstarter.utils.PagingHeaders;
import springbootstarter.utils.TopicPagingResponse;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class TopicController {

    @Autowired
    private TopicService topicService;

    /*
     * the api implicit prams and request params
     * with name and description are meant for swagger UI to display them
     * TODO:add the paging param to Swagger UI
     * */
    @GetMapping("/topics")
    @ApiImplicitParams(@ApiImplicitParam(dataType = "Sort", paramType = "query",name = "sort"))
    public ResponseEntity<List<Topic>> getTopics(
            @And({
                    @Spec(path = "name", params = "name", spec = Like.class),
                    @Spec(path = "description", params = "description", spec = Like.class)
            }) Specification<Topic> spec,
            Sort sort,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestHeader HttpHeaders headers) {
        final TopicPagingResponse response = topicService.get(spec, headers, sort);
        return new ResponseEntity<>(response.getTopics(), returnHttpHeaders(response), HttpStatus.OK);
    }

    @ApiOperation(value = "Get specific topic by ID")
    @GetMapping("/topics/{topicId}")
    public Optional<Topic> getTopic(@PathVariable Long topicId) {
        return topicService.findTopicById(topicId).or(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "topic with ID: " + topicId + " does not exist");
        });
    }
    @ApiOperation(value = "Get specific topic by Name")
    @GetMapping("/topic")
    public Optional<Topic> getTopicByName(@RequestParam(value = "topicName",defaultValue = "Topic Name") String topicName){
        Optional<Topic> topic =topicService.findTopicByName(topicName);
        return  topic.or(()->{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "topic with name: " + topicName +" not found");
        });
    }
    @ApiOperation(value = "Create a new Topic")
    @PostMapping("/topics")
    public void addTopic(@RequestBody @Valid Topic topic, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getFieldErrors().toString());
        }
        topicService.save(topic);
    }

    @ApiOperation(value = "Update existing Topic",notes = "provide a valid Topic ID")
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


    public HttpHeaders returnHttpHeaders(TopicPagingResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(PagingHeaders.COUNT.getName(), String.valueOf(response.getCount()));
        headers.set(PagingHeaders.PAGE_SIZE.getName(), String.valueOf(response.getPageSize()));
        headers.set(PagingHeaders.PAGE_OFFSET.getName(), String.valueOf(response.getPageOffset()));
        headers.set(PagingHeaders.PAGE_NUMBER.getName(), String.valueOf(response.getPageNumber()));
        headers.set(PagingHeaders.PAGE_TOTAL.getName(), String.valueOf(response.getPageTotal()));
        return headers;
    }
}
