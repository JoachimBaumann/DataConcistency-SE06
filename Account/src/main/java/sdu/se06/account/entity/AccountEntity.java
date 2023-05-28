package sdu.se06.account.entity;

//import org.springframework.web.bind.annotation.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
@Entity
public class AccountEntity {

    public AccountEntity(String username, String password, double balance, String email) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.email = email;
    }

    // Annotation sets ID as primary Key
    @Id
    @GeneratedValue
    private int id;
    private String username;
    private String password;
    private double balance;

    private String email;

}