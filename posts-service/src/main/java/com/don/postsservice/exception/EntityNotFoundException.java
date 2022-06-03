package com.don.postsservice.exception;

import com.don.postsservice.exception.entity.type.EEntity;
import io.github.wimdeblauwe.errorhandlingspringbootstarter.ResponseErrorProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception that handles all the NOT FOUND type of exceptions.
 * The actual response will be handled by the imported library.
 *
 * Example: when throw new EntityNotFoundException(EEntity.POST, postId), the response will be
 * {
 *   "code": "ENTITY_NOT_FOUND",
 *   "message": "POST with id: 789  was not found",
 *   "entityName": "POST"
 *   "entityId": "789"
 * }
 *
 *
 * @author Donald Veizi
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

    private Long entityId;
    private String entityName;
    private String userEmail;

    public EntityNotFoundException(final EEntity entity, final Long id) {
        super(String.format("%s with id: %s  was not found", entity.getEntityType(), id));
        this.entityId = id;
        this.entityName = entity.getEntityType();
    }

    public EntityNotFoundException(final EEntity entity, final String email) {
        super(String.format("%s with email: %s  was not found", entity.getEntityType(), email));
        // this case the enity will be USER
        this.userEmail = email;
        this.entityName = entity.getEntityType();
    }

    public EntityNotFoundException(final EEntity entity) {
        super(String.format("%s was not found", entity.getEntityType()));
        this.entityName = entity.getEntityType();
    }

    // add extra properties to the default response that the library builds.
    // if null, the field will not be shown (@ResponseErrorProperty(includeIfNull=true) if I want to include null the field even if value is null)
    @ResponseErrorProperty
    public String getEntityId() {
        return entityId.toString();
    }

    @ResponseErrorProperty
    public String getEntityName() {
        return entityName;
    }

    @ResponseErrorProperty
    public String getUserEmail() {
        return userEmail;
    }
}
