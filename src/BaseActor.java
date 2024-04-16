import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class BaseActor<E> implements Actor, Runnable {
    private final BlockingQueue<Message> mailBox = new LinkedBlockingQueue<>();
    private Thread thread;
    private volatile boolean running = true;

    protected ArrayList<Actor> references = new ArrayList<>();
    protected ArrayList<Actor> receiveHistory = new ArrayList<>();
    protected List<E> state = new ArrayList<>();

    public BaseActor(boolean createdFromOtherActor) {
        if (createdFromOtherActor) start();
    }
    @Override
    public void sendMessage(Actor recipient, Message message) {
        recipient.receiveMessage(message);
        recipient.addToSenderHistory(this);
    }

   public void addToSenderHistory(Actor sender) {
        this.receiveHistory.add(sender);
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

    public void printStorage() {
        System.out.println(state);
    }

    public List<E> getStorage() {
        return state;
    }
}