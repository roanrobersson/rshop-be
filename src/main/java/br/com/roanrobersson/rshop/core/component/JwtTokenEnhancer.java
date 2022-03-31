package br.com.roanrobersson.rshop.core.component;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import br.com.roanrobersson.rshop.domain.exception.UserNotFoundException;
import br.com.roanrobersson.rshop.domain.model.User;
import br.com.roanrobersson.rshop.domain.repository.UserRepository;

@Component
public class JwtTokenEnhancer implements TokenEnhancer {

	@Autowired
	private UserRepository userRepository;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

		String email = authentication.getName();
		User user = userRepository.findByEmail(email).orElseThrow(
				() -> new UserNotFoundException(String.format("There is no user with the email %s", email)));

		Map<String, Object> map = new HashMap<>();

		map.put("user_id", user.getId());
		map.put("user_name", user.getName());
		map.put("user_first_name", user.getFirstName());

		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
		token.setAdditionalInformation(map);

		return accessToken;
	}

}
