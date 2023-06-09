package com.ysys.restdocs.api;


import com.ysys.restdocs.config.RestDocsConfiguration;
import com.ysys.restdocs.controller.ProblemController;
import com.ysys.restdocs.model.dto.res.ProblemGetRes;
import com.ysys.restdocs.model.entity.Problem;
import com.ysys.restdocs.service.ProblemService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

@AutoConfigureRestDocs
@WebMvcTest(ProblemController.class)
@Import(RestDocsConfiguration.class)
public class ProblemControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProblemService problemService;

    @Test
    void test() throws Exception {
        FieldDescriptor[] reviews = getReviewFieldDescriptors();

        List<Problem> problemList = new ArrayList<>();
        problemList.add(new Problem("p1", "pname1"));
        problemList.add(new Problem("p2", "pname2"));

        List<ProblemGetRes> problemGetResList = problemList.stream().map(ProblemGetRes::of).collect(Collectors.toList());

        Mockito.when(problemService.findAllProblem()).thenReturn(problemGetResList);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/problems").header(HttpHeaders.AUTHORIZATION, "hihi").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].problemId").value("p1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].problemName").value("pname1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].problemId").value("p2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].problemName").value("pname2"));

        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        response.addHeader("hihi", "headerRes");

        resultActions.andDo(MockMvcRestDocumentation.document(
                "/problems",
                PayloadDocumentation.responseFields(reviews),
                HeaderDocumentation.requestHeaders(HeaderDocumentation.headerWithName(HttpHeaders.AUTHORIZATION).description("req header")),
                HeaderDocumentation.responseHeaders(HeaderDocumentation.headerWithName("hihi").description("headerRes"))
        ));
    }

    private FieldDescriptor[] getReviewFieldDescriptors() {
        return new FieldDescriptor[]{
                fieldWithPath("[].problemId").description("문제아이디"),
                fieldWithPath("[].problemName").description("문제이름")
        };
    }
}
