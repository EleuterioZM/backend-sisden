package mz.sisden.sisden.repositories;

import mz.sisden.sisden.entities.ReportClassification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportClassificationRepository extends JpaRepository<ReportClassification, Long> {
}
