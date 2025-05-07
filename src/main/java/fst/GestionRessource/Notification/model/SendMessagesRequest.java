package fst.GestionRessource.Notification.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMessagesRequest {
	private String message;
	private String sender;
	private String[] receivers;
	private NotificationType type;
}
