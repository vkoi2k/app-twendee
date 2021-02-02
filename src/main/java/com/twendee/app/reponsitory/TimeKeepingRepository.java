package com.twendee.app.reponsitory;

import com.twendee.app.model.entity.TimeKeeping;
import com.twendee.app.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TimeKeepingRepository extends JpaRepository<TimeKeeping, Integer> {
    TimeKeeping findByUser_UserIdAndDateGreaterThanEqualAndDateLessThanEqual(
            Integer userId, Date minDate, Date maxDate
    );

    List<TimeKeeping> findByDateGreaterThanEqualAndDateLessThanEqual(
            Date minDate, Date maxDate
    );

    List<TimeKeeping> findByUserAndDateGreaterThanEqualAndDateLessThan(
            User user, Date minDate, Date maxDate, Sort sort
    );
    Page<TimeKeeping> findByUserAndDateGreaterThanEqualAndDateLessThan(
            User user, Date minDate, Date maxDate, Pageable pageable
    );

    List<TimeKeeping> findByUserAndDateGreaterThanEqualAndDateLessThanEqual
            (User user, Date minDate, Date maxDate);
}
