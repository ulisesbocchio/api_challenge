package com.disney.studios.repositories;

import com.disney.studios.entities.Pet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ulises Bocchio, Sergio.U.Bocchio@Disney.com (BOCCS002)
 */
@Repository
public interface PetRepository extends PagingAndSortingRepository<Pet, Long> {
    Page<Pet> findByBreedNameIgnoreCase(String breedId, Pageable pageable);

}
