package imf.virtualpet.domain.virtualpet.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Random;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "pets")
public class VirtualPet {
    @Id
    private String id;
    @Field("name")
    private String name;
    @Field("owner")
    private String ownerUsername;
    @Field("petType")
    private PetType petType;
    @Field("colour")
    private String colour;
    @Field("hunger")
    private boolean isHungry;
    @Field("happiness")
    private boolean isHappy;

    public VirtualPet(String name, String ownerUsername, PetType petType, String colour) {
        this.name = name;
        this.ownerUsername = ownerUsername;
        this.petType = petType;
        this.colour = colour;
        this.isHungry = getRandomBool();
        this.isHappy = getRandomBool();
    }

    public static boolean getRandomBool() {
        Random randomNumber = new Random();
        int myRandNumber = randomNumber.nextInt(2);
        return myRandNumber == 1;
    }

}