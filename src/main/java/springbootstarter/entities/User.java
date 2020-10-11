package springbootstarter.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity @Getter @Setter @NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userId;

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;

    private int active;
    private String roles="";
    private String permissions="";

    public List<String> getPermissionsList(){
        if (this.permissions.length()>0){
            return Arrays.asList(this.permissions.split(","));
        }
        return new ArrayList<>();
    }
    public List<String> getRolesList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();

    }
    public User(String username,String password,String roles,String permissions) {
        this.username=username;
        this.password=password;
        this.roles=roles;
        this.permissions=permissions;
        this.active=1;

    }
}
