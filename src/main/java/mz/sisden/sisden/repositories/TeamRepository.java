package mz.sisden.sisden.repositories;

import mz.sisden.sisden.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM TeamUser tu WHERE tu.team.id = :teamId")
    void deleteTeamUsersByTeamId(@Param("teamId") Long teamId);
}
