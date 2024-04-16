public class PalindromeCheckActor extends BaseActor<String> {

    boolean isPalidronme = true;

    public PalindromeCheckActor(boolean createdFromOtherActor) {
        super(createdFromOtherActor);
    }

    @Override
    protected void processMessage(Message message) {
        String messageStringValue = (String) message.getValue();
        if (message.getMessage().equals("isPalindrome")) {
            int i = 0, j = messageStringValue.length() - 1;
            while (i < j) {
                if (messageStringValue.charAt(i) != messageStringValue.charAt(j)) {
                    System.out.println(messageStringValue + " is not a palindrome");
                    isPalidronme = false;
                    break;
                }
                i++;
                j--;
            }
            if (isPalidronme) {
                System.out.println(messageStringValue + " is a palindrome");
                for (int k = 0; k < receiveHistory.size(); k++) {
                    if (receiveHistory.get(k).getClass() == StringProcessorActor.class) {
                        this.sendMessage(receiveHistory.get(k), new Message("count", "1"));
                    }
                }
            }
        }
        this.stop();
    }
}
