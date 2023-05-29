package sdu.se06.account.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sdu.se06.account.entity.AccountEntity;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AccountControllerUnitTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAccount_ReturnsCreatedAccount() throws URISyntaxException {
        // Arrange
        AccountEntity account = new AccountEntity("John", "password", 100.0, "john@example.com");
        when(accountRepository.save(account)).thenReturn(account);

        // Act
        ResponseEntity response = accountController.createAccount(account);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(account, response.getBody());
    }

    @Test
    void getAccounts_ReturnsAllAccounts() {
        // Arrange
        List<AccountEntity> accounts = new ArrayList<>();
        accounts.add(new AccountEntity("John", "password", 100.0, "john@example.com"));
        accounts.add(new AccountEntity("Jane", "password", 200.0, "jane@example.com"));
        when(accountRepository.findAll()).thenReturn(accounts);

        // Act
        List<AccountEntity> result = accountController.getAccounts();

        // Assert
        assertEquals(accounts.size(), result.size());
        assertEquals(accounts.get(0), result.get(0));
        assertEquals(accounts.get(1), result.get(1));
    }

    @Test
    void getAccount_ValidId_ReturnsAccount() {
        // Arrange
        int accountId = 1;
        AccountEntity account = new AccountEntity("John", "password", 100.0, "john@example.com");
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // Act
        AccountEntity result = accountController.getAccount(accountId);

        // Assert
        assertEquals(account, result);
    }
}
