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
 * snoCADutilities.java
 *
 * Created on 26 March 2007, 22:42
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package snocadx;

/**
 *
 * @author dgraf
 */
public class snoCADutilities {
    
    /** Creates a new instance of snoCADutilities */
    public snoCADutilities() {
    }
    
    static public String getFirstTagAndConsume(referenceString XML, String tagname)
    {
        String value = "";
        String startTag = "<" + tagname + ">";
        String endTag = "</" + tagname + ">";
        
        int startTagIndex = XML.m_string.indexOf(startTag);
        int endTagIndex = XML.m_string.indexOf(endTag);
        
        value = XML.m_string.substring(startTagIndex + startTag.length(), endTagIndex);
        
        XML.setString(XML.m_string.substring(endTagIndex + endTag.length()));
                
        
        return value;
    }
    
     static public String addTaggedData(String data, String tag, int value )
    {
        data += "<" + tag + ">" + value + "</" + tag + ">";
        return data;
    }
    
    static public String addTaggedData(String data, String tag, double value )
    {
        data += "<" + tag + ">" + value + "</" + tag + ">";
        return data;
    }
        
    static public String addTaggedData(String data, String tag, String value )
    {
        data += "<" + tag + ">" + value + "</" + tag + ">";
        return data;
    }
     
    static public String getTaggedData(String XML, String tagname)
    {
        String value = "";
        String startTag = "<" + tagname + ">";
        String endTag = "</" + tagname + ">";
        
        int startTagIndex = XML.indexOf(startTag);
        int endTagIndex = XML.indexOf(endTag);
        
        if (startTagIndex < 0 || endTagIndex < 0) return "";
        
        value = XML.substring(startTagIndex + startTag.length(), endTagIndex);
         
        return value;
    }
    
    static public int getTaggedInt(int value, String XML, String tagname)
    {
        String strvalue = "";
        
        String startTag = "<" + tagname + ">";
        String endTag = "</" + tagname + ">";
        
        int startTagIndex = XML.indexOf(startTag);
        int endTagIndex = XML.indexOf(endTag);
        
        if (startTagIndex < 0 || endTagIndex < 0) return value;
        
        strvalue = XML.substring(startTagIndex + startTag.length(), endTagIndex);
        
        if (strvalue.length() > 0) value = Integer.valueOf(strvalue);
        
        return value;
        
    }
    
    static public double getTaggedDouble(double value, String XML, String tagname)
    {
        String strvalue = "";
        
        String startTag = "<" + tagname + ">";
        String endTag = "</" + tagname + ">";
        
        int startTagIndex = XML.indexOf(startTag);
        int endTagIndex = XML.indexOf(endTag);
        
        if (startTagIndex < 0 || endTagIndex < 0) return value;
        
        strvalue = XML.substring(startTagIndex + startTag.length(), endTagIndex);
        
        if (strvalue.length() > 0) value = Double.valueOf(strvalue);
        
        return value;
        
    }
    
    static public boolean getTaggedBool(boolean value, String XML, String tagname)
    {
        String strvalue = "";
        
        String startTag = "<" + tagname + ">";
        String endTag = "</" + tagname + ">";
        
        int startTagIndex = XML.indexOf(startTag);
        int endTagIndex = XML.indexOf(endTag);
        
        if (startTagIndex < 0 || endTagIndex < 0) return value;
        
        strvalue = XML.substring(startTagIndex + startTag.length(), endTagIndex);
        
        if (strvalue.length() > 0) value = Boolean.valueOf(strvalue);
        
        return value;
        
    }
    
    static public String getIniItem(String iniFile, String itemName)
    {
        String value = "";


        int startTagIndex = iniFile.toLowerCase().indexOf(itemName.toLowerCase());
        String iniFileSection = iniFile.toLowerCase().substring(startTagIndex);
        
        int endTagIndex = iniFileSection.indexOf("\r\n");
        
        String item = iniFileSection.substring(0, endTagIndex);
        
        int equalsIndex = item.indexOf("=");
        
        value = item.substring(equalsIndex + 1);
         
        return value;
        
    }
    
    static public class referenceString
    {
        public referenceString(String string)
        {
            m_string = string;
        }
        
        public void setString(String string) { m_string = string;}
        public String getString() { return m_string;}
        
        String m_string;
    }
    
    static public class widthObject
    {
       public widthObject(double maxWidth, double maxWidthPercent, double minWidth, double minWidthPercent)
       {
           m_maxWidth = maxWidth;
           m_maxWidthPercent = maxWidthPercent;
           m_minWidth = minWidth;
           m_minWidthPercent = minWidthPercent;
       }
       
       private double m_maxWidth;
       private double m_maxWidthPercent;
       private double m_minWidth;
       private double m_minWidthPercent;
       
       public double getMaxWidth() { return m_maxWidth;}
       public double getMinWidth() { return m_minWidth;}
       public double getMaxWidthPercent() { return m_maxWidthPercent;}
       public double getMinWidthPercent() { return m_minWidthPercent;}
       
    }
    static public class point 
    {
        public point(double _x, double _y)
        {
            x = _x;
            y = _y;
        }
        
        public double x;
        public double y;
    }
        
    
    static public point getQuadraticControlPoint(point a, point q, point c) 
    {
	point b = new point(0, 0);
	point f1 = new point(0, 0);
	point f2 = new point(0, 0);
	f1 = extrapolate(c, q, 1);
	f2 = extrapolate(a, q, 1);
	b = lerp(f1, f2);
	return b;
    }
    
