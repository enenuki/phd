/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.tools;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileInputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.InputStream;
/*   7:    */ import java.net.MalformedURLException;
/*   8:    */ import java.net.URL;
/*   9:    */ import java.net.URLConnection;
/*  10:    */ import net.sourceforge.htmlunit.corejs.javascript.Kit;
/*  11:    */ import net.sourceforge.htmlunit.corejs.javascript.commonjs.module.provider.ParsedContentType;
/*  12:    */ 
/*  13:    */ public class SourceReader
/*  14:    */ {
/*  15:    */   public static Object readFileOrUrl(String path, boolean convertToString, String defaultEncoding)
/*  16:    */     throws IOException
/*  17:    */   {
/*  18: 23 */     URL url = null;
/*  19: 27 */     if (path.indexOf(':') >= 2) {
/*  20:    */       try
/*  21:    */       {
/*  22: 29 */         url = new URL(path);
/*  23:    */       }
/*  24:    */       catch (MalformedURLException ex) {}
/*  25:    */     }
/*  26: 34 */     InputStream is = null;
/*  27: 35 */     int capacityHint = 0;
/*  28:    */     String encoding;
/*  29:    */     String contentType;
/*  30:    */     byte[] data;
/*  31:    */     try
/*  32:    */     {
/*  33: 40 */       if (url == null)
/*  34:    */       {
/*  35: 41 */         File file = new File(path);
/*  36:    */         String encoding;
/*  37: 42 */         String contentType = encoding = null;
/*  38: 43 */         capacityHint = (int)file.length();
/*  39: 44 */         is = new FileInputStream(file);
/*  40:    */       }
/*  41:    */       else
/*  42:    */       {
/*  43: 46 */         URLConnection uc = url.openConnection();
/*  44: 47 */         is = uc.getInputStream();
/*  45:    */         String encoding;
/*  46: 48 */         if (convertToString)
/*  47:    */         {
/*  48: 49 */           ParsedContentType pct = new ParsedContentType(uc.getContentType());
/*  49: 50 */           String contentType = pct.getContentType();
/*  50: 51 */           encoding = pct.getEncoding();
/*  51:    */         }
/*  52:    */         else
/*  53:    */         {
/*  54: 54 */           contentType = encoding = null;
/*  55:    */         }
/*  56: 56 */         capacityHint = uc.getContentLength();
/*  57: 58 */         if (capacityHint > 1048576) {
/*  58: 59 */           capacityHint = -1;
/*  59:    */         }
/*  60:    */       }
/*  61: 62 */       if (capacityHint <= 0) {
/*  62: 63 */         capacityHint = 4096;
/*  63:    */       }
/*  64: 66 */       data = Kit.readStream(is, capacityHint);
/*  65:    */     }
/*  66:    */     finally
/*  67:    */     {
/*  68: 68 */       if (is != null) {
/*  69: 69 */         is.close();
/*  70:    */       }
/*  71:    */     }
/*  72:    */     Object result;
/*  73:    */     Object result;
/*  74: 74 */     if (!convertToString)
/*  75:    */     {
/*  76: 75 */       result = data;
/*  77:    */     }
/*  78:    */     else
/*  79:    */     {
/*  80: 77 */       if (encoding == null) {
/*  81: 80 */         if ((data.length > 3) && (data[0] == -1) && (data[1] == -2) && (data[2] == 0) && (data[3] == 0))
/*  82:    */         {
/*  83: 81 */           encoding = "UTF-32LE";
/*  84:    */         }
/*  85: 83 */         else if ((data.length > 3) && (data[0] == 0) && (data[1] == 0) && (data[2] == -2) && (data[3] == -1))
/*  86:    */         {
/*  87: 84 */           encoding = "UTF-32BE";
/*  88:    */         }
/*  89: 86 */         else if ((data.length > 2) && (data[0] == -17) && (data[1] == -69) && (data[2] == -65))
/*  90:    */         {
/*  91: 87 */           encoding = "UTF-8";
/*  92:    */         }
/*  93: 89 */         else if ((data.length > 1) && (data[0] == -1) && (data[1] == -2))
/*  94:    */         {
/*  95: 90 */           encoding = "UTF-16LE";
/*  96:    */         }
/*  97: 92 */         else if ((data.length > 1) && (data[0] == -2) && (data[1] == -1))
/*  98:    */         {
/*  99: 93 */           encoding = "UTF-16BE";
/* 100:    */         }
/* 101:    */         else
/* 102:    */         {
/* 103: 97 */           encoding = defaultEncoding;
/* 104: 98 */           if (encoding == null) {
/* 105:100 */             if (url == null) {
/* 106:102 */               encoding = System.getProperty("file.encoding");
/* 107:104 */             } else if ((contentType != null) && (contentType.startsWith("application/"))) {
/* 108:106 */               encoding = "UTF-8";
/* 109:    */             } else {
/* 110:110 */               encoding = "US-ASCII";
/* 111:    */             }
/* 112:    */           }
/* 113:    */         }
/* 114:    */       }
/* 115:115 */       String strResult = new String(data, encoding);
/* 116:117 */       if ((strResult.length() > 0) && (strResult.charAt(0) == 65279)) {
/* 117:119 */         strResult = strResult.substring(1);
/* 118:    */       }
/* 119:121 */       result = strResult;
/* 120:    */     }
/* 121:123 */     return result;
/* 122:    */   }
/* 123:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.SourceReader
 * JD-Core Version:    0.7.0.1
 */