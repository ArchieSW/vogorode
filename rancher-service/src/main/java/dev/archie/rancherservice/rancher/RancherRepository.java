package dev.archie.rancherservice.rancher;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RancherRepository extends MongoRepository<Plot, String> {
}
