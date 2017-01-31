package communication;

import java.io.Serializable;

import RSA.PublicKey;

public class Message  implements Serializable {
	

	private String message;
	private PublicKey key;
	private TypeAction type;
	
	public Message(TypeAction leType, String leMessage, PublicKey leKey){
		type = leType;
		message = leMessage;
		key = leKey;
	}
	
	public String getMessage() {
		return message;
	}

	public PublicKey getKey() {
		return key;
	}

	public TypeAction getType() {
		return type;
	}
}
