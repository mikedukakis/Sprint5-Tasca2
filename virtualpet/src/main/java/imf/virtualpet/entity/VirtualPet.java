package imf.virtualpet.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class VirtualPet {
    @Id
    private String id;
    private String name;
    private PetType petType;
    private String colour;


}
