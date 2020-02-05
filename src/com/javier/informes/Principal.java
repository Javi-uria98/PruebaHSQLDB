package com.javier.informes;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Principal extends Application {

    private void generarInfome(String ciudad) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/test", "sa", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(connection);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("CIUDAD", ciudad);

        try {
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/com/javier/informes/jasper/pruebaHSQLDB.jasper"), parametros, connection);
            JasperExportManager.exportReportToPdfFile(print, "informes/pruebaHSQLDB.pdf");
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        TextField textField = new TextField("Dallas");
        Button button = new Button();
        VBox vBox=new VBox();
        vBox.getChildren().addAll(textField,button);
        Scene scene=new Scene(vBox);
        stage.setScene(scene);
        stage.show();
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                generarInfome(textField.getText());
            }
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}
