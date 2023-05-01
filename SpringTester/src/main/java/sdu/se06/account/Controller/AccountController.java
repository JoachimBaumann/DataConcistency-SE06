package sdu.se06.account.Controller;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import sdu.se06.account.Entity.AccountEntity;

import java.util.List;



@RequestMapping("/accounts")
@RestController
public class AccountController {

    private final AccountRepository repository;

    public AccountController(AccountRepository repository) {
        this.repository = repository;
    }


    @PostMapping("/")
    public AccountEntity createAccount(@RequestBody AccountEntity account){
        return repository.save(account);
    }

    @GetMapping("/")
    public List<AccountEntity> getAccounts(){
        return repository.findAll();

    }


}
