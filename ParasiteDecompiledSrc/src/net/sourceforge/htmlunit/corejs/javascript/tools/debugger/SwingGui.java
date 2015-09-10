/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.tools.debugger;
/*   2:    */ 
/*   3:    */ import java.awt.AWTEvent;
/*   4:    */ import java.awt.ActiveEvent;
/*   5:    */ import java.awt.BorderLayout;
/*   6:    */ import java.awt.Component;
/*   7:    */ import java.awt.Container;
/*   8:    */ import java.awt.Dimension;
/*   9:    */ import java.awt.EventQueue;
/*  10:    */ import java.awt.MenuComponent;
/*  11:    */ import java.awt.Toolkit;
/*  12:    */ import java.awt.event.ActionEvent;
/*  13:    */ import java.awt.event.ActionListener;
/*  14:    */ import java.awt.event.WindowAdapter;
/*  15:    */ import java.awt.event.WindowEvent;
/*  16:    */ import java.io.File;
/*  17:    */ import java.io.FileReader;
/*  18:    */ import java.io.IOException;
/*  19:    */ import java.io.Reader;
/*  20:    */ import java.lang.reflect.InvocationTargetException;
/*  21:    */ import java.lang.reflect.Method;
/*  22:    */ import java.util.Collections;
/*  23:    */ import java.util.HashMap;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import java.util.Properties;
/*  27:    */ import javax.swing.DesktopManager;
/*  28:    */ import javax.swing.JButton;
/*  29:    */ import javax.swing.JComboBox;
/*  30:    */ import javax.swing.JDesktopPane;
/*  31:    */ import javax.swing.JFileChooser;
/*  32:    */ import javax.swing.JFrame;
/*  33:    */ import javax.swing.JInternalFrame;
/*  34:    */ import javax.swing.JLabel;
/*  35:    */ import javax.swing.JMenu;
/*  36:    */ import javax.swing.JMenuItem;
/*  37:    */ import javax.swing.JPanel;
/*  38:    */ import javax.swing.JSplitPane;
/*  39:    */ import javax.swing.JTextArea;
/*  40:    */ import javax.swing.JToolBar;
/*  41:    */ import javax.swing.SwingUtilities;
/*  42:    */ import javax.swing.filechooser.FileFilter;
/*  43:    */ import javax.swing.text.BadLocationException;
/*  44:    */ import net.sourceforge.htmlunit.corejs.javascript.Kit;
/*  45:    */ import net.sourceforge.htmlunit.corejs.javascript.SecurityUtilities;
/*  46:    */ import net.sourceforge.htmlunit.corejs.javascript.tools.shell.ConsoleTextArea;
/*  47:    */ 
/*  48:    */ public class SwingGui
/*  49:    */   extends JFrame
/*  50:    */   implements GuiCallback
/*  51:    */ {
/*  52:    */   private static final long serialVersionUID = -8217029773456711621L;
/*  53:    */   Dim dim;
/*  54:    */   private Runnable exitAction;
/*  55:    */   private JDesktopPane desk;
/*  56:    */   private ContextWindow context;
/*  57:    */   private Menubar menubar;
/*  58:    */   private JToolBar toolBar;
/*  59:    */   private JSInternalConsole console;
/*  60:    */   private JSplitPane split1;
/*  61:    */   private JLabel statusBar;
/*  62:155 */   private final Map<String, JFrame> toplevels = Collections.synchronizedMap(new HashMap());
/*  63:161 */   private final Map<String, FileWindow> fileWindows = Collections.synchronizedMap(new HashMap());
/*  64:    */   private FileWindow currentWindow;
/*  65:    */   JFileChooser dlg;
/*  66:    */   private EventQueue awtEventQueue;
/*  67:    */   
/*  68:    */   public SwingGui(Dim dim, String title)
/*  69:    */   {
/*  70:185 */     super(title);
/*  71:186 */     this.dim = dim;
/*  72:187 */     init();
/*  73:188 */     dim.setGuiCallback(this);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Menubar getMenubar()
/*  77:    */   {
/*  78:195 */     return this.menubar;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setExitAction(Runnable r)
/*  82:    */   {
/*  83:203 */     this.exitAction = r;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public JSInternalConsole getConsole()
/*  87:    */   {
/*  88:210 */     return this.console;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setVisible(boolean b)
/*  92:    */   {
/*  93:218 */     super.setVisible(b);
/*  94:219 */     if (b)
/*  95:    */     {
/*  96:221 */       this.console.consoleTextArea.requestFocus();
/*  97:222 */       this.context.split.setDividerLocation(0.5D);
/*  98:    */       try
/*  99:    */       {
/* 100:224 */         this.console.setMaximum(true);
/* 101:225 */         this.console.setSelected(true);
/* 102:226 */         this.console.show();
/* 103:227 */         this.console.consoleTextArea.requestFocus();
/* 104:    */       }
/* 105:    */       catch (Exception exc) {}
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   void addTopLevel(String key, JFrame frame)
/* 110:    */   {
/* 111:237 */     if (frame != this) {
/* 112:238 */       this.toplevels.put(key, frame);
/* 113:    */     }
/* 114:    */   }
/* 115:    */   
/* 116:    */   private void init()
/* 117:    */   {
/* 118:246 */     this.menubar = new Menubar(this);
/* 119:247 */     setJMenuBar(this.menubar);
/* 120:248 */     this.toolBar = new JToolBar();
/* 121:    */     
/* 122:    */ 
/* 123:    */ 
/* 124:252 */     String[] toolTips = { "Break (Pause)", "Go (F5)", "Step Into (F11)", "Step Over (F7)", "Step Out (F8)" };
/* 125:    */     
/* 126:    */ 
/* 127:    */ 
/* 128:    */ 
/* 129:257 */     int count = 0;
/* 130:    */     JButton breakButton;
/* 131:258 */     JButton button = breakButton = new JButton("Break");
/* 132:259 */     button.setToolTipText("Break");
/* 133:260 */     button.setActionCommand("Break");
/* 134:261 */     button.addActionListener(this.menubar);
/* 135:262 */     button.setEnabled(true);
/* 136:263 */     button.setToolTipText(toolTips[(count++)]);
/* 137:    */     JButton goButton;
/* 138:265 */     button = goButton = new JButton("Go");
/* 139:266 */     button.setToolTipText("Go");
/* 140:267 */     button.setActionCommand("Go");
/* 141:268 */     button.addActionListener(this.menubar);
/* 142:269 */     button.setEnabled(false);
/* 143:270 */     button.setToolTipText(toolTips[(count++)]);
/* 144:    */     JButton stepIntoButton;
/* 145:272 */     button = stepIntoButton = new JButton("Step Into");
/* 146:273 */     button.setToolTipText("Step Into");
/* 147:274 */     button.setActionCommand("Step Into");
/* 148:275 */     button.addActionListener(this.menubar);
/* 149:276 */     button.setEnabled(false);
/* 150:277 */     button.setToolTipText(toolTips[(count++)]);
/* 151:    */     JButton stepOverButton;
/* 152:279 */     button = stepOverButton = new JButton("Step Over");
/* 153:280 */     button.setToolTipText("Step Over");
/* 154:281 */     button.setActionCommand("Step Over");
/* 155:282 */     button.setEnabled(false);
/* 156:283 */     button.addActionListener(this.menubar);
/* 157:284 */     button.setToolTipText(toolTips[(count++)]);
/* 158:    */     JButton stepOutButton;
/* 159:286 */     button = stepOutButton = new JButton("Step Out");
/* 160:287 */     button.setToolTipText("Step Out");
/* 161:288 */     button.setActionCommand("Step Out");
/* 162:289 */     button.setEnabled(false);
/* 163:290 */     button.addActionListener(this.menubar);
/* 164:291 */     button.setToolTipText(toolTips[(count++)]);
/* 165:    */     
/* 166:293 */     Dimension dim = stepOverButton.getPreferredSize();
/* 167:294 */     breakButton.setPreferredSize(dim);
/* 168:295 */     breakButton.setMinimumSize(dim);
/* 169:296 */     breakButton.setMaximumSize(dim);
/* 170:297 */     breakButton.setSize(dim);
/* 171:298 */     goButton.setPreferredSize(dim);
/* 172:299 */     goButton.setMinimumSize(dim);
/* 173:300 */     goButton.setMaximumSize(dim);
/* 174:301 */     stepIntoButton.setPreferredSize(dim);
/* 175:302 */     stepIntoButton.setMinimumSize(dim);
/* 176:303 */     stepIntoButton.setMaximumSize(dim);
/* 177:304 */     stepOverButton.setPreferredSize(dim);
/* 178:305 */     stepOverButton.setMinimumSize(dim);
/* 179:306 */     stepOverButton.setMaximumSize(dim);
/* 180:307 */     stepOutButton.setPreferredSize(dim);
/* 181:308 */     stepOutButton.setMinimumSize(dim);
/* 182:309 */     stepOutButton.setMaximumSize(dim);
/* 183:310 */     this.toolBar.add(breakButton);
/* 184:311 */     this.toolBar.add(goButton);
/* 185:312 */     this.toolBar.add(stepIntoButton);
/* 186:313 */     this.toolBar.add(stepOverButton);
/* 187:314 */     this.toolBar.add(stepOutButton);
/* 188:    */     
/* 189:316 */     JPanel contentPane = new JPanel();
/* 190:317 */     contentPane.setLayout(new BorderLayout());
/* 191:318 */     getContentPane().add(this.toolBar, "North");
/* 192:319 */     getContentPane().add(contentPane, "Center");
/* 193:320 */     this.desk = new JDesktopPane();
/* 194:321 */     this.desk.setPreferredSize(new Dimension(600, 300));
/* 195:322 */     this.desk.setMinimumSize(new Dimension(150, 50));
/* 196:323 */     this.desk.add(this.console = new JSInternalConsole("JavaScript Console"));
/* 197:324 */     this.context = new ContextWindow(this);
/* 198:325 */     this.context.setPreferredSize(new Dimension(600, 120));
/* 199:326 */     this.context.setMinimumSize(new Dimension(50, 50));
/* 200:    */     
/* 201:328 */     this.split1 = new JSplitPane(0, this.desk, this.context);
/* 202:    */     
/* 203:330 */     this.split1.setOneTouchExpandable(true);
/* 204:331 */     setResizeWeight(this.split1, 0.66D);
/* 205:332 */     contentPane.add(this.split1, "Center");
/* 206:333 */     this.statusBar = new JLabel();
/* 207:334 */     this.statusBar.setText("Thread: ");
/* 208:335 */     contentPane.add(this.statusBar, "South");
/* 209:336 */     this.dlg = new JFileChooser();
/* 210:    */     
/* 211:338 */     FileFilter filter = new FileFilter()
/* 212:    */     {
/* 213:    */       public boolean accept(File f)
/* 214:    */       {
/* 215:342 */         if (f.isDirectory()) {
/* 216:343 */           return true;
/* 217:    */         }
/* 218:345 */         String n = f.getName();
/* 219:346 */         int i = n.lastIndexOf('.');
/* 220:347 */         if ((i > 0) && (i < n.length() - 1))
/* 221:    */         {
/* 222:348 */           String ext = n.substring(i + 1).toLowerCase();
/* 223:349 */           if (ext.equals("js")) {
/* 224:350 */             return true;
/* 225:    */           }
/* 226:    */         }
/* 227:353 */         return false;
/* 228:    */       }
/* 229:    */       
/* 230:    */       public String getDescription()
/* 231:    */       {
/* 232:358 */         return "JavaScript Files (*.js)";
/* 233:    */       }
/* 234:360 */     };
/* 235:361 */     this.dlg.addChoosableFileFilter(filter);
/* 236:362 */     addWindowListener(new WindowAdapter()
/* 237:    */     {
/* 238:    */       public void windowClosing(WindowEvent e)
/* 239:    */       {
/* 240:365 */         SwingGui.this.exit();
/* 241:    */       }
/* 242:    */     });
/* 243:    */   }
/* 244:    */   
/* 245:    */   private void exit()
/* 246:    */   {
/* 247:374 */     if (this.exitAction != null) {
/* 248:375 */       SwingUtilities.invokeLater(this.exitAction);
/* 249:    */     }
/* 250:377 */     this.dim.setReturnValue(5);
/* 251:    */   }
/* 252:    */   
/* 253:    */   FileWindow getFileWindow(String url)
/* 254:    */   {
/* 255:384 */     if ((url == null) || (url.equals("<stdin>"))) {
/* 256:385 */       return null;
/* 257:    */     }
/* 258:387 */     return (FileWindow)this.fileWindows.get(url);
/* 259:    */   }
/* 260:    */   
/* 261:    */   static String getShortName(String url)
/* 262:    */   {
/* 263:394 */     int lastSlash = url.lastIndexOf('/');
/* 264:395 */     if (lastSlash < 0) {
/* 265:396 */       lastSlash = url.lastIndexOf('\\');
/* 266:    */     }
/* 267:398 */     String shortName = url;
/* 268:399 */     if ((lastSlash >= 0) && (lastSlash + 1 < url.length())) {
/* 269:400 */       shortName = url.substring(lastSlash + 1);
/* 270:    */     }
/* 271:402 */     return shortName;
/* 272:    */   }
/* 273:    */   
/* 274:    */   void removeWindow(FileWindow w)
/* 275:    */   {
/* 276:409 */     this.fileWindows.remove(w.getUrl());
/* 277:410 */     JMenu windowMenu = getWindowMenu();
/* 278:411 */     int count = windowMenu.getItemCount();
/* 279:412 */     JMenuItem lastItem = windowMenu.getItem(count - 1);
/* 280:413 */     String name = getShortName(w.getUrl());
/* 281:414 */     for (int i = 5; i < count; i++)
/* 282:    */     {
/* 283:415 */       JMenuItem item = windowMenu.getItem(i);
/* 284:416 */       if (item != null)
/* 285:    */       {
/* 286:417 */         String text = item.getText();
/* 287:    */         
/* 288:    */ 
/* 289:420 */         int pos = text.indexOf(' ');
/* 290:421 */         if (text.substring(pos + 1).equals(name))
/* 291:    */         {
/* 292:422 */           windowMenu.remove(item);
/* 293:428 */           if (count == 6)
/* 294:    */           {
/* 295:430 */             windowMenu.remove(4); break;
/* 296:    */           }
/* 297:432 */           int j = i - 4;
/* 298:433 */           for (; i < count - 1; i++)
/* 299:    */           {
/* 300:434 */             JMenuItem thisItem = windowMenu.getItem(i);
/* 301:435 */             if (thisItem != null)
/* 302:    */             {
/* 303:438 */               text = thisItem.getText();
/* 304:439 */               if (text.equals("More Windows...")) {
/* 305:    */                 break;
/* 306:    */               }
/* 307:442 */               pos = text.indexOf(' ');
/* 308:443 */               thisItem.setText((char)(48 + j) + " " + text.substring(pos + 1));
/* 309:    */               
/* 310:445 */               thisItem.setMnemonic(48 + j);
/* 311:446 */               j++;
/* 312:    */             }
/* 313:    */           }
/* 314:450 */           if ((count - 6 == 0) && (lastItem != item) && 
/* 315:451 */             (lastItem.getText().equals("More Windows..."))) {
/* 316:452 */             windowMenu.remove(lastItem);
/* 317:    */           }
/* 318:456 */           break;
/* 319:    */         }
/* 320:    */       }
/* 321:    */     }
/* 322:459 */     windowMenu.revalidate();
/* 323:    */   }
/* 324:    */   
/* 325:    */   void showStopLine(Dim.StackFrame frame)
/* 326:    */   {
/* 327:466 */     String sourceName = frame.getUrl();
/* 328:467 */     if ((sourceName == null) || (sourceName.equals("<stdin>")))
/* 329:    */     {
/* 330:468 */       if (this.console.isVisible()) {
/* 331:469 */         this.console.show();
/* 332:    */       }
/* 333:    */     }
/* 334:    */     else
/* 335:    */     {
/* 336:472 */       showFileWindow(sourceName, -1);
/* 337:473 */       int lineNumber = frame.getLineNumber();
/* 338:474 */       FileWindow w = getFileWindow(sourceName);
/* 339:475 */       if (w != null) {
/* 340:476 */         setFilePosition(w, lineNumber);
/* 341:    */       }
/* 342:    */     }
/* 343:    */   }
/* 344:    */   
/* 345:    */   protected void showFileWindow(String sourceUrl, int lineNumber)
/* 346:    */   {
/* 347:489 */     FileWindow w = getFileWindow(sourceUrl);
/* 348:490 */     if (w == null)
/* 349:    */     {
/* 350:491 */       Dim.SourceInfo si = this.dim.sourceInfo(sourceUrl);
/* 351:492 */       createFileWindow(si, -1);
/* 352:493 */       w = getFileWindow(sourceUrl);
/* 353:    */     }
/* 354:495 */     if (lineNumber > -1)
/* 355:    */     {
/* 356:496 */       int start = w.getPosition(lineNumber - 1);
/* 357:497 */       int end = w.getPosition(lineNumber) - 1;
/* 358:498 */       w.textArea.select(start);
/* 359:499 */       w.textArea.setCaretPosition(start);
/* 360:500 */       w.textArea.moveCaretPosition(end);
/* 361:    */     }
/* 362:    */     try
/* 363:    */     {
/* 364:503 */       if (w.isIcon()) {
/* 365:504 */         w.setIcon(false);
/* 366:    */       }
/* 367:506 */       w.setVisible(true);
/* 368:507 */       w.moveToFront();
/* 369:508 */       w.setSelected(true);
/* 370:509 */       requestFocus();
/* 371:510 */       w.requestFocus();
/* 372:511 */       w.textArea.requestFocus();
/* 373:    */     }
/* 374:    */     catch (Exception exc) {}
/* 375:    */   }
/* 376:    */   
/* 377:    */   protected void createFileWindow(Dim.SourceInfo sourceInfo, int line)
/* 378:    */   {
/* 379:520 */     boolean activate = true;
/* 380:    */     
/* 381:522 */     String url = sourceInfo.url();
/* 382:523 */     FileWindow w = new FileWindow(this, sourceInfo);
/* 383:524 */     this.fileWindows.put(url, w);
/* 384:525 */     if (line != -1)
/* 385:    */     {
/* 386:526 */       if (this.currentWindow != null) {
/* 387:527 */         this.currentWindow.setPosition(-1);
/* 388:    */       }
/* 389:    */       try
/* 390:    */       {
/* 391:530 */         w.setPosition(w.textArea.getLineStartOffset(line - 1));
/* 392:    */       }
/* 393:    */       catch (BadLocationException exc)
/* 394:    */       {
/* 395:    */         try
/* 396:    */         {
/* 397:533 */           w.setPosition(w.textArea.getLineStartOffset(0));
/* 398:    */         }
/* 399:    */         catch (BadLocationException ee)
/* 400:    */         {
/* 401:535 */           w.setPosition(-1);
/* 402:    */         }
/* 403:    */       }
/* 404:    */     }
/* 405:539 */     this.desk.add(w);
/* 406:540 */     if (line != -1) {
/* 407:541 */       this.currentWindow = w;
/* 408:    */     }
/* 409:543 */     this.menubar.addFile(url);
/* 410:544 */     w.setVisible(true);
/* 411:546 */     if (activate) {
/* 412:    */       try
/* 413:    */       {
/* 414:548 */         w.setMaximum(true);
/* 415:549 */         w.setSelected(true);
/* 416:550 */         w.moveToFront();
/* 417:    */       }
/* 418:    */       catch (Exception exc) {}
/* 419:    */     }
/* 420:    */   }
/* 421:    */   
/* 422:    */   protected boolean updateFileWindow(Dim.SourceInfo sourceInfo)
/* 423:    */   {
/* 424:565 */     String fileName = sourceInfo.url();
/* 425:566 */     FileWindow w = getFileWindow(fileName);
/* 426:567 */     if (w != null)
/* 427:    */     {
/* 428:568 */       w.updateText(sourceInfo);
/* 429:569 */       w.show();
/* 430:570 */       return true;
/* 431:    */     }
/* 432:572 */     return false;
/* 433:    */   }
/* 434:    */   
/* 435:    */   private void setFilePosition(FileWindow w, int line)
/* 436:    */   {
/* 437:580 */     boolean activate = true;
/* 438:581 */     JTextArea ta = w.textArea;
/* 439:    */     try
/* 440:    */     {
/* 441:583 */       if (line == -1)
/* 442:    */       {
/* 443:584 */         w.setPosition(-1);
/* 444:585 */         if (this.currentWindow == w) {
/* 445:586 */           this.currentWindow = null;
/* 446:    */         }
/* 447:    */       }
/* 448:    */       else
/* 449:    */       {
/* 450:589 */         int loc = ta.getLineStartOffset(line - 1);
/* 451:590 */         if ((this.currentWindow != null) && (this.currentWindow != w)) {
/* 452:591 */           this.currentWindow.setPosition(-1);
/* 453:    */         }
/* 454:593 */         w.setPosition(loc);
/* 455:594 */         this.currentWindow = w;
/* 456:    */       }
/* 457:    */     }
/* 458:    */     catch (BadLocationException exc) {}
/* 459:599 */     if (activate)
/* 460:    */     {
/* 461:600 */       if (w.isIcon()) {
/* 462:601 */         this.desk.getDesktopManager().deiconifyFrame(w);
/* 463:    */       }
/* 464:603 */       this.desk.getDesktopManager().activateFrame(w);
/* 465:    */       try
/* 466:    */       {
/* 467:605 */         w.show();
/* 468:606 */         w.toFront();
/* 469:607 */         w.setSelected(true);
/* 470:    */       }
/* 471:    */       catch (Exception exc) {}
/* 472:    */     }
/* 473:    */   }
/* 474:    */   
/* 475:    */   void enterInterruptImpl(Dim.StackFrame lastFrame, String threadTitle, String alertMessage)
/* 476:    */   {
/* 477:618 */     this.statusBar.setText("Thread: " + threadTitle);
/* 478:    */     
/* 479:620 */     showStopLine(lastFrame);
/* 480:622 */     if (alertMessage != null) {
/* 481:623 */       MessageDialogWrapper.showMessageDialog(this, alertMessage, "Exception in Script", 0);
/* 482:    */     }
/* 483:629 */     updateEnabled(true);
/* 484:    */     
/* 485:631 */     Dim.ContextData contextData = lastFrame.contextData();
/* 486:    */     
/* 487:633 */     JComboBox ctx = this.context.context;
/* 488:634 */     List<String> toolTips = this.context.toolTips;
/* 489:635 */     this.context.disableUpdate();
/* 490:636 */     int frameCount = contextData.frameCount();
/* 491:637 */     ctx.removeAllItems();
/* 492:    */     
/* 493:    */ 
/* 494:640 */     ctx.setSelectedItem(null);
/* 495:641 */     toolTips.clear();
/* 496:642 */     for (int i = 0; i < frameCount; i++)
/* 497:    */     {
/* 498:643 */       Dim.StackFrame frame = contextData.getFrame(i);
/* 499:644 */       String url = frame.getUrl();
/* 500:645 */       int lineNumber = frame.getLineNumber();
/* 501:646 */       String shortName = url;
/* 502:647 */       if (url.length() > 20) {
/* 503:648 */         shortName = "..." + url.substring(url.length() - 17);
/* 504:    */       }
/* 505:650 */       String location = "\"" + shortName + "\", line " + lineNumber;
/* 506:651 */       ctx.insertItemAt(location, i);
/* 507:652 */       location = "\"" + url + "\", line " + lineNumber;
/* 508:653 */       toolTips.add(location);
/* 509:    */     }
/* 510:655 */     this.context.enableUpdate();
/* 511:656 */     ctx.setSelectedIndex(0);
/* 512:657 */     ctx.setMinimumSize(new Dimension(50, ctx.getMinimumSize().height));
/* 513:    */   }
/* 514:    */   
/* 515:    */   private JMenu getWindowMenu()
/* 516:    */   {
/* 517:664 */     return this.menubar.getMenu(3);
/* 518:    */   }
/* 519:    */   
/* 520:    */   private String chooseFile(String title)
/* 521:    */   {
/* 522:671 */     this.dlg.setDialogTitle(title);
/* 523:672 */     File CWD = null;
/* 524:673 */     String dir = SecurityUtilities.getSystemProperty("user.dir");
/* 525:674 */     if (dir != null) {
/* 526:675 */       CWD = new File(dir);
/* 527:    */     }
/* 528:677 */     if (CWD != null) {
/* 529:678 */       this.dlg.setCurrentDirectory(CWD);
/* 530:    */     }
/* 531:680 */     int returnVal = this.dlg.showOpenDialog(this);
/* 532:681 */     if (returnVal == 0) {
/* 533:    */       try
/* 534:    */       {
/* 535:683 */         String result = this.dlg.getSelectedFile().getCanonicalPath();
/* 536:684 */         CWD = this.dlg.getSelectedFile().getParentFile();
/* 537:685 */         Properties props = System.getProperties();
/* 538:686 */         props.put("user.dir", CWD.getPath());
/* 539:687 */         System.setProperties(props);
/* 540:688 */         return result;
/* 541:    */       }
/* 542:    */       catch (IOException ignored) {}catch (SecurityException ignored) {}
/* 543:    */     }
/* 544:693 */     return null;
/* 545:    */   }
/* 546:    */   
/* 547:    */   private JInternalFrame getSelectedFrame()
/* 548:    */   {
/* 549:700 */     JInternalFrame[] frames = this.desk.getAllFrames();
/* 550:701 */     for (int i = 0; i < frames.length; i++) {
/* 551:702 */       if (frames[i].isShowing()) {
/* 552:703 */         return frames[i];
/* 553:    */       }
/* 554:    */     }
/* 555:706 */     return frames[(frames.length - 1)];
/* 556:    */   }
/* 557:    */   
/* 558:    */   private void updateEnabled(boolean interrupted)
/* 559:    */   {
/* 560:714 */     ((Menubar)getJMenuBar()).updateEnabled(interrupted);
/* 561:715 */     int ci = 0;
/* 562:715 */     for (int cc = this.toolBar.getComponentCount(); ci < cc; ci++)
/* 563:    */     {
/* 564:    */       boolean enableButton;
/* 565:    */       boolean enableButton;
/* 566:717 */       if (ci == 0) {
/* 567:719 */         enableButton = !interrupted;
/* 568:    */       } else {
/* 569:721 */         enableButton = interrupted;
/* 570:    */       }
/* 571:723 */       this.toolBar.getComponent(ci).setEnabled(enableButton);
/* 572:    */     }
/* 573:725 */     if (interrupted)
/* 574:    */     {
/* 575:726 */       this.toolBar.setEnabled(true);
/* 576:    */       
/* 577:728 */       int state = getExtendedState();
/* 578:729 */       if (state == 1) {
/* 579:730 */         setExtendedState(0);
/* 580:    */       }
/* 581:732 */       toFront();
/* 582:733 */       this.context.setEnabled(true);
/* 583:    */     }
/* 584:    */     else
/* 585:    */     {
/* 586:735 */       if (this.currentWindow != null) {
/* 587:735 */         this.currentWindow.setPosition(-1);
/* 588:    */       }
/* 589:736 */       this.context.setEnabled(false);
/* 590:    */     }
/* 591:    */   }
/* 592:    */   
/* 593:    */   static void setResizeWeight(JSplitPane pane, double weight)
/* 594:    */   {
/* 595:    */     try
/* 596:    */     {
/* 597:746 */       Method m = JSplitPane.class.getMethod("setResizeWeight", new Class[] { Double.TYPE });
/* 598:    */       
/* 599:748 */       m.invoke(pane, new Object[] { new Double(weight) });
/* 600:    */     }
/* 601:    */     catch (NoSuchMethodException exc) {}catch (IllegalAccessException exc) {}catch (InvocationTargetException exc) {}
/* 602:    */   }
/* 603:    */   
/* 604:    */   private String readFile(String fileName)
/* 605:    */   {
/* 606:    */     String text;
/* 607:    */     try
/* 608:    */     {
/* 609:761 */       Reader r = new FileReader(fileName);
/* 610:    */       try
/* 611:    */       {
/* 612:763 */         text = Kit.readReader(r);
/* 613:    */       }
/* 614:    */       finally
/* 615:    */       {
/* 616:765 */         r.close();
/* 617:    */       }
/* 618:    */     }
/* 619:    */     catch (IOException ex)
/* 620:    */     {
/* 621:768 */       MessageDialogWrapper.showMessageDialog(this, ex.getMessage(), "Error reading " + fileName, 0);
/* 622:    */       
/* 623:    */ 
/* 624:    */ 
/* 625:772 */       text = null;
/* 626:    */     }
/* 627:774 */     return text;
/* 628:    */   }
/* 629:    */   
/* 630:    */   public void updateSourceText(Dim.SourceInfo sourceInfo)
/* 631:    */   {
/* 632:783 */     RunProxy proxy = new RunProxy(this, 3);
/* 633:784 */     proxy.sourceInfo = sourceInfo;
/* 634:785 */     SwingUtilities.invokeLater(proxy);
/* 635:    */   }
/* 636:    */   
/* 637:    */   public void enterInterrupt(Dim.StackFrame lastFrame, String threadTitle, String alertMessage)
/* 638:    */   {
/* 639:794 */     if (SwingUtilities.isEventDispatchThread())
/* 640:    */     {
/* 641:795 */       enterInterruptImpl(lastFrame, threadTitle, alertMessage);
/* 642:    */     }
/* 643:    */     else
/* 644:    */     {
/* 645:797 */       RunProxy proxy = new RunProxy(this, 4);
/* 646:798 */       proxy.lastFrame = lastFrame;
/* 647:799 */       proxy.threadTitle = threadTitle;
/* 648:800 */       proxy.alertMessage = alertMessage;
/* 649:801 */       SwingUtilities.invokeLater(proxy);
/* 650:    */     }
/* 651:    */   }
/* 652:    */   
/* 653:    */   public boolean isGuiEventThread()
/* 654:    */   {
/* 655:809 */     return SwingUtilities.isEventDispatchThread();
/* 656:    */   }
/* 657:    */   
/* 658:    */   public void dispatchNextGuiEvent()
/* 659:    */     throws InterruptedException
/* 660:    */   {
/* 661:816 */     EventQueue queue = this.awtEventQueue;
/* 662:817 */     if (queue == null)
/* 663:    */     {
/* 664:818 */       queue = Toolkit.getDefaultToolkit().getSystemEventQueue();
/* 665:819 */       this.awtEventQueue = queue;
/* 666:    */     }
/* 667:821 */     AWTEvent event = queue.getNextEvent();
/* 668:822 */     if ((event instanceof ActiveEvent))
/* 669:    */     {
/* 670:823 */       ((ActiveEvent)event).dispatch();
/* 671:    */     }
/* 672:    */     else
/* 673:    */     {
/* 674:825 */       Object source = event.getSource();
/* 675:826 */       if ((source instanceof Component))
/* 676:    */       {
/* 677:827 */         Component comp = (Component)source;
/* 678:828 */         comp.dispatchEvent(event);
/* 679:    */       }
/* 680:829 */       else if ((source instanceof MenuComponent))
/* 681:    */       {
/* 682:830 */         ((MenuComponent)source).dispatchEvent(event);
/* 683:    */       }
/* 684:    */     }
/* 685:    */   }
/* 686:    */   
/* 687:    */   public void actionPerformed(ActionEvent e)
/* 688:    */   {
/* 689:841 */     String cmd = e.getActionCommand();
/* 690:842 */     int returnValue = -1;
/* 691:843 */     if ((cmd.equals("Cut")) || (cmd.equals("Copy")) || (cmd.equals("Paste")))
/* 692:    */     {
/* 693:844 */       JInternalFrame f = getSelectedFrame();
/* 694:845 */       if ((f != null) && ((f instanceof ActionListener))) {
/* 695:846 */         ((ActionListener)f).actionPerformed(e);
/* 696:    */       }
/* 697:    */     }
/* 698:848 */     else if (cmd.equals("Step Over"))
/* 699:    */     {
/* 700:849 */       returnValue = 0;
/* 701:    */     }
/* 702:850 */     else if (cmd.equals("Step Into"))
/* 703:    */     {
/* 704:851 */       returnValue = 1;
/* 705:    */     }
/* 706:852 */     else if (cmd.equals("Step Out"))
/* 707:    */     {
/* 708:853 */       returnValue = 2;
/* 709:    */     }
/* 710:854 */     else if (cmd.equals("Go"))
/* 711:    */     {
/* 712:855 */       returnValue = 3;
/* 713:    */     }
/* 714:856 */     else if (cmd.equals("Break"))
/* 715:    */     {
/* 716:857 */       this.dim.setBreak();
/* 717:    */     }
/* 718:858 */     else if (cmd.equals("Exit"))
/* 719:    */     {
/* 720:859 */       exit();
/* 721:    */     }
/* 722:860 */     else if (cmd.equals("Open"))
/* 723:    */     {
/* 724:861 */       String fileName = chooseFile("Select a file to compile");
/* 725:862 */       if (fileName != null)
/* 726:    */       {
/* 727:863 */         String text = readFile(fileName);
/* 728:864 */         if (text != null)
/* 729:    */         {
/* 730:865 */           RunProxy proxy = new RunProxy(this, 1);
/* 731:866 */           proxy.fileName = fileName;
/* 732:867 */           proxy.text = text;
/* 733:868 */           new Thread(proxy).start();
/* 734:    */         }
/* 735:    */       }
/* 736:    */     }
/* 737:871 */     else if (cmd.equals("Load"))
/* 738:    */     {
/* 739:872 */       String fileName = chooseFile("Select a file to execute");
/* 740:873 */       if (fileName != null)
/* 741:    */       {
/* 742:874 */         String text = readFile(fileName);
/* 743:875 */         if (text != null)
/* 744:    */         {
/* 745:876 */           RunProxy proxy = new RunProxy(this, 2);
/* 746:877 */           proxy.fileName = fileName;
/* 747:878 */           proxy.text = text;
/* 748:879 */           new Thread(proxy).start();
/* 749:    */         }
/* 750:    */       }
/* 751:    */     }
/* 752:882 */     else if (cmd.equals("More Windows..."))
/* 753:    */     {
/* 754:883 */       MoreWindows dlg = new MoreWindows(this, this.fileWindows, "Window", "Files");
/* 755:    */       
/* 756:885 */       dlg.showDialog(this);
/* 757:    */     }
/* 758:886 */     else if (cmd.equals("Console"))
/* 759:    */     {
/* 760:887 */       if (this.console.isIcon()) {
/* 761:888 */         this.desk.getDesktopManager().deiconifyFrame(this.console);
/* 762:    */       }
/* 763:890 */       this.console.show();
/* 764:891 */       this.desk.getDesktopManager().activateFrame(this.console);
/* 765:892 */       this.console.consoleTextArea.requestFocus();
/* 766:    */     }
/* 767:893 */     else if ((!cmd.equals("Cut")) && 
/* 768:894 */       (!cmd.equals("Copy")) && 
/* 769:895 */       (!cmd.equals("Paste")))
/* 770:    */     {
/* 771:896 */       if (cmd.equals("Go to function..."))
/* 772:    */       {
/* 773:897 */         FindFunction dlg = new FindFunction(this, "Go to function", "Function");
/* 774:    */         
/* 775:899 */         dlg.showDialog(this);
/* 776:    */       }
/* 777:900 */       else if (cmd.equals("Tile"))
/* 778:    */       {
/* 779:901 */         JInternalFrame[] frames = this.desk.getAllFrames();
/* 780:902 */         int count = frames.length;
/* 781:    */         int cols;
/* 782:904 */         int rows = cols = (int)Math.sqrt(count);
/* 783:905 */         if (rows * cols < count)
/* 784:    */         {
/* 785:906 */           cols++;
/* 786:907 */           if (rows * cols < count) {
/* 787:908 */             rows++;
/* 788:    */           }
/* 789:    */         }
/* 790:911 */         Dimension size = this.desk.getSize();
/* 791:912 */         int w = size.width / cols;
/* 792:913 */         int h = size.height / rows;
/* 793:914 */         int x = 0;
/* 794:915 */         int y = 0;
/* 795:916 */         for (int i = 0; i < rows; i++)
/* 796:    */         {
/* 797:917 */           for (int j = 0; j < cols; j++)
/* 798:    */           {
/* 799:918 */             int index = i * cols + j;
/* 800:919 */             if (index >= frames.length) {
/* 801:    */               break;
/* 802:    */             }
/* 803:922 */             JInternalFrame f = frames[index];
/* 804:    */             try
/* 805:    */             {
/* 806:924 */               f.setIcon(false);
/* 807:925 */               f.setMaximum(false);
/* 808:    */             }
/* 809:    */             catch (Exception exc) {}
/* 810:928 */             this.desk.getDesktopManager().setBoundsForFrame(f, x, y, w, h);
/* 811:    */             
/* 812:930 */             x += w;
/* 813:    */           }
/* 814:932 */           y += h;
/* 815:933 */           x = 0;
/* 816:    */         }
/* 817:    */       }
/* 818:935 */       else if (cmd.equals("Cascade"))
/* 819:    */       {
/* 820:936 */         JInternalFrame[] frames = this.desk.getAllFrames();
/* 821:937 */         int count = frames.length;
/* 822:    */         int y;
/* 823:939 */         int x = y = 0;
/* 824:940 */         int h = this.desk.getHeight();
/* 825:941 */         int d = h / count;
/* 826:942 */         if (d > 30) {
/* 827:942 */           d = 30;
/* 828:    */         }
/* 829:943 */         for (int i = count - 1; i >= 0; y += d)
/* 830:    */         {
/* 831:944 */           JInternalFrame f = frames[i];
/* 832:    */           try
/* 833:    */           {
/* 834:946 */             f.setIcon(false);
/* 835:947 */             f.setMaximum(false);
/* 836:    */           }
/* 837:    */           catch (Exception exc) {}
/* 838:950 */           Dimension dimen = f.getPreferredSize();
/* 839:951 */           int w = dimen.width;
/* 840:952 */           h = dimen.height;
/* 841:953 */           this.desk.getDesktopManager().setBoundsForFrame(f, x, y, w, h);i--;x += d;
/* 842:    */         }
/* 843:    */       }
/* 844:    */       else
/* 845:    */       {
/* 846:956 */         Object obj = getFileWindow(cmd);
/* 847:957 */         if (obj != null)
/* 848:    */         {
/* 849:958 */           FileWindow w = (FileWindow)obj;
/* 850:    */           try
/* 851:    */           {
/* 852:960 */             if (w.isIcon()) {
/* 853:961 */               w.setIcon(false);
/* 854:    */             }
/* 855:963 */             w.setVisible(true);
/* 856:964 */             w.moveToFront();
/* 857:965 */             w.setSelected(true);
/* 858:    */           }
/* 859:    */           catch (Exception exc) {}
/* 860:    */         }
/* 861:    */       }
/* 862:    */     }
/* 863:970 */     if (returnValue != -1)
/* 864:    */     {
/* 865:971 */       updateEnabled(false);
/* 866:972 */       this.dim.setReturnValue(returnValue);
/* 867:    */     }
/* 868:    */   }
/* 869:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.debugger.SwingGui
 * JD-Core Version:    0.7.0.1
 */