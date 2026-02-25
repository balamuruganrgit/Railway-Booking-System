package com.example.demo.RailwayController;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.RailwayDTO.LoginRequest;
import com.example.demo.RailwayDTO.RegisterRequest;
import com.example.demo.RailwayEntity.Railwayuser;
import com.example.demo.RailwayService.UserService;


@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
 
    @Autowired
    private UserService userService;
    
   
        @GetMapping("/{id}")
        public ResponseEntity<Railwayuser> getUser(@PathVariable Long id) {
            Railwayuser user = userService.findById(id);
            return ResponseEntity.ok(user);
        }
        
        @PutMapping("/{id}")
        public ResponseEntity<Railwayuser> updateUser(@PathVariable Long id, @RequestBody Map<String, String> userData) {
            Railwayuser user = userService.findById(id);  // Get existing user
            if (user != null) {
                user.setUsername(userData.get("username"));
                user.setEmail(userData.get("email"));
                user.setMobile(userData.get("mobile"));
                // Save to DB
                userService.save(user);
            }
            return ResponseEntity.ok(user);
        }

        
    


    @PostMapping("/register")
    public Railwayuser register(@RequestBody RegisterRequest request) {
        System.out.println("✅ /api/users/register CALLED");
        return userService.registerUser(request);
    }

    @PostMapping("/login")
    public Railwayuser login(@RequestBody LoginRequest request) {
        System.out.println("✅ /users/login CALLED");
        return userService.login(request);
    }
    @GetMapping("/all")
    public List<Railwayuser> getAllUsers() {
        return userService.getAllUsers();
    }
}
