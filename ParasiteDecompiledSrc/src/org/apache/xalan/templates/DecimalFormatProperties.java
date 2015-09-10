/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import java.text.DecimalFormatSymbols;
/*   4:    */ import org.apache.xml.utils.QName;
/*   5:    */ 
/*   6:    */ public class DecimalFormatProperties
/*   7:    */   extends ElemTemplateElement
/*   8:    */ {
/*   9:    */   static final long serialVersionUID = -6559409339256269446L;
/*  10:    */   DecimalFormatSymbols m_dfs;
/*  11:    */   
/*  12:    */   public DecimalFormatProperties(int docOrderNumber)
/*  13:    */   {
/*  14: 63 */     this.m_dfs = new DecimalFormatSymbols();
/*  15:    */     
/*  16:    */ 
/*  17: 66 */     this.m_dfs.setInfinity("Infinity");
/*  18: 67 */     this.m_dfs.setNaN("NaN");
/*  19:    */     
/*  20: 69 */     this.m_docOrderNumber = docOrderNumber;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public DecimalFormatSymbols getDecimalFormatSymbols()
/*  24:    */   {
/*  25: 93 */     return this.m_dfs;
/*  26:    */   }
/*  27:    */   
/*  28:101 */   private QName m_qname = null;
/*  29:    */   
/*  30:    */   public void setName(QName qname)
/*  31:    */   {
/*  32:112 */     this.m_qname = qname;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public QName getName()
/*  36:    */   {
/*  37:125 */     if (this.m_qname == null) {
/*  38:126 */       return new QName("");
/*  39:    */     }
/*  40:128 */     return this.m_qname;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setDecimalSeparator(char ds)
/*  44:    */   {
/*  45:140 */     this.m_dfs.setDecimalSeparator(ds);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public char getDecimalSeparator()
/*  49:    */   {
/*  50:152 */     return this.m_dfs.getDecimalSeparator();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setGroupingSeparator(char gs)
/*  54:    */   {
/*  55:164 */     this.m_dfs.setGroupingSeparator(gs);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public char getGroupingSeparator()
/*  59:    */   {
/*  60:176 */     return this.m_dfs.getGroupingSeparator();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setInfinity(String inf)
/*  64:    */   {
/*  65:188 */     this.m_dfs.setInfinity(inf);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String getInfinity()
/*  69:    */   {
/*  70:200 */     return this.m_dfs.getInfinity();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setMinusSign(char v)
/*  74:    */   {
/*  75:212 */     this.m_dfs.setMinusSign(v);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public char getMinusSign()
/*  79:    */   {
/*  80:224 */     return this.m_dfs.getMinusSign();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setNaN(String v)
/*  84:    */   {
/*  85:236 */     this.m_dfs.setNaN(v);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String getNaN()
/*  89:    */   {
/*  90:248 */     return this.m_dfs.getNaN();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getNodeName()
/*  94:    */   {
/*  95:258 */     return "decimal-format";
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setPercent(char v)
/*  99:    */   {
/* 100:270 */     this.m_dfs.setPercent(v);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public char getPercent()
/* 104:    */   {
/* 105:282 */     return this.m_dfs.getPercent();
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setPerMille(char v)
/* 109:    */   {
/* 110:294 */     this.m_dfs.setPerMill(v);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public char getPerMille()
/* 114:    */   {
/* 115:306 */     return this.m_dfs.getPerMill();
/* 116:    */   }
/* 117:    */   
/* 118:    */   public int getXSLToken()
/* 119:    */   {
/* 120:317 */     return 83;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setZeroDigit(char v)
/* 124:    */   {
/* 125:329 */     this.m_dfs.setZeroDigit(v);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public char getZeroDigit()
/* 129:    */   {
/* 130:341 */     return this.m_dfs.getZeroDigit();
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setDigit(char v)
/* 134:    */   {
/* 135:353 */     this.m_dfs.setDigit(v);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public char getDigit()
/* 139:    */   {
/* 140:365 */     return this.m_dfs.getDigit();
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setPatternSeparator(char v)
/* 144:    */   {
/* 145:378 */     this.m_dfs.setPatternSeparator(v);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public char getPatternSeparator()
/* 149:    */   {
/* 150:391 */     return this.m_dfs.getPatternSeparator();
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void recompose(StylesheetRoot root)
/* 154:    */   {
/* 155:401 */     root.recomposeDecimalFormats(this);
/* 156:    */   }
/* 157:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.DecimalFormatProperties
 * JD-Core Version:    0.7.0.1
 */