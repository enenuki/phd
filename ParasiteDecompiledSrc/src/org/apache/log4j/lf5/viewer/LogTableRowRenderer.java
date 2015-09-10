/*  1:   */ package org.apache.log4j.lf5.viewer;
/*  2:   */ 
/*  3:   */ import java.awt.Color;
/*  4:   */ import java.awt.Component;
/*  5:   */ import java.util.Map;
/*  6:   */ import javax.swing.JTable;
/*  7:   */ import javax.swing.table.DefaultTableCellRenderer;
/*  8:   */ import org.apache.log4j.lf5.LogLevel;
/*  9:   */ import org.apache.log4j.lf5.LogRecord;
/* 10:   */ 
/* 11:   */ public class LogTableRowRenderer
/* 12:   */   extends DefaultTableCellRenderer
/* 13:   */ {
/* 14:   */   private static final long serialVersionUID = -3951639953706443213L;
/* 15:47 */   protected boolean _highlightFatal = true;
/* 16:48 */   protected Color _color = new Color(230, 230, 230);
/* 17:   */   
/* 18:   */   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col)
/* 19:   */   {
/* 20:69 */     if (row % 2 == 0) {
/* 21:70 */       setBackground(this._color);
/* 22:   */     } else {
/* 23:72 */       setBackground(Color.white);
/* 24:   */     }
/* 25:75 */     FilteredLogTableModel model = (FilteredLogTableModel)table.getModel();
/* 26:76 */     LogRecord record = model.getFilteredRecord(row);
/* 27:   */     
/* 28:78 */     setForeground(getLogLevelColor(record.getLevel()));
/* 29:   */     
/* 30:80 */     return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
/* 31:   */   }
/* 32:   */   
/* 33:   */   protected Color getLogLevelColor(LogLevel level)
/* 34:   */   {
/* 35:92 */     return (Color)LogLevel.getLogLevelColorMap().get(level);
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.LogTableRowRenderer
 * JD-Core Version:    0.7.0.1
 */