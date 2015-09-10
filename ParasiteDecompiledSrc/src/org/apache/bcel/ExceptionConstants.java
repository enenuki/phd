/*   1:    */ package org.apache.bcel;
/*   2:    */ 
/*   3:    */ public abstract interface ExceptionConstants
/*   4:    */ {
/*   5: 66 */   public static final Class THROWABLE = 1.class$java$lang$Throwable == null ? (1.class$java$lang$Throwable = 1.class$("java.lang.Throwable")) : 1.class$java$lang$Throwable;
/*   6: 70 */   public static final Class RUNTIME_EXCEPTION = 1.class$java$lang$RuntimeException == null ? (1.class$java$lang$RuntimeException = 1.class$("java.lang.RuntimeException")) : 1.class$java$lang$RuntimeException;
/*   7: 74 */   public static final Class LINKING_EXCEPTION = 1.class$java$lang$LinkageError == null ? (1.class$java$lang$LinkageError = 1.class$("java.lang.LinkageError")) : 1.class$java$lang$LinkageError;
/*   8: 78 */   public static final Class CLASS_CIRCULARITY_ERROR = 1.class$java$lang$ClassCircularityError == null ? (1.class$java$lang$ClassCircularityError = 1.class$("java.lang.ClassCircularityError")) : 1.class$java$lang$ClassCircularityError;
/*   9: 79 */   public static final Class CLASS_FORMAT_ERROR = 1.class$java$lang$ClassFormatError == null ? (1.class$java$lang$ClassFormatError = 1.class$("java.lang.ClassFormatError")) : 1.class$java$lang$ClassFormatError;
/*  10: 80 */   public static final Class EXCEPTION_IN_INITIALIZER_ERROR = 1.class$java$lang$ExceptionInInitializerError == null ? (1.class$java$lang$ExceptionInInitializerError = 1.class$("java.lang.ExceptionInInitializerError")) : 1.class$java$lang$ExceptionInInitializerError;
/*  11: 81 */   public static final Class INCOMPATIBLE_CLASS_CHANGE_ERROR = 1.class$java$lang$IncompatibleClassChangeError == null ? (1.class$java$lang$IncompatibleClassChangeError = 1.class$("java.lang.IncompatibleClassChangeError")) : 1.class$java$lang$IncompatibleClassChangeError;
/*  12: 82 */   public static final Class ABSTRACT_METHOD_ERROR = 1.class$java$lang$AbstractMethodError == null ? (1.class$java$lang$AbstractMethodError = 1.class$("java.lang.AbstractMethodError")) : 1.class$java$lang$AbstractMethodError;
/*  13: 83 */   public static final Class ILLEGAL_ACCESS_ERROR = 1.class$java$lang$IllegalAccessError == null ? (1.class$java$lang$IllegalAccessError = 1.class$("java.lang.IllegalAccessError")) : 1.class$java$lang$IllegalAccessError;
/*  14: 84 */   public static final Class INSTANTIATION_ERROR = 1.class$java$lang$InstantiationError == null ? (1.class$java$lang$InstantiationError = 1.class$("java.lang.InstantiationError")) : 1.class$java$lang$InstantiationError;
/*  15: 85 */   public static final Class NO_SUCH_FIELD_ERROR = 1.class$java$lang$NoSuchFieldError == null ? (1.class$java$lang$NoSuchFieldError = 1.class$("java.lang.NoSuchFieldError")) : 1.class$java$lang$NoSuchFieldError;
/*  16: 86 */   public static final Class NO_SUCH_METHOD_ERROR = 1.class$java$lang$NoSuchMethodError == null ? (1.class$java$lang$NoSuchMethodError = 1.class$("java.lang.NoSuchMethodError")) : 1.class$java$lang$NoSuchMethodError;
/*  17: 87 */   public static final Class NO_CLASS_DEF_FOUND_ERROR = 1.class$java$lang$NoClassDefFoundError == null ? (1.class$java$lang$NoClassDefFoundError = 1.class$("java.lang.NoClassDefFoundError")) : 1.class$java$lang$NoClassDefFoundError;
/*  18: 88 */   public static final Class UNSATISFIED_LINK_ERROR = 1.class$java$lang$UnsatisfiedLinkError == null ? (1.class$java$lang$UnsatisfiedLinkError = 1.class$("java.lang.UnsatisfiedLinkError")) : 1.class$java$lang$UnsatisfiedLinkError;
/*  19: 89 */   public static final Class VERIFY_ERROR = 1.class$java$lang$VerifyError == null ? (1.class$java$lang$VerifyError = 1.class$("java.lang.VerifyError")) : 1.class$java$lang$VerifyError;
/*  20: 96 */   public static final Class NULL_POINTER_EXCEPTION = 1.class$java$lang$NullPointerException == null ? (1.class$java$lang$NullPointerException = 1.class$("java.lang.NullPointerException")) : 1.class$java$lang$NullPointerException;
/*  21: 97 */   public static final Class ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION = 1.class$java$lang$ArrayIndexOutOfBoundsException == null ? (1.class$java$lang$ArrayIndexOutOfBoundsException = 1.class$("java.lang.ArrayIndexOutOfBoundsException")) : 1.class$java$lang$ArrayIndexOutOfBoundsException;
/*  22: 98 */   public static final Class ARITHMETIC_EXCEPTION = 1.class$java$lang$ArithmeticException == null ? (1.class$java$lang$ArithmeticException = 1.class$("java.lang.ArithmeticException")) : 1.class$java$lang$ArithmeticException;
/*  23: 99 */   public static final Class NEGATIVE_ARRAY_SIZE_EXCEPTION = 1.class$java$lang$NegativeArraySizeException == null ? (1.class$java$lang$NegativeArraySizeException = 1.class$("java.lang.NegativeArraySizeException")) : 1.class$java$lang$NegativeArraySizeException;
/*  24:100 */   public static final Class CLASS_CAST_EXCEPTION = 1.class$java$lang$ClassCastException == null ? (1.class$java$lang$ClassCastException = 1.class$("java.lang.ClassCastException")) : 1.class$java$lang$ClassCastException;
/*  25:101 */   public static final Class ILLEGAL_MONITOR_STATE = 1.class$java$lang$IllegalMonitorStateException == null ? (1.class$java$lang$IllegalMonitorStateException = 1.class$("java.lang.IllegalMonitorStateException")) : 1.class$java$lang$IllegalMonitorStateException;
/*  26:106 */   public static final Class[] EXCS_CLASS_AND_INTERFACE_RESOLUTION = { NO_CLASS_DEF_FOUND_ERROR, CLASS_FORMAT_ERROR, VERIFY_ERROR, ABSTRACT_METHOD_ERROR, EXCEPTION_IN_INITIALIZER_ERROR, ILLEGAL_ACCESS_ERROR };
/*  27:111 */   public static final Class[] EXCS_FIELD_AND_METHOD_RESOLUTION = { NO_SUCH_FIELD_ERROR, ILLEGAL_ACCESS_ERROR, NO_SUCH_METHOD_ERROR };
/*  28:115 */   public static final Class[] EXCS_INTERFACE_METHOD_RESOLUTION = new Class[0];
/*  29:116 */   public static final Class[] EXCS_STRING_RESOLUTION = new Class[0];
/*  30:119 */   public static final Class[] EXCS_ARRAY_EXCEPTION = { NULL_POINTER_EXCEPTION, ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION };
/*  31:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.ExceptionConstants
 * JD-Core Version:    0.7.0.1
 */