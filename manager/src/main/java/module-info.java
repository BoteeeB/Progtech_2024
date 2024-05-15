module com.manager.manager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;


    exports com.manager.manager to javafx.graphics;
    opens com.manager.manager to javafx.fxml;
    exports com.manager.manager.abstraction to javafx.fxml;
    exports com.manager.manager.controller to javafx.graphics;
    opens com.manager.manager.controller to javafx.fxml;
    exports com.manager.manager.Products to javafx.graphics;
    opens com.manager.manager.Products to javafx.fxml;
    exports com.manager.manager.user to javafx.graphics;
    opens com.manager.manager.user to javafx.fxml;
}