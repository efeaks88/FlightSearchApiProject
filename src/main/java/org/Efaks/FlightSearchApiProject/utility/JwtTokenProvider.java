package org.Efaks.FlightSearchApiProject.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.Efaks.FlightSearchApiProject.exception.EErrorType;
import org.Efaks.FlightSearchApiProject.exception.UserManagerException;
import org.Efaks.FlightSearchApiProject.repository.enums.ERole;
import org.Efaks.FlightSearchApiProject.repository.enums.EStatus;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class JwtTokenProvider {
    private final String issuer = "issuer";
    private final String secretKey = "secretkey";
    private final String audience = "audience";

    public Optional<String> createToken(Long userId, ERole eRole, EStatus status, String userName) {
        String token = null;
        Long exDate = 9000000L;

        try {
            token = JWT.create().withAudience(new String[]{this.audience})
                    .withClaim("userId", userId)
                    .withClaim("userName", userName)
                    .withClaim("howtopage", "UserAuthenticationMicroService")
                    .withClaim("lastjoin", System.currentTimeMillis())
                    .withClaim("role", String.valueOf(eRole))
                    .withClaim("status", String.valueOf(status)).withIssuer(this.issuer).withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + exDate)).sign(Algorithm.HMAC512(this.secretKey));
            return Optional.of(token);
        } catch (Exception var) {
            return Optional.empty();
        }
    }

    public Boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(this.secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(this.issuer).withAudience(new String[]{this.audience}).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if (decodedJWT == null) {
                return false;
            }
        } catch (Exception var5) {
            System.out.println(var5.getMessage());
            throw new UserManagerException(EErrorType.INVALID_TOKEN);
        }

        return true;
    }

    public Optional<Long> getIdFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(this.secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(this.issuer).withAudience(new String[]{this.audience}).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if (decodedJWT == null) {
                throw new UserManagerException(EErrorType.NOT_DECODED);
            } else {
                Long id = decodedJWT.getClaim("userId").asLong();

                return Optional.of(id);
            }
        } catch (Exception var) {
            throw new UserManagerException(EErrorType.INVALID_TOKEN);
        }
    }

    public Optional<String> getRoleFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(this.secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(this.issuer).withAudience(new String[]{this.audience}).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if (decodedJWT == null) {
                throw new UserManagerException(EErrorType.INVALID_TOKEN);
            } else {
                String role = decodedJWT.getClaim("role").asString();
                return Optional.of(role);
            }
        } catch (Exception var6) {
            System.out.println(var6.getMessage());
            throw new UserManagerException(EErrorType.INVALID_TOKEN);
        }
    }

    public EStatus getStatusFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(this.secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(this.issuer).withAudience(new String[]{this.audience}).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if (decodedJWT == null) {
                throw new UserManagerException(EErrorType.INVALID_TOKEN);
            } else {
                EStatus status = (EStatus)decodedJWT.getClaim("status").as(EStatus.class);
                return status;
            }
        } catch (Exception var6) {
            System.out.println(var6.getMessage());
            throw new UserManagerException(EErrorType.INVALID_TOKEN);
        }
    }
    public Boolean verifyToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .withAudience(audience)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception exception){
            return false;
        }
    }
}
