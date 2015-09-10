/*   1:    */ package javassist;
/*   2:    */ 
/*   3:    */ import java.io.InputStream;
/*   4:    */ import java.lang.reflect.InvocationTargetException;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import java.security.ProtectionDomain;
/*   7:    */ import java.util.Hashtable;
/*   8:    */ import java.util.Vector;
/*   9:    */ 
/*  10:    */ public class Loader
/*  11:    */   extends ClassLoader
/*  12:    */ {
/*  13:    */   private Hashtable notDefinedHere;
/*  14:    */   private Vector notDefinedPackages;
/*  15:    */   private ClassPool source;
/*  16:    */   private Translator translator;
/*  17:    */   private ProtectionDomain domain;
/*  18:153 */   public boolean doDelegation = true;
/*  19:    */   
/*  20:    */   public Loader()
/*  21:    */   {
/*  22:159 */     this(null);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public Loader(ClassPool cp)
/*  26:    */   {
/*  27:168 */     init(cp);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Loader(ClassLoader parent, ClassPool cp)
/*  31:    */   {
/*  32:179 */     super(parent);
/*  33:180 */     init(cp);
/*  34:    */   }
/*  35:    */   
/*  36:    */   private void init(ClassPool cp)
/*  37:    */   {
/*  38:184 */     this.notDefinedHere = new Hashtable();
/*  39:185 */     this.notDefinedPackages = new Vector();
/*  40:186 */     this.source = cp;
/*  41:187 */     this.translator = null;
/*  42:188 */     this.domain = null;
/*  43:189 */     delegateLoadingOf("javassist.Loader");
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void delegateLoadingOf(String classname)
/*  47:    */   {
/*  48:201 */     if (classname.endsWith(".")) {
/*  49:202 */       this.notDefinedPackages.addElement(classname);
/*  50:    */     } else {
/*  51:204 */       this.notDefinedHere.put(classname, this);
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setDomain(ProtectionDomain d)
/*  56:    */   {
/*  57:214 */     this.domain = d;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setClassPool(ClassPool cp)
/*  61:    */   {
/*  62:221 */     this.source = cp;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void addTranslator(ClassPool cp, Translator t)
/*  66:    */     throws NotFoundException, CannotCompileException
/*  67:    */   {
/*  68:235 */     this.source = cp;
/*  69:236 */     this.translator = t;
/*  70:237 */     t.start(cp);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static void main(String[] args)
/*  74:    */     throws Throwable
/*  75:    */   {
/*  76:256 */     Loader cl = new Loader();
/*  77:257 */     cl.run(args);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void run(String[] args)
/*  81:    */     throws Throwable
/*  82:    */   {
/*  83:271 */     int n = args.length - 1;
/*  84:272 */     if (n >= 0)
/*  85:    */     {
/*  86:273 */       String[] args2 = new String[n];
/*  87:274 */       for (int i = 0; i < n; i++) {
/*  88:275 */         args2[i] = args[(i + 1)];
/*  89:    */       }
/*  90:277 */       run(args[0], args2);
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void run(String classname, String[] args)
/*  95:    */     throws Throwable
/*  96:    */   {
/*  97:288 */     Class c = loadClass(classname);
/*  98:    */     try
/*  99:    */     {
/* 100:290 */       c.getDeclaredMethod("main", new Class[] { new String[0].getClass() }).invoke(null, new Object[] { args });
/* 101:    */     }
/* 102:    */     catch (InvocationTargetException e)
/* 103:    */     {
/* 104:295 */       throw e.getTargetException();
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected Class loadClass(String name, boolean resolve)
/* 109:    */     throws ClassFormatError, ClassNotFoundException
/* 110:    */   {
/* 111:304 */     name = name.intern();
/* 112:305 */     synchronized (name)
/* 113:    */     {
/* 114:306 */       Class c = findLoadedClass(name);
/* 115:307 */       if (c == null) {
/* 116:308 */         c = loadClassByDelegation(name);
/* 117:    */       }
/* 118:310 */       if (c == null) {
/* 119:311 */         c = findClass(name);
/* 120:    */       }
/* 121:313 */       if (c == null) {
/* 122:314 */         c = delegateToParent(name);
/* 123:    */       }
/* 124:316 */       if (resolve) {
/* 125:317 */         resolveClass(c);
/* 126:    */       }
/* 127:319 */       return c;
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   protected Class findClass(String name)
/* 132:    */     throws ClassNotFoundException
/* 133:    */   {
/* 134:    */     byte[] classfile;
/* 135:    */     try
/* 136:    */     {
/* 137:338 */       if (this.source != null)
/* 138:    */       {
/* 139:339 */         if (this.translator != null) {
/* 140:340 */           this.translator.onLoad(this.source, name);
/* 141:    */         }
/* 142:    */         try
/* 143:    */         {
/* 144:343 */           classfile = this.source.get(name).toBytecode();
/* 145:    */         }
/* 146:    */         catch (NotFoundException e)
/* 147:    */         {
/* 148:346 */           return null;
/* 149:    */         }
/* 150:    */       }
/* 151:    */       else
/* 152:    */       {
/* 153:350 */         String jarname = "/" + name.replace('.', '/') + ".class";
/* 154:351 */         InputStream in = getClass().getResourceAsStream(jarname);
/* 155:352 */         if (in == null) {
/* 156:353 */           return null;
/* 157:    */         }
/* 158:355 */         classfile = ClassPoolTail.readStream(in);
/* 159:    */       }
/* 160:    */     }
/* 161:    */     catch (Exception e)
/* 162:    */     {
/* 163:359 */       throw new ClassNotFoundException("caught an exception while obtaining a class file for " + name, e);
/* 164:    */     }
/* 165:364 */     int i = name.lastIndexOf('.');
/* 166:365 */     if (i != -1)
/* 167:    */     {
/* 168:366 */       String pname = name.substring(0, i);
/* 169:367 */       if (getPackage(pname) == null) {
/* 170:    */         try
/* 171:    */         {
/* 172:369 */           definePackage(pname, null, null, null, null, null, null, null);
/* 173:    */         }
/* 174:    */         catch (IllegalArgumentException e) {}
/* 175:    */       }
/* 176:    */     }
/* 177:378 */     if (this.domain == null) {
/* 178:379 */       return defineClass(name, classfile, 0, classfile.length);
/* 179:    */     }
/* 180:381 */     return defineClass(name, classfile, 0, classfile.length, this.domain);
/* 181:    */   }
/* 182:    */   
/* 183:    */   protected Class loadClassByDelegation(String name)
/* 184:    */     throws ClassNotFoundException
/* 185:    */   {
/* 186:397 */     Class c = null;
/* 187:398 */     if ((this.doDelegation) && (
/* 188:399 */       (name.startsWith("java.")) || (name.startsWith("javax.")) || (name.startsWith("sun.")) || (name.startsWith("com.sun.")) || (name.startsWith("org.w3c.")) || (name.startsWith("org.xml.")) || (notDelegated(name)))) {
/* 189:406 */       c = delegateToParent(name);
/* 190:    */     }
/* 191:408 */     return c;
/* 192:    */   }
/* 193:    */   
/* 194:    */   private boolean notDelegated(String name)
/* 195:    */   {
/* 196:412 */     if (this.notDefinedHere.get(name) != null) {
/* 197:413 */       return true;
/* 198:    */     }
/* 199:415 */     int n = this.notDefinedPackages.size();
/* 200:416 */     for (int i = 0; i < n; i++) {
/* 201:417 */       if (name.startsWith((String)this.notDefinedPackages.elementAt(i))) {
/* 202:418 */         return true;
/* 203:    */       }
/* 204:    */     }
/* 205:420 */     return false;
/* 206:    */   }
/* 207:    */   
/* 208:    */   protected Class delegateToParent(String classname)
/* 209:    */     throws ClassNotFoundException
/* 210:    */   {
/* 211:426 */     ClassLoader cl = getParent();
/* 212:427 */     if (cl != null) {
/* 213:428 */       return cl.loadClass(classname);
/* 214:    */     }
/* 215:430 */     return findSystemClass(classname);
/* 216:    */   }
/* 217:    */   
/* 218:    */   protected Package getPackage(String name)
/* 219:    */   {
/* 220:434 */     return super.getPackage(name);
/* 221:    */   }
/* 222:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.Loader
 * JD-Core Version:    0.7.0.1
 */