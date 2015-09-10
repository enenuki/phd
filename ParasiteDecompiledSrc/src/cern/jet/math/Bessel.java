package cern.jet.math;

public class Bessel
  extends Constants
{
  protected static final double[] A_i0 = { -4.41534164647934E-018D, 3.330794518822238E-017D, -2.431279846547955E-016D, 1.715391285555133E-015D, -1.168533287799345E-014D, 7.676185498604936E-014D, -4.856446783111929E-013D, 2.95505266312964E-012D, -1.726826291441556E-011D, 9.675809035373237E-011D, -5.189795601635263E-010D, 2.659823724682387E-009D, -1.300025009986248E-008D, 6.046995022541919E-008D, -2.670793853940612E-007D, 1.117387539120104E-006D, -4.416738358458751E-006D, 1.64484480707289E-005D, -5.754195010082104E-005D, 0.0001885028850958417D, -0.0005763755745385824D, 0.001639475616941336D, -0.004324309995050576D, 0.010546460394595D, -0.02373741480589947D, 0.04930528423967071D, -0.09490109704804764D, 0.1716209015222088D, -0.304682672343198D, 0.6767952744094761D };
  protected static final double[] B_i0 = { -7.233180487874754E-018D, -4.830504485944182E-018D, 4.46562142029676E-017D, 3.461222867697461E-017D, -2.827623980516584E-016D, -3.425485619677219E-016D, 1.772560133056526E-015D, 3.811680669352622E-015D, -9.554846698828307E-015D, -4.150569347287222E-014D, 1.54008621752141E-014D, 3.852778382742143E-013D, 7.180124451383666E-013D, -1.794178531506806E-012D, -1.321581184044771E-011D, -3.149916527963242E-011D, 1.188914710784644E-011D, 4.94060238822497E-010D, 3.396232025708387E-009D, 2.266668990498178E-008D, 2.048918589469064E-007D, 2.891370520834757E-006D, 6.889758346916825E-005D, 0.003369116478255694D, 0.8044904110141088D };
  protected static final double[] A_i1 = { 2.777914112761046E-018D, -2.111421214358166E-017D, 1.553631957736201E-016D, -1.105596947735386E-015D, 7.600684294735408E-015D, -5.042185504727912E-014D, 3.223793365945575E-013D, -1.983974397764944E-012D, 1.17361862988909E-011D, -6.663489723502027E-011D, 3.625590281552117E-010D, -1.887249751722829E-009D, 9.381537386495773E-009D, -4.445059128796328E-008D, 2.003294753552135E-007D, -8.568720264695455E-007D, 3.470251308137679E-006D, -1.327316365603944E-005D, 4.781565107550054E-005D, -0.0001617608158258967D, 0.0005122859561685758D, -0.001513572450631253D, 0.004156422944312888D, -0.0105640848946262D, 0.02472644903062652D, -0.05294598120809499D, 0.1026436586898471D, -0.1764165183578341D, 0.2525871864436337D };
  protected static final double[] B_i1 = { 7.517296310842105E-018D, 4.414348323071708E-018D, -4.650305368489359E-017D, -3.209525921993424E-017D, 2.96262899764595E-016D, 3.308202310920929E-016D, -1.880354775510783E-015D, -3.814403072437008E-015D, 1.04202769841288E-014D, 4.272440016711951E-014D, -2.101541842772664E-014D, -4.083551111092197E-013D, -7.198551776245908E-013D, 2.03562854414709E-012D, 1.412580743661378E-011D, 3.252603583015488E-011D, -1.897495812350541E-011D, -5.589743462196584E-010D, -3.835380385964237E-009D, -2.63146884688952E-008D, -2.512236237870209E-007D, -3.882564808877691E-006D, -0.0001105889387626237D, -0.009761097491361469D, 0.7785762350182801D };
  protected static final double[] A_k0 = { 1.374465435613523E-016D, 4.25981614279661E-014D, 1.034969525763384E-011D, 1.904516377220209E-009D, 2.534791079026149E-007D, 2.286212103119452E-005D, 0.00126461541144693D, 0.0359799365153615D, 0.344289899924629D, -0.5353273932339028D };
  protected static final double[] B_k0 = { 5.300433772686263E-018D, -1.647580430152421E-017D, 5.210391505039027E-017D, -1.678231096805412E-016D, 5.512055978524319E-016D, -1.848593377343779E-015D, 6.340076477405071E-015D, -2.22751332699167E-014D, 8.032890775363575E-014D, -2.98009692317273E-013D, 1.140340588208475E-012D, -4.514597883373944E-012D, 1.855949114954718E-011D, -7.957489244477107E-011D, 3.577397281400301E-010D, -1.69753450938906E-009D, 8.574034017414225E-009D, -4.660489897687948E-008D, 2.766813639445015E-007D, -1.83175552271912E-006D, 1.39498137188765E-005D, -0.000128495495816278D, 0.001569883885730053D, -0.0314481013119645D, 2.440303082065956D };
  protected static final double[] A_k1 = { -7.023863479386288E-018D, -2.427449850519366E-015D, -6.66690169419933E-013D, -1.411488392633528E-010D, -2.213387630734726E-008D, -2.433406141565968E-006D, -0.000173028895751305D, -0.006975723859639864D, -0.1226111808226572D, -0.3531559607765449D, 1.525300227338948D };
  protected static final double[] B_k1 = { -5.756744483665017E-018D, 1.794050873147559E-017D, -5.689462558442859E-017D, 1.838093544366639E-016D, -6.057047248373319E-016D, 2.038703165624334E-015D, -7.019837090418314E-015D, 2.477154424481304E-014D, -8.976705182324994E-014D, 3.348419666078429E-013D, -1.289173960951029E-012D, 5.13963967348173E-012D, -2.129967838427568E-011D, 9.218315187605006E-011D, -4.190354759341897E-010D, 2.015049755197033E-009D, -1.03457624656781E-008D, 5.74108412545005E-008D, -3.501960603087813E-007D, 2.406484947837217E-006D, -1.936197974166083E-005D, 0.0001952155184713516D, -0.002857816859622779D, 0.1039237365768172D, 2.720626190484443D };
  
  public static double i0(double paramDouble)
    throws ArithmeticException
  {
    if (paramDouble < 0.0D) {
      paramDouble = -paramDouble;
    }
    if (paramDouble <= 8.0D)
    {
      double d = paramDouble / 2.0D - 2.0D;
      return Math.exp(paramDouble) * Arithmetic.chbevl(d, A_i0, 30);
    }
    return Math.exp(paramDouble) * Arithmetic.chbevl(32.0D / paramDouble - 2.0D, B_i0, 25) / Math.sqrt(paramDouble);
  }
  
  public static double i0e(double paramDouble)
    throws ArithmeticException
  {
    if (paramDouble < 0.0D) {
      paramDouble = -paramDouble;
    }
    if (paramDouble <= 8.0D)
    {
      double d = paramDouble / 2.0D - 2.0D;
      return Arithmetic.chbevl(d, A_i0, 30);
    }
    return Arithmetic.chbevl(32.0D / paramDouble - 2.0D, B_i0, 25) / Math.sqrt(paramDouble);
  }
  
  public static double i1(double paramDouble)
    throws ArithmeticException
  {
    double d2 = Math.abs(paramDouble);
    if (d2 <= 8.0D)
    {
      double d1 = d2 / 2.0D - 2.0D;
      d2 = Arithmetic.chbevl(d1, A_i1, 29) * d2 * Math.exp(d2);
    }
    else
    {
      d2 = Math.exp(d2) * Arithmetic.chbevl(32.0D / d2 - 2.0D, B_i1, 25) / Math.sqrt(d2);
    }
    if (paramDouble < 0.0D) {
      d2 = -d2;
    }
    return d2;
  }
  
  public static double i1e(double paramDouble)
    throws ArithmeticException
  {
    double d2 = Math.abs(paramDouble);
    if (d2 <= 8.0D)
    {
      double d1 = d2 / 2.0D - 2.0D;
      d2 = Arithmetic.chbevl(d1, A_i1, 29) * d2;
    }
    else
    {
      d2 = Arithmetic.chbevl(32.0D / d2 - 2.0D, B_i1, 25) / Math.sqrt(d2);
    }
    if (paramDouble < 0.0D) {
      d2 = -d2;
    }
    return d2;
  }
  
  public static double j0(double paramDouble)
    throws ArithmeticException
  {
    double d1;
    if ((d1 = Math.abs(paramDouble)) < 8.0D)
    {
      d2 = paramDouble * paramDouble;
      d3 = 57568490574.0D + d2 * (-13362590354.0D + d2 * (651619640.70000005D + d2 * (-11214424.18D + d2 * (77392.330170000001D + d2 * -184.9052456D))));
      d4 = 57568490411.0D + d2 * (1029532985.0D + d2 * (9494680.7180000003D + d2 * (59272.648529999999D + d2 * (267.85327119999999D + d2 * 1.0D))));
      return d3 / d4;
    }
    double d2 = 8.0D / d1;
    double d3 = d2 * d2;
    double d4 = d1 - 0.785398164D;
    double d5 = 1.0D + d3 * (-0.001098628627D + d3 * (2.734510407E-005D + d3 * (-2.073370639E-006D + d3 * 2.093887211E-007D)));
    double d6 = -0.01562499995D + d3 * (0.0001430488765D + d3 * (-6.911147651E-006D + d3 * (7.621095161000001E-007D - d3 * 9.349351519999999E-008D)));
    return Math.sqrt(0.636619772D / d1) * (Math.cos(d4) * d5 - d2 * Math.sin(d4) * d6);
  }
  
  public static double j1(double paramDouble)
    throws ArithmeticException
  {
    double d1;
    if ((d1 = Math.abs(paramDouble)) < 8.0D)
    {
      d2 = paramDouble * paramDouble;
      d3 = paramDouble * (72362614232.0D + d2 * (-7895059235.0D + d2 * (242396853.09999999D + d2 * (-2972611.4389999998D + d2 * (15704.482599999999D + d2 * -30.160366060000001D)))));
      d4 = 144725228442.0D + d2 * (2300535178.0D + d2 * (18583304.739999998D + d2 * (99447.433940000003D + d2 * (376.9991397D + d2 * 1.0D))));
      return d3 / d4;
    }
    double d5 = 8.0D / d1;
    double d6 = d1 - 2.356194491D;
    double d2 = d5 * d5;
    double d3 = 1.0D + d2 * (0.00183105D + d2 * (-3.516396496E-005D + d2 * (2.457520174E-006D + d2 * -2.40337019E-007D)));
    double d4 = 0.04687499995D + d2 * (-0.0002002690873D + d2 * (8.449199096E-006D + d2 * (-8.8228987E-007D + d2 * 1.05787412E-007D)));
    double d7 = Math.sqrt(0.636619772D / d1) * (Math.cos(d6) * d3 - d5 * Math.sin(d6) * d4);
    if (paramDouble < 0.0D) {
      d7 = -d7;
    }
    return d7;
  }
  
  public static double jn(int paramInt, double paramDouble)
    throws ArithmeticException
  {
    if (paramInt == 0) {
      return j0(paramDouble);
    }
    if (paramInt == 1) {
      return j1(paramDouble);
    }
    double d1 = Math.abs(paramDouble);
    if (d1 == 0.0D) {
      return 0.0D;
    }
    double d6;
    double d3;
    double d2;
    int i;
    double d4;
    double d7;
    if (d1 > paramInt)
    {
      d6 = 2.0D / d1;
      d3 = j0(d1);
      d2 = j1(d1);
      for (i = 1; i < paramInt; i++)
      {
        d4 = i * d6 * d2 - d3;
        d3 = d2;
        d2 = d4;
      }
      d7 = d2;
    }
    else
    {
      d6 = 2.0D / d1;
      int j = 2 * ((paramInt + (int)Math.sqrt(40.0D * paramInt)) / 2);
      int k = 0;
      d4 = d7 = d5 = 0.0D;
      d2 = 1.0D;
      for (i = j; i > 0; i--)
      {
        d3 = i * d6 * d2 - d4;
        d4 = d2;
        d2 = d3;
        if (Math.abs(d2) > 10000000000.0D)
        {
          d2 *= 1.0E-010D;
          d4 *= 1.0E-010D;
          d7 *= 1.0E-010D;
          d5 *= 1.0E-010D;
        }
        if (k != 0) {
          d5 += d2;
        }
        k = k == 0 ? 1 : 0;
        if (i == paramInt) {
          d7 = d4;
        }
      }
      double d5 = 2.0D * d5 - d2;
      d7 /= d5;
    }
    return (paramDouble < 0.0D) && (paramInt % 2 == 1) ? -d7 : d7;
  }
  
  public static double k0(double paramDouble)
    throws ArithmeticException
  {
    if (paramDouble <= 0.0D) {
      throw new ArithmeticException();
    }
    if (paramDouble <= 2.0D)
    {
      d1 = paramDouble * paramDouble - 2.0D;
      d1 = Arithmetic.chbevl(d1, A_k0, 10) - Math.log(0.5D * paramDouble) * i0(paramDouble);
      return d1;
    }
    double d2 = 8.0D / paramDouble - 2.0D;
    double d1 = Math.exp(-paramDouble) * Arithmetic.chbevl(d2, B_k0, 25) / Math.sqrt(paramDouble);
    return d1;
  }
  
  public static double k0e(double paramDouble)
    throws ArithmeticException
  {
    if (paramDouble <= 0.0D) {
      throw new ArithmeticException();
    }
    if (paramDouble <= 2.0D)
    {
      d = paramDouble * paramDouble - 2.0D;
      d = Arithmetic.chbevl(d, A_k0, 10) - Math.log(0.5D * paramDouble) * i0(paramDouble);
      return d * Math.exp(paramDouble);
    }
    double d = Arithmetic.chbevl(8.0D / paramDouble - 2.0D, B_k0, 25) / Math.sqrt(paramDouble);
    return d;
  }
  
  public static double k1(double paramDouble)
    throws ArithmeticException
  {
    double d2 = 0.5D * paramDouble;
    if (d2 <= 0.0D) {
      throw new ArithmeticException();
    }
    if (paramDouble <= 2.0D)
    {
      double d1 = paramDouble * paramDouble - 2.0D;
      d1 = Math.log(d2) * i1(paramDouble) + Arithmetic.chbevl(d1, A_k1, 11) / paramDouble;
      return d1;
    }
    return Math.exp(-paramDouble) * Arithmetic.chbevl(8.0D / paramDouble - 2.0D, B_k1, 25) / Math.sqrt(paramDouble);
  }
  
  public static double k1e(double paramDouble)
    throws ArithmeticException
  {
    if (paramDouble <= 0.0D) {
      throw new ArithmeticException();
    }
    if (paramDouble <= 2.0D)
    {
      double d = paramDouble * paramDouble - 2.0D;
      d = Math.log(0.5D * paramDouble) * i1(paramDouble) + Arithmetic.chbevl(d, A_k1, 11) / paramDouble;
      return d * Math.exp(paramDouble);
    }
    return Arithmetic.chbevl(8.0D / paramDouble - 2.0D, B_k1, 25) / Math.sqrt(paramDouble);
  }
  
  public static double kn(int paramInt, double paramDouble)
    throws ArithmeticException
  {
    int j;
    if (paramInt < 0) {
      j = -paramInt;
    } else {
      j = paramInt;
    }
    if (j > 31) {
      throw new ArithmeticException("Overflow");
    }
    if (paramDouble <= 0.0D) {
      throw new IllegalArgumentException();
    }
    double d3;
    double d9;
    if (paramDouble <= 9.550000000000001D)
    {
      d10 = 0.0D;
      d8 = 0.25D * paramDouble * paramDouble;
      d11 = 1.0D;
      d12 = 0.0D;
      double d14 = 1.0D;
      double d16 = 2.0D / paramDouble;
      if (j > 0)
      {
        d12 = -0.5772156649015329D;
        d1 = 1.0D;
        for (i = 1; i < j; i++)
        {
          d12 += 1.0D / d1;
          d1 += 1.0D;
          d11 *= d1;
        }
        d14 = d16;
        if (j == 1)
        {
          d10 = 1.0D / paramDouble;
        }
        else
        {
          d3 = d11 / j;
          double d2 = 1.0D;
          d7 = d3;
          d9 = -d8;
          double d5 = 1.0D;
          for (i = 1; i < j; i++)
          {
            d3 /= (j - i);
            d2 *= i;
            d5 *= d9;
            d6 = d3 * d5 / d2;
            d7 += d6;
            if (1.7976931348623157E+308D - Math.abs(d6) < Math.abs(d7)) {
              throw new ArithmeticException("Overflow");
            }
            if ((d16 > 1.0D) && (1.7976931348623157E+308D / d16 < d14)) {
              throw new ArithmeticException("Overflow");
            }
            d14 *= d16;
          }
          d7 *= 0.5D;
          d6 = Math.abs(d7);
          if ((d14 > 1.0D) && (1.7976931348623157E+308D / d14 < d6)) {
            throw new ArithmeticException("Overflow");
          }
          if ((d6 > 1.0D) && (1.7976931348623157E+308D / d6 < d14)) {
            throw new ArithmeticException("Overflow");
          }
          d10 = d7 * d14;
        }
      }
      double d15 = 2.0D * Math.log(0.5D * paramDouble);
      d13 = -0.5772156649015329D;
      if (j == 0)
      {
        d12 = d13;
        d6 = 1.0D;
      }
      else
      {
        d12 += 1.0D / j;
        d6 = 1.0D / d11;
      }
      d7 = (d13 + d12 - d15) * d6;
      d1 = 1.0D;
      do
      {
        d6 *= d8 / (d1 * (d1 + j));
        d13 += 1.0D / d1;
        d12 += 1.0D / (d1 + j);
        d7 += (d13 + d12 - d15) * d6;
        d1 += 1.0D;
      } while (Math.abs(d6 / d7) > 1.110223024625157E-016D);
      d7 = 0.5D * d7 / d14;
      if ((j & 0x1) > 0) {
        d7 = -d7;
      }
      d10 += d7;
      return d10;
    }
    if (paramDouble > 709.78271289338397D) {
      throw new ArithmeticException("Underflow");
    }
    double d1 = j;
    double d12 = 4.0D * d1 * d1;
    double d13 = 1.0D;
    double d8 = 8.0D * paramDouble;
    double d11 = 1.0D;
    double d6 = 1.0D;
    double d7 = d6;
    double d4 = 1.7976931348623157E+308D;
    int i = 0;
    do
    {
      d9 = d12 - d13 * d13;
      d6 = d6 * d9 / (d11 * d8);
      d3 = Math.abs(d6);
      if ((i >= j) && (d3 > d4))
      {
        d10 = Math.exp(-paramDouble) * Math.sqrt(3.141592653589793D / (2.0D * paramDouble)) * d7;
        return d10;
      }
      d4 = d3;
      d7 += d6;
      d11 += 1.0D;
      d13 += 2.0D;
      i++;
    } while (Math.abs(d6 / d7) > 1.110223024625157E-016D);
    double d10 = Math.exp(-paramDouble) * Math.sqrt(3.141592653589793D / (2.0D * paramDouble)) * d7;
    return d10;
  }
  
  public static double y0(double paramDouble)
    throws ArithmeticException
  {
    if (paramDouble < 8.0D)
    {
      d1 = paramDouble * paramDouble;
      d2 = -2957821389.0D + d1 * (7062834065.0D + d1 * (-512359803.60000002D + d1 * (10879881.289999999D + d1 * (-86327.92757D + d1 * 228.46227329999999D))));
      d3 = 40076544269.0D + d1 * (745249964.79999995D + d1 * (7189466.4380000001D + d1 * (47447.2647D + d1 * (226.10302440000001D + d1 * 1.0D))));
      return d2 / d3 + 0.636619772D * j0(paramDouble) * Math.log(paramDouble);
    }
    double d1 = 8.0D / paramDouble;
    double d2 = d1 * d1;
    double d3 = paramDouble - 0.785398164D;
    double d4 = 1.0D + d2 * (-0.001098628627D + d2 * (2.734510407E-005D + d2 * (-2.073370639E-006D + d2 * 2.093887211E-007D)));
    double d5 = -0.01562499995D + d2 * (0.0001430488765D + d2 * (-6.911147651E-006D + d2 * (7.621095161000001E-007D + d2 * -9.34945152E-008D)));
    return Math.sqrt(0.636619772D / paramDouble) * (Math.sin(d3) * d4 + d1 * Math.cos(d3) * d5);
  }
  
  public static double y1(double paramDouble)
    throws ArithmeticException
  {
    if (paramDouble < 8.0D)
    {
      d1 = paramDouble * paramDouble;
      d2 = paramDouble * (-4900604943000.0D + d1 * (1275274390000.0D + d1 * (-51534381390.0D + d1 * (734926455.10000002D + d1 * (-4237922.7259999998D + d1 * 8511.9379349999999D)))));
      d3 = 24995805700000.0D + d1 * (424441966400.0D + d1 * (3733650367.0D + d1 * (22459040.02D + d1 * (102042.605D + d1 * (354.96328849999998D + d1)))));
      return d2 / d3 + 0.636619772D * (j1(paramDouble) * Math.log(paramDouble) - 1.0D / paramDouble);
    }
    double d1 = 8.0D / paramDouble;
    double d2 = d1 * d1;
    double d3 = paramDouble - 2.356194491D;
    double d4 = 1.0D + d2 * (0.00183105D + d2 * (-3.516396496E-005D + d2 * (2.457520174E-006D + d2 * -2.40337019E-007D)));
    double d5 = 0.04687499995D + d2 * (-0.0002002690873D + d2 * (8.449199096E-006D + d2 * (-8.8228987E-007D + d2 * 1.05787412E-007D)));
    return Math.sqrt(0.636619772D / paramDouble) * (Math.sin(d3) * d4 + d1 * Math.cos(d3) * d5);
  }
  
  public static double yn(int paramInt, double paramDouble)
    throws ArithmeticException
  {
    if (paramInt == 0) {
      return y0(paramDouble);
    }
    if (paramInt == 1) {
      return y1(paramDouble);
    }
    double d4 = 2.0D / paramDouble;
    double d1 = y1(paramDouble);
    double d2 = y0(paramDouble);
    for (int i = 1; i < paramInt; i++)
    {
      double d3 = i * d4 * d1 - d2;
      d2 = d1;
      d1 = d3;
    }
    return d1;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.math.Bessel
 * JD-Core Version:    0.7.0.1
 */