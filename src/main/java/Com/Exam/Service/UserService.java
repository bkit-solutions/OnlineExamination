package Com.Exam.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import Com.Exam.Entity.User;
import Com.Exam.Repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Validate user credentials and role
    public boolean validateUser(String username, String password, String role) {
        // Find user by username
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return false; // User not found
        }

        // Validate password (Assuming password is stored in plain text or hashed)
        if (!user.getPassword().equals(password)) {
            return false; // Invalid password
        }

        // Check user role
        if (!user.getRole().equals(role)) {
            return false; // Invalid role
        }

        return true; // Valid user
    }
    
    public ResponseEntity<String> createStudent(String name, String email, String username, String password) {
        User student = new User(name, email, username, password, "STUDENT");
        userRepository.save(student);
        return ResponseEntity.ok("Student created successfully!");
    }

    public ResponseEntity<String> createTeacher(String name, String email, String username, String password) {
        User teacher = new User(name, email, username, password, "TEACHER");
        userRepository.save(teacher);
        return ResponseEntity.ok("Teacher created successfully!");
    }

    public String getEmailByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return (user != null) ? user.getEmail() : null;
    }
    
}
