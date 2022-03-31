package parpar;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WinMain {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> boxL;

    @FXML
    private ComboBox<String> boxR;

    @FXML
    private Button button;

    @FXML
    private Label labelDate;

    @FXML
    private Label labelEUR;

    @FXML
    private Label labelUSD;

    @FXML
    private Button arrowBtn;

    @FXML
    private TextField textFieldL;

    @FXML
    private TextField textFieldR;


    private static Document getPage() throws IOException {
        String url = "https://cbr.ru/";
        Document page = (Document) Jsoup.parse(new URL(url), 3000);
        return page;
    }

    @FXML

    void initialize() throws IOException {
        Document page = getPage();
        Element tabCurrency = page.select("div.main-indicator_rates-table").first();
        Elements currency = tabCurrency.select("div.main-indicator_rate");
        String[] s = currency.text().split(" ");
        Elements date = tabCurrency.select("div[class=main-indicator_rates-head]");
        String[] today = date.text().split(" ");
        System.out.println();
        boxL.getItems().addAll("USD","RUB","EUR");
        boxR.getItems().addAll("USD","RUB","EUR");
        boxL.setValue("USD");
        boxR.setValue("RUB");

        labelDate.setText("Курс на " + today[2]);
        labelUSD.setText("USD  " + s[1].replace(",","."));
        labelEUR.setText("EUR  " + s[10].replace(",","."));

        textFieldL.setText("1");
        textFieldR.setText((labelUSD.getText().replace(" ₽","").replace("USD  ","")));
        textFieldR.setEditable(false);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    if (boxR.getValue().equals(boxL.getValue())){
                        textFieldR.setText(textFieldL.getText()); // неправильно +/-
                    }else if(boxL.getValue().equals("RUB") && boxR.getValue().equals("USD")){
                        rubToUsd();
                    }else if (boxL.getValue().equals("USD") && boxR.getValue() == "RUB"){
                        usdToRub();
                    }else if(boxL.getValue().equals("RUB") && boxR.getValue() == "EUR"){
                        rubToEur();
                    }else if(boxL.getValue().equals("EUR") && boxR.getValue().equals("RUB")){
                        eurToRub();
                    }else if(boxL.getValue().equals("EUR") && boxR.getValue().equals("USD")){
                        eurToUsd();
                    }else if(boxL.getValue().equals("USD") && boxR.getValue().equals("EUR")){
                        usdToEur();
                    }

                }catch (Exception e){
                    exn();
                }
            }
        });

        arrowBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String changeBox = boxL.getValue();
                boxL.setValue(boxR.getValue());
                boxR.setValue(changeBox);
                String changeText = textFieldL.getText();
                textFieldL.setText(textFieldR.getText());
                textFieldR.setText(changeText);
            }
        });

    }

    public void exn(){
        textFieldL.deleteText(0, textFieldL.getLength());
        textFieldR.deleteText(0, textFieldR.getLength());
    }

    public void usdToRub(){
        double userNumber = Double.parseDouble(String.valueOf(textFieldL.getText()));
        String num = labelUSD.getText().replace(" ₽","").replace("USD  ","");
        double a = Double.parseDouble(num);
        a *= userNumber;
        double result = Math.ceil(a * 1000)/1000;
        textFieldR.setText(String.valueOf(result));
    }

    public void eurToRub(){
        double userNumber = Double.parseDouble(String.valueOf(textFieldL.getText()));
        String num = labelEUR.getText().replace(" ₽","").replace("EUR  ","");
        double a = Double.parseDouble(num);
        a *= userNumber;
        double result = Math.ceil(a * 1000)/1000;
        textFieldR.setText(String.valueOf(result));
    }

    public void rubToEur(){
        double userNumber = Double.parseDouble(String.valueOf(textFieldL.getText()));
        String num = labelEUR.getText().replace(" ₽","").replace("EUR  ","");
        double a = Double.parseDouble(num);
        userNumber /= a;
        double result = Math.ceil(userNumber * 1000)/1000;
        textFieldR.setText(String.valueOf(result));
    }

    public void rubToUsd(){
        double userNumber = Double.parseDouble(String.valueOf(textFieldL.getText()));
        String num = labelUSD.getText().replace(" ₽","").replace("USD  ","");
        double a = Double.parseDouble(num);
        userNumber /= a;
        double result = Math.ceil(userNumber * 1000)/1000;
        textFieldR.setText(String.valueOf(result));
    }

    public void eurToUsd(){
        double userNumber = Double.parseDouble(String.valueOf(textFieldL.getText()));
        String num1 = labelEUR.getText().replace(" ₽","").replace("EUR  ","");
        String num2 = labelUSD.getText().replace(" ₽","").replace("USD  ","");
        double a = Double.parseDouble(num1);
        double b = Double.parseDouble(num2);
        a = userNumber * (a/b);
        double result = Math.ceil(a * 1000)/1000;
        textFieldR.setText(String.valueOf(result));
    }

    public void usdToEur(){
        double userNumber = Double.parseDouble(String.valueOf(textFieldL.getText()));
        String num1 = labelEUR.getText().replace(" ₽","").replace("EUR  ","");
        String num2 = labelUSD.getText().replace(" ₽","").replace("USD  ","");
        double a = Double.parseDouble(num1);
        double b = Double.parseDouble(num2);
        a = userNumber * (b/a);
        double result = Math.ceil(a * 1000)/1000;
        textFieldR.setText(String.valueOf(result));
    }
}
