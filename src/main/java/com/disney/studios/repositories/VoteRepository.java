package com.disney.studios.repositories;

import com.disney.studios.entities.Vote;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ulises Bocchio, Sergio.U.Bocchio@Disney.com (BOCCS002)
 */
@Repository
public interface VoteRepository extends PagingAndSortingRepository<Vote, Long> {

}
