package com.example.fallenangels.config;

import com.example.fallenangels.models.User;
import com.example.fallenangels.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    /**
     * Devuelve el objeto AuthenticationManager configurado.
     *
     * @param config La configuración de autenticación.
     * @return El objeto AuthenticationManager configurado.
     * @throws Exception Si ocurre un error al obtener el AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    }

    /**
     * Devuelve un proveedor de autenticación que utiliza un servicio de detalles de usuario y un codificador de contraseñas.
     * 
     * @return el proveedor de autenticación configurado
     */
    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider authenticationProvider= new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * Devuelve una instancia de PasswordEncoder que utiliza el algoritmo BCrypt para codificar contraseñas.
     * 
     * @return una instancia de PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }


    /**
     * Retorna un objeto UserDetailsService que se utiliza para cargar los detalles del usuario.
     * 
     * @return el objeto UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailService()
    {
        return email -> {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
            return org.springframework.security.core.userdetails.User
                    .withUsername(email)
                    .password(user.getPassword())
                    .accountExpired(!user.isAccountNonExpired())
                    .accountLocked(!user.isAccountNonLocked())
                    .credentialsExpired(!user.isCredentialsNonExpired())
                    .disabled(!user.isEnabled())
                    .authorities(user.getAuthorities())
                    .build();
        };
    }

}