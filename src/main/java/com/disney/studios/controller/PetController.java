package com.disney.studios.controller;

import static java.util.stream.Collectors.toList;

import com.disney.studios.dto.PetDTO;
import com.disney.studios.dto.VoteDTO;
import com.disney.studios.entities.Pet;
import com.disney.studios.entities.Vote;
import com.disney.studios.repositories.PetRepository;

import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * @author Ulises Bocchio, Sergio.U.Bocchio@Disney.com (BOCCS002)
 */
@RestController
@RequestMapping("/pets")
@Slf4j
public class PetController {

    @Autowired
    PetRepository petRepository;

    @Autowired
    ModelMapper mapper;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<PetDTO>> getPets(Pageable pageable) {
        Page<Pet> allPets = petRepository.findAll(pageable);
        Page<PetDTO> allPetDTOs = allPets.map(p -> mapper.map(p, PetDTO.class));
        return ResponseEntity.ok(allPetDTOs);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{petId}")
    public ResponseEntity<PetDTO> getPet(@PathVariable Long petId) {
        Pet pet = petRepository.findOne(petId);
        PetDTO dto = mapper.map(pet, PetDTO.class);
        return ResponseEntity.ok(dto);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{petId}/votes")
    public ResponseEntity<List<VoteDTO>> getPetVotes(@PathVariable Long petId) {
        Pet pet = petRepository.findOne(petId);
        log.info("Found {} votes for Pet {}", pet.getVotes().size(), pet.getId());
        List<VoteDTO> votes = pet.getVotes().stream().map(v -> mapper.map(v, VoteDTO.class)).collect(toList());
        return ResponseEntity.ok(votes);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{petId}/votes")
    public ResponseEntity<Void> vote(@PathVariable Long petId, @RequestBody VoteDTO voteDTO, UriComponentsBuilder uriBuilder) {
        Pet pet = petRepository.findOne(petId);
        if (pet != null) {
            Vote vote = mapper.map(voteDTO, Vote.class);
            //vote.setPet(pet);
            pet.addVote(vote);
            petRepository.save(pet);
            return ResponseEntity.created(uriBuilder.path("/pets/{id}/vote/{votId}").buildAndExpand(petId, vote.getId()).toUri()).build();
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/breed/{breedId}")
    public ResponseEntity<Page<PetDTO>> getPetsByBreed(@PathVariable String breedId, Pageable pageable) {
        Page<Pet> breedPets = petRepository.findByBreedNameIgnoreCase(breedId, pageable);
        Page<PetDTO> breedPetDTOs = breedPets.map(p -> mapper.map(p, PetDTO.class));
        return ResponseEntity.ok(breedPetDTOs);
    }
}
