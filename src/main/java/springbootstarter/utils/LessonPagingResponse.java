package springbootstarter.utils;


import lombok.Getter;
import lombok.Setter;
import springbootstarter.entities.Lesson;

import java.util.List;

@Getter
@Setter
public class LessonPagingResponse extends PagingResponse {
    List<Lesson> lessons;
    public LessonPagingResponse(Long count, Long pageNumber, Long pageSize, Long pageOffset, Long pageTotal, List<Lesson> lessons) {
        super(count, pageNumber, pageSize, pageOffset, pageTotal);
        this.lessons = lessons;
    }
}
