/*  1:   */ package org.hibernate.secure.internal;
/*  2:   */ 
/*  3:   */ import java.security.Permission;
/*  4:   */ 
/*  5:   */ public class HibernatePermission
/*  6:   */   extends Permission
/*  7:   */ {
/*  8:   */   public static final String INSERT = "insert";
/*  9:   */   public static final String UPDATE = "update";
/* 10:   */   public static final String DELETE = "delete";
/* 11:   */   public static final String READ = "read";
/* 12:   */   public static final String ANY = "*";
/* 13:   */   private final String actions;
/* 14:   */   
/* 15:   */   public HibernatePermission(String entityName, String actions)
/* 16:   */   {
/* 17:41 */     super(entityName);
/* 18:42 */     this.actions = actions;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean implies(Permission permission)
/* 22:   */   {
/* 23:47 */     return (("*".equals(getName())) || (getName().equals(permission.getName()))) && (("*".equals(this.actions)) || (this.actions.indexOf(permission.getActions()) >= 0));
/* 24:   */   }
/* 25:   */   
/* 26:   */   public boolean equals(Object obj)
/* 27:   */   {
/* 28:52 */     if (!(obj instanceof HibernatePermission)) {
/* 29:52 */       return false;
/* 30:   */     }
/* 31:53 */     HibernatePermission permission = (HibernatePermission)obj;
/* 32:54 */     return (permission.getName().equals(getName())) && (permission.getActions().equals(this.actions));
/* 33:   */   }
/* 34:   */   
/* 35:   */   public int hashCode()
/* 36:   */   {
/* 37:59 */     return getName().hashCode() * 37 + this.actions.hashCode();
/* 38:   */   }
/* 39:   */   
/* 40:   */   public String getActions()
/* 41:   */   {
/* 42:63 */     return this.actions;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public String toString()
/* 46:   */   {
/* 47:67 */     return "HibernatePermission(" + getName() + ':' + this.actions + ')';
/* 48:   */   }
/* 49:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.secure.internal.HibernatePermission
 * JD-Core Version:    0.7.0.1
 */