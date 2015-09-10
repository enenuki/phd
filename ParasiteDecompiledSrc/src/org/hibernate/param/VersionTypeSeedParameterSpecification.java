/*  1:   */ package org.hibernate.param;
/*  2:   */ 
/*  3:   */ import java.sql.PreparedStatement;
/*  4:   */ import java.sql.SQLException;
/*  5:   */ import org.hibernate.engine.spi.QueryParameters;
/*  6:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  7:   */ import org.hibernate.type.Type;
/*  8:   */ import org.hibernate.type.VersionType;
/*  9:   */ 
/* 10:   */ public class VersionTypeSeedParameterSpecification
/* 11:   */   implements ParameterSpecification
/* 12:   */ {
/* 13:   */   private VersionType type;
/* 14:   */   
/* 15:   */   public VersionTypeSeedParameterSpecification(VersionType type)
/* 16:   */   {
/* 17:48 */     this.type = type;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public int bind(PreparedStatement statement, QueryParameters qp, SessionImplementor session, int position)
/* 21:   */     throws SQLException
/* 22:   */   {
/* 23:56 */     this.type.nullSafeSet(statement, this.type.seed(session), position, session);
/* 24:57 */     return 1;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public Type getExpectedType()
/* 28:   */   {
/* 29:64 */     return this.type;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void setExpectedType(Type expectedType) {}
/* 33:   */   
/* 34:   */   public String renderDisplayInfo()
/* 35:   */   {
/* 36:78 */     return "version-seed, type=" + this.type;
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.param.VersionTypeSeedParameterSpecification
 * JD-Core Version:    0.7.0.1
 */