/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.tools.shell;
/*   2:    */ 
/*   3:    */ import java.net.MalformedURLException;
/*   4:    */ import java.net.URL;
/*   5:    */ import java.security.AccessControlContext;
/*   6:    */ import java.security.AccessControlException;
/*   7:    */ import java.security.AccessController;
/*   8:    */ import java.security.CodeSource;
/*   9:    */ import java.security.Permission;
/*  10:    */ import java.security.PermissionCollection;
/*  11:    */ import java.security.Policy;
/*  12:    */ import java.security.PrivilegedAction;
/*  13:    */ import java.security.ProtectionDomain;
/*  14:    */ import java.security.cert.Certificate;
/*  15:    */ import java.util.Enumeration;
/*  16:    */ import net.sourceforge.htmlunit.corejs.javascript.Callable;
/*  17:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  18:    */ import net.sourceforge.htmlunit.corejs.javascript.GeneratedClassLoader;
/*  19:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  20:    */ 
/*  21:    */ public class JavaPolicySecurity
/*  22:    */   extends SecurityProxy
/*  23:    */ {
/*  24:    */   public Class<?> getStaticSecurityDomainClassInternal()
/*  25:    */   {
/*  26: 53 */     return ProtectionDomain.class;
/*  27:    */   }
/*  28:    */   
/*  29:    */   private static class Loader
/*  30:    */     extends ClassLoader
/*  31:    */     implements GeneratedClassLoader
/*  32:    */   {
/*  33:    */     private ProtectionDomain domain;
/*  34:    */     
/*  35:    */     Loader(ClassLoader parent, ProtectionDomain domain)
/*  36:    */     {
/*  37: 62 */       super();
/*  38: 63 */       this.domain = domain;
/*  39:    */     }
/*  40:    */     
/*  41:    */     public Class<?> defineClass(String name, byte[] data)
/*  42:    */     {
/*  43: 67 */       return super.defineClass(name, data, 0, data.length, this.domain);
/*  44:    */     }
/*  45:    */     
/*  46:    */     public void linkClass(Class<?> cl)
/*  47:    */     {
/*  48: 71 */       resolveClass(cl);
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   private static class ContextPermissions
/*  53:    */     extends PermissionCollection
/*  54:    */   {
/*  55:    */     static final long serialVersionUID = -1721494496320750721L;
/*  56:    */     AccessControlContext _context;
/*  57:    */     PermissionCollection _statisPermissions;
/*  58:    */     
/*  59:    */     ContextPermissions(ProtectionDomain staticDomain)
/*  60:    */     {
/*  61: 83 */       this._context = AccessController.getContext();
/*  62: 84 */       if (staticDomain != null) {
/*  63: 85 */         this._statisPermissions = staticDomain.getPermissions();
/*  64:    */       }
/*  65: 87 */       setReadOnly();
/*  66:    */     }
/*  67:    */     
/*  68:    */     public void add(Permission permission)
/*  69:    */     {
/*  70: 92 */       throw new RuntimeException("NOT IMPLEMENTED");
/*  71:    */     }
/*  72:    */     
/*  73:    */     public boolean implies(Permission permission)
/*  74:    */     {
/*  75: 97 */       if ((this._statisPermissions != null) && 
/*  76: 98 */         (!this._statisPermissions.implies(permission))) {
/*  77: 99 */         return false;
/*  78:    */       }
/*  79:    */       try
/*  80:    */       {
/*  81:103 */         this._context.checkPermission(permission);
/*  82:104 */         return true;
/*  83:    */       }
/*  84:    */       catch (AccessControlException ex) {}
/*  85:106 */       return false;
/*  86:    */     }
/*  87:    */     
/*  88:    */     public Enumeration<Permission> elements()
/*  89:    */     {
/*  90:113 */       new Enumeration()
/*  91:    */       {
/*  92:    */         public boolean hasMoreElements()
/*  93:    */         {
/*  94:114 */           return false;
/*  95:    */         }
/*  96:    */         
/*  97:    */         public Permission nextElement()
/*  98:    */         {
/*  99:115 */           return null;
/* 100:    */         }
/* 101:    */       };
/* 102:    */     }
/* 103:    */     
/* 104:    */     public String toString()
/* 105:    */     {
/* 106:121 */       StringBuffer sb = new StringBuffer();
/* 107:122 */       sb.append(getClass().getName());
/* 108:123 */       sb.append('@');
/* 109:124 */       sb.append(Integer.toHexString(System.identityHashCode(this)));
/* 110:125 */       sb.append(" (context=");
/* 111:126 */       sb.append(this._context);
/* 112:127 */       sb.append(", static_permitions=");
/* 113:128 */       sb.append(this._statisPermissions);
/* 114:129 */       sb.append(')');
/* 115:130 */       return sb.toString();
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   public JavaPolicySecurity()
/* 120:    */   {
/* 121:140 */     new CodeSource(null, (Certificate[])null);
/* 122:    */   }
/* 123:    */   
/* 124:    */   protected void callProcessFileSecure(final Context cx, final Scriptable scope, final String filename)
/* 125:    */   {
/* 126:148 */     AccessController.doPrivileged(new PrivilegedAction()
/* 127:    */     {
/* 128:    */       public Object run()
/* 129:    */       {
/* 130:150 */         URL url = JavaPolicySecurity.this.getUrlObj(filename);
/* 131:151 */         ProtectionDomain staticDomain = JavaPolicySecurity.this.getUrlDomain(url);
/* 132:152 */         Main.processFileSecure(cx, scope, url.toExternalForm(), staticDomain);
/* 133:    */         
/* 134:154 */         return null;
/* 135:    */       }
/* 136:    */     });
/* 137:    */   }
/* 138:    */   
/* 139:    */   private URL getUrlObj(String url)
/* 140:    */   {
/* 141:    */     URL urlObj;
/* 142:    */     try
/* 143:    */     {
/* 144:163 */       urlObj = new URL(url);
/* 145:    */     }
/* 146:    */     catch (MalformedURLException ex)
/* 147:    */     {
/* 148:167 */       String curDir = System.getProperty("user.dir");
/* 149:168 */       curDir = curDir.replace('\\', '/');
/* 150:169 */       if (!curDir.endsWith("/")) {
/* 151:170 */         curDir = curDir + '/';
/* 152:    */       }
/* 153:    */       try
/* 154:    */       {
/* 155:173 */         URL curDirURL = new URL("file:" + curDir);
/* 156:174 */         urlObj = new URL(curDirURL, url);
/* 157:    */       }
/* 158:    */       catch (MalformedURLException ex2)
/* 159:    */       {
/* 160:176 */         throw new RuntimeException("Can not construct file URL for '" + url + "':" + ex2.getMessage());
/* 161:    */       }
/* 162:    */     }
/* 163:181 */     return urlObj;
/* 164:    */   }
/* 165:    */   
/* 166:    */   private ProtectionDomain getUrlDomain(URL url)
/* 167:    */   {
/* 168:187 */     CodeSource cs = new CodeSource(url, (Certificate[])null);
/* 169:188 */     PermissionCollection pc = Policy.getPolicy().getPermissions(cs);
/* 170:189 */     return new ProtectionDomain(cs, pc);
/* 171:    */   }
/* 172:    */   
/* 173:    */   public GeneratedClassLoader createClassLoader(final ClassLoader parentLoader, Object securityDomain)
/* 174:    */   {
/* 175:196 */     final ProtectionDomain domain = (ProtectionDomain)securityDomain;
/* 176:197 */     (GeneratedClassLoader)AccessController.doPrivileged(new PrivilegedAction()
/* 177:    */     {
/* 178:    */       public JavaPolicySecurity.Loader run()
/* 179:    */       {
/* 180:199 */         return new JavaPolicySecurity.Loader(parentLoader, domain);
/* 181:    */       }
/* 182:    */     });
/* 183:    */   }
/* 184:    */   
/* 185:    */   public Object getDynamicSecurityDomain(Object securityDomain)
/* 186:    */   {
/* 187:207 */     ProtectionDomain staticDomain = (ProtectionDomain)securityDomain;
/* 188:208 */     return getDynamicDomain(staticDomain);
/* 189:    */   }
/* 190:    */   
/* 191:    */   private ProtectionDomain getDynamicDomain(ProtectionDomain staticDomain)
/* 192:    */   {
/* 193:212 */     ContextPermissions p = new ContextPermissions(staticDomain);
/* 194:213 */     ProtectionDomain contextDomain = new ProtectionDomain(null, p);
/* 195:214 */     return contextDomain;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public Object callWithDomain(Object securityDomain, final Context cx, final Callable callable, final Scriptable scope, final Scriptable thisObj, final Object[] args)
/* 199:    */   {
/* 200:225 */     ProtectionDomain staticDomain = (ProtectionDomain)securityDomain;
/* 201:    */     
/* 202:    */ 
/* 203:    */ 
/* 204:    */ 
/* 205:    */ 
/* 206:    */ 
/* 207:    */ 
/* 208:    */ 
/* 209:    */ 
/* 210:    */ 
/* 211:    */ 
/* 212:    */ 
/* 213:    */ 
/* 214:    */ 
/* 215:    */ 
/* 216:241 */     ProtectionDomain dynamicDomain = getDynamicDomain(staticDomain);
/* 217:242 */     ProtectionDomain[] tmp = { dynamicDomain };
/* 218:243 */     AccessControlContext restricted = new AccessControlContext(tmp);
/* 219:    */     
/* 220:245 */     PrivilegedAction<Object> action = new PrivilegedAction()
/* 221:    */     {
/* 222:    */       public Object run()
/* 223:    */       {
/* 224:247 */         return callable.call(cx, scope, thisObj, args);
/* 225:    */       }
/* 226:250 */     };
/* 227:251 */     return AccessController.doPrivileged(action, restricted);
/* 228:    */   }
/* 229:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.shell.JavaPolicySecurity
 * JD-Core Version:    0.7.0.1
 */