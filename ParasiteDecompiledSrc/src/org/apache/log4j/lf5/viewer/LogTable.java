/*   1:    */ package org.apache.log4j.lf5.viewer;
/*   2:    */ 
/*   3:    */ import java.awt.Font;
/*   4:    */ import java.awt.FontMetrics;
/*   5:    */ import java.awt.Graphics;
/*   6:    */ import java.util.Enumeration;
/*   7:    */ import java.util.EventObject;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Vector;
/*  11:    */ import javax.swing.JComponent;
/*  12:    */ import javax.swing.JTable;
/*  13:    */ import javax.swing.JTextArea;
/*  14:    */ import javax.swing.ListSelectionModel;
/*  15:    */ import javax.swing.event.ListSelectionEvent;
/*  16:    */ import javax.swing.event.ListSelectionListener;
/*  17:    */ import javax.swing.table.TableColumn;
/*  18:    */ import javax.swing.table.TableColumnModel;
/*  19:    */ import javax.swing.table.TableModel;
/*  20:    */ import javax.swing.text.JTextComponent;
/*  21:    */ import org.apache.log4j.lf5.util.DateFormatManager;
/*  22:    */ 
/*  23:    */ public class LogTable
/*  24:    */   extends JTable
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 4867085140195148458L;
/*  27: 57 */   protected int _rowHeight = 30;
/*  28:    */   protected JTextArea _detailTextArea;
/*  29: 61 */   protected int _numCols = 9;
/*  30: 62 */   protected TableColumn[] _tableColumns = new TableColumn[this._numCols];
/*  31: 63 */   protected int[] _colWidths = { 40, 40, 40, 70, 70, 360, 440, 200, 60 };
/*  32: 64 */   protected LogTableColumn[] _colNames = LogTableColumn.getLogTableColumnArray();
/*  33: 65 */   protected int _colDate = 0;
/*  34: 66 */   protected int _colThread = 1;
/*  35: 67 */   protected int _colMessageNum = 2;
/*  36: 68 */   protected int _colLevel = 3;
/*  37: 69 */   protected int _colNDC = 4;
/*  38: 70 */   protected int _colCategory = 5;
/*  39: 71 */   protected int _colMessage = 6;
/*  40: 72 */   protected int _colLocation = 7;
/*  41: 73 */   protected int _colThrown = 8;
/*  42: 75 */   protected DateFormatManager _dateFormatManager = null;
/*  43:    */   
/*  44:    */   public LogTable(JTextArea detailTextArea)
/*  45:    */   {
/*  46: 88 */     init();
/*  47:    */     
/*  48: 90 */     this._detailTextArea = detailTextArea;
/*  49:    */     
/*  50: 92 */     setModel(new FilteredLogTableModel());
/*  51:    */     
/*  52: 94 */     Enumeration columns = getColumnModel().getColumns();
/*  53: 95 */     int i = 0;
/*  54: 96 */     while (columns.hasMoreElements())
/*  55:    */     {
/*  56: 97 */       TableColumn col = (TableColumn)columns.nextElement();
/*  57: 98 */       col.setCellRenderer(new LogTableRowRenderer());
/*  58: 99 */       col.setPreferredWidth(this._colWidths[i]);
/*  59:    */       
/*  60:101 */       this._tableColumns[i] = col;
/*  61:102 */       i++;
/*  62:    */     }
/*  63:105 */     ListSelectionModel rowSM = getSelectionModel();
/*  64:106 */     rowSM.addListSelectionListener(new LogTableListSelectionListener(this));
/*  65:    */   }
/*  66:    */   
/*  67:    */   public DateFormatManager getDateFormatManager()
/*  68:    */   {
/*  69:119 */     return this._dateFormatManager;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setDateFormatManager(DateFormatManager dfm)
/*  73:    */   {
/*  74:126 */     this._dateFormatManager = dfm;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public synchronized void clearLogRecords()
/*  78:    */   {
/*  79:134 */     getFilteredLogTableModel().clear();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public FilteredLogTableModel getFilteredLogTableModel()
/*  83:    */   {
/*  84:138 */     return (FilteredLogTableModel)getModel();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setDetailedView()
/*  88:    */   {
/*  89:144 */     TableColumnModel model = getColumnModel();
/*  90:146 */     for (int f = 0; f < this._numCols; f++) {
/*  91:147 */       model.removeColumn(this._tableColumns[f]);
/*  92:    */     }
/*  93:150 */     for (int i = 0; i < this._numCols; i++) {
/*  94:151 */       model.addColumn(this._tableColumns[i]);
/*  95:    */     }
/*  96:154 */     sizeColumnsToFit(-1);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setView(List columns)
/* 100:    */   {
/* 101:158 */     TableColumnModel model = getColumnModel();
/* 102:161 */     for (int f = 0; f < this._numCols; f++) {
/* 103:162 */       model.removeColumn(this._tableColumns[f]);
/* 104:    */     }
/* 105:164 */     Iterator selectedColumns = columns.iterator();
/* 106:165 */     Vector columnNameAndNumber = getColumnNameAndNumber();
/* 107:166 */     while (selectedColumns.hasNext()) {
/* 108:168 */       model.addColumn(this._tableColumns[columnNameAndNumber.indexOf(selectedColumns.next())]);
/* 109:    */     }
/* 110:172 */     sizeColumnsToFit(-1);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setFont(Font font)
/* 114:    */   {
/* 115:176 */     super.setFont(font);
/* 116:177 */     Graphics g = getGraphics();
/* 117:178 */     if (g != null)
/* 118:    */     {
/* 119:179 */       FontMetrics fm = g.getFontMetrics(font);
/* 120:180 */       int height = fm.getHeight();
/* 121:181 */       this._rowHeight = (height + height / 3);
/* 122:182 */       setRowHeight(this._rowHeight);
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   protected void init()
/* 127:    */   {
/* 128:194 */     setRowHeight(this._rowHeight);
/* 129:195 */     setSelectionMode(0);
/* 130:    */   }
/* 131:    */   
/* 132:    */   protected Vector getColumnNameAndNumber()
/* 133:    */   {
/* 134:200 */     Vector columnNameAndNumber = new Vector();
/* 135:201 */     for (int i = 0; i < this._colNames.length; i++) {
/* 136:202 */       columnNameAndNumber.add(i, this._colNames[i]);
/* 137:    */     }
/* 138:204 */     return columnNameAndNumber;
/* 139:    */   }
/* 140:    */   
/* 141:    */   class LogTableListSelectionListener
/* 142:    */     implements ListSelectionListener
/* 143:    */   {
/* 144:    */     protected JTable _table;
/* 145:    */     
/* 146:    */     public LogTableListSelectionListener(JTable table)
/* 147:    */     {
/* 148:219 */       this._table = table;
/* 149:    */     }
/* 150:    */     
/* 151:    */     public void valueChanged(ListSelectionEvent e)
/* 152:    */     {
/* 153:224 */       if (e.getValueIsAdjusting()) {
/* 154:225 */         return;
/* 155:    */       }
/* 156:228 */       ListSelectionModel lsm = (ListSelectionModel)e.getSource();
/* 157:229 */       if (!lsm.isSelectionEmpty())
/* 158:    */       {
/* 159:232 */         StringBuffer buf = new StringBuffer();
/* 160:233 */         int selectedRow = lsm.getMinSelectionIndex();
/* 161:235 */         for (int i = 0; i < LogTable.this._numCols - 1; i++)
/* 162:    */         {
/* 163:236 */           String value = "";
/* 164:237 */           Object obj = this._table.getModel().getValueAt(selectedRow, i);
/* 165:238 */           if (obj != null) {
/* 166:239 */             value = obj.toString();
/* 167:    */           }
/* 168:242 */           buf.append(LogTable.this._colNames[i] + ":");
/* 169:243 */           buf.append("\t");
/* 170:245 */           if ((i == LogTable.this._colThread) || (i == LogTable.this._colMessage) || (i == LogTable.this._colLevel)) {
/* 171:246 */             buf.append("\t");
/* 172:    */           }
/* 173:249 */           if ((i == LogTable.this._colDate) || (i == LogTable.this._colNDC)) {
/* 174:250 */             buf.append("\t\t");
/* 175:    */           }
/* 176:258 */           buf.append(value);
/* 177:259 */           buf.append("\n");
/* 178:    */         }
/* 179:261 */         buf.append(LogTable.this._colNames[(LogTable.this._numCols - 1)] + ":\n");
/* 180:262 */         Object obj = this._table.getModel().getValueAt(selectedRow, LogTable.this._numCols - 1);
/* 181:263 */         if (obj != null) {
/* 182:264 */           buf.append(obj.toString());
/* 183:    */         }
/* 184:267 */         LogTable.this._detailTextArea.setText(buf.toString());
/* 185:    */       }
/* 186:    */     }
/* 187:    */   }
/* 188:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.LogTable
 * JD-Core Version:    0.7.0.1
 */