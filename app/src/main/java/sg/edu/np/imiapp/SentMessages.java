package sg.edu.np.imiapp;

public class SentMessages {
    public String toUIDUser;
    public String UIDcurrentuser;
    public String Message;

    public SentMessages() {
    }

    public SentMessages(String toUIDUser, String UIDcurrentuser, String message) {
        this.UIDcurrentuser = UIDcurrentuser;
        this.toUIDUser = toUIDUser;
        Message = message;
    }
}
