package cern.jet.math;

import cern.colt.Timer;
import cern.colt.function.DoubleDoubleFunction;
import cern.colt.function.DoubleDoubleProcedure;
import cern.colt.function.DoubleFunction;
import cern.colt.function.DoubleProcedure;
import cern.jet.random.engine.MersenneTwister;
import java.io.PrintStream;
import java.util.Date;

public class Functions
{
  public static final Functions functions = new Functions();
  public static final DoubleFunction abs = new DoubleFunction()
  {
    public final double apply(double paramAnonymousDouble)
    {
      return Math.abs(paramAnonymousDouble);
    }
  };
  public static final DoubleFunction acos = new DoubleFunction()
  {
    public final double apply(double paramAnonymousDouble)
    {
      return Math.acos(paramAnonymousDouble);
    }
  };
  public static final DoubleFunction asin = new DoubleFunction()
  {
    public final double apply(double paramAnonymousDouble)
    {
      return Math.asin(paramAnonymousDouble);
    }
  };
  public static final DoubleFunction atan = new DoubleFunction()
  {
    public final double apply(double paramAnonymousDouble)
    {
      return Math.atan(paramAnonymousDouble);
    }
  };
  public static final DoubleFunction ceil = new DoubleFunction()
  {
    public final double apply(double paramAnonymousDouble)
    {
      return Math.ceil(paramAnonymousDouble);
    }
  };
  public static final DoubleFunction cos = new DoubleFunction()
  {
    public final double apply(double paramAnonymousDouble)
    {
      return Math.cos(paramAnonymousDouble);
    }
  };
  public static final DoubleFunction exp = new DoubleFunction()
  {
    public final double apply(double paramAnonymousDouble)
    {
      return Math.exp(paramAnonymousDouble);
    }
  };
  public static final DoubleFunction floor = new DoubleFunction()
  {
    public final double apply(double paramAnonymousDouble)
    {
      return Math.floor(paramAnonymousDouble);
    }
  };
  public static final DoubleFunction identity = new DoubleFunction()
  {
    public final double apply(double paramAnonymousDouble)
    {
      return paramAnonymousDouble;
    }
  };
  public static final DoubleFunction inv = new DoubleFunction()
  {
    public final double apply(double paramAnonymousDouble)
    {
      return 1.0D / paramAnonymousDouble;
    }
  };
  public static final DoubleFunction log = new DoubleFunction()
  {
    public final double apply(double paramAnonymousDouble)
    {
      return Math.log(paramAnonymousDouble);
    }
  };
  public static final DoubleFunction log2 = new DoubleFunction()
  {
    public final double apply(double paramAnonymousDouble)
    {
      return Math.log(paramAnonymousDouble) * 1.442695040888963D;
    }
  };
  public static final DoubleFunction neg = new DoubleFunction()
  {
    public final double apply(double paramAnonymousDouble)
    {
      return -paramAnonymousDouble;
    }
  };
  public static final DoubleFunction rint = new DoubleFunction()
  {
    public final double apply(double paramAnonymousDouble)
    {
      return Math.rint(paramAnonymousDouble);
    }
  };
  public static final DoubleFunction sign = new DoubleFunction()
  {
    public final double apply(double paramAnonymousDouble)
    {
      return paramAnonymousDouble > 0.0D ? 1.0D : paramAnonymousDouble < 0.0D ? -1.0D : 0.0D;
    }
  };
  public static final DoubleFunction sin = new DoubleFunction()
  {
    public final double apply(double paramAnonymousDouble)
    {
      return Math.sin(paramAnonymousDouble);
    }
  };
  public static final DoubleFunction sqrt = new DoubleFunction()
  {
    public final double apply(double paramAnonymousDouble)
    {
      return Math.sqrt(paramAnonymousDouble);
    }
  };
  public static final DoubleFunction square = new DoubleFunction()
  {
    public final double apply(double paramAnonymousDouble)
    {
      return paramAnonymousDouble * paramAnonymousDouble;
    }
  };
  public static final DoubleFunction tan = new DoubleFunction()
  {
    public final double apply(double paramAnonymousDouble)
    {
      return Math.tan(paramAnonymousDouble);
    }
  };
  public static final DoubleDoubleFunction atan2 = new DoubleDoubleFunction()
  {
    public final double apply(double paramAnonymousDouble1, double paramAnonymousDouble2)
    {
      return Math.atan2(paramAnonymousDouble1, paramAnonymousDouble2);
    }
  };
  public static final DoubleDoubleFunction compare = new DoubleDoubleFunction()
  {
    public final double apply(double paramAnonymousDouble1, double paramAnonymousDouble2)
    {
      return paramAnonymousDouble1 > paramAnonymousDouble2 ? 1.0D : paramAnonymousDouble1 < paramAnonymousDouble2 ? -1.0D : 0.0D;
    }
  };
  public static final DoubleDoubleFunction div = new DoubleDoubleFunction()
  {
    public final double apply(double paramAnonymousDouble1, double paramAnonymousDouble2)
    {
      return paramAnonymousDouble1 / paramAnonymousDouble2;
    }
  };
  public static final DoubleDoubleFunction equals = new DoubleDoubleFunction()
  {
    public final double apply(double paramAnonymousDouble1, double paramAnonymousDouble2)
    {
      return paramAnonymousDouble1 == paramAnonymousDouble2 ? 1.0D : 0.0D;
    }
  };
  public static final DoubleDoubleFunction greater = new DoubleDoubleFunction()
  {
    public final double apply(double paramAnonymousDouble1, double paramAnonymousDouble2)
    {
      return paramAnonymousDouble1 > paramAnonymousDouble2 ? 1.0D : 0.0D;
    }
  };
  public static final DoubleDoubleFunction IEEEremainder = new DoubleDoubleFunction()
  {
    public final double apply(double paramAnonymousDouble1, double paramAnonymousDouble2)
    {
      return Math.IEEEremainder(paramAnonymousDouble1, paramAnonymousDouble2);
    }
  };
  public static final DoubleDoubleProcedure isEqual = new DoubleDoubleProcedure()
  {
    public final boolean apply(double paramAnonymousDouble1, double paramAnonymousDouble2)
    {
      return paramAnonymousDouble1 == paramAnonymousDouble2;
    }
  };
  public static final DoubleDoubleProcedure isLess = new DoubleDoubleProcedure()
  {
    public final boolean apply(double paramAnonymousDouble1, double paramAnonymousDouble2)
    {
      return paramAnonymousDouble1 < paramAnonymousDouble2;
    }
  };
  public static final DoubleDoubleProcedure isGreater = new DoubleDoubleProcedure()
  {
    public final boolean apply(double paramAnonymousDouble1, double paramAnonymousDouble2)
    {
      return paramAnonymousDouble1 > paramAnonymousDouble2;
    }
  };
  public static final DoubleDoubleFunction less = new DoubleDoubleFunction()
  {
    public final double apply(double paramAnonymousDouble1, double paramAnonymousDouble2)
    {
      return paramAnonymousDouble1 < paramAnonymousDouble2 ? 1.0D : 0.0D;
    }
  };
  public static final DoubleDoubleFunction lg = new DoubleDoubleFunction()
  {
    public final double apply(double paramAnonymousDouble1, double paramAnonymousDouble2)
    {
      return Math.log(paramAnonymousDouble1) / Math.log(paramAnonymousDouble2);
    }
  };
  public static final DoubleDoubleFunction max = new DoubleDoubleFunction()
  {
    public final double apply(double paramAnonymousDouble1, double paramAnonymousDouble2)
    {
      return Math.max(paramAnonymousDouble1, paramAnonymousDouble2);
    }
  };
  public static final DoubleDoubleFunction min = new DoubleDoubleFunction()
  {
    public final double apply(double paramAnonymousDouble1, double paramAnonymousDouble2)
    {
      return Math.min(paramAnonymousDouble1, paramAnonymousDouble2);
    }
  };
  public static final DoubleDoubleFunction minus = plusMult(-1.0D);
  public static final DoubleDoubleFunction mod = new DoubleDoubleFunction()
  {
    public final double apply(double paramAnonymousDouble1, double paramAnonymousDouble2)
    {
      return paramAnonymousDouble1 % paramAnonymousDouble2;
    }
  };
  public static final DoubleDoubleFunction mult = new DoubleDoubleFunction()
  {
    public final double apply(double paramAnonymousDouble1, double paramAnonymousDouble2)
    {
      return paramAnonymousDouble1 * paramAnonymousDouble2;
    }
  };
  public static final DoubleDoubleFunction plus = plusMult(1.0D);
  public static final DoubleDoubleFunction plusAbs = new DoubleDoubleFunction()
  {
    public final double apply(double paramAnonymousDouble1, double paramAnonymousDouble2)
    {
      return Math.abs(paramAnonymousDouble1) + Math.abs(paramAnonymousDouble2);
    }
  };
  public static final DoubleDoubleFunction pow = new DoubleDoubleFunction()
  {
    public final double apply(double paramAnonymousDouble1, double paramAnonymousDouble2)
    {
      return Math.pow(paramAnonymousDouble1, paramAnonymousDouble2);
    }
  };
  
