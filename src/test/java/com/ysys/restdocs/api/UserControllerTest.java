package com.ysys.restdocs.api;

import com.ysys.restdocs.config.RestDocsConfiguration;
import com.ysys.restdocs.controller.UserController;
import com.ysys.restdocs.model.dto.res.UserGetRes;
import com.ysys.restdocs.model.entity.Problem;
import com.ysys.restdocs.model.entity.User;
import com.ysys.restdocs.service.UserService;
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
@WebMvcTest(UserController.class)
@Import(RestDocsConfiguration.class)
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    void test() throws Exception {
        FieldDescriptor[] reviews = getReviewFieldDescriptors();

        List<Problem> problemList1 = new ArrayList<>();
        problemList1.add(new Problem("p1", "pname1"));
        problemList1.add(new Problem("p2", "pname2"));

        List<Problem> problemList2 = new ArrayList<>();
        problemList2.add(new Problem("p3", "pname3"));
        problemList2.add(new Problem("p4", "pname4"));

        List<User> userList = new ArrayList<>();
        userList.add(new User("id1", "name1", "pw1", problemList1));
        userList.add(new User("id2", "name2", "pw2", problemList2));

        List<UserGetRes> userGetResList = userList.stream().map(UserGetRes::of).collect(Collectors.toList());

        Mockito.when(userService.findAllUser()).thenReturn(userGetResList);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users").header(HttpHeaders.AUTHORIZATION, "hihi").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value("id1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userName").value("name1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userPassword").value("pw1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].problemGetResList").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].userId").value("id2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].userName").value("name2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].userPassword").value("pw2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].problemGetResList").exists());

        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        response.addHeader("hihi", "headerRes");

        resultActions.andDo(MockMvcRestDocumentation.document(
                "/users",
                PayloadDocumentation.responseFields(reviews),
                HeaderDocumentation.requestHeaders(HeaderDocumentation.headerWithName(HttpHeaders.AUTHORIZATION).description("headerReq")),
                HeaderDocumentation.responseHeaders(HeaderDocumentation.headerWithName("hihi").description("headerRes"))
        ));
    }

    private FieldDescriptor[] getReviewFieldDescriptors() {
        return new FieldDescriptor[]{
                fieldWithPath("[].userId").description("유저아이디"),
                fieldWithPath("[].userName").description("유저이름"),
                fieldWithPath("[].userPassword").description("유저패스워드"),
                fieldWithPath("[].problemGetResList[].problemId").description("문제아이디"),
                fieldWithPath("[].problemGetResList[].problemName").description("문제이름")
        };
    }
}
