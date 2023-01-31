package com.example.springsecuritydemo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;

@Configuration
public class SecurityConfig {
    @Autowired
    VerifyCodeFilter verifyCodeFilter;

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    protected UserDetailsService configure() {
        InMemoryUserDetailsManager users = new InMemoryUserDetailsManager();
        users.createUser(User.withUsername("Pore").password("1234").roles("admin").build());
        users.createUser(User.withUsername("user").password("1234").roles("user").build());
        return users;
    }

    /**
     * 忽略过滤地址
     *
     * @return
     */
    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("/hello", "/vercode");
    }

    /**
     * 角色继承
     *
     * @return
     */
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        //admin可以访问所有user的接口
        hierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return hierarchy;
    }

    /**
     * 登录页面，参数等，进行定制
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(verifyCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                //如果请求路径满足 /admin/** 格式，则用户需要具备 admin 角色。
                //如果请求路径满足 /user/** 格式，则用户需要具备 user 角色。
                //剩余的其他格式的请求路径，只需要认证（登录）后就可以访问。
                /**antMatchers 必须在anyRequest 前面**/
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/user/**").hasRole("user")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //成功回调
                .successHandler((req, resp, authentication) -> {
                    Object principal = authentication.getPrincipal();
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(principal));
                    out.flush();
                    out.close();
                    //失败回调
                }).failureHandler((req, resp, e) -> {
            resp.setContentType("application/json;charset=utf-8");
            PrintWriter out = resp.getWriter();
            out.write(e.getMessage());
            out.flush();
            out.close();
        }).and().logout().logoutUrl("/logout")
                //注销回调
                .logoutSuccessHandler((req, resp, authentication) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write("注销成功");
                    out.flush();
                    out.close();
                })
                .permitAll()
                .and()
                .csrf().disable();
//                //未登录回调 测试方便,先注释
//                .exceptionHandling().authenticationEntryPoint((req, resp, authException) -> {
//                    resp.setContentType("application/json;charset=utf-8");
//                    PrintWriter out = resp.getWriter();
//                    out.write("尚未登录，请先登录");
//                    out.flush();
//                    out.close();
//                }
//        );
        return http.build();
    }

}
