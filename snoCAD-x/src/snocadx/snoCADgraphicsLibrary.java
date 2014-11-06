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
 * snoCADgraphicsLibrary.java
 *
 * Created on 26 March 2007, 22:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package snocadx;

/**
 *
 * @author dgraf
 */
public class snoCADgraphicsLibrary {
    
    /** Creates a new instance of snoCADgraphicsLibrary */
    public snoCADgraphicsLibrary() 
    {
        m_graphicElements = new java.util.ArrayList<snoCADgraphicElement>();
    }
    
    private java.util.ArrayList m_graphicElements;
    
    public void addElement(snoCADgraphicElement element)
    {
        
        javax.swing.JLabel label = new javax.swing.JLabel();
        label.setText(element.getName());
        m_graphicElements.add(element);
        element.setLayer((double)m_graphicElements.indexOf(element) + 1);
        
       
    }
    
    public void addElement(snoCADgraphicElement element, int index)
    {
        
        javax.swing.JLabel label = new javax.swing.JLabel();
        element.setLayer((double)index + 1);
        
        label.setText(element.getName());
        
        m_graphicElements.add(index, element);

    }
    
    
    public void deleteElement(int i)
    {
        m_graphicElements.remove(i);
    }
    
    public void reLayer()
    {
        
        
        for (int i = 0; i < getSize(); i++)
        {
            getEntry(i).setLayer(i + 1);
        }
        
    }
    
    public void sort()
    {
        java.util.Collections.sort(m_graphicElements, new elementComparator());
        reLayer();
    }
    
    class elementComparator implements java.util.Comparator
   {
      public final int compare ( Object a, Object b )
      {
          snoCADgraphicElement c = (snoCADgraphicElement) a;
          snoCADgraphicElement d = (snoCADgraphicElement) b;
 
          return (String.valueOf(c.getLayer()).compareTo(String.valueOf(d.getLayer())));
      }
   } 

    
    public int getSize() { return m_graphicElements.size();} 
    
    public snoCADgraphicElement getEntry(int i) { return (snoCADgraphicElement) m_graphicElements.get(i);}
    
}
