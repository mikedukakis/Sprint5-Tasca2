package imf.virtualpet.domain.virtualpet.dto;

import imf.virtualpet.domain.virtualpet.entity.PetType;
import imf.virtualpet.domain.virtualpet.entity.VirtualPet;
import lombok.Data;

@Data
public class VirtualPetResponseDTO {
    private String id;
    private String name;
    private String ownerUsername;
    private PetType petType;
    private String colour;
    private Boolean isHungry;
    private Boolean isHappy;

    public VirtualPetResponseDTO(String id, String ownerUsername, String name, PetType petType, String colour, Boolean isHungry, Boolean isHappy) {
        this.id = id;
        this.name = name;
        this.ownerUsername = ownerUsername;
        this.petType = petType;
        this.colour = colour;
        this.isHungry = isHungry;
        this.isHappy = isHappy;
    }

    public static VirtualPetResponseDTO fromEntity(VirtualPet pet) {
        return new VirtualPetResponseDTO(pet.getId(), pet.getName(), pet.getOwnerUsername(), pet.getPetType(), pet.getColour(), pet.isHungry(), pet.isHappy());
    }
}
