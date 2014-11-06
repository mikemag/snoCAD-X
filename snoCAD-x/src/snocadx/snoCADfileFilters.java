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
 * fileFilterClasses.java
 *
 * Created on 25 March 2007, 23:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package snocadx;

/**
 *
 * @author dgraf
 */
public class snoCADfileFilters {
    
    /** Creates a new instance of fileFilterClasses */
    public snoCADfileFilters() 
    {
        m_snoCADxFileFilter = new snoCADxFileFilter();
        m_importFilter = new importFilter();
        m_dxfFilter = new dxfFilter();
        m_graphicsFilter = new graphicsFilter();
        m_pngFilter = new pngFilter();
        m_pdfFilter = new pdfFilter();
        m_htmlFilter = new htmlFilter();
        m_folderFilter = new folderFilter();
    }
    
    public snoCADxFileFilter getSnoCADxFileFilter(){return m_snoCADxFileFilter;}
    public importFilter getImportFilter() {return m_importFilter;}
    public dxfFilter getDXFfilter(){return m_dxfFilter;}
    public graphicsFilter getGraphicsFilter(){ return m_graphicsFilter;}
    public pdfFilter getPDFfilter() { return m_pdfFilter;}
    public pngFilter getPNGfilter() { return m_pngFilter;}
    public htmlFilter getHTMLfilter() { return m_htmlFilter;}
    public folderFilter getFolderFilter() { return m_folderFilter;}
    
    private snoCADxFileFilter m_snoCADxFileFilter;
    private importFilter m_importFilter;
    private dxfFilter m_dxfFilter;
    private graphicsFilter m_graphicsFilter;
    private pdfFilter m_pdfFilter;
    private pngFilter m_pngFilter;
    private htmlFilter m_htmlFilter;
    private folderFilter m_folderFilter;
    
    public class htmlFilter extends javax.swing.filechooser.FileFilter 
    {
        public boolean accept(java.io.File f) 
        {
            return f.isDirectory() || f.getName().toLowerCase().endsWith(".htm") || f.getName().toLowerCase().endsWith(".html");
        }
    
        public String getDescription() 
        {
            return "HTML files";
        }
    }
    
    public class folderFilter extends javax.swing.filechooser.FileFilter
    {
        public boolean accept(java.io.File f)
        {
            return f.isDirectory();
        }
        
        public String getDescription()
        {
            return "Folders";
        }
        
    }
    
    public class snoCADxFileFilter extends javax.swing.filechooser.FileFilter 
    {
        public boolean accept(java.io.File f) 
        {
            return f.isDirectory() || f.getName().toLowerCase().endsWith(".snx");
        }
    
        public String getDescription() 
        {
            return "snoCAD-x files";
        }
    }
    
     public class pdfFilter extends javax.swing.filechooser.FileFilter 
    {
        public boolean accept(java.io.File f) 
        {
            return f.isDirectory() || f.getName().toLowerCase().endsWith(".pdf");
        }
    
        public String getDescription() 
        {
            return "PDF files";
        }
    }
     
      public class pngFilter extends javax.swing.filechooser.FileFilter 
    {
        public boolean accept(java.io.File f) 
        {
            return f.isDirectory() || f.getName().toLowerCase().endsWith(".png");
        }
    
        public String getDescription() 
        {
            return "Portable Network Graphic images";
        }
    }
    
    

    public class importFilter extends javax.swing.filechooser.FileFilter 
    {
        public boolean accept(java.io.File f) 
        {
            return f.isDirectory() || f.getName().toLowerCase().endsWith(".sno") || f.getName().toLowerCase().endsWith(".bcr");
        }
    
        public String getDescription() 
        {
            return "snoCAD2 files and Boardcrafter Design files";
        }
    }
    
    public class dxfFilter extends javax.swing.filechooser.FileFilter 
    {
        public boolean accept(java.io.File f) 
        {
            return f.isDirectory() || f.getName().toLowerCase().endsWith(".dxf");
        }
    
        public String getDescription() 
        {
            return "DXF Drawing eXchange Format";
        }
    }
    
     public class graphicsFilter extends javax.swing.filechooser.FileFilter 
    {
        public boolean accept(java.io.File f) 
        {
            return f.isDirectory() || 
                    f.getName().toLowerCase().endsWith(".jpg") ||
                    f.getName().toLowerCase().endsWith(".gif") ||
                    f.getName().toLowerCase().endsWith(".png") ;
                   
        }
    
        public String getDescription() 
        {
            return "Image Files";
        }
    }



    
}
