package com.api.webvote.tests.controller;

import com.api.webvote.tests.config.Convert;
import com.api.webvote.v1.controller.VoteController;
import com.api.webvote.v1.enums.VotoEnum;
import com.api.webvote.v1.model.Associate;
import com.api.webvote.v1.model.Schedule;
import com.api.webvote.v1.model.Vote;
import com.api.webvote.v1.service.vote.VoteServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VoteController.class)
@ContextConfiguration(classes = VoteController.class)
public class VoteControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VoteServiceInterface voteService;

    private Vote voteMock;
    private Associate clientMock;
    private Schedule scheduleMock;

    @BeforeEach
    public void inicialize() {

        List<Vote> votes = new ArrayList<Vote>();
        clientMock = new Associate(1L, "Ralf Drehmer Wink", "000.000.000-00");

        scheduleMock = new Schedule(1L, "Schedule title", votes, 1, LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(1));

        voteMock = new Vote(1L, VotoEnum.SIM, clientMock, scheduleMock);

        when(voteService.save(voteMock)).thenReturn(ResponseEntity.ok().build());
    }

    @Test
    public void deveRetornarSucesso_aoVotarEmUmaPauta() throws Exception {

        mockMvc.perform(post("/v1/api/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Convert.asJsonString(voteMock)))
                .andExpect(status().isOk());
    }

    @Test
    public void deveRetornarFalha_aoVotarEmUmaPauta() throws Exception {

        mockMvc.perform(post("/v1/api/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Convert.asJsonString(null)))
                .andExpect(status().isBadRequest());
    }

}
