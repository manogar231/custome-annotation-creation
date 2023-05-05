package com.custome.util;

import java.io.Serializable;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.swing.JComboBox.KeySelectionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.custome.entity.User;
import com.custome.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenGenerator implements Serializable{

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	  private static final long serialVersionUID = -2550185165626007488L;
	  @Autowired
      private  UserRepository userRepository;
	  
	    private  String secret="manogarsrlm";
	  
	  //retrieve expiration date from jwt token
	  public Date getExpirationDateFromToken(String token) {
	    return getClaimFromToken(token, Claims::getExpiration);
	  }

	  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
	    final Claims claims = getAllClaimsFromToken(token);
	    return claimsResolver.apply(claims);
	  }

	  //for retrieveing any information from token we will need the secret key
	  private Claims getAllClaimsFromToken(String token) {
	    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	  }

	  //check if the token has expired
	  public Boolean isTokenExpired(String token) {
	    final Date expiration = getExpirationDateFromToken(token);
	    return expiration.before(new Date());
	  }

	  //generate token for user
	  public String generateToken(String  email) {		 
	    Map<String, Object> claims = new HashMap<>();
	    return doGenerateToken(claims, email);
	  }
	  
	  private String doGenerateToken(Map<String, Object> claims, String subject) {
	    return Jwts.builder().setClaims(claims).setSubject(String.valueOf(subject)).setIssuedAt(new Date(System.currentTimeMillis()))
	      .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
	      .signWith(SignatureAlgorithm.HS512, "manogar").compact();
	  }

	  //validate token
	  public Boolean validateToken(String token) {

	    try {

	      Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
	      return true;
	    } catch (SignatureException ex) {

	      throw new SignatureException("Invalid Jwt signnature");
	    } catch (MalformedJwtException ex) {

	      throw new MalformedJwtException("Invalid Jwt signnature");
	    } catch (ExpiredJwtException ex) {

	      ex.printStackTrace();
	    } catch (UnsupportedJwtException ex) {

	      throw new UnsupportedJwtException("Unsupported jwt token");
	    } catch (IllegalArgumentException ex) {

	      throw new IllegalArgumentException("Jwt Claims String is empty");
	    }
	    return true;
	  }

	
	
}
