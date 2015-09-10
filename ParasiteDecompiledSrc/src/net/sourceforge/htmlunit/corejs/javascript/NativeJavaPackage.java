/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.util.HashSet;
/*   4:    */ import java.util.Set;
/*   5:    */ 
/*   6:    */ public class NativeJavaPackage
/*   7:    */   extends ScriptableObject
/*   8:    */ {
/*   9:    */   static final long serialVersionUID = 7445054382212031523L;
/*  10:    */   private String packageName;
/*  11:    */   private ClassLoader classLoader;
/*  12:    */   
/*  13:    */   NativeJavaPackage(boolean internalUsage, String packageName, ClassLoader classLoader)
/*  14:    */   {
/*  15: 66 */     this.packageName = packageName;
/*  16: 67 */     this.classLoader = classLoader;
/*  17:    */   }
/*  18:    */   
/*  19:    */   /**
/*  20:    */    * @deprecated
/*  21:    */    */
/*  22:    */   public NativeJavaPackage(String packageName, ClassLoader classLoader)
/*  23:    */   {
/*  24: 75 */     this(false, packageName, classLoader);
/*  25:    */   }
/*  26:    */   
/*  27:    */   /**
/*  28:    */    * @deprecated
/*  29:    */    */
/*  30:    */   public NativeJavaPackage(String packageName)
/*  31:    */   {
/*  32: 83 */     this(false, packageName, Context.getCurrentContext().getApplicationClassLoader());
/*  33:    */   }
/*  34:    */   
/*  35:    */   public String getClassName()
/*  36:    */   {
/*  37: 89 */     return "JavaPackage";
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean has(String id, Scriptable start)
/*  41:    */   {
/*  42: 94 */     return true;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean has(int index, Scriptable start)
/*  46:    */   {
/*  47: 99 */     return false;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void put(String id, Scriptable start, Object value) {}
/*  51:    */   
/*  52:    */   public void put(int index, Scriptable start, Object value)
/*  53:    */   {
/*  54:109 */     throw Context.reportRuntimeError0("msg.pkg.int");
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Object get(String id, Scriptable start)
/*  58:    */   {
/*  59:114 */     return getPkgProperty(id, start, true);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Object get(int index, Scriptable start)
/*  63:    */   {
/*  64:119 */     return NOT_FOUND;
/*  65:    */   }
/*  66:    */   
/*  67:    */   NativeJavaPackage forcePackage(String name, Scriptable scope)
/*  68:    */   {
/*  69:126 */     Object cached = super.get(name, this);
/*  70:127 */     if ((cached != null) && ((cached instanceof NativeJavaPackage))) {
/*  71:128 */       return (NativeJavaPackage)cached;
/*  72:    */     }
/*  73:130 */     String newPackage = this.packageName + "." + name;
/*  74:    */     
/*  75:    */ 
/*  76:133 */     NativeJavaPackage pkg = new NativeJavaPackage(true, newPackage, this.classLoader);
/*  77:134 */     ScriptRuntime.setObjectProtoAndParent(pkg, scope);
/*  78:135 */     super.put(name, this, pkg);
/*  79:136 */     return pkg;
/*  80:    */   }
/*  81:    */   
/*  82:    */   synchronized Object getPkgProperty(String name, Scriptable start, boolean createPkg)
/*  83:    */   {
/*  84:143 */     Object cached = super.get(name, start);
/*  85:144 */     if (cached != NOT_FOUND) {
/*  86:145 */       return cached;
/*  87:    */     }
/*  88:146 */     if ((this.negativeCache != null) && (this.negativeCache.contains(name))) {
/*  89:148 */       return null;
/*  90:    */     }
/*  91:151 */     String className = this.packageName + '.' + name;
/*  92:    */     
/*  93:153 */     Context cx = Context.getContext();
/*  94:154 */     ClassShutter shutter = cx.getClassShutter();
/*  95:155 */     Scriptable newValue = null;
/*  96:156 */     if ((shutter == null) || (shutter.visibleToScripts(className)))
/*  97:    */     {
/*  98:157 */       Class<?> cl = null;
/*  99:158 */       if (this.classLoader != null) {
/* 100:159 */         cl = Kit.classOrNull(this.classLoader, className);
/* 101:    */       } else {
/* 102:161 */         cl = Kit.classOrNull(className);
/* 103:    */       }
/* 104:163 */       if (cl != null)
/* 105:    */       {
/* 106:164 */         WrapFactory wrapFactory = cx.getWrapFactory();
/* 107:165 */         newValue = wrapFactory.wrapJavaClass(cx, getTopLevelScope(this), cl);
/* 108:166 */         newValue.setPrototype(getPrototype());
/* 109:    */       }
/* 110:    */     }
/* 111:169 */     if (newValue == null) {
/* 112:170 */       if (createPkg)
/* 113:    */       {
/* 114:172 */         NativeJavaPackage pkg = new NativeJavaPackage(true, className, this.classLoader);
/* 115:173 */         ScriptRuntime.setObjectProtoAndParent(pkg, getParentScope());
/* 116:174 */         newValue = pkg;
/* 117:    */       }
/* 118:    */       else
/* 119:    */       {
/* 120:177 */         if (this.negativeCache == null) {
/* 121:178 */           this.negativeCache = new HashSet();
/* 122:    */         }
/* 123:179 */         this.negativeCache.add(name);
/* 124:    */       }
/* 125:    */     }
/* 126:182 */     if (newValue != null) {
/* 127:185 */       super.put(name, start, newValue);
/* 128:    */     }
/* 129:187 */     return newValue;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public Object getDefaultValue(Class<?> ignored)
/* 133:    */   {
/* 134:192 */     return toString();
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String toString()
/* 138:    */   {
/* 139:197 */     return "[JavaPackage " + this.packageName + "]";
/* 140:    */   }
/* 141:    */   
/* 142:    */   public boolean equals(Object obj)
/* 143:    */   {
/* 144:202 */     if ((obj instanceof NativeJavaPackage))
/* 145:    */     {
/* 146:203 */       NativeJavaPackage njp = (NativeJavaPackage)obj;
/* 147:204 */       return (this.packageName.equals(njp.packageName)) && (this.classLoader == njp.classLoader);
/* 148:    */     }
/* 149:207 */     return false;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public int hashCode()
/* 153:    */   {
/* 154:212 */     return this.packageName.hashCode() ^ (this.classLoader == null ? 0 : this.classLoader.hashCode());
/* 155:    */   }
/* 156:    */   
/* 157:218 */   private Set<String> negativeCache = null;
/* 158:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.NativeJavaPackage
 * JD-Core Version:    0.7.0.1
 */