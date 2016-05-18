package com.disney.studios;

import com.disney.studios.entities.Breed;
import com.disney.studios.entities.Pet;
import com.disney.studios.entities.User;
import com.disney.studios.repositories.BreedRepository;
import com.disney.studios.repositories.PetRepository;
import com.disney.studios.repositories.UserRepository;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

/**
 * Loads stored objects from the file system and builds up the appropriate objects to add to the data source.
 *
 * Created by fredjean on 9/21/15.
 */
@Component
@Slf4j
public class PetLoader implements InitializingBean {

    // Resources to the different files we need to load.
    @Value("#{@resourceResolver.getResources('classpath:data/*.txt')}")
    private List<Resource> breeds;

    @Value("classpath:/data/users/users.txt")
    private Resource users;

    @Autowired
    PetRepository dogRepo;

    @Autowired
    BreedRepository breedRepo;

    @Autowired
    UserRepository userRepo;

    @Bean
    public ResourcePatternResolver resourceResolver() {
        return new PathMatchingResourcePatternResolver();
    }


    /**
     * Load the different breeds into the data source after the application is ready.
     *
     * @throws Exception In case something goes wrong while we load the breeds.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        loadUsers();
        loadBreeds();
        printData();
    }

    private void printData() {
        printEntities(userRepo, "Users");
        printEntities(breedRepo, "Breeds");
        printEntities(dogRepo, "Dogs");
    }

    private void printEntities(CrudRepository<?, ?> repo, String message) {
        log.info("*****{}*******", message);
        repo.findAll().forEach(e -> log.info(e.toString()));
        log.info("****************");
    }

    private void loadUsers() throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(users.getInputStream()))) {
            br.lines().forEach(userName -> userRepo.save(new User(userName)));
        }
    }

    private void loadBreeds() {
        breeds.forEach(breed -> loadBreed(StringUtils.capitalize(StringUtils.stripFilenameExtension(breed.getFilename())), breed));
    }

    /**
     * Reads the list of dogs in a category and (eventually) add them to the data source.
     *
     * @param breed  The breed that we are loading.
     * @param source The file holding the breeds.
     * @throws IOException In case things go horribly, horribly wrong.
     */
    @SneakyThrows
    private void loadBreed(String breed, Resource source) {
        log.info("Loading Breed: {}", breed);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(source.getInputStream()))) {
            br.lines().forEach(imageUrl -> loadDog(breed, imageUrl));
        }
    }

    private void loadDog(String breed, String imageUrl) {
        log.info("Loading Dog Image: {}", imageUrl);
        Pet pet = new Pet();
        pet.setImageURL(imageUrl);
        Breed breedEntity = getOrCreateBreed(breed);
        pet.setBreed(breedEntity);
        breedEntity.addPet(pet);
        breedRepo.save(breedEntity);
    }

    private Breed getOrCreateBreed(String breed) {
        return Optional.ofNullable(breedRepo.findByNameIgnoreCase(breed))
                .orElseGet(() -> breedRepo.save(new Breed(breed)));
    }
}
