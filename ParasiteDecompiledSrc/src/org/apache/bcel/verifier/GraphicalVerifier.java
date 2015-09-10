/*   1:    */ package org.apache.bcel.verifier;
/*   2:    */ 
/*   3:    */ import java.awt.Component;
/*   4:    */ import java.awt.Container;
/*   5:    */ import java.awt.Dimension;
/*   6:    */ import java.awt.Toolkit;
/*   7:    */ import java.awt.Window;
/*   8:    */ import javax.swing.JList;
/*   9:    */ import javax.swing.UIManager;
/*  10:    */ import org.apache.bcel.generic.ObjectType;
/*  11:    */ import org.apache.bcel.generic.Type;
/*  12:    */ 
/*  13:    */ public class GraphicalVerifier
/*  14:    */ {
/*  15: 70 */   boolean packFrame = false;
/*  16:    */   
/*  17:    */   public GraphicalVerifier()
/*  18:    */   {
/*  19: 74 */     VerifierAppFrame frame = new VerifierAppFrame();
/*  20: 77 */     if (this.packFrame) {
/*  21: 78 */       frame.pack();
/*  22:    */     } else {
/*  23: 81 */       frame.validate();
/*  24:    */     }
/*  25: 84 */     Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/*  26: 85 */     Dimension frameSize = frame.getSize();
/*  27: 86 */     if (frameSize.height > screenSize.height) {
/*  28: 87 */       frameSize.height = screenSize.height;
/*  29:    */     }
/*  30: 89 */     if (frameSize.width > screenSize.width) {
/*  31: 90 */       frameSize.width = screenSize.width;
/*  32:    */     }
/*  33: 92 */     frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
/*  34: 93 */     frame.setVisible(true);
/*  35:    */     
/*  36: 95 */     frame.classNamesJList.setModel(new VerifierFactoryListModel());
/*  37: 96 */     VerifierFactory.getVerifier(Type.OBJECT.getClassName());
/*  38: 97 */     frame.classNamesJList.setSelectedIndex(0);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static void main(String[] args)
/*  42:    */   {
/*  43:    */     try
/*  44:    */     {
/*  45:102 */       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
/*  46:    */     }
/*  47:    */     catch (Exception e)
/*  48:    */     {
/*  49:105 */       e.printStackTrace();
/*  50:    */     }
/*  51:107 */     new GraphicalVerifier();
/*  52:    */   }
/*  53:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.GraphicalVerifier
 * JD-Core Version:    0.7.0.1
 */