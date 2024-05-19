package com.crio.starter.repository;

import java.util.List;
import java.util.Optional;
import com.crio.starter.data.MemeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemeRepository extends MongoRepository<MemeEntity, String> {
    List<MemeEntity> findAllByOrderByIdDesc(org.springframework.data.domain.Pageable pageable);
    Optional<MemeEntity> findByNameAndUrlAndCaption(String name, String url, String caption);
}
