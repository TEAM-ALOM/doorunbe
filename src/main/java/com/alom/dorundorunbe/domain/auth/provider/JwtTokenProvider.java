package com.alom.dorundorunbe.domain.auth.provider;

import com.alom.dorundorunbe.domain.auth.token.AccessToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.accessExpirationTime}")
    private Long ACCESS_EXPIRATION_TIME;

    private final SecretKey secretKey;

    @Autowired
    public JwtTokenProvider(@Value("{jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public AccessToken generateAccessToken(String username) {
        String token = Jwts.builder()
                .subject(username)
                .claim("type", "access")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();

        return AccessToken.of(token);
    }
}
