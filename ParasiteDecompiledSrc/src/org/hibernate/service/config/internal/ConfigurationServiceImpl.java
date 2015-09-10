/*  1:   */ package org.hibernate.service.config.internal;
/*  2:   */ 
/*  3:   */ import java.util.Collections;
/*  4:   */ import java.util.Map;
/*  5:   */ import org.hibernate.service.config.spi.ConfigurationService;
/*  6:   */ import org.hibernate.service.config.spi.ConfigurationService.Converter;
/*  7:   */ 
/*  8:   */ public class ConfigurationServiceImpl
/*  9:   */   implements ConfigurationService
/* 10:   */ {
/* 11:   */   private final Map settings;
/* 12:   */   
/* 13:   */   public ConfigurationServiceImpl(Map settings)
/* 14:   */   {
/* 15:39 */     this.settings = Collections.unmodifiableMap(settings);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Map getSettings()
/* 19:   */   {
/* 20:44 */     return this.settings;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public <T> T getSetting(String name, ConfigurationService.Converter<T> converter)
/* 24:   */   {
/* 25:49 */     return getSetting(name, converter, null);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public <T> T getSetting(String name, ConfigurationService.Converter<T> converter, T defaultValue)
/* 29:   */   {
/* 30:54 */     Object value = this.settings.get(name);
/* 31:55 */     if (value == null) {
/* 32:56 */       return defaultValue;
/* 33:   */     }
/* 34:59 */     return converter.convert(value);
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.config.internal.ConfigurationServiceImpl
 * JD-Core Version:    0.7.0.1
 */