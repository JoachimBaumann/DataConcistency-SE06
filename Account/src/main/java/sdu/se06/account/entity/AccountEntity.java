package sdu.se06.account.entity;

//import org.springframework.web.bind.annotation.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;

//This annotation specifies this class as a JPA entity, making sure that these values are mapped to the SQL database
@Entity @AllArgsConstructor
public class AccountEntity {
    public AccountEntity() {
    }

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

    public int getId() {
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
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
