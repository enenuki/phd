/*   1:    */ package org.hibernate.tool.hbm2ddl;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileInputStream;
/*   5:    */ import java.io.FileNotFoundException;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.LinkedList;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Properties;
/*  11:    */ import org.apache.tools.ant.BuildException;
/*  12:    */ import org.apache.tools.ant.DirectoryScanner;
/*  13:    */ import org.apache.tools.ant.Project;
/*  14:    */ import org.apache.tools.ant.taskdefs.MatchingTask;
/*  15:    */ import org.apache.tools.ant.types.FileSet;
/*  16:    */ import org.hibernate.HibernateException;
/*  17:    */ import org.hibernate.cfg.Configuration;
/*  18:    */ import org.hibernate.cfg.NamingStrategy;
/*  19:    */ import org.hibernate.internal.util.ReflectHelper;
/*  20:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  21:    */ 
/*  22:    */ public class SchemaValidatorTask
/*  23:    */   extends MatchingTask
/*  24:    */ {
/*  25: 69 */   private List fileSets = new LinkedList();
/*  26: 70 */   private File propertiesFile = null;
/*  27: 71 */   private File configurationFile = null;
/*  28: 72 */   private String namingStrategy = null;
/*  29:    */   
/*  30:    */   public void addFileset(FileSet set)
/*  31:    */   {
/*  32: 75 */     this.fileSets.add(set);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void setProperties(File propertiesFile)
/*  36:    */   {
/*  37: 83 */     if (!propertiesFile.exists()) {
/*  38: 84 */       throw new BuildException("Properties file: " + propertiesFile + " does not exist.");
/*  39:    */     }
/*  40: 87 */     log("Using properties file " + propertiesFile, 4);
/*  41: 88 */     this.propertiesFile = propertiesFile;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setConfig(File configurationFile)
/*  45:    */   {
/*  46: 96 */     this.configurationFile = configurationFile;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void execute()
/*  50:    */     throws BuildException
/*  51:    */   {
/*  52:    */     try
/*  53:    */     {
/*  54:105 */       Configuration cfg = getConfiguration();
/*  55:106 */       getSchemaValidator(cfg).validate();
/*  56:    */     }
/*  57:    */     catch (HibernateException e)
/*  58:    */     {
/*  59:109 */       throw new BuildException("Schema text failed: " + e.getMessage(), e);
/*  60:    */     }
/*  61:    */     catch (FileNotFoundException e)
/*  62:    */     {
/*  63:112 */       throw new BuildException("File not found: " + e.getMessage(), e);
/*  64:    */     }
/*  65:    */     catch (IOException e)
/*  66:    */     {
/*  67:115 */       throw new BuildException("IOException : " + e.getMessage(), e);
/*  68:    */     }
/*  69:    */     catch (Exception e)
/*  70:    */     {
/*  71:118 */       throw new BuildException(e);
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   private String[] getFiles()
/*  76:    */   {
/*  77:124 */     List files = new LinkedList();
/*  78:125 */     for (Iterator i = this.fileSets.iterator(); i.hasNext();)
/*  79:    */     {
/*  80:127 */       FileSet fs = (FileSet)i.next();
/*  81:128 */       DirectoryScanner ds = fs.getDirectoryScanner(getProject());
/*  82:    */       
/*  83:130 */       String[] dsFiles = ds.getIncludedFiles();
/*  84:131 */       for (int j = 0; j < dsFiles.length; j++)
/*  85:    */       {
/*  86:132 */         File f = new File(dsFiles[j]);
/*  87:133 */         if (!f.isFile()) {
/*  88:134 */           f = new File(ds.getBasedir(), dsFiles[j]);
/*  89:    */         }
/*  90:137 */         files.add(f.getAbsolutePath());
/*  91:    */       }
/*  92:    */     }
/*  93:141 */     return ArrayHelper.toStringArray(files);
/*  94:    */   }
/*  95:    */   
/*  96:    */   private Configuration getConfiguration()
/*  97:    */     throws Exception
/*  98:    */   {
/*  99:145 */     Configuration cfg = new Configuration();
/* 100:146 */     if (this.namingStrategy != null) {
/* 101:147 */       cfg.setNamingStrategy((NamingStrategy)ReflectHelper.classForName(this.namingStrategy).newInstance());
/* 102:    */     }
/* 103:151 */     if (this.configurationFile != null) {
/* 104:152 */       cfg.configure(this.configurationFile);
/* 105:    */     }
/* 106:155 */     String[] files = getFiles();
/* 107:156 */     for (int i = 0; i < files.length; i++)
/* 108:    */     {
/* 109:157 */       String filename = files[i];
/* 110:158 */       if (filename.endsWith(".jar")) {
/* 111:159 */         cfg.addJar(new File(filename));
/* 112:    */       } else {
/* 113:162 */         cfg.addFile(filename);
/* 114:    */       }
/* 115:    */     }
/* 116:165 */     return cfg;
/* 117:    */   }
/* 118:    */   
/* 119:    */   private SchemaValidator getSchemaValidator(Configuration cfg)
/* 120:    */     throws HibernateException, IOException
/* 121:    */   {
/* 122:169 */     Properties properties = new Properties();
/* 123:170 */     properties.putAll(cfg.getProperties());
/* 124:171 */     if (this.propertiesFile == null) {
/* 125:172 */       properties.putAll(getProject().getProperties());
/* 126:    */     } else {
/* 127:175 */       properties.load(new FileInputStream(this.propertiesFile));
/* 128:    */     }
/* 129:177 */     cfg.setProperties(properties);
/* 130:178 */     return new SchemaValidator(cfg);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setNamingStrategy(String namingStrategy)
/* 134:    */   {
/* 135:182 */     this.namingStrategy = namingStrategy;
/* 136:    */   }
/* 137:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tool.hbm2ddl.SchemaValidatorTask
 * JD-Core Version:    0.7.0.1
 */