package sg.edu.np.imiapp;

public class Interests {
    private String text;
    private boolean isSelected = false;
    
    public Interests(){
    }

    public Interests(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public boolean isSelected() {
        return isSelected;
    }
}
