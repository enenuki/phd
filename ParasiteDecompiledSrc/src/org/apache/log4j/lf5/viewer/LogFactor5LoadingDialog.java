/*  1:   */ package org.apache.log4j.lf5.viewer;
/*  2:   */ 
/*  3:   */ import java.awt.Container;
/*  4:   */ import java.awt.FlowLayout;
/*  5:   */ import java.awt.GridBagLayout;
/*  6:   */ import javax.swing.JDialog;
/*  7:   */ import javax.swing.JFrame;
/*  8:   */ import javax.swing.JPanel;
/*  9:   */ 
/* 10:   */ public class LogFactor5LoadingDialog
/* 11:   */   extends LogFactor5Dialog
/* 12:   */ {
/* 13:   */   public LogFactor5LoadingDialog(JFrame jframe, String message)
/* 14:   */   {
/* 15:53 */     super(jframe, "LogFactor5", false);
/* 16:   */     
/* 17:55 */     JPanel bottom = new JPanel();
/* 18:56 */     bottom.setLayout(new FlowLayout());
/* 19:   */     
/* 20:58 */     JPanel main = new JPanel();
/* 21:59 */     main.setLayout(new GridBagLayout());
/* 22:60 */     wrapStringOnPanel(message, main);
/* 23:   */     
/* 24:62 */     getContentPane().add(main, "Center");
/* 25:63 */     getContentPane().add(bottom, "South");
/* 26:64 */     show();
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.LogFactor5LoadingDialog
 * JD-Core Version:    0.7.0.1
 */