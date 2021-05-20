package Classes;

import com.mysql.cj.protocol.Resultset;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Main extends Application {
/*________________________________________________________________________________*/
    private static int currentUser = 0;
    private static boolean curentVote = false;
    private  static  List <String> posts = new ArrayList<String>();
    private static boolean autorize = false;
    private static boolean admin = false;
    private static ObservableList<ModelTable> winners = FXCollections.observableArrayList();
/*________________________________________________________________________________*/

    public static void addPosts(String post) {
        posts.add(post);
    }
//////////////////////////////////////////////////////////////////////
    public static List<String> getPosts() {
        return posts;
    }
////////////////////////////////////////////////////////////////////////
    public static int getCurrentUser() {
        return currentUser;
    }

    public static boolean isAutorize() {
        return autorize;
    }

    public static boolean isAdmin() {
        return admin;
    }

    public static void setAutorize(boolean autorize) {
        Main.autorize = autorize;
    }

    public static void setAdmin(boolean admin) {
        Main.admin = admin;
    }

    public static boolean isCurentVote() {
        return curentVote;
    }

    public static ObservableList<ModelTable> getWinners() {
        return winners;
    }

    /*________________________________________________________________________________*/
    private double xOffset;
    private double yOffset;

    static DatabaseHandler dbHandler = new DatabaseHandler();


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXMLs/startPage.fxml"));
        loader.load();

        Parent root = loader.getRoot();

        Scene scene = new Scene(root);
        /*____________________________________________________________________________________________________________________*/
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            }
        });                                                                 /*Пересування форми по натисканю на мишу*/
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }
        });
        /*____________________________________________________________________________________________________________________*/

        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.show();


    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        String insert = "INSERT INTO " + Const.USER_TABLE + " ( " + Const.USERS_NAME_ + ","
                + Const.USERS_LOGIN + "," + Const.USERS_PASS + "," + Const.USERS_ITVOTE + " ) " + " VALUES(?,?,?,?)";


        try {
            PreparedStatement regSt = dbHandler.getDbConnection().prepareStatement(insert);
            regSt.setString(1, "admin");
            regSt.setString(2, "admin");
            regSt.setString(3, "admin");
            regSt.setBoolean(4, true);

            regSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        launch(args);
    }

    /*____________________________________________________________________________________________________________________*/
    /*
     * Реєстрація нового користувача
     */
    public static void addUser(Elector user) {


        String insert = "INSERT INTO " + Const.USER_TABLE + " (" + Const.USERS_NAME_ + ","
                + Const.USERS_LOGIN + "," + Const.USERS_PASS + "," + Const.USERS_ITVOTE + ") " + " VALUES(?,?,?,?)";


        try {
            PreparedStatement regSt = dbHandler.getDbConnection().prepareStatement(insert);
            regSt.setString(1, user.getName());
            regSt.setString(2, user.getLogin());
            regSt.setString(3, user.getPassword());
            regSt.setBoolean(4, user.isVoted());

            regSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    ;

    /*____________________________________________________________________________________________________________________*/
    /*
     * Реєстрація нового кандидата
     */
    public static void addCandidat(Candidate candidat) {

        String insert = "INSERT INTO " + Const.CANDIDAT_TABLE + "(" + Const.CANDIDATS_POST + ","
                + Const.CANDIDATS_NAME + "," + Const.CANDIDATS_VOICES + ")" + " VALUES(?,?,?)";


        try {
            PreparedStatement regSt = dbHandler.getDbConnection().prepareStatement(insert);
            regSt.setString(1, candidat.getPost());
            regSt.setString(2, candidat.getNameCandidat());
            regSt.setInt(3, candidat.getVoices());


            regSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



/*____________________________________________________________________________________________________________________*/
    /*
     * авторизація в системі
     */
    public static void findUser(String login, String password) throws SQLException {

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_LOGIN + "=? AND " + Const.USERS_PASS + "=?";



        ResultSet rs = null;

        try {
            PreparedStatement findSt = dbHandler.getDbConnection().prepareStatement(select);
            findSt.setString(1, login);
            findSt.setString(2, password);


            rs = findSt.executeQuery();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        while (rs.next()) {
            curentVote = rs.getBoolean((Const.USERS_ITVOTE));
            currentUser = rs.getInt("id");
            autorize = true;

        }


        if(login.equals("admin") && password.equals("admin")){
            admin = true;
        }



    }
/*____________________________________________________________________________________________________________________*/
    /*
    Додає кандтдату голос
     */
    public static void voted(String name_candidat){

        String name = name_candidat;

        String updateCand = "UPDATE " + Const.CANDIDAT_TABLE + " SET " + Const.CANDIDATS_VOICES + "= " + Const.CANDIDATS_VOICES +"+1"
                 + " WHERE " + Const.CANDIDATS_NAME + "='" + name + "'";

        Statement upSt = null;
        try {

            upSt = dbHandler.getDbConnection().createStatement();
            upSt.executeUpdate(updateCand);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
/*____________________________________________________________________________________________________________________*/
    /*
    Присваює юзеру стан що він вже голосував
     */
    public static void setVoitUser(){

        String update = "UPDATE " + Const.USER_TABLE + " SET " + Const.USERS_ITVOTE + "= true";

        Statement upSt = null;
        try {

            upSt = dbHandler.getDbConnection().createStatement();
            upSt.executeUpdate(update);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



    }
/*____________________________________________________________________________________________________________________*/
    /*
    Очищає данні з бд
     */
    public static void clear(){

        String truncuteUser = " TRUNCATE table " + Const.USER_TABLE;
        String truncuteCand = " TRUNCATE table " + Const.CANDIDAT_TABLE;

        Statement clearSt = null;
        try {
            clearSt = dbHandler.getDbConnection().createStatement();
            clearSt.execute(truncuteCand);
            clearSt.execute(truncuteUser);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
/*____________________________________________________________________________________________________________________*/
    /*
    Створює список з кандидатами і їх голосами
     */
   public static void getResult(String post) throws SQLException {

       String select = "SELECT * FROM " + Const.CANDIDAT_TABLE + " WHERE " +
               Const.CANDIDATS_POST + "=? ";



       ResultSet rs = null;

       try {
           PreparedStatement selSt = dbHandler.getDbConnection().prepareStatement(select);
           selSt.setString(1, post);



           rs = selSt.executeQuery();


       } catch (SQLException e) {
           e.printStackTrace();
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       }


       while (rs.next()) {

           winners.add(new ModelTable(rs.getString(Const.CANDIDATS_NAME),rs.getInt(Const.CANDIDATS_VOICES)));

       }


   }
/*____________________________________________________________________________________________________________________*/

    public static String getWin(){

        ModelTable name = winners.get(1);

        for(int i =0;i<winners.size();i++){

            if(winners.get(i).getVoices()>name.getVoices()){
                name = winners.get(i);
            }

        }
        return name.getName();

    }
}