public class CounterActor extends BaseActor<Integer> {

    public CounterActor(boolean createdFromOtherActor) {
        super(createdFromOtherActor);
    }

    @Override
    protected void processMessage(Message message) {
        Integer messageIntegerValue = (Integer) message.getValue();
        switch (message.getMessage()) {
            case "store":
                state.add(messageIntegerValue);
                break;
            case "increment":
                state.set(0, state.get(0) + messageIntegerValue);
                System.out.println("Amount of Palindromes: " + getStorage());
                break;
            default:
                System.err.println("Invalid Message");
                break;
        }
    }
}