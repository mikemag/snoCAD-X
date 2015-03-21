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
 * snoCADrailShaper.java
 *
 * Created on 04 April 2007, 10:48
 */

package snocadx;

/**
 *
 * @author  dgraf
 */
public class snoCADcoreShaper extends javax.swing.JDialog{
    
    /**
     * Creates new form snoCADrailShaper
     */
    
    public snoCADcoreShaper() {
        initComponents();
    }
    
    public void setupVariables()
    {
        int tipspacerType = m_bp.getBoard().getTipspacerType();
        
        if (tipspacerType == snoCADutilities.SIDEWALL) m_tipspacerType.setSelectedIndex(0);
        if (tipspacerType == snoCADutilities.RADIUS) m_tipspacerType.setSelectedIndex(1);
        if (tipspacerType == snoCADutilities.INTERLOCK) m_tipspacerType.setSelectedIndex(2);
        if (tipspacerType == snoCADutilities.STRAIGHT) m_tipspacerType.setSelectedIndex(3);
        if (tipspacerType == snoCADutilities.POINTY) m_tipspacerType.setSelectedIndex(4);
        if (tipspacerType == snoCADutilities.NONE) m_tipspacerType.setSelectedIndex(5);
        
        int sidewallWidth = (int)Math.round(m_bp.getBoard().getSidewallWidth());
        m_sidewallWidth.setValue(sidewallWidth);
        sidewallWidth = m_sidewallWidth.getValue();
        m_sidewallWidthLabel.setText("Sidewall Width : " + Integer.toString(sidewallWidth) + "mm");
        
        int tipSidewallOffset = (int)Math.round(m_bp.getBoard().getTipSidewallOffset());
        m_tipSidewallOffset.setValue(tipSidewallOffset);
        tipSidewallOffset = m_tipSidewallOffset.getValue();
        m_tipSidewallOffsetLabel.setText("Tip Sidewall Offset : " + Integer.toString(tipSidewallOffset) + "mm");
        
        
        int interlockRadius = m_bp.getBoard().getTipspacerInterlockRadius();
        m_interlockRadius.setValue(interlockRadius);
        m_interlockRadiusLabel.setText("Interlock Radius : " + Integer.toString(interlockRadius) + "mm");
        
        int tipspacerRadius = m_bp.getBoard().getNoseTipspacerRadius();
        int tipspacerRadiusTail = m_bp.getBoard().getTailTipspacerRadius();
        
        int min_nose = ((m_bp.getBoard().getNoseWidth() - (m_bp.getBoard().getSidewallWidth() * 2)) / 2);
        int min_tail = ((m_bp.getBoard().getTailWidth() - (m_bp.getBoard().getSidewallWidth() * 2)) / 2);
        m_tipspacerRadius.setMinimum(min_nose);
        
        m_tipspacerRadiusTail.setMinimum(min_tail);
        
        m_tipspacerRadius.setValue(tipspacerRadius);
        m_tipspacerRadiusLabel.setText("Tipspacer Radius Nose : " + Integer.toString(tipspacerRadius) + "mm");
        
        m_tipspacerRadiusTail.setValue(tipspacerRadiusTail);
        m_tipspacerRadiusLabelTail.setText("Tipspacer Radius Tail : " + Integer.toString(tipspacerRadiusTail) + "mm");
        
        m_tipspacerPointy.setValue(m_bp.getBoard().getNoseTipspacerPointy() );
        m_tipspacerPointyTail.setValue(m_bp.getBoard().getTailTipspacerPointy());
        
        m_tipspacerPointy.setMaximum(m_bp.getBoard().getNoseLength());
        m_tipspacerPointyTail.setMaximum(m_bp.getBoard().getTailLength());
        
        int insertStringerWidth = m_bp.getBoard().getInsertStringerWidth();
        int outerStringerWidth = m_bp.getBoard().getCoreStringerWidth();
        
        m_insertStringerWidth.setValue(insertStringerWidth);
        m_outerStringerWidth.setValue(outerStringerWidth);
        
        m_insertStringerWidthLabel.setText("Insert Stringer Width : " + Integer.toString(insertStringerWidth) + "mm");
        m_outerStringerWidthLabel.setText("Outer Stringer Width : " + Integer.toString(outerStringerWidth) + "mm");
        
        m_tsLengthNose.setMinimum(m_bp.getBoard().getNoseLength());
        m_tsLengthNose.setValue(m_bp.getBoard().getNoseTipspacerMaterialLength());
        m_tsLengthNoseLabel.setText("Tipspacer Length Nose : " + Integer.toString(m_bp.getBoard().getNoseTipspacerMaterialLength()) + "mm");
        
        m_tsWidthNose.setMinimum(m_bp.getBoard().getNoseWidth());
        m_tsWidthNose.setValue(m_bp.getBoard().getNoseTipspacerMaterialWidth());
        m_tsWidthNoseLabel.setText("Tipspacer Width Nose : " + Integer.toString(m_bp.getBoard().getNoseTipspacerMaterialWidth()) + "mm");
        
        m_tsLengthTail.setValue(m_bp.getBoard().getTailTipspacerMaterialLength());
        m_tsLengthTail.setMinimum(m_bp.getBoard().getTailLength());
        m_tsLengthTailLabel.setText("Tipspacer Length Tail : " + Integer.toString(m_bp.getBoard().getTailTipspacerMaterialLength()) + "mm");
        
        m_tsWidthTail.setMinimum(m_bp.getBoard().getTailWidth());
        m_tsWidthTail.setValue(m_bp.getBoard().getTailTipspacerMaterialWidth());
        m_tsWidthTailLabel.setText("Tipspacer Width Tail : " + Integer.toString(m_bp.getBoard().getTailTipspacerMaterialWidth()) + "mm");
        

    }
    public snoCADcoreShaper(snoCADboardDisplay bp) {
        
        m_bp = bp;
        initComponents();
        
        setModal(true);
        setModalityType(java.awt.Dialog.ModalityType.TOOLKIT_MODAL);
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.TOOLKIT_EXCLUDE);
        
