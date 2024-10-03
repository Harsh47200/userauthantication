package UserAuthantication.UserCheckValidOrNot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;



import UserAuthantication.UserCheckValidOrNot.dto.LoginDto;
import UserAuthantication.UserCheckValidOrNot.dto.TokenResponse;
import UserAuthantication.UserCheckValidOrNot.dto.UserDetails;
import UserAuthantication.UserCheckValidOrNot.pojo.User;
import UserAuthantication.UserCheckValidOrNot.repository.UserRepository;
import UserAuthantication.UserCheckValidOrNot.service.UserService;
import UserAuthantication.UserCheckValidOrNot.utill.GenricResponse;
import UserAuthantication.UserCheckValidOrNot.utill.JwtUtil;
import UserAuthantication.UserCheckValidOrNot.utill.UserDetailsInfo;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;
	

	@Autowired
	private JwtUtil jwtUtil;
	

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/login")
	public ResponseEntity<?> login(){
		
		// Create a new instance of LoginDto
		LoginDto loginDto = new LoginDto();
		
		
		// Return a successful response with the loginDto object
		return ResponseEntity.ok(new GenricResponse(201, "Success", loginDto));
	}
    
    @PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody User user){
		
		// Encode the user's password using BCryptPasswordEncoder
		String password = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(password); // Set the encoded password
		
		// Call the addUserDetails method from LoginServices to save the user and return the response
		return userService.addUserDetails(user);
	}
    
    @GetMapping("/auth")
	public ResponseEntity<?> auth(@RequestBody LoginDto loginDto) throws Exception {
		
		// Authenticate the user using the provided email and password
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
		);
		
		// Check if the authentication was successful
		if (authentication.isAuthenticated()) {
			 
			// Create a new instance of TokenResponse
			TokenResponse tokenResponse = new TokenResponse();
			 
			// Retrieve the User object by email
			User user = userRepository.findByEmail(loginDto.getEmail());
			 
			// Set the user and generated token in the tokenResponse object
			tokenResponse.setUser(user);
			tokenResponse.setToken(jwtUtil.generateToken(loginDto.getEmail()));
			 
			// Return a successful response with the tokenResponse object
			return ResponseEntity.ok(new GenricResponse(201, "Success", tokenResponse));
		} else {
			// Throw an exception if authentication fails
			throw new UsernameNotFoundException("Invalid user request!");
		} 
	}
	
	// Define an endpoint to check the current user's authentication (GET request to "/api/check")
	@GetMapping("/check")
	public ResponseEntity<?> check() {
		
		// Get the current authentication object from the SecurityContext
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		
		// Retrieve the UserDetailsInfo object from the authentication principal
		UserDetailsInfo user1 = (UserDetailsInfo) user.getPrincipal();
		
		// Create a new instance of UserDetails and populate it with user information
		UserDetails userDetails = new UserDetails();
		userDetails.setId(user1.getUser().getId());
		userDetails.setEmail(user1.getUser().getEmail());
		
		// Return a successful response with the userDetails object
		return ResponseEntity.ok(new GenricResponse(201, "Success", userDetails));
	}

 @GetMapping
 public List<User> getAllUsers() {
     return userService.getAllUsers();
 }
 

 @GetMapping("/{id}")
 public ResponseEntity<User> getUserById(@PathVariable String id) {
     User user = userService.getUserById(id);
     return new ResponseEntity<>(user, HttpStatus.OK);
 }

 @PostMapping
 public ResponseEntity<User> createUser(@RequestBody User user) {
     User newUser = userService.createUser(user);
     return new ResponseEntity<>(newUser, HttpStatus.CREATED);
 }

 @PutMapping("/{id}")
 public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User userDetails) {
     User updatedUser = userService.updateUser(id, userDetails);
     return new ResponseEntity<>(updatedUser, HttpStatus.OK);
 }

 @DeleteMapping("/{id}")
 public ResponseEntity<Void> deleteUser(@PathVariable String id) {
     userService.deleteUser(id);
     return new ResponseEntity<>(HttpStatus.NO_CONTENT);
 }
}

