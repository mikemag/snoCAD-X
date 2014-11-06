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
 * snoCADrailGeometry.java
 *
 * Created on 01 May 2007, 14:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package snocadx;

/**
 *
 * @author dgraf
 */
public class snoCADrailGeometry {
    
    /** Creates a new instance of snoCADrailGeometry */
    public snoCADrailGeometry() 
    {
        m_railPoints = new java.util.ArrayList<snoCADrailPoint>();
        m_smoothingPercent = 50;
    }
    
    public void addElement(snoCADrailPoint element)
    {

        m_railPoints.add(element);

    }
    
    public int addElement(double percent, double width)
    {
        snoCADrailPoint p = new snoCADrailPoint(percent, width);
        addElement(p);
        sort();
        
        for (int i = 0; i < getSize(); i++)
        {
            if (getEntry(i).getRailPercent() == percent)
            {
                return i + 1;
            }
        }
        
        return 0;
    }
    
    public void addElement(snoCADrailPoint element, int index)
    {
        m_railPoints.add(index, element);
    }
    
    
    public void deletePoint(int i)
    {
        m_railPoints.remove(i);
    }
    

    public void sort()
    {
        java.util.Collections.sort(m_railPoints, new elementComparator());
    }
    
    public void deleteAll()
    {
        m_railPoints.clear();
    }
    
    class elementComparator implements java.util.Comparator
   {
      public final int compare ( Object a, Object b )
      {
          snoCADrailPoint c = (snoCADrailPoint) a;
          snoCADrailPoint d = (snoCADrailPoint) b;
 
          return (String.valueOf(c.getRailPercent()).compareTo(String.valueOf(d.getRailPercent())));
      }
   } 

    
    public int getSize() { return m_railPoints.size();} 
    public double getSmoothingPercent() { return m_smoothingPercent;}
    
    public void setSmoothingPercent(double percent) { m_smoothingPercent = percent;}
    
    public snoCADrailPoint getEntry(int i) { return (snoCADrailPoint) m_railPoints.get(i);}
    
    private java.util.ArrayList<snoCADrailPoint> m_railPoints;
    private double m_smoothingPercent;
    
}
