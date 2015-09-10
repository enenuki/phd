/*   1:    */ package hr.nukic.parasite.core.algorithm.configurations;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Map.Entry;
/*   9:    */ import java.util.Set;
/*  10:    */ import nukic.parasite.utils.MainLogger;
/*  11:    */ 
/*  12:    */ public class AlgorithmConfigurationSuite
/*  13:    */ {
/*  14:    */   AlgorithmConfiguration template;
/*  15: 23 */   public Map<String, List<AlgorithmConfiguration>> configurationMap = new HashMap();
/*  16: 26 */   public Map<String, AlgorithmConfiguration> winnerConfigurationMap = new HashMap();
/*  17:    */   AlgorithmConfiguration bestConfig;
/*  18:    */   
/*  19:    */   public AlgorithmConfigurationSuite(AlgorithmConfiguration template, boolean fillConfigurationMap)
/*  20:    */   {
/*  21: 32 */     this.template = template;
/*  22: 34 */     if (fillConfigurationMap)
/*  23:    */     {
/*  24: 35 */       AlgorithmConfiguration config = AlgorithmConfiguration.createAllDefaultValuesConfig(template);
/*  25:    */       
/*  26: 37 */       boolean isAllDefaultConfigAdded = false;
/*  27: 38 */       Iterator<Configurable> i = config.configurables.iterator();
/*  28:    */       Configurable c;
/*  29: 39 */       for (; i.hasNext(); !c.createNext().equals(c))
/*  30:    */       {
/*  31: 40 */         Configurable variable = (Configurable)i.next();
/*  32: 41 */         this.configurationMap.put(variable.name, new ArrayList());
/*  33: 44 */         if (!isAllDefaultConfigAdded)
/*  34:    */         {
/*  35: 46 */           ((List)this.configurationMap.get(variable.name)).add(config);
/*  36: 47 */           MainLogger.debug("Adding all default config: " + config.version);
/*  37: 48 */           isAllDefaultConfigAdded = true;
/*  38:    */         }
/*  39: 52 */         c = variable.getInstanceWithMinValue();
/*  40: 53 */         AlgorithmConfiguration ac = new AlgorithmConfiguration(config);
/*  41: 54 */         ac.overwriteConfigurableWithSameName(c);
/*  42: 55 */         ((List)this.configurationMap.get(variable.name)).add(ac);
/*  43:    */         
/*  44:    */ 
/*  45: 58 */         continue;
/*  46: 59 */         ac = new AlgorithmConfiguration(config);
/*  47: 60 */         Configurable nextConfigurable = c.createNext();
/*  48: 61 */         ac.overwriteConfigurableWithSameName(nextConfigurable);
/*  49: 64 */         if (!ac.isAllDefaultConfig()) {
/*  50: 65 */           ((List)this.configurationMap.get(variable.name)).add(ac);
/*  51:    */         }
/*  52: 67 */         c = nextConfigurable;
/*  53:    */       }
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void calculateNewDefaultsUsing(List<String> csvFilePathList)
/*  58:    */   {
/*  59: 75 */     this.configurationMap.clear();
/*  60: 76 */     this.winnerConfigurationMap.clear();
/*  61:    */     
/*  62: 78 */     AlgorithmConfiguration config = AlgorithmConfiguration.createAllDefaultValuesConfig(this.template);
/*  63: 79 */     Iterator<Configurable> i = config.configurables.iterator();
/*  64:    */     Configurable c;
/*  65: 80 */     for (; i.hasNext(); !c.createNext().equals(c))
/*  66:    */     {
/*  67: 81 */       Configurable variable = (Configurable)i.next();
/*  68:    */       
/*  69: 83 */       MainLogger.debug("\n\n\n\n Filling first record and running evaluations for all configurations varying around " + 
/*  70: 84 */         variable.name);
/*  71:    */       
/*  72: 86 */       this.configurationMap.put(variable.name, new ArrayList());
/*  73:    */       
/*  74:    */ 
/*  75: 89 */       c = variable.getInstanceWithMinValue();
/*  76: 90 */       AlgorithmConfiguration ac = new AlgorithmConfiguration(config);
/*  77: 91 */       ac.overwriteConfigurableWithSameName(c);
/*  78: 92 */       ((List)this.configurationMap.get(variable.name)).add(ac);
/*  79:    */       
/*  80:    */ 
/*  81: 95 */       ConfigurationEvaluationSuite evaluationSuite = new ConfigurationEvaluationSuite(ac, csvFilePathList);
/*  82: 96 */       evaluationSuite.execute();
/*  83:    */       
/*  84:    */ 
/*  85: 99 */       continue;
/*  86:100 */       MainLogger.info("\n\n\nNew " + c.name + " value: " + c.value);
/*  87:    */       
/*  88:102 */       ac = new AlgorithmConfiguration(config);
/*  89:    */       
/*  90:104 */       Configurable nextConfigurable = c.createNext();
/*  91:105 */       ac.overwriteConfigurableWithSameName(nextConfigurable);
/*  92:    */       
/*  93:    */ 
/*  94:108 */       ((List)this.configurationMap.get(variable.name)).add(ac);
/*  95:    */       
/*  96:    */ 
/*  97:111 */       ConfigurationEvaluationSuite evalSuite = new ConfigurationEvaluationSuite(ac, csvFilePathList);
/*  98:112 */       evalSuite.execute();
/*  99:    */       
/* 100:114 */       c = nextConfigurable;
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   private Configurable getBestConfigurable(List<AlgorithmConfiguration> list, String configurableName)
/* 105:    */   {
/* 106:121 */     MainLogger.debug("Looking for the best algorithm configuration and best value of variable configurable... ");
/* 107:122 */     AlgorithmConfiguration bestAc = null;
/* 108:123 */     Iterator i = list.iterator();
/* 109:124 */     while (i.hasNext())
/* 110:    */     {
/* 111:125 */       AlgorithmConfiguration ac = (AlgorithmConfiguration)i.next();
/* 112:126 */       if ((bestAc == null) || 
/* 113:127 */         (ac.getEvaluationSuite().totalScore.relativeScore > bestAc.getEvaluationSuite().totalScore.relativeScore)) {
/* 114:128 */         bestAc = ac;
/* 115:    */       }
/* 116:    */     }
/* 117:131 */     return bestAc.getConfigurableByName(configurableName);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void printConfigurationMap()
/* 121:    */   {
/* 122:136 */     Iterator<Map.Entry<String, List<AlgorithmConfiguration>>> i = this.configurationMap.entrySet().iterator();
/* 123:    */     Iterator<AlgorithmConfiguration> k;
/* 124:137 */     for (; i.hasNext(); k.hasNext())
/* 125:    */     {
/* 126:138 */       Map.Entry<String, List<AlgorithmConfiguration>> entry = (Map.Entry)i.next();
/* 127:    */       
/* 128:140 */       MainLogger.debug("------------------------------------------------------------------------------------------");
/* 129:141 */       MainLogger.debug("Listing configurations that vary around configurable: " + (String)entry.getKey());
/* 130:    */       
/* 131:143 */       k = ((List)entry.getValue()).iterator();
/* 132:144 */       continue;
/* 133:145 */       AlgorithmConfiguration ac = (AlgorithmConfiguration)k.next();
/* 134:146 */       MainLogger.info(ac.toString());
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void runFirstRound() {}
/* 139:    */   
/* 140:    */   public void findBestConfiguration() {}
/* 141:    */   
/* 142:    */   private float calculateDefaultValue(String configurableName)
/* 143:    */   {
/* 144:161 */     float defaultValue = 0.0F;
/* 145:162 */     List<AlgorithmConfiguration> list = (List)this.configurationMap.get(configurableName);
/* 146:163 */     Iterator<AlgorithmConfiguration> i = list.iterator();
/* 147:164 */     while (i.hasNext()) {
/* 148:165 */       AlgorithmConfiguration localAlgorithmConfiguration = (AlgorithmConfiguration)i.next();
/* 149:    */     }
/* 150:168 */     return defaultValue;
/* 151:    */   }
/* 152:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.core.algorithm.configurations.AlgorithmConfigurationSuite
 * JD-Core Version:    0.7.0.1
 */