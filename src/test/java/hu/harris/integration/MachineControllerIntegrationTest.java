package hu.harris.integration;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import hu.harris.backendchallenge.BackendChallengeApplication;
import hu.harris.backendchallenge.dto.MachineDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = BackendChallengeApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
		  locations = "classpath:application-integrationtest.properties")
public class MachineControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;
	
	@Test
	public void postNewMachine_thenStatus201()
	  throws Exception {
		
		MachineDto machineDto = new MachineDto();
		machineDto.setId(UUID.randomUUID().toString());
		machineDto.setName("New machine");
		
	    mvc.perform(MockMvcRequestBuilders.post("/machines")
	    		.content(getJSONString(machineDto))
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isCreated())
	      .andExpect(content()
	      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      .andExpect(jsonPath("$.version", is(0)))
	      .andExpect(jsonPath("$.name", is(machineDto.getName())))
	      .andExpect(jsonPath("$.id", is(machineDto.getId())));
	}
	
	private String getJSONString(MachineDto machineDto) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    
	    return ow.writeValueAsString(machineDto);
	}
}
