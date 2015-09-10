/*   1:    */ package org.apache.xalan.xsltc.runtime;
/*   2:    */ 
/*   3:    */ import org.apache.xml.serializer.EmptySerializer;
/*   4:    */ import org.xml.sax.SAXException;
/*   5:    */ 
/*   6:    */ public final class StringValueHandler
/*   7:    */   extends EmptySerializer
/*   8:    */ {
/*   9: 35 */   private StringBuffer _buffer = new StringBuffer();
/*  10: 36 */   private String _str = null;
/*  11:    */   private static final String EMPTY_STR = "";
/*  12: 38 */   private boolean m_escaping = false;
/*  13: 39 */   private int _nestedLevel = 0;
/*  14:    */   
/*  15:    */   public void characters(char[] ch, int off, int len)
/*  16:    */     throws SAXException
/*  17:    */   {
/*  18: 44 */     if (this._nestedLevel > 0) {
/*  19: 45 */       return;
/*  20:    */     }
/*  21: 47 */     if (this._str != null)
/*  22:    */     {
/*  23: 48 */       this._buffer.append(this._str);
/*  24: 49 */       this._str = null;
/*  25:    */     }
/*  26: 51 */     this._buffer.append(ch, off, len);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public String getValue()
/*  30:    */   {
/*  31: 55 */     if (this._buffer.length() != 0)
/*  32:    */     {
/*  33: 56 */       String result = this._buffer.toString();
/*  34: 57 */       this._buffer.setLength(0);
/*  35: 58 */       return result;
/*  36:    */     }
/*  37: 61 */     String result = this._str;
/*  38: 62 */     this._str = null;
/*  39: 63 */     return result != null ? result : "";
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void characters(String characters)
/*  43:    */     throws SAXException
/*  44:    */   {
/*  45: 68 */     if (this._nestedLevel > 0) {
/*  46: 69 */       return;
/*  47:    */     }
/*  48: 71 */     if ((this._str == null) && (this._buffer.length() == 0))
/*  49:    */     {
/*  50: 72 */       this._str = characters;
/*  51:    */     }
/*  52:    */     else
/*  53:    */     {
/*  54: 75 */       if (this._str != null)
/*  55:    */       {
/*  56: 76 */         this._buffer.append(this._str);
/*  57: 77 */         this._str = null;
/*  58:    */       }
/*  59: 80 */       this._buffer.append(characters);
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void startElement(String qname)
/*  64:    */     throws SAXException
/*  65:    */   {
/*  66: 85 */     this._nestedLevel += 1;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void endElement(String qname)
/*  70:    */     throws SAXException
/*  71:    */   {
/*  72: 89 */     this._nestedLevel -= 1;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean setEscaping(boolean bool)
/*  76:    */   {
/*  77: 95 */     boolean oldEscaping = this.m_escaping;
/*  78: 96 */     this.m_escaping = bool;
/*  79:    */     
/*  80: 98 */     return bool;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String getValueOfPI()
/*  84:    */   {
/*  85:106 */     String value = getValue();
/*  86:108 */     if (value.indexOf("?>") > 0)
/*  87:    */     {
/*  88:109 */       int n = value.length();
/*  89:110 */       StringBuffer valueOfPI = new StringBuffer();
/*  90:112 */       for (int i = 0; i < n;)
/*  91:    */       {
/*  92:113 */         char ch = value.charAt(i++);
/*  93:114 */         if ((ch == '?') && (value.charAt(i) == '>'))
/*  94:    */         {
/*  95:115 */           valueOfPI.append("? >");i++;
/*  96:    */         }
/*  97:    */         else
/*  98:    */         {
/*  99:118 */           valueOfPI.append(ch);
/* 100:    */         }
/* 101:    */       }
/* 102:121 */       return valueOfPI.toString();
/* 103:    */     }
/* 104:123 */     return value;
/* 105:    */   }
/* 106:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.runtime.StringValueHandler
 * JD-Core Version:    0.7.0.1
 */