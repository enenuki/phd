/*   1:    */ package org.hibernate.engine.spi;
/*   2:    */ 
/*   3:    */ import org.hibernate.MappingException;
/*   4:    */ import org.hibernate.id.IdentifierGeneratorHelper;
/*   5:    */ import org.hibernate.id.IntegralDataTypeHolder;
/*   6:    */ import org.hibernate.internal.CoreMessageLogger;
/*   7:    */ import org.jboss.logging.Logger;
/*   8:    */ 
/*   9:    */ public class VersionValue
/*  10:    */   implements UnsavedValueStrategy
/*  11:    */ {
/*  12: 42 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, VersionValue.class.getName());
/*  13:    */   private final Object value;
/*  14: 49 */   public static final VersionValue NULL = new VersionValue()
/*  15:    */   {
/*  16:    */     public final Boolean isUnsaved(Object version)
/*  17:    */     {
/*  18: 52 */       VersionValue.LOG.trace("Version unsaved-value strategy NULL");
/*  19: 53 */       return Boolean.valueOf(version == null);
/*  20:    */     }
/*  21:    */     
/*  22:    */     public Object getDefaultValue(Object currentValue)
/*  23:    */     {
/*  24: 57 */       return null;
/*  25:    */     }
/*  26:    */     
/*  27:    */     public String toString()
/*  28:    */     {
/*  29: 61 */       return "VERSION_SAVE_NULL";
/*  30:    */     }
/*  31:    */   };
/*  32: 68 */   public static final VersionValue UNDEFINED = new VersionValue()
/*  33:    */   {
/*  34:    */     public final Boolean isUnsaved(Object version)
/*  35:    */     {
/*  36: 71 */       VersionValue.LOG.trace("Version unsaved-value strategy UNDEFINED");
/*  37: 72 */       return version == null ? Boolean.TRUE : null;
/*  38:    */     }
/*  39:    */     
/*  40:    */     public Object getDefaultValue(Object currentValue)
/*  41:    */     {
/*  42: 76 */       return currentValue;
/*  43:    */     }
/*  44:    */     
/*  45:    */     public String toString()
/*  46:    */     {
/*  47: 80 */       return "VERSION_UNDEFINED";
/*  48:    */     }
/*  49:    */   };
/*  50: 87 */   public static final VersionValue NEGATIVE = new VersionValue()
/*  51:    */   {
/*  52:    */     public final Boolean isUnsaved(Object version)
/*  53:    */       throws MappingException
/*  54:    */     {
/*  55: 91 */       VersionValue.LOG.trace("Version unsaved-value strategy NEGATIVE");
/*  56: 92 */       if (version == null) {
/*  57: 92 */         return Boolean.TRUE;
/*  58:    */       }
/*  59: 93 */       if ((version instanceof Number)) {
/*  60: 94 */         return Boolean.valueOf(((Number)version).longValue() < 0L);
/*  61:    */       }
/*  62: 96 */       throw new MappingException("unsaved-value NEGATIVE may only be used with short, int and long types");
/*  63:    */     }
/*  64:    */     
/*  65:    */     public Object getDefaultValue(Object currentValue)
/*  66:    */     {
/*  67:100 */       return IdentifierGeneratorHelper.getIntegralDataTypeHolder(currentValue.getClass()).initialize(-1L).makeValue();
/*  68:    */     }
/*  69:    */     
/*  70:    */     public String toString()
/*  71:    */     {
/*  72:106 */       return "VERSION_NEGATIVE";
/*  73:    */     }
/*  74:    */   };
/*  75:    */   
/*  76:    */   protected VersionValue()
/*  77:    */   {
/*  78:111 */     this.value = null;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public VersionValue(Object value)
/*  82:    */   {
/*  83:120 */     this.value = value;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Boolean isUnsaved(Object version)
/*  87:    */     throws MappingException
/*  88:    */   {
/*  89:125 */     LOG.tracev("Version unsaved-value: {0}", this.value);
/*  90:126 */     return Boolean.valueOf((version == null) || (version.equals(this.value)));
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Object getDefaultValue(Object currentValue)
/*  94:    */   {
/*  95:131 */     return this.value;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String toString()
/*  99:    */   {
/* 100:136 */     return "version unsaved-value: " + this.value;
/* 101:    */   }
/* 102:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.spi.VersionValue
 * JD-Core Version:    0.7.0.1
 */