package sdu.se06.account.Controller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sdu.se06.account.Entity.AccountEntity;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

}
