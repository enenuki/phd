/*   1:    */ package org.apache.regexp;
/*   2:    */ 
/*   3:    */ import java.applet.Applet;
/*   4:    */ import java.awt.Component;
/*   5:    */ import java.awt.Container;
/*   6:    */ import java.awt.FlowLayout;
/*   7:    */ import java.awt.GridBagConstraints;
/*   8:    */ import java.awt.GridBagLayout;
/*   9:    */ import java.awt.Insets;
/*  10:    */ import java.awt.Label;
/*  11:    */ import java.awt.TextArea;
/*  12:    */ import java.awt.TextComponent;
/*  13:    */ import java.awt.TextField;
/*  14:    */ import java.awt.Window;
/*  15:    */ import java.awt.event.TextEvent;
/*  16:    */ import java.awt.event.TextListener;
/*  17:    */ import java.awt.event.WindowAdapter;
/*  18:    */ import java.awt.event.WindowEvent;
/*  19:    */ import java.io.CharArrayWriter;
/*  20:    */ import java.io.PrintStream;
/*  21:    */ import java.io.PrintWriter;
/*  22:    */ import java.util.EventObject;
/*  23:    */ import javax.swing.JFrame;
/*  24:    */ 
/*  25:    */ public class REDemo
/*  26:    */   extends Applet
/*  27:    */   implements TextListener
/*  28:    */ {
/*  29: 76 */   RE r = new RE();
/*  30: 77 */   REDebugCompiler compiler = new REDebugCompiler();
/*  31:    */   TextField fieldRE;
/*  32:    */   TextField fieldMatch;
/*  33:    */   TextArea outRE;
/*  34:    */   TextArea outMatch;
/*  35:    */   
/*  36:    */   public void init()
/*  37:    */   {
/*  38: 93 */     GridBagLayout localGridBagLayout = new GridBagLayout();
/*  39: 94 */     setLayout(localGridBagLayout);
/*  40: 95 */     GridBagConstraints localGridBagConstraints = new GridBagConstraints();
/*  41: 96 */     localGridBagConstraints.insets = new Insets(5, 5, 5, 5);
/*  42: 97 */     localGridBagConstraints.anchor = 13;
/*  43: 98 */     localGridBagLayout.setConstraints(add(new Label("Regular expression:", 2)), localGridBagConstraints);
/*  44: 99 */     localGridBagConstraints.gridy = 0;
/*  45:100 */     localGridBagConstraints.anchor = 17;
/*  46:101 */     localGridBagLayout.setConstraints(add(this.fieldRE = new TextField("\\[([:javastart:][:javapart:]*)\\]", 40)), localGridBagConstraints);
/*  47:102 */     localGridBagConstraints.gridx = 0;
/*  48:103 */     localGridBagConstraints.gridy = -1;
/*  49:104 */     localGridBagConstraints.anchor = 13;
/*  50:105 */     localGridBagLayout.setConstraints(add(new Label("String:", 2)), localGridBagConstraints);
/*  51:106 */     localGridBagConstraints.gridy = 1;
/*  52:107 */     localGridBagConstraints.gridx = -1;
/*  53:108 */     localGridBagConstraints.anchor = 17;
/*  54:109 */     localGridBagLayout.setConstraints(add(this.fieldMatch = new TextField("aaa([foo])aaa", 40)), localGridBagConstraints);
/*  55:110 */     localGridBagConstraints.gridy = 2;
/*  56:111 */     localGridBagConstraints.gridx = -1;
/*  57:112 */     localGridBagConstraints.fill = 1;
/*  58:113 */     localGridBagConstraints.weighty = 1.0D;
/*  59:114 */     localGridBagConstraints.weightx = 1.0D;
/*  60:115 */     localGridBagLayout.setConstraints(add(this.outRE = new TextArea()), localGridBagConstraints);
/*  61:116 */     localGridBagConstraints.gridy = 2;
/*  62:117 */     localGridBagConstraints.gridx = -1;
/*  63:118 */     localGridBagLayout.setConstraints(add(this.outMatch = new TextArea()), localGridBagConstraints);
/*  64:    */     
/*  65:    */ 
/*  66:121 */     this.fieldRE.addTextListener(this);
/*  67:122 */     this.fieldMatch.addTextListener(this);
/*  68:    */     
/*  69:    */ 
/*  70:125 */     textValueChanged(null);
/*  71:    */   }
/*  72:    */   
/*  73:    */   void sayRE(String paramString)
/*  74:    */   {
/*  75:134 */     this.outRE.setText(paramString);
/*  76:    */   }
/*  77:    */   
/*  78:    */   void sayMatch(String paramString)
/*  79:    */   {
/*  80:143 */     this.outMatch.setText(paramString);
/*  81:    */   }
/*  82:    */   
/*  83:    */   String throwableToString(Throwable paramThrowable)
/*  84:    */   {
/*  85:152 */     String str1 = paramThrowable.getClass().getName();
/*  86:    */     String str2;
/*  87:154 */     if ((str2 = paramThrowable.getMessage()) != null) {
/*  88:156 */       str1 = str1 + "\n" + str2;
/*  89:    */     }
/*  90:158 */     return str1;
/*  91:    */   }
/*  92:    */   
/*  93:    */   void updateRE(String paramString)
/*  94:    */   {
/*  95:    */     try
/*  96:    */     {
/*  97:170 */       this.r.setProgram(this.compiler.compile(paramString));
/*  98:    */       
/*  99:    */ 
/* 100:173 */       CharArrayWriter localCharArrayWriter = new CharArrayWriter();
/* 101:174 */       this.compiler.dumpProgram(new PrintWriter(localCharArrayWriter));
/* 102:175 */       sayRE(localCharArrayWriter.toString());
/* 103:176 */       System.out.println(localCharArrayWriter);
/* 104:    */     }
/* 105:    */     catch (Exception localException)
/* 106:    */     {
/* 107:180 */       this.r.setProgram(null);
/* 108:181 */       sayRE(throwableToString(localException));
/* 109:    */     }
/* 110:    */     catch (Throwable localThrowable)
/* 111:    */     {
/* 112:185 */       this.r.setProgram(null);
/* 113:186 */       sayRE(throwableToString(localThrowable));
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   void updateMatch(String paramString)
/* 118:    */   {
/* 119:    */     try
/* 120:    */     {
/* 121:200 */       if (this.r.match(paramString))
/* 122:    */       {
/* 123:203 */         String str = "Matches.\n\n";
/* 124:206 */         for (int i = 0; i < this.r.getParenCount(); i++) {
/* 125:208 */           str = str + "$" + i + " = " + this.r.getParen(i) + "\n";
/* 126:    */         }
/* 127:210 */         sayMatch(str);
/* 128:    */       }
/* 129:    */       else
/* 130:    */       {
/* 131:215 */         sayMatch("Does not match");
/* 132:    */       }
/* 133:    */     }
/* 134:    */     catch (Throwable localThrowable)
/* 135:    */     {
/* 136:220 */       sayMatch(throwableToString(localThrowable));
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void textValueChanged(TextEvent paramTextEvent)
/* 141:    */   {
/* 142:231 */     if ((paramTextEvent == null) || (paramTextEvent.getSource() == this.fieldRE)) {
/* 143:234 */       updateRE(this.fieldRE.getText());
/* 144:    */     }
/* 145:238 */     updateMatch(this.fieldMatch.getText());
/* 146:    */   }
/* 147:    */   
/* 148:    */   public static void main(String[] paramArrayOfString)
/* 149:    */   {
/* 150:247 */     JFrame localJFrame = new JFrame("RE Demo");
/* 151:    */     
/* 152:249 */     localJFrame.addWindowListener(new WindowAdapter()
/* 153:    */     {
/* 154:    */       public void windowClosing(WindowEvent paramAnonymousWindowEvent)
/* 155:    */       {
/* 156:253 */         System.exit(0);
/* 157:    */       }
/* 158:255 */     });
/* 159:256 */     Container localContainer = localJFrame.getContentPane();
/* 160:257 */     localContainer.setLayout(new FlowLayout());
/* 161:258 */     REDemo localREDemo = new REDemo();
/* 162:259 */     localContainer.add(localREDemo);
/* 163:260 */     localREDemo.init();
/* 164:261 */     localJFrame.pack();
/* 165:262 */     localJFrame.setVisible(true);
/* 166:    */   }
/* 167:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.regexp.REDemo
 * JD-Core Version:    0.7.0.1
 */