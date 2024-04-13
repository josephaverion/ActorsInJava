public class CalculationActor extends BaseActor<Object> {

    @Override
    protected void processMessage(Message message) {
        switch (message.getMessage()) {
            case "store":
                state.add(message.getValue());
                break;
            case "increment":
                state.set(0, (Integer) state.get(0) + 1);
                break;
            default:
                System.err.println("Invalid Message");
                break;
        }
    }

    public void printStorage() {
        System.out.println(state);
    }
}