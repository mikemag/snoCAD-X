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
 * snxFileManager.java
 *
 * Created on 26 March 2007, 20:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package snocadx;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import javax.imageio.ImageReadParam;
import javax.imageio.metadata.IIOMetadata;

/**
 *
 * @author dgraf
 */
public class snxFileManager extends javax.swing.JPanel {
    
    /** Creates a new instance of snxFileManager */
    public snxFileManager() 
    {
    }
    
    private java.util.zip.ZipOutputStream m_zipOut;
    private java.util.zip.ZipFile m_zipIn;
   
    public void openForReading(java.io.File file)
    {
        try 
        {
            m_zipIn = new java.util.zip.ZipFile(file.getAbsolutePath());
        } 
        catch (java.io.FileNotFoundException ex)
        {
            javax.swing.JOptionPane.showMessageDialog(this,
            "File " + file.getAbsolutePath() + " not found" ,
            "snoCAD-X Error",
            javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (java.io.IOException ex) 
        {
            ex.printStackTrace();
        }
    }
    // This is called first before adding items...
    public void openForWriting(java.io.File file)
    {
        
        try 
        {
            m_zipOut = new java.util.zip.ZipOutputStream(new java.io.FileOutputStream(file));
        } 
        catch (FileNotFoundException ex) 
        {
            Main.alert(ex.getMessage());
            ex.printStackTrace();
        }        
        catch (IOException ex) 
        {
            Main.alert(ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public void closeReadFile()
    {
        try 
        {
            m_zipIn.close();
        } 
        catch (IOException ex) 
        {
            Main.alert(ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public void closeWrittenFile() 
    { 
        try 
        {
            m_zipOut.close();
        } 
        catch (IOException ex) 
        {
            Main.alert(ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public void addBoard(String boardBuffer)
    {
        try 
        {
            m_zipOut.putNextEntry(new java.util.zip.ZipEntry("board_geometry"));
            java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.OutputStreamWriter(m_zipOut));
            writer.write(boardBuffer);
            writer.newLine();
            writer.flush();
            m_zipOut.closeEntry();
        } 
        catch (IOException ex) 
        {
            Main.alert(ex.getMessage());
            ex.printStackTrace();
        }
    
    }
    
    public void addGraphicMetaData(String metaDataBuffer)
    {
        try 
        {
            m_zipOut.putNextEntry(new java.util.zip.ZipEntry("graphic_metadata"));
            java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.OutputStreamWriter(m_zipOut));
            writer.write(metaDataBuffer);
            writer.newLine();
            writer.flush();
            m_zipOut.closeEntry();
        } 
        catch (IOException ex) 
        {
            Main.alert(ex.getMessage());
            ex.printStackTrace();
        }
    
    }
    
    public void addImage(String name, java.awt.image.BufferedImage image)
    { 
        try 
        {
            m_zipOut.putNextEntry(new java.util.zip.ZipEntry(name));
        } 
        catch (IOException ex) 
        {
            Main.alert(ex.getMessage());
            ex.printStackTrace();
        }
        
        try 
        { 
            String type = "png";
            
            if (image.getColorModel().hasAlpha()) type = "png";
            
            javax.imageio.ImageIO.write(image, type, m_zipOut);
        }
        catch (IOException ex) 
        {
            Main.alert(ex.getMessage());
            ex.printStackTrace();
        }
        
        try 
        {
            m_zipOut.closeEntry();
        } 
        catch (IOException ex) 
        {
            Main.alert(ex.getMessage());
            ex.printStackTrace();
        }
        
    }
    
    public String getBoardGeometry()
    {
        return getTextFile("board_geometry");
    }
    
    public String getGraphicsMetaData()
    {
        return getTextFile("graphic_metadata");
    }
    
    private String getTextFile(String textfilename)
    {
        String data = "";
        java.util.zip.ZipEntry entry = null;
        
        if (m_zipIn == null) return null;
        
        try 
        {
            java.util.Enumeration fileList = m_zipIn.entries();
            
            while( fileList.hasMoreElements() ) 
            {
                entry = m_zipIn.getEntry(String.valueOf(fileList.nextElement()));
                String test = entry.getName();
                
                if (test.compareTo(textfilename) == 0)
                {
                    java.io.BufferedInputStream inputStream = new BufferedInputStream(m_zipIn.getInputStream(entry));
                    
                    byte dataBytes[] = new byte[1];
                    
                    while (inputStream.read(dataBytes, 0, 1) != -1)
                    {
                        data += (char)dataBytes[0];
                    }
                }
            }
        }
        catch( Exception e ) 
        {
            Main.alert(e.getMessage());
            e.printStackTrace();
        }
        
        data = data.replaceAll("\r", "");
        data = data.replaceAll("\n", "");
        return data;
    }
    
    public java.awt.image.BufferedImage getImage(String imageFilename)
    {
       java.awt.image.BufferedImage returnImage = null;
       

        java.util.zip.ZipEntry entry = null;
        
        try 
        {
            java.util.Enumeration fileList = m_zipIn.entries();
            
            while( fileList.hasMoreElements() ) 
            {
                entry = m_zipIn.getEntry(String.valueOf(fileList.nextElement()));
                String test = entry.getName();
                
                if (test.compareTo(imageFilename) == 0)
                {
                    returnImage = javax.imageio.ImageIO.read(m_zipIn.getInputStream(entry));
                    
                }
            }
        }
        catch( Exception e ) 
        {
            Main.alert(e.getMessage());
            e.printStackTrace();
        }
        
        return returnImage;
       
    }
}
