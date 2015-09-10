/*  1:   */ package net.sourceforge.htmlunit.corejs.javascript.tools.debugger.treetable;
/*  2:   */ 
/*  3:   */ import java.util.EventObject;
/*  4:   */ import javax.swing.CellEditor;
/*  5:   */ import javax.swing.event.CellEditorListener;
/*  6:   */ import javax.swing.event.ChangeEvent;
/*  7:   */ import javax.swing.event.EventListenerList;
/*  8:   */ 
/*  9:   */ public class AbstractCellEditor
/* 10:   */   implements CellEditor
/* 11:   */ {
/* 12:40 */   protected EventListenerList listenerList = new EventListenerList();
/* 13:   */   
/* 14:   */   public Object getCellEditorValue()
/* 15:   */   {
/* 16:42 */     return null;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean isCellEditable(EventObject e)
/* 20:   */   {
/* 21:43 */     return true;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public boolean shouldSelectCell(EventObject anEvent)
/* 25:   */   {
/* 26:44 */     return false;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public boolean stopCellEditing()
/* 30:   */   {
/* 31:45 */     return true;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void cancelCellEditing() {}
/* 35:   */   
/* 36:   */   public void addCellEditorListener(CellEditorListener l)
/* 37:   */   {
/* 38:49 */     this.listenerList.add(CellEditorListener.class, l);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void removeCellEditorListener(CellEditorListener l)
/* 42:   */   {
/* 43:53 */     this.listenerList.remove(CellEditorListener.class, l);
/* 44:   */   }
/* 45:   */   
/* 46:   */   protected void fireEditingStopped()
/* 47:   */   {
/* 48:63 */     Object[] listeners = this.listenerList.getListenerList();
/* 49:66 */     for (int i = listeners.length - 2; i >= 0; i -= 2) {
/* 50:67 */       if (listeners[i] == CellEditorListener.class) {
/* 51:68 */         ((CellEditorListener)listeners[(i + 1)]).editingStopped(new ChangeEvent(this));
/* 52:   */       }
/* 53:   */     }
/* 54:   */   }
/* 55:   */   
/* 56:   */   protected void fireEditingCanceled()
/* 57:   */   {
/* 58:80 */     Object[] listeners = this.listenerList.getListenerList();
/* 59:83 */     for (int i = listeners.length - 2; i >= 0; i -= 2) {
/* 60:84 */       if (listeners[i] == CellEditorListener.class) {
/* 61:85 */         ((CellEditorListener)listeners[(i + 1)]).editingCanceled(new ChangeEvent(this));
/* 62:   */       }
/* 63:   */     }
/* 64:   */   }
/* 65:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.debugger.treetable.AbstractCellEditor
 * JD-Core Version:    0.7.0.1
 */