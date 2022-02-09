package com.orbital3d.server.tei.security;

import java.util.Arrays;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.orbital3d.server.tei.database.document.User;
import com.orbital3d.server.tei.database.repository.UserRepository;
import com.orbital3d.server.tei.service.PasswordService;

@Component
public class SecurityConfiguration
{
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordService passwordService;

	@Bean
	public Realm authenticatingRealm()
	{
		return new AuthenticatingRealm()
		{

			@Override
			protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
			{
				UsernamePasswordToken userPasswordToken = (UsernamePasswordToken) token;
				User user = userRepository.findByUserName(userPasswordToken.getUsername());
				if (user != null)
				{
					byte[] hashedPassword = passwordService.hashPassword(new String(userPasswordToken.getPassword()), user.getSalt());
					if (Arrays.compare(hashedPassword, user.getPassword()) == 0)
					{
						return new SimpleAccount(user.getUserName(), user, getName());
					}
				}
				throw new AuthenticationException("Invalid credentials");
			}

			@Override
			protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException
			{
				// TODO : Figure out how this should really be done
			}

		};
	}

	@Bean
	protected Realm authorizingzRealm()
	{
		return new AuthorizingRealm()
		{

			@Override
			protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
			{
				// Authenticated n other realm
				return null;
			}

			@Override
			protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
			{
				String userName = (String) principals.getPrimaryPrincipal();
				// TODO : Get authorization information fr the user
				SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
				authorizationInfo.addStringPermission("tei:index");
				authorizationInfo.addStringPermission("tei:view");
				authorizationInfo.addStringPermission("tei:send");
				return authorizationInfo;
			}
		};
	}

	@Bean
	public ShiroFilterChainDefinition shiroFilterChainDefinition()
	{
		// TODO ; Figure out how this shuld be done correctly

		DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

		// logged in users with the 'admin' role
		chainDefinition.addPathDefinition("/admin/**", "authc, roles[admin]");

		// all other paths require a logged in user or uses annotations, need to figure
		// out how to protect resource paths as needed!
		chainDefinition.addPathDefinition("/**", "anon");
		return chainDefinition;
	}

	@Bean
	protected CacheManager cacheManager()
	{
		return new MemoryConstrainedCacheManager();
	}
}
