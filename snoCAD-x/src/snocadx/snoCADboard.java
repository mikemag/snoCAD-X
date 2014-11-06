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
 * snoCADboard.java
 *
 * Created on 22 March 2007, 19:25
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package snocadx;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.*;

/**
 *
 * @author dgraf
 */
public class snoCADboard {
    
    /** Creates a new instance of snoCADboard */
    public snoCADboard() 
    {
        m_boardName = "Untitled snowboard";
        m_scaleFactor = 2.6; // millimetres per pixel
        m_boardOutlineColour = new java.awt.Color(190,190,190);
        m_boardFillColour = new java.awt.Color(0,0,102);
        
        m_runningLength = 1250;
        m_noseLength = 180;
        m_tailLength = 180;
        m_noseWidth = 300;
        m_tailWidth = 300;
        m_sidecutBias = 0;
        m_sidecutRadius = 10000;
        

        m_frontPackRowCount = 4;
        m_rearPackRowCount = 4;
        m_frontPackInsertSpacing = 40;
        m_rearPackInsertSpacing = 40;
        m_stanceWidth = 550;
        m_stanceSetback = 0;
        
        m_noseBezier1Xfactor = 50;
        m_noseBezier1Yfactor = 80;
        m_noseBezier2Xfactor = 0;
        m_noseBezier2Yfactor = 60;
        m_tailBezier1Xfactor = 50;
        m_tailBezier1Yfactor = 80;
        m_tailBezier2Xfactor = 0;
        m_tailBezier2Yfactor = 60;
        
        m_camber = 15;
        m_camberSetback = 0;
        m_noseRadius = 300;
        m_tailRadius = 300;
        
        m_convexRails = false;
        m_serrationCount = 0;
        m_serrationDepth = 0;
        
        m_coreNoseLength = 173;
        m_coreTailLength = 173;
        m_tailThickness = 3;
        m_rearPackOutboard = 300;
        m_rearPackInboard = 400;
        m_midPackOrd = 0;
        m_frontPackOutboard = 300;
        m_frontPackInboard = 400;
        m_noseThickness = 3;
        m_midPackThickness = 6;
        m_frontPackThickness = 7;
        m_rearPackThickness = 7;
        
        m_sidewallWidth = 7;
        m_insertStringerWidth = 20;
        m_coreStringerWidth = 20;
        m_tipspacerType = snoCADutilities.SIDEWALL;
        m_tipspacerRadiusNose = 150;
        m_tipspacerRadiusTail = 150;
        m_tipspacerInterlockRadius = 10;
        m_tipSidewallOffset = 0;
        
        m_noseTipSpacerMaterialLength = 200;
        m_noseTipSpacerMaterialWidth = 320;
        m_tailTipSpacerMaterialLength = 200;
        m_tailTipSpacerMaterialWidth = 320;
        
        m_graphicsLibrary = new snoCADgraphicsLibrary();
        m_graphicsPrintBorder = 20;
        
        m_railGeometry = new snoCADrailGeometry();
        m_railType = snoCADutilities.QUADRATIC;
 
    }
    
     public snoCADboard(int boardType) 
    {
        // Create a default board 
        this();
       
        if (boardType == 1) // replace it with a ski
        {
        m_boardName = "Untitled ski";
        m_scaleFactor = 2.6; // millimetres per pixel
        m_boardOutlineColour = new java.awt.Color(190,190,190);
        m_boardFillColour = new java.awt.Color(51,0,0);
        
        m_runningLength = 1460;
        m_noseLength = 150;
        m_tailLength = 120;
        
        m_noseTipSpacerMaterialLength = 170;
        m_noseTipSpacerMaterialWidth = 160;
        m_tailTipSpacerMaterialLength = 140;
        m_tailTipSpacerMaterialWidth = 160;
        
        m_noseWidth = 130;
        m_tailWidth = 124;
        m_sidecutBias = 0;
        m_sidecutRadius = 16650;

        m_frontPackRowCount = 0;
        m_rearPackRowCount = 0;
        m_frontPackInsertSpacing = 40;
        m_rearPackInsertSpacing = 40;
        m_stanceWidth = 550;
        m_stanceSetback = 0;
        
        m_noseBezier1Xfactor = 40;
        m_noseBezier1Yfactor = 38;
        m_noseBezier2Xfactor = 0;
        m_noseBezier2Yfactor = 37;
        m_tailBezier1Xfactor = 44;
        m_tailBezier1Yfactor = 36;
        m_tailBezier2Xfactor = 0;
        m_tailBezier2Yfactor = 48;
        
        m_camber = 25;
        m_camberSetback = 0;
        m_noseRadius = 300;
        m_tailRadius = 300;
        
        m_convexRails = false;
        m_serrationCount = 0;
        m_serrationDepth = 0;
        
        m_coreNoseLength = 143;
        m_coreTailLength = 113;
        m_tailThickness = 3;
        m_rearPackOutboard = 300;
        m_rearPackInboard = 400;
        m_midPackOrd = 0;
        m_frontPackOutboard = 300;
        m_frontPackInboard = 400;
        m_noseThickness = 3;
        m_midPackThickness = 6;
        m_frontPackThickness = 7;
        m_rearPackThickness = 7;
        
        m_tipspacerRadiusNose = 65;
        m_tipspacerRadiusTail = 65;
        
        m_tipspacerInterlockRadius = 10;
        
        }
            
        
        m_graphicsLibrary = new snoCADgraphicsLibrary();
        m_railGeometry = new snoCADrailGeometry();
        m_railType = snoCADutilities.QUADRATIC;
 
    }
       
