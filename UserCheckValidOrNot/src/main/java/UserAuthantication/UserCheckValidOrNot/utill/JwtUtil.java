package UserAuthantication.UserCheckValidOrNot.utill;

import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
// Declares the JwtUtil class as a Spring service component. This class provides utility methods for working with JWT tokens.

    private String secret = "javatechiefgbbtrbrtbtr";
    // Declares a private string `secret` used as the signing key for generating and validating JWT tokens.

    public String extractUsername(String token) {
        // Method to extract the username (subject) from the JWT token.
        return extractClaim(token, Claims::getSubject);
        // Calls the extractClaim method, passing the token and a reference to the getSubject method of Claims to retrieve the subject.
    }

    public Date extractExpiration(String token) {
        // Method to extract the expiration date from the JWT token.
        return extractClaim(token, Claims::getExpiration);
        // Calls the extractClaim method, passing the token and a reference to the getExpiration method of Claims to retrieve the expiration date.
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        // Generic method to extract a specific claim from the JWT token using a function.
        final Claims claims = extractAllClaims(token);
        // Extracts all claims from the token using the extractAllClaims method.
        return claimsResolver.apply(claims);
        // Applies the provided function (claimsResolver) to the extracted claims to retrieve the desired claim.
    }

    private Claims extractAllClaims(String token) {
        // Private method to extract all claims (payload) from the JWT token.
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        // Parses the token using the secret key to retrieve the claims body.
    }

    private Boolean isTokenExpired(String token) {
        // Private method to check if the JWT token has expired.
        return extractExpiration(token).before(new Date());
        // Compares the token's expiration date with the current date to determine if it has expired.
    }

    public String generateToken(String username) {
        // Method to generate a new JWT token for the specified username.
        Map<String, Object> claims = new HashMap<>();
        // Creates an empty claims map.
        return createToken(claims, username);
        // Calls the createToken method, passing the empty claims map and the username to generate the token.
    }

    private String createToken(Map<String, Object> claims, String subject) {
        // Private method to create a JWT token with the specified claims and subject (username).
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                // Sets the claims, subject, and the issued date (current time).
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                // Sets the expiration date to 10 hours from the current time.
                .signWith(SignatureAlgorithm.HS256, secret).compact();
                // Signs the token with the HS256 algorithm and the secret key, then compacts it into a string.
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        // Method to validate the JWT token against the user details.
        final String username = extractUsername(token);
        // Extracts the username from the token.
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        // Returns true if the extracted username matches the username from the userDetails and the token is not expired.
    }
}