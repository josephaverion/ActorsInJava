import java.util.Arrays;

public class StringProcessorActor extends BaseActor<String> {

    protected int messageState = 0;
    protected boolean countSetting = false;

    public StringProcessorActor(boolean createdFromOtherActor) {
        super(createdFromOtherActor);
    }

    @Override
    protected void processMessage(Message message) {
        String messageStringValue = (String) message.getValue();
        switch (message.getMessage()) {
            case "store":
                if (messageState == 0) {
                    state.add(messageStringValue);
                    messageState = 1;
                    System.out.println("StringProcessorActor: Stored String");
                } else {
                    System.out.println("StringProcessorActor: Replaced String");
                }
                break;
            case "increment":
                if (messageState == 1) {
                     state.set(0, state.get(0) + messageStringValue);
                    break;
                }
            case "tokenize":
                if (messageState == 1) {
                    String[] tokens = state.get(0).split(messageStringValue);
                    state.remove(0);
                    state.addAll(Arrays.asList(tokens));
                    messageState = 2;
                    break;
                }
            case "process":
                if (messageStringValue.equals("palindromeCheck")) {
                    if (messageState == 1) {
                        this.references.add(0, new PalindromeCheckActor(true));
                        this.sendMessage(references.get(0), new Message("isPalindrome", state.get(0)));
                        break;
                    } else if (messageState == 2) {
                        for (int i = 0; i < state.size(); i++) {
                            this.references.add(new PalindromeCheckActor(true));
                        }
                        int k = 0;
                        for (int i = 0; i < references.size(); i++) {
                            if (references.get(i).getClass() == PalindromeCheckActor.class) {
                                this.sendMessage(references.get(i), new Message("isPalindrome", state.get(k)));
                                k++;
                            }
                        }
                        break;
                    }
                } else {
                    System.err.println("Invalid Message");
                    break;
                }
            case "count":
                CounterActor counterActor = null;
                boolean found = false;
                for (Actor actor : references) {
                    if (actor.getClass() == CounterActor.class) {
                        found = true;
                        counterActor = (CounterActor) actor;
                        break;
                    }
                }

                if (!found) {
                    countSetting = true;
                    references.add(new CounterActor(true));
                    for (Actor actor : references) {
                        if (actor.getClass() == CounterActor.class) {
                            this.sendMessage(actor, new Message("store", 0));
                            ((CounterActor) actor).printStorage();
                        }
                    }
                } else {
                    this.sendMessage(counterActor, new Message("increment", 1));
                }
                break;
            default:
                System.err.println("Invalid Message");
                break;
        }
    }
}
