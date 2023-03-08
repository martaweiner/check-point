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
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        checkPoint.setContent(content);
        AppUser user = appUserRepository.findFirstByUsername(username);
        checkPoint.setUser(user);

        if (user == null) {
            throw new UserNotFoundException("User " + user + " doesn't exist in database.");
        }

        List<String> matches = Pattern.compile("@([a-zA-Z0-9_]+)")
                .matcher(content)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.toList());

        for (String match : matches) {
            String mentionedUserName = match.substring(1);
            AppUser mentionedUser = appUserRepository.findFirstByUsername(mentionedUserName);

            if (mentionedUser == null) {
                throw new UserNotFoundException("User " + mentionedUserName + " doesn't exist in database.");
            }
        }
        checkpointRepository.save(checkPoint);
    }

    public List<CheckPoint> getAllByTag(String tag) {
        return checkpointTagsService.findByTag(tag);
    }
}
