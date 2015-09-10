/*   1:    */ package org.apache.xalan.xsltc.cmdline;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import java.net.URL;
/*   6:    */ import java.util.Vector;
/*   7:    */ import org.apache.xalan.xsltc.cmdline.getopt.GetOpt;
/*   8:    */ import org.apache.xalan.xsltc.cmdline.getopt.GetOptsException;
/*   9:    */ import org.apache.xalan.xsltc.compiler.XSLTC;
/*  10:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  11:    */ 
/*  12:    */ public final class Compile
/*  13:    */ {
/*  14: 42 */   private static int VERSION_MAJOR = 1;
/*  15: 43 */   private static int VERSION_MINOR = 4;
/*  16: 44 */   private static int VERSION_DELTA = 0;
/*  17:    */   
/*  18:    */   public static void printUsage()
/*  19:    */   {
/*  20: 48 */     StringBuffer vers = new StringBuffer("XSLTC version " + VERSION_MAJOR + "." + VERSION_MINOR + (VERSION_DELTA > 0 ? "." + VERSION_DELTA : ""));
/*  21:    */     
/*  22:    */ 
/*  23: 51 */     System.err.println(vers + "\n" + new ErrorMsg("COMPILE_USAGE_STR"));
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static void main(String[] args)
/*  27:    */   {
/*  28:    */     try
/*  29:    */     {
/*  30: 64 */       boolean inputIsURL = false;
/*  31: 65 */       boolean useStdIn = false;
/*  32: 66 */       boolean classNameSet = false;
/*  33: 67 */       GetOpt getopt = new GetOpt(args, "o:d:j:p:uxhsinv");
/*  34: 68 */       if (args.length < 1) {
/*  35: 68 */         printUsage();
/*  36:    */       }
/*  37: 70 */       XSLTC xsltc = new XSLTC();
/*  38: 71 */       xsltc.init();
/*  39:    */       int c;
/*  40: 74 */       while ((c = getopt.getNextOption()) != -1)
/*  41:    */       {
/*  42:    */         int i;
/*  43: 75 */         switch (i)
/*  44:    */         {
/*  45:    */         case 105: 
/*  46: 77 */           useStdIn = true;
/*  47: 78 */           break;
/*  48:    */         case 111: 
/*  49: 80 */           xsltc.setClassName(getopt.getOptionArg());
/*  50: 81 */           classNameSet = true;
/*  51: 82 */           break;
/*  52:    */         case 100: 
/*  53: 84 */           xsltc.setDestDirectory(getopt.getOptionArg());
/*  54: 85 */           break;
/*  55:    */         case 112: 
/*  56: 87 */           xsltc.setPackageName(getopt.getOptionArg());
/*  57: 88 */           break;
/*  58:    */         case 106: 
/*  59: 90 */           xsltc.setJarFileName(getopt.getOptionArg());
/*  60: 91 */           break;
/*  61:    */         case 120: 
/*  62: 93 */           xsltc.setDebug(true);
/*  63: 94 */           break;
/*  64:    */         case 117: 
/*  65: 96 */           inputIsURL = true;
/*  66: 97 */           break;
/*  67:    */         case 110: 
/*  68: 99 */           xsltc.setTemplateInlining(true);
/*  69:100 */           break;
/*  70:    */         case 101: 
/*  71:    */         case 102: 
/*  72:    */         case 103: 
/*  73:    */         case 104: 
/*  74:    */         case 107: 
/*  75:    */         case 108: 
/*  76:    */         case 109: 
/*  77:    */         case 113: 
/*  78:    */         case 114: 
/*  79:    */         case 115: 
/*  80:    */         case 116: 
/*  81:    */         case 118: 
/*  82:    */         case 119: 
/*  83:    */         default: 
/*  84:105 */           printUsage();
/*  85:    */         }
/*  86:    */       }
/*  87:    */       boolean compileOK;
/*  88:112 */       if (useStdIn)
/*  89:    */       {
/*  90:113 */         if (!classNameSet) {
/*  91:114 */           System.err.println(new ErrorMsg("COMPILE_STDIN_ERR"));
/*  92:    */         }
/*  93:116 */         compileOK = xsltc.compile(System.in, xsltc.getClassName());
/*  94:    */       }
/*  95:    */       else
/*  96:    */       {
/*  97:120 */         String[] stylesheetNames = getopt.getCmdArgs();
/*  98:121 */         Vector stylesheetVector = new Vector();
/*  99:122 */         for (int i = 0; i < stylesheetNames.length; i++)
/* 100:    */         {
/* 101:123 */           String name = stylesheetNames[i];
/* 102:    */           URL url;
/* 103:125 */           if (inputIsURL) {
/* 104:126 */             url = new URL(name);
/* 105:    */           } else {
/* 106:128 */             url = new File(name).toURL();
/* 107:    */           }
/* 108:129 */           stylesheetVector.addElement(url);
/* 109:    */         }
/* 110:131 */         compileOK = xsltc.compile(stylesheetVector);
/* 111:    */       }
/* 112:135 */       if (compileOK)
/* 113:    */       {
/* 114:136 */         xsltc.printWarnings();
/* 115:137 */         if (xsltc.getJarFileName() != null) {
/* 116:137 */           xsltc.outputToJar();
/* 117:    */         }
/* 118:    */       }
/* 119:    */       else
/* 120:    */       {
/* 121:140 */         xsltc.printWarnings();
/* 122:141 */         xsltc.printErrors();
/* 123:    */       }
/* 124:    */     }
/* 125:    */     catch (GetOptsException ex)
/* 126:    */     {
/* 127:145 */       System.err.println(ex);
/* 128:146 */       printUsage();
/* 129:    */     }
/* 130:    */     catch (Exception e)
/* 131:    */     {
/* 132:149 */       e.printStackTrace();
/* 133:    */     }
/* 134:    */   }
/* 135:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.cmdline.Compile
 * JD-Core Version:    0.7.0.1
 */