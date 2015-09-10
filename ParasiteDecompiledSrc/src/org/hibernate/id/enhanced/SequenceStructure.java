/*   1:    */ package org.hibernate.id.enhanced;
/*   2:    */ 
/*   3:    */ import org.hibernate.HibernateException;
/*   4:    */ import org.hibernate.dialect.Dialect;
/*   5:    */ import org.hibernate.engine.spi.SessionImplementor;
/*   6:    */ import org.hibernate.internal.CoreMessageLogger;
/*   7:    */ import org.jboss.logging.Logger;
/*   8:    */ 
/*   9:    */ public class SequenceStructure
/*  10:    */   implements DatabaseStructure
/*  11:    */ {
/*  12: 46 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, SequenceStructure.class.getName());
/*  13:    */   private final String sequenceName;
/*  14:    */   private final int initialValue;
/*  15:    */   private final int incrementSize;
/*  16:    */   private final Class numberType;
/*  17:    */   private final String sql;
/*  18:    */   private boolean applyIncrementSizeToSourceValues;
/*  19:    */   private int accessCounter;
/*  20:    */   
/*  21:    */   public SequenceStructure(Dialect dialect, String sequenceName, int initialValue, int incrementSize, Class numberType)
/*  22:    */   {
/*  23: 62 */     this.sequenceName = sequenceName;
/*  24: 63 */     this.initialValue = initialValue;
/*  25: 64 */     this.incrementSize = incrementSize;
/*  26: 65 */     this.numberType = numberType;
/*  27: 66 */     this.sql = dialect.getSequenceNextValString(sequenceName);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String getName()
/*  31:    */   {
/*  32: 73 */     return this.sequenceName;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public int getIncrementSize()
/*  36:    */   {
/*  37: 80 */     return this.incrementSize;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public int getTimesAccessed()
/*  41:    */   {
/*  42: 87 */     return this.accessCounter;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int getInitialValue()
/*  46:    */   {
/*  47: 94 */     return this.initialValue;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public AccessCallback buildCallback(final SessionImplementor session)
/*  51:    */   {
/*  52:101 */     new AccessCallback()
/*  53:    */     {
/*  54:    */       /* Error */
/*  55:    */       public org.hibernate.id.IntegralDataTypeHolder getNextValue()
/*  56:    */       {
/*  57:    */         // Byte code:
/*  58:    */         //   0: aload_0
/*  59:    */         //   1: getfield 1	org/hibernate/id/enhanced/SequenceStructure$1:this$0	Lorg/hibernate/id/enhanced/SequenceStructure;
/*  60:    */         //   4: invokestatic 4	org/hibernate/id/enhanced/SequenceStructure:access$008	(Lorg/hibernate/id/enhanced/SequenceStructure;)I
/*  61:    */         //   7: pop
/*  62:    */         //   8: aload_0
/*  63:    */         //   9: getfield 2	org/hibernate/id/enhanced/SequenceStructure$1:val$session	Lorg/hibernate/engine/spi/SessionImplementor;
/*  64:    */         //   12: invokeinterface 5 1 0
/*  65:    */         //   17: invokeinterface 6 1 0
/*  66:    */         //   22: invokeinterface 7 1 0
/*  67:    */         //   27: aload_0
/*  68:    */         //   28: getfield 1	org/hibernate/id/enhanced/SequenceStructure$1:this$0	Lorg/hibernate/id/enhanced/SequenceStructure;
/*  69:    */         //   31: invokestatic 8	org/hibernate/id/enhanced/SequenceStructure:access$100	(Lorg/hibernate/id/enhanced/SequenceStructure;)Ljava/lang/String;
/*  70:    */         //   34: invokeinterface 9 2 0
/*  71:    */         //   39: astore_1
/*  72:    */         //   40: aload_1
/*  73:    */         //   41: invokeinterface 10 1 0
/*  74:    */         //   46: astore_2
/*  75:    */         //   47: aload_2
/*  76:    */         //   48: invokeinterface 11 1 0
/*  77:    */         //   53: pop
/*  78:    */         //   54: aload_0
/*  79:    */         //   55: getfield 1	org/hibernate/id/enhanced/SequenceStructure$1:this$0	Lorg/hibernate/id/enhanced/SequenceStructure;
/*  80:    */         //   58: invokestatic 12	org/hibernate/id/enhanced/SequenceStructure:access$200	(Lorg/hibernate/id/enhanced/SequenceStructure;)Ljava/lang/Class;
/*  81:    */         //   61: invokestatic 13	org/hibernate/id/IdentifierGeneratorHelper:getIntegralDataTypeHolder	(Ljava/lang/Class;)Lorg/hibernate/id/IntegralDataTypeHolder;
/*  82:    */         //   64: astore_3
/*  83:    */         //   65: aload_3
/*  84:    */         //   66: aload_2
/*  85:    */         //   67: lconst_1
/*  86:    */         //   68: invokeinterface 14 4 0
/*  87:    */         //   73: pop
/*  88:    */         //   74: invokestatic 15	org/hibernate/id/enhanced/SequenceStructure:access$300	()Lorg/hibernate/internal/CoreMessageLogger;
/*  89:    */         //   77: invokeinterface 16 1 0
/*  90:    */         //   82: ifeq +19 -> 101
/*  91:    */         //   85: invokestatic 15	org/hibernate/id/enhanced/SequenceStructure:access$300	()Lorg/hibernate/internal/CoreMessageLogger;
/*  92:    */         //   88: ldc 17
/*  93:    */         //   90: aload_3
/*  94:    */         //   91: invokeinterface 18 1 0
/*  95:    */         //   96: invokeinterface 19 3 0
/*  96:    */         //   101: aload_3
/*  97:    */         //   102: astore 4
/*  98:    */         //   104: aload_2
/*  99:    */         //   105: invokeinterface 20 1 0
/* 100:    */         //   110: goto +5 -> 115
/* 101:    */         //   113: astore 5
/* 102:    */         //   115: aload_1
/* 103:    */         //   116: invokeinterface 22 1 0
/* 104:    */         //   121: aload 4
/* 105:    */         //   123: areturn
/* 106:    */         //   124: astore 6
/* 107:    */         //   126: aload_2
/* 108:    */         //   127: invokeinterface 20 1 0
/* 109:    */         //   132: goto +5 -> 137
/* 110:    */         //   135: astore 7
/* 111:    */         //   137: aload 6
/* 112:    */         //   139: athrow
/* 113:    */         //   140: astore 8
/* 114:    */         //   142: aload_1
/* 115:    */         //   143: invokeinterface 22 1 0
/* 116:    */         //   148: aload 8
/* 117:    */         //   150: athrow
/* 118:    */         //   151: astore_1
/* 119:    */         //   152: aload_0
/* 120:    */         //   153: getfield 2	org/hibernate/id/enhanced/SequenceStructure$1:val$session	Lorg/hibernate/engine/spi/SessionImplementor;
/* 121:    */         //   156: invokeinterface 24 1 0
/* 122:    */         //   161: invokeinterface 25 1 0
/* 123:    */         //   166: aload_1
/* 124:    */         //   167: ldc 26
/* 125:    */         //   169: aload_0
/* 126:    */         //   170: getfield 1	org/hibernate/id/enhanced/SequenceStructure$1:this$0	Lorg/hibernate/id/enhanced/SequenceStructure;
/* 127:    */         //   173: invokestatic 8	org/hibernate/id/enhanced/SequenceStructure:access$100	(Lorg/hibernate/id/enhanced/SequenceStructure;)Ljava/lang/String;
/* 128:    */         //   176: invokevirtual 27	org/hibernate/engine/jdbc/spi/SqlExceptionHelper:convert	(Ljava/sql/SQLException;Ljava/lang/String;Ljava/lang/String;)Lorg/hibernate/JDBCException;
/* 129:    */         //   179: athrow
/* 130:    */         // Line number table:
/* 131:    */         //   Java source line #103	-> byte code offset #0
/* 132:    */         //   Java source line #105	-> byte code offset #8
/* 133:    */         //   Java source line #107	-> byte code offset #40
/* 134:    */         //   Java source line #109	-> byte code offset #47
/* 135:    */         //   Java source line #110	-> byte code offset #54
/* 136:    */         //   Java source line #111	-> byte code offset #65
/* 137:    */         //   Java source line #112	-> byte code offset #74
/* 138:    */         //   Java source line #113	-> byte code offset #85
/* 139:    */         //   Java source line #115	-> byte code offset #101
/* 140:    */         //   Java source line #119	-> byte code offset #104
/* 141:    */         //   Java source line #123	-> byte code offset #110
/* 142:    */         //   Java source line #121	-> byte code offset #113
/* 143:    */         //   Java source line #127	-> byte code offset #115
/* 144:    */         //   Java source line #118	-> byte code offset #124
/* 145:    */         //   Java source line #119	-> byte code offset #126
/* 146:    */         //   Java source line #123	-> byte code offset #132
/* 147:    */         //   Java source line #121	-> byte code offset #135
/* 148:    */         //   Java source line #123	-> byte code offset #137
/* 149:    */         //   Java source line #127	-> byte code offset #140
/* 150:    */         //   Java source line #131	-> byte code offset #151
/* 151:    */         //   Java source line #132	-> byte code offset #152
/* 152:    */         // Local variable table:
/* 153:    */         //   start	length	slot	name	signature
/* 154:    */         //   0	180	0	this	1
/* 155:    */         //   39	104	1	st	java.sql.PreparedStatement
/* 156:    */         //   151	16	1	sqle	java.sql.SQLException
/* 157:    */         //   46	81	2	rs	java.sql.ResultSet
/* 158:    */         //   64	38	3	value	org.hibernate.id.IntegralDataTypeHolder
/* 159:    */         //   102	20	4	localIntegralDataTypeHolder1	org.hibernate.id.IntegralDataTypeHolder
/* 160:    */         //   113	3	5	ignore	java.lang.Throwable
/* 161:    */         //   124	14	6	localObject1	Object
/* 162:    */         //   135	3	7	ignore	java.lang.Throwable
/* 163:    */         //   140	9	8	localObject2	Object
/* 164:    */         // Exception table:
/* 165:    */         //   from	to	target	type
/* 166:    */         //   104	110	113	java/lang/Throwable
/* 167:    */         //   47	104	124	finally
/* 168:    */         //   124	126	124	finally
/* 169:    */         //   126	132	135	java/lang/Throwable
/* 170:    */         //   40	115	140	finally
/* 171:    */         //   124	142	140	finally
/* 172:    */         //   8	121	151	java/sql/SQLException
/* 173:    */         //   124	151	151	java/sql/SQLException
/* 174:    */       }
/* 175:    */     };
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void prepare(Optimizer optimizer)
/* 179:    */   {
/* 180:146 */     this.applyIncrementSizeToSourceValues = optimizer.applyIncrementSizeToSourceValues();
/* 181:    */   }
/* 182:    */   
/* 183:    */   public String[] sqlCreateStrings(Dialect dialect)
/* 184:    */     throws HibernateException
/* 185:    */   {
/* 186:153 */     int sourceIncrementSize = this.applyIncrementSizeToSourceValues ? this.incrementSize : 1;
/* 187:154 */     return dialect.getCreateSequenceStrings(this.sequenceName, this.initialValue, sourceIncrementSize);
/* 188:    */   }
/* 189:    */   
/* 190:    */   public String[] sqlDropStrings(Dialect dialect)
/* 191:    */     throws HibernateException
/* 192:    */   {
/* 193:161 */     return dialect.getDropSequenceStrings(this.sequenceName);
/* 194:    */   }
/* 195:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.enhanced.SequenceStructure
 * JD-Core Version:    0.7.0.1
 */