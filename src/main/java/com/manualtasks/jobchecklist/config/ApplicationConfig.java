package com.manualtasks.jobchecklist.config;

import java.util.Collections;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

@Configuration
@EnableAsync
@Lazy
@EnableWebSecurity
public class ApplicationConfig {

	private static Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

//	private final SftpAuthenticationProvider authenticationProvider;
//
//	public ApplicationConfig(SftpAuthenticationProvider authenticationProvider) {
//		this.authenticationProvider = authenticationProvider;
//	}

	@Bean
	@Scope(value = "prototype")
	public ChannelSftp connectSftp(String hostServer, String username, String password) throws JSchException {
		Session session = new JSch().getSession(username, hostServer, 22);
		session.setPassword(password);
		session.setConfig("StrictHostKeyChecking", "no");
		session.connect();
		ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
		sftpChannel.connect();
		if (sftpChannel.isConnected()) {
			logger.info("Connected to - {}", session.getHost());
		}
		return sftpChannel;
	}

	public void disconnectSftp(ChannelSftp sftpChannel) throws JSchException {
		Session session = sftpChannel.getSession();
		if (sftpChannel != null && sftpChannel.isConnected()) {
			sftpChannel.disconnect();
		}
		if (session != null && session.isConnected()) {
			session.disconnect();
		}
		if (!session.isConnected()) {
			logger.info("Disconnected from - {}", session.getHost());
		}
	}

	@Bean("asyncTaskExecutor")
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(3);
		executor.setMaxPoolSize(3);
		executor.setQueueCapacity(15);
		executor.setThreadNamePrefix("SFTP Thread-");
		executor.initialize();
		return executor;
	}

	@Bean
	public InMemoryUserDetailsManager userDetailsManager() {
		UserDetails user1 = User.builder().username("AL04040").password("{noop}Ascs@123").roles("ADMIN", "USER")
				.build();
		UserDetails user2 = User.builder().username("ASCS-L1").password("{noop}Ascs@123").roles("ADMIN", "USER")
				.build();
		return new InMemoryUserDetailsManager(user1, user2);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.csrf().disable()
				.authorizeRequests(configurer -> configurer.antMatchers("/resources/**", "/images/**","/process-data").permitAll()
						.anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").loginProcessingUrl("/authenticate-user").permitAll());
//			.logout(logout -> logout.permitAll())
//			.exceptionHandling(configurer -> configurer.accessDeniedPage("/access-denied"));
		return httpSecurity.build();
	}

}
