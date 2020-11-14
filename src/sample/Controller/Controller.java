package sample.Controller;

import com.sun.javafx.css.*;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;


import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;


public class Controller {
    @FXML
    private TextField Taikhoan;
    @FXML
    private PasswordField Matkhau;

    @FXML
    private Text target;


    //nhấn nút đăng nhập
    public void enter(ActionEvent event) throws IOException {
        try {
            if (Matkhau.getText().equals("aa")) setSceneHome(event); //kiểm tra mật khẩu đơn giản
            else this.target.setVisible(true);                              //nếu mật khẩu sai hiển thị báo lỗi
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //sự kiện nhấn nút "Đăng nhập" để vào Trang chủ
    public void setSceneHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../Fxml/sample1.fxml"));
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,1500,800);
        //window.getIcons().add(new Image(getClass().getResourceAsStream("../book.png")));
        window.setTitle("Quản lý thông tin tổ dân phố");
        window.setX(0);
        window.setY(0);
        window.setFullScreen(false);
        window.setScene(scene);
        window.show();
        window.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    //Hiển thị khi thông tin đăng nhập sai
    public void Error(){
        this.target.setFill(Color.RED);
        //this.target.set
        this.target.setText("Tài khoản hoặc mặt khẩu không chính xác");
    }

    /*
    private void login(){
        String taikhoan = Taikhoan.getText();
        String matkhau = String.valueOf(Matkhau.getPassword());

        try {
            if () {
                JOptionPane
                        .showMessageDialog(rootPane, "Sai tài khoản hoặc mật khẩu!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error!","Warning",JOptionPane.WARNING_MESSAGE);
        }
    }
*/

}