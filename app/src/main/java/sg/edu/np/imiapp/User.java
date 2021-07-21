package sg.edu.np.imiapp;

import java.util.ArrayList;

public class User {
    private String uid;
    private String username;
    private String profilePic;
    private ArrayList<String> interests;

    public User() {
    }

    public User(String uid, String username, String profilePic, ArrayList<String> interests) {

        this.uid = uid;
        this.username = username;
        this.profilePic = profilePic;
        this.interests = interests;

    }

    public String getUID() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getProfilePic() { return profilePic; }

    public ArrayList<String> getInterests() {
        return interests;
    }
}
