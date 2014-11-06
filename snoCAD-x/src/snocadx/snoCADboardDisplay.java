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
 * boardPanel.java
 *
 * Created on 22 March 2007, 17:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package snocadx;

import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

/**
 *
 * @author dgraf
 */

public class snoCADboardDisplay extends javax.swing.JPanel {
    
    /** Creates a new instance of boardPanel */
    public snoCADboardDisplay() {
        // Setup mouse handlers

        addMouseMotionListener(new MouseMotionHandler());
        
        addMouseListener(new java.awt.event.MouseListener() {
            public void mouseClicked(MouseEvent e) 
            {
                
            }
            public void mouseEntered(MouseEvent e) {
                showHandles(e);  
            }
            public void mouseExited(MouseEvent e) {
                eraseHandles();
            }
            public void mousePressed(MouseEvent e) 
            {
                String modifiers = e.getMouseModifiersText(e.getModifiers());
                
                m_mousePositionX = e.getX();
                m_mousePositionY = e.getY();
                
                checkHandles();

            }
            public void mouseReleased(MouseEvent e) {
                releaseHandles();
            }
        });
        
        // Setup drawing handles
        
        m_noseTipHandle = new snoCADgeometryManipulator("nose length");
        m_tailTipHandle = new snoCADgeometryManipulator("tail length");
        m_toeSideCutHandle = new snoCADgeometryManipulator("sidecut radius / bias");
        m_heelSideCutHandle = new snoCADgeometryManipulator("sidecut radius / bias");
        m_noseWidthHandleToe = new snoCADgeometryManipulator("nose width");
        m_noseWidthHandleHeel = new snoCADgeometryManipulator("nose width");
        m_tailWidthHandleToe = new snoCADgeometryManipulator("tail width");
        m_tailWidthHandleHeel = new snoCADgeometryManipulator("tail width");
        
        m_noseRunningLengthHandle = new snoCADgeometryManipulator("running length");
        m_tailRunningLengthHandle = new snoCADgeometryManipulator("running length");
        
        m_frontPackStanceWidthHandle = new snoCADgeometryManipulator("stance width");
        m_rearPackStanceWidthHandle = new snoCADgeometryManipulator("stance width");
        m_stanceOffsetHandle = new snoCADgeometryManipulator("stance setback");
        
        m_noseCubicHandle_1 = new snoCADgeometryManipulator("nose curve");
        m_noseCubicHandle_2 = new snoCADgeometryManipulator("nose curve");
        m_noseCubicHandle_3 = new snoCADgeometryManipulator("nose curve");
        m_noseCubicHandle_4 = new snoCADgeometryManipulator("nose curve");
        
        m_tailCubicHandle_1 = new snoCADgeometryManipulator("tail curve");
        m_tailCubicHandle_2 = new snoCADgeometryManipulator("tail curve");
        m_tailCubicHandle_3 = new snoCADgeometryManipulator("tail curve");
        m_tailCubicHandle_4 = new snoCADgeometryManipulator("tail curve");
        
        m_camberHandle = new snoCADgeometryManipulator("camber");
        m_camberSetbackHandle = new snoCADgeometryManipulator("camber setback");
        m_noseRadiusHandle = new snoCADgeometryManipulator("nose tip radius");
        m_tailRadiusHandle = new snoCADgeometryManipulator("tail tip radius");
        
        m_frontPackRowCountHandle = new snoCADgeometryManipulator("row count");
        m_frontPackInsertSpacingHandle = new snoCADgeometryManipulator("insert spacing");
        m_rearPackRowCountHandle = new snoCADgeometryManipulator("row count");
        m_rearPackInsertSpacingHandle = new snoCADgeometryManipulator("insert spacing");
        
        m_noseThicknessHandle = new snoCADgeometryManipulator("nose thickness") ;
        m_frontPackOutboardThicknessHandle = new snoCADgeometryManipulator("nose thickness 1") ;
        m_frontPackInboardThicknessHandle = new snoCADgeometryManipulator("nose thickness 2") ;
        m_midPointThicknessHandle = new snoCADgeometryManipulator("midpoint thickness");
        m_rearPackInboardThicknessHandle = new snoCADgeometryManipulator("tail thickness 2") ;
        m_rearPackOutboardThicknessHandle = new snoCADgeometryManipulator("tail thickness 1") ;
        m_tailThicknessHandle = new snoCADgeometryManipulator("tail thickness") ;
        
        m_frontPackRowCountHandle.setSensorBounds(10);
        m_frontPackInsertSpacingHandle.setSensorBounds(10);
        m_rearPackRowCountHandle.setSensorBounds(10);
        m_rearPackInsertSpacingHandle.setSensorBounds(10);
        
        m_boardBoundsWidth = 0;
        m_boardBoundsHeight = 0;
        m_boardBoundsX = 0;
        m_boardBoundsY = 0;
        
        m_renderBoard = true;
        m_highlights = true;
        m_showGrid = true;
        m_showAxes = true;
        m_measuring = false;
        
        m_editingMode = snoCADutilities.EDIT_BOARD;
        
        m_offscreenImage = new java.awt.image.BufferedImage(640, 480, java.awt.image.BufferedImage.TYPE_INT_RGB);
        m_Bg2d = (java.awt.Graphics2D)m_offscreenImage.createGraphics();
        
        
    }
    
    private void checkHandles() {
        if (!m_graphicsPanel.isVisible()) 
        {
            m_noseTipHandle.setInUse(m_noseTipHandle.handleHeld(m_mousePositionX, m_mousePositionY));
            m_tailTipHandle.setInUse(m_tailTipHandle.handleHeld(m_mousePositionX, m_mousePositionY));
            
            m_noseWidthHandleToe.setInUse(m_noseWidthHandleToe.handleHeld(m_mousePositionX, m_mousePositionY));
            m_noseWidthHandleHeel.setInUse(m_noseWidthHandleHeel.handleHeld(m_mousePositionX, m_mousePositionY));
            m_tailWidthHandleToe.setInUse(m_tailWidthHandleToe.handleHeld(m_mousePositionX, m_mousePositionY));
            m_tailWidthHandleHeel.setInUse(m_tailWidthHandleHeel.handleHeld(m_mousePositionX, m_mousePositionY));
            
            m_noseRunningLengthHandle.setInUse(m_noseRunningLengthHandle.handleHeld(m_mousePositionX, m_mousePositionY));
            m_tailRunningLengthHandle.setInUse(m_tailRunningLengthHandle.handleHeld(m_mousePositionX, m_mousePositionY));
            
            m_frontPackStanceWidthHandle.setInUse(m_frontPackStanceWidthHandle.handleHeld(m_mousePositionX, m_mousePositionY));
            m_rearPackStanceWidthHandle.setInUse(m_rearPackStanceWidthHandle.handleHeld(m_mousePositionX, m_mousePositionY));
            m_stanceOffsetHandle.setInUse(m_stanceOffsetHandle.handleHeld(m_mousePositionX, m_mousePositionY));
            
            m_noseCubicHandle_1.setInUse(m_noseCubicHandle_1.handleHeld(m_mousePositionX, m_mousePositionY));
            m_noseCubicHandle_2.setInUse(m_noseCubicHandle_2.handleHeld(m_mousePositionX, m_mousePositionY));
            m_noseCubicHandle_3.setInUse(m_noseCubicHandle_3.handleHeld(m_mousePositionX, m_mousePositionY));
            m_noseCubicHandle_4.setInUse(m_noseCubicHandle_4.handleHeld(m_mousePositionX, m_mousePositionY));
            
            m_tailCubicHandle_1.setInUse(m_tailCubicHandle_1.handleHeld(m_mousePositionX, m_mousePositionY));
            m_tailCubicHandle_2.setInUse(m_tailCubicHandle_2.handleHeld(m_mousePositionX, m_mousePositionY));
            m_tailCubicHandle_3.setInUse(m_tailCubicHandle_3.handleHeld(m_mousePositionX, m_mousePositionY));
            m_tailCubicHandle_4.setInUse(m_tailCubicHandle_4.handleHeld(m_mousePositionX, m_mousePositionY));
            
            m_frontPackRowCountHandle.setInUse(m_frontPackRowCountHandle.handleHeld(m_mousePositionX, m_mousePositionY));
            m_frontPackInsertSpacingHandle.setInUse(m_frontPackInsertSpacingHandle.handleHeld(m_mousePositionX, m_mousePositionY));
            m_rearPackRowCountHandle.setInUse(m_rearPackRowCountHandle.handleHeld(m_mousePositionX, m_mousePositionY));
            m_rearPackInsertSpacingHandle.setInUse(m_rearPackInsertSpacingHandle.handleHeld(m_mousePositionX, m_mousePositionY));
            
            m_camberHandle.setInUse(m_camberHandle.handleHeld(m_mousePositionX, m_mousePositionY));
            m_camberSetbackHandle.setInUse(m_camberSetbackHandle.handleHeld(m_mousePositionX, m_mousePositionY));
            m_noseRadiusHandle.setInUse(m_noseRadiusHandle.handleHeld(m_mousePositionX, m_mousePositionY));
            m_tailRadiusHandle.setInUse(m_tailRadiusHandle.handleHeld(m_mousePositionX, m_mousePositionY));
            
            m_noseThicknessHandle.setInUse(m_noseThicknessHandle.handleHeld(m_mousePositionX, m_mousePositionY));
            m_frontPackOutboardThicknessHandle.setInUse(m_frontPackOutboardThicknessHandle.handleHeld(m_mousePositionX, m_mousePositionY));
            m_frontPackInboardThicknessHandle.setInUse(m_frontPackInboardThicknessHandle.handleHeld(m_mousePositionX, m_mousePositionY));
            m_midPointThicknessHandle.setInUse(m_midPointThicknessHandle.handleHeld(m_mousePositionX, m_mousePositionY));
            m_rearPackInboardThicknessHandle.setInUse(m_rearPackInboardThicknessHandle.handleHeld(m_mousePositionX, m_mousePositionY));
            m_rearPackOutboardThicknessHandle.setInUse(m_rearPackOutboardThicknessHandle.handleHeld(m_mousePositionX, m_mousePositionY));
            m_tailThicknessHandle.setInUse(m_tailThicknessHandle.handleHeld(m_mousePositionX, m_mousePositionY));
            
            if (m_board.getRailType() == snoCADutilities.QUADRATIC)
            {
                m_toeSideCutHandle.setInUse(m_toeSideCutHandle.handleHeld(m_mousePositionX, m_mousePositionY));
                m_heelSideCutHandle.setInUse(m_heelSideCutHandle.handleHeld(m_mousePositionX, m_mousePositionY));
            }
            if ((m_board.getRailType() == snoCADutilities.MULTIPOINT_FREE) && (m_board.getRailGeometry().getSize() > 0))
            {
                for (int i = 0; i < m_board.getRailGeometry().getSize(); i++)
                {
                    m_board.getRailGeometry().getEntry(i).getHandle().setInUse(m_board.getRailGeometry().getEntry(i).getHandle().handleHeld(m_mousePositionX, m_mousePositionY));
                }
            }
            
        } else {
            if (m_board.getGraphics().getSize() > 0) {
                for (int i = 0; i < m_board.getGraphics().getSize(); i++) {
                    m_board.getGraphics().getEntry(i).getDragger().setInUse(m_board.getGraphics().getEntry(i).getDragger().handleHeld(m_mousePositionX, m_mousePositionY));
                    m_board.getGraphics().getEntry(i).getStretcher().setInUse(m_board.getGraphics().getEntry(i).getStretcher().handleHeld(m_mousePositionX, m_mousePositionY));
                    m_board.getGraphics().getEntry(i).getRotator().setInUse(m_board.getGraphics().getEntry(i).getRotator().handleHeld(m_mousePositionX, m_mousePositionY));
                    
                    if (m_board.getGraphics().getEntry(i).getDragger().handleHeld(m_mousePositionX, m_mousePositionY)) {
                        m_graphicsPanel.getTree().setSelectionPath(m_board.getGraphics().getEntry(i).getDragger().getTreePath());
                        m_graphicsPanel.getTree().scrollPathToVisible(m_board.getGraphics().getEntry(i).getDragger().getTreePath());
                        m_graphicsPanel.getTree().repaint();
                    }
                    
                    if (m_board.getGraphics().getEntry(i).getStretcher().handleHeld(m_mousePositionX, m_mousePositionY)) {
                        m_graphicsPanel.getTree().setSelectionPath(m_board.getGraphics().getEntry(i).getStretcher().getTreePath());
                        m_graphicsPanel.getTree().scrollPathToVisible(m_board.getGraphics().getEntry(i).getStretcher().getTreePath());
                        m_graphicsPanel.getTree().repaint();
                    }
                }
                
            }
        }
    }
    private void releaseHandles() {
        m_noseTipHandle.setInUse(false);
        m_tailTipHandle.setInUse(false);
       
        m_noseWidthHandleToe.setInUse(false);
        m_noseWidthHandleHeel.setInUse(false);
        m_tailWidthHandleToe.setInUse(false);
        m_tailWidthHandleHeel.setInUse(false);
        
        m_noseRunningLengthHandle.setInUse(false);
        m_tailRunningLengthHandle.setInUse(false);
        
        m_frontPackStanceWidthHandle.setInUse(false);
        m_rearPackStanceWidthHandle.setInUse(false);
        m_stanceOffsetHandle.setInUse(false);
        
        m_noseCubicHandle_1.setInUse(false);
        m_noseCubicHandle_2.setInUse(false);
        m_noseCubicHandle_3.setInUse(false);
        m_noseCubicHandle_4.setInUse(false);
        
        m_tailCubicHandle_1.setInUse(false);
        m_tailCubicHandle_2.setInUse(false);
        m_tailCubicHandle_3.setInUse(false);
        m_tailCubicHandle_4.setInUse(false);
        
        m_frontPackRowCountHandle.setInUse(false);
        m_frontPackInsertSpacingHandle.setInUse(false);
        m_rearPackRowCountHandle.setInUse(false);
        m_rearPackInsertSpacingHandle.setInUse(false);
        
        m_camberHandle.setInUse(false);
        m_camberSetbackHandle.setInUse(false);
        m_noseRadiusHandle.setInUse(false);
        m_tailRadiusHandle.setInUse(false);
        
        m_noseThicknessHandle.setInUse(false);
        m_frontPackOutboardThicknessHandle.setInUse(false);
        m_frontPackInboardThicknessHandle.setInUse(false);
        m_midPointThicknessHandle.setInUse(false);
        m_rearPackInboardThicknessHandle.setInUse(false);
        m_rearPackOutboardThicknessHandle.setInUse(false);
        m_tailThicknessHandle.setInUse(false);
        
        if (m_board.getRailType() == snoCADutilities.QUADRATIC)
        {
            m_toeSideCutHandle.setInUse(false);
            m_heelSideCutHandle.setInUse(false);
        }
        
        if ((m_board.getRailType() == snoCADutilities.MULTIPOINT_FREE) && (m_board.getRailGeometry().getSize() > 0))
        {
            for (int i = 0; i < m_board.getRailGeometry().getSize(); i++)
            {
                m_board.getRailGeometry().getEntry(i).getHandle().setInUse(false);
            }
         }
        
        if (m_board.getGraphics().getSize() > 0) 
        {
            for (int i = 0; i < m_board.getGraphics().getSize(); i++) 
            {
                m_board.getGraphics().getEntry(i).getDragger().setInUse(false);
                m_board.getGraphics().getEntry(i).getStretcher().setInUse(false);
                m_board.getGraphics().getEntry(i).getRotator().setInUse(false);
            }
            
        }
    }
    
    public void hideHandles() 
    {
        m_noseTipHandle.setVisible(false);
        m_tailTipHandle.setVisible(false);
     
        m_noseWidthHandleToe.setVisible(false);
        m_noseWidthHandleHeel.setVisible(false);
        m_tailWidthHandleToe.setVisible(false);
        m_tailWidthHandleHeel.setVisible(false);
        
        m_noseRunningLengthHandle.setVisible(false);
        m_tailRunningLengthHandle.setVisible(false);
        
        m_frontPackStanceWidthHandle.setVisible(false);
        m_rearPackStanceWidthHandle.setVisible(false);
        m_stanceOffsetHandle.setVisible(false);
        
        m_noseCubicHandle_1.setVisible(false);
        m_noseCubicHandle_2.setVisible(false);
        m_noseCubicHandle_3.setVisible(false);
        m_noseCubicHandle_4.setVisible(false);
        
        m_tailCubicHandle_1.setVisible(false);
        m_tailCubicHandle_2.setVisible(false);
        m_tailCubicHandle_3.setVisible(false);
        m_tailCubicHandle_4.setVisible(false);
        
        m_frontPackRowCountHandle.setVisible(false);
        m_frontPackInsertSpacingHandle.setVisible(false);
        m_rearPackRowCountHandle.setVisible(false);
        m_rearPackInsertSpacingHandle.setVisible(false);
        
        m_camberHandle.setVisible(false);
        m_camberSetbackHandle.setVisible(false);
        m_noseRadiusHandle.setVisible(false);
        m_tailRadiusHandle.setVisible(false);
        
        m_noseThicknessHandle.setVisible(false);
        m_frontPackOutboardThicknessHandle.setVisible(false);
        m_frontPackInboardThicknessHandle.setVisible(false);
        m_midPointThicknessHandle.setVisible(false);
        m_rearPackInboardThicknessHandle.setVisible(false);
        m_rearPackOutboardThicknessHandle.setVisible(false);
        m_tailThicknessHandle.setVisible(false);
        
        m_drawMaxMin = false;

        m_toeSideCutHandle.setVisible(false);
        m_heelSideCutHandle.setVisible(false);

        
        if ((m_board.getRailType() == snoCADutilities.MULTIPOINT_FREE) && (m_board.getRailGeometry().getSize() > 0))
        {
            for (int i = 0; i < m_board.getRailGeometry().getSize(); i++)
            {
                m_board.getRailGeometry().getEntry(i).getHandle().setVisible(false);
            }
         }
        
        if (!m_graphicsPanel.isVisible()) {
            if (m_board.getGraphics().getSize() > 0) {
                for (int i = 0; i < m_board.getGraphics().getSize(); i++) {
                    m_board.getGraphics().getEntry(i).getDragger().setVisible(false);
                    m_board.getGraphics().getEntry(i).getStretcher().setVisible(false);
                    m_board.getGraphics().getEntry(i).getRotator().setVisible(false);
                }
                
            }
        }
        
        repaint();
    }
    
    public void eraseHandles() {
        
        m_drawMaxMin = false;
        
        m_noseTipHandle.hideHandle();
        m_tailTipHandle.hideHandle();
       
        m_noseWidthHandleToe.hideHandle();
        m_noseWidthHandleHeel.hideHandle();
        m_tailWidthHandleToe.hideHandle();
        m_tailWidthHandleHeel.hideHandle();
        
        m_noseRunningLengthHandle.hideHandle();
        m_tailRunningLengthHandle.hideHandle();
        
        m_frontPackStanceWidthHandle.hideHandle();
        m_rearPackStanceWidthHandle.hideHandle();
        m_stanceOffsetHandle.hideHandle();
        
        m_noseCubicHandle_1.hideHandle();
        m_noseCubicHandle_2.hideHandle();
        m_noseCubicHandle_3.hideHandle();
        m_noseCubicHandle_4.hideHandle();
        
        m_tailCubicHandle_1.hideHandle();
        m_tailCubicHandle_2.hideHandle();
        m_tailCubicHandle_3.hideHandle();
        m_tailCubicHandle_4.hideHandle();
        
        m_frontPackRowCountHandle.hideHandle();
        m_frontPackInsertSpacingHandle.hideHandle();
        m_rearPackRowCountHandle.hideHandle();
        m_rearPackInsertSpacingHandle.hideHandle();
        
        m_camberHandle.hideHandle();
        m_camberSetbackHandle.hideHandle();
        m_noseRadiusHandle.hideHandle();
        m_tailRadiusHandle.hideHandle();
        
        m_noseThicknessHandle.hideHandle();
        m_frontPackOutboardThicknessHandle.hideHandle();
        m_frontPackInboardThicknessHandle.hideHandle();
        m_midPointThicknessHandle.hideHandle();
        m_rearPackInboardThicknessHandle.hideHandle();
        m_rearPackOutboardThicknessHandle.hideHandle();
        m_tailThicknessHandle.hideHandle();

        m_toeSideCutHandle.hideHandle();
        m_heelSideCutHandle.hideHandle();

        if ((m_board.getRailType() == snoCADutilities.MULTIPOINT_FREE) && (m_board.getRailGeometry().getSize() > 0))
        {
            for (int i = 0; i < m_board.getRailGeometry().getSize(); i++)
            {
                m_board.getRailGeometry().getEntry(i).getHandle().hideHandle();
            }
         }
        
        if (m_board.getGraphics().getSize() > 0) {
            for (int i = 0; i < m_board.getGraphics().getSize(); i++) {
                m_board.getGraphics().getEntry(i).getDragger().hideHandle();
                m_board.getGraphics().getEntry(i).getStretcher().hideHandle();
                m_board.getGraphics().getEntry(i).getRotator().hideHandle();
            }
            
        }
        
        repaint();
    }
    
