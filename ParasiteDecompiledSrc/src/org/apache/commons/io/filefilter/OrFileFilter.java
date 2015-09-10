/*   1:    */ package org.apache.commons.io.filefilter;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Collections;
/*   7:    */ import java.util.List;
/*   8:    */ 
/*   9:    */ public class OrFileFilter
/*  10:    */   extends AbstractFileFilter
/*  11:    */   implements ConditionalFileFilter, Serializable
/*  12:    */ {
/*  13:    */   private final List<IOFileFilter> fileFilters;
/*  14:    */   
/*  15:    */   public OrFileFilter()
/*  16:    */   {
/*  17: 51 */     this.fileFilters = new ArrayList();
/*  18:    */   }
/*  19:    */   
/*  20:    */   public OrFileFilter(List<IOFileFilter> fileFilters)
/*  21:    */   {
/*  22: 62 */     if (fileFilters == null) {
/*  23: 63 */       this.fileFilters = new ArrayList();
/*  24:    */     } else {
/*  25: 65 */       this.fileFilters = new ArrayList(fileFilters);
/*  26:    */     }
/*  27:    */   }
/*  28:    */   
/*  29:    */   public OrFileFilter(IOFileFilter filter1, IOFileFilter filter2)
/*  30:    */   {
/*  31: 77 */     if ((filter1 == null) || (filter2 == null)) {
/*  32: 78 */       throw new IllegalArgumentException("The filters must not be null");
/*  33:    */     }
/*  34: 80 */     this.fileFilters = new ArrayList(2);
/*  35: 81 */     addFileFilter(filter1);
/*  36: 82 */     addFileFilter(filter2);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void addFileFilter(IOFileFilter ioFileFilter)
/*  40:    */   {
/*  41: 89 */     this.fileFilters.add(ioFileFilter);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public List<IOFileFilter> getFileFilters()
/*  45:    */   {
/*  46: 96 */     return Collections.unmodifiableList(this.fileFilters);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean removeFileFilter(IOFileFilter ioFileFilter)
/*  50:    */   {
/*  51:103 */     return this.fileFilters.remove(ioFileFilter);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setFileFilters(List<IOFileFilter> fileFilters)
/*  55:    */   {
/*  56:110 */     this.fileFilters.clear();
/*  57:111 */     this.fileFilters.addAll(fileFilters);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean accept(File file)
/*  61:    */   {
/*  62:119 */     for (IOFileFilter fileFilter : this.fileFilters) {
/*  63:120 */       if (fileFilter.accept(file)) {
/*  64:121 */         return true;
/*  65:    */       }
/*  66:    */     }
/*  67:124 */     return false;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean accept(File file, String name)
/*  71:    */   {
/*  72:132 */     for (IOFileFilter fileFilter : this.fileFilters) {
/*  73:133 */       if (fileFilter.accept(file, name)) {
/*  74:134 */         return true;
/*  75:    */       }
/*  76:    */     }
/*  77:137 */     return false;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String toString()
/*  81:    */   {
/*  82:147 */     StringBuilder buffer = new StringBuilder();
/*  83:148 */     buffer.append(super.toString());
/*  84:149 */     buffer.append("(");
/*  85:150 */     if (this.fileFilters != null) {
/*  86:151 */       for (int i = 0; i < this.fileFilters.size(); i++)
/*  87:    */       {
/*  88:152 */         if (i > 0) {
/*  89:153 */           buffer.append(",");
/*  90:    */         }
/*  91:155 */         Object filter = this.fileFilters.get(i);
/*  92:156 */         buffer.append(filter == null ? "null" : filter.toString());
/*  93:    */       }
/*  94:    */     }
/*  95:159 */     buffer.append(")");
/*  96:160 */     return buffer.toString();
/*  97:    */   }
/*  98:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.filefilter.OrFileFilter
 * JD-Core Version:    0.7.0.1
 */