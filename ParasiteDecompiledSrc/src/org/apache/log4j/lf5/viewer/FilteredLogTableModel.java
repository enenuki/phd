/*   1:    */ package org.apache.log4j.lf5.viewer;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Date;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.swing.table.AbstractTableModel;
/*   8:    */ import org.apache.log4j.lf5.LogRecord;
/*   9:    */ import org.apache.log4j.lf5.LogRecordFilter;
/*  10:    */ import org.apache.log4j.lf5.PassingLogRecordFilter;
/*  11:    */ 
/*  12:    */ public class FilteredLogTableModel
/*  13:    */   extends AbstractTableModel
/*  14:    */ {
/*  15: 49 */   protected LogRecordFilter _filter = new PassingLogRecordFilter();
/*  16: 50 */   protected List _allRecords = new ArrayList();
/*  17:    */   protected List _filteredRecords;
/*  18: 52 */   protected int _maxNumberOfLogRecords = 5000;
/*  19: 53 */   protected String[] _colNames = { "Date", "Thread", "Message #", "Level", "NDC", "Category", "Message", "Location", "Thrown" };
/*  20:    */   
/*  21:    */   public void setLogRecordFilter(LogRecordFilter filter)
/*  22:    */   {
/*  23: 80 */     this._filter = filter;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public LogRecordFilter getLogRecordFilter()
/*  27:    */   {
/*  28: 84 */     return this._filter;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public String getColumnName(int i)
/*  32:    */   {
/*  33: 88 */     return this._colNames[i];
/*  34:    */   }
/*  35:    */   
/*  36:    */   public int getColumnCount()
/*  37:    */   {
/*  38: 92 */     return this._colNames.length;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public int getRowCount()
/*  42:    */   {
/*  43: 96 */     return getFilteredRecords().size();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getTotalRowCount()
/*  47:    */   {
/*  48:100 */     return this._allRecords.size();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Object getValueAt(int row, int col)
/*  52:    */   {
/*  53:104 */     LogRecord record = getFilteredRecord(row);
/*  54:105 */     return getColumn(col, record);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setMaxNumberOfLogRecords(int maxNumRecords)
/*  58:    */   {
/*  59:109 */     if (maxNumRecords > 0) {
/*  60:110 */       this._maxNumberOfLogRecords = maxNumRecords;
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   public synchronized boolean addLogRecord(LogRecord record)
/*  65:    */   {
/*  66:117 */     this._allRecords.add(record);
/*  67:119 */     if (!this._filter.passes(record)) {
/*  68:120 */       return false;
/*  69:    */     }
/*  70:122 */     getFilteredRecords().add(record);
/*  71:123 */     fireTableRowsInserted(getRowCount(), getRowCount());
/*  72:124 */     trimRecords();
/*  73:125 */     return true;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public synchronized void refresh()
/*  77:    */   {
/*  78:133 */     this._filteredRecords = createFilteredRecordsList();
/*  79:134 */     fireTableDataChanged();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public synchronized void fastRefresh()
/*  83:    */   {
/*  84:138 */     this._filteredRecords.remove(0);
/*  85:139 */     fireTableRowsDeleted(0, 0);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public synchronized void clear()
/*  89:    */   {
/*  90:147 */     this._allRecords.clear();
/*  91:148 */     this._filteredRecords.clear();
/*  92:149 */     fireTableDataChanged();
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected List getFilteredRecords()
/*  96:    */   {
/*  97:157 */     if (this._filteredRecords == null) {
/*  98:158 */       refresh();
/*  99:    */     }
/* 100:160 */     return this._filteredRecords;
/* 101:    */   }
/* 102:    */   
/* 103:    */   protected List createFilteredRecordsList()
/* 104:    */   {
/* 105:164 */     List result = new ArrayList();
/* 106:165 */     Iterator records = this._allRecords.iterator();
/* 107:167 */     while (records.hasNext())
/* 108:    */     {
/* 109:168 */       LogRecord current = (LogRecord)records.next();
/* 110:169 */       if (this._filter.passes(current)) {
/* 111:170 */         result.add(current);
/* 112:    */       }
/* 113:    */     }
/* 114:173 */     return result;
/* 115:    */   }
/* 116:    */   
/* 117:    */   protected LogRecord getFilteredRecord(int row)
/* 118:    */   {
/* 119:177 */     List records = getFilteredRecords();
/* 120:178 */     int size = records.size();
/* 121:179 */     if (row < size) {
/* 122:180 */       return (LogRecord)records.get(row);
/* 123:    */     }
/* 124:186 */     return (LogRecord)records.get(size - 1);
/* 125:    */   }
/* 126:    */   
/* 127:    */   protected Object getColumn(int col, LogRecord lr)
/* 128:    */   {
/* 129:191 */     if (lr == null) {
/* 130:192 */       return "NULL Column";
/* 131:    */     }
/* 132:194 */     String date = new Date(lr.getMillis()).toString();
/* 133:195 */     switch (col)
/* 134:    */     {
/* 135:    */     case 0: 
/* 136:197 */       return date + " (" + lr.getMillis() + ")";
/* 137:    */     case 1: 
/* 138:199 */       return lr.getThreadDescription();
/* 139:    */     case 2: 
/* 140:201 */       return new Long(lr.getSequenceNumber());
/* 141:    */     case 3: 
/* 142:203 */       return lr.getLevel();
/* 143:    */     case 4: 
/* 144:205 */       return lr.getNDC();
/* 145:    */     case 5: 
/* 146:207 */       return lr.getCategory();
/* 147:    */     case 6: 
/* 148:209 */       return lr.getMessage();
/* 149:    */     case 7: 
/* 150:211 */       return lr.getLocation();
/* 151:    */     case 8: 
/* 152:213 */       return lr.getThrownStackTrace();
/* 153:    */     }
/* 154:215 */     String message = "The column number " + col + "must be between 0 and 8";
/* 155:216 */     throw new IllegalArgumentException(message);
/* 156:    */   }
/* 157:    */   
/* 158:    */   protected void trimRecords()
/* 159:    */   {
/* 160:227 */     if (needsTrimming()) {
/* 161:228 */       trimOldestRecords();
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   protected boolean needsTrimming()
/* 166:    */   {
/* 167:233 */     return this._allRecords.size() > this._maxNumberOfLogRecords;
/* 168:    */   }
/* 169:    */   
/* 170:    */   protected void trimOldestRecords()
/* 171:    */   {
/* 172:237 */     synchronized (this._allRecords)
/* 173:    */     {
/* 174:238 */       int trim = numberOfRecordsToTrim();
/* 175:239 */       if (trim > 1)
/* 176:    */       {
/* 177:240 */         List oldRecords = this._allRecords.subList(0, trim);
/* 178:    */         
/* 179:242 */         oldRecords.clear();
/* 180:243 */         refresh();
/* 181:    */       }
/* 182:    */       else
/* 183:    */       {
/* 184:245 */         this._allRecords.remove(0);
/* 185:246 */         fastRefresh();
/* 186:    */       }
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   private int numberOfRecordsToTrim()
/* 191:    */   {
/* 192:256 */     return this._allRecords.size() - this._maxNumberOfLogRecords;
/* 193:    */   }
/* 194:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.FilteredLogTableModel
 * JD-Core Version:    0.7.0.1
 */