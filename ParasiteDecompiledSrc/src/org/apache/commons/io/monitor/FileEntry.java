/*   1:    */ package org.apache.commons.io.monitor;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.Serializable;
/*   5:    */ 
/*   6:    */ public class FileEntry
/*   7:    */   implements Serializable
/*   8:    */ {
/*   9: 44 */   static final FileEntry[] EMPTY_ENTRIES = new FileEntry[0];
/*  10:    */   private final FileEntry parent;
/*  11:    */   private FileEntry[] children;
/*  12:    */   private final File file;
/*  13:    */   private String name;
/*  14:    */   private boolean exists;
/*  15:    */   private boolean directory;
/*  16:    */   private long lastModified;
/*  17:    */   private long length;
/*  18:    */   
/*  19:    */   public FileEntry(File file)
/*  20:    */   {
/*  21: 61 */     this((FileEntry)null, file);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public FileEntry(FileEntry parent, File file)
/*  25:    */   {
/*  26: 71 */     if (file == null) {
/*  27: 72 */       throw new IllegalArgumentException("File is missing");
/*  28:    */     }
/*  29: 74 */     this.file = file;
/*  30: 75 */     this.parent = parent;
/*  31: 76 */     this.name = file.getName();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public boolean refresh(File file)
/*  35:    */   {
/*  36: 96 */     boolean origExists = this.exists;
/*  37: 97 */     long origLastModified = this.lastModified;
/*  38: 98 */     boolean origDirectory = this.directory;
/*  39: 99 */     long origLength = this.length;
/*  40:    */     
/*  41:    */ 
/*  42:102 */     this.name = file.getName();
/*  43:103 */     this.exists = file.exists();
/*  44:104 */     this.directory = (this.exists ? file.isDirectory() : false);
/*  45:105 */     this.lastModified = (this.exists ? file.lastModified() : 0L);
/*  46:106 */     this.length = ((this.exists) && (!this.directory) ? file.length() : 0L);
/*  47:    */     
/*  48:    */ 
/*  49:109 */     return (this.exists != origExists) || (this.lastModified != origLastModified) || (this.directory != origDirectory) || (this.length != origLength);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public FileEntry newChildInstance(File file)
/*  53:    */   {
/*  54:125 */     return new FileEntry(this, file);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public FileEntry getParent()
/*  58:    */   {
/*  59:134 */     return this.parent;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int getLevel()
/*  63:    */   {
/*  64:143 */     return this.parent == null ? 0 : this.parent.getLevel() + 1;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public FileEntry[] getChildren()
/*  68:    */   {
/*  69:154 */     return this.children != null ? this.children : EMPTY_ENTRIES;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setChildren(FileEntry[] children)
/*  73:    */   {
/*  74:163 */     this.children = children;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public File getFile()
/*  78:    */   {
/*  79:172 */     return this.file;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String getName()
/*  83:    */   {
/*  84:181 */     return this.name;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setName(String name)
/*  88:    */   {
/*  89:190 */     this.name = name;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public long getLastModified()
/*  93:    */   {
/*  94:200 */     return this.lastModified;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setLastModified(long lastModified)
/*  98:    */   {
/*  99:210 */     this.lastModified = lastModified;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public long getLength()
/* 103:    */   {
/* 104:219 */     return this.length;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setLength(long length)
/* 108:    */   {
/* 109:228 */     this.length = length;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public boolean isExists()
/* 113:    */   {
/* 114:238 */     return this.exists;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setExists(boolean exists)
/* 118:    */   {
/* 119:248 */     this.exists = exists;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public boolean isDirectory()
/* 123:    */   {
/* 124:257 */     return this.directory;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setDirectory(boolean directory)
/* 128:    */   {
/* 129:266 */     this.directory = directory;
/* 130:    */   }
/* 131:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.monitor.FileEntry
 * JD-Core Version:    0.7.0.1
 */