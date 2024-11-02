package imf.virtualpet.domain.virtualpet.util;

import imf.virtualpet.domain.virtualpet.dto.VirtualPetResponseDTO;
import imf.virtualpet.domain.virtualpet.dto.VirtualPetCreationRequestDTO;
import imf.virtualpet.domain.virtualpet.entity.VirtualPet;

public class VirtualPetMapper {
    public static VirtualPet fromCreationDTOToEntity(VirtualPetCreationRequestDTO virtualPetCreationRequestDTO) {
        VirtualPet virtualPet = new VirtualPet(
                virtualPetCreationRequestDTO.getName(),
                virtualPetCreationRequestDTO.getOwnerUsername(),
                virtualPetCreationRequestDTO.getPetType(),
                virtualPetCreationRequestDTO.getColour()
        );
        virtualPet.setHungry(virtualPetCreationRequestDTO.isHungry());
        virtualPet.setHappy(virtualPetCreationRequestDTO.isHappy());
        return virtualPet;
    }

    public static VirtualPet fromResponseDTOToEntity(VirtualPetResponseDTO virtualPetResponseDTO) {
        VirtualPet virtualPet = new VirtualPet(
                virtualPetResponseDTO.getName(),
                virtualPetResponseDTO.getOwnerUsername(),
                virtualPetResponseDTO.getPetType(),
                virtualPetResponseDTO.getColour()
        );
        virtualPet.setHungry(virtualPetResponseDTO.getIsHungry());
        virtualPet.setHappy(virtualPetResponseDTO.getIsHappy());
        return virtualPet;
    }

}
