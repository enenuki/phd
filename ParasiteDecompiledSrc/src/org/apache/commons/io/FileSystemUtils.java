/*   1:    */ package org.apache.commons.io;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.InputStream;
/*   7:    */ import java.io.InputStreamReader;
/*   8:    */ import java.io.OutputStream;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Arrays;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Locale;
/*  13:    */ import java.util.StringTokenizer;
/*  14:    */ 
/*  15:    */ public class FileSystemUtils
/*  16:    */ {
/*  17: 54 */   private static final FileSystemUtils INSTANCE = new FileSystemUtils();
/*  18:    */   private static final int INIT_PROBLEM = -1;
/*  19:    */   private static final int OTHER = 0;
/*  20:    */   private static final int WINDOWS = 1;
/*  21:    */   private static final int UNIX = 2;
/*  22:    */   private static final int POSIX_UNIX = 3;
/*  23:    */   private static final int OS;
/*  24:    */   private static final String DF;
/*  25:    */   
/*  26:    */   static
/*  27:    */   {
/*  28: 74 */     int os = 0;
/*  29: 75 */     String dfPath = "df";
/*  30:    */     try
/*  31:    */     {
/*  32: 77 */       String osName = System.getProperty("os.name");
/*  33: 78 */       if (osName == null) {
/*  34: 79 */         throw new IOException("os.name not found");
/*  35:    */       }
/*  36: 81 */       osName = osName.toLowerCase(Locale.ENGLISH);
/*  37: 83 */       if (osName.indexOf("windows") != -1)
/*  38:    */       {
/*  39: 84 */         os = 1;
/*  40:    */       }
/*  41: 85 */       else if ((osName.indexOf("linux") != -1) || (osName.indexOf("mpe/ix") != -1) || (osName.indexOf("freebsd") != -1) || (osName.indexOf("irix") != -1) || (osName.indexOf("digital unix") != -1) || (osName.indexOf("unix") != -1) || (osName.indexOf("mac os x") != -1))
/*  42:    */       {
/*  43: 92 */         os = 2;
/*  44:    */       }
/*  45: 93 */       else if ((osName.indexOf("sun os") != -1) || (osName.indexOf("sunos") != -1) || (osName.indexOf("solaris") != -1))
/*  46:    */       {
/*  47: 96 */         os = 3;
/*  48: 97 */         dfPath = "/usr/xpg4/bin/df";
/*  49:    */       }
/*  50: 98 */       else if ((osName.indexOf("hp-ux") != -1) || (osName.indexOf("aix") != -1))
/*  51:    */       {
/*  52:100 */         os = 3;
/*  53:    */       }
/*  54:    */       else
/*  55:    */       {
/*  56:102 */         os = 0;
/*  57:    */       }
/*  58:    */     }
/*  59:    */     catch (Exception ex)
/*  60:    */     {
/*  61:106 */       os = -1;
/*  62:    */     }
/*  63:108 */     OS = os;
/*  64:109 */     DF = dfPath;
/*  65:    */   }
/*  66:    */   
/*  67:    */   @Deprecated
/*  68:    */   public static long freeSpace(String path)
/*  69:    */     throws IOException
/*  70:    */   {
/*  71:148 */     return INSTANCE.freeSpaceOS(path, OS, false, -1L);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static long freeSpaceKb(String path)
/*  75:    */     throws IOException
/*  76:    */   {
/*  77:177 */     return freeSpaceKb(path, -1L);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static long freeSpaceKb(String path, long timeout)
/*  81:    */     throws IOException
/*  82:    */   {
/*  83:206 */     return INSTANCE.freeSpaceOS(path, OS, true, timeout);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public static long freeSpaceKb()
/*  87:    */     throws IOException
/*  88:    */   {
/*  89:222 */     return freeSpaceKb(-1L);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static long freeSpaceKb(long timeout)
/*  93:    */     throws IOException
/*  94:    */   {
/*  95:240 */     return freeSpaceKb(new File(".").getAbsolutePath(), timeout);
/*  96:    */   }
/*  97:    */   
/*  98:    */   long freeSpaceOS(String path, int os, boolean kb, long timeout)
/*  99:    */     throws IOException
/* 100:    */   {
/* 101:265 */     if (path == null) {
/* 102:266 */       throw new IllegalArgumentException("Path must not be empty");
/* 103:    */     }
/* 104:268 */     switch (os)
/* 105:    */     {
/* 106:    */     case 1: 
/* 107:270 */       return kb ? freeSpaceWindows(path, timeout) / 1024L : freeSpaceWindows(path, timeout);
/* 108:    */     case 2: 
/* 109:272 */       return freeSpaceUnix(path, kb, false, timeout);
/* 110:    */     case 3: 
/* 111:274 */       return freeSpaceUnix(path, kb, true, timeout);
/* 112:    */     case 0: 
/* 113:276 */       throw new IllegalStateException("Unsupported operating system");
/* 114:    */     }
/* 115:278 */     throw new IllegalStateException("Exception caught when determining operating system");
/* 116:    */   }
/* 117:    */   
/* 118:    */   long freeSpaceWindows(String path, long timeout)
/* 119:    */     throws IOException
/* 120:    */   {
/* 121:294 */     path = FilenameUtils.normalize(path, false);
/* 122:295 */     if ((path.length() > 0) && (path.charAt(0) != '"')) {
/* 123:296 */       path = "\"" + path + "\"";
/* 124:    */     }
/* 125:300 */     String[] cmdAttribs = { "cmd.exe", "/C", "dir /-c " + path };
/* 126:    */     
/* 127:    */ 
/* 128:303 */     List<String> lines = performCommand(cmdAttribs, 2147483647, timeout);
/* 129:309 */     for (int i = lines.size() - 1; i >= 0; i--)
/* 130:    */     {
/* 131:310 */       String line = (String)lines.get(i);
/* 132:311 */       if (line.length() > 0) {
/* 133:312 */         return parseDir(line, path);
/* 134:    */       }
/* 135:    */     }
/* 136:316 */     throw new IOException("Command line 'dir /-c' did not return any info for path '" + path + "'");
/* 137:    */   }
/* 138:    */   
/* 139:    */   long parseDir(String line, String path)
/* 140:    */     throws IOException
/* 141:    */   {
/* 142:334 */     int bytesStart = 0;
/* 143:335 */     int bytesEnd = 0;
/* 144:336 */     int j = line.length() - 1;
/* 145:337 */     while (j >= 0)
/* 146:    */     {
/* 147:338 */       char c = line.charAt(j);
/* 148:339 */       if (Character.isDigit(c))
/* 149:    */       {
/* 150:342 */         bytesEnd = j + 1;
/* 151:343 */         break;
/* 152:    */       }
/* 153:345 */       j--;
/* 154:    */     }
/* 155:347 */     while (j >= 0)
/* 156:    */     {
/* 157:348 */       char c = line.charAt(j);
/* 158:349 */       if ((!Character.isDigit(c)) && (c != ',') && (c != '.'))
/* 159:    */       {
/* 160:352 */         bytesStart = j + 1;
/* 161:353 */         break;
/* 162:    */       }
/* 163:355 */       j--;
/* 164:    */     }
/* 165:357 */     if (j < 0) {
/* 166:358 */       throw new IOException("Command line 'dir /-c' did not return valid info for path '" + path + "'");
/* 167:    */     }
/* 168:364 */     StringBuilder buf = new StringBuilder(line.substring(bytesStart, bytesEnd));
/* 169:365 */     for (int k = 0; k < buf.length(); k++) {
/* 170:366 */       if ((buf.charAt(k) == ',') || (buf.charAt(k) == '.')) {
/* 171:367 */         buf.deleteCharAt(k--);
/* 172:    */       }
/* 173:    */     }
/* 174:370 */     return parseBytes(buf.toString(), path);
/* 175:    */   }
/* 176:    */   
/* 177:    */   long freeSpaceUnix(String path, boolean kb, boolean posix, long timeout)
/* 178:    */     throws IOException
/* 179:    */   {
/* 180:386 */     if (path.length() == 0) {
/* 181:387 */       throw new IllegalArgumentException("Path must not be empty");
/* 182:    */     }
/* 183:391 */     String flags = "-";
/* 184:392 */     if (kb) {
/* 185:393 */       flags = flags + "k";
/* 186:    */     }
/* 187:395 */     if (posix) {
/* 188:396 */       flags = flags + "P";
/* 189:    */     }
/* 190:398 */     String[] cmdAttribs = { DF, flags.length() > 1 ? new String[] { DF, flags, path } : path };
/* 191:    */     
/* 192:    */ 
/* 193:    */ 
/* 194:402 */     List<String> lines = performCommand(cmdAttribs, 3, timeout);
/* 195:403 */     if (lines.size() < 2) {
/* 196:405 */       throw new IOException("Command line '" + DF + "' did not return info as expected " + "for path '" + path + "'- response was " + lines);
/* 197:    */     }
/* 198:409 */     String line2 = (String)lines.get(1);
/* 199:    */     
/* 200:    */ 
/* 201:412 */     StringTokenizer tok = new StringTokenizer(line2, " ");
/* 202:413 */     if (tok.countTokens() < 4)
/* 203:    */     {
/* 204:415 */       if ((tok.countTokens() == 1) && (lines.size() >= 3))
/* 205:    */       {
/* 206:416 */         String line3 = (String)lines.get(2);
/* 207:417 */         tok = new StringTokenizer(line3, " ");
/* 208:    */       }
/* 209:    */       else
/* 210:    */       {
/* 211:419 */         throw new IOException("Command line '" + DF + "' did not return data as expected " + "for path '" + path + "'- check path is valid");
/* 212:    */       }
/* 213:    */     }
/* 214:    */     else {
/* 215:424 */       tok.nextToken();
/* 216:    */     }
/* 217:426 */     tok.nextToken();
/* 218:427 */     tok.nextToken();
/* 219:428 */     String freeSpace = tok.nextToken();
/* 220:429 */     return parseBytes(freeSpace, path);
/* 221:    */   }
/* 222:    */   
/* 223:    */   long parseBytes(String freeSpace, String path)
/* 224:    */     throws IOException
/* 225:    */   {
/* 226:    */     try
/* 227:    */     {
/* 228:443 */       long bytes = Long.parseLong(freeSpace);
/* 229:444 */       if (bytes < 0L) {
/* 230:445 */         throw new IOException("Command line '" + DF + "' did not find free space in response " + "for path '" + path + "'- check path is valid");
/* 231:    */       }
/* 232:449 */       return bytes;
/* 233:    */     }
/* 234:    */     catch (NumberFormatException ex)
/* 235:    */     {
/* 236:452 */       throw new IOExceptionWithCause("Command line '" + DF + "' did not return numeric data as expected " + "for path '" + path + "'- check path is valid", ex);
/* 237:    */     }
/* 238:    */   }
/* 239:    */   
/* 240:    */   List<String> performCommand(String[] cmdAttribs, int max, long timeout)
/* 241:    */     throws IOException
/* 242:    */   {
/* 243:478 */     List<String> lines = new ArrayList(20);
/* 244:479 */     Process proc = null;
/* 245:480 */     InputStream in = null;
/* 246:481 */     OutputStream out = null;
/* 247:482 */     InputStream err = null;
/* 248:483 */     BufferedReader inr = null;
/* 249:    */     try
/* 250:    */     {
/* 251:486 */       Thread monitor = ThreadMonitor.start(timeout);
/* 252:    */       
/* 253:488 */       proc = openProcess(cmdAttribs);
/* 254:489 */       in = proc.getInputStream();
/* 255:490 */       out = proc.getOutputStream();
/* 256:491 */       err = proc.getErrorStream();
/* 257:492 */       inr = new BufferedReader(new InputStreamReader(in));
/* 258:493 */       String line = inr.readLine();
/* 259:494 */       while ((line != null) && (lines.size() < max))
/* 260:    */       {
/* 261:495 */         line = line.toLowerCase(Locale.ENGLISH).trim();
/* 262:496 */         lines.add(line);
/* 263:497 */         line = inr.readLine();
/* 264:    */       }
/* 265:500 */       proc.waitFor();
/* 266:    */       
/* 267:502 */       ThreadMonitor.stop(monitor);
/* 268:504 */       if (proc.exitValue() != 0) {
/* 269:506 */         throw new IOException("Command line returned OS error code '" + proc.exitValue() + "' for command " + Arrays.asList(cmdAttribs));
/* 270:    */       }
/* 271:510 */       if (lines.size() == 0) {
/* 272:512 */         throw new IOException("Command line did not return any info for command " + Arrays.asList(cmdAttribs));
/* 273:    */       }
/* 274:516 */       return lines;
/* 275:    */     }
/* 276:    */     catch (InterruptedException ex)
/* 277:    */     {
/* 278:519 */       throw new IOExceptionWithCause("Command line threw an InterruptedException for command " + Arrays.asList(cmdAttribs) + " timeout=" + timeout, ex);
/* 279:    */     }
/* 280:    */     finally
/* 281:    */     {
/* 282:523 */       IOUtils.closeQuietly(in);
/* 283:524 */       IOUtils.closeQuietly(out);
/* 284:525 */       IOUtils.closeQuietly(err);
/* 285:526 */       IOUtils.closeQuietly(inr);
/* 286:527 */       if (proc != null) {
/* 287:528 */         proc.destroy();
/* 288:    */       }
/* 289:    */     }
/* 290:    */   }
/* 291:    */   
/* 292:    */   Process openProcess(String[] cmdAttribs)
/* 293:    */     throws IOException
/* 294:    */   {
/* 295:541 */     return Runtime.getRuntime().exec(cmdAttribs);
/* 296:    */   }
/* 297:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.FileSystemUtils
 * JD-Core Version:    0.7.0.1
 */