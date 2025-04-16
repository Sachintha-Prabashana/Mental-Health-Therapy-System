module lk.ijse {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.jfoenix;
    //requires java.mail;
//    requires net.sf.jasperreports.core;
//    requires javax.mail.api;
//    requires xmlgraphics.commons;
    requires lombok;
    requires jakarta.persistence;
    requires jbcrypt;
    requires org.hibernate.orm.core;
    requires java.naming;


    opens lk.ijse.view.tdm to javafx.base;
    opens lk.ijse.entity to org.hibernate.orm.core;
    exports lk.ijse;
    opens lk.ijse.config to jakarta.persistence, javafx.fxml;
    opens lk.ijse.controller to jakarta.persistence, javafx.fxml;
}