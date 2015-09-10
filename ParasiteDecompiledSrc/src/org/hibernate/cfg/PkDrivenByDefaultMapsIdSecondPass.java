/*  1:   */ package org.hibernate.cfg;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.AnnotationException;
/*  5:   */ import org.hibernate.MappingException;
/*  6:   */ import org.hibernate.cfg.annotations.TableBinder;
/*  7:   */ import org.hibernate.mapping.KeyValue;
/*  8:   */ import org.hibernate.mapping.PersistentClass;
/*  9:   */ import org.hibernate.mapping.SimpleValue;
/* 10:   */ 
/* 11:   */ public class PkDrivenByDefaultMapsIdSecondPass
/* 12:   */   implements SecondPass
/* 13:   */ {
/* 14:   */   private final String referencedEntityName;
/* 15:   */   private final Ejb3JoinColumn[] columns;
/* 16:   */   private final SimpleValue value;
/* 17:   */   
/* 18:   */   public PkDrivenByDefaultMapsIdSecondPass(String referencedEntityName, Ejb3JoinColumn[] columns, SimpleValue value)
/* 19:   */   {
/* 20:42 */     this.referencedEntityName = referencedEntityName;
/* 21:43 */     this.columns = columns;
/* 22:44 */     this.value = value;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void doSecondPass(Map persistentClasses)
/* 26:   */     throws MappingException
/* 27:   */   {
/* 28:48 */     PersistentClass referencedEntity = (PersistentClass)persistentClasses.get(this.referencedEntityName);
/* 29:49 */     if (referencedEntity == null) {
/* 30:50 */       throw new AnnotationException("Unknown entity name: " + this.referencedEntityName);
/* 31:   */     }
/* 32:54 */     TableBinder.linkJoinColumnWithValueOverridingNameIfImplicit(referencedEntity, referencedEntity.getKey().getColumnIterator(), this.columns, this.value);
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.PkDrivenByDefaultMapsIdSecondPass
 * JD-Core Version:    0.7.0.1
 */