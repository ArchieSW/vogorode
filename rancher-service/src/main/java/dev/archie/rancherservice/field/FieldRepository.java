package dev.archie.rancherservice.field;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FieldRepository extends MongoRepository<Field, String> {
}
