package fst.GestionRessource.User.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class PasswordDeserializer extends JsonDeserializer<String> {
  @Override
  public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    String password = p.getText().trim();  // Retrieve password text and trim it
    // You can add additional logic here to handle the password
    return password;  // Return the password as is or encode/decode it as needed
  }
}
