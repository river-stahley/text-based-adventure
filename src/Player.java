import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {
    public String location;
    public String name;
    public List<String> clues;

    //Constructor
    public Player(String name) {
        this.name = name;
        this.location = "Crime Scene";
        this.clues = new ArrayList<>();
    }

    // Getters and Setters
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getClues() {
        return clues;
    }

    public void setClue(List<String> clues) {
        this.clues = clues;
    }

    public void addClue(String clue) {
        clues.add(clue);
    }

    //Method to display player details
    public String displayPlayerDetails() {
        return "Player Name: " + name + "\n" +
               "Current Location: " + location + "\n" +
               "Clues: " + clues.toString();
    }

    // Method to format and display the clues list
    public String displayClues() {
        if (clues.isEmpty()) {
            return "No clues found yet.";
        }
        StringBuilder cluesList = new StringBuilder("Clues:\n");
        for (int i = 0; i < clues.size(); i++) {
            cluesList.append((i + 1)).append(". ").append(clues.get(i)).append("\n");
        }
        return cluesList.toString();
    }

    // Save player data to a file
    public void saveProgress(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save.dat"))) {
            oos.writeObject(this);
        }
    }

    // Load player data from a file
    public static Player loadProgress(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("save.dat"))) {
            return (Player) ois.readObject();
        }
    }
}