/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fingerprint;

import com.griaule.fingerprintsdk.sample.Util;
import com.griaule.grfingerjava.FingerprintImage;
import com.griaule.grfingerjava.GrFingerJava;
import com.griaule.grfingerjava.GrFingerJavaException;
import com.griaule.grfingerjava.IFingerEventListener;
import com.griaule.grfingerjava.IImageEventListener;
import com.griaule.grfingerjava.IStatusEventListener;
import com.griaule.grfingerjava.MatchingContext;
import com.griaule.grfingerjava.Template;
import java.awt.TrayIcon;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.stream.ImageOutputStream;
import ntg.TrayIconClass;

/**
 *
 * @author OTHREE
 */

public class FingerPrint implements IStatusEventListener, IImageEventListener, IFingerEventListener {
    public static FingerprintImage fingerprintimage;
    public static MatchingContext fingerprintSDK;
    /**
     * The template extracted from the last acquired image.
     */
    public  static Template template;
    public FingerPrint() {
        
        String grFingerNativeDirectory = new File(".").getAbsolutePath();
        try {
//            GrFingerJava.installLicense("LOFAI-KWQQP-GDBFC-UVHCI");
        } catch (Exception ex) {
            Logger.getLogger(FingerPrint.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Util.setFingerprintSDKNativeDirectory(grFingerNativeDirectory);
        init();
        
         
    
    }

    
    
    
    
    @Override
    public void onSensorPlug(String string) {
        
         try {
           //Start capturing from plugged sensor.
           GrFingerJava.startCapture(string, this, this);
           TrayIconClass.getMainTrayIcon().displayMessage("NTG", "Finger Print Sensor Plugged\n" + new java.util.Date(), TrayIcon.MessageType.NONE);
       } catch (GrFingerJavaException e) {
           //write error to log
         e.printStackTrace();
       }
    }

    @Override
    public void onSensorUnplug(String string) {
        try {
            GrFingerJava.stopCapture(string);
            TrayIconClass.getMainTrayIcon().displayMessage("NTG", "Finger Print Sensor UnPlugged\n" + new java.util.Date(), TrayIcon.MessageType.WARNING);
        } catch (GrFingerJavaException ex) {
            
        }
   
        
    }

//    @Override
//    public void onImageAcquired(String string, FingerprintImage fi) {
//         System.out.println(fi.getData());
//        this.fingerprintimage=fi;
//        BufferedImage ij = null;
//        try {
//            template = fingerprintSDK.extract(fingerprintimage);
//            ij = GrFingerJava.getBiometricImage(template,fingerprintimage);
//        } catch (GrFingerJavaException ex) {
//            
//        }
//         
//        System.out.println(template.getData());
//        ImageWriterSpi spi = new PNGImageWriterSpi();
//
//            File f = null;
//        try {
//            f = File.createTempFile("image", null);
//        } catch (IOException ex) {
//            Logger.getLogger(registerController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//            saveToFile(f,spi);
//        
//        System.out.println(f.toURI().toString());
//        try {
//            registerController.fingerprint.setImage(new Image(f.toURL().toString()));
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(registerController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//    }

    @Override
    public void onFingerDown(String string) {
        System.out.println("Finget Placed on "+string);
    }

    @Override
    public void onFingerUp(String string) {
        System.out.println("Finger removed from "+string);
    }



    private void init() {
           try {
               
           fingerprintSDK = new MatchingContext();
           //Starts fingerprint capture.
           
           GrFingerJava.initializeCapture(this);
      TrayIconClass.getMainTrayIcon().displayMessage("NTG", "SDK initialised\n" + new java.util.Date(), TrayIcon.MessageType.NONE);
       } catch (Exception e) {
           //If any error ocurred while initializing GrFinger,
           //writes the error to log'
           e.printStackTrace();
          
       }
    }
    
    public void saveToFile(File file, ImageWriterSpi spi) {
       try {
           //Creates a image writer.
           ImageWriter writer = spi.createWriterInstance();
           ImageOutputStream output = ImageIO.createImageOutputStream(file);
           writer.setOutput(output);
           //Writes the image.
           writer.write(fingerprintimage);
           System.out.println(template.getData().length);

           //Closes the stream.
           output.close();
           writer.dispose();
       } catch (IOException e) {
           // write error to log
           
       }

   }

    @Override
    public void onImageAcquired(String string, FingerprintImage fi) {
        
    }

}
