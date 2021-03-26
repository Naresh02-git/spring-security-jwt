package com.mnk.demo.jwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mnk.demo.jwt.utility.JWTUtility;
import com.mnk.demo.service.UserService;

@Component
public class JwtFilter extends  OncePerRequestFilter{

	 @Autowired
	private JWTUtility jwtUtility;
	
	 @Autowired
	 private UserService userService;
	 
	 @Autowired
	 private UserDetailsService userDetailsService;
	 
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authorizationHeader=request.getHeader("Authorization");
		
		String jwt=null;
		String username=null;
		
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
		jwt=authorizationHeader.substring(7);
		username=jwtUtility.extractUsername(jwt);
		
		
	}
	
	if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
		UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
		
		if(jwtUtility.validateToken(jwt, userDetails))
		{
			UsernamePasswordAuthenticationToken usernamePasswordAuthentiationToken
			=new UsernamePasswordAuthenticationToken(userDetails,
					null,userDetails.getAuthorities());
			
			usernamePasswordAuthentiationToken
					.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthentiationToken);
		}
	}
	
		
	 filterChain.doFilter(request,response);
	
	}
	

}
