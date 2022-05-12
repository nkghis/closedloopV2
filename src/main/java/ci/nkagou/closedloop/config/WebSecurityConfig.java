package ci.nkagou.closedloop.config;

import ci.nkagou.closedloop.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;


import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private DataSource dataSource;


   /* private UserDetailsServiceImpl userDetailsService;
    private DataSource dataSource;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, DataSource dataSource) {
        this.userDetailsService = userDetailsService;
        this.dataSource = dataSource;
    }*/

    /*public WebSecurityConfig(boolean disableDefaults, UserDetailsServiceImpl userDetailsService, DataSource dataSource) {
        super(disableDefaults);
        this.userDetailsService = userDetailsService;
        this.dataSource = dataSource;
    }
*/
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    /*@Autowired
      public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { */

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        // Setting Service to find User in the database.
        // And Setting PassswordEncoder
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        // The pages does not require login
        http.authorizeRequests().antMatchers("/", "/login", "/logout","/dashboard").permitAll();

        // /user page requires login as ROLE_USER or ROLE_ADMIN.
        // If no login, it will redirect to /login page.
        //http.authorizeRequests().antMatchers("/admin/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
        http.authorizeRequests().antMatchers("/user/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
        http.authorizeRequests().antMatchers("/dashboard/*").access("hasAnyRole('ROLE_USER', 'ROLE_ACCES', 'ROLE_ADMIN')");
        http.authorizeRequests().antMatchers("/acces/**").access("hasAnyRole('ROLE_ACCES', 'ROLE_ADMIN')");
        /*http.authorizeRequests().antMatchers("/auth/**").access("hasAnyRole('ROLE_SUPERVISOR', 'ROLE_ADMIN')");
        http.authorizeRequests().antMatchers("/agent/**").access("hasAnyRole('ROLE_AGENT', 'ROLE_ADMIN')");
        http.authorizeRequests().antMatchers("/access/**").access("hasAnyRole('ROLE_ACCES', 'ROLE_ADMIN', 'ROLE_SUPERVISOR')");
        http.authorizeRequests().antMatchers("/stocks").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN','ROLE_SUPERVISOR', 'ROLE_AGENT')");
        http.authorizeRequests().antMatchers("/dashboard/*").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN','ROLE_SUPERVISOR', 'ROLE_AGENT')");
*/
        // For ADMIN only.
        http.authorizeRequests().antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')");

        // When the user has logged in as XX.
        // But access a page that requires role YY,
        // AccessDeniedException will be thrown.
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

        // Config for Login Form
        http.authorizeRequests().and().formLogin()//
                // Submit URL of login page.
                .loginProcessingUrl("/j_spring_security_check") // Submit URL
                .loginPage("/login")//
                /*.defaultSuccessUrl("/userAccountInfo")//*/
                .defaultSuccessUrl("/dashboard")//
                .failureUrl("/login?error=true")//
                .usernameParameter("username")//
                .passwordParameter("password")
                // Config for Logout Page
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login");

        // Config Remember Me.
        http.authorizeRequests().and() //
                .rememberMe().tokenRepository(this.persistentTokenRepository()) //
                .tokenValiditySeconds(1 * 24 * 60 * 60); // 24h

    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }

}