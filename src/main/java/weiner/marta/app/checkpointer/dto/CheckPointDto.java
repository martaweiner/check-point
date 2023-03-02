package weiner.marta.app.checkpointer.dto;

import lombok.Value;

import java.util.Date;

@Value
public class CheckPointDto {
    String username;
    String content;
    Date createdOn;
}
