/*  1:   */ package org.apache.log4j.lf5.viewer;
/*  2:   */ 
/*  3:   */ import java.awt.Container;
/*  4:   */ import java.awt.Dialog;
/*  5:   */ import java.awt.FlowLayout;
/*  6:   */ import java.awt.GridBagLayout;
/*  7:   */ import java.awt.event.ActionEvent;
/*  8:   */ import java.awt.event.ActionListener;
/*  9:   */ import javax.swing.AbstractButton;
/* 10:   */ import javax.swing.JButton;
/* 11:   */ import javax.swing.JDialog;
/* 12:   */ import javax.swing.JFrame;
/* 13:   */ import javax.swing.JPanel;
/* 14:   */ 
/* 15:   */ public class LogFactor5ErrorDialog
/* 16:   */   extends LogFactor5Dialog
/* 17:   */ {
/* 18:   */   public LogFactor5ErrorDialog(JFrame jframe, String message)
/* 19:   */   {
/* 20:55 */     super(jframe, "Error", true);
/* 21:   */     
/* 22:57 */     JButton ok = new JButton("Ok");
/* 23:58 */     ok.addActionListener(new ActionListener()
/* 24:   */     {
/* 25:   */       public void actionPerformed(ActionEvent e)
/* 26:   */       {
/* 27:60 */         LogFactor5ErrorDialog.this.hide();
/* 28:   */       }
/* 29:63 */     });
/* 30:64 */     JPanel bottom = new JPanel();
/* 31:65 */     bottom.setLayout(new FlowLayout());
/* 32:66 */     bottom.add(ok);
/* 33:   */     
/* 34:68 */     JPanel main = new JPanel();
/* 35:69 */     main.setLayout(new GridBagLayout());
/* 36:70 */     wrapStringOnPanel(message, main);
/* 37:   */     
/* 38:72 */     getContentPane().add(main, "Center");
/* 39:73 */     getContentPane().add(bottom, "South");
/* 40:74 */     show();
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.LogFactor5ErrorDialog
 * JD-Core Version:    0.7.0.1
 */