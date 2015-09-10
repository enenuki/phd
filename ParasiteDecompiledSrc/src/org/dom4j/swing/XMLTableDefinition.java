/*   1:    */ package org.dom4j.swing;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import org.dom4j.Document;
/*  11:    */ import org.dom4j.DocumentHelper;
/*  12:    */ import org.dom4j.Element;
/*  13:    */ import org.dom4j.XPath;
/*  14:    */ import org.jaxen.VariableContext;
/*  15:    */ 
/*  16:    */ public class XMLTableDefinition
/*  17:    */   implements Serializable, VariableContext
/*  18:    */ {
/*  19:    */   private XPath rowXPath;
/*  20: 38 */   private List columns = new ArrayList();
/*  21:    */   private XMLTableColumnDefinition[] columnArray;
/*  22:    */   private Map columnNameIndex;
/*  23:    */   private VariableContext variableContext;
/*  24:    */   private Object rowValue;
/*  25:    */   
/*  26:    */   public static XMLTableDefinition load(Document definition)
/*  27:    */   {
/*  28: 64 */     return load(definition.getRootElement());
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static XMLTableDefinition load(Element definition)
/*  32:    */   {
/*  33: 76 */     XMLTableDefinition answer = new XMLTableDefinition();
/*  34: 77 */     answer.setRowExpression(definition.attributeValue("select"));
/*  35:    */     
/*  36: 79 */     Iterator iter = definition.elementIterator("column");
/*  37: 80 */     while (iter.hasNext())
/*  38:    */     {
/*  39: 81 */       Element element = (Element)iter.next();
/*  40: 82 */       String expression = element.attributeValue("select");
/*  41: 83 */       String name = element.getText();
/*  42: 84 */       String typeName = element.attributeValue("type", "string");
/*  43: 85 */       String columnXPath = element.attributeValue("columnNameXPath");
/*  44: 86 */       int type = XMLTableColumnDefinition.parseType(typeName);
/*  45: 88 */       if (columnXPath != null) {
/*  46: 89 */         answer.addColumnWithXPathName(columnXPath, expression, type);
/*  47:    */       } else {
/*  48: 91 */         answer.addColumn(name, expression, type);
/*  49:    */       }
/*  50:    */     }
/*  51: 95 */     return answer;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Class getColumnClass(int columnIndex)
/*  55:    */   {
/*  56: 99 */     return getColumn(columnIndex).getColumnClass();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getColumnCount()
/*  60:    */   {
/*  61:103 */     return this.columns.size();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String getColumnName(int columnIndex)
/*  65:    */   {
/*  66:116 */     return getColumn(columnIndex).getName();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public XPath getColumnXPath(int columnIndex)
/*  70:    */   {
/*  71:129 */     return getColumn(columnIndex).getXPath();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public XPath getColumnNameXPath(int columnIndex)
/*  75:    */   {
/*  76:142 */     return getColumn(columnIndex).getColumnNameXPath();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public synchronized Object getValueAt(Object row, int columnIndex)
/*  80:    */   {
/*  81:146 */     XMLTableColumnDefinition column = getColumn(columnIndex);
/*  82:147 */     Object answer = null;
/*  83:149 */     synchronized (this)
/*  84:    */     {
/*  85:150 */       this.rowValue = row;
/*  86:151 */       answer = column.getValue(row);
/*  87:152 */       this.rowValue = null;
/*  88:    */     }
/*  89:155 */     return answer;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void addColumn(String name, String expression)
/*  93:    */   {
/*  94:159 */     addColumn(name, expression, 0);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void addColumn(String name, String expression, int type)
/*  98:    */   {
/*  99:163 */     XPath xpath = createColumnXPath(expression);
/* 100:164 */     addColumn(new XMLTableColumnDefinition(name, xpath, type));
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void addColumnWithXPathName(String columnNameXPathExpression, String expression, int type)
/* 104:    */   {
/* 105:169 */     XPath columnNameXPath = createColumnXPath(columnNameXPathExpression);
/* 106:170 */     XPath xpath = createColumnXPath(expression);
/* 107:171 */     addColumn(new XMLTableColumnDefinition(columnNameXPath, xpath, type));
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void addStringColumn(String name, String expression)
/* 111:    */   {
/* 112:175 */     addColumn(name, expression, 1);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void addNumberColumn(String name, String expression)
/* 116:    */   {
/* 117:179 */     addColumn(name, expression, 2);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void addColumn(XMLTableColumnDefinition column)
/* 121:    */   {
/* 122:183 */     clearCaches();
/* 123:184 */     this.columns.add(column);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void removeColumn(XMLTableColumnDefinition column)
/* 127:    */   {
/* 128:188 */     clearCaches();
/* 129:189 */     this.columns.remove(column);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void clear()
/* 133:    */   {
/* 134:193 */     clearCaches();
/* 135:194 */     this.columns.clear();
/* 136:    */   }
/* 137:    */   
/* 138:    */   public XMLTableColumnDefinition getColumn(int index)
/* 139:    */   {
/* 140:198 */     if (this.columnArray == null)
/* 141:    */     {
/* 142:199 */       this.columnArray = new XMLTableColumnDefinition[this.columns.size()];
/* 143:200 */       this.columns.toArray(this.columnArray);
/* 144:    */     }
/* 145:203 */     return this.columnArray[index];
/* 146:    */   }
/* 147:    */   
/* 148:    */   public XMLTableColumnDefinition getColumn(String columnName)
/* 149:    */   {
/* 150:    */     Iterator it;
/* 151:207 */     if (this.columnNameIndex == null)
/* 152:    */     {
/* 153:208 */       this.columnNameIndex = new HashMap();
/* 154:210 */       for (it = this.columns.iterator(); it.hasNext();)
/* 155:    */       {
/* 156:211 */         XMLTableColumnDefinition column = (XMLTableColumnDefinition)it.next();
/* 157:    */         
/* 158:213 */         this.columnNameIndex.put(column.getName(), column);
/* 159:    */       }
/* 160:    */     }
/* 161:217 */     return (XMLTableColumnDefinition)this.columnNameIndex.get(columnName);
/* 162:    */   }
/* 163:    */   
/* 164:    */   public XPath getRowXPath()
/* 165:    */   {
/* 166:226 */     return this.rowXPath;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setRowXPath(XPath rowXPath)
/* 170:    */   {
/* 171:236 */     this.rowXPath = rowXPath;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setRowExpression(String xpath)
/* 175:    */   {
/* 176:240 */     setRowXPath(createXPath(xpath));
/* 177:    */   }
/* 178:    */   
/* 179:    */   public Object getVariableValue(String namespaceURI, String prefix, String localName)
/* 180:    */   {
/* 181:247 */     XMLTableColumnDefinition column = getColumn(localName);
/* 182:249 */     if (column != null) {
/* 183:250 */       return column.getValue(this.rowValue);
/* 184:    */     }
/* 185:253 */     return null;
/* 186:    */   }
/* 187:    */   
/* 188:    */   protected XPath createXPath(String expression)
/* 189:    */   {
/* 190:259 */     return DocumentHelper.createXPath(expression);
/* 191:    */   }
/* 192:    */   
/* 193:    */   protected XPath createColumnXPath(String expression)
/* 194:    */   {
/* 195:263 */     XPath xpath = createXPath(expression);
/* 196:    */     
/* 197:    */ 
/* 198:266 */     xpath.setVariableContext(this);
/* 199:    */     
/* 200:268 */     return xpath;
/* 201:    */   }
/* 202:    */   
/* 203:    */   protected void clearCaches()
/* 204:    */   {
/* 205:272 */     this.columnArray = null;
/* 206:273 */     this.columnNameIndex = null;
/* 207:    */   }
/* 208:    */   
/* 209:    */   protected void handleException(Exception e)
/* 210:    */   {
/* 211:278 */     System.out.println("Caught: " + e);
/* 212:    */   }
/* 213:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.swing.XMLTableDefinition
 * JD-Core Version:    0.7.0.1
 */