package Classes;

public class ModelTable {

    String name;
    int voices;

    public ModelTable(String name, int voices) {
        this.name = name;
        this.voices = voices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVoices() {
        return voices;
    }

    public void setVoices(int voices) {
        this.voices = voices;
    }
}
