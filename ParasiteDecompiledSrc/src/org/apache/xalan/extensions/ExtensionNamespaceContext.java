/*   1:    */ package org.apache.xalan.extensions;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import javax.xml.namespace.NamespaceContext;
/*   5:    */ import org.apache.xalan.res.XSLMessages;
/*   6:    */ 
/*   7:    */ public class ExtensionNamespaceContext
/*   8:    */   implements NamespaceContext
/*   9:    */ {
/*  10:    */   public static final String EXSLT_PREFIX = "exslt";
/*  11:    */   public static final String EXSLT_URI = "http://exslt.org/common";
/*  12:    */   public static final String EXSLT_MATH_PREFIX = "math";
/*  13:    */   public static final String EXSLT_MATH_URI = "http://exslt.org/math";
/*  14:    */   public static final String EXSLT_SET_PREFIX = "set";
/*  15:    */   public static final String EXSLT_SET_URI = "http://exslt.org/sets";
/*  16:    */   public static final String EXSLT_STRING_PREFIX = "str";
/*  17:    */   public static final String EXSLT_STRING_URI = "http://exslt.org/strings";
/*  18:    */   public static final String EXSLT_DATETIME_PREFIX = "datetime";
/*  19:    */   public static final String EXSLT_DATETIME_URI = "http://exslt.org/dates-and-times";
/*  20:    */   public static final String EXSLT_DYNAMIC_PREFIX = "dyn";
/*  21:    */   public static final String EXSLT_DYNAMIC_URI = "http://exslt.org/dynamic";
/*  22:    */   public static final String JAVA_EXT_PREFIX = "java";
/*  23:    */   public static final String JAVA_EXT_URI = "http://xml.apache.org/xalan/java";
/*  24:    */   
/*  25:    */   public String getNamespaceURI(String prefix)
/*  26:    */   {
/*  27: 53 */     if (prefix == null) {
/*  28: 54 */       throw new IllegalArgumentException(XSLMessages.createMessage("ER_NAMESPACE_CONTEXT_NULL_PREFIX", null));
/*  29:    */     }
/*  30: 58 */     if (prefix.equals("")) {
/*  31: 59 */       return "";
/*  32:    */     }
/*  33: 60 */     if (prefix.equals("xml")) {
/*  34: 61 */       return "http://www.w3.org/XML/1998/namespace";
/*  35:    */     }
/*  36: 62 */     if (prefix.equals("xmlns")) {
/*  37: 63 */       return "http://www.w3.org/2000/xmlns/";
/*  38:    */     }
/*  39: 64 */     if (prefix.equals("exslt")) {
/*  40: 65 */       return "http://exslt.org/common";
/*  41:    */     }
/*  42: 66 */     if (prefix.equals("math")) {
/*  43: 67 */       return "http://exslt.org/math";
/*  44:    */     }
/*  45: 68 */     if (prefix.equals("set")) {
/*  46: 69 */       return "http://exslt.org/sets";
/*  47:    */     }
/*  48: 70 */     if (prefix.equals("str")) {
/*  49: 71 */       return "http://exslt.org/strings";
/*  50:    */     }
/*  51: 72 */     if (prefix.equals("datetime")) {
/*  52: 73 */       return "http://exslt.org/dates-and-times";
/*  53:    */     }
/*  54: 74 */     if (prefix.equals("dyn")) {
/*  55: 75 */       return "http://exslt.org/dynamic";
/*  56:    */     }
/*  57: 76 */     if (prefix.equals("java")) {
/*  58: 77 */       return "http://xml.apache.org/xalan/java";
/*  59:    */     }
/*  60: 79 */     return "";
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String getPrefix(String namespace)
/*  64:    */   {
/*  65: 87 */     if (namespace == null) {
/*  66: 88 */       throw new IllegalArgumentException(XSLMessages.createMessage("ER_NAMESPACE_CONTEXT_NULL_NAMESPACE", null));
/*  67:    */     }
/*  68: 92 */     if (namespace.equals("http://www.w3.org/XML/1998/namespace")) {
/*  69: 93 */       return "xml";
/*  70:    */     }
/*  71: 94 */     if (namespace.equals("http://www.w3.org/2000/xmlns/")) {
/*  72: 95 */       return "xmlns";
/*  73:    */     }
/*  74: 96 */     if (namespace.equals("http://exslt.org/common")) {
/*  75: 97 */       return "exslt";
/*  76:    */     }
/*  77: 98 */     if (namespace.equals("http://exslt.org/math")) {
/*  78: 99 */       return "math";
/*  79:    */     }
/*  80:100 */     if (namespace.equals("http://exslt.org/sets")) {
/*  81:101 */       return "set";
/*  82:    */     }
/*  83:102 */     if (namespace.equals("http://exslt.org/strings")) {
/*  84:103 */       return "str";
/*  85:    */     }
/*  86:104 */     if (namespace.equals("http://exslt.org/dates-and-times")) {
/*  87:105 */       return "datetime";
/*  88:    */     }
/*  89:106 */     if (namespace.equals("http://exslt.org/dynamic")) {
/*  90:107 */       return "dyn";
/*  91:    */     }
/*  92:108 */     if (namespace.equals("http://xml.apache.org/xalan/java")) {
/*  93:109 */       return "java";
/*  94:    */     }
/*  95:111 */     return null;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Iterator getPrefixes(String namespace)
/*  99:    */   {
/* 100:116 */     String result = getPrefix(namespace);
/* 101:    */     
/* 102:118 */     new Iterator()
/* 103:    */     {
/* 104:    */       private boolean isFirstIteration;
/* 105:    */       private final String val$result;
/* 106:    */       
/* 107:    */       public boolean hasNext()
/* 108:    */       {
/* 109:123 */         return this.isFirstIteration;
/* 110:    */       }
/* 111:    */       
/* 112:    */       public Object next()
/* 113:    */       {
/* 114:127 */         if (this.isFirstIteration)
/* 115:    */         {
/* 116:128 */           this.isFirstIteration = false;
/* 117:129 */           return this.val$result;
/* 118:    */         }
/* 119:132 */         return null;
/* 120:    */       }
/* 121:    */       
/* 122:    */       public void remove()
/* 123:    */       {
/* 124:136 */         throw new UnsupportedOperationException();
/* 125:    */       }
/* 126:    */     };
/* 127:    */   }
/* 128:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.extensions.ExtensionNamespaceContext
 * JD-Core Version:    0.7.0.1
 */