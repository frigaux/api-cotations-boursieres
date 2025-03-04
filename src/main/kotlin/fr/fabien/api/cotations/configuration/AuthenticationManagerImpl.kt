package fr.fabien.api.cotations.configuration

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication

// TODO : delete
class AuthenticationManagerImpl: AuthenticationManager {
    override fun authenticate(authentication: Authentication): Authentication {
        return authentication
    }
}