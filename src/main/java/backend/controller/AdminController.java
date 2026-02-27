package backend.controller;

import backend.exception.AdminNotFoundException;
import backend.model.AdminUser;
import backend.repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from React
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminRepo adminRepo;

    // Create new user
    @PostMapping
    public AdminUser addUser(@RequestBody AdminUser admin) {
        return adminRepo.save(admin);
    }

    // Login endpoint
    @PostMapping("/userlogin")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AdminUser loginDetails) {
        try {
            System.out.println("Login request received: " + loginDetails.getEmail());

            AdminUser admin = adminRepo.findByEmail(loginDetails.getEmail())
                    .orElseThrow(() -> new AdminNotFoundException(
                            "Email not found: " + loginDetails.getEmail()));

            // Check password
            if (!admin.getPassword().equals(loginDetails.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Incorrect password!"));
            }



// SUCCESS
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("id", admin.getId());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace(); // Logs error in console
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // Get all users
    @GetMapping
    public List<AdminUser> getAllUsers() {
        return adminRepo.findAll();
    }

    // Get user by ID
    @GetMapping("/{id}")
    public AdminUser getUserById(@PathVariable Long id) {
        return adminRepo.findById(id)
                .orElseThrow(() -> new AdminNotFoundException(id));
    }

    // Update user
    @PutMapping("/{id}")
    public AdminUser updateUser(@RequestBody AdminUser newAdminUser, @PathVariable Long id) {
        return adminRepo.findById(id)
                .map(adminUser -> {
                    adminUser.setFullname(newAdminUser.getFullname());
                    adminUser.setEmail(newAdminUser.getEmail());
                    adminUser.setPassword(newAdminUser.getPassword());
                    adminUser.setPhone(newAdminUser.getPhone());
                    adminUser.setRole(newAdminUser.getRole());
                    adminUser.setDepartment(newAdminUser.getDepartment());
                    adminUser.setStartdate(newAdminUser.getStartdate());
                    adminUser.setShift(newAdminUser.getShift());
                    adminUser.setAddress(newAdminUser.getAddress());
                    adminUser.setEmecontact(newAdminUser.getEmecontact());
                    adminUser.setRemark(newAdminUser.getRemark());
                    return adminRepo.save(adminUser);
                }).orElseThrow(() -> new AdminNotFoundException(id));
    }

    // Delete user
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        if (!adminRepo.existsById(id)) {
            throw new AdminNotFoundException(id);
        }
        adminRepo.deleteById(id);
        return "User account " + id + " deleted";
    }
}
