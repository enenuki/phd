package cern.jet.math;

import cern.colt.function.IntFunction;
import cern.colt.function.IntIntFunction;
import cern.colt.function.IntIntProcedure;
import cern.colt.function.IntProcedure;
import cern.jet.random.engine.MersenneTwister;
import java.util.Date;

public class IntFunctions
{
  public static final IntFunctions intFunctions = new IntFunctions();
  public static final IntFunction abs = new IntFunction()
  {
    public final int apply(int paramAnonymousInt)
    {
      return paramAnonymousInt < 0 ? -paramAnonymousInt : paramAnonymousInt;
    }
  };
  public static final IntFunction dec = new IntFunction()
  {
    public final int apply(int paramAnonymousInt)
    {
      return paramAnonymousInt--;
    }
  };
  public static final IntFunction factorial = new IntFunction()
  {
    public final int apply(int paramAnonymousInt)
    {
      return (int)Arithmetic.factorial(paramAnonymousInt);
    }
  };
  public static final IntFunction identity = new IntFunction()
  {
    public final int apply(int paramAnonymousInt)
    {
      return paramAnonymousInt;
    }
  };
  public static final IntFunction inc = new IntFunction()
  {
    public final int apply(int paramAnonymousInt)
    {
      return paramAnonymousInt++;
    }
  };
  public static final IntFunction neg = new IntFunction()
  {
    public final int apply(int paramAnonymousInt)
    {
      return -paramAnonymousInt;
    }
  };
  public static final IntFunction not = new IntFunction()
  {
    public final int apply(int paramAnonymousInt)
    {
      return paramAnonymousInt ^ 0xFFFFFFFF;
    }
  };
  public static final IntFunction sign = new IntFunction()
  {
    public final int apply(int paramAnonymousInt)
    {
      return paramAnonymousInt > 0 ? 1 : paramAnonymousInt < 0 ? -1 : 0;
    }
  };
  public static final IntFunction square = new IntFunction()
  {
    public final int apply(int paramAnonymousInt)
    {
      return paramAnonymousInt * paramAnonymousInt;
    }
  };
  public static final IntIntFunction and = new IntIntFunction()
  {
    public final int apply(int paramAnonymousInt1, int paramAnonymousInt2)
    {
      return paramAnonymousInt1 & paramAnonymousInt2;
    }
  };
  public static final IntIntFunction compare = new IntIntFunction()
  {
    public final int apply(int paramAnonymousInt1, int paramAnonymousInt2)
    {
      return paramAnonymousInt1 > paramAnonymousInt2 ? 1 : paramAnonymousInt1 < paramAnonymousInt2 ? -1 : 0;
    }
  };
  public static final IntIntFunction div = new IntIntFunction()
  {
    public final int apply(int paramAnonymousInt1, int paramAnonymousInt2)
    {
      return paramAnonymousInt1 / paramAnonymousInt2;
    }
  };
  public static final IntIntFunction equals = new IntIntFunction()
  {
    public final int apply(int paramAnonymousInt1, int paramAnonymousInt2)
    {
      return paramAnonymousInt1 == paramAnonymousInt2 ? 1 : 0;
    }
  };
  public static final IntIntProcedure isEqual = new IntIntProcedure()
  {
    public final boolean apply(int paramAnonymousInt1, int paramAnonymousInt2)
    {
      return paramAnonymousInt1 == paramAnonymousInt2;
    }
  };
  public static final IntIntProcedure isLess = new IntIntProcedure()
  {
    public final boolean apply(int paramAnonymousInt1, int paramAnonymousInt2)
    {
      return paramAnonymousInt1 < paramAnonymousInt2;
    }
  };
  public static final IntIntProcedure isGreater = new IntIntProcedure()
  {
    public final boolean apply(int paramAnonymousInt1, int paramAnonymousInt2)
    {
      return paramAnonymousInt1 > paramAnonymousInt2;
    }
  };
  public static final IntIntFunction max = new IntIntFunction()
  {
    public final int apply(int paramAnonymousInt1, int paramAnonymousInt2)
    {
      return paramAnonymousInt1 >= paramAnonymousInt2 ? paramAnonymousInt1 : paramAnonymousInt2;
    }
  };
  public static final IntIntFunction min = new IntIntFunction()
  {
    public final int apply(int paramAnonymousInt1, int paramAnonymousInt2)
    {
      return paramAnonymousInt1 <= paramAnonymousInt2 ? paramAnonymousInt1 : paramAnonymousInt2;
    }
  };
  public static final IntIntFunction minus = new IntIntFunction()
  {
    public final int apply(int paramAnonymousInt1, int paramAnonymousInt2)
    {
      return paramAnonymousInt1 - paramAnonymousInt2;
    }
  };
  public static final IntIntFunction mod = new IntIntFunction()
  {
    public final int apply(int paramAnonymousInt1, int paramAnonymousInt2)
    {
      return paramAnonymousInt1 % paramAnonymousInt2;
    }
  };
  public static final IntIntFunction mult = new IntIntFunction()
  {
    public final int apply(int paramAnonymousInt1, int paramAnonymousInt2)
    {
      return paramAnonymousInt1 * paramAnonymousInt2;
    }
  };
  public static final IntIntFunction or = new IntIntFunction()
  {
    public final int apply(int paramAnonymousInt1, int paramAnonymousInt2)
    {
      return paramAnonymousInt1 | paramAnonymousInt2;
    }
  };
  public static final IntIntFunction plus = new IntIntFunction()
  {
    public final int apply(int paramAnonymousInt1, int paramAnonymousInt2)
    {
      return paramAnonymousInt1 + paramAnonymousInt2;
    }
  };
  public static final IntIntFunction pow = new IntIntFunction()
  {
    public final int apply(int paramAnonymousInt1, int paramAnonymousInt2)
    {
      return (int)Math.pow(paramAnonymousInt1, paramAnonymousInt2);
    }
  };
  public static final IntIntFunction shiftLeft = new IntIntFunction()
  {
    public final int apply(int paramAnonymousInt1, int paramAnonymousInt2)
    {
      return paramAnonymousInt1 << paramAnonymousInt2;
    }
  };
  public static final IntIntFunction shiftRightSigned = new IntIntFunction()
  {
    public final int apply(int paramAnonymousInt1, int paramAnonymousInt2)
    {
      return paramAnonymousInt1 >> paramAnonymousInt2;
    }
  };
  public static final IntIntFunction shiftRightUnsigned = new IntIntFunction()
  {
    public final int apply(int paramAnonymousInt1, int paramAnonymousInt2)
    {
      return paramAnonymousInt1 >>> paramAnonymousInt2;
    }
  };
  public static final IntIntFunction xor = new IntIntFunction()
  {
    public final int apply(int paramAnonymousInt1, int paramAnonymousInt2)
    {
      return paramAnonymousInt1 ^ paramAnonymousInt2;
    }
  };
  
