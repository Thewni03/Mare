package backend.repository;

import backend.model.Hr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository

public interface HrRepo extends JpaRepository<Hr, Long> {

}

