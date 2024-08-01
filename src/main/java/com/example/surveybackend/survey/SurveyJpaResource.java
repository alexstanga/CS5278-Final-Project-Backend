package com.example.surveybackend.survey;

import com.example.surveybackend.jpa.ResultResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import com.example.surveybackend.jpa.ResultRepository;
import com.example.surveybackend.jpa.SurveyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class SurveyJpaResource {

    @Autowired
    private SurveyJpaService surveyJpaService;
    @Autowired
    private SurveyRepository surveyRepository;

    public SurveyJpaResource(SurveyJpaService surveyJpaService, SurveyRepository surveyRepository) {
        this.surveyJpaService = surveyJpaService;
        this.surveyRepository = surveyRepository;
    }

    @GetMapping("/jpa/surveys/init")
    public void initSurvey(){
        surveyJpaService.init();
    }

    @GetMapping("/jpa/surveys")
    public List<Survey> retrieveAllSurveys() {
        return surveyRepository.findAll();
    }

    @GetMapping("/jpa/surveys/{id}")
    public EntityModel<Survey> retrieveSurvey(@PathVariable int id) {
        Optional<Survey> survey = surveyRepository.findById(id);

        if(survey.isEmpty()) throw new SurveyNotFoundException("id: " + id);

        EntityModel<Survey> entityModel = EntityModel.of(survey.get());

        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllSurveys());
        entityModel.add(link.withRel("all-surveys"));

        return entityModel;
    }

    @DeleteMapping("/jpa/surveys/{id}")
    public void deleteSurvey(@PathVariable int id) {
        surveyRepository.deleteById(id);
    }

    @PostMapping("/jpa/surveys")
    public ResponseEntity<Survey> createSurvey(@RequestBody Survey survey) {
        Survey savedSurvey = surveyRepository.save(survey);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedSurvey.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping("/jpa/surveys/{id}/results")
    public ResponseEntity<Object> createResultForSurvey(@PathVariable int id, @RequestBody Map<String, String> responses){
        try {
            Result result = surveyJpaService.saveResult(id, responses);

            // Construct the URI for the newly created result
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{resultId}")
                    .buildAndExpand(result.getId())
                    .toUri();

            // Return 201 Created status with the Location header set to the URI of the new resource
            return ResponseEntity.created(location).build();
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/jpa/surveys/{id}/results")
    public ResponseEntity<List<Result>> retrieveResultsForSurvey(@PathVariable int id) {
        Optional<Survey> survey = surveyRepository.findById(id);
        if (survey.isPresent()) {
            List<Result> results = survey.get().getResults();
            return ResponseEntity.ok(results);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
