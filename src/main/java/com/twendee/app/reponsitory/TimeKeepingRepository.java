package com.twendee.app.reponsitory;

import com.twendee.app.model.entity.TimeKeeping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeKeepingRepository extends JpaRepository<TimeKeeping, Integer> {
}