        setupVariables();
        
        m_tipSidewallOffset.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent e) 
            {
                  int tipSidewallOffset = (int)m_tipSidewallOffset.getValue();
                  m_bp.getBoard().setTipSidewallOffset(tipSidewallOffset);
                  m_tipSidewallOffsetLabel.setText("Tip Sidewall Offset : " + Integer.toString(tipSidewallOffset) + "mm");
                  
                  if (m_bp.getBoard().getTipspacerType() == snoCADutilities.SIDEWALL || m_bp.getBoard().getTipspacerType() == snoCADutilities.INTERLOCK)
                  {
                      m_bp.getBoard().setCoreNoseLength(m_bp.getBoard().getNoseLength() - m_bp.getBoard().getSidewallWidth() - m_bp.getBoard().getTipSidewallOffset());
                      m_bp.getBoard().setCoreTailLength(m_bp.getBoard().getTailLength() - m_bp.getBoard().getSidewallWidth() - m_bp.getBoard().getTipSidewallOffset());
                  }
                  
                  m_bp.repaint(); 
            }
        }) ;
        
        m_tsLengthNose.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent e) 
            {
                  int tsLength = (int)m_tsLengthNose.getValue();
                  m_bp.getBoard().setNoseTipSpacerMaterialLength(tsLength);
                  m_tsLengthNoseLabel.setText("Tipspacer Length Nose : " + Integer.toString(tsLength) + "mm");
                  m_bp.repaint(); 
            }
        }) ;

         m_tsWidthNose.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent e) 
            {
                  int tsLength = (int)m_tsWidthNose.getValue();
                  m_bp.getBoard().setNoseTipSpacerMaterialWidth(tsLength);
                  m_tsWidthNoseLabel.setText("Tipspacer Width Nose : " + Integer.toString(tsLength) + "mm");
                  m_bp.repaint(); 
            }
        }) ;
        
    
        m_tsLengthTail.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent e) 
            {
                  int tsLength = (int)m_tsLengthTail.getValue();
                  m_bp.getBoard().setTailTipSpacerMaterialLength(tsLength);
                  m_tsLengthTailLabel.setText("Tipspacer Length Tail : " + Integer.toString(tsLength) + "mm");
                  m_bp.repaint(); 
            }
        }) ;

         m_tsWidthTail.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent e) 
            {
                  int tsLength = (int)m_tsWidthTail.getValue();
                  m_bp.getBoard().setTailTipSpacerMaterialWidth(tsLength);
                  m_tsWidthTailLabel.setText("Tipspacer Width Tail : " + Integer.toString(tsLength) + "mm");
                  m_bp.repaint(); 
            }
        }) ;
        
        m_tipspacerRadius.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent e) 
            {
                  int tipspacerRadius = (int)m_tipspacerRadius.getValue();
                  m_bp.getBoard().setNoseTipspacerRadius(tipspacerRadius);
                  m_bp.getCoreGeometry();
                  m_bp.getBoard().setCoreNoseLength(m_bp.getCoreNoseCalculatedLength());
                  m_tipspacerRadiusLabel.setText("Tipspacer Radius : " + Integer.toString(tipspacerRadius) + "mm");

                  m_bp.repaint(); 
            }
        }) ;
        
        m_tipspacerRadiusTail.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent e) 
            {
                  int tipspacerRadius = (int)m_tipspacerRadiusTail.getValue();
                  m_bp.getBoard().setTailTipspacerRadius(tipspacerRadius);
                  m_bp.getCoreGeometry();
                  m_bp.getBoard().setCoreTailLength(m_bp.getCoreTailCalculatedLength());
                  m_tipspacerRadiusLabelTail.setText("Tipspacer Radius Tail : " + Integer.toString(tipspacerRadius) + "mm");

                  m_bp.repaint(); 
            }
        }) ;
        
        m_tipspacerPointy.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent e) 
            {
                  int tipspacerPointy = (int)m_tipspacerPointy.getValue();
                  m_bp.getBoard().setNoseTipspacerPointy(tipspacerPointy);
                  m_bp.getCoreGeometry();
                  
                  //TODO Check the core length
                  m_bp.getBoard().setCoreNoseLength(m_bp.getCoreNoseCalculatedLength());
                  m_tipspacerPointyLabel.setText("Tipspacer Pointy : " + Integer.toString(tipspacerPointy) + "mm");

                  m_bp.repaint(); 
            }
        }) ;
        
        m_tipspacerPointyTail.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent e) 
            {
                  int tipspacerPointy = (int)m_tipspacerPointyTail.getValue();
                  m_bp.getBoard().setTailTipspacerPointy(tipspacerPointy);
                  m_bp.getCoreGeometry();
                  
                  //TODO Check the core length
                  m_bp.getBoard().setCoreTailLength(m_bp.getCoreTailCalculatedLength());
                  m_tipspacerPointyLabelTail.setText("Tipspacer Pointy Tail : " + Integer.toString(tipspacerPointy) + "mm");

                  m_bp.repaint(); 
            }
        }) ;
        
        m_sidewallWidth.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent e) 
            {
                  int sidewallWidth = (int)m_sidewallWidth.getValue();
                  m_bp.getBoard().setSidewallWidth(sidewallWidth);
                  m_sidewallWidthLabel.setText("Sidewall Width : " + Integer.toString(sidewallWidth) + "mm");
                  
                  if (m_bp.getBoard().getTipspacerType() == snoCADutilities.SIDEWALL || m_bp.getBoard().getTipspacerType() == snoCADutilities.INTERLOCK)
                  {
                      m_bp.getBoard().setCoreNoseLength(m_bp.getBoard().getNoseLength() - sidewallWidth - m_bp.getBoard().getTipSidewallOffset());
                      m_bp.getBoard().setCoreTailLength(m_bp.getBoard().getTailLength() - sidewallWidth - m_bp.getBoard().getTipSidewallOffset());
                  }
                  
                  m_bp.repaint(); 
            }
        }) ;
        
        m_insertStringerWidth.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent e) 
            {
                  int insertStringerWidth = (int)m_insertStringerWidth.getValue();
                  m_bp.getBoard().setInsertStringerWidth(insertStringerWidth);
                  m_insertStringerWidthLabel.setText("Insert Stringer Width : " + Integer.toString(insertStringerWidth) + "mm");

                  m_bp.repaint(); 
            }
        }) ;
        
        m_interlockRadius.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent e) 
            {
                  int interlockRadius = (int)m_interlockRadius.getValue();
                  m_bp.getBoard().setTipspacerInterlockRadius(interlockRadius);
                  m_interlockRadiusLabel.setText("Interlock Radius : " + Integer.toString(interlockRadius) + "mm");

                  m_bp.repaint(); 
            }
        }) ;
        
        m_outerStringerWidth.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent e) 
            {
                  int outerStringerWidth = (int)m_outerStringerWidth.getValue();
                  m_bp.getBoard().setCoreStringerWidth(outerStringerWidth);
                  m_outerStringerWidthLabel.setText("Outer Stringer Width : " + Integer.toString(outerStringerWidth) + "mm");

                  m_bp.repaint(); 
            }
        }) ;
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        m_sidewallWidth = new javax.swing.JSlider();
        m_sidewallWidthLabel = new javax.swing.JLabel();
        m_insertStringerWidth = new javax.swing.JSlider();
        m_outerStringerWidth = new javax.swing.JSlider();
        m_insertStringerWidthLabel = new javax.swing.JLabel();
        m_outerStringerWidthLabel = new javax.swing.JLabel();
        m_tipSidewallOffset = new javax.swing.JSlider();
        m_tipSidewallOffsetLabel = new javax.swing.JLabel();
        m_close = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        m_tipspacerType = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        m_tipspacerRadius = new javax.swing.JSlider();
        m_tipspacerRadiusLabel = new javax.swing.JLabel();
        m_tsLengthNose = new javax.swing.JSlider();
        m_tsWidthNose = new javax.swing.JSlider();
        m_tsLengthNoseLabel = new javax.swing.JLabel();
        m_tsWidthNoseLabel = new javax.swing.JLabel();
        m_tipspacerRadiusLabelTail = new javax.swing.JLabel();
        m_tipspacerRadiusTail = new javax.swing.JSlider();
        m_tsLengthTail = new javax.swing.JSlider();
        m_tsLengthTailLabel = new javax.swing.JLabel();
        m_tsWidthTail = new javax.swing.JSlider();
        m_tsWidthTailLabel = new javax.swing.JLabel();
        m_interlockRadiusLabel = new javax.swing.JLabel();
        m_interlockRadius = new javax.swing.JSlider();
        m_tipspacerPointy = new javax.swing.JSlider();
        m_tipspacerPointyLabel = new javax.swing.JLabel();
        m_tipspacerPointyLabelTail = new javax.swing.JLabel();
        m_tipspacerPointyTail = new javax.swing.JSlider();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Core"));

        m_sidewallWidth.setMajorTickSpacing(5);
        m_sidewallWidth.setMaximum(30);
        m_sidewallWidth.setMinorTickSpacing(1);
        m_sidewallWidth.setPaintTicks(true);
        m_sidewallWidth.setValue(0);

        m_sidewallWidthLabel.setText("Sidewall Width :");

        m_insertStringerWidth.setMajorTickSpacing(5);
        m_insertStringerWidth.setMaximum(40);
        m_insertStringerWidth.setMinorTickSpacing(1);
        m_insertStringerWidth.setPaintTicks(true);
        m_insertStringerWidth.setValue(0);

        m_outerStringerWidth.setMajorTickSpacing(10);
        m_outerStringerWidth.setMaximum(150);
        m_outerStringerWidth.setMinimum(1);
        m_outerStringerWidth.setMinorTickSpacing(5);
        m_outerStringerWidth.setPaintTicks(true);

        m_insertStringerWidthLabel.setText("Insert Stringer Width :");

        m_outerStringerWidthLabel.setText("Outer Stringer Width :");

        m_tipSidewallOffset.setMajorTickSpacing(10);
        m_tipSidewallOffset.setMinorTickSpacing(5);
        m_tipSidewallOffset.setPaintTicks(true);
        m_tipSidewallOffset.setValue(0);

        m_tipSidewallOffsetLabel.setText("Tip Sidewall Offset");

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(m_sidewallWidth, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                    .add(m_sidewallWidthLabel)
                    .add(m_insertStringerWidthLabel)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, m_insertStringerWidth, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                    .add(m_outerStringerWidthLabel)
                    .add(m_outerStringerWidth, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                    .add(m_tipSidewallOffsetLabel)
                    .add(m_tipSidewallOffset, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(m_sidewallWidthLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_sidewallWidth, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_tipSidewallOffsetLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_tipSidewallOffset, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(36, 36, 36)
                .add(m_insertStringerWidthLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_insertStringerWidth, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(19, 19, 19)
                .add(m_outerStringerWidthLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_outerStringerWidth, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        m_close.setText("Close");
        m_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OnClose(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipspacer"));

        m_tipspacerType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sidewall", "Radius", "Interlock", "Straight", "Pointy", "None" }));
        m_tipspacerType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OnChangeTipspacerType(evt);
            }
        });

        jLabel1.setText("Tipspacer Type");

        m_tipspacerRadius.setMajorTickSpacing(100);
        m_tipspacerRadius.setMaximum(300);
        m_tipspacerRadius.setMinimum(50);
        m_tipspacerRadius.setMinorTickSpacing(10);
        m_tipspacerRadius.setPaintTicks(true);

        m_tipspacerRadiusLabel.setText("Tipspacer Radius Nose");

        m_tsLengthNose.setMajorTickSpacing(50);
        m_tsLengthNose.setMaximum(400);
        m_tsLengthNose.setMinorTickSpacing(10);
        m_tsLengthNose.setPaintTicks(true);
        m_tsLengthNose.setValue(0);

        m_tsWidthNose.setMajorTickSpacing(50);
        m_tsWidthNose.setMaximum(450);
        m_tsWidthNose.setMinorTickSpacing(10);
        m_tsWidthNose.setPaintTicks(true);
        m_tsWidthNose.setValue(0);

        m_tsLengthNoseLabel.setText("Tipspacer Length Nose");

        m_tsWidthNoseLabel.setText("Tipspacer Width Nose");

        m_tipspacerRadiusLabelTail.setText("Tipspacer Radius Tail");

        m_tipspacerRadiusTail.setMajorTickSpacing(100);
        m_tipspacerRadiusTail.setMaximum(300);
        m_tipspacerRadiusTail.setMinimum(50);
        m_tipspacerRadiusTail.setMinorTickSpacing(10);
        m_tipspacerRadiusTail.setPaintTicks(true);

        m_tsLengthTail.setMajorTickSpacing(50);
        m_tsLengthTail.setMaximum(400);
        m_tsLengthTail.setMinorTickSpacing(10);
        m_tsLengthTail.setPaintTicks(true);

        m_tsLengthTailLabel.setText("Tipspacer Length Tail");

        m_tsWidthTail.setMajorTickSpacing(50);
        m_tsWidthTail.setMaximum(450);
        m_tsWidthTail.setMinorTickSpacing(10);
        m_tsWidthTail.setPaintTicks(true);
        m_tsWidthTail.setValue(0);

        m_tsWidthTailLabel.setText("Tipspacer Width Tail");

        m_interlockRadiusLabel.setText("Interlock Radius");

        m_interlockRadius.setMajorTickSpacing(10);
        m_interlockRadius.setMaximum(50);
        m_interlockRadius.setMinorTickSpacing(1);
        m_interlockRadius.setPaintTicks(true);
        m_interlockRadius.setValue(0);

        m_tipspacerPointy.setMajorTickSpacing(100);
        m_tipspacerPointy.setMaximum(300);
        m_tipspacerPointy.setMinorTickSpacing(10);
        m_tipspacerPointy.setPaintTicks(true);
        m_tipspacerPointy.setValue(30);

        m_tipspacerPointyLabel.setText("Tipspacer Pointy Nose");

        m_tipspacerPointyLabelTail.setText("Tipspacer Pointy Tail");

        m_tipspacerPointyTail.setMajorTickSpacing(100);
        m_tipspacerPointyTail.setMaximum(300);
        m_tipspacerPointyTail.setMinorTickSpacing(10);
        m_tipspacerPointyTail.setPaintTicks(true);
        m_tipspacerPointyTail.setValue(30);

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(m_tipspacerRadiusTail, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(m_tipspacerPointy, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                .add(jLabel1)
                                .add(m_tipspacerType, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .add(m_tsLengthTailLabel)
                            .add(m_tsLengthTail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(m_tsWidthTailLabel)
                            .add(m_tsWidthTail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(m_interlockRadiusLabel)
                            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, m_interlockRadius, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, m_tipspacerRadiusLabel)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, m_tsLengthNoseLabel)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, m_tsLengthNose, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, m_tsWidthNoseLabel)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, m_tsWidthNose, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, m_tipspacerRadius, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .add(m_tipspacerPointyLabel)
                            .add(m_tipspacerRadiusLabelTail)
                            .add(m_tipspacerPointyLabelTail))
                        .add(0, 0, Short.MAX_VALUE))
                    .add(m_tipspacerPointyTail, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_tipspacerType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_interlockRadiusLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_interlockRadius, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 22, Short.MAX_VALUE)
                .add(m_tipspacerRadiusLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_tipspacerRadius, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_tipspacerPointyLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_tipspacerPointy, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_tsLengthNoseLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_tsLengthNose, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_tsWidthNoseLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_tsWidthNose, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(m_tipspacerRadiusLabelTail)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_tipspacerRadiusTail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_tipspacerPointyLabelTail)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_tipspacerPointyTail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_tsLengthTailLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_tsLengthTail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_tsWidthTailLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_tsWidthTail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(m_close, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_close)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void OnChangeTipspacerType(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OnChangeTipspacerType
// TODO add your handling code here:
        int tipspacerIdx = m_tipspacerType.getSelectedIndex();
        
        if (tipspacerIdx == 0) 
        {
            m_bp.getBoard().setTipspacerType(snoCADutilities.SIDEWALL);
            m_bp.getBoard().setCoreTailLength((double)m_bp.getBoard().getTailLength() - m_bp.getBoard().getSidewallWidth() - m_bp.getBoard().getTipSidewallOffset());
            m_bp.getBoard().setCoreNoseLength((double)m_bp.getBoard().getNoseLength() - m_bp.getBoard().getSidewallWidth() - m_bp.getBoard().getTipSidewallOffset());
            m_interlockRadius.setEnabled(false);
            m_tipspacerRadius.setEnabled(false);
            m_tipspacerRadiusTail.setEnabled(false);
            m_interlockRadiusLabel.setEnabled(false);
            m_tipspacerRadiusLabel.setEnabled(false);
            m_tipspacerRadiusLabelTail.setEnabled(false);
            m_tipspacerPointy.setEnabled(false);
            m_tipspacerPointyTail.setEnabled(false);
            m_tipspacerPointyLabel.setEnabled(false);
            m_tipspacerPointyLabelTail.setEnabled(false);
            m_tipSidewallOffset.setEnabled(true);
            m_tipSidewallOffsetLabel.setEnabled(true);
            
        }
        if (tipspacerIdx == 1) 
        {
            m_bp.getBoard().setTipspacerType(snoCADutilities.RADIUS);
            m_bp.getCoreGeometry();
            m_bp.getBoard().setCoreTailLength(m_bp.getCoreTailCalculatedLength());
            m_bp.getBoard().setCoreNoseLength(m_bp.getCoreNoseCalculatedLength());
            m_bp.repaint();
            m_interlockRadius.setEnabled(false);
            m_tipspacerRadius.setEnabled(true);
            m_tipspacerRadiusTail.setEnabled(true);
            m_interlockRadiusLabel.setEnabled(false);
            m_tipspacerRadiusLabel.setEnabled(true);
            m_tipspacerRadiusLabelTail.setEnabled(true);
            m_tipspacerPointy.setEnabled(false);
            m_tipspacerPointyTail.setEnabled(false);
            m_tipspacerPointyLabel.setEnabled(false);
            m_tipspacerPointyLabelTail.setEnabled(false);
            m_tipSidewallOffset.setEnabled(false);
            m_tipSidewallOffsetLabel.setEnabled(false);
        }
        if (tipspacerIdx == 2) 
        {
            m_bp.getBoard().setTipspacerType(snoCADutilities.INTERLOCK);
              m_bp.getBoard().setCoreTailLength((double)m_bp.getBoard().getTailLength() - m_bp.getBoard().getSidewallWidth() - m_bp.getBoard().getTipSidewallOffset());
            m_bp.getBoard().setCoreNoseLength((double)m_bp.getBoard().getNoseLength() - m_bp.getBoard().getSidewallWidth() - m_bp.getBoard().getTipSidewallOffset());
            m_interlockRadius.setEnabled(true);
            m_tipspacerRadius.setEnabled(false);
            m_tipspacerRadiusTail.setEnabled(false);
            m_interlockRadiusLabel.setEnabled(true);
            m_tipspacerRadiusLabel.setEnabled(false);
            m_tipspacerRadiusLabelTail.setEnabled(false);
            m_tipspacerPointy.setEnabled(false);
            m_tipspacerPointyTail.setEnabled(false);
            m_tipspacerPointyLabel.setEnabled(false);
            m_tipspacerPointyLabelTail.setEnabled(false);
            m_tipSidewallOffset.setEnabled(true);
            m_tipSidewallOffsetLabel.setEnabled(true);
            
        }
        
        if (tipspacerIdx == 3) 
        {
            m_bp.getBoard().setTipspacerType(snoCADutilities.STRAIGHT);
            m_bp.getBoard().setCoreTailLength(0);
            m_bp.getBoard().setCoreNoseLength(0);
            m_interlockRadius.setEnabled(false);
            m_tipspacerRadius.setEnabled(false);
            m_tipspacerRadiusTail.setEnabled(false);
            m_interlockRadiusLabel.setEnabled(false);
            m_tipspacerRadiusLabel.setEnabled(false);
            m_tipspacerRadiusLabelTail.setEnabled(false);
            m_tipSidewallOffset.setEnabled(false);
            m_tipSidewallOffsetLabel.setEnabled(false);
        }
                
        if (tipspacerIdx == 4) 
        {
            m_bp.getBoard().setTipspacerType(snoCADutilities.POINTY);
            m_bp.getCoreGeometry();
            //Check core length
            m_bp.getBoard().setCoreTailLength(m_bp.getCoreTailCalculatedLength());
            m_bp.getBoard().setCoreNoseLength(m_bp.getCoreNoseCalculatedLength());
            m_bp.repaint();
            m_interlockRadius.setEnabled(false);
            m_tipspacerRadius.setEnabled(false);
            m_tipspacerRadiusTail.setEnabled(false);
            m_interlockRadiusLabel.setEnabled(false);
            m_tipspacerRadiusLabel.setEnabled(false);
            m_tipspacerRadiusLabelTail.setEnabled(false);
            m_tipspacerPointy.setEnabled(true);
            m_tipspacerPointyTail.setEnabled(true);
            m_tipspacerPointyLabel.setEnabled(true);
            m_tipspacerPointyLabelTail.setEnabled(true);
            m_tipSidewallOffset.setEnabled(false);
            m_tipSidewallOffsetLabel.setEnabled(false);        }
        
        if (tipspacerIdx == 5) 
        {
            m_bp.getBoard().setTipspacerType(snoCADutilities.NONE);
            m_bp.getBoard().setCoreTailLength(m_bp.getBoard().getTailLength());
            m_bp.getBoard().setCoreNoseLength(m_bp.getBoard().getNoseLength());
            m_interlockRadius.setEnabled(false);
            m_tipspacerRadius.setEnabled(false);
            m_tipspacerRadiusTail.setEnabled(false);
            m_interlockRadiusLabel.setEnabled(false);
            m_tipspacerRadiusLabel.setEnabled(false);
            m_tipspacerRadiusLabelTail.setEnabled(false);
        }
        
        m_bp.repaint();
    }//GEN-LAST:event_OnChangeTipspacerType

    private void OnClose(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OnClose
        
        this.dispose();
    }//GEN-LAST:event_OnClose
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new snoCADcoreShaper().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton m_close;
    private javax.swing.JSlider m_insertStringerWidth;
    private javax.swing.JLabel m_insertStringerWidthLabel;
    private javax.swing.JSlider m_interlockRadius;
    private javax.swing.JLabel m_interlockRadiusLabel;
    private javax.swing.JSlider m_outerStringerWidth;
    private javax.swing.JLabel m_outerStringerWidthLabel;
    private javax.swing.JSlider m_sidewallWidth;
    private javax.swing.JLabel m_sidewallWidthLabel;
    private javax.swing.JSlider m_tipSidewallOffset;
    private javax.swing.JLabel m_tipSidewallOffsetLabel;
    private javax.swing.JSlider m_tipspacerPointy;
    private javax.swing.JLabel m_tipspacerPointyLabel;
    private javax.swing.JLabel m_tipspacerPointyLabelTail;
    private javax.swing.JSlider m_tipspacerPointyTail;
    private javax.swing.JSlider m_tipspacerRadius;
    private javax.swing.JLabel m_tipspacerRadiusLabel;
    private javax.swing.JLabel m_tipspacerRadiusLabelTail;
    private javax.swing.JSlider m_tipspacerRadiusTail;
    private javax.swing.JComboBox m_tipspacerType;
    private javax.swing.JSlider m_tsLengthNose;
    private javax.swing.JLabel m_tsLengthNoseLabel;
    private javax.swing.JSlider m_tsLengthTail;
    private javax.swing.JLabel m_tsLengthTailLabel;
    private javax.swing.JSlider m_tsWidthNose;
    private javax.swing.JLabel m_tsWidthNoseLabel;
    private javax.swing.JSlider m_tsWidthTail;
    private javax.swing.JLabel m_tsWidthTailLabel;
    // End of variables declaration//GEN-END:variables
    private snoCADboardDisplay m_bp;
}
