/*   1:    */ package org.apache.commons.collections;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.EmptyStackException;
/*   5:    */ 
/*   6:    */ public class ArrayStack
/*   7:    */   extends ArrayList
/*   8:    */   implements Buffer
/*   9:    */ {
/*  10:    */   private static final long serialVersionUID = 2130079159931574599L;
/*  11:    */   
/*  12:    */   public ArrayStack() {}
/*  13:    */   
/*  14:    */   public ArrayStack(int initialSize)
/*  15:    */   {
/*  16: 66 */     super(initialSize);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public boolean empty()
/*  20:    */   {
/*  21: 78 */     return isEmpty();
/*  22:    */   }
/*  23:    */   
/*  24:    */   public Object peek()
/*  25:    */     throws EmptyStackException
/*  26:    */   {
/*  27: 88 */     int n = size();
/*  28: 89 */     if (n <= 0) {
/*  29: 90 */       throw new EmptyStackException();
/*  30:    */     }
/*  31: 92 */     return get(n - 1);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Object peek(int n)
/*  35:    */     throws EmptyStackException
/*  36:    */   {
/*  37:106 */     int m = size() - n - 1;
/*  38:107 */     if (m < 0) {
/*  39:108 */       throw new EmptyStackException();
/*  40:    */     }
/*  41:110 */     return get(m);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Object pop()
/*  45:    */     throws EmptyStackException
/*  46:    */   {
/*  47:121 */     int n = size();
/*  48:122 */     if (n <= 0) {
/*  49:123 */       throw new EmptyStackException();
/*  50:    */     }
/*  51:125 */     return remove(n - 1);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Object push(Object item)
/*  55:    */   {
/*  56:137 */     add(item);
/*  57:138 */     return item;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int search(Object object)
/*  61:    */   {
/*  62:153 */     int i = size() - 1;
/*  63:154 */     int n = 1;
/*  64:155 */     while (i >= 0)
/*  65:    */     {
/*  66:156 */       Object current = get(i);
/*  67:157 */       if (((object == null) && (current == null)) || ((object != null) && (object.equals(current)))) {
/*  68:159 */         return n;
/*  69:    */       }
/*  70:161 */       i--;
/*  71:162 */       n++;
/*  72:    */     }
/*  73:164 */     return -1;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Object get()
/*  77:    */   {
/*  78:174 */     int size = size();
/*  79:175 */     if (size == 0) {
/*  80:176 */       throw new BufferUnderflowException();
/*  81:    */     }
/*  82:178 */     return get(size - 1);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Object remove()
/*  86:    */   {
/*  87:188 */     int size = size();
/*  88:189 */     if (size == 0) {
/*  89:190 */       throw new BufferUnderflowException();
/*  90:    */     }
/*  91:192 */     return remove(size - 1);
/*  92:    */   }
/*  93:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.ArrayStack
 * JD-Core Version:    0.7.0.1
 */