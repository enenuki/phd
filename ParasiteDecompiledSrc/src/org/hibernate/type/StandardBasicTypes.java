/*   1:    */ package org.hibernate.type;
/*   2:    */ 
/*   3:    */ import java.util.HashSet;
/*   4:    */ import java.util.Set;
/*   5:    */ import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
/*   6:    */ 
/*   7:    */ public class StandardBasicTypes
/*   8:    */ {
/*   9: 47 */   private static final Set<SqlTypeDescriptor> sqlTypeDescriptors = new HashSet();
/*  10: 54 */   public static final BooleanType BOOLEAN = BooleanType.INSTANCE;
/*  11: 61 */   public static final NumericBooleanType NUMERIC_BOOLEAN = NumericBooleanType.INSTANCE;
/*  12: 68 */   public static final TrueFalseType TRUE_FALSE = TrueFalseType.INSTANCE;
/*  13: 75 */   public static final YesNoType YES_NO = YesNoType.INSTANCE;
/*  14: 80 */   public static final ByteType BYTE = ByteType.INSTANCE;
/*  15: 87 */   public static final ShortType SHORT = ShortType.INSTANCE;
/*  16: 94 */   public static final IntegerType INTEGER = IntegerType.INSTANCE;
/*  17:101 */   public static final LongType LONG = LongType.INSTANCE;
/*  18:108 */   public static final FloatType FLOAT = FloatType.INSTANCE;
/*  19:115 */   public static final DoubleType DOUBLE = DoubleType.INSTANCE;
/*  20:122 */   public static final BigIntegerType BIG_INTEGER = BigIntegerType.INSTANCE;
/*  21:129 */   public static final BigDecimalType BIG_DECIMAL = BigDecimalType.INSTANCE;
/*  22:136 */   public static final CharacterType CHARACTER = CharacterType.INSTANCE;
/*  23:143 */   public static final StringType STRING = StringType.INSTANCE;
/*  24:150 */   public static final UrlType URL = UrlType.INSTANCE;
/*  25:158 */   public static final TimeType TIME = TimeType.INSTANCE;
/*  26:166 */   public static final DateType DATE = DateType.INSTANCE;
/*  27:174 */   public static final TimestampType TIMESTAMP = TimestampType.INSTANCE;
/*  28:182 */   public static final CalendarType CALENDAR = CalendarType.INSTANCE;
/*  29:190 */   public static final CalendarDateType CALENDAR_DATE = CalendarDateType.INSTANCE;
/*  30:197 */   public static final ClassType CLASS = ClassType.INSTANCE;
/*  31:204 */   public static final LocaleType LOCALE = LocaleType.INSTANCE;
/*  32:211 */   public static final CurrencyType CURRENCY = CurrencyType.INSTANCE;
/*  33:218 */   public static final TimeZoneType TIMEZONE = TimeZoneType.INSTANCE;
/*  34:225 */   public static final UUIDBinaryType UUID_BINARY = UUIDBinaryType.INSTANCE;
/*  35:232 */   public static final UUIDCharType UUID_CHAR = UUIDCharType.INSTANCE;
/*  36:239 */   public static final BinaryType BINARY = BinaryType.INSTANCE;
/*  37:246 */   public static final WrapperBinaryType WRAPPER_BINARY = WrapperBinaryType.INSTANCE;
/*  38:254 */   public static final ImageType IMAGE = ImageType.INSTANCE;
/*  39:262 */   public static final BlobType BLOB = BlobType.INSTANCE;
/*  40:271 */   public static final MaterializedBlobType MATERIALIZED_BLOB = MaterializedBlobType.INSTANCE;
/*  41:278 */   public static final CharArrayType CHAR_ARRAY = CharArrayType.INSTANCE;
/*  42:286 */   public static final CharacterArrayType CHARACTER_ARRAY = CharacterArrayType.INSTANCE;
/*  43:295 */   public static final TextType TEXT = TextType.INSTANCE;
/*  44:303 */   public static final ClobType CLOB = ClobType.INSTANCE;
/*  45:312 */   public static final MaterializedClobType MATERIALIZED_CLOB = MaterializedClobType.INSTANCE;
/*  46:321 */   public static final SerializableType SERIALIZABLE = SerializableType.INSTANCE;
/*  47:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.StandardBasicTypes
 * JD-Core Version:    0.7.0.1
 */