    public snoCADboard(java.io.File inputFile) 
    {
        this();
        
        snxFileManager fm = new snxFileManager();
        fm.openForReading(inputFile);
        String data = fm.getBoardGeometry();
        
        if (data == null) return;
        
        m_boardName = snoCADutilities.getTaggedData(data, "boardName");
        m_scaleFactor = snoCADutilities.getTaggedDouble(m_scaleFactor, data, "scaleFactor");
        
        int r = 0;
        r = snoCADutilities.getTaggedInt(r, data,"boardOutlineColourR");
        int g = 0;
        g = snoCADutilities.getTaggedInt(g, data,"boardOutlineColourG");
        int b = 0;
        b = snoCADutilities.getTaggedInt(b, data,"boardOutlineColourB");
        
        m_boardOutlineColour = new java.awt.Color(r,g,b);
        
        r = snoCADutilities.getTaggedInt(r, data,"boardFillColourR");
        g = snoCADutilities.getTaggedInt(g, data,"boardFillColourG");
        b = snoCADutilities.getTaggedInt(b, data,"boardFillColourB");
     
        m_boardFillColour = new java.awt.Color(r,g,b);
        
        m_runningLength = snoCADutilities.getTaggedInt(m_runningLength, data, "runningLength");
        m_noseLength = snoCADutilities.getTaggedInt(m_noseLength, data, "noseLength");
        m_tailLength = snoCADutilities.getTaggedInt(m_tailLength, data, "tailLength");
        m_noseWidth = snoCADutilities.getTaggedInt(m_noseWidth, data, "noseWidth");
        m_tailWidth = snoCADutilities.getTaggedInt(m_tailWidth, data, "tailWidth");
        m_sidecutBias = snoCADutilities.getTaggedInt(m_sidecutBias, data, "sidecutBias");
        m_sidecutRadius = snoCADutilities.getTaggedInt(m_sidecutRadius, data, "sidecutRadius");

        m_frontPackRowCount = snoCADutilities.getTaggedInt(m_frontPackRowCount, data, "frontPackRowCount");
        m_rearPackRowCount = snoCADutilities.getTaggedInt(m_rearPackRowCount, data, "rearPackRowCount");
        m_frontPackInsertSpacing = snoCADutilities.getTaggedInt(m_frontPackInsertSpacing, data, "frontPackInsertSpacing");
        m_rearPackInsertSpacing = snoCADutilities.getTaggedInt(m_rearPackInsertSpacing, data, "rearPackInsertSpacing");
        m_stanceWidth = snoCADutilities.getTaggedInt(m_stanceWidth, data, "stanceWidth");
        m_stanceSetback = snoCADutilities.getTaggedInt(m_stanceSetback, data, "stanceSetback");
        
        m_noseBezier1Xfactor = snoCADutilities.getTaggedInt(m_noseBezier1Xfactor, data, "noseBezier1Xfactor");
        m_noseBezier1Yfactor = snoCADutilities.getTaggedInt(m_noseBezier1Yfactor, data, "noseBezier1Yfactor");
        m_noseBezier2Xfactor = snoCADutilities.getTaggedInt(m_noseBezier2Xfactor, data, "noseBezier2Xfactor");
        m_noseBezier2Yfactor = snoCADutilities.getTaggedInt(m_noseBezier2Yfactor, data, "noseBezier2Yfactor");
        m_tailBezier1Xfactor = snoCADutilities.getTaggedInt(m_tailBezier1Xfactor, data, "tailBezier1Xfactor");
        m_tailBezier1Yfactor = snoCADutilities.getTaggedInt(m_tailBezier1Yfactor, data, "tailBezier1Yfactor");
        m_tailBezier2Xfactor = snoCADutilities.getTaggedInt(m_tailBezier2Xfactor, data, "tailBezier2Xfactor");
        m_tailBezier2Yfactor = snoCADutilities.getTaggedInt(m_tailBezier2Yfactor, data, "tailBezier2Yfactor");
        
        m_convexRails = snoCADutilities.getTaggedBool(m_convexRails, data, "convexRails");
        
        m_camber = snoCADutilities.getTaggedInt(m_camber, data, "camber");
        
        m_camberSetback = snoCADutilities.getTaggedInt(m_camberSetback, data, "camberSetback");
       
        m_noseRadius = snoCADutilities.getTaggedInt(m_noseRadius, data,"noseRadius");
        m_tailRadius = snoCADutilities.getTaggedInt(m_tailRadius, data,"tailRadius");
        
        m_serrationCount = snoCADutilities.getTaggedInt(m_serrationCount, data, "serrationCount");
        m_serrationDepth = snoCADutilities.getTaggedDouble(m_serrationDepth, data, "serrationDepth");
        
        m_coreNoseLength = snoCADutilities.getTaggedDouble(m_coreNoseLength, data, "coreNoseLength");
        m_coreTailLength = snoCADutilities.getTaggedDouble(m_coreTailLength, data, "coreTailLength");
        m_tailThickness = snoCADutilities.getTaggedDouble(m_tailThickness, data, "tailThickness");
        m_rearPackOutboard = snoCADutilities.getTaggedInt(m_rearPackOutboard, data, "rearPackOutboard");
        m_rearPackInboard = snoCADutilities.getTaggedInt(m_rearPackInboard, data, "rearPackInboard");
        m_midPackOrd = snoCADutilities.getTaggedInt(m_midPackOrd, data, "midPackOrd");
        m_frontPackOutboard = snoCADutilities.getTaggedInt(m_frontPackOutboard, data, "frontPackOutboard");
        m_frontPackInboard = snoCADutilities.getTaggedInt(m_frontPackInboard, data, "frontPackInboard");
        m_noseThickness = snoCADutilities.getTaggedDouble(m_noseThickness, data, "noseThickness");
        m_midPackThickness = snoCADutilities.getTaggedDouble(m_midPackThickness, data, "midPackThickness");
        m_frontPackThickness = snoCADutilities.getTaggedDouble(m_frontPackThickness, data, "frontPackThickness");
        m_rearPackThickness = snoCADutilities.getTaggedDouble(m_rearPackThickness, data, "rearPackThickness");
        
        m_sidewallWidth = snoCADutilities.getTaggedInt(m_sidewallWidth, data, "sidewallWidth");
        m_insertStringerWidth = snoCADutilities.getTaggedInt(m_insertStringerWidth, data, "insertStringerWidth");
        m_coreStringerWidth = snoCADutilities.getTaggedInt(m_coreStringerWidth, data, "coreStringerWidth");
    
    
        m_tipspacerType = snoCADutilities.getTaggedInt(m_tipspacerType, data, "tipspacerType");
        m_tipspacerRadiusNose = snoCADutilities.getTaggedInt(m_tipspacerRadiusNose, data, "tipspacerRadiusNose");
        m_tipspacerRadiusTail = snoCADutilities.getTaggedInt(m_tipspacerRadiusTail, data, "tipspacerRadiusTail");
        m_tipspacerInterlockRadius = snoCADutilities.getTaggedInt(m_tipspacerInterlockRadius, data, "tipspacerInterlockRadius");
        m_tipSidewallOffset = snoCADutilities.getTaggedInt(m_tipSidewallOffset, data, "tipSidewallOffset");
    
        m_noseTipSpacerMaterialLength = snoCADutilities.getTaggedInt(m_noseTipSpacerMaterialLength, data, "tipspacerMaterialLengthNose");
        m_noseTipSpacerMaterialWidth = snoCADutilities.getTaggedInt(m_noseTipSpacerMaterialWidth, data, "tipspacerMaterialWidthNose");
        m_tailTipSpacerMaterialLength = snoCADutilities.getTaggedInt(m_tailTipSpacerMaterialLength, data, "tipspacerMaterialLengthTail");
        m_tailTipSpacerMaterialWidth = snoCADutilities.getTaggedInt(m_tailTipSpacerMaterialWidth, data, "tipspacerMaterialWidthTail");
        
        m_graphicsPrintBorder = snoCADutilities.getTaggedInt(m_graphicsPrintBorder, data, "graphicsPrintBorder");
      
        m_graphicsLibrary = new snoCADgraphicsLibrary();
        m_railGeometry = new snoCADrailGeometry();
        
        m_railType = snoCADutilities.getTaggedInt(m_railType, data, "railType");
        
        if (m_railType == snoCADutilities.MULTIPOINT_FREE)
        {
            for (int i = 0; i <= 4; i++)
            {
               String railData = "";
               railData = snoCADutilities.getTaggedData(data, "railPoint_" + Integer.toString(i));
               
               if (railData != "")
               {
                   snoCADrailPoint thisPoint = new snoCADrailPoint(snoCADutilities.getTaggedDouble(0, railData, "percent"), snoCADutilities.getTaggedDouble(0, railData, "width"));
                   m_railGeometry.addElement(thisPoint);
               }
            }
        }
       
        snoCADutilities.referenceString graphicsMetaData = new snoCADutilities.referenceString(fm.getGraphicsMetaData());
        
        while (graphicsMetaData.getString().length() > 0)
        {
            String thisElement = snoCADutilities.getFirstTagAndConsume(graphicsMetaData, "graphicElement");
            
            snoCADgraphicElement newElement = new snoCADgraphicElement(thisElement);
            newElement.setImage(fm.getImage(newElement.getName()), newElement.getName());

            m_graphicsLibrary.addElement(newElement, (int)newElement.getLayer() - 1); 
        }
        
        fm.closeReadFile();

    }
    
