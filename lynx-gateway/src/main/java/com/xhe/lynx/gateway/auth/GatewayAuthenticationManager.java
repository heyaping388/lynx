package com.xhe.lynx.gateway.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.BearerTokenErrorCodes;
import reactor.core.publisher.Mono;

/**
 * @Auther: xhe
 * @Date: 2019/12/9 10:59
 * @Description: ReactiveAuthenticationManager
 */
@Slf4j
public class GatewayAuthenticationManager implements ReactiveAuthenticationManager {

    private TokenStore tokenStore;

    public GatewayAuthenticationManager(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication)
                .filter(a -> a instanceof BearerTokenAuthenticationToken)
                .cast(BearerTokenAuthenticationToken.class)
                .map(BearerTokenAuthenticationToken::getToken)
                .flatMap((accessTokenValue -> {
                    OAuth2AccessToken accessToken = tokenStore.readAccessToken(accessTokenValue);
                    if(null == accessToken){
                        OAuth2Error oAuth2Error = new OAuth2Error(BearerTokenErrorCodes.INVALID_TOKEN,"Invalid access token: " + accessTokenValue,null);
                        return Mono.error(new OAuth2AuthenticationException(oAuth2Error));
                    }
                    if(accessToken.isExpired()){
                        tokenStore.removeAccessToken(accessToken);
                        OAuth2Error oAuth2Error = new OAuth2Error(BearerTokenErrorCodes.INVALID_TOKEN,"Access token expired: " + accessTokenValue,null);
                        return Mono.error(new OAuth2AuthenticationException(oAuth2Error));
                    }
                    OAuth2Authentication result = tokenStore.readAuthentication(accessToken);
                    if (result == null) {
                        OAuth2Error oAuth2Error = new OAuth2Error(BearerTokenErrorCodes.INVALID_TOKEN,"Invalid access token: " + accessTokenValue,null);
                        return Mono.error(new OAuth2AuthenticationException(oAuth2Error));
                    }
                    return Mono.just(result);
                }))
                .cast(Authentication.class);
    }
}
