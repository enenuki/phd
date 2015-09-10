/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ 
/*   6:    */ public class StylesheetComposed
/*   7:    */   extends Stylesheet
/*   8:    */ {
/*   9:    */   static final long serialVersionUID = -3444072247410233923L;
/*  10:    */   
/*  11:    */   public StylesheetComposed(Stylesheet parent)
/*  12:    */   {
/*  13: 56 */     super(parent);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public boolean isAggregatedType()
/*  17:    */   {
/*  18: 67 */     return true;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void recompose(Vector recomposableElements)
/*  22:    */     throws TransformerException
/*  23:    */   {
/*  24: 85 */     int n = getIncludeCountComposed();
/*  25: 87 */     for (int i = -1; i < n; i++)
/*  26:    */     {
/*  27: 89 */       Stylesheet included = getIncludeComposed(i);
/*  28:    */       
/*  29:    */ 
/*  30:    */ 
/*  31: 93 */       int s = included.getOutputCount();
/*  32: 94 */       for (int j = 0; j < s; j++) {
/*  33: 96 */         recomposableElements.addElement(included.getOutput(j));
/*  34:    */       }
/*  35:101 */       s = included.getAttributeSetCount();
/*  36:102 */       for (int j = 0; j < s; j++) {
/*  37:104 */         recomposableElements.addElement(included.getAttributeSet(j));
/*  38:    */       }
/*  39:109 */       s = included.getDecimalFormatCount();
/*  40:110 */       for (int j = 0; j < s; j++) {
/*  41:112 */         recomposableElements.addElement(included.getDecimalFormat(j));
/*  42:    */       }
/*  43:117 */       s = included.getKeyCount();
/*  44:118 */       for (int j = 0; j < s; j++) {
/*  45:120 */         recomposableElements.addElement(included.getKey(j));
/*  46:    */       }
/*  47:125 */       s = included.getNamespaceAliasCount();
/*  48:126 */       for (int j = 0; j < s; j++) {
/*  49:128 */         recomposableElements.addElement(included.getNamespaceAlias(j));
/*  50:    */       }
/*  51:133 */       s = included.getTemplateCount();
/*  52:134 */       for (int j = 0; j < s; j++) {
/*  53:136 */         recomposableElements.addElement(included.getTemplate(j));
/*  54:    */       }
/*  55:141 */       s = included.getVariableOrParamCount();
/*  56:142 */       for (int j = 0; j < s; j++) {
/*  57:144 */         recomposableElements.addElement(included.getVariableOrParam(j));
/*  58:    */       }
/*  59:149 */       s = included.getStripSpaceCount();
/*  60:150 */       for (int j = 0; j < s; j++) {
/*  61:152 */         recomposableElements.addElement(included.getStripSpace(j));
/*  62:    */       }
/*  63:155 */       s = included.getPreserveSpaceCount();
/*  64:156 */       for (int j = 0; j < s; j++) {
/*  65:158 */         recomposableElements.addElement(included.getPreserveSpace(j));
/*  66:    */       }
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:165 */   private int m_importNumber = -1;
/*  71:    */   private int m_importCountComposed;
/*  72:    */   private int m_endImportCountComposed;
/*  73:    */   private transient Vector m_includesComposed;
/*  74:    */   
/*  75:    */   void recomposeImports()
/*  76:    */   {
/*  77:185 */     this.m_importNumber = getStylesheetRoot().getImportNumber(this);
/*  78:    */     
/*  79:187 */     StylesheetRoot root = getStylesheetRoot();
/*  80:188 */     int globalImportCount = root.getGlobalImportCount();
/*  81:    */     
/*  82:190 */     this.m_importCountComposed = (globalImportCount - this.m_importNumber - 1);
/*  83:    */     
/*  84:    */ 
/*  85:193 */     int count = getImportCount();
/*  86:194 */     if (count > 0)
/*  87:    */     {
/*  88:196 */       this.m_endImportCountComposed += count;
/*  89:197 */       while (count > 0) {
/*  90:198 */         this.m_endImportCountComposed += getImport(--count).getEndImportCountComposed();
/*  91:    */       }
/*  92:    */     }
/*  93:203 */     count = getIncludeCountComposed();
/*  94:    */     int imports;
/*  95:204 */     for (; count > 0; imports > 0)
/*  96:    */     {
/*  97:206 */       imports = getIncludeComposed(--count).getImportCount();
/*  98:207 */       this.m_endImportCountComposed += imports;
/*  99:208 */       continue;
/* 100:209 */       this.m_endImportCountComposed += getIncludeComposed(count).getImport(--imports).getEndImportCountComposed();
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public StylesheetComposed getImportComposed(int i)
/* 105:    */     throws ArrayIndexOutOfBoundsException
/* 106:    */   {
/* 107:228 */     StylesheetRoot root = getStylesheetRoot();
/* 108:    */     
/* 109:    */ 
/* 110:    */ 
/* 111:    */ 
/* 112:    */ 
/* 113:234 */     return root.getGlobalImport(1 + this.m_importNumber + i);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public int getImportCountComposed()
/* 117:    */   {
/* 118:246 */     return this.m_importCountComposed;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public int getEndImportCountComposed()
/* 122:    */   {
/* 123:256 */     return this.m_endImportCountComposed;
/* 124:    */   }
/* 125:    */   
/* 126:    */   void recomposeIncludes(Stylesheet including)
/* 127:    */   {
/* 128:275 */     int n = including.getIncludeCount();
/* 129:277 */     if (n > 0)
/* 130:    */     {
/* 131:279 */       if (null == this.m_includesComposed) {
/* 132:280 */         this.m_includesComposed = new Vector();
/* 133:    */       }
/* 134:282 */       for (int i = 0; i < n; i++)
/* 135:    */       {
/* 136:284 */         Stylesheet included = including.getInclude(i);
/* 137:285 */         this.m_includesComposed.addElement(included);
/* 138:286 */         recomposeIncludes(included);
/* 139:    */       }
/* 140:    */     }
/* 141:    */   }
/* 142:    */   
/* 143:    */   public Stylesheet getIncludeComposed(int i)
/* 144:    */     throws ArrayIndexOutOfBoundsException
/* 145:    */   {
/* 146:305 */     if (-1 == i) {
/* 147:306 */       return this;
/* 148:    */     }
/* 149:308 */     if (null == this.m_includesComposed) {
/* 150:309 */       throw new ArrayIndexOutOfBoundsException();
/* 151:    */     }
/* 152:311 */     return (Stylesheet)this.m_includesComposed.elementAt(i);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public int getIncludeCountComposed()
/* 156:    */   {
/* 157:322 */     return null != this.m_includesComposed ? this.m_includesComposed.size() : 0;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void recomposeTemplates(boolean flushFirst)
/* 161:    */     throws TransformerException
/* 162:    */   {}
/* 163:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.StylesheetComposed
 * JD-Core Version:    0.7.0.1
 */