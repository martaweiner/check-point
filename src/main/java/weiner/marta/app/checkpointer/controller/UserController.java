package weiner.marta.app.checkpointer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import weiner.marta.app.checkpointer.dto.UserDto;
import weiner.marta.app.checkpointer.entity.AppUser;
import weiner.marta.app.checkpointer.repository.AppUserRepository;

import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor

public class UserController {
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("register")
    public ResponseEntity getUsers() throws JsonProcessingException {
        List<AppUser> users = appUserRepository.findAll();
        return ResponseEntity.ok(objectMapper.writeValueAsString(users));

    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody UserDto userDto) {
        AppUser user = new AppUser();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        appUserRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
}
