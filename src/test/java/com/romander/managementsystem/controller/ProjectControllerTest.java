package com.romander.managementsystem.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.romander.managementsystem.dto.project.ProjectRequestDto;
import com.romander.managementsystem.dto.project.ProjectResponseDto;
import com.romander.managementsystem.model.Project;
import com.romander.managementsystem.model.User;
import com.romander.managementsystem.security.AuthenticationService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static com.romander.managementsystem.util.ProjectDataTest.*;
import static com.romander.managementsystem.util.UserDataTest.getSampleUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProjectControllerTest {
    private static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @BeforeEach
    void setUp(@Autowired DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/project/add-projects.sql")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        User testUser = getSampleUser();

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                testUser,
                null,
                testUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @AfterEach
    void tearDown(@Autowired DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()){
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/project/delete-projects.sql")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createProject_WithValidRequest_ShouldReturnDto() throws Exception {
        ProjectResponseDto expected = new ProjectResponseDto();
        expected.setName("Test Project");
        expected.setDescription("Test Description");
        expected.setId(3L);
        expected.setStartDate(LocalDate.of(2020, 1, 1));
        expected.setEndDate(LocalDate.of(2020, 1, 2));
        expected.setStatus(Project.Status.INITIATED);
        expected.setUserId(1L);

        ProjectRequestDto requestDto = new ProjectRequestDto();
        requestDto.setName(expected.getName());
        requestDto.setDescription(expected.getDescription());
        requestDto.setEndDate(expected.getEndDate());
        requestDto.setStartDate(expected.getStartDate());
        requestDto.setUserId(expected.getUserId());

        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        MvcResult result = mockMvc.perform(post("/projects")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        ProjectResponseDto actual = objectMapper.readValue(result.getResponse().getContentAsString(), ProjectResponseDto.class);

        assertEquals(expected, actual);
    }

    @Test
    void getAllProjects_WithValidRequest_ShouldReturnListDto() throws Exception {
        ProjectResponseDto expected1 = getSampleProjectResponseDto();
        ProjectResponseDto expected2 = getSampleAnotherProjectResponseDto();
        List<ProjectResponseDto> expectedList = List.of(expected1, expected2);

        MvcResult result = mockMvc.perform(get("/projects")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ProjectResponseDto[] actual = objectMapper.readValue(result.getResponse().getContentAsString(), ProjectResponseDto[].class);

        assertEquals(expectedList.size(), actual.length);
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), actual[i]);
        }
    }

    @Test
    void getProject_WithValidId_ShouldReturnDto() throws Exception {
        ProjectResponseDto expected = getSampleProjectResponseDto();

        Long Id = 1L;

        MvcResult result = mockMvc.perform(get("/projects/{id}", Id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ProjectResponseDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                ProjectResponseDto.class);

        assertEquals(expected, actual);
    }

    @Test
    void updateProject_WithValidRequest_ShouldReturnDto() throws Exception {
        ProjectResponseDto expected = getSampleProjectResponseDto();
        Long id = 2L;

        ProjectRequestDto requestDto = getSampleProjectRequestDto();

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(put("/projects/{id}", id)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ProjectResponseDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                ProjectResponseDto.class);

        assertTrue(EqualsBuilder.reflectionEquals(expected, actual, "status", "id"));
    }

    @Test
    void deleteProject_WithValidId_ShouldReturnStatusNoContent() throws Exception {
        Long id = 1L;
        List<ProjectResponseDto> expectedList = List.of(getSampleAnotherProjectResponseDto());

        mockMvc.perform(delete("/projects/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        MvcResult result = mockMvc.perform(get("/projects")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ProjectResponseDto[] actual = objectMapper.readValue(result.getResponse().getContentAsString(), ProjectResponseDto[].class);

        assertEquals(expectedList.size(), actual.length);
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), actual[i]);
        }


    }
}
