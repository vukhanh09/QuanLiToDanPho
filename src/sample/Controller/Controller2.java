package sample.Controller;

import javafx.application.Platform;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import sample.Database;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller2 {

    @FXML
    private TextArea chiTiet;

    @FXML
    private AnchorPane ds_MoiNhan;
    //donNop
    @FXML
    private TextField hoTen, diaChi, SDT1,SDT2, noiDungKhac;
    @FXML
    PasswordField cmt1,cmt2;
    @FXML
    private Group groupNopDon;
    @FXML
    private StackPane stackPane;
    @FXML
    private ScrollPane donNop;
    @FXML
    private RadioButton radioAnNinh,radioCoSo,radioQuyDinh,radioKhac;
    @FXML
    private DatePicker ngaySinh,ngayNop,ngayNop2;

    //
    @FXML
    private AnchorPane ds_XuLi;
    @FXML
    private AnchorPane timKiem;
    @FXML
    private AnchorPane thongKe_Quy;

    @FXML
    public ComboBox quy;
    @FXML
    public ComboBox tim_Noidung;
    @FXML
    public ComboBox tim_Trangthai;

    ObservableList<Integer> list_quy = FXCollections.observableArrayList(1, 2, 3, 4);
    ObservableList<String> list_Noidung = FXCollections.observableArrayList("An ninh, trật tự", "Cơ sở hạ tầng", "Quy định, quy chế", "Khác...");
    ObservableList<String> list_Trangthai = FXCollections.observableArrayList("Mới ghi nhận", "Chưa giải quyết", "Đã giải quyết");

    //button Nộp đơn
    public void button_donNop(ActionEvent e){
        this.donNop.setVisible(true);
        this.ds_MoiNhan.setVisible(false);
        this.ds_XuLi.setVisible(false);
        this.timKiem.setVisible(false);
        this.thongKe_Quy.setVisible(false);
    }

    //button danh sách các đơn mới ghi nhận
    public void button_ds_MoiNhan(ActionEvent e){
        this.donNop.setVisible(false);
        this.ds_MoiNhan.setVisible(true);
        this.ds_XuLi.setVisible(false);
        this.timKiem.setVisible(false);
        this.thongKe_Quy.setVisible(false);
    }

    //button danh sách đơn đang xử lí
    public void button_ds_XuLi(ActionEvent e){
        this.donNop.setVisible(false);
        this.ds_MoiNhan.setVisible(false);
        this.ds_XuLi.setVisible(true);
        this.timKiem.setVisible(false);
        this.thongKe_Quy.setVisible(false);
    }

    //button tìm kiếm
    public void button_timKiem(ActionEvent e){
        this.donNop.setVisible(false);
        this.ds_MoiNhan.setVisible(false);
        this.ds_XuLi.setVisible(false);
        this.timKiem.setVisible(true);
        this.thongKe_Quy.setVisible(false);
        tim_Noidung.setItems(list_Noidung);
        tim_Trangthai.setItems(list_Trangthai);
    }

    //button danh sách quý
    public void button_thongKe_Quy(ActionEvent e){
        this.donNop.setVisible(false);
        this.ds_MoiNhan.setVisible(false);
        this.ds_XuLi.setVisible(false);
        this.timKiem.setVisible(false);
        this.thongKe_Quy.setVisible(true);
        quy.setItems(list_quy);
    }

    //Xử lí nút Close ứng dụng
    public void Close(ActionEvent e){
        Platform.exit();
        System.exit(0);
    }

    //hiện không dùng đến
    public void pressButtonRecent(ActionEvent e) throws IOException {
        AnchorPane label = FXMLLoader.load(getClass().getResource("../Fxml/Recent_List.fxml"));
        Stage window = new Stage();
        Scene scene = new Scene(label,1700,900);
        //window.getIcons().add(new Image(getClass().getResourceAsStream("../book.png")));
        window.setTitle("Danh sách đơn mới nhận");
        window.setScene(scene);
        window.show();
    }

    //nút "Trang chủ" để quay lại trang ban đầu sau khi đăng nhập
    public void pressButtonHome(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../Fxml/sample1.fxml"));
        Stage window = (Stage)((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,1700,900);
        //window.getIcons().add(new Image(getClass().getResourceAsStream("../book.png")));
        window.setTitle("Quản lý thông tin tổ dân phố");
        window.setScene(scene);
        window.show();
    }

    public String getClassify(){
        String classify=null;
        if(radioAnNinh.isSelected()){
            classify = "An ninh, trật tự";
        }
        else if(radioCoSo.isSelected()){
            classify = "Cơ sở hạ tầng";
        }
        else if(radioQuyDinh.isSelected()){
            classify = "Quy định quy chế";
        }
        else{
            classify = noiDungKhac.getText();
        }
        return classify;
    }
    public void showAlter(){
        Alert hi = new Alert(Alert.AlertType.INFORMATION);
        hi.setTitle("Update data");
        hi.setHeaderText(null);
        hi.setContentText("Update to the database successfully !");
        hi.showAndWait();
    }

    public void luuLai(ActionEvent actionEvent) throws SQLException {
        ObservableList<Node> list = this.stackPane.getChildren();
        Node hasInfo = list.get(list.size()-1);
        Node noInfo = list.get(list.size()-2);
        Database database = new Database();
        if(hasInfo.isVisible()){
            String cmt = cmt2.getText();
            String date = String.valueOf(ngayNop2.getValue());
            String ndChiTiet = chiTiet.getText();
            String []arrayQuy= date.split("-");
            int quy = Integer.parseInt(arrayQuy[1])/3+1;
            int state = -1;
            String classify = null;
            classify = getClassify();
            database.createPetitionInDatabase(cmt,ndChiTiet,date,quy,classify,state);
            showAlter();
        }
        else{
            String name = hoTen.getText();
            String cmt = cmt1.getText();
            String accommodation = diaChi.getText();
            String birthday = String.valueOf(ngaySinh.getValue());
            String sdt = SDT1.getText();

            //insert nguoi lam don
            /*database.insertUser(cmt,name,birthday,sdt,accommodation);*/
            String classify = null;
            classify = getClassify();
            //insert noi dung
            String date = String.valueOf(ngayNop.getValue());
            String ndChiTiet = chiTiet.getText();
            String []arrayQuy= date.split("-");
            int quy = Integer.parseInt(arrayQuy[1])/3+1;
            int state = -1;

            database.insertPetitionIntoDatabase(cmt,name,birthday,sdt,accommodation,ndChiTiet,date,quy,classify,state);

            showAlter();
        }



    }

    public void changeHasInfo(ActionEvent actionEvent) {
        ObservableList<Node> list = this.stackPane.getChildren();
        Node hasInfo = list.get(list.size()-1);
        Node noInfo = list.get(list.size()-2);
        hasInfo.setVisible(true);
        noInfo.setVisible(false);
    }

    public void changeNoInfo(ActionEvent actionEvent) {
        ObservableList<Node> list = this.stackPane.getChildren();
        Node hasInfo = list.get(list.size()-1);
        Node noInfo = list.get(list.size()-2);
        hasInfo.setVisible(false);
        noInfo.setVisible(true);
    }
}