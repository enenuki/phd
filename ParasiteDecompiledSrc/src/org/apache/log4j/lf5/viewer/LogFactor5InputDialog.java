/*   1:    */ package org.apache.log4j.lf5.viewer;
/*   2:    */ 
/*   3:    */ import java.awt.Component;
/*   4:    */ import java.awt.Container;
/*   5:    */ import java.awt.Dialog;
/*   6:    */ import java.awt.FlowLayout;
/*   7:    */ import java.awt.Window;
/*   8:    */ import java.awt.event.ActionEvent;
/*   9:    */ import java.awt.event.ActionListener;
/*  10:    */ import java.awt.event.KeyAdapter;
/*  11:    */ import java.awt.event.KeyEvent;
/*  12:    */ import javax.swing.AbstractButton;
/*  13:    */ import javax.swing.JButton;
/*  14:    */ import javax.swing.JDialog;
/*  15:    */ import javax.swing.JFrame;
/*  16:    */ import javax.swing.JLabel;
/*  17:    */ import javax.swing.JPanel;
/*  18:    */ import javax.swing.JTextField;
/*  19:    */ import javax.swing.text.JTextComponent;
/*  20:    */ 
/*  21:    */ public class LogFactor5InputDialog
/*  22:    */   extends LogFactor5Dialog
/*  23:    */ {
/*  24:    */   public static final int SIZE = 30;
/*  25:    */   private JTextField _textField;
/*  26:    */   
/*  27:    */   public LogFactor5InputDialog(JFrame jframe, String title, String label)
/*  28:    */   {
/*  29: 62 */     this(jframe, title, label, 30);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public LogFactor5InputDialog(JFrame jframe, String title, String label, int size)
/*  33:    */   {
/*  34: 74 */     super(jframe, title, true);
/*  35:    */     
/*  36: 76 */     JPanel bottom = new JPanel();
/*  37: 77 */     bottom.setLayout(new FlowLayout());
/*  38:    */     
/*  39: 79 */     JPanel main = new JPanel();
/*  40: 80 */     main.setLayout(new FlowLayout());
/*  41: 81 */     main.add(new JLabel(label));
/*  42: 82 */     this._textField = new JTextField(size);
/*  43: 83 */     main.add(this._textField);
/*  44:    */     
/*  45: 85 */     addKeyListener(new KeyAdapter()
/*  46:    */     {
/*  47:    */       public void keyPressed(KeyEvent e)
/*  48:    */       {
/*  49: 87 */         if (e.getKeyCode() == 10) {
/*  50: 88 */           LogFactor5InputDialog.this.hide();
/*  51:    */         }
/*  52:    */       }
/*  53: 92 */     });
/*  54: 93 */     JButton ok = new JButton("Ok");
/*  55: 94 */     ok.addActionListener(new ActionListener()
/*  56:    */     {
/*  57:    */       public void actionPerformed(ActionEvent e)
/*  58:    */       {
/*  59: 96 */         LogFactor5InputDialog.this.hide();
/*  60:    */       }
/*  61: 99 */     });
/*  62:100 */     JButton cancel = new JButton("Cancel");
/*  63:101 */     cancel.addActionListener(new ActionListener()
/*  64:    */     {
/*  65:    */       public void actionPerformed(ActionEvent e)
/*  66:    */       {
/*  67:103 */         LogFactor5InputDialog.this.hide();
/*  68:    */         
/*  69:    */ 
/*  70:    */ 
/*  71:107 */         LogFactor5InputDialog.this._textField.setText("");
/*  72:    */       }
/*  73:110 */     });
/*  74:111 */     bottom.add(ok);
/*  75:112 */     bottom.add(cancel);
/*  76:113 */     getContentPane().add(main, "Center");
/*  77:114 */     getContentPane().add(bottom, "South");
/*  78:115 */     pack();
/*  79:116 */     centerWindow(this);
/*  80:117 */     show();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String getText()
/*  84:    */   {
/*  85:124 */     String s = this._textField.getText();
/*  86:126 */     if ((s != null) && (s.trim().length() == 0)) {
/*  87:127 */       return null;
/*  88:    */     }
/*  89:130 */     return s;
/*  90:    */   }
/*  91:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.LogFactor5InputDialog
 * JD-Core Version:    0.7.0.1
 */