package weiner.marta.app.checkpointer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import weiner.marta.app.checkpointer.entity.AppUser;
import weiner.marta.app.checkpointer.entity.CheckPoint;

import java.util.List;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findFirstByUsername(String username);
}
