/*   1:    */ package org.apache.commons.io;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.Reader;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.NoSuchElementException;
/*   7:    */ 
/*   8:    */ public class LineIterator
/*   9:    */   implements Iterator<String>
/*  10:    */ {
/*  11:    */   private final BufferedReader bufferedReader;
/*  12:    */   private String cachedLine;
/*  13: 60 */   private boolean finished = false;
/*  14:    */   
/*  15:    */   public LineIterator(Reader reader)
/*  16:    */     throws IllegalArgumentException
/*  17:    */   {
/*  18: 69 */     if (reader == null) {
/*  19: 70 */       throw new IllegalArgumentException("Reader must not be null");
/*  20:    */     }
/*  21: 72 */     if ((reader instanceof BufferedReader)) {
/*  22: 73 */       this.bufferedReader = ((BufferedReader)reader);
/*  23:    */     } else {
/*  24: 75 */       this.bufferedReader = new BufferedReader(reader);
/*  25:    */     }
/*  26:    */   }
/*  27:    */   
/*  28:    */   /* Error */
/*  29:    */   public boolean hasNext()
/*  30:    */   {
/*  31:    */     // Byte code:
/*  32:    */     //   0: aload_0
/*  33:    */     //   1: getfield 9	org/apache/commons/io/LineIterator:cachedLine	Ljava/lang/String;
/*  34:    */     //   4: ifnull +5 -> 9
/*  35:    */     //   7: iconst_1
/*  36:    */     //   8: ireturn
/*  37:    */     //   9: aload_0
/*  38:    */     //   10: getfield 2	org/apache/commons/io/LineIterator:finished	Z
/*  39:    */     //   13: ifeq +5 -> 18
/*  40:    */     //   16: iconst_0
/*  41:    */     //   17: ireturn
/*  42:    */     //   18: aload_0
/*  43:    */     //   19: getfield 7	org/apache/commons/io/LineIterator:bufferedReader	Ljava/io/BufferedReader;
/*  44:    */     //   22: invokevirtual 10	java/io/BufferedReader:readLine	()Ljava/lang/String;
/*  45:    */     //   25: astore_1
/*  46:    */     //   26: aload_1
/*  47:    */     //   27: ifnonnull +10 -> 37
/*  48:    */     //   30: aload_0
/*  49:    */     //   31: iconst_1
/*  50:    */     //   32: putfield 2	org/apache/commons/io/LineIterator:finished	Z
/*  51:    */     //   35: iconst_0
/*  52:    */     //   36: ireturn
/*  53:    */     //   37: aload_0
/*  54:    */     //   38: aload_1
/*  55:    */     //   39: invokevirtual 11	org/apache/commons/io/LineIterator:isValidLine	(Ljava/lang/String;)Z
/*  56:    */     //   42: ifeq +10 -> 52
/*  57:    */     //   45: aload_0
/*  58:    */     //   46: aload_1
/*  59:    */     //   47: putfield 9	org/apache/commons/io/LineIterator:cachedLine	Ljava/lang/String;
/*  60:    */     //   50: iconst_1
/*  61:    */     //   51: ireturn
/*  62:    */     //   52: goto -34 -> 18
/*  63:    */     //   55: astore_1
/*  64:    */     //   56: aload_0
/*  65:    */     //   57: invokevirtual 13	org/apache/commons/io/LineIterator:close	()V
/*  66:    */     //   60: new 14	java/lang/IllegalStateException
/*  67:    */     //   63: dup
/*  68:    */     //   64: aload_1
/*  69:    */     //   65: invokespecial 15	java/lang/IllegalStateException:<init>	(Ljava/lang/Throwable;)V
/*  70:    */     //   68: athrow
/*  71:    */     // Line number table:
/*  72:    */     //   Java source line #89	-> byte code offset #0
/*  73:    */     //   Java source line #90	-> byte code offset #7
/*  74:    */     //   Java source line #91	-> byte code offset #9
/*  75:    */     //   Java source line #92	-> byte code offset #16
/*  76:    */     //   Java source line #96	-> byte code offset #18
/*  77:    */     //   Java source line #97	-> byte code offset #26
/*  78:    */     //   Java source line #98	-> byte code offset #30
/*  79:    */     //   Java source line #99	-> byte code offset #35
/*  80:    */     //   Java source line #100	-> byte code offset #37
/*  81:    */     //   Java source line #101	-> byte code offset #45
/*  82:    */     //   Java source line #102	-> byte code offset #50
/*  83:    */     //   Java source line #104	-> byte code offset #52
/*  84:    */     //   Java source line #105	-> byte code offset #55
/*  85:    */     //   Java source line #106	-> byte code offset #56
/*  86:    */     //   Java source line #107	-> byte code offset #60
/*  87:    */     // Local variable table:
/*  88:    */     //   start	length	slot	name	signature
/*  89:    */     //   0	69	0	this	LineIterator
/*  90:    */     //   25	22	1	line	String
/*  91:    */     //   55	10	1	ioe	java.io.IOException
/*  92:    */     // Exception table:
/*  93:    */     //   from	to	target	type
/*  94:    */     //   18	36	55	java/io/IOException
/*  95:    */     //   37	51	55	java/io/IOException
/*  96:    */     //   52	55	55	java/io/IOException
/*  97:    */   }
/*  98:    */   
/*  99:    */   protected boolean isValidLine(String line)
/* 100:    */   {
/* 101:119 */     return true;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String next()
/* 105:    */   {
/* 106:129 */     return nextLine();
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String nextLine()
/* 110:    */   {
/* 111:139 */     if (!hasNext()) {
/* 112:140 */       throw new NoSuchElementException("No more lines");
/* 113:    */     }
/* 114:142 */     String currentLine = this.cachedLine;
/* 115:143 */     this.cachedLine = null;
/* 116:144 */     return currentLine;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void close()
/* 120:    */   {
/* 121:155 */     this.finished = true;
/* 122:156 */     IOUtils.closeQuietly(this.bufferedReader);
/* 123:157 */     this.cachedLine = null;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void remove()
/* 127:    */   {
/* 128:166 */     throw new UnsupportedOperationException("Remove unsupported on LineIterator");
/* 129:    */   }
/* 130:    */   
/* 131:    */   public static void closeQuietly(LineIterator iterator)
/* 132:    */   {
/* 133:176 */     if (iterator != null) {
/* 134:177 */       iterator.close();
/* 135:    */     }
/* 136:    */   }
/* 137:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.LineIterator
 * JD-Core Version:    0.7.0.1
 */