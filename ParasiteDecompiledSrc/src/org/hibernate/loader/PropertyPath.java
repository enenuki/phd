/*  1:   */ package org.hibernate.loader;
/*  2:   */ 
/*  3:   */ import org.hibernate.internal.util.StringHelper;
/*  4:   */ 
/*  5:   */ public class PropertyPath
/*  6:   */ {
/*  7:   */   private final PropertyPath parent;
/*  8:   */   private final String property;
/*  9:   */   private final String fullPath;
/* 10:   */   
/* 11:   */   public PropertyPath(PropertyPath parent, String property)
/* 12:   */   {
/* 13:37 */     this.parent = parent;
/* 14:38 */     this.property = property;
/* 15:43 */     if ("_identifierMapper".equals(property))
/* 16:   */     {
/* 17:44 */       this.fullPath = (parent != null ? parent.getFullPath() : "");
/* 18:   */     }
/* 19:   */     else
/* 20:   */     {
/* 21:   */       String prefix;
/* 22:   */       String prefix;
/* 23:48 */       if (parent != null)
/* 24:   */       {
/* 25:49 */         String resolvedParent = parent.getFullPath();
/* 26:   */         String prefix;
/* 27:50 */         if (StringHelper.isEmpty(resolvedParent)) {
/* 28:51 */           prefix = "";
/* 29:   */         } else {
/* 30:54 */           prefix = resolvedParent + '.';
/* 31:   */         }
/* 32:   */       }
/* 33:   */       else
/* 34:   */       {
/* 35:58 */         prefix = "";
/* 36:   */       }
/* 37:61 */       this.fullPath = (prefix + property);
/* 38:   */     }
/* 39:   */   }
/* 40:   */   
/* 41:   */   public PropertyPath(String property)
/* 42:   */   {
/* 43:66 */     this(null, property);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public PropertyPath()
/* 47:   */   {
/* 48:70 */     this("");
/* 49:   */   }
/* 50:   */   
/* 51:   */   public PropertyPath append(String property)
/* 52:   */   {
/* 53:74 */     return new PropertyPath(this, property);
/* 54:   */   }
/* 55:   */   
/* 56:   */   public PropertyPath getParent()
/* 57:   */   {
/* 58:78 */     return this.parent;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public String getProperty()
/* 62:   */   {
/* 63:82 */     return this.property;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public String getFullPath()
/* 67:   */   {
/* 68:86 */     return this.fullPath;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public boolean isRoot()
/* 72:   */   {
/* 73:90 */     return (this.parent == null) && (StringHelper.isEmpty(this.property));
/* 74:   */   }
/* 75:   */   
/* 76:   */   public String toString()
/* 77:   */   {
/* 78:95 */     return getClass().getSimpleName() + '[' + this.fullPath + ']';
/* 79:   */   }
/* 80:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.PropertyPath
 * JD-Core Version:    0.7.0.1
 */