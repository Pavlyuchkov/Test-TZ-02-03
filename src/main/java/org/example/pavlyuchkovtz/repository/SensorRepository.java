package org.example.pavlyuchkovtz.repository;

import org.example.pavlyuchkovtz.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor, Long> {

    @Query("""
        SELECT s FROM Sensor s
        WHERE (COALESCE(:name, '') = '' OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%')))
        AND (COALESCE(:model, '') = '' OR LOWER(s.model) LIKE LOWER(CONCAT('%', :model, '%')))
    """)
    List<Sensor> findByNameAndModelContaining(@Param("name") String name,
                                              @Param("model") String model);
}
