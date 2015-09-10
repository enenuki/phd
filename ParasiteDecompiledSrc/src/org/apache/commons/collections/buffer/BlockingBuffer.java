/*   1:    */ package org.apache.commons.collections.buffer;
/*   2:    */ 
/*   3:    */ import java.io.PrintWriter;
/*   4:    */ import java.io.StringWriter;
/*   5:    */ import java.util.Collection;
/*   6:    */ import org.apache.commons.collections.Buffer;
/*   7:    */ import org.apache.commons.collections.BufferUnderflowException;
/*   8:    */ 
/*   9:    */ public class BlockingBuffer
/*  10:    */   extends SynchronizedBuffer
/*  11:    */ {
/*  12:    */   private static final long serialVersionUID = 1719328905017860541L;
/*  13:    */   private final long timeout;
/*  14:    */   
/*  15:    */   public static Buffer decorate(Buffer buffer)
/*  16:    */   {
/*  17: 66 */     return new BlockingBuffer(buffer);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public static Buffer decorate(Buffer buffer, long timeoutMillis)
/*  21:    */   {
/*  22: 79 */     return new BlockingBuffer(buffer, timeoutMillis);
/*  23:    */   }
/*  24:    */   
/*  25:    */   protected BlockingBuffer(Buffer buffer)
/*  26:    */   {
/*  27: 90 */     super(buffer);
/*  28: 91 */     this.timeout = 0L;
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected BlockingBuffer(Buffer buffer, long timeoutMillis)
/*  32:    */   {
/*  33:103 */     super(buffer);
/*  34:104 */     this.timeout = (timeoutMillis < 0L ? 0L : timeoutMillis);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean add(Object o)
/*  38:    */   {
/*  39:109 */     synchronized (this.lock)
/*  40:    */     {
/*  41:110 */       boolean result = this.collection.add(o);
/*  42:111 */       this.lock.notifyAll();
/*  43:112 */       return result;
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean addAll(Collection c)
/*  48:    */   {
/*  49:117 */     synchronized (this.lock)
/*  50:    */     {
/*  51:118 */       boolean result = this.collection.addAll(c);
/*  52:119 */       this.lock.notifyAll();
/*  53:120 */       return result;
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Object get()
/*  58:    */   {
/*  59:132 */     synchronized (this.lock)
/*  60:    */     {
/*  61:133 */       while (this.collection.isEmpty()) {
/*  62:    */         try
/*  63:    */         {
/*  64:135 */           if (this.timeout <= 0L) {
/*  65:136 */             this.lock.wait();
/*  66:    */           } else {
/*  67:138 */             return get(this.timeout);
/*  68:    */           }
/*  69:    */         }
/*  70:    */         catch (InterruptedException e)
/*  71:    */         {
/*  72:141 */           PrintWriter out = new PrintWriter(new StringWriter());
/*  73:142 */           e.printStackTrace(out);
/*  74:143 */           throw new BufferUnderflowException("Caused by InterruptedException: " + out.toString());
/*  75:    */         }
/*  76:    */       }
/*  77:146 */       return getBuffer().get();
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Object get(long timeout)
/*  82:    */   {
/*  83:160 */     synchronized (this.lock)
/*  84:    */     {
/*  85:161 */       long expiration = System.currentTimeMillis() + timeout;
/*  86:162 */       long timeLeft = expiration - System.currentTimeMillis();
/*  87:163 */       while ((timeLeft > 0L) && (this.collection.isEmpty())) {
/*  88:    */         try
/*  89:    */         {
/*  90:165 */           this.lock.wait(timeLeft);
/*  91:166 */           timeLeft = expiration - System.currentTimeMillis();
/*  92:    */         }
/*  93:    */         catch (InterruptedException e)
/*  94:    */         {
/*  95:168 */           PrintWriter out = new PrintWriter(new StringWriter());
/*  96:169 */           e.printStackTrace(out);
/*  97:170 */           throw new BufferUnderflowException("Caused by InterruptedException: " + out.toString());
/*  98:    */         }
/*  99:    */       }
/* 100:173 */       if (this.collection.isEmpty()) {
/* 101:174 */         throw new BufferUnderflowException("Timeout expired");
/* 102:    */       }
/* 103:176 */       return getBuffer().get();
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public Object remove()
/* 108:    */   {
/* 109:188 */     synchronized (this.lock)
/* 110:    */     {
/* 111:189 */       while (this.collection.isEmpty()) {
/* 112:    */         try
/* 113:    */         {
/* 114:191 */           if (this.timeout <= 0L) {
/* 115:192 */             this.lock.wait();
/* 116:    */           } else {
/* 117:194 */             return remove(this.timeout);
/* 118:    */           }
/* 119:    */         }
/* 120:    */         catch (InterruptedException e)
/* 121:    */         {
/* 122:197 */           PrintWriter out = new PrintWriter(new StringWriter());
/* 123:198 */           e.printStackTrace(out);
/* 124:199 */           throw new BufferUnderflowException("Caused by InterruptedException: " + out.toString());
/* 125:    */         }
/* 126:    */       }
/* 127:202 */       return getBuffer().remove();
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   public Object remove(long timeout)
/* 132:    */   {
/* 133:216 */     synchronized (this.lock)
/* 134:    */     {
/* 135:217 */       long expiration = System.currentTimeMillis() + timeout;
/* 136:218 */       long timeLeft = expiration - System.currentTimeMillis();
/* 137:219 */       while ((timeLeft > 0L) && (this.collection.isEmpty())) {
/* 138:    */         try
/* 139:    */         {
/* 140:221 */           this.lock.wait(timeLeft);
/* 141:222 */           timeLeft = expiration - System.currentTimeMillis();
/* 142:    */         }
/* 143:    */         catch (InterruptedException e)
/* 144:    */         {
/* 145:224 */           PrintWriter out = new PrintWriter(new StringWriter());
/* 146:225 */           e.printStackTrace(out);
/* 147:226 */           throw new BufferUnderflowException("Caused by InterruptedException: " + out.toString());
/* 148:    */         }
/* 149:    */       }
/* 150:229 */       if (this.collection.isEmpty()) {
/* 151:230 */         throw new BufferUnderflowException("Timeout expired");
/* 152:    */       }
/* 153:232 */       return getBuffer().remove();
/* 154:    */     }
/* 155:    */   }
/* 156:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.buffer.BlockingBuffer
 * JD-Core Version:    0.7.0.1
 */