package Com.Exam.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Com.Exam.Service.UserService;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> login(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String role) {

        System.out.println("Login attempt -> Username: " + username + ", Role: " + role);

        boolean isValidUser = userService.validateUser(username, password, role);

        if (isValidUser) {
            // Mock email for example; replace with real lookup if needed
            String email = userService.getEmailByUsername(username); // You need to implement this

            String redirectUrl = switch (role.toUpperCase()) {
                case "ADMIN" -> "/admin-dashboard.html";
                case "TEACHER" -> "/teacher-dashboard.html";
                case "STUDENT" -> "/student-dashboard.html";
                default -> null;
            };

            if (redirectUrl == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("success", false, "message", "Invalid role specified."));
            }

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("redirect", redirectUrl);
            response.put("username", username);
            response.put("role", role);
            response.put("email", email);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("success", false, "message", "Invalid credentials or role."));
    }
}
