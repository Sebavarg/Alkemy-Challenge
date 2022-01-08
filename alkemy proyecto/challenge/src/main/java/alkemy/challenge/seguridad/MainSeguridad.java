/**
 *
 * @author Sebastian Vargas Zingaretti
 * mail vargaszingaretti@gmail.com
 * 
 */
package alkemy.challenge.seguridad;

import alkemy.challenge.seguridad.jwt.JwtEntryPoint;
import alkemy.challenge.seguridad.jwt.JwtTokenFilter;
import alkemy.challenge.seguridad.service.UsersDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author Sebastian
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity (prePostEnabled=true)
public class MainSeguridad extends WebSecurityConfigurerAdapter{
   @Autowired
    UsersDetailsServiceImpl userDetailsService;
@Autowired
JwtEntryPoint jwtEntryPoint;

@Bean
public JwtTokenFilter jwtTokenFilter(){
    return new JwtTokenFilter();
}

@Bean
public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.cors().and().csrf().disable()
              .authorizeRequests()
              .antMatchers("/auth/**").permitAll()
              .anyRequest ().authenticated()
              .and()
              .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
      http.addFilterBefore(jwtTokenFilter(),UsernamePasswordAuthenticationFilter.class);
    
        }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager(); //To change body of generated methods, choose Tools | Templates.
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

}
