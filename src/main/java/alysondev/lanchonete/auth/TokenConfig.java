package alysondev.lanchonete.auth;

import alysondev.lanchonete.entity.Usuario;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;


@Component
public class TokenConfig {
    @Value("${jwt.secret}")
    private String secret;


    public String generateToken(Usuario usuario){
        Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.create().withClaim("userId", usuario.getId())
              .withSubject(usuario.getLogin())
              .withExpiresAt(Instant.now().plusSeconds(900 ))
              .withIssuedAt(Instant.now())
              .sign(algorithm);
    }
    public String generateRefreshToken(Usuario usuario){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create().withClaim("userId",usuario.getId())
                .withExpiresAt(Instant.now().plus(7, ChronoUnit.DAYS))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }
    public Optional<JWTUserData> validateToken(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);

//            return Optional.of(JWTUserData.builder()
//                    .userId(decodedJWT.getClaim("userId").asLong())
//                  .email(decodedJWT.getSubject())
//                   .build());0

            return Optional.of(new JWTUserData(
                    decodedJWT.getClaim("userId").asLong(),
                    decodedJWT.getSubject()
            ));
        } catch (JWTVerificationException ex) {
            return Optional.empty();

        }

    }
}
