package imf.virtualpet.service;

import imf.virtualpet.dto.VirtualPetCreationRequestDTO;
import imf.virtualpet.domain.virtualpet.VirtualPet;
import imf.virtualpet.dto.VirtualPetResponseDTO;
import imf.virtualpet.repository.VirtualPetRepository;
import imf.virtualpet.dto.VirtualPetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class VirtualPetService {
    private final VirtualPetRepository virtualPetRepository;

    public Flux<VirtualPet> findPetsByUsername(String username) {
        return virtualPetRepository.findByOwnerUsername(username);
    }

    public Mono<VirtualPet> findPetByNameAndUsername(String name, String username) {
        return virtualPetRepository.findByNameAndOwnerUsername(name, username);
    }

    public Mono<VirtualPet> createPet(VirtualPetCreationRequestDTO virtualPetCreationRequestDTO, String ownerUsername) {
        VirtualPet virtualPet = VirtualPetMapper.fromCreationDTOToEntity(virtualPetCreationRequestDTO);
        virtualPet.setOwnerUsername(ownerUsername);
        return virtualPetRepository.save(virtualPet);
    }

    public Mono<Void> deletePetIfOwner(String petId, String username) {
        return virtualPetRepository.findById(petId)
                .filter(virtualPet -> virtualPet.getOwnerUsername().equals(username))
                .flatMap(virtualPetRepository::delete);
    }

    public Mono<VirtualPet> findPet(String name) {
        return virtualPetRepository.findByName(name);
    }

    public Flux<VirtualPet> findAllPets() {
        return virtualPetRepository.findAll();
    }

    public Mono<Void> deletePet(String petId) {
        return virtualPetRepository.deleteById(petId);
    }

    public Mono<VirtualPetResponseDTO> feedPet(String petId) {
        return virtualPetRepository.findById(petId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found with id " + petId)))
                .flatMap(pet -> {
                    pet.setIsHungry(false);
                    return virtualPetRepository.save(pet);
                })
                .map(VirtualPetResponseDTO::fromEntity);
    }

    public Mono<VirtualPetResponseDTO> petPet(String petId) {
        return virtualPetRepository.findById(petId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found with id " + petId)))
                .flatMap(pet -> {
                    pet.setIsHappy(true);
                    return virtualPetRepository.save(pet);
                })
                .map(VirtualPetResponseDTO::fromEntity);
    }

}
