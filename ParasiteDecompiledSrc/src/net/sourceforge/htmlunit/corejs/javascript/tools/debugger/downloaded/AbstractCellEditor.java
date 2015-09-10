/*  1:   */ package net.sourceforge.htmlunit.corejs.javascript.tools.debugger.downloaded;
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
/* 12:14 */   protected EventListenerList listenerList = new EventListenerList();
/* 13:   */   
/* 14:   */   public Object getCellEditorValue()
/* 15:   */   {
/* 16:16 */     return null;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean isCellEditable(EventObject e)
/* 20:   */   {
/* 21:17 */     return true;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public boolean shouldSelectCell(EventObject anEvent)
/* 25:   */   {
/* 26:18 */     return false;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public boolean stopCellEditing()
/* 30:   */   {
/* 31:19 */     return true;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void cancelCellEditing() {}
/* 35:   */   
/* 36:   */   public void addCellEditorListener(CellEditorListener l)
/* 37:   */   {
/* 38:23 */     this.listenerList.add(CellEditorListener.class, l);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void removeCellEditorListener(CellEditorListener l)
/* 42:   */   {
/* 43:27 */     this.listenerList.remove(CellEditorListener.class, l);
/* 44:   */   }
/* 45:   */   
/* 46:   */   protected void fireEditingStopped()
/* 47:   */   {
/* 48:37 */     Object[] listeners = this.listenerList.getListenerList();
/* 49:40 */     for (int i = listeners.length - 2; i >= 0; i -= 2) {
/* 50:41 */       if (listeners[i] == CellEditorListener.class) {
/* 51:42 */         ((CellEditorListener)listeners[(i + 1)]).editingStopped(new ChangeEvent(this));
/* 52:   */       }
/* 53:   */     }
/* 54:   */   }
/* 55:   */   
/* 56:   */   protected void fireEditingCanceled()
/* 57:   */   {
/* 58:54 */     Object[] listeners = this.listenerList.getListenerList();
/* 59:57 */     for (int i = listeners.length - 2; i >= 0; i -= 2) {
/* 60:58 */       if (listeners[i] == CellEditorListener.class) {
/* 61:59 */         ((CellEditorListener)listeners[(i + 1)]).editingCanceled(new ChangeEvent(this));
/* 62:   */       }
/* 63:   */     }
/* 64:   */   }
/* 65:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.debugger.downloaded.AbstractCellEditor
 * JD-Core Version:    0.7.0.1
 */