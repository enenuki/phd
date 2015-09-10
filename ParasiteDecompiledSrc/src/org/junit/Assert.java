/*   1:    */ package org.junit;
/*   2:    */ 
/*   3:    */ import org.hamcrest.Description;
/*   4:    */ import org.hamcrest.Matcher;
/*   5:    */ import org.hamcrest.StringDescription;
/*   6:    */ import org.junit.internal.ArrayComparisonFailure;
/*   7:    */ import org.junit.internal.ExactComparisonCriteria;
/*   8:    */ import org.junit.internal.InexactComparisonCriteria;
/*   9:    */ 
/*  10:    */ public class Assert
/*  11:    */ {
/*  12:    */   public static void assertTrue(String message, boolean condition)
/*  13:    */   {
/*  14: 42 */     if (!condition) {
/*  15: 43 */       fail(message);
/*  16:    */     }
/*  17:    */   }
/*  18:    */   
/*  19:    */   public static void assertTrue(boolean condition)
/*  20:    */   {
/*  21: 54 */     assertTrue(null, condition);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public static void assertFalse(String message, boolean condition)
/*  25:    */   {
/*  26: 68 */     assertTrue(message, !condition);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static void assertFalse(boolean condition)
/*  30:    */   {
/*  31: 79 */     assertFalse(null, condition);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static void fail(String message)
/*  35:    */   {
/*  36: 91 */     if (message == null) {
/*  37: 92 */       throw new AssertionError();
/*  38:    */     }
/*  39: 93 */     throw new AssertionError(message);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static void fail()
/*  43:    */   {
/*  44:100 */     fail(null);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static void assertEquals(String message, Object expected, Object actual)
/*  48:    */   {
/*  49:119 */     if ((expected == null) && (actual == null)) {
/*  50:120 */       return;
/*  51:    */     }
/*  52:121 */     if ((expected != null) && (isEquals(expected, actual))) {
/*  53:122 */       return;
/*  54:    */     }
/*  55:123 */     if (((expected instanceof String)) && ((actual instanceof String)))
/*  56:    */     {
/*  57:124 */       String cleanMessage = message == null ? "" : message;
/*  58:125 */       throw new ComparisonFailure(cleanMessage, (String)expected, (String)actual);
/*  59:    */     }
/*  60:128 */     failNotEquals(message, expected, actual);
/*  61:    */   }
/*  62:    */   
/*  63:    */   private static boolean isEquals(Object expected, Object actual)
/*  64:    */   {
/*  65:132 */     return expected.equals(actual);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static void assertEquals(Object expected, Object actual)
/*  69:    */   {
/*  70:147 */     assertEquals(null, expected, actual);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static void assertArrayEquals(String message, Object[] expecteds, Object[] actuals)
/*  74:    */     throws ArrayComparisonFailure
/*  75:    */   {
/*  76:168 */     internalArrayEquals(message, expecteds, actuals);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static void assertArrayEquals(Object[] expecteds, Object[] actuals)
/*  80:    */   {
/*  81:185 */     assertArrayEquals(null, expecteds, actuals);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static void assertArrayEquals(String message, byte[] expecteds, byte[] actuals)
/*  85:    */     throws ArrayComparisonFailure
/*  86:    */   {
/*  87:202 */     internalArrayEquals(message, expecteds, actuals);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static void assertArrayEquals(byte[] expecteds, byte[] actuals)
/*  91:    */   {
/*  92:215 */     assertArrayEquals(null, expecteds, actuals);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public static void assertArrayEquals(String message, char[] expecteds, char[] actuals)
/*  96:    */     throws ArrayComparisonFailure
/*  97:    */   {
/*  98:232 */     internalArrayEquals(message, expecteds, actuals);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static void assertArrayEquals(char[] expecteds, char[] actuals)
/* 102:    */   {
/* 103:245 */     assertArrayEquals(null, expecteds, actuals);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public static void assertArrayEquals(String message, short[] expecteds, short[] actuals)
/* 107:    */     throws ArrayComparisonFailure
/* 108:    */   {
/* 109:262 */     internalArrayEquals(message, expecteds, actuals);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public static void assertArrayEquals(short[] expecteds, short[] actuals)
/* 113:    */   {
/* 114:275 */     assertArrayEquals(null, expecteds, actuals);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public static void assertArrayEquals(String message, int[] expecteds, int[] actuals)
/* 118:    */     throws ArrayComparisonFailure
/* 119:    */   {
/* 120:292 */     internalArrayEquals(message, expecteds, actuals);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public static void assertArrayEquals(int[] expecteds, int[] actuals)
/* 124:    */   {
/* 125:305 */     assertArrayEquals(null, expecteds, actuals);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public static void assertArrayEquals(String message, long[] expecteds, long[] actuals)
/* 129:    */     throws ArrayComparisonFailure
/* 130:    */   {
/* 131:322 */     internalArrayEquals(message, expecteds, actuals);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public static void assertArrayEquals(long[] expecteds, long[] actuals)
/* 135:    */   {
/* 136:335 */     assertArrayEquals(null, expecteds, actuals);
/* 137:    */   }
/* 138:    */   
/* 139:    */   public static void assertArrayEquals(String message, double[] expecteds, double[] actuals, double delta)
/* 140:    */     throws ArrayComparisonFailure
/* 141:    */   {
/* 142:352 */     new InexactComparisonCriteria(delta).arrayEquals(message, expecteds, actuals);
/* 143:    */   }
/* 144:    */   
/* 145:    */   public static void assertArrayEquals(double[] expecteds, double[] actuals, double delta)
/* 146:    */   {
/* 147:365 */     assertArrayEquals(null, expecteds, actuals, delta);
/* 148:    */   }
/* 149:    */   
/* 150:    */   public static void assertArrayEquals(String message, float[] expecteds, float[] actuals, float delta)
/* 151:    */     throws ArrayComparisonFailure
/* 152:    */   {
/* 153:382 */     new InexactComparisonCriteria(delta).arrayEquals(message, expecteds, actuals);
/* 154:    */   }
/* 155:    */   
/* 156:    */   public static void assertArrayEquals(float[] expecteds, float[] actuals, float delta)
/* 157:    */   {
/* 158:395 */     assertArrayEquals(null, expecteds, actuals, delta);
/* 159:    */   }
/* 160:    */   
/* 161:    */   private static void internalArrayEquals(String message, Object expecteds, Object actuals)
/* 162:    */     throws ArrayComparisonFailure
/* 163:    */   {
/* 164:416 */     new ExactComparisonCriteria().arrayEquals(message, expecteds, actuals);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public static void assertEquals(String message, double expected, double actual, double delta)
/* 168:    */   {
/* 169:440 */     if (Double.compare(expected, actual) == 0) {
/* 170:441 */       return;
/* 171:    */     }
/* 172:442 */     if (Math.abs(expected - actual) > delta) {
/* 173:443 */       failNotEquals(message, new Double(expected), new Double(actual));
/* 174:    */     }
/* 175:    */   }
/* 176:    */   
/* 177:    */   public static void assertEquals(long expected, long actual)
/* 178:    */   {
/* 179:456 */     assertEquals(null, expected, actual);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public static void assertEquals(String message, long expected, long actual)
/* 183:    */   {
/* 184:472 */     assertEquals(message, Long.valueOf(expected), Long.valueOf(actual));
/* 185:    */   }
/* 186:    */   
/* 187:    */   @Deprecated
/* 188:    */   public static void assertEquals(double expected, double actual)
/* 189:    */   {
/* 190:482 */     assertEquals(null, expected, actual);
/* 191:    */   }
/* 192:    */   
/* 193:    */   @Deprecated
/* 194:    */   public static void assertEquals(String message, double expected, double actual)
/* 195:    */   {
/* 196:493 */     fail("Use assertEquals(expected, actual, delta) to compare floating-point numbers");
/* 197:    */   }
/* 198:    */   
/* 199:    */   public static void assertEquals(double expected, double actual, double delta)
/* 200:    */   {
/* 201:512 */     assertEquals(null, expected, actual, delta);
/* 202:    */   }
/* 203:    */   
/* 204:    */   public static void assertNotNull(String message, Object object)
/* 205:    */   {
/* 206:526 */     assertTrue(message, object != null);
/* 207:    */   }
/* 208:    */   
/* 209:    */   public static void assertNotNull(Object object)
/* 210:    */   {
/* 211:537 */     assertNotNull(null, object);
/* 212:    */   }
/* 213:    */   
/* 214:    */   public static void assertNull(String message, Object object)
/* 215:    */   {
/* 216:551 */     assertTrue(message, object == null);
/* 217:    */   }
/* 218:    */   
/* 219:    */   public static void assertNull(Object object)
/* 220:    */   {
/* 221:562 */     assertNull(null, object);
/* 222:    */   }
/* 223:    */   
/* 224:    */   public static void assertSame(String message, Object expected, Object actual)
/* 225:    */   {
/* 226:578 */     if (expected == actual) {
/* 227:579 */       return;
/* 228:    */     }
/* 229:580 */     failNotSame(message, expected, actual);
/* 230:    */   }
/* 231:    */   
/* 232:    */   public static void assertSame(Object expected, Object actual)
/* 233:    */   {
/* 234:593 */     assertSame(null, expected, actual);
/* 235:    */   }
/* 236:    */   
/* 237:    */   public static void assertNotSame(String message, Object unexpected, Object actual)
/* 238:    */   {
/* 239:611 */     if (unexpected == actual) {
/* 240:612 */       failSame(message);
/* 241:    */     }
/* 242:    */   }
/* 243:    */   
/* 244:    */   public static void assertNotSame(Object unexpected, Object actual)
/* 245:    */   {
/* 246:626 */     assertNotSame(null, unexpected, actual);
/* 247:    */   }
/* 248:    */   
/* 249:    */   private static void failSame(String message)
/* 250:    */   {
/* 251:630 */     String formatted = "";
/* 252:631 */     if (message != null) {
/* 253:632 */       formatted = message + " ";
/* 254:    */     }
/* 255:633 */     fail(formatted + "expected not same");
/* 256:    */   }
/* 257:    */   
/* 258:    */   private static void failNotSame(String message, Object expected, Object actual)
/* 259:    */   {
/* 260:638 */     String formatted = "";
/* 261:639 */     if (message != null) {
/* 262:640 */       formatted = message + " ";
/* 263:    */     }
/* 264:641 */     fail(formatted + "expected same:<" + expected + "> was not:<" + actual + ">");
/* 265:    */   }
/* 266:    */   
/* 267:    */   private static void failNotEquals(String message, Object expected, Object actual)
/* 268:    */   {
/* 269:647 */     fail(format(message, expected, actual));
/* 270:    */   }
/* 271:    */   
/* 272:    */   static String format(String message, Object expected, Object actual)
/* 273:    */   {
/* 274:651 */     String formatted = "";
/* 275:652 */     if ((message != null) && (!message.equals(""))) {
/* 276:653 */       formatted = message + " ";
/* 277:    */     }
/* 278:654 */     String expectedString = String.valueOf(expected);
/* 279:655 */     String actualString = String.valueOf(actual);
/* 280:656 */     if (expectedString.equals(actualString)) {
/* 281:657 */       return formatted + "expected: " + formatClassAndValue(expected, expectedString) + " but was: " + formatClassAndValue(actual, actualString);
/* 282:    */     }
/* 283:661 */     return formatted + "expected:<" + expectedString + "> but was:<" + actualString + ">";
/* 284:    */   }
/* 285:    */   
/* 286:    */   private static String formatClassAndValue(Object value, String valueString)
/* 287:    */   {
/* 288:666 */     String className = value == null ? "null" : value.getClass().getName();
/* 289:667 */     return className + "<" + valueString + ">";
/* 290:    */   }
/* 291:    */   
/* 292:    */   @Deprecated
/* 293:    */   public static void assertEquals(String message, Object[] expecteds, Object[] actuals)
/* 294:    */   {
/* 295:690 */     assertArrayEquals(message, expecteds, actuals);
/* 296:    */   }
/* 297:    */   
/* 298:    */   @Deprecated
/* 299:    */   public static void assertEquals(Object[] expecteds, Object[] actuals)
/* 300:    */   {
/* 301:709 */     assertArrayEquals(expecteds, actuals);
/* 302:    */   }
/* 303:    */   
/* 304:    */   public static <T> void assertThat(T actual, Matcher<T> matcher)
/* 305:    */   {
/* 306:738 */     assertThat("", actual, matcher);
/* 307:    */   }
/* 308:    */   
/* 309:    */   public static <T> void assertThat(String reason, T actual, Matcher<T> matcher)
/* 310:    */   {
/* 311:772 */     if (!matcher.matches(actual))
/* 312:    */     {
/* 313:773 */       Description description = new StringDescription();
/* 314:774 */       description.appendText(reason);
/* 315:775 */       description.appendText("\nExpected: ");
/* 316:776 */       description.appendDescriptionOf(matcher);
/* 317:777 */       description.appendText("\n     got: ");
/* 318:778 */       description.appendValue(actual);
/* 319:779 */       description.appendText("\n");
/* 320:780 */       throw new AssertionError(description.toString());
/* 321:    */     }
/* 322:    */   }
/* 323:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.Assert
 * JD-Core Version:    0.7.0.1
 */