package springbootstarter.utils;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import springbootstarter.entities.Course;

@Getter
@Setter
public class CoursePagingResponse extends PagingResponse {
    private List<Course> courses;

    public CoursePagingResponse(Long count, Long pageNumber, Long pageSize, Long pageOffset, Long pageTotal, List<Course> courses) {
        super(count, pageNumber, pageSize, pageOffset, pageTotal);
        this.courses = courses;
    }
}
