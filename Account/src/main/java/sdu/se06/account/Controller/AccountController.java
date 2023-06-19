package sdu.se06.account.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sdu.se06.account.entity.AccountEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


@CrossOrigin("*")
@RequestMapping(value = "/accounts")
@RestController
public class AccountController {

    private final AccountRepository repository;

    public AccountController(AccountRepository repository) {
        this.repository = repository;
    }

    @CrossOrigin("*")
    @PostMapping(value = "/")
    public ResponseEntity createAccount(@RequestBody AccountEntity account) throws URISyntaxException {
        AccountEntity savedAccount = repository.save(account);
        return ResponseEntity.created(new URI("/accounts/" + savedAccount.getId())).body(savedAccount);
    }

    @GetMapping("/")
    public List<AccountEntity> getAccounts(){
        return repository.findAll();

    }

    @GetMapping("/generateUsers")
    public String generateUsers() {
        AccountEntity account1 = new AccountEntity("Lars","password",150.0,"Lars@Larsen.dk");
        AccountEntity account2 = new AccountEntity("Lone","password",50.0,"Lone@lonesen.dk");
        AccountEntity account3 = new AccountEntity("Lotte","password",250.0,"Lotte@Lottensen.dk");
        repository.save(account1);
        repository.save(account2);
        repository.save(account3);

        return "Users created";

    }

    @GetMapping("/{id}")
    public AccountEntity getAccount(@PathVariable int id){
        return repository.findById(id).orElseThrow(RuntimeException::new);
    }

}