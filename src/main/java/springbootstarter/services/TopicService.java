package springbootstarter.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import springbootstarter.entities.Topic;
import springbootstarter.repositories.TopicRepository;
import springbootstarter.utils.PagingHeaders;
import springbootstarter.utils.TopicPagingResponse;

import java.util.List;
import java.util.Objects;
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


    /**
     * get element using Criteria.
     *
     * @param spec    *
     * @param headers pagination data
     * @param sort    sort criteria
     * @return retrieve elements with pagination
     */
    public TopicPagingResponse get(Specification<Topic> spec, HttpHeaders headers, Sort sort) {
        if (isRequestPaged(headers)) {
            return get(spec, buildPageRequest(headers, sort));
        } else {
            final List<Topic> entities = get(spec, sort);
            return new TopicPagingResponse((long) entities.size(), 0L, 0L, 0L, 0L, entities);
        }
    }
    public TopicPagingResponse get(Specification<Topic> spec, Pageable pageable) {
        Page<Topic> page = topicRepository.findAll(spec, pageable);
        List<Topic> content = page.getContent();
        return new TopicPagingResponse(page.getTotalElements(), (long) page.getNumber(), (long) page.getNumberOfElements(), pageable.getOffset(), (long) page.getTotalPages(), content);
    }
    public List<Topic> get(Specification<Topic> spec, Sort sort) {
        return topicRepository.findAll(spec, sort);
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
