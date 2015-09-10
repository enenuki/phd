/*   1:    */ package org.apache.bcel.verifier;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Component;
/*   5:    */ import java.awt.Container;
/*   6:    */ import java.awt.Dialog;
/*   7:    */ import java.awt.Frame;
/*   8:    */ import java.awt.SystemColor;
/*   9:    */ import java.awt.Window;
/*  10:    */ import java.awt.event.ActionEvent;
/*  11:    */ import java.awt.event.ActionListener;
/*  12:    */ import java.awt.event.WindowAdapter;
/*  13:    */ import java.awt.event.WindowEvent;
/*  14:    */ import java.io.PrintStream;
/*  15:    */ import java.util.EventObject;
/*  16:    */ import javax.swing.AbstractButton;
/*  17:    */ import javax.swing.JButton;
/*  18:    */ import javax.swing.JComponent;
/*  19:    */ import javax.swing.JDialog;
/*  20:    */ import javax.swing.JPanel;
/*  21:    */ import org.apache.bcel.Repository;
/*  22:    */ import org.apache.bcel.classfile.JavaClass;
/*  23:    */ 
/*  24:    */ public class VerifyDialog
/*  25:    */   extends JDialog
/*  26:    */ {
/*  27: 78 */   private JPanel ivjJDialogContentPane = null;
/*  28: 80 */   private JPanel ivjPass1Panel = null;
/*  29: 82 */   private JPanel ivjPass2Panel = null;
/*  30: 84 */   private JPanel ivjPass3Panel = null;
/*  31: 86 */   private JButton ivjPass1Button = null;
/*  32: 88 */   private JButton ivjPass2Button = null;
/*  33: 90 */   private JButton ivjPass3Button = null;
/*  34: 92 */   IvjEventHandler ivjEventHandler = new IvjEventHandler();
/*  35: 99 */   private String class_name = "java.lang.Object";
/*  36:    */   private static int classes_to_verify;
/*  37:    */   
/*  38:    */   class IvjEventHandler
/*  39:    */     implements ActionListener
/*  40:    */   {
/*  41:    */     IvjEventHandler() {}
/*  42:    */     
/*  43:    */     public void actionPerformed(ActionEvent e)
/*  44:    */     {
/*  45:111 */       if (e.getSource() == VerifyDialog.this.getPass1Button()) {
/*  46:112 */         VerifyDialog.this.connEtoC1(e);
/*  47:    */       }
/*  48:113 */       if (e.getSource() == VerifyDialog.this.getPass2Button()) {
/*  49:114 */         VerifyDialog.this.connEtoC2(e);
/*  50:    */       }
/*  51:115 */       if (e.getSource() == VerifyDialog.this.getPass3Button()) {
/*  52:116 */         VerifyDialog.this.connEtoC3(e);
/*  53:    */       }
/*  54:117 */       if (e.getSource() == VerifyDialog.this.getFlushButton()) {
/*  55:118 */         VerifyDialog.this.connEtoC4(e);
/*  56:    */       }
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:122 */   private JButton ivjFlushButton = null;
/*  61:    */   
/*  62:    */   public VerifyDialog()
/*  63:    */   {
/*  64:126 */     initialize();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public VerifyDialog(Dialog owner)
/*  68:    */   {
/*  69:131 */     super(owner);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public VerifyDialog(Dialog owner, String title)
/*  73:    */   {
/*  74:136 */     super(owner, title);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public VerifyDialog(Dialog owner, String title, boolean modal)
/*  78:    */   {
/*  79:141 */     super(owner, title, modal);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public VerifyDialog(Dialog owner, boolean modal)
/*  83:    */   {
/*  84:146 */     super(owner, modal);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public VerifyDialog(Frame owner)
/*  88:    */   {
/*  89:151 */     super(owner);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public VerifyDialog(Frame owner, String title)
/*  93:    */   {
/*  94:156 */     super(owner, title);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public VerifyDialog(Frame owner, String title, boolean modal)
/*  98:    */   {
/*  99:161 */     super(owner, title, modal);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public VerifyDialog(Frame owner, boolean modal)
/* 103:    */   {
/* 104:166 */     super(owner, modal);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public VerifyDialog(String fully_qualified_class_name)
/* 108:    */   {
/* 109:177 */     int dotclasspos = fully_qualified_class_name.lastIndexOf(".class");
/* 110:178 */     if (dotclasspos != -1) {
/* 111:178 */       fully_qualified_class_name = fully_qualified_class_name.substring(0, dotclasspos);
/* 112:    */     }
/* 113:179 */     fully_qualified_class_name = fully_qualified_class_name.replace('/', '.');
/* 114:    */     
/* 115:181 */     this.class_name = fully_qualified_class_name;
/* 116:182 */     initialize();
/* 117:    */   }
/* 118:    */   
/* 119:    */   private void connEtoC1(ActionEvent arg1)
/* 120:    */   {
/* 121:    */     try
/* 122:    */     {
/* 123:191 */       pass1Button_ActionPerformed(arg1);
/* 124:    */     }
/* 125:    */     catch (Throwable ivjExc)
/* 126:    */     {
/* 127:197 */       handleException(ivjExc);
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   private void connEtoC2(ActionEvent arg1)
/* 132:    */   {
/* 133:    */     try
/* 134:    */     {
/* 135:206 */       pass2Button_ActionPerformed(arg1);
/* 136:    */     }
/* 137:    */     catch (Throwable ivjExc)
/* 138:    */     {
/* 139:212 */       handleException(ivjExc);
/* 140:    */     }
/* 141:    */   }
/* 142:    */   
/* 143:    */   private void connEtoC3(ActionEvent arg1)
/* 144:    */   {
/* 145:    */     try
/* 146:    */     {
/* 147:221 */       pass4Button_ActionPerformed(arg1);
/* 148:    */     }
/* 149:    */     catch (Throwable ivjExc)
/* 150:    */     {
/* 151:227 */       handleException(ivjExc);
/* 152:    */     }
/* 153:    */   }
/* 154:    */   
/* 155:    */   private void connEtoC4(ActionEvent arg1)
/* 156:    */   {
/* 157:    */     try
/* 158:    */     {
/* 159:236 */       flushButton_ActionPerformed(arg1);
/* 160:    */     }
/* 161:    */     catch (Throwable ivjExc)
/* 162:    */     {
/* 163:242 */       handleException(ivjExc);
/* 164:    */     }
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void flushButton_ActionPerformed(ActionEvent actionEvent)
/* 168:    */   {
/* 169:248 */     VerifierFactory.getVerifier(this.class_name).flush();
/* 170:249 */     Repository.removeClass(this.class_name);
/* 171:250 */     getPass1Panel().setBackground(Color.gray);
/* 172:251 */     getPass1Panel().repaint();
/* 173:252 */     getPass2Panel().setBackground(Color.gray);
/* 174:253 */     getPass2Panel().repaint();
/* 175:254 */     getPass3Panel().setBackground(Color.gray);
/* 176:255 */     getPass3Panel().repaint();
/* 177:    */   }
/* 178:    */   
/* 179:    */   private JButton getFlushButton()
/* 180:    */   {
/* 181:260 */     if (this.ivjFlushButton == null) {
/* 182:    */       try
/* 183:    */       {
/* 184:262 */         this.ivjFlushButton = new JButton();
/* 185:263 */         this.ivjFlushButton.setName("FlushButton");
/* 186:264 */         this.ivjFlushButton.setText("Flush: Forget old verification results");
/* 187:265 */         this.ivjFlushButton.setBackground(SystemColor.controlHighlight);
/* 188:266 */         this.ivjFlushButton.setBounds(60, 215, 300, 30);
/* 189:267 */         this.ivjFlushButton.setForeground(Color.red);
/* 190:268 */         this.ivjFlushButton.setActionCommand("FlushButton");
/* 191:    */       }
/* 192:    */       catch (Throwable ivjExc)
/* 193:    */       {
/* 194:274 */         handleException(ivjExc);
/* 195:    */       }
/* 196:    */     }
/* 197:277 */     return this.ivjFlushButton;
/* 198:    */   }
/* 199:    */   
/* 200:    */   private JPanel getJDialogContentPane()
/* 201:    */   {
/* 202:282 */     if (this.ivjJDialogContentPane == null) {
/* 203:    */       try
/* 204:    */       {
/* 205:284 */         this.ivjJDialogContentPane = new JPanel();
/* 206:285 */         this.ivjJDialogContentPane.setName("JDialogContentPane");
/* 207:286 */         this.ivjJDialogContentPane.setLayout(null);
/* 208:287 */         getJDialogContentPane().add(getPass1Panel(), getPass1Panel().getName());
/* 209:288 */         getJDialogContentPane().add(getPass3Panel(), getPass3Panel().getName());
/* 210:289 */         getJDialogContentPane().add(getPass2Panel(), getPass2Panel().getName());
/* 211:290 */         getJDialogContentPane().add(getPass1Button(), getPass1Button().getName());
/* 212:291 */         getJDialogContentPane().add(getPass2Button(), getPass2Button().getName());
/* 213:292 */         getJDialogContentPane().add(getPass3Button(), getPass3Button().getName());
/* 214:293 */         getJDialogContentPane().add(getFlushButton(), getFlushButton().getName());
/* 215:    */       }
/* 216:    */       catch (Throwable ivjExc)
/* 217:    */       {
/* 218:299 */         handleException(ivjExc);
/* 219:    */       }
/* 220:    */     }
/* 221:302 */     return this.ivjJDialogContentPane;
/* 222:    */   }
/* 223:    */   
/* 224:    */   private JButton getPass1Button()
/* 225:    */   {
/* 226:307 */     if (this.ivjPass1Button == null) {
/* 227:    */       try
/* 228:    */       {
/* 229:309 */         this.ivjPass1Button = new JButton();
/* 230:310 */         this.ivjPass1Button.setName("Pass1Button");
/* 231:311 */         this.ivjPass1Button.setText("Pass1: Verify binary layout of .class file");
/* 232:312 */         this.ivjPass1Button.setBackground(SystemColor.controlHighlight);
/* 233:313 */         this.ivjPass1Button.setBounds(100, 40, 300, 30);
/* 234:314 */         this.ivjPass1Button.setActionCommand("Button1");
/* 235:    */       }
/* 236:    */       catch (Throwable ivjExc)
/* 237:    */       {
/* 238:320 */         handleException(ivjExc);
/* 239:    */       }
/* 240:    */     }
/* 241:323 */     return this.ivjPass1Button;
/* 242:    */   }
/* 243:    */   
/* 244:    */   private JPanel getPass1Panel()
/* 245:    */   {
/* 246:328 */     if (this.ivjPass1Panel == null) {
/* 247:    */       try
/* 248:    */       {
/* 249:330 */         this.ivjPass1Panel = new JPanel();
/* 250:331 */         this.ivjPass1Panel.setName("Pass1Panel");
/* 251:332 */         this.ivjPass1Panel.setLayout(null);
/* 252:333 */         this.ivjPass1Panel.setBackground(SystemColor.controlShadow);
/* 253:334 */         this.ivjPass1Panel.setBounds(30, 30, 50, 50);
/* 254:    */       }
/* 255:    */       catch (Throwable ivjExc)
/* 256:    */       {
/* 257:340 */         handleException(ivjExc);
/* 258:    */       }
/* 259:    */     }
/* 260:343 */     return this.ivjPass1Panel;
/* 261:    */   }
/* 262:    */   
/* 263:    */   private JButton getPass2Button()
/* 264:    */   {
/* 265:348 */     if (this.ivjPass2Button == null) {
/* 266:    */       try
/* 267:    */       {
/* 268:350 */         this.ivjPass2Button = new JButton();
/* 269:351 */         this.ivjPass2Button.setName("Pass2Button");
/* 270:352 */         this.ivjPass2Button.setText("Pass 2: Verify static .class file constraints");
/* 271:353 */         this.ivjPass2Button.setBackground(SystemColor.controlHighlight);
/* 272:354 */         this.ivjPass2Button.setBounds(100, 100, 300, 30);
/* 273:355 */         this.ivjPass2Button.setActionCommand("Button2");
/* 274:    */       }
/* 275:    */       catch (Throwable ivjExc)
/* 276:    */       {
/* 277:361 */         handleException(ivjExc);
/* 278:    */       }
/* 279:    */     }
/* 280:364 */     return this.ivjPass2Button;
/* 281:    */   }
/* 282:    */   
/* 283:    */   private JPanel getPass2Panel()
/* 284:    */   {
/* 285:369 */     if (this.ivjPass2Panel == null) {
/* 286:    */       try
/* 287:    */       {
/* 288:371 */         this.ivjPass2Panel = new JPanel();
/* 289:372 */         this.ivjPass2Panel.setName("Pass2Panel");
/* 290:373 */         this.ivjPass2Panel.setLayout(null);
/* 291:374 */         this.ivjPass2Panel.setBackground(SystemColor.controlShadow);
/* 292:375 */         this.ivjPass2Panel.setBounds(30, 90, 50, 50);
/* 293:    */       }
/* 294:    */       catch (Throwable ivjExc)
/* 295:    */       {
/* 296:381 */         handleException(ivjExc);
/* 297:    */       }
/* 298:    */     }
/* 299:384 */     return this.ivjPass2Panel;
/* 300:    */   }
/* 301:    */   
/* 302:    */   private JButton getPass3Button()
/* 303:    */   {
/* 304:389 */     if (this.ivjPass3Button == null) {
/* 305:    */       try
/* 306:    */       {
/* 307:391 */         this.ivjPass3Button = new JButton();
/* 308:392 */         this.ivjPass3Button.setName("Pass3Button");
/* 309:393 */         this.ivjPass3Button.setText("Passes 3a+3b: Verify code arrays");
/* 310:394 */         this.ivjPass3Button.setBackground(SystemColor.controlHighlight);
/* 311:395 */         this.ivjPass3Button.setBounds(100, 160, 300, 30);
/* 312:396 */         this.ivjPass3Button.setActionCommand("Button2");
/* 313:    */       }
/* 314:    */       catch (Throwable ivjExc)
/* 315:    */       {
/* 316:402 */         handleException(ivjExc);
/* 317:    */       }
/* 318:    */     }
/* 319:405 */     return this.ivjPass3Button;
/* 320:    */   }
/* 321:    */   
/* 322:    */   private JPanel getPass3Panel()
/* 323:    */   {
/* 324:410 */     if (this.ivjPass3Panel == null) {
/* 325:    */       try
/* 326:    */       {
/* 327:412 */         this.ivjPass3Panel = new JPanel();
/* 328:413 */         this.ivjPass3Panel.setName("Pass3Panel");
/* 329:414 */         this.ivjPass3Panel.setLayout(null);
/* 330:415 */         this.ivjPass3Panel.setBackground(SystemColor.controlShadow);
/* 331:416 */         this.ivjPass3Panel.setBounds(30, 150, 50, 50);
/* 332:    */       }
/* 333:    */       catch (Throwable ivjExc)
/* 334:    */       {
/* 335:422 */         handleException(ivjExc);
/* 336:    */       }
/* 337:    */     }
/* 338:425 */     return this.ivjPass3Panel;
/* 339:    */   }
/* 340:    */   
/* 341:    */   private void handleException(Throwable exception)
/* 342:    */   {
/* 343:432 */     System.out.println("--------- UNCAUGHT EXCEPTION ---------");
/* 344:433 */     exception.printStackTrace(System.out);
/* 345:    */   }
/* 346:    */   
/* 347:    */   private void initConnections()
/* 348:    */     throws Exception
/* 349:    */   {
/* 350:441 */     getPass1Button().addActionListener(this.ivjEventHandler);
/* 351:442 */     getPass2Button().addActionListener(this.ivjEventHandler);
/* 352:443 */     getPass3Button().addActionListener(this.ivjEventHandler);
/* 353:444 */     getFlushButton().addActionListener(this.ivjEventHandler);
/* 354:    */   }
/* 355:    */   
/* 356:    */   private void initialize()
/* 357:    */   {
/* 358:    */     try
/* 359:    */     {
/* 360:452 */       setName("VerifyDialog");
/* 361:453 */       setDefaultCloseOperation(2);
/* 362:454 */       setSize(430, 280);
/* 363:455 */       setVisible(true);
/* 364:456 */       setModal(true);
/* 365:457 */       setResizable(false);
/* 366:458 */       setContentPane(getJDialogContentPane());
/* 367:459 */       initConnections();
/* 368:    */     }
/* 369:    */     catch (Throwable ivjExc)
/* 370:    */     {
/* 371:461 */       handleException(ivjExc);
/* 372:    */     }
/* 373:464 */     setTitle("'" + this.class_name + "' verification - JustIce / BCEL");
/* 374:    */   }
/* 375:    */   
/* 376:    */   public static void main(String[] args)
/* 377:    */   {
/* 378:474 */     classes_to_verify = args.length;
/* 379:476 */     for (int i = 0; i < args.length; i++) {
/* 380:    */       try
/* 381:    */       {
/* 382:480 */         VerifyDialog aVerifyDialog = new VerifyDialog(args[i]);
/* 383:481 */         aVerifyDialog.setModal(true);
/* 384:482 */         aVerifyDialog.addWindowListener(new WindowAdapter()
/* 385:    */         {
/* 386:    */           public void windowClosing(WindowEvent e)
/* 387:    */           {
/* 388:484 */             VerifyDialog.access$810();
/* 389:485 */             if (VerifyDialog.classes_to_verify == 0) {
/* 390:485 */               System.exit(0);
/* 391:    */             }
/* 392:    */           }
/* 393:487 */         });
/* 394:488 */         aVerifyDialog.setVisible(true);
/* 395:    */       }
/* 396:    */       catch (Throwable exception)
/* 397:    */       {
/* 398:490 */         System.err.println("Exception occurred in main() of javax.swing.JDialog");
/* 399:491 */         exception.printStackTrace(System.out);
/* 400:    */       }
/* 401:    */     }
/* 402:    */   }
/* 403:    */   
/* 404:    */   public void pass1Button_ActionPerformed(ActionEvent actionEvent)
/* 405:    */   {
/* 406:499 */     Verifier v = VerifierFactory.getVerifier(this.class_name);
/* 407:500 */     VerificationResult vr = v.doPass1();
/* 408:501 */     if (vr.getStatus() == 1)
/* 409:    */     {
/* 410:502 */       getPass1Panel().setBackground(Color.green);
/* 411:503 */       getPass1Panel().repaint();
/* 412:    */     }
/* 413:505 */     if (vr.getStatus() == 2)
/* 414:    */     {
/* 415:506 */       getPass1Panel().setBackground(Color.red);
/* 416:507 */       getPass1Panel().repaint();
/* 417:    */     }
/* 418:    */   }
/* 419:    */   
/* 420:    */   public void pass2Button_ActionPerformed(ActionEvent actionEvent)
/* 421:    */   {
/* 422:513 */     pass1Button_ActionPerformed(actionEvent);
/* 423:    */     
/* 424:515 */     Verifier v = VerifierFactory.getVerifier(this.class_name);
/* 425:516 */     VerificationResult vr = v.doPass2();
/* 426:517 */     if (vr.getStatus() == 1)
/* 427:    */     {
/* 428:518 */       getPass2Panel().setBackground(Color.green);
/* 429:519 */       getPass2Panel().repaint();
/* 430:    */     }
/* 431:521 */     if (vr.getStatus() == 0)
/* 432:    */     {
/* 433:522 */       getPass2Panel().setBackground(Color.yellow);
/* 434:523 */       getPass2Panel().repaint();
/* 435:    */     }
/* 436:525 */     if (vr.getStatus() == 2)
/* 437:    */     {
/* 438:526 */       getPass2Panel().setBackground(Color.red);
/* 439:527 */       getPass2Panel().repaint();
/* 440:    */     }
/* 441:    */   }
/* 442:    */   
/* 443:    */   public void pass4Button_ActionPerformed(ActionEvent actionEvent)
/* 444:    */   {
/* 445:534 */     pass2Button_ActionPerformed(actionEvent);
/* 446:    */     
/* 447:    */ 
/* 448:537 */     Color color = Color.green;
/* 449:    */     
/* 450:539 */     Verifier v = VerifierFactory.getVerifier(this.class_name);
/* 451:540 */     VerificationResult vr = v.doPass2();
/* 452:541 */     if (vr.getStatus() == 1)
/* 453:    */     {
/* 454:542 */       JavaClass jc = Repository.lookupClass(this.class_name);
/* 455:543 */       int nr = jc.getMethods().length;
/* 456:544 */       for (int i = 0; i < nr; i++)
/* 457:    */       {
/* 458:545 */         vr = v.doPass3b(i);
/* 459:546 */         if (vr.getStatus() != 1)
/* 460:    */         {
/* 461:547 */           color = Color.red;
/* 462:548 */           break;
/* 463:    */         }
/* 464:    */       }
/* 465:    */     }
/* 466:    */     else
/* 467:    */     {
/* 468:553 */       color = Color.yellow;
/* 469:    */     }
/* 470:556 */     getPass3Panel().setBackground(color);
/* 471:557 */     getPass3Panel().repaint();
/* 472:    */   }
/* 473:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.VerifyDialog
 * JD-Core Version:    0.7.0.1
 */