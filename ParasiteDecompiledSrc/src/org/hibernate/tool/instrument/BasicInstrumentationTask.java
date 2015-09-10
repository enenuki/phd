/*   1:    */ package org.hibernate.tool.instrument;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Set;
/*   9:    */ import org.apache.tools.ant.BuildException;
/*  10:    */ import org.apache.tools.ant.DirectoryScanner;
/*  11:    */ import org.apache.tools.ant.Project;
/*  12:    */ import org.apache.tools.ant.Task;
/*  13:    */ import org.apache.tools.ant.types.FileSet;
/*  14:    */ import org.hibernate.bytecode.buildtime.spi.Instrumenter;
/*  15:    */ import org.hibernate.bytecode.buildtime.spi.Instrumenter.Options;
/*  16:    */ import org.hibernate.bytecode.buildtime.spi.Logger;
/*  17:    */ 
/*  18:    */ public abstract class BasicInstrumentationTask
/*  19:    */   extends Task
/*  20:    */   implements Instrumenter.Options
/*  21:    */ {
/*  22:    */   private final LoggerBridge logger;
/*  23:    */   private List filesets;
/*  24:    */   private boolean extended;
/*  25:    */   private boolean verbose;
/*  26:    */   
/*  27:    */   public BasicInstrumentationTask()
/*  28:    */   {
/*  29: 50 */     this.logger = new LoggerBridge();
/*  30:    */     
/*  31: 52 */     this.filesets = new ArrayList();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void addFileset(FileSet set)
/*  35:    */   {
/*  36: 59 */     this.filesets.add(set);
/*  37:    */   }
/*  38:    */   
/*  39:    */   protected final Iterator filesets()
/*  40:    */   {
/*  41: 63 */     return this.filesets.iterator();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean isExtended()
/*  45:    */   {
/*  46: 67 */     return this.extended;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setExtended(boolean extended)
/*  50:    */   {
/*  51: 71 */     this.extended = extended;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean isVerbose()
/*  55:    */   {
/*  56: 75 */     return this.verbose;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setVerbose(boolean verbose)
/*  60:    */   {
/*  61: 79 */     this.verbose = verbose;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public final boolean performExtendedInstrumentation()
/*  65:    */   {
/*  66: 83 */     return isExtended();
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected abstract Instrumenter buildInstrumenter(Logger paramLogger, Instrumenter.Options paramOptions);
/*  70:    */   
/*  71:    */   public void execute()
/*  72:    */     throws BuildException
/*  73:    */   {
/*  74:    */     try
/*  75:    */     {
/*  76: 91 */       buildInstrumenter(this.logger, this).execute(collectSpecifiedFiles());
/*  77:    */     }
/*  78:    */     catch (Throwable t)
/*  79:    */     {
/*  80: 95 */       throw new BuildException(t);
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   private Set collectSpecifiedFiles()
/*  85:    */   {
/*  86:100 */     HashSet files = new HashSet();
/*  87:101 */     Project project = getProject();
/*  88:102 */     Iterator filesets = filesets();
/*  89:103 */     while (filesets.hasNext())
/*  90:    */     {
/*  91:104 */       FileSet fs = (FileSet)filesets.next();
/*  92:105 */       DirectoryScanner ds = fs.getDirectoryScanner(project);
/*  93:106 */       String[] includedFiles = ds.getIncludedFiles();
/*  94:107 */       File d = fs.getDir(project);
/*  95:108 */       for (int i = 0; i < includedFiles.length; i++) {
/*  96:109 */         files.add(new File(d, includedFiles[i]));
/*  97:    */       }
/*  98:    */     }
/*  99:112 */     return files;
/* 100:    */   }
/* 101:    */   
/* 102:    */   protected class LoggerBridge
/* 103:    */     implements Logger
/* 104:    */   {
/* 105:    */     protected LoggerBridge() {}
/* 106:    */     
/* 107:    */     public void trace(String message)
/* 108:    */     {
/* 109:117 */       BasicInstrumentationTask.this.log(message, 3);
/* 110:    */     }
/* 111:    */     
/* 112:    */     public void debug(String message)
/* 113:    */     {
/* 114:121 */       BasicInstrumentationTask.this.log(message, 4);
/* 115:    */     }
/* 116:    */     
/* 117:    */     public void info(String message)
/* 118:    */     {
/* 119:125 */       BasicInstrumentationTask.this.log(message, 2);
/* 120:    */     }
/* 121:    */     
/* 122:    */     public void warn(String message)
/* 123:    */     {
/* 124:129 */       BasicInstrumentationTask.this.log(message, 1);
/* 125:    */     }
/* 126:    */     
/* 127:    */     public void error(String message)
/* 128:    */     {
/* 129:133 */       BasicInstrumentationTask.this.log(message, 0);
/* 130:    */     }
/* 131:    */   }
/* 132:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tool.instrument.BasicInstrumentationTask
 * JD-Core Version:    0.7.0.1
 */