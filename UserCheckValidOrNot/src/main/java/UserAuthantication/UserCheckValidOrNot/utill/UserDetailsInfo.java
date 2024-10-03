package UserAuthantication.UserCheckValidOrNot.utill;


import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

import UserAuthantication.UserCheckValidOrNot.pojo.User;




public class UserDetailsInfo implements UserDetails {
// Declares the UserDetailsInfo class, which implements the UserDetails interface from Spring Security.
// This class is used to adapt the application's User class to Spring Security's UserDetails.

    private static final long serialVersionUID = 1L;
    // Declares a static final long field serialVersionUID, used for serialization and deserialization of the class.

    private final User user;
    // Declares a private final field `user` of type User, which will store the User object associated with this UserDetailsInfo.

    public UserDetailsInfo(User user) {
        // Constructor that takes a User object as a parameter and initializes the `user` field.
        this.user = user;
    }
    
    public User getUser() {
        // Getter method for the `user` field, returning the User object.
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Method to return the authorities granted to the user. 
        // Currently returns null; in a complete implementation, this would return the user's roles/permissions.
        return null;
    }

    @Override
    public String getPassword() {
        // Method to return the user's password. 
        // The password is retrieved from the User object.
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        // Method to return the user's username.
        // In this implementation, the user's email is used as the username.
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        // Method to check if the user's account is non-expired.
        // This implementation always returns true, meaning the account is not expired.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Method to check if the user's account is non-locked.
        // This implementation always returns true, meaning the account is not locked.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Method to check if the user's credentials (password) are non-expired.
        // This implementation always returns true, meaning the credentials are valid.
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Method to check if the user's account is enabled.
        // This implementation always returns true, meaning the account is enabled.
        return true;
    }

}
