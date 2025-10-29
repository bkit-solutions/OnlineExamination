package Com.Exam.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Com.Exam.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by username
    User findByUsername(String username);
}