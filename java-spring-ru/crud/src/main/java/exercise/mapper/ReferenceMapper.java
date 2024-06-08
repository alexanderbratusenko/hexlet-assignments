package exercise.mapper;

import exercise.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;

import exercise.model.BaseEntity;
import jakarta.persistence.EntityManager;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public abstract class ReferenceMapper {

    @Autowired
    private EntityManager entityManager;

    public <T extends BaseEntity> T map(Long id , @TargetType Class<T> entityClass) {
        if(id == null) {
            throw new ResourceNotFoundException("No entity found with id " + id);
        }
        try {
            return entityManager.find(entityClass, id);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("No entity found with id " + id);
        }
    }
}