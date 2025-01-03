package pt.psoft.g1.psoftg1.shared.infraestructure.repositories.impl;

import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import pt.psoft.g1.psoftg1.shared.Constants;
import pt.psoft.g1.psoftg1.shared.model.Photo;
import pt.psoft.g1.psoftg1.shared.repositories.PhotoRepository;
import pt.psoft.g1.psoftg1.specifications.GenericSpecifications;

public class JPAPhotoRepositoryService extends SimpleJpaRepository<Photo,String> implements PhotoRepository{
    public JPAPhotoRepositoryService(EntityManager entityManager) {
        super(Photo.class, entityManager);
    }
    @Override
    public void deleteByPhotoFile(String photoFile) {
         super.delete(GenericSpecifications.equals(photoFile,Constants.PHOTO_FILE));
    }

    @Override
    public void deleteInBatch(Iterable<Photo> entities) {
        super.deleteAllInBatch(entities);
    }
}
