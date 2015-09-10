/*   1:    */ package org.apache.commons.io.filefilter;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Collections;
/*   7:    */ import java.util.List;
/*   8:    */ 
/*   9:    */ public class AndFileFilter
/*  10:    */   extends AbstractFileFilter
/*  11:    */   implements ConditionalFileFilter, Serializable
/*  12:    */ {
/*  13:    */   private final List<IOFileFilter> fileFilters;
/*  14:    */   
/*  15:    */   public AndFileFilter()
/*  16:    */   {
/*  17: 51 */     this.fileFilters = new ArrayList();
/*  18:    */   }
/*  19:    */   
/*  20:    */   public AndFileFilter(List<IOFileFilter> fileFilters)
/*  21:    */   {
/*  22: 62 */     if (fileFilters == null) {
/*  23: 63 */       this.fileFilters = new ArrayList();
/*  24:    */     } else {
/*  25: 65 */       this.fileFilters = new ArrayList(fileFilters);
/*  26:    */     }
/*  27:    */   }
/*  28:    */   
/*  29:    */   public AndFileFilter(IOFileFilter filter1, IOFileFilter filter2)
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
/*  62:119 */     if (this.fileFilters.size() == 0) {
/*  63:120 */       return false;
/*  64:    */     }
/*  65:122 */     for (IOFileFilter fileFilter : this.fileFilters) {
/*  66:123 */       if (!fileFilter.accept(file)) {
/*  67:124 */         return false;
/*  68:    */       }
/*  69:    */     }
/*  70:127 */     return true;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public boolean accept(File file, String name)
/*  74:    */   {
/*  75:135 */     if (this.fileFilters.size() == 0) {
/*  76:136 */       return false;
/*  77:    */     }
/*  78:138 */     for (IOFileFilter fileFilter : this.fileFilters) {
/*  79:139 */       if (!fileFilter.accept(file, name)) {
/*  80:140 */         return false;
/*  81:    */       }
/*  82:    */     }
/*  83:143 */     return true;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String toString()
/*  87:    */   {
/*  88:153 */     StringBuilder buffer = new StringBuilder();
/*  89:154 */     buffer.append(super.toString());
/*  90:155 */     buffer.append("(");
/*  91:156 */     if (this.fileFilters != null) {
/*  92:157 */       for (int i = 0; i < this.fileFilters.size(); i++)
/*  93:    */       {
/*  94:158 */         if (i > 0) {
/*  95:159 */           buffer.append(",");
/*  96:    */         }
/*  97:161 */         Object filter = this.fileFilters.get(i);
/*  98:162 */         buffer.append(filter == null ? "null" : filter.toString());
/*  99:    */       }
/* 100:    */     }
/* 101:165 */     buffer.append(")");
/* 102:166 */     return buffer.toString();
/* 103:    */   }
/* 104:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.filefilter.AndFileFilter
 * JD-Core Version:    0.7.0.1
 */