  public static DoubleFunction between(double paramDouble1, double paramDouble2)
  {
    new DoubleFunction()
    {
      private final double val$from;
      private final double val$to;
      
      public final double apply(double paramAnonymousDouble)
      {
        return (this.val$from <= paramAnonymousDouble) && (paramAnonymousDouble <= this.val$to) ? 1.0D : 0.0D;
      }
    };
  }
  
  public static DoubleFunction bindArg1(DoubleDoubleFunction paramDoubleDoubleFunction, double paramDouble)
  {
    new DoubleFunction()
    {
      private final DoubleDoubleFunction val$function;
      private final double val$c;
      
      public final double apply(double paramAnonymousDouble)
      {
        return this.val$function.apply(this.val$c, paramAnonymousDouble);
      }
    };
  }
  
  public static DoubleFunction bindArg2(DoubleDoubleFunction paramDoubleDoubleFunction, double paramDouble)
  {
    new DoubleFunction()
    {
      private final DoubleDoubleFunction val$function;
      private final double val$c;
      
      public final double apply(double paramAnonymousDouble)
      {
        return this.val$function.apply(paramAnonymousDouble, this.val$c);
      }
    };
  }
  
  public static DoubleDoubleFunction chain(DoubleDoubleFunction paramDoubleDoubleFunction, DoubleFunction paramDoubleFunction1, DoubleFunction paramDoubleFunction2)
  {
    new DoubleDoubleFunction()
    {
      private final DoubleDoubleFunction val$f;
      private final DoubleFunction val$g;
      private final DoubleFunction val$h;
      
      public final double apply(double paramAnonymousDouble1, double paramAnonymousDouble2)
      {
        return this.val$f.apply(this.val$g.apply(paramAnonymousDouble1), this.val$h.apply(paramAnonymousDouble2));
      }
    };
  }
  