  public static IntFunction and(int paramInt)
  {
    new IntFunction()
    {
      private final int val$b;
      
      public final int apply(int paramAnonymousInt)
      {
        return paramAnonymousInt & this.val$b;
      }
    };
  }
  
  public static IntFunction between(int paramInt1, int paramInt2)
  {
    new IntFunction()
    {
      private final int val$from;
      private final int val$to;
      
      public final int apply(int paramAnonymousInt)
      {
        return (this.val$from <= paramAnonymousInt) && (paramAnonymousInt <= this.val$to) ? 1 : 0;
      }
    };
  }
  
  public static IntFunction bindArg1(IntIntFunction paramIntIntFunction, int paramInt)
  {
    new IntFunction()
    {
      private final IntIntFunction val$function;
      private final int val$c;
      
      public final int apply(int paramAnonymousInt)
      {
        return this.val$function.apply(this.val$c, paramAnonymousInt);
      }
    };
  }
  
  public static IntFunction bindArg2(IntIntFunction paramIntIntFunction, int paramInt)
  {
    new IntFunction()
    {
      private final IntIntFunction val$function;
      private final int val$c;
      
      public final int apply(int paramAnonymousInt)
      {
        return this.val$function.apply(paramAnonymousInt, this.val$c);
      }
    };
  }
  
  public static IntFunction chain(IntFunction paramIntFunction1, IntFunction paramIntFunction2)
  {
    new IntFunction()
    {
      private final IntFunction val$g;
      private final IntFunction val$h;
      
      public final int apply(int paramAnonymousInt)
      {
        return this.val$g.apply(this.val$h.apply(paramAnonymousInt));
      }
    };
  }
  
  public static IntIntFunction chain(IntFunction paramIntFunction, IntIntFunction paramIntIntFunction)
  {
    new IntIntFunction()
    {
      private final IntFunction val$g;
      private final IntIntFunction val$h;
      
      public final int apply(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return this.val$g.apply(this.val$h.apply(paramAnonymousInt1, paramAnonymousInt2));
      }
    };
  }
  
  public static IntIntFunction chain(IntIntFunction paramIntIntFunction, IntFunction paramIntFunction1, IntFunction paramIntFunction2)
  {
    new IntIntFunction()
    {
      private final IntIntFunction val$f;
      private final IntFunction val$g;
      private final IntFunction val$h;
      
      public final int apply(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return this.val$f.apply(this.val$g.apply(paramAnonymousInt1), this.val$h.apply(paramAnonymousInt2));
      }
    };
  }
  
  public static IntFunction compare(int paramInt)
  {
    new IntFunction()
    {
      private final int val$b;
      
      public final int apply(int paramAnonymousInt)
      {
        return paramAnonymousInt > this.val$b ? 1 : paramAnonymousInt < this.val$b ? -1 : 0;
      }
    };
  }
  
  public static IntFunction constant(int paramInt)
  {
    new IntFunction()
    {
      private final int val$c;
      
      public final int apply(int paramAnonymousInt)
      {
        return this.val$c;
      }
    };
  }
  
