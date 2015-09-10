/*   1:    */ package org.apache.xml.serializer.utils;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ 
/*   6:    */ public final class SystemIDResolver
/*   7:    */ {
/*   8:    */   public static String getAbsoluteURIFromRelative(String localPath)
/*   9:    */   {
/*  10: 64 */     if ((localPath == null) || (localPath.length() == 0)) {
/*  11: 65 */       return "";
/*  12:    */     }
/*  13: 69 */     String absolutePath = localPath;
/*  14: 70 */     if (!isAbsolutePath(localPath)) {
/*  15:    */       try
/*  16:    */       {
/*  17: 74 */         absolutePath = getAbsolutePathFromRelativePath(localPath);
/*  18:    */       }
/*  19:    */       catch (SecurityException se)
/*  20:    */       {
/*  21: 79 */         return "file:" + localPath;
/*  22:    */       }
/*  23:    */     }
/*  24:    */     String urlString;
/*  25: 84 */     if (null != absolutePath)
/*  26:    */     {
/*  27: 86 */       if (absolutePath.startsWith(File.separator)) {
/*  28: 87 */         urlString = "file://" + absolutePath;
/*  29:    */       } else {
/*  30: 89 */         urlString = "file:///" + absolutePath;
/*  31:    */       }
/*  32:    */     }
/*  33:    */     else {
/*  34: 92 */       urlString = "file:" + localPath;
/*  35:    */     }
/*  36: 94 */     return replaceChars(urlString);
/*  37:    */   }
/*  38:    */   
/*  39:    */   private static String getAbsolutePathFromRelativePath(String relativePath)
/*  40:    */   {
/*  41:105 */     return new File(relativePath).getAbsolutePath();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static boolean isAbsoluteURI(String systemId)
/*  45:    */   {
/*  46:127 */     if (isWindowsAbsolutePath(systemId)) {
/*  47:128 */       return false;
/*  48:    */     }
/*  49:131 */     int fragmentIndex = systemId.indexOf('#');
/*  50:132 */     int queryIndex = systemId.indexOf('?');
/*  51:133 */     int slashIndex = systemId.indexOf('/');
/*  52:134 */     int colonIndex = systemId.indexOf(':');
/*  53:    */     
/*  54:    */ 
/*  55:137 */     int index = systemId.length() - 1;
/*  56:138 */     if (fragmentIndex > 0) {
/*  57:139 */       index = fragmentIndex;
/*  58:    */     }
/*  59:140 */     if ((queryIndex > 0) && (queryIndex < index)) {
/*  60:141 */       index = queryIndex;
/*  61:    */     }
/*  62:142 */     if ((slashIndex > 0) && (slashIndex < index)) {
/*  63:143 */       index = slashIndex;
/*  64:    */     }
/*  65:145 */     return (colonIndex > 0) && (colonIndex < index);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static boolean isAbsolutePath(String systemId)
/*  69:    */   {
/*  70:157 */     if (systemId == null) {
/*  71:158 */       return false;
/*  72:    */     }
/*  73:159 */     File file = new File(systemId);
/*  74:160 */     return file.isAbsolute();
/*  75:    */   }
/*  76:    */   
/*  77:    */   private static boolean isWindowsAbsolutePath(String systemId)
/*  78:    */   {
/*  79:172 */     if (!isAbsolutePath(systemId)) {
/*  80:173 */       return false;
/*  81:    */     }
/*  82:175 */     if ((systemId.length() > 2) && (systemId.charAt(1) == ':') && (Character.isLetter(systemId.charAt(0))) && ((systemId.charAt(2) == '\\') || (systemId.charAt(2) == '/'))) {
/*  83:179 */       return true;
/*  84:    */     }
/*  85:181 */     return false;
/*  86:    */   }
/*  87:    */   
/*  88:    */   private static String replaceChars(String str)
/*  89:    */   {
/*  90:193 */     StringBuffer buf = new StringBuffer(str);
/*  91:194 */     int length = buf.length();
/*  92:195 */     for (int i = 0; i < length; i++)
/*  93:    */     {
/*  94:197 */       char currentChar = buf.charAt(i);
/*  95:199 */       if (currentChar == ' ')
/*  96:    */       {
/*  97:201 */         buf.setCharAt(i, '%');
/*  98:202 */         buf.insert(i + 1, "20");
/*  99:203 */         length += 2;
/* 100:204 */         i += 2;
/* 101:    */       }
/* 102:207 */       else if (currentChar == '\\')
/* 103:    */       {
/* 104:209 */         buf.setCharAt(i, '/');
/* 105:    */       }
/* 106:    */     }
/* 107:213 */     return buf.toString();
/* 108:    */   }
/* 109:    */   
/* 110:    */   public static String getAbsoluteURI(String systemId)
/* 111:    */   {
/* 112:225 */     String absoluteURI = systemId;
/* 113:226 */     if (isAbsoluteURI(systemId))
/* 114:    */     {
/* 115:229 */       if (systemId.startsWith("file:"))
/* 116:    */       {
/* 117:231 */         String str = systemId.substring(5);
/* 118:235 */         if ((str != null) && (str.startsWith("/")))
/* 119:    */         {
/* 120:237 */           if ((str.startsWith("///")) || (!str.startsWith("//")))
/* 121:    */           {
/* 122:241 */             int secondColonIndex = systemId.indexOf(':', 5);
/* 123:242 */             if (secondColonIndex > 0)
/* 124:    */             {
/* 125:244 */               String localPath = systemId.substring(secondColonIndex - 1);
/* 126:    */               try
/* 127:    */               {
/* 128:246 */                 if (!isAbsolutePath(localPath)) {
/* 129:247 */                   absoluteURI = systemId.substring(0, secondColonIndex - 1) + getAbsolutePathFromRelativePath(localPath);
/* 130:    */                 }
/* 131:    */               }
/* 132:    */               catch (SecurityException se)
/* 133:    */               {
/* 134:251 */                 return systemId;
/* 135:    */               }
/* 136:    */             }
/* 137:    */           }
/* 138:    */         }
/* 139:    */         else {
/* 140:258 */           return getAbsoluteURIFromRelative(systemId.substring(5));
/* 141:    */         }
/* 142:261 */         return replaceChars(absoluteURI);
/* 143:    */       }
/* 144:264 */       return systemId;
/* 145:    */     }
/* 146:267 */     return getAbsoluteURIFromRelative(systemId);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public static String getAbsoluteURI(String urlString, String base)
/* 150:    */     throws TransformerException
/* 151:    */   {
/* 152:284 */     if (base == null) {
/* 153:285 */       return getAbsoluteURI(urlString);
/* 154:    */     }
/* 155:287 */     String absoluteBase = getAbsoluteURI(base);
/* 156:288 */     URI uri = null;
/* 157:    */     try
/* 158:    */     {
/* 159:291 */       URI baseURI = new URI(absoluteBase);
/* 160:292 */       uri = new URI(baseURI, urlString);
/* 161:    */     }
/* 162:    */     catch (URI.MalformedURIException mue)
/* 163:    */     {
/* 164:296 */       throw new TransformerException(mue);
/* 165:    */     }
/* 166:299 */     return replaceChars(uri.toString());
/* 167:    */   }
/* 168:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.utils.SystemIDResolver
 * JD-Core Version:    0.7.0.1
 */