package weiner.marta.app.checkpointer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import weiner.marta.app.checkpointer.entity.CheckPoint;
import weiner.marta.app.checkpointer.repository.CheckpointRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Profile("!local")
public class CheckpointTagsService{
    private final CheckpointRepository checkpointRepository;

    public List<CheckPoint> findByTag(String tag){
        if (!tag.startsWith("#")){
            tag = "#" + tag;
        }
        ExampleMatcher customExampleMatcher = ExampleMatcher.matchingAny()
                .withMatcher("content", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase()) ;
        CheckPoint cp = new CheckPoint();
        cp.setContent(tag);
        Example<CheckPoint> example = Example.of(cp, customExampleMatcher);
        return checkpointRepository.findAll(example, Sort.by("createdOn").descending());
    }
}
