/*
 * Copyright 2007-2014 Daniel Graf (https://github.com/danielgraf)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Main.java
 *
 * Created on 22 March 2007, 10:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package snocadx;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;
import javax.swing.*;
import javax.imageio.*;


/**
 *
 * @author dgraf
 */
public class Main {

final static String version = "1.0.7.b";
static String globalPreferences = "";

    /** Creates a new instance of Main */
    public Main()
    {

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {



      final SplashScreen splash = SplashScreen.getSplashScreen();

       if (splash != null)
       {
          Graphics2D g = (Graphics2D)splash.createGraphics();

          if (g != null)
          {
               g.setComposite(AlphaComposite.Clear);
               g.setPaintMode();
               g.setColor(Color.BLACK);
               Dimension size = splash.getSize();
               g.setComposite(AlphaComposite.Clear);
               g.fillRect(0, 0, size.width, size.height);
               g.setPaintMode();
               Font font = new java.awt.Font("Arial", java.awt.Font.PLAIN, 10);
               g.setFont(font);
               g.drawString("version " + version, 200, 143);
               splash.update();
          }

       }

     /*  try {

            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }

        File f = new File("mydata.txt");
FileInputStream fis = new FileInputStream(f);
BufferedInputStream bis = new BufferedInputStream(fis);
DataInputStream dis = new DataInputStream(bis);
*/
        String fileName = (System.getProperty("user.home") + "/snoCAD-x.ini");
       // String fileName = (System.getCodeBase() + "/snoCAD-x.ini");
        java.io.File iniFile = new java.io.File(fileName) ;
        String iniContents = "";
        String iniLine = "";

        try
        {
            java.io.FileInputStream fis = new java.io.FileInputStream(iniFile);
            java.io.InputStreamReader bis = new java.io.InputStreamReader(fis);
            java.io.BufferedReader br = new java.io.BufferedReader ( bis ) ;

            while((iniLine = br.readLine()) != null)
            {
                iniContents = iniContents + iniLine;
            }

            br.close();

        }
        catch (java.io.FileNotFoundException ex)
        {
            //alert("ini file not found at " + fileName);
            try
            {
                java.io.PrintWriter out = new java.io.PrintWriter(new java.io.BufferedWriter(new java.io.FileWriter(fileName)));
                String preferences = "";
                preferences = snoCADutilities.addTaggedData(preferences, "defaultProduct", "0");
                iniContents = preferences;
                out.print(preferences);
                out.close();

            }
            catch (java.io.IOException exc)
            {
                exc.printStackTrace();
            }
        }
        catch (java.io.IOException ex)
        {
            ex.printStackTrace();
        }
        catch (java.lang.Exception  ex)
        {
            ex.printStackTrace();
        }

        globalPreferences = iniContents;

        m_frame = new snoCADapplicationFrame();

        int x = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - m_frame.getWidth()) / 2;
        int y = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - m_frame.getHeight()) / 2;

        m_frame.setLocation(x,y);


        javax.swing.ImageIcon iconImage = new javax.swing.ImageIcon(Main.class.getResource("images/sno2.gif"));

        if (iconImage.getImage() != null) m_frame.setIconImage(iconImage.getImage());

        m_frame.setTitle("snoCAD-X " + version);
        m_frame.setArgs(args);

        try {

            Thread.currentThread().sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        m_frame.setVisible(true);


    }

    public static void alert(String message)
    {
        javax.swing.JOptionPane.showMessageDialog(m_frame,
        message,
        "snoCAD-X Error !",
        javax.swing.JOptionPane.WARNING_MESSAGE);
    }

    private static snoCADapplicationFrame m_frame;

    public static String getProjectFolder()
    {
        String pf = snoCADutilities.getTaggedData(globalPreferences, "projectFolder");
        if (pf.length() == 0) return System.getProperty("user.home");
        return snoCADutilities.getTaggedData(globalPreferences, "projectFolder");
    }
    public static String getBrandLogo()
    {
        return snoCADutilities.getTaggedData(globalPreferences, "brandLogo");
    }
    public static int getDefaultProduct()
    {
        int product = 0;
        return snoCADutilities.getTaggedInt(product, globalPreferences, "defaultProduct");

    }
}
