package weiner.marta.app.checkpointer.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "CHECKPOINT")
@Data
public class CheckPoint {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = AppUser.class)
    @JoinColumn(name="user_id")
    private AppUser user;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_on", nullable = false)
    private Date createdOn;

}
