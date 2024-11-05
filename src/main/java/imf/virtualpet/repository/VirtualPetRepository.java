package imf.virtualpet.repository;

import imf.virtualpet.domain.virtualpet.VirtualPet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VirtualPetRepository extends ReactiveMongoRepository<VirtualPet, String> {
    Mono<VirtualPet> findByName(String name);

    Mono<VirtualPet> findByNameAndOwnerUsername(String name, String ownerUsername);

    Flux<VirtualPet> findByOwnerUsername(String username);
}
