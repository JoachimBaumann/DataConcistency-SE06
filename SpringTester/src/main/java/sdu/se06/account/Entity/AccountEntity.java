gpackage sdu.se06.account.Entity;

//import org.springframework.web.bind.annotation.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

//This annotation specifies this class as a JPA entity, making sure that these values are mapped to the SQL database
@Entity
public class AccountEntity {


    // Annotation sets ID as primary Key
    @Id
    @GeneratedValue
    private int id;
    private String username;
    private String password;

    private String Email;

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

}
