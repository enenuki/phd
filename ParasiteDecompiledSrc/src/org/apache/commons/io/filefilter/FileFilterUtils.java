/*   1:    */ package org.apache.commons.io.filefilter;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileFilter;
/*   5:    */ import java.io.FilenameFilter;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Arrays;
/*   8:    */ import java.util.Collection;
/*   9:    */ import java.util.Date;
/*  10:    */ import java.util.HashSet;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Set;
/*  13:    */ import org.apache.commons.io.IOCase;
/*  14:    */ 
/*  15:    */ public class FileFilterUtils
/*  16:    */ {
/*  17:    */   public static File[] filter(IOFileFilter filter, File... files)
/*  18:    */   {
/*  19: 82 */     if (filter == null) {
/*  20: 83 */       throw new IllegalArgumentException("file filter is null");
/*  21:    */     }
/*  22: 85 */     if (files == null) {
/*  23: 86 */       return new File[0];
/*  24:    */     }
/*  25: 88 */     List<File> acceptedFiles = new ArrayList();
/*  26: 89 */     for (File file : files)
/*  27:    */     {
/*  28: 90 */       if (file == null) {
/*  29: 91 */         throw new IllegalArgumentException("file array contains null");
/*  30:    */       }
/*  31: 93 */       if (filter.accept(file)) {
/*  32: 94 */         acceptedFiles.add(file);
/*  33:    */       }
/*  34:    */     }
/*  35: 97 */     return (File[])acceptedFiles.toArray(new File[acceptedFiles.size()]);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static File[] filter(IOFileFilter filter, Iterable<File> files)
/*  39:    */   {
/*  40:127 */     List<File> acceptedFiles = filterList(filter, files);
/*  41:128 */     return (File[])acceptedFiles.toArray(new File[acceptedFiles.size()]);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static List<File> filterList(IOFileFilter filter, Iterable<File> files)
/*  45:    */   {
/*  46:157 */     return (List)filter(filter, files, new ArrayList());
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static List<File> filterList(IOFileFilter filter, File... files)
/*  50:    */   {
/*  51:186 */     File[] acceptedFiles = filter(filter, files);
/*  52:187 */     return Arrays.asList(acceptedFiles);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static Set<File> filterSet(IOFileFilter filter, File... files)
/*  56:    */   {
/*  57:217 */     File[] acceptedFiles = filter(filter, files);
/*  58:218 */     return new HashSet(Arrays.asList(acceptedFiles));
/*  59:    */   }
/*  60:    */   
/*  61:    */   public static Set<File> filterSet(IOFileFilter filter, Iterable<File> files)
/*  62:    */   {
/*  63:248 */     return (Set)filter(filter, files, new HashSet());
/*  64:    */   }
/*  65:    */   
/*  66:    */   private static <T extends Collection<File>> T filter(IOFileFilter filter, Iterable<File> files, T acceptedFiles)
/*  67:    */   {
/*  68:275 */     if (filter == null) {
/*  69:276 */       throw new IllegalArgumentException("file filter is null");
/*  70:    */     }
/*  71:278 */     if (files != null) {
/*  72:279 */       for (File file : files)
/*  73:    */       {
/*  74:280 */         if (file == null) {
/*  75:281 */           throw new IllegalArgumentException("file collection contains null");
/*  76:    */         }
/*  77:283 */         if (filter.accept(file)) {
/*  78:284 */           acceptedFiles.add(file);
/*  79:    */         }
/*  80:    */       }
/*  81:    */     }
/*  82:288 */     return acceptedFiles;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static IOFileFilter prefixFileFilter(String prefix)
/*  86:    */   {
/*  87:299 */     return new PrefixFileFilter(prefix);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static IOFileFilter prefixFileFilter(String prefix, IOCase caseSensitivity)
/*  91:    */   {
/*  92:312 */     return new PrefixFileFilter(prefix, caseSensitivity);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public static IOFileFilter suffixFileFilter(String suffix)
/*  96:    */   {
/*  97:323 */     return new SuffixFileFilter(suffix);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static IOFileFilter suffixFileFilter(String suffix, IOCase caseSensitivity)
/* 101:    */   {
/* 102:336 */     return new SuffixFileFilter(suffix, caseSensitivity);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public static IOFileFilter nameFileFilter(String name)
/* 106:    */   {
/* 107:347 */     return new NameFileFilter(name);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public static IOFileFilter nameFileFilter(String name, IOCase caseSensitivity)
/* 111:    */   {
/* 112:360 */     return new NameFileFilter(name, caseSensitivity);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public static IOFileFilter directoryFileFilter()
/* 116:    */   {
/* 117:370 */     return DirectoryFileFilter.DIRECTORY;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public static IOFileFilter fileFileFilter()
/* 121:    */   {
/* 122:380 */     return FileFileFilter.FILE;
/* 123:    */   }
/* 124:    */   
/* 125:    */   @Deprecated
/* 126:    */   public static IOFileFilter andFileFilter(IOFileFilter filter1, IOFileFilter filter2)
/* 127:    */   {
/* 128:396 */     return new AndFileFilter(filter1, filter2);
/* 129:    */   }
/* 130:    */   
/* 131:    */   @Deprecated
/* 132:    */   public static IOFileFilter orFileFilter(IOFileFilter filter1, IOFileFilter filter2)
/* 133:    */   {
/* 134:411 */     return new OrFileFilter(filter1, filter2);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public static IOFileFilter and(IOFileFilter... filters)
/* 138:    */   {
/* 139:426 */     return new AndFileFilter(toList(filters));
/* 140:    */   }
/* 141:    */   
/* 142:    */   public static IOFileFilter or(IOFileFilter... filters)
/* 143:    */   {
/* 144:441 */     return new OrFileFilter(toList(filters));
/* 145:    */   }
/* 146:    */   
/* 147:    */   public static List<IOFileFilter> toList(IOFileFilter... filters)
/* 148:    */   {
/* 149:454 */     if (filters == null) {
/* 150:455 */       throw new IllegalArgumentException("The filters must not be null");
/* 151:    */     }
/* 152:457 */     List<IOFileFilter> list = new ArrayList(filters.length);
/* 153:458 */     for (int i = 0; i < filters.length; i++)
/* 154:    */     {
/* 155:459 */       if (filters[i] == null) {
/* 156:460 */         throw new IllegalArgumentException("The filter[" + i + "] is null");
/* 157:    */       }
/* 158:462 */       list.add(filters[i]);
/* 159:    */     }
/* 160:464 */     return list;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public static IOFileFilter notFileFilter(IOFileFilter filter)
/* 164:    */   {
/* 165:475 */     return new NotFileFilter(filter);
/* 166:    */   }
/* 167:    */   
/* 168:    */   public static IOFileFilter trueFileFilter()
/* 169:    */   {
/* 170:486 */     return TrueFileFilter.TRUE;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public static IOFileFilter falseFileFilter()
/* 174:    */   {
/* 175:496 */     return FalseFileFilter.FALSE;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public static IOFileFilter asFileFilter(FileFilter filter)
/* 179:    */   {
/* 180:509 */     return new DelegateFileFilter(filter);
/* 181:    */   }
/* 182:    */   
/* 183:    */   public static IOFileFilter asFileFilter(FilenameFilter filter)
/* 184:    */   {
/* 185:521 */     return new DelegateFileFilter(filter);
/* 186:    */   }
/* 187:    */   
/* 188:    */   public static IOFileFilter ageFileFilter(long cutoff)
/* 189:    */   {
/* 190:535 */     return new AgeFileFilter(cutoff);
/* 191:    */   }
/* 192:    */   
/* 193:    */   public static IOFileFilter ageFileFilter(long cutoff, boolean acceptOlder)
/* 194:    */   {
/* 195:548 */     return new AgeFileFilter(cutoff, acceptOlder);
/* 196:    */   }
/* 197:    */   
/* 198:    */   public static IOFileFilter ageFileFilter(Date cutoffDate)
/* 199:    */   {
/* 200:561 */     return new AgeFileFilter(cutoffDate);
/* 201:    */   }
/* 202:    */   
/* 203:    */   public static IOFileFilter ageFileFilter(Date cutoffDate, boolean acceptOlder)
/* 204:    */   {
/* 205:574 */     return new AgeFileFilter(cutoffDate, acceptOlder);
/* 206:    */   }
/* 207:    */   
/* 208:    */   public static IOFileFilter ageFileFilter(File cutoffReference)
/* 209:    */   {
/* 210:588 */     return new AgeFileFilter(cutoffReference);
/* 211:    */   }
/* 212:    */   
/* 213:    */   public static IOFileFilter ageFileFilter(File cutoffReference, boolean acceptOlder)
/* 214:    */   {
/* 215:602 */     return new AgeFileFilter(cutoffReference, acceptOlder);
/* 216:    */   }
/* 217:    */   
/* 218:    */   public static IOFileFilter sizeFileFilter(long threshold)
/* 219:    */   {
/* 220:615 */     return new SizeFileFilter(threshold);
/* 221:    */   }
/* 222:    */   
/* 223:    */   public static IOFileFilter sizeFileFilter(long threshold, boolean acceptLarger)
/* 224:    */   {
/* 225:628 */     return new SizeFileFilter(threshold, acceptLarger);
/* 226:    */   }
/* 227:    */   
/* 228:    */   public static IOFileFilter sizeRangeFileFilter(long minSizeInclusive, long maxSizeInclusive)
/* 229:    */   {
/* 230:642 */     IOFileFilter minimumFilter = new SizeFileFilter(minSizeInclusive, true);
/* 231:643 */     IOFileFilter maximumFilter = new SizeFileFilter(maxSizeInclusive + 1L, false);
/* 232:644 */     return new AndFileFilter(minimumFilter, maximumFilter);
/* 233:    */   }
/* 234:    */   
/* 235:    */   public static IOFileFilter magicNumberFileFilter(String magicNumber)
/* 236:    */   {
/* 237:663 */     return new MagicNumberFileFilter(magicNumber);
/* 238:    */   }
/* 239:    */   
/* 240:    */   public static IOFileFilter magicNumberFileFilter(String magicNumber, long offset)
/* 241:    */   {
/* 242:684 */     return new MagicNumberFileFilter(magicNumber, offset);
/* 243:    */   }
/* 244:    */   
/* 245:    */   public static IOFileFilter magicNumberFileFilter(byte[] magicNumber)
/* 246:    */   {
/* 247:703 */     return new MagicNumberFileFilter(magicNumber);
/* 248:    */   }
/* 249:    */   
/* 250:    */   public static IOFileFilter magicNumberFileFilter(byte[] magicNumber, long offset)
/* 251:    */   {
/* 252:724 */     return new MagicNumberFileFilter(magicNumber, offset);
/* 253:    */   }
/* 254:    */   
/* 255:729 */   private static final IOFileFilter cvsFilter = notFileFilter(and(new IOFileFilter[] { directoryFileFilter(), nameFileFilter("CVS") }));
/* 256:733 */   private static final IOFileFilter svnFilter = notFileFilter(and(new IOFileFilter[] { directoryFileFilter(), nameFileFilter(".svn") }));
/* 257:    */   
/* 258:    */   public static IOFileFilter makeCVSAware(IOFileFilter filter)
/* 259:    */   {
/* 260:746 */     if (filter == null) {
/* 261:747 */       return cvsFilter;
/* 262:    */     }
/* 263:749 */     return and(new IOFileFilter[] { filter, cvsFilter });
/* 264:    */   }
/* 265:    */   
/* 266:    */   public static IOFileFilter makeSVNAware(IOFileFilter filter)
/* 267:    */   {
/* 268:763 */     if (filter == null) {
/* 269:764 */       return svnFilter;
/* 270:    */     }
/* 271:766 */     return and(new IOFileFilter[] { filter, svnFilter });
/* 272:    */   }
/* 273:    */   
/* 274:    */   public static IOFileFilter makeDirectoryOnly(IOFileFilter filter)
/* 275:    */   {
/* 276:780 */     if (filter == null) {
/* 277:781 */       return DirectoryFileFilter.DIRECTORY;
/* 278:    */     }
/* 279:783 */     return new AndFileFilter(DirectoryFileFilter.DIRECTORY, filter);
/* 280:    */   }
/* 281:    */   
/* 282:    */   public static IOFileFilter makeFileOnly(IOFileFilter filter)
/* 283:    */   {
/* 284:795 */     if (filter == null) {
/* 285:796 */       return FileFileFilter.FILE;
/* 286:    */     }
/* 287:798 */     return new AndFileFilter(FileFileFilter.FILE, filter);
/* 288:    */   }
/* 289:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.filefilter.FileFilterUtils
 * JD-Core Version:    0.7.0.1
 */