    public void importFromSNO(java.io.File inputFile)
    {
       String line = null;
       java.io.BufferedReader fileReader = null; 
       int nullValue = 0;
       double nullDoubleValue;
       
        try 
        {
            fileReader = new java.io.BufferedReader(new java.io.FileReader(inputFile));
        }
                catch (FileNotFoundException ex) 
        {
            
        }
       
        try 
        {
             m_scaleFactor = 2.6; // millimetres per pixel
             m_boardOutlineColour = new java.awt.Color(190,190,190);
             m_boardFillColour = new java.awt.Color(102,0,0);
             m_boardName = inputFile.getName();
             m_runningLength = Integer.valueOf(fileReader.readLine());
             m_noseLength = Integer.valueOf(fileReader.readLine());
             m_tailLength = Integer.valueOf(fileReader.readLine());
             m_noseWidth = Integer.valueOf(fileReader.readLine());
             m_tailWidth = Integer.valueOf(fileReader.readLine());
             m_sidecutRadius = Integer.valueOf(fileReader.readLine());
             nullDoubleValue = Double.valueOf(fileReader.readLine()); // boardWidth
             m_sidecutBias = Integer.valueOf(fileReader.readLine());
             nullValue = Integer.valueOf(fileReader.readLine()); // board length
             nullValue = Integer.valueOf(fileReader.readLine()); // edge offset
             nullValue = Integer.valueOf(fileReader.readLine()); // coreNW
             nullValue = Integer.valueOf(fileReader.readLine()); // coreNL
             nullValue = Integer.valueOf(fileReader.readLine()); // coreTW
             nullValue = Integer.valueOf(fileReader.readLine()); // coreTL
             nullValue = Integer.valueOf(fileReader.readLine()); // nose thickness
             nullValue = Integer.valueOf(fileReader.readLine()); // fpt
             nullValue = Integer.valueOf(fileReader.readLine()); // rpt
             nullValue = Integer.valueOf(fileReader.readLine()); // fptoo
             nullValue = Integer.valueOf(fileReader.readLine()); // fptoi
             nullValue = Integer.valueOf(fileReader.readLine()); // mpt
             nullValue = Integer.valueOf(fileReader.readLine()); // mpto
             nullValue = Integer.valueOf(fileReader.readLine()); // rptoi
             nullValue = Integer.valueOf(fileReader.readLine()); // rptoo
             nullValue = Integer.valueOf(fileReader.readLine()); // tt
             m_stanceWidth = Integer.valueOf(fileReader.readLine());
             m_stanceSetback = Integer.valueOf(fileReader.readLine());
             m_frontPackInsertSpacing = 10 * Integer.valueOf(fileReader.readLine());
             m_frontPackRowCount = Integer.valueOf(fileReader.readLine());
             m_rearPackInsertSpacing = 10 * Integer.valueOf(fileReader.readLine());
             m_rearPackRowCount = Integer.valueOf(fileReader.readLine());
             nullDoubleValue = Double.valueOf(fileReader.readLine()); // fp centre
             nullDoubleValue = Double.valueOf(fileReader.readLine()); // rp centre
             nullDoubleValue = Double.valueOf(fileReader.readLine()); // waist width
             m_noseRadius = Integer.valueOf(fileReader.readLine()); // nose tip radius
             m_tailRadius = Integer.valueOf(fileReader.readLine()); // tail tip radius
             m_camber = Integer.valueOf(fileReader.readLine()); // camber
             nullValue = Integer.valueOf(fileReader.readLine()); // nose 3D yn
             nullValue = Integer.valueOf(fileReader.readLine()); // tail 3D yn
             nullValue = Integer.valueOf(fileReader.readLine()); // nose 3D length
             nullValue = Integer.valueOf(fileReader.readLine()); // nose 3D width
             nullValue = Integer.valueOf(fileReader.readLine()); // tail 3D length
             nullValue = Integer.valueOf(fileReader.readLine()); // tail 3D width
             nullValue = Integer.valueOf(fileReader.readLine()); // nose blend
             nullValue = Integer.valueOf(fileReader.readLine()); // tail blend
             nullValue = Integer.valueOf(fileReader.readLine()); // nose geom
             nullValue = Integer.valueOf(fileReader.readLine()); // tail geom
             nullValue = Integer.valueOf(fileReader.readLine()); // noseXfactor
             nullValue = Integer.valueOf(fileReader.readLine()); // noseYfactor
             nullValue = Integer.valueOf(fileReader.readLine()); // tailXfactor
             nullValue = Integer.valueOf(fileReader.readLine()); // tailYfactor
             nullValue = Integer.valueOf(fileReader.readLine()); // noseAutoSmooth
             nullValue = Integer.valueOf(fileReader.readLine()); // tailAutoSmooth
             m_noseBezier1Xfactor = Integer.valueOf(fileReader.readLine());
             m_noseBezier1Yfactor = Integer.valueOf(fileReader.readLine());
             m_noseBezier2Xfactor = Integer.valueOf(fileReader.readLine());
             m_noseBezier2Yfactor = Integer.valueOf(fileReader.readLine());
             m_tailBezier1Xfactor = Integer.valueOf(fileReader.readLine());
             m_tailBezier1Yfactor = Integer.valueOf(fileReader.readLine());
             m_tailBezier2Xfactor = Integer.valueOf(fileReader.readLine());
             m_tailBezier2Yfactor = Integer.valueOf(fileReader.readLine());
             
             m_camberSetback = 0;
             m_tipspacerType = snoCADutilities.SIDEWALL;

             m_graphicsLibrary = new snoCADgraphicsLibrary();
        
             m_railGeometry = new snoCADrailGeometry();
             m_railType = snoCADutilities.QUADRATIC;
             
   
        } 
        catch (java.io.IOException ex)
        {
            
        }

        
    }
    
