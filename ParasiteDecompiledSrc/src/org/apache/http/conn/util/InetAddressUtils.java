/*  1:   */ package org.apache.http.conn.util;
/*  2:   */ 
/*  3:   */ import java.util.regex.Matcher;
/*  4:   */ import java.util.regex.Pattern;
/*  5:   */ import org.apache.http.annotation.Immutable;
/*  6:   */ 
/*  7:   */ @Immutable
/*  8:   */ public class InetAddressUtils
/*  9:   */ {
/* 10:45 */   private static final Pattern IPV4_PATTERN = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
/* 11:49 */   private static final Pattern IPV6_STD_PATTERN = Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");
/* 12:53 */   private static final Pattern IPV6_HEX_COMPRESSED_PATTERN = Pattern.compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");
/* 13:   */   
/* 14:   */   public static boolean isIPv4Address(String input)
/* 15:   */   {
/* 16:58 */     return IPV4_PATTERN.matcher(input).matches();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static boolean isIPv6StdAddress(String input)
/* 20:   */   {
/* 21:62 */     return IPV6_STD_PATTERN.matcher(input).matches();
/* 22:   */   }
/* 23:   */   
/* 24:   */   public static boolean isIPv6HexCompressedAddress(String input)
/* 25:   */   {
/* 26:66 */     return IPV6_HEX_COMPRESSED_PATTERN.matcher(input).matches();
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static boolean isIPv6Address(String input)
/* 30:   */   {
/* 31:70 */     return (isIPv6StdAddress(input)) || (isIPv6HexCompressedAddress(input));
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.util.InetAddressUtils
 * JD-Core Version:    0.7.0.1
 */