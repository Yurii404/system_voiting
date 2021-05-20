package Classes;

public class Elector extends  User {

    boolean voices =false;

    Elector(String name, String login, String password){
        super(name, login, password);
    }

    public boolean isVoted() {
        return voices;
    }

    public void setVoted(boolean voted) {
        this.voices = voted;
    }
}