    public void importFromBCR(java.io.File inputFile)
    {
       String line = null;
       java.lang.StringBuffer fileContent = new java.lang.StringBuffer();
       java.io.BufferedReader fileReader = null; 
       
        try 
        {
            fileReader = new java.io.BufferedReader(new java.io.FileReader(inputFile));
        }
                catch (FileNotFoundException ex) 
        {
            
        }
       
        try 
        {
            while (( line = fileReader.readLine()) != null)
            {
                fileContent.append(line);
                fileContent.append(System.getProperty("line.separator"));
            }
        } 
        catch (java.io.IOException ex)
        {
            
        }

        String data = fileContent.toString();
        
        m_scaleFactor = 2.6; // millimetres per pixel
        m_boardOutlineColour = new java.awt.Color(190,190,190);
        m_boardFillColour = new java.awt.Color(102,0,0);
        m_boardName = inputFile.getName();
        
        m_runningLength =  Integer.valueOf(snoCADutilities.getIniItem(data, "RunningLength"));
        m_noseLength = Integer.valueOf(snoCADutilities.getIniItem(data, "NoseLength"));
        m_tailLength = Integer.valueOf(snoCADutilities.getIniItem(data, "TailLength"));
        m_noseWidth = Integer.valueOf(snoCADutilities.getIniItem(data, "NoseWidth"));
        m_tailWidth = Integer.valueOf(snoCADutilities.getIniItem(data, "TailWidth"));
        m_sidecutBias = Integer.valueOf(snoCADutilities.getIniItem(data, "SidecutBias"));
        m_sidecutRadius = Integer.valueOf(snoCADutilities.getIniItem(data, "SidecutRadius"));

        m_frontPackRowCount = Integer.valueOf(snoCADutilities.getIniItem(data, "FrontRowCount"));
        m_rearPackRowCount = Integer.valueOf(snoCADutilities.getIniItem(data, "RearRowCount"));
        m_frontPackInsertSpacing = 10 * Integer.valueOf(snoCADutilities.getIniItem(data, "FrontRowSpacing"));
        m_rearPackInsertSpacing = 10 * Integer.valueOf(snoCADutilities.getIniItem(data, "RearRowSpacing"));
        m_stanceWidth = Integer.valueOf(snoCADutilities.getIniItem(data, "StanceWidth"));
        m_stanceSetback = Integer.valueOf(snoCADutilities.getIniItem(data, "Offset"));
        
        m_noseBezier1Xfactor = m_noseLength / 2 - (Integer.valueOf(snoCADutilities.getIniItem(data, "NoseX2")));
        m_noseBezier1Yfactor = (m_noseWidth / 4) + (Integer.valueOf(snoCADutilities.getIniItem(data, "NoseY2")));
        m_noseBezier2Xfactor = (Integer.valueOf(snoCADutilities.getIniItem(data, "NoseX3")));
        m_noseBezier2Yfactor = 0 - (Integer.valueOf(snoCADutilities.getIniItem(data, "NoseY3")));
        m_tailBezier1Xfactor = m_tailLength / 2 + (Integer.valueOf(snoCADutilities.getIniItem(data, "TailX2")));
        m_tailBezier1Yfactor = (m_tailWidth / 4) + (Integer.valueOf(snoCADutilities.getIniItem(data, "TailY2")));
        m_tailBezier2Xfactor =  (Integer.valueOf(snoCADutilities.getIniItem(data, "TailX3")));
        m_tailBezier2Yfactor = 0 - (Integer.valueOf(snoCADutilities.getIniItem(data, "TailY3")));
        
        m_camber = (Integer.valueOf(snoCADutilities.getIniItem(data, "ProfileCamber")));
        m_noseRadius = (Integer.valueOf(snoCADutilities.getIniItem(data, "ProfileNoseRadius")));
        m_tailRadius = (Integer.valueOf(snoCADutilities.getIniItem(data, "ProfileTailRadius")));
        m_camberSetback = (Integer.valueOf(snoCADutilities.getIniItem(data, "ProfileOffset")));
        
        m_tipspacerType = snoCADutilities.SIDEWALL;
        m_graphicsLibrary = new snoCADgraphicsLibrary();
        
        m_railGeometry = new snoCADrailGeometry();
        m_railType = snoCADutilities.QUADRATIC;

    }
    
