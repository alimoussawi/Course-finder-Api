package springbootstarter.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import java.util.List;
@Entity @Getter @Setter @NoArgsConstructor
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long topicId;
    @NotEmpty
    @Size(min = 3,max = 100,message = "name length must be between 3 and 99")
    private String name;
    @NotEmpty
    @Size(min = 3,max = 500,message = "description length must be between 3 and 500")
    private String description;

    @OneToMany(mappedBy = "topic")
    @JsonManagedReference
    List<Course> courses;

    public Topic(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
