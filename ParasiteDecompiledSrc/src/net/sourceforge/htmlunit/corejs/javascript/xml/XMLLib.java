/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.xml;
/*   2:    */ 
/*   3:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   4:    */ import net.sourceforge.htmlunit.corejs.javascript.Ref;
/*   5:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptRuntime;
/*   6:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*   7:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;
/*   8:    */ 
/*   9:    */ public abstract class XMLLib
/*  10:    */ {
/*  11: 45 */   private static final Object XML_LIB_KEY = new Object();
/*  12:    */   
/*  13:    */   public static abstract class Factory
/*  14:    */   {
/*  15:    */     public static Factory create(String className)
/*  16:    */     {
/*  17: 58 */       new Factory()
/*  18:    */       {
/*  19:    */         public String getImplementationClassName()
/*  20:    */         {
/*  21: 61 */           return this.val$className;
/*  22:    */         }
/*  23:    */       };
/*  24:    */     }
/*  25:    */     
/*  26:    */     public abstract String getImplementationClassName();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static XMLLib extractFromScopeOrNull(Scriptable scope)
/*  30:    */   {
/*  31: 71 */     ScriptableObject so = ScriptRuntime.getLibraryScopeOrNull(scope);
/*  32: 72 */     if (so == null) {
/*  33: 74 */       return null;
/*  34:    */     }
/*  35: 79 */     ScriptableObject.getProperty(so, "XML");
/*  36:    */     
/*  37: 81 */     return (XMLLib)so.getAssociatedValue(XML_LIB_KEY);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static XMLLib extractFromScope(Scriptable scope)
/*  41:    */   {
/*  42: 86 */     XMLLib lib = extractFromScopeOrNull(scope);
/*  43: 87 */     if (lib != null) {
/*  44: 88 */       return lib;
/*  45:    */     }
/*  46: 90 */     String msg = ScriptRuntime.getMessage0("msg.XML.not.available");
/*  47: 91 */     throw Context.reportRuntimeError(msg);
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected final XMLLib bindToScope(Scriptable scope)
/*  51:    */   {
/*  52: 96 */     ScriptableObject so = ScriptRuntime.getLibraryScopeOrNull(scope);
/*  53: 97 */     if (so == null) {
/*  54: 99 */       throw new IllegalStateException();
/*  55:    */     }
/*  56:101 */     return (XMLLib)so.associateValue(XML_LIB_KEY, this);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public abstract boolean isXMLName(Context paramContext, Object paramObject);
/*  60:    */   
/*  61:    */   public abstract Ref nameRef(Context paramContext, Object paramObject, Scriptable paramScriptable, int paramInt);
/*  62:    */   
/*  63:    */   public abstract Ref nameRef(Context paramContext, Object paramObject1, Object paramObject2, Scriptable paramScriptable, int paramInt);
/*  64:    */   
/*  65:    */   public abstract String escapeAttributeValue(Object paramObject);
/*  66:    */   
/*  67:    */   public abstract String escapeTextValue(Object paramObject);
/*  68:    */   
/*  69:    */   public abstract Object toDefaultXmlNamespace(Context paramContext, Object paramObject);
/*  70:    */   
/*  71:    */   public void setIgnoreComments(boolean b)
/*  72:    */   {
/*  73:135 */     throw new UnsupportedOperationException();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setIgnoreWhitespace(boolean b)
/*  77:    */   {
/*  78:139 */     throw new UnsupportedOperationException();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setIgnoreProcessingInstructions(boolean b)
/*  82:    */   {
/*  83:143 */     throw new UnsupportedOperationException();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setPrettyPrinting(boolean b)
/*  87:    */   {
/*  88:147 */     throw new UnsupportedOperationException();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setPrettyIndent(int i)
/*  92:    */   {
/*  93:151 */     throw new UnsupportedOperationException();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public boolean isIgnoreComments()
/*  97:    */   {
/*  98:155 */     throw new UnsupportedOperationException();
/*  99:    */   }
/* 100:    */   
/* 101:    */   public boolean isIgnoreProcessingInstructions()
/* 102:    */   {
/* 103:159 */     throw new UnsupportedOperationException();
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean isIgnoreWhitespace()
/* 107:    */   {
/* 108:163 */     throw new UnsupportedOperationException();
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean isPrettyPrinting()
/* 112:    */   {
/* 113:167 */     throw new UnsupportedOperationException();
/* 114:    */   }
/* 115:    */   
/* 116:    */   public int getPrettyIndent()
/* 117:    */   {
/* 118:171 */     throw new UnsupportedOperationException();
/* 119:    */   }
/* 120:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.xml.XMLLib
 * JD-Core Version:    0.7.0.1
 */