    public void saveBoard(java.io.File outputFile) 
    {
      
            String data = "";
        
            data = snoCADutilities.addTaggedData(data, "boardName" , m_boardName);

            data = snoCADutilities.addTaggedData(data, "scaleFactor", snoCADutilities.formatDouble(m_scaleFactor));
            
            data = snoCADutilities.addTaggedData(data,"boardOutlineColourR", m_boardOutlineColour.getRed());
            data = snoCADutilities.addTaggedData(data,"boardOutlineColourG", m_boardOutlineColour.getGreen());
            data = snoCADutilities.addTaggedData(data,"boardOutlineColourB", m_boardOutlineColour.getBlue());
            
            
            data = snoCADutilities.addTaggedData(data,"boardFillColourR", m_boardFillColour.getRed());
            data = snoCADutilities.addTaggedData(data,"boardFillColourG",m_boardFillColour.getGreen());
            data = snoCADutilities.addTaggedData(data,"boardFillColourB",m_boardFillColour.getBlue());

            data = snoCADutilities.addTaggedData(data, "runningLength", m_runningLength);
            data = snoCADutilities.addTaggedData(data, "noseLength", m_noseLength);
            data = snoCADutilities.addTaggedData(data, "tailLength", m_tailLength);
            data = snoCADutilities.addTaggedData(data, "noseWidth", m_noseWidth);
            data = snoCADutilities.addTaggedData(data, "tailWidth", m_tailWidth);
            data = snoCADutilities.addTaggedData(data, "sidecutBias", m_sidecutBias);
            data = snoCADutilities.addTaggedData(data, "sidecutRadius", m_sidecutRadius);

            data = snoCADutilities.addTaggedData(data, "frontPackRowCount", m_frontPackRowCount);
            data = snoCADutilities.addTaggedData(data, "rearPackRowCount", m_rearPackRowCount);
            data = snoCADutilities.addTaggedData(data, "frontPackInsertSpacing", m_frontPackInsertSpacing);
            data = snoCADutilities.addTaggedData(data, "rearPackInsertSpacing", m_rearPackInsertSpacing);
            data = snoCADutilities.addTaggedData(data, "stanceWidth", m_stanceWidth);
            data = snoCADutilities.addTaggedData(data, "stanceSetback", m_stanceSetback);
            
            data = snoCADutilities.addTaggedData(data, "noseBezier1Xfactor", m_noseBezier1Xfactor);
            data = snoCADutilities.addTaggedData(data, "noseBezier1Yfactor", m_noseBezier1Yfactor);
            data = snoCADutilities.addTaggedData(data, "noseBezier2Xfactor", m_noseBezier2Xfactor);
            data = snoCADutilities.addTaggedData(data, "noseBezier2Yfactor", m_noseBezier2Yfactor);
            data = snoCADutilities.addTaggedData(data, "tailBezier1Xfactor", m_tailBezier1Xfactor);
            data = snoCADutilities.addTaggedData(data, "tailBezier1Yfactor", m_tailBezier1Yfactor);
            data = snoCADutilities.addTaggedData(data, "tailBezier2Xfactor", m_tailBezier2Xfactor);
            data = snoCADutilities.addTaggedData(data, "tailBezier2Yfactor", m_tailBezier2Yfactor);
            
            data = snoCADutilities.addTaggedData(data, "camber", m_camber);
            data = snoCADutilities.addTaggedData(data, "camberSetback", m_camberSetback);
            data = snoCADutilities.addTaggedData(data, "noseRadius", m_noseRadius);
            data = snoCADutilities.addTaggedData(data, "tailRadius", m_tailRadius);
            
            data = snoCADutilities.addTaggedData(data, "convexRails", String.valueOf(m_convexRails));
            data = snoCADutilities.addTaggedData(data, "serrationCount", m_serrationCount);
            data = snoCADutilities.addTaggedData(data, "serrationDepth", m_serrationDepth);
            
            data = snoCADutilities.addTaggedData(data, "coreNoseLength", m_coreNoseLength);
            data = snoCADutilities.addTaggedData(data, "coreTailLength", m_coreTailLength);
            data = snoCADutilities.addTaggedData(data, "tailThickness", m_tailThickness);
            data = snoCADutilities.addTaggedData(data, "rearPackOutboard", m_rearPackOutboard);
            data = snoCADutilities.addTaggedData(data, "rearPackInboard" , m_rearPackInboard);
            data = snoCADutilities.addTaggedData(data, "midPackOrd", m_midPackOrd);
            data = snoCADutilities.addTaggedData(data, "frontPackOutboard", m_frontPackOutboard);
            data = snoCADutilities.addTaggedData(data, "frontPackInboard", m_frontPackInboard);
            data = snoCADutilities.addTaggedData(data, "noseThickness", m_noseThickness);
            data = snoCADutilities.addTaggedData(data, "midPackThickness", m_midPackThickness);
            data = snoCADutilities.addTaggedData(data, "frontPackThickness", m_frontPackThickness);
            data = snoCADutilities.addTaggedData(data, "rearPackThickness", m_rearPackThickness);
            
            data = snoCADutilities.addTaggedData(data, "sidewallWidth", m_sidewallWidth);
            data = snoCADutilities.addTaggedData(data, "insertStringerWidth", m_insertStringerWidth);
            data = snoCADutilities.addTaggedData(data, "coreStringerWidth", m_coreStringerWidth );
    
    
            data = snoCADutilities.addTaggedData(data, "tipspacerType", m_tipspacerType );
            data = snoCADutilities.addTaggedData(data, "tipspacerRadiusNose", m_tipspacerRadiusNose);
            data = snoCADutilities.addTaggedData(data, "tipspacerRadiusTail", m_tipspacerRadiusTail);
            data = snoCADutilities.addTaggedData(data, "tipspacerInterlockRadius", m_tipspacerInterlockRadius);
            data = snoCADutilities.addTaggedData(data, "tipSidewallOffset", m_tipSidewallOffset);
    
            data = snoCADutilities.addTaggedData(data, "tipspacerMaterialLengthNose", m_noseTipSpacerMaterialLength);
            data = snoCADutilities.addTaggedData(data, "tipspacerMaterialWidthNose", m_noseTipSpacerMaterialWidth);
            data = snoCADutilities.addTaggedData(data, "tipspacerMaterialLengthTail", m_tailTipSpacerMaterialLength);
            data = snoCADutilities.addTaggedData(data, "tipspacerMaterialWidthTail", m_tailTipSpacerMaterialWidth);
            data = snoCADutilities.addTaggedData(data, "graphicsPrintBorder", m_graphicsPrintBorder);
            
            data = snoCADutilities.addTaggedData(data, "railType", m_railType);
            
            if (m_railType == snoCADutilities.MULTIPOINT_FREE)
            {
                data = snoCADutilities.addTaggedData(data, "railPoint_0", getRailGeometry().getEntry(0).getAsXML());
                data = snoCADutilities.addTaggedData(data, "railPoint_1", getRailGeometry().getEntry(1).getAsXML());
                data = snoCADutilities.addTaggedData(data, "railPoint_2", getRailGeometry().getEntry(2).getAsXML());
                data = snoCADutilities.addTaggedData(data, "railPoint_3", getRailGeometry().getEntry(3).getAsXML());
                data = snoCADutilities.addTaggedData(data, "railPoint_4", getRailGeometry().getEntry(4).getAsXML());
            }

            snxFileManager fileWriter = new snxFileManager();
            
            fileWriter.openForWriting(outputFile);
            fileWriter.addBoard(data);
            
            if (m_graphicsLibrary.getSize() > 0)
            {
                String graphicsMetaData = "";
                
                for (int i = 0; i < m_graphicsLibrary.getSize(); i++)
                {
                     graphicsMetaData += m_graphicsLibrary.getEntry(i).getMetaData();  
                     fileWriter.addImage(m_graphicsLibrary.getEntry(i).getName(), m_graphicsLibrary.getEntry(i).getImage());
                }
                
                fileWriter.addGraphicMetaData(graphicsMetaData);
            }
            
            
            fileWriter.closeWrittenFile();

    }
    
   
    
