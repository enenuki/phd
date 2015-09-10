package corejava;

import java.io.PrintStream;

public class Format
{
  private int width = 0;
  private int precision = -1;
  private String pre = "";
  private String post = "";
  private boolean leadingZeroes = false;
  private boolean showPlus = false;
  private boolean alternate = false;
  private boolean showSpace = false;
  private boolean leftAlign = false;
  private char fmt = ' ';
  
  public Format(String paramString)
  {
    int i = 0;
    int j = paramString.length();
    int k = 0;
    for (int m = 0; k == 0; m++) {
      if (m >= j) {
        k = 5;
      } else if (paramString.charAt(m) == '%')
      {
        if (m < j - 1)
        {
          if (paramString.charAt(m + 1) == '%')
          {
            this.pre += '%';
            m++;
          }
          else
          {
            k = 1;
          }
        }
        else {
          throw new IllegalArgumentException();
        }
      }
      else {
        this.pre += paramString.charAt(m);
      }
    }
    while (k == 1)
    {
      if (m >= j)
      {
        k = 5;
      }
      else if (paramString.charAt(m) == ' ')
      {
        this.showSpace = true;
      }
      else if (paramString.charAt(m) == '-')
      {
        this.leftAlign = true;
      }
      else if (paramString.charAt(m) == '+')
      {
        this.showPlus = true;
      }
      else if (paramString.charAt(m) == '0')
      {
        this.leadingZeroes = true;
      }
      else if (paramString.charAt(m) == '#')
      {
        this.alternate = true;
      }
      else
      {
        k = 2;
        m--;
      }
      m++;
    }
    while (k == 2) {
      if (m >= j)
      {
        k = 5;
      }
      else if (('0' <= paramString.charAt(m)) && (paramString.charAt(m) <= '9'))
      {
        this.width = (this.width * 10 + paramString.charAt(m) - 48);
        m++;
      }
      else if (paramString.charAt(m) == '.')
      {
        k = 3;
        this.precision = 0;
        m++;
      }
      else
      {
        k = 4;
      }
    }
    while (k == 3) {
      if (m >= j)
      {
        k = 5;
      }
      else if (('0' <= paramString.charAt(m)) && (paramString.charAt(m) <= '9'))
      {
        this.precision = (this.precision * 10 + paramString.charAt(m) - 48);
        m++;
      }
      else
      {
        k = 4;
      }
    }
    if (k == 4)
    {
      if (m >= j) {
        k = 5;
      } else {
        this.fmt = paramString.charAt(m);
      }
      m++;
    }
    if (m < j) {
      this.post = paramString.substring(m, j);
    }
  }
  
  public static void printf(String paramString, double paramDouble)
  {
    System.out.print(new Format(paramString).format(paramDouble));
  }
  
  public static void printf(String paramString, int paramInt)
  {
    System.out.print(new Format(paramString).format(paramInt));
  }
  
  public static void printf(String paramString, long paramLong)
  {
    System.out.print(new Format(paramString).format(paramLong));
  }
  
  public static void printf(String paramString, char paramChar)
  {
    System.out.print(new Format(paramString).format(paramChar));
  }
  
  public static void printf(String paramString1, String paramString2)
  {
    System.out.print(new Format(paramString1).format(paramString2));
  }
  
  public static int atoi(String paramString)
  {
    return (int)atol(paramString);
  }
  
  public static long atol(String paramString)
  {
    for (int i = 0; (i < paramString.length()) && (Character.isWhitespace(paramString.charAt(i))); i++) {}
    if ((i < paramString.length()) && (paramString.charAt(i) == '0'))
    {
      if ((i + 1 < paramString.length()) && ((paramString.charAt(i + 1) == 'x') || (paramString.charAt(i + 1) == 'X'))) {
        return parseLong(paramString.substring(i + 2), 16);
      }
      return parseLong(paramString, 8);
    }
    return parseLong(paramString, 10);
  }
  
