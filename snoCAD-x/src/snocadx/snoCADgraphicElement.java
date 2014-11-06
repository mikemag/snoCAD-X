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
 * snoCADgraphicElement.java
 *
 * Created on 26 March 2007, 22:34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package snocadx;

/**
 *
 * @author dgraf
 */
public class snoCADgraphicElement {
    
    /** Creates a new instance of snoCADgraphicElement */
    public snoCADgraphicElement()
    {
        m_image = null;
        m_width = 0;
        m_height = 0;
        m_posX = 0;
        m_posY = 0;
        m_name = "";
        m_positionMode = FLOAT;
        m_renderMode = FULLSIZE;
        m_tileheight = 100; //mm
        m_tilewidth = 100; //mm
        m_layer = 0;
        m_opacity = 100;
        m_angle = 0;
        
        m_stretcher = new snoCADgeometryManipulator("stretch");
        m_dragger = new snoCADgeometryManipulator("drag");
        m_rotator = new snoCADgeometryManipulator("rotate");

    }
    
    public snoCADgraphicElement(String metadata)
    {
        m_image = null;
        m_width = snoCADutilities.getTaggedInt(m_width, metadata, "imageWidth"); // As a % of board length
        m_height = snoCADutilities.getTaggedInt(m_height, metadata, "imageHeight"); // As a % of board width
        m_posX = snoCADutilities.getTaggedInt(m_posX, metadata, "imagePosX"); // As a % of board length 0 is board centre (negative towards nose)
        m_posY = snoCADutilities.getTaggedInt(m_posY, metadata, "imagePosY"); // In mm from board width 0 is board centre (negative towards toe)
        m_name = snoCADutilities.getTaggedData(metadata, "imageName");
        m_angle = snoCADutilities.getTaggedDouble(m_angle, metadata, "angle"); // In degrees
        m_positionMode = snoCADutilities.getTaggedInt(m_positionMode, metadata, "positionMode");
        m_renderMode = snoCADutilities.getTaggedInt( m_renderMode, metadata, "renderMode");
        m_tileheight = snoCADutilities.getTaggedInt(m_tileheight, metadata, "tileHeight");
        m_tilewidth = snoCADutilities.getTaggedInt(m_tilewidth, metadata, "tileWidth");
        m_layer = snoCADutilities.getTaggedDouble(m_layer, metadata, "layer");
        m_opacity = snoCADutilities.getTaggedInt(m_opacity, metadata, "opacity");
        m_stretcher = new snoCADgeometryManipulator("stretch");
        m_dragger = new snoCADgeometryManipulator("drag");
        m_rotator = new snoCADgeometryManipulator("rotate");
    }
    
    public void setImage(java.awt.image.BufferedImage image, String name) 
    { 
        m_image = image; 
        m_name = name;
    }
    public void setName(String name) {m_name = name;}
    public void setHeight(int height) {m_height = height;}
    public void setWidth(int width) {m_width = width;}
    public void setPosX(int posX) { m_posX = posX;}
    public void setPosY(int posY) { m_posY = posY;}
    public void setAngle(double angle) { m_angle = angle;}
    
    public int getHeight() { return m_height;}
    public int getWidth() { return m_width;}
    public int getPosX() { return m_posX;}
    public int getPosY() { return m_posY;}
    public double getAngle() { return m_angle;}
    
    public String getName() { return m_name;}
    public java.awt.image.BufferedImage getImage() { return m_image;}
    
    public String getMetaData()
    {
        String metaData = "";
        metaData = "<graphicElement>";
        metaData = snoCADutilities.addTaggedData(metaData, "imageName", m_name);
        metaData = snoCADutilities.addTaggedData(metaData, "imageHeight", m_height);
        metaData = snoCADutilities.addTaggedData(metaData, "imageWidth", m_width);
        metaData = snoCADutilities.addTaggedData(metaData, "imagePosX", m_posX);
        metaData = snoCADutilities.addTaggedData(metaData, "imagePosY", m_posY);
        metaData = snoCADutilities.addTaggedData(metaData, "layer", m_layer);
        metaData = snoCADutilities.addTaggedData(metaData, "positionMode", m_positionMode);
        metaData = snoCADutilities.addTaggedData(metaData, "renderMode", m_renderMode);
        metaData = snoCADutilities.addTaggedData(metaData, "tileWidth", m_tilewidth);
        metaData = snoCADutilities.addTaggedData(metaData, "tileHeight", m_tileheight);
        metaData = snoCADutilities.addTaggedData(metaData, "opacity", m_opacity);
        metaData = snoCADutilities.addTaggedData(metaData, "angle", m_angle);
        metaData += "</graphicElement>";
        
        return metaData;    
    }
    
    private java.awt.image.BufferedImage m_image;
    private int m_width;
    private int m_height;
    private int m_posX;
    private int m_posY;
    private String m_name;
    private int m_renderMode;
    private int m_positionMode;
    private double m_layer;
    private int m_tileheight;
    private int m_tilewidth;
    private int m_opacity;
    private double m_angle;
    
    public int getRenderMode() { return m_renderMode;}
    public int getPositionMode() { return m_positionMode;}
    public double getLayer() { return m_layer;}
    public int getTileHeight() { return m_tileheight;}
    public int getTileWidth() { return m_tilewidth;}
    public int getOpacity() { return m_opacity;}
    
    public void setRenderMode(int mode) { m_renderMode = mode;}
    public void setPositionMode(int mode) { m_positionMode = mode;}  
    public void setLayer(double layer) { m_layer = layer;}
    public void setTileHeight(int tileheight) { m_tileheight = tileheight;}
    public void setTileWidth(int tilewidth) { m_tilewidth = tilewidth;}
    public void setOpacity(int opacity) { m_opacity = opacity;}
    
    private snoCADgeometryManipulator m_stretcher;
    private snoCADgeometryManipulator m_dragger;
    private snoCADgeometryManipulator m_rotator;
    
    public snoCADgeometryManipulator getStretcher() { return m_stretcher;}
    public snoCADgeometryManipulator getDragger() { return m_dragger;}
    public snoCADgeometryManipulator getRotator() { return m_rotator;}
    
    public static final int FLOAT = 0;
    public static final int STRETCH = 1;

    public static final int FULLSIZE = 0;
    public static final int TILED = 1;

    
}
