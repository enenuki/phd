/*   1:    */ package org.hibernate.bytecode.buildtime.spi;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.io.DataInputStream;
/*   5:    */ import java.io.File;
/*   6:    */ import java.io.FileInputStream;
/*   7:    */ import java.io.FileOutputStream;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.io.OutputStream;
/*  10:    */ import java.util.HashSet;
/*  11:    */ import java.util.Set;
/*  12:    */ import java.util.zip.CRC32;
/*  13:    */ import java.util.zip.ZipEntry;
/*  14:    */ import java.util.zip.ZipInputStream;
/*  15:    */ import java.util.zip.ZipOutputStream;
/*  16:    */ import org.hibernate.bytecode.spi.ByteCodeHelper;
/*  17:    */ import org.hibernate.bytecode.spi.ClassTransformer;
/*  18:    */ 
/*  19:    */ public abstract class AbstractInstrumenter
/*  20:    */   implements Instrumenter
/*  21:    */ {
/*  22:    */   private static final int ZIP_MAGIC = 1347093252;
/*  23:    */   private static final int CLASS_MAGIC = -889275714;
/*  24:    */   protected final Logger logger;
/*  25:    */   protected final Instrumenter.Options options;
/*  26:    */   
/*  27:    */   public AbstractInstrumenter(Logger logger, Instrumenter.Options options)
/*  28:    */   {
/*  29: 62 */     this.logger = logger;
/*  30: 63 */     this.options = options;
/*  31:    */   }
/*  32:    */   
/*  33:    */   protected abstract ClassDescriptor getClassDescriptor(byte[] paramArrayOfByte)
/*  34:    */     throws Exception;
/*  35:    */   
/*  36:    */   protected abstract ClassTransformer getClassTransformer(ClassDescriptor paramClassDescriptor, Set paramSet);
/*  37:    */   
/*  38:    */   public void execute(Set<File> files)
/*  39:    */   {
/*  40: 95 */     Set<String> classNames = new HashSet();
/*  41: 97 */     if (this.options.performExtendedInstrumentation())
/*  42:    */     {
/*  43: 98 */       this.logger.debug("collecting class names for extended instrumentation determination");
/*  44:    */       try
/*  45:    */       {
/*  46:100 */         for (Object file1 : files)
/*  47:    */         {
/*  48:101 */           File file = (File)file1;
/*  49:102 */           collectClassNames(file, classNames);
/*  50:    */         }
/*  51:    */       }
/*  52:    */       catch (ExecutionException ee)
/*  53:    */       {
/*  54:106 */         throw ee;
/*  55:    */       }
/*  56:    */       catch (Exception e)
/*  57:    */       {
/*  58:109 */         throw new ExecutionException(e);
/*  59:    */       }
/*  60:    */     }
/*  61:113 */     this.logger.info("starting instrumentation");
/*  62:    */     try
/*  63:    */     {
/*  64:115 */       for (File file : files) {
/*  65:116 */         processFile(file, classNames);
/*  66:    */       }
/*  67:    */     }
/*  68:    */     catch (ExecutionException ee)
/*  69:    */     {
/*  70:120 */       throw ee;
/*  71:    */     }
/*  72:    */     catch (Exception e)
/*  73:    */     {
/*  74:123 */       throw new ExecutionException(e);
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   private void collectClassNames(File file, final Set<String> classNames)
/*  79:    */     throws Exception
/*  80:    */   {
/*  81:139 */     if (isClassFile(file))
/*  82:    */     {
/*  83:140 */       byte[] bytes = ByteCodeHelper.readByteCode(file);
/*  84:141 */       ClassDescriptor descriptor = getClassDescriptor(bytes);
/*  85:142 */       classNames.add(descriptor.getName());
/*  86:    */     }
/*  87:144 */     else if (isJarFile(file))
/*  88:    */     {
/*  89:145 */       ZipEntryHandler collector = new ZipEntryHandler()
/*  90:    */       {
/*  91:    */         public void handleEntry(ZipEntry entry, byte[] byteCode)
/*  92:    */           throws Exception
/*  93:    */         {
/*  94:147 */           if (!entry.isDirectory())
/*  95:    */           {
/*  96:149 */             DataInputStream din = new DataInputStream(new ByteArrayInputStream(byteCode));
/*  97:150 */             if (din.readInt() == -889275714) {
/*  98:151 */               classNames.add(AbstractInstrumenter.this.getClassDescriptor(byteCode).getName());
/*  99:    */             }
/* 100:    */           }
/* 101:    */         }
/* 102:155 */       };
/* 103:156 */       ZipFileProcessor processor = new ZipFileProcessor(collector);
/* 104:157 */       processor.process(file);
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected final boolean isClassFile(File file)
/* 109:    */     throws IOException
/* 110:    */   {
/* 111:171 */     return checkMagic(file, -889275714L);
/* 112:    */   }
/* 113:    */   
/* 114:    */   protected final boolean isJarFile(File file)
/* 115:    */     throws IOException
/* 116:    */   {
/* 117:184 */     return checkMagic(file, 1347093252L);
/* 118:    */   }
/* 119:    */   
/* 120:    */   protected final boolean checkMagic(File file, long magic)
/* 121:    */     throws IOException
/* 122:    */   {
/* 123:188 */     DataInputStream in = new DataInputStream(new FileInputStream(file));
/* 124:    */     try
/* 125:    */     {
/* 126:190 */       int m = in.readInt();
/* 127:191 */       return magic == m;
/* 128:    */     }
/* 129:    */     finally
/* 130:    */     {
/* 131:194 */       in.close();
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   protected void processFile(File file, Set<String> classNames)
/* 136:    */     throws Exception
/* 137:    */   {
/* 138:210 */     if (isClassFile(file))
/* 139:    */     {
/* 140:211 */       this.logger.debug("processing class file : " + file.getAbsolutePath());
/* 141:212 */       processClassFile(file, classNames);
/* 142:    */     }
/* 143:214 */     else if (isJarFile(file))
/* 144:    */     {
/* 145:215 */       this.logger.debug("processing jar file : " + file.getAbsolutePath());
/* 146:216 */       processJarFile(file, classNames);
/* 147:    */     }
/* 148:    */     else
/* 149:    */     {
/* 150:219 */       this.logger.debug("ignoring file : " + file.getAbsolutePath());
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   protected void processClassFile(File file, Set<String> classNames)
/* 155:    */     throws Exception
/* 156:    */   {
/* 157:233 */     byte[] bytes = ByteCodeHelper.readByteCode(file);
/* 158:234 */     ClassDescriptor descriptor = getClassDescriptor(bytes);
/* 159:235 */     ClassTransformer transformer = getClassTransformer(descriptor, classNames);
/* 160:236 */     if (transformer == null)
/* 161:    */     {
/* 162:237 */       this.logger.debug("no trasformer for class file : " + file.getAbsolutePath());
/* 163:238 */       return;
/* 164:    */     }
/* 165:241 */     this.logger.info("processing class : " + descriptor.getName() + ";  file = " + file.getAbsolutePath());
/* 166:242 */     byte[] transformedBytes = transformer.transform(getClass().getClassLoader(), descriptor.getName(), null, null, descriptor.getBytes());
/* 167:    */     
/* 168:    */ 
/* 169:    */ 
/* 170:    */ 
/* 171:    */ 
/* 172:    */ 
/* 173:    */ 
/* 174:250 */     OutputStream out = new FileOutputStream(file);
/* 175:    */     try
/* 176:    */     {
/* 177:252 */       out.write(transformedBytes);
/* 178:253 */       out.flush(); return;
/* 179:    */     }
/* 180:    */     finally
/* 181:    */     {
/* 182:    */       try
/* 183:    */       {
/* 184:257 */         out.close();
/* 185:    */       }
/* 186:    */       catch (IOException ignore) {}
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   protected void processJarFile(final File file, final Set<String> classNames)
/* 191:    */     throws Exception
/* 192:    */   {
/* 193:275 */     File tempFile = File.createTempFile(file.getName(), null, new File(file.getAbsoluteFile().getParent()));
/* 194:    */     try
/* 195:    */     {
/* 196:282 */       FileOutputStream fout = new FileOutputStream(tempFile, false);
/* 197:    */       try
/* 198:    */       {
/* 199:284 */         final ZipOutputStream out = new ZipOutputStream(fout);
/* 200:285 */         ZipEntryHandler transformer = new ZipEntryHandler()
/* 201:    */         {
/* 202:    */           public void handleEntry(ZipEntry entry, byte[] byteCode)
/* 203:    */             throws Exception
/* 204:    */           {
/* 205:287 */             AbstractInstrumenter.this.logger.debug("starting zip entry : " + entry.toString());
/* 206:288 */             if (!entry.isDirectory())
/* 207:    */             {
/* 208:290 */               DataInputStream din = new DataInputStream(new ByteArrayInputStream(byteCode));
/* 209:291 */               if (din.readInt() == -889275714)
/* 210:    */               {
/* 211:292 */                 ClassDescriptor descriptor = AbstractInstrumenter.this.getClassDescriptor(byteCode);
/* 212:293 */                 ClassTransformer transformer = AbstractInstrumenter.this.getClassTransformer(descriptor, classNames);
/* 213:294 */                 if (transformer == null)
/* 214:    */                 {
/* 215:295 */                   AbstractInstrumenter.this.logger.debug("no transformer for zip entry :  " + entry.toString());
/* 216:    */                 }
/* 217:    */                 else
/* 218:    */                 {
/* 219:298 */                   AbstractInstrumenter.this.logger.info("processing class : " + descriptor.getName() + ";  entry = " + file.getAbsolutePath());
/* 220:299 */                   byteCode = transformer.transform(getClass().getClassLoader(), descriptor.getName(), null, null, descriptor.getBytes());
/* 221:    */                 }
/* 222:    */               }
/* 223:    */               else
/* 224:    */               {
/* 225:309 */                 AbstractInstrumenter.this.logger.debug("ignoring zip entry : " + entry.toString());
/* 226:    */               }
/* 227:    */             }
/* 228:313 */             ZipEntry outEntry = new ZipEntry(entry.getName());
/* 229:314 */             outEntry.setMethod(entry.getMethod());
/* 230:315 */             outEntry.setComment(entry.getComment());
/* 231:316 */             outEntry.setSize(byteCode.length);
/* 232:318 */             if (outEntry.getMethod() == 0)
/* 233:    */             {
/* 234:319 */               CRC32 crc = new CRC32();
/* 235:320 */               crc.update(byteCode);
/* 236:321 */               outEntry.setCrc(crc.getValue());
/* 237:322 */               outEntry.setCompressedSize(byteCode.length);
/* 238:    */             }
/* 239:324 */             out.putNextEntry(outEntry);
/* 240:325 */             out.write(byteCode);
/* 241:326 */             out.closeEntry();
/* 242:    */           }
/* 243:328 */         };
/* 244:329 */         ZipFileProcessor processor = new ZipFileProcessor(transformer);
/* 245:330 */         processor.process(file);
/* 246:331 */         out.close();
/* 247:    */       }
/* 248:    */       finally
/* 249:    */       {
/* 250:334 */         fout.close();
/* 251:    */       }
/* 252:337 */       if (file.delete())
/* 253:    */       {
/* 254:338 */         File newFile = new File(tempFile.getAbsolutePath());
/* 255:339 */         if (!newFile.renameTo(file)) {
/* 256:340 */           throw new IOException("can not rename " + tempFile + " to " + file);
/* 257:    */         }
/* 258:    */       }
/* 259:    */       else
/* 260:    */       {
/* 261:344 */         throw new IOException("can not delete " + file);
/* 262:    */       }
/* 263:    */     }
/* 264:    */     finally
/* 265:    */     {
/* 266:348 */       if (!tempFile.delete()) {
/* 267:349 */         this.logger.info("Unable to cleanup temporary jar file : " + tempFile.getAbsolutePath());
/* 268:    */       }
/* 269:    */     }
/* 270:    */   }
/* 271:    */   
/* 272:    */   protected class CustomFieldFilter
/* 273:    */     implements FieldFilter
/* 274:    */   {
/* 275:    */     private final ClassDescriptor descriptor;
/* 276:    */     private final Set classNames;
/* 277:    */     
/* 278:    */     public CustomFieldFilter(ClassDescriptor descriptor, Set classNames)
/* 279:    */     {
/* 280:362 */       this.descriptor = descriptor;
/* 281:363 */       this.classNames = classNames;
/* 282:    */     }
/* 283:    */     
/* 284:    */     public boolean shouldInstrumentField(String className, String fieldName)
/* 285:    */     {
/* 286:367 */       if (this.descriptor.getName().equals(className))
/* 287:    */       {
/* 288:368 */         AbstractInstrumenter.this.logger.trace("accepting transformation of field [" + className + "." + fieldName + "]");
/* 289:369 */         return true;
/* 290:    */       }
/* 291:372 */       AbstractInstrumenter.this.logger.trace("rejecting transformation of field [" + className + "." + fieldName + "]");
/* 292:373 */       return false;
/* 293:    */     }
/* 294:    */     
/* 295:    */     public boolean shouldTransformFieldAccess(String transformingClassName, String fieldOwnerClassName, String fieldName)
/* 296:    */     {
/* 297:381 */       if (this.descriptor.getName().equals(fieldOwnerClassName))
/* 298:    */       {
/* 299:382 */         AbstractInstrumenter.this.logger.trace("accepting transformation of field access [" + fieldOwnerClassName + "." + fieldName + "]");
/* 300:383 */         return true;
/* 301:    */       }
/* 302:385 */       if ((AbstractInstrumenter.this.options.performExtendedInstrumentation()) && (this.classNames.contains(fieldOwnerClassName)))
/* 303:    */       {
/* 304:386 */         AbstractInstrumenter.this.logger.trace("accepting extended transformation of field access [" + fieldOwnerClassName + "." + fieldName + "]");
/* 305:387 */         return true;
/* 306:    */       }
/* 307:390 */       AbstractInstrumenter.this.logger.trace("rejecting transformation of field access [" + fieldOwnerClassName + "." + fieldName + "]; caller = " + transformingClassName);
/* 308:391 */       return false;
/* 309:    */     }
/* 310:    */   }
/* 311:    */   
/* 312:    */   private static abstract interface ZipEntryHandler
/* 313:    */   {
/* 314:    */     public abstract void handleEntry(ZipEntry paramZipEntry, byte[] paramArrayOfByte)
/* 315:    */       throws Exception;
/* 316:    */   }
/* 317:    */   
/* 318:    */   private static class ZipFileProcessor
/* 319:    */   {
/* 320:    */     private final AbstractInstrumenter.ZipEntryHandler entryHandler;
/* 321:    */     
/* 322:    */     public ZipFileProcessor(AbstractInstrumenter.ZipEntryHandler entryHandler)
/* 323:    */     {
/* 324:418 */       this.entryHandler = entryHandler;
/* 325:    */     }
/* 326:    */     
/* 327:    */     public void process(File file)
/* 328:    */       throws Exception
/* 329:    */     {
/* 330:422 */       ZipInputStream zip = new ZipInputStream(new FileInputStream(file));
/* 331:    */       try
/* 332:    */       {
/* 333:    */         ZipEntry entry;
/* 334:426 */         while ((entry = zip.getNextEntry()) != null)
/* 335:    */         {
/* 336:427 */           byte[] bytes = ByteCodeHelper.readByteCode(zip);
/* 337:428 */           this.entryHandler.handleEntry(entry, bytes);
/* 338:429 */           zip.closeEntry();
/* 339:    */         }
/* 340:    */       }
/* 341:    */       finally
/* 342:    */       {
/* 343:433 */         zip.close();
/* 344:    */       }
/* 345:    */     }
/* 346:    */   }
/* 347:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.buildtime.spi.AbstractInstrumenter
 * JD-Core Version:    0.7.0.1
 */