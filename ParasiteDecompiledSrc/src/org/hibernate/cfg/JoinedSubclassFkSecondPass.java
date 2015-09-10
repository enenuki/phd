/*  1:   */ package org.hibernate.cfg;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.MappingException;
/*  5:   */ import org.hibernate.cfg.annotations.TableBinder;
/*  6:   */ import org.hibernate.mapping.JoinedSubclass;
/*  7:   */ import org.hibernate.mapping.PersistentClass;
/*  8:   */ import org.hibernate.mapping.SimpleValue;
/*  9:   */ 
/* 10:   */ public class JoinedSubclassFkSecondPass
/* 11:   */   extends FkSecondPass
/* 12:   */ {
/* 13:   */   private JoinedSubclass entity;
/* 14:   */   private Mappings mappings;
/* 15:   */   
/* 16:   */   public JoinedSubclassFkSecondPass(JoinedSubclass entity, Ejb3JoinColumn[] inheritanceJoinedColumns, SimpleValue key, Mappings mappings)
/* 17:   */   {
/* 18:45 */     super(key, inheritanceJoinedColumns);
/* 19:46 */     this.entity = entity;
/* 20:47 */     this.mappings = mappings;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getReferencedEntityName()
/* 24:   */   {
/* 25:51 */     return this.entity.getSuperclass().getEntityName();
/* 26:   */   }
/* 27:   */   
/* 28:   */   public boolean isInPrimaryKey()
/* 29:   */   {
/* 30:55 */     return true;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void doSecondPass(Map persistentClasses)
/* 34:   */     throws MappingException
/* 35:   */   {
/* 36:59 */     TableBinder.bindFk(this.entity.getSuperclass(), this.entity, this.columns, this.value, false, this.mappings);
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.JoinedSubclassFkSecondPass
 * JD-Core Version:    0.7.0.1
 */