package com.disney.studios;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import com.disney.studios.dto.PetDTO;
import com.disney.studios.entities.Pet;
import com.disney.studios.entities.Vote;
import com.disney.studios.entities.VoteType;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Map;

/**
 * Bootstraps the Spring Boot com.disney.studios.Application
 *
 * Created by fredjean on 9/21/15.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ModelMapper modelMapper(List<Converter> converters) {
        ModelMapper modelMapper = new ModelMapper();
        converters.forEach(modelMapper::addConverter);
        return modelMapper;
    }

    @Bean
    public Converter<Pet, PetDTO> petDTOConverter() {
        return new Converter<Pet, PetDTO>() {
            @Override
            public PetDTO convert(MappingContext<Pet, PetDTO> mappingContext) {
                PetDTO dto = mappingContext.getDestination();
                Pet source = mappingContext.getSource();
                Map<VoteType, Long> counts = source.getVotes().stream().collect(groupingBy(Vote::getVoteType, counting()));
                dto.setId(source.getId());
                dto.setImageURL(source.getImageURL());
                dto.setName(source.getName());
                dto.setUpVotes(counts.getOrDefault(VoteType.UP, 0L).intValue());
                dto.setDownVotes(counts.getOrDefault(VoteType.DOWN, 0L).intValue());
                dto.setBreed(source.getBreed().getName());
                return dto;
            }
        };
    }
}
