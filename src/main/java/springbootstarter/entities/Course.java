package springbootstarter.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity @Getter @Setter @NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long courseId;
    @NotEmpty
    @Size(min = 3,max = 100,message = "name length must be between 3 and 100")
    private String name;
    @NotEmpty
    @Size(min = 3,max = 500,message = "description length must be between 3 and 500")
    private String description;

    @ManyToOne
    @JoinColumn(name="topic_id")
    @JsonBackReference
    private Topic topic;


    @JsonManagedReference
    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL)
    List<Lesson> lessons;

    public Course(String name, String description) {
        this.name = name;
        this.description = description;
    }



}
