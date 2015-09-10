/*   1:    */ package org.hibernate.internal.util;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.io.ByteArrayOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.InputStream;
/*   7:    */ import java.io.ObjectInputStream;
/*   8:    */ import java.io.ObjectOutputStream;
/*   9:    */ import java.io.ObjectStreamClass;
/*  10:    */ import java.io.OutputStream;
/*  11:    */ import java.io.Serializable;
/*  12:    */ import org.hibernate.Hibernate;
/*  13:    */ import org.hibernate.internal.CoreMessageLogger;
/*  14:    */ import org.hibernate.type.SerializationException;
/*  15:    */ import org.jboss.logging.Logger;
/*  16:    */ 
/*  17:    */ public final class SerializationHelper
/*  18:    */ {
/*  19: 67 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, SerializationHelper.class.getName());
/*  20:    */   
/*  21:    */   public static Object clone(Serializable object)
/*  22:    */     throws SerializationException
/*  23:    */   {
/*  24: 91 */     LOG.trace("Starting clone through serialization");
/*  25: 92 */     if (object == null) {
/*  26: 93 */       return null;
/*  27:    */     }
/*  28: 95 */     return deserialize(serialize(object), object.getClass().getClassLoader());
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static void serialize(Serializable obj, OutputStream outputStream)
/*  32:    */     throws SerializationException
/*  33:    */   {
/*  34:118 */     if (outputStream == null) {
/*  35:119 */       throw new IllegalArgumentException("The OutputStream must not be null");
/*  36:    */     }
/*  37:122 */     if (LOG.isTraceEnabled()) {
/*  38:123 */       if (Hibernate.isInitialized(obj)) {
/*  39:124 */         LOG.tracev("Starting serialization of object [{0}]", obj);
/*  40:    */       } else {
/*  41:127 */         LOG.trace("Starting serialization of [uninitialized proxy]");
/*  42:    */       }
/*  43:    */     }
/*  44:131 */     ObjectOutputStream out = null;
/*  45:    */     try
/*  46:    */     {
/*  47:134 */       out = new ObjectOutputStream(outputStream);
/*  48:135 */       out.writeObject(obj); return;
/*  49:    */     }
/*  50:    */     catch (IOException ex)
/*  51:    */     {
/*  52:139 */       throw new SerializationException("could not serialize", ex);
/*  53:    */     }
/*  54:    */     finally
/*  55:    */     {
/*  56:    */       try
/*  57:    */       {
/*  58:143 */         if (out != null) {
/*  59:144 */           out.close();
/*  60:    */         }
/*  61:    */       }
/*  62:    */       catch (IOException ignored) {}
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static byte[] serialize(Serializable obj)
/*  67:    */     throws SerializationException
/*  68:    */   {
/*  69:163 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
/*  70:164 */     serialize(obj, byteArrayOutputStream);
/*  71:165 */     return byteArrayOutputStream.toByteArray();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static Object deserialize(InputStream inputStream)
/*  75:    */     throws SerializationException
/*  76:    */   {
/*  77:185 */     return doDeserialize(inputStream, defaultClassLoader(), hibernateClassLoader(), null);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static ClassLoader defaultClassLoader()
/*  81:    */   {
/*  82:194 */     return Thread.currentThread().getContextClassLoader();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static ClassLoader hibernateClassLoader()
/*  86:    */   {
/*  87:198 */     return SerializationHelper.class.getClassLoader();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static Object deserialize(InputStream inputStream, ClassLoader loader)
/*  91:    */     throws SerializationException
/*  92:    */   {
/*  93:222 */     return doDeserialize(inputStream, loader, defaultClassLoader(), hibernateClassLoader());
/*  94:    */   }
/*  95:    */   
/*  96:    */   /* Error */
/*  97:    */   public static Object doDeserialize(InputStream inputStream, ClassLoader loader, ClassLoader fallbackLoader1, ClassLoader fallbackLoader2)
/*  98:    */     throws SerializationException
/*  99:    */   {
/* 100:    */     // Byte code:
/* 101:    */     //   0: aload_0
/* 102:    */     //   1: ifnonnull +13 -> 14
/* 103:    */     //   4: new 9	java/lang/IllegalArgumentException
/* 104:    */     //   7: dup
/* 105:    */     //   8: ldc 35
/* 106:    */     //   10: invokespecial 11	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
/* 107:    */     //   13: athrow
/* 108:    */     //   14: getstatic 1	org/hibernate/internal/util/SerializationHelper:LOG	Lorg/hibernate/internal/CoreMessageLogger;
/* 109:    */     //   17: ldc 36
/* 110:    */     //   19: invokeinterface 4 2 0
/* 111:    */     //   24: new 37	org/hibernate/internal/util/SerializationHelper$CustomObjectInputStream
/* 112:    */     //   27: dup
/* 113:    */     //   28: aload_0
/* 114:    */     //   29: aload_1
/* 115:    */     //   30: aload_2
/* 116:    */     //   31: aload_3
/* 117:    */     //   32: aconst_null
/* 118:    */     //   33: invokespecial 38	org/hibernate/internal/util/SerializationHelper$CustomObjectInputStream:<init>	(Ljava/io/InputStream;Ljava/lang/ClassLoader;Ljava/lang/ClassLoader;Ljava/lang/ClassLoader;Lorg/hibernate/internal/util/SerializationHelper$1;)V
/* 119:    */     //   36: astore 4
/* 120:    */     //   38: aload 4
/* 121:    */     //   40: invokevirtual 39	org/hibernate/internal/util/SerializationHelper$CustomObjectInputStream:readObject	()Ljava/lang/Object;
/* 122:    */     //   43: astore 5
/* 123:    */     //   45: aload 4
/* 124:    */     //   47: invokevirtual 40	org/hibernate/internal/util/SerializationHelper$CustomObjectInputStream:close	()V
/* 125:    */     //   50: goto +5 -> 55
/* 126:    */     //   53: astore 6
/* 127:    */     //   55: aload 5
/* 128:    */     //   57: areturn
/* 129:    */     //   58: astore 5
/* 130:    */     //   60: new 22	org/hibernate/type/SerializationException
/* 131:    */     //   63: dup
/* 132:    */     //   64: ldc 42
/* 133:    */     //   66: aload 5
/* 134:    */     //   68: invokespecial 24	org/hibernate/type/SerializationException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
/* 135:    */     //   71: athrow
/* 136:    */     //   72: astore 5
/* 137:    */     //   74: new 22	org/hibernate/type/SerializationException
/* 138:    */     //   77: dup
/* 139:    */     //   78: ldc 42
/* 140:    */     //   80: aload 5
/* 141:    */     //   82: invokespecial 24	org/hibernate/type/SerializationException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
/* 142:    */     //   85: athrow
/* 143:    */     //   86: astore 7
/* 144:    */     //   88: aload 4
/* 145:    */     //   90: invokevirtual 40	org/hibernate/internal/util/SerializationHelper$CustomObjectInputStream:close	()V
/* 146:    */     //   93: goto +5 -> 98
/* 147:    */     //   96: astore 8
/* 148:    */     //   98: aload 7
/* 149:    */     //   100: athrow
/* 150:    */     //   101: astore 4
/* 151:    */     //   103: new 22	org/hibernate/type/SerializationException
/* 152:    */     //   106: dup
/* 153:    */     //   107: ldc 42
/* 154:    */     //   109: aload 4
/* 155:    */     //   111: invokespecial 24	org/hibernate/type/SerializationException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
/* 156:    */     //   114: athrow
/* 157:    */     // Line number table:
/* 158:    */     //   Java source line #230	-> byte code offset #0
/* 159:    */     //   Java source line #231	-> byte code offset #4
/* 160:    */     //   Java source line #234	-> byte code offset #14
/* 161:    */     //   Java source line #237	-> byte code offset #24
/* 162:    */     //   Java source line #244	-> byte code offset #38
/* 163:    */     //   Java source line #254	-> byte code offset #45
/* 164:    */     //   Java source line #258	-> byte code offset #50
/* 165:    */     //   Java source line #256	-> byte code offset #53
/* 166:    */     //   Java source line #258	-> byte code offset #55
/* 167:    */     //   Java source line #246	-> byte code offset #58
/* 168:    */     //   Java source line #247	-> byte code offset #60
/* 169:    */     //   Java source line #249	-> byte code offset #72
/* 170:    */     //   Java source line #250	-> byte code offset #74
/* 171:    */     //   Java source line #253	-> byte code offset #86
/* 172:    */     //   Java source line #254	-> byte code offset #88
/* 173:    */     //   Java source line #258	-> byte code offset #93
/* 174:    */     //   Java source line #256	-> byte code offset #96
/* 175:    */     //   Java source line #258	-> byte code offset #98
/* 176:    */     //   Java source line #261	-> byte code offset #101
/* 177:    */     //   Java source line #262	-> byte code offset #103
/* 178:    */     // Local variable table:
/* 179:    */     //   start	length	slot	name	signature
/* 180:    */     //   0	115	0	inputStream	InputStream
/* 181:    */     //   0	115	1	loader	ClassLoader
/* 182:    */     //   0	115	2	fallbackLoader1	ClassLoader
/* 183:    */     //   0	115	3	fallbackLoader2	ClassLoader
/* 184:    */     //   36	53	4	in	CustomObjectInputStream
/* 185:    */     //   101	9	4	e	IOException
/* 186:    */     //   58	9	5	e	ClassNotFoundException
/* 187:    */     //   72	9	5	e	IOException
/* 188:    */     //   53	3	6	ignore	IOException
/* 189:    */     //   86	13	7	localObject2	Object
/* 190:    */     //   96	3	8	ignore	IOException
/* 191:    */     // Exception table:
/* 192:    */     //   from	to	target	type
/* 193:    */     //   45	50	53	java/io/IOException
/* 194:    */     //   38	45	58	java/lang/ClassNotFoundException
/* 195:    */     //   38	45	72	java/io/IOException
/* 196:    */     //   38	45	86	finally
/* 197:    */     //   58	88	86	finally
/* 198:    */     //   88	93	96	java/io/IOException
/* 199:    */     //   24	55	101	java/io/IOException
/* 200:    */     //   58	101	101	java/io/IOException
/* 201:    */   }
/* 202:    */   
/* 203:    */   public static Object deserialize(byte[] objectData)
/* 204:    */     throws SerializationException
/* 205:    */   {
/* 206:281 */     return doDeserialize(wrap(objectData), defaultClassLoader(), hibernateClassLoader(), null);
/* 207:    */   }
/* 208:    */   
/* 209:    */   private static InputStream wrap(byte[] objectData)
/* 210:    */   {
/* 211:285 */     if (objectData == null) {
/* 212:286 */       throw new IllegalArgumentException("The byte[] must not be null");
/* 213:    */     }
/* 214:288 */     return new ByteArrayInputStream(objectData);
/* 215:    */   }
/* 216:    */   
/* 217:    */   public static Object deserialize(byte[] objectData, ClassLoader loader)
/* 218:    */     throws SerializationException
/* 219:    */   {
/* 220:306 */     return doDeserialize(wrap(objectData), loader, defaultClassLoader(), hibernateClassLoader());
/* 221:    */   }
/* 222:    */   
/* 223:    */   private static final class CustomObjectInputStream
/* 224:    */     extends ObjectInputStream
/* 225:    */   {
/* 226:    */     private final ClassLoader loader1;
/* 227:    */     private final ClassLoader loader2;
/* 228:    */     private final ClassLoader loader3;
/* 229:    */     
/* 230:    */     private CustomObjectInputStream(InputStream in, ClassLoader loader1, ClassLoader loader2, ClassLoader loader3)
/* 231:    */       throws IOException
/* 232:    */     {
/* 233:328 */       super();
/* 234:329 */       this.loader1 = loader1;
/* 235:330 */       this.loader2 = loader2;
/* 236:331 */       this.loader3 = loader3;
/* 237:    */     }
/* 238:    */     
/* 239:    */     protected Class resolveClass(ObjectStreamClass v)
/* 240:    */       throws IOException, ClassNotFoundException
/* 241:    */     {
/* 242:339 */       String className = v.getName();
/* 243:340 */       SerializationHelper.LOG.tracev("Attempting to locate class [{0}]", className);
/* 244:    */       try
/* 245:    */       {
/* 246:343 */         return Class.forName(className, false, this.loader1);
/* 247:    */       }
/* 248:    */       catch (ClassNotFoundException e)
/* 249:    */       {
/* 250:346 */         SerializationHelper.LOG.trace("Unable to locate class using given classloader");
/* 251:349 */         if (different(this.loader1, this.loader2)) {
/* 252:    */           try
/* 253:    */           {
/* 254:351 */             return Class.forName(className, false, this.loader2);
/* 255:    */           }
/* 256:    */           catch (ClassNotFoundException e)
/* 257:    */           {
/* 258:354 */             SerializationHelper.LOG.trace("Unable to locate class using given classloader");
/* 259:    */           }
/* 260:    */         }
/* 261:358 */         if ((different(this.loader1, this.loader3)) && (different(this.loader2, this.loader3))) {
/* 262:    */           try
/* 263:    */           {
/* 264:360 */             return Class.forName(className, false, this.loader3);
/* 265:    */           }
/* 266:    */           catch (ClassNotFoundException e)
/* 267:    */           {
/* 268:363 */             SerializationHelper.LOG.trace("Unable to locate class using given classloader");
/* 269:    */           }
/* 270:    */         }
/* 271:    */       }
/* 272:369 */       return super.resolveClass(v);
/* 273:    */     }
/* 274:    */     
/* 275:    */     private boolean different(ClassLoader one, ClassLoader other)
/* 276:    */     {
/* 277:373 */       if (one == null) {
/* 278:373 */         return other != null;
/* 279:    */       }
/* 280:374 */       return !one.equals(other);
/* 281:    */     }
/* 282:    */   }
/* 283:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.SerializationHelper
 * JD-Core Version:    0.7.0.1
 */