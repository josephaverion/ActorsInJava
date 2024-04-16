public class Main {
    public static void main (String[] args) throws InterruptedException {

        StringProcessorActor stringProcessor = new StringProcessorActor(false);
        stringProcessor.start();
        stringProcessor.sendMessage(stringProcessor, new Message("store", "wow is that your"));

        System.out.println("Actor stringProcessor Mailbox:" + stringProcessor.getQueueSnapshot());
        Thread.sleep(500);

        stringProcessor.printStorage();

        stringProcessor.sendMessage(stringProcessor, new Message("increment", " racecar"));

        System.out.println("Actor stringProcessor Mailbox:" + stringProcessor.getQueueSnapshot());
        Thread.sleep(500);

        stringProcessor.printStorage();

        stringProcessor.sendMessage(stringProcessor, new Message("tokenize", " "));

        System.out.println("Actor stringProcessor Mailbox: " + stringProcessor.getQueueSnapshot());
        Thread.sleep(500);

        stringProcessor.printStorage();

        stringProcessor.sendMessage(stringProcessor, new Message("count","enable"));
        System.out.println("Actor stringProcessor Mailbox: " + stringProcessor.getQueueSnapshot());

        stringProcessor.sendMessage(stringProcessor, new Message("process","palindromeCheck"));
        System.out.println("Actor stringProcessor Mailbox: " + stringProcessor.getQueueSnapshot());
        Thread.sleep(500);

        stringProcessor.stop();
    }
}