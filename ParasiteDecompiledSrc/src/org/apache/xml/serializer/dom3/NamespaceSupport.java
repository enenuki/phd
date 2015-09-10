/*   1:    */ package org.apache.xml.serializer.dom3;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ import java.util.NoSuchElementException;
/*   5:    */ 
/*   6:    */ public class NamespaceSupport
/*   7:    */ {
/*   8: 41 */   static final String PREFIX_XML = "xml".intern();
/*   9: 43 */   static final String PREFIX_XMLNS = "xmlns".intern();
/*  10: 49 */   public static final String XML_URI = "http://www.w3.org/XML/1998/namespace".intern();
/*  11: 56 */   public static final String XMLNS_URI = "http://www.w3.org/2000/xmlns/".intern();
/*  12: 71 */   protected String[] fNamespace = new String[32];
/*  13:    */   protected int fNamespaceSize;
/*  14: 87 */   protected int[] fContext = new int[8];
/*  15:    */   protected int fCurrentContext;
/*  16: 92 */   protected String[] fPrefixes = new String[16];
/*  17:    */   
/*  18:    */   public void reset()
/*  19:    */   {
/*  20:112 */     this.fNamespaceSize = 0;
/*  21:113 */     this.fCurrentContext = 0;
/*  22:114 */     this.fContext[this.fCurrentContext] = this.fNamespaceSize;
/*  23:    */     
/*  24:    */ 
/*  25:117 */     this.fNamespace[(this.fNamespaceSize++)] = PREFIX_XML;
/*  26:118 */     this.fNamespace[(this.fNamespaceSize++)] = XML_URI;
/*  27:    */     
/*  28:120 */     this.fNamespace[(this.fNamespaceSize++)] = PREFIX_XMLNS;
/*  29:121 */     this.fNamespace[(this.fNamespaceSize++)] = XMLNS_URI;
/*  30:122 */     this.fCurrentContext += 1;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void pushContext()
/*  34:    */   {
/*  35:133 */     if (this.fCurrentContext + 1 == this.fContext.length)
/*  36:    */     {
/*  37:134 */       int[] contextarray = new int[this.fContext.length * 2];
/*  38:135 */       System.arraycopy(this.fContext, 0, contextarray, 0, this.fContext.length);
/*  39:136 */       this.fContext = contextarray;
/*  40:    */     }
/*  41:140 */     this.fContext[(++this.fCurrentContext)] = this.fNamespaceSize;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void popContext()
/*  45:    */   {
/*  46:149 */     this.fNamespaceSize = this.fContext[(this.fCurrentContext--)];
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean declarePrefix(String prefix, String uri)
/*  50:    */   {
/*  51:157 */     if ((prefix == PREFIX_XML) || (prefix == PREFIX_XMLNS)) {
/*  52:158 */       return false;
/*  53:    */     }
/*  54:162 */     for (int i = this.fNamespaceSize; i > this.fContext[this.fCurrentContext]; i -= 2) {
/*  55:164 */       if (this.fNamespace[(i - 2)].equals(prefix))
/*  56:    */       {
/*  57:171 */         this.fNamespace[(i - 1)] = uri;
/*  58:172 */         return true;
/*  59:    */       }
/*  60:    */     }
/*  61:177 */     if (this.fNamespaceSize == this.fNamespace.length)
/*  62:    */     {
/*  63:178 */       String[] namespacearray = new String[this.fNamespaceSize * 2];
/*  64:179 */       System.arraycopy(this.fNamespace, 0, namespacearray, 0, this.fNamespaceSize);
/*  65:180 */       this.fNamespace = namespacearray;
/*  66:    */     }
/*  67:184 */     this.fNamespace[(this.fNamespaceSize++)] = prefix;
/*  68:185 */     this.fNamespace[(this.fNamespaceSize++)] = uri;
/*  69:    */     
/*  70:187 */     return true;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String getURI(String prefix)
/*  74:    */   {
/*  75:197 */     for (int i = this.fNamespaceSize; i > 0; i -= 2) {
/*  76:199 */       if (this.fNamespace[(i - 2)].equals(prefix)) {
/*  77:200 */         return this.fNamespace[(i - 1)];
/*  78:    */       }
/*  79:    */     }
/*  80:205 */     return null;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String getPrefix(String uri)
/*  84:    */   {
/*  85:216 */     for (int i = this.fNamespaceSize; i > 0; i -= 2) {
/*  86:218 */       if (this.fNamespace[(i - 1)].equals(uri)) {
/*  87:220 */         if (getURI(this.fNamespace[(i - 2)]).equals(uri)) {
/*  88:221 */           return this.fNamespace[(i - 2)];
/*  89:    */         }
/*  90:    */       }
/*  91:    */     }
/*  92:226 */     return null;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int getDeclaredPrefixCount()
/*  96:    */   {
/*  97:235 */     return (this.fNamespaceSize - this.fContext[this.fCurrentContext]) / 2;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getDeclaredPrefixAt(int index)
/* 101:    */   {
/* 102:242 */     return this.fNamespace[(this.fContext[this.fCurrentContext] + index * 2)];
/* 103:    */   }
/* 104:    */   
/* 105:    */   public Enumeration getAllPrefixes()
/* 106:    */   {
/* 107:249 */     int count = 0;
/* 108:250 */     if (this.fPrefixes.length < this.fNamespace.length / 2)
/* 109:    */     {
/* 110:252 */       String[] prefixes = new String[this.fNamespaceSize];
/* 111:253 */       this.fPrefixes = prefixes;
/* 112:    */     }
/* 113:255 */     String prefix = null;
/* 114:256 */     boolean unique = true;
/* 115:257 */     for (int i = 2; i < this.fNamespaceSize - 2; i += 2)
/* 116:    */     {
/* 117:258 */       prefix = this.fNamespace[(i + 2)];
/* 118:259 */       for (int k = 0; k < count; k++) {
/* 119:260 */         if (this.fPrefixes[k] == prefix)
/* 120:    */         {
/* 121:261 */           unique = false;
/* 122:262 */           break;
/* 123:    */         }
/* 124:    */       }
/* 125:265 */       if (unique) {
/* 126:266 */         this.fPrefixes[(count++)] = prefix;
/* 127:    */       }
/* 128:268 */       unique = true;
/* 129:    */     }
/* 130:270 */     return new Prefixes(this.fPrefixes, count);
/* 131:    */   }
/* 132:    */   
/* 133:    */   protected final class Prefixes
/* 134:    */     implements Enumeration
/* 135:    */   {
/* 136:    */     private String[] prefixes;
/* 137:275 */     private int counter = 0;
/* 138:276 */     private int size = 0;
/* 139:    */     
/* 140:    */     public Prefixes(String[] prefixes, int size)
/* 141:    */     {
/* 142:282 */       this.prefixes = prefixes;
/* 143:283 */       this.size = size;
/* 144:    */     }
/* 145:    */     
/* 146:    */     public boolean hasMoreElements()
/* 147:    */     {
/* 148:290 */       return this.counter < this.size;
/* 149:    */     }
/* 150:    */     
/* 151:    */     public Object nextElement()
/* 152:    */     {
/* 153:297 */       if (this.counter < this.size) {
/* 154:298 */         return NamespaceSupport.this.fPrefixes[(this.counter++)];
/* 155:    */       }
/* 156:300 */       throw new NoSuchElementException("Illegal access to Namespace prefixes enumeration.");
/* 157:    */     }
/* 158:    */     
/* 159:    */     public String toString()
/* 160:    */     {
/* 161:304 */       StringBuffer buf = new StringBuffer();
/* 162:305 */       for (int i = 0; i < this.size; i++)
/* 163:    */       {
/* 164:306 */         buf.append(this.prefixes[i]);
/* 165:307 */         buf.append(" ");
/* 166:    */       }
/* 167:310 */       return buf.toString();
/* 168:    */     }
/* 169:    */   }
/* 170:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.dom3.NamespaceSupport
 * JD-Core Version:    0.7.0.1
 */