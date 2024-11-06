package imf.virtualpet.dto;

import imf.virtualpet.domain.virtualpet.VirtualPet;

public class VirtualPetMapper {
    public static VirtualPet fromCreationDTOToEntity(VirtualPetCreationRequestDTO virtualPetCreationRequestDTO) {
        VirtualPet virtualPet = new VirtualPet(
                virtualPetCreationRequestDTO.getName(),
                virtualPetCreationRequestDTO.getOwnerUsername(),
                virtualPetCreationRequestDTO.getPetType(),
                virtualPetCreationRequestDTO.getColour()
        );
        virtualPet.setIsHungry(virtualPetCreationRequestDTO.isHungry());
        virtualPet.setIsHappy(virtualPetCreationRequestDTO.isHappy());
        return virtualPet;
    }

    public static VirtualPet fromResponseDTOToEntity(VirtualPetResponseDTO virtualPetResponseDTO) {
        VirtualPet virtualPet = new VirtualPet(
                virtualPetResponseDTO.getName(),
                virtualPetResponseDTO.getOwnerUsername(),
                virtualPetResponseDTO.getPetType(),
                virtualPetResponseDTO.getColour()
        );
        virtualPet.setIsHungry(virtualPetResponseDTO.getIsHungry());
        virtualPet.setIsHappy(virtualPetResponseDTO.getIsHappy());
        return virtualPet;
    }

}
