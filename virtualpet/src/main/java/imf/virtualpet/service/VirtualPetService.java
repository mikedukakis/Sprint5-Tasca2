package imf.virtualpet.service;

import imf.virtualpet.entity.VirtualPet;
import imf.virtualpet.repository.VirtualPetRepository;

import lombok.Data;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Data
@Service
public class VirtualPetService {
    private VirtualPetRepository virtualPetRepository;

    public VirtualPetService(VirtualPetRepository virtualPetRepository) {
        this.virtualPetRepository = virtualPetRepository;
    }

    public Mono<VirtualPet> createPet(VirtualPet virtualPet) {
        return virtualPetRepository.save(virtualPet);
    }

    public Flux<VirtualPet> getAllPets(String userId) {
        return virtualPetRepository.findAllByUserId(userId);
    }

    public Mono<VirtualPet> updatePet(String petId, VirtualPet updatedPet) {
        return virtualPetRepository.findById(petId)
                .flatMap(existingPet -> {
                    existingPet.setName(updatedPet.getName());
                    existingPet.setPetType(updatedPet.getPetType());
                    existingPet.setColour(updatedPet.getColour());
                    return virtualPetRepository.save(existingPet);
                });
    }

    public Mono<Void> deletePet(String petId) {
        return virtualPetRepository.deleteById(petId);
    }
}
