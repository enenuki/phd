/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.util.TimeZone;
/*  4:   */ import org.hibernate.dialect.Dialect;
/*  5:   */ import org.hibernate.type.descriptor.java.TimeZoneTypeDescriptor;
/*  6:   */ import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;
/*  7:   */ 
/*  8:   */ public class TimeZoneType
/*  9:   */   extends AbstractSingleColumnStandardBasicType<TimeZone>
/* 10:   */   implements LiteralType<TimeZone>
/* 11:   */ {
/* 12:42 */   public static final TimeZoneType INSTANCE = new TimeZoneType();
/* 13:   */   
/* 14:   */   public TimeZoneType()
/* 15:   */   {
/* 16:45 */     super(VarcharTypeDescriptor.INSTANCE, TimeZoneTypeDescriptor.INSTANCE);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getName()
/* 20:   */   {
/* 21:49 */     return "timezone";
/* 22:   */   }
/* 23:   */   
/* 24:   */   protected boolean registerUnderJavaType()
/* 25:   */   {
/* 26:54 */     return true;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String objectToSQLString(TimeZone value, Dialect dialect)
/* 30:   */     throws Exception
/* 31:   */   {
/* 32:58 */     return StringType.INSTANCE.objectToSQLString(value.getID(), dialect);
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.TimeZoneType
 * JD-Core Version:    0.7.0.1
 */