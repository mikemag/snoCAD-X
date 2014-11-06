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
 * snoCADrailPoint.java
 *
 * Created on 01 May 2007, 14:42
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package snocadx;

/**
 *
 * @author dgraf
 */
public class snoCADrailPoint {
    
    /** Creates a new instance of snoCADrailPoint */
    public snoCADrailPoint(double railPercent, double width) 
    {
        m_railPercent = railPercent;
        m_width = width;
        m_geomHandle = new snoCADgeometryManipulator("Rail Point");
    }
    
    public String getAsXML()
    {
        String xml = "";
        xml = snoCADutilities.addTaggedData(xml, "percent", m_railPercent);
        xml = snoCADutilities.addTaggedData(xml, "width", m_width);
        return xml;
    }
    
    private double m_railPercent;
    private double m_width;
    
    public snoCADgeometryManipulator getHandle() { return m_geomHandle;}
    public void setHandle(snoCADgeometryManipulator gmp) { m_geomHandle = gmp;}
    
    public double getRailPercent() {return m_railPercent;}
    public double getWidth() {return m_width;}
    
    public void setRailPercent(double percent) { m_railPercent = percent;}
    public void setWidth(double width) {m_width = width;}
    
    private snoCADgeometryManipulator m_geomHandle;

}
