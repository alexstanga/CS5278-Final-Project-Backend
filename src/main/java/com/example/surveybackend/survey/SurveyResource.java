package com.example.surveybackend.survey;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class SurveyResource {

    private SurveyDaoService service;

    public SurveyResource(SurveyDaoService service){
        this.service = service;
    }

    @GetMapping("/surveys")
    public List<Survey> retrieveAllSurveys() {
        return service.findAll();
    }

    @GetMapping("/surveys/{id}")
    public EntityModel<Survey> retrieveSurvey(@PathVariable int id) {
        Survey survey = service.findOne(id);

        if (survey==null) throw new SurveyNotFoundException("id: " + id);

        EntityModel<Survey> entityModel = EntityModel.of(survey);

        WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).retrieveAllSurveys());
        entityModel.add(link.withRel("all-surveys"));

        return entityModel;
    }

    @DeleteMapping("/surveys/{id}")
    public void deleteSurvey(@PathVariable int id) {
        service.deleteById(id);
    }

    @PostMapping("/surveys")
    public ResponseEntity<Survey> createSurvey(@RequestBody Survey survey) {
        Survey savedSurvey = service.save(survey);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedSurvey.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
