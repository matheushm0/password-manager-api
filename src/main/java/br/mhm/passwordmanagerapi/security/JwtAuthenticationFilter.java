package br.mhm.passwordmanagerapi.security;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import br.mhm.passwordmanagerapi.service.UsuarioService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired UsuarioService usuarioService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = request.getHeader("Authorization");
		
		if (token != null) {
			try {
	            Claims claims = Jwts.parser()
	                    .setSigningKey("84554d5c-3072-11ec-8d3d-0242ac130003")
	                    .parseClaimsJws(token)
	                    .getBody();
	            
	            UUID userId = UUID.fromString(claims.get("id").toString());
	            UserCredentials credentials = usuarioService.loadUserById(userId);
	            
	            if (credentials != null) {
	                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(credentials,
	                        null, credentials.getAuthorities());
	                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(auth);
	            }
			}
			catch (Exception e) {}
		}
		
		filterChain.doFilter(request, response);
	}
}
