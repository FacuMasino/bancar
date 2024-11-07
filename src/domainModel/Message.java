package domainModel;

/**
 * Clase utilizada para comunicar mensajes de éxito/error entre
 * la capa Vista y Controlador
 *
 */
public class Message {

	private String message;
	private MessageType type;
	
	public enum MessageType {
		ERROR, SUCCESS
	}
	
	public Message(String message, MessageType type) {
		this.message = message;
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}
	
}
