package com.disney.studios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

/**
 * @author Ulises Bocchio, Sergio.U.Bocchio@Disney.com (BOCCS002)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetDTO {

    private Long id;
    @URL
    @NotNull
    private String imageURL;
    private String name;
    private String breed;
    int upVotes;
    int downVotes;
}
