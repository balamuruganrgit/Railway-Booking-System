package com.example.demo.RailwayService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.RailwayDTO.LoginRequest;
import com.example.demo.RailwayDTO.RegisterRequest;
import com.example.demo.RailwayEntity.Railwayuser;
import com.example.demo.RailwayRepository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // =============================
    // ✅ REGISTER USER
    // =============================
    public Railwayuser registerUser(RegisterRequest req) {

        
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        if (userRepository.existsByMobile(req.getMobile())) {
            throw new RuntimeException("Mobile already registered");
        }

        Railwayuser user = new Railwayuser();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setMobile(req.getMobile());
        user.setPassword(req.getPassword()); // encrypt later

        return userRepository.save(user);
    }

    // =============================
    // ✅ LOGIN USER  (YOUR CODE – CORRECT PLACE)
    // =============================
 // ✅ LOGIN USER
    public Railwayuser login(LoginRequest request) {

        if (request.getEmail() == null || request.getPassword() == null) {
            throw new RuntimeException("Email and Password are required");
        }

        Railwayuser user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!request.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return user; // ✅ success
    }


    // =============================
    // GET ALL USERS (optional)
    // =============================
    public List<Railwayuser> getAllUsers() {
        return userRepository.findAll();
    }

	public Railwayuser findById(Long id) {
		 return userRepository.findById(id).orElse(null);
	}

	public void save(Railwayuser user) {
		// TODO Auto-generated method stub
		userRepository.save(user);
	}
}

