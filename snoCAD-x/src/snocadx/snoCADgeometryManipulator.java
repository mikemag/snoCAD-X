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
 * geometryManipulator.java
 *
 * Created on 23 March 2007, 18:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package snocadx;

import java.awt.geom.Rectangle2D;

/**
 *
 * @author dgraf
 */
public class snoCADgeometryManipulator {
    
    /** Creates a new instance of geometryManipulator */
    
    // Constructor when location is unknown
    public snoCADgeometryManipulator(String handleTitle)
    {
        m_size = 5;
        m_sensorSize = 100;
        m_symbol = new java.awt.geom.Rectangle2D.Double();
        m_x = 0;
        m_y = 0;
        m_visible = false;
        m_hidden = false;
        m_inUse = false;
        m_handleTitle = handleTitle;
        m_message = "";
        m_font = new java.awt.Font("Arial", java.awt.Font.PLAIN, 10);
        m_treePath = null;
       

        updateSymbol();
    }
    
    // COnstruct in a known location
    public snoCADgeometryManipulator(double x, double y, String handleTitle) 
    {
        m_size = 5;
        m_sensorSize = 100;
        m_symbol = new java.awt.geom.Rectangle2D.Double();
        m_x = x;
        m_y = y;
        m_visible = false;
        m_hidden = false;
        m_inUse = false;
        m_handleTitle = handleTitle;
        m_message = "";
        m_font = new java.awt.Font("Arial", java.awt.Font.PLAIN, 10);
        m_treePath = null;
        
        updateSymbol();

    }
    
    public void draw(double x, double y, java.awt.Graphics2D g2d)
    {
       
       
       setLocation(x, y);
       updateSymbol();
       g2d.setColor(m_colour);

       if (m_hidden) return;
       
       if (m_visible) 
       {
           g2d.draw(m_symbol);
           g2d.setFont(m_font);
           g2d.setColor(m_inactiveColour);
           g2d.drawString(m_handleTitle, (int)x + m_size - 1, (int)y - m_size- 1);
           g2d.drawString(m_message, (int)x + m_size + 1, (int)y + 6);
           g2d.setColor(m_colour);
           g2d.drawString(m_handleTitle, (int)x + m_size, (int)y - m_size);
           g2d.drawString(m_message, (int)x + m_size, (int)y + 5);

       }
       else
       {
           g2d.setColor(m_inactiveColour);
           g2d.drawRect((int)m_x - 1, (int)m_y - 1, 2, 2);
       }
    }
    
    private void updateSymbol()
    {

        m_colour = new java.awt.Color(255,255,0);
        m_inactiveColour = new java.awt.Color(90,90,30);
        m_symbol.setFrame(m_x - m_size / 2, m_y - m_size / 2, m_size, m_size);  
    }
    
    public double getX() { return m_x; }
    public double getY() { return m_y; }
    public void setVisible(boolean visibility) { m_visible = visibility; m_hidden = false;}
    public void hideHandle( ) { m_hidden = true;}
    
    public void setLocation(double x, double y) { m_x = x; m_y = y; updateSymbol(); }
    
    public java.awt.geom.Rectangle2D.Double getSymbol() { return m_symbol; }
    
    public boolean sensorActivated(double x, double y)
    {
        return getSensorBounds().contains(x,y);
    }
    
    public void setInUse(boolean inUse) { m_inUse = inUse;}
    
    public boolean inUse() { return m_inUse; }
    
    public boolean isVisible() { return m_visible;}
    
    public void setMessage(String message) { m_message = message;}
    
    public boolean handleHeld(double x, double y)
    {
        return m_symbol.contains(x, y);
    }
    
    public java.awt.geom.Rectangle2D.Double getSensorBounds() 
    { 
        java.awt.geom.Rectangle2D.Double returnRectangle = new java.awt.geom.Rectangle2D.Double();
        returnRectangle.setFrame(m_x - m_sensorSize / 2, m_y - m_sensorSize / 2, m_sensorSize, m_sensorSize);
        return returnRectangle;
    }
    
    void setSensorBounds(int sensorSize)
    {
        m_sensorSize = sensorSize;
    }
    
    public void setTreePath(javax.swing.tree.TreePath tp) { m_treePath = tp; }
    public javax.swing.tree.TreePath getTreePath() { return m_treePath;}
    
    private double m_x;
    private double m_y;
    private java.awt.Color m_colour;
    private java.awt.Color m_inactiveColour;
    private boolean m_visible;
    private boolean m_inUse;
    private boolean m_hidden;
    private int m_size;
    private int m_sensorSize;
    private String m_handleTitle;
    private String m_message;
    private java.awt.Font m_font;

    private java.awt.geom.Rectangle2D.Double m_symbol;
    private javax.swing.tree.TreePath m_treePath;

}
