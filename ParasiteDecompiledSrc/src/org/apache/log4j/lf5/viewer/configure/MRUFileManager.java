/*   1:    */ package org.apache.log4j.lf5.viewer.configure;
/*   2:    */ 
/*   3:    */ import java.io.BufferedInputStream;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.FileInputStream;
/*   6:    */ import java.io.FileNotFoundException;
/*   7:    */ import java.io.FileOutputStream;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.io.InputStream;
/*  10:    */ import java.io.ObjectInputStream;
/*  11:    */ import java.io.ObjectOutputStream;
/*  12:    */ import java.net.URL;
/*  13:    */ import java.util.AbstractSequentialList;
/*  14:    */ import java.util.Iterator;
/*  15:    */ import java.util.LinkedList;
/*  16:    */ 
/*  17:    */ public class MRUFileManager
/*  18:    */ {
/*  19:    */   private static final String CONFIG_FILE_NAME = "mru_file_manager";
/*  20:    */   private static final int DEFAULT_MAX_SIZE = 3;
/*  21: 57 */   private int _maxSize = 0;
/*  22:    */   private LinkedList _mruFileList;
/*  23:    */   
/*  24:    */   public MRUFileManager()
/*  25:    */   {
/*  26: 64 */     load();
/*  27: 65 */     setMaxSize(3);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public MRUFileManager(int maxSize)
/*  31:    */   {
/*  32: 69 */     load();
/*  33: 70 */     setMaxSize(maxSize);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void save()
/*  37:    */   {
/*  38: 80 */     File file = new File(getFilename());
/*  39:    */     try
/*  40:    */     {
/*  41: 83 */       ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
/*  42:    */       
/*  43: 85 */       oos.writeObject(this._mruFileList);
/*  44: 86 */       oos.flush();
/*  45: 87 */       oos.close();
/*  46:    */     }
/*  47:    */     catch (Exception e)
/*  48:    */     {
/*  49: 90 */       e.printStackTrace();
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int size()
/*  54:    */   {
/*  55: 98 */     return this._mruFileList.size();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Object getFile(int index)
/*  59:    */   {
/*  60:106 */     if (index < size()) {
/*  61:107 */       return this._mruFileList.get(index);
/*  62:    */     }
/*  63:110 */     return null;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public InputStream getInputStream(int index)
/*  67:    */     throws IOException, FileNotFoundException
/*  68:    */   {
/*  69:118 */     if (index < size())
/*  70:    */     {
/*  71:119 */       Object o = getFile(index);
/*  72:120 */       if ((o instanceof File)) {
/*  73:121 */         return getInputStream((File)o);
/*  74:    */       }
/*  75:123 */       return getInputStream((URL)o);
/*  76:    */     }
/*  77:126 */     return null;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void set(File file)
/*  81:    */   {
/*  82:133 */     setMRU(file);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void set(URL url)
/*  86:    */   {
/*  87:140 */     setMRU(url);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String[] getMRUFileList()
/*  91:    */   {
/*  92:147 */     if (size() == 0) {
/*  93:148 */       return null;
/*  94:    */     }
/*  95:151 */     String[] ss = new String[size()];
/*  96:153 */     for (int i = 0; i < size(); i++)
/*  97:    */     {
/*  98:154 */       Object o = getFile(i);
/*  99:155 */       if ((o instanceof File)) {
/* 100:156 */         ss[i] = ((File)o).getAbsolutePath();
/* 101:    */       } else {
/* 102:159 */         ss[i] = o.toString();
/* 103:    */       }
/* 104:    */     }
/* 105:164 */     return ss;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void moveToTop(int index)
/* 109:    */   {
/* 110:173 */     this._mruFileList.add(0, this._mruFileList.remove(index));
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static void createConfigurationDirectory()
/* 114:    */   {
/* 115:183 */     String home = System.getProperty("user.home");
/* 116:184 */     String sep = System.getProperty("file.separator");
/* 117:185 */     File f = new File(home + sep + "lf5");
/* 118:186 */     if (!f.exists()) {
/* 119:    */       try
/* 120:    */       {
/* 121:188 */         f.mkdir();
/* 122:    */       }
/* 123:    */       catch (SecurityException e)
/* 124:    */       {
/* 125:190 */         e.printStackTrace();
/* 126:    */       }
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   protected InputStream getInputStream(File file)
/* 131:    */     throws IOException, FileNotFoundException
/* 132:    */   {
/* 133:206 */     BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
/* 134:    */     
/* 135:    */ 
/* 136:209 */     return reader;
/* 137:    */   }
/* 138:    */   
/* 139:    */   protected InputStream getInputStream(URL url)
/* 140:    */     throws IOException
/* 141:    */   {
/* 142:219 */     return url.openStream();
/* 143:    */   }
/* 144:    */   
/* 145:    */   protected void setMRU(Object o)
/* 146:    */   {
/* 147:226 */     int index = this._mruFileList.indexOf(o);
/* 148:228 */     if (index == -1)
/* 149:    */     {
/* 150:229 */       this._mruFileList.add(0, o);
/* 151:230 */       setMaxSize(this._maxSize);
/* 152:    */     }
/* 153:    */     else
/* 154:    */     {
/* 155:232 */       moveToTop(index);
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   protected void load()
/* 160:    */   {
/* 161:241 */     createConfigurationDirectory();
/* 162:242 */     File file = new File(getFilename());
/* 163:243 */     if (file.exists()) {
/* 164:    */       try
/* 165:    */       {
/* 166:245 */         ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
/* 167:    */         
/* 168:247 */         this._mruFileList = ((LinkedList)ois.readObject());
/* 169:248 */         ois.close();
/* 170:    */         
/* 171:    */ 
/* 172:251 */         Iterator it = this._mruFileList.iterator();
/* 173:252 */         while (it.hasNext())
/* 174:    */         {
/* 175:253 */           Object o = it.next();
/* 176:254 */           if ((!(o instanceof File)) && (!(o instanceof URL))) {
/* 177:255 */             it.remove();
/* 178:    */           }
/* 179:    */         }
/* 180:    */       }
/* 181:    */       catch (Exception e)
/* 182:    */       {
/* 183:259 */         this._mruFileList = new LinkedList();
/* 184:    */       }
/* 185:    */     } else {
/* 186:262 */       this._mruFileList = new LinkedList();
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   protected String getFilename()
/* 191:    */   {
/* 192:268 */     String home = System.getProperty("user.home");
/* 193:269 */     String sep = System.getProperty("file.separator");
/* 194:    */     
/* 195:271 */     return home + sep + "lf5" + sep + "mru_file_manager";
/* 196:    */   }
/* 197:    */   
/* 198:    */   protected void setMaxSize(int maxSize)
/* 199:    */   {
/* 200:278 */     if (maxSize < this._mruFileList.size()) {
/* 201:279 */       for (int i = 0; i < this._mruFileList.size() - maxSize; i++) {
/* 202:280 */         this._mruFileList.removeLast();
/* 203:    */       }
/* 204:    */     }
/* 205:284 */     this._maxSize = maxSize;
/* 206:    */   }
/* 207:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.configure.MRUFileManager
 * JD-Core Version:    0.7.0.1
 */