    // Get methods
    public String getBoardName() { return m_boardName; }
    public double getScaleFactor() { return m_scaleFactor;}
    public java.awt.Color getBoardOutlineColour() { return m_boardOutlineColour; }
    public java.awt.Color getBoardFillColour() { return m_boardFillColour; }
    
    public int getRunningLength() { return m_runningLength; }
    public int getNoseLength() { return m_noseLength;}
    public int getTailLength() { return m_tailLength;}
    public int getNoseWidth() {return m_noseWidth;}
    public int getTailWidth() {return m_tailWidth;}
    public int getSidecutBias() {return m_sidecutBias;}
    public int getSidecutRadius() {return m_sidecutRadius;}
    
    public int getNoseBezier1Xfactor() {return m_noseBezier1Xfactor;}
    public int getNoseBezier1Yfactor() {return m_noseBezier1Yfactor;}
    public int getNoseBezier2Xfactor() {return m_noseBezier2Xfactor;}
    public int getNoseBezier2Yfactor() {return m_noseBezier2Yfactor;}
    public int getTailBezier1Xfactor() {return m_tailBezier1Xfactor;}
    public int getTailBezier1Yfactor() {return m_tailBezier1Yfactor;}
    public int getTailBezier2Xfactor() {return m_tailBezier2Xfactor;}
    public int getTailBezier2Yfactor() {return m_tailBezier2Yfactor;}
    
    public int getFrontPackRowCount() {return m_frontPackRowCount ;}
    public int getRearPackRowCount() {return m_rearPackRowCount ;}
    public int getFrontPackInsertSpacing() {return m_frontPackInsertSpacing ;}
    public int getRearPackInsertSpacing() {return m_rearPackInsertSpacing ;}
    public int getStanceWidth() {return m_stanceWidth ;}
    public int getStanceSetback() {return m_stanceSetback ;}
    
    public int getCamber() { return m_camber;}
    public int getNoseRadius() { return m_noseRadius;}
    public int getTailRadius() { return m_tailRadius;}
    
    public double getNoseHeight() { return m_noseHeight;}
    public double getTailHeight() { return m_tailHeight;}
    
    public int getCamberSetback() { return m_camberSetback;}
    
    public boolean getConvexRails() { return m_convexRails;}
    public int getSerrationCount() { return m_serrationCount;}
    public double getSerrationDepth() { return m_serrationDepth;}
    
