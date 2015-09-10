/*   1:    */ package org.hibernate.service.jndi.internal;
/*   2:    */ 
/*   3:    */ import java.util.Hashtable;
/*   4:    */ import java.util.Map;
/*   5:    */ import javax.naming.Context;
/*   6:    */ import javax.naming.InitialContext;
/*   7:    */ import javax.naming.InvalidNameException;
/*   8:    */ import javax.naming.Name;
/*   9:    */ import javax.naming.NameNotFoundException;
/*  10:    */ import javax.naming.NameParser;
/*  11:    */ import javax.naming.NamingException;
/*  12:    */ import javax.naming.event.EventContext;
/*  13:    */ import javax.naming.event.NamespaceChangeListener;
/*  14:    */ import org.hibernate.internal.CoreMessageLogger;
/*  15:    */ import org.hibernate.internal.util.jndi.JndiHelper;
/*  16:    */ import org.hibernate.service.jndi.JndiException;
/*  17:    */ import org.hibernate.service.jndi.JndiNameException;
/*  18:    */ import org.hibernate.service.jndi.spi.JndiService;
/*  19:    */ import org.jboss.logging.Logger;
/*  20:    */ 
/*  21:    */ public class JndiServiceImpl
/*  22:    */   implements JndiService
/*  23:    */ {
/*  24: 52 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, JndiServiceImpl.class.getName());
/*  25:    */   private final Hashtable initialContextSettings;
/*  26:    */   
/*  27:    */   public JndiServiceImpl(Map configurationValues)
/*  28:    */   {
/*  29: 57 */     this.initialContextSettings = JndiHelper.extractJndiProperties(configurationValues);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Object locate(String jndiName)
/*  33:    */   {
/*  34: 62 */     InitialContext initialContext = buildInitialContext();
/*  35: 63 */     Name name = parseName(jndiName, initialContext);
/*  36:    */     try
/*  37:    */     {
/*  38: 65 */       return initialContext.lookup(name);
/*  39:    */     }
/*  40:    */     catch (NamingException e)
/*  41:    */     {
/*  42: 68 */       throw new JndiException("Unable to lookup JNDI name [" + jndiName + "]", e);
/*  43:    */     }
/*  44:    */     finally
/*  45:    */     {
/*  46: 71 */       cleanUp(initialContext);
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   private InitialContext buildInitialContext()
/*  51:    */   {
/*  52:    */     try
/*  53:    */     {
/*  54: 77 */       return this.initialContextSettings.size() == 0 ? new InitialContext() : new InitialContext(this.initialContextSettings);
/*  55:    */     }
/*  56:    */     catch (NamingException e)
/*  57:    */     {
/*  58: 80 */       throw new JndiException("Unable to open InitialContext", e);
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   private Name parseName(String jndiName, Context context)
/*  63:    */   {
/*  64:    */     try
/*  65:    */     {
/*  66: 86 */       return context.getNameParser("").parse(jndiName);
/*  67:    */     }
/*  68:    */     catch (InvalidNameException e)
/*  69:    */     {
/*  70: 89 */       throw new JndiNameException("JNDI name [" + jndiName + "] was not valid", e);
/*  71:    */     }
/*  72:    */     catch (NamingException e)
/*  73:    */     {
/*  74: 92 */       throw new JndiException("Error parsing JNDI name [" + jndiName + "]", e);
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   private void cleanUp(InitialContext initialContext)
/*  79:    */   {
/*  80:    */     try
/*  81:    */     {
/*  82: 98 */       initialContext.close();
/*  83:    */     }
/*  84:    */     catch (NamingException e)
/*  85:    */     {
/*  86:101 */       LOG.unableToCloseInitialContext(e.toString());
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void bind(String jndiName, Object value)
/*  91:    */   {
/*  92:107 */     InitialContext initialContext = buildInitialContext();
/*  93:108 */     Name name = parseName(jndiName, initialContext);
/*  94:    */     try
/*  95:    */     {
/*  96:110 */       bind(name, value, initialContext);
/*  97:    */     }
/*  98:    */     finally
/*  99:    */     {
/* 100:113 */       cleanUp(initialContext);
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   private void bind(Name name, Object value, Context context)
/* 105:    */   {
/* 106:    */     try
/* 107:    */     {
/* 108:119 */       LOG.tracef("Binding : %s", name);
/* 109:120 */       context.rebind(name, value);
/* 110:    */     }
/* 111:    */     catch (Exception initialException)
/* 112:    */     {
/* 113:124 */       if (name.size() == 1) {
/* 114:126 */         throw new JndiException("Error performing bind [" + name + "]", initialException);
/* 115:    */       }
/* 116:131 */       Context intermediateContextBase = context;
/* 117:132 */       while (name.size() > 1)
/* 118:    */       {
/* 119:133 */         String intermediateContextName = name.get(0);
/* 120:    */         
/* 121:135 */         Context intermediateContext = null;
/* 122:    */         try
/* 123:    */         {
/* 124:137 */           LOG.tracev("Intermediate lookup: {0}", intermediateContextName);
/* 125:138 */           intermediateContext = (Context)intermediateContextBase.lookup(intermediateContextName);
/* 126:    */         }
/* 127:    */         catch (NameNotFoundException handledBelow) {}catch (NamingException e)
/* 128:    */         {
/* 129:144 */           throw new JndiException("Unanticipated error doing intermediate lookup", e);
/* 130:    */         }
/* 131:147 */         if (intermediateContext != null)
/* 132:    */         {
/* 133:148 */           LOG.tracev("Found intermediate context: {0}", intermediateContextName);
/* 134:    */         }
/* 135:    */         else
/* 136:    */         {
/* 137:151 */           LOG.tracev("Creating sub-context: {0}", intermediateContextName);
/* 138:    */           try
/* 139:    */           {
/* 140:153 */             intermediateContext = intermediateContextBase.createSubcontext(intermediateContextName);
/* 141:    */           }
/* 142:    */           catch (NamingException e)
/* 143:    */           {
/* 144:156 */             throw new JndiException("Error creating intermediate context [" + intermediateContextName + "]", e);
/* 145:    */           }
/* 146:    */         }
/* 147:159 */         intermediateContextBase = intermediateContext;
/* 148:160 */         name = name.getSuffix(1);
/* 149:    */       }
/* 150:162 */       LOG.tracev("Binding : {0}", name);
/* 151:    */       try
/* 152:    */       {
/* 153:164 */         intermediateContextBase.rebind(name, value);
/* 154:    */       }
/* 155:    */       catch (NamingException e)
/* 156:    */       {
/* 157:167 */         throw new JndiException("Error performing intermediate bind [" + name + "]", e);
/* 158:    */       }
/* 159:    */     }
/* 160:170 */     LOG.debugf("Bound name: %s", name);
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void unbind(String jndiName)
/* 164:    */   {
/* 165:175 */     InitialContext initialContext = buildInitialContext();
/* 166:176 */     Name name = parseName(jndiName, initialContext);
/* 167:    */     try
/* 168:    */     {
/* 169:178 */       initialContext.unbind(name);
/* 170:    */     }
/* 171:    */     catch (Exception e)
/* 172:    */     {
/* 173:181 */       throw new JndiException("Error performing unbind [" + name + "]", e);
/* 174:    */     }
/* 175:    */     finally
/* 176:    */     {
/* 177:184 */       cleanUp(initialContext);
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void addListener(String jndiName, NamespaceChangeListener listener)
/* 182:    */   {
/* 183:190 */     InitialContext initialContext = buildInitialContext();
/* 184:191 */     Name name = parseName(jndiName, initialContext);
/* 185:    */     try
/* 186:    */     {
/* 187:193 */       ((EventContext)initialContext).addNamingListener(name, 0, listener);
/* 188:    */     }
/* 189:    */     catch (Exception e)
/* 190:    */     {
/* 191:196 */       throw new JndiException("Unable to bind listener to namespace [" + name + "]", e);
/* 192:    */     }
/* 193:    */     finally
/* 194:    */     {
/* 195:199 */       cleanUp(initialContext);
/* 196:    */     }
/* 197:    */   }
/* 198:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jndi.internal.JndiServiceImpl
 * JD-Core Version:    0.7.0.1
 */