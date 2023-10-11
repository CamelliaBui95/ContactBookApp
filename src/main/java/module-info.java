module fr.btn.contactsbook {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens fr.btn.contactsbook to javafx.fxml;
    exports fr.btn.contactsbook;
    exports fr.btn.contactsbook.controllers;
    opens fr.btn.contactsbook.controllers to javafx.fxml;
}