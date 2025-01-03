package pt.psoft.g1.psoftg1.shared.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import pt.psoft.g1.psoftg1.interfaces.GetID;

import java.nio.file.Path;

@Entity
public class Photo implements GetID<String>{
    @Id
    //@GeneratedValue(strategy= GenerationType.AUTO)
    @UuidGenerator
    private String pk;

    @NotNull
    @Setter
    @Getter
    private String photoFile;

    protected Photo (){}

    public Photo (Path photoPath){
        setPhotoFile(photoPath.toString());
    }

    @Override
    public String getID() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getID'");
    }
}

