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
/*  22:    */ public class SchemaExportTask
/*  23:    */   extends MatchingTask
/*  24:    */ {
/*  25: 74 */   private List fileSets = new LinkedList();
/*  26: 75 */   private File propertiesFile = null;
/*  27: 76 */   private File configurationFile = null;
/*  28: 77 */   private File outputFile = null;
/*  29: 78 */   private boolean quiet = false;
/*  30: 79 */   private boolean text = false;
/*  31: 80 */   private boolean drop = false;
/*  32: 81 */   private boolean create = false;
/*  33: 82 */   private boolean haltOnError = false;
/*  34: 83 */   private String delimiter = null;
/*  35: 84 */   private String namingStrategy = null;
/*  36:    */   
/*  37:    */   public void addFileset(FileSet set)
/*  38:    */   {
/*  39: 87 */     this.fileSets.add(set);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setProperties(File propertiesFile)
/*  43:    */   {
/*  44: 95 */     if (!propertiesFile.exists()) {
/*  45: 96 */       throw new BuildException("Properties file: " + propertiesFile + " does not exist.");
/*  46:    */     }
/*  47: 99 */     log("Using properties file " + propertiesFile, 4);
/*  48:100 */     this.propertiesFile = propertiesFile;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setConfig(File configurationFile)
/*  52:    */   {
/*  53:109 */     this.configurationFile = configurationFile;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setQuiet(boolean quiet)
/*  57:    */   {
/*  58:118 */     this.quiet = quiet;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setText(boolean text)
/*  62:    */   {
/*  63:127 */     this.text = text;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setDrop(boolean drop)
/*  67:    */   {
/*  68:136 */     this.drop = drop;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setCreate(boolean create)
/*  72:    */   {
/*  73:145 */     this.create = create;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setDelimiter(String delimiter)
/*  77:    */   {
/*  78:153 */     this.delimiter = delimiter;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setOutput(File outputFile)
/*  82:    */   {
/*  83:161 */     this.outputFile = outputFile;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void execute()
/*  87:    */     throws BuildException
/*  88:    */   {
/*  89:    */     try
/*  90:    */     {
/*  91:170 */       getSchemaExport(getConfiguration()).execute(!this.quiet, !this.text, this.drop, this.create);
/*  92:    */     }
/*  93:    */     catch (HibernateException e)
/*  94:    */     {
/*  95:173 */       throw new BuildException("Schema text failed: " + e.getMessage(), e);
/*  96:    */     }
/*  97:    */     catch (FileNotFoundException e)
/*  98:    */     {
/*  99:176 */       throw new BuildException("File not found: " + e.getMessage(), e);
/* 100:    */     }
/* 101:    */     catch (IOException e)
/* 102:    */     {
/* 103:179 */       throw new BuildException("IOException : " + e.getMessage(), e);
/* 104:    */     }
/* 105:    */     catch (Exception e)
/* 106:    */     {
/* 107:182 */       throw new BuildException(e);
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   private String[] getFiles()
/* 112:    */   {
/* 113:188 */     List files = new LinkedList();
/* 114:189 */     for (Iterator i = this.fileSets.iterator(); i.hasNext();)
/* 115:    */     {
/* 116:191 */       FileSet fs = (FileSet)i.next();
/* 117:192 */       DirectoryScanner ds = fs.getDirectoryScanner(getProject());
/* 118:    */       
/* 119:194 */       String[] dsFiles = ds.getIncludedFiles();
/* 120:195 */       for (int j = 0; j < dsFiles.length; j++)
/* 121:    */       {
/* 122:196 */         File f = new File(dsFiles[j]);
/* 123:197 */         if (!f.isFile()) {
/* 124:198 */           f = new File(ds.getBasedir(), dsFiles[j]);
/* 125:    */         }
/* 126:201 */         files.add(f.getAbsolutePath());
/* 127:    */       }
/* 128:    */     }
/* 129:205 */     return ArrayHelper.toStringArray(files);
/* 130:    */   }
/* 131:    */   
/* 132:    */   private Configuration getConfiguration()
/* 133:    */     throws Exception
/* 134:    */   {
/* 135:209 */     Configuration cfg = new Configuration();
/* 136:210 */     if (this.namingStrategy != null) {
/* 137:211 */       cfg.setNamingStrategy((NamingStrategy)ReflectHelper.classForName(this.namingStrategy).newInstance());
/* 138:    */     }
/* 139:215 */     if (this.configurationFile != null) {
/* 140:216 */       cfg.configure(this.configurationFile);
/* 141:    */     }
/* 142:219 */     String[] files = getFiles();
/* 143:220 */     for (int i = 0; i < files.length; i++)
/* 144:    */     {
/* 145:221 */       String filename = files[i];
/* 146:222 */       if (filename.endsWith(".jar")) {
/* 147:223 */         cfg.addJar(new File(filename));
/* 148:    */       } else {
/* 149:226 */         cfg.addFile(filename);
/* 150:    */       }
/* 151:    */     }
/* 152:229 */     return cfg;
/* 153:    */   }
/* 154:    */   
/* 155:    */   private SchemaExport getSchemaExport(Configuration cfg)
/* 156:    */     throws HibernateException, IOException
/* 157:    */   {
/* 158:233 */     Properties properties = new Properties();
/* 159:234 */     properties.putAll(cfg.getProperties());
/* 160:235 */     if (this.propertiesFile == null) {
/* 161:236 */       properties.putAll(getProject().getProperties());
/* 162:    */     } else {
/* 163:239 */       properties.load(new FileInputStream(this.propertiesFile));
/* 164:    */     }
/* 165:241 */     cfg.setProperties(properties);
/* 166:242 */     return new SchemaExport(cfg).setHaltOnError(this.haltOnError).setOutputFile(this.outputFile.getPath()).setDelimiter(this.delimiter);
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setNamingStrategy(String namingStrategy)
/* 170:    */   {
/* 171:249 */     this.namingStrategy = namingStrategy;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setHaltonerror(boolean haltOnError)
/* 175:    */   {
/* 176:253 */     this.haltOnError = haltOnError;
/* 177:    */   }
/* 178:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tool.hbm2ddl.SchemaExportTask
 * JD-Core Version:    0.7.0.1
 */