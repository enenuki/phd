/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.tools.debugger;
/*    2:     */ 
/*    3:     */ import java.awt.event.ActionEvent;
/*    4:     */ import java.awt.event.ActionListener;
/*    5:     */ import java.util.ArrayList;
/*    6:     */ import java.util.Collections;
/*    7:     */ import java.util.List;
/*    8:     */ import javax.swing.JCheckBoxMenuItem;
/*    9:     */ import javax.swing.JMenu;
/*   10:     */ import javax.swing.JMenuBar;
/*   11:     */ import javax.swing.JMenuItem;
/*   12:     */ import javax.swing.KeyStroke;
/*   13:     */ import javax.swing.SwingUtilities;
/*   14:     */ import javax.swing.UIManager;
/*   15:     */ 
/*   16:     */ class Menubar
/*   17:     */   extends JMenuBar
/*   18:     */   implements ActionListener
/*   19:     */ {
/*   20:     */   private static final long serialVersionUID = 3217170497245911461L;
/*   21:3212 */   private List<JMenuItem> interruptOnlyItems = Collections.synchronizedList(new ArrayList());
/*   22:3218 */   private List<JMenuItem> runOnlyItems = Collections.synchronizedList(new ArrayList());
/*   23:     */   private SwingGui debugGui;
/*   24:     */   private JMenu windowMenu;
/*   25:     */   private JCheckBoxMenuItem breakOnExceptions;
/*   26:     */   private JCheckBoxMenuItem breakOnEnter;
/*   27:     */   private JCheckBoxMenuItem breakOnReturn;
/*   28:     */   
/*   29:     */   Menubar(SwingGui debugGui)
/*   30:     */   {
/*   31:3251 */     this.debugGui = debugGui;
/*   32:3252 */     String[] fileItems = { "Open...", "Run...", "", "Exit" };
/*   33:3253 */     String[] fileCmds = { "Open", "Load", "", "Exit" };
/*   34:3254 */     char[] fileShortCuts = { '0', 'N', '\000', 'X' };
/*   35:3255 */     int[] fileAccelerators = { 79, 78, 0, 81 };
/*   36:     */     
/*   37:     */ 
/*   38:     */ 
/*   39:3259 */     String[] editItems = { "Cut", "Copy", "Paste", "Go to function..." };
/*   40:3260 */     char[] editShortCuts = { 'T', 'C', 'P', 'F' };
/*   41:3261 */     String[] debugItems = { "Break", "Go", "Step Into", "Step Over", "Step Out" };
/*   42:3262 */     char[] debugShortCuts = { 'B', 'G', 'I', 'O', 'T' };
/*   43:3263 */     String[] plafItems = { "Metal", "Windows", "Motif" };
/*   44:3264 */     char[] plafShortCuts = { 'M', 'W', 'F' };
/*   45:3265 */     int[] debugAccelerators = { 19, 116, 122, 118, 119, 0, 0 };
/*   46:     */     
/*   47:     */ 
/*   48:     */ 
/*   49:     */ 
/*   50:     */ 
/*   51:     */ 
/*   52:3272 */     JMenu fileMenu = new JMenu("File");
/*   53:3273 */     fileMenu.setMnemonic('F');
/*   54:3274 */     JMenu editMenu = new JMenu("Edit");
/*   55:3275 */     editMenu.setMnemonic('E');
/*   56:3276 */     JMenu plafMenu = new JMenu("Platform");
/*   57:3277 */     plafMenu.setMnemonic('P');
/*   58:3278 */     JMenu debugMenu = new JMenu("Debug");
/*   59:3279 */     debugMenu.setMnemonic('D');
/*   60:3280 */     this.windowMenu = new JMenu("Window");
/*   61:3281 */     this.windowMenu.setMnemonic('W');
/*   62:3282 */     for (int i = 0; i < fileItems.length; i++) {
/*   63:3283 */       if (fileItems[i].length() == 0)
/*   64:     */       {
/*   65:3284 */         fileMenu.addSeparator();
/*   66:     */       }
/*   67:     */       else
/*   68:     */       {
/*   69:3286 */         JMenuItem item = new JMenuItem(fileItems[i], fileShortCuts[i]);
/*   70:     */         
/*   71:3288 */         item.setActionCommand(fileCmds[i]);
/*   72:3289 */         item.addActionListener(this);
/*   73:3290 */         fileMenu.add(item);
/*   74:3291 */         if (fileAccelerators[i] != 0)
/*   75:     */         {
/*   76:3292 */           KeyStroke k = KeyStroke.getKeyStroke(fileAccelerators[i], 2);
/*   77:3293 */           item.setAccelerator(k);
/*   78:     */         }
/*   79:     */       }
/*   80:     */     }
/*   81:3297 */     for (int i = 0; i < editItems.length; i++)
/*   82:     */     {
/*   83:3298 */       JMenuItem item = new JMenuItem(editItems[i], editShortCuts[i]);
/*   84:     */       
/*   85:3300 */       item.addActionListener(this);
/*   86:3301 */       editMenu.add(item);
/*   87:     */     }
/*   88:3303 */     for (int i = 0; i < plafItems.length; i++)
/*   89:     */     {
/*   90:3304 */       JMenuItem item = new JMenuItem(plafItems[i], plafShortCuts[i]);
/*   91:     */       
/*   92:3306 */       item.addActionListener(this);
/*   93:3307 */       plafMenu.add(item);
/*   94:     */     }
/*   95:3309 */     for (int i = 0; i < debugItems.length; i++)
/*   96:     */     {
/*   97:3310 */       JMenuItem item = new JMenuItem(debugItems[i], debugShortCuts[i]);
/*   98:     */       
/*   99:3312 */       item.addActionListener(this);
/*  100:3313 */       if (debugAccelerators[i] != 0)
/*  101:     */       {
/*  102:3314 */         KeyStroke k = KeyStroke.getKeyStroke(debugAccelerators[i], 0);
/*  103:3315 */         item.setAccelerator(k);
/*  104:     */       }
/*  105:3317 */       if (i != 0) {
/*  106:3318 */         this.interruptOnlyItems.add(item);
/*  107:     */       } else {
/*  108:3320 */         this.runOnlyItems.add(item);
/*  109:     */       }
/*  110:3322 */       debugMenu.add(item);
/*  111:     */     }
/*  112:3324 */     this.breakOnExceptions = new JCheckBoxMenuItem("Break on Exceptions");
/*  113:3325 */     this.breakOnExceptions.setMnemonic('X');
/*  114:3326 */     this.breakOnExceptions.addActionListener(this);
/*  115:3327 */     this.breakOnExceptions.setSelected(false);
/*  116:3328 */     debugMenu.add(this.breakOnExceptions);
/*  117:     */     
/*  118:3330 */     this.breakOnEnter = new JCheckBoxMenuItem("Break on Function Enter");
/*  119:3331 */     this.breakOnEnter.setMnemonic('E');
/*  120:3332 */     this.breakOnEnter.addActionListener(this);
/*  121:3333 */     this.breakOnEnter.setSelected(false);
/*  122:3334 */     debugMenu.add(this.breakOnEnter);
/*  123:     */     
/*  124:3336 */     this.breakOnReturn = new JCheckBoxMenuItem("Break on Function Return");
/*  125:3337 */     this.breakOnReturn.setMnemonic('R');
/*  126:3338 */     this.breakOnReturn.addActionListener(this);
/*  127:3339 */     this.breakOnReturn.setSelected(false);
/*  128:3340 */     debugMenu.add(this.breakOnReturn);
/*  129:     */     
/*  130:3342 */     add(fileMenu);
/*  131:3343 */     add(editMenu);
/*  132:     */     
/*  133:3345 */     add(debugMenu);
/*  134:     */     JMenuItem item;
/*  135:3347 */     this.windowMenu.add(item = new JMenuItem("Cascade", 65));
/*  136:3348 */     item.addActionListener(this);
/*  137:3349 */     this.windowMenu.add(item = new JMenuItem("Tile", 84));
/*  138:3350 */     item.addActionListener(this);
/*  139:3351 */     this.windowMenu.addSeparator();
/*  140:3352 */     this.windowMenu.add(item = new JMenuItem("Console", 67));
/*  141:3353 */     item.addActionListener(this);
/*  142:3354 */     add(this.windowMenu);
/*  143:     */     
/*  144:3356 */     updateEnabled(false);
/*  145:     */   }
/*  146:     */   
/*  147:     */   public JCheckBoxMenuItem getBreakOnExceptions()
/*  148:     */   {
/*  149:3363 */     return this.breakOnExceptions;
/*  150:     */   }
/*  151:     */   
/*  152:     */   public JCheckBoxMenuItem getBreakOnEnter()
/*  153:     */   {
/*  154:3370 */     return this.breakOnEnter;
/*  155:     */   }
/*  156:     */   
/*  157:     */   public JCheckBoxMenuItem getBreakOnReturn()
/*  158:     */   {
/*  159:3377 */     return this.breakOnReturn;
/*  160:     */   }
/*  161:     */   
/*  162:     */   public JMenu getDebugMenu()
/*  163:     */   {
/*  164:3384 */     return getMenu(2);
/*  165:     */   }
/*  166:     */   
/*  167:     */   public void actionPerformed(ActionEvent e)
/*  168:     */   {
/*  169:3393 */     String cmd = e.getActionCommand();
/*  170:3394 */     String plaf_name = null;
/*  171:3395 */     if (cmd.equals("Metal"))
/*  172:     */     {
/*  173:3396 */       plaf_name = "javax.swing.plaf.metal.MetalLookAndFeel";
/*  174:     */     }
/*  175:3397 */     else if (cmd.equals("Windows"))
/*  176:     */     {
/*  177:3398 */       plaf_name = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
/*  178:     */     }
/*  179:3399 */     else if (cmd.equals("Motif"))
/*  180:     */     {
/*  181:3400 */       plaf_name = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
/*  182:     */     }
/*  183:     */     else
/*  184:     */     {
/*  185:3402 */       Object source = e.getSource();
/*  186:3403 */       if (source == this.breakOnExceptions) {
/*  187:3404 */         this.debugGui.dim.setBreakOnExceptions(this.breakOnExceptions.isSelected());
/*  188:3405 */       } else if (source == this.breakOnEnter) {
/*  189:3406 */         this.debugGui.dim.setBreakOnEnter(this.breakOnEnter.isSelected());
/*  190:3407 */       } else if (source == this.breakOnReturn) {
/*  191:3408 */         this.debugGui.dim.setBreakOnReturn(this.breakOnReturn.isSelected());
/*  192:     */       } else {
/*  193:3410 */         this.debugGui.actionPerformed(e);
/*  194:     */       }
/*  195:3412 */       return;
/*  196:     */     }
/*  197:     */     try
/*  198:     */     {
/*  199:3415 */       UIManager.setLookAndFeel(plaf_name);
/*  200:3416 */       SwingUtilities.updateComponentTreeUI(this.debugGui);
/*  201:3417 */       SwingUtilities.updateComponentTreeUI(this.debugGui.dlg);
/*  202:     */     }
/*  203:     */     catch (Exception ignored) {}
/*  204:     */   }
/*  205:     */   
/*  206:     */   public void addFile(String url)
/*  207:     */   {
/*  208:3427 */     int count = this.windowMenu.getItemCount();
/*  209:3429 */     if (count == 4)
/*  210:     */     {
/*  211:3430 */       this.windowMenu.addSeparator();
/*  212:3431 */       count++;
/*  213:     */     }
/*  214:3433 */     JMenuItem lastItem = this.windowMenu.getItem(count - 1);
/*  215:3434 */     boolean hasMoreWin = false;
/*  216:3435 */     int maxWin = 5;
/*  217:3436 */     if ((lastItem != null) && (lastItem.getText().equals("More Windows...")))
/*  218:     */     {
/*  219:3438 */       hasMoreWin = true;
/*  220:3439 */       maxWin++;
/*  221:     */     }
/*  222:3441 */     if ((!hasMoreWin) && (count - 4 == 5))
/*  223:     */     {
/*  224:     */       JMenuItem item;
/*  225:3442 */       this.windowMenu.add(item = new JMenuItem("More Windows...", 77));
/*  226:3443 */       item.setActionCommand("More Windows...");
/*  227:3444 */       item.addActionListener(this);
/*  228:3445 */       return;
/*  229:     */     }
/*  230:3446 */     if (count - 4 <= maxWin)
/*  231:     */     {
/*  232:3447 */       if (hasMoreWin)
/*  233:     */       {
/*  234:3448 */         count--;
/*  235:3449 */         this.windowMenu.remove(lastItem);
/*  236:     */       }
/*  237:3451 */       String shortName = SwingGui.getShortName(url);
/*  238:     */       JMenuItem item;
/*  239:3453 */       this.windowMenu.add(item = new JMenuItem((char)(48 + (count - 4)) + " " + shortName, 48 + (count - 4)));
/*  240:3454 */       if (hasMoreWin) {
/*  241:3455 */         this.windowMenu.add(lastItem);
/*  242:     */       }
/*  243:     */     }
/*  244:     */     else
/*  245:     */     {
/*  246:     */       return;
/*  247:     */     }
/*  248:     */     JMenuItem item;
/*  249:3460 */     item.setActionCommand(url);
/*  250:3461 */     item.addActionListener(this);
/*  251:     */   }
/*  252:     */   
/*  253:     */   public void updateEnabled(boolean interrupted)
/*  254:     */   {
/*  255:3468 */     for (int i = 0; i != this.interruptOnlyItems.size(); i++)
/*  256:     */     {
/*  257:3469 */       JMenuItem item = (JMenuItem)this.interruptOnlyItems.get(i);
/*  258:3470 */       item.setEnabled(interrupted);
/*  259:     */     }
/*  260:3473 */     for (int i = 0; i != this.runOnlyItems.size(); i++)
/*  261:     */     {
/*  262:3474 */       JMenuItem item = (JMenuItem)this.runOnlyItems.get(i);
/*  263:3475 */       item.setEnabled(!interrupted);
/*  264:     */     }
/*  265:     */   }
/*  266:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.debugger.Menubar
 * JD-Core Version:    0.7.0.1
 */