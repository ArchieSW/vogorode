package dev.archie.handymanservice.handyman.skill;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SkillRepository extends MongoRepository<Skill, Long> {
}
