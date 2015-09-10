/*   1:    */ package org.apache.commons.collections.buffer;
/*   2:    */ 
/*   3:    */ import java.io.PrintWriter;
/*   4:    */ import java.io.StringWriter;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import org.apache.commons.collections.BoundedCollection;
/*   8:    */ import org.apache.commons.collections.Buffer;
/*   9:    */ import org.apache.commons.collections.BufferOverflowException;
/*  10:    */ import org.apache.commons.collections.BufferUnderflowException;
/*  11:    */ import org.apache.commons.collections.iterators.AbstractIteratorDecorator;
/*  12:    */ 
/*  13:    */ public class BoundedBuffer
/*  14:    */   extends SynchronizedBuffer
/*  15:    */   implements BoundedCollection
/*  16:    */ {
/*  17:    */   private static final long serialVersionUID = 1536432911093974264L;
/*  18:    */   private final int maximumSize;
/*  19:    */   private final long timeout;
/*  20:    */   
/*  21:    */   public static BoundedBuffer decorate(Buffer buffer, int maximumSize)
/*  22:    */   {
/*  23: 71 */     return new BoundedBuffer(buffer, maximumSize, 0L);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static BoundedBuffer decorate(Buffer buffer, int maximumSize, long timeout)
/*  27:    */   {
/*  28: 86 */     return new BoundedBuffer(buffer, maximumSize, timeout);
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected BoundedBuffer(Buffer buffer, int maximumSize, long timeout)
/*  32:    */   {
/*  33:101 */     super(buffer);
/*  34:102 */     if (maximumSize < 1) {
/*  35:103 */       throw new IllegalArgumentException();
/*  36:    */     }
/*  37:105 */     this.maximumSize = maximumSize;
/*  38:106 */     this.timeout = timeout;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Object remove()
/*  42:    */   {
/*  43:111 */     synchronized (this.lock)
/*  44:    */     {
/*  45:112 */       Object returnValue = getBuffer().remove();
/*  46:113 */       this.lock.notifyAll();
/*  47:114 */       return returnValue;
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean add(Object o)
/*  52:    */   {
/*  53:119 */     synchronized (this.lock)
/*  54:    */     {
/*  55:120 */       timeoutWait(1);
/*  56:121 */       return getBuffer().add(o);
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean addAll(Collection c)
/*  61:    */   {
/*  62:126 */     synchronized (this.lock)
/*  63:    */     {
/*  64:127 */       timeoutWait(c.size());
/*  65:128 */       return getBuffer().addAll(c);
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Iterator iterator()
/*  70:    */   {
/*  71:133 */     return new NotifyingIterator(this.collection.iterator());
/*  72:    */   }
/*  73:    */   
/*  74:    */   private void timeoutWait(int nAdditions)
/*  75:    */   {
/*  76:138 */     if (nAdditions > this.maximumSize) {
/*  77:139 */       throw new BufferOverflowException("Buffer size cannot exceed " + this.maximumSize);
/*  78:    */     }
/*  79:142 */     if (this.timeout <= 0L)
/*  80:    */     {
/*  81:144 */       if (getBuffer().size() + nAdditions > this.maximumSize) {
/*  82:145 */         throw new BufferOverflowException("Buffer size cannot exceed " + this.maximumSize);
/*  83:    */       }
/*  84:148 */       return;
/*  85:    */     }
/*  86:150 */     long expiration = System.currentTimeMillis() + this.timeout;
/*  87:151 */     long timeLeft = expiration - System.currentTimeMillis();
/*  88:152 */     while ((timeLeft > 0L) && (getBuffer().size() + nAdditions > this.maximumSize)) {
/*  89:    */       try
/*  90:    */       {
/*  91:154 */         this.lock.wait(timeLeft);
/*  92:155 */         timeLeft = expiration - System.currentTimeMillis();
/*  93:    */       }
/*  94:    */       catch (InterruptedException ex)
/*  95:    */       {
/*  96:157 */         PrintWriter out = new PrintWriter(new StringWriter());
/*  97:158 */         ex.printStackTrace(out);
/*  98:159 */         throw new BufferUnderflowException("Caused by InterruptedException: " + out.toString());
/*  99:    */       }
/* 100:    */     }
/* 101:163 */     if (getBuffer().size() + nAdditions > this.maximumSize) {
/* 102:164 */       throw new BufferOverflowException("Timeout expired");
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean isFull()
/* 107:    */   {
/* 108:170 */     return size() == maxSize();
/* 109:    */   }
/* 110:    */   
/* 111:    */   public int maxSize()
/* 112:    */   {
/* 113:174 */     return this.maximumSize;
/* 114:    */   }
/* 115:    */   
/* 116:    */   private class NotifyingIterator
/* 117:    */     extends AbstractIteratorDecorator
/* 118:    */   {
/* 119:    */     public NotifyingIterator(Iterator it)
/* 120:    */     {
/* 121:184 */       super();
/* 122:    */     }
/* 123:    */     
/* 124:    */     public void remove()
/* 125:    */     {
/* 126:188 */       synchronized (BoundedBuffer.this.lock)
/* 127:    */       {
/* 128:189 */         this.iterator.remove();
/* 129:190 */         BoundedBuffer.this.lock.notifyAll();
/* 130:    */       }
/* 131:    */     }
/* 132:    */   }
/* 133:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.buffer.BoundedBuffer
 * JD-Core Version:    0.7.0.1
 */