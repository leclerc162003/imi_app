package sg.edu.np.imiapp;

public class ThreadMessage {
    public String threadName;
    public String interestName;
    public String sentUserId;
    public String sentUserName;
    public String messageContent;

    public String getSentUserId() {
        return sentUserId;
    }

    public void setSentUserId(String sentUserId) {
        this.sentUserId = sentUserId;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getInterestName() {
        return interestName;
    }

    public void setInterestName(String interestName) {
        this.interestName = interestName;
    }

    public String getSentUserName() {
        return sentUserName;
    }

    public void setSentUserName(String sentUserName) {
        this.sentUserName = sentUserName;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public ThreadMessage(){};

    public ThreadMessage(String ThreadName, String InterestName, String SentUserName, String SentUserId, String MessageContent){
        threadName = ThreadName;
        interestName = InterestName;
        sentUserId = SentUserId;
        sentUserName = SentUserName;
        messageContent = MessageContent;
    }

}