    private void showHandles(java.awt.event.MouseEvent e) 
    {
        if (!m_graphicsPanel.isVisible()) {
            m_noseTipHandle.setVisible(m_noseTipHandle.sensorActivated(e.getX(), e.getY()));
            m_tailTipHandle.setVisible(m_tailTipHandle.sensorActivated(e.getX(), e.getY()));
           
            m_noseWidthHandleToe.setVisible(m_noseWidthHandleToe.sensorActivated(e.getX(), e.getY()));
            m_noseWidthHandleHeel.setVisible(m_noseWidthHandleHeel.sensorActivated(e.getX(), e.getY()));
            m_tailWidthHandleToe.setVisible(m_tailWidthHandleToe.sensorActivated(e.getX(), e.getY()));
            m_tailWidthHandleHeel.setVisible(m_tailWidthHandleHeel.sensorActivated(e.getX(), e.getY()));
            
            m_noseRunningLengthHandle.setVisible(m_noseRunningLengthHandle.sensorActivated(e.getX(), e.getY()));
            m_tailRunningLengthHandle.setVisible(m_tailRunningLengthHandle.sensorActivated(e.getX(), e.getY()));
            
            m_frontPackStanceWidthHandle.setVisible(m_frontPackStanceWidthHandle.sensorActivated(e.getX(), e.getY()));
            m_rearPackStanceWidthHandle.setVisible(m_rearPackStanceWidthHandle.sensorActivated(e.getX(), e.getY()));
            m_stanceOffsetHandle.setVisible(m_stanceOffsetHandle.sensorActivated(e.getX(), e.getY()));
            
            m_noseCubicHandle_1.setVisible(m_noseCubicHandle_1.sensorActivated(e.getX(), e.getY()));
            m_noseCubicHandle_2.setVisible(m_noseCubicHandle_2.sensorActivated(e.getX(), e.getY()));
            m_noseCubicHandle_3.setVisible(m_noseCubicHandle_3.sensorActivated(e.getX(), e.getY()));
            m_noseCubicHandle_4.setVisible(m_noseCubicHandle_4.sensorActivated(e.getX(), e.getY()));
            
            m_tailCubicHandle_1.setVisible(m_tailCubicHandle_1.sensorActivated(e.getX(), e.getY()));
            m_tailCubicHandle_2.setVisible(m_tailCubicHandle_2.sensorActivated(e.getX(), e.getY()));
            m_tailCubicHandle_3.setVisible(m_tailCubicHandle_3.sensorActivated(e.getX(), e.getY()));
            m_tailCubicHandle_4.setVisible(m_tailCubicHandle_4.sensorActivated(e.getX(), e.getY()));
            
            m_frontPackRowCountHandle.setVisible(m_frontPackRowCountHandle.sensorActivated(e.getX(), e.getY()));
            m_frontPackInsertSpacingHandle.setVisible(m_frontPackInsertSpacingHandle.sensorActivated(e.getX(), e.getY()));
            m_rearPackRowCountHandle.setVisible(m_rearPackRowCountHandle.sensorActivated(e.getX(), e.getY()));
            m_rearPackInsertSpacingHandle.setVisible(m_rearPackInsertSpacingHandle.sensorActivated(e.getX(), e.getY()));
            
            m_camberHandle.setVisible(m_camberHandle.sensorActivated(e.getX(), e.getY()));
            m_camberSetbackHandle.setVisible( m_camberSetbackHandle.sensorActivated(e.getX(), e.getY()));
            m_noseRadiusHandle.setVisible(m_noseRadiusHandle.sensorActivated(e.getX(), e.getY()));
            m_tailRadiusHandle.setVisible(m_tailRadiusHandle.sensorActivated(e.getX(), e.getY()));
            
            m_noseThicknessHandle.setVisible(m_noseThicknessHandle.sensorActivated(e.getX(), e.getY()));
            m_frontPackOutboardThicknessHandle.setVisible(m_frontPackOutboardThicknessHandle.sensorActivated(e.getX(), e.getY()));
            m_frontPackInboardThicknessHandle.setVisible(m_frontPackInboardThicknessHandle.sensorActivated(e.getX(), e.getY()));
            m_midPointThicknessHandle.setVisible(m_midPointThicknessHandle.sensorActivated(e.getX(), e.getY()));
            m_rearPackInboardThicknessHandle.setVisible(m_rearPackInboardThicknessHandle.sensorActivated(e.getX(), e.getY()));
            m_rearPackOutboardThicknessHandle.setVisible(m_rearPackOutboardThicknessHandle.sensorActivated(e.getX(), e.getY()));
            m_tailThicknessHandle.setVisible(m_tailThicknessHandle.sensorActivated(e.getX(), e.getY()));
            
            if (m_board.getRailType() == snoCADutilities.QUADRATIC)
            {
                m_toeSideCutHandle.setVisible(m_toeSideCutHandle.sensorActivated(e.getX(), e.getY()));
                m_heelSideCutHandle.setVisible(m_heelSideCutHandle.sensorActivated(e.getX(), e.getY()));
            }
            if ((m_board.getRailType() == snoCADutilities.MULTIPOINT_FREE) && (m_board.getRailGeometry().getSize() > 0))
            {
                m_drawMaxMin = true;

                for (int i = 0; i < m_board.getRailGeometry().getSize(); i++)
                {
                    m_board.getRailGeometry().getEntry(i).getHandle().setVisible(m_board.getRailGeometry().getEntry(i).getHandle().sensorActivated(e.getX(), e.getY()));
                }
             }
            
        } else {
            if (m_board.getGraphics().getSize() > 0) {
                for (int i = 0; i < m_board.getGraphics().getSize(); i++) {
                    m_board.getGraphics().getEntry(i).getDragger().setVisible(m_board.getGraphics().getEntry(i).getDragger().sensorActivated(e.getX(), e.getY()));
                    m_board.getGraphics().getEntry(i).getStretcher().setVisible(m_board.getGraphics().getEntry(i).getStretcher().sensorActivated(e.getX(), e.getY()));
                    m_board.getGraphics().getEntry(i).getRotator().setVisible(m_board.getGraphics().getEntry(i).getRotator().sensorActivated(e.getX(), e.getY()));
                    
                }
                
            }
            
            
        }
        
        repaint();
    }
    
    public void updateHandles() {
        
    
        
        m_noseTipHandle.setMessage(m_board.getNoseLength() + "mm");
        m_tailTipHandle.setMessage(m_board.getTailLength() + "mm");
        m_toeSideCutHandle.setMessage(m_board.getSidecutRadius() + "mm / " + m_board.getSidecutBias() + "mm");
        m_heelSideCutHandle.setMessage(m_board.getSidecutRadius() + "mm / " + m_board.getSidecutBias() + "mm");
        m_noseWidthHandleToe.setMessage(m_board.getNoseWidth() + "mm");
        m_noseWidthHandleHeel.setMessage(m_board.getNoseWidth() + "mm");
        m_tailWidthHandleToe.setMessage(m_board.getTailWidth() + "mm");
        m_tailWidthHandleHeel.setMessage(m_board.getTailWidth() + "mm");
        
        m_noseRunningLengthHandle.setMessage(m_board.getRunningLength() + "mm");
        m_tailRunningLengthHandle.setMessage(m_board.getRunningLength() + "mm");
        
        m_frontPackStanceWidthHandle.setMessage(m_board.getStanceWidth() + "mm");
        m_rearPackStanceWidthHandle.setMessage(m_board.getStanceWidth() + "mm");
        m_stanceOffsetHandle.setMessage(m_board.getStanceSetback() + "mm");
        
        m_noseCubicHandle_1.setMessage(m_board.getNoseBezier1Xfactor() + "," + m_board.getNoseBezier1Yfactor());
        m_noseCubicHandle_2.setMessage(m_board.getNoseBezier2Xfactor() + "," + m_board.getNoseBezier2Yfactor());
        m_noseCubicHandle_3.setMessage(m_board.getNoseBezier1Xfactor() + "," + m_board.getNoseBezier1Yfactor());
        m_noseCubicHandle_4.setMessage(m_board.getNoseBezier2Xfactor() + "," + m_board.getNoseBezier2Yfactor());
        
        m_tailCubicHandle_1.setMessage(m_board.getTailBezier1Xfactor() + "," + m_board.getTailBezier1Yfactor());
        m_tailCubicHandle_2.setMessage(m_board.getTailBezier2Xfactor() + "," + m_board.getTailBezier2Yfactor());
        m_tailCubicHandle_3.setMessage(m_board.getTailBezier1Xfactor() + "," + m_board.getTailBezier1Yfactor());
        m_tailCubicHandle_4.setMessage(m_board.getTailBezier2Xfactor() + "," + m_board.getTailBezier2Yfactor());
        
        m_frontPackRowCountHandle.setMessage(m_board.getFrontPackRowCount() + " rows");
        m_frontPackInsertSpacingHandle.setMessage(m_board.getFrontPackInsertSpacing() + "mm");
        m_rearPackRowCountHandle.setMessage(m_board.getRearPackRowCount() + " rows");
        m_rearPackInsertSpacingHandle.setMessage(m_board.getRearPackInsertSpacing() + "mm");
        
        m_camberHandle.setMessage(m_board.getCamber() + "mm");
        m_camberSetbackHandle.setMessage(m_board.getCamberSetback() + "mm");
        m_noseRadiusHandle.setMessage(m_board.getNoseRadius() + "mm");
        m_tailRadiusHandle.setMessage(m_board.getTailRadius() + "mm");
        
        
        m_noseThicknessHandle.setMessage(snoCADutilities.formatDouble(m_board.getNoseThickness()) + "mm");
        m_frontPackOutboardThicknessHandle.setMessage(m_board.getFrontPackOutboard() + "mm (" + snoCADutilities.formatDouble(m_board.getFrontPackThickness()) + "mm)");
        m_frontPackInboardThicknessHandle.setMessage(m_board.getFrontPackInboard() + "mm (" + snoCADutilities.formatDouble(m_board.getFrontPackThickness()) + "mm)");
        m_midPointThicknessHandle.setMessage(m_board.getMidPackOrd() + "mm (" + snoCADutilities.formatDouble(m_board.getMidPackThickness()) + "mm)");
        m_rearPackInboardThicknessHandle.setMessage(m_board.getRearPackInboard() + "mm (" + snoCADutilities.formatDouble(m_board.getRearPackThickness()) + "mm)");
        m_rearPackOutboardThicknessHandle.setMessage(m_board.getRearPackOutboard() + "mm (" + snoCADutilities.formatDouble(m_board.getRearPackThickness()) + "mm)");
        m_tailThicknessHandle.setMessage(snoCADutilities.formatDouble(m_board.getTailThickness()) + "mm");
        
        if ((m_board.getRailType() == snoCADutilities.MULTIPOINT_FREE) && (m_board.getRailGeometry().getSize() > 0))
        {

                m_board.getRailGeometry().getEntry(0).getHandle().setMessage("Nose Point 1");
                m_board.getRailGeometry().getEntry(1).getHandle().setMessage("Nose Point 2");
                m_board.getRailGeometry().getEntry(2).getHandle().setMessage("Centre Point (width : " + snoCADutilities.formatDouble(m_board.getWaistWidth()) + "mm)");
                m_board.getRailGeometry().getEntry(3).getHandle().setMessage("Tail Point 2");
                m_board.getRailGeometry().getEntry(4).getHandle().setMessage("Tail Point 1");

        }
        
        if (m_board.getGraphics().getSize() > 0) {
            for (int i = 0; i < m_board.getGraphics().getSize(); i++) {
                m_board.getGraphics().getEntry(i).getDragger().  setMessage(m_board.getGraphics().getEntry(i).getName() + " " + m_board.getGraphics().getEntry(i).getPosX() + "," + m_board.getGraphics().getEntry(i).getPosY());
                m_board.getGraphics().getEntry(i).getStretcher().setMessage(m_board.getGraphics().getEntry(i).getName() + " " + m_board.getGraphics().getEntry(i).getWidth() + "x" + m_board.getGraphics().getEntry(i).getHeight());
                m_board.getGraphics().getEntry(i).getRotator().setMessage(m_board.getGraphics().getEntry(i).getName() + " " +  m_board.getGraphics().getEntry(i).getAngle() + "'");
            }
            
        }
    }
    
    public java.awt.image.BufferedImage getImageOfWorkspace(int viewType, int width) 
    {
        java.awt.Graphics2D oldGC = (java.awt.Graphics2D)m_offscreenImage.getGraphics();
        
        
        int w = width;
        
        int maxLength = (int)Math.round(m_board.getScaleFactor() * getBoardGeometry().getBounds2D().getWidth());
        maxLength = maxLength + (int)Math.round(m_board.getNoseLength() / 2);
        
        double scaleFactor = (double)width / maxLength;
        
        int maxHeight = (int)Math.round(m_board.getScaleFactor() * getBoardGeometry().getBounds2D().getHeight());

        if (viewType < 3)
        {
            maxHeight = maxHeight + (m_board.getGraphicsPrintBorder() * 2);
            maxLength = maxLength + (m_board.getGraphicsPrintBorder() * 2);
        }
        
        maxHeight =  (int)Math.round(maxHeight * scaleFactor);
        maxLength =  (int)Math.round(maxLength * scaleFactor);
        
        double originalScaleFactor = m_board.getScaleFactor();
        m_board.setScaleFactor(1 / scaleFactor);
        java.awt.image.BufferedImage retImg = new java.awt.image.BufferedImage(maxLength, maxHeight, java.awt.image.BufferedImage.TYPE_INT_RGB);
        m_Bg2d = (java.awt.Graphics2D)retImg.getGraphics();
        centreTargeted((retImg.getWidth() / 2), (retImg.getHeight() / 2)) ;
        m_Bg2d.setColor(java.awt.Color.white);
        m_Bg2d.fillRect(0,0,maxLength,maxHeight);
        m_offscreenImage = retImg;
        
        eraseHandles();
        if (viewType == 0) {
            drawBoardOutline(false, true);
            drawInserts();
        }
        
        if (viewType == 1) {
            drawCore();
            drawBoardOutline(true, true); // outline only
            drawSidewalls();
            drawInserts();
        }
        
        if (viewType == 2) {
            boolean originalRender = m_renderBoard;
            java.awt.Color originalCol = m_board.getBoardOutlineColour();
            m_board.setBoardOutlineColour(java.awt.Color.black);
            m_renderBoard = false;
            m_Bg2d.setStroke(new java.awt.BasicStroke(4));
            drawBoardOutline(true, true); // outline only
            m_Bg2d.setStroke(new java.awt.BasicStroke(1));
            drawInserts();
            
            m_renderBoard = originalRender;
            m_board.setBoardOutlineColour(originalCol);
            
        }
        
        if (viewType == 3) 
        {
            boolean originalRender = m_renderBoard;
            java.awt.Color originalCol = m_board.getBoardOutlineColour();
            m_board.setBoardOutlineColour(java.awt.Color.black);
            m_renderBoard = false;
            m_Bg2d.setStroke(new java.awt.BasicStroke(1));
            drawBoardOutline(true, false); // outline only
            m_Bg2d.setStroke(new java.awt.BasicStroke(1));
            drawInserts();
            drawAxes();
            m_renderBoard = originalRender;
            m_board.setBoardOutlineColour(originalCol);
        }
        
        
        repaint();

        return retImg;
    }
    
    public void paintComponent(java.awt.Graphics g) {
        java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;
        g2d.clearRect(0,0,getWidth(), getHeight());
      //  long start = System.currentTimeMillis();

        if (!m_statsPanel.waistWidthSet()) 
        {
            m_statsPanel.setBoard(m_board);
            updateStatsPanel();
        }
        
        m_offscreenImage = new java.awt.image.BufferedImage(getWidth(), getHeight(), java.awt.image.BufferedImage.TYPE_INT_RGB);
        m_Bg2d = (java.awt.Graphics2D)m_offscreenImage.getGraphics();
        
        m_Bg2d.clearRect(0,0, getWidth(), getHeight());
        m_Bg2d.setColor(java.awt.Color.black);
        m_Bg2d.fillRect(0,0,getWidth(), getHeight());
        
        if (m_showGrid) {
            java.awt.Color gridMinorColor = new java.awt.Color(10,10,10);
            java.awt.Color gridMajorColor = new java.awt.Color(20,20,30);
            drawGrid(m_Bg2d, gridMinorColor, 10);
            drawGrid(m_Bg2d, gridMajorColor, 50);
        }
        
        if (m_showAxes) {
            drawAxes();
        }
        
       
        
        if (m_editingMode == snoCADutilities.EDIT_BOARD) {
            drawBoardOutline(false, true);
            drawProfile();
        }
        
        if (m_editingMode == snoCADutilities.EDIT_GRAPHICS) {
            drawBoardOutline(false, true);
        }
        
        if (m_editingMode == snoCADutilities.EDIT_CORE) {
            
            drawCore();
            drawTipspacers();
            drawBoardOutline(true, true); // outline only
            drawSidewalls();
            drawFlex();
        }
        
        drawInserts();
        
        drawSmoothAid(m_noseCubicHandle_1.inUse() || m_noseCubicHandle_3.inUse(), // nose
                m_noseCubicHandle_1.inUse() || m_tailCubicHandle_1.inUse(), // toe
                m_tailCubicHandle_1.inUse() || m_tailCubicHandle_3.inUse(), // tail
                m_noseCubicHandle_3.inUse() || m_tailCubicHandle_3.inUse());// heel
        
        
        
        if (m_measuring) drawMeasuring();
        
        g2d.setClip(0,0, getWidth(), getHeight());
        g2d.drawImage(m_offscreenImage, 0, 0, this);
        m_Bg2d.dispose();
        
      //  long elapsedTimeMillis = System.currentTimeMillis()-start;
       // System.out.println(elapsedTimeMillis + "ms");
        
    }
    
