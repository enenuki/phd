/*  1:   */ package org.apache.log4j.helpers;
/*  2:   */ 
/*  3:   */ import java.io.ByteArrayInputStream;
/*  4:   */ import java.io.ByteArrayOutputStream;
/*  5:   */ import java.io.ObjectInputStream;
/*  6:   */ import java.io.ObjectOutputStream;
/*  7:   */ import java.lang.reflect.Method;
/*  8:   */ import java.util.Set;
/*  9:   */ import org.apache.log4j.pattern.LogEvent;
/* 10:   */ import org.apache.log4j.spi.LoggingEvent;
/* 11:   */ 
/* 12:   */ public final class MDCKeySetExtractor
/* 13:   */ {
/* 14:   */   private final Method getKeySetMethod;
/* 15:32 */   public static final MDCKeySetExtractor INSTANCE = new MDCKeySetExtractor();
/* 16:   */   
/* 17:   */   private MDCKeySetExtractor()
/* 18:   */   {
/* 19:41 */     Method getMethod = null;
/* 20:   */     try
/* 21:   */     {
/* 22:44 */       getMethod = LoggingEvent.class.getMethod("getPropertyKeySet", null);
/* 23:   */     }
/* 24:   */     catch (Exception ex)
/* 25:   */     {
/* 26:47 */       getMethod = null;
/* 27:   */     }
/* 28:49 */     this.getKeySetMethod = getMethod;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Set getPropertyKeySet(LoggingEvent event)
/* 32:   */     throws Exception
/* 33:   */   {
/* 34:57 */     Set keySet = null;
/* 35:58 */     if (this.getKeySetMethod != null)
/* 36:   */     {
/* 37:59 */       keySet = (Set)this.getKeySetMethod.invoke(event, null);
/* 38:   */     }
/* 39:   */     else
/* 40:   */     {
/* 41:64 */       ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
/* 42:65 */       ObjectOutputStream os = new ObjectOutputStream(outBytes);
/* 43:66 */       os.writeObject(event);
/* 44:67 */       os.close();
/* 45:   */       
/* 46:69 */       byte[] raw = outBytes.toByteArray();
/* 47:   */       
/* 48:   */ 
/* 49:   */ 
/* 50:73 */       String subClassName = LogEvent.class.getName();
/* 51:74 */       if ((raw[6] == 0) || (raw[7] == subClassName.length()))
/* 52:   */       {
/* 53:78 */         for (int i = 0; i < subClassName.length(); i++) {
/* 54:79 */           raw[(8 + i)] = ((byte)subClassName.charAt(i));
/* 55:   */         }
/* 56:81 */         ByteArrayInputStream inBytes = new ByteArrayInputStream(raw);
/* 57:82 */         ObjectInputStream is = new ObjectInputStream(inBytes);
/* 58:83 */         Object cracked = is.readObject();
/* 59:84 */         if ((cracked instanceof LogEvent)) {
/* 60:85 */           keySet = ((LogEvent)cracked).getPropertyKeySet();
/* 61:   */         }
/* 62:87 */         is.close();
/* 63:   */       }
/* 64:   */     }
/* 65:90 */     return keySet;
/* 66:   */   }
/* 67:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.helpers.MDCKeySetExtractor
 * JD-Core Version:    0.7.0.1
 */