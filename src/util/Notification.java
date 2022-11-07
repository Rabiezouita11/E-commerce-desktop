/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 *
 * @author Asus
 */
public class Notification {
    public static void notificationSuccess(String titleOfNotif , String messageOfNotif) {
         String title = titleOfNotif;
       String message = messageOfNotif;
      
        TrayNotification tray = new TrayNotification();
        AnimationType type = AnimationType.SLIDE;
        tray.setAnimationType(type);
        tray.setTitle(title);
        tray.setMessage(message);
        
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndDismiss(Duration.seconds(3));
    }
       public static void notificationError(String titleOfNotif) {
         String title = titleOfNotif;
        //String message = messageOfNotif;
      
        TrayNotification tray = new TrayNotification();
        AnimationType type = AnimationType.SLIDE;
        tray.setAnimationType(type);
        tray.setTitle(title);
     //   tray.setMessage(message);
        tray.setNotificationType(NotificationType.ERROR);
        tray.showAndDismiss(Duration.seconds(3));
    }
            public static void notificationInformation(String titleOfNotif,String messageOfNotif) {
         String title = titleOfNotif;
        String message = messageOfNotif;
      
        TrayNotification tray = new TrayNotification();
        AnimationType type = AnimationType.SLIDE;
        tray.setAnimationType(type);
        tray.setTitle(title);
        tray.setMessage(message);
        tray.setNotificationType(NotificationType.INFORMATION);
        tray.showAndDismiss(Duration.seconds(3));
    }
}
