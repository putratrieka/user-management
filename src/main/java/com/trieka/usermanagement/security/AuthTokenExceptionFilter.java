package com.trieka.usermanagement.security;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthTokenExceptionFilter {
	
//	@Override
//	public void doFilterInternal(HttpServletRequest request,
//			HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//		try {
//			filterChain.doFilter(request, response);
//		} catch (InvalidAuthTokenException | AuthTokenExpiredException e) {
//			log.debug("Handling authentication exception", e);
//			
//			response.setStatus(HttpStatus.UNAUTHORIZED.value());
//			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//			response.getWriter().write(String.format("{\"message\": \"%s\"}", e.getMessage()));
//			response.getWriter().flush();
//			response.getWriter().close();
//		} catch (UnauthorizedOperationException e) {
//			response.setStatus(HttpStatus.FORBIDDEN.value());
//			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//			response.getWriter().write(String.format("{\"message\": \"%s\"}", e.getMessage()));
//			response.getWriter().flush();
//			response.getWriter().close();
//		}
//	}

}
