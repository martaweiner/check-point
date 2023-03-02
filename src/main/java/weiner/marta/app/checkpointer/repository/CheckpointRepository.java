package weiner.marta.app.checkpointer.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import weiner.marta.app.checkpointer.entity.CheckPoint;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CheckpointRepository extends JpaRepository<CheckPoint, Long> {
    List <CheckPoint> findAllByUserUsername(String username);
}