  public static DoubleDoubleFunction chain(DoubleFunction paramDoubleFunction, DoubleDoubleFunction paramDoubleDoubleFunction)
  {
    new DoubleDoubleFunction()
    {
      private final DoubleFunction val$g;
      private final DoubleDoubleFunction val$h;
      
      public final double apply(double paramAnonymousDouble1, double paramAnonymousDouble2)
      {
        return this.val$g.apply(this.val$h.apply(paramAnonymousDouble1, paramAnonymousDouble2));
      }
    };
  }
  
  public static DoubleFunction chain(DoubleFunction paramDoubleFunction1, DoubleFunction paramDoubleFunction2)
  {
    new DoubleFunction()
    {
      private final DoubleFunction val$g;
      private final DoubleFunction val$h;
      
      public final double apply(double paramAnonymousDouble)
      {
        return this.val$g.apply(this.val$h.apply(paramAnonymousDouble));
      }
    };
  }
  
  public static DoubleFunction compare(double paramDouble)
  {
    new DoubleFunction()
    {
      private final double val$b;
      
      public final double apply(double paramAnonymousDouble)
      {
        return paramAnonymousDouble > this.val$b ? 1.0D : paramAnonymousDouble < this.val$b ? -1.0D : 0.0D;
      }
    };
  }
  
  public static DoubleFunction constant(double paramDouble)
  {
    new DoubleFunction()
    {
      private final double val$c;
      
      public final double apply(double paramAnonymousDouble)
      {
        return this.val$c;
      }
    };
  }
  
  public static void demo1()
  {
    Functions localFunctions = functions;
    double d1 = 0.5D;
    double d2 = 0.2D;
    double d3 = Math.sin(d1) + Math.pow(Math.cos(d2), 2.0D);
    System.out.println(d3);
    DoubleDoubleFunction localDoubleDoubleFunction = chain(plus, sin, chain(square, cos));
    System.out.println(localDoubleDoubleFunction.apply(d1, d2));
    DoubleDoubleFunction local45 = new DoubleDoubleFunction()
    {
      public final double apply(double paramAnonymousDouble1, double paramAnonymousDouble2)
      {
        return Math.sin(paramAnonymousDouble1) + Math.pow(Math.cos(paramAnonymousDouble2), 2.0D);
      }
    };
    System.out.println(local45.apply(d1, d2));
    DoubleFunction localDoubleFunction1 = plus(3.0D);
    DoubleFunction localDoubleFunction2 = plus(4.0D);
    System.out.println(localDoubleFunction1.apply(0.0D));
    System.out.println(localDoubleFunction2.apply(0.0D));
  }
  
