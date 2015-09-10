/*   1:    */ package org.hibernate.type;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Map;
/*   5:    */ import java.util.concurrent.ConcurrentHashMap;
/*   6:    */ import org.hibernate.HibernateException;
/*   7:    */ import org.hibernate.internal.CoreMessageLogger;
/*   8:    */ import org.hibernate.usertype.CompositeUserType;
/*   9:    */ import org.hibernate.usertype.UserType;
/*  10:    */ import org.jboss.logging.Logger;
/*  11:    */ 
/*  12:    */ public class BasicTypeRegistry
/*  13:    */   implements Serializable
/*  14:    */ {
/*  15: 44 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, BasicTypeRegistry.class.getName());
/*  16: 47 */   private Map<String, BasicType> registry = new ConcurrentHashMap(100, 0.75F, 1);
/*  17: 48 */   private boolean locked = false;
/*  18:    */   
/*  19:    */   public BasicTypeRegistry()
/*  20:    */   {
/*  21: 51 */     register(BooleanType.INSTANCE);
/*  22: 52 */     register(NumericBooleanType.INSTANCE);
/*  23: 53 */     register(TrueFalseType.INSTANCE);
/*  24: 54 */     register(YesNoType.INSTANCE);
/*  25:    */     
/*  26: 56 */     register(ByteType.INSTANCE);
/*  27: 57 */     register(CharacterType.INSTANCE);
/*  28: 58 */     register(ShortType.INSTANCE);
/*  29: 59 */     register(IntegerType.INSTANCE);
/*  30: 60 */     register(LongType.INSTANCE);
/*  31: 61 */     register(FloatType.INSTANCE);
/*  32: 62 */     register(DoubleType.INSTANCE);
/*  33: 63 */     register(BigDecimalType.INSTANCE);
/*  34: 64 */     register(BigIntegerType.INSTANCE);
/*  35:    */     
/*  36: 66 */     register(StringType.INSTANCE);
/*  37: 67 */     register(UrlType.INSTANCE);
/*  38:    */     
/*  39: 69 */     register(DateType.INSTANCE);
/*  40: 70 */     register(TimeType.INSTANCE);
/*  41: 71 */     register(TimestampType.INSTANCE);
/*  42: 72 */     register(DbTimestampType.INSTANCE);
/*  43: 73 */     register(CalendarType.INSTANCE);
/*  44: 74 */     register(CalendarDateType.INSTANCE);
/*  45:    */     
/*  46: 76 */     register(LocaleType.INSTANCE);
/*  47: 77 */     register(CurrencyType.INSTANCE);
/*  48: 78 */     register(TimeZoneType.INSTANCE);
/*  49: 79 */     register(ClassType.INSTANCE);
/*  50: 80 */     register(UUIDBinaryType.INSTANCE);
/*  51: 81 */     register(UUIDCharType.INSTANCE);
/*  52: 82 */     register(PostgresUUIDType.INSTANCE);
/*  53:    */     
/*  54: 84 */     register(BinaryType.INSTANCE);
/*  55: 85 */     register(WrapperBinaryType.INSTANCE);
/*  56: 86 */     register(ImageType.INSTANCE);
/*  57: 87 */     register(CharArrayType.INSTANCE);
/*  58: 88 */     register(CharacterArrayType.INSTANCE);
/*  59: 89 */     register(TextType.INSTANCE);
/*  60: 90 */     register(BlobType.INSTANCE);
/*  61: 91 */     register(MaterializedBlobType.INSTANCE);
/*  62: 92 */     register(ClobType.INSTANCE);
/*  63: 93 */     register(MaterializedClobType.INSTANCE);
/*  64: 94 */     register(SerializableType.INSTANCE);
/*  65:    */     
/*  66: 96 */     register(ObjectType.INSTANCE);
/*  67:    */     
/*  68:    */ 
/*  69: 99 */     register(new AdaptedImmutableType(DateType.INSTANCE));
/*  70:    */     
/*  71:101 */     register(new AdaptedImmutableType(TimeType.INSTANCE));
/*  72:    */     
/*  73:103 */     register(new AdaptedImmutableType(TimestampType.INSTANCE));
/*  74:    */     
/*  75:105 */     register(new AdaptedImmutableType(DbTimestampType.INSTANCE));
/*  76:    */     
/*  77:107 */     register(new AdaptedImmutableType(CalendarType.INSTANCE));
/*  78:    */     
/*  79:109 */     register(new AdaptedImmutableType(CalendarDateType.INSTANCE));
/*  80:    */     
/*  81:111 */     register(new AdaptedImmutableType(BinaryType.INSTANCE));
/*  82:    */     
/*  83:113 */     register(new AdaptedImmutableType(SerializableType.INSTANCE));
/*  84:    */   }
/*  85:    */   
/*  86:    */   private BasicTypeRegistry(Map<String, BasicType> registeredTypes)
/*  87:    */   {
/*  88:123 */     this.registry.putAll(registeredTypes);
/*  89:124 */     this.locked = true;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void register(BasicType type)
/*  93:    */   {
/*  94:128 */     if (this.locked) {
/*  95:129 */       throw new HibernateException("Can not alter TypeRegistry at this time");
/*  96:    */     }
/*  97:132 */     if (type == null) {
/*  98:133 */       throw new HibernateException("Type to register cannot be null");
/*  99:    */     }
/* 100:136 */     if ((type.getRegistrationKeys() == null) || (type.getRegistrationKeys().length == 0)) {
/* 101:137 */       LOG.typeDefinedNoRegistrationKeys(type);
/* 102:    */     }
/* 103:140 */     for (String key : type.getRegistrationKeys()) {
/* 104:142 */       if (key != null)
/* 105:    */       {
/* 106:143 */         LOG.debugf("Adding type registration %s -> %s", key, type);
/* 107:144 */         Type old = (Type)this.registry.put(key, type);
/* 108:145 */         if ((old != null) && (old != type)) {
/* 109:145 */           LOG.typeRegistrationOverridesPrevious(key, old);
/* 110:    */         }
/* 111:    */       }
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void register(UserType type, String[] keys)
/* 116:    */   {
/* 117:150 */     register(new CustomType(type, keys));
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void register(CompositeUserType type, String[] keys)
/* 121:    */   {
/* 122:154 */     register(new CompositeCustomType(type, keys));
/* 123:    */   }
/* 124:    */   
/* 125:    */   public BasicType getRegisteredType(String key)
/* 126:    */   {
/* 127:158 */     return (BasicType)this.registry.get(key);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public BasicTypeRegistry shallowCopy()
/* 131:    */   {
/* 132:162 */     return new BasicTypeRegistry(this.registry);
/* 133:    */   }
/* 134:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.BasicTypeRegistry
 * JD-Core Version:    0.7.0.1
 */