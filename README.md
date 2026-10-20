# Exemple d'API combinant Spring Boot Web et Kotlin
- spring-boot-starter-parent 4.1.1
  - spring-boot-starter-webmvc
  - spring-boot-starter-webmvc-test
  - spring-boot-starter-tomcat-runtime
  - spring-boot-starter-actuator
  - spring-boot-starter-validation (annotations correspondant aux contraintes pour la validation des données)
  - spring-boot-starter-cache
  - spring-boot-starter-security (spring-security-config 7.1.1 et spring-security-web 7.1.1)
    - spring-security-oauth2-resource-server 7.1.1 (APIs via OAuth 2.0 Bearer Tokens)
    - spring-security-oauth2-jose 7.1.1 (Javascript Object Signing and Encryption : JSON Web Token, JSON Web Signature, JSON Web Encryption, JSON Web Key)
- springdoc-openapi-starter-webmvc-ui 3.1.1
- kotlin 2.4.21
  - kotlin-stdlib
  - kotlin-reflect
  - kotlin-test-junit5
- kotlin-logging-jvm 3.0.5
- dépendance JPA
- mysql 8.0.36, mysql-connector-j 9.7.0
- H2 2.5.252 (tests)

# Spring Security : OAuth 2.0 Resource Server JWT
[OAuth 2.0 Resource Server JWT](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html)

## Appel sans le Bearer
```mermaid
  sequenceDiagram
    Navigateur->>BearerTokenAuthenticationFilter: appel d'une resource protégée
    BearerTokenAuthenticationFilter->>ProviderManager: authentification
    ProviderManager->>JwtAuthenticationProvider: authentification
    JwtAuthenticationProvider->>ProviderManager: InvalidBearerTokenException
    ProviderManager->>BearerTokenAuthenticationFilter: InvalidBearerTokenException
    BearerTokenAuthenticationFilter->>Navigateur: 401
```

## Appel avec le Bearer dans le header Authorization
```mermaid
  sequenceDiagram
    Navigateur->>BearerTokenAuthenticationFilter: appel d'une resource protégée
    BearerTokenAuthenticationFilter->>ProviderManager: authentification
    ProviderManager->>JwtAuthenticationProvider: authentification
    JwtAuthenticationProvider->>NimbusJwtDecoder: décodage du JWT (vérification signature clé secrète)
    NimbusJwtDecoder->>JwtAuthenticationProvider: JWT
    JwtAuthenticationProvider->>JwtAuthenticationConverter: GrantedAuthority
    JwtAuthenticationConverter->>JwtAuthenticationProvider: granted authorities
    JwtAuthenticationProvider->>ProviderManager: JwtAuthenticationToken
    ProviderManager->>BearerTokenAuthenticationFilter: JwtAuthenticationToken
    BearerTokenAuthenticationFilter->>SecurityContext: JwtAuthenticationToken
    BearerTokenAuthenticationFilter->>DispatcherServlet: authentification réussie avec stockage du JwtAuthenticationToken dans SecurityContext
    DispatcherServlet->>Navigateur: exécution de la resource et renvoie du résultat au navigateur
```


# Spring Security : authentification OAuth2 avec GitHub
[Spring Boot : OAuth avec GitHub](https://spring.io/guides/tutorials/spring-boot-oauth2)

[GitHub : OAuth](https://docs.github.com/en/apps/oauth-apps/building-oauth-apps/authorizing-oauth-apps)

## Enregistrement de la nouvelle application OAuth sur GitHub
[Enregistrement d'une application sur GitHub](https://github.com/settings/applications/new)
```mermaid
  sequenceDiagram
    Développeur->>GitHub: nom et description de la nouvelle application, url de la page d'accueil, url de retour après une authentification réussie
    GitHub->>Développeur: "client ID" et "client secret" de l'application crée
```

## Scénario d'authentification auprès de GitHub par Spring Security
```mermaid
  sequenceDiagram
    Utilisateur->>Navigateur: charge une page du site
    Navigateur->>SpringSecurity: envoie la requête
    SpringSecurity->>Navigateur: session inexistante, redirige le navigateur vers la page d'authentification de GitHub avec le "client id" identifiant l'application
    Navigateur->>Utilisateur: affiche la page d'authentification de GitHub
    Utilisateur->>GitHub: s'authentifie avec succès
    GitHub->>Navigateur: redirige vers l'url de retour avec un "code temporaire"
    Navigateur->>SpringSecurity: envoie la requête avec le "code temporaire"
    SpringSecurity->>GitHub: récupèration d'un "access token" avec le "code temporaire" + le "client ID" et "client secret" de l'application
    GitHub->>SpringSecurity: "access token"
    SpringSecurity->>GitHub: utilisation de l'"access token" pour appeler l'API GitHub, notamment la récupération du profil de l'utilisateur authentifié afin de le stocker en session
    GitHub->>SpringSecurity: profil de l'utilisateur authentifié
    SpringSecurity->>Navigateur: affiche la page d'acceuil en lui joignant le cookie JSESSIONID (les pages peuvent désormais afficher les informations utilisateur via la session)
```
