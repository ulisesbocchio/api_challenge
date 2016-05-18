package com.disney.studios.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @author Ulises Bocchio, Sergio.U.Bocchio@Disney.com (BOCCS002)
 */
@Entity
@Data
@ToString(exclude = "pets")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Breed {

    @Id
    @NonNull
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "breed")
    List<Pet> pets;

    public void addPet(Pet pet) {
        if(pets == null) {
            pets = new ArrayList<>();
        }
        pets.add(pet);
    }
}
