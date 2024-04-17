module griffith.guipro {
    requires javafx.controls;
    requires javafx.fxml;


    opens griffith.guipro to javafx.fxml;
    exports griffith.guipro;
}