    public double getCoreNoseLength() { return m_coreNoseLength;}
    public double getCoreTailLength() { return m_coreTailLength;}
    public int getCoreNoseWidth()  { return m_coreNoseWidth;}
    public int getCoreTailWidth() { return m_coreTailWidth;}
    public int getRearPackOutboard() {return m_rearPackOutboard;}
    public int getRearPackInboard() { return m_rearPackInboard;}
    public int getMidPackOrd() { return m_midPackOrd;}
    public int getFrontPackOutboard() { return m_frontPackOutboard;}
    public int getFrontPackInboard() { return m_frontPackInboard;}
    public double getNoseThickness() { return m_noseThickness;}
    public double getTailThickness() { return m_tailThickness;}
    public double getMidPackThickness() { return m_midPackThickness;}
    public double getFrontPackThickness() { return m_frontPackThickness;}
    public double getRearPackThickness() { return m_rearPackThickness;}
    
    public int getSidewallWidth() { return m_sidewallWidth;}
    public int getCoreStringerWidth() { return m_coreStringerWidth;}
    public int getInsertStringerWidth() { return m_insertStringerWidth;}
    public int getTipspacerType() { return m_tipspacerType;}
    
    public int getNoseTipspacerRadius() { return m_tipspacerRadiusNose;}
    public int getTailTipspacerRadius() { return m_tipspacerRadiusTail;}
    public int getTipSidewallOffset() { return m_tipSidewallOffset;}
    public int getTipspacerInterlockRadius() { return m_tipspacerInterlockRadius;}
    
    public int getNoseTipspacerMaterialLength() { return m_noseTipSpacerMaterialLength;}
    public int getNoseTipspacerMaterialWidth() { return m_noseTipSpacerMaterialWidth;}
    public int getTailTipspacerMaterialLength() { return m_tailTipSpacerMaterialLength;}
    public int getTailTipspacerMaterialWidth() { return m_tailTipSpacerMaterialWidth;}
    
    public int getGraphicsPrintBorder() { return m_graphicsPrintBorder;}
        
    public int getLength() 
    { 
        int length = m_runningLength;
        if (m_noseLength > 0) length += m_noseLength;
        if (m_tailLength > 0) length += m_tailLength;
        
        return length;
    }
    
    public double getWaistWidth(){ return m_waistWidth ;}

    // Set Methods
    public void setBoardName(String name) { m_boardName = name; } 
    public void setScaleFactor(double scaleFactor) { m_scaleFactor = scaleFactor; }
    public void setBoardOutlineColour(java.awt.Color colour) { m_boardOutlineColour = colour;}
    public void setBoardFillColour(java.awt.Color colour) { m_boardFillColour = colour;}
    
    public void setRunningLength(int runningLength) { m_runningLength = runningLength; }
    public void setNoseLength(int noseLength) { m_noseLength = noseLength;}
    public void setTailLength(int tailLength) { m_tailLength = tailLength;}
    public void setNoseWidth(int noseWidth) { m_noseWidth = noseWidth;}
    public void setTailWidth(int tailWidth) { m_tailWidth = tailWidth;}
    public void setSidecutBias(int sidecutBias) { m_sidecutBias = sidecutBias;}
    public void setSidecutRadius(int sidecutRadius) { m_sidecutRadius = sidecutRadius;}
    
    public void setWaistWidth(double waistWidth) { m_waistWidth = waistWidth;}
    
    public void setNoseBezier1Xfactor(int noseBezier1Xfactor) {m_noseBezier1Xfactor = noseBezier1Xfactor ;}
    public void setNoseBezier1Yfactor(int noseBezier1Yfactor) {m_noseBezier1Yfactor = noseBezier1Yfactor ;}
    public void setNoseBezier2Xfactor(int noseBezier2Xfactor) {m_noseBezier2Xfactor = noseBezier2Xfactor ;}
    public void setNoseBezier2Yfactor(int noseBezier2Yfactor) {m_noseBezier2Yfactor = noseBezier2Yfactor ;}
    public void setTailBezier1Xfactor(int tailBezier1Xfactor) {m_tailBezier1Xfactor = tailBezier1Xfactor ;}
    public void setTailBezier1Yfactor(int tailBezier1Yfactor) {m_tailBezier1Yfactor = tailBezier1Yfactor ;}
    public void setTailBezier2Xfactor(int tailBezier2Xfactor) {m_tailBezier2Xfactor = tailBezier2Xfactor ;}
    public void setTailBezier2Yfactor(int tailBezier2Yfactor) {m_tailBezier2Yfactor = tailBezier2Yfactor ;}
    
    public void setFrontPackInsertSpacing(int frontPackInsertSpacing) {m_frontPackInsertSpacing = frontPackInsertSpacing;}
    public void setRearPackInsertSpacing(int rearPackInsertSpacing) {m_rearPackInsertSpacing = rearPackInsertSpacing;}
    public void setFrontPackRowCount(int frontPackRowCount) {m_frontPackRowCount = frontPackRowCount;}
    public void setRearPackRowCount(int rearPackRowCount) {m_rearPackRowCount = rearPackRowCount;}
    public void setStanceWidth(int stanceWidth) { m_stanceWidth = stanceWidth;}
    public void setStanceSetback(int setback) {m_stanceSetback = setback;}
    
    public void setCamber(int camber) {m_camber = camber;}
    public void setCamberSetback(int camberSetback) { m_camberSetback = camberSetback;}
    public void setNoseRadius(int radius) {m_noseRadius = radius;}
    public void setTailRadius(int radius) {m_tailRadius = radius;}
    public void setNoseHeight(double height) { m_noseHeight = height;}
    public void setTailHeight(double height) { m_tailHeight = height;}
    
    public void setConvexRails(boolean railsMode) { m_convexRails = railsMode;}
    public void setSerrationCount(int count) { m_serrationCount = count;}
    public void setSerrationDepth(double depth) { m_serrationDepth = depth;}
    
