package fst.GestionRessource.Authentication;

import fst.GestionRessource.User.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String accessToken;
    private User user;
    /*@JsonProperty("refresh_token")
    private String refreshToken;*/
}
