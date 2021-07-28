package sg.edu.np.imiapp;

public class firebase_Threads {
    public String interestName;
    public String threadName;

    public firebase_Threads(){}

    public firebase_Threads(String InterestName, String ThreadName){
        interestName = InterestName;
        threadName = ThreadName;
    }

    public String getInterestName() {
        return interestName;
    }

    public void setInterestName(String interestName) {
        this.interestName = interestName;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }
}
