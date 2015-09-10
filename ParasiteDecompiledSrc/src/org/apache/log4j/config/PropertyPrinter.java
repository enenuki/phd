/*   1:    */ package org.apache.log4j.config;
/*   2:    */ 
/*   3:    */ import java.io.PrintWriter;
/*   4:    */ import java.util.Enumeration;
/*   5:    */ import java.util.Hashtable;
/*   6:    */ import org.apache.log4j.Appender;
/*   7:    */ import org.apache.log4j.Category;
/*   8:    */ import org.apache.log4j.Level;
/*   9:    */ import org.apache.log4j.LogManager;
/*  10:    */ import org.apache.log4j.Logger;
/*  11:    */ import org.apache.log4j.Priority;
/*  12:    */ 
/*  13:    */ public class PropertyPrinter
/*  14:    */   implements PropertyGetter.PropertyCallback
/*  15:    */ {
/*  16: 38 */   protected int numAppenders = 0;
/*  17: 39 */   protected Hashtable appenderNames = new Hashtable();
/*  18: 40 */   protected Hashtable layoutNames = new Hashtable();
/*  19:    */   protected PrintWriter out;
/*  20:    */   protected boolean doCapitalize;
/*  21:    */   
/*  22:    */   public PropertyPrinter(PrintWriter out)
/*  23:    */   {
/*  24: 46 */     this(out, false);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public PropertyPrinter(PrintWriter out, boolean doCapitalize)
/*  28:    */   {
/*  29: 51 */     this.out = out;
/*  30: 52 */     this.doCapitalize = doCapitalize;
/*  31:    */     
/*  32: 54 */     print(out);
/*  33: 55 */     out.flush();
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected String genAppName()
/*  37:    */   {
/*  38: 60 */     return "A" + this.numAppenders++;
/*  39:    */   }
/*  40:    */   
/*  41:    */   protected boolean isGenAppName(String name)
/*  42:    */   {
/*  43: 69 */     if ((name.length() < 2) || (name.charAt(0) != 'A')) {
/*  44: 69 */       return false;
/*  45:    */     }
/*  46: 71 */     for (int i = 0; i < name.length(); i++) {
/*  47: 72 */       if ((name.charAt(i) < '0') || (name.charAt(i) > '9')) {
/*  48: 72 */         return false;
/*  49:    */       }
/*  50:    */     }
/*  51: 74 */     return true;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void print(PrintWriter out)
/*  55:    */   {
/*  56: 85 */     printOptions(out, Logger.getRootLogger());
/*  57:    */     
/*  58: 87 */     Enumeration cats = LogManager.getCurrentLoggers();
/*  59: 88 */     while (cats.hasMoreElements()) {
/*  60: 89 */       printOptions(out, (Logger)cats.nextElement());
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected void printOptions(PrintWriter out, Category cat)
/*  65:    */   {
/*  66: 98 */     Enumeration appenders = cat.getAllAppenders();
/*  67: 99 */     Level prio = cat.getLevel();
/*  68:100 */     String appenderString = prio == null ? "" : prio.toString();
/*  69:102 */     while (appenders.hasMoreElements())
/*  70:    */     {
/*  71:103 */       Appender app = (Appender)appenders.nextElement();
/*  72:    */       String name;
/*  73:106 */       if ((name = (String)this.appenderNames.get(app)) == null)
/*  74:    */       {
/*  75:109 */         if (((name = app.getName()) == null) || (isGenAppName(name))) {
/*  76:110 */           name = genAppName();
/*  77:    */         }
/*  78:112 */         this.appenderNames.put(app, name);
/*  79:    */         
/*  80:114 */         printOptions(out, app, "log4j.appender." + name);
/*  81:115 */         if (app.getLayout() != null) {
/*  82:116 */           printOptions(out, app.getLayout(), "log4j.appender." + name + ".layout");
/*  83:    */         }
/*  84:    */       }
/*  85:119 */       appenderString = appenderString + ", " + name;
/*  86:    */     }
/*  87:121 */     String catKey = "log4j.logger." + cat.getName();
/*  88:124 */     if (appenderString != "") {
/*  89:125 */       out.println(catKey + "=" + appenderString);
/*  90:    */     }
/*  91:127 */     if ((!cat.getAdditivity()) && (cat != Logger.getRootLogger())) {
/*  92:128 */       out.println("log4j.additivity." + cat.getName() + "=false");
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   protected void printOptions(PrintWriter out, Logger cat)
/*  97:    */   {
/*  98:133 */     printOptions(out, cat);
/*  99:    */   }
/* 100:    */   
/* 101:    */   protected void printOptions(PrintWriter out, Object obj, String fullname)
/* 102:    */   {
/* 103:138 */     out.println(fullname + "=" + obj.getClass().getName());
/* 104:139 */     PropertyGetter.getProperties(obj, this, fullname + ".");
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void foundProperty(Object obj, String prefix, String name, Object value)
/* 108:    */   {
/* 109:144 */     if (((obj instanceof Appender)) && ("name".equals(name))) {
/* 110:145 */       return;
/* 111:    */     }
/* 112:147 */     if (this.doCapitalize) {
/* 113:148 */       name = capitalize(name);
/* 114:    */     }
/* 115:150 */     this.out.println(prefix + name + "=" + value.toString());
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static String capitalize(String name)
/* 119:    */   {
/* 120:154 */     if ((Character.isLowerCase(name.charAt(0))) && (
/* 121:155 */       (name.length() == 1) || (Character.isLowerCase(name.charAt(1)))))
/* 122:    */     {
/* 123:156 */       StringBuffer newname = new StringBuffer(name);
/* 124:157 */       newname.setCharAt(0, Character.toUpperCase(name.charAt(0)));
/* 125:158 */       return newname.toString();
/* 126:    */     }
/* 127:161 */     return name;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public static void main(String[] args)
/* 131:    */   {
/* 132:166 */     new PropertyPrinter(new PrintWriter(System.out));
/* 133:    */   }
/* 134:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.config.PropertyPrinter
 * JD-Core Version:    0.7.0.1
 */