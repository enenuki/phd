/*   1:    */ package org.apache.log4j.lf5.viewer.categoryexplorer;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Component;
/*   5:    */ import java.awt.Container;
/*   6:    */ import java.awt.Dimension;
/*   7:    */ import java.awt.FlowLayout;
/*   8:    */ import java.net.URL;
/*   9:    */ import javax.swing.AbstractButton;
/*  10:    */ import javax.swing.ImageIcon;
/*  11:    */ import javax.swing.JCheckBox;
/*  12:    */ import javax.swing.JComponent;
/*  13:    */ import javax.swing.JPanel;
/*  14:    */ import javax.swing.JTree;
/*  15:    */ import javax.swing.UIManager;
/*  16:    */ import javax.swing.tree.DefaultTreeCellRenderer;
/*  17:    */ 
/*  18:    */ public class CategoryNodeRenderer
/*  19:    */   extends DefaultTreeCellRenderer
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = -6046702673278595048L;
/*  22: 40 */   public static final Color FATAL_CHILDREN = new Color(189, 113, 0);
/*  23: 45 */   protected JCheckBox _checkBox = new JCheckBox();
/*  24: 46 */   protected JPanel _panel = new JPanel();
/*  25: 47 */   protected static ImageIcon _sat = null;
/*  26:    */   
/*  27:    */   public CategoryNodeRenderer()
/*  28:    */   {
/*  29: 58 */     this._panel.setBackground(UIManager.getColor("Tree.textBackground"));
/*  30: 60 */     if (_sat == null)
/*  31:    */     {
/*  32: 62 */       String resource = "/org/apache/log4j/lf5/viewer/images/channelexplorer_satellite.gif";
/*  33:    */       
/*  34: 64 */       URL satURL = getClass().getResource(resource);
/*  35:    */       
/*  36: 66 */       _sat = new ImageIcon(satURL);
/*  37:    */     }
/*  38: 69 */     setOpaque(false);
/*  39: 70 */     this._checkBox.setOpaque(false);
/*  40: 71 */     this._panel.setOpaque(false);
/*  41:    */     
/*  42:    */ 
/*  43:    */ 
/*  44: 75 */     this._panel.setLayout(new FlowLayout(0, 0, 0));
/*  45: 76 */     this._panel.add(this._checkBox);
/*  46: 77 */     this._panel.add(this);
/*  47:    */     
/*  48: 79 */     setOpenIcon(_sat);
/*  49: 80 */     setClosedIcon(_sat);
/*  50: 81 */     setLeafIcon(_sat);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
/*  54:    */   {
/*  55: 93 */     CategoryNode node = (CategoryNode)value;
/*  56:    */     
/*  57:    */ 
/*  58:    */ 
/*  59:    */ 
/*  60: 98 */     super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
/*  61:102 */     if (row == 0)
/*  62:    */     {
/*  63:104 */       this._checkBox.setVisible(false);
/*  64:    */     }
/*  65:    */     else
/*  66:    */     {
/*  67:106 */       this._checkBox.setVisible(true);
/*  68:107 */       this._checkBox.setSelected(node.isSelected());
/*  69:    */     }
/*  70:109 */     String toolTip = buildToolTip(node);
/*  71:110 */     this._panel.setToolTipText(toolTip);
/*  72:111 */     if (node.hasFatalChildren()) {
/*  73:112 */       setForeground(FATAL_CHILDREN);
/*  74:    */     }
/*  75:114 */     if (node.hasFatalRecords()) {
/*  76:115 */       setForeground(Color.red);
/*  77:    */     }
/*  78:118 */     return this._panel;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Dimension getCheckBoxOffset()
/*  82:    */   {
/*  83:122 */     return new Dimension(0, 0);
/*  84:    */   }
/*  85:    */   
/*  86:    */   protected String buildToolTip(CategoryNode node)
/*  87:    */   {
/*  88:130 */     StringBuffer result = new StringBuffer();
/*  89:131 */     result.append(node.getTitle()).append(" contains a total of ");
/*  90:132 */     result.append(node.getTotalNumberOfRecords());
/*  91:133 */     result.append(" LogRecords.");
/*  92:134 */     result.append(" Right-click for more info.");
/*  93:135 */     return result.toString();
/*  94:    */   }
/*  95:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.categoryexplorer.CategoryNodeRenderer
 * JD-Core Version:    0.7.0.1
 */