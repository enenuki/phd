/*   1:    */ package javax.persistence.spi;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.io.InputStreamReader;
/*   7:    */ import java.lang.ref.WeakReference;
/*   8:    */ import java.net.URL;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Enumeration;
/*  11:    */ import java.util.HashSet;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Set;
/*  14:    */ import java.util.WeakHashMap;
/*  15:    */ import java.util.regex.Matcher;
/*  16:    */ import java.util.regex.Pattern;
/*  17:    */ import javax.persistence.PersistenceException;
/*  18:    */ 
/*  19:    */ public class PersistenceProviderResolverHolder
/*  20:    */ {
/*  21: 44 */   private static final PersistenceProviderResolver DEFAULT_RESOLVER = new PersistenceProviderResolverPerClassLoader(null);
/*  22:    */   private static volatile PersistenceProviderResolver RESOLVER;
/*  23:    */   
/*  24:    */   public static PersistenceProviderResolver getPersistenceProviderResolver()
/*  25:    */   {
/*  26: 54 */     return RESOLVER == null ? DEFAULT_RESOLVER : RESOLVER;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static void setPersistenceProviderResolver(PersistenceProviderResolver resolver)
/*  30:    */   {
/*  31: 63 */     RESOLVER = resolver;
/*  32:    */   }
/*  33:    */   
/*  34:    */   private static class PersistenceProviderResolverPerClassLoader
/*  35:    */     implements PersistenceProviderResolver
/*  36:    */   {
/*  37:    */     private final WeakHashMap<ClassLoader, PersistenceProviderResolver> resolvers;
/*  38:    */     private volatile short barrier;
/*  39:    */     
/*  40:    */     private PersistenceProviderResolverPerClassLoader()
/*  41:    */     {
/*  42: 76 */       this.resolvers = new WeakHashMap();
/*  43:    */       
/*  44: 78 */       this.barrier = 1;
/*  45:    */     }
/*  46:    */     
/*  47:    */     public List<PersistenceProvider> getPersistenceProviders()
/*  48:    */     {
/*  49: 84 */       ClassLoader cl = getContextualClassLoader();
/*  50: 85 */       if (this.barrier == 1) {}
/*  51: 86 */       PersistenceProviderResolver currentResolver = (PersistenceProviderResolver)this.resolvers.get(cl);
/*  52: 87 */       if (currentResolver == null)
/*  53:    */       {
/*  54: 88 */         currentResolver = new CachingPersistenceProviderResolver(cl);
/*  55: 89 */         this.resolvers.put(cl, currentResolver);
/*  56: 90 */         this.barrier = 1;
/*  57:    */       }
/*  58: 92 */       return currentResolver.getPersistenceProviders();
/*  59:    */     }
/*  60:    */     
/*  61:    */     public void clearCachedProviders()
/*  62:    */     {
/*  63:100 */       ClassLoader cl = getContextualClassLoader();
/*  64:101 */       if (this.barrier == 1) {}
/*  65:102 */       PersistenceProviderResolver currentResolver = (PersistenceProviderResolver)this.resolvers.get(cl);
/*  66:103 */       if (currentResolver != null) {
/*  67:104 */         currentResolver.clearCachedProviders();
/*  68:    */       }
/*  69:    */     }
/*  70:    */     
/*  71:    */     private static ClassLoader getContextualClassLoader()
/*  72:    */     {
/*  73:109 */       ClassLoader cl = Thread.currentThread().getContextClassLoader();
/*  74:110 */       if (cl == null) {
/*  75:111 */         cl = PersistenceProviderResolverPerClassLoader.class.getClassLoader();
/*  76:    */       }
/*  77:113 */       return cl;
/*  78:    */     }
/*  79:    */     
/*  80:    */     private static class CachingPersistenceProviderResolver
/*  81:    */       implements PersistenceProviderResolver
/*  82:    */     {
/*  83:126 */       private final List<WeakReference<Class<? extends PersistenceProvider>>> resolverClasses = new ArrayList();
/*  84:    */       
/*  85:    */       public CachingPersistenceProviderResolver(ClassLoader cl)
/*  86:    */       {
/*  87:130 */         loadResolverClasses(cl);
/*  88:    */       }
/*  89:    */       
/*  90:    */       private void loadResolverClasses(ClassLoader cl)
/*  91:    */       {
/*  92:134 */         synchronized (this.resolverClasses)
/*  93:    */         {
/*  94:    */           try
/*  95:    */           {
/*  96:136 */             Enumeration<URL> resources = cl.getResources("META-INF/services/" + PersistenceProvider.class.getName());
/*  97:137 */             Set<String> names = new HashSet();
/*  98:138 */             while (resources.hasMoreElements())
/*  99:    */             {
/* 100:139 */               URL url = (URL)resources.nextElement();
/* 101:140 */               InputStream is = url.openStream();
/* 102:    */               try
/* 103:    */               {
/* 104:142 */                 names.addAll(providerNamesFromReader(new BufferedReader(new InputStreamReader(is))));
/* 105:    */               }
/* 106:    */               finally
/* 107:    */               {
/* 108:145 */                 is.close();
/* 109:    */               }
/* 110:    */             }
/* 111:148 */             for (String s : names)
/* 112:    */             {
/* 113:150 */               Object providerClass = cl.loadClass(s);
/* 114:151 */               WeakReference<Class<? extends PersistenceProvider>> reference = new WeakReference(providerClass);
/* 115:154 */               if ((s.endsWith("HibernatePersistence")) && (this.resolverClasses.size() > 0))
/* 116:    */               {
/* 117:155 */                 WeakReference<Class<? extends PersistenceProvider>> movedReference = (WeakReference)this.resolverClasses.get(0);
/* 118:156 */                 this.resolverClasses.add(0, reference);
/* 119:157 */                 this.resolverClasses.add(movedReference);
/* 120:    */               }
/* 121:    */               else
/* 122:    */               {
/* 123:160 */                 this.resolverClasses.add(reference);
/* 124:    */               }
/* 125:    */             }
/* 126:    */           }
/* 127:    */           catch (IOException e)
/* 128:    */           {
/* 129:165 */             throw new PersistenceException(e);
/* 130:    */           }
/* 131:    */           catch (ClassNotFoundException e)
/* 132:    */           {
/* 133:168 */             throw new PersistenceException(e);
/* 134:    */           }
/* 135:    */         }
/* 136:    */       }
/* 137:    */       
/* 138:    */       public List<PersistenceProvider> getPersistenceProviders()
/* 139:    */       {
/* 140:181 */         synchronized (this.resolverClasses)
/* 141:    */         {
/* 142:182 */           List<PersistenceProvider> providers = new ArrayList(this.resolverClasses.size());
/* 143:    */           try
/* 144:    */           {
/* 145:184 */             for (WeakReference<Class<? extends PersistenceProvider>> providerClass : this.resolverClasses) {
/* 146:185 */               providers.add(((Class)providerClass.get()).newInstance());
/* 147:    */             }
/* 148:    */           }
/* 149:    */           catch (InstantiationException e)
/* 150:    */           {
/* 151:189 */             throw new PersistenceException(e);
/* 152:    */           }
/* 153:    */           catch (IllegalAccessException e)
/* 154:    */           {
/* 155:192 */             throw new PersistenceException(e);
/* 156:    */           }
/* 157:194 */           return providers;
/* 158:    */         }
/* 159:    */       }
/* 160:    */       
/* 161:    */       public synchronized void clearCachedProviders()
/* 162:    */       {
/* 163:202 */         synchronized (this.resolverClasses)
/* 164:    */         {
/* 165:203 */           this.resolverClasses.clear();
/* 166:204 */           loadResolverClasses(PersistenceProviderResolverHolder.PersistenceProviderResolverPerClassLoader.access$100());
/* 167:    */         }
/* 168:    */       }
/* 169:    */       
/* 170:209 */       private static final Pattern nonCommentPattern = Pattern.compile("^([^#]+)");
/* 171:    */       
/* 172:    */       private static Set<String> providerNamesFromReader(BufferedReader reader)
/* 173:    */         throws IOException
/* 174:    */       {
/* 175:212 */         Set<String> names = new HashSet();
/* 176:    */         String line;
/* 177:214 */         while ((line = reader.readLine()) != null)
/* 178:    */         {
/* 179:215 */           line = line.trim();
/* 180:216 */           Matcher m = nonCommentPattern.matcher(line);
/* 181:217 */           if (m.find()) {
/* 182:218 */             names.add(m.group().trim());
/* 183:    */           }
/* 184:    */         }
/* 185:221 */         return names;
/* 186:    */       }
/* 187:    */     }
/* 188:    */   }
/* 189:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.spi.PersistenceProviderResolverHolder
 * JD-Core Version:    0.7.0.1
 */