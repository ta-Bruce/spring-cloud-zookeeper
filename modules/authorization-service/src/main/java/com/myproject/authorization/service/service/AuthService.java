package com.myproject.authorization.service.service;

import com.myproject.authorization.api.dto.TokenDTO;
import com.myproject.authorization.service.feign.client.UserService;
import com.myproject.common.exception.api.exception.HttpExceptions;
import com.myproject.usermanagement.api.data.UserDTO;
import com.myproject.usermanagement.api.data.ValidateCredentialsDTO;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${jwt.key}")
    private String jwtKey;
    @Value("${jwt.expiry}")
    private Long jwtExpiry;

    private final UserService userService;

    public TokenDTO getToken(ValidateCredentialsDTO request){
        UserDTO user = userService.validateCredentials(request);
        Date issuedAt = new Date();
        Date expireAt = new Date(issuedAt.getTime() + jwtExpiry*1000);
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Base64.getEncoder()
                .encode(jwtKey.getBytes())).setId(UUID.randomUUID().toString()).setIssuer("http://myproject.com")
                .setIssuedAt(issuedAt).setExpiration(expireAt)
                .claim("id", user.getId())
                .claim("username", user.getUsername())
                .compact();
        return new TokenDTO(token, user);
    }

    public Claims validateToken(String token){

        if (StringUtils.isEmpty(token))
            throw new HttpExceptions.UnauthorizedException("authorization.token.invalid");

        try {
            Jws<Claims> claims =  Jwts.parserBuilder().setSigningKey(Base64.getEncoder().encode(jwtKey.getBytes()))
                    .requireIssuer("http://myproject.com").build().parseClaimsJws(token);
            return claims.getBody();
        } catch (ExpiredJwtException ex){
            throw new HttpExceptions.UnauthorizedException("authorization.token.expired");
        } catch (JwtException ex){
            throw new HttpExceptions.UnauthorizedException("authorization.token.invalid");
        }
    }
}
