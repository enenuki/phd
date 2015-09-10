/*   1:    */ package org.apache.commons.lang.exception;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.io.PrintWriter;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.io.StringWriter;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Arrays;
/*   9:    */ import java.util.Collections;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.List;
/*  12:    */ 
/*  13:    */ public class NestableDelegate
/*  14:    */   implements Serializable
/*  15:    */ {
/*  16:    */   private static final long serialVersionUID = 1L;
/*  17:    */   private static final transient String MUST_BE_THROWABLE = "The Nestable implementation passed to the NestableDelegate(Nestable) constructor must extend java.lang.Throwable";
/*  18: 68 */   private Throwable nestable = null;
/*  19: 78 */   public static boolean topDown = true;
/*  20: 88 */   public static boolean trimStackFrames = true;
/*  21: 98 */   public static boolean matchSubclasses = true;
/*  22:    */   
/*  23:    */   public NestableDelegate(Nestable nestable)
/*  24:    */   {
/*  25:109 */     if ((nestable instanceof Throwable)) {
/*  26:110 */       this.nestable = ((Throwable)nestable);
/*  27:    */     } else {
/*  28:112 */       throw new IllegalArgumentException("The Nestable implementation passed to the NestableDelegate(Nestable) constructor must extend java.lang.Throwable");
/*  29:    */     }
/*  30:    */   }
/*  31:    */   
/*  32:    */   public String getMessage(int index)
/*  33:    */   {
/*  34:130 */     Throwable t = getThrowable(index);
/*  35:131 */     if (Nestable.class.isInstance(t)) {
/*  36:132 */       return ((Nestable)t).getMessage(0);
/*  37:    */     }
/*  38:134 */     return t.getMessage();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String getMessage(String baseMsg)
/*  42:    */   {
/*  43:148 */     Throwable nestedCause = ExceptionUtils.getCause(this.nestable);
/*  44:149 */     String causeMsg = nestedCause == null ? null : nestedCause.getMessage();
/*  45:150 */     if ((nestedCause == null) || (causeMsg == null)) {
/*  46:151 */       return baseMsg;
/*  47:    */     }
/*  48:153 */     if (baseMsg == null) {
/*  49:154 */       return causeMsg;
/*  50:    */     }
/*  51:156 */     return baseMsg + ": " + causeMsg;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String[] getMessages()
/*  55:    */   {
/*  56:169 */     Throwable[] throwables = getThrowables();
/*  57:170 */     String[] msgs = new String[throwables.length];
/*  58:171 */     for (int i = 0; i < throwables.length; i++) {
/*  59:172 */       msgs[i] = (Nestable.class.isInstance(throwables[i]) ? ((Nestable)throwables[i]).getMessage(0) : throwables[i].getMessage());
/*  60:    */     }
/*  61:177 */     return msgs;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Throwable getThrowable(int index)
/*  65:    */   {
/*  66:193 */     if (index == 0) {
/*  67:194 */       return this.nestable;
/*  68:    */     }
/*  69:196 */     Throwable[] throwables = getThrowables();
/*  70:197 */     return throwables[index];
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getThrowableCount()
/*  74:    */   {
/*  75:208 */     return ExceptionUtils.getThrowableCount(this.nestable);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Throwable[] getThrowables()
/*  79:    */   {
/*  80:220 */     return ExceptionUtils.getThrowables(this.nestable);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int indexOfThrowable(Class type, int fromIndex)
/*  84:    */   {
/*  85:248 */     if (type == null) {
/*  86:249 */       return -1;
/*  87:    */     }
/*  88:251 */     if (fromIndex < 0) {
/*  89:252 */       throw new IndexOutOfBoundsException("The start index was out of bounds: " + fromIndex);
/*  90:    */     }
/*  91:254 */     Throwable[] throwables = ExceptionUtils.getThrowables(this.nestable);
/*  92:255 */     if (fromIndex >= throwables.length) {
/*  93:256 */       throw new IndexOutOfBoundsException("The start index was out of bounds: " + fromIndex + " >= " + throwables.length);
/*  94:    */     }
/*  95:259 */     if (matchSubclasses) {
/*  96:260 */       for (int i = fromIndex; i < throwables.length; i++) {
/*  97:261 */         if (type.isAssignableFrom(throwables[i].getClass())) {
/*  98:262 */           return i;
/*  99:    */         }
/* 100:    */       }
/* 101:    */     } else {
/* 102:266 */       for (int i = fromIndex; i < throwables.length; i++) {
/* 103:267 */         if (type.equals(throwables[i].getClass())) {
/* 104:268 */           return i;
/* 105:    */         }
/* 106:    */       }
/* 107:    */     }
/* 108:272 */     return -1;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void printStackTrace()
/* 112:    */   {
/* 113:280 */     printStackTrace(System.err);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void printStackTrace(PrintStream out)
/* 117:    */   {
/* 118:291 */     synchronized (out)
/* 119:    */     {
/* 120:292 */       PrintWriter pw = new PrintWriter(out, false);
/* 121:293 */       printStackTrace(pw);
/* 122:    */       
/* 123:295 */       pw.flush();
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void printStackTrace(PrintWriter out)
/* 128:    */   {
/* 129:311 */     Throwable throwable = this.nestable;
/* 130:313 */     if (ExceptionUtils.isThrowableNested())
/* 131:    */     {
/* 132:314 */       if ((throwable instanceof Nestable)) {
/* 133:315 */         ((Nestable)throwable).printPartialStackTrace(out);
/* 134:    */       } else {
/* 135:317 */         throwable.printStackTrace(out);
/* 136:    */       }
/* 137:319 */       return;
/* 138:    */     }
/* 139:323 */     List stacks = new ArrayList();
/* 140:324 */     while (throwable != null)
/* 141:    */     {
/* 142:325 */       String[] st = getStackFrames(throwable);
/* 143:326 */       stacks.add(st);
/* 144:327 */       throwable = ExceptionUtils.getCause(throwable);
/* 145:    */     }
/* 146:331 */     String separatorLine = "Caused by: ";
/* 147:332 */     if (!topDown)
/* 148:    */     {
/* 149:333 */       separatorLine = "Rethrown as: ";
/* 150:334 */       Collections.reverse(stacks);
/* 151:    */     }
/* 152:338 */     if (trimStackFrames) {
/* 153:339 */       trimStackFrames(stacks);
/* 154:    */     }
/* 155:    */     Iterator iter;
/* 156:342 */     synchronized (out)
/* 157:    */     {
/* 158:343 */       for (iter = stacks.iterator(); iter.hasNext();)
/* 159:    */       {
/* 160:344 */         String[] st = (String[])iter.next();
/* 161:345 */         int i = 0;
/* 162:345 */         for (int len = st.length; i < len; i++) {
/* 163:346 */           out.println(st[i]);
/* 164:    */         }
/* 165:348 */         if (iter.hasNext()) {
/* 166:349 */           out.print(separatorLine);
/* 167:    */         }
/* 168:    */       }
/* 169:    */     }
/* 170:    */   }
/* 171:    */   
/* 172:    */   protected String[] getStackFrames(Throwable t)
/* 173:    */   {
/* 174:365 */     StringWriter sw = new StringWriter();
/* 175:366 */     PrintWriter pw = new PrintWriter(sw, true);
/* 176:369 */     if ((t instanceof Nestable)) {
/* 177:370 */       ((Nestable)t).printPartialStackTrace(pw);
/* 178:    */     } else {
/* 179:372 */       t.printStackTrace(pw);
/* 180:    */     }
/* 181:374 */     return ExceptionUtils.getStackFrames(sw.getBuffer().toString());
/* 182:    */   }
/* 183:    */   
/* 184:    */   protected void trimStackFrames(List stacks)
/* 185:    */   {
/* 186:386 */     int size = stacks.size();
/* 187:386 */     for (int i = size - 1; i > 0; i--)
/* 188:    */     {
/* 189:387 */       String[] curr = (String[])stacks.get(i);
/* 190:388 */       String[] next = (String[])stacks.get(i - 1);
/* 191:    */       
/* 192:390 */       List currList = new ArrayList(Arrays.asList(curr));
/* 193:391 */       List nextList = new ArrayList(Arrays.asList(next));
/* 194:392 */       ExceptionUtils.removeCommonFrames(currList, nextList);
/* 195:    */       
/* 196:394 */       int trimmed = curr.length - currList.size();
/* 197:395 */       if (trimmed > 0)
/* 198:    */       {
/* 199:396 */         currList.add("\t... " + trimmed + " more");
/* 200:397 */         stacks.set(i, currList.toArray(new String[currList.size()]));
/* 201:    */       }
/* 202:    */     }
/* 203:    */   }
/* 204:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.exception.NestableDelegate
 * JD-Core Version:    0.7.0.1
 */