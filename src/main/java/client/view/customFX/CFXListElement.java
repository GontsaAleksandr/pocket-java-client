package client.view.customFX;

import database.entity.User;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.IOException;

public class CFXListElement extends GridPane {

    @FXML
    private Text topic;

    @FXML
    private Text body;

    @FXML
    private Circle avatar;

    @FXML
    private Text dateText;

    @FXML
    private Text unreadMessages;

    @FXML
    private Circle circleUnreadMessages;

    @FXML
    private MaterialDesignIconView unreadGlyph;

//    @FXML
//    private FontAwesomeIconView unreadGlyph;

    private String imageURL;

    private User user;


    public CFXListElement() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/fxml/CFXListElement.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception){
            throw new RuntimeException(exception);
        }
    }

    public String getTopic(){
        return this.topic.getText();
    }

    public void setTopic(String topic) {
        this.topic.setText(topic);
    }

    public String getDateText() {
        return dateText.getText();
    }

    public void setDateText(String dateText) {
        this.dateText.setText(dateText);
    }

    public String getBody() {
        return body.getText();
    }

    public void setBody(String body) {
        int maxLenOfVisible = 20;
        if (body.length() > maxLenOfVisible) body = body.substring(0, maxLenOfVisible - 3) + "...";
        body = body.replace("\n", " ");
        this.body.setText(body);
    }

    public void setAvatar(Image avatar) {
        this.avatar.setFill(new ImagePattern(avatar));

    }

    public String getUnreadMessages() {
        return unreadMessages.getText();
    }

    public void setUnreadMessages(String unreadMessages) {
        if (unreadMessages.equals("0")){
            circleUnreadMessages.setVisible(false);
            unreadGlyph.setVisible(false);
            this.unreadMessages.setVisible(false);
        } else
        {
            circleUnreadMessages.setVisible(true);
            unreadGlyph.setVisible(true);
            this.unreadMessages.setText("+"+unreadMessages);
            this.unreadMessages.setVisible(true);
        }
    }

    public User getUser(){
        return this.user;
    }

    public void setUser(User user){
        this.user = user;
        this.topic.setText(user.getAccount_name());

    }

}