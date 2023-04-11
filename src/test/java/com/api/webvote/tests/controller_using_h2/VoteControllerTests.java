package com.api.webvote.tests.controller_using_h2;

import com.api.webvote.tests.config.Convert;
import com.api.webvote.tests.stubs.VoteStub;
import com.api.webvote.v1.ApiWebvoteApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static com.api.webvote.tests.SqlProvider.insertVote;
import static com.api.webvote.tests.SqlProvider.resetDB;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ApiWebvoteApplication.class)
@AutoConfigureMockMvc
public class VoteControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = insertVote),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = resetDB)
    })
    public void Should_ReturnOk_When_Voting () throws Exception {
        mockMvc.perform(post("/v1/api/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Convert.asJsonString(VoteStub.voteDefault())))
                .andExpect(status().isOk());
    }

    @Test
    public void Should_ReturnBadRequest_When_Voting () throws Exception {
        mockMvc.perform(post("/v1/api/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Convert.asJsonString(null)))
                .andExpect(status().isBadRequest());
    }
}
