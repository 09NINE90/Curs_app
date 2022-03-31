module parpar.parser {
    requires javafx.controls;
    requires javafx.fxml;
    requires jsoup;


    opens parpar to javafx.fxml;
    exports parpar;
}