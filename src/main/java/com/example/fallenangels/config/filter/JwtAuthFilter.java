package com.example.fallenangels.config.filter;

import com.example.fallenangels.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;


    /**
     * Filtra las peticiones HTTP y realiza la autenticación basada en el token JWT.
     * Si el token es válido y el usuario no está autenticado, se establece la autenticación del usuario.
     * 
     * @param request La solicitud HTTP recibida.
     * @param response La respuesta HTTP que se enviará.
     * @param filterChain El filtro de cadena que se utilizará para continuar con el siguiente filtro.
     * @throws ServletException Si ocurre un error durante el filtrado.
     * @throws IOException Si ocurre un error de entrada/salida durante el filtrado.
     */
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String token = getTokenFromRequest(request);
        final String email;
        System.out.println("Token: " + token);

        if(token==null)
        {
            filterChain.doFilter(request,response);
            return;
        }

        email = jwtService.getEmailFromToken(token);

        System.out.println("Email: " + email);

        if(email!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            System.out.println("userDetails: " + userDetails);

            if(jwtService.isTokenValid(token,userDetails))
            {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                System.out.println("authToken: " + authToken);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }

    /**
     * Obtiene el token de autenticación de la solicitud HTTP.
     *
     * @param request La solicitud HTTP.
     * @return El token de autenticación o null si no se encuentra.
     */
    private String getTokenFromRequest(HttpServletRequest request) 
    {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer "))
        {
            return authHeader.substring(7);
        }
        return null;
    }


}
