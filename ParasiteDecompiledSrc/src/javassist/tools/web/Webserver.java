/*   1:    */ package javassist.tools.web;
/*   2:    */ 
/*   3:    */ import java.io.BufferedInputStream;
/*   4:    */ import java.io.BufferedOutputStream;
/*   5:    */ import java.io.ByteArrayOutputStream;
/*   6:    */ import java.io.File;
/*   7:    */ import java.io.FileInputStream;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.io.InputStream;
/*  10:    */ import java.io.OutputStream;
/*  11:    */ import java.io.PrintStream;
/*  12:    */ import java.net.InetAddress;
/*  13:    */ import java.net.ServerSocket;
/*  14:    */ import java.net.Socket;
/*  15:    */ import java.util.Date;
/*  16:    */ import javassist.CannotCompileException;
/*  17:    */ import javassist.ClassPool;
/*  18:    */ import javassist.CtClass;
/*  19:    */ import javassist.NotFoundException;
/*  20:    */ import javassist.Translator;
/*  21:    */ 
/*  22:    */ public class Webserver
/*  23:    */ {
/*  24:    */   private ServerSocket socket;
/*  25:    */   private ClassPool classPool;
/*  26:    */   protected Translator translator;
/*  27: 41 */   private static final byte[] endofline = { 13, 10 };
/*  28:    */   private static final int typeHtml = 1;
/*  29:    */   private static final int typeClass = 2;
/*  30:    */   private static final int typeGif = 3;
/*  31:    */   private static final int typeJpeg = 4;
/*  32:    */   private static final int typeText = 5;
/*  33: 55 */   public String debugDir = null;
/*  34: 71 */   public String htmlfileBase = null;
/*  35:    */   
/*  36:    */   public static void main(String[] args)
/*  37:    */     throws IOException
/*  38:    */   {
/*  39: 78 */     if (args.length == 1)
/*  40:    */     {
/*  41: 79 */       Webserver web = new Webserver(args[0]);
/*  42: 80 */       web.run();
/*  43:    */     }
/*  44:    */     else
/*  45:    */     {
/*  46: 83 */       System.err.println("Usage: java javassist.tools.web.Webserver <port number>");
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Webserver(String port)
/*  51:    */     throws IOException
/*  52:    */   {
/*  53: 93 */     this(Integer.parseInt(port));
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Webserver(int port)
/*  57:    */     throws IOException
/*  58:    */   {
/*  59:102 */     this.socket = new ServerSocket(port);
/*  60:103 */     this.classPool = null;
/*  61:104 */     this.translator = null;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setClassPool(ClassPool loader)
/*  65:    */   {
/*  66:112 */     this.classPool = loader;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void addTranslator(ClassPool cp, Translator t)
/*  70:    */     throws NotFoundException, CannotCompileException
/*  71:    */   {
/*  72:126 */     this.classPool = cp;
/*  73:127 */     this.translator = t;
/*  74:128 */     t.start(this.classPool);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void end()
/*  78:    */     throws IOException
/*  79:    */   {
/*  80:135 */     this.socket.close();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void logging(String msg)
/*  84:    */   {
/*  85:142 */     System.out.println(msg);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void logging(String msg1, String msg2)
/*  89:    */   {
/*  90:149 */     System.out.print(msg1);
/*  91:150 */     System.out.print(" ");
/*  92:151 */     System.out.println(msg2);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void logging(String msg1, String msg2, String msg3)
/*  96:    */   {
/*  97:158 */     System.out.print(msg1);
/*  98:159 */     System.out.print(" ");
/*  99:160 */     System.out.print(msg2);
/* 100:161 */     System.out.print(" ");
/* 101:162 */     System.out.println(msg3);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void logging2(String msg)
/* 105:    */   {
/* 106:169 */     System.out.print("    ");
/* 107:170 */     System.out.println(msg);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void run()
/* 111:    */   {
/* 112:177 */     System.err.println("ready to service...");
/* 113:    */     try
/* 114:    */     {
/* 115:    */       for (;;)
/* 116:    */       {
/* 117:180 */         ServiceThread th = new ServiceThread(this, this.socket.accept());
/* 118:181 */         th.start();
/* 119:    */       }
/* 120:    */     }
/* 121:    */     catch (IOException e)
/* 122:    */     {
/* 123:184 */       logging(e.toString());
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   final void process(Socket clnt)
/* 128:    */     throws IOException
/* 129:    */   {
/* 130:189 */     InputStream in = new BufferedInputStream(clnt.getInputStream());
/* 131:190 */     String cmd = readLine(in);
/* 132:191 */     logging(clnt.getInetAddress().getHostName(), new Date().toString(), cmd);
/* 133:193 */     while (skipLine(in) > 0) {}
/* 134:196 */     OutputStream out = new BufferedOutputStream(clnt.getOutputStream());
/* 135:    */     try
/* 136:    */     {
/* 137:198 */       doReply(in, out, cmd);
/* 138:    */     }
/* 139:    */     catch (BadHttpRequest e)
/* 140:    */     {
/* 141:201 */       replyError(out, e);
/* 142:    */     }
/* 143:204 */     out.flush();
/* 144:205 */     in.close();
/* 145:206 */     out.close();
/* 146:207 */     clnt.close();
/* 147:    */   }
/* 148:    */   
/* 149:    */   private String readLine(InputStream in)
/* 150:    */     throws IOException
/* 151:    */   {
/* 152:211 */     StringBuffer buf = new StringBuffer();
/* 153:    */     int c;
/* 154:213 */     while (((c = in.read()) >= 0) && (c != 13)) {
/* 155:214 */       buf.append((char)c);
/* 156:    */     }
/* 157:216 */     in.read();
/* 158:217 */     return buf.toString();
/* 159:    */   }
/* 160:    */   
/* 161:    */   private int skipLine(InputStream in)
/* 162:    */     throws IOException
/* 163:    */   {
/* 164:222 */     int len = 0;
/* 165:    */     int c;
/* 166:223 */     while (((c = in.read()) >= 0) && (c != 13)) {
/* 167:224 */       len++;
/* 168:    */     }
/* 169:226 */     in.read();
/* 170:227 */     return len;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void doReply(InputStream in, OutputStream out, String cmd)
/* 174:    */     throws IOException, BadHttpRequest
/* 175:    */   {
/* 176:    */     String filename;
/* 177:243 */     if (cmd.startsWith("GET /"))
/* 178:    */     {
/* 179:    */       String urlName;
/* 180:244 */       filename = urlName = cmd.substring(5, cmd.indexOf(' ', 5));
/* 181:    */     }
/* 182:    */     else
/* 183:    */     {
/* 184:246 */       throw new BadHttpRequest();
/* 185:    */     }
/* 186:    */     String urlName;
/* 187:    */     String filename;
/* 188:    */     int fileType;
/* 189:    */     int fileType;
/* 190:248 */     if (filename.endsWith(".class"))
/* 191:    */     {
/* 192:249 */       fileType = 2;
/* 193:    */     }
/* 194:    */     else
/* 195:    */     {
/* 196:    */       int fileType;
/* 197:250 */       if ((filename.endsWith(".html")) || (filename.endsWith(".htm")))
/* 198:    */       {
/* 199:251 */         fileType = 1;
/* 200:    */       }
/* 201:    */       else
/* 202:    */       {
/* 203:    */         int fileType;
/* 204:252 */         if (filename.endsWith(".gif"))
/* 205:    */         {
/* 206:253 */           fileType = 3;
/* 207:    */         }
/* 208:    */         else
/* 209:    */         {
/* 210:    */           int fileType;
/* 211:254 */           if (filename.endsWith(".jpg")) {
/* 212:255 */             fileType = 4;
/* 213:    */           } else {
/* 214:257 */             fileType = 5;
/* 215:    */           }
/* 216:    */         }
/* 217:    */       }
/* 218:    */     }
/* 219:259 */     int len = filename.length();
/* 220:260 */     if ((fileType == 2) && (letUsersSendClassfile(out, filename, len))) {
/* 221:262 */       return;
/* 222:    */     }
/* 223:264 */     checkFilename(filename, len);
/* 224:265 */     if (this.htmlfileBase != null) {
/* 225:266 */       filename = this.htmlfileBase + filename;
/* 226:    */     }
/* 227:268 */     if (File.separatorChar != '/') {
/* 228:269 */       filename = filename.replace('/', File.separatorChar);
/* 229:    */     }
/* 230:271 */     File file = new File(filename);
/* 231:272 */     if (file.canRead())
/* 232:    */     {
/* 233:273 */       sendHeader(out, file.length(), fileType);
/* 234:274 */       FileInputStream fin = new FileInputStream(file);
/* 235:275 */       byte[] filebuffer = new byte[4096];
/* 236:    */       for (;;)
/* 237:    */       {
/* 238:277 */         len = fin.read(filebuffer);
/* 239:278 */         if (len <= 0) {
/* 240:    */           break;
/* 241:    */         }
/* 242:281 */         out.write(filebuffer, 0, len);
/* 243:    */       }
/* 244:284 */       fin.close();
/* 245:285 */       return;
/* 246:    */     }
/* 247:291 */     if (fileType == 2)
/* 248:    */     {
/* 249:292 */       InputStream fin = getClass().getResourceAsStream("/" + urlName);
/* 250:294 */       if (fin != null)
/* 251:    */       {
/* 252:295 */         ByteArrayOutputStream barray = new ByteArrayOutputStream();
/* 253:296 */         byte[] filebuffer = new byte[4096];
/* 254:    */         for (;;)
/* 255:    */         {
/* 256:298 */           len = fin.read(filebuffer);
/* 257:299 */           if (len <= 0) {
/* 258:    */             break;
/* 259:    */           }
/* 260:302 */           barray.write(filebuffer, 0, len);
/* 261:    */         }
/* 262:305 */         byte[] classfile = barray.toByteArray();
/* 263:306 */         sendHeader(out, classfile.length, 2);
/* 264:307 */         out.write(classfile);
/* 265:308 */         fin.close();
/* 266:309 */         return;
/* 267:    */       }
/* 268:    */     }
/* 269:313 */     throw new BadHttpRequest();
/* 270:    */   }
/* 271:    */   
/* 272:    */   private void checkFilename(String filename, int len)
/* 273:    */     throws BadHttpRequest
/* 274:    */   {
/* 275:319 */     for (int i = 0; i < len; i++)
/* 276:    */     {
/* 277:320 */       char c = filename.charAt(i);
/* 278:321 */       if ((!Character.isJavaIdentifierPart(c)) && (c != '.') && (c != '/')) {
/* 279:322 */         throw new BadHttpRequest();
/* 280:    */       }
/* 281:    */     }
/* 282:325 */     if (filename.indexOf("..") >= 0) {
/* 283:326 */       throw new BadHttpRequest();
/* 284:    */     }
/* 285:    */   }
/* 286:    */   
/* 287:    */   private boolean letUsersSendClassfile(OutputStream out, String filename, int length)
/* 288:    */     throws IOException, BadHttpRequest
/* 289:    */   {
/* 290:333 */     if (this.classPool == null) {
/* 291:334 */       return false;
/* 292:    */     }
/* 293:337 */     String classname = filename.substring(0, length - 6).replace('/', '.');
/* 294:    */     byte[] classfile;
/* 295:    */     try
/* 296:    */     {
/* 297:340 */       if (this.translator != null) {
/* 298:341 */         this.translator.onLoad(this.classPool, classname);
/* 299:    */       }
/* 300:343 */       CtClass c = this.classPool.get(classname);
/* 301:344 */       classfile = c.toBytecode();
/* 302:345 */       if (this.debugDir != null) {
/* 303:346 */         c.writeFile(this.debugDir);
/* 304:    */       }
/* 305:    */     }
/* 306:    */     catch (Exception e)
/* 307:    */     {
/* 308:349 */       throw new BadHttpRequest(e);
/* 309:    */     }
/* 310:352 */     sendHeader(out, classfile.length, 2);
/* 311:353 */     out.write(classfile);
/* 312:354 */     return true;
/* 313:    */   }
/* 314:    */   
/* 315:    */   private void sendHeader(OutputStream out, long dataLength, int filetype)
/* 316:    */     throws IOException
/* 317:    */   {
/* 318:360 */     out.write("HTTP/1.0 200 OK".getBytes());
/* 319:361 */     out.write(endofline);
/* 320:362 */     out.write("Content-Length: ".getBytes());
/* 321:363 */     out.write(Long.toString(dataLength).getBytes());
/* 322:364 */     out.write(endofline);
/* 323:365 */     if (filetype == 2) {
/* 324:366 */       out.write("Content-Type: application/octet-stream".getBytes());
/* 325:367 */     } else if (filetype == 1) {
/* 326:368 */       out.write("Content-Type: text/html".getBytes());
/* 327:369 */     } else if (filetype == 3) {
/* 328:370 */       out.write("Content-Type: image/gif".getBytes());
/* 329:371 */     } else if (filetype == 4) {
/* 330:372 */       out.write("Content-Type: image/jpg".getBytes());
/* 331:373 */     } else if (filetype == 5) {
/* 332:374 */       out.write("Content-Type: text/plain".getBytes());
/* 333:    */     }
/* 334:376 */     out.write(endofline);
/* 335:377 */     out.write(endofline);
/* 336:    */   }
/* 337:    */   
/* 338:    */   private void replyError(OutputStream out, BadHttpRequest e)
/* 339:    */     throws IOException
/* 340:    */   {
/* 341:383 */     logging2("bad request: " + e.toString());
/* 342:384 */     out.write("HTTP/1.0 400 Bad Request".getBytes());
/* 343:385 */     out.write(endofline);
/* 344:386 */     out.write(endofline);
/* 345:387 */     out.write("<H1>Bad Request</H1>".getBytes());
/* 346:    */   }
/* 347:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.tools.web.Webserver
 * JD-Core Version:    0.7.0.1
 */