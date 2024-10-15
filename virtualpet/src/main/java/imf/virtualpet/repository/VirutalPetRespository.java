package imf.virtualpet.repository;

import imf.virtualpet.entity.VirtualPet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VirutalPetRespository extends ReactiveMongoRepository<VirtualPet, String> {
}
