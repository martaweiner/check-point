package weiner.marta.app.checkpointer.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import weiner.marta.app.checkpointer.CheckpointerApplication;
import weiner.marta.app.checkpointer.service.CheckpointService;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = CheckpointerApplication.class)
@AutoConfigureMockMvc
public class CheckpointControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CheckpointService service;

    @Test
    public void givenUsername_whenAllByUser_shouldReturnUserCheckpoints()
            throws Exception {

        mvc.perform(get("/checkpoint/user?username=janek87")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].username", Matchers.is("janek87")))
                .andExpect(jsonPath("$[0].content", Matchers.is("Zrealizowanie pierwszej transakcji z klientem")))
                .andExpect(jsonPath("$[0].createdOn", Matchers.is("2023-01-19T23:00:00.000+00:00")));
    }
    @Test
    public void givenTag_whenTagIsOk()
            throws Exception {

        mvc.perform(get("/checkpoint/tag?tag=test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].content", Matchers.is("Podpisanie umowy z partnerem biznesowym #test")));
    }
    @Test
    public void createCheckPoint()
            throws Exception {

        mvc.perform(post("/checkpoint")
                        .content("{\n" +
                                "    \"username\":\"user\",\n" +
                                "    \"content\":\"#asdfg\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        mvc.perform(get("/checkpoint/tag?tag=asdfg")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].username", Matchers.is("user")))
                .andExpect(jsonPath("$[0].content", Matchers.is("#asdfg")));

    }
    @Test
    public void allRecentCheckpoints()
            throws Exception {

        mvc.perform(get("/checkpoint?page=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(10)));
    }
}
