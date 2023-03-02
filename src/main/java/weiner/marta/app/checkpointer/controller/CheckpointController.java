package weiner.marta.app.checkpointer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import weiner.marta.app.checkpointer.dto.CheckPointDto;
import weiner.marta.app.checkpointer.service.CheckpointService;
import weiner.marta.app.checkpointer.service.UserNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/checkpoint")
@RequiredArgsConstructor
public class CheckpointController {
    private final CheckpointService service;

    @GetMapping
    public List<CheckPointDto> allRecentCheckpoints(@RequestParam(name = "page", defaultValue = "0") int page) {
        return service.getRecentCheckpoints(page)
                .stream()
                .map(cp -> new CheckPointDto(cp.getUser().getUsername(), cp.getContent(), cp.getCreatedOn()))
                .collect(Collectors.toList());
    }

    @GetMapping(path = "user")
    public List<CheckPointDto> allByUser(@RequestParam(name = "username") String userName) {
        return service.getAllByUserName(userName)
                .stream()
                .map(cp -> new CheckPointDto(cp.getUser().getUsername(), cp.getContent(), cp.getCreatedOn()))
                .collect(Collectors.toList());
    }

    @PostMapping
    public void createCheckPoint(@RequestBody CheckPointDto dto) throws UserNotFoundException {
        service.saveCheckPoint(dto.getUsername(), dto.getContent());
    }

    @GetMapping(path = "tag")
    public List<CheckPointDto> allByTag(@RequestParam(name = "tag") String tag) {
        return service.getAllByTag(tag)
                .stream()
                .map(cp -> new CheckPointDto(cp.getUser().getUsername(), cp.getContent(), cp.getCreatedOn()))
                .collect(Collectors.toList());
    }
}
