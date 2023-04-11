package com.api.webvote.tests.controller_using_h2;

import com.api.webvote.tests.config.Convert;
import com.api.webvote.tests.stubs.AssociateStub;
import com.api.webvote.v1.ApiWebvoteApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static com.api.webvote.tests.SqlProvider.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ApiWebvoteApplication.class)
@AutoConfigureMockMvc
public class AssociateControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = insertAssociate),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = resetDB)
    })
    public void Should_ReturnOk_When_GetAssociateById() throws Exception {
		mockMvc.perform(get("/v1/api/associate/{id}", 1L))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cpf").value("317.932.710-38"))
				.andExpect(jsonPath("$.name").value("Fulano"));

    }

    @Test
    public void Should_ReturnNotFound_When_GetAssociateWithInvalidId () throws Exception {
        mockMvc.perform(get("/v1/api/associate/{id}", 99999L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = resetDB)
    public void Should_ReturnOk_When_CreateNewAssociate () throws Exception {
        mockMvc.perform(post("/v1/api/associate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Convert.asJsonString(AssociateStub.associateDefault())))
                .andExpect(status().isOk());
    }

    @Test
    public void Should_ReturnBadRequest_When_CreateNewAssociate () throws Exception {
        mockMvc.perform(post("/v1/api/associate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Convert.asJsonString(null)))
                .andExpect(status().isBadRequest());
    }
}