  public static void demo2(int paramInt)
  {
    Functions localFunctions = functions;
    System.out.println("\n\n");
    double d1 = 0.0D;
    double d2 = 0.0D;
    double d3 = Math.abs(Math.sin(d1) + Math.pow(Math.cos(d2), 2.0D));
    System.out.println(d3);
    DoubleDoubleFunction localDoubleDoubleFunction = chain(abs, chain(plus, sin, chain(square, cos)));
    System.out.println(localDoubleDoubleFunction.apply(d1, d2));
    DoubleDoubleFunction local46 = new DoubleDoubleFunction()
    {
      public final double apply(double paramAnonymousDouble1, double paramAnonymousDouble2)
      {
        return Math.abs(Math.sin(paramAnonymousDouble1) + Math.pow(Math.cos(paramAnonymousDouble2), 2.0D));
      }
    };
    System.out.println(local46.apply(d1, d2));
    Timer localTimer1 = new Timer().start();
    d1 = 0.0D;
    d2 = 0.0D;
    double d4 = 0.0D;
    int i = paramInt;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      d4 += d1;
      d1 += 1.0D;
      d2 += 1.0D;
    }
    localTimer1.stop().display();
    System.out.println("empty sum=" + d4);
    Timer localTimer2 = new Timer().start();
    d1 = 0.0D;
    d2 = 0.0D;
    d4 = 0.0D;
    int j = paramInt;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      d4 += Math.abs(Math.sin(d1) + Math.pow(Math.cos(d2), 2.0D));
      d1 += 1.0D;
      d2 += 1.0D;
    }
    localTimer2.stop().display();
    System.out.println("evals / sec = " + paramInt / localTimer2.minus(localTimer1).seconds());
    System.out.println("sum=" + d4);
    localTimer2.reset().start();
    d1 = 0.0D;
    d2 = 0.0D;
    d4 = 0.0D;
    j = paramInt;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      d4 += localDoubleDoubleFunction.apply(d1, d2);
      d1 += 1.0D;
      d2 += 1.0D;
    }
    localTimer2.stop().display();
    System.out.println("evals / sec = " + paramInt / localTimer2.minus(localTimer1).seconds());
    System.out.println("sum=" + d4);
    localTimer2.reset().start();
    d1 = 0.0D;
    d2 = 0.0D;
    d4 = 0.0D;
    j = paramInt;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      d4 += local46.apply(d1, d2);
      d1 += 1.0D;
      d2 += 1.0D;
    }
    localTimer2.stop().display();
    System.out.println("evals / sec = " + paramInt / localTimer2.minus(localTimer1).seconds());
    System.out.println("sum=" + d4);
  }
  
  public static DoubleFunction div(double paramDouble)
  {
    return mult(1.0D / paramDouble);
  }
  
  public static DoubleFunction equals(double paramDouble)
  {
    new DoubleFunction()
    {
      private final double val$b;
      
      public final double apply(double paramAnonymousDouble)
      {
        return paramAnonymousDouble == this.val$b ? 1.0D : 0.0D;
      }
    };
  }
  
  public static DoubleFunction greater(double paramDouble)
  {
    new DoubleFunction()
    {
      private final double val$b;
      
      public final double apply(double paramAnonymousDouble)
      {
        return paramAnonymousDouble > this.val$b ? 1.0D : 0.0D;
      }
    };
  }
  
  public static DoubleFunction IEEEremainder(double paramDouble)
  {
    new DoubleFunction()
    {
      private final double val$b;
      
      public final double apply(double paramAnonymousDouble)
      {
        return Math.IEEEremainder(paramAnonymousDouble, this.val$b);
      }
    };
  }
  
  public static DoubleProcedure isBetween(double paramDouble1, double paramDouble2)
  {
    new DoubleProcedure()
    {
      private final double val$from;
      private final double val$to;
      
      public final boolean apply(double paramAnonymousDouble)
      {
        return (this.val$from <= paramAnonymousDouble) && (paramAnonymousDouble <= this.val$to);
      }
    };
  }
  
  public static DoubleProcedure isEqual(double paramDouble)
  {
    new DoubleProcedure()
    {
      private final double val$b;
      
      public final boolean apply(double paramAnonymousDouble)
      {
        return paramAnonymousDouble == this.val$b;
      }
    };
  }
  
  public static DoubleProcedure isGreater(double paramDouble)
  {
    new DoubleProcedure()
    {
      private final double val$b;
      
      public final boolean apply(double paramAnonymousDouble)
      {
        return paramAnonymousDouble > this.val$b;
      }
    };
  }
  
  public static DoubleProcedure isLess(double paramDouble)
  {
    new DoubleProcedure()
    {
      private final double val$b;
      
      public final boolean apply(double paramAnonymousDouble)
      {
        return paramAnonymousDouble < this.val$b;
      }
    };
  }
  
  public static DoubleFunction less(double paramDouble)
  {
    new DoubleFunction()
    {
      private final double val$b;
      
      public final double apply(double paramAnonymousDouble)
      {
        return paramAnonymousDouble < this.val$b ? 1.0D : 0.0D;
      }
    };
  }
  
  public static DoubleFunction lg(double paramDouble)
  {
    new DoubleFunction()
    {
      private final double logInv;
      private final double val$b;
      
      public final double apply(double paramAnonymousDouble)
      {
        return Math.log(paramAnonymousDouble) * this.logInv;
      }
    };
  }
  
  protected static void main(String[] paramArrayOfString)
  {
    int i = Integer.parseInt(paramArrayOfString[0]);
    demo2(i);
  }
  
  public static DoubleFunction max(double paramDouble)
  {
    new DoubleFunction()
    {
      private final double val$b;
      
      public final double apply(double paramAnonymousDouble)
      {
        return Math.max(paramAnonymousDouble, this.val$b);
      }
    };
  }
  
  public static DoubleFunction min(double paramDouble)
  {
    new DoubleFunction()
    {
      private final double val$b;
      
      public final double apply(double paramAnonymousDouble)
      {
        return Math.min(paramAnonymousDouble, this.val$b);
      }
    };
  }
  
  public static DoubleFunction minus(double paramDouble)
  {
    return plus(-paramDouble);
  }
  
  public static DoubleDoubleFunction minusMult(double paramDouble)
  {
    return plusMult(-paramDouble);
  }
  
  public static DoubleFunction mod(double paramDouble)
  {
    new DoubleFunction()
    {
      private final double val$b;
      
      public final double apply(double paramAnonymousDouble)
      {
        return paramAnonymousDouble % this.val$b;
      }
    };
  }
  
  public static DoubleFunction mult(double paramDouble)
  {
    return new Mult(paramDouble);
  }
  
  public static DoubleFunction plus(double paramDouble)
  {
    new DoubleFunction()
    {
      private final double val$b;
      
      public final double apply(double paramAnonymousDouble)
      {
        return paramAnonymousDouble + this.val$b;
      }
    };
  }
  
  public static DoubleDoubleFunction plusMult(double paramDouble)
  {
    return new PlusMult(paramDouble);
  }
  
  public static DoubleFunction pow(double paramDouble)
  {
    new DoubleFunction()
    {
      private final double val$b;
      
      public final double apply(double paramAnonymousDouble)
      {
        return Math.pow(paramAnonymousDouble, this.val$b);
      }
    };
  }
  
  public static DoubleFunction random()
  {
    return new MersenneTwister(new Date());
  }
  
  public static DoubleFunction round(double paramDouble)
  {
    new DoubleFunction()
    {
      private final double val$precision;
      
      public final double apply(double paramAnonymousDouble)
      {
        return Math.rint(paramAnonymousDouble / this.val$precision) * this.val$precision;
      }
    };
  }
  
  public static DoubleDoubleFunction swapArgs(DoubleDoubleFunction paramDoubleDoubleFunction)
  {
    new DoubleDoubleFunction()
    {
      private final DoubleDoubleFunction val$function;
      
      public final double apply(double paramAnonymousDouble1, double paramAnonymousDouble2)
      {
        return this.val$function.apply(paramAnonymousDouble2, paramAnonymousDouble1);
      }
    };
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.math.Functions
 * JD-Core Version:    0.7.0.1
 */