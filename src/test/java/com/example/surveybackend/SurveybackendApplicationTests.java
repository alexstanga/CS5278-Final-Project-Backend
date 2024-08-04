package com.example.surveybackend;

import com.example.surveybackend.jpa.ResultRepository;
import com.example.surveybackend.jpa.SurveyRepository;
import com.example.surveybackend.pdf.CompositePDFComponent;
import com.example.surveybackend.pdf.PDFBuilder;
import com.example.surveybackend.pdf.PDFComponent;

import com.example.surveybackend.survey.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SurveybackendApplicationTests {

	@Autowired
	private MockMvc mockMvc;  // Inject MockMvc for web layer testing

	@MockBean
	private SurveyJpaService surveyJpaService;  // Mock service dependency

	@MockBean
	private SurveyRepository surveyRepository;  // Mock repository dependency

	@MockBean
	private ResultRepository resultRepository;

	private final String testFilePath = "Survey_Results_1.pdf";
	private String testJSON = "[{\"id\":null,\"type\":\"radiogroup\",\"name\":\"quetion1\",\"title\":\"Question1\",\"choices\":[{\"text\":\"Option A\",\"value\":1},{\"text\":\"Option B\",\"value\":2},{\"text\":\"Option C\",\"value\":3}]},{\"id\":null,\"type\":\"text\",\"name\":\"question2\",\"title\":\"Question2\"},{\"id\":null,\"type\":\"radiogroup\",\"name\":\"quetion3\",\"title\":\"Question3\",\"choices\":[{\"text\":\"Option A\",\"value\":1},{\"text\":\"Option B\",\"value\":2},{\"text\":\"Option C\",\"value\":3}]},{\"id\":null,\"type\":\"text\",\"name\":\"question4\",\"title\":\"Question4\"},{\"id\":null,\"type\":\"radiogroup\",\"name\":\"quetion5\",\"title\":\"Question5\",\"choices\":[{\"text\":\"Option A\",\"value\":1},{\"text\":\"Option B\",\"value\":2},{\"text\":\"Option C\",\"value\":3}]},{\"id\":null,\"type\":\"text\",\"name\":\"question6\",\"title\":\"Question6\"},{\"id\":null,\"type\":\"radiogroup\",\"name\":\"quetion7\",\"title\":\"Question7\",\"choices\":[{\"text\":\"Option A\",\"value\":1},{\"text\":\"Option B\",\"value\":2},{\"text\":\"Option C\",\"value\":3}]},{\"id\":null,\"type\":\"text\",\"name\":\"question8\",\"title\":\"Question8\"},{\"id\":null,\"type\":\"radiogroup\",\"name\":\"quetion9\",\"title\":\"Question9\",\"choices\":[{\"text\":\"Option A\",\"value\":1},{\"text\":\"Option B\",\"value\":2},{\"text\":\"Option C\",\"value\":3}]},{\"id\":null,\"type\":\"text\",\"name\":\"question10\",\"title\":\"Question10\"}]";
	private String testResult = "{\n" +
			"   \"result\": {\n" +
			"      \"id\": 1,\n" +
			"      \"resultResponses\": [\n" +
			"         {\n" +
			"            \"id\": 1,\n" +
			"            \"question\": \"phq-1\",\n" +
			"            \"response\": \"Several days\",\n" +
			"            \"score\": 1\n" +
			"         },\n" +
			"         {\n" +
			"            \"id\": 2,\n" +
			"            \"question\": \"phq-6\",\n" +
			"            \"response\": \"Several days\",\n" +
			"            \"score\": 1\n" +
			"         },\n" +
			"         {\n" +
			"            \"id\": 3,\n" +
			"            \"question\": \"gad-1\",\n" +
			"            \"response\": \"Several days\",\n" +
			"            \"score\": 1\n" +
			"         },\n" +
			"         {\n" +
			"            \"id\": 4,\n" +
			"            \"question\": \"gad-4\",\n" +
			"            \"response\": \"Nearly every day\",\n" +
			"            \"score\": 3\n" +
			"         },\n" +
			"         {\n" +
			"            \"id\": 5,\n" +
			"            \"question\": \"gad-7\",\n" +
			"            \"response\": \"Nearly every day\",\n" +
			"            \"score\": 3\n" +
			"         }\n" +
			"      ],\n" +
			"      \"json\": null,\n" +
			"      \"categoryScores\": {\n" +
			"         \"phq\": 2,\n" +
			"         \"gad\": 7\n" +
			"      }\n" +
			"   },\n" +
			"   \"location\": \"http://localhost:8080/jpa/surveys/1/results/1\"\n" +
			"}";

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	// Test 1. Testing creating a new survey works.
	@Test
	public void testCreateSurvey() throws Exception {
		Survey survey = new Survey();
		survey.setName("Survey Name");
		survey.setJson(testJSON);

		when(surveyJpaService.createSurvey(any(Survey.class))).thenReturn(survey);

		mockMvc.perform(post("/surveys")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"name\": \"Survey Name\", \"json\": \"testJSON\"}"))
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", "http://localhost/surveys/2"));

	}

	// Test 2. Testing creating 10 surveys and they all save
	@Test
	public void testCreateMultipleSurveys() throws Exception {
		Survey survey = new Survey();
		survey.setName("Survey Name");
		survey.setJson("testJSON"); // Example field

		when(surveyJpaService.createSurvey(any(Survey.class))).thenAnswer(invocation -> {
			Survey createdSurvey = invocation.getArgument(0);
			createdSurvey.setId(1 + (int) (Math.random() * 1000)); // Mocking ID assignment
			return createdSurvey;
		});

		// Create 10 surveys
		for (int i = 0; i < 10; i++) {
			mockMvc.perform(post("/surveys")
							.contentType(MediaType.APPLICATION_JSON)
							.content("{\"name\": \"Survey Name " + i + "\", \"json\": \"testJSON\"}"))
					.andExpect(status().isCreated());
		}

		// Check the size of the number of surveys
		mockMvc.perform(get("/surveys")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(12)); // 10 + 1 we created in test 1 and + 1 in the init invocation
	}

	// Test 3. Test deleting a survey
	@Test
	public void deleteSurvey() throws Exception {
		doNothing().when(surveyRepository).deleteById(1);

		mockMvc.perform(delete("/jpa/surveys/1"))
				.andExpect(status().isOk());
	}

	// Test 4. Test Create Result for survey
	@Test
	public void testCreateResultForSurvey() throws Exception {
		// Prepare the request body
		Map<String, Map<String, ResultResponseDto>> responses = new HashMap<>();
		Map<String, ResultResponseDto> phq9Responses = new HashMap<>();
		phq9Responses.put("phq-1", new ResultResponseDto("Several days", 1));
		phq9Responses.put("phq-2", new ResultResponseDto("More than half the days", 2));
		// Add more responses as needed
		responses.put("phq-9", phq9Responses);

		Result result = new Result();  // Prepare a mock result
		result.setId(1);

		// Mock the service methods
		when(surveyJpaService.saveResult(anyInt(), anyMap())).thenReturn(result);
		doNothing().when(surveyJpaService).calculateTotalScore(anyInt());

		// Perform the POST request
		MvcResult mvcResult = mockMvc.perform(post("/jpa/surveys/1/results")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(responses)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.result.id").value(1))
				.andExpect(jsonPath("$.location").value("http://localhost/jpa/surveys/1/results/1"))
				.andReturn();

		// Check the location header
		String locationHeader = mvcResult.getResponse().getHeader("Location");
		assertNotNull(locationHeader);
		assertTrue(locationHeader.contains("/jpa/surveys/1/results/1"));
	}

	// Test 5. Test retrieving results for survey
	@Test
	public void testRetrieveResultsForSurvey() throws Exception {
		// Prepare mock results
		Result result1 = new Result();
		result1.setId(1);
		Result result2 = new Result();
		result2.setId(2);
		List<Result> results = new ArrayList<>();
		results.add(result1);
		results.add(result2);

		// Prepare mock survey
		Survey survey = new Survey();
		survey.setId(1);
		survey.setResults(results);

		// Mock the repository
		when(surveyRepository.findById(1)).thenReturn(Optional.of(survey));

		// Perform the GET request
		mockMvc.perform(get("/jpa/surveys/1/results"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[1].id").value(2));
	}

	// Test 6. Test retrieving results for survey not found
	@Test
	public void testRetrieveResultsForSurveyNotFound() throws Exception {
		// Mock the repository to return empty
		when(surveyRepository.findById(1)).thenReturn(Optional.empty());

		// Perform the GET request
		mockMvc.perform(get("/jpa/surveys/1/results"))
				.andExpect(status().isNotFound());
	}

	// Test 7. Test downloading the pdf
	@Test
	public void testRetrieveSurveyPDF() throws Exception {
		// Perform the GET request to download the PDF
		mockMvc.perform(get("/jpa/download/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + testFilePath))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	// Test 8. drawing using the CompositePDFComponent
	@Test
	public void testDraw() throws IOException {
		// Arrange
		CompositePDFComponent composite = new CompositePDFComponent();
		PDFComponent component1 = mock(PDFComponent.class);
		PDFComponent component2 = mock(PDFComponent.class);

		composite.addComponent(component1);
		composite.addComponent(component2);

		PDPageContentStream contentStream = mock(PDPageContentStream.class);
		PDFBuilder builder = mock(PDFBuilder.class);

		// Act
		composite.draw(contentStream, builder);

		// Assert
		verify(component1).draw(contentStream, builder);
		verify(component2).draw(contentStream, builder);
	}

}
