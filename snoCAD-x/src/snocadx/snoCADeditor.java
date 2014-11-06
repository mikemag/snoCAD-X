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
 * snoCADeditor.java
 *
 * Created on 22 March 2007, 15:56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package snocadx;

import java.io.IOException;

/**
 *
 * @author dgraf
 */
public class snoCADeditor extends javax.swing.JInternalFrame {
    
    /** Creates a new instance of snoCADeditor */
    public snoCADeditor(String title, int x, int y, int width, int height, int boardType) {
        
          super(title,
          true, //resizable
          true, //closable
          true, //maximizable
          true);//iconifiable
          
         
       //   m_toolbarOffset = 30;
       //   m_statsPanelHeight = 10;
          m_zoomFactor = 0.2;
          
          m_board = new snoCADboard(boardType);
          m_board.setBoardName(title);
         
          setBounds(x, y, width, height);

          setBackground(java.awt.Color.BLACK);
          setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.LIGHT_GRAY, 3));
          setTitle(m_board.getBoardName());
          setLocation(x,y);
          setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("images/board.gif")));
          
          
          m_bp = new snoCADboardDisplay();
          m_bp.setBounds(0,0, getWidth(), getHeight());
          m_bp.setBackground(java.awt.Color.black);
          m_bp.setSnowboard(m_board);
          m_bp.setDoubleBuffered(true);
         
          this.getContentPane().add(m_bp);

          addToolbar();
          
          m_bp.setStatsPanel(m_statsPanel);
          
          m_statsPanel.setBoard(m_board);
          m_statsPanel.setContainingFrame(this);
          m_statsPanel.setBoardDisplay(m_bp);
          m_bp.setGraphicsPanel(m_graphicsPanel);
          m_menu.setBoardPanel(m_bp);
          m_menu.setEditor(this);
          m_bp.repaint();
          m_bp.updateGraphicsPanel();
          
        addMouseWheelListener(new java.awt.event.MouseWheelListener() 
        { 
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent e) 
            {
                double change =  m_board.getScaleFactor() + (e.getWheelRotation() * 0.05) ;
                if (change <= 0.3) change = 0.3;
                m_bp.setDrawMaxMin(false);
                m_board.setScaleFactor(change);
                repaint();
            }
        });
        
       
        setVisible(true);
       

    }
   
    public void zoomInButtonActionPerformed(java.awt.event.ActionEvent evt) 
    {
        double change =  m_board.getScaleFactor() - m_zoomFactor ;
        if (change <= 0.3) change = 0.3;
        m_board.setScaleFactor(change);
        m_bp.repaint();
    }
    
    public void zoomOutButtonActionPerformed(java.awt.event.ActionEvent evt) 
    {
        double change =  m_board.getScaleFactor() + m_zoomFactor ;
        if (change <= 0.3) change = 0.3;
        m_board.setScaleFactor(change);
        m_bp.repaint();
    }
    
    public void zoomExtentsButtonActionPerformed(java.awt.event.ActionEvent evt) 
    {
        // Scale factor is in mm per pixel.
        // How long is the board ?
        
        double boardLength = 1.1 * m_board.getLength(); // plus 50mm either side
        
        // How many pixels are available
        int ourWidth = m_bp.getWidth();
        
        double scaleFactor = boardLength / ourWidth;
        
        m_board.setScaleFactor(scaleFactor);
        m_bp.centreDrawing();
        m_bp.repaint();
    }
    
     public void zoomRailsButtonActionPerformed(java.awt.event.ActionEvent evt) 
    {
        // Scale factor is in mm per pixel.
        // How long is the board ?
        
        double waistWidth = 1.3 * m_board.getWaistWidth(); // plus 50mm either side
        
        // How many pixels are available
        int ourHeight = m_bp.getHeight();
        
        double scaleFactor = waistWidth / ourHeight;
        
        m_board.setScaleFactor(scaleFactor);
        m_bp.centreDrawing(0 -  m_board.getSidecutBias(), 0);
        m_bp.repaint();
    }
    
    public void zoomNoseButtonActionPerformed(java.awt.event.ActionEvent evt) 
    {
        // Scale factor is in mm per pixel.
        // How long is the board ?
        
        double noseLength = 1.5 * m_board.getNoseLength(); // plus 50mm either side
        double noseWidth = 1.5 * m_board.getNoseWidth(); // plus 50mm either side
        
        // How many pixels are available
        int ourHeight = m_bp.getHeight();
        int ourWidth = m_bp.getWidth();
        
        double scaleFactorLength = noseLength / ourWidth;
        double scaleFactorWidth = noseWidth / ourHeight;
        
        if (scaleFactorLength < scaleFactorWidth) 
        {
            m_board.setScaleFactor(scaleFactorWidth);
            m_bp.centreDrawing(( m_board.getNoseLength() + (m_board.getRunningLength() / 2) - 100) / scaleFactorWidth, 0 );
        }
        else
        {
            m_board.setScaleFactor(scaleFactorLength);
            m_bp.centreDrawing(( m_board.getNoseLength() + (m_board.getRunningLength() / 2) - 100) / scaleFactorLength, 0 );
            
        }

        m_bp.repaint();
    }
    
    public void zoomTailButtonActionPerformed(java.awt.event.ActionEvent evt) 
    {
        // Scale factor is in mm per pixel.
        // How long is the board ?
        
        double tailLength = 1.5 * m_board.getTailLength(); // plus 50mm either side
        double tailWidth = 1.5 * m_board.getTailWidth(); // plus 50mm either side
        
        // How many pixels are available
        int ourHeight = m_bp.getHeight();
        int ourWidth = m_bp.getWidth();
        
        double scaleFactorLength = tailLength / ourWidth;
        double scaleFactorWidth = tailWidth / ourHeight;
        
        if (scaleFactorLength < scaleFactorWidth) 
        {
            m_board.setScaleFactor(scaleFactorWidth);
            m_bp.centreDrawing(0 - ( m_board.getTailLength() + (m_board.getRunningLength() / 2) - 100) / scaleFactorWidth, 0 );
        }
        else
        {
            m_board.setScaleFactor(scaleFactorLength);
            m_bp.centreDrawing(0 - ( m_board.getTailLength() + (m_board.getRunningLength() / 2) - 100) / scaleFactorLength, 0 );
            
        }

        m_bp.repaint();
    }
    
    public void zoomFrontPackButtonActionPerformed(java.awt.event.ActionEvent evt) 
    {

        double waistWidth = 1.1 * 160; 
        
        // How many pixels are available
        int ourHeight = m_bp.getHeight();
        int ourWidth = m_bp.getWidth();
        

        double scaleFactor = waistWidth / ourHeight;
        
        m_board.setScaleFactor(scaleFactor);
        m_bp.centreDrawing(( m_board.getStanceWidth() / 2 - m_board.getStanceSetback())/ scaleFactor, 0);
   

        m_bp.repaint();
    }
    
    public void zoomRearPackButtonActionPerformed(java.awt.event.ActionEvent evt) 
    {

        double waistWidth = 1.1 * 160; 
        
        // How many pixels are available
        int ourHeight = m_bp.getHeight();
        int ourWidth = m_bp.getWidth();

        double scaleFactor = waistWidth / ourHeight;
        
        m_board.setScaleFactor(scaleFactor);
        m_bp.centreDrawing(0 - ( m_board.getStanceWidth() / 2 - m_board.getStanceSetback())/ scaleFactor, 0);
   

        m_bp.repaint();
    }
    
    public void colourChooserButtonActionPerformed(java.awt.event.ActionEvent evt)
    {
        java.awt.Color colour = m_palette.showDialog(this, "choose colour", m_board.getBoardFillColour());

        if (colour != null) m_board.setBoardFillColour(colour);

        m_bp.repaint();

    }
    
    public void statsPanelToggleButtonActionPerformed(java.awt.event.ActionEvent evt)
    {
        m_statsPanel.updateStats();
        m_statsPanel.setVisible(!m_statsPanel.isVisible());
        
        m_bp.repaint();

    }
    
    private void addToolbar()
    {
        m_menu = new snoCADeditorMenu();
        javax.swing.JToolBar boardEditorToolbar = new javax.swing.JToolBar();
        javax.swing.JButton zoomInButton = new javax.swing.JButton();
        javax.swing.JButton zoomOutButton = new javax.swing.JButton();
        javax.swing.JButton zoomExtentsButton = new javax.swing.JButton();
        javax.swing.JButton zoomNoseButton = new javax.swing.JButton();
        javax.swing.JButton zoomFrontPackButton = new javax.swing.JButton();
        javax.swing.JButton zoomRailsButton = new javax.swing.JButton();
        javax.swing.JButton zoomRearPackButton = new javax.swing.JButton();
        javax.swing.JButton zoomTailButton = new javax.swing.JButton();
                
        javax.swing.JButton colourChooserButton = new javax.swing.JButton();
        javax.swing.JButton statsPanelToggle = new javax.swing.JButton();

        boardEditorToolbar.setFloatable(false);
        
        java.awt.Color toolBarColor = new java.awt.Color(212,208,200);
        
        boardEditorToolbar.setBackground(toolBarColor);
        boardEditorToolbar.setFloatable(false);
        boardEditorToolbar.setBorder(javax.swing.BorderFactory.createEtchedBorder());

     
        //----------------------------------------------------------------------
        zoomInButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/ZoomIn24.gif")));
        zoomInButton.setBorder(null);
        zoomInButton.setFocusable(false);
        zoomInButton.setToolTipText("Zoom In");
        
        
        zoomInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomInButtonActionPerformed(evt);
            }
        });
        
        boardEditorToolbar.add(zoomInButton);
        //----------------------------------------------------------------------
        
        //----------------------------------------------------------------------
        zoomOutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/ZoomOut24.gif")));
        zoomOutButton.setBorder(null);
        zoomOutButton.setFocusable(false);
        zoomOutButton.setToolTipText("Zoom Out");
        
        
        zoomOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomOutButtonActionPerformed(evt);
            }
        });
        
        boardEditorToolbar.add(zoomOutButton);
        //----------------------------------------------------------------------
        
        //----------------------------------------------------------------------
        zoomExtentsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("images/zoom_extents.png")));
        zoomExtentsButton.setBorder(null);
        zoomExtentsButton.setFocusable(false);
        zoomExtentsButton.setToolTipText("Zoom Extents");
        
        
        zoomExtentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomExtentsButtonActionPerformed(evt);
            }
        });
        
        boardEditorToolbar.add(zoomExtentsButton);
        //----------------------------------------------------------------------
        
       //----------------------------------------------------------------------
        zoomNoseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("images/zoom_nose.png")));
        zoomNoseButton.setBorder(null);
        zoomNoseButton.setFocusable(false);
        zoomNoseButton.setToolTipText("Zoom Nose");
        
        
        zoomNoseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomNoseButtonActionPerformed(evt);
            }
        });
        
        boardEditorToolbar.add(zoomNoseButton);
        //----------------------------------------------------------------------
        
        //----------------------------------------------------------------------
        zoomFrontPackButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("images/zoom_front_pack.png")));
        zoomFrontPackButton.setBorder(null);
        zoomFrontPackButton.setFocusable(false);
        zoomFrontPackButton.setToolTipText("Zoom Front Inserts");
        
        
        zoomFrontPackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomFrontPackButtonActionPerformed(evt);
            }
        });
        
        boardEditorToolbar.add(zoomFrontPackButton);
        //----------------------------------------------------------------------
        
        //----------------------------------------------------------------------
        zoomRailsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("images/zoom_rails_centre.png")));
        zoomRailsButton.setBorder(null);
        zoomRailsButton.setFocusable(false);
        zoomRailsButton.setToolTipText("Zoom Rails Centre");
        
        
        zoomRailsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomRailsButtonActionPerformed(evt);
            }
        });
        
        boardEditorToolbar.add(zoomRailsButton);
        //----------------------------------------------------------------------
        
        //----------------------------------------------------------------------
        zoomRearPackButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("images/zoom_rear_pack.png")));
        zoomRearPackButton.setBorder(null);
        zoomRearPackButton.setFocusable(false);
        zoomRearPackButton.setToolTipText("Zoom Rear Inserts");
        
        
        zoomRearPackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomRearPackButtonActionPerformed(evt);
            }
        });
        
        boardEditorToolbar.add(zoomRearPackButton);
        //----------------------------------------------------------------------
        
        //----------------------------------------------------------------------
        zoomTailButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("images/zoom_tail.png")));
        zoomTailButton.setBorder(null);
        zoomTailButton.setFocusable(false);
        zoomTailButton.setToolTipText("Zoom Tail");
        
        
        zoomTailButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomTailButtonActionPerformed(evt);
            }
        });
        
        boardEditorToolbar.add(zoomTailButton);
        
        //----------------------------------------------------------------------
        
        boardEditorToolbar.addSeparator();
        
        colourChooserButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("images/colorSwatches_24.png")));
        colourChooserButton.setBorder(null);
        colourChooserButton.setFocusable(false);
        colourChooserButton.setToolTipText("Choose Board Colour");
        
        colourChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colourChooserButtonActionPerformed(evt);
            }
        });
        
        boardEditorToolbar.add(colourChooserButton);
        
        boardEditorToolbar.addSeparator();
        
        statsPanelToggle.setIcon(new javax.swing.ImageIcon(getClass().getResource("images/MeasureIcon.gif")));
        statsPanelToggle.setBorder(null);
        statsPanelToggle.setFocusable(false);
        statsPanelToggle.setToolTipText("Board Dimensions");
        
        statsPanelToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statsPanelToggleButtonActionPerformed(evt);
            }
        });
        
       // boardEditorToolbar.addSeparator();
        
        boardEditorToolbar.add(statsPanelToggle);
        
        m_palette = new javax.swing.JColorChooser(m_board.getBoardFillColour());
        m_palette.setPreviewPanel(new javax.swing.JPanel());

        this.getContentPane().add(boardEditorToolbar, java.awt.BorderLayout.PAGE_START);
        this.setJMenuBar(m_menu.getMenu());
  
        m_statsPanel = new snoCADstatsPanel();
        m_statsPanel.setVisible(false);

        this.getContentPane().add(m_statsPanel, java.awt.BorderLayout.WEST);
        
        m_graphicsPanel = new snoCADgraphicsPanel(m_bp);
        m_graphicsPanel.setVisible(false);
        
        this.getContentPane().add(m_graphicsPanel, java.awt.BorderLayout.EAST);
   
    }
    
    public boolean loadBoard(java.io.File incomingSnoCADfile)
    {
        if (incomingSnoCADfile != null)
        {
            snoCADboard openedBoard = new snoCADboard(incomingSnoCADfile);
            
            if (openedBoard == null) return false;
            
            
            m_board = openedBoard;
            this.setTitle(m_board.getBoardName());
            
            try 
            {
                m_menu.setLastUsedPath(incomingSnoCADfile.getCanonicalPath());
                m_menu.setSnxFileName(incomingSnoCADfile.getCanonicalPath());
            } 
            catch (IOException ex) 
            {
                ex.printStackTrace();
            }
        }
        m_bp.setSnowboard(m_board);
        m_bp.updateGraphicsPanel();
        return true;
    }
    
    public boolean importBoard(java.io.File incomingFile, String extension)
    {
        snoCADboard openedBoard = new snoCADboard();
        
        if (extension == "sno")
        {
            openedBoard.importFromSNO(incomingFile);
        }
        else
        {
            openedBoard.importFromBCR(incomingFile);
        }
        
        try 
        {
            m_menu.setLastUsedPath(incomingFile.getCanonicalPath());
        } 
        catch (IOException ex) 
        {
            ex.printStackTrace();
        }
        
    
        this.setTitle(m_board.getBoardName());
        m_board = openedBoard;
        m_bp.setSnowboard(m_board);
        return true;
    }

    private snoCADboard m_board;
    private snoCADboardDisplay m_bp;
    
    public snoCADstatsPanel getStatsPanel() { return m_statsPanel;}
    
    private int m_toolbarOffset;
    private javax.swing.JColorChooser m_palette;
    public snoCADstatsPanel m_statsPanel;
    public snoCADgraphicsPanel m_graphicsPanel;
    private double m_zoomFactor;
    private int m_statsPanelHeight;
    private snoCADeditorMenu m_menu;
    private java.awt.event.MouseWheelListener m_wheelListener;
    
   
    
}
