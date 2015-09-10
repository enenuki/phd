/*   1:    */ package org.dom4j.swing;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.List;
/*   5:    */ import javax.swing.table.AbstractTableModel;
/*   6:    */ import org.dom4j.Document;
/*   7:    */ import org.dom4j.Element;
/*   8:    */ import org.dom4j.XPath;
/*   9:    */ 
/*  10:    */ public class XMLTableModel
/*  11:    */   extends AbstractTableModel
/*  12:    */ {
/*  13:    */   private XMLTableDefinition definition;
/*  14:    */   private Object source;
/*  15:    */   private List rows;
/*  16:    */   
/*  17:    */   public XMLTableModel(Element tableDefinition, Object source)
/*  18:    */   {
/*  19: 46 */     this(XMLTableDefinition.load(tableDefinition), source);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public XMLTableModel(Document tableDefinition, Object source)
/*  23:    */   {
/*  24: 59 */     this(XMLTableDefinition.load(tableDefinition), source);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public XMLTableModel(XMLTableDefinition definition, Object source)
/*  28:    */   {
/*  29: 63 */     this.definition = definition;
/*  30: 64 */     this.source = source;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Object getRowValue(int rowIndex)
/*  34:    */   {
/*  35: 68 */     return getRows().get(rowIndex);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public List getRows()
/*  39:    */   {
/*  40: 72 */     if (this.rows == null) {
/*  41: 73 */       this.rows = this.definition.getRowXPath().selectNodes(this.source);
/*  42:    */     }
/*  43: 76 */     return this.rows;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Class getColumnClass(int columnIndex)
/*  47:    */   {
/*  48: 82 */     return this.definition.getColumnClass(columnIndex);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int getColumnCount()
/*  52:    */   {
/*  53: 86 */     return this.definition.getColumnCount();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String getColumnName(int columnIndex)
/*  57:    */   {
/*  58: 90 */     XPath xpath = this.definition.getColumnNameXPath(columnIndex);
/*  59: 92 */     if (xpath != null)
/*  60:    */     {
/*  61: 93 */       System.out.println("Evaluating column xpath: " + xpath + " value: " + xpath.valueOf(this.source));
/*  62:    */       
/*  63:    */ 
/*  64: 96 */       return xpath.valueOf(this.source);
/*  65:    */     }
/*  66: 99 */     return this.definition.getColumnName(columnIndex);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Object getValueAt(int rowIndex, int columnIndex)
/*  70:    */   {
/*  71:    */     try
/*  72:    */     {
/*  73:104 */       Object row = getRowValue(rowIndex);
/*  74:    */       
/*  75:106 */       return this.definition.getValueAt(row, columnIndex);
/*  76:    */     }
/*  77:    */     catch (Exception e)
/*  78:    */     {
/*  79:108 */       handleException(e);
/*  80:    */     }
/*  81:110 */     return null;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int getRowCount()
/*  85:    */   {
/*  86:115 */     return getRows().size();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public XMLTableDefinition getDefinition()
/*  90:    */   {
/*  91:127 */     return this.definition;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setDefinition(XMLTableDefinition definition)
/*  95:    */   {
/*  96:137 */     this.definition = definition;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public Object getSource()
/* 100:    */   {
/* 101:146 */     return this.source;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setSource(Object source)
/* 105:    */   {
/* 106:156 */     this.source = source;
/* 107:157 */     this.rows = null;
/* 108:    */   }
/* 109:    */   
/* 110:    */   protected void handleException(Exception e)
/* 111:    */   {
/* 112:164 */     System.out.println("Caught: " + e);
/* 113:    */   }
/* 114:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.swing.XMLTableModel
 * JD-Core Version:    0.7.0.1
 */