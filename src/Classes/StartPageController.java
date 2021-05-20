package Classes;


import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Classes.Elector;
import Classes.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StartPageController {

    public Label userIsnueField;
    public Label fildElectField;
    public Pane doneElectFPane;
    public Label doneElectField;
    public Label doneElectField1;
    public Button clearButton;
    public Pane clearPane;
    public Button clearAllButton;
    public Pane showResultPane;
    public TableView<ModelTable> resutTanle;
    public TableColumn<ModelTable, String> columnName;
    public TableColumn<ModelTable, Integer> columnVoices;
    public Label winnerLabel;
    public Pane resultGolPane;
    public ComboBox selectBuletBox1;
    public Button showResultButton;
    List<String> candidats = new ArrayList<String>();


    @FXML
    public Button registrNewUserButton;

    @FXML
    public ImageView btnBack;

    @FXML
    public Pane createBuletPane;

    @FXML
    public Button addCandidatButton;

    @FXML
    public TextField nameCandidatField;

    @FXML
    public TextField postCandidatField;

    @FXML
    public Pane userElectPane;

    @FXML
    public ComboBox selectBuletBox;

    @FXML
    public ComboBox selectCandidatBox;

    @FXML
    public Button voiceButton;

    @FXML
    public Label doneRegLabel;
    public Label fielFindField;
    public BorderPane mainPane;
    public Pane AdminPane;
    public Label doneAddCandField;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private StackPane stackPaneRight;

    @FXML
    private Pane signUpPane;

    @FXML
    private TextField passField;

    @FXML
    private Button registrButton;

    @FXML
    private TextField loginField;

    @FXML
    private Button signUpButton;

    @FXML
    private Pane registrPane;

    @FXML
    private TextField nameField;

    @FXML
    private TextField loginRegField;

    @FXML
    private TextField passwordRegField;

    @FXML
    private StackPane stackPaneLeft;

    @FXML
    private Pane userMenuPane;

    @FXML
    private ImageView btnClose11;

    @FXML
    private ImageView btnMinimize11;

    @FXML
    private Button exitAccountButton1;

    @FXML
    private Pane adminMenuPane;

    @FXML
    private ImageView btnClose1;

    @FXML
    private ImageView btnMinimize1;

    @FXML
    private Button createVoitButton;

    @FXML
    private Button resultButton;

    @FXML
    private Button exitAccountButton;

    @FXML
    private Pane startPane;

    @FXML
    private ImageView btnClose;

    @FXML
    private ImageView btnMinimize;
    /*____________________________________________________________________________________________________________________*/

    String currentPost = null;

    /*____________________________________________________________________________________________________________________*/















    /*____________________________________________________________________________________________________________________*/

    @FXML
    void handleButtonAvtion(ActionEvent event) throws SQLException {

        /*____________________________________________________________________________________________________________________*/
        if (event.getSource().equals(registrButton)) {
            registrPane.toFront();/* Відкриття панелі реєстрації після натиску кнопки "Реєстрація"*/

        }
        /*____________________________________________________________________________________________________________________*/
        if (event.getSource().equals(registrNewUserButton)) {

            String name = nameField.getText().trim();
            String login = loginRegField.getText().trim();
            String password = passwordRegField.getText().trim();      /*Реєстрація користувача після натиску кнопки "Зареєструвати"*/

            if(nameField.getText().isEmpty() || loginRegField.getText().isEmpty() || passwordRegField.getText().isEmpty()){

                userIsnueField.setVisible(true);

            }
            else {
                Main.findUser(login, password);


                if (Main.isAutorize() == false) {
                    Main.addUser(new Elector(name, login, password));
                    userIsnueField.setVisible(false);
                    doneRegLabel.setVisible(true);
                    nameField.clear();
                    loginRegField.clear();
                    passwordRegField.clear();
                }
                if (Main.isAutorize() == true) {
                    userIsnueField.setVisible(true);
                }
            }
        }
        /*____________________________________________________________________________________________________________________*/
        if (event.getSource().equals(signUpButton)) {

            String login = loginField.getText().trim();
            String password = passField.getText().trim();

            Main.findUser(login, password);


            if (Main.isAutorize() == true) {

                if (Main.isAdmin() == true) {                        /*Авторизація користувача*/
                    AdminPane.toFront();
                    adminMenuPane.toFront();
                }
                if (Main.isAdmin() == false) {
                    userElectPane.toFront();
                    userMenuPane.toFront();

                    selectBuletBox.getItems().addAll(Main.getPosts());//заповняємо комбо бокс бюлетнями


                }

            }
            if (Main.isAutorize() == false) {
                fielFindField.setVisible(true);
            }


        }
 /*____________________________________________________________________________________________________________________*/
    if(event.getSource().equals(exitAccountButton) || event.getSource().equals(exitAccountButton1)){
        startPane.toFront();
        signUpPane.toFront();                               /*вИХІД З АКАУНТУ*/
        loginField.clear();
        passField.clear();
        Main.setAdmin(false);
        Main.setAutorize(false);

        fildElectField.setVisible(false);
        selectCandidatBox.getItems().clear();
        selectBuletBox.getItems().clear();
        candidats.clear();
    }
/*____________________________________________________________________________________________________________________*/
    if(event.getSource().equals(addCandidatButton)){

        int cl = 0;

        String nameCand = nameCandidatField.getText().trim();
        String postCand = postCandidatField.getText().trim();

        Main.addCandidat(new Candidate(nameCand,postCand));


        for(int i=0;i<Main.getPosts().size();i++){                            /*додавання кандидата у бюлетень*/
            if(Main.getPosts().get(i).equals(postCand)){
                cl = cl+1;
            }

        }

        if(cl == 0){
            Main.addPosts(postCand);
        }

        doneAddCandField.setVisible(true);
        nameCandidatField.clear();
        postCandidatField.clear();


    }

 /*____________________________________________________________________________________________________________________*/
    if(event.getSource().equals(createVoitButton)){
        doneAddCandField.setVisible(false);
        createBuletPane.toFront();                          /*відкриття форми додавання кандидатів*/

    }
 /*____________________________________________________________________________________________________________________*/
    if(event.getSource().equals(voiceButton)){
            String nameK = (String)selectCandidatBox.getValue();

            if(Main.isCurentVote() == true){
                fildElectField.setVisible(true);
            }
            if(Main.isCurentVote() == false){
                Main.voted(nameK);
                Main.setVoitUser();
                doneElectFPane.toFront();
            }

    }
/*____________________________________________________________________________________________________________________*/
        if(event.getSource().equals(clearButton)){
               clearPane.toFront();                         /*відкриває панель очищення*/
        }
/*____________________________________________________________________________________________________________________*/
        if(event.getSource().equals(clearAllButton)){
            Main.clear();                                   /*очищає данні бд*/

        }
/*____________________________________________________________________________________________________________________*/
        if(event.getSource().equals(resultButton)){
            resultGolPane.toFront();
            selectBuletBox1.getItems().addAll(Main.getPosts());//заповняємо комбо бокс бюлетнями
        }
 /*____________________________________________________________________________________________________________________*/
        if(event.getSource().equals(showResultButton)){


            String post = (String)selectBuletBox1.getValue();

            Main.getResult(post);


            columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
            columnVoices.setCellValueFactory(new PropertyValueFactory<>("voices"));
            resutTanle.setItems(Main.getWinners());
            resutTanle.sort();

            winnerLabel.setText(Main.getWin());

            showResultPane.toFront();

        }
 /*____________________________________________________________________________________________________________________*/



        

    }
/*____________________________________________________________________________________________________________________*/


    @FXML
    void handleMouseEvent(MouseEvent event) throws SQLException, ClassNotFoundException {
/*____________________________________________________________________________________________________________________*/
            if(event.getSource().equals(btnClose)){

                System.exit(0);

            }
            if(event.getSource().equals(btnClose1)){                 /*Закриття вікна по натиску хрестика*/

                 System.exit(0);
            }
            if(event.getSource().equals(btnClose11)){

                 System.exit(0);
            }
/*____________________________________________________________________________________________________________________*/
            if(event.getSource().equals(btnMinimize)){
                Stage stage = (Stage) btnMinimize11.getScene().getWindow();
                stage.setIconified(true);
              }
             if(event.getSource().equals(btnMinimize1)){                       /*Згортання вікна по натиску риски*/
                 Stage stage = (Stage) btnMinimize11.getScene().getWindow();
                 stage.setIconified(true);
             }
             if(event.getSource().equals(btnMinimize11)) {
                 Stage stage = (Stage) btnMinimize11.getScene().getWindow();
                 stage.setIconified(true);
             }
/*____________________________________________________________________________________________________________________*/
            if(event.getSource().equals(btnBack)){
                signUpPane.toFront();
                doneRegLabel.setVisible(false);
                nameField.clear();                   /* відкриває панель авторизаціїї після натискання на значок "Back"*/
                loginRegField.clear();
                passwordRegField.clear();
                fielFindField.setVisible(false);
                loginField.clear();
                passField.clear();
                userIsnueField.setVisible(false);
            }
/*____________________________________________________________________________________________________________________*/
            if(event.getSource().equals(selectCandidatBox)){ //натискання на бокс з кандидатами

                selectCandidatBox.getItems().clear();
                candidats.clear();

                currentPost = (String)selectBuletBox.getValue();
                String select = "SELECT * FROM " + Const.CANDIDAT_TABLE + " WHERE " +   //витягуємо всіх кандидатів на данний пост
                        Const.CANDIDATS_POST + "=?";


                ResultSet rs1 = null;

                try {
                    PreparedStatement createSt = Main.dbHandler.getDbConnection().prepareStatement(select);
                    createSt.setString(1, currentPost);



                    rs1 = createSt.executeQuery();


                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }


                String nameC;

                while (rs1.next()) {

                    nameC = rs1.getString(Const.CANDIDATS_NAME); //додаємо всії витягнутих кандидатів до списку
                    candidats.add(nameC);
                }

                System.out.println(currentPost);
                System.out.println(candidats);

                selectCandidatBox.getItems().addAll(candidats);//додаємо всіх кандидатів на дани й пост в кобо бокс після його відкриття

            }
/*____________________________________________________________________________________________________________________*/
        if(event.getSource().equals(selectBuletBox)){
            candidats.clear();
            selectCandidatBox.getItems().clear();

        }
/*____________________________________________________________________________________________________________________*/


    }


}
