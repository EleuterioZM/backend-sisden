package mz.sisden.sisden.repositories;

import mz.sisden.sisden.entities.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportTypeRepository extends JpaRepository<ReportType, Long> {
    List<ReportType> findByInstituitionId(Long instituitionId);
}
