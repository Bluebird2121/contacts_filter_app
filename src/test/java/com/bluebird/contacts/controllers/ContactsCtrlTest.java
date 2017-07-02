package com.bluebird.contacts.controllers;

import com.bluebird.contacts.configs.AppConfig;
import com.bluebird.contacts.services.ContactsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.regex.PatternSyntaxException;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppConfig.class)
@WebAppConfiguration
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class ContactsCtrlTest {

    private static final String ENDPOINT_URL = "/hello/contacts";
    private static final String NAME_FILTER = "nameFilter=";
    private static final String PAGE_FILTER = "page=";
    private static final String POPULATE_CONTACTS_URL = "/populate_contacts_data";

    private final StaticApplicationContext applicationContext = new StaticApplicationContext();
    private final WebMvcConfigurationSupport webMvcConfSupport = new WebMvcConfigurationSupport();

    @Mock
    private ContactsService mockContactsService;

    @InjectMocks
    private ContactsCtrl contactsCtrl;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        applicationContext.registerSingleton("exceptionHandler", ExceptionHandlerCtrl.class);
        webMvcConfSupport.setApplicationContext(applicationContext);
        this.mockMvc = MockMvcBuilders.standaloneSetup(contactsCtrl)
                .setHandlerExceptionResolvers(webMvcConfSupport.handlerExceptionResolver())
                .build();
    }

    @Test
    public void testFilterWithoutPageParameter() throws Exception {
        mockMvc.perform(get(ENDPOINT_URL + "?" + NAME_FILTER + "*"))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(is("Required int parameter 'page' is not present")));
    }

    @Test
    public void testFilterWithoutNameFilterParameter() throws Exception {
        mockMvc.perform(get(ENDPOINT_URL + "?" + PAGE_FILTER + "1"))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(is("Required String parameter 'nameFilter' is not present")));
    }

    @Test
    public void testFilterWithPatternSyntaxException() throws Exception {
        Mockito.when(mockContactsService.filterNameNotMatch(anyInt(), anyString()))
                .thenThrow(new PatternSyntaxException("Wrong pattern", "(", 0));
        mockMvc.perform(get(ENDPOINT_URL + "?" + PAGE_FILTER + "1&"+NAME_FILTER + "=("))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Filter value '=(' is invalid."));
    }

    @Test
    public void testFilterWhenAllParamsOk() throws Exception {
        mockMvc.perform(get(ENDPOINT_URL + "?" + PAGE_FILTER + "1&"+NAME_FILTER + "=(.*?)"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPopulateNotEmptyDatabase() throws Exception {
        String expectedMessage = "Can't populate not empty database.";
        Mockito.doThrow(new IllegalStateException(expectedMessage)).when(mockContactsService).populateContactsData();

        mockMvc.perform(get(ENDPOINT_URL + POPULATE_CONTACTS_URL))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(expectedMessage));
    }

    @Test
    public void testPopulateEmptyDatabase() throws Exception {
        mockMvc.perform(get(ENDPOINT_URL + POPULATE_CONTACTS_URL))
                .andExpect(status().isOk());
    }
}