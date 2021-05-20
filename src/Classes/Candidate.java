package Classes;

public class Candidate {

    private String nameCandidat;
    private int voices;



    private String post;



    Candidate(String name,String post){
        this.nameCandidat = name;
        this.voices = 0;
        this.post = post;
    }

    public void addVoice() {
        this.voices = this.voices + 1;;
    }

    public int getVoices() {
        return voices;
    }

    public String getNameCandidat() {
        return nameCandidat;
    }

    public String getPost() {
        return post;
    }


}
