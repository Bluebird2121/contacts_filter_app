package com.bluebird.contacts.controllers;

import com.bluebird.contacts.configs.AppConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppConfig.class)
@AutoConfigureMockMvc(secure=false)
public class ContactsCtrlTest {

    private static final String ENDPOINT_URL ="/hello/contacts";
    private static final String NAME_FILTER ="nameFilter=";
    private static final String PAGE_FILTER ="page=";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testFilterWithoutPageParameter() throws Exception {
        mockMvc.perform(get(ENDPOINT_URL+"?"+NAME_FILTER+"*"))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(is("Required int parameter 'page' is not present")));
    }

    @Test
    public void testFilterWithoutNameFilterParameter() throws Exception {
        mockMvc.perform(get(ENDPOINT_URL+"?"+PAGE_FILTER+"1"))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(is("Required String parameter 'nameFilter' is not present")));
    }

    @Test
    public void testWithBadNameFilterPattern() throws Exception {
        String urlTemplate = "/hello/contacts?page=0&nameFilter=(";
        mockMvc.perform(get(urlTemplate))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Filter value '(' is invalid."));
    }


}