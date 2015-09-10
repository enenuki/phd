/*   1:    */ package org.dom4j.swing;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import org.dom4j.DocumentHelper;
/*   6:    */ import org.dom4j.Node;
/*   7:    */ import org.dom4j.XPath;
/*   8:    */ 
/*   9:    */ public class XMLTableColumnDefinition
/*  10:    */   implements Serializable
/*  11:    */ {
/*  12:    */   public static final int OBJECT_TYPE = 0;
/*  13:    */   public static final int STRING_TYPE = 1;
/*  14:    */   public static final int NUMBER_TYPE = 2;
/*  15:    */   public static final int NODE_TYPE = 3;
/*  16:    */   private int type;
/*  17:    */   private String name;
/*  18:    */   private XPath xpath;
/*  19:    */   private XPath columnNameXPath;
/*  20:    */   
/*  21:    */   public XMLTableColumnDefinition() {}
/*  22:    */   
/*  23:    */   public XMLTableColumnDefinition(String name, String expression, int type)
/*  24:    */   {
/*  25: 49 */     this.name = name;
/*  26: 50 */     this.type = type;
/*  27: 51 */     this.xpath = createXPath(expression);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public XMLTableColumnDefinition(String name, XPath xpath, int type)
/*  31:    */   {
/*  32: 55 */     this.name = name;
/*  33: 56 */     this.xpath = xpath;
/*  34: 57 */     this.type = type;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public XMLTableColumnDefinition(XPath columnXPath, XPath xpath, int type)
/*  38:    */   {
/*  39: 61 */     this.xpath = xpath;
/*  40: 62 */     this.columnNameXPath = columnXPath;
/*  41: 63 */     this.type = type;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static int parseType(String typeName)
/*  45:    */   {
/*  46: 67 */     if ((typeName != null) && (typeName.length() > 0))
/*  47:    */     {
/*  48: 68 */       if (typeName.equals("string")) {
/*  49: 69 */         return 1;
/*  50:    */       }
/*  51: 70 */       if (typeName.equals("number")) {
/*  52: 71 */         return 2;
/*  53:    */       }
/*  54: 72 */       if (typeName.equals("node")) {
/*  55: 73 */         return 3;
/*  56:    */       }
/*  57:    */     }
/*  58: 77 */     return 0;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Class getColumnClass()
/*  62:    */   {
/*  63: 81 */     switch (this.type)
/*  64:    */     {
/*  65:    */     case 1: 
/*  66: 83 */       return String.class;
/*  67:    */     case 2: 
/*  68: 86 */       return Number.class;
/*  69:    */     case 3: 
/*  70: 89 */       return Node.class;
/*  71:    */     }
/*  72: 92 */     return Object.class;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public Object getValue(Object row)
/*  76:    */   {
/*  77: 97 */     switch (this.type)
/*  78:    */     {
/*  79:    */     case 1: 
/*  80: 99 */       return this.xpath.valueOf(row);
/*  81:    */     case 2: 
/*  82:102 */       return this.xpath.numberValueOf(row);
/*  83:    */     case 3: 
/*  84:105 */       return this.xpath.selectSingleNode(row);
/*  85:    */     }
/*  86:108 */     return this.xpath.evaluate(row);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getType()
/*  90:    */   {
/*  91:121 */     return this.type;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setType(int type)
/*  95:    */   {
/*  96:131 */     this.type = type;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getName()
/* 100:    */   {
/* 101:140 */     return this.name;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setName(String name)
/* 105:    */   {
/* 106:150 */     this.name = name;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public XPath getXPath()
/* 110:    */   {
/* 111:159 */     return this.xpath;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setXPath(XPath xPath)
/* 115:    */   {
/* 116:169 */     this.xpath = xPath;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public XPath getColumnNameXPath()
/* 120:    */   {
/* 121:178 */     return this.columnNameXPath;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setColumnNameXPath(XPath columnNameXPath)
/* 125:    */   {
/* 126:188 */     this.columnNameXPath = columnNameXPath;
/* 127:    */   }
/* 128:    */   
/* 129:    */   protected XPath createXPath(String expression)
/* 130:    */   {
/* 131:194 */     return DocumentHelper.createXPath(expression);
/* 132:    */   }
/* 133:    */   
/* 134:    */   protected void handleException(Exception e)
/* 135:    */   {
/* 136:199 */     System.out.println("Caught: " + e);
/* 137:    */   }
/* 138:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.swing.XMLTableColumnDefinition
 * JD-Core Version:    0.7.0.1
 */