package UserAuthantication.UserCheckValidOrNot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import UserAuthantication.UserCheckValidOrNot.service.CustomUserDetailsService;
import UserAuthantication.UserCheckValidOrNot.utill.JwtFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfigration{
	

	    @Autowired
	    private JwtFilter jwtFilter;



        
    @Bean
    UserDetailsService userDetailsService() { 
	        return new CustomUserDetailsService(); 
	    }
    
	

  
    @Bean
    PasswordEncoder passwordEncoder(){
	        return new BCryptPasswordEncoder();
	    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
    	DaoAuthenticationProvider authenticationProvider =new DaoAuthenticationProvider();
    	authenticationProvider.setUserDetailsService(userDetailsService());
    	authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
    }

	    @Bean
	    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
	        return configuration.getAuthenticationManager();
	    }

		@SuppressWarnings("deprecation")
		@Bean
	    public SecurityFilterChain  securityFilterChain(HttpSecurity httpSecurity) throws Exception {
	    	
	    	return httpSecurity.csrf(csrf -> csrf.disable())
                    .authorizeRequests(requests -> requests
                            .requestMatchers("/api/users/login", "/api/users/auth", "/api/users/signup").permitAll())
                    .sessionManagement(management -> management
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
	    }
		
		

}
