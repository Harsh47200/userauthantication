package UserAuthantication.UserCheckValidOrNot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;



import UserAuthantication.UserCheckValidOrNot.exception.ResourceNotFoundException;
import UserAuthantication.UserCheckValidOrNot.pojo.User;
import UserAuthantication.UserCheckValidOrNot.repository.UserRepository;
import UserAuthantication.UserCheckValidOrNot.utill.GenricResponse;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

  public List<User> getAllUsers() {
      return userRepository.findAll();
  }
 
  public User getUserById(String id) {
      return userRepository.findById(id)
          .orElseThrow(() -> new ResourceNotFoundException("User not found"));
  }
 
  public User createUser(User user) {
      return userRepository.save(user);
  }
 
  public User updateUser(String id, User userDetails) {
      User user = userRepository.findById(id)
          .orElseThrow(() -> new ResourceNotFoundException("User not found"));
 
      user.setUsername(userDetails.getUsername());
      user.setEmail(userDetails.getEmail());
 
      return userRepository.save(user);
  }
 
  public void deleteUser(String id) {
      User user = userRepository.findById(id)
          .orElseThrow(() -> new ResourceNotFoundException("User not found"));
 
      userRepository.delete(user);
  }
  
  
    public ResponseEntity<?> addUserDetails(User user) {
        // Implements the addUserDetails method from the LoginServices interface

        // Extract the email from the User object
        String email = user.getEmail();

        // Fetch the user from the repository by email
        User user1 = userRepository.findByEmail(email);

        // Check if the user with the given email already exists
        if (user1 == null) {
            // If the user does not exist, save the new user to the repository
        	userRepository.save(user);

         

            // Return a success response with HTTP status 201 (Created) and the new user details
            return ResponseEntity.ok(new GenricResponse(201, "Success", user));
        } else {
            // If the user already exists, return a response indicating that the email is already in use
            return ResponseEntity.ok(new GenricResponse(203, "Sorry Email id already exist", null));
        }
    }
}
