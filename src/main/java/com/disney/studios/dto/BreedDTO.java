package com.disney.studios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ulises Bocchio, Sergio.U.Bocchio@Disney.com (BOCCS002)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BreedDTO {
    private String name;
    List<PetDTO> pets;
}
