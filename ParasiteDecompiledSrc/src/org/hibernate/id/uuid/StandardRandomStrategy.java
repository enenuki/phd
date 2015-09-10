/*  1:   */ package org.hibernate.id.uuid;
/*  2:   */ 
/*  3:   */ import java.util.UUID;
/*  4:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  5:   */ import org.hibernate.id.UUIDGenerationStrategy;
/*  6:   */ 
/*  7:   */ public class StandardRandomStrategy
/*  8:   */   implements UUIDGenerationStrategy
/*  9:   */ {
/* 10:36 */   public static final StandardRandomStrategy INSTANCE = new StandardRandomStrategy();
/* 11:   */   
/* 12:   */   public int getGeneratedVersion()
/* 13:   */   {
/* 14:43 */     return 4;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public UUID generateUUID(SessionImplementor session)
/* 18:   */   {
/* 19:50 */     return UUID.randomUUID();
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.uuid.StandardRandomStrategy
 * JD-Core Version:    0.7.0.1
 */