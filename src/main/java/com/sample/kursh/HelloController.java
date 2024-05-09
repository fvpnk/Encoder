package com.sample.kursh;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.input.MouseEvent;

public class HelloController {

    @FXML
    private TextField TextEnter;

    @FXML
    private TextField KeySimmet;


    @FXML
    private TextArea TextOut;

    @FXML
    public TextArea KEYOUTANDINPUTCRYPT;


    @FXML
    private Button Close;

    private String selectedEncoding;

    private Button lastClickedButton;

    @FXML
    void handleCloseButtonClick(ActionEvent event) {
        Stage stage = (Stage) Close.getScene().getWindow();
        stage.close();
    }


    public void handleBase64ButtonClick(ActionEvent event) {
        setButtonColor((Button) event.getSource(), "#383838", "white");
        selectedEncoding = "Base64";
    }

    public void handleMD5ButtonClick(ActionEvent event) {
        setButtonColor((Button) event.getSource(), "#383838", "white");
        selectedEncoding = "MD5";
    }

    public void handleRIPEMD160ButtonClick(ActionEvent event) {
        setButtonColor((Button) event.getSource(), "#383838", "white");
        selectedEncoding = "RIPEMD-160";
    }

    public void handleSHA1ButtonClick(ActionEvent event) {
        setButtonColor((Button) event.getSource(), "#383838", "white");
        selectedEncoding = "SHA-1";
    }

    public void handleAESButtonClick(ActionEvent event) {
        setButtonColor((Button) event.getSource(), "#383838", "white");
        selectedEncoding = "AES (Rijndael)";
    }

    public void handleDESButtonClick(ActionEvent event) {
        setButtonColor((Button) event.getSource(), "#383838", "white");
        selectedEncoding = "DES";
    }

    public void handleRC4ButtonClick(ActionEvent event) {
        setButtonColor((Button) event.getSource(), "#383838", "white");
        selectedEncoding = "RC4";
    }

    public void handleRSABButtonClick(ActionEvent event) {
        setButtonColor((Button) event.getSource(), "#383838", "white");
        selectedEncoding = "RSA";
    }

    public void handleCodirovatButtonClick(ActionEvent event) {
        if (selectedEncoding != null) {
            String inputText = TextEnter.getText();
            String key = KeySimmet.getText();
            String encodedText = Encoder.encode(inputText, selectedEncoding, key);
            TextOut.setText(encodedText);
        } else {
            TextOut.setText("Выберите тип кодирования");
        }
    }


    public void handleDecodeButtonClick(ActionEvent event) {
        if (selectedEncoding != null) {
            String inputText = TextEnter.getText();
            String key = KeySimmet.getText();
            String privateKey = KEYOUTANDINPUTCRYPT.getText();
            String decodedText = Decoder.decode(inputText, selectedEncoding, key, privateKey);
            TextOut.setText(decodedText);
        } else {
            TextOut.setText("Выберите тип кодирования");
        }

    }



    public void handleCopyButtonClick(ActionEvent event) {
        String outputText = TextOut.getText();
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(outputText);
        clipboard.setContent(content);

        Button copyButton = (Button) event.getSource();
        copyButton.setText("Скопировано!");

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2));

        pauseTransition.setOnFinished(e -> {
            copyButton.setText("Копировать");
        });

        pauseTransition.play();
    }
    private void setButtonColor(Button button, String backgroundColor, String textColor) {
        if (lastClickedButton != null) {
            lastClickedButton.setStyle("-fx-background-color: #fafafa; -fx-border-color: #ccc; -fx-border-radius: 5px; -fx-text-fill: black;");
        }
        button.setStyle("-fx-background-color: " + backgroundColor + "; -fx-border-color: #ccc; -fx-border-radius: 5px; -fx-text-fill: " + textColor + ";");
        lastClickedButton = button;
    }
}
