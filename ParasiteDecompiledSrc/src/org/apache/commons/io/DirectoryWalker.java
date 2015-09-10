/*   1:    */ package org.apache.commons.io;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileFilter;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.Collection;
/*   7:    */ import org.apache.commons.io.filefilter.FileFilterUtils;
/*   8:    */ import org.apache.commons.io.filefilter.IOFileFilter;
/*   9:    */ import org.apache.commons.io.filefilter.TrueFileFilter;
/*  10:    */ 
/*  11:    */ public abstract class DirectoryWalker<T>
/*  12:    */ {
/*  13:    */   private final FileFilter filter;
/*  14:    */   private final int depthLimit;
/*  15:    */   
/*  16:    */   protected DirectoryWalker()
/*  17:    */   {
/*  18:266 */     this(null, -1);
/*  19:    */   }
/*  20:    */   
/*  21:    */   protected DirectoryWalker(FileFilter filter, int depthLimit)
/*  22:    */   {
/*  23:282 */     this.filter = filter;
/*  24:283 */     this.depthLimit = depthLimit;
/*  25:    */   }
/*  26:    */   
/*  27:    */   protected DirectoryWalker(IOFileFilter directoryFilter, IOFileFilter fileFilter, int depthLimit)
/*  28:    */   {
/*  29:301 */     if ((directoryFilter == null) && (fileFilter == null))
/*  30:    */     {
/*  31:302 */       this.filter = null;
/*  32:    */     }
/*  33:    */     else
/*  34:    */     {
/*  35:304 */       directoryFilter = directoryFilter != null ? directoryFilter : TrueFileFilter.TRUE;
/*  36:305 */       fileFilter = fileFilter != null ? fileFilter : TrueFileFilter.TRUE;
/*  37:306 */       directoryFilter = FileFilterUtils.makeDirectoryOnly(directoryFilter);
/*  38:307 */       fileFilter = FileFilterUtils.makeFileOnly(fileFilter);
/*  39:308 */       this.filter = FileFilterUtils.or(new IOFileFilter[] { directoryFilter, fileFilter });
/*  40:    */     }
/*  41:310 */     this.depthLimit = depthLimit;
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected final void walk(File startDirectory, Collection<T> results)
/*  45:    */     throws IOException
/*  46:    */   {
/*  47:330 */     if (startDirectory == null) {
/*  48:331 */       throw new NullPointerException("Start Directory is null");
/*  49:    */     }
/*  50:    */     try
/*  51:    */     {
/*  52:334 */       handleStart(startDirectory, results);
/*  53:335 */       walk(startDirectory, 0, results);
/*  54:336 */       handleEnd(results);
/*  55:    */     }
/*  56:    */     catch (CancelException cancel)
/*  57:    */     {
/*  58:338 */       handleCancelled(startDirectory, results, cancel);
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   private void walk(File directory, int depth, Collection<T> results)
/*  63:    */     throws IOException
/*  64:    */   {
/*  65:351 */     checkIfCancelled(directory, depth, results);
/*  66:352 */     if (handleDirectory(directory, depth, results))
/*  67:    */     {
/*  68:353 */       handleDirectoryStart(directory, depth, results);
/*  69:354 */       int childDepth = depth + 1;
/*  70:355 */       if ((this.depthLimit < 0) || (childDepth <= this.depthLimit))
/*  71:    */       {
/*  72:356 */         checkIfCancelled(directory, depth, results);
/*  73:357 */         File[] childFiles = this.filter == null ? directory.listFiles() : directory.listFiles(this.filter);
/*  74:358 */         childFiles = filterDirectoryContents(directory, depth, childFiles);
/*  75:359 */         if (childFiles == null) {
/*  76:360 */           handleRestricted(directory, childDepth, results);
/*  77:    */         } else {
/*  78:362 */           for (File childFile : childFiles) {
/*  79:363 */             if (childFile.isDirectory())
/*  80:    */             {
/*  81:364 */               walk(childFile, childDepth, results);
/*  82:    */             }
/*  83:    */             else
/*  84:    */             {
/*  85:366 */               checkIfCancelled(childFile, childDepth, results);
/*  86:367 */               handleFile(childFile, childDepth, results);
/*  87:368 */               checkIfCancelled(childFile, childDepth, results);
/*  88:    */             }
/*  89:    */           }
/*  90:    */         }
/*  91:    */       }
/*  92:373 */       handleDirectoryEnd(directory, depth, results);
/*  93:    */     }
/*  94:375 */     checkIfCancelled(directory, depth, results);
/*  95:    */   }
/*  96:    */   
/*  97:    */   protected final void checkIfCancelled(File file, int depth, Collection<T> results)
/*  98:    */     throws IOException
/*  99:    */   {
/* 100:394 */     if (handleIsCancelled(file, depth, results)) {
/* 101:395 */       throw new CancelException(file, depth);
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   protected boolean handleIsCancelled(File file, int depth, Collection<T> results)
/* 106:    */     throws IOException
/* 107:    */   {
/* 108:437 */     return false;
/* 109:    */   }
/* 110:    */   
/* 111:    */   protected void handleCancelled(File startDirectory, Collection<T> results, CancelException cancel)
/* 112:    */     throws IOException
/* 113:    */   {
/* 114:456 */     throw cancel;
/* 115:    */   }
/* 116:    */   
/* 117:    */   protected void handleStart(File startDirectory, Collection<T> results)
/* 118:    */     throws IOException
/* 119:    */   {}
/* 120:    */   
/* 121:    */   protected boolean handleDirectory(File directory, int depth, Collection<T> results)
/* 122:    */     throws IOException
/* 123:    */   {
/* 124:490 */     return true;
/* 125:    */   }
/* 126:    */   
/* 127:    */   protected void handleDirectoryStart(File directory, int depth, Collection<T> results)
/* 128:    */     throws IOException
/* 129:    */   {}
/* 130:    */   
/* 131:    */   protected File[] filterDirectoryContents(File directory, int depth, File[] files)
/* 132:    */     throws IOException
/* 133:    */   {
/* 134:520 */     return files;
/* 135:    */   }
/* 136:    */   
/* 137:    */   protected void handleFile(File file, int depth, Collection<T> results)
/* 138:    */     throws IOException
/* 139:    */   {}
/* 140:    */   
/* 141:    */   protected void handleRestricted(File directory, int depth, Collection<T> results)
/* 142:    */     throws IOException
/* 143:    */   {}
/* 144:    */   
/* 145:    */   protected void handleDirectoryEnd(File directory, int depth, Collection<T> results)
/* 146:    */     throws IOException
/* 147:    */   {}
/* 148:    */   
/* 149:    */   protected void handleEnd(Collection<T> results)
/* 150:    */     throws IOException
/* 151:    */   {}
/* 152:    */   
/* 153:    */   public static class CancelException
/* 154:    */     extends IOException
/* 155:    */   {
/* 156:    */     private static final long serialVersionUID = 1347339620135041008L;
/* 157:    */     private final File file;
/* 158:    */     private final int depth;
/* 159:    */     
/* 160:    */     public CancelException(File file, int depth)
/* 161:    */     {
/* 162:600 */       this("Operation Cancelled", file, depth);
/* 163:    */     }
/* 164:    */     
/* 165:    */     public CancelException(String message, File file, int depth)
/* 166:    */     {
/* 167:613 */       super();
/* 168:614 */       this.file = file;
/* 169:615 */       this.depth = depth;
/* 170:    */     }
/* 171:    */     
/* 172:    */     public File getFile()
/* 173:    */     {
/* 174:624 */       return this.file;
/* 175:    */     }
/* 176:    */     
/* 177:    */     public int getDepth()
/* 178:    */     {
/* 179:633 */       return this.depth;
/* 180:    */     }
/* 181:    */   }
/* 182:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.DirectoryWalker
 * JD-Core Version:    0.7.0.1
 */