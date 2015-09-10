/*   1:    */ package org.apache.log4j;
/*   2:    */ 
/*   3:    */ import java.io.FilterOutputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import org.apache.log4j.helpers.LogLog;
/*   8:    */ 
/*   9:    */ public class ConsoleAppender
/*  10:    */   extends WriterAppender
/*  11:    */ {
/*  12:    */   public static final String SYSTEM_OUT = "System.out";
/*  13:    */   public static final String SYSTEM_ERR = "System.err";
/*  14: 37 */   protected String target = "System.out";
/*  15: 43 */   private boolean follow = false;
/*  16:    */   
/*  17:    */   public ConsoleAppender() {}
/*  18:    */   
/*  19:    */   public ConsoleAppender(Layout layout)
/*  20:    */   {
/*  21: 57 */     this(layout, "System.out");
/*  22:    */   }
/*  23:    */   
/*  24:    */   public ConsoleAppender(Layout layout, String target)
/*  25:    */   {
/*  26: 66 */     setLayout(layout);
/*  27: 67 */     setTarget(target);
/*  28: 68 */     activateOptions();
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setTarget(String value)
/*  32:    */   {
/*  33: 78 */     String v = value.trim();
/*  34: 80 */     if ("System.out".equalsIgnoreCase(v)) {
/*  35: 81 */       this.target = "System.out";
/*  36: 82 */     } else if ("System.err".equalsIgnoreCase(v)) {
/*  37: 83 */       this.target = "System.err";
/*  38:    */     } else {
/*  39: 85 */       targetWarn(value);
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String getTarget()
/*  44:    */   {
/*  45: 97 */     return this.target;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public final void setFollow(boolean newValue)
/*  49:    */   {
/*  50:108 */     this.follow = newValue;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public final boolean getFollow()
/*  54:    */   {
/*  55:119 */     return this.follow;
/*  56:    */   }
/*  57:    */   
/*  58:    */   void targetWarn(String val)
/*  59:    */   {
/*  60:123 */     LogLog.warn("[" + val + "] should be System.out or System.err.");
/*  61:124 */     LogLog.warn("Using previously set target, System.out by default.");
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void activateOptions()
/*  65:    */   {
/*  66:131 */     if (this.follow)
/*  67:    */     {
/*  68:132 */       if (this.target.equals("System.err")) {
/*  69:133 */         setWriter(createWriter(new SystemErrStream()));
/*  70:    */       } else {
/*  71:135 */         setWriter(createWriter(new SystemOutStream()));
/*  72:    */       }
/*  73:    */     }
/*  74:138 */     else if (this.target.equals("System.err")) {
/*  75:139 */       setWriter(createWriter(System.err));
/*  76:    */     } else {
/*  77:141 */       setWriter(createWriter(System.out));
/*  78:    */     }
/*  79:145 */     super.activateOptions();
/*  80:    */   }
/*  81:    */   
/*  82:    */   protected final void closeWriter()
/*  83:    */   {
/*  84:154 */     if (this.follow) {
/*  85:155 */       super.closeWriter();
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   private static class SystemErrStream
/*  90:    */     extends OutputStream
/*  91:    */   {
/*  92:    */     public void close() {}
/*  93:    */     
/*  94:    */     public void flush()
/*  95:    */     {
/*  96:173 */       System.err.flush();
/*  97:    */     }
/*  98:    */     
/*  99:    */     public void write(byte[] b)
/* 100:    */       throws IOException
/* 101:    */     {
/* 102:177 */       System.err.write(b);
/* 103:    */     }
/* 104:    */     
/* 105:    */     public void write(byte[] b, int off, int len)
/* 106:    */       throws IOException
/* 107:    */     {
/* 108:182 */       System.err.write(b, off, len);
/* 109:    */     }
/* 110:    */     
/* 111:    */     public void write(int b)
/* 112:    */       throws IOException
/* 113:    */     {
/* 114:186 */       System.err.write(b);
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   private static class SystemOutStream
/* 119:    */     extends OutputStream
/* 120:    */   {
/* 121:    */     public void close() {}
/* 122:    */     
/* 123:    */     public void flush()
/* 124:    */     {
/* 125:203 */       System.out.flush();
/* 126:    */     }
/* 127:    */     
/* 128:    */     public void write(byte[] b)
/* 129:    */       throws IOException
/* 130:    */     {
/* 131:207 */       System.out.write(b);
/* 132:    */     }
/* 133:    */     
/* 134:    */     public void write(byte[] b, int off, int len)
/* 135:    */       throws IOException
/* 136:    */     {
/* 137:212 */       System.out.write(b, off, len);
/* 138:    */     }
/* 139:    */     
/* 140:    */     public void write(int b)
/* 141:    */       throws IOException
/* 142:    */     {
/* 143:216 */       System.out.write(b);
/* 144:    */     }
/* 145:    */   }
/* 146:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.ConsoleAppender
 * JD-Core Version:    0.7.0.1
 */