package UserAuthantication.UserCheckValidOrNot.dto;

public class LoginDto {
	
	//public String username;
	public String email;
	public String password;
	

	
	public String getPassword() {
		return password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
