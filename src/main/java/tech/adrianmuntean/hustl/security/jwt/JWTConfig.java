package tech.adrianmuntean.hustl.security.jwt;

import io.jsonwebtoken.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import tech.adrianmuntean.hustl.security.services.UserDetailsImpl;

import java.util.Date;


@Component
public class JWTConfig {
    private static final Logger logger = LogManager.getLogger(JWTConfig.class);

    @Value("${hustl.app.expiration}")
    private int expiration;

    @Value("${hustl.app.secret}")
    private String secret;

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: " + e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: " + e);
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: " + e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: " + e);
        } catch (Exception e) {
            logger.error("Token expired or something else: " + e);
        }

        return false;
    }

    public String generateToken(Authentication authentication) {
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder().setSubject(user.getUsername()).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }
}
