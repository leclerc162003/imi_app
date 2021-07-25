package sg.edu.np.imiapp;

public class ThreadMessage {
    public String threadID;
    public String sentUserID;
    public String sentUserName;
    public String messageContent;

    public String getThreadID() {
        return threadID;
    }

    public void setThreadID(String threadID) {
        this.threadID = threadID;
    }

    public String getSentUserID() { return sentUserID; }

    public void setSentUserID(String sentUserID) { this.sentUserID = sentUserID; }

    public String getSentUserName() { return sentUserName; }

    public void setSentUserName(String sentUserName) { this.sentUserName = sentUserID; }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