    static public point lerp(point pointA, point pointB) 
    {
	point midpoint = new point(0, 0);
	midpoint.x = pointA.x + (pointB.x - pointA.x) * 0.5;
	midpoint.y = pointA.y + (pointB.y - pointA.y) * 0.5;
	return midpoint;
    }
    
    static public point extrapolate(point a, point b, int factor) 
    {
	point extrapolated = new point(0, 0);
        
	if (a.x < b.x) {
		extrapolated.x = b.x + (factor * (b.x - a.x));
	}
	if (a.x > b.x) {
		extrapolated.x = b.x - (factor * (a.x - b.x));
	}
	if (a.x == b.x) {
		extrapolated.x = factor * (a.x);
	}
	if (a.y < b.y) {
		extrapolated.y = b.y + (factor * (b.y - a.y));
	}
	if (a.y > b.y) {
		extrapolated.y = b.y - (factor * (a.y - b.y));
	}
	if (a.y == b.y) {
		extrapolated.y = factor * (b.y);
	}
        
	return extrapolated;
    }
    
     static public snoCADutilities.point getMiddle(snoCADutilities.point P0, snoCADutilities.point P1) 
    { 
        snoCADutilities.point middle = new snoCADutilities.point(0,0);
        middle.x = (P0.x + P1.x) / 2;
        middle.y = (P0.y + P1.y) / 2;
        return middle; 
    }
    
    static public snoCADutilities.point getPointOnSegment(snoCADutilities.point P0, snoCADutilities.point P1, double ratio)
    {
        snoCADutilities.point returnPoint = new snoCADutilities.point(0,0);
        returnPoint.x = (P0.x + (P1.x - P0.x) * ratio) ;
        returnPoint.y = (P0.y + (P1.y - P0.y) * ratio) ;
        
        return returnPoint;
    }
    
    static public snoCADutilities.point quadratic(double t, snoCADutilities.point a, snoCADutilities.point b, snoCADutilities.point c)
    {
        snoCADutilities.point quadPoint = new snoCADutilities.point(0,0);
        double invT = 1 - t;
        quadPoint.x = (a.x * t * t + b.x * 2 * t * invT) + c.x * invT * invT;
        quadPoint.y = (a.y * t * t + b.y * 2 * t * invT) + c.y * invT * invT;
        
        return quadPoint;
    }
    
    static public double distance(int ax, int ay, int bx, int by) 
    {
        int length_A = 0;
        int length_B = 0;
	if (bx > ax) 
        {
		length_A = bx - ax;
	} 
        else 
        {
		length_A = ax - bx;
	}
	if (by > ay) 
        {
		length_B = by - ay;
	} 
        else 
        {
		length_B = ay - by;
	}
	double result = Math.sqrt(length_B * length_B + length_A * length_A);
	return result;
    }
    
     static public double distance(double ax, double ay, double bx, double by) 
    {
        double length_A = 0;
        double length_B = 0;
        
	if (bx > ax) 
        {
		length_A = bx - ax;
	} 
        else 
        {
		length_A = ax - bx;
	}
	if (by > ay) 
        {
		length_B = by - ay;
	} 
        else 
        {
		length_B = ay - by;
	}
	double result = Math.sqrt(length_B * length_B + length_A * length_A);
	return result;
    }
     
    static public javax.swing.JDialog beginTask(String title, javax.swing.JInternalFrame frame)
    {
       
       javax.swing.JDialog progress = new javax.swing.JDialog();
       progress.setSize(200,100);
       progress.setTitle(title);
       progress.setLocation(frame.getX() + (frame.getWidth() / 2), frame.getY() + (frame.getHeight() / 2));
       progress.setAlwaysOnTop(true);
       progress.setVisible(true);
 
       return progress;
    }
    
    static public void endTask(javax.swing.JDialog jp)
    {
          javax.swing.JButton button = new javax.swing.JButton();
          button.setText("Done");
          button.setSize(100,50);
          button.setLocation(50, 25);
          final javax.swing.JDialog dlg = jp;
          dlg.add(button);
          button.addActionListener(new java.awt.event.ActionListener() 
          {
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
                dlg.setVisible(false);
                dlg.dispose();
                
            }
        });
        
    }
    
    static public String formatDouble(double value)
    {
        
        java.math.BigDecimal bigVal = new java.math.BigDecimal(value);
        java.text.NumberFormat n = java.text.NumberFormat.getInstance(java.util.Locale.US); 
        String retString = n.format(value);
        return retString;
      
    }
    
    
    // Edit Mode Constants
    static public final int EDIT_BOARD = 0;
    static public final int EDIT_GRAPHICS = 1;
    static public final int EDIT_CORE = 2;
    static public final int EDIT_NOSE_TIPSPACER = 3;
    static public final int EDIT_TAIL_TIPSPACER = 4;
    static public final int EDIT_PROFILE = 5;
    static public final int EDIT_FLEX = 6;
    
    // Tipspacer Type Constants
    static public final int SIDEWALL = 0;
    static public final int RADIUS = 1;
    static public final int INTERLOCK = 2;
    static public final int STRAIGHT = 3;
    static public final int POINTY = 4;
    static public final int NONE = 99;
    
    // Rail Type Constants
    static public final int QUADRATIC = 0;
    static public final int RADIAL = 1;
    static public final int RAIL_STRAIGHT = 2;
    static public final int MULTIPOINT_FREE = 4;
    static public final int MULTIPOINT_GUIDED = 5;
    
    // Paper Size Constants
    static public final int A4 = 0;
    static public final int A3 = 1;
    static public final int LEGAL = 2;
    
    
    
}
