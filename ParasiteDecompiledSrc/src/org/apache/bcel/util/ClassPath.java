/*   1:    */ package org.apache.bcel.util;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.FilenameFilter;
/*   6:    */ import java.io.FilterInputStream;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.io.InputStream;
/*   9:    */ import java.io.PrintStream;
/*  10:    */ import java.util.AbstractList;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.Iterator;
/*  13:    */ import java.util.StringTokenizer;
/*  14:    */ import java.util.zip.ZipEntry;
/*  15:    */ import java.util.zip.ZipFile;
/*  16:    */ 
/*  17:    */ public class ClassPath
/*  18:    */ {
/*  19:    */   private PathEntry[] paths;
/*  20:    */   
/*  21:    */   public ClassPath(String class_path)
/*  22:    */   {
/*  23: 75 */     ArrayList vec = new ArrayList();
/*  24:    */     
/*  25: 77 */     StringTokenizer tok = new StringTokenizer(class_path, System.getProperty("path.separator"));
/*  26: 79 */     while (tok.hasMoreTokens())
/*  27:    */     {
/*  28: 81 */       String path = tok.nextToken();
/*  29: 83 */       if (!path.equals(""))
/*  30:    */       {
/*  31: 84 */         File file = new File(path);
/*  32:    */         try
/*  33:    */         {
/*  34: 87 */           if (file.exists()) {
/*  35: 88 */             if (file.isDirectory()) {
/*  36: 89 */               vec.add(new Dir(path));
/*  37:    */             } else {
/*  38: 91 */               vec.add(new Zip(new ZipFile(file)));
/*  39:    */             }
/*  40:    */           }
/*  41:    */         }
/*  42:    */         catch (IOException e)
/*  43:    */         {
/*  44: 94 */           System.err.println("CLASSPATH component " + file + ": " + e);
/*  45:    */         }
/*  46:    */       }
/*  47:    */     }
/*  48: 99 */     this.paths = new PathEntry[vec.size()];
/*  49:100 */     vec.toArray(this.paths);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public ClassPath()
/*  53:    */   {
/*  54:107 */     this(getClassPath());
/*  55:    */   }
/*  56:    */   
/*  57:    */   private static final void getPathComponents(String path, ArrayList list)
/*  58:    */   {
/*  59:111 */     if (path != null)
/*  60:    */     {
/*  61:112 */       StringTokenizer tok = new StringTokenizer(path, File.pathSeparator);
/*  62:114 */       while (tok.hasMoreTokens())
/*  63:    */       {
/*  64:115 */         String name = tok.nextToken();
/*  65:116 */         File file = new File(name);
/*  66:118 */         if (file.exists()) {
/*  67:119 */           list.add(name);
/*  68:    */         }
/*  69:    */       }
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static final String getClassPath()
/*  74:    */   {
/*  75:130 */     String class_path = System.getProperty("java.class.path");
/*  76:131 */     String boot_path = System.getProperty("sun.boot.class.path");
/*  77:132 */     String ext_path = System.getProperty("java.ext.dirs");
/*  78:    */     
/*  79:134 */     ArrayList list = new ArrayList();
/*  80:    */     
/*  81:136 */     getPathComponents(class_path, list);
/*  82:137 */     getPathComponents(boot_path, list);
/*  83:    */     
/*  84:139 */     ArrayList dirs = new ArrayList();
/*  85:140 */     getPathComponents(ext_path, dirs);
/*  86:142 */     for (Iterator e = dirs.iterator(); e.hasNext();)
/*  87:    */     {
/*  88:143 */       File ext_dir = new File((String)e.next());
/*  89:144 */       String[] extensions = ext_dir.list(new FilenameFilter()
/*  90:    */       {
/*  91:    */         public boolean accept(File dir, String name)
/*  92:    */         {
/*  93:146 */           name = name.toLowerCase();
/*  94:147 */           return (name.endsWith(".zip")) || (name.endsWith(".jar"));
/*  95:    */         }
/*  96:    */       });
/*  97:151 */       if (extensions != null) {
/*  98:152 */         for (int i = 0; i < extensions.length; i++) {
/*  99:153 */           list.add(ext_path + File.separatorChar + extensions[i]);
/* 100:    */         }
/* 101:    */       }
/* 102:    */     }
/* 103:156 */     StringBuffer buf = new StringBuffer();
/* 104:158 */     for (Iterator e = list.iterator(); e.hasNext();)
/* 105:    */     {
/* 106:159 */       buf.append((String)e.next());
/* 107:161 */       if (e.hasNext()) {
/* 108:162 */         buf.append(File.pathSeparatorChar);
/* 109:    */       }
/* 110:    */     }
/* 111:165 */     return buf.toString();
/* 112:    */   }
/* 113:    */   
/* 114:    */   public InputStream getInputStream(String name)
/* 115:    */     throws IOException
/* 116:    */   {
/* 117:173 */     return getInputStream(name, ".class");
/* 118:    */   }
/* 119:    */   
/* 120:    */   public InputStream getInputStream(String name, String suffix)
/* 121:    */     throws IOException
/* 122:    */   {
/* 123:184 */     InputStream is = null;
/* 124:    */     try
/* 125:    */     {
/* 126:187 */       is = getClass().getClassLoader().getResourceAsStream(name + suffix);
/* 127:    */     }
/* 128:    */     catch (Exception e) {}
/* 129:190 */     if (is != null) {
/* 130:191 */       return is;
/* 131:    */     }
/* 132:193 */     return getClassFile(name, suffix).getInputStream();
/* 133:    */   }
/* 134:    */   
/* 135:    */   public ClassFile getClassFile(String name, String suffix)
/* 136:    */     throws IOException
/* 137:    */   {
/* 138:202 */     for (int i = 0; i < this.paths.length; i++)
/* 139:    */     {
/* 140:    */       ClassFile cf;
/* 141:205 */       if ((cf = this.paths[i].getClassFile(name, suffix)) != null) {
/* 142:206 */         return cf;
/* 143:    */       }
/* 144:    */     }
/* 145:209 */     throw new IOException("Couldn't find: " + name + suffix);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public ClassFile getClassFile(String name)
/* 149:    */     throws IOException
/* 150:    */   {
/* 151:217 */     return getClassFile(name, ".class");
/* 152:    */   }
/* 153:    */   
/* 154:    */   public byte[] getBytes(String name, String suffix)
/* 155:    */     throws IOException
/* 156:    */   {
/* 157:226 */     InputStream is = getInputStream(name, suffix);
/* 158:228 */     if (is == null) {
/* 159:229 */       throw new IOException("Couldn't find: " + name + suffix);
/* 160:    */     }
/* 161:231 */     DataInputStream dis = new DataInputStream(is);
/* 162:232 */     byte[] bytes = new byte[is.available()];
/* 163:233 */     dis.readFully(bytes);
/* 164:234 */     dis.close();is.close();
/* 165:    */     
/* 166:236 */     return bytes;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public byte[] getBytes(String name)
/* 170:    */     throws IOException
/* 171:    */   {
/* 172:243 */     return getBytes(name, ".class");
/* 173:    */   }
/* 174:    */   
/* 175:    */   public String getPath(String name)
/* 176:    */     throws IOException
/* 177:    */   {
/* 178:251 */     int index = name.lastIndexOf('.');
/* 179:252 */     String suffix = "";
/* 180:254 */     if (index > 0)
/* 181:    */     {
/* 182:255 */       suffix = name.substring(index);
/* 183:256 */       name = name.substring(0, index);
/* 184:    */     }
/* 185:259 */     return getPath(name, suffix);
/* 186:    */   }
/* 187:    */   
/* 188:    */   public String getPath(String name, String suffix)
/* 189:    */     throws IOException
/* 190:    */   {
/* 191:268 */     return getClassFile(name, suffix).getPath();
/* 192:    */   }
/* 193:    */   
/* 194:    */   private static abstract class PathEntry
/* 195:    */   {
/* 196:    */     private PathEntry() {}
/* 197:    */     
/* 198:    */     abstract ClassPath.ClassFile getClassFile(String paramString1, String paramString2)
/* 199:    */       throws IOException;
/* 200:    */     
/* 201:    */     PathEntry(ClassPath.1 x0)
/* 202:    */     {
/* 203:271 */       this();
/* 204:    */     }
/* 205:    */   }
/* 206:    */   
/* 207:    */   public static abstract class ClassFile
/* 208:    */   {
/* 209:    */     public abstract InputStream getInputStream()
/* 210:    */       throws IOException;
/* 211:    */     
/* 212:    */     public abstract String getPath();
/* 213:    */     
/* 214:    */     public abstract long getTime();
/* 215:    */     
/* 216:    */     public abstract long getSize();
/* 217:    */   }
/* 218:    */   
/* 219:    */   private static class Dir
/* 220:    */     extends ClassPath.PathEntry
/* 221:    */   {
/* 222:    */     private String dir;
/* 223:    */     
/* 224:    */     Dir(String d)
/* 225:    */     {
/* 226:298 */       super();this.dir = d;
/* 227:    */     }
/* 228:    */     
/* 229:    */     ClassPath.ClassFile getClassFile(String name, String suffix)
/* 230:    */       throws IOException
/* 231:    */     {
/* 232:301 */       File file = new File(this.dir + File.separatorChar + name.replace('.', File.separatorChar) + suffix);
/* 233:    */       
/* 234:    */ 
/* 235:304 */       return file.exists() ? new ClassPath.2(this, file) : null;
/* 236:    */     }
/* 237:    */     
/* 238:    */     public String toString()
/* 239:    */     {
/* 240:317 */       return this.dir;
/* 241:    */     }
/* 242:    */   }
/* 243:    */   
/* 244:    */   private static class Zip
/* 245:    */     extends ClassPath.PathEntry
/* 246:    */   {
/* 247:    */     private ZipFile zip;
/* 248:    */     
/* 249:    */     Zip(ZipFile z)
/* 250:    */     {
/* 251:323 */       super();this.zip = z;
/* 252:    */     }
/* 253:    */     
/* 254:    */     ClassPath.ClassFile getClassFile(String name, String suffix)
/* 255:    */       throws IOException
/* 256:    */     {
/* 257:326 */       ZipEntry entry = this.zip.getEntry(name.replace('.', '/') + suffix);
/* 258:    */       
/* 259:328 */       return entry != null ? new ClassPath.3(this, entry) : null;
/* 260:    */     }
/* 261:    */   }
/* 262:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.util.ClassPath
 * JD-Core Version:    0.7.0.1
 */