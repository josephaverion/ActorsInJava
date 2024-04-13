public class Message {
    private String message;
    private Object value; // Can be Integer, String, or Actor (reference to another object)

    public Message(String message, Object value) {
        this.message = message;
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getValue() {
        return value;
    }

    public void getMessage(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Message: " + message + ", Value: " + value;
    }
}
