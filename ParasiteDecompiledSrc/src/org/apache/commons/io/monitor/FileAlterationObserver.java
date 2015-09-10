/*   1:    */ package org.apache.commons.io.monitor;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileFilter;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.util.Arrays;
/*   7:    */ import java.util.Comparator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.concurrent.CopyOnWriteArrayList;
/*  10:    */ import org.apache.commons.io.FileUtils;
/*  11:    */ import org.apache.commons.io.IOCase;
/*  12:    */ import org.apache.commons.io.comparator.NameFileComparator;
/*  13:    */ 
/*  14:    */ public class FileAlterationObserver
/*  15:    */   implements Serializable
/*  16:    */ {
/*  17:124 */   private final List<FileAlterationListener> listeners = new CopyOnWriteArrayList();
/*  18:    */   private final FileEntry rootEntry;
/*  19:    */   private final FileFilter fileFilter;
/*  20:    */   private final Comparator<File> comparator;
/*  21:    */   
/*  22:    */   public FileAlterationObserver(String directoryName)
/*  23:    */   {
/*  24:135 */     this(new File(directoryName));
/*  25:    */   }
/*  26:    */   
/*  27:    */   public FileAlterationObserver(String directoryName, FileFilter fileFilter)
/*  28:    */   {
/*  29:145 */     this(new File(directoryName), fileFilter);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public FileAlterationObserver(String directoryName, FileFilter fileFilter, IOCase caseSensitivity)
/*  33:    */   {
/*  34:157 */     this(new File(directoryName), fileFilter, caseSensitivity);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public FileAlterationObserver(File directory)
/*  38:    */   {
/*  39:166 */     this(directory, (FileFilter)null);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public FileAlterationObserver(File directory, FileFilter fileFilter)
/*  43:    */   {
/*  44:176 */     this(directory, fileFilter, (IOCase)null);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public FileAlterationObserver(File directory, FileFilter fileFilter, IOCase caseSensitivity)
/*  48:    */   {
/*  49:188 */     this(new FileEntry(directory), fileFilter, caseSensitivity);
/*  50:    */   }
/*  51:    */   
/*  52:    */   protected FileAlterationObserver(FileEntry rootEntry, FileFilter fileFilter, IOCase caseSensitivity)
/*  53:    */   {
/*  54:200 */     if (rootEntry == null) {
/*  55:201 */       throw new IllegalArgumentException("Root entry is missing");
/*  56:    */     }
/*  57:203 */     if (rootEntry.getFile() == null) {
/*  58:204 */       throw new IllegalArgumentException("Root directory is missing");
/*  59:    */     }
/*  60:206 */     this.rootEntry = rootEntry;
/*  61:207 */     this.fileFilter = fileFilter;
/*  62:208 */     if ((caseSensitivity == null) || (caseSensitivity.equals(IOCase.SYSTEM))) {
/*  63:209 */       this.comparator = NameFileComparator.NAME_SYSTEM_COMPARATOR;
/*  64:210 */     } else if (caseSensitivity.equals(IOCase.INSENSITIVE)) {
/*  65:211 */       this.comparator = NameFileComparator.NAME_INSENSITIVE_COMPARATOR;
/*  66:    */     } else {
/*  67:213 */       this.comparator = NameFileComparator.NAME_COMPARATOR;
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public File getDirectory()
/*  72:    */   {
/*  73:223 */     return this.rootEntry.getFile();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void addListener(FileAlterationListener listener)
/*  77:    */   {
/*  78:232 */     if (listener != null) {
/*  79:233 */       this.listeners.add(listener);
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void removeListener(FileAlterationListener listener)
/*  84:    */   {
/*  85:243 */     while ((listener != null) && 
/*  86:244 */       (this.listeners.remove(listener))) {}
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Iterable<FileAlterationListener> getListeners()
/*  90:    */   {
/*  91:255 */     return this.listeners;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void initialize()
/*  95:    */     throws Exception
/*  96:    */   {
/*  97:264 */     this.rootEntry.refresh(this.rootEntry.getFile());
/*  98:265 */     File[] files = listFiles(this.rootEntry.getFile());
/*  99:266 */     FileEntry[] children = files.length > 0 ? new FileEntry[files.length] : FileEntry.EMPTY_ENTRIES;
/* 100:267 */     for (int i = 0; i < files.length; i++) {
/* 101:268 */       children[i] = createFileEntry(this.rootEntry, files[i]);
/* 102:    */     }
/* 103:270 */     this.rootEntry.setChildren(children);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void destroy()
/* 107:    */     throws Exception
/* 108:    */   {}
/* 109:    */   
/* 110:    */   public void checkAndNotify()
/* 111:    */   {
/* 112:287 */     for (FileAlterationListener listener : this.listeners) {
/* 113:288 */       listener.onStart(this);
/* 114:    */     }
/* 115:292 */     File rootFile = this.rootEntry.getFile();
/* 116:293 */     if (rootFile.exists()) {
/* 117:294 */       checkAndNotify(this.rootEntry, this.rootEntry.getChildren(), listFiles(rootFile));
/* 118:295 */     } else if (this.rootEntry.isExists()) {
/* 119:296 */       checkAndNotify(this.rootEntry, this.rootEntry.getChildren(), FileUtils.EMPTY_FILE_ARRAY);
/* 120:    */     }
/* 121:302 */     for (FileAlterationListener listener : this.listeners) {
/* 122:303 */       listener.onStop(this);
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   private void checkAndNotify(FileEntry parent, FileEntry[] previous, File[] files)
/* 127:    */   {
/* 128:315 */     int c = 0;
/* 129:316 */     FileEntry[] current = files.length > 0 ? new FileEntry[files.length] : FileEntry.EMPTY_ENTRIES;
/* 130:317 */     for (FileEntry entry : previous)
/* 131:    */     {
/* 132:318 */       while ((c < files.length) && (this.comparator.compare(entry.getFile(), files[c]) > 0))
/* 133:    */       {
/* 134:319 */         current[c] = createFileEntry(parent, files[c]);
/* 135:320 */         doCreate(current[c]);
/* 136:321 */         c++;
/* 137:    */       }
/* 138:323 */       if ((c < files.length) && (this.comparator.compare(entry.getFile(), files[c]) == 0))
/* 139:    */       {
/* 140:324 */         doMatch(entry, files[c]);
/* 141:325 */         checkAndNotify(entry, entry.getChildren(), listFiles(files[c]));
/* 142:326 */         current[c] = entry;
/* 143:327 */         c++;
/* 144:    */       }
/* 145:    */       else
/* 146:    */       {
/* 147:329 */         checkAndNotify(entry, entry.getChildren(), FileUtils.EMPTY_FILE_ARRAY);
/* 148:330 */         doDelete(entry);
/* 149:    */       }
/* 150:    */     }
/* 151:333 */     for (; c < files.length; c++)
/* 152:    */     {
/* 153:334 */       current[c] = createFileEntry(parent, files[c]);
/* 154:335 */       doCreate(current[c]);
/* 155:    */     }
/* 156:337 */     parent.setChildren(current);
/* 157:    */   }
/* 158:    */   
/* 159:    */   private FileEntry createFileEntry(FileEntry parent, File file)
/* 160:    */   {
/* 161:348 */     FileEntry entry = parent.newChildInstance(file);
/* 162:349 */     entry.refresh(file);
/* 163:350 */     File[] files = listFiles(file);
/* 164:351 */     FileEntry[] children = files.length > 0 ? new FileEntry[files.length] : FileEntry.EMPTY_ENTRIES;
/* 165:352 */     for (int i = 0; i < files.length; i++) {
/* 166:353 */       children[i] = createFileEntry(entry, files[i]);
/* 167:    */     }
/* 168:355 */     entry.setChildren(children);
/* 169:356 */     return entry;
/* 170:    */   }
/* 171:    */   
/* 172:    */   private void doCreate(FileEntry entry)
/* 173:    */   {
/* 174:365 */     for (FileAlterationListener listener : this.listeners) {
/* 175:366 */       if (entry.isDirectory()) {
/* 176:367 */         listener.onDirectoryCreate(entry.getFile());
/* 177:    */       } else {
/* 178:369 */         listener.onFileCreate(entry.getFile());
/* 179:    */       }
/* 180:    */     }
/* 181:372 */     FileEntry[] children = entry.getChildren();
/* 182:373 */     for (FileEntry aChildren : children) {
/* 183:374 */       doCreate(aChildren);
/* 184:    */     }
/* 185:    */   }
/* 186:    */   
/* 187:    */   private void doMatch(FileEntry entry, File file)
/* 188:    */   {
/* 189:385 */     if (entry.refresh(file)) {
/* 190:386 */       for (FileAlterationListener listener : this.listeners) {
/* 191:387 */         if (entry.isDirectory()) {
/* 192:388 */           listener.onDirectoryChange(file);
/* 193:    */         } else {
/* 194:390 */           listener.onFileChange(file);
/* 195:    */         }
/* 196:    */       }
/* 197:    */     }
/* 198:    */   }
/* 199:    */   
/* 200:    */   private void doDelete(FileEntry entry)
/* 201:    */   {
/* 202:402 */     for (FileAlterationListener listener : this.listeners) {
/* 203:403 */       if (entry.isDirectory()) {
/* 204:404 */         listener.onDirectoryDelete(entry.getFile());
/* 205:    */       } else {
/* 206:406 */         listener.onFileDelete(entry.getFile());
/* 207:    */       }
/* 208:    */     }
/* 209:    */   }
/* 210:    */   
/* 211:    */   private File[] listFiles(File file)
/* 212:    */   {
/* 213:419 */     File[] children = null;
/* 214:420 */     if (file.isDirectory()) {
/* 215:421 */       children = this.fileFilter == null ? file.listFiles() : file.listFiles(this.fileFilter);
/* 216:    */     }
/* 217:423 */     if (children == null) {
/* 218:424 */       children = FileUtils.EMPTY_FILE_ARRAY;
/* 219:    */     }
/* 220:426 */     if ((this.comparator != null) && (children.length > 1)) {
/* 221:427 */       Arrays.sort(children, this.comparator);
/* 222:    */     }
/* 223:429 */     return children;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public String toString()
/* 227:    */   {
/* 228:439 */     StringBuilder builder = new StringBuilder();
/* 229:440 */     builder.append(getClass().getSimpleName());
/* 230:441 */     builder.append("[file='");
/* 231:442 */     builder.append(getDirectory().getPath());
/* 232:443 */     builder.append('\'');
/* 233:444 */     if (this.fileFilter != null)
/* 234:    */     {
/* 235:445 */       builder.append(", ");
/* 236:446 */       builder.append(this.fileFilter.toString());
/* 237:    */     }
/* 238:448 */     builder.append(", listeners=");
/* 239:449 */     builder.append(this.listeners.size());
/* 240:450 */     builder.append("]");
/* 241:451 */     return builder.toString();
/* 242:    */   }
/* 243:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.monitor.FileAlterationObserver
 * JD-Core Version:    0.7.0.1
 */