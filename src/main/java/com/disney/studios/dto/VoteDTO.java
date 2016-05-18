package com.disney.studios.dto;

import com.disney.studios.entities.VoteType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ulises Bocchio, Sergio.U.Bocchio@Disney.com (BOCCS002)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDTO {

    VoteType voteType;
    UserDTO user;
}
