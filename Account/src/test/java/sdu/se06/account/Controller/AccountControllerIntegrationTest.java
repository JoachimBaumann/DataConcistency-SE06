package sdu.se06.account.Controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import sdu.se06.account.entity.AccountEntity;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountController.class)
public class AccountControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountRepository repository;

    @Test
    public void testGetAccounts() throws Exception {
        // Arrange
        AccountEntity account1 = new AccountEntity("Lars", "password", 150.0, "Lars@larsen.dk");
        AccountEntity account2 = new AccountEntity("Lone", "password", 50.0, "Lone@lonesen.dk");
        List<AccountEntity> accounts = Arrays.asList(account1, account2);

        Mockito.when(repository.findAll()).thenReturn(accounts);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("Lars"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].balance").value(150.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].username").value("Lone"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].balance").value(50.0));
    }

    @Test
    public void testCreateAccount() throws Exception {
        // Arrange
        AccountEntity account = new AccountEntity("John", "password", 100.0, "john@example.com");
        AccountEntity savedAccount = new AccountEntity(1, "John", "password", 100.0, "john@example.com");

        Mockito.when(repository.save(Mockito.any(AccountEntity.class))).thenReturn(savedAccount);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/accounts/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"John\",\"password\":\"password\",\"balance\":100.0,\"email\":\"john@example.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "/accounts/1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(100.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("john@example.com"));
    }

    // Add more test methods as needed

}
