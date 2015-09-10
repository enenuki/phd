/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.tools.debugger;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ import java.util.Collections;
/*    5:     */ import java.util.List;
/*    6:     */ import javax.swing.table.AbstractTableModel;
/*    7:     */ 
/*    8:     */ class MyTableModel
/*    9:     */   extends AbstractTableModel
/*   10:     */ {
/*   11:     */   private static final long serialVersionUID = 2971618907207577000L;
/*   12:     */   private SwingGui debugGui;
/*   13:     */   private List<String> expressions;
/*   14:     */   private List<String> values;
/*   15:     */   
/*   16:     */   public MyTableModel(SwingGui debugGui)
/*   17:     */   {
/*   18:2336 */     this.debugGui = debugGui;
/*   19:2337 */     this.expressions = Collections.synchronizedList(new ArrayList());
/*   20:2338 */     this.values = Collections.synchronizedList(new ArrayList());
/*   21:2339 */     this.expressions.add("");
/*   22:2340 */     this.values.add("");
/*   23:     */   }
/*   24:     */   
/*   25:     */   public int getColumnCount()
/*   26:     */   {
/*   27:2347 */     return 2;
/*   28:     */   }
/*   29:     */   
/*   30:     */   public int getRowCount()
/*   31:     */   {
/*   32:2354 */     return this.expressions.size();
/*   33:     */   }
/*   34:     */   
/*   35:     */   public String getColumnName(int column)
/*   36:     */   {
/*   37:2362 */     switch (column)
/*   38:     */     {
/*   39:     */     case 0: 
/*   40:2364 */       return "Expression";
/*   41:     */     case 1: 
/*   42:2366 */       return "Value";
/*   43:     */     }
/*   44:2368 */     return null;
/*   45:     */   }
/*   46:     */   
/*   47:     */   public boolean isCellEditable(int row, int column)
/*   48:     */   {
/*   49:2376 */     return true;
/*   50:     */   }
/*   51:     */   
/*   52:     */   public Object getValueAt(int row, int column)
/*   53:     */   {
/*   54:2383 */     switch (column)
/*   55:     */     {
/*   56:     */     case 0: 
/*   57:2385 */       return this.expressions.get(row);
/*   58:     */     case 1: 
/*   59:2387 */       return this.values.get(row);
/*   60:     */     }
/*   61:2389 */     return "";
/*   62:     */   }
/*   63:     */   
/*   64:     */   public void setValueAt(Object value, int row, int column)
/*   65:     */   {
/*   66:2397 */     switch (column)
/*   67:     */     {
/*   68:     */     case 0: 
/*   69:2399 */       String expr = value.toString();
/*   70:2400 */       this.expressions.set(row, expr);
/*   71:2401 */       String result = "";
/*   72:2402 */       if (expr.length() > 0)
/*   73:     */       {
/*   74:2403 */         result = this.debugGui.dim.eval(expr);
/*   75:2404 */         if (result == null) {
/*   76:2404 */           result = "";
/*   77:     */         }
/*   78:     */       }
/*   79:2406 */       this.values.set(row, result);
/*   80:2407 */       updateModel();
/*   81:2408 */       if (row + 1 == this.expressions.size())
/*   82:     */       {
/*   83:2409 */         this.expressions.add("");
/*   84:2410 */         this.values.add("");
/*   85:2411 */         fireTableRowsInserted(row + 1, row + 1);
/*   86:     */       }
/*   87:     */       break;
/*   88:     */     case 1: 
/*   89:2416 */       fireTableDataChanged();
/*   90:     */     }
/*   91:     */   }
/*   92:     */   
/*   93:     */   void updateModel()
/*   94:     */   {
/*   95:2424 */     for (int i = 0; i < this.expressions.size(); i++)
/*   96:     */     {
/*   97:2425 */       String expr = (String)this.expressions.get(i);
/*   98:2426 */       String result = "";
/*   99:2427 */       if (expr.length() > 0)
/*  100:     */       {
/*  101:2428 */         result = this.debugGui.dim.eval(expr);
/*  102:2429 */         if (result == null) {
/*  103:2429 */           result = "";
/*  104:     */         }
/*  105:     */       }
/*  106:     */       else
/*  107:     */       {
/*  108:2431 */         result = "";
/*  109:     */       }
/*  110:2433 */       result = result.replace('\n', ' ');
/*  111:2434 */       this.values.set(i, result);
/*  112:     */     }
/*  113:2436 */     fireTableDataChanged();
/*  114:     */   }
/*  115:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.debugger.MyTableModel
 * JD-Core Version:    0.7.0.1
 */