    public java.awt.geom.Path2D.Double getCoreGeometry() 
    {
        m_Bg2d.setColor(m_board.getBoardOutlineColour());
        
        // Helper variables for quadratic calculation
        double sidecutBase = Math.sqrt((m_board.getSidecutRadius() * m_board.getSidecutRadius()) - ((m_board.getRunningLength() / 2) * (m_board.getRunningLength() / 2)));
        double sidecutWidth = m_board.getSidecutRadius() - sidecutBase;
        
        // Key co-ordinates on the outline
        snoCADutilities.point noseToeWidePoint = new snoCADutilities.point(getMidPointX() - scale(m_board.getRunningLength()) / 2, getMidPointY() - scale(m_board.getNoseWidth()) / 2);
        snoCADutilities.point noseHeelWidePoint = new snoCADutilities.point(getMidPointX() - scale(m_board.getRunningLength()) / 2, getMidPointY() + scale(m_board.getNoseWidth()) / 2);
        
        if (m_board.getTipspacerType() != snoCADutilities.NONE) {
            noseToeWidePoint.y = noseToeWidePoint.y + scale(m_board.getSidewallWidth());
            noseHeelWidePoint.y = noseHeelWidePoint.y - scale(m_board.getSidewallWidth());
        }
        
        snoCADutilities.point tailToeWidePoint = new snoCADutilities.point(getMidPointX() + scale(m_board.getRunningLength()) / 2, getMidPointY() - scale(m_board.getTailWidth()) / 2);
        snoCADutilities.point tailHeelWidePoint = new snoCADutilities.point(getMidPointX() + scale(m_board.getRunningLength()) / 2, getMidPointY() + scale(m_board.getTailWidth()) / 2);
        if (m_board.getTipspacerType() != snoCADutilities.NONE) {
            tailToeWidePoint.y = tailToeWidePoint.y + scale(m_board.getSidewallWidth());
            tailHeelWidePoint.y = tailHeelWidePoint.y - scale(m_board.getSidewallWidth());
        }
        
        // Points through which the rail midpoint runs
        snoCADutilities.point xyToe = new snoCADutilities.point(getMidPointX() + scale(m_board.getSidecutBias()), (noseToeWidePoint.y + tailToeWidePoint.y) / 2 + scale(sidecutWidth));
        snoCADutilities.point xyHeel = new snoCADutilities.point(getMidPointX() + scale(m_board.getSidecutBias()), (noseHeelWidePoint.y + tailHeelWidePoint.y) / 2 - scale(sidecutWidth));
        
        // Cubic Bezier handles
        snoCADutilities.point noseTip = new snoCADutilities.point((noseToeWidePoint.x - scale(m_board.getCoreNoseLength())), getMidPointY());
        snoCADutilities.point lowerNoseGuidePoint = new snoCADutilities.point(((noseHeelWidePoint.x + noseTip.x)) / 2, ((noseHeelWidePoint.y + noseTip.y)) / 2);
        snoCADutilities.point upperNoseGuidePoint = new snoCADutilities.point(((noseToeWidePoint.x + noseTip.x)) / 2, ((noseToeWidePoint.y + noseTip.y)) / 2);
        snoCADutilities.point lowerControlPoint1 = new snoCADutilities.point((lowerNoseGuidePoint.x - scale(m_board.getNoseBezier1Xfactor())), (lowerNoseGuidePoint.y + scale(m_board.getNoseBezier1Yfactor())));
        snoCADutilities.point lowerControlPoint2 = new snoCADutilities.point((noseTip.x + scale(m_board.getNoseBezier2Xfactor())), (noseTip.y + scale(m_board.getNoseBezier2Yfactor())));
        snoCADutilities.point upperControlPoint1 = new snoCADutilities.point((upperNoseGuidePoint.x - scale(m_board.getNoseBezier1Xfactor())), (upperNoseGuidePoint.y - scale(m_board.getNoseBezier1Yfactor())));
        snoCADutilities.point upperControlPoint2 = new snoCADutilities.point((noseTip.x + scale(m_board.getNoseBezier2Xfactor())), (noseTip.y - scale(m_board.getNoseBezier2Yfactor())));
        
        if (m_board.getTipspacerType() == snoCADutilities.SIDEWALL || m_board.getTipspacerType() == snoCADutilities.INTERLOCK) {
            lowerControlPoint1.x += scale(m_board.getSidewallWidth() + m_board.getTipSidewallOffset());
            upperControlPoint1.x += scale(m_board.getSidewallWidth() + m_board.getTipSidewallOffset());
            lowerControlPoint1.y -= scale(m_board.getSidewallWidth() + m_board.getTipSidewallOffset());
            upperControlPoint1.y += scale(m_board.getSidewallWidth() + m_board.getTipSidewallOffset());
        }
        
        
        snoCADutilities.point tailTip = new snoCADutilities.point((tailToeWidePoint.x + scale(m_board.getCoreTailLength())), getMidPointY());
        snoCADutilities.point lowerTailGuidePoint = new snoCADutilities.point(((tailHeelWidePoint.x + tailTip.x)) / 2, ((tailHeelWidePoint.y + tailTip.y)) / 2);
        snoCADutilities.point upperTailGuidePoint = new snoCADutilities.point(((tailToeWidePoint.x + tailTip.x)) / 2, ((tailToeWidePoint.y + tailTip.y)) / 2);
        snoCADutilities.point lowerTailControlPoint1 = new snoCADutilities.point((lowerTailGuidePoint.x + scale(m_board.getTailBezier1Xfactor())), (lowerTailGuidePoint.y + scale(m_board.getTailBezier1Yfactor())));
        snoCADutilities.point lowerTailControlPoint2 = new snoCADutilities.point((tailTip.x + scale(m_board.getTailBezier2Xfactor())), (tailTip.y + scale(m_board.getTailBezier2Yfactor())));
        snoCADutilities.point upperTailControlPoint1 = new snoCADutilities.point((upperTailGuidePoint.x + scale(m_board.getTailBezier1Xfactor())), (upperTailGuidePoint.y - scale(m_board.getTailBezier1Yfactor())));
        snoCADutilities.point upperTailControlPoint2 = new snoCADutilities.point((tailTip.x + scale(m_board.getTailBezier2Xfactor())), (tailTip.y - scale(m_board.getTailBezier2Yfactor())));
        
        
        
        if (m_board.getTipspacerType() == snoCADutilities.SIDEWALL || m_board.getTipspacerType() == snoCADutilities.INTERLOCK) {
            lowerTailControlPoint1.x -= scale(m_board.getSidewallWidth() + m_board.getTipSidewallOffset());
            upperTailControlPoint1.x -= scale(m_board.getSidewallWidth() + m_board.getTipSidewallOffset());
            lowerTailControlPoint1.y -= scale(m_board.getSidewallWidth() + m_board.getTipSidewallOffset());
            upperTailControlPoint1.y += scale(m_board.getSidewallWidth() + m_board.getTipSidewallOffset());
            
        }
        
        if (m_board.getConvexRails()) {
            xyToe = new snoCADutilities.point(getMidPointX() + scale(m_board.getSidecutBias()), (noseToeWidePoint.y + tailToeWidePoint.y) / 2 - scale(sidecutWidth));
            xyHeel = new snoCADutilities.point(getMidPointX() + scale(m_board.getSidecutBias()), (noseHeelWidePoint.y + tailHeelWidePoint.y) / 2 + scale(sidecutWidth));
        }
        
        // Control snoCADutilities.point to achieve above rail path
        snoCADutilities.point sideCutToeMidControlPoint = snoCADutilities.getQuadraticControlPoint(noseToeWidePoint, xyToe, tailToeWidePoint);
        snoCADutilities.point sideCutHeelMidControlPoint = snoCADutilities.getQuadraticControlPoint(noseHeelWidePoint, xyHeel, tailHeelWidePoint);
        
        
        // Outline
        java.awt.geom.Path2D.Double p = new java.awt.geom.Path2D.Double();
        java.awt.geom.Path2D.Double noseInterfacePath = new java.awt.geom.Path2D.Double();
        java.awt.geom.Path2D.Double tailInterfacePath = new java.awt.geom.Path2D.Double();
        m_Bg2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        
        switch(m_board.getTipspacerType()) {
            case snoCADutilities.SIDEWALL :
                
                tailInterfacePath.moveTo(tailToeWidePoint.x, tailToeWidePoint.y );
                tailInterfacePath.lineTo(tailToeWidePoint.x, tailToeWidePoint.y + scale(m_board.getTipSidewallOffset()));
                tailInterfacePath.curveTo(upperTailControlPoint1.x, upperTailControlPoint1.y, upperTailControlPoint2.x, upperTailControlPoint2.y, tailTip.x, tailTip.y);
                tailInterfacePath.curveTo(lowerTailControlPoint2.x, lowerTailControlPoint2.y, lowerTailControlPoint1.x, lowerTailControlPoint1.y, tailHeelWidePoint.x, tailHeelWidePoint.y - scale(m_board.getTipSidewallOffset()));
                tailInterfacePath.lineTo(tailHeelWidePoint.x, tailHeelWidePoint.y);
                
                
                noseInterfacePath.moveTo(noseHeelWidePoint.x, noseHeelWidePoint.y);
                noseInterfacePath.lineTo(noseHeelWidePoint.x, noseHeelWidePoint.y - scale(m_board.getTipSidewallOffset()));
                noseInterfacePath.curveTo(lowerControlPoint1.x, lowerControlPoint1.y, lowerControlPoint2.x, lowerControlPoint2.y, noseTip.x, noseTip.y);
                noseInterfacePath.curveTo(upperControlPoint2.x, upperControlPoint2.y, upperControlPoint1.x, upperControlPoint1.y, noseToeWidePoint.x, noseToeWidePoint.y + scale(m_board.getTipSidewallOffset()));
                noseInterfacePath.lineTo(noseToeWidePoint.x, noseToeWidePoint.y);
                break;
                
            case snoCADutilities.INTERLOCK :
                
                java.awt.geom.Arc2D.Double tailToeInterlock = new java.awt.geom.Arc2D.Double();
                tailToeInterlock.setArcByCenter(tailToeWidePoint.x, tailToeWidePoint.y + scale(m_board.getTipSidewallOffset()), scale(m_board.getTipspacerInterlockRadius()), 270, 90, java.awt.geom.Arc2D.OPEN);
                
                java.awt.geom.Arc2D.Double tailHeelInterlock = new java.awt.geom.Arc2D.Double();
                tailHeelInterlock.setArcByCenter(tailHeelWidePoint.x, tailHeelWidePoint.y - scale(m_board.getTipSidewallOffset()), scale(m_board.getTipspacerInterlockRadius()), 0, 90, java.awt.geom.Arc2D.OPEN);
                
                java.awt.geom.Arc2D.Double noseHeelInterlock = new java.awt.geom.Arc2D.Double();
                noseHeelInterlock.setArcByCenter(noseHeelWidePoint.x, noseHeelWidePoint.y - scale(m_board.getTipSidewallOffset()), scale(m_board.getTipspacerInterlockRadius()), 90, 90, java.awt.geom.Arc2D.OPEN);
                
                java.awt.geom.Arc2D.Double noseToeInterlock = new java.awt.geom.Arc2D.Double();
                noseToeInterlock.setArcByCenter(noseToeWidePoint.x, noseToeWidePoint.y + scale(m_board.getTipSidewallOffset()), scale(m_board.getTipspacerInterlockRadius()), 180, 90, java.awt.geom.Arc2D.OPEN);
                
                tailInterfacePath.moveTo(tailToeWidePoint.x, tailToeWidePoint.y );
                tailInterfacePath.lineTo(tailToeWidePoint.x, tailToeWidePoint.y + scale(m_board.getTipSidewallOffset()));
                tailInterfacePath.append(tailToeInterlock.getPathIterator(null), true);
                tailInterfacePath.curveTo(upperTailControlPoint1.x, upperTailControlPoint1.y, upperTailControlPoint2.x, upperTailControlPoint2.y, tailTip.x, tailTip.y);
                tailInterfacePath.curveTo(lowerTailControlPoint2.x, lowerTailControlPoint2.y, lowerTailControlPoint1.x, lowerTailControlPoint1.y, tailHeelWidePoint.x + scale(m_board.getTipspacerInterlockRadius()), tailHeelWidePoint.y - scale(m_board.getTipSidewallOffset()));
                tailInterfacePath.append(tailHeelInterlock.getPathIterator(null), true);
                tailInterfacePath.lineTo(tailHeelWidePoint.x, tailHeelWidePoint.y);
                
                noseInterfacePath.moveTo(noseHeelWidePoint.x, noseHeelWidePoint.y);
                noseInterfacePath.lineTo(noseHeelWidePoint.x, noseHeelWidePoint.y - scale(m_board.getTipSidewallOffset()));
                noseInterfacePath.append(noseHeelInterlock.getPathIterator(null), true);
                noseInterfacePath.curveTo(lowerControlPoint1.x, lowerControlPoint1.y, lowerControlPoint2.x, lowerControlPoint2.y, noseTip.x, noseTip.y);
                noseInterfacePath.curveTo(upperControlPoint2.x, upperControlPoint2.y, upperControlPoint1.x, upperControlPoint1.y, noseToeWidePoint.x - scale(m_board.getTipspacerInterlockRadius()), noseToeWidePoint.y + scale(m_board.getTipSidewallOffset()));
                noseInterfacePath.append(noseToeInterlock.getPathIterator(null), true);
                noseInterfacePath.lineTo(noseToeWidePoint.x, noseToeWidePoint.y);
                
                break;
                
            case snoCADutilities.STRAIGHT :
                
                tailInterfacePath.moveTo(tailToeWidePoint.x, tailToeWidePoint.y);
                tailInterfacePath.lineTo(tailHeelWidePoint.x, tailHeelWidePoint.y);
                
                noseInterfacePath.moveTo(noseHeelWidePoint.x, noseHeelWidePoint.y);
                noseInterfacePath.lineTo(noseToeWidePoint.x, noseToeWidePoint.y);
                break;
                
            case snoCADutilities.RADIUS :
                
                double noseTsrSquared = m_board.getNoseTipspacerRadius() * m_board.getNoseTipspacerRadius();
                double halfNoseWidth = (((m_board.getNoseWidth() - m_board.getSidewallWidth() * 2)/ 2) * ((m_board.getNoseWidth() - m_board.getSidewallWidth() * 2) / 2)) ;
                double noseArcBaseSquared = noseTsrSquared - halfNoseWidth;
                if (noseArcBaseSquared < 0) noseArcBaseSquared = 0 - noseArcBaseSquared;
                double noseArcBase = Math.sqrt(noseArcBaseSquared);
                double noseArcWidth = m_board.getNoseTipspacerRadius() - noseArcBase;
                
                double tailTsrSquared = m_board.getTailTipspacerRadius() * m_board.getTailTipspacerRadius();
                double halfTailWidth = (((m_board.getTailWidth() - m_board.getSidewallWidth() * 2)/ 2) * ((m_board.getTailWidth() - m_board.getSidewallWidth() * 2) / 2)) ;
                double tailArcBaseSquared = tailTsrSquared - halfTailWidth;
                if (tailArcBaseSquared < 0) tailArcBaseSquared = 0 - tailArcBaseSquared;
                double tailArcBase = Math.sqrt(tailArcBaseSquared);
                double tailArcWidth = m_board.getTailTipspacerRadius() - tailArcBase;
                
                snoCADutilities.point xyNose = new snoCADutilities.point(noseToeWidePoint.x - scale(noseArcWidth), getMidPointY());
                snoCADutilities.point noseRadiusCenter = new snoCADutilities.point(xyNose.x + scale(m_board.getNoseTipspacerRadius()), getMidPointY());
                
                
                snoCADutilities.point xyTail = new snoCADutilities.point(tailToeWidePoint.x + scale(tailArcWidth), getMidPointY());
                snoCADutilities.point tailRadiusCenter = new snoCADutilities.point(xyTail.x - scale(m_board.getTailTipspacerRadius()), getMidPointY());
                
                
                noseInterfacePath.moveTo(noseToeWidePoint.x, noseToeWidePoint.y);
                
                java.awt.geom.Arc2D.Double tip = new java.awt.geom.Arc2D.Double();
                tip.setFrameFromCenter(noseRadiusCenter.x, noseRadiusCenter.y, noseRadiusCenter.x - scale(m_board.getNoseTipspacerRadius()), noseRadiusCenter.y - scale(m_board.getNoseTipspacerRadius()));
                java.awt.geom.Point2D.Double startPoint = new java.awt.geom.Point2D.Double();
                java.awt.geom.Point2D.Double endPoint = new java.awt.geom.Point2D.Double();
                startPoint.x = noseHeelWidePoint.x;
                startPoint.y = noseHeelWidePoint.y;
                endPoint.x = noseToeWidePoint.x;
                endPoint.y = noseToeWidePoint.y;
                tip.setAngles(endPoint, startPoint);
                
                noseInterfacePath.append(tip, true);
                
                
                java.awt.geom.Arc2D.Double tail = new java.awt.geom.Arc2D.Double();
                tail.setFrameFromCenter(tailRadiusCenter.x, tailRadiusCenter.y, tailRadiusCenter.x + scale(m_board.getTailTipspacerRadius()), tailRadiusCenter.y - scale(m_board.getTailTipspacerRadius()));
                java.awt.geom.Point2D.Double startPointTail = new java.awt.geom.Point2D.Double();
                java.awt.geom.Point2D.Double endPointTail = new java.awt.geom.Point2D.Double();
                startPointTail.x = tailToeWidePoint.x;
                startPointTail.y = tailToeWidePoint.y;
                endPointTail.x = tailHeelWidePoint.x;
                endPointTail.y = tailHeelWidePoint.y;
                
                tail.setAngles(endPointTail, startPointTail);
                tailInterfacePath.moveTo(tailHeelWidePoint.x, tailHeelWidePoint.y);
                tailInterfacePath.append(tail, true);
                
                m_coreNoseCalculatedLength = noseArcWidth;
                m_coreTailCalculatedLength = tailArcWidth;
                
                break;
                
            case snoCADutilities.NONE :
                
                tailInterfacePath.moveTo(tailToeWidePoint.x, tailToeWidePoint.y + scale(m_board.getSidewallWidth()));
                tailInterfacePath.lineTo(tailToeWidePoint.x, tailToeWidePoint.y);
                tailInterfacePath.curveTo(upperTailControlPoint1.x, upperTailControlPoint1.y, upperTailControlPoint2.x, upperTailControlPoint2.y, tailTip.x, tailTip.y);
                tailInterfacePath.curveTo(lowerTailControlPoint2.x, lowerTailControlPoint2.y, lowerTailControlPoint1.x, lowerTailControlPoint1.y, tailHeelWidePoint.x, tailHeelWidePoint.y);
                tailInterfacePath.lineTo(tailHeelWidePoint.x, tailHeelWidePoint.y - scale(m_board.getSidewallWidth()));
                
                
                noseInterfacePath.moveTo(noseHeelWidePoint.x, noseHeelWidePoint.y + scale(m_board.getSidewallWidth()));
                noseInterfacePath.lineTo(noseHeelWidePoint.x, noseHeelWidePoint.y);
                noseInterfacePath.curveTo(lowerControlPoint1.x, lowerControlPoint1.y, lowerControlPoint2.x, lowerControlPoint2.y, noseTip.x, noseTip.y);
                noseInterfacePath.curveTo(upperControlPoint2.x, upperControlPoint2.y, upperControlPoint1.x, upperControlPoint1.y, noseToeWidePoint.x, noseToeWidePoint.y);
                noseInterfacePath.lineTo(noseToeWidePoint.x, noseToeWidePoint.y + scale(m_board.getSidewallWidth()));
                break;
                
            default :
                tailInterfacePath.moveTo(tailToeWidePoint.x, tailToeWidePoint.y);
                tailInterfacePath.lineTo(tailHeelWidePoint.x, tailHeelWidePoint.y);
                
                noseInterfacePath.moveTo(noseHeelWidePoint.x, noseHeelWidePoint.y);
                noseInterfacePath.lineTo(noseToeWidePoint.x, noseToeWidePoint.y);
                break;
        }
        
        m_noseInterfacePath = new java.awt.geom.Path2D.Double();
        m_noseInterfacePath = noseInterfacePath;
        m_tailInterfacePath = tailInterfacePath;
        
        java.awt.geom.Path2D.Double toeRail = new java.awt.geom.Path2D.Double();
        java.awt.geom.Path2D.Double heelRail = new java.awt.geom.Path2D.Double();

        if (m_board.getRailType() == snoCADutilities.QUADRATIC)
        {
            if (m_board.getTipspacerType() == snoCADutilities.RADIUS) {
                heelRail.moveTo(noseHeelWidePoint.x, noseHeelWidePoint.y);
                heelRail.quadTo(sideCutHeelMidControlPoint.x, sideCutHeelMidControlPoint.y, tailHeelWidePoint.x, tailHeelWidePoint.y);
                toeRail.moveTo(tailToeWidePoint.x, tailToeWidePoint.y);
                toeRail.quadTo(sideCutToeMidControlPoint.x, sideCutToeMidControlPoint.y, noseToeWidePoint.x, noseToeWidePoint.y);
            
            } else {
                heelRail.moveTo(tailHeelWidePoint.x, tailHeelWidePoint.y);
                heelRail.quadTo(sideCutHeelMidControlPoint.x, sideCutHeelMidControlPoint.y, noseHeelWidePoint.x, noseHeelWidePoint.y);
                toeRail.moveTo(noseToeWidePoint.x, noseToeWidePoint.y);
                toeRail.quadTo(sideCutToeMidControlPoint.x, sideCutToeMidControlPoint.y, tailToeWidePoint.x, tailToeWidePoint.y);
            }
        }
        
        
        if (m_board.getRailType() == snoCADutilities.MULTIPOINT_FREE)
        {
            java.awt.geom.Path2D.Double pR1 = new java.awt.geom.Path2D.Double();
            java.awt.geom.Path2D.Double pR2 = new java.awt.geom.Path2D.Double();
            
            snoCADrailGeometry rg = m_board.getRailGeometry();
            snoCADutilities.point currentPoint = new snoCADutilities.point(noseToeWidePoint.x, noseToeWidePoint.y);
            pR1.moveTo(noseToeWidePoint.x, noseToeWidePoint.y);

            int rgs = rg.getSize();
            double pX[] = new double[7];
            double pY[] = new double[7];
            
            pX[0] = noseToeWidePoint.x;
            pY[0] = noseToeWidePoint.y;
            
                for (int i = 1; i <= 5 ; i++)
                {
                 
                   snoCADrailPoint nextPoint = rg.getEntry(i-1);
                   double nextPointX  = noseToeWidePoint.x + scale(m_board.getRunningLength() / (100 / nextPoint.getRailPercent()));
                   double nextPointY = getMidPointY() - scale((nextPoint.getWidth() / 2) - m_board.getSidewallWidth());
                  
                   pX[i] = nextPointX;
                   pY[i] = nextPointY;
                   
                }
                
             pX[6] = tailToeWidePoint.x;
             pY[6] = tailToeWidePoint.y;


             pR1.curveTo(pX[1], pY[1], pX[2], pY[2], pX[3], pY[3]);
             pR1.curveTo(pX[4], pY[4], pX[5], pY[5], pX[6], pY[6]);
             
             toeRail = pR1;
             
             currentPoint = new snoCADutilities.point(tailHeelWidePoint.x, tailHeelWidePoint.y);
            
             pR2.moveTo(tailHeelWidePoint.x, tailHeelWidePoint.y);

             pX[0] = tailHeelWidePoint.x;
             pY[0] = tailHeelWidePoint.y;
            
                for (int i = 5; i > 0 ; i--)
                {
                   int j = 5 - (i - 1);
                   snoCADrailPoint nextPoint = rg.getEntry(i-1);
                   
                   double nextPointX  = noseToeWidePoint.x + scale(m_board.getRunningLength() / (100 / nextPoint.getRailPercent()));
                   double nextPointY = getMidPointY() + scale((nextPoint.getWidth() / 2) - m_board.getSidewallWidth());
                  
                   pX[j] = nextPointX;
                   pY[j] = nextPointY;
                   
                }
                
             pX[6] = noseHeelWidePoint.x;
             pY[6] = noseHeelWidePoint.y;

             pR2.curveTo(pX[1], pY[1], pX[2], pY[2], pX[3], pY[3]);
             pR2.curveTo(pX[4], pY[4], pX[5], pY[5], pX[6], pY[6]);
             
             heelRail = pR2;
        }
         
        
        if (m_board.getTipspacerType() == snoCADutilities.RADIUS) {
            p.moveTo(noseToeWidePoint.x, noseToeWidePoint.y);
            p.append(noseInterfacePath, true);
            p.append(heelRail, true);
            p.append(tailInterfacePath, true);
            p.append(toeRail, true);
            
        } else {
            p.moveTo(noseToeWidePoint.x, noseToeWidePoint.y);
            p.append(toeRail, true);
            p.append(tailInterfacePath, true);
            p.append(heelRail, true);
            p.append(noseInterfacePath, true);
        }
        
        
        
        return p;
        
        
        
    }
    
