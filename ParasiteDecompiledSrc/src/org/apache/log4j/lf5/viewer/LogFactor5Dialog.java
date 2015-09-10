/*   1:    */ package org.apache.log4j.lf5.viewer;
/*   2:    */ 
/*   3:    */ import java.awt.Component;
/*   4:    */ import java.awt.Container;
/*   5:    */ import java.awt.Dialog;
/*   6:    */ import java.awt.Dimension;
/*   7:    */ import java.awt.Font;
/*   8:    */ import java.awt.GridBagConstraints;
/*   9:    */ import java.awt.GridBagLayout;
/*  10:    */ import java.awt.Insets;
/*  11:    */ import java.awt.Label;
/*  12:    */ import java.awt.Toolkit;
/*  13:    */ import java.awt.Window;
/*  14:    */ import javax.swing.JDialog;
/*  15:    */ import javax.swing.JFrame;
/*  16:    */ 
/*  17:    */ public abstract class LogFactor5Dialog
/*  18:    */   extends JDialog
/*  19:    */ {
/*  20: 46 */   protected static final Font DISPLAY_FONT = new Font("Arial", 1, 12);
/*  21:    */   
/*  22:    */   protected LogFactor5Dialog(JFrame jframe, String message, boolean modal)
/*  23:    */   {
/*  24: 59 */     super(jframe, message, modal);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void show()
/*  28:    */   {
/*  29: 66 */     pack();
/*  30: 67 */     minimumSizeDialog(this, 200, 100);
/*  31: 68 */     centerWindow(this);
/*  32: 69 */     super.show();
/*  33:    */   }
/*  34:    */   
/*  35:    */   protected void centerWindow(Window win)
/*  36:    */   {
/*  37: 80 */     Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
/*  38: 83 */     if (screenDim.width < win.getSize().width) {
/*  39: 84 */       win.setSize(screenDim.width, win.getSize().height);
/*  40:    */     }
/*  41: 87 */     if (screenDim.height < win.getSize().height) {
/*  42: 88 */       win.setSize(win.getSize().width, screenDim.height);
/*  43:    */     }
/*  44: 92 */     int x = (screenDim.width - win.getSize().width) / 2;
/*  45: 93 */     int y = (screenDim.height - win.getSize().height) / 2;
/*  46: 94 */     win.setLocation(x, y);
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected void wrapStringOnPanel(String message, Container container)
/*  50:    */   {
/*  51: 99 */     GridBagConstraints c = getDefaultConstraints();
/*  52:100 */     c.gridwidth = 0;
/*  53:    */     
/*  54:102 */     c.insets = new Insets(0, 0, 0, 0);
/*  55:103 */     GridBagLayout gbLayout = (GridBagLayout)container.getLayout();
/*  56:106 */     while (message.length() > 0)
/*  57:    */     {
/*  58:107 */       int newLineIndex = message.indexOf('\n');
/*  59:    */       String line;
/*  60:109 */       if (newLineIndex >= 0)
/*  61:    */       {
/*  62:110 */         String line = message.substring(0, newLineIndex);
/*  63:111 */         message = message.substring(newLineIndex + 1);
/*  64:    */       }
/*  65:    */       else
/*  66:    */       {
/*  67:113 */         line = message;
/*  68:114 */         message = "";
/*  69:    */       }
/*  70:116 */       Label label = new Label(line);
/*  71:117 */       label.setFont(DISPLAY_FONT);
/*  72:118 */       gbLayout.setConstraints(label, c);
/*  73:119 */       container.add(label);
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected GridBagConstraints getDefaultConstraints()
/*  78:    */   {
/*  79:124 */     GridBagConstraints constraints = new GridBagConstraints();
/*  80:125 */     constraints.weightx = 1.0D;
/*  81:126 */     constraints.weighty = 1.0D;
/*  82:127 */     constraints.gridheight = 1;
/*  83:    */     
/*  84:129 */     constraints.insets = new Insets(4, 4, 4, 4);
/*  85:    */     
/*  86:131 */     constraints.fill = 0;
/*  87:    */     
/*  88:133 */     constraints.anchor = 17;
/*  89:    */     
/*  90:135 */     return constraints;
/*  91:    */   }
/*  92:    */   
/*  93:    */   protected void minimumSizeDialog(Component component, int minWidth, int minHeight)
/*  94:    */   {
/*  95:142 */     if (component.getSize().width < minWidth) {
/*  96:143 */       component.setSize(minWidth, component.getSize().height);
/*  97:    */     }
/*  98:145 */     if (component.getSize().height < minHeight) {
/*  99:146 */       component.setSize(component.getSize().width, minHeight);
/* 100:    */     }
/* 101:    */   }
/* 102:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.LogFactor5Dialog
 * JD-Core Version:    0.7.0.1
 */