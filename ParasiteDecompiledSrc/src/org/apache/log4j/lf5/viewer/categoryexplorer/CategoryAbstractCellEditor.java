/*   1:    */ package org.apache.log4j.lf5.viewer.categoryexplorer;
/*   2:    */ 
/*   3:    */ import java.awt.Component;
/*   4:    */ import java.awt.event.MouseEvent;
/*   5:    */ import java.util.EventObject;
/*   6:    */ import javax.swing.JTable;
/*   7:    */ import javax.swing.JTree;
/*   8:    */ import javax.swing.event.CellEditorListener;
/*   9:    */ import javax.swing.event.ChangeEvent;
/*  10:    */ import javax.swing.event.EventListenerList;
/*  11:    */ import javax.swing.table.TableCellEditor;
/*  12:    */ import javax.swing.tree.TreeCellEditor;
/*  13:    */ 
/*  14:    */ public class CategoryAbstractCellEditor
/*  15:    */   implements TableCellEditor, TreeCellEditor
/*  16:    */ {
/*  17: 49 */   protected EventListenerList _listenerList = new EventListenerList();
/*  18:    */   protected Object _value;
/*  19: 51 */   protected ChangeEvent _changeEvent = null;
/*  20: 52 */   protected int _clickCountToStart = 1;
/*  21:    */   
/*  22:    */   public Object getCellEditorValue()
/*  23:    */   {
/*  24: 67 */     return this._value;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void setCellEditorValue(Object value)
/*  28:    */   {
/*  29: 71 */     this._value = value;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void setClickCountToStart(int count)
/*  33:    */   {
/*  34: 75 */     this._clickCountToStart = count;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public int getClickCountToStart()
/*  38:    */   {
/*  39: 79 */     return this._clickCountToStart;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean isCellEditable(EventObject anEvent)
/*  43:    */   {
/*  44: 83 */     if (((anEvent instanceof MouseEvent)) && 
/*  45: 84 */       (((MouseEvent)anEvent).getClickCount() < this._clickCountToStart)) {
/*  46: 85 */       return false;
/*  47:    */     }
/*  48: 88 */     return true;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean shouldSelectCell(EventObject anEvent)
/*  52:    */   {
/*  53: 92 */     if ((isCellEditable(anEvent)) && (
/*  54: 93 */       (anEvent == null) || (((MouseEvent)anEvent).getClickCount() >= this._clickCountToStart))) {
/*  55: 95 */       return true;
/*  56:    */     }
/*  57: 98 */     return false;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean stopCellEditing()
/*  61:    */   {
/*  62:102 */     fireEditingStopped();
/*  63:103 */     return true;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void cancelCellEditing()
/*  67:    */   {
/*  68:107 */     fireEditingCanceled();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void addCellEditorListener(CellEditorListener l)
/*  72:    */   {
/*  73:111 */     this._listenerList.add(CellEditorListener.class, l);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void removeCellEditorListener(CellEditorListener l)
/*  77:    */   {
/*  78:115 */     this._listenerList.remove(CellEditorListener.class, l);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row)
/*  82:    */   {
/*  83:123 */     return null;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
/*  87:    */   {
/*  88:130 */     return null;
/*  89:    */   }
/*  90:    */   
/*  91:    */   protected void fireEditingStopped()
/*  92:    */   {
/*  93:137 */     Object[] listeners = this._listenerList.getListenerList();
/*  94:139 */     for (int i = listeners.length - 2; i >= 0; i -= 2) {
/*  95:140 */       if (listeners[i] == CellEditorListener.class)
/*  96:    */       {
/*  97:141 */         if (this._changeEvent == null) {
/*  98:142 */           this._changeEvent = new ChangeEvent(this);
/*  99:    */         }
/* 100:145 */         ((CellEditorListener)listeners[(i + 1)]).editingStopped(this._changeEvent);
/* 101:    */       }
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   protected void fireEditingCanceled()
/* 106:    */   {
/* 107:151 */     Object[] listeners = this._listenerList.getListenerList();
/* 108:153 */     for (int i = listeners.length - 2; i >= 0; i -= 2) {
/* 109:154 */       if (listeners[i] == CellEditorListener.class)
/* 110:    */       {
/* 111:155 */         if (this._changeEvent == null) {
/* 112:156 */           this._changeEvent = new ChangeEvent(this);
/* 113:    */         }
/* 114:159 */         ((CellEditorListener)listeners[(i + 1)]).editingCanceled(this._changeEvent);
/* 115:    */       }
/* 116:    */     }
/* 117:    */   }
/* 118:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.categoryexplorer.CategoryAbstractCellEditor
 * JD-Core Version:    0.7.0.1
 */