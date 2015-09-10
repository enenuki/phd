package cern.jet.math;

public class Arithmetic
  extends Constants
{
  private static final double[] stirlingCorrection = { 0.0D, 0.08106146679532726D, 0.0413406959554093D, 0.02767792568499834D, 0.0207906721037651D, 0.0166446911898212D, 0.0138761288230708D, 0.0118967099458918D, 0.01041126526197209D, 0.009255462182712733D, 0.008330563433362871D, 0.007573675487951841D, 0.00694284010720953D, 0.006408994188004207D, 0.005951370112758848D, 0.005554733551962801D, 0.00520765591960964D, 0.004901395948434738D, 0.004629153749334029D, 0.004385560249232324D, 0.004166319691996922D, 0.00396795421864086D, 0.00378761806844443D, 0.0036229602246831D, 0.00347202138297877D, 0.00333315563672809D, 0.00320497022805504D, 0.00308627868260878D, 0.00297606398355041D, 0.00287344936235247D, 0.00277767492975269D };
  protected static final double[] logFactorials = { 0.0D, 0.0D, 0.6931471805599453D, 1.791759469228055D, 3.178053830347946D, 4.787491742782046D, 6.579251212010101D, 8.525161361065415D, 10.604602902745251D, 12.801827480081469D, 15.104412573075516D, 17.502307845873887D, 19.987214495661885D, 22.552163853123425D, 25.19122118273868D, 27.89927138384089D, 30.671860106080672D, 33.505073450136891D, 36.395445208033053D, 39.339884187199495D, 42.335616460753485D, 45.380138898476908D, 48.471181351835227D, 51.606675567764377D, 54.784729398112319D, 58.003605222980518D, 61.261701761002001D, 64.557538627006338D, 67.88974313718154D, 71.257038967168015D };
  protected static final long[] longFactorials = { 1L, 1L, 2L, 6L, 24L, 120L, 720L, 5040L, 40320L, 362880L, 3628800L, 39916800L, 479001600L, 6227020800L, 87178291200L, 1307674368000L, 20922789888000L, 355687428096000L, 6402373705728000L, 121645100408832000L, 2432902008176640000L };
  protected static final double[] doubleFactorials = { 5.109094217170944E+019D, 1.124000727777608E+021D, 2.585201673888498E+022D, 6.204484017332394E+023D, 1.551121004333098E+025D, 4.032914611266057E+026D, 1.088886945041835E+028D, 3.048883446117138E+029D, 8.841761993739701E+030D, 2.652528598121911E+032D, 8.222838654177924E+033D, 2.631308369336936E+035D, 8.68331761881189E+036D, 2.952327990396041E+038D, 1.033314796638614E+040D, 3.719933267899013E+041D, 1.376375309122635E+043D, 5.23022617466601E+044D, 2.039788208119745E+046D, 8.15915283247898E+047D, 3.34525266131638E+049D, 1.40500611775288E+051D, 6.041526306337385E+052D, 2.65827157478845E+054D, 1.196222208654802E+056D, 5.502622159812089E+057D, 2.586232415111683E+059D, 1.241391559253607E+061D, 6.082818640342679E+062D, 3.041409320171338E+064D, 1.551118753287382E+066D, 8.06581751709439E+067D, 4.274883284060024E+069D, 2.308436973392413E+071D, 1.269640335365826E+073D, 7.109985878048632E+074D, 4.052691950487723E+076D, 2.350561331282879E+078D, 1.386831185456898E+080D, 8.32098711274139E+081D, 5.075802138772246E+083D, 3.146997326038794E+085D, 1.98260831540444E+087D, 1.268869321858841E+089D, 8.247650592082472E+090D, 5.443449390774432E+092D, 3.647111091818871E+094D, 2.48003554243683E+096D, 1.711224524281413E+098D, 1.197857166996989E+100D, 8.504785885678624E+101D, 6.123445837688612E+103D, 4.470115461512686E+105D, 3.307885441519387E+107D, 2.48091408113954E+109D, 1.885494701666051E+111D, 1.451830920282859E+113D, 1.13242811782063E+115D, 8.94618213078298E+116D, 7.15694570462638E+118D, 5.797126020747369E+120D, 4.753643337012844E+122D, 3.94552396972066E+124D, 3.314240134565354E+126D, 2.817104114380549E+128D, 2.422709538367274E+130D, 2.107757298379527E+132D, 1.854826422573984E+134D, 1.650795516090847E+136D, 1.485715964481761E+138D, 1.352001527678403E+140D, 1.243841405464131E+142D, 1.156772507081641E+144D, 1.087366156656743E+146D, 1.032997848823906E+148D, 9.916779348709491E+149D, 9.619275968248216E+151D, 9.426890448883248E+153D, 9.332621544394415E+155D, 9.332621544394418E+157D, 9.42594775983836E+159D, 9.614466715035125E+161D, 9.902900716486178E+163D, 1.029901674514563E+166D, 1.081396758240291E+168D, 1.146280563734709E+170D, 1.226520203196137E+172D, 1.324641819451829E+174D, 1.443859583202494E+176D, 1.588245541522742E+178D, 1.762952551090246E+180D, 1.974506857221075E+182D, 2.231192748659814E+184D, 2.543559733472186E+186D, 2.925093693493014E+188D, 3.393108684451899E+190D, 3.96993716080872E+192D, 4.68452584975429E+194D, 5.574585761207606E+196D, 6.689502913449135E+198D, 8.094298525273444E+200D, 9.875044200833601E+202D, 1.214630436702533E+205D, 1.506141741511141E+207D, 1.882677176888926E+209D, 2.372173242880048E+211D, 3.012660018457662E+213D, 3.856204823625808E+215D, 4.974504222477288E+217D, 6.466855489220473E+219D, 8.471580690878813E+221D, 1.118248651196004E+224D, 1.487270706090685E+226D, 1.99294274616152E+228D, 2.690472707318049E+230D, 3.659042881952548E+232D, 5.012888748274988E+234D, 6.917786472619482E+236D, 9.615723196941089E+238D, 1.346201247571752E+241D, 1.898143759076171E+243D, 2.695364137888163E+245D, 3.854370717180069E+247D, 5.550293832739308E+249D, 8.047926057471989E+251D, 1.174997204390911E+254D, 1.72724589045464E+256D, 2.556323917872864E+258D, 3.808922637630569E+260D, 5.713383956445858E+262D, 8.627209774233244E+264D, 1.311335885683453E+267D, 2.006343905095684E+269D, 3.089769613847352E+271D, 4.789142901463393E+273D, 7.471062926282892E+275D, 1.172956879426413E+278D, 1.853271869493735E+280D, 2.946702272495036E+282D, 4.714723635992061E+284D, 7.590705053947223E+286D, 1.229694218739449E+289D, 2.004401576545303E+291D, 3.287218585534299E+293D, 5.423910666131583E+295D, 9.003691705778434E+297D, 1.503616514864998E+300D, 2.526075744973199E+302D, 4.269068009004706E+304D, 7.257415615308004E+306D };
  
  public static double binomial(double paramDouble, long paramLong)
  {
    if (paramLong < 0L) {
      return 0.0D;
    }
    if (paramLong == 0L) {
      return 1.0D;
    }
    if (paramLong == 1L) {
      return paramDouble;
    }
    double d1 = paramDouble - paramLong + 1.0D;
    double d2 = 1.0D;
    double d3 = 1.0D;
    long l = paramLong;
    while (l-- > 0L) {
      d3 *= d1++ / d2++;
    }
    return d3;
  }
  
  public static double binomial(long paramLong1, long paramLong2)
  {
    if (paramLong2 < 0L) {
      return 0.0D;
    }
    if ((paramLong2 == 0L) || (paramLong2 == paramLong1)) {
      return 1.0D;
    }
    if ((paramLong2 == 1L) || (paramLong2 == paramLong1 - 1L)) {
      return paramLong1;
    }
    if (paramLong1 > paramLong2)
    {
      int i = longFactorials.length + doubleFactorials.length;
      if (paramLong1 < i)
      {
        double d1 = factorial((int)paramLong1);
        double d2 = factorial((int)paramLong2);
        double d4 = factorial((int)(paramLong1 - paramLong2));
        double d5 = d4 * d2;
        if (d5 != (1.0D / 0.0D)) {
          return d1 / d5;
        }
      }
      if (paramLong2 > paramLong1 / 2L) {
        paramLong2 = paramLong1 - paramLong2;
      }
    }
    long l1 = paramLong1 - paramLong2 + 1L;
    long l2 = 1L;
    double d3 = 1.0D;
    long l3 = paramLong2;
    while (l3-- > 0L) {
      d3 *= l1++ / l2++;
    }
    return d3;
  }
  
  public static long ceil(double paramDouble)
  {
    return Math.round(Math.ceil(paramDouble));
  }
  
  public static double chbevl(double paramDouble, double[] paramArrayOfDouble, int paramInt)
    throws ArithmeticException
  {
    int i = 0;
    double d1 = paramArrayOfDouble[(i++)];
    double d2 = 0.0D;
    int j = paramInt - 1;
    double d3;
    do
    {
      d3 = d2;
      d2 = d1;
      d1 = paramDouble * d2 - d3 + paramArrayOfDouble[(i++)];
      j--;
    } while (j > 0);
    return 0.5D * (d1 - d3);
  }
  
  private static long fac1(int paramInt)
  {
    long l1 = paramInt;
    if (paramInt < 0) {
      l1 = Math.abs(paramInt);
    }
    if (l1 > longFactorials.length) {
      throw new IllegalArgumentException("Overflow");
    }
    for (long l2 = 1L; l1 > 1L; l2 *= l1--) {}
    if (paramInt < 0) {
      return -l2;
    }
    return l2;
  }
  
  private static double fac2(int paramInt)
  {
    long l = paramInt;
    if (paramInt < 0) {
      l = Math.abs(paramInt);
    }
    for (double d = 1.0D; l > 1L; d *= l--) {}
    if (paramInt < 0) {
      return -d;
    }
    return d;
  }
  
  public static double factorial(int paramInt)
  {
    if (paramInt < 0) {
      throw new IllegalArgumentException();
    }
    int i = longFactorials.length;
    if (paramInt < i) {
      return longFactorials[paramInt];
    }
    int j = doubleFactorials.length;
    if (paramInt < i + j) {
      return doubleFactorials[(paramInt - i)];
    }
    return (1.0D / 0.0D);
  }
  
  public static long floor(double paramDouble)
  {
    return Math.round(Math.floor(paramDouble));
  }
  
  public static double log(double paramDouble1, double paramDouble2)
  {
    return Math.log(paramDouble2) / Math.log(paramDouble1);
  }
  
  public static double log10(double paramDouble)
  {
    return Math.log(paramDouble) * 0.4342944819032518D;
  }
  
  public static double log2(double paramDouble)
  {
    return Math.log(paramDouble) * 1.442695040888963D;
  }
  
  public static double logFactorial(int paramInt)
  {
    if (paramInt >= 30)
    {
      double d1 = 1.0D / paramInt;
      double d2 = d1 * d1;
      return (paramInt + 0.5D) * Math.log(paramInt) - paramInt + 0.9189385332046728D + d1 * (0.08333333333333333D + d2 * (-0.002777777777777778D + d2 * (0.0007936507936507937D + d2 * -0.0005952380952380953D)));
    }
    return logFactorials[paramInt];
  }
  
  public static long longFactorial(int paramInt)
    throws IllegalArgumentException
  {
    if (paramInt < 0) {
      throw new IllegalArgumentException("Negative k");
    }
    if (paramInt < longFactorials.length) {
      return longFactorials[paramInt];
    }
    throw new IllegalArgumentException("Overflow");
  }
  
  public static double stirlingCorrection(int paramInt)
  {
    if (paramInt > 30)
    {
      double d1 = 1.0D / paramInt;
      double d2 = d1 * d1;
      return d1 * (0.08333333333333333D + d2 * (-0.002777777777777778D + d2 * (0.0007936507936507937D + d2 * -0.0005952380952380953D)));
    }
    return stirlingCorrection[paramInt];
  }
  
  private static long xlongBinomial(long paramLong1, long paramLong2)
  {
    return Math.round(binomial(paramLong1, paramLong2));
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.math.Arithmetic
 * JD-Core Version:    0.7.0.1
 */