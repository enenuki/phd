/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ 
/*   6:    */ public class SystemIDResolver
/*   7:    */ {
/*   8:    */   public static String getAbsoluteURIFromRelative(String localPath)
/*   9:    */   {
/*  10: 57 */     if ((localPath == null) || (localPath.length() == 0)) {
/*  11: 58 */       return "";
/*  12:    */     }
/*  13: 62 */     String absolutePath = localPath;
/*  14: 63 */     if (!isAbsolutePath(localPath)) {
/*  15:    */       try
/*  16:    */       {
/*  17: 67 */         absolutePath = getAbsolutePathFromRelativePath(localPath);
/*  18:    */       }
/*  19:    */       catch (SecurityException se)
/*  20:    */       {
/*  21: 72 */         return "file:" + localPath;
/*  22:    */       }
/*  23:    */     }
/*  24:    */     String urlString;
/*  25: 77 */     if (null != absolutePath)
/*  26:    */     {
/*  27: 79 */       if (absolutePath.startsWith(File.separator)) {
/*  28: 80 */         urlString = "file://" + absolutePath;
/*  29:    */       } else {
/*  30: 82 */         urlString = "file:///" + absolutePath;
/*  31:    */       }
/*  32:    */     }
/*  33:    */     else {
/*  34: 85 */       urlString = "file:" + localPath;
/*  35:    */     }
/*  36: 87 */     return replaceChars(urlString);
/*  37:    */   }
/*  38:    */   
/*  39:    */   private static String getAbsolutePathFromRelativePath(String relativePath)
/*  40:    */   {
/*  41: 98 */     return new File(relativePath).getAbsolutePath();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static boolean isAbsoluteURI(String systemId)
/*  45:    */   {
/*  46:120 */     if (isWindowsAbsolutePath(systemId)) {
/*  47:121 */       return false;
/*  48:    */     }
/*  49:124 */     int fragmentIndex = systemId.indexOf('#');
/*  50:125 */     int queryIndex = systemId.indexOf('?');
/*  51:126 */     int slashIndex = systemId.indexOf('/');
/*  52:127 */     int colonIndex = systemId.indexOf(':');
/*  53:    */     
/*  54:    */ 
/*  55:130 */     int index = systemId.length() - 1;
/*  56:131 */     if (fragmentIndex > 0) {
/*  57:132 */       index = fragmentIndex;
/*  58:    */     }
/*  59:133 */     if ((queryIndex > 0) && (queryIndex < index)) {
/*  60:134 */       index = queryIndex;
/*  61:    */     }
/*  62:135 */     if ((slashIndex > 0) && (slashIndex < index)) {
/*  63:136 */       index = slashIndex;
/*  64:    */     }
/*  65:138 */     return (colonIndex > 0) && (colonIndex < index);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static boolean isAbsolutePath(String systemId)
/*  69:    */   {
/*  70:150 */     if (systemId == null) {
/*  71:151 */       return false;
/*  72:    */     }
/*  73:152 */     File file = new File(systemId);
/*  74:153 */     return file.isAbsolute();
/*  75:    */   }
/*  76:    */   
/*  77:    */   private static boolean isWindowsAbsolutePath(String systemId)
/*  78:    */   {
/*  79:165 */     if (!isAbsolutePath(systemId)) {
/*  80:166 */       return false;
/*  81:    */     }
/*  82:168 */     if ((systemId.length() > 2) && (systemId.charAt(1) == ':') && (Character.isLetter(systemId.charAt(0))) && ((systemId.charAt(2) == '\\') || (systemId.charAt(2) == '/'))) {
/*  83:172 */       return true;
/*  84:    */     }
/*  85:174 */     return false;
/*  86:    */   }
/*  87:    */   
/*  88:    */   private static String replaceChars(String str)
/*  89:    */   {
/*  90:186 */     StringBuffer buf = new StringBuffer(str);
/*  91:187 */     int length = buf.length();
/*  92:188 */     for (int i = 0; i < length; i++)
/*  93:    */     {
/*  94:190 */       char currentChar = buf.charAt(i);
/*  95:192 */       if (currentChar == ' ')
/*  96:    */       {
/*  97:194 */         buf.setCharAt(i, '%');
/*  98:195 */         buf.insert(i + 1, "20");
/*  99:196 */         length += 2;
/* 100:197 */         i += 2;
/* 101:    */       }
/* 102:200 */       else if (currentChar == '\\')
/* 103:    */       {
/* 104:202 */         buf.setCharAt(i, '/');
/* 105:    */       }
/* 106:    */     }
/* 107:206 */     return buf.toString();
/* 108:    */   }
/* 109:    */   
/* 110:    */   public static String getAbsoluteURI(String systemId)
/* 111:    */   {
/* 112:218 */     String absoluteURI = systemId;
/* 113:219 */     if (isAbsoluteURI(systemId))
/* 114:    */     {
/* 115:222 */       if (systemId.startsWith("file:"))
/* 116:    */       {
/* 117:224 */         String str = systemId.substring(5);
/* 118:228 */         if ((str != null) && (str.startsWith("/")))
/* 119:    */         {
/* 120:230 */           if ((str.startsWith("///")) || (!str.startsWith("//")))
/* 121:    */           {
/* 122:234 */             int secondColonIndex = systemId.indexOf(':', 5);
/* 123:235 */             if (secondColonIndex > 0)
/* 124:    */             {
/* 125:237 */               String localPath = systemId.substring(secondColonIndex - 1);
/* 126:    */               try
/* 127:    */               {
/* 128:239 */                 if (!isAbsolutePath(localPath)) {
/* 129:240 */                   absoluteURI = systemId.substring(0, secondColonIndex - 1) + getAbsolutePathFromRelativePath(localPath);
/* 130:    */                 }
/* 131:    */               }
/* 132:    */               catch (SecurityException se)
/* 133:    */               {
/* 134:244 */                 return systemId;
/* 135:    */               }
/* 136:    */             }
/* 137:    */           }
/* 138:    */         }
/* 139:    */         else {
/* 140:251 */           return getAbsoluteURIFromRelative(systemId.substring(5));
/* 141:    */         }
/* 142:254 */         return replaceChars(absoluteURI);
/* 143:    */       }
/* 144:257 */       return systemId;
/* 145:    */     }
/* 146:260 */     return getAbsoluteURIFromRelative(systemId);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public static String getAbsoluteURI(String urlString, String base)
/* 150:    */     throws TransformerException
/* 151:    */   {
/* 152:277 */     if (base == null) {
/* 153:278 */       return getAbsoluteURI(urlString);
/* 154:    */     }
/* 155:280 */     String absoluteBase = getAbsoluteURI(base);
/* 156:281 */     URI uri = null;
/* 157:    */     try
/* 158:    */     {
/* 159:284 */       URI baseURI = new URI(absoluteBase);
/* 160:285 */       uri = new URI(baseURI, urlString);
/* 161:    */     }
/* 162:    */     catch (URI.MalformedURIException mue)
/* 163:    */     {
/* 164:289 */       throw new TransformerException(mue);
/* 165:    */     }
/* 166:292 */     return replaceChars(uri.toString());
/* 167:    */   }
/* 168:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.SystemIDResolver
 * JD-Core Version:    0.7.0.1
 */