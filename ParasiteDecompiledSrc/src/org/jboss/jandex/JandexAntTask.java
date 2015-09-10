/*   1:    */ package org.jboss.jandex;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import org.apache.tools.ant.BuildException;
/*   8:    */ import org.apache.tools.ant.DirectoryScanner;
/*   9:    */ import org.apache.tools.ant.Task;
/*  10:    */ import org.apache.tools.ant.types.FileSet;
/*  11:    */ 
/*  12:    */ public class JandexAntTask
/*  13:    */   extends Task
/*  14:    */ {
/*  15: 39 */   private final List<FileSet> filesets = new ArrayList();
/*  16: 41 */   private boolean modify = false;
/*  17: 43 */   private boolean newJar = false;
/*  18: 45 */   private boolean verbose = false;
/*  19: 47 */   private boolean run = true;
/*  20:    */   
/*  21:    */   public void execute()
/*  22:    */     throws BuildException
/*  23:    */   {
/*  24: 51 */     if (!this.run) {
/*  25: 52 */       return;
/*  26:    */     }
/*  27: 54 */     if ((this.modify) && (this.newJar)) {
/*  28: 55 */       throw new BuildException("Specifying both modify and newJar does not make sense.");
/*  29:    */     }
/*  30: 57 */     Indexer indexer = new Indexer();
/*  31: 58 */     for (FileSet fileset : this.filesets)
/*  32:    */     {
/*  33: 59 */       String[] files = fileset.getDirectoryScanner(getProject()).getIncludedFiles();
/*  34: 60 */       for (String file : files) {
/*  35: 61 */         if (file.endsWith(".jar")) {
/*  36:    */           try
/*  37:    */           {
/*  38: 63 */             JarIndexer.createJarIndex(new File(fileset.getDir().getAbsolutePath() + "/" + file), indexer, this.modify, this.newJar, this.verbose);
/*  39:    */           }
/*  40:    */           catch (IOException e)
/*  41:    */           {
/*  42: 65 */             throw new BuildException(e);
/*  43:    */           }
/*  44:    */         }
/*  45:    */       }
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void addFileset(FileSet fileset)
/*  50:    */   {
/*  51: 74 */     this.filesets.add(fileset);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean isModify()
/*  55:    */   {
/*  56: 78 */     return this.modify;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setModify(boolean modify)
/*  60:    */   {
/*  61: 82 */     this.modify = modify;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean isVerbose()
/*  65:    */   {
/*  66: 86 */     return this.verbose;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setVerbose(boolean verbose)
/*  70:    */   {
/*  71: 90 */     this.verbose = verbose;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public boolean isRun()
/*  75:    */   {
/*  76: 94 */     return this.run;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setRun(boolean run)
/*  80:    */   {
/*  81: 98 */     this.run = run;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public boolean isNewJar()
/*  85:    */   {
/*  86:102 */     return this.newJar;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setNewJar(boolean newJar)
/*  90:    */   {
/*  91:106 */     this.newJar = newJar;
/*  92:    */   }
/*  93:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.jandex.JandexAntTask
 * JD-Core Version:    0.7.0.1
 */