package SpringSecurity.SpringSecurity.service.auth;


import SpringSecurity.SpringSecurity.persistance.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${security.jwt.expiration-in-minutes}")
    private Long EXPIRATION_IN_MINUTES;

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    public String generateToken(UserDetails user, Map<String, Object> extraClaims){

        Date issueDat = new Date(System.currentTimeMillis());
        Date expirationInSecondsDate = new Date((EXPIRATION_IN_MINUTES * 60 * 1000) + issueDat.getTime()); // convierte el tiempo de expiración a milisegundos
        String jwt = Jwts.builder()
                // ==header
                .header().add("typ", "JWT").and() // yo
                // == pyload
                .claims(extraClaims)
                .subject(user.getUsername())
                .issuedAt(issueDat) // fecha de emision en milisegundos
                .expiration(expirationInSecondsDate) // fecha de expiracion en milisegundos
                // === firma
                .signWith(generateKey(), Jwts.SIG.HS256) // yo


                .compact(); // ESTE ES EL METODO QUE HACE EL PROCESO DE generar los base 64 del header del pyload y de la firma

        return jwt;
    }

    // genera y devuelve la clave secreta decodificada ya que en las propiedades ya esta codificadda
    private SecretKey generateKey() {
        byte [] passwordDecoded = Decoders.BASE64.decode(SECRET_KEY); // decodificar porque en las propiedades ya está codificada en base 64

        // String passwordPase64 = Encoders.BASE64.encode(passwordDecoded); // esta reia para codificar en base 64
        // byte [] passwordEncodeBytes = passwordPase64.getBytes(StandardCharsets.UTF_8); // obtener en bytes  el passwordPase64

        System.out.println("contraseña decodificada " + new String(passwordDecoded));
        return Keys.hmacShaKeyFor(passwordDecoded);
    }



    // Los siguientes dos metodos son usados para validar un tocken

    public String extractUsername(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }
    private Claims extractAllClaims(String jwt){
        return Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(jwt).getPayload(); //parseSignedClaims me indica que es un jwt firmado
    }
}
// # Los Claims son las afirmaciones o declaraciones que se incluyen en el
// cuerpo del token y contienen información
// # sobre el usuario y los permisos asociados