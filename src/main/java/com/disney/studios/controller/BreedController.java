package com.disney.studios.controller;

import com.disney.studios.dto.BreedDTO;
import com.disney.studios.repositories.BreedRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ulises Bocchio, Sergio.U.Bocchio@Disney.com (BOCCS002)
 */
@RestController
@RequestMapping("/breeds")
public class BreedController {

    @Autowired
    BreedRepository breedRepository;

    @Autowired
    ModelMapper modelMapper;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<BreedDTO>> getBreeds(Pageable pageable) {
        Page<BreedDTO> breeds = breedRepository.findAll(pageable).map(b -> modelMapper.map(b, BreedDTO.class));
        return ResponseEntity.ok(breeds);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{breedId}")
    public ResponseEntity<BreedDTO> getBreedPets(@PathVariable("breedId") String breedId) {
        BreedDTO breed = modelMapper.map(breedRepository.findByNameIgnoreCase(breedId), BreedDTO.class);
        return ResponseEntity.ok(breed);
    }

}
