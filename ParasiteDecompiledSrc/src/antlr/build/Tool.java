/*   1:    */ package antlr.build;
/*   2:    */ 
/*   3:    */ import antlr.Utils;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import java.lang.reflect.Method;
/*   8:    */ 
/*   9:    */ public class Tool
/*  10:    */ {
/*  11: 50 */   public String os = null;
/*  12:    */   
/*  13:    */   public Tool()
/*  14:    */   {
/*  15: 53 */     this.os = System.getProperty("os.name");
/*  16:    */   }
/*  17:    */   
/*  18:    */   public static void main(String[] paramArrayOfString)
/*  19:    */   {
/*  20: 57 */     if (paramArrayOfString.length != 1)
/*  21:    */     {
/*  22: 58 */       System.err.println("usage: java antlr.build.Tool action");
/*  23: 59 */       return;
/*  24:    */     }
/*  25: 61 */     String str1 = "antlr.build.ANTLR";
/*  26: 62 */     String str2 = paramArrayOfString[0];
/*  27: 63 */     Tool localTool = new Tool();
/*  28: 64 */     localTool.perform(str1, str2);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void perform(String paramString1, String paramString2)
/*  32:    */   {
/*  33: 71 */     if ((paramString1 == null) || (paramString2 == null))
/*  34:    */     {
/*  35: 72 */       error("missing app or action");
/*  36: 73 */       return;
/*  37:    */     }
/*  38: 75 */     Class localClass = null;
/*  39: 76 */     Method localMethod = null;
/*  40: 77 */     Object localObject = null;
/*  41:    */     try
/*  42:    */     {
/*  43: 79 */       localObject = Utils.createInstanceOf(paramString1);
/*  44:    */     }
/*  45:    */     catch (Exception localException1)
/*  46:    */     {
/*  47:    */       try
/*  48:    */       {
/*  49: 84 */         if (!paramString1.startsWith("antlr.build.")) {
/*  50: 85 */           localClass = Utils.loadClass("antlr.build." + paramString1);
/*  51:    */         }
/*  52: 87 */         error("no such application " + paramString1, localException1);
/*  53:    */       }
/*  54:    */       catch (Exception localException3)
/*  55:    */       {
/*  56: 90 */         error("no such application " + paramString1, localException3);
/*  57:    */       }
/*  58:    */     }
/*  59: 93 */     if ((localClass == null) || (localObject == null)) {
/*  60: 94 */       return;
/*  61:    */     }
/*  62:    */     try
/*  63:    */     {
/*  64: 97 */       localMethod = localClass.getMethod(paramString2, new Class[] { Tool.class });
/*  65:    */       
/*  66: 99 */       localMethod.invoke(localObject, new Object[] { this });
/*  67:    */     }
/*  68:    */     catch (Exception localException2)
/*  69:    */     {
/*  70:103 */       error("no such action for application " + paramString1, localException2);
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void system(String paramString)
/*  75:    */   {
/*  76:111 */     Runtime localRuntime = Runtime.getRuntime();
/*  77:    */     try
/*  78:    */     {
/*  79:113 */       log(paramString);
/*  80:114 */       Process localProcess = null;
/*  81:115 */       if (!this.os.startsWith("Windows")) {
/*  82:117 */         localProcess = localRuntime.exec(new String[] { "sh", "-c", paramString });
/*  83:    */       } else {
/*  84:120 */         localProcess = localRuntime.exec(paramString);
/*  85:    */       }
/*  86:122 */       StreamScarfer localStreamScarfer1 = new StreamScarfer(localProcess.getErrorStream(), "stderr", this);
/*  87:    */       
/*  88:124 */       StreamScarfer localStreamScarfer2 = new StreamScarfer(localProcess.getInputStream(), "stdout", this);
/*  89:    */       
/*  90:126 */       localStreamScarfer1.start();
/*  91:127 */       localStreamScarfer2.start();
/*  92:128 */       int i = localProcess.waitFor();
/*  93:    */     }
/*  94:    */     catch (Exception localException)
/*  95:    */     {
/*  96:131 */       error("cannot exec " + paramString, localException);
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void antlr(String paramString)
/* 101:    */   {
/* 102:139 */     String str = null;
/* 103:    */     try
/* 104:    */     {
/* 105:141 */       str = new File(paramString).getParent();
/* 106:142 */       if (str != null) {
/* 107:143 */         str = new File(str).getCanonicalPath();
/* 108:    */       }
/* 109:    */     }
/* 110:    */     catch (IOException localIOException)
/* 111:    */     {
/* 112:147 */       error("Invalid grammar file: " + paramString);
/* 113:    */     }
/* 114:149 */     if (str != null)
/* 115:    */     {
/* 116:150 */       log("java antlr.Tool -o " + str + " " + paramString);
/* 117:151 */       antlr.Tool localTool = new antlr.Tool();
/* 118:152 */       localTool.doEverything(new String[] { "-o", str, paramString });
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void stdout(String paramString)
/* 123:    */   {
/* 124:158 */     System.out.println(paramString);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void stderr(String paramString)
/* 128:    */   {
/* 129:163 */     System.err.println(paramString);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void error(String paramString)
/* 133:    */   {
/* 134:167 */     System.err.println("antlr.build.Tool: " + paramString);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void log(String paramString)
/* 138:    */   {
/* 139:171 */     System.out.println("executing: " + paramString);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void error(String paramString, Exception paramException)
/* 143:    */   {
/* 144:175 */     System.err.println("antlr.build.Tool: " + paramString);
/* 145:176 */     paramException.printStackTrace(System.err);
/* 146:    */   }
/* 147:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.build.Tool
 * JD-Core Version:    0.7.0.1
 */