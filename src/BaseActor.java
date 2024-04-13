import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class BaseActor<E> implements Actor, Runnable {
    private final BlockingQueue<Message> mailBox = new LinkedBlockingQueue<>();
    private Thread thread;
    private volatile boolean running = true;

    protected Actor lastSender = null;
    protected List<E> state = new ArrayList<>();

    @Override
    public void sendMessage(Actor recipient, Message message) {
        recipient.receiveMessage(message);
        recipient.setLastSender(this);
    }

   public void setLastSender(Actor sender) {
        this.lastSender = sender;
   }

    @Override
    public void receiveMessage(Message message) {
        mailBox.offer(message);
    }

    @Override
    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void stop() {
        running = false;
        thread.interrupt();
    }

    public List<Message> getQueueSnapshot() {
        return new ArrayList<>(mailBox);
    }

    @Override
    public void run() {
        while (running) {
            try {
                Message message = mailBox.take(); // Blocks if queue is empty
                processMessage(message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    protected abstract void processMessage(Message message);
}