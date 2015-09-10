/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.tools.shell;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import java.util.Properties;
/*   5:    */ import java.util.Set;
/*   6:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptRuntime;
/*   7:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*   8:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;
/*   9:    */ 
/*  10:    */ public class Environment
/*  11:    */   extends ScriptableObject
/*  12:    */ {
/*  13:    */   static final long serialVersionUID = -430727378460177065L;
/*  14: 64 */   private Environment thePrototypeInstance = null;
/*  15:    */   
/*  16:    */   public static void defineClass(ScriptableObject scope)
/*  17:    */   {
/*  18:    */     try
/*  19:    */     {
/*  20: 68 */       ScriptableObject.defineClass(scope, Environment.class);
/*  21:    */     }
/*  22:    */     catch (Exception e)
/*  23:    */     {
/*  24: 70 */       throw new Error(e.getMessage());
/*  25:    */     }
/*  26:    */   }
/*  27:    */   
/*  28:    */   public String getClassName()
/*  29:    */   {
/*  30: 76 */     return "Environment";
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Environment()
/*  34:    */   {
/*  35: 80 */     if (this.thePrototypeInstance == null) {
/*  36: 81 */       this.thePrototypeInstance = this;
/*  37:    */     }
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Environment(ScriptableObject scope)
/*  41:    */   {
/*  42: 85 */     setParentScope(scope);
/*  43: 86 */     Object ctor = ScriptRuntime.getTopLevelProp(scope, "Environment");
/*  44: 87 */     if ((ctor != null) && ((ctor instanceof Scriptable)))
/*  45:    */     {
/*  46: 88 */       Scriptable s = (Scriptable)ctor;
/*  47: 89 */       setPrototype((Scriptable)s.get("prototype", s));
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean has(String name, Scriptable start)
/*  52:    */   {
/*  53: 95 */     if (this == this.thePrototypeInstance) {
/*  54: 96 */       return super.has(name, start);
/*  55:    */     }
/*  56: 98 */     return System.getProperty(name) != null;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Object get(String name, Scriptable start)
/*  60:    */   {
/*  61:103 */     if (this == this.thePrototypeInstance) {
/*  62:104 */       return super.get(name, start);
/*  63:    */     }
/*  64:106 */     String result = System.getProperty(name);
/*  65:107 */     if (result != null) {
/*  66:108 */       return ScriptRuntime.toObject(getParentScope(), result);
/*  67:    */     }
/*  68:110 */     return Scriptable.NOT_FOUND;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void put(String name, Scriptable start, Object value)
/*  72:    */   {
/*  73:115 */     if (this == this.thePrototypeInstance) {
/*  74:116 */       super.put(name, start, value);
/*  75:    */     } else {
/*  76:118 */       System.getProperties().put(name, ScriptRuntime.toString(value));
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   private Object[] collectIds()
/*  81:    */   {
/*  82:122 */     Map<Object, Object> props = System.getProperties();
/*  83:123 */     return props.keySet().toArray();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Object[] getIds()
/*  87:    */   {
/*  88:128 */     if (this == this.thePrototypeInstance) {
/*  89:129 */       return super.getIds();
/*  90:    */     }
/*  91:130 */     return collectIds();
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Object[] getAllIds()
/*  95:    */   {
/*  96:135 */     if (this == this.thePrototypeInstance) {
/*  97:136 */       return super.getAllIds();
/*  98:    */     }
/*  99:137 */     return collectIds();
/* 100:    */   }
/* 101:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.shell.Environment
 * JD-Core Version:    0.7.0.1
 */