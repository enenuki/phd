/*   1:    */ package org.apache.bcel.verifier;
/*   2:    */ 
/*   3:    */ import java.awt.AWTEvent;
/*   4:    */ import java.awt.CardLayout;
/*   5:    */ import java.awt.Color;
/*   6:    */ import java.awt.Component;
/*   7:    */ import java.awt.Container;
/*   8:    */ import java.awt.Dimension;
/*   9:    */ import java.awt.Frame;
/*  10:    */ import java.awt.GridLayout;
/*  11:    */ import java.awt.event.ActionEvent;
/*  12:    */ import java.awt.event.ActionListener;
/*  13:    */ import java.awt.event.WindowEvent;
/*  14:    */ import javax.swing.AbstractButton;
/*  15:    */ import javax.swing.BorderFactory;
/*  16:    */ import javax.swing.JComponent;
/*  17:    */ import javax.swing.JEditorPane;
/*  18:    */ import javax.swing.JFrame;
/*  19:    */ import javax.swing.JList;
/*  20:    */ import javax.swing.JMenu;
/*  21:    */ import javax.swing.JMenuBar;
/*  22:    */ import javax.swing.JMenuItem;
/*  23:    */ import javax.swing.JOptionPane;
/*  24:    */ import javax.swing.JPanel;
/*  25:    */ import javax.swing.JScrollPane;
/*  26:    */ import javax.swing.JSplitPane;
/*  27:    */ import javax.swing.JTextPane;
/*  28:    */ import javax.swing.KeyStroke;
/*  29:    */ import javax.swing.ListModel;
/*  30:    */ import javax.swing.event.ListSelectionEvent;
/*  31:    */ import javax.swing.event.ListSelectionListener;
/*  32:    */ import javax.swing.text.JTextComponent;
/*  33:    */ import org.apache.bcel.Repository;
/*  34:    */ import org.apache.bcel.classfile.JavaClass;
/*  35:    */ import org.apache.bcel.classfile.Method;
/*  36:    */ 
/*  37:    */ public class VerifierAppFrame
/*  38:    */   extends JFrame
/*  39:    */ {
/*  40:    */   JPanel contentPane;
/*  41: 75 */   JSplitPane jSplitPane1 = new JSplitPane();
/*  42: 76 */   JPanel jPanel1 = new JPanel();
/*  43: 77 */   JPanel jPanel2 = new JPanel();
/*  44: 78 */   JSplitPane jSplitPane2 = new JSplitPane();
/*  45: 79 */   JPanel jPanel3 = new JPanel();
/*  46: 80 */   JList classNamesJList = new JList();
/*  47: 81 */   GridLayout gridLayout1 = new GridLayout();
/*  48: 82 */   JPanel messagesPanel = new JPanel();
/*  49: 83 */   GridLayout gridLayout2 = new GridLayout();
/*  50: 84 */   JMenuBar jMenuBar1 = new JMenuBar();
/*  51: 85 */   JMenu jMenu1 = new JMenu();
/*  52: 86 */   JScrollPane jScrollPane1 = new JScrollPane();
/*  53: 87 */   JScrollPane messagesScrollPane = new JScrollPane();
/*  54: 88 */   JScrollPane jScrollPane3 = new JScrollPane();
/*  55: 89 */   GridLayout gridLayout4 = new GridLayout();
/*  56: 90 */   JScrollPane jScrollPane4 = new JScrollPane();
/*  57: 91 */   CardLayout cardLayout1 = new CardLayout();
/*  58: 93 */   private String JUSTICE_VERSION = "JustIce by Enver Haase";
/*  59:    */   private String current_class;
/*  60: 95 */   GridLayout gridLayout3 = new GridLayout();
/*  61: 96 */   JTextPane pass1TextPane = new JTextPane();
/*  62: 97 */   JTextPane pass2TextPane = new JTextPane();
/*  63: 98 */   JTextPane messagesTextPane = new JTextPane();
/*  64: 99 */   JMenuItem newFileMenuItem = new JMenuItem();
/*  65:100 */   JSplitPane jSplitPane3 = new JSplitPane();
/*  66:101 */   JSplitPane jSplitPane4 = new JSplitPane();
/*  67:102 */   JScrollPane jScrollPane2 = new JScrollPane();
/*  68:103 */   JScrollPane jScrollPane5 = new JScrollPane();
/*  69:104 */   JScrollPane jScrollPane6 = new JScrollPane();
/*  70:105 */   JScrollPane jScrollPane7 = new JScrollPane();
/*  71:106 */   JList pass3aJList = new JList();
/*  72:107 */   JList pass3bJList = new JList();
/*  73:108 */   JTextPane pass3aTextPane = new JTextPane();
/*  74:109 */   JTextPane pass3bTextPane = new JTextPane();
/*  75:110 */   JMenu jMenu2 = new JMenu();
/*  76:111 */   JMenuItem whatisMenuItem = new JMenuItem();
/*  77:112 */   JMenuItem aboutMenuItem = new JMenuItem();
/*  78:    */   
/*  79:    */   public VerifierAppFrame()
/*  80:    */   {
/*  81:116 */     enableEvents(64L);
/*  82:    */     try
/*  83:    */     {
/*  84:118 */       jbInit();
/*  85:    */     }
/*  86:    */     catch (Exception e)
/*  87:    */     {
/*  88:121 */       e.printStackTrace();
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   private void jbInit()
/*  93:    */     throws Exception
/*  94:    */   {
/*  95:127 */     this.contentPane = ((JPanel)getContentPane());
/*  96:128 */     this.contentPane.setLayout(this.cardLayout1);
/*  97:129 */     setJMenuBar(this.jMenuBar1);
/*  98:130 */     setSize(new Dimension(708, 451));
/*  99:131 */     setTitle("JustIce");
/* 100:132 */     this.jPanel1.setMinimumSize(new Dimension(100, 100));
/* 101:133 */     this.jPanel1.setPreferredSize(new Dimension(100, 100));
/* 102:134 */     this.jPanel1.setLayout(this.gridLayout1);
/* 103:135 */     this.jSplitPane2.setOrientation(0);
/* 104:136 */     this.jPanel2.setLayout(this.gridLayout2);
/* 105:137 */     this.jPanel3.setMinimumSize(new Dimension(200, 100));
/* 106:138 */     this.jPanel3.setPreferredSize(new Dimension(400, 400));
/* 107:139 */     this.jPanel3.setLayout(this.gridLayout4);
/* 108:140 */     this.messagesPanel.setMinimumSize(new Dimension(100, 100));
/* 109:141 */     this.messagesPanel.setLayout(this.gridLayout3);
/* 110:142 */     this.jPanel2.setMinimumSize(new Dimension(200, 100));
/* 111:143 */     this.jMenu1.setText("File");
/* 112:    */     
/* 113:145 */     this.jScrollPane1.getViewport().setBackground(Color.red);
/* 114:146 */     this.messagesScrollPane.getViewport().setBackground(Color.red);
/* 115:147 */     this.messagesScrollPane.setPreferredSize(new Dimension(10, 10));
/* 116:148 */     this.classNamesJList.addListSelectionListener(new ListSelectionListener()
/* 117:    */     {
/* 118:    */       public void valueChanged(ListSelectionEvent e)
/* 119:    */       {
/* 120:150 */         VerifierAppFrame.this.classNamesJList_valueChanged(e);
/* 121:    */       }
/* 122:152 */     });
/* 123:153 */     this.classNamesJList.setSelectionMode(0);
/* 124:154 */     this.jScrollPane3.setBorder(BorderFactory.createLineBorder(Color.black));
/* 125:155 */     this.jScrollPane3.setPreferredSize(new Dimension(100, 100));
/* 126:156 */     this.gridLayout4.setRows(4);
/* 127:157 */     this.gridLayout4.setColumns(1);
/* 128:158 */     this.gridLayout4.setHgap(1);
/* 129:159 */     this.jScrollPane4.setBorder(BorderFactory.createLineBorder(Color.black));
/* 130:160 */     this.jScrollPane4.setPreferredSize(new Dimension(100, 100));
/* 131:161 */     this.pass1TextPane.setBorder(BorderFactory.createRaisedBevelBorder());
/* 132:162 */     this.pass1TextPane.setToolTipText("");
/* 133:163 */     this.pass1TextPane.setEditable(false);
/* 134:164 */     this.pass2TextPane.setBorder(BorderFactory.createRaisedBevelBorder());
/* 135:165 */     this.pass2TextPane.setEditable(false);
/* 136:166 */     this.messagesTextPane.setBorder(BorderFactory.createRaisedBevelBorder());
/* 137:167 */     this.messagesTextPane.setEditable(false);
/* 138:168 */     this.newFileMenuItem.setText("New...");
/* 139:169 */     this.newFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(78, 2, true));
/* 140:170 */     this.newFileMenuItem.addActionListener(new ActionListener()
/* 141:    */     {
/* 142:    */       public void actionPerformed(ActionEvent e)
/* 143:    */       {
/* 144:172 */         VerifierAppFrame.this.newFileMenuItem_actionPerformed(e);
/* 145:    */       }
/* 146:174 */     });
/* 147:175 */     this.pass3aTextPane.setEditable(false);
/* 148:176 */     this.pass3bTextPane.setEditable(false);
/* 149:177 */     this.pass3aJList.addListSelectionListener(new ListSelectionListener()
/* 150:    */     {
/* 151:    */       public void valueChanged(ListSelectionEvent e)
/* 152:    */       {
/* 153:179 */         VerifierAppFrame.this.pass3aJList_valueChanged(e);
/* 154:    */       }
/* 155:181 */     });
/* 156:182 */     this.pass3bJList.addListSelectionListener(new ListSelectionListener()
/* 157:    */     {
/* 158:    */       public void valueChanged(ListSelectionEvent e)
/* 159:    */       {
/* 160:184 */         VerifierAppFrame.this.pass3bJList_valueChanged(e);
/* 161:    */       }
/* 162:186 */     });
/* 163:187 */     this.jMenu2.setText("Help");
/* 164:188 */     this.whatisMenuItem.setText("What is...");
/* 165:189 */     this.whatisMenuItem.addActionListener(new ActionListener()
/* 166:    */     {
/* 167:    */       public void actionPerformed(ActionEvent e)
/* 168:    */       {
/* 169:191 */         VerifierAppFrame.this.whatisMenuItem_actionPerformed(e);
/* 170:    */       }
/* 171:193 */     });
/* 172:194 */     this.aboutMenuItem.setText("About");
/* 173:195 */     this.aboutMenuItem.addActionListener(new ActionListener()
/* 174:    */     {
/* 175:    */       public void actionPerformed(ActionEvent e)
/* 176:    */       {
/* 177:197 */         VerifierAppFrame.this.aboutMenuItem_actionPerformed(e);
/* 178:    */       }
/* 179:199 */     });
/* 180:200 */     this.jSplitPane2.add(this.messagesPanel, "bottom");
/* 181:201 */     this.messagesPanel.add(this.messagesScrollPane, null);
/* 182:202 */     this.messagesScrollPane.getViewport().add(this.messagesTextPane, null);
/* 183:203 */     this.jSplitPane2.add(this.jPanel3, "top");
/* 184:204 */     this.jPanel3.add(this.jScrollPane3, null);
/* 185:205 */     this.jScrollPane3.getViewport().add(this.pass1TextPane, null);
/* 186:206 */     this.jPanel3.add(this.jScrollPane4, null);
/* 187:207 */     this.jPanel3.add(this.jSplitPane3, null);
/* 188:208 */     this.jSplitPane3.add(this.jScrollPane2, "left");
/* 189:209 */     this.jScrollPane2.getViewport().add(this.pass3aJList, null);
/* 190:210 */     this.jSplitPane3.add(this.jScrollPane5, "right");
/* 191:211 */     this.jScrollPane5.getViewport().add(this.pass3aTextPane, null);
/* 192:212 */     this.jPanel3.add(this.jSplitPane4, null);
/* 193:213 */     this.jSplitPane4.add(this.jScrollPane6, "left");
/* 194:214 */     this.jScrollPane6.getViewport().add(this.pass3bJList, null);
/* 195:215 */     this.jSplitPane4.add(this.jScrollPane7, "right");
/* 196:216 */     this.jScrollPane7.getViewport().add(this.pass3bTextPane, null);
/* 197:217 */     this.jScrollPane4.getViewport().add(this.pass2TextPane, null);
/* 198:218 */     this.jSplitPane1.add(this.jPanel2, "top");
/* 199:219 */     this.jPanel2.add(this.jScrollPane1, null);
/* 200:220 */     this.jSplitPane1.add(this.jPanel1, "bottom");
/* 201:221 */     this.jPanel1.add(this.jSplitPane2, null);
/* 202:222 */     this.jScrollPane1.getViewport().add(this.classNamesJList, null);
/* 203:223 */     this.jMenuBar1.add(this.jMenu1);
/* 204:224 */     this.jMenuBar1.add(this.jMenu2);
/* 205:225 */     this.contentPane.add(this.jSplitPane1, "jSplitPane1");
/* 206:226 */     this.jMenu1.add(this.newFileMenuItem);
/* 207:227 */     this.jMenu2.add(this.whatisMenuItem);
/* 208:228 */     this.jMenu2.add(this.aboutMenuItem);
/* 209:229 */     this.jSplitPane2.setDividerLocation(300);
/* 210:230 */     this.jSplitPane3.setDividerLocation(150);
/* 211:231 */     this.jSplitPane4.setDividerLocation(150);
/* 212:    */   }
/* 213:    */   
/* 214:    */   protected void processWindowEvent(WindowEvent e)
/* 215:    */   {
/* 216:236 */     super.processWindowEvent(e);
/* 217:237 */     if (e.getID() == 201) {
/* 218:238 */       System.exit(0);
/* 219:    */     }
/* 220:    */   }
/* 221:    */   
/* 222:    */   synchronized void classNamesJList_valueChanged(ListSelectionEvent e)
/* 223:    */   {
/* 224:243 */     if (e.getValueIsAdjusting()) {
/* 225:243 */       return;
/* 226:    */     }
/* 227:244 */     this.current_class = this.classNamesJList.getSelectedValue().toString();
/* 228:245 */     verify();
/* 229:246 */     this.classNamesJList.setSelectedValue(this.current_class, true);
/* 230:    */   }
/* 231:    */   
/* 232:    */   private void verify()
/* 233:    */   {
/* 234:250 */     setTitle("PLEASE WAIT");
/* 235:    */     
/* 236:252 */     Verifier v = VerifierFactory.getVerifier(this.current_class);
/* 237:253 */     v.flush();
/* 238:    */     
/* 239:    */ 
/* 240:    */ 
/* 241:257 */     VerificationResult vr = v.doPass1();
/* 242:258 */     if (vr.getStatus() == 2)
/* 243:    */     {
/* 244:259 */       this.pass1TextPane.setText(vr.getMessage());
/* 245:260 */       this.pass1TextPane.setBackground(Color.red);
/* 246:    */       
/* 247:262 */       this.pass2TextPane.setText("");
/* 248:263 */       this.pass2TextPane.setBackground(Color.yellow);
/* 249:264 */       this.pass3aTextPane.setText("");
/* 250:265 */       this.pass3aJList.setListData(new Object[0]);
/* 251:266 */       this.pass3aTextPane.setBackground(Color.yellow);
/* 252:    */       
/* 253:268 */       this.pass3bTextPane.setText("");
/* 254:269 */       this.pass3bJList.setListData(new Object[0]);
/* 255:270 */       this.pass3bTextPane.setBackground(Color.yellow);
/* 256:    */     }
/* 257:    */     else
/* 258:    */     {
/* 259:274 */       this.pass1TextPane.setBackground(Color.green);
/* 260:275 */       this.pass1TextPane.setText(vr.getMessage());
/* 261:    */       
/* 262:277 */       vr = v.doPass2();
/* 263:278 */       if (vr.getStatus() == 2)
/* 264:    */       {
/* 265:279 */         this.pass2TextPane.setText(vr.getMessage());
/* 266:280 */         this.pass2TextPane.setBackground(Color.red);
/* 267:    */         
/* 268:282 */         this.pass3aTextPane.setText("");
/* 269:283 */         this.pass3aTextPane.setBackground(Color.yellow);
/* 270:284 */         this.pass3aJList.setListData(new Object[0]);
/* 271:285 */         this.pass3bTextPane.setText("");
/* 272:286 */         this.pass3bTextPane.setBackground(Color.yellow);
/* 273:287 */         this.pass3bJList.setListData(new Object[0]);
/* 274:    */       }
/* 275:    */       else
/* 276:    */       {
/* 277:290 */         this.pass2TextPane.setText(vr.getMessage());
/* 278:291 */         this.pass2TextPane.setBackground(Color.green);
/* 279:    */         
/* 280:293 */         JavaClass jc = Repository.lookupClass(this.current_class);
/* 281:294 */         boolean all3aok = true;
/* 282:295 */         boolean all3bok = true;
/* 283:296 */         String all3amsg = "";
/* 284:297 */         String all3bmsg = "";
/* 285:    */         
/* 286:299 */         String[] methodnames = new String[jc.getMethods().length];
/* 287:300 */         for (int i = 0; i < jc.getMethods().length; i++) {
/* 288:301 */           methodnames[i] = jc.getMethods()[i].toString().replace('\n', ' ').replace('\t', ' ');
/* 289:    */         }
/* 290:303 */         this.pass3aJList.setListData(methodnames);
/* 291:304 */         this.pass3aJList.setSelectionInterval(0, jc.getMethods().length - 1);
/* 292:305 */         this.pass3bJList.setListData(methodnames);
/* 293:306 */         this.pass3bJList.setSelectionInterval(0, jc.getMethods().length - 1);
/* 294:    */       }
/* 295:    */     }
/* 296:310 */     String[] msgs = v.getMessages();
/* 297:311 */     this.messagesTextPane.setBackground(msgs.length == 0 ? Color.green : Color.yellow);
/* 298:312 */     String allmsgs = "";
/* 299:313 */     for (int i = 0; i < msgs.length; i++)
/* 300:    */     {
/* 301:314 */       msgs[i] = msgs[i].replace('\n', ' ');
/* 302:315 */       allmsgs = allmsgs + msgs[i] + "\n\n";
/* 303:    */     }
/* 304:317 */     this.messagesTextPane.setText(allmsgs);
/* 305:    */     
/* 306:319 */     setTitle(this.current_class + " - " + this.JUSTICE_VERSION);
/* 307:    */   }
/* 308:    */   
/* 309:    */   void newFileMenuItem_actionPerformed(ActionEvent e)
/* 310:    */   {
/* 311:323 */     String classname = JOptionPane.showInputDialog("Please enter the fully qualified name of a class or interface to verify:");
/* 312:324 */     if ((classname == null) || (classname.equals(""))) {
/* 313:324 */       return;
/* 314:    */     }
/* 315:325 */     VerifierFactory.getVerifier(classname);
/* 316:326 */     this.classNamesJList.setSelectedValue(classname, true);
/* 317:    */   }
/* 318:    */   
/* 319:    */   synchronized void pass3aJList_valueChanged(ListSelectionEvent e)
/* 320:    */   {
/* 321:331 */     if (e.getValueIsAdjusting()) {
/* 322:331 */       return;
/* 323:    */     }
/* 324:332 */     Verifier v = VerifierFactory.getVerifier(this.current_class);
/* 325:    */     
/* 326:334 */     String all3amsg = "";
/* 327:335 */     boolean all3aok = true;
/* 328:336 */     boolean rejected = false;
/* 329:337 */     for (int i = 0; i < this.pass3aJList.getModel().getSize(); i++) {
/* 330:339 */       if (this.pass3aJList.isSelectedIndex(i))
/* 331:    */       {
/* 332:340 */         VerificationResult vr = v.doPass3a(i);
/* 333:342 */         if (vr.getStatus() == 2)
/* 334:    */         {
/* 335:343 */           all3aok = false;
/* 336:344 */           rejected = true;
/* 337:    */         }
/* 338:346 */         all3amsg = all3amsg + "Method '" + Repository.lookupClass(v.getClassName()).getMethods()[i] + "': " + vr.getMessage().replace('\n', ' ') + "\n\n";
/* 339:    */       }
/* 340:    */     }
/* 341:349 */     this.pass3aTextPane.setText(all3amsg);
/* 342:350 */     this.pass3aTextPane.setBackground(rejected ? Color.red : all3aok ? Color.green : Color.yellow);
/* 343:    */   }
/* 344:    */   
/* 345:    */   synchronized void pass3bJList_valueChanged(ListSelectionEvent e)
/* 346:    */   {
/* 347:355 */     if (e.getValueIsAdjusting()) {
/* 348:355 */       return;
/* 349:    */     }
/* 350:357 */     Verifier v = VerifierFactory.getVerifier(this.current_class);
/* 351:    */     
/* 352:359 */     String all3bmsg = "";
/* 353:360 */     boolean all3bok = true;
/* 354:361 */     boolean rejected = false;
/* 355:362 */     for (int i = 0; i < this.pass3bJList.getModel().getSize(); i++) {
/* 356:364 */       if (this.pass3bJList.isSelectedIndex(i))
/* 357:    */       {
/* 358:365 */         VerificationResult vr = v.doPass3b(i);
/* 359:367 */         if (vr.getStatus() == 2)
/* 360:    */         {
/* 361:368 */           all3bok = false;
/* 362:369 */           rejected = true;
/* 363:    */         }
/* 364:371 */         all3bmsg = all3bmsg + "Method '" + Repository.lookupClass(v.getClassName()).getMethods()[i] + "': " + vr.getMessage().replace('\n', ' ') + "\n\n";
/* 365:    */       }
/* 366:    */     }
/* 367:374 */     this.pass3bTextPane.setText(all3bmsg);
/* 368:375 */     this.pass3bTextPane.setBackground(rejected ? Color.red : all3bok ? Color.green : Color.yellow);
/* 369:    */   }
/* 370:    */   
/* 371:    */   void aboutMenuItem_actionPerformed(ActionEvent e)
/* 372:    */   {
/* 373:380 */     JOptionPane.showMessageDialog(this, "JustIce is a Java class file verifier.\nIt was implemented by Enver Haase in 2001.\nhttp://bcel.sourceforge.net", this.JUSTICE_VERSION, 1);
/* 374:    */   }
/* 375:    */   
/* 376:    */   void whatisMenuItem_actionPerformed(ActionEvent e)
/* 377:    */   {
/* 378:386 */     JOptionPane.showMessageDialog(this, "The upper four boxes to the right reflect verification passes according to The Java Virtual Machine Specification.\nThese are (in that order): Pass one, Pass two, Pass three (before data flow analysis), Pass three (data flow analysis).\nThe bottom box to the right shows (warning) messages; warnings do not cause a class to be rejected.", this.JUSTICE_VERSION, 1);
/* 379:    */   }
/* 380:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.VerifierAppFrame
 * JD-Core Version:    0.7.0.1
 */