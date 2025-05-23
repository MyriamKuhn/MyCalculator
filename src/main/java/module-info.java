module com.calculator.mycalculator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.jetbrains.annotations;
    requires java.scripting;
    requires exp4j;

    opens com.calculator.mycalculator to javafx.fxml;
    exports com.calculator.mycalculator;
    exports com.calculator.mycalculator.controller;
    opens com.calculator.mycalculator.controller to javafx.fxml;
}