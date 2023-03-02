package weiner.marta.app.checkpointer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weiner.marta.app.checkpointer.entity.AppUser;
import weiner.marta.app.checkpointer.repository.AppUserRepository;

import java.util.List;

@Service
public class UserService {

    private final AppUserRepository appUserRepository;

    @Autowired
    public UserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    public AppUser getUserById(Long userId) {
        return appUserRepository.findById(userId).orElse(null);
    }

    public AppUser saveUser(AppUser user) {
        return appUserRepository.save(user);
    }

    public void deleteUser(Long userId) {
        appUserRepository.deleteById(userId);
    }

}
