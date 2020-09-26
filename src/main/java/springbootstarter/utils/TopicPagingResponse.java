package springbootstarter.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springbootstarter.entities.Course;
import springbootstarter.entities.Lesson;
import springbootstarter.entities.Topic;

import java.util.List;

/**
 * DTO using for pagination
 */
@Getter
@Setter
public class TopicPagingResponse extends PagingResponse {
    private List<Topic> topics;

    public TopicPagingResponse(Long count, Long pageNumber, Long pageSize, Long pageOffset, Long pageTotal, List<Topic> topics) {
        super(count, pageNumber, pageSize, pageOffset, pageTotal);
        this.topics = topics;
    }
}
