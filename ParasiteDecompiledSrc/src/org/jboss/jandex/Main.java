/*   1:    */ package org.jboss.jandex;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileInputStream;
/*   5:    */ import java.io.FileNotFoundException;
/*   6:    */ import java.io.FileOutputStream;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.io.PrintStream;
/*   9:    */ import java.util.Map;
/*  10:    */ 
/*  11:    */ public class Main
/*  12:    */ {
/*  13:    */   private boolean modify;
/*  14:    */   private boolean verbose;
/*  15:    */   private boolean dump;
/*  16:    */   private File outputFile;
/*  17:    */   private File source;
/*  18:    */   
/*  19:    */   public static void main(String[] args)
/*  20:    */   {
/*  21: 48 */     if (args.length == 0)
/*  22:    */     {
/*  23: 49 */       printUsage();
/*  24: 50 */       return;
/*  25:    */     }
/*  26: 53 */     Main main = new Main();
/*  27: 54 */     main.execute(args);
/*  28:    */   }
/*  29:    */   
/*  30:    */   private void execute(String[] args)
/*  31:    */   {
/*  32: 59 */     boolean printUsage = true;
/*  33:    */     try
/*  34:    */     {
/*  35: 61 */       parseOptions(args);
/*  36:    */       
/*  37: 63 */       printUsage = false;
/*  38: 64 */       if (this.dump)
/*  39:    */       {
/*  40: 65 */         dumpIndex(this.source);
/*  41: 66 */         return;
/*  42:    */       }
/*  43: 69 */       long start = System.currentTimeMillis();
/*  44: 70 */       Indexer indexer = new Indexer();
/*  45: 71 */       Result result = this.source.isDirectory() ? indexDirectory(this.source, indexer) : JarIndexer.createJarIndex(this.source, indexer, this.modify, false, this.verbose);
/*  46: 72 */       double time = (System.currentTimeMillis() - start) / 1000.0D;
/*  47: 73 */       System.out.printf("Wrote %s in %.4f seconds (%d classes, %d annotations, %d instances, %d bytes)\n", new Object[] { result.getName(), Double.valueOf(time), Integer.valueOf(result.getClasses()), Integer.valueOf(result.getAnnotations()), Integer.valueOf(result.getInstances()), Integer.valueOf(result.getBytes()) });
/*  48:    */     }
/*  49:    */     catch (Exception e)
/*  50:    */     {
/*  51: 75 */       if ((!this.verbose) && (((e instanceof IllegalArgumentException)) || ((e instanceof FileNotFoundException)))) {
/*  52: 76 */         System.err.println("ERROR: " + e.getMessage());
/*  53:    */       } else {
/*  54: 78 */         e.printStackTrace(System.err);
/*  55:    */       }
/*  56: 81 */       if (printUsage)
/*  57:    */       {
/*  58: 82 */         System.out.println();
/*  59: 83 */         printUsage();
/*  60:    */       }
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   private void dumpIndex(File source)
/*  65:    */     throws IOException
/*  66:    */   {
/*  67: 89 */     FileInputStream input = new FileInputStream(source);
/*  68: 90 */     IndexReader reader = new IndexReader(input);
/*  69:    */     
/*  70: 92 */     long start = System.currentTimeMillis();
/*  71: 93 */     Index index = reader.read();
/*  72: 94 */     long end = System.currentTimeMillis() - start;
/*  73: 95 */     index.printAnnotations();
/*  74: 96 */     index.printSubclasses();
/*  75:    */     
/*  76: 98 */     System.out.printf("\nRead %s in %.04f seconds\n", new Object[] { source.getName(), Double.valueOf(end / 1000.0D) });
/*  77:    */   }
/*  78:    */   
/*  79:    */   private Result indexDirectory(File source, Indexer indexer)
/*  80:    */     throws FileNotFoundException, IOException
/*  81:    */   {
/*  82:102 */     File outputFile = this.outputFile;
/*  83:103 */     scanFile(source, indexer);
/*  84:105 */     if (this.modify)
/*  85:    */     {
/*  86:106 */       new File(source, "META-INF").mkdirs();
/*  87:107 */       outputFile = new File(source, "META-INF/jandex.idx");
/*  88:    */     }
/*  89:109 */     if (outputFile == null) {
/*  90:110 */       outputFile = new File(source.getName().replace('.', '-') + ".idx");
/*  91:    */     }
/*  92:113 */     FileOutputStream out = new FileOutputStream(outputFile);
/*  93:114 */     IndexWriter writer = new IndexWriter(out);
/*  94:    */     try
/*  95:    */     {
/*  96:117 */       Index index = indexer.complete();
/*  97:118 */       int bytes = writer.write(index);
/*  98:119 */       return new Result(index, outputFile.getPath(), bytes);
/*  99:    */     }
/* 100:    */     finally
/* 101:    */     {
/* 102:121 */       out.flush();
/* 103:122 */       out.close();
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   private void printIndexEntryInfo(ClassInfo info)
/* 108:    */   {
/* 109:127 */     System.out.println("Indexed " + info.name() + " (" + info.annotations().size() + " annotations)");
/* 110:    */   }
/* 111:    */   
/* 112:    */   private void scanFile(File source, Indexer indexer)
/* 113:    */     throws FileNotFoundException, IOException
/* 114:    */   {
/* 115:131 */     if (source.isDirectory())
/* 116:    */     {
/* 117:132 */       File[] children = source.listFiles();
/* 118:133 */       if (children == null) {
/* 119:134 */         throw new FileNotFoundException("Source directory disappeared: " + source);
/* 120:    */       }
/* 121:136 */       for (File child : children) {
/* 122:137 */         scanFile(child, indexer);
/* 123:    */       }
/* 124:139 */       return;
/* 125:    */     }
/* 126:142 */     if (!source.getName().endsWith(".class")) {
/* 127:143 */       return;
/* 128:    */     }
/* 129:145 */     FileInputStream input = new FileInputStream(source);
/* 130:    */     try
/* 131:    */     {
/* 132:148 */       ClassInfo info = indexer.index(input);
/* 133:149 */       if ((this.verbose) && (info != null)) {
/* 134:150 */         printIndexEntryInfo(info);
/* 135:    */       }
/* 136:    */     }
/* 137:    */     catch (Exception e)
/* 138:    */     {
/* 139:152 */       String message = e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
/* 140:153 */       System.err.println("ERROR: Could not index " + source.getName() + ": " + message);
/* 141:154 */       if (this.verbose) {
/* 142:155 */         e.printStackTrace(System.err);
/* 143:    */       }
/* 144:    */     }
/* 145:    */   }
/* 146:    */   
/* 147:    */   private static void printUsage()
/* 148:    */   {
/* 149:162 */     System.out.println("Usage: jandex [-v] [-m] [-o file-name] <directory> | <jar>");
/* 150:163 */     System.out.println("        -or-");
/* 151:164 */     System.out.println("       jandex [-d] <index-file-name>");
/* 152:165 */     System.out.println("Options:");
/* 153:166 */     System.out.println("  -v  verbose output");
/* 154:167 */     System.out.println("  -m  modify directory or jar instead of creating an external index file");
/* 155:168 */     System.out.println("  -o  name the external index file file-name");
/* 156:169 */     System.out.println("  -d  dump the index file index-file-name");
/* 157:170 */     System.out.println("\nThe default behavior, with no options specified, is to autogenerate an external index file");
/* 158:    */   }
/* 159:    */   
/* 160:    */   private void parseOptions(String[] args)
/* 161:    */   {
/* 162:174 */     int optionCount = 0;
/* 163:176 */     for (int i = 0; i < args.length; i++)
/* 164:    */     {
/* 165:177 */       String arg = args[i];
/* 166:178 */       if ((arg.length() < 2) || (arg.charAt(0) != '-'))
/* 167:    */       {
/* 168:179 */         if (this.source != null) {
/* 169:180 */           throw new IllegalArgumentException("Only one source location can be specified");
/* 170:    */         }
/* 171:182 */         this.source = new File(arg);
/* 172:183 */         if (!this.source.exists()) {
/* 173:184 */           throw new IllegalArgumentException("Source directory/jar not found: " + this.source.getName());
/* 174:    */         }
/* 175:    */       }
/* 176:    */       else
/* 177:    */       {
/* 178:190 */         switch (arg.charAt(1))
/* 179:    */         {
/* 180:    */         case 'm': 
/* 181:192 */           this.modify = true;
/* 182:193 */           optionCount++;
/* 183:194 */           break;
/* 184:    */         case 'd': 
/* 185:196 */           this.dump = true;
/* 186:197 */           optionCount++;
/* 187:198 */           break;
/* 188:    */         case 'v': 
/* 189:200 */           this.verbose = true;
/* 190:201 */           optionCount++;
/* 191:202 */           break;
/* 192:    */         case 'o': 
/* 193:204 */           if (i >= args.length) {
/* 194:205 */             throw new IllegalArgumentException("-o reuires an output file name");
/* 195:    */           }
/* 196:207 */           String name = args[(++i)];
/* 197:208 */           if (name.length() < 1) {
/* 198:209 */             throw new IllegalArgumentException("-o reuires an output file name");
/* 199:    */           }
/* 200:211 */           this.outputFile = new File(name);
/* 201:212 */           optionCount++;
/* 202:213 */           break;
/* 203:    */         default: 
/* 204:215 */           throw new IllegalArgumentException("Option not understood: " + arg);
/* 205:    */         }
/* 206:    */       }
/* 207:    */     }
/* 208:219 */     if (this.source == null) {
/* 209:220 */       throw new IllegalArgumentException("Source location not specified");
/* 210:    */     }
/* 211:222 */     if ((this.outputFile != null) && (this.modify)) {
/* 212:223 */       throw new IllegalArgumentException("-o and -m are mutually exclusive");
/* 213:    */     }
/* 214:225 */     if ((this.dump) && (optionCount != 1)) {
/* 215:226 */       throw new IllegalArgumentException("-d can not be specified with other options");
/* 216:    */     }
/* 217:    */   }
/* 218:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.jandex.Main
 * JD-Core Version:    0.7.0.1
 */