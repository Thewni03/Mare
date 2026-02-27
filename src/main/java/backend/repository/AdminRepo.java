package backend.repository;


import backend.model.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo extends JpaRepository<AdminUser, Long> {

    Optional<AdminUser>findByEmail(String email);


}