  private static long parseLong(String paramString, int paramInt)
  {
    int i = 0;
    int j = 1;
    long l = 0L;
    while ((i < paramString.length()) && (Character.isWhitespace(paramString.charAt(i)))) {
      i++;
    }
    if ((i < paramString.length()) && (paramString.charAt(i) == '-'))
    {
      j = -1;
      i++;
    }
    else if ((i < paramString.length()) && (paramString.charAt(i) == '+'))
    {
      i++;
    }
    while (i < paramString.length())
    {
      int k = paramString.charAt(i);
      if ((48 <= k) && (k < 48 + paramInt)) {
        l = l * paramInt + k - 48L;
      } else if ((65 <= k) && (k < 65 + paramInt - 10)) {
        l = l * paramInt + k - 65L + 10L;
      } else if ((97 <= k) && (k < 97 + paramInt - 10)) {
        l = l * paramInt + k - 97L + 10L;
      } else {
        return l * j;
      }
      i++;
    }
    return l * j;
  }
  
  public static double atof(String paramString)
  {
    int i = 0;
    int j = 1;
    double d1 = 0.0D;
    double d2 = 0.0D;
    double d3 = 1.0D;
    int k = 0;
    while ((i < paramString.length()) && (Character.isWhitespace(paramString.charAt(i)))) {
      i++;
    }
    if ((i < paramString.length()) && (paramString.charAt(i) == '-'))
    {
      j = -1;
      i++;
    }
    else if ((i < paramString.length()) && (paramString.charAt(i) == '+'))
    {
      i++;
    }
    while (i < paramString.length())
    {
      int m = paramString.charAt(i);
      if ((48 <= m) && (m <= 57))
      {
        if (k == 0)
        {
          d1 = d1 * 10.0D + m - 48.0D;
        }
        else if (k == 1)
        {
          d3 /= 10.0D;
          d1 += d3 * (m - 48);
        }
      }
      else if (m == 46)
      {
        if (k == 0) {
          k = 1;
        } else {
          return j * d1;
        }
      }
      else
      {
        if ((m == 101) || (m == 69))
        {
          long l = (int)parseLong(paramString.substring(i + 1), 10);
          return j * d1 * Math.pow(10.0D, l);
        }
        return j * d1;
      }
      i++;
    }
    return j * d1;
  }
  
  public String format(double paramDouble)
  {
    if (this.precision < 0) {
      this.precision = 6;
    }
    int i = 1;
    if (paramDouble < 0.0D)
    {
      paramDouble = -paramDouble;
      i = -1;
    }
    String str;
    if (Double.isNaN(paramDouble)) {
      str = "NaN";
    } else if (paramDouble == (1.0D / 0.0D)) {
      str = "Inf";
    } else if (this.fmt == 'f') {
      str = fixedFormat(paramDouble);
    } else if ((this.fmt == 'e') || (this.fmt == 'E') || (this.fmt == 'g') || (this.fmt == 'G')) {
      str = expFormat(paramDouble);
    } else {
      throw new IllegalArgumentException();
    }
    return pad(sign(i, str));
  }
  
  public String format(int paramInt)
  {
    long l = paramInt;
    if ((this.fmt == 'o') || (this.fmt == 'x') || (this.fmt == 'X')) {
      l &= 0xFFFFFFFF;
    }
    return format(l);
  }
  
  public String format(long paramLong)
  {
    int i = 0;
    String str;
    if ((this.fmt == 'd') || (this.fmt == 'i'))
    {
      if (paramLong < 0L)
      {
        str = ("" + paramLong).substring(1);
        i = -1;
      }
      else
      {
        str = "" + paramLong;
        i = 1;
      }
    }
    else if (this.fmt == 'o') {
      str = convert(paramLong, 3, 7, "01234567");
    } else if (this.fmt == 'x') {
      str = convert(paramLong, 4, 15, "0123456789abcdef");
    } else if (this.fmt == 'X') {
      str = convert(paramLong, 4, 15, "0123456789ABCDEF");
    } else {
      throw new IllegalArgumentException();
    }
    return pad(sign(i, str));
  }
  
  public String format(char paramChar)
  {
    if (this.fmt != 'c') {
      throw new IllegalArgumentException();
    }
    String str = "" + paramChar;
    return pad(str);
  }
  
