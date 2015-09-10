/*   1:    */ package org.apache.http.message;
/*   2:    */ 
/*   3:    */ import org.apache.http.HeaderElement;
/*   4:    */ import org.apache.http.NameValuePair;
/*   5:    */ import org.apache.http.util.CharArrayBuffer;
/*   6:    */ import org.apache.http.util.LangUtils;
/*   7:    */ 
/*   8:    */ public class BasicHeaderElement
/*   9:    */   implements HeaderElement, Cloneable
/*  10:    */ {
/*  11:    */   private final String name;
/*  12:    */   private final String value;
/*  13:    */   private final NameValuePair[] parameters;
/*  14:    */   
/*  15:    */   public BasicHeaderElement(String name, String value, NameValuePair[] parameters)
/*  16:    */   {
/*  17: 59 */     if (name == null) {
/*  18: 60 */       throw new IllegalArgumentException("Name may not be null");
/*  19:    */     }
/*  20: 62 */     this.name = name;
/*  21: 63 */     this.value = value;
/*  22: 64 */     if (parameters != null) {
/*  23: 65 */       this.parameters = parameters;
/*  24:    */     } else {
/*  25: 67 */       this.parameters = new NameValuePair[0];
/*  26:    */     }
/*  27:    */   }
/*  28:    */   
/*  29:    */   public BasicHeaderElement(String name, String value)
/*  30:    */   {
/*  31: 78 */     this(name, value, null);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String getName()
/*  35:    */   {
/*  36: 82 */     return this.name;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getValue()
/*  40:    */   {
/*  41: 86 */     return this.value;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public NameValuePair[] getParameters()
/*  45:    */   {
/*  46: 90 */     return (NameValuePair[])this.parameters.clone();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int getParameterCount()
/*  50:    */   {
/*  51: 94 */     return this.parameters.length;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public NameValuePair getParameter(int index)
/*  55:    */   {
/*  56: 99 */     return this.parameters[index];
/*  57:    */   }
/*  58:    */   
/*  59:    */   public NameValuePair getParameterByName(String name)
/*  60:    */   {
/*  61:103 */     if (name == null) {
/*  62:104 */       throw new IllegalArgumentException("Name may not be null");
/*  63:    */     }
/*  64:106 */     NameValuePair found = null;
/*  65:107 */     for (int i = 0; i < this.parameters.length; i++)
/*  66:    */     {
/*  67:108 */       NameValuePair current = this.parameters[i];
/*  68:109 */       if (current.getName().equalsIgnoreCase(name))
/*  69:    */       {
/*  70:110 */         found = current;
/*  71:111 */         break;
/*  72:    */       }
/*  73:    */     }
/*  74:114 */     return found;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean equals(Object object)
/*  78:    */   {
/*  79:118 */     if (this == object) {
/*  80:118 */       return true;
/*  81:    */     }
/*  82:119 */     if ((object instanceof HeaderElement))
/*  83:    */     {
/*  84:120 */       BasicHeaderElement that = (BasicHeaderElement)object;
/*  85:121 */       return (this.name.equals(that.name)) && (LangUtils.equals(this.value, that.value)) && (LangUtils.equals(this.parameters, that.parameters));
/*  86:    */     }
/*  87:125 */     return false;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int hashCode()
/*  91:    */   {
/*  92:130 */     int hash = 17;
/*  93:131 */     hash = LangUtils.hashCode(hash, this.name);
/*  94:132 */     hash = LangUtils.hashCode(hash, this.value);
/*  95:133 */     for (int i = 0; i < this.parameters.length; i++) {
/*  96:134 */       hash = LangUtils.hashCode(hash, this.parameters[i]);
/*  97:    */     }
/*  98:136 */     return hash;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String toString()
/* 102:    */   {
/* 103:140 */     CharArrayBuffer buffer = new CharArrayBuffer(64);
/* 104:141 */     buffer.append(this.name);
/* 105:142 */     if (this.value != null)
/* 106:    */     {
/* 107:143 */       buffer.append("=");
/* 108:144 */       buffer.append(this.value);
/* 109:    */     }
/* 110:146 */     for (int i = 0; i < this.parameters.length; i++)
/* 111:    */     {
/* 112:147 */       buffer.append("; ");
/* 113:148 */       buffer.append(this.parameters[i]);
/* 114:    */     }
/* 115:150 */     return buffer.toString();
/* 116:    */   }
/* 117:    */   
/* 118:    */   public Object clone()
/* 119:    */     throws CloneNotSupportedException
/* 120:    */   {
/* 121:156 */     return super.clone();
/* 122:    */   }
/* 123:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.message.BasicHeaderElement
 * JD-Core Version:    0.7.0.1
 */