    private void drawCore() {
        
        java.awt.geom.Path2D.Double p = getCoreGeometry();
        
        m_Bg2d.setClip(p);
        
        java.awt.geom.Rectangle2D.Double coreStringerToe = new java.awt.geom.Rectangle2D.Double();
        java.awt.geom.Rectangle2D.Double coreStringerHeel = new java.awt.geom.Rectangle2D.Double();
        java.awt.geom.Rectangle2D.Double coreBounds = (java.awt.geom.Rectangle2D.Double)p.getBounds2D();
        
        coreStringerToe.x = coreBounds.x;
        coreStringerToe.width = coreBounds.width;
        coreStringerToe.y = getMidPointY() - scale(20) - scale(m_board.getInsertStringerWidth() / 2);
        coreStringerToe.height = scale(m_board.getInsertStringerWidth());
        
        coreStringerHeel.x = coreBounds.x;
        coreStringerHeel.width = coreBounds.width;
        coreStringerHeel.y = getMidPointY() + scale(20) - scale(m_board.getInsertStringerWidth() / 2);
        coreStringerHeel.height = scale(m_board.getInsertStringerWidth());
        
        javax.swing.ImageIcon beechJPG = new javax.swing.ImageIcon(Main.class.getResource("images/beech.jpg"));
        javax.swing.ImageIcon spruceJPG = new javax.swing.ImageIcon(Main.class.getResource("images/spruce.jpg"));
        
        java.awt.image.BufferedImage beechImage = new java.awt.image.BufferedImage((int)coreBounds.width, (int)coreBounds.height, java.awt.image.BufferedImage.TYPE_INT_RGB);
        java.awt.image.BufferedImage spruceImage = new java.awt.image.BufferedImage((int)coreBounds.width, (int)coreBounds.height, java.awt.image.BufferedImage.TYPE_INT_RGB);
        beechImage.getGraphics().drawImage(beechJPG.getImage(), 0, 0, beechImage.getWidth(), beechImage.getHeight(), null);
        spruceImage.getGraphics().drawImage(spruceJPG.getImage(), 0, 0, beechImage.getWidth(), beechImage.getHeight(), null);
        
        java.awt.TexturePaint paint = new java.awt.TexturePaint(beechImage, coreBounds);
        java.awt.TexturePaint paint2 = new java.awt.TexturePaint(spruceImage, coreStringerToe);
        
        if (m_renderBoard) {
            m_Bg2d.setPaint(paint);
            m_Bg2d.fill(p);
            m_Bg2d.setPaint(paint2);
            
            if (m_board.getInsertStringerWidth() > 0) {
                m_Bg2d.fill(coreStringerToe);
                m_Bg2d.fill(coreStringerHeel);
            }
        }
        
        m_Bg2d.setColor(m_board.getBoardOutlineColour());
        
        if (m_board.getInsertStringerWidth() > 0) {
            m_Bg2d.draw(coreStringerToe);
            m_Bg2d.draw(coreStringerHeel);
        }
        
        double i = coreStringerToe.y;
        m_Bg2d.setColor(m_board.getBoardOutlineColour());
        java.awt.geom.Path2D.Double pStringer = new java.awt.geom.Path2D.Double();
        
        while (i >= coreBounds.y) {
            i -= scale(m_board.getCoreStringerWidth());
            pStringer.moveTo(coreBounds.x, i);
            pStringer.lineTo(coreBounds.x + coreBounds.width, i);
        }
        
        i = coreStringerHeel.y + coreStringerHeel.height;
        while (i <= coreBounds.y + coreBounds.height) {
            i += scale(m_board.getCoreStringerWidth());
            pStringer.moveTo(coreBounds.x, i);
            pStringer.lineTo(coreBounds.x + coreBounds.width, i);
        }
        
        m_Bg2d.draw(pStringer);
        
        m_Bg2d.setPaint(null);
        m_Bg2d.setClip(null);
        m_Bg2d.setColor(m_board.getBoardOutlineColour());
        m_Bg2d.draw(p);
        
        m_Bg2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,java.awt.RenderingHints.VALUE_ANTIALIAS_OFF);
    }
    
    public java.awt.geom.Path2D.Double getNoseTipspacerGeometry() {
        snoCADutilities.point noseToeWidePoint = new snoCADutilities.point(getMidPointX() - scale(m_board.getRunningLength()) / 2, getMidPointY() - scale(m_board.getNoseWidth()) / 2);
        snoCADutilities.point noseHeelWidePoint = new snoCADutilities.point(getMidPointX() - scale(m_board.getRunningLength()) / 2, getMidPointY() + scale(m_board.getNoseWidth()) / 2);
        
        java.awt.geom.Path2D.Double ts = new java.awt.geom.Path2D.Double();
        
        if (m_board.getTipspacerType() == snoCADutilities.RADIUS) {
            ts.moveTo(noseToeWidePoint.x - scale(m_board.getNoseTipspacerMaterialLength()), getMidPointY() - scale(m_board.getNoseTipspacerMaterialWidth() / 2));
            ts.lineTo(noseToeWidePoint.x, getMidPointY() - scale(m_board.getNoseTipspacerMaterialWidth() / 2));
            ts.lineTo(noseToeWidePoint.x, noseToeWidePoint.y);
            ts.append(m_noseInterfacePath, true);
            ts.lineTo(noseHeelWidePoint.x, noseHeelWidePoint.y);
            ts.lineTo(noseHeelWidePoint.x, getMidPointY() + scale(m_board.getNoseTipspacerMaterialWidth() / 2));
            ts.lineTo(noseHeelWidePoint.x - scale(m_board.getNoseTipspacerMaterialLength()), getMidPointY() + scale(m_board.getNoseTipspacerMaterialWidth() / 2));
            ts.lineTo(noseToeWidePoint.x - scale(m_board.getNoseTipspacerMaterialLength()), getMidPointY() - scale(m_board.getNoseTipspacerMaterialWidth() / 2));
        } else {
            ts.moveTo(noseHeelWidePoint.x - scale(m_board.getNoseTipspacerMaterialLength()), getMidPointY() + scale(m_board.getNoseTipspacerMaterialWidth() / 2));
            ts.lineTo(noseHeelWidePoint.x, getMidPointY() + scale(m_board.getNoseTipspacerMaterialWidth() / 2));
            ts.lineTo(noseHeelWidePoint.x, noseHeelWidePoint.y);
            ts.append(m_noseInterfacePath, true);
            ts.lineTo(noseToeWidePoint.x, noseToeWidePoint.y);
            ts.lineTo(noseToeWidePoint.x, getMidPointY() - scale(m_board.getNoseTipspacerMaterialWidth() / 2));
            ts.lineTo(noseToeWidePoint.x - scale(m_board.getNoseTipspacerMaterialLength()), getMidPointY() - scale(m_board.getNoseTipspacerMaterialWidth() / 2));
        }   ts.lineTo(noseHeelWidePoint.x - scale(m_board.getNoseTipspacerMaterialLength()), getMidPointY() + scale(m_board.getNoseTipspacerMaterialWidth() / 2));
        return ts;
        
    }
    
    public java.awt.geom.Path2D.Double getTailTipspacerGeometry() {
        snoCADutilities.point tailToeWidePoint = new snoCADutilities.point(getMidPointX() + scale(m_board.getRunningLength()) / 2, getMidPointY() - scale(m_board.getTailWidth()) / 2);
        snoCADutilities.point tailHeelWidePoint = new snoCADutilities.point(getMidPointX() + scale(m_board.getRunningLength()) / 2, getMidPointY() + scale(m_board.getTailWidth()) / 2);
        java.awt.geom.Path2D.Double ts = new java.awt.geom.Path2D.Double();
        
        if (m_board.getTipspacerType() == snoCADutilities.RADIUS) {
            ts.moveTo(tailHeelWidePoint.x + scale(m_board.getTailTipspacerMaterialLength()), getMidPointY() + scale(m_board.getTailTipspacerMaterialWidth() / 2));
            ts.lineTo(tailHeelWidePoint.x, getMidPointY() + scale(m_board.getTailTipspacerMaterialWidth() / 2));
            ts.lineTo(tailHeelWidePoint.x, tailHeelWidePoint.y);
            ts.append(m_tailInterfacePath, true);
            ts.lineTo(tailToeWidePoint.x, tailToeWidePoint.y);
            ts.lineTo(tailToeWidePoint.x, getMidPointY() - scale(m_board.getTailTipspacerMaterialWidth() / 2));
            ts.lineTo(tailToeWidePoint.x + scale(m_board.getTailTipspacerMaterialLength()), getMidPointY() - scale(m_board.getTailTipspacerMaterialWidth() / 2));
            ts.lineTo(tailHeelWidePoint.x + scale(m_board.getTailTipspacerMaterialLength()), getMidPointY() + scale(m_board.getTailTipspacerMaterialWidth() / 2));
            
        } else {
            
            ts.moveTo(tailToeWidePoint.x + scale(m_board.getTailTipspacerMaterialLength()), getMidPointY() - scale(m_board.getTailTipspacerMaterialWidth() / 2));
            ts.lineTo(tailToeWidePoint.x, getMidPointY() - scale(m_board.getTailTipspacerMaterialWidth() / 2));
            ts.lineTo(tailToeWidePoint.x, tailToeWidePoint.y);
            ts.append(m_tailInterfacePath, true);
            ts.lineTo(tailHeelWidePoint.x, tailHeelWidePoint.y);
            ts.lineTo(tailHeelWidePoint.x, getMidPointY() + scale(m_board.getTailTipspacerMaterialWidth() / 2));
            ts.lineTo(tailHeelWidePoint.x + scale(m_board.getTailTipspacerMaterialLength()), getMidPointY() + scale(m_board.getTailTipspacerMaterialWidth() / 2));
        }   ts.lineTo(tailToeWidePoint.x + scale(m_board.getTailTipspacerMaterialLength()), getMidPointY() - scale(m_board.getTailTipspacerMaterialWidth() / 2));
        return ts;
    }
    
    
    private void drawTipspacers() {
        
        java.awt.geom.Path2D.Double p1 = getNoseTipspacerGeometry();
        java.awt.geom.Path2D.Double p2 = getTailTipspacerGeometry();
        
        m_Bg2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (m_renderBoard) {
            m_Bg2d.setColor(java.awt.Color.white);
            m_Bg2d.fill(p1);
            m_Bg2d.fill(p2);
        }
        
        m_Bg2d.setColor(m_board.getBoardOutlineColour());
        m_Bg2d.draw(p1);
        m_Bg2d.draw(p2);
        
        m_Bg2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,java.awt.RenderingHints.VALUE_ANTIALIAS_OFF);
    }
    
    private void drawSidewalls() {
        m_Bg2d.setColor(m_board.getBoardOutlineColour());
        
        // Helper variables for quadratic calculation
        double sidecutBase = Math.sqrt((m_board.getSidecutRadius() * m_board.getSidecutRadius()) - ((m_board.getRunningLength() / 2) * (m_board.getRunningLength() / 2)));
        double sidecutWidth = m_board.getSidecutRadius() - sidecutBase;
        
        // Key co-ordinates on the outline
        snoCADutilities.point noseToeWidePoint = new snoCADutilities.point(getMidPointX() - scale(m_board.getRunningLength()) / 2, getMidPointY() - scale(m_board.getNoseWidth()) / 2);
        snoCADutilities.point noseHeelWidePoint = new snoCADutilities.point(getMidPointX() - scale(m_board.getRunningLength()) / 2, getMidPointY() + scale(m_board.getNoseWidth()) / 2);
        
        snoCADutilities.point noseToeWidePointInner = new snoCADutilities.point(noseToeWidePoint.x, noseToeWidePoint.y);
        snoCADutilities.point noseHeelWidePointInner = new snoCADutilities.point(noseHeelWidePoint.x, noseHeelWidePoint.y);
        noseToeWidePointInner.y = noseToeWidePoint.y + scale(m_board.getSidewallWidth());
        noseHeelWidePointInner.y = noseHeelWidePoint.y - scale(m_board.getSidewallWidth());
        
        snoCADutilities.point tailToeWidePoint = new snoCADutilities.point(getMidPointX() + scale(m_board.getRunningLength()) / 2, getMidPointY() - scale(m_board.getTailWidth()) / 2);
        snoCADutilities.point tailHeelWidePoint = new snoCADutilities.point(getMidPointX() + scale(m_board.getRunningLength()) / 2, getMidPointY() + scale(m_board.getTailWidth()) / 2);
        
        snoCADutilities.point tailToeWidePointInner = new snoCADutilities.point(tailToeWidePoint.x, tailToeWidePoint.y);
        snoCADutilities.point tailHeelWidePointInner = new snoCADutilities.point(tailHeelWidePoint.x, tailHeelWidePoint.y);
        tailToeWidePointInner.y = tailToeWidePoint.y + scale(m_board.getSidewallWidth());
        tailHeelWidePointInner.y = tailHeelWidePoint.y - scale(m_board.getSidewallWidth());
        
        // Points through which the rail midsnoCADutilities.point runs
        snoCADutilities.point xyToe = new snoCADutilities.point(getMidPointX() + scale(m_board.getSidecutBias()), (noseToeWidePoint.y + tailToeWidePoint.y) / 2 + scale(sidecutWidth));
        snoCADutilities.point xyHeel = new snoCADutilities.point(getMidPointX() + scale(m_board.getSidecutBias()), (noseHeelWidePoint.y + tailHeelWidePoint.y) / 2 - scale(sidecutWidth));
        
        snoCADutilities.point xyToeInner = new snoCADutilities.point(getMidPointX() + scale(m_board.getSidecutBias()), (noseToeWidePointInner.y + tailToeWidePointInner.y) / 2 + scale(sidecutWidth));
        snoCADutilities.point xyHeelInner = new snoCADutilities.point(getMidPointX() + scale(m_board.getSidecutBias()), (noseHeelWidePointInner.y + tailHeelWidePointInner.y) / 2 - scale(sidecutWidth));
        
        if (m_board.getConvexRails()) {
            xyToe = new snoCADutilities.point(getMidPointX() + scale(m_board.getSidecutBias()), (noseToeWidePoint.y + tailToeWidePoint.y) / 2 - scale(sidecutWidth));
            xyHeel = new snoCADutilities.point(getMidPointX() + scale(m_board.getSidecutBias()), (noseHeelWidePoint.y + tailHeelWidePoint.y) / 2 + scale(sidecutWidth));
            xyToeInner = new snoCADutilities.point(getMidPointX() + scale(m_board.getSidecutBias()), (noseToeWidePointInner.y + tailToeWidePointInner.y) / 2 - scale(sidecutWidth));
            xyHeelInner = new snoCADutilities.point(getMidPointX() + scale(m_board.getSidecutBias()), (noseHeelWidePointInner.y + tailHeelWidePointInner.y) / 2 + scale(sidecutWidth));
        }
        
        // Control snoCADutilities.point to achieve above rail path
        snoCADutilities.point sideCutToeMidControlPoint = snoCADutilities.getQuadraticControlPoint(noseToeWidePoint, xyToe, tailToeWidePoint);
        snoCADutilities.point sideCutHeelMidControlPoint = snoCADutilities.getQuadraticControlPoint(noseHeelWidePoint, xyHeel, tailHeelWidePoint);
        snoCADutilities.point sideCutToeMidControlPointInner = snoCADutilities.getQuadraticControlPoint(noseToeWidePointInner, xyToeInner, tailToeWidePointInner);
        snoCADutilities.point sideCutHeelMidControlPointInner = snoCADutilities.getQuadraticControlPoint(noseHeelWidePointInner, xyHeelInner, tailHeelWidePointInner);
        
        // Outline
        java.awt.geom.Path2D.Double p1 = new java.awt.geom.Path2D.Double();
        java.awt.geom.Path2D.Double p2 = new java.awt.geom.Path2D.Double();
        
        if (m_board.getRailType() == snoCADutilities.QUADRATIC)
        {
        
            p1.moveTo(noseToeWidePoint.x, noseToeWidePoint.y );
            p1.quadTo(sideCutToeMidControlPoint.x, sideCutToeMidControlPoint.y, tailToeWidePoint.x, tailToeWidePoint.y);
            p1.lineTo(tailToeWidePointInner.x, tailToeWidePointInner.y);
            p1.quadTo(sideCutToeMidControlPointInner.x, sideCutToeMidControlPointInner.y, noseToeWidePointInner.x, noseToeWidePointInner.y);
            p1.lineTo(noseToeWidePoint.x, noseToeWidePoint.y);
            p1.closePath();
        
            p2.moveTo(noseHeelWidePoint.x, noseHeelWidePoint.y);
            p2.quadTo(sideCutHeelMidControlPoint.x, sideCutHeelMidControlPoint.y, tailHeelWidePoint.x, tailHeelWidePoint.y);
            p2.lineTo(tailHeelWidePointInner.x, tailHeelWidePointInner.y);
            p2.quadTo(sideCutHeelMidControlPointInner.x, sideCutHeelMidControlPointInner.y, noseHeelWidePointInner.x, noseHeelWidePointInner.y);
            p2.lineTo(noseHeelWidePoint.x, noseHeelWidePoint.y);
            p2.closePath();
        }
        
        if (m_board.getRailType() == snoCADutilities.MULTIPOINT_FREE)
        {
            p1.moveTo(noseToeWidePoint.x, noseToeWidePoint.y );
            java.awt.geom.Path2D.Double pR1 = new java.awt.geom.Path2D.Double();
            java.awt.geom.Path2D.Double pR2 = new java.awt.geom.Path2D.Double();
            
            snoCADrailGeometry rg = m_board.getRailGeometry();
            snoCADutilities.point currentPoint = new snoCADutilities.point(noseToeWidePoint.x, noseToeWidePoint.y);
            pR1.moveTo(noseToeWidePoint.x, noseToeWidePoint.y);

            int rgs = rg.getSize();
            double pX[] = new double[7];
            double pY[] = new double[7];
            
            pX[0] = noseToeWidePoint.x;
            pY[0] = noseToeWidePoint.y;
            
                for (int i = 1; i <= 5 ; i++)
                {
                 
                   snoCADrailPoint nextPoint = rg.getEntry(i-1);
                   double nextPointX  = noseToeWidePoint.x + scale(m_board.getRunningLength() / (100 / nextPoint.getRailPercent()));
                   double nextPointY = getMidPointY() - scale((nextPoint.getWidth() / 2));
                  
                   pX[i] = nextPointX;
                   pY[i] = nextPointY;
                   
                }
                
             pX[6] = tailToeWidePoint.x;
             pY[6] = tailToeWidePoint.y;


             pR1.curveTo(pX[1], pY[1], pX[2], pY[2], pX[3], pY[3]);
             pR1.curveTo(pX[4], pY[4], pX[5], pY[5], pX[6], pY[6]);
             
             currentPoint = new snoCADutilities.point(tailToeWidePoint.x, tailToeWidePoint.y);
            
             pR1.lineTo(tailToeWidePoint.x, tailToeWidePoint.y + scale(m_board.getSidewallWidth()));

             pX[0] = tailHeelWidePoint.x;
             pY[0] = tailHeelWidePoint.y;
            
                for (int i = 5; i > 0 ; i--)
                {
                   int j = 5 - (i - 1);
                   snoCADrailPoint nextPoint = rg.getEntry(i-1);
                   
                   double nextPointX  = noseToeWidePoint.x + scale(m_board.getRunningLength() / (100 / nextPoint.getRailPercent()));
                   double nextPointY = getMidPointY() - scale((nextPoint.getWidth() / 2) - m_board.getSidewallWidth());
                  
                   pX[j] = nextPointX;
                   pY[j] = nextPointY;
                   
                }
                
             pX[6] = noseToeWidePoint.x;
             pY[6] = noseToeWidePoint.y + scale(m_board.getSidewallWidth());

             pR1.curveTo(pX[1], pY[1], pX[2], pY[2], pX[3], pY[3]);
             pR1.curveTo(pX[4], pY[4], pX[5], pY[5], pX[6], pY[6]);
             
             pR1.lineTo(noseToeWidePoint.x, noseToeWidePoint.y);
             pR1.closePath();
             
             p1 = pR1;
             
             
             
             // ----------------- HEEL
             
            currentPoint = new snoCADutilities.point(noseHeelWidePoint.x, noseHeelWidePoint.y);
            pR2.moveTo(noseHeelWidePoint.x, noseHeelWidePoint.y);

            
            pX[0] = noseHeelWidePoint.x;
            pY[0] = noseHeelWidePoint.y;
            
                for (int i = 1; i <= 5 ; i++)
                {
                 
                   snoCADrailPoint nextPoint = rg.getEntry(i-1);
                   double nextPointX  = noseHeelWidePoint.x + scale(m_board.getRunningLength() / (100 / nextPoint.getRailPercent()));
                   double nextPointY = getMidPointY() + scale((nextPoint.getWidth() / 2));
                  
                   pX[i] = nextPointX;
                   pY[i] = nextPointY;
                   
                }
                
             pX[6] = tailHeelWidePoint.x;
             pY[6] = tailHeelWidePoint.y;


             pR2.curveTo(pX[1], pY[1], pX[2], pY[2], pX[3], pY[3]);
             pR2.curveTo(pX[4], pY[4], pX[5], pY[5], pX[6], pY[6]);
             
             currentPoint = new snoCADutilities.point(tailHeelWidePoint.x, tailHeelWidePoint.y);
            
             pR2.lineTo(tailHeelWidePoint.x, tailHeelWidePoint.y - scale(m_board.getSidewallWidth()));

             pX[0] = tailHeelWidePoint.x;
             pY[0] = tailHeelWidePoint.y;
            
                for (int i = 5; i > 0 ; i--)
                {
                   int j = 5 - (i - 1);
                   snoCADrailPoint nextPoint = rg.getEntry(i-1);
                   
                   double nextPointX  = noseToeWidePoint.x + scale(m_board.getRunningLength() / (100 / nextPoint.getRailPercent()));
                   double nextPointY = getMidPointY() + scale((nextPoint.getWidth() / 2) - m_board.getSidewallWidth());
                  
                   pX[j] = nextPointX;
                   pY[j] = nextPointY;
                   
                }
                
             pX[6] = noseHeelWidePoint.x;
             pY[6] = noseHeelWidePoint.y - scale(m_board.getSidewallWidth());

             pR2.curveTo(pX[1], pY[1], pX[2], pY[2], pX[3], pY[3]);
             pR2.curveTo(pX[4], pY[4], pX[5], pY[5], pX[6], pY[6]);
             
             pR2.lineTo(noseHeelWidePoint.x, noseHeelWidePoint.y);
             pR2.closePath();
             
             p2 = pR2;
            
        }
        
        
        
        
        m_Bg2d.setBackground(java.awt.Color.white);
        m_Bg2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        m_Bg2d.setColor(java.awt.Color.white);
        if (m_renderBoard) {
            m_Bg2d.fill(p1);
            m_Bg2d.fill(p2);
        }
        m_Bg2d.setColor(m_board.getBoardOutlineColour());
        m_Bg2d.draw(p1);
        m_Bg2d.draw(p2);
        m_Bg2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,java.awt.RenderingHints.VALUE_ANTIALIAS_OFF);
        
    }
    
    private void drawMeasuring() {
        m_Bg2d.setColor(java.awt.Color.red);
        m_Bg2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        m_Bg2d.drawLine(m_mousePositionX, m_mousePositionY,m_measuringEndPointX,m_measuringEndPointY);
        
        double distanceValue = snoCADutilities.distance(m_mousePositionX, m_mousePositionY, m_measuringEndPointX, m_measuringEndPointY);
        distanceValue = distanceValue * m_board.getScaleFactor();
        
        String distance = snoCADutilities.formatDouble(distanceValue);
        
        m_Bg2d.setColor(java.awt.Color.black);
        m_Bg2d.drawString(distance + "mm", m_measuringEndPointX + 4, m_measuringEndPointY - 4);
        m_Bg2d.setColor(java.awt.Color.white);
        m_Bg2d.drawString(distance + "mm", m_measuringEndPointX + 5, m_measuringEndPointY - 5);
        
        m_Bg2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,java.awt.RenderingHints.VALUE_ANTIALIAS_OFF);
        
    }
    
    public java.awt.geom.Path2D.Double getProfileGeometry() {
        double x1base = getMidPointX() - scale(m_board.getRunningLength()) / 2;
        double x2base = getMidPointX() + scale(m_board.getRunningLength()) / 2;
        double profileBase = getMidPointY() + scale(m_board.getNoseWidth()) + scale(100);
        
        double mid = (x1base + (x2base - x1base) / 2);
        snoCADutilities.point camberMidControlPoint = new snoCADutilities.point(0, 0);
        
        snoCADutilities.point a = new snoCADutilities.point(x1base, profileBase);
        snoCADutilities.point b = new snoCADutilities.point(getMidPointX() + scale(m_board.getCamberSetback()), (profileBase - scale(m_board.getCamber())));
        snoCADutilities.point c = new snoCADutilities.point(x2base, profileBase);
        camberMidControlPoint = snoCADutilities.getQuadraticControlPoint(a, b, c);
        
        
        snoCADutilities.point d = new snoCADutilities.point(x1base, a.y - scale(m_board.getNoseThickness()));
        snoCADutilities.point e = new snoCADutilities.point(b.x, b.y - scale(m_board.getMidPackThickness()));
        snoCADutilities.point f = new snoCADutilities.point(x2base, c.y - scale(m_board.getTailThickness()));
        
        snoCADutilities.point camberMidControlPointUpper = new snoCADutilities.point(0,0);
        
        camberMidControlPointUpper = snoCADutilities.getQuadraticControlPoint(d,e,f);
        
        // Outline
        java.awt.geom.Path2D.Double p = new java.awt.geom.Path2D.Double();
        int noseRadius = m_board.getNoseRadius();
        int tailRadius = m_board.getTailRadius();
        snoCADutilities.point tailTipLower = new snoCADutilities.point(0,0);
        snoCADutilities.point noseTipLower = new snoCADutilities.point(0,0);
        
        double curveAngle = 360 * m_board.getNoseLength() / (2 * Math.PI * noseRadius);
        noseTipLower = flexCurve(p, d.x, (d.y - scale(noseRadius)), 180, (180 + curveAngle), scale(noseRadius), false, false);
        
        java.awt.geom.Arc2D.Double tip = new java.awt.geom.Arc2D.Double();
        tip.setFrameFromCenter(x1base, profileBase - scale(noseRadius), x1base - scale(noseRadius), profileBase - scale(noseRadius * 2));
        java.awt.geom.Point2D.Double tipStart = new java.awt.geom.Point2D.Double(noseTipLower.x, noseTipLower.y);
        java.awt.geom.Point2D.Double tipEnd = new java.awt.geom.Point2D.Double(x1base, profileBase);
        
        tip.setAngles(tipStart, tipEnd);
        p.moveTo(tip.getStartPoint().getX(), tip.getStartPoint().getY());
        p.append(tip, true);
        p.quadTo(camberMidControlPoint.x, camberMidControlPoint.y, c.x, c.y);
        
        curveAngle = 360 * m_board.getTailLength() / (2 * Math.PI * tailRadius);
        tailTipLower = flexCurve(p, x2base, (profileBase - scale(tailRadius)), 180, (180 + curveAngle), scale(tailRadius), true, false);
        
        java.awt.geom.Arc2D.Double tail = new java.awt.geom.Arc2D.Double();
        tail.setFrameFromCenter(x2base, profileBase - scale(tailRadius), x2base + scale(tailRadius), profileBase - scale(tailRadius * 2));
        java.awt.geom.Point2D.Double tailStart = new java.awt.geom.Point2D.Double(tailTipLower.x, tailTipLower.y);
        java.awt.geom.Point2D.Double tailEnd = new java.awt.geom.Point2D.Double(x2base, profileBase);
        tail.setAngles(tailEnd, tailStart);
        p.append(tail, true);
        
        return p;
    }
    
    private void drawProfile() {
        java.awt.geom.Path2D.Double p = getProfileGeometry();
        
        double x1base = getMidPointX() - scale(m_board.getRunningLength()) / 2;
        double x2base = getMidPointX() + scale(m_board.getRunningLength()) / 2;
        double profileBase = getMidPointY() + scale(m_board.getNoseWidth()) + scale(100);
        
        double mid = (x1base + (x2base - x1base) / 2);
        snoCADutilities.point camberMidControlPoint = new snoCADutilities.point(0, 0);
        
        snoCADutilities.point a = new snoCADutilities.point(x1base, profileBase);
        snoCADutilities.point b = new snoCADutilities.point(getMidPointX() + scale(m_board.getCamberSetback()), (profileBase - scale(m_board.getCamber())));
        snoCADutilities.point c = new snoCADutilities.point(x2base, profileBase);
        camberMidControlPoint = snoCADutilities.getQuadraticControlPoint(a, b, c);
        
        
        snoCADutilities.point d = new snoCADutilities.point(x1base, a.y - scale(m_board.getNoseThickness()));
        snoCADutilities.point e = new snoCADutilities.point(b.x, b.y - scale(m_board.getMidPackThickness()));
        snoCADutilities.point f = new snoCADutilities.point(x2base, c.y - scale(m_board.getTailThickness()));
        
        snoCADutilities.point camberMidControlPointUpper = new snoCADutilities.point(0,0);
        
        camberMidControlPointUpper = snoCADutilities.getQuadraticControlPoint(d,e,f);
        
        m_Bg2d.setColor(m_board.getBoardOutlineColour());
        m_Bg2d.setClip(0,0,getWidth(), getHeight());
        m_Bg2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        m_Bg2d.draw(p);
        m_Bg2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,java.awt.RenderingHints.VALUE_ANTIALIAS_OFF);
        
        int noseRadius = m_board.getNoseRadius();
        int tailRadius = m_board.getTailRadius();
        double curveAngle = 360 * m_board.getNoseLength() / (2 * Math.PI * noseRadius);
        double innerAngle = ((180 - curveAngle) - 90);
        m_board.setNoseHeight(Math.round(noseRadius - Math.sin(0.017453292519943295 * innerAngle) * noseRadius));
        
        curveAngle = 360 * m_board.getTailLength() / (2 * Math.PI * tailRadius);
        innerAngle = ((180 - curveAngle) - 90);
        m_board.setTailHeight(Math.round(tailRadius - Math.sin(0.017453292519943295 * innerAngle) * tailRadius));
        
        m_camberHandle.draw(b.x, b.y, m_Bg2d);
        m_camberSetbackHandle.draw(b.x, b.y - 30, m_Bg2d);
        m_noseRadiusHandle.draw(x1base, profileBase, m_Bg2d);
        m_tailRadiusHandle.draw(x2base, profileBase, m_Bg2d);
        
        
    }
    
    private snoCADutilities.point flexCurve(java.awt.geom.Path2D.Double p, double ctrX, double ctrY, double startAngle, double endAngle, double radius, boolean reverse, boolean draw) {
        double xpos;
        double ypos;
        double angle;
        
        xpos = ctrX;
        ypos = ctrY;
        xpos +=  radius * Math.sin(0.017453292519943295 * startAngle);
        ypos +=  -radius * Math.cos(0.017453292519943295 * startAngle);
        if (draw) p.moveTo(xpos, ypos);
        angle = startAngle;
        
        while (angle <= endAngle) {
            xpos = radius * Math.sin(0.017453292519943295 * angle);
            ypos = -radius * Math.cos(0.017453292519943295 * angle);
            
            if (reverse) xpos = 0 - xpos;
            
            if (draw)p.lineTo((xpos + ctrX), (ypos + ctrY));
            angle +=  1;
        }
        
        snoCADutilities.point returnPoint = new snoCADutilities.point(xpos + ctrX, ypos + ctrY);
        return returnPoint;
        
    }
    
    public java.awt.geom.Path2D.Double getFlexGeometry() {
        double x1base = getMidPointX() - scale(m_board.getRunningLength()) / 2;
        double x2base = getMidPointX() + scale(m_board.getRunningLength()) / 2;
        double flexBase = getMidPointY() + scale(m_board.getNoseWidth()) + scale(20);
        
        m_Bg2d.setColor(m_board.getBoardOutlineColour());
        
        double coreNl = m_board.getCoreNoseLength();
        double coreTl = m_board.getCoreTailLength();
        double tt = m_board.getTailThickness();
        int rptoo = m_board.getRearPackOutboard();
        int rptoi = m_board.getRearPackInboard();
        int mpto = m_board.getMidPackOrd();
        int fptoo = m_board.getFrontPackOutboard();
        int fptoi = m_board.getFrontPackInboard();
        double nt = m_board.getNoseThickness();
        double mpt = m_board.getMidPackThickness();
        double fpt = m_board.getFrontPackThickness();
        double rpt = m_board.getRearPackThickness();
        
        // Outline
        java.awt.geom.Path2D.Double p = new java.awt.geom.Path2D.Double();
        
        p.moveTo((x1base - scale(coreNl)), flexBase);
        p.lineTo((x2base + scale(coreTl)), flexBase);
        p.lineTo((x2base + scale(coreTl)), (flexBase - scale(tt)));
        p.lineTo(x2base, (flexBase - scale(tt)));
        p.lineTo((x2base - scale(rptoo)), (flexBase - scale(rpt)));
        p.lineTo((x2base - scale(rptoi)), (flexBase - scale(rpt)));
        p.lineTo(getMidPointX() + scale(mpto), flexBase - scale(mpt));
        p.lineTo((x1base + scale(fptoi)), (flexBase - scale(fpt)));
        p.lineTo((x1base + scale(fptoo)), (flexBase - scale(fpt)));
        p.lineTo(x1base, (flexBase - scale(nt)));
        p.lineTo((x1base - scale(coreNl)), (flexBase - scale(nt)));
        p.lineTo((x1base - scale(coreNl)), flexBase);
        
        return p;
    }
    
    private void drawFlex() {
        java.awt.geom.Path2D.Double p = getFlexGeometry();
        
        m_Bg2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        
        double x1base = getMidPointX() - scale(m_board.getRunningLength()) / 2;
        double x2base = getMidPointX() + scale(m_board.getRunningLength()) / 2;
        double flexBase = getMidPointY() + scale(m_board.getNoseWidth()) + scale(20);
        
        m_Bg2d.setColor(m_board.getBoardOutlineColour());
        
        double coreNl = m_board.getCoreNoseLength();
        double coreTl = m_board.getCoreTailLength();
        double tt = m_board.getTailThickness();
        int rptoo = m_board.getRearPackOutboard();
        int rptoi = m_board.getRearPackInboard();
        int mpto = m_board.getMidPackOrd();
        int fptoo = m_board.getFrontPackOutboard();
        int fptoi = m_board.getFrontPackInboard();
        double nt = m_board.getNoseThickness();
        double mpt = m_board.getMidPackThickness();
        double fpt = m_board.getFrontPackThickness();
        double rpt = m_board.getRearPackThickness();
        
        if (m_renderBoard) {
            javax.swing.ImageIcon beechJPG = new javax.swing.ImageIcon(Main.class.getResource("images/beech.jpg"));
            java.awt.Rectangle coreBounds = p.getBounds();
            java.awt.image.BufferedImage beechImage = new java.awt.image.BufferedImage((int)coreBounds.width, (int)coreBounds.height, java.awt.image.BufferedImage.TYPE_INT_RGB);
            beechImage.getGraphics().drawImage(beechJPG.getImage(), 0, 0, beechImage.getWidth(), beechImage.getHeight(), null);
            java.awt.TexturePaint paint = new java.awt.TexturePaint(beechImage, coreBounds);
            
            m_Bg2d.setColor(new java.awt.Color(153,153,0));
            m_Bg2d.setPaint(paint);
            m_Bg2d.fill(p);
            m_Bg2d.setPaint(null);
        }
        
        m_Bg2d.setColor(m_board.getBoardOutlineColour());
        m_Bg2d.draw(p);
        m_Bg2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,java.awt.RenderingHints.VALUE_ANTIALIAS_OFF);
        
        m_noseThicknessHandle.draw(x1base, flexBase - scale(nt), m_Bg2d);
        m_frontPackOutboardThicknessHandle.draw(x1base + scale(fptoo), flexBase - scale(fpt), m_Bg2d);
        m_frontPackInboardThicknessHandle.draw(x1base + scale(fptoi), flexBase - scale(fpt), m_Bg2d);
        m_midPointThicknessHandle.draw(getMidPointX() + scale(mpto), flexBase - scale(mpt), m_Bg2d);
        m_rearPackInboardThicknessHandle.draw(x2base - scale(rptoi), flexBase - scale(rpt), m_Bg2d);
        m_rearPackOutboardThicknessHandle.draw(x2base - scale(rptoo), flexBase - scale(rpt), m_Bg2d);
        m_tailThicknessHandle.draw(x2base, flexBase - scale(tt), m_Bg2d);
        
    }
    
    public java.awt.geom.Path2D.Double getBoardGeometry() 
    {
        
        // Helper variables for quadratic calculation
        double sidecutBase = Math.sqrt((m_board.getSidecutRadius() * m_board.getSidecutRadius()) - ((m_board.getRunningLength() / 2) * (m_board.getRunningLength() / 2)));
        double sidecutWidth = m_board.getSidecutRadius() - sidecutBase;
        
        // Key co-ordinates on the outline
        snoCADutilities.point noseToeWidePoint = new snoCADutilities.point(getMidPointX() - scale(m_board.getRunningLength()) / 2, getMidPointY() - scale(m_board.getNoseWidth()) / 2);
        snoCADutilities.point noseHeelWidePoint = new snoCADutilities.point(getMidPointX() - scale(m_board.getRunningLength()) / 2, getMidPointY() + scale(m_board.getNoseWidth()) / 2);
        
        snoCADutilities.point tailToeWidePoint = new snoCADutilities.point(getMidPointX() + scale(m_board.getRunningLength()) / 2, getMidPointY() - scale(m_board.getTailWidth()) / 2);
        snoCADutilities.point tailHeelWidePoint = new snoCADutilities.point(getMidPointX() + scale(m_board.getRunningLength()) / 2, getMidPointY() + scale(m_board.getTailWidth()) / 2);
        
        // Points through which the rail midsnoCADutilities.point runs
        snoCADutilities.point xyToe = new snoCADutilities.point(getMidPointX() + scale(m_board.getSidecutBias()), (noseToeWidePoint.y + tailToeWidePoint.y) / 2 + scale(sidecutWidth));
        snoCADutilities.point xyHeel = new snoCADutilities.point(getMidPointX() + scale(m_board.getSidecutBias()), (noseHeelWidePoint.y + tailHeelWidePoint.y) / 2 - scale(sidecutWidth));
        
        if (m_board.getConvexRails()) {
            xyToe = new snoCADutilities.point(getMidPointX() + scale(m_board.getSidecutBias()), (noseToeWidePoint.y + tailToeWidePoint.y) / 2 - scale(sidecutWidth));
            xyHeel = new snoCADutilities.point(getMidPointX() + scale(m_board.getSidecutBias()), (noseHeelWidePoint.y + tailHeelWidePoint.y) / 2 + scale(sidecutWidth));
        }
        
        if (m_board.getRailType() == snoCADutilities.QUADRATIC)
        {
            double ww = (xyHeel.y - xyToe.y) * m_board.getScaleFactor() ;
            m_board.setWaistWidth(ww);
        }
 
        if (m_board.getRailType() == snoCADutilities.MULTIPOINT_FREE)
        {
            double ww = m_board.getRailGeometry().getEntry(2).getWidth() ;
            m_board.setWaistWidth(ww);
        }
        
        // Control snoCADutilities.point to achieve above rail path
        snoCADutilities.point sideCutToeMidControlPoint = snoCADutilities.getQuadraticControlPoint(noseToeWidePoint, xyToe, tailToeWidePoint);
        snoCADutilities.point sideCutHeelMidControlPoint = snoCADutilities.getQuadraticControlPoint(noseHeelWidePoint, xyHeel, tailHeelWidePoint);
        
        // Cubic Bezier handles
        snoCADutilities.point noseTip = new snoCADutilities.point((noseToeWidePoint.x - scale(m_board.getNoseLength())), getMidPointY());
        snoCADutilities.point lowerNoseGuidePoint = new snoCADutilities.point(((noseHeelWidePoint.x + noseTip.x)) / 2, ((noseHeelWidePoint.y + noseTip.y)) / 2);
        snoCADutilities.point upperNoseGuidePoint = new snoCADutilities.point(((noseToeWidePoint.x + noseTip.x)) / 2, ((noseToeWidePoint.y + noseTip.y)) / 2);
        snoCADutilities.point lowerControlPoint1 = new snoCADutilities.point((lowerNoseGuidePoint.x - scale(m_board.getNoseBezier1Xfactor())), (lowerNoseGuidePoint.y + scale(m_board.getNoseBezier1Yfactor())));
        snoCADutilities.point lowerControlPoint2 = new snoCADutilities.point((noseTip.x + scale(m_board.getNoseBezier2Xfactor())), (noseTip.y + scale(m_board.getNoseBezier2Yfactor())));
        snoCADutilities.point upperControlPoint1 = new snoCADutilities.point((upperNoseGuidePoint.x - scale(m_board.getNoseBezier1Xfactor())), (upperNoseGuidePoint.y - scale(m_board.getNoseBezier1Yfactor())));
        snoCADutilities.point upperControlPoint2 = new snoCADutilities.point((noseTip.x + scale(m_board.getNoseBezier2Xfactor())), (noseTip.y - scale(m_board.getNoseBezier2Yfactor())));
        
        snoCADutilities.point tailTip = new snoCADutilities.point((tailToeWidePoint.x + scale(m_board.getTailLength())), getMidPointY());
        snoCADutilities.point lowerTailGuidePoint = new snoCADutilities.point(((tailHeelWidePoint.x + tailTip.x)) / 2, ((tailHeelWidePoint.y + tailTip.y)) / 2);
        snoCADutilities.point upperTailGuidePoint = new snoCADutilities.point(((tailToeWidePoint.x + tailTip.x)) / 2, ((tailToeWidePoint.y + tailTip.y)) / 2);
        snoCADutilities.point lowerTailControlPoint1 = new snoCADutilities.point((lowerTailGuidePoint.x + scale(m_board.getTailBezier1Xfactor())), (lowerTailGuidePoint.y + scale(m_board.getTailBezier1Yfactor())));
        snoCADutilities.point lowerTailControlPoint2 = new snoCADutilities.point((tailTip.x + scale(m_board.getTailBezier2Xfactor())), (tailTip.y + scale(m_board.getTailBezier2Yfactor())));
        snoCADutilities.point upperTailControlPoint1 = new snoCADutilities.point((upperTailGuidePoint.x + scale(m_board.getTailBezier1Xfactor())), (upperTailGuidePoint.y - scale(m_board.getTailBezier1Yfactor())));
        snoCADutilities.point upperTailControlPoint2 = new snoCADutilities.point((tailTip.x + scale(m_board.getTailBezier2Xfactor())), (tailTip.y - scale(m_board.getTailBezier2Yfactor())));
        
        // Outline
        java.awt.geom.Path2D.Double p = new java.awt.geom.Path2D.Double();
        java.awt.geom.Path2D.Double pR1 = new java.awt.geom.Path2D.Double();
        java.awt.geom.Path2D.Double pR2 = new java.awt.geom.Path2D.Double();
        
        //java.awt.geom.Path2D.Double p2 = new java.awt.geom.Path2D.Double();
        
        pR1.moveTo(noseToeWidePoint.x, noseToeWidePoint.y );
        //p2.moveTo(noseToeWidePoint.x, noseToeWidePoint.y );
        
        int points = m_board.getSerrationCount();
        double serrationDepth = m_board.getSerrationDepth();
        snoCADutilities.point currentPoint = new snoCADutilities.point(noseToeWidePoint.x, noseToeWidePoint.y);
        
        if (m_board.getRailType() == snoCADutilities.QUADRATIC)
        {
            if (points == 0) 
            {
                pR1.quadTo(sideCutToeMidControlPoint.x, sideCutToeMidControlPoint.y, tailToeWidePoint.x, tailToeWidePoint.y);
            } 
            else 
            {
                
               if (points == 0) 
               {
                    pR1.quadTo(sideCutHeelMidControlPoint.x, sideCutHeelMidControlPoint.y, noseHeelWidePoint.x, noseHeelWidePoint.y);
               }
               else 
               {
                double nodes = 100 / points;
            
                for (double i = 0; i <= 100; i+= nodes) 
                {
                    double t = i / 100;
                    snoCADutilities.point segmentMidPoint = snoCADutilities.quadratic(1 - t, noseToeWidePoint, sideCutToeMidControlPoint, tailToeWidePoint);
                    if (i % (nodes * 2) != 0) segmentMidPoint.y -= (int)scale(serrationDepth);
                    double smoothing = (segmentMidPoint.x - currentPoint.x) / 4;
                    pR1.curveTo(currentPoint.x + smoothing, currentPoint.y, segmentMidPoint.x - smoothing, segmentMidPoint.y, segmentMidPoint.x, segmentMidPoint.y);
                    currentPoint = segmentMidPoint;
                }
               }
            }
        }
        
        
        if (m_board.getRailType() == snoCADutilities.MULTIPOINT_FREE)
        {
            snoCADrailGeometry rg = m_board.getRailGeometry();
            currentPoint = new snoCADutilities.point(noseToeWidePoint.x, noseToeWidePoint.y);
            pR1.moveTo(noseToeWidePoint.x, noseToeWidePoint.y);

            int rgs = rg.getSize();
            double pX[] = new double[7];
            double pY[] = new double[7];
            
            pX[0] = noseToeWidePoint.x;
            pY[0] = noseToeWidePoint.y;
            
                for (int i = 1; i <= 5 ; i++)
                {
                 
                   snoCADrailPoint nextPoint = rg.getEntry(i-1);
                   double nextPointX  = noseToeWidePoint.x + scale(m_board.getRunningLength() / (100 / nextPoint.getRailPercent()));
                   double nextPointY = getMidPointY() - scale(nextPoint.getWidth() / 2);
                  
                   pX[i] = nextPointX;
                   pY[i] = nextPointY;
                   
                }
                
             pX[6] = tailToeWidePoint.x;
             pY[6] = tailToeWidePoint.y;


             pR1.curveTo(pX[1], pY[1], pX[2], pY[2], pX[3], pY[3]);
             pR1.curveTo(pX[4], pY[4], pX[5], pY[5], pX[6], pY[6]);
        }

        
        pR1.lineTo(tailToeWidePoint.x, tailToeWidePoint.y);
        
        p.append(pR1, true);
        
        m_toeRailPath = pR1;
        
        p.curveTo(upperTailControlPoint1.x, upperTailControlPoint1.y, upperTailControlPoint2.x, upperTailControlPoint2.y, tailTip.x, tailTip.y);
        p.curveTo(lowerTailControlPoint2.x, lowerTailControlPoint2.y, lowerTailControlPoint1.x, lowerTailControlPoint1.y, tailHeelWidePoint.x, tailHeelWidePoint.y);
        currentPoint.x = tailHeelWidePoint.x;
        currentPoint.y = tailHeelWidePoint.y;
        
        pR2.moveTo(tailHeelWidePoint.x, tailHeelWidePoint.y);
        
        if (m_board.getRailType() == snoCADutilities.QUADRATIC)
        {
            if (points == 0) {
                pR2.quadTo(sideCutHeelMidControlPoint.x, sideCutHeelMidControlPoint.y, noseHeelWidePoint.x, noseHeelWidePoint.y);
            } else {
                double nodes = 100 / points;
            
                for (double i = 0; i <= 100; i+= nodes) {
                    double t = i / 100;
                    snoCADutilities.point segmentMidPoint = snoCADutilities.quadratic(1 - t, tailHeelWidePoint, sideCutHeelMidControlPoint, noseHeelWidePoint);
                    if (i % (nodes * 2) != 0) segmentMidPoint.y += (int)scale(serrationDepth);
                    double smoothing = (currentPoint.x - segmentMidPoint.x) / 4;
                    pR2.curveTo(currentPoint.x - smoothing, currentPoint.y, segmentMidPoint.x + smoothing, segmentMidPoint.y, segmentMidPoint.x, segmentMidPoint.y);
                    currentPoint = segmentMidPoint;
                
                }
            }
        }
        
        if (m_board.getRailType() == snoCADutilities.MULTIPOINT_FREE)
        {
            snoCADrailGeometry rg = m_board.getRailGeometry();
            currentPoint = new snoCADutilities.point(tailHeelWidePoint.x, tailHeelWidePoint.y);
            
            pR2.moveTo(tailHeelWidePoint.x, tailHeelWidePoint.y);
            
            int rgs = rg.getSize();
            double pX[] = new double[7];
            double pY[] = new double[7];
            
            pX[0] = tailHeelWidePoint.x;
            pY[0] = tailHeelWidePoint.y;
            
                for (int i = 5; i > 0 ; i--)
                {
                   int j = 5 - (i - 1);
                   snoCADrailPoint nextPoint = rg.getEntry(i-1);
                   
                   double nextPointX  = noseToeWidePoint.x + scale(m_board.getRunningLength() / (100 / nextPoint.getRailPercent()));
                   double nextPointY = getMidPointY() + scale(nextPoint.getWidth() / 2);
                  
                   pX[j] = nextPointX;
                   pY[j] = nextPointY;
                   
                }
                
             pX[6] = noseHeelWidePoint.x;
             pY[6] = noseHeelWidePoint.y;

             pR2.curveTo(pX[1], pY[1], pX[2], pY[2], pX[3], pY[3]);
             pR2.curveTo(pX[4], pY[4], pX[5], pY[5], pX[6], pY[6]);
        }
        
        pR2.lineTo(noseHeelWidePoint.x, noseHeelWidePoint.y);
        p.append(pR2, true);
        m_heelRailPath = pR2;
        
        p.curveTo(lowerControlPoint1.x, lowerControlPoint1.y, lowerControlPoint2.x, lowerControlPoint2.y, noseTip.x, noseTip.y);
        p.curveTo(upperControlPoint2.x, upperControlPoint2.y, upperControlPoint1.x, upperControlPoint1.y, noseToeWidePoint.x, noseToeWidePoint.y);
        
        return p;
        
    }
    
    private void drawBoardOutline(boolean outlineOnly, boolean antialias) {
        java.awt.geom.Path2D.Double p = getBoardGeometry();
        
        m_Bg2d.setColor(m_board.getBoardOutlineColour());
        m_Bg2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        
        
        // Key co-ordinates on the outline
        snoCADutilities.point noseToeWidePoint = new snoCADutilities.point(getMidPointX() - scale(m_board.getRunningLength()) / 2, getMidPointY() - scale(m_board.getNoseWidth()) / 2);
        snoCADutilities.point noseHeelWidePoint = new snoCADutilities.point(getMidPointX() - scale(m_board.getRunningLength()) / 2, getMidPointY() + scale(m_board.getNoseWidth()) / 2);
        
        snoCADutilities.point tailToeWidePoint = new snoCADutilities.point(getMidPointX() + scale(m_board.getRunningLength()) / 2, getMidPointY() - scale(m_board.getTailWidth()) / 2);
        snoCADutilities.point tailHeelWidePoint = new snoCADutilities.point(getMidPointX() + scale(m_board.getRunningLength()) / 2, getMidPointY() + scale(m_board.getTailWidth()) / 2);
        
        snoCADutilities.point noseTip = new snoCADutilities.point((noseToeWidePoint.x - scale(m_board.getNoseLength())), getMidPointY());
        snoCADutilities.point tailTip = new snoCADutilities.point((tailToeWidePoint.x + scale(m_board.getTailLength())), getMidPointY());
        
        // Helper variables for quadratic calculation
        double sidecutBase = Math.sqrt((m_board.getSidecutRadius() * m_board.getSidecutRadius()) - ((m_board.getRunningLength() / 2) * (m_board.getRunningLength() / 2)));
        double sidecutWidth = m_board.getSidecutRadius() - sidecutBase;
        
        // Points through which the rail midsnoCADutilities.point runs
        snoCADutilities.point xyToe = new snoCADutilities.point(getMidPointX() + scale(m_board.getSidecutBias()), (noseToeWidePoint.y + tailToeWidePoint.y) / 2 + scale(sidecutWidth));
        snoCADutilities.point xyHeel = new snoCADutilities.point(getMidPointX() + scale(m_board.getSidecutBias()), (noseHeelWidePoint.y + tailHeelWidePoint.y) / 2 - scale(sidecutWidth));
        
        if (m_board.getConvexRails()) {
            xyToe = new snoCADutilities.point(getMidPointX() + scale(m_board.getSidecutBias()), (noseToeWidePoint.y + tailToeWidePoint.y) / 2 - scale(sidecutWidth));
            xyHeel = new snoCADutilities.point(getMidPointX() + scale(m_board.getSidecutBias()), (noseHeelWidePoint.y + tailHeelWidePoint.y) / 2 + scale(sidecutWidth));
        }
        
        // Control snoCADutilities.point to achieve above rail path
        snoCADutilities.point sideCutToeMidControlPoint = snoCADutilities.getQuadraticControlPoint(noseToeWidePoint, xyToe, tailToeWidePoint);
        snoCADutilities.point sideCutHeelMidControlPoint = snoCADutilities.getQuadraticControlPoint(noseHeelWidePoint, xyHeel, tailHeelWidePoint);
        
        // Cubic Bezier handles
        
        snoCADutilities.point lowerNoseGuidePoint = new snoCADutilities.point(((noseHeelWidePoint.x + noseTip.x)) / 2, ((noseHeelWidePoint.y + noseTip.y)) / 2);
        snoCADutilities.point upperNoseGuidePoint = new snoCADutilities.point(((noseToeWidePoint.x + noseTip.x)) / 2, ((noseToeWidePoint.y + noseTip.y)) / 2);
        snoCADutilities.point lowerControlPoint1 = new snoCADutilities.point((lowerNoseGuidePoint.x - scale(m_board.getNoseBezier1Xfactor())), (lowerNoseGuidePoint.y + scale(m_board.getNoseBezier1Yfactor())));
        snoCADutilities.point lowerControlPoint2 = new snoCADutilities.point((noseTip.x + scale(m_board.getNoseBezier2Xfactor())), (noseTip.y + scale(m_board.getNoseBezier2Yfactor())));
        snoCADutilities.point upperControlPoint1 = new snoCADutilities.point((upperNoseGuidePoint.x - scale(m_board.getNoseBezier1Xfactor())), (upperNoseGuidePoint.y - scale(m_board.getNoseBezier1Yfactor())));
        snoCADutilities.point upperControlPoint2 = new snoCADutilities.point((noseTip.x + scale(m_board.getNoseBezier2Xfactor())), (noseTip.y - scale(m_board.getNoseBezier2Yfactor())));
        
        
        snoCADutilities.point lowerTailGuidePoint = new snoCADutilities.point(((tailHeelWidePoint.x + tailTip.x)) / 2, ((tailHeelWidePoint.y + tailTip.y)) / 2);
        snoCADutilities.point upperTailGuidePoint = new snoCADutilities.point(((tailToeWidePoint.x + tailTip.x)) / 2, ((tailToeWidePoint.y + tailTip.y)) / 2);
        snoCADutilities.point lowerTailControlPoint1 = new snoCADutilities.point((lowerTailGuidePoint.x + scale(m_board.getTailBezier1Xfactor())), (lowerTailGuidePoint.y + scale(m_board.getTailBezier1Yfactor())));
        snoCADutilities.point lowerTailControlPoint2 = new snoCADutilities.point((tailTip.x + scale(m_board.getTailBezier2Xfactor())), (tailTip.y + scale(m_board.getTailBezier2Yfactor())));
        snoCADutilities.point upperTailControlPoint1 = new snoCADutilities.point((upperTailGuidePoint.x + scale(m_board.getTailBezier1Xfactor())), (upperTailGuidePoint.y - scale(m_board.getTailBezier1Yfactor())));
        snoCADutilities.point upperTailControlPoint2 = new snoCADutilities.point((tailTip.x + scale(m_board.getTailBezier2Xfactor())), (tailTip.y - scale(m_board.getTailBezier2Yfactor())));
        
        
        if (outlineOnly == true) {
            m_Bg2d.setColor(java.awt.Color.darkGray);
            antiAlias(antialias);
            m_Bg2d.draw(p);
            m_Bg2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,java.awt.RenderingHints.VALUE_ANTIALIAS_OFF);
            return;
        }
        
        // Render the board in colour with highlights...
        
        if (m_renderBoard) {
            
            m_Bg2d.setColor(m_board.getBoardFillColour());
            m_Bg2d.fill(p);
            
            if (m_board.getGraphics().getSize() > 0) {
                for (int i = 0; i < m_board.getGraphics().getSize(); i++) {
                    m_Bg2d.setClip(p);
                    
                    
                    int width =  (int)Math.round(scale(m_board.getGraphics().getEntry(i).getWidth()));
                    int height = (int)Math.round(scale(m_board.getGraphics().getEntry(i).getHeight()));
                    
                    int positionX = (int)Math.round(noseTip.x + scale(m_board.getGraphics().getEntry(i).getPosX()));
                    int positionY = (int)Math.round(noseTip.y + scale(m_board.getGraphics().getEntry(i).getPosY()));
                    
                    if (m_board.getGraphics().getEntry(i).getPositionMode() == snoCADgraphicElement.STRETCH) {
                        positionX = (int)Math.round(p.getBounds2D().getX() - scale(m_board.getGraphicsPrintBorder()));
                        positionY = (int)Math.round(p.getBounds2D().getY() - scale(m_board.getGraphicsPrintBorder()));
                        width = (int)Math.round(p.getBounds2D().getWidth() + scale(m_board.getGraphicsPrintBorder() * 2));
                        height = (int)Math.round(p.getBounds2D().getHeight() + scale(m_board.getGraphicsPrintBorder() * 2));
                    }
                    
                    float opacity = (float)m_board.getGraphics().getEntry(i).getOpacity() / 100;
                    
                    float[] scales = { 1f, 1f, 1f, opacity };
                    float[] offsets = new float[4];
                    java.awt.image.RescaleOp rop = new java.awt.image.RescaleOp(scales, offsets, null);
                    java.awt.image.BufferedImage displayImage = null;
                    
                    try
                    {
                        //displayImage = new java.awt.image.BufferedImage((int)Math.round(scale(m_board.getGraphics().getEntry(i).getImage().getWidth())),
                        //(int)Math.round(scale(m_board.getGraphics().getEntry(i).getImage().getHeight())),       
                        //java.awt.image.BufferedImage.TYPE_INT_RGB);
                        displayImage = (m_board.getGraphics().getEntry(i).getImage());
                    }
                    catch(java.lang.OutOfMemoryError e)
                    {
                        Main.alert("Out of Memory !");
                    }
                    
                    if (m_board.getGraphics().getEntry(i).getImage().getColorModel().hasAlpha()) {
                        if ((m_board.getGraphics().getEntry(i).getImage().getType() != 12) && (m_board.getGraphics().getEntry(i).getImage().getType() != 13) ) // GIF// GIF
                        {
                            try 
                            {
                                displayImage = rop.filter(displayImage, null);
                            }
                            catch(java.lang.OutOfMemoryError e)
                            {
                                Main.alert("Out of Memory !");
                            }
                        }
                    }
                    
                    java.awt.TexturePaint paint = null;
                    
                    if (m_board.getGraphics().getEntry(i).getRenderMode() == snoCADgraphicElement.TILED) {
                        paint = new java.awt.TexturePaint(m_board.getGraphics().getEntry(i).getImage() , new java.awt.Rectangle(positionX,positionY,roundScale(m_board.getGraphics().getEntry(i).getTileWidth()),roundScale(m_board.getGraphics().getEntry(i).getTileHeight())));
                        java.awt.Rectangle rect = new java.awt.Rectangle(positionX, positionY, width, height);
                        m_Bg2d.setPaint(paint);
                        m_Bg2d.fill(rect);
                    } else {
                        
                        AffineTransform at = new AffineTransform();
                        at.setToIdentity();
                        at.translate(positionX, positionY);
                        at.rotate(Math.toRadians(m_board.getGraphics().getEntry(i).getAngle()), (double)width / 2 , (double) height / 2 );                        
                        at.scale((double)width / m_board.getGraphics().getEntry(i).getImage().getWidth(), (double) height / m_board.getGraphics().getEntry(i).getImage().getHeight());

                        m_Bg2d.drawImage(displayImage, at, null);
                   
                    }
                    
                    m_Bg2d.setClip(null);
                    
                    if (m_board.getGraphics().getEntry(i).getPositionMode() != snoCADgraphicElement.STRETCH) {
                        if (m_editingMode == snoCADutilities.EDIT_GRAPHICS) {
                            
                            m_Bg2d.setColor(java.awt.Color.darkGray);
                            java.awt.geom.Rectangle2D.Double rect = new java.awt.geom.Rectangle2D.Double();
                            rect.setRect(0,0, width, height);
                            AffineTransform saveTr = m_Bg2d.getTransform();
                            AffineTransform rot = new AffineTransform();
                            rot.setToTranslation(positionX, positionY);
                            rot.rotate(Math.toRadians(m_board.getGraphics().getEntry(i).getAngle()), width / 2, height / 2);
                            m_Bg2d.setTransform(rot);
                            m_Bg2d.draw(rect);
                            m_Bg2d.setTransform(saveTr);
                            
                            java.awt.geom.Point2D.Double stretcherPoint = new java.awt.geom.Point2D.Double();
                            java.awt.geom.Point2D.Double draggerPoint = new java.awt.geom.Point2D.Double();
                            java.awt.geom.Point2D.Double rotatorPoint = new java.awt.geom.Point2D.Double();
                            
                            stretcherPoint.setLocation(rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight());
                            draggerPoint.setLocation(rect.getX(), rect.getY());
                            rotatorPoint.setLocation(rect.getX() + rect.getWidth(), rect.getY());
                            
                            rot.transform(stretcherPoint, stretcherPoint);
                            rot.transform(draggerPoint, draggerPoint);
                            rot.transform(rotatorPoint, rotatorPoint);
                            
                            m_board.getGraphics().getEntry(i).getStretcher().setLocation(stretcherPoint.getX(), stretcherPoint.getY());
                            m_board.getGraphics().getEntry(i).getDragger().setLocation(draggerPoint.getX(), draggerPoint.getY());
                            m_board.getGraphics().getEntry(i).getRotator().setLocation(rotatorPoint.getX(), rotatorPoint.getY());
                            
                            m_board.getGraphics().getEntry(i).getDragger().draw(draggerPoint.getX(), draggerPoint.getY(), m_Bg2d);
                            m_board.getGraphics().getEntry(i).getStretcher().draw(stretcherPoint.getX(), stretcherPoint.getY(), m_Bg2d);
                            m_board.getGraphics().getEntry(i).getRotator().draw(rotatorPoint.getX(), rotatorPoint.getY(), m_Bg2d);
   
                        }
                    }
                    
                }
            }
            
            if (m_highlights) {
                
                // Create board highlights
                java.awt.image.BufferedImage highlight = null;
                
                try {
                    highlight = javax.imageio.ImageIO.read(getClass().getResource("images/highlight.png"));
                } catch (java.io.IOException e) {}
                
                if (highlight != null) {
                    // Create a rescale filter op that makes the image 30% opaque
                    float[] scales = { 1f, 1f, 1f, 0.3f };
                    float[] offsets = new float[4];
                    java.awt.image.RescaleOp rop = new java.awt.image.RescaleOp(scales, offsets, null);
                    int noseHighlightSize = roundScale(m_board.getNoseLength() / 4);
                    int tailHighlightSize = roundScale(m_board.getTailLength() / 4);
                    int rlHighlightSize = roundScale(m_board.getRunningLength() / 2);
                    rop.filter(highlight, highlight);
                    m_Bg2d.setClip(p);
                    m_Bg2d.drawImage(highlight,(int)noseToeWidePoint.x - noseHighlightSize, (int)p.getBounds().getY(), noseHighlightSize * 2, (int)p.getBounds().getHeight(), null);
                    m_Bg2d.drawImage(highlight,(int)tailToeWidePoint.x - tailHighlightSize, (int)p.getBounds().getY(), tailHighlightSize * 2, (int)p.getBounds().getHeight(), null);
                    float[] rlscales = { 1f, 1f, 1f, 0.5f };
                    float[] rloffsets = new float[4];
                    rop = new java.awt.image.RescaleOp(rlscales, rloffsets, null);
                    rop.filter(highlight, highlight);
                    m_Bg2d.drawImage(highlight,(int)xyToe.x - rlHighlightSize, (int)p.getBounds().y, rlHighlightSize * 2, p.getBounds().height, null);
                    m_Bg2d.setClip(null);
                }
                
            }
            
        }
        
        
        m_Bg2d.setColor(m_board.getBoardOutlineColour());
        
        m_Bg2d.setClip(null);
        m_Bg2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        m_Bg2d.draw(p);
        m_Bg2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,java.awt.RenderingHints.VALUE_ANTIALIAS_OFF);
        m_Bg2d.setClip(null);
        
        // Draw Handles
        m_noseTipHandle.draw(noseTip.x, noseTip.y, m_Bg2d);
        m_tailTipHandle.draw(tailTip.x, tailTip.y, m_Bg2d);
        m_toeSideCutHandle.draw(xyToe.x, xyToe.y, m_Bg2d);
        m_heelSideCutHandle.draw(xyHeel.x, xyHeel.y, m_Bg2d);
        
        m_noseWidthHandleToe.draw(noseToeWidePoint.x, noseToeWidePoint.y, m_Bg2d);
        m_noseWidthHandleHeel.draw(noseHeelWidePoint.x, noseHeelWidePoint.y, m_Bg2d);
        m_tailWidthHandleToe.draw(tailToeWidePoint.x, tailToeWidePoint.y, m_Bg2d);
        m_tailWidthHandleHeel.draw(tailHeelWidePoint.x, tailHeelWidePoint.y, m_Bg2d);
        
        m_noseRunningLengthHandle.draw(noseHeelWidePoint.x, getMidPointY(), m_Bg2d);
        m_tailRunningLengthHandle.draw(tailHeelWidePoint.x, getMidPointY(), m_Bg2d);
        
        
        m_noseCubicHandle_1.draw(upperControlPoint1.x, upperControlPoint1.y, m_Bg2d);
        m_noseCubicHandle_2.draw(upperControlPoint2.x, upperControlPoint2.y, m_Bg2d);
        m_noseCubicHandle_3.draw(lowerControlPoint1.x, lowerControlPoint1.y, m_Bg2d);
        m_noseCubicHandle_4.draw(lowerControlPoint2.x, lowerControlPoint2.y, m_Bg2d);
        
        m_tailCubicHandle_1.draw(upperTailControlPoint1.x, upperTailControlPoint1.y, m_Bg2d);
        m_tailCubicHandle_2.draw(upperTailControlPoint2.x, upperTailControlPoint2.y, m_Bg2d);
        m_tailCubicHandle_3.draw(lowerTailControlPoint1.x, lowerTailControlPoint1.y, m_Bg2d);
        m_tailCubicHandle_4.draw(lowerTailControlPoint2.x, lowerTailControlPoint2.y, m_Bg2d);
        
        if ((m_board.getRailType() == snoCADutilities.MULTIPOINT_FREE) && (m_board.getRailGeometry().getSize() > 0))
        {
            getWidthAtPoint();
            getWidthAtPointTail();
            
            for (int i = 0; i < m_board.getRailGeometry().getSize(); i++)
            {
                snoCADrailPoint thisPoint = m_board.getRailGeometry().getEntry(i);
                double pointX  = noseToeWidePoint.x + scale(m_board.getRunningLength() / (100 / thisPoint.getRailPercent()));
                double pointY = getMidPointY() - scale(thisPoint.getWidth() / 2);
                m_board.getRailGeometry().getEntry(i).getHandle().draw(pointX, pointY, m_Bg2d);
            }

         }
        
    }
    
    public java.util.ArrayList<snoCADutilities.point>  getInsertGeometry() {
        java.util.ArrayList insertPoints = new java.util.ArrayList<snoCADutilities.point>();
        
        int frontPackCentre = getMidPointX() - roundScale((m_board.getStanceWidth() / 2) - m_board.getStanceSetback());
        
        int rowPos = 0;
        int frontPackBegin = frontPackCentre - (m_board.getFrontPackRowCount() / 2 * roundScale(m_board.getFrontPackInsertSpacing()) - roundScale(m_board.getFrontPackInsertSpacing() / 2) );
        int rowNbr = 0;
        
        while (rowNbr < m_board.getFrontPackRowCount()) {
            insertPoints.add(new snoCADutilities.point(frontPackBegin + scale(rowPos), getMidPointY() + scale(20)));
            insertPoints.add(new snoCADutilities.point(frontPackBegin + scale(rowPos), getMidPointY() - scale(20)));
            rowPos +=  m_board.getFrontPackInsertSpacing();
            rowNbr++;
        }
        
        int rearPackCentre =  getMidPointX() + roundScale((m_board.getStanceWidth() / 2) + m_board.getStanceSetback());
        
        
        rowPos = 0;
        int rearPackBegin = rearPackCentre - (m_board.getRearPackRowCount() / 2 * roundScale(m_board.getRearPackInsertSpacing()) - roundScale(m_board.getRearPackInsertSpacing() / 2) );
        
        rowNbr = 0;
        
        while (rowNbr < m_board.getRearPackRowCount()) {
            insertPoints.add(new snoCADutilities.point(rearPackBegin + scale(rowPos), getMidPointY() + scale(20)));
            insertPoints.add(new snoCADutilities.point(rearPackBegin + scale(rowPos), getMidPointY() - scale(20)));
            rowPos +=  m_board.getRearPackInsertSpacing();
            rowNbr++;
        }
        
        return insertPoints;
        
    }
    
    public java.util.ArrayList<snoCADutilities.point> getAlignmentHoleGeometry()
    {
        java.util.ArrayList insertPoints = new java.util.ArrayList<snoCADutilities.point>();
        int noseAxisX = getMidPointX() - roundScale(this.getBoard().getRunningLength() / 2);
        int tailAxisX = getMidPointX() + roundScale(this.getBoard().getRunningLength() / 2);
        int noseToeY = getMidPointY() + roundScale((this.getBoard().getNoseWidth() / 2) - 5) ;
        int noseHeelY = getMidPointY() - roundScale((this.getBoard().getNoseWidth() / 2) - 5) ;
        int tailToeY = getMidPointY() + roundScale((this.getBoard().getTailWidth() / 2) - 5) ;
        int tailHeelY = getMidPointY() - roundScale((this.getBoard().getTailWidth() / 2) - 5) ;
        int midToeY = getMidPointY() + roundScale((int)(this.getBoard().getWaistWidth() / 2) -5);
        int midHeelY = getMidPointY() - roundScale((int)(this.getBoard().getWaistWidth() / 2) -5);
        
        int noseTipX = getMidPointX() - roundScale(this.getBoard().getRunningLength() / 2) - roundScale(this.getBoard().getNoseLength()) + roundScale(5);
        int tailTipX = getMidPointX() + roundScale(this.getBoard().getRunningLength() / 2) + roundScale(this.getBoard().getTailLength()) - roundScale(5);
        
        insertPoints.add(new snoCADutilities.point(noseTipX, getMidPointY()));
        insertPoints.add(new snoCADutilities.point(tailTipX, getMidPointY()));
        insertPoints.add(new snoCADutilities.point(noseAxisX, getMidPointY()));
        insertPoints.add(new snoCADutilities.point(noseAxisX, noseToeY));
        insertPoints.add(new snoCADutilities.point(noseAxisX, noseHeelY));
        
        insertPoints.add(new snoCADutilities.point(getMidPointX(), getMidPointY()));
        insertPoints.add(new snoCADutilities.point(getMidPointX(), midToeY));
        insertPoints.add(new snoCADutilities.point(getMidPointX(), midHeelY));
        
        insertPoints.add(new snoCADutilities.point(tailAxisX, getMidPointY()));
        insertPoints.add(new snoCADutilities.point(tailAxisX, tailToeY));
        insertPoints.add(new snoCADutilities.point(tailAxisX, tailHeelY));
        
        return insertPoints;
    }
    
     public java.util.ArrayList<snoCADutilities.point> getIdentHoleGeometry()
     {
         java.util.ArrayList insertPoints = new java.util.ArrayList<snoCADutilities.point>();
         int noseIdentX = getMidPointX() - roundScale( (this.getBoard().getRunningLength() / 2)) - roundScale(this.getBoard().getNoseLength()) + roundScale(20);
         insertPoints.add(new snoCADutilities.point(noseIdentX, getMidPointY()));
         return insertPoints;
     }
     
     public java.util.ArrayList<snoCADutilities.point> getStanceLocatorGeometry()
     {
         java.util.ArrayList insertPoints = new java.util.ArrayList<snoCADutilities.point>();
         int frontPackCentre = getMidPointX() - roundScale((m_board.getStanceWidth() / 2) - m_board.getStanceSetback());
         int rearPackCentre =  getMidPointX() + roundScale((m_board.getStanceWidth() / 2) + m_board.getStanceSetback());
         int stanceMidPoint = getMidPointX() + roundScale(m_board.getStanceSetback());
         
         insertPoints.add(new snoCADutilities.point(frontPackCentre, getMidPointY()));
         insertPoints.add(new snoCADutilities.point(rearPackCentre, getMidPointY()));
         
         if (m_board.getStanceSetback() != 0)
         {
             insertPoints.add(new snoCADutilities.point(stanceMidPoint, getMidPointY() + scale(10)));
             insertPoints.add(new snoCADutilities.point(stanceMidPoint, getMidPointY() - scale(10)));
         }
         
         return insertPoints;
     }
    
    
    public void drawInserts() {
        java.util.ArrayList<snoCADutilities.point> points = getInsertGeometry();
        
        for(int i = 0; i < points.size(); i++) {
            drawCenteredCircle(points.get(i).x, points.get(i).y, scale(3));
        }
        
        int frontPackCentre = getMidPointX() - roundScale((m_board.getStanceWidth() / 2) - m_board.getStanceSetback());
        int frontPackBegin = frontPackCentre - (m_board.getFrontPackRowCount() / 2 * roundScale(m_board.getFrontPackInsertSpacing()) - roundScale(m_board.getFrontPackInsertSpacing() / 2) );
        int rearPackCentre =  getMidPointX() + roundScale((m_board.getStanceWidth() / 2) + m_board.getStanceSetback());
        int rearPackBegin = rearPackCentre + (m_board.getRearPackRowCount() / 2 * roundScale(m_board.getRearPackInsertSpacing()) - roundScale(m_board.getRearPackInsertSpacing() / 2) );
        
        m_frontPackStanceWidthHandle.draw((double)frontPackCentre, (double)getMidPointY(), m_Bg2d);
        m_rearPackStanceWidthHandle.draw((double)rearPackCentre, (double)getMidPointY(),m_Bg2d);
        m_stanceOffsetHandle.draw(getMidPointX(), getMidPointY(),m_Bg2d);
        
        m_frontPackRowCountHandle.draw((double)frontPackCentre, (double)getMidPointY() - roundScale(40), m_Bg2d);
        m_frontPackInsertSpacingHandle.draw((double)frontPackCentre, (double)getMidPointY() + roundScale(40), m_Bg2d);
        m_rearPackRowCountHandle.draw((double)rearPackCentre, (double)getMidPointY() - roundScale(40), m_Bg2d);
        m_rearPackInsertSpacingHandle.draw((double)rearPackCentre, (double)getMidPointY() + roundScale(40), m_Bg2d);
        
    }
    
    private void drawCenteredCircle(double x, double y, double diameter) {
        
        snoCADutilities.point tlc = new snoCADutilities.point(x - diameter / 2, y - diameter / 2);
        
        java.awt.geom.Ellipse2D.Double insert = new java.awt.geom.Ellipse2D.Double();
        insert.setFrame((int)Math.round(tlc.x), (int)Math.round(tlc.y), (int)Math.round(diameter), (int)Math.round(diameter));
        
        //m_Bg2d.setColor(java.awt.Color.black);
        
        if (m_renderBoard) 
        {
            m_Bg2d.setColor(java.awt.Color.black);
            m_Bg2d.fill(insert);
        }
        
        m_Bg2d.setColor(m_board.getBoardOutlineColour());
        
        if (m_editingMode == snoCADutilities.EDIT_CORE && m_renderBoard) m_Bg2d.setColor(java.awt.Color.black);
        
        m_Bg2d.draw(insert);
        
        
    }
    
    private void drawSmoothAid( boolean nose, boolean toe, boolean tail, boolean heel) 
    {
        snoCADutilities.point sideCutMidControlPoint = new snoCADutilities.point(0, 0);
        
        double sidecutBase = Math.sqrt((m_board.getSidecutRadius() * m_board.getSidecutRadius()) - ((m_board.getRunningLength() / 2) * (m_board.getRunningLength() / 2)));
        double sidecutWidth = m_board.getSidecutRadius() - sidecutBase;
        
        snoCADutilities.point noseToeWidePoint = new snoCADutilities.point(getMidPointX() - scale(m_board.getRunningLength()) / 2, getMidPointY() - scale(m_board.getNoseWidth()) / 2);
        snoCADutilities.point noseHeelWidePoint = new snoCADutilities.point(getMidPointX() - scale(m_board.getRunningLength()) / 2, getMidPointY() + scale(m_board.getNoseWidth()) / 2);
        
        snoCADutilities.point tailToeWidePoint = new snoCADutilities.point(getMidPointX() + scale(m_board.getRunningLength()) / 2, getMidPointY() - scale(m_board.getTailWidth()) / 2);
        snoCADutilities.point tailHeelWidePoint = new snoCADutilities.point(getMidPointX() + scale(m_board.getRunningLength()) / 2, getMidPointY() + scale(m_board.getTailWidth()) / 2);
        
        snoCADutilities.point xyToe = new snoCADutilities.point(getMidPointX() + scale(m_board.getSidecutBias()), (noseToeWidePoint.y + tailToeWidePoint.y) / 2 + scale(sidecutWidth));

        // Control snoCADutilities.point to achieve above rail path
        snoCADutilities.point sideCutToeMidControlPoint = snoCADutilities.getQuadraticControlPoint(noseToeWidePoint, xyToe, tailToeWidePoint);
        snoCADutilities.point a = new snoCADutilities.point(0,0);
        snoCADutilities.point b = new snoCADutilities.point(0,0);
        snoCADutilities.point c = new snoCADutilities.point(0,0);
        
        snoCADutilities.point projectedPoint = new snoCADutilities.point(0,0);
        
        m_Bg2d.setColor(java.awt.Color.red);
        m_Bg2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (m_board.getRailType() == snoCADutilities.QUADRATIC)
        {
        
            if (tail && toe) {
                // Tail toeside
                a = new snoCADutilities.point(noseToeWidePoint.x, noseToeWidePoint.y);
                b = new snoCADutilities.point(getMidPointX() + scale(m_board.getSidecutBias()), (noseToeWidePoint.y + tailToeWidePoint.y) / 2 + scale(sidecutWidth));
            
                if (m_board.getConvexRails()) {
                    b = new snoCADutilities.point(getMidPointX() + scale(m_board.getSidecutBias()), (noseToeWidePoint.y + tailToeWidePoint.y) / 2 - scale(sidecutWidth));
                }
                c = new snoCADutilities.point(tailToeWidePoint.x, tailToeWidePoint.y);
                sideCutMidControlPoint = snoCADutilities.getQuadraticControlPoint(a, b, c);
                projectedPoint = new snoCADutilities.point(0, 0);
                projectedPoint = snoCADutilities.extrapolate(sideCutMidControlPoint, tailToeWidePoint, 1);
               m_Bg2d.drawLine((int)sideCutMidControlPoint.x, (int)sideCutMidControlPoint.y, (int)projectedPoint.x, (int)projectedPoint.y);
            }
        
            if (nose && toe) {
                // Nose toeside
                a = new snoCADutilities.point(noseToeWidePoint.x, noseToeWidePoint.y);
                b = new snoCADutilities.point(getMidPointX() + scale(m_board.getSidecutBias()), (noseToeWidePoint.y + tailToeWidePoint.y) / 2 + scale(sidecutWidth));
            
                if (m_board.getConvexRails()) {
                    b = new snoCADutilities.point(getMidPointX() + scale(m_board.getSidecutBias()), (noseToeWidePoint.y + tailToeWidePoint.y) / 2 - scale(sidecutWidth));
                }
                c = new snoCADutilities.point(tailToeWidePoint.x, tailToeWidePoint.y);
                sideCutMidControlPoint = snoCADutilities.getQuadraticControlPoint(a, b, c);
                projectedPoint = new snoCADutilities.point(0, 0);
                projectedPoint = snoCADutilities.extrapolate(sideCutMidControlPoint, noseToeWidePoint, 1);
                m_Bg2d.drawLine((int)sideCutMidControlPoint.x, (int)sideCutMidControlPoint.y, (int)projectedPoint.x, (int)projectedPoint.y);
            }
        
            if (tail && heel) {
                // Tail heelside
                a = new snoCADutilities.point(noseHeelWidePoint.x, noseHeelWidePoint.y);
                b = new snoCADutilities.point(getMidPointX() + scale(m_board.getSidecutBias()), (noseHeelWidePoint.y + tailHeelWidePoint.y) / 2 - scale(sidecutWidth));
            
                if (m_board.getConvexRails()) {
                    b = new snoCADutilities.point(getMidPointX() + scale(m_board.getSidecutBias()), (noseHeelWidePoint.y + tailHeelWidePoint.y) / 2 + scale(sidecutWidth));
                }
            
                c = new snoCADutilities.point(tailHeelWidePoint.x, tailHeelWidePoint.y);
                sideCutMidControlPoint = snoCADutilities.getQuadraticControlPoint(a, b, c);
                projectedPoint = new snoCADutilities.point(0, 0);
                projectedPoint = snoCADutilities.extrapolate(sideCutMidControlPoint, tailHeelWidePoint, 1);
                m_Bg2d.drawLine((int)sideCutMidControlPoint.x, (int)sideCutMidControlPoint.y, (int)projectedPoint.x, (int)projectedPoint.y);
            }
        
            if (nose && heel) {
                // Nose heelside
                a = new snoCADutilities.point(noseHeelWidePoint.x, noseHeelWidePoint.y);
                b = new snoCADutilities.point(getMidPointX() + scale(m_board.getSidecutBias()), (noseHeelWidePoint.y + tailHeelWidePoint.y) / 2 - scale(sidecutWidth));
            
                if (m_board.getConvexRails()) {
                    b = new snoCADutilities.point(getMidPointX() + scale(m_board.getSidecutBias()), (noseHeelWidePoint.y + tailHeelWidePoint.y) / 2 + scale(sidecutWidth));
                }
                c = new snoCADutilities.point(tailHeelWidePoint.x, tailHeelWidePoint.y);
                sideCutMidControlPoint = snoCADutilities.getQuadraticControlPoint(a, b, c);
                projectedPoint = new snoCADutilities.point(0, 0);
                projectedPoint = snoCADutilities.extrapolate(sideCutMidControlPoint, noseHeelWidePoint, 1);
                m_Bg2d.drawLine((int)sideCutMidControlPoint.x, (int)sideCutMidControlPoint.y, (int)projectedPoint.x, (int)projectedPoint.y);
            }
        }
        
        if (m_board.getRailType() == snoCADutilities.MULTIPOINT_FREE)
        {
            if (nose && toe)
            {
                projectedPoint = new snoCADutilities.point(0, 0);
                snoCADutilities.point cp1 = new snoCADutilities.point(0,0);
                cp1.x = m_board.getRailGeometry().getEntry(0).getHandle().getX();
                cp1.y = m_board.getRailGeometry().getEntry(0).getHandle().getY();
                
                projectedPoint = snoCADutilities.extrapolate(cp1, noseToeWidePoint, 1);
                m_Bg2d.drawLine((int)noseToeWidePoint.x, (int)noseToeWidePoint.y, (int)projectedPoint.x, (int)projectedPoint.y);
            }

            if (tail && toe)
            {
                projectedPoint = new snoCADutilities.point(0, 0);
                snoCADutilities.point cp1 = new snoCADutilities.point(0,0);
                cp1.x = m_board.getRailGeometry().getEntry(4).getHandle().getX();
                cp1.y = m_board.getRailGeometry().getEntry(4).getHandle().getY();
                projectedPoint = snoCADutilities.extrapolate(cp1, tailToeWidePoint, 1);
                m_Bg2d.drawLine((int)tailToeWidePoint.x, (int)tailToeWidePoint.y, (int)projectedPoint.x, (int)projectedPoint.y);
            }
            

            if (m_board.getRailGeometry().getSize() > 0)
            {
                if (m_board.getRailGeometry().getEntry(1).getHandle().inUse())
                {
                    projectedPoint = new snoCADutilities.point(0, 0);
                    snoCADutilities.point cp1 = new snoCADutilities.point(0,0);
                    snoCADutilities.point cp2 = new snoCADutilities.point(0,0);
                    cp1.x = m_board.getRailGeometry().getEntry(2).getHandle().getX();
                    cp1.y = m_board.getRailGeometry().getEntry(2).getHandle().getY();
                    cp2.x = m_board.getRailGeometry().getEntry(3).getHandle().getX();
                    cp2.y = m_board.getRailGeometry().getEntry(3).getHandle().getY();
                    
                    projectedPoint = snoCADutilities.extrapolate(cp2, cp1, 1);
                    m_Bg2d.drawLine((int)cp1.x, (int)cp1.y, (int)projectedPoint.x, (int)projectedPoint.y);
                }
                
                if (m_board.getRailGeometry().getEntry(3).getHandle().inUse())
                {
                    projectedPoint = new snoCADutilities.point(0, 0);
                    snoCADutilities.point cp1 = new snoCADutilities.point(0,0);
                    snoCADutilities.point cp2 = new snoCADutilities.point(0,0);
                    cp1.x = m_board.getRailGeometry().getEntry(2).getHandle().getX();
                    cp1.y = m_board.getRailGeometry().getEntry(2).getHandle().getY();
                    cp2.x = m_board.getRailGeometry().getEntry(1).getHandle().getX();
                    cp2.y = m_board.getRailGeometry().getEntry(1).getHandle().getY();
                    
                    projectedPoint = snoCADutilities.extrapolate(cp2, cp1, 1);
                    m_Bg2d.drawLine((int)cp1.x, (int)cp1.y, (int)projectedPoint.x, (int)projectedPoint.y);
                }
            }
            
            /*
            for (int i = 0; i < m_board.getRailGeometry().getSize(); i++)
            {
                if (m_board.getRailGeometry().getEntry(i).getHandle().inUse())
                {
                    java.awt.geom.Path2D.Double railGuide = new java.awt.geom.Path2D.Double();
                    railGuide.moveTo(noseToeWidePoint.x, noseToeWidePoint.y);
                    railGuide.quadTo(sideCutToeMidControlPoint.x, sideCutToeMidControlPoint.y, tailToeWidePoint.x, tailToeWidePoint.y);
                    m_Bg2d.draw(railGuide);
                }
            }
             **/
        }
        
        m_Bg2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,java.awt.RenderingHints.VALUE_ANTIALIAS_OFF);
        
        
    }
    
    private void drawAxes() {
        int axisOvershoot = 10;
        
        m_Bg2d.setColor(java.awt.Color.darkGray);
        m_Bg2d.drawLine(0, getMidPointY(), getWidth(), getMidPointY());
        m_Bg2d.drawLine(getMidPointX(), 0, getMidPointX(), getHeight());
        m_Bg2d.drawLine(getMidPointX() - roundScale(m_board.getRunningLength() / 2), getMidPointY() + roundScale(m_board.getNoseWidth() / 2 + axisOvershoot),
                getMidPointX() - roundScale(m_board.getRunningLength() / 2), getMidPointY() - roundScale(m_board.getNoseWidth() / 2 + axisOvershoot));
        
        m_Bg2d.drawLine(getMidPointX() + roundScale(m_board.getRunningLength() / 2), getMidPointY() + roundScale(m_board.getTailWidth() / 2 + axisOvershoot),
                getMidPointX() + roundScale(m_board.getRunningLength() / 2), getMidPointY() - roundScale(m_board.getTailWidth() / 2 + axisOvershoot));
        
    }
    
    private double scale(int value) {
        return value / m_board.getScaleFactor();
    }
    
    private double scale(double value) {
        return value / m_board.getScaleFactor();
    }
    
    private int roundScale(int value) {
        return (int)Math.round(value / m_board.getScaleFactor());
    }
    
    
    
    private void drawGrid(java.awt.Graphics2D m_Bg2d, java.awt.Color color, int spacingRequirement) {
        // Major divisions
        
        int minSpacing = 5;
        
        double spacingWidth = scale(spacingRequirement);
        
        java.awt.geom.Path2D.Double p = new java.awt.geom.Path2D.Double();
        
        m_Bg2d.setColor(color);
        
        double i = getMidPointX();
        
        while (i < getWidth() + spacingWidth) {
            if (spacingWidth >= minSpacing) {
                p.moveTo(i - spacingWidth, 0);
                p.lineTo(i - spacingWidth,(double)getHeight());
            }
            
            i+= spacingWidth;
        }
        
        
        i = getMidPointX();
        
        while (i > 0 - spacingWidth) {
            if (spacingWidth >= minSpacing) {
                p.moveTo(i - spacingWidth, 0);
                p.lineTo(i - spacingWidth,(double)getHeight());
            }
            i-= spacingWidth;
        }
        
        i = getMidPointY();
        
        while (i <= getHeight() + spacingWidth) {
            if (spacingWidth >= minSpacing) {
                p.moveTo(0, i - spacingWidth);
                p.lineTo((double)getWidth(),i - spacingWidth);
            }
            
            i += spacingWidth;
        }
        
        i = getMidPointY();
        
        while (i >= 0 - spacingWidth) {
            if (spacingWidth >= minSpacing) {
                p.moveTo(0, i - spacingWidth);
                p.lineTo((double)getWidth(),i - spacingWidth);
            }
            i -= spacingWidth;
        }
        
        m_Bg2d.draw(p);
        
        
    }
    
    public int getMidPointX() { return m_drawingCentreX ; }
    public int getMidPointY() { return m_drawingCentreY ; }
    
    public void setSnowboard(snoCADboard board) {
        m_board = board;
        
        m_drawingCentreX = m_offscreenImage.getWidth() / 2;
        m_drawingCentreY = m_offscreenImage.getHeight() / 2;
        
        // Scale factor is in mm per pixel.
        // How long is the board ?
        
        double boardLength = 1.1 * m_board.getLength(); // plus 50mm either side
        
        // How many pixels are available
        int ourWidth = getWidth();
        
        double scaleFactor = boardLength / ourWidth;
        
        m_board.setScaleFactor(scaleFactor);
        centreDrawing();
        
        updateHandles();
        
    }
    
    
    public void centreDrawing() {
        m_drawingCentreX = getWidth() / 2;
        m_drawingCentreY = getHeight() / 2;
        
    }
    
    public void originCentre() {
        m_drawingCentreX = getWidth() / 2;
        m_drawingCentreY = getHeight() / 2;
        
    }
    
    public void centreDrawing(double x, double y) {
        centreDrawing();
        
        m_drawingCentreX += x;
        m_drawingCentreY += y;
    }
    
    public void centreTargeted(double x, double y) {
        //centreDrawing();
        
        m_drawingCentreX = (int)x;
        m_drawingCentreY = (int)y;
    }
    
    
    private snoCADboard m_board;
    private int m_toolbarOffset;
    
    private int m_drawingCentreX;
    private int m_drawingCentreY;
    
    private int m_mousePositionX;
    private int m_mousePositionY;
    
    private snoCADgeometryManipulator m_noseTipHandle;
    private snoCADgeometryManipulator m_tailTipHandle;
    private snoCADgeometryManipulator m_toeSideCutHandle;
    private snoCADgeometryManipulator m_heelSideCutHandle;
    private snoCADgeometryManipulator m_noseWidthHandleToe;
    private snoCADgeometryManipulator m_noseWidthHandleHeel;
    private snoCADgeometryManipulator m_tailWidthHandleToe;
    private snoCADgeometryManipulator m_tailWidthHandleHeel;
    
    private snoCADgeometryManipulator m_noseRunningLengthHandle;
    private snoCADgeometryManipulator m_tailRunningLengthHandle;
    
    private snoCADgeometryManipulator m_frontPackStanceWidthHandle;
    private snoCADgeometryManipulator m_rearPackStanceWidthHandle;
    private snoCADgeometryManipulator m_stanceOffsetHandle;
    
    private snoCADgeometryManipulator m_noseCubicHandle_1;
    private snoCADgeometryManipulator m_noseCubicHandle_2;
    private snoCADgeometryManipulator m_noseCubicHandle_3;
    private snoCADgeometryManipulator m_noseCubicHandle_4;
    
    private snoCADgeometryManipulator m_tailCubicHandle_1;
    private snoCADgeometryManipulator m_tailCubicHandle_2;
    private snoCADgeometryManipulator m_tailCubicHandle_3;
    private snoCADgeometryManipulator m_tailCubicHandle_4;
    
    private snoCADgeometryManipulator m_frontPackRowCountHandle;
    private snoCADgeometryManipulator m_frontPackInsertSpacingHandle;
    private snoCADgeometryManipulator m_rearPackRowCountHandle;
    private snoCADgeometryManipulator m_rearPackInsertSpacingHandle;
    
    private snoCADgeometryManipulator m_noseRadiusHandle;
    private snoCADgeometryManipulator m_tailRadiusHandle;
    private snoCADgeometryManipulator m_camberHandle;
    private snoCADgeometryManipulator m_camberSetbackHandle;
    
    private snoCADgeometryManipulator m_noseThicknessHandle;
    private snoCADgeometryManipulator m_frontPackOutboardThicknessHandle;
    private snoCADgeometryManipulator m_frontPackInboardThicknessHandle;
    private snoCADgeometryManipulator m_midPointThicknessHandle;
    private snoCADgeometryManipulator m_rearPackInboardThicknessHandle;
    private snoCADgeometryManipulator m_rearPackOutboardThicknessHandle;
    private snoCADgeometryManipulator m_tailThicknessHandle;
    
    private snoCADstatsPanel m_statsPanel;
    private snoCADgraphicsPanel m_graphicsPanel;
    private snoCADrailShaper m_railShaper;
    
    private static java.awt.image.BufferedImage m_offscreenImage;
    private static java.awt.Graphics2D m_Bg2d;
    
    public void setGraphicsContext(java.awt.Graphics2D context) { m_Bg2d = context;}
    public java.awt.Graphics2D getGraphicsContext() { return m_Bg2d;}
    
    private int m_boardBoundsX;
    private int m_boardBoundsY;
    private int m_boardBoundsWidth;
    private int m_boardBoundsHeight;
    
    private boolean m_renderBoard;
    private boolean m_highlights;
    private boolean m_showGrid;
    private boolean m_showAxes;
    private boolean m_measuring;
    private int m_measuringEndPointX;
    private int m_measuringEndPointY;
    
    private boolean m_drawMaxMin;
    public void setDrawMaxMin(boolean val) { m_drawMaxMin = val;}
    
    private int m_editingMode;
    public void setEditingMode(int mode) { m_editingMode = mode;}
    
    private java.awt.geom.Path2D.Double m_noseInterfacePath;
    private java.awt.geom.Path2D.Double m_tailInterfacePath;
    private java.awt.geom.Path2D.Double m_toeRailPath;
    private java.awt.geom.Path2D.Double m_heelRailPath;
    
    private double m_coreNoseCalculatedLength;
    private double m_coreTailCalculatedLength;
    
    public double getCoreNoseCalculatedLength() { return m_coreNoseCalculatedLength;}
    public double getCoreTailCalculatedLength() { return m_coreTailCalculatedLength;}
    
    public java.awt.geom.Path2D.Double getNoseInterfacePath() { return m_noseInterfacePath;}
    public java.awt.geom.Path2D.Double getTailInterfacePath() { return m_tailInterfacePath;}
    public java.awt.geom.Path2D.Double getToeRailPath() { return m_toeRailPath;}
    public java.awt.geom.Path2D.Double getHeelRailPath() { return m_heelRailPath;}
    
    // private static java.awt.Image m_offscreen;
    
    public snoCADgeometryManipulator getnoseRunningLengthHandle() { return  m_noseRunningLengthHandle;}
    public snoCADgeometryManipulator gettailRunningLengthHandle() { return  m_tailRunningLengthHandle;}
    public snoCADgeometryManipulator getfrontPackStanceWidthHandle() { return  m_frontPackStanceWidthHandle;}
    public snoCADgeometryManipulator getrearPackStanceWidthHandle() { return  m_rearPackStanceWidthHandle;}
    public snoCADgeometryManipulator getstanceOffsetHandleHandle() { return  m_stanceOffsetHandle;}
    
    public snoCADboard getBoard() { return m_board;}
    
    public void setRenderBoard(boolean value) {m_renderBoard = value;}
    public void setHightlights(boolean value) {m_highlights = value;}
    public void setGrid(boolean value) { m_showGrid = value;}
    public void setAxes(boolean value) { m_showAxes = value;}
    
    public void setStatsPanel(snoCADstatsPanel s) { m_statsPanel = s;}
    public void setGraphicsPanel(snoCADgraphicsPanel g) { m_graphicsPanel = g;}
    
    public void setRailShaper(snoCADrailShaper s) { m_railShaper = s;}
    
    public void setToolbarOffset(int toolbarOffset) { m_toolbarOffset = toolbarOffset;}
    
    private int getHandleMovedDistance(int old, int now) {
        double delta = old - now;
        
        return (int)Math.round(delta * m_board.getScaleFactor());
        
    }
    
    private void antiAlias(boolean on)
    {
            
        if (on)
        {
            m_Bg2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        }
        else
        {
             m_Bg2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,java.awt.RenderingHints.VALUE_ANTIALIAS_OFF);
        }
    }
    
    public void getWidthAtPoint()
    {
        double width = 0;
        double ratio = 0.5;
        
        snoCADutilities.point p0 = new snoCADutilities.point(m_noseWidthHandleToe.getX(), m_noseWidthHandleToe.getY());
        snoCADutilities.point p1 = new snoCADutilities.point(m_board.getRailGeometry().getEntry(0).getHandle().getX(), m_board.getRailGeometry().getEntry(0).getHandle().getY());
        snoCADutilities.point p2 = new snoCADutilities.point(m_board.getRailGeometry().getEntry(1).getHandle().getX(), m_board.getRailGeometry().getEntry(1).getHandle().getY());
        snoCADutilities.point p3 = new snoCADutilities.point(m_board.getRailGeometry().getEntry(2).getHandle().getX(), m_board.getRailGeometry().getEntry(2).getHandle().getY());
        snoCADutilities.point p4 = new snoCADutilities.point(m_tailWidthHandleToe.getX(), m_tailWidthHandleToe.getY());
        
        double maxWidth = 0;
        double maxWidthPercent = 0;
        double maxX = 0;
        double maxY = 0;
        
        double minWidth = 99999999;
        double minWidthPercent = 0;
        double minX = 0;
        double minY = 0;
        
        
        for (double i = 0; i <= 1; i+= 0.01)
        {
        
            ratio = i;
            
            snoCADutilities.point q0 = snoCADutilities.getPointOnSegment(p0, p1, ratio);
            snoCADutilities.point q1 = snoCADutilities.getPointOnSegment(p1, p2, ratio);
            snoCADutilities.point q2 = snoCADutilities.getPointOnSegment(p2, p3, ratio);
        
            snoCADutilities.point r0 = snoCADutilities.getPointOnSegment(q0, q1, ratio);
            snoCADutilities.point r1 = snoCADutilities.getPointOnSegment(q1, q2, ratio);
        
            snoCADutilities.point m1 = snoCADutilities.getPointOnSegment(r0, r1, ratio);
            
            double widthAtM1 = 2 * (m_board.getScaleFactor() * (getMidPointY() - m1.y));
            
            if (widthAtM1 > maxWidth) 
            {
                maxWidth = widthAtM1;
                maxX = m1.x;
                maxY = m1.y;  
                maxWidthPercent = (100 / (p4.x - p0.x)) * (m1.x - p0.x);
            }
            
            if (widthAtM1 < minWidth) 
            {
                minWidth = widthAtM1;
                minX = m1.x;
                minY = m1.y;  
                minWidthPercent = (100 / (p4.x - p0.x)) * (m1.x - p0.x);
            }
            
        }
        
                
        double maxInMM = (m_board.getRunningLength() / 100) * maxWidthPercent;
        double minInMM = (m_board.getRunningLength() / 100) * minWidthPercent;

        if (m_drawMaxMin)
        {
        m_Bg2d.setColor(java.awt.Color.white);

        java.awt.Font font = new java.awt.Font("Arial", java.awt.Font.PLAIN, 10);
        m_Bg2d.setFont(font);
        antiAlias(true);
        
        if (maxWidthPercent > 0)
        {
                    
            m_Bg2d.drawLine((int)maxX - 2, (int)maxY, (int)maxX + 2, (int)maxY);
            m_Bg2d.drawLine((int)maxX , (int)maxY - 2, (int)maxX, (int)maxY+ 2);
            m_Bg2d.drawLine((int)maxX, (int)maxY - 5, (int) maxX, (int)maxY - roundScale(100));
            m_Bg2d.drawString("Max Width Nose : " + snoCADutilities.formatDouble(maxWidth) + "mm", (int)maxX + 2, (int) maxY - roundScale(100));
            m_Bg2d.drawString("Location : " + snoCADutilities.formatDouble(maxInMM) + "mm", (int)maxX + 2, (int) maxY - roundScale(70));
        }
        
         if (minWidthPercent > 0)
        {
            m_Bg2d.drawLine((int)minX - 2, (int)minY, (int)minX + 2, (int)minY);
            m_Bg2d.drawLine((int)minX , (int)minY - 2, (int)minX, (int)minY+ 2);
            m_Bg2d.drawLine((int)minX, (int)minY - 5, (int) minX, (int)minY - roundScale(100));
            m_Bg2d.drawString("Min Width Nose : " + snoCADutilities.formatDouble(minWidth) + "mm", (int)minX + 2, (int) minY - roundScale(100));
            m_Bg2d.drawString("Location : " + snoCADutilities.formatDouble(minInMM) + "mm", (int)minX + 2, (int) minY - roundScale(70));
        }
        
        antiAlias(false);
        }
        
      

    }
    
    
    // ---- TAIL
    
     public void getWidthAtPointTail()
    {
        double width = 0;
        double ratio = 0.5;
        
        snoCADutilities.point p0 = new snoCADutilities.point(m_noseWidthHandleToe.getX(), m_noseWidthHandleToe.getY());
        snoCADutilities.point p1 = new snoCADutilities.point(m_board.getRailGeometry().getEntry(2).getHandle().getX(), m_board.getRailGeometry().getEntry(2).getHandle().getY());
        snoCADutilities.point p2 = new snoCADutilities.point(m_board.getRailGeometry().getEntry(3).getHandle().getX(), m_board.getRailGeometry().getEntry(3).getHandle().getY());
        snoCADutilities.point p3 = new snoCADutilities.point(m_board.getRailGeometry().getEntry(4).getHandle().getX(), m_board.getRailGeometry().getEntry(4).getHandle().getY());
        snoCADutilities.point p4 = new snoCADutilities.point(m_tailWidthHandleToe.getX(), m_tailWidthHandleToe.getY());
        
        double maxWidth = 0;
        double maxWidthPercent = 0;
        double maxX = 0;
        double maxY = 0;
        
        double minWidth = 99999999;
        double minWidthPercent = 0;
        double minX = 0;
        double minY = 0;
        
        
        for (double i = 0; i <= 1; i+= 0.01)
        {
        
            ratio = i;
            
            snoCADutilities.point q0 = snoCADutilities.getPointOnSegment(p1, p2, ratio);
            snoCADutilities.point q1 = snoCADutilities.getPointOnSegment(p2, p3, ratio);
            snoCADutilities.point q2 = snoCADutilities.getPointOnSegment(p3, p4, ratio);
        
            snoCADutilities.point r0 = snoCADutilities.getPointOnSegment(q0, q1, ratio);
            snoCADutilities.point r1 = snoCADutilities.getPointOnSegment(q1, q2, ratio);
        
            snoCADutilities.point m1 = snoCADutilities.getPointOnSegment(r0, r1, ratio);
            
            double widthAtM1 = 2 * (m_board.getScaleFactor() * (getMidPointY() - m1.y));
            
            if (widthAtM1 > maxWidth) 
            {
                maxWidth = widthAtM1;
                maxX = m1.x;
                maxY = m1.y;  
                maxWidthPercent = (100 / (p4.x - p0.x)) * (m1.x - p0.x);
            }
            
            if (widthAtM1 < minWidth) 
            {
                minWidth = widthAtM1;
                minX = m1.x;
                minY = m1.y;  
                minWidthPercent = (100 / (p4.x - p0.x)) * (m1.x - p0.x);
            }
            
        }
        
                
        double maxInMM = (m_board.getRunningLength() / 100) * maxWidthPercent;
        double minInMM = (m_board.getRunningLength() / 100) * minWidthPercent;

        
        if (m_drawMaxMin)
        {
        m_Bg2d.setColor(java.awt.Color.orange);

        java.awt.Font font = new java.awt.Font("Arial", java.awt.Font.PLAIN, 10);
        m_Bg2d.setFont(font);
        
        antiAlias(true);
        
        if (maxWidthPercent > 50)
        {
            m_Bg2d.drawLine((int)maxX - 2, (int)maxY, (int)maxX + 2, (int)maxY);
            m_Bg2d.drawLine((int)maxX , (int)maxY - 2, (int)maxX, (int)maxY+ 2);
            m_Bg2d.drawLine((int)maxX, (int)maxY - 5, (int) maxX, (int)maxY - roundScale(150));
            m_Bg2d.drawString("Max Width Tail : " + snoCADutilities.formatDouble(maxWidth) + "mm", (int)maxX + 2, (int) maxY - roundScale(150));
            m_Bg2d.drawString("Location : " + snoCADutilities.formatDouble(maxInMM) + "mm", (int)maxX + 2, (int) maxY - roundScale(120));
        }
        
         if (minWidthPercent > 50)
        {
            m_Bg2d.drawLine((int)minX - 2, (int)minY, (int)minX + 2, (int)minY);
            m_Bg2d.drawLine((int)minX , (int)minY - 2, (int)minX, (int)minY+ 2);
            m_Bg2d.drawLine((int)minX, (int)minY - 5, (int) minX, (int)minY - roundScale(150));
            m_Bg2d.drawString("Min Width Tail : " + snoCADutilities.formatDouble(minWidth) + "mm", (int)minX + 2, (int) minY - roundScale(150));
            m_Bg2d.drawString("Location : " + snoCADutilities.formatDouble(minInMM) + "mm", (int)minX + 2, (int) minY - roundScale(120));
        }
        
        antiAlias(false);
        }
        
      

    }
    
    public void updateStatsPanel() {
        m_statsPanel.updateStats();
    }
    
    public void updateGraphicsPanel() {
        
        m_graphicsPanel.setTree(m_board.getGraphics());
    }
    
    
    
    private class MouseMotionHandler extends java.awt.event.MouseMotionAdapter {
        
       
        public void mouseDragged(java.awt.event.MouseEvent e) {
            
            m_measuring = false;
            m_drawMaxMin = false;
            
            String modifiers = e.getMouseModifiersText(e.getModifiers());
            
            if (modifiers.indexOf("Shift") < 0) {
                if (m_noseTipHandle.inUse()) {
                    
                    int noseLength = m_board.getNoseLength() + getHandleMovedDistance(m_mousePositionX, e.getX()) ;
                    
                    m_board.setNoseLength(noseLength);
                    
                    if (m_board.getTipspacerType() == snoCADutilities.SIDEWALL || m_board.getTipspacerType() == snoCADutilities.INTERLOCK) m_board.setCoreNoseLength(noseLength - m_board.getSidewallWidth() - m_board.getTipSidewallOffset());
                    if (m_board.getNoseTipspacerMaterialLength() < noseLength) m_board.setNoseTipSpacerMaterialLength(noseLength);
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                }
                
                if (m_tailTipHandle.inUse()) {
                    
                    int tailLength =  m_board.getTailLength() + getHandleMovedDistance(e.getX(), m_mousePositionX) ;
                    m_board.setTailLength(tailLength);
                    
                    if (m_board.getTipspacerType() == snoCADutilities.SIDEWALL || m_board.getTipspacerType() == snoCADutilities.INTERLOCK) m_board.setCoreTailLength(tailLength - m_board.getSidewallWidth() - m_board.getTipSidewallOffset());
                    if (m_board.getTailTipspacerMaterialLength() < tailLength) m_board.setTailTipSpacerMaterialLength(tailLength);
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                }
                
                if (m_noseRunningLengthHandle.inUse()) {
                    
                    int runningLength =  m_board.getRunningLength() +  getHandleMovedDistance(m_mousePositionX, e.getX()) ;
                    m_board.setRunningLength(runningLength);
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                }
                
                if (m_tailRunningLengthHandle.inUse()) {
                    int runningLength =  m_board.getRunningLength() + getHandleMovedDistance(e.getX(), m_mousePositionX  ) ;
                    m_board.setRunningLength(runningLength);
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                }
                
                if (m_noseWidthHandleToe.inUse() ) {
                    
                    
                    int noseWidth = m_board.getNoseWidth() - getHandleMovedDistance(e.getY(), m_mousePositionY  ) ;;
                    m_board.setNoseWidth(noseWidth);
                    
                    int min_nose = ((m_board.getNoseWidth() - (m_board.getSidewallWidth() * 2)) / 2);
                    if (m_board.getNoseTipspacerRadius() < min_nose) m_board.setNoseTipspacerRadius(min_nose);
                    if (m_board.getNoseTipspacerMaterialWidth() < noseWidth) m_board.setNoseTipSpacerMaterialWidth(noseWidth);
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                }
                
                if (m_noseWidthHandleHeel.inUse() ) {
                    
                    int noseWidth = m_board.getNoseWidth() + + getHandleMovedDistance(e.getY(), m_mousePositionY ) ;
                    m_board.setNoseWidth(noseWidth);
                    int min_nose = ((m_board.getNoseWidth() - (m_board.getSidewallWidth() * 2)) / 2);
                    if (m_board.getNoseTipspacerRadius() < min_nose) m_board.setNoseTipspacerRadius(min_nose);
                    if (m_board.getNoseTipspacerMaterialWidth() < noseWidth) m_board.setNoseTipSpacerMaterialWidth(noseWidth);
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    updateStatsPanel();
                    return;
                }
                
                if (m_tailWidthHandleToe.inUse() ) {
                    int tailWidth = m_board.getTailWidth() - getHandleMovedDistance(e.getY(), m_mousePositionY  ) ;
                    m_board.setTailWidth(tailWidth);
                    int min_tail = ((m_board.getTailWidth() - (m_board.getSidewallWidth() * 2)) / 2);
                    if (m_board.getTailTipspacerRadius() < min_tail) m_board.setTailTipspacerRadius(min_tail);
                    if (m_board.getTailTipspacerMaterialWidth() < tailWidth) m_board.setTailTipSpacerMaterialWidth(tailWidth);
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                }
                
                if (m_tailWidthHandleHeel.inUse() ) {
                    
                    int tailWidth = m_board.getTailWidth() + getHandleMovedDistance(e.getY(), m_mousePositionY  ) ;;
                    m_board.setTailWidth(tailWidth);
                    int min_tail = ((m_board.getTailWidth() - (m_board.getSidewallWidth() * 2)) / 2);
                    if (m_board.getTailTipspacerRadius() < min_tail) m_board.setTailTipspacerRadius(min_tail);
                    if (m_board.getTailTipspacerMaterialWidth() < tailWidth) m_board.setTailTipSpacerMaterialWidth(tailWidth);
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                }
                
                if (m_frontPackStanceWidthHandle.inUse() ) {
                    
                    int stanceWidth = m_board.getStanceWidth() - getHandleMovedDistance(e.getX(), m_mousePositionX  ) ;
                    
                    if (stanceWidth <= 0) stanceWidth = 0;
                    
                    m_board.setStanceWidth(stanceWidth);
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                }
                
                if (m_rearPackStanceWidthHandle.inUse() ) {
                    
                    int stanceWidth = m_board.getStanceWidth() + getHandleMovedDistance(e.getX(), m_mousePositionX  ) ;
                    if (stanceWidth <= 0) stanceWidth = 0;
                    
                    m_board.setStanceWidth(stanceWidth);
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                }
                
                if (m_stanceOffsetHandle.inUse() ) {
                    
                    int stanceSetback = m_board.getStanceSetback() + getHandleMovedDistance(e.getX(), m_mousePositionX  ) ;
                    m_board.setStanceSetback(stanceSetback);
                    
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                }
                
                if (m_toeSideCutHandle.inUse() ) {
                    
                    int sideCutRadius =  m_board.getSidecutRadius() - 50 * (getHandleMovedDistance(e.getY(), m_mousePositionY  )) ;
                    
                    if (m_board.getConvexRails()) sideCutRadius = m_board.getSidecutRadius() + 50 * (getHandleMovedDistance(e.getY(), m_mousePositionY  )) ;
                    
                    if (sideCutRadius < 1000) sideCutRadius = 1000;
                    if (sideCutRadius > 46000) sideCutRadius = 46000;
                    
                    m_board.setSidecutRadius(sideCutRadius);
                    
                    int sideCutBias = m_board.getSidecutBias() + getHandleMovedDistance(e.getX(), m_mousePositionX  ) ;
                    
                    
                    m_board.setSidecutBias(sideCutBias);
                    
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                }
                
                if (m_heelSideCutHandle.inUse() ) {
                    int sideCutRadius =  m_board.getSidecutRadius() + 50 * (getHandleMovedDistance(e.getY(), m_mousePositionY  )) ;
                    
                    if (m_board.getConvexRails()) sideCutRadius = m_board.getSidecutRadius() - 50 * (getHandleMovedDistance(e.getY(), m_mousePositionY  )) ;
                    
                    if (sideCutRadius < 1000) sideCutRadius = 1000;
                    if (sideCutRadius > 46000) sideCutRadius = 46000;
                    
                    m_board.setSidecutRadius(sideCutRadius);
                    
                    int sideCutBias = m_board.getSidecutBias() + getHandleMovedDistance(e.getX(), m_mousePositionX  ) ;
                    m_board.setSidecutBias(sideCutBias);
                    
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                }
                
                if (m_noseCubicHandle_1.inUse()) {
                    
                    int nbx1 = m_board.getNoseBezier1Xfactor() - getHandleMovedDistance(e.getX(), m_mousePositionX  );
                    int nby1 = m_board.getNoseBezier1Yfactor() - getHandleMovedDistance(e.getY(), m_mousePositionY  );
                    
                    m_board.setNoseBezier1Xfactor(nbx1);
                    m_board.setNoseBezier1Yfactor(nby1);
                    
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                    
                }
                
                if (m_noseCubicHandle_2.inUse()) {
                    
                    int nbx1 = m_board.getNoseBezier2Xfactor() + getHandleMovedDistance(e.getX(), m_mousePositionX  );
                    int nby1 = m_board.getNoseBezier2Yfactor() - getHandleMovedDistance(e.getY(), m_mousePositionY  );
                    
                    m_board.setNoseBezier2Xfactor(nbx1);
                    m_board.setNoseBezier2Yfactor(nby1);
                    
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                    
                }
                
                
                
                
                if (m_noseCubicHandle_3.inUse()) {
                    
                    int nbx1 = m_board.getNoseBezier1Xfactor() - getHandleMovedDistance(e.getX(), m_mousePositionX  );
                    int nby1 = m_board.getNoseBezier1Yfactor() + getHandleMovedDistance(e.getY(), m_mousePositionY  );
                    
                    m_board.setNoseBezier1Xfactor(nbx1);
                    m_board.setNoseBezier1Yfactor(nby1);
                    
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                    
                }
                
                if (m_noseCubicHandle_4.inUse()) {
                    
                    int nbx1 = m_board.getNoseBezier2Xfactor() + getHandleMovedDistance(e.getX(), m_mousePositionX  );
                    int nby1 = m_board.getNoseBezier2Yfactor() + getHandleMovedDistance(e.getY(), m_mousePositionY  );
                    
                    m_board.setNoseBezier2Xfactor(nbx1);
                    m_board.setNoseBezier2Yfactor(nby1);
                    
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                    
                }
                
                if (m_tailCubicHandle_1.inUse()) {
                    
                    
                    int nbx1 = m_board.getTailBezier1Xfactor() + getHandleMovedDistance(e.getX(), m_mousePositionX  );
                    int nby1 = m_board.getTailBezier1Yfactor() - getHandleMovedDistance(e.getY(), m_mousePositionY  );
                    
                    m_board.setTailBezier1Xfactor(nbx1);
                    m_board.setTailBezier1Yfactor(nby1);
                    
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                    
                }
                
                if (m_tailCubicHandle_2.inUse()) {
                    
                    int nbx1 = m_board.getTailBezier2Xfactor() + getHandleMovedDistance(e.getX(), m_mousePositionX  );
                    int nby1 = m_board.getTailBezier2Yfactor() - getHandleMovedDistance(e.getY(), m_mousePositionY  );
                    
                    m_board.setTailBezier2Xfactor(nbx1);
                    m_board.setTailBezier2Yfactor(nby1);
                    
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                    
                }
                
                
                if (m_tailCubicHandle_3.inUse()) {
                    int nbx1 = m_board.getTailBezier1Xfactor() + getHandleMovedDistance(e.getX(), m_mousePositionX  );
                    int nby1 = m_board.getTailBezier1Yfactor() + getHandleMovedDistance(e.getY(), m_mousePositionY  );
                    
                    m_board.setTailBezier1Xfactor(nbx1);
                    m_board.setTailBezier1Yfactor(nby1);
                    
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                    
                }
                
                if (m_tailCubicHandle_4.inUse()) {
                    
                    int nbx1 = m_board.getTailBezier2Xfactor() + getHandleMovedDistance(e.getX(), m_mousePositionX  );
                    int nby1 = m_board.getTailBezier2Yfactor() + getHandleMovedDistance(e.getY(), m_mousePositionY  );
                    
                    m_board.setTailBezier2Xfactor(nbx1);
                    m_board.setTailBezier2Yfactor(nby1);
                    
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                    
                }
                
                if (m_frontPackInsertSpacingHandle.inUse()) {
                    
                    int frontPackInsertSpacing = m_board.getFrontPackInsertSpacing() - getHandleMovedDistance(m_mousePositionX, e.getX()) ;
                    if (frontPackInsertSpacing < 0) frontPackInsertSpacing = 0;
                    m_board.setFrontPackInsertSpacing(frontPackInsertSpacing);
                    
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                }
                
                if (m_rearPackInsertSpacingHandle.inUse()) {
                    
                    int rearPackInsertSpacing = m_board.getRearPackInsertSpacing() - getHandleMovedDistance(m_mousePositionX, e.getX()) ;
                    if (rearPackInsertSpacing < 0) rearPackInsertSpacing = 0;
                    m_board.setRearPackInsertSpacing(rearPackInsertSpacing);
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                }
                
                if (m_frontPackRowCountHandle.inUse()) {
                    
                    int frontPackRowCount = m_board.getFrontPackRowCount() - getHandleMovedDistance(m_mousePositionX, e.getX() ) ;
                    if (frontPackRowCount < 0) frontPackRowCount = 0;
                    m_board.setFrontPackRowCount(frontPackRowCount);
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                }
                
                if (m_rearPackRowCountHandle.inUse()) {
                    
                    int rearPackRowCount = m_board.getRearPackRowCount() - getHandleMovedDistance(m_mousePositionX, e.getX( ) ) ;
                    if (rearPackRowCount < 0) rearPackRowCount = 0;
                    m_board.setRearPackRowCount(rearPackRowCount);
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                }
                
                if (m_camberHandle.inUse()) {
                    
                    int camber = m_board.getCamber() + getHandleMovedDistance(m_mousePositionY, e.getY()) ;
                    
                    m_board.setCamber(camber);
                    
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                }
                
                if (m_noseThicknessHandle.inUse()) {
                    
                    double thickness = m_board.getNoseThickness() + (getHandleMovedDistance(m_mousePositionY, e.getY()) * 0.1) ;
                    if (thickness < 0) thickness = 0;
                    m_board.setNoseThickness(thickness);
                    
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                }
                
                if (m_tailThicknessHandle.inUse()) {
                    
                    double thickness = m_board.getTailThickness() + (getHandleMovedDistance(m_mousePositionY, e.getY()) * 0.1) ;
                    if (thickness < 0) thickness = 0;
                    m_board.setTailThickness(thickness);
                    
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                }
                
                if (m_frontPackOutboardThicknessHandle.inUse()) {
                    
                    double thickness = m_board.getFrontPackThickness() + (getHandleMovedDistance(m_mousePositionY, e.getY()) * 0.1) ;
                    int ordinate = m_board.getFrontPackOutboard() - getHandleMovedDistance(m_mousePositionX, e.getX());
                    if (thickness < 0) thickness = 0;
                    
                    m_board.setFrontPackThickness(thickness);
                    m_board.setFrontPackOutboard(ordinate);
                    
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                }
                
                if (m_frontPackInboardThicknessHandle.inUse()) {
                    
                    double thickness = m_board.getFrontPackThickness() + (getHandleMovedDistance(m_mousePositionY, e.getY()) * 0.1) ;
                    int ordinate = m_board.getFrontPackInboard() - getHandleMovedDistance(m_mousePositionX, e.getX());
                    if (thickness < 0) thickness = 0;
                    
                    m_board.setFrontPackThickness(thickness);
                    m_board.setFrontPackInboard(ordinate);
                    
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                }
                
                if (m_rearPackInboardThicknessHandle.inUse()) {
                    
                    double thickness = m_board.getRearPackThickness() + (getHandleMovedDistance(m_mousePositionY, e.getY()) * 0.1) ;
                    int ordinate = m_board.getRearPackInboard() + getHandleMovedDistance(m_mousePositionX, e.getX());
                    if (thickness < 0) thickness = 0;
                    
                    m_board.setRearPackThickness(thickness);
                    m_board.setRearPackInboard(ordinate);
                    
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                }
                
                if (m_rearPackOutboardThicknessHandle.inUse()) {
                    
                    double thickness = m_board.getRearPackThickness() + (getHandleMovedDistance(m_mousePositionY, e.getY()) * 0.1) ;
                    int ordinate = m_board.getRearPackOutboard() + getHandleMovedDistance(m_mousePositionX, e.getX());
                    if (thickness < 0) thickness = 0;
                    
                    m_board.setRearPackThickness(thickness);
                    m_board.setRearPackOutboard(ordinate);
                    
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                }
                
                if (m_midPointThicknessHandle.inUse()) {
                    
                    double thickness = m_board.getMidPackThickness() + (getHandleMovedDistance(m_mousePositionY, e.getY()) * 0.1) ;
                    int ordinate = m_board.getMidPackOrd() - getHandleMovedDistance(m_mousePositionX, e.getX());
                    if (thickness < 0) thickness = 0;
                    
                    m_board.setMidPackThickness(thickness);
                    m_board.setMidPackOrd(ordinate);
                    
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                }
                
                if (m_camberSetbackHandle.inUse()) {
                    
                    int camberSetback = m_board.getCamberSetback() - getHandleMovedDistance(m_mousePositionX, e.getX()) ;
                    
                    m_board.setCamberSetback(camberSetback);
                    
                    
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                }
                
                if (m_noseRadiusHandle.inUse()) {
                    
                    int noseRadius = m_board.getNoseRadius() + getHandleMovedDistance(m_mousePositionX, e.getX()) ;
                    
                    m_board.setNoseRadius(noseRadius);
                    
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                }
                
                if (m_tailRadiusHandle.inUse()) {
                    
                    int tailRadius = m_board.getTailRadius() - getHandleMovedDistance(m_mousePositionX, e.getX()) ;
                    
                    m_board.setTailRadius(tailRadius);
                    
                    updateHandles();
                    
                    m_mousePositionX = e.getX();
                    m_mousePositionY = e.getY();
                    
                    repaint();
                    updateStatsPanel();
                    return;
                }
                
                if (m_board.getRailGeometry().getSize() > 0)
                {
                    for (int i = 0; i < m_board.getRailGeometry().getSize(); i++)
                    {
                        if (m_board.getRailGeometry().getEntry(i).getHandle().inUse())
                        {
                            
                            double valueX = ((m_board.getRailGeometry().getEntry(i).getRailPercent()) - (double)getHandleMovedDistance(m_mousePositionX, e.getX()) / 10) ;
                            double valueY = m_board.getRailGeometry().getEntry(i).getWidth() + getHandleMovedDistance(m_mousePositionY, e.getY());
                            
                            m_board.getRailGeometry().getEntry(i).setRailPercent(valueX);
                            m_board.getRailGeometry().getEntry(i).setWidth(valueY);

                            updateHandles();
                            
                            m_mousePositionX = e.getX();
                            m_mousePositionY = e.getY();
                            m_drawMaxMin = true;
                            repaint();
                            updateStatsPanel();
                            return;

                        }
                    }
                }
                
                if (m_board.getGraphics().getSize() > 0) {
                    for (int i = 0; i < m_board.getGraphics().getSize(); i++) {
                        
                        if (m_board.getGraphics().getEntry(i).getDragger().inUse()) {
                            
                            int valueX =  m_board.getGraphics().getEntry(i).getPosX() - getHandleMovedDistance(m_mousePositionX, e.getX( ) ) ;
                            int valueY =  m_board.getGraphics().getEntry(i).getPosY() - getHandleMovedDistance(m_mousePositionY, e.getY( ) ) ;

                            m_board.getGraphics().getEntry(i).setPosX(valueX);
                            m_board.getGraphics().getEntry(i).setPosY(valueY);
                            
                            updateHandles();
                            
                            m_mousePositionX = e.getX();
                            m_mousePositionY = e.getY();
                            
                            repaint();
                            updateStatsPanel();
                            return;
                        }
                        
                        if (m_board.getGraphics().getEntry(i).getStretcher().inUse()) {
                            
                            int valueX =  m_board.getGraphics().getEntry(i).getWidth() - getHandleMovedDistance(m_mousePositionX, e.getX( ) ) ;
                            int valueY =  m_board.getGraphics().getEntry(i).getHeight() - getHandleMovedDistance(m_mousePositionY, e.getY( ) ) ;
                            
                            
                            m_board.getGraphics().getEntry(i).setWidth(valueX);
                            m_board.getGraphics().getEntry(i).setHeight(valueY);
                            
                            updateHandles();
                            
                            m_mousePositionX = e.getX();
                            m_mousePositionY = e.getY();
                            
                            repaint();
                            updateStatsPanel();
                            return;
                        }
                        
                          if (m_board.getGraphics().getEntry(i).getRotator().inUse()) {
                            
                            int valueX =  0 - getHandleMovedDistance(m_mousePositionX, e.getX( ) ) ;
                            //int valueY =  m_board.getGraphics().getEntry(i).getHeight() - getHandleMovedDistance(m_mousePositionY, e.getY( ) ) ;
                            
                            
                            m_board.getGraphics().getEntry(i).setAngle(m_board.getGraphics().getEntry(i).getAngle() + valueX);
                            if (m_board.getGraphics().getEntry(i).getAngle() > 360) m_board.getGraphics().getEntry(i).setAngle(0);
                            if (m_board.getGraphics().getEntry(i).getAngle() < 0) m_board.getGraphics().getEntry(i).setAngle(359);

                          
                            updateHandles();
                            
                            m_mousePositionX = e.getX();
                            m_mousePositionY = e.getY();
                            
                            repaint();
                            updateStatsPanel();
                            return;
                        }
                    }
                    
                }
            }
            
            if (modifiers.indexOf("Shift") >= 0) {
                m_measuringEndPointX = e.getX();
                m_measuringEndPointY = e.getY();
                
                m_measuring = true;
                repaint();
                return;
            }
            
            
            // Default : Drag drawing field around
            int x = m_mousePositionX - e.getX();
            int y = m_mousePositionY - e.getY();
            
            m_drawingCentreX -= x;
            m_drawingCentreY -= y;
            
            m_mousePositionX = e.getX();
            m_mousePositionY = e.getY();
            
            repaint();
            
        }

        public void mouseMoved(java.awt.event.MouseEvent e) 
        {
            m_measuring = false;
            showHandles(e);
        }
        
    }
    
}
