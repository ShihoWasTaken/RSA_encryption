package communication;

public enum TypeAction {
	public_key ("Public Key"),
	message ("Message"),
	message_return ("Message envoyé");
	
	
	private final String name;       

    private TypeAction(String s) {
        name = s;
    }
    
    public String toString() {
        return this.name;
     }
}
