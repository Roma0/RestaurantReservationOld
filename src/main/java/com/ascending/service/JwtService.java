package com.ascending.service;

import com.ascending.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private final String SECRET_KEY = System.getProperty("secret.key");
    private final String ISSUER = "com.ascending";
    private final long EXPIRATION_TIME = 86400 * 1000;

    public String generateToken(User user){
        //JWT signature algorithm using sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //Sign JWT with SECRET_KEY
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        //Set the JWT Claims
        Claims claims = Jwts.claims();
        claims.setId(String.valueOf(user.getId()));
        claims.setSubject(user.getName());
        claims.setIssuedAt(new Date(System.currentTimeMillis()));
        claims.setIssuer(ISSUER);
        claims.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME));
        //Build the JWT and serializes it to a compact, URL-safe string
        JwtBuilder jwtBuilder = Jwts.builder().setClaims(claims).signWith(signatureAlgorithm, signingKey);
        return jwtBuilder.compact();
    }

    public Claims getClaimsFromToken(String token){

        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                    .parseClaimsJws(token).getBody();
        }catch (Exception e){
            logger.error("Failure to decrypt token. {}", e.getMessage());
            claims = null;
        }
        if (claims != null)logger.debug("Claims:" + claims.toString());
        return claims;
    }

    public Date getExpirationDateFromToken(String token){
        Date expirationDate;
        try {
            final Claims claims = getClaimsFromToken(token);
            expirationDate = claims.getExpiration();
        }catch (Exception e){
            logger.error("Failure to get expiration date from the token. {}", e.getMessage());
            expirationDate = null;
        }
        return expirationDate;
    }

    public boolean isTokenExpired(String token){
        final Date expirationDate  = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    //Todo validateToken
//    public boolean validateToken(String token){
//
//    }
}
