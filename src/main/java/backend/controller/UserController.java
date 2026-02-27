package backend.controller;


import backend.exception.UserNotFoundException;
import backend.model.UserModel;
import backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    //insert
    @PostMapping
    public UserModel adduser(@RequestBody UserModel user) {
        return userRepo.save(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserModel loginDetails) {
        UserModel user = userRepo.findByEmail(loginDetails.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Email not found :" + loginDetails.getEmail()));
        // Check if the password matches
        if (user.getPassword().equals(loginDetails.getPassword())) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("id", user.getId()); // Return user ID
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid credentials!"));
        }
    }
//display

    @GetMapping
    List<UserModel> getAllUsers() {return userRepo.findAll();}

    @GetMapping("/{id}")
    UserModel getUserId(@PathVariable Long id){

        return userRepo.findById(id)
                .orElseThrow(()-> new UserNotFoundException(id));
    }

    //update
    @PutMapping("/{id}")
    public UserModel updateProfile(@RequestBody UserModel newUserModel, @PathVariable Long id) {
        return userRepo.findById(id)
                .map(userModel -> {
                    userModel.setFullname(newUserModel.getFullname());
                    userModel.setEmail(newUserModel.getEmail());
                    userModel.setPassword(newUserModel.getPassword());
                    userModel.setPhone(newUserModel.getPhone());

                    return userRepo.save(userModel);
                }).orElseThrow(() -> new UserNotFoundException(id));

    }

    //delete
    @DeleteMapping("/{id}")
    String deleteProfile(@PathVariable Long id){
        if (!userRepo.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepo.deleteById(id);
        return "user account "+ id + "deleted";
    }

}



