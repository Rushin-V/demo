package com.dataprocessing.demo;

import com.dataprocessing.controller.DataProcessingController;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@WebAppConfiguration("src/test/webapp")
@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class DataProcessingApplicationTests {

	/** The Data Processing Controller */
	@Autowired
	DataProcessingController dataProcessingController;

	/** The Mock MVC */
	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		mockMvc = standaloneSetup(dataProcessingController).build();
	}

	@Test
	public void processCSVTest() throws Exception {

		String content = "Employee Id,Employee Name,Age,Country\n" +
				"23400,Rushin Test,30,India\n" +
				"23420,Sagar Test,31,China\n" +
				"23421,Saviz Test,32,Japan\n" +
				"501, New Joinee Test,32,Japan\n"+
				"502, Kevin Test,thirty two,Japan\n";
		MockMultipartFile file = new MockMultipartFile("file", "employee.csv", "text/csv",content.getBytes());
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.multipart("/processCSV").file(file)).
				andExpect(MockMvcResultMatchers.status().isCreated());
		String responseContent = result.andReturn().getResponse().getContentAsString();
		Assert.assertTrue(responseContent.contains("\"saveRecords\":4"));
		Assert.assertTrue(responseContent.contains("\"skipRecords\":1"));
	}

}
