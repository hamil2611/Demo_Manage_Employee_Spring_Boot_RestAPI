package com.example.manageemployee.jwt;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private String jwtSecret ="";
    private int jwtExpriration = 86400;
    public String createToken(Authentication auth){
        UserDetails userDetails = (UserDetails) auth.getDetails();
        return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime()+jwtExpriration*1000))
                .signWith(SignatureAlgorithm.ES512,jwtSecret)
                .compact();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (SignatureException e){
            logger.error("");

        }catch (MalformedJwtException e){

        }catch (UnsupportedJwtException e){

        }catch (ExpiredJwtException e){

        }catch (IllegalArgumentException e){

        }
        return false;
    }

    public String getUsernameFromToken(String token){
        String username = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        return username;

    }
}
