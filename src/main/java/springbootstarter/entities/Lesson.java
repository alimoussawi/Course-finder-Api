package springbootstarter.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity @Getter @Setter @NoArgsConstructor
public class Lesson {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long lessonId;
    @NotEmpty
    @Size(min = 3,max = 100,message = "name length must be between 3 and 100")
    private String name;
    @NotEmpty
    @Size(min = 3,max = 500,message = "name length must be between 3 and 500")
    private String description;

    @ManyToOne
    @JoinColumn(name="course_id")
    @JsonBackReference
    private Course course;


    public Lesson(String name, String description) {
        this.name = name;
        this.description = description;

    }
}
