/*   1:    */ package org.apache.commons.io.comparator;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Comparator;
/*   7:    */ import java.util.List;
/*   8:    */ 
/*   9:    */ public class CompositeFileComparator
/*  10:    */   extends AbstractFileComparator
/*  11:    */   implements Serializable
/*  12:    */ {
/*  13: 47 */   private static final Comparator<?>[] NO_COMPARATORS = new Comparator[0];
/*  14:    */   private final Comparator<File>[] delegates;
/*  15:    */   
/*  16:    */   public CompositeFileComparator(Comparator<File>... delegates)
/*  17:    */   {
/*  18: 57 */     if (delegates == null)
/*  19:    */     {
/*  20: 58 */       this.delegates = ((Comparator[])NO_COMPARATORS);
/*  21:    */     }
/*  22:    */     else
/*  23:    */     {
/*  24: 60 */       this.delegates = ((Comparator[])new Comparator[delegates.length]);
/*  25: 61 */       System.arraycopy(delegates, 0, this.delegates, 0, delegates.length);
/*  26:    */     }
/*  27:    */   }
/*  28:    */   
/*  29:    */   public CompositeFileComparator(Iterable<Comparator<File>> delegates)
/*  30:    */   {
/*  31: 72 */     if (delegates == null)
/*  32:    */     {
/*  33: 73 */       this.delegates = ((Comparator[])NO_COMPARATORS);
/*  34:    */     }
/*  35:    */     else
/*  36:    */     {
/*  37: 75 */       List<Comparator<File>> list = new ArrayList();
/*  38: 76 */       for (Comparator<File> comparator : delegates) {
/*  39: 77 */         list.add(comparator);
/*  40:    */       }
/*  41: 79 */       this.delegates = ((Comparator[])list.toArray(new Comparator[list.size()]));
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int compare(File file1, File file2)
/*  46:    */   {
/*  47: 92 */     int result = 0;
/*  48: 93 */     for (Comparator<File> delegate : this.delegates)
/*  49:    */     {
/*  50: 94 */       result = delegate.compare(file1, file2);
/*  51: 95 */       if (result != 0) {
/*  52:    */         break;
/*  53:    */       }
/*  54:    */     }
/*  55: 99 */     return result;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String toString()
/*  59:    */   {
/*  60:109 */     StringBuilder builder = new StringBuilder();
/*  61:110 */     builder.append(super.toString());
/*  62:111 */     builder.append('{');
/*  63:112 */     for (int i = 0; i < this.delegates.length; i++)
/*  64:    */     {
/*  65:113 */       if (i > 0) {
/*  66:114 */         builder.append(',');
/*  67:    */       }
/*  68:116 */       builder.append(this.delegates[i]);
/*  69:    */     }
/*  70:118 */     builder.append('}');
/*  71:119 */     return builder.toString();
/*  72:    */   }
/*  73:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.comparator.CompositeFileComparator
 * JD-Core Version:    0.7.0.1
 */