package com.github.hardlolmaster;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hardlolmaster.actions.FindIndividualsByNameAction;
import com.github.hardlolmaster.controller.CommandObject;
import com.github.hardlolmaster.controller.ResponseObject;
import com.github.hardlolmaster.domain.Individual;
import com.github.hardlolmaster.repository.IndividualRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FrontControllerTest {
    @Autowired
    private IndividualRepository individualRepository;
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser
    public void testCommandObject() throws Exception {
        CommandObject commandObject = new CommandObject();
        char[] simpleName = FindIndividualsByNameAction.class.getSimpleName().toCharArray();
        simpleName[0] = Character.toLowerCase(simpleName[0]);
        commandObject.setCommand(String.valueOf(simpleName));
        Individual payload = new Individual();
        payload.setFirstName("FirstName");
        payload.setLastName("LastName");
        individualRepository.save(payload);
        commandObject.setPayload(payload);

        MvcResult result = sendRequest(commandObject);

        String contentAsString = result.getResponse().getContentAsString();
        assertNotNull(contentAsString);
        assertTrue(contentAsString.length() > 0);

        ResponseObject<?> responseObject = mapper.readValue(contentAsString, ResponseObject.class);
        assertTrue(responseObject.isOk());

        List<?> list = mapper.convertValue(responseObject.getPayload(), List.class);
        Individual individual = mapper.convertValue(list.get(0), Individual.class);
        assertEquals(payload, individual);
    }

    @Test
    @WithMockUser
    public void testNullCommandObject() throws Exception {
        {
            MvcResult result = sendRequest(new CommandObject());
            String contentAsString = result.getResponse().getContentAsString();
            assertNotNull(contentAsString);
            assertTrue(contentAsString.length() > 0);
            ResponseObject<?> responseObject = mapper.readValue(contentAsString, ResponseObject.class);
            assertFalse(responseObject.isOk());
        }
        {
            CommandObject commandObject = new CommandObject();
            char[] simpleName = FindIndividualsByNameAction.class.getSimpleName().toCharArray();
            simpleName[0] = Character.toLowerCase(simpleName[0]);
            commandObject.setCommand(String.valueOf(simpleName));

            MvcResult result = sendRequest(commandObject);

            String contentAsString = result.getResponse().getContentAsString();
            assertNotNull(contentAsString);
            assertTrue(contentAsString.length() > 0);

            ResponseObject<?> responseObject = mapper.readValue(contentAsString, ResponseObject.class);
            assertFalse(responseObject.isOk());
        }
    }

    private MvcResult sendRequest(CommandObject commandObject) throws Exception {
        MockHttpServletRequestBuilder request = post("/property.insurance")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .content(mapper.writeValueAsString(commandObject));
        return mockMvc.perform(request).andDo(print()).andExpect(status().isOk()).andReturn();
    }
}
