/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.repositories;


import mz.sisden.sisden.entities.Notification;
import mz.sisden.sisden.entities.NotificationType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findByHouseholdIdAndNotificationType(Long householdId, NotificationType notificationType);

    Optional<Notification> findByMemberIdAndNotificationType(Long memberId, NotificationType notificationType);

    @Query("select n from Notification as n left join n.permission p left join p.module m left join n.userGroup ug where n.viewed = :viewed and (p.code in (:authorityList) or m.code in (:authorityList) or ug.code in (:authorityList))")
    List<Notification> findAllByAuthorityListAndViewed(List<String> authorityList, Boolean viewed, Sort sort);
}