  public static IntFunction div(int paramInt)
  {
    new IntFunction()
    {
      private final int val$b;
      
      public final int apply(int paramAnonymousInt)
      {
        return paramAnonymousInt / this.val$b;
      }
    };
  }
  
  public static IntFunction equals(int paramInt)
  {
    new IntFunction()
    {
      private final int val$b;
      
      public final int apply(int paramAnonymousInt)
      {
        return paramAnonymousInt == this.val$b ? 1 : 0;
      }
    };
  }
  
  public static IntProcedure isBetween(int paramInt1, int paramInt2)
  {
    new IntProcedure()
    {
      private final int val$from;
      private final int val$to;
      
      public final boolean apply(int paramAnonymousInt)
      {
        return (this.val$from <= paramAnonymousInt) && (paramAnonymousInt <= this.val$to);
      }
    };
  }
  
  public static IntProcedure isEqual(int paramInt)
  {
    new IntProcedure()
    {
      private final int val$b;
      
      public final boolean apply(int paramAnonymousInt)
      {
        return paramAnonymousInt == this.val$b;
      }
    };
  }
  
  public static IntProcedure isGreater(int paramInt)
  {
    new IntProcedure()
    {
      private final int val$b;
      
      public final boolean apply(int paramAnonymousInt)
      {
        return paramAnonymousInt > this.val$b;
      }
    };
  }
  
  public static IntProcedure isLess(int paramInt)
  {
    new IntProcedure()
    {
      private final int val$b;
      
      public final boolean apply(int paramAnonymousInt)
      {
        return paramAnonymousInt < this.val$b;
      }
    };
  }
  
  public static IntFunction max(int paramInt)
  {
    new IntFunction()
    {
      private final int val$b;
      
      public final int apply(int paramAnonymousInt)
      {
        return paramAnonymousInt >= this.val$b ? paramAnonymousInt : this.val$b;
      }
    };
  }
  
  public static IntFunction min(int paramInt)
  {
    new IntFunction()
    {
      private final int val$b;
      
      public final int apply(int paramAnonymousInt)
      {
        return paramAnonymousInt <= this.val$b ? paramAnonymousInt : this.val$b;
      }
    };
  }
  
  public static IntFunction minus(int paramInt)
  {
    new IntFunction()
    {
      private final int val$b;
      
      public final int apply(int paramAnonymousInt)
      {
        return paramAnonymousInt - this.val$b;
      }
    };
  }
  
  public static IntFunction mod(int paramInt)
  {
    new IntFunction()
    {
      private final int val$b;
      
      public final int apply(int paramAnonymousInt)
      {
        return paramAnonymousInt % this.val$b;
      }
    };
  }
  
  public static IntFunction mult(int paramInt)
  {
    new IntFunction()
    {
      private final int val$b;
      
      public final int apply(int paramAnonymousInt)
      {
        return paramAnonymousInt * this.val$b;
      }
    };
  }
  
  public static IntFunction or(int paramInt)
  {
    new IntFunction()
    {
      private final int val$b;
      
      public final int apply(int paramAnonymousInt)
      {
        return paramAnonymousInt | this.val$b;
      }
    };
  }
  
  public static IntFunction plus(int paramInt)
  {
    new IntFunction()
    {
      private final int val$b;
      
      public final int apply(int paramAnonymousInt)
      {
        return paramAnonymousInt + this.val$b;
      }
    };
  }
  
  public static IntFunction pow(int paramInt)
  {
    new IntFunction()
    {
      private final int val$b;
      
      public final int apply(int paramAnonymousInt)
      {
        return (int)Math.pow(paramAnonymousInt, this.val$b);
      }
    };
  }
  
  public static IntFunction random()
  {
    return new MersenneTwister(new Date());
  }
  
  public static IntFunction shiftLeft(int paramInt)
  {
    new IntFunction()
    {
      private final int val$b;
      
      public final int apply(int paramAnonymousInt)
      {
        return paramAnonymousInt << this.val$b;
      }
    };
  }
  
  public static IntFunction shiftRightSigned(int paramInt)
  {
    new IntFunction()
    {
      private final int val$b;
      
      public final int apply(int paramAnonymousInt)
      {
        return paramAnonymousInt >> this.val$b;
      }
    };
  }
  
  public static IntFunction shiftRightUnsigned(int paramInt)
  {
    new IntFunction()
    {
      private final int val$b;
      
      public final int apply(int paramAnonymousInt)
      {
        return paramAnonymousInt >>> this.val$b;
      }
    };
  }
  
  public static IntIntFunction swapArgs(IntIntFunction paramIntIntFunction)
  {
    new IntIntFunction()
    {
      private final IntIntFunction val$function;
      
      public final int apply(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return this.val$function.apply(paramAnonymousInt2, paramAnonymousInt1);
      }
    };
  }
  
  public static IntFunction xor(int paramInt)
  {
    new IntFunction()
    {
      private final int val$b;
      
      public final int apply(int paramAnonymousInt)
      {
        return paramAnonymousInt ^ this.val$b;
      }
    };
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.math.IntFunctions
 * JD-Core Version:    0.7.0.1
 */