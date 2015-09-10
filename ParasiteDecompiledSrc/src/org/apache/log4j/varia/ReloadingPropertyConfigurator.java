/*  1:   */ package org.apache.log4j.varia;
/*  2:   */ 
/*  3:   */ import java.net.URL;
/*  4:   */ import org.apache.log4j.PropertyConfigurator;
/*  5:   */ import org.apache.log4j.spi.Configurator;
/*  6:   */ import org.apache.log4j.spi.LoggerRepository;
/*  7:   */ 
/*  8:   */ public class ReloadingPropertyConfigurator
/*  9:   */   implements Configurator
/* 10:   */ {
/* 11:28 */   PropertyConfigurator delegate = new PropertyConfigurator();
/* 12:   */   
/* 13:   */   public void doConfigure(URL url, LoggerRepository repository) {}
/* 14:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.varia.ReloadingPropertyConfigurator
 * JD-Core Version:    0.7.0.1
 */