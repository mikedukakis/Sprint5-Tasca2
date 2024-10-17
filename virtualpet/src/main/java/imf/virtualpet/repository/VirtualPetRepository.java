package imf.virtualpet.repository;

import imf.virtualpet.entity.VirtualPet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VirtualPetRepository extends ReactiveMongoRepository<VirtualPet, String> {
    Mono<VirtualPet> findById(String virtualPetId);
    Flux<VirtualPet> findAllByUserId(String userId);
}
