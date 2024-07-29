package com.example.surveybackend.survey;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import com.example.surveybackend.jpa.ResultRepository;
import com.example.surveybackend.jpa.SurveyRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class SurveyJpaResource {

    private SurveyRepository surveyRepository;
    private ResultRepository resultRepository;

    public SurveyJpaResource(SurveyRepository surveyRepository, ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
        this.surveyRepository = surveyRepository;
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

    @GetMapping("/jpa/surveys/{id}/results")
    public List<Result> retrieveResultsForSurvey(@PathVariable int id) {
        Optional<Survey> survey = surveyRepository.findById(id);

        if(survey.isEmpty()) throw new SurveyNotFoundException("id: " + id);
        return survey.get().getResults();
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
    public ResponseEntity<Object> createResultForSurvey(@PathVariable int id, @RequestBody Result result){
        Optional<Survey> survey = surveyRepository.findById(id);

        if(survey.isEmpty()) throw new SurveyNotFoundException("id: " + id);

        result.setSurvey(survey.get());

        Result savedResult = resultRepository.save(result);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedResult.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