  public String format(String paramString)
  {
    if (this.fmt != 's') {
      throw new IllegalArgumentException();
    }
    if ((this.precision >= 0) && (this.precision < paramString.length())) {
      paramString = paramString.substring(0, this.precision);
    }
    return pad(paramString);
  }
  
  public static void main(String[] paramArrayOfString)
  {
    double d1 = 1.23456789012D;
    double d2 = 123.0D;
    double d3 = 1.2345E+030D;
    double d4 = 1.02D;
    double d5 = 1.234E-005D;
    double d6 = 10.0D;
    int i = 51966;
    printf("x = |%f|\n", d1);
    printf("u = |%20f|\n", d5);
    printf("x = |% .5f|\n", d1);
    printf("w = |%20.5f|\n", d4);
    printf("x = |%020.5f|\n", d1);
    printf("x = |%+20.5f|\n", d1);
    printf("x = |%+020.5f|\n", d1);
    printf("x = |% 020.5f|\n", d1);
    printf("y = |%#+20.5f|\n", d2);
    printf("y = |%-+20.5f|\n", d2);
    printf("z = |%20.5f|\n", d3);
    printf("x = |%e|\n", d1);
    printf("u = |%20e|\n", d5);
    printf("x = |% .5e|\n", d1);
    printf("w = |%20.5e|\n", d4);
    printf("x = |%020.5e|\n", d1);
    printf("x = |%+20.5e|\n", d1);
    printf("x = |%+020.5e|\n", d1);
    printf("x = |% 020.5e|\n", d1);
    printf("y = |%#+20.5e|\n", d2);
    printf("y = |%-+20.5e|\n", d2);
    printf("v = |%12.5e|\n", d6);
    printf("x = |%g|\n", d1);
    printf("z = |%g|\n", d3);
    printf("w = |%g|\n", d4);
    printf("u = |%g|\n", d5);
    printf("y = |%.2g|\n", d2);
    printf("y = |%#.2g|\n", d2);
    printf("d = |%d|\n", i);
    printf("d = |%20d|\n", i);
    printf("d = |%020d|\n", i);
    printf("d = |%+20d|\n", i);
    printf("d = |% 020d|\n", i);
    printf("d = |%-20d|\n", i);
    printf("d = |%20.8d|\n", i);
    printf("d = |%x|\n", i);
    printf("d = |%20X|\n", i);
    printf("d = |%#20x|\n", i);
    printf("d = |%020X|\n", i);
    printf("d = |%20.8x|\n", i);
    printf("d = |%o|\n", i);
    printf("d = |%020o|\n", i);
    printf("d = |%#20o|\n", i);
    printf("d = |%#020o|\n", i);
    printf("d = |%20.12o|\n", i);
    printf("s = |%-20s|\n", "Hello");
    printf("s = |%-20c|\n", '!');
    printf("|%i|\n", -9223372036854775808L);
    printf("|%6.2e|\n", 0.0D);
    printf("|%6.2g|\n", 0.0D);
    printf("|%6.2f|\n", 9.99D);
    printf("|%6.2f|\n", 9.999000000000001D);
    printf("|%.2f|\n", 1.999D);
    printf("|%6.0f|\n", 9.999000000000001D);
    printf("|%20.10s|\n", "Hello");
    i = -1;
    printf("-1 = |%X|\n", i);
    printf("100 = |%e|\n", 100.0D);
    printf("1/0 = |%f|\n", (1.0D / 0.0D));
    printf("-1/0 = |%e|\n", (-1.0D / 0.0D));
    printf("0/0 = |%g|\n", (0.0D / 0.0D));
  }
  
  private static String repeat(char paramChar, int paramInt)
  {
    if (paramInt <= 0) {
      return "";
    }
    StringBuffer localStringBuffer = new StringBuffer(paramInt);
    for (int i = 0; i < paramInt; i++) {
      localStringBuffer.append(paramChar);
    }
    return localStringBuffer.toString();
  }
  
