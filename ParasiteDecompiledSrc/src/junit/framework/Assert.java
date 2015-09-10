/*   1:    */ package junit.framework;
/*   2:    */ 
/*   3:    */ public class Assert
/*   4:    */ {
/*   5:    */   public static void assertTrue(String message, boolean condition)
/*   6:    */   {
/*   7: 19 */     if (!condition) {
/*   8: 20 */       fail(message);
/*   9:    */     }
/*  10:    */   }
/*  11:    */   
/*  12:    */   public static void assertTrue(boolean condition)
/*  13:    */   {
/*  14: 27 */     assertTrue(null, condition);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public static void assertFalse(String message, boolean condition)
/*  18:    */   {
/*  19: 34 */     assertTrue(message, !condition);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public static void assertFalse(boolean condition)
/*  23:    */   {
/*  24: 41 */     assertFalse(null, condition);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static void fail(String message)
/*  28:    */   {
/*  29: 47 */     if (message == null) {
/*  30: 48 */       throw new AssertionFailedError();
/*  31:    */     }
/*  32: 50 */     throw new AssertionFailedError(message);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static void fail()
/*  36:    */   {
/*  37: 56 */     fail(null);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static void assertEquals(String message, Object expected, Object actual)
/*  41:    */   {
/*  42: 63 */     if ((expected == null) && (actual == null)) {
/*  43: 64 */       return;
/*  44:    */     }
/*  45: 65 */     if ((expected != null) && (expected.equals(actual))) {
/*  46: 66 */       return;
/*  47:    */     }
/*  48: 67 */     failNotEquals(message, expected, actual);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public static void assertEquals(Object expected, Object actual)
/*  52:    */   {
/*  53: 74 */     assertEquals(null, expected, actual);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static void assertEquals(String message, String expected, String actual)
/*  57:    */   {
/*  58: 80 */     if ((expected == null) && (actual == null)) {
/*  59: 81 */       return;
/*  60:    */     }
/*  61: 82 */     if ((expected != null) && (expected.equals(actual))) {
/*  62: 83 */       return;
/*  63:    */     }
/*  64: 84 */     String cleanMessage = message == null ? "" : message;
/*  65: 85 */     throw new ComparisonFailure(cleanMessage, expected, actual);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static void assertEquals(String expected, String actual)
/*  69:    */   {
/*  70: 91 */     assertEquals(null, expected, actual);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static void assertEquals(String message, double expected, double actual, double delta)
/*  74:    */   {
/*  75: 99 */     if (Double.compare(expected, actual) == 0) {
/*  76:100 */       return;
/*  77:    */     }
/*  78:101 */     if (Math.abs(expected - actual) > delta) {
/*  79:102 */       failNotEquals(message, new Double(expected), new Double(actual));
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static void assertEquals(double expected, double actual, double delta)
/*  84:    */   {
/*  85:109 */     assertEquals(null, expected, actual, delta);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static void assertEquals(String message, float expected, float actual, float delta)
/*  89:    */   {
/*  90:117 */     if (Float.compare(expected, actual) == 0) {
/*  91:118 */       return;
/*  92:    */     }
/*  93:119 */     if (Math.abs(expected - actual) > delta) {
/*  94:120 */       failNotEquals(message, new Float(expected), new Float(actual));
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public static void assertEquals(float expected, float actual, float delta)
/*  99:    */   {
/* 100:127 */     assertEquals(null, expected, actual, delta);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public static void assertEquals(String message, long expected, long actual)
/* 104:    */   {
/* 105:134 */     assertEquals(message, new Long(expected), new Long(actual));
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static void assertEquals(long expected, long actual)
/* 109:    */   {
/* 110:140 */     assertEquals(null, expected, actual);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static void assertEquals(String message, boolean expected, boolean actual)
/* 114:    */   {
/* 115:147 */     assertEquals(message, Boolean.valueOf(expected), Boolean.valueOf(actual));
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static void assertEquals(boolean expected, boolean actual)
/* 119:    */   {
/* 120:153 */     assertEquals(null, expected, actual);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public static void assertEquals(String message, byte expected, byte actual)
/* 124:    */   {
/* 125:160 */     assertEquals(message, new Byte(expected), new Byte(actual));
/* 126:    */   }
/* 127:    */   
/* 128:    */   public static void assertEquals(byte expected, byte actual)
/* 129:    */   {
/* 130:166 */     assertEquals(null, expected, actual);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public static void assertEquals(String message, char expected, char actual)
/* 134:    */   {
/* 135:173 */     assertEquals(message, new Character(expected), new Character(actual));
/* 136:    */   }
/* 137:    */   
/* 138:    */   public static void assertEquals(char expected, char actual)
/* 139:    */   {
/* 140:179 */     assertEquals(null, expected, actual);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public static void assertEquals(String message, short expected, short actual)
/* 144:    */   {
/* 145:186 */     assertEquals(message, new Short(expected), new Short(actual));
/* 146:    */   }
/* 147:    */   
/* 148:    */   public static void assertEquals(short expected, short actual)
/* 149:    */   {
/* 150:192 */     assertEquals(null, expected, actual);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public static void assertEquals(String message, int expected, int actual)
/* 154:    */   {
/* 155:199 */     assertEquals(message, new Integer(expected), new Integer(actual));
/* 156:    */   }
/* 157:    */   
/* 158:    */   public static void assertEquals(int expected, int actual)
/* 159:    */   {
/* 160:205 */     assertEquals(null, expected, actual);
/* 161:    */   }
/* 162:    */   
/* 163:    */   public static void assertNotNull(Object object)
/* 164:    */   {
/* 165:211 */     assertNotNull(null, object);
/* 166:    */   }
/* 167:    */   
/* 168:    */   public static void assertNotNull(String message, Object object)
/* 169:    */   {
/* 170:218 */     assertTrue(message, object != null);
/* 171:    */   }
/* 172:    */   
/* 173:    */   public static void assertNull(Object object)
/* 174:    */   {
/* 175:229 */     String message = "Expected: <null> but was: " + String.valueOf(object);
/* 176:230 */     assertNull(message, object);
/* 177:    */   }
/* 178:    */   
/* 179:    */   public static void assertNull(String message, Object object)
/* 180:    */   {
/* 181:237 */     assertTrue(message, object == null);
/* 182:    */   }
/* 183:    */   
/* 184:    */   public static void assertSame(String message, Object expected, Object actual)
/* 185:    */   {
/* 186:244 */     if (expected == actual) {
/* 187:245 */       return;
/* 188:    */     }
/* 189:246 */     failNotSame(message, expected, actual);
/* 190:    */   }
/* 191:    */   
/* 192:    */   public static void assertSame(Object expected, Object actual)
/* 193:    */   {
/* 194:253 */     assertSame(null, expected, actual);
/* 195:    */   }
/* 196:    */   
/* 197:    */   public static void assertNotSame(String message, Object expected, Object actual)
/* 198:    */   {
/* 199:261 */     if (expected == actual) {
/* 200:262 */       failSame(message);
/* 201:    */     }
/* 202:    */   }
/* 203:    */   
/* 204:    */   public static void assertNotSame(Object expected, Object actual)
/* 205:    */   {
/* 206:269 */     assertNotSame(null, expected, actual);
/* 207:    */   }
/* 208:    */   
/* 209:    */   public static void failSame(String message)
/* 210:    */   {
/* 211:273 */     String formatted = "";
/* 212:274 */     if (message != null) {
/* 213:275 */       formatted = message + " ";
/* 214:    */     }
/* 215:276 */     fail(formatted + "expected not same");
/* 216:    */   }
/* 217:    */   
/* 218:    */   public static void failNotSame(String message, Object expected, Object actual)
/* 219:    */   {
/* 220:280 */     String formatted = "";
/* 221:281 */     if (message != null) {
/* 222:282 */       formatted = message + " ";
/* 223:    */     }
/* 224:283 */     fail(formatted + "expected same:<" + expected + "> was not:<" + actual + ">");
/* 225:    */   }
/* 226:    */   
/* 227:    */   public static void failNotEquals(String message, Object expected, Object actual)
/* 228:    */   {
/* 229:287 */     fail(format(message, expected, actual));
/* 230:    */   }
/* 231:    */   
/* 232:    */   public static String format(String message, Object expected, Object actual)
/* 233:    */   {
/* 234:291 */     String formatted = "";
/* 235:292 */     if ((message != null) && (!message.isEmpty())) {
/* 236:293 */       formatted = message + " ";
/* 237:    */     }
/* 238:294 */     return formatted + "expected:<" + expected + "> but was:<" + actual + ">";
/* 239:    */   }
/* 240:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     junit.framework.Assert
 * JD-Core Version:    0.7.0.1
 */