import java.util.ArrayList;
import java.util.List;

public class Suspect {
    public String name;
    public String motive;
    public String method;
    public String alibi;
    public List<String> suspects;

    // Constructor
    public Suspect(String name, String motive, String method, String alibi) {
        this.name = name;
        this.motive = "Unknown";
        this.method = "Unknown";
        this.alibi = "Unknown";
        this.suspects = new ArrayList<>();
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMotive() {
        return motive;
    }

    public void setMotive(String motive) {
        this.motive = motive;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAlibi() {
        return alibi;
    }

    public void setAlibi(String alibi) {
        this.alibi = alibi;
    }

    public List<String> getSuspects() {
        return suspects;
    }

    public void setSuspects(List<String> suspects) {
        this.suspects = suspects;
    }

    public void addSuspect(String suspect) {
        suspects.add(suspect);
    }

    // Method to display suspect details
    public String displaySuspectDetails() {
        return "Suspect Name: " + name + "\n" +
               "Motive: " + motive + "\n" +
                "Method: " + method + "\n" +
               "Alibi: " + alibi + "\n" +
               "Suspects: " + suspects.toString();
    }

    // Method to format and display the suspects list
    public String displaySuspects() {
        if (suspects.isEmpty()) {
            return "No suspects available.";
        } else {
            StringBuilder suspectList = new StringBuilder();
            for (String suspect : suspects) {
                suspectList.append(suspect).append("\n");
            }
            return suspectList.toString();
        }
    }
}