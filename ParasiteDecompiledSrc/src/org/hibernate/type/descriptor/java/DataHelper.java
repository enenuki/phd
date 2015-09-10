/*   1:    */ package org.hibernate.type.descriptor.java;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayOutputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.io.Reader;
/*   7:    */ import java.io.StringReader;
/*   8:    */ import org.hibernate.HibernateException;
/*   9:    */ import org.hibernate.internal.CoreMessageLogger;
/*  10:    */ import org.hibernate.internal.util.ReflectHelper;
/*  11:    */ import org.hibernate.type.descriptor.BinaryStream;
/*  12:    */ import org.jboss.logging.Logger;
/*  13:    */ 
/*  14:    */ public class DataHelper
/*  15:    */ {
/*  16: 46 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, DataHelper.class.getName());
/*  17:    */   private static Class nClobClass;
/*  18:    */   
/*  19:    */   static
/*  20:    */   {
/*  21:    */     try
/*  22:    */     {
/*  23: 52 */       nClobClass = ReflectHelper.classForName("java.sql.NClob", DataHelper.class);
/*  24:    */     }
/*  25:    */     catch (ClassNotFoundException e)
/*  26:    */     {
/*  27: 55 */       LOG.unableToLocateNClobClass();
/*  28:    */     }
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static boolean isNClob(Class type)
/*  32:    */   {
/*  33: 60 */     return (nClobClass != null) && (nClobClass.isAssignableFrom(type));
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static String extractString(Reader reader)
/*  37:    */   {
/*  38: 72 */     stringBuilder = new StringBuilder();
/*  39:    */     try
/*  40:    */     {
/*  41: 74 */       char[] buffer = new char[2048];
/*  42:    */       for (;;)
/*  43:    */       {
/*  44: 76 */         int amountRead = reader.read(buffer, 0, buffer.length);
/*  45: 77 */         if (amountRead == -1) {
/*  46:    */           break;
/*  47:    */         }
/*  48: 80 */         stringBuilder.append(buffer, 0, amountRead);
/*  49:    */       }
/*  50: 94 */       return stringBuilder.toString();
/*  51:    */     }
/*  52:    */     catch (IOException ioe)
/*  53:    */     {
/*  54: 84 */       throw new HibernateException("IOException occurred reading text", ioe);
/*  55:    */     }
/*  56:    */     finally
/*  57:    */     {
/*  58:    */       try
/*  59:    */       {
/*  60: 88 */         reader.close();
/*  61:    */       }
/*  62:    */       catch (IOException e)
/*  63:    */       {
/*  64: 91 */         LOG.unableToCloseStream(e);
/*  65:    */       }
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   private static String extractString(Reader characterStream, long start, int length)
/*  70:    */   {
/*  71:107 */     StringBuilder stringBuilder = new StringBuilder(length);
/*  72:    */     try
/*  73:    */     {
/*  74:109 */       long skipped = characterStream.skip(start);
/*  75:110 */       if (skipped != start) {
/*  76:111 */         throw new HibernateException("Unable to skip needed bytes");
/*  77:    */       }
/*  78:113 */       char[] buffer = new char[2048];
/*  79:114 */       int charsRead = 0;
/*  80:    */       for (;;)
/*  81:    */       {
/*  82:116 */         int amountRead = characterStream.read(buffer, 0, buffer.length);
/*  83:117 */         if (amountRead == -1) {
/*  84:    */           break;
/*  85:    */         }
/*  86:120 */         stringBuilder.append(buffer, 0, amountRead);
/*  87:121 */         if (amountRead < buffer.length) {
/*  88:    */           break;
/*  89:    */         }
/*  90:125 */         charsRead += amountRead;
/*  91:126 */         if (charsRead >= length) {
/*  92:    */           break;
/*  93:    */         }
/*  94:    */       }
/*  95:    */     }
/*  96:    */     catch (IOException ioe)
/*  97:    */     {
/*  98:132 */       throw new HibernateException("IOException occurred reading a binary value", ioe);
/*  99:    */     }
/* 100:134 */     return stringBuilder.toString();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public static Object subStream(Reader characterStream, long start, int length)
/* 104:    */   {
/* 105:147 */     return new StringReader(extractString(characterStream, start, length));
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static byte[] extractBytes(InputStream inputStream)
/* 109:    */   {
/* 110:158 */     if (BinaryStream.class.isInstance(inputStream)) {
/* 111:159 */       return ((BinaryStream)inputStream).getBytes();
/* 112:    */     }
/* 113:163 */     outputStream = new ByteArrayOutputStream(2048);
/* 114:    */     try
/* 115:    */     {
/* 116:165 */       byte[] buffer = new byte[2048];
/* 117:    */       for (;;)
/* 118:    */       {
/* 119:167 */         int amountRead = inputStream.read(buffer);
/* 120:168 */         if (amountRead == -1) {
/* 121:    */           break;
/* 122:    */         }
/* 123:171 */         outputStream.write(buffer, 0, amountRead);
/* 124:    */       }
/* 125:191 */       return outputStream.toByteArray();
/* 126:    */     }
/* 127:    */     catch (IOException ioe)
/* 128:    */     {
/* 129:175 */       throw new HibernateException("IOException occurred reading a binary value", ioe);
/* 130:    */     }
/* 131:    */     finally
/* 132:    */     {
/* 133:    */       try
/* 134:    */       {
/* 135:179 */         inputStream.close();
/* 136:    */       }
/* 137:    */       catch (IOException e)
/* 138:    */       {
/* 139:182 */         LOG.unableToCloseInputStream(e);
/* 140:    */       }
/* 141:    */       try
/* 142:    */       {
/* 143:185 */         outputStream.close();
/* 144:    */       }
/* 145:    */       catch (IOException e)
/* 146:    */       {
/* 147:188 */         LOG.unableToCloseOutputStream(e);
/* 148:    */       }
/* 149:    */     }
/* 150:    */   }
/* 151:    */   
/* 152:    */   public static byte[] extractBytes(InputStream inputStream, long start, int length)
/* 153:    */   {
/* 154:204 */     if ((BinaryStream.class.isInstance(inputStream)) && (2147483647L > start))
/* 155:    */     {
/* 156:205 */       byte[] data = ((BinaryStream)inputStream).getBytes();
/* 157:206 */       int size = Math.min(length, data.length);
/* 158:207 */       byte[] result = new byte[size];
/* 159:208 */       System.arraycopy(data, (int)start, result, 0, size);
/* 160:209 */       return result;
/* 161:    */     }
/* 162:212 */     ByteArrayOutputStream outputStream = new ByteArrayOutputStream(length);
/* 163:    */     try
/* 164:    */     {
/* 165:214 */       long skipped = inputStream.skip(start);
/* 166:215 */       if (skipped != start) {
/* 167:216 */         throw new HibernateException("Unable to skip needed bytes");
/* 168:    */       }
/* 169:218 */       byte[] buffer = new byte[2048];
/* 170:219 */       int bytesRead = 0;
/* 171:    */       for (;;)
/* 172:    */       {
/* 173:221 */         int amountRead = inputStream.read(buffer);
/* 174:222 */         if (amountRead == -1) {
/* 175:    */           break;
/* 176:    */         }
/* 177:225 */         outputStream.write(buffer, 0, amountRead);
/* 178:226 */         if (amountRead < buffer.length) {
/* 179:    */           break;
/* 180:    */         }
/* 181:230 */         bytesRead += amountRead;
/* 182:231 */         if (bytesRead >= length) {
/* 183:    */           break;
/* 184:    */         }
/* 185:    */       }
/* 186:    */     }
/* 187:    */     catch (IOException ioe)
/* 188:    */     {
/* 189:237 */       throw new HibernateException("IOException occurred reading a binary value", ioe);
/* 190:    */     }
/* 191:239 */     return outputStream.toByteArray();
/* 192:    */   }
/* 193:    */   
/* 194:    */   public static InputStream subStream(InputStream inputStream, long start, int length)
/* 195:    */   {
/* 196:252 */     return new BinaryStreamImpl(extractBytes(inputStream, start, length));
/* 197:    */   }
/* 198:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.DataHelper
 * JD-Core Version:    0.7.0.1
 */