package com.alten.practica.service.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
/*
 * Servicio para manejar la lógica relacionada con JWT (JSON Web Tokens).
 * Proporciona métodos para generar, validar y extraer información de los tokens JWT.
 * 
 * @see org.springframework.stereotype.Service
 */
@Service
public class JwtService {

	// Clave secreta utilizada para firmar el token JWT
	private static final String SECRET_KEY = "586E32723575387996255454572B4B625065536850502155454";

	// Método público para generar un token JWT para un usuario específico
	public String getToken(UserDetails user) {
		// Llama al método privado getToken con un mapa vacío de claims adicionales
		return getToken(new HashMap<>(), user);
	}

	// Método privado que genera un token JWT con claims adicionales y detalles del
	// usuario
	private String getToken(Map<String, Object> extraClaims, UserDetails user) {
		// Construye el token JWT con claims, asunto, fecha de emisión, fecha de
		// expiración y firma
		return Jwts.builder().setClaims(extraClaims) // Establece claims adicionales
				.setSubject(user.getUsername()) // Establece el nombre de usuario como asunto del token
				.setIssuedAt(new Date(System.currentTimeMillis())) // Establece la fecha de emisión
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // Establece la fecha de
																						// expiración																						
				.signWith(getKey(), SignatureAlgorithm.HS256) // Firma el token con la clave secreta y el algoritmo
																// HS256
				.compact(); // Compacta el token a su representación final
	}

	// Método privado que obtiene la clave secreta para firmar el token
	private Key getKey() {
		// Decodifica la clave secreta desde Base64
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		// Retorna una clave HMAC-SHA a partir de los bytes decodificados
		return Keys.hmacShaKeyFor(keyBytes);
	}

	// Método público para obtener el nombre de usuario desde un token JWT
	public String getUsernameFromToken(String token) {
		// Llama al método getClaim con el token y una función para obtener el asunto
		// (nombre de usuario)
		return getClaim(token, Claims::getSubject);
	}

	// Método público para validar un token JWT con los detalles del usuario
	public boolean isTokenValid(String token, UserDetails userDetails) {
		// Obtiene el nombre de usuario desde el token
		final String username = getUsernameFromToken(token);
		// Verifica que el nombre de usuario del token coincida con el nombre de usuario
		// del usuario y que el token no haya expirado
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	// Método privado para obtener todos los claims desde un token JWT
	private Claims getAllClaims(String token) {
		// Parsea el token JWT y obtiene los claims usando la clave secreta
		return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
	}

	// Método público para obtener un claim específico desde un token JWT usando una
	// función resolutora de claims
	public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
		// Obtiene todos los claims desde el token
		final Claims claims = getAllClaims(token);
		// Aplica la función resolutora de claims para obtener el claim específico
		return claimsResolver.apply(claims);
	}

	// Método privado para obtener la fecha de expiración desde un token JWT
	private Date getExpiration(String token) {
		// Llama al método getClaim con el token y una función para obtener la fecha de
		// expiración
		return getClaim(token, Claims::getExpiration);
	}

	// Método privado para verificar si un token JWT ha expirado
	private boolean isTokenExpired(String token) {
		// Compara la fecha de expiración del token con la fecha actual
		return getExpiration(token).before(new Date());
	}
}
