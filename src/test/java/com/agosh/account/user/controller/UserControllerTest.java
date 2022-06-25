package com.agosh.account.user.controller;

import com.agosh.account.user.model.User;
import com.agosh.account.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerTest {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    LocalDate localDate =LocalDate.now();

    Date  date =   subtractDaysFromDate(String.valueOf(localDate), "200");



    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    UserControllerTest() throws ParseException {
    }


    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() throws Exception {

        List<User> userList = new ArrayList<>();
        userList.add(User.builder().firstName("Ramesh").lastName("John").email("ramesh@gmail.com").dob(date).build());
        userList.add(User.builder().firstName("Tony").lastName("Stark").email("tony@gmail.com").dob(date).build());
        userRepository.saveAll(userList);
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/users"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(userList.size())));

    }

    @Test
    void newUser() throws Exception {

        User user = User.builder()
                .firstName("suneel")
                .lastName("pervaiz")
                .email("suneel@gmail.com")
                .dob(date).build();

        ResultActions response = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName",
                        is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(user.getLastName())))
                .andExpect(jsonPath("$.email",
                        is(user.getEmail())));

    }

    @Test
    void updateUser() throws Exception {

        User user = User.builder()
                .firstName("suneel")
                .lastName("pervaiz")
                .email("suneel@gmail.com")
                .dob(date).build();
        userRepository.save(user);

        User updatedUser = User.builder()
                .firstName("adeel")
                .lastName("pervaiz")
                .email("adeel@gmail.com")
                .dob(date)
                .build();

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/users/{id}", user.getId(), updatedUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)));


        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(updatedUser.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedUser.getLastName())))
                .andExpect(jsonPath("$.email", is(updatedUser.getEmail())));

    }

    @Test
    void findOne() throws Exception {

        User user = User.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail.com")
                .dob(date)
                .build();
        userRepository.save(user);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/users/{id}", user.getId()));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));

    }


    private static Date subtractDaysFromDate(String date, String days) throws ParseException {
        Calendar c = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        try {
            Date myDate = df.parse(date.trim());
            c.setTime(myDate);
            c.add(Calendar.DATE, (Integer.parseInt(days) * -1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String toDate = df.format(c.getTime());
        Date date1=new SimpleDateFormat(DATE_FORMAT).parse(toDate);

        return date1;
    }
}