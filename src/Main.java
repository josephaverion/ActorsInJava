public class Main {
    public static void main (String[] args) throws InterruptedException {
        CalculationActor actor1 = new CalculationActor();
        CalculationActor actor2 = new CalculationActor();
        CalculationActor actor3 = new CalculationActor();

        actor1.start();
        actor2.start();

        System.out.println("Actor 2 Mailbox:" + actor2.getQueueSnapshot());
        actor1.sendMessage(actor2, new Message("store", 2));
        System.out.println("Actor 2 Mailbox:" + actor2.getQueueSnapshot());
        Thread.sleep(1000);
        System.out.println("Actor 2 Mailbox:" + actor2.getQueueSnapshot());
        actor2.printStorage();
        actor1.sendMessage(actor2, new Message("increment", 2));
        System.out.println("Actor 2 Mailbox:" + actor2.getQueueSnapshot());
        Thread.sleep(1000);
        actor2.printStorage();
    }
}