    public void setCoreNoseLength(double value) {  m_coreNoseLength = value ;}
    public void setCoreTailLength(double value) {  m_coreTailLength = value ;}
    public void setCoreNoseWidth(int value)  {  m_coreNoseWidth = value ;}
    public void setCoreTailWidth(int value) {  m_coreTailWidth = value ;}
    public void setRearPackOutboard(int value) { m_rearPackOutboard = value ;}
    public void setRearPackInboard(int value) {  m_rearPackInboard = value ;}
    public void setMidPackOrd(int value) {  m_midPackOrd = value ;}
    public void setFrontPackOutboard(int value) {  m_frontPackOutboard = value ;}
    public void setFrontPackInboard(int value) {  m_frontPackInboard = value ;}
    public void setNoseThickness(double value) {  m_noseThickness = value ;}
    public void setTailThickness(double value) {  m_tailThickness = value ;}
    public void setMidPackThickness(double value) {  m_midPackThickness = value ;}
    public void setFrontPackThickness(double value) {  m_frontPackThickness = value ;}
    public void setRearPackThickness(double value) {  m_rearPackThickness = value ;}
    
    public void setTipspacerType (int type) { m_tipspacerType = type;}
   
    public void setSidewallWidth(int value) { m_sidewallWidth = value; }
    public void setCoreStringerWidth(int value) { m_coreStringerWidth = value;}
    public void setInsertStringerWidth(int value) { m_insertStringerWidth = value;}
    
    public void setNoseTipspacerRadius(int radius) { m_tipspacerRadiusNose = radius;}
    public void setTailTipspacerRadius(int radius) { m_tipspacerRadiusTail = radius;}
    public void setTipspacerInterlockRadius(int radius) { m_tipspacerInterlockRadius = radius;}
    public void setTipSidewallOffset(int offset) { m_tipSidewallOffset = offset;}
    
    public void setNoseTipSpacerMaterialLength(int length) {m_noseTipSpacerMaterialLength = length;}
    public void setNoseTipSpacerMaterialWidth(int length) {m_noseTipSpacerMaterialWidth = length;}
    public void setTailTipSpacerMaterialLength(int length) {m_tailTipSpacerMaterialLength = length;}
    public void setTailTipSpacerMaterialWidth(int length) {m_tailTipSpacerMaterialWidth = length;}
    
    public void setGraphicsPrintBorder(int border) { m_graphicsPrintBorder = border;}
    public void setRailType(int type) { m_railType = type;}
    
    public snoCADrailGeometry getRailGeometry() { return m_railGeometry;}

        
    public void addBoardGraphic(java.awt.image.BufferedImage image, String name) 
    { 
        snoCADgraphicElement gElement = new snoCADgraphicElement();
        gElement.setImage(image, name);
        gElement.setWidth(300);
        
        double scaleFactor = (double)image.getWidth() / (double)image.getHeight();

        gElement.setHeight((int)Math.round(gElement.getWidth() / scaleFactor));

        gElement.setPosX(0);
        gElement.setPosY(0 - (m_noseWidth / 2) - gElement.getHeight() - 50);
        m_graphicsLibrary.addElement(gElement);
    }
    
    public snoCADgraphicsLibrary getGraphics() { return m_graphicsLibrary; }
    
    public void clearGraphics()
    {
        
    }
    
    public int getRailType() { return m_railType;}
       
    // Board Representation
    private String m_boardName; 
    private double m_scaleFactor;
    private java.awt.Color m_boardOutlineColour;
    private java.awt.Color m_boardFillColour;
    
    // Board Geometry
    private int m_runningLength;
    private int m_noseLength;
    private int m_tailLength;
    private int m_noseWidth;
    private int m_tailWidth;
    private int m_sidecutBias;
    private int m_sidecutRadius;
    
    // Rail Geometry
    private boolean m_convexRails;
    private int m_serrationCount;
    private double m_serrationDepth;
    
    private double m_waistWidth;
    
    private int m_railType;
    private snoCADrailGeometry m_railGeometry;
    
    // Tip Shape
    private int m_noseBezier1Xfactor;
    private int m_noseBezier1Yfactor;
    private int m_noseBezier2Xfactor;
    private int m_noseBezier2Yfactor;
    private int m_tailBezier1Xfactor;
    private int m_tailBezier1Yfactor;
    private int m_tailBezier2Xfactor;
    private int m_tailBezier2Yfactor;
    
    // Inserts
    private int m_frontPackInsertSpacing;
    private int m_frontPackRowCount;
    private int m_rearPackInsertSpacing;
    private int m_rearPackRowCount;
    private int m_stanceWidth;
    private int m_stanceSetback;
    
    // Profile
    private int m_camber;
    private int m_camberSetback;
    private int m_noseRadius;
    private int m_tailRadius;
    private double m_noseHeight;
    private double m_tailHeight;
    
    // Core
    private double m_coreNoseLength;
    private double m_coreTailLength;
    private int m_coreNoseWidth;
    private int m_coreTailWidth;
    private int m_rearPackOutboard;
    private int m_rearPackInboard;
    private int m_midPackOrd;
    private int m_frontPackOutboard;
    private int m_frontPackInboard;
    private double m_noseThickness;
    private double m_tailThickness;
    private double m_midPackThickness;
    private double m_frontPackThickness;
    private double m_rearPackThickness;
    
    private int m_sidewallWidth;
    private int m_insertStringerWidth;
    private int m_coreStringerWidth;
    
    
    private int m_tipspacerType;
    private int m_tipspacerRadiusNose;
    private int m_tipspacerRadiusTail;
    private int m_tipspacerInterlockRadius;
    private int m_tipSidewallOffset;
    
    private int m_noseTipSpacerMaterialLength;
    private int m_noseTipSpacerMaterialWidth;
    private int m_tailTipSpacerMaterialLength;
    private int m_tailTipSpacerMaterialWidth;
    
    // Graphics
    private snoCADgraphicsLibrary m_graphicsLibrary;
    private int m_graphicsPrintBorder;
   
}
