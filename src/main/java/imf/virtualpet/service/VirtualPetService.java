package imf.virtualpet.service;

import imf.virtualpet.dto.VirtualPetCreationRequestDTO;
import imf.virtualpet.domain.virtualpet.VirtualPet;
import imf.virtualpet.repository.VirtualPetRepository;
import imf.virtualpet.dto.VirtualPetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

}
