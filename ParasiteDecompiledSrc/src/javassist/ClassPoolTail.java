/*   1:    */ package javassist;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.io.OutputStream;
/*   7:    */ import java.net.URL;
/*   8:    */ import java.util.Hashtable;
/*   9:    */ 
/*  10:    */ final class ClassPoolTail
/*  11:    */ {
/*  12:    */   protected ClassPathList pathList;
/*  13:    */   private Hashtable packages;
/*  14:    */   
/*  15:    */   public ClassPoolTail()
/*  16:    */   {
/*  17:183 */     this.pathList = null;
/*  18:184 */     this.packages = new Hashtable();
/*  19:    */   }
/*  20:    */   
/*  21:    */   public String toString()
/*  22:    */   {
/*  23:188 */     StringBuffer buf = new StringBuffer();
/*  24:189 */     buf.append("[class path: ");
/*  25:190 */     ClassPathList list = this.pathList;
/*  26:191 */     while (list != null)
/*  27:    */     {
/*  28:192 */       buf.append(list.path.toString());
/*  29:193 */       buf.append(File.pathSeparatorChar);
/*  30:194 */       list = list.next;
/*  31:    */     }
/*  32:197 */     buf.append(']');
/*  33:198 */     return buf.toString();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public synchronized ClassPath insertClassPath(ClassPath cp)
/*  37:    */   {
/*  38:202 */     this.pathList = new ClassPathList(cp, this.pathList);
/*  39:203 */     return cp;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public synchronized ClassPath appendClassPath(ClassPath cp)
/*  43:    */   {
/*  44:207 */     ClassPathList tail = new ClassPathList(cp, null);
/*  45:208 */     ClassPathList list = this.pathList;
/*  46:209 */     if (list == null)
/*  47:    */     {
/*  48:210 */       this.pathList = tail;
/*  49:    */     }
/*  50:    */     else
/*  51:    */     {
/*  52:212 */       while (list.next != null) {
/*  53:213 */         list = list.next;
/*  54:    */       }
/*  55:215 */       list.next = tail;
/*  56:    */     }
/*  57:218 */     return cp;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public synchronized void removeClassPath(ClassPath cp)
/*  61:    */   {
/*  62:222 */     ClassPathList list = this.pathList;
/*  63:223 */     if (list != null) {
/*  64:224 */       if (list.path == cp) {
/*  65:225 */         this.pathList = list.next;
/*  66:    */       } else {
/*  67:227 */         while (list.next != null) {
/*  68:228 */           if (list.next.path == cp) {
/*  69:229 */             list.next = list.next.next;
/*  70:    */           } else {
/*  71:231 */             list = list.next;
/*  72:    */           }
/*  73:    */         }
/*  74:    */       }
/*  75:    */     }
/*  76:234 */     cp.close();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public ClassPath appendSystemPath()
/*  80:    */   {
/*  81:238 */     return appendClassPath(new ClassClassPath());
/*  82:    */   }
/*  83:    */   
/*  84:    */   public ClassPath insertClassPath(String pathname)
/*  85:    */     throws NotFoundException
/*  86:    */   {
/*  87:244 */     return insertClassPath(makePathObject(pathname));
/*  88:    */   }
/*  89:    */   
/*  90:    */   public ClassPath appendClassPath(String pathname)
/*  91:    */     throws NotFoundException
/*  92:    */   {
/*  93:250 */     return appendClassPath(makePathObject(pathname));
/*  94:    */   }
/*  95:    */   
/*  96:    */   private static ClassPath makePathObject(String pathname)
/*  97:    */     throws NotFoundException
/*  98:    */   {
/*  99:256 */     String lower = pathname.toLowerCase();
/* 100:257 */     if ((lower.endsWith(".jar")) || (lower.endsWith(".zip"))) {
/* 101:258 */       return new JarClassPath(pathname);
/* 102:    */     }
/* 103:260 */     int len = pathname.length();
/* 104:261 */     if ((len > 2) && (pathname.charAt(len - 1) == '*') && ((pathname.charAt(len - 2) == '/') || (pathname.charAt(len - 2) == File.separatorChar)))
/* 105:    */     {
/* 106:264 */       String dir = pathname.substring(0, len - 2);
/* 107:265 */       return new JarDirClassPath(dir);
/* 108:    */     }
/* 109:268 */     return new DirClassPath(pathname);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void recordInvalidClassName(String name)
/* 113:    */   {
/* 114:276 */     this.packages.put(name, name);
/* 115:    */   }
/* 116:    */   
/* 117:    */   void writeClassfile(String classname, OutputStream out)
/* 118:    */     throws NotFoundException, IOException, CannotCompileException
/* 119:    */   {
/* 120:285 */     InputStream fin = openClassfile(classname);
/* 121:286 */     if (fin == null) {
/* 122:287 */       throw new NotFoundException(classname);
/* 123:    */     }
/* 124:    */     try
/* 125:    */     {
/* 126:290 */       copyStream(fin, out);
/* 127:    */     }
/* 128:    */     finally
/* 129:    */     {
/* 130:293 */       fin.close();
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   InputStream openClassfile(String classname)
/* 135:    */     throws NotFoundException
/* 136:    */   {
/* 137:327 */     if (this.packages.get(classname) != null) {
/* 138:328 */       return null;
/* 139:    */     }
/* 140:330 */     ClassPathList list = this.pathList;
/* 141:331 */     InputStream ins = null;
/* 142:332 */     NotFoundException error = null;
/* 143:333 */     while (list != null)
/* 144:    */     {
/* 145:    */       try
/* 146:    */       {
/* 147:335 */         ins = list.path.openClassfile(classname);
/* 148:    */       }
/* 149:    */       catch (NotFoundException e)
/* 150:    */       {
/* 151:338 */         if (error == null) {
/* 152:339 */           error = e;
/* 153:    */         }
/* 154:    */       }
/* 155:342 */       if (ins == null) {
/* 156:343 */         list = list.next;
/* 157:    */       } else {
/* 158:345 */         return ins;
/* 159:    */       }
/* 160:    */     }
/* 161:348 */     if (error != null) {
/* 162:349 */       throw error;
/* 163:    */     }
/* 164:351 */     return null;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public URL find(String classname)
/* 168:    */   {
/* 169:363 */     if (this.packages.get(classname) != null) {
/* 170:364 */       return null;
/* 171:    */     }
/* 172:366 */     ClassPathList list = this.pathList;
/* 173:367 */     URL url = null;
/* 174:368 */     while (list != null)
/* 175:    */     {
/* 176:369 */       url = list.path.find(classname);
/* 177:370 */       if (url == null) {
/* 178:371 */         list = list.next;
/* 179:    */       } else {
/* 180:373 */         return url;
/* 181:    */       }
/* 182:    */     }
/* 183:376 */     return null;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public static byte[] readStream(InputStream fin)
/* 187:    */     throws IOException
/* 188:    */   {
/* 189:385 */     byte[][] bufs = new byte[8][];
/* 190:386 */     int bufsize = 4096;
/* 191:388 */     for (int i = 0; i < 8; i++)
/* 192:    */     {
/* 193:389 */       bufs[i] = new byte[bufsize];
/* 194:390 */       int size = 0;
/* 195:391 */       int len = 0;
/* 196:    */       do
/* 197:    */       {
/* 198:393 */         len = fin.read(bufs[i], size, bufsize - size);
/* 199:394 */         if (len >= 0)
/* 200:    */         {
/* 201:395 */           size += len;
/* 202:    */         }
/* 203:    */         else
/* 204:    */         {
/* 205:397 */           byte[] result = new byte[bufsize - 4096 + size];
/* 206:398 */           int s = 0;
/* 207:399 */           for (int j = 0; j < i; j++)
/* 208:    */           {
/* 209:400 */             System.arraycopy(bufs[j], 0, result, s, s + 4096);
/* 210:401 */             s = s + s + 4096;
/* 211:    */           }
/* 212:404 */           System.arraycopy(bufs[i], 0, result, s, size);
/* 213:405 */           return result;
/* 214:    */         }
/* 215:407 */       } while (size < bufsize);
/* 216:408 */       bufsize *= 2;
/* 217:    */     }
/* 218:411 */     throw new IOException("too much data");
/* 219:    */   }
/* 220:    */   
/* 221:    */   public static void copyStream(InputStream fin, OutputStream fout)
/* 222:    */     throws IOException
/* 223:    */   {
/* 224:422 */     int bufsize = 4096;
/* 225:423 */     for (int i = 0; i < 8; i++)
/* 226:    */     {
/* 227:424 */       byte[] buf = new byte[bufsize];
/* 228:425 */       int size = 0;
/* 229:426 */       int len = 0;
/* 230:    */       do
/* 231:    */       {
/* 232:428 */         len = fin.read(buf, size, bufsize - size);
/* 233:429 */         if (len >= 0)
/* 234:    */         {
/* 235:430 */           size += len;
/* 236:    */         }
/* 237:    */         else
/* 238:    */         {
/* 239:432 */           fout.write(buf, 0, size);
/* 240:433 */           return;
/* 241:    */         }
/* 242:435 */       } while (size < bufsize);
/* 243:436 */       fout.write(buf);
/* 244:437 */       bufsize *= 2;
/* 245:    */     }
/* 246:440 */     throw new IOException("too much data");
/* 247:    */   }
/* 248:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.ClassPoolTail
 * JD-Core Version:    0.7.0.1
 */