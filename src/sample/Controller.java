package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.List;

public class Controller {

    private Desktop desktop = Desktop.getDesktop();

    @FXML
    private TextArea instructionsTextArea;

    @FXML
    private ImageView imgLogo;

    @FXML
    private TextArea textArea, codedTextArea;

    @FXML
    private Button openFileDecodedTextBtn, clearDecodedTextBtn, saveDecodedTextBtn;

    @FXML
    private Button openFileCodedTextBtn, clearCodedTextBtn, saveCodedTextBtn;

    @FXML
    private Button codeBtn, decodeBtn, instructionBtn, addKey;

    @FXML
    private TextField forKey, forElemNum, lengthOfText;

    @FXML
    private Hyperlink openSiteLink;

    @FXML
    private ToggleGroup toggle;

    @FXML
    private Button textLengthBtn;

    @FXML
    private Button hyperlinkBtn;

    @FXML
    public void initialize() {
        getLangFromToggleGroup();
        toggle.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> getLangFromToggleGroup());
    }

    private void getLangFromToggleGroup() {
        if (toggle.getSelectedToggle() != null) {
            switch (((RadioButton) toggle.getSelectedToggle()).getText()) {
                case "Українська":
                    Shifrator.setLanguage(Shifrator.InUkrAlphabet());
                    break;
                case "Русский":
                    Shifrator.setLanguage(Shifrator.InRusAlphabet());
                    break;
                case "English":
                    Shifrator.setLanguage(Shifrator.InEnglAlphabet());
                    break;
            }
        }
    }

    @FXML
    void codeThisText(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Помилка!");
        if(forElemNum.getText().equals("")) {
            alert.setContentText("Введіть довжину блоку!");
            alert.showAndWait();
        }  else if (Integer.parseInt(forElemNum.getText())<0 || Integer.parseInt(forElemNum.getText())>=10) {
            alert.setContentText("Ви ввели некоректне значення довжини блоку!");
            alert.showAndWait();
        } else
            if (textArea.getText().equals("")){
            alert.setContentText("Введіть відкрите повідомлення!");
            alert.showAndWait();
        }else {
                List<Integer> nullList = new ArrayList<>();
                Shifrator.setKey(nullList);
                Shifrator.setText(null);
                Shifrator.setCodedText(null);
                String text = textArea.getText();
                int l = text.length();
                String lStr = Integer.toString(l);
                forKey.setText("");
                lengthOfText.setText(lStr);
                Shifrator.setLength(l);
                String keyNum = forElemNum.getText();
                int key = Integer.parseInt(keyNum);
                if (key < 0) System.out.println("k не може бути менше 0!");
                Shifrator shifrator = new Shifrator(key, text);
                shifrator.genKey();
                shifrator.Encipher();
                String keyString = shifrator.printKey();
                String codedText = shifrator.printCodedText();
                forKey.setText(keyString);
                codedTextArea.setText(codedText);
                Shifrator.setCodedText(codedText);
            }
    }

    @FXML
    void decodeThisText(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Помилка!");
        if (forElemNum.getText().equals("")) {
            alert.setContentText("Введіть довжину блоку!");
            alert.showAndWait();
        } else if (lengthOfText.getText().equals("")) {
            alert.setContentText("Введіть довжину тексту!");
            alert.showAndWait();
        } else if (Integer.parseInt(forElemNum.getText()) < 0 || Integer.parseInt(forElemNum.getText()) >= 10) {
            alert.setContentText("Ви ввели некоректне значення довжини блоку!");
            alert.showAndWait();
        } else if (codedTextArea.getText().equals("")) {
            alert.setContentText("Введіть закрите повідомлення!");
            alert.showAndWait();
        } else if (Integer.parseInt(lengthOfText.getText()) <= 0) {
            alert.setContentText("Довжина тексту не може бути від'ємною або нульовою!");
            alert.showAndWait();
            lengthOfText.setText("");
        } else if (forKey.getText().equals("")) {
            alert.setContentText("Введіть ключ!");
            alert.showAndWait();
        } else if (forKey.getText().length() != Integer.parseInt(forElemNum.getText())) {
            alert.setContentText("Введено неправильний ключ!");
            alert.showAndWait();
        } else if (codedTextArea.getText().length() % Integer.parseInt(forElemNum.getText()) != 0) {
            alert.setContentText("Введена довжина тексту або довжина блоку не співпадає\nз дійсністю!");
            alert.showAndWait();
        } else {
            String codedText = null;

            if (Shifrator.getCodedText() == null) {
                codedText = codedTextArea.getText();
            } else {
                codedText = Shifrator.getCodedText();
            }
            String keyNum = forElemNum.getText();

            int k = Integer.parseInt(keyNum);
            String keyStr = forKey.getText();
            List<Integer> key = new ArrayList<>();

            boolean KeyCont = false;
            for (int i = 0; i < k; i++) {
                int num = keyStr.charAt(i);
                key.add(num - 48);
            }
            String textLength = lengthOfText.getText();
            int length = Integer.parseInt(textLength);
            Shifrator.setLength(length);
            Shifrator.setText(null);

            Shifrator shifrator = new Shifrator(key, k, codedText);

            String decodedText = shifrator.Decode();
            textArea.setText(decodedText);
        }
    }

    @FXML
    void addKeyToText(ActionEvent event) {

        String keyString ="";

        if(Shifrator.getLength()==0){
            String length = lengthOfText.getText();
            codedTextArea.appendText("\nДовжина: "+length);
        }
        else {
                String length = Integer.toString(Shifrator.getLength());
                codedTextArea.appendText("\nДовжина: "+length);
            }
        List<Integer> key = new ArrayList<>(Shifrator.getKey());
        for (int i =0;i<key.size();i++){
            keyString+=key.get(i);
        }
        codedTextArea.appendText("\nКлюч: "+keyString);
    }

    @FXML
    void setLengthOfText(ActionEvent event) {
        String text = codedTextArea.getText();
        int length = text.length();
        String lengthString = Integer.toString(length);
        lengthOfText.setText(lengthString);
    }

    @FXML
    void clearCodedText(ActionEvent event) {
        codedTextArea.setText("");
        Shifrator.setLength(0);
        Shifrator.setText("");
    }

    @FXML
    void clearDecodedText(ActionEvent event) {
        textArea.setText("");
        Shifrator.setLength(0);
        Shifrator.setText("");
    }

    @FXML
    void clearBlockLength(ActionEvent event) {
        forElemNum.setText("");
    }

    @FXML
    void clearTextLength(ActionEvent event) {
        lengthOfText.setText("");
    }

    @FXML
    void clearKey(ActionEvent event) {
        forKey.setText("");
    }
    
    @FXML
    void openCodedText(ActionEvent event) {
        codedTextArea.setText(read().toString());
    }

    @FXML
    void openDecodedText(ActionEvent event) {
        textArea.setText(read().toString());
    }

    @FXML
    void openInstruction(ActionEvent event) throws Exception{
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("instructions.fxml"));
        primaryStage.setTitle("Інструкція");
        primaryStage.getIcons().add(new Image("/icon.png"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }




    @FXML
    void saveCodedText(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(null);

        if(file != null){
            try{
            FileWriter fileWriter;
            fileWriter = new FileWriter(file);
            fileWriter.write(codedTextArea.getText());
            fileWriter.close();}catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    void saveDecodedText(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(null);

        if(file != null){
            try{
                FileWriter fileWriter;
                fileWriter = new FileWriter(file);
                fileWriter.write(textArea.getText());
                fileWriter.close();
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    private void hyperlinkClicked() {
        try {
            Desktop desktop = java.awt.Desktop.getDesktop();
            URL url = this.getClass().getResource("/games(html)/site.html");
            desktop.browse(url.toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String read() {
        String list = new String();
        FileChooser choose = new FileChooser();
        File txt = choose.showOpenDialog(null);
        try (Scanner in = new Scanner (txt)){
            while(in.hasNext()){
                list +=in.nextLine() + " ";// + "\n";
            }

        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }

        return list;
    }

    public static Scene getScene() {
        Parent root = loadFXML();
        assert root != null;
        return new Scene(root);
    }

    private static Parent loadFXML() {
        try {
            return FXMLLoader.load(MainController.class.getResource("sample.fxml"));
        }
        catch (IOException e) {
            e.printStackTrace();
            return new Pane();
        }
    }
}
