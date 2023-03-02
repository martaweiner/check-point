package weiner.marta.app.checkpointer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import weiner.marta.app.checkpointer.entity.AppUser;
import weiner.marta.app.checkpointer.entity.CheckPoint;
import weiner.marta.app.checkpointer.repository.AppUserRepository;
import weiner.marta.app.checkpointer.repository.CheckpointRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CheckpointService {
    private static final int PAGE_SIZE = 10;

    private final CheckpointRepository checkpointRepository;
    private final AppUserRepository appUserRepository;

    private final CheckpointTagsService checkpointTagsService;

    public List<CheckPoint> getRecentCheckpoints(int page) {
        Pageable sortedByCreatedOn =
                PageRequest.of(page, PAGE_SIZE, Sort.by("createdOn").descending());
        return checkpointRepository.findAll(sortedByCreatedOn).toList();
    }

    public List<CheckPoint> getAllByUserName(String userName) {
        CheckPoint cp = new CheckPoint();
        AppUser user = new AppUser();
        user.setUsername(userName);
        cp.setUser(user);
        Example<CheckPoint> example = Example.of(cp);
        return checkpointRepository.findAll(example, Sort.by("createdOn").descending());
    }

    public void saveCheckPoint(String username, String content) throws UserNotFoundException {

        CheckPoint checkPoint = new CheckPoint();
        checkPoint.setCreatedOn(new Date());

        AppUser user = appUserRepository.findFirstByUsername(username);

        checkPoint.setUser(user);

        if (user == null){
            throw new UserNotFoundException("Użytkownik " + user + " nie istnieje w bazie danych.");
        }

        Matcher matcher = Pattern.compile("@([a-zA-Z0-9_]+)").matcher(content);
        List<AppUser> mentionedUsers = new ArrayList<>();

        while (matcher.find()) {
            if (matcher.find()) {
                String mentionedUserName = matcher.group(1);
                AppUser mentionedUser = appUserRepository.findFirstByUsername(mentionedUserName);
                System.out.println(mentionedUser);

                if (mentionedUser != null) {
                    checkPoint.setContent(content);
                    mentionedUsers.add(mentionedUser);
                    checkpointRepository.save(checkPoint);
                } else {
                    System.out.println("Użytkownik @" + mentionedUserName + " nie istnieje w bazie danych.");
                    throw new UserNotFoundException("Użytkownik " + mentionedUserName + " nie istnieje w bazie danych.");
                }
            }
        }
    }

    public List<CheckPoint> getAllByTag(String tag) {
        return checkpointTagsService.findByTag(tag);
    }
}
