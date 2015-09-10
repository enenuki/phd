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
/*  22:    */ public class SchemaUpdateTask
/*  23:    */   extends MatchingTask
/*  24:    */ {
/*  25: 70 */   private List fileSets = new LinkedList();
/*  26: 71 */   private File propertiesFile = null;
/*  27: 72 */   private File configurationFile = null;
/*  28: 73 */   private File outputFile = null;
/*  29: 74 */   private boolean quiet = false;
/*  30: 75 */   private boolean text = true;
/*  31: 76 */   private boolean haltOnError = false;
/*  32: 77 */   private String delimiter = null;
/*  33: 78 */   private String namingStrategy = null;
/*  34:    */   
/*  35:    */   public void addFileset(FileSet set)
/*  36:    */   {
/*  37: 82 */     this.fileSets.add(set);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setProperties(File propertiesFile)
/*  41:    */   {
/*  42: 90 */     if (!propertiesFile.exists()) {
/*  43: 91 */       throw new BuildException("Properties file: " + propertiesFile + " does not exist.");
/*  44:    */     }
/*  45: 94 */     log("Using properties file " + propertiesFile, 4);
/*  46: 95 */     this.propertiesFile = propertiesFile;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setConfig(File configurationFile)
/*  50:    */   {
/*  51:103 */     this.configurationFile = configurationFile;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setText(boolean text)
/*  55:    */   {
/*  56:112 */     this.text = text;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setQuiet(boolean quiet)
/*  60:    */   {
/*  61:121 */     this.quiet = quiet;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void execute()
/*  65:    */     throws BuildException
/*  66:    */   {
/*  67:    */     try
/*  68:    */     {
/*  69:130 */       log("Running Hibernate Core SchemaUpdate.");
/*  70:131 */       log("This is an Ant task supporting only mapping files, if you want to use annotations see http://tools.hibernate.org.");
/*  71:132 */       Configuration cfg = getConfiguration();
/*  72:133 */       getSchemaUpdate(cfg).execute(!this.quiet, !this.text);
/*  73:    */     }
/*  74:    */     catch (HibernateException e)
/*  75:    */     {
/*  76:136 */       throw new BuildException("Schema text failed: " + e.getMessage(), e);
/*  77:    */     }
/*  78:    */     catch (FileNotFoundException e)
/*  79:    */     {
/*  80:139 */       throw new BuildException("File not found: " + e.getMessage(), e);
/*  81:    */     }
/*  82:    */     catch (IOException e)
/*  83:    */     {
/*  84:142 */       throw new BuildException("IOException : " + e.getMessage(), e);
/*  85:    */     }
/*  86:    */     catch (Exception e)
/*  87:    */     {
/*  88:145 */       throw new BuildException(e);
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   private String[] getFiles()
/*  93:    */   {
/*  94:151 */     List files = new LinkedList();
/*  95:152 */     for (Iterator i = this.fileSets.iterator(); i.hasNext();)
/*  96:    */     {
/*  97:154 */       FileSet fs = (FileSet)i.next();
/*  98:155 */       DirectoryScanner ds = fs.getDirectoryScanner(getProject());
/*  99:    */       
/* 100:157 */       String[] dsFiles = ds.getIncludedFiles();
/* 101:158 */       for (int j = 0; j < dsFiles.length; j++)
/* 102:    */       {
/* 103:159 */         File f = new File(dsFiles[j]);
/* 104:160 */         if (!f.isFile()) {
/* 105:161 */           f = new File(ds.getBasedir(), dsFiles[j]);
/* 106:    */         }
/* 107:164 */         files.add(f.getAbsolutePath());
/* 108:    */       }
/* 109:    */     }
/* 110:168 */     return ArrayHelper.toStringArray(files);
/* 111:    */   }
/* 112:    */   
/* 113:    */   private Configuration getConfiguration()
/* 114:    */     throws Exception
/* 115:    */   {
/* 116:172 */     Configuration cfg = new Configuration();
/* 117:173 */     if (this.namingStrategy != null) {
/* 118:174 */       cfg.setNamingStrategy((NamingStrategy)ReflectHelper.classForName(this.namingStrategy).newInstance());
/* 119:    */     }
/* 120:178 */     if (this.configurationFile != null) {
/* 121:179 */       cfg.configure(this.configurationFile);
/* 122:    */     }
/* 123:182 */     String[] files = getFiles();
/* 124:183 */     for (int i = 0; i < files.length; i++)
/* 125:    */     {
/* 126:184 */       String filename = files[i];
/* 127:185 */       if (filename.endsWith(".jar")) {
/* 128:186 */         cfg.addJar(new File(filename));
/* 129:    */       } else {
/* 130:189 */         cfg.addFile(filename);
/* 131:    */       }
/* 132:    */     }
/* 133:192 */     return cfg;
/* 134:    */   }
/* 135:    */   
/* 136:    */   private SchemaUpdate getSchemaUpdate(Configuration cfg)
/* 137:    */     throws HibernateException, IOException
/* 138:    */   {
/* 139:196 */     Properties properties = new Properties();
/* 140:197 */     properties.putAll(cfg.getProperties());
/* 141:198 */     if (this.propertiesFile == null) {
/* 142:199 */       properties.putAll(getProject().getProperties());
/* 143:    */     } else {
/* 144:202 */       properties.load(new FileInputStream(this.propertiesFile));
/* 145:    */     }
/* 146:204 */     cfg.setProperties(properties);
/* 147:205 */     SchemaUpdate su = new SchemaUpdate(cfg);
/* 148:206 */     su.setOutputFile(this.outputFile.getPath());
/* 149:207 */     su.setDelimiter(this.delimiter);
/* 150:208 */     su.setHaltOnError(this.haltOnError);
/* 151:209 */     return su;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setNamingStrategy(String namingStrategy)
/* 155:    */   {
/* 156:213 */     this.namingStrategy = namingStrategy;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public File getOutputFile()
/* 160:    */   {
/* 161:217 */     return this.outputFile;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setOutputFile(File outputFile)
/* 165:    */   {
/* 166:221 */     this.outputFile = outputFile;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public boolean isHaltOnError()
/* 170:    */   {
/* 171:225 */     return this.haltOnError;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setHaltOnError(boolean haltOnError)
/* 175:    */   {
/* 176:229 */     this.haltOnError = haltOnError;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public String getDelimiter()
/* 180:    */   {
/* 181:233 */     return this.delimiter;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setDelimiter(String delimiter)
/* 185:    */   {
/* 186:237 */     this.delimiter = delimiter;
/* 187:    */   }
/* 188:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tool.hbm2ddl.SchemaUpdateTask
 * JD-Core Version:    0.7.0.1
 */