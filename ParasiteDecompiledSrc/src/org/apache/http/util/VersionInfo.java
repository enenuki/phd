/*   1:    */ package org.apache.http.util;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.Properties;
/*   8:    */ 
/*   9:    */ public class VersionInfo
/*  10:    */ {
/*  11:    */   public static final String UNAVAILABLE = "UNAVAILABLE";
/*  12:    */   public static final String VERSION_PROPERTY_FILE = "version.properties";
/*  13:    */   public static final String PROPERTY_MODULE = "info.module";
/*  14:    */   public static final String PROPERTY_RELEASE = "info.release";
/*  15:    */   public static final String PROPERTY_TIMESTAMP = "info.timestamp";
/*  16:    */   private final String infoPackage;
/*  17:    */   private final String infoModule;
/*  18:    */   private final String infoRelease;
/*  19:    */   private final String infoTimestamp;
/*  20:    */   private final String infoClassloader;
/*  21:    */   
/*  22:    */   protected VersionInfo(String pckg, String module, String release, String time, String clsldr)
/*  23:    */   {
/*  24: 90 */     if (pckg == null) {
/*  25: 91 */       throw new IllegalArgumentException("Package identifier must not be null.");
/*  26:    */     }
/*  27: 95 */     this.infoPackage = pckg;
/*  28: 96 */     this.infoModule = (module != null ? module : "UNAVAILABLE");
/*  29: 97 */     this.infoRelease = (release != null ? release : "UNAVAILABLE");
/*  30: 98 */     this.infoTimestamp = (time != null ? time : "UNAVAILABLE");
/*  31: 99 */     this.infoClassloader = (clsldr != null ? clsldr : "UNAVAILABLE");
/*  32:    */   }
/*  33:    */   
/*  34:    */   public final String getPackage()
/*  35:    */   {
/*  36:110 */     return this.infoPackage;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public final String getModule()
/*  40:    */   {
/*  41:120 */     return this.infoModule;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public final String getRelease()
/*  45:    */   {
/*  46:130 */     return this.infoRelease;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public final String getTimestamp()
/*  50:    */   {
/*  51:140 */     return this.infoTimestamp;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public final String getClassloader()
/*  55:    */   {
/*  56:152 */     return this.infoClassloader;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String toString()
/*  60:    */   {
/*  61:162 */     StringBuffer sb = new StringBuffer(20 + this.infoPackage.length() + this.infoModule.length() + this.infoRelease.length() + this.infoTimestamp.length() + this.infoClassloader.length());
/*  62:    */     
/*  63:    */ 
/*  64:    */ 
/*  65:    */ 
/*  66:167 */     sb.append("VersionInfo(").append(this.infoPackage).append(':').append(this.infoModule);
/*  67:172 */     if (!"UNAVAILABLE".equals(this.infoRelease)) {
/*  68:173 */       sb.append(':').append(this.infoRelease);
/*  69:    */     }
/*  70:174 */     if (!"UNAVAILABLE".equals(this.infoTimestamp)) {
/*  71:175 */       sb.append(':').append(this.infoTimestamp);
/*  72:    */     }
/*  73:177 */     sb.append(')');
/*  74:179 */     if (!"UNAVAILABLE".equals(this.infoClassloader)) {
/*  75:180 */       sb.append('@').append(this.infoClassloader);
/*  76:    */     }
/*  77:182 */     return sb.toString();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static final VersionInfo[] loadVersionInfo(String[] pckgs, ClassLoader clsldr)
/*  81:    */   {
/*  82:198 */     if (pckgs == null) {
/*  83:199 */       throw new IllegalArgumentException("Package identifier list must not be null.");
/*  84:    */     }
/*  85:203 */     ArrayList vil = new ArrayList(pckgs.length);
/*  86:204 */     for (int i = 0; i < pckgs.length; i++)
/*  87:    */     {
/*  88:205 */       VersionInfo vi = loadVersionInfo(pckgs[i], clsldr);
/*  89:206 */       if (vi != null) {
/*  90:207 */         vil.add(vi);
/*  91:    */       }
/*  92:    */     }
/*  93:210 */     return (VersionInfo[])vil.toArray(new VersionInfo[vil.size()]);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static final VersionInfo loadVersionInfo(String pckg, ClassLoader clsldr)
/*  97:    */   {
/*  98:228 */     if (pckg == null) {
/*  99:229 */       throw new IllegalArgumentException("Package identifier must not be null.");
/* 100:    */     }
/* 101:233 */     if (clsldr == null) {
/* 102:234 */       clsldr = Thread.currentThread().getContextClassLoader();
/* 103:    */     }
/* 104:236 */     Properties vip = null;
/* 105:    */     try
/* 106:    */     {
/* 107:240 */       InputStream is = clsldr.getResourceAsStream(pckg.replace('.', '/') + "/" + "version.properties");
/* 108:242 */       if (is != null) {
/* 109:    */         try
/* 110:    */         {
/* 111:244 */           Properties props = new Properties();
/* 112:245 */           props.load(is);
/* 113:246 */           vip = props;
/* 114:    */         }
/* 115:    */         finally
/* 116:    */         {
/* 117:248 */           is.close();
/* 118:    */         }
/* 119:    */       }
/* 120:    */     }
/* 121:    */     catch (IOException ex) {}
/* 122:255 */     VersionInfo result = null;
/* 123:256 */     if (vip != null) {
/* 124:257 */       result = fromMap(pckg, vip, clsldr);
/* 125:    */     }
/* 126:259 */     return result;
/* 127:    */   }
/* 128:    */   
/* 129:    */   protected static final VersionInfo fromMap(String pckg, Map info, ClassLoader clsldr)
/* 130:    */   {
/* 131:275 */     if (pckg == null) {
/* 132:276 */       throw new IllegalArgumentException("Package identifier must not be null.");
/* 133:    */     }
/* 134:280 */     String module = null;
/* 135:281 */     String release = null;
/* 136:282 */     String timestamp = null;
/* 137:284 */     if (info != null)
/* 138:    */     {
/* 139:285 */       module = (String)info.get("info.module");
/* 140:286 */       if ((module != null) && (module.length() < 1)) {
/* 141:287 */         module = null;
/* 142:    */       }
/* 143:289 */       release = (String)info.get("info.release");
/* 144:290 */       if ((release != null) && ((release.length() < 1) || (release.equals("${pom.version}")))) {
/* 145:292 */         release = null;
/* 146:    */       }
/* 147:294 */       timestamp = (String)info.get("info.timestamp");
/* 148:295 */       if ((timestamp != null) && ((timestamp.length() < 1) || (timestamp.equals("${mvn.timestamp}")))) {
/* 149:299 */         timestamp = null;
/* 150:    */       }
/* 151:    */     }
/* 152:302 */     String clsldrstr = null;
/* 153:303 */     if (clsldr != null) {
/* 154:304 */       clsldrstr = clsldr.toString();
/* 155:    */     }
/* 156:306 */     return new VersionInfo(pckg, module, release, timestamp, clsldrstr);
/* 157:    */   }
/* 158:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.util.VersionInfo
 * JD-Core Version:    0.7.0.1
 */