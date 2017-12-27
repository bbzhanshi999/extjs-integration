package com.zql.common.config;

import com.zql.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * spring sercurity配置
 * Created by Administrator on 2017/9/7.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,jsr250Enabled = true,prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    /**
     * 密码加密器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    /**
     * 注册aunthenticationProvider
     * @return
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin()
                .loginPage("/")
                .loginProcessingUrl("/loginUser")
                .failureForwardUrl("/loginFailure").failureUrl("/loginFailure")
                .successForwardUrl("/loginSuccess").defaultSuccessUrl("/loginSuccess")
                .and().rememberMe().tokenValiditySeconds(500000)
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccess")
                .and().authorizeRequests()
                .antMatchers("/","/logoutSuccess/**","/static/**",
                        "/ext-dev/**","/ext-prod/**","/loginFailure","/accessDenied/**").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint())
                .and().requiresChannel().antMatchers("/secret").requiresSecure()
                .and().csrf().disable();
    }


    @Override
    protected void configure(
            AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userService);//定义了provider，就不需要再手动注册userDetailsService
        auth.authenticationProvider(authenticationProvider());
    }

    /**
     * 没有权限的方位处理
     * @return
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        AccessDeniedHandlerImpl accessDeniedHandler = new AccessDeniedHandlerImpl();
        accessDeniedHandler.setErrorPage("/accessDenied");
        return accessDeniedHandler;
    }

    /**
     * 未验证身份的访问请求处理
     * @return
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new LoginUrlAuthenticationEntryPoint("/");
    }
}
