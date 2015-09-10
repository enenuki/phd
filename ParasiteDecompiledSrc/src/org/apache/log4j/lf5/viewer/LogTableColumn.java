/*   1:    */ package org.apache.log4j.lf5.viewer;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ 
/*   9:    */ public class LogTableColumn
/*  10:    */   implements Serializable
/*  11:    */ {
/*  12:    */   private static final long serialVersionUID = -4275827753626456547L;
/*  13: 37 */   public static final LogTableColumn DATE = new LogTableColumn("Date");
/*  14: 38 */   public static final LogTableColumn THREAD = new LogTableColumn("Thread");
/*  15: 39 */   public static final LogTableColumn MESSAGE_NUM = new LogTableColumn("Message #");
/*  16: 40 */   public static final LogTableColumn LEVEL = new LogTableColumn("Level");
/*  17: 41 */   public static final LogTableColumn NDC = new LogTableColumn("NDC");
/*  18: 42 */   public static final LogTableColumn CATEGORY = new LogTableColumn("Category");
/*  19: 43 */   public static final LogTableColumn MESSAGE = new LogTableColumn("Message");
/*  20: 44 */   public static final LogTableColumn LOCATION = new LogTableColumn("Location");
/*  21: 45 */   public static final LogTableColumn THROWN = new LogTableColumn("Thrown");
/*  22:    */   protected String _label;
/*  23: 63 */   private static LogTableColumn[] _log4JColumns = { DATE, THREAD, MESSAGE_NUM, LEVEL, NDC, CATEGORY, MESSAGE, LOCATION, THROWN };
/*  24: 66 */   private static Map _logTableColumnMap = new HashMap();
/*  25:    */   
/*  26:    */   static
/*  27:    */   {
/*  28: 68 */     for (int i = 0; i < _log4JColumns.length; i++) {
/*  29: 69 */       _logTableColumnMap.put(_log4JColumns[i].getLabel(), _log4JColumns[i]);
/*  30:    */     }
/*  31:    */   }
/*  32:    */   
/*  33:    */   public LogTableColumn(String label)
/*  34:    */   {
/*  35: 75 */     this._label = label;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public String getLabel()
/*  39:    */   {
/*  40: 86 */     return this._label;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static LogTableColumn valueOf(String column)
/*  44:    */     throws LogTableColumnFormatException
/*  45:    */   {
/*  46: 99 */     LogTableColumn tableColumn = null;
/*  47:100 */     if (column != null)
/*  48:    */     {
/*  49:101 */       column = column.trim();
/*  50:102 */       tableColumn = (LogTableColumn)_logTableColumnMap.get(column);
/*  51:    */     }
/*  52:105 */     if (tableColumn == null)
/*  53:    */     {
/*  54:106 */       StringBuffer buf = new StringBuffer();
/*  55:107 */       buf.append("Error while trying to parse (" + column + ") into");
/*  56:108 */       buf.append(" a LogTableColumn.");
/*  57:109 */       throw new LogTableColumnFormatException(buf.toString());
/*  58:    */     }
/*  59:111 */     return tableColumn;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean equals(Object o)
/*  63:    */   {
/*  64:116 */     boolean equals = false;
/*  65:118 */     if (((o instanceof LogTableColumn)) && 
/*  66:119 */       (getLabel() == ((LogTableColumn)o).getLabel())) {
/*  67:121 */       equals = true;
/*  68:    */     }
/*  69:125 */     return equals;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int hashCode()
/*  73:    */   {
/*  74:129 */     return this._label.hashCode();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String toString()
/*  78:    */   {
/*  79:133 */     return this._label;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public static List getLogTableColumns()
/*  83:    */   {
/*  84:141 */     return Arrays.asList(_log4JColumns);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static LogTableColumn[] getLogTableColumnArray()
/*  88:    */   {
/*  89:145 */     return _log4JColumns;
/*  90:    */   }
/*  91:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.LogTableColumn
 * JD-Core Version:    0.7.0.1
 */