package mz.sisden.sisden.repositories;

import mz.sisden.sisden.entities.Instituition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstituitionRepository extends JpaRepository<Instituition, Long> {
}