  private static String convert(long paramLong, int paramInt1, int paramInt2, String paramString)
  {
    if (paramLong == 0L) {
      return "0";
    }
    String str = "";
    while (paramLong != 0L)
    {
      str = paramString.charAt((int)(paramLong & paramInt2)) + str;
      paramLong >>>= paramInt1;
    }
    return str;
  }
  
  private String pad(String paramString)
  {
    String str = repeat(' ', this.width - paramString.length());
    if (this.leftAlign) {
      return this.pre + paramString + str + this.post;
    }
    return this.pre + str + paramString + this.post;
  }
  
  private String sign(int paramInt, String paramString)
  {
    String str = "";
    if (paramInt < 0) {
      str = "-";
    } else if (paramInt > 0)
    {
      if (this.showPlus) {
        str = "+";
      } else if (this.showSpace) {
        str = " ";
      }
    }
    else if ((this.fmt == 'o') && (this.alternate) && (paramString.length() > 0) && (paramString.charAt(0) != '0')) {
      str = "0";
    } else if ((this.fmt == 'x') && (this.alternate)) {
      str = "0x";
    } else if ((this.fmt == 'X') && (this.alternate)) {
      str = "0X";
    }
    int i = 0;
    if (this.leadingZeroes) {
      i = this.width;
    } else if (((this.fmt == 'd') || (this.fmt == 'i') || (this.fmt == 'x') || (this.fmt == 'X') || (this.fmt == 'o')) && (this.precision > 0)) {
      i = this.precision;
    }
    return str + repeat('0', i - str.length() - paramString.length()) + paramString;
  }
  
  private String fixedFormat(double paramDouble)
  {
    int i = ((this.fmt == 'G') || (this.fmt == 'g')) && (!this.alternate) ? 1 : 0;
    if (paramDouble > 9.223372036854776E+018D) {
      return expFormat(paramDouble);
    }
    if (this.precision == 0) {
      return (paramDouble + 0.5D) + (i != 0 ? "" : ".");
    }
    long l1 = paramDouble;
    double d1 = paramDouble - l1;
    if ((d1 >= 1.0D) || (d1 < 0.0D)) {
      return expFormat(paramDouble);
    }
    double d2 = 1.0D;
    String str1 = "";
    for (int j = 1; (j <= this.precision) && (d2 <= 9.223372036854776E+018D); j++)
    {
      d2 *= 10.0D;
      str1 = str1 + "0";
    }
    long l2 = (d2 * d1 + 0.5D);
    if (l2 >= d2)
    {
      l2 = 0L;
      l1 += 1L;
    }
    String str2 = str1 + l2;
    str2 = "." + str2.substring(str2.length() - this.precision, str2.length());
    if (i != 0)
    {
      for (int k = str2.length() - 1; (k >= 0) && (str2.charAt(k) == '0'); k--) {}
      if ((k >= 0) && (str2.charAt(k) == '.')) {
        k--;
      }
      str2 = str2.substring(0, k + 1);
    }
    return l1 + str2;
  }
  
  private String expFormat(double paramDouble)
  {
    String str1 = "";
    int i = 0;
    double d1 = paramDouble;
    double d2 = 1.0D;
    if (paramDouble != 0.0D)
    {
      while (d1 >= 10.0D)
      {
        i++;
        d2 /= 10.0D;
        d1 /= 10.0D;
      }
      while (d1 < 1.0D)
      {
        i--;
        d2 *= 10.0D;
        d1 *= 10.0D;
      }
    }
    if (((this.fmt == 'g') || (this.fmt == 'G')) && (i >= -4) && (i < this.precision)) {
      return fixedFormat(paramDouble);
    }
    paramDouble *= d2;
    str1 = str1 + fixedFormat(paramDouble);
    if ((this.fmt == 'e') || (this.fmt == 'g')) {
      str1 = str1 + "e";
    } else {
      str1 = str1 + "E";
    }
    String str2 = "000";
    if (i >= 0)
    {
      str1 = str1 + "+";
      str2 = str2 + i;
    }
    else
    {
      str1 = str1 + "-";
      str2 = str2 + -i;
    }
    return str1 + str2.substring(str2.length() - 3, str2.length());
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     corejava.Format
 * JD-Core Version:    0.7.0.1
 */