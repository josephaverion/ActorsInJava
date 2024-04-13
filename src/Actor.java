public interface Actor {
    void sendMessage(Actor recipient, Message message);
    void receiveMessage(Message message);
    void start();
    void stop();
    void setLastSender(Actor sender);
}