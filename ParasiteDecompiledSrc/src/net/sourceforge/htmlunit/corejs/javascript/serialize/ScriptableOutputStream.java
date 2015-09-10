/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.serialize;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectOutputStream;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.StringTokenizer;
/*  10:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  11:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;
/*  12:    */ import net.sourceforge.htmlunit.corejs.javascript.UniqueTag;
/*  13:    */ 
/*  14:    */ public class ScriptableOutputStream
/*  15:    */   extends ObjectOutputStream
/*  16:    */ {
/*  17:    */   private Scriptable scope;
/*  18:    */   private Map<Object, String> table;
/*  19:    */   
/*  20:    */   public ScriptableOutputStream(OutputStream out, Scriptable scope)
/*  21:    */     throws IOException
/*  22:    */   {
/*  23: 80 */     super(out);
/*  24: 81 */     this.scope = scope;
/*  25: 82 */     this.table = new HashMap();
/*  26: 83 */     this.table.put(scope, "");
/*  27: 84 */     enableReplaceObject(true);
/*  28: 85 */     excludeStandardObjectNames();
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void excludeAllIds(Object[] ids)
/*  32:    */   {
/*  33: 89 */     for (Object id : ids) {
/*  34: 90 */       if (((id instanceof String)) && ((this.scope.get((String)id, this.scope) instanceof Scriptable))) {
/*  35: 93 */         addExcludedName((String)id);
/*  36:    */       }
/*  37:    */     }
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void addOptionalExcludedName(String name)
/*  41:    */   {
/*  42:109 */     Object obj = lookupQualifiedName(this.scope, name);
/*  43:110 */     if ((obj != null) && (obj != UniqueTag.NOT_FOUND))
/*  44:    */     {
/*  45:111 */       if (!(obj instanceof Scriptable)) {
/*  46:112 */         throw new IllegalArgumentException("Object for excluded name " + name + " is not a Scriptable, it is " + obj.getClass().getName());
/*  47:    */       }
/*  48:117 */       this.table.put(obj, name);
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void addExcludedName(String name)
/*  53:    */   {
/*  54:131 */     Object obj = lookupQualifiedName(this.scope, name);
/*  55:132 */     if (!(obj instanceof Scriptable)) {
/*  56:133 */       throw new IllegalArgumentException("Object for excluded name " + name + " not found.");
/*  57:    */     }
/*  58:136 */     this.table.put(obj, name);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public boolean hasExcludedName(String name)
/*  62:    */   {
/*  63:143 */     return this.table.get(name) != null;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void removeExcludedName(String name)
/*  67:    */   {
/*  68:150 */     this.table.remove(name);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void excludeStandardObjectNames()
/*  72:    */   {
/*  73:158 */     String[] names = { "Object", "Object.prototype", "Function", "Function.prototype", "String", "String.prototype", "Math", "Array", "Array.prototype", "Error", "Error.prototype", "Number", "Number.prototype", "Date", "Date.prototype", "RegExp", "RegExp.prototype", "Script", "Script.prototype", "Continuation", "Continuation.prototype" };
/*  74:170 */     for (int i = 0; i < names.length; i++) {
/*  75:171 */       addExcludedName(names[i]);
/*  76:    */     }
/*  77:174 */     String[] optionalNames = { "XML", "XML.prototype", "XMLList", "XMLList.prototype" };
/*  78:178 */     for (int i = 0; i < optionalNames.length; i++) {
/*  79:179 */       addOptionalExcludedName(optionalNames[i]);
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   static Object lookupQualifiedName(Scriptable scope, String qualifiedName)
/*  84:    */   {
/*  85:186 */     StringTokenizer st = new StringTokenizer(qualifiedName, ".");
/*  86:187 */     Object result = scope;
/*  87:188 */     while (st.hasMoreTokens())
/*  88:    */     {
/*  89:189 */       String s = st.nextToken();
/*  90:190 */       result = ScriptableObject.getProperty((Scriptable)result, s);
/*  91:191 */       if ((result == null) || (!(result instanceof Scriptable))) {
/*  92:    */         break;
/*  93:    */       }
/*  94:    */     }
/*  95:194 */     return result;
/*  96:    */   }
/*  97:    */   
/*  98:    */   static class PendingLookup
/*  99:    */     implements Serializable
/* 100:    */   {
/* 101:    */     static final long serialVersionUID = -2692990309789917727L;
/* 102:    */     private String name;
/* 103:    */     
/* 104:    */     PendingLookup(String name)
/* 105:    */     {
/* 106:201 */       this.name = name;
/* 107:    */     }
/* 108:    */     
/* 109:    */     String getName()
/* 110:    */     {
/* 111:203 */       return this.name;
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   protected Object replaceObject(Object obj)
/* 116:    */     throws IOException
/* 117:    */   {
/* 118:212 */     String name = (String)this.table.get(obj);
/* 119:213 */     if (name == null) {
/* 120:214 */       return obj;
/* 121:    */     }
/* 122:215 */     return new PendingLookup(name);
/* 123:    */   }
/* 124:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.serialize.ScriptableOutputStream
 * JD-Core Version:    0.7.0.1
 */