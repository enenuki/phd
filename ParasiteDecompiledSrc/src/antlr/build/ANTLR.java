/*  1:   */ package antlr.build;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.io.FilenameFilter;
/*  5:   */ 
/*  6:   */ public class ANTLR
/*  7:   */ {
/*  8: 7 */   public static String compiler = "javac";
/*  9: 8 */   public static String jarName = "antlr.jar";
/* 10: 9 */   public static String root = ".";
/* 11:11 */   public static String[] srcdir = { "antlr", "antlr/actions/cpp", "antlr/actions/java", "antlr/actions/csharp", "antlr/collections", "antlr/collections/impl", "antlr/debug", "antlr/ASdebug", "antlr/debug/misc", "antlr/preprocessor" };
/* 12:   */   
/* 13:   */   public ANTLR()
/* 14:   */   {
/* 15:25 */     compiler = System.getProperty("antlr.build.compiler", compiler);
/* 16:26 */     root = System.getProperty("antlr.build.root", root);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getName()
/* 20:   */   {
/* 21:29 */     return "ANTLR";
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void build(Tool paramTool)
/* 25:   */   {
/* 26:33 */     if (!rootIsValidANTLRDir(paramTool)) {
/* 27:34 */       return;
/* 28:   */     }
/* 29:37 */     paramTool.antlr(root + "/antlr/antlr.g");
/* 30:38 */     paramTool.antlr(root + "/antlr/tokdef.g");
/* 31:39 */     paramTool.antlr(root + "/antlr/preprocessor/preproc.g");
/* 32:40 */     paramTool.antlr(root + "/antlr/actions/java/action.g");
/* 33:41 */     paramTool.antlr(root + "/antlr/actions/cpp/action.g");
/* 34:42 */     paramTool.antlr(root + "/antlr/actions/csharp/action.g");
/* 35:43 */     for (int i = 0; i < srcdir.length; i++)
/* 36:   */     {
/* 37:44 */       String str = compiler + " -d " + root + " " + root + "/" + srcdir[i] + "/*.java";
/* 38:45 */       paramTool.system(str);
/* 39:   */     }
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void jar(Tool paramTool)
/* 43:   */   {
/* 44:51 */     if (!rootIsValidANTLRDir(paramTool)) {
/* 45:52 */       return;
/* 46:   */     }
/* 47:54 */     StringBuffer localStringBuffer = new StringBuffer(2000);
/* 48:55 */     localStringBuffer.append("jar cvf " + root + "/" + jarName);
/* 49:56 */     for (int i = 0; i < srcdir.length; i++) {
/* 50:57 */       localStringBuffer.append(" " + root + "/" + srcdir[i] + "/*.class");
/* 51:   */     }
/* 52:59 */     paramTool.system(localStringBuffer.toString());
/* 53:   */   }
/* 54:   */   
/* 55:   */   protected boolean rootIsValidANTLRDir(Tool paramTool)
/* 56:   */   {
/* 57:66 */     if (root == null) {
/* 58:67 */       return false;
/* 59:   */     }
/* 60:69 */     File localFile1 = new File(root);
/* 61:70 */     if (!localFile1.exists())
/* 62:   */     {
/* 63:71 */       paramTool.error("Property antlr.build.root==" + root + " does not exist");
/* 64:72 */       return false;
/* 65:   */     }
/* 66:74 */     if (!localFile1.isDirectory())
/* 67:   */     {
/* 68:75 */       paramTool.error("Property antlr.build.root==" + root + " is not a directory");
/* 69:76 */       return false;
/* 70:   */     }
/* 71:78 */     String[] arrayOfString1 = localFile1.list(new FilenameFilter()
/* 72:   */     {
/* 73:   */       public boolean accept(File paramAnonymousFile, String paramAnonymousString)
/* 74:   */       {
/* 75:80 */         return (paramAnonymousFile.isDirectory()) && (paramAnonymousString.equals("antlr"));
/* 76:   */       }
/* 77:   */     });
/* 78:83 */     if ((arrayOfString1 == null) || (arrayOfString1.length == 0))
/* 79:   */     {
/* 80:84 */       paramTool.error("Property antlr.build.root==" + root + " does not appear to be a valid ANTLR project root (no antlr subdir)");
/* 81:85 */       return false;
/* 82:   */     }
/* 83:87 */     File localFile2 = new File(root + "/antlr");
/* 84:88 */     String[] arrayOfString2 = localFile2.list();
/* 85:89 */     if ((arrayOfString2 == null) || (arrayOfString2.length == 0))
/* 86:   */     {
/* 87:90 */       paramTool.error("Property antlr.build.root==" + root + " does not appear to be a valid ANTLR project root (no .java files in antlr subdir");
/* 88:91 */       return false;
/* 89:   */     }
/* 90:93 */     return true;
/* 91:   */   }
/* 92:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.build.ANTLR
 * JD-Core Version:    0.7.0.1
 */