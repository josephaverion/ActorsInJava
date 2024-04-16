import java.util.Arrays;
import java.util.Collections;

public class StringProcessorActor extends BaseActor<String> {

    int messageState = 0;
    String processType = "";
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
                    // send messages to other actors to reset because there is a new string
                }
                break;
            case "increment":
                state.set(0, state.get(0) + messageStringValue);
                break;
            case "tokenize":
                String[] tokens = state.get(0).split(messageStringValue);
                state.remove(0);
                state.addAll(Arrays.asList(tokens));
                messageState = 2;
                break;
            case "process":
                if (messageStringValue.equals("palindromeCheck")) {
                    if (messageState == 1) {
                        this.sendMessage(new PalindromeCheckActor(), new Message("isPalindrome", state.get(0)));
                    } else if (messageState == 2) {
                        for (String str : state) {
                            this.sendMessage(new PalindromeCheckActor(), new Message("isPalindrome", str));
                        }
                    }
                }
            default:
                System.err.println("Invalid Message");
                break;
        }
    }

    private void checkProcessType(String type) {
        processType = type;
    }
}
