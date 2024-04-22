package email.webscraper.microservice.configuration.security.token.impl;

import email.webscraper.microservice.configuration.security.token.AccessToken;
import email.webscraper.microservice.configuration.security.token.AccessTokenDecoder;
import email.webscraper.microservice.configuration.security.token.AccessTokenEncoder;
import email.webscraper.microservice.configuration.security.token.exception.InvalidAccessTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccessTokenEncoderDecoderImpl implements AccessTokenEncoder, AccessTokenDecoder {
    private final Key key;

    public AccessTokenEncoderDecoderImpl(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String encode(AccessToken accessToken) {
        Map<String, Object> claimsMap = new HashMap<>();
        if (accessToken.getUserId() != null) {
            claimsMap.put("userId", accessToken.getUserId());
        }
        if (accessToken.getEmail() != null) {
            claimsMap.put("email", accessToken.getEmail());
        }
        claimsMap.put("isAdmin", accessToken.isAdmin());
        Instant now = Instant.now();
        return Jwts.builder()
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES))) //Token expiry time
                .addClaims(claimsMap)
                .signWith(key)
                .compact();
    }

    @Override
    public AccessToken decode(String accessTokenEncoded) {
        try {
            Jwt<?, Claims> jwt = Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(accessTokenEncoded);
            Claims claims = jwt.getBody();

            String email = claims.get("email", String.class);
            Long userId = claims.get("userId", Long.class);
            boolean isAdmin;
            try
            { isAdmin = (boolean) claims.get("isAdmin"); }
            catch(Exception e)
            { isAdmin = false; }
            return new AccessTokenImpl(email, userId,isAdmin);
        } catch (JwtException e) {
            throw new InvalidAccessTokenException(e.getMessage());
        }
    }
}
