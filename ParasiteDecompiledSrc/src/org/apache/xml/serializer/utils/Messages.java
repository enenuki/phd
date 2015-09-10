/*   1:    */ package org.apache.xml.serializer.utils;
/*   2:    */ 
/*   3:    */ import java.text.MessageFormat;
/*   4:    */ import java.util.ListResourceBundle;
/*   5:    */ import java.util.Locale;
/*   6:    */ import java.util.MissingResourceException;
/*   7:    */ import java.util.ResourceBundle;
/*   8:    */ 
/*   9:    */ public final class Messages
/*  10:    */ {
/*  11:100 */   private final Locale m_locale = Locale.getDefault();
/*  12:    */   private ListResourceBundle m_resourceBundle;
/*  13:    */   private String m_resourceBundleName;
/*  14:    */   
/*  15:    */   Messages(String resourceBundle)
/*  16:    */   {
/*  17:124 */     this.m_resourceBundleName = resourceBundle;
/*  18:    */   }
/*  19:    */   
/*  20:    */   private Locale getLocale()
/*  21:    */   {
/*  22:148 */     return this.m_locale;
/*  23:    */   }
/*  24:    */   
/*  25:    */   private ListResourceBundle getResourceBundle()
/*  26:    */   {
/*  27:158 */     return this.m_resourceBundle;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public final String createMessage(String msgKey, Object[] args)
/*  31:    */   {
/*  32:174 */     if (this.m_resourceBundle == null) {
/*  33:175 */       this.m_resourceBundle = loadResourceBundle(this.m_resourceBundleName);
/*  34:    */     }
/*  35:177 */     if (this.m_resourceBundle != null) {
/*  36:179 */       return createMsg(this.m_resourceBundle, msgKey, args);
/*  37:    */     }
/*  38:182 */     return "Could not load the resource bundles: " + this.m_resourceBundleName;
/*  39:    */   }
/*  40:    */   
/*  41:    */   private final String createMsg(ListResourceBundle fResourceBundle, String msgKey, Object[] args)
/*  42:    */   {
/*  43:205 */     String fmsg = null;
/*  44:206 */     boolean throwex = false;
/*  45:207 */     String msg = null;
/*  46:209 */     if (msgKey != null) {
/*  47:210 */       msg = fResourceBundle.getString(msgKey);
/*  48:    */     } else {
/*  49:212 */       msgKey = "";
/*  50:    */     }
/*  51:214 */     if (msg == null)
/*  52:    */     {
/*  53:216 */       throwex = true;
/*  54:    */       try
/*  55:    */       {
/*  56:223 */         msg = MessageFormat.format("BAD_MSGKEY", new Object[] { msgKey, this.m_resourceBundleName });
/*  57:    */       }
/*  58:    */       catch (Exception e)
/*  59:    */       {
/*  60:233 */         msg = "The message key '" + msgKey + "' is not in the message class '" + this.m_resourceBundleName + "'";
/*  61:    */       }
/*  62:    */     }
/*  63:240 */     else if (args != null)
/*  64:    */     {
/*  65:    */       try
/*  66:    */       {
/*  67:247 */         int n = args.length;
/*  68:249 */         for (int i = 0; i < n; i++) {
/*  69:251 */           if (null == args[i]) {
/*  70:252 */             args[i] = "";
/*  71:    */           }
/*  72:    */         }
/*  73:255 */         fmsg = MessageFormat.format(msg, args);
/*  74:    */       }
/*  75:    */       catch (Exception e)
/*  76:    */       {
/*  77:260 */         throwex = true;
/*  78:    */         try
/*  79:    */         {
/*  80:264 */           fmsg = MessageFormat.format("BAD_MSGFORMAT", new Object[] { msgKey, this.m_resourceBundleName });
/*  81:    */           
/*  82:    */ 
/*  83:    */ 
/*  84:268 */           fmsg = fmsg + " " + msg;
/*  85:    */         }
/*  86:    */         catch (Exception formatfailed)
/*  87:    */         {
/*  88:274 */           fmsg = "The format of message '" + msgKey + "' in message class '" + this.m_resourceBundleName + "' failed.";
/*  89:    */         }
/*  90:    */       }
/*  91:    */     }
/*  92:    */     else
/*  93:    */     {
/*  94:284 */       fmsg = msg;
/*  95:    */     }
/*  96:286 */     if (throwex) {
/*  97:288 */       throw new RuntimeException(fmsg);
/*  98:    */     }
/*  99:291 */     return fmsg;
/* 100:    */   }
/* 101:    */   
/* 102:    */   private ListResourceBundle loadResourceBundle(String resourceBundle)
/* 103:    */     throws MissingResourceException
/* 104:    */   {
/* 105:307 */     this.m_resourceBundleName = resourceBundle;
/* 106:308 */     Locale locale = getLocale();
/* 107:    */     ListResourceBundle lrb;
/* 108:    */     try
/* 109:    */     {
/* 110:315 */       ResourceBundle rb = ResourceBundle.getBundle(this.m_resourceBundleName, locale);
/* 111:    */       
/* 112:317 */       lrb = (ListResourceBundle)rb;
/* 113:    */     }
/* 114:    */     catch (MissingResourceException e)
/* 115:    */     {
/* 116:    */       try
/* 117:    */       {
/* 118:326 */         lrb = (ListResourceBundle)ResourceBundle.getBundle(this.m_resourceBundleName, new Locale("en", "US"));
/* 119:    */       }
/* 120:    */       catch (MissingResourceException e2)
/* 121:    */       {
/* 122:336 */         throw new MissingResourceException("Could not load any resource bundles." + this.m_resourceBundleName, this.m_resourceBundleName, "");
/* 123:    */       }
/* 124:    */     }
/* 125:342 */     this.m_resourceBundle = lrb;
/* 126:343 */     return lrb;
/* 127:    */   }
/* 128:    */   
/* 129:    */   private static String getResourceSuffix(Locale locale)
/* 130:    */   {
/* 131:358 */     String suffix = "_" + locale.getLanguage();
/* 132:359 */     String country = locale.getCountry();
/* 133:361 */     if (country.equals("TW")) {
/* 134:362 */       suffix = suffix + "_" + country;
/* 135:    */     }
/* 136:364 */     return suffix;
/* 137:    */   }
/* 138:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.utils.Messages
 * JD-Core Version:    0.7.0.1
 */