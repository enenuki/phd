/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.tools.debugger;
/*    2:     */ 
/*    3:     */ import java.awt.Font;
/*    4:     */ import java.awt.Point;
/*    5:     */ import java.awt.Rectangle;
/*    6:     */ import java.awt.event.ActionEvent;
/*    7:     */ import java.awt.event.ActionListener;
/*    8:     */ import java.awt.event.KeyEvent;
/*    9:     */ import java.awt.event.KeyListener;
/*   10:     */ import java.awt.event.MouseEvent;
/*   11:     */ import java.awt.event.MouseListener;
/*   12:     */ import javax.swing.JTextArea;
/*   13:     */ import javax.swing.JViewport;
/*   14:     */ import javax.swing.event.PopupMenuEvent;
/*   15:     */ import javax.swing.event.PopupMenuListener;
/*   16:     */ import javax.swing.text.BadLocationException;
/*   17:     */ import javax.swing.text.Caret;
/*   18:     */ 
/*   19:     */ class FileTextArea
/*   20:     */   extends JTextArea
/*   21:     */   implements ActionListener, PopupMenuListener, KeyListener, MouseListener
/*   22:     */ {
/*   23:     */   private static final long serialVersionUID = -25032065448563720L;
/*   24:     */   private FileWindow w;
/*   25:     */   private FilePopupMenu popup;
/*   26:     */   
/*   27:     */   public FileTextArea(FileWindow w)
/*   28:     */   {
/*   29:1447 */     this.w = w;
/*   30:1448 */     this.popup = new FilePopupMenu(this);
/*   31:1449 */     this.popup.addPopupMenuListener(this);
/*   32:1450 */     addMouseListener(this);
/*   33:1451 */     addKeyListener(this);
/*   34:1452 */     setFont(new Font("Monospaced", 0, 12));
/*   35:     */   }
/*   36:     */   
/*   37:     */   public void select(int pos)
/*   38:     */   {
/*   39:1459 */     if (pos >= 0) {
/*   40:     */       try
/*   41:     */       {
/*   42:1461 */         int line = getLineOfOffset(pos);
/*   43:1462 */         Rectangle rect = modelToView(pos);
/*   44:1463 */         if (rect == null)
/*   45:     */         {
/*   46:1464 */           select(pos, pos);
/*   47:     */         }
/*   48:     */         else
/*   49:     */         {
/*   50:     */           try
/*   51:     */           {
/*   52:1467 */             Rectangle nrect = modelToView(getLineStartOffset(line + 1));
/*   53:1469 */             if (nrect != null) {
/*   54:1470 */               rect = nrect;
/*   55:     */             }
/*   56:     */           }
/*   57:     */           catch (Exception exc) {}
/*   58:1474 */           JViewport vp = (JViewport)getParent();
/*   59:1475 */           Rectangle viewRect = vp.getViewRect();
/*   60:1476 */           if (viewRect.y + viewRect.height > rect.y)
/*   61:     */           {
/*   62:1478 */             select(pos, pos);
/*   63:     */           }
/*   64:     */           else
/*   65:     */           {
/*   66:1481 */             rect.y += (viewRect.height - rect.height) / 2;
/*   67:1482 */             scrollRectToVisible(rect);
/*   68:1483 */             select(pos, pos);
/*   69:     */           }
/*   70:     */         }
/*   71:     */       }
/*   72:     */       catch (BadLocationException exc)
/*   73:     */       {
/*   74:1487 */         select(pos, pos);
/*   75:     */       }
/*   76:     */     }
/*   77:     */   }
/*   78:     */   
/*   79:     */   private void checkPopup(MouseEvent e)
/*   80:     */   {
/*   81:1497 */     if (e.isPopupTrigger()) {
/*   82:1498 */       this.popup.show(this, e.getX(), e.getY());
/*   83:     */     }
/*   84:     */   }
/*   85:     */   
/*   86:     */   public void mousePressed(MouseEvent e)
/*   87:     */   {
/*   88:1508 */     checkPopup(e);
/*   89:     */   }
/*   90:     */   
/*   91:     */   public void mouseClicked(MouseEvent e)
/*   92:     */   {
/*   93:1515 */     checkPopup(e);
/*   94:1516 */     requestFocus();
/*   95:1517 */     getCaret().setVisible(true);
/*   96:     */   }
/*   97:     */   
/*   98:     */   public void mouseEntered(MouseEvent e) {}
/*   99:     */   
/*  100:     */   public void mouseExited(MouseEvent e) {}
/*  101:     */   
/*  102:     */   public void mouseReleased(MouseEvent e)
/*  103:     */   {
/*  104:1536 */     checkPopup(e);
/*  105:     */   }
/*  106:     */   
/*  107:     */   public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}
/*  108:     */   
/*  109:     */   public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}
/*  110:     */   
/*  111:     */   public void popupMenuCanceled(PopupMenuEvent e) {}
/*  112:     */   
/*  113:     */   public void actionPerformed(ActionEvent e)
/*  114:     */   {
/*  115:1565 */     int pos = viewToModel(new Point(this.popup.x, this.popup.y));
/*  116:1566 */     this.popup.setVisible(false);
/*  117:1567 */     String cmd = e.getActionCommand();
/*  118:1568 */     int line = -1;
/*  119:     */     try
/*  120:     */     {
/*  121:1570 */       line = getLineOfOffset(pos);
/*  122:     */     }
/*  123:     */     catch (Exception exc) {}
/*  124:1573 */     if (cmd.equals("Set Breakpoint")) {
/*  125:1574 */       this.w.setBreakPoint(line + 1);
/*  126:1575 */     } else if (cmd.equals("Clear Breakpoint")) {
/*  127:1576 */       this.w.clearBreakPoint(line + 1);
/*  128:1577 */     } else if (cmd.equals("Run")) {
/*  129:1578 */       this.w.load();
/*  130:     */     }
/*  131:     */   }
/*  132:     */   
/*  133:     */   public void keyPressed(KeyEvent e)
/*  134:     */   {
/*  135:1588 */     switch (e.getKeyCode())
/*  136:     */     {
/*  137:     */     case 8: 
/*  138:     */     case 9: 
/*  139:     */     case 10: 
/*  140:     */     case 127: 
/*  141:1593 */       e.consume();
/*  142:     */     }
/*  143:     */   }
/*  144:     */   
/*  145:     */   public void keyTyped(KeyEvent e)
/*  146:     */   {
/*  147:1602 */     e.consume();
/*  148:     */   }
/*  149:     */   
/*  150:     */   public void keyReleased(KeyEvent e)
/*  151:     */   {
/*  152:1609 */     e.consume();
/*  153:     */   }
/*  154:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.debugger.FileTextArea
 * JD-Core Version:    0.7.0.1
 */