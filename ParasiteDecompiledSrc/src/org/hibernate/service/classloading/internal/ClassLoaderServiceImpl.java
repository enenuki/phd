/*   1:    */ package org.hibernate.service.classloading.internal;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.net.URL;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Enumeration;
/*   8:    */ import java.util.HashSet;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.LinkedHashSet;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import java.util.ServiceLoader;
/*  14:    */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/*  15:    */ import org.hibernate.service.classloading.spi.ClassLoadingException;
/*  16:    */ 
/*  17:    */ public class ClassLoaderServiceImpl
/*  18:    */   implements ClassLoaderService
/*  19:    */ {
/*  20:    */   private final ClassLoader classClassLoader;
/*  21:    */   private final ClassLoader resourcesClassLoader;
/*  22:    */   
/*  23:    */   public ClassLoaderServiceImpl()
/*  24:    */   {
/*  25: 52 */     this(ClassLoaderServiceImpl.class.getClassLoader());
/*  26:    */   }
/*  27:    */   
/*  28:    */   public ClassLoaderServiceImpl(ClassLoader classLoader)
/*  29:    */   {
/*  30: 56 */     this(classLoader, classLoader, classLoader, classLoader);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public ClassLoaderServiceImpl(ClassLoader applicationClassLoader, ClassLoader resourcesClassLoader, ClassLoader hibernateClassLoader, ClassLoader environmentClassLoader)
/*  34:    */   {
/*  35: 65 */     if (hibernateClassLoader == null) {
/*  36: 66 */       hibernateClassLoader = ClassLoaderServiceImpl.class.getClassLoader();
/*  37:    */     }
/*  38: 69 */     if ((environmentClassLoader == null) || (applicationClassLoader == null))
/*  39:    */     {
/*  40: 70 */       ClassLoader sysClassLoader = locateSystemClassLoader();
/*  41: 71 */       ClassLoader tccl = locateTCCL();
/*  42: 72 */       if (environmentClassLoader == null) {
/*  43: 73 */         environmentClassLoader = sysClassLoader != null ? sysClassLoader : hibernateClassLoader;
/*  44:    */       }
/*  45: 75 */       if (applicationClassLoader == null) {
/*  46: 76 */         applicationClassLoader = tccl != null ? tccl : hibernateClassLoader;
/*  47:    */       }
/*  48:    */     }
/*  49: 80 */     if (resourcesClassLoader == null) {
/*  50: 81 */       resourcesClassLoader = applicationClassLoader;
/*  51:    */     }
/*  52: 84 */     final LinkedHashSet<ClassLoader> classLoadingClassLoaders = new LinkedHashSet();
/*  53: 85 */     classLoadingClassLoaders.add(applicationClassLoader);
/*  54: 86 */     classLoadingClassLoaders.add(hibernateClassLoader);
/*  55: 87 */     classLoadingClassLoaders.add(environmentClassLoader);
/*  56:    */     
/*  57: 89 */     this.classClassLoader = new ClassLoader()
/*  58:    */     {
/*  59:    */       protected Class<?> findClass(String name)
/*  60:    */         throws ClassNotFoundException
/*  61:    */       {
/*  62: 92 */         for (ClassLoader loader : classLoadingClassLoaders) {
/*  63:    */           try
/*  64:    */           {
/*  65: 94 */             return loader.loadClass(name);
/*  66:    */           }
/*  67:    */           catch (Exception ignore) {}
/*  68:    */         }
/*  69: 99 */         throw new ClassNotFoundException("Could not load requested class : " + name);
/*  70:    */       }
/*  71:102 */     };
/*  72:103 */     this.resourcesClassLoader = resourcesClassLoader;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static ClassLoaderServiceImpl fromConfigSettings(Map configVales)
/*  76:    */   {
/*  77:108 */     return new ClassLoaderServiceImpl((ClassLoader)configVales.get("hibernate.classLoader.application"), (ClassLoader)configVales.get("hibernate.classLoader.resources"), (ClassLoader)configVales.get("hibernate.classLoader.hibernate"), (ClassLoader)configVales.get("hibernate.classLoader.environment"));
/*  78:    */   }
/*  79:    */   
/*  80:    */   private static ClassLoader locateSystemClassLoader()
/*  81:    */   {
/*  82:    */     try
/*  83:    */     {
/*  84:118 */       return ClassLoader.getSystemClassLoader();
/*  85:    */     }
/*  86:    */     catch (Exception e) {}
/*  87:121 */     return null;
/*  88:    */   }
/*  89:    */   
/*  90:    */   private static ClassLoader locateTCCL()
/*  91:    */   {
/*  92:    */     try
/*  93:    */     {
/*  94:127 */       return Thread.currentThread().getContextClassLoader();
/*  95:    */     }
/*  96:    */     catch (Exception e) {}
/*  97:130 */     return null;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public <T> Class<T> classForName(String className)
/* 101:    */   {
/* 102:    */     try
/* 103:    */     {
/* 104:138 */       return this.classClassLoader.loadClass(className);
/* 105:    */     }
/* 106:    */     catch (Exception e)
/* 107:    */     {
/* 108:141 */       throw new ClassLoadingException("Unable to load class [" + className + "]", e);
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   public URL locateResource(String name)
/* 113:    */   {
/* 114:    */     try
/* 115:    */     {
/* 116:149 */       return new URL(name);
/* 117:    */     }
/* 118:    */     catch (Exception ignore)
/* 119:    */     {
/* 120:    */       try
/* 121:    */       {
/* 122:155 */         return this.resourcesClassLoader.getResource(name);
/* 123:    */       }
/* 124:    */       catch (Exception ignore) {}
/* 125:    */     }
/* 126:160 */     return null;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public InputStream locateResourceStream(String name)
/* 130:    */   {
/* 131:    */     try
/* 132:    */     {
/* 133:167 */       return new URL(name).openStream();
/* 134:    */     }
/* 135:    */     catch (Exception ignore)
/* 136:    */     {
/* 137:    */       try
/* 138:    */       {
/* 139:173 */         return this.resourcesClassLoader.getResourceAsStream(name);
/* 140:    */       }
/* 141:    */       catch (Exception ignore) {}
/* 142:    */     }
/* 143:178 */     return null;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public List<URL> locateResources(String name)
/* 147:    */   {
/* 148:183 */     ArrayList<URL> urls = new ArrayList();
/* 149:    */     try
/* 150:    */     {
/* 151:185 */       Enumeration<URL> urlEnumeration = this.resourcesClassLoader.getResources(name);
/* 152:186 */       if ((urlEnumeration != null) && (urlEnumeration.hasMoreElements())) {
/* 153:187 */         while (urlEnumeration.hasMoreElements()) {
/* 154:188 */           urls.add(urlEnumeration.nextElement());
/* 155:    */         }
/* 156:    */       }
/* 157:    */     }
/* 158:    */     catch (Exception ignore) {}
/* 159:195 */     return urls;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public <S> LinkedHashSet<S> loadJavaServices(Class<S> serviceContract)
/* 163:    */   {
/* 164:200 */     ClassLoader serviceLoaderClassLoader = new ClassLoader()
/* 165:    */     {
/* 166:201 */       final ClassLoader[] classLoaderArray = { getClass().getClassLoader(), ClassLoaderServiceImpl.this.resourcesClassLoader, ClassLoaderServiceImpl.this.classClassLoader };
/* 167:    */       
/* 168:    */       public Enumeration<URL> getResources(String name)
/* 169:    */         throws IOException
/* 170:    */       {
/* 171:212 */         final HashSet<URL> resourceUrls = new HashSet();
/* 172:214 */         for (ClassLoader classLoader : this.classLoaderArray)
/* 173:    */         {
/* 174:215 */           Enumeration<URL> urls = classLoader.getResources(name);
/* 175:216 */           while (urls.hasMoreElements()) {
/* 176:217 */             resourceUrls.add(urls.nextElement());
/* 177:    */           }
/* 178:    */         }
/* 179:221 */         new Enumeration()
/* 180:    */         {
/* 181:222 */           final Iterator<URL> resourceUrlIterator = resourceUrls.iterator();
/* 182:    */           
/* 183:    */           public boolean hasMoreElements()
/* 184:    */           {
/* 185:225 */             return this.resourceUrlIterator.hasNext();
/* 186:    */           }
/* 187:    */           
/* 188:    */           public URL nextElement()
/* 189:    */           {
/* 190:230 */             return (URL)this.resourceUrlIterator.next();
/* 191:    */           }
/* 192:    */         };
/* 193:    */       }
/* 194:    */       
/* 195:    */       protected Class<?> findClass(String name)
/* 196:    */         throws ClassNotFoundException
/* 197:    */       {
/* 198:237 */         for (ClassLoader classLoader : this.classLoaderArray) {
/* 199:    */           try
/* 200:    */           {
/* 201:239 */             return classLoader.loadClass(name);
/* 202:    */           }
/* 203:    */           catch (Exception ignore) {}
/* 204:    */         }
/* 205:245 */         throw new ClassNotFoundException("Could not load requested class : " + name);
/* 206:    */       }
/* 207:248 */     };
/* 208:249 */     ServiceLoader<S> loader = ServiceLoader.load(serviceContract, serviceLoaderClassLoader);
/* 209:250 */     LinkedHashSet<S> services = new LinkedHashSet();
/* 210:251 */     for (S service : loader) {
/* 211:252 */       services.add(service);
/* 212:    */     }
/* 213:255 */     return services;
/* 214:    */   }
/* 215:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.classloading.internal.ClassLoaderServiceImpl
 * JD-Core Version:    0.7.0.1
 */