package com.disney.studios.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 * @author Ulises Bocchio, Sergio.U.Bocchio@Disney.com (BOCCS002)
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pet {

    @Id
    @GeneratedValue
    private Long id;
    @URL
    @NotNull
    private String imageURL;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    private Breed breed;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pet")
    List<Vote> votes;

    public void addVote(Vote vote) {
        if(votes == null) {
            votes = new ArrayList<>();
        }
        votes.add(vote);
    }
}
