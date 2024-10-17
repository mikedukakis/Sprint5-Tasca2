package imf.virtualpet.controller;

import imf.virtualpet.entity.VirtualPet;
import imf.virtualpet.service.VirtualPetService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class VirtualPetController {
    private VirtualPetService virtualPetService;

    public VirtualPetController(VirtualPetService virtualPetService) {
        this.virtualPetService = virtualPetService;
    }

    @PostMapping
    public Mono<VirtualPet> createPet(@RequestBody VirtualPet virtualPet) {
        return virtualPetService.createPet(virtualPet);
    }

    @GetMapping("/{userId}")
    public Flux<VirtualPet> getAllPets(@PathVariable String userId) {
        return virtualPetService.getAllPets(userId);
    }

    @PutMapping("/{petId}")
    public Mono<VirtualPet> updatePet(@PathVariable String petId, @RequestBody VirtualPet updatedPet) {
        return virtualPetService.updatePet(petId, updatedPet);
    }

    @DeleteMapping("/{petId}")
    public Mono<Void> deletePet(@PathVariable String petId) {
        return virtualPetService.deletePet(petId);
    }
}
