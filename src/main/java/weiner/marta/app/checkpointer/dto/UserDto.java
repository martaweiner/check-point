package weiner.marta.app.checkpointer.dto;

import com.sun.istack.NotNull;
import lombok.Value;

@Value
public class UserDto {
    @NotNull
    String username;

    @NotNull
    String password;
    String matchingPassword;

}
