public class GameStory {
    private final String[][] storySegments;
    private int currentSegment;
    private final int killerIndex;
    private final Suspect killer;

    public GameStory(Suspect killer) {
        this.killer = killer;
        this.killerIndex = getKillerIndex(killer);

        // Add as many story segments as you like here
        // Each row is a different suspect's story
        storySegments = new String[][] {
            { // Dr. William Patrick
                "You arrive at the scene. Dr. William Patrick is acting strangely.",
                "You find psychiatric notes that don't add up.",
                "A patient mentions Dr. Patrick's late-night visits.",
                "You confront Dr. Patrick. He stumbles over his alibi.",
                "You discover a prescription bottle at the scene.",
                "Dr. Patrick tries to leave town.",
                "You reveal Dr. Patrick as the killer!"
            },
            { // Mr. Paul Todd
                "You arrive at the scene. Mr. Paul Todd is already there.",
                "Business records show recent arguments.",
                "A secretary mentions Todd's threats.",
                "You find a torn contract in the trash.",
                "Todd tries to bribe you.",
                "You catch Todd sneaking into the victim's office.",
                "You reveal Mr. Paul Todd as the killer!"
            },
            { // Mrs. Annie West
                "You arrive at the scene. Mrs. Annie West is crying.",
                "Neighbors mention loud arguments last night.",
                "You find a hidden letter from Annie.",
                "Annie's fingerprints are on the murder weapon.",
                "She tries to blame the lover.",
                "You find Annie's diary with a confession.",
                "You reveal Mrs. Annie West as the killer!"
            },
            { // Ms. Karen Turner
                "You arrive at the scene. Ms. Karen Turner avoids your gaze.",
                "You find love letters between Karen and the victim.",
                "Karen's alibi doesn't check out.",
                "A friend says Karen was jealous.",
                "You find Karen's scarf at the scene.",
                "Karen tries to flee the city.",
                "You reveal Ms. Karen Turner as the killer!"
            },
            { // Mr. Wayne Cady
                "You arrive at the scene. Mr. Wayne Cady is defensive.",
                "Cady's rivalry with the victim is well known.",
                "You find threatening emails from Cady.",
                "Cady's car was seen near the scene.",
                "You find Cady's glove in the garden.",
                "Cady tries to intimidate you.",
                "You reveal Mr. Wayne Cady as the killer!"
            },
            { // Mr. Jack Shipman
                "You arrive at the scene. Jack Shipman is too helpful.",
                "Jack's timeline doesn't match up.",
                "You find muddy boots in Jack's house.",
                "A neighbor saw Jack near the scene.",
                "You find Jack's watch at the crime scene.",
                "Jack tries to mislead your investigation.",
                "You reveal Mr. Jack Shipman as the killer!"
            },
            { // Mr. Eugene West
                "You arrive at the scene. Eugene West is missing.",
                "Eugene had a fight with the victim yesterday.",
                "You find Eugene's phone at the scene.",
                "A friend says Eugene was desperate for money.",
                "You find a withdrawal slip in Eugene's name.",
                "Eugene tries to escape town.",
                "You reveal Mr. Eugene West as the killer!"
            }
        };
        currentSegment = 0;
    }

    private int getKillerIndex(Suspect killer) {
        String[] names = {
            "Dr. William Patrick", "Mr. Paul Todd", "Mrs. Annie West",
            "Ms. Karen Turner", "Mr. Wayne Cady", "Mr. Jack Shipman", "Mr. Eugene West"
        };
        for (int i = 0; i < names.length; i++) {
            if (killer.getName().equals(names[i])) return i;
        }
        return 0; // default
    }

    public String getCurrentSegment() {
        return storySegments[killerIndex][currentSegment];
    }

    public String nextSegment() {
        if (currentSegment < storySegments[killerIndex].length - 1) {
            currentSegment++;
        }
        return getCurrentSegment();
    }

    public int getCurrentSegmentIndex() {
        return currentSegment;
    }

    public boolean hasNext() {
        return currentSegment < storySegments[killerIndex].length - 1;
    }

    public void reset() {
        currentSegment = 0;
    }

    public Suspect getKiller() {
        return killer;
    }   
}