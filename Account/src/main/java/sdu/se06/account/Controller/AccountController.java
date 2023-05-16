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

    @GetMapping("/{id}")
    public AccountEntity getAccount(@PathVariable Long id){
        return repository.findById(id).orElseThrow(RuntimeException::new);
    }

}