/*    1:     */ package jxl.write.biff;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.OutputStream;
/*    5:     */ import java.util.ArrayList;
/*    6:     */ import java.util.HashMap;
/*    7:     */ import java.util.Iterator;
/*    8:     */ import jxl.biff.BaseCompoundFile;
/*    9:     */ import jxl.biff.BaseCompoundFile.PropertyStorage;
/*   10:     */ import jxl.biff.IntegerHelper;
/*   11:     */ import jxl.common.Assert;
/*   12:     */ import jxl.common.Logger;
/*   13:     */ import jxl.read.biff.BiffException;
/*   14:     */ 
/*   15:     */ final class CompoundFile
/*   16:     */   extends BaseCompoundFile
/*   17:     */ {
/*   18:  52 */   private static Logger logger = Logger.getLogger(CompoundFile.class);
/*   19:     */   private OutputStream out;
/*   20:     */   private ExcelDataOutput excelData;
/*   21:     */   private int size;
/*   22:     */   private int requiredSize;
/*   23:     */   private int numBigBlockDepotBlocks;
/*   24:     */   private int numSmallBlockDepotChainBlocks;
/*   25:     */   private int numSmallBlockDepotBlocks;
/*   26:     */   private int numExtensionBlocks;
/*   27:     */   private int extensionBlock;
/*   28:     */   private int excelDataBlocks;
/*   29:     */   private int rootStartBlock;
/*   30:     */   private int excelDataStartBlock;
/*   31:     */   private int bbdStartBlock;
/*   32:     */   private int sbdStartBlockChain;
/*   33:     */   private int sbdStartBlock;
/*   34:     */   private int additionalPropertyBlocks;
/*   35:     */   private int numSmallBlocks;
/*   36:     */   private int numPropertySets;
/*   37:     */   private int numRootEntryBlocks;
/*   38:     */   private ArrayList additionalPropertySets;
/*   39:     */   private HashMap standardPropertySets;
/*   40:     */   private int bbdPos;
/*   41:     */   private byte[] bigBlockDepot;
/*   42:     */   
/*   43:     */   private static final class ReadPropertyStorage
/*   44:     */   {
/*   45:     */     BaseCompoundFile.PropertyStorage propertyStorage;
/*   46:     */     byte[] data;
/*   47:     */     int number;
/*   48:     */     
/*   49:     */     ReadPropertyStorage(BaseCompoundFile.PropertyStorage ps, byte[] d, int n)
/*   50:     */     {
/*   51: 172 */       this.propertyStorage = ps;
/*   52: 173 */       this.data = d;
/*   53: 174 */       this.number = n;
/*   54:     */     }
/*   55:     */   }
/*   56:     */   
/*   57:     */   public CompoundFile(ExcelDataOutput data, int l, OutputStream os, jxl.read.biff.CompoundFile rcf)
/*   58:     */     throws CopyAdditionalPropertySetsException, IOException
/*   59:     */   {
/*   60: 206 */     this.size = l;
/*   61: 207 */     this.excelData = data;
/*   62:     */     
/*   63: 209 */     readAdditionalPropertySets(rcf);
/*   64:     */     
/*   65: 211 */     this.numRootEntryBlocks = 1;
/*   66: 212 */     this.numPropertySets = (4 + (this.additionalPropertySets != null ? this.additionalPropertySets.size() : 0));
/*   67: 216 */     if (this.additionalPropertySets != null)
/*   68:     */     {
/*   69: 218 */       this.numSmallBlockDepotChainBlocks = getBigBlocksRequired(this.numSmallBlocks * 4);
/*   70: 219 */       this.numSmallBlockDepotBlocks = getBigBlocksRequired(this.numSmallBlocks * 64);
/*   71:     */       
/*   72:     */ 
/*   73: 222 */       this.numRootEntryBlocks += getBigBlocksRequired(this.additionalPropertySets.size() * 128);
/*   74:     */     }
/*   75: 227 */     int blocks = getBigBlocksRequired(l);
/*   76: 231 */     if (l < 4096) {
/*   77: 233 */       this.requiredSize = 4096;
/*   78:     */     } else {
/*   79: 237 */       this.requiredSize = (blocks * 512);
/*   80:     */     }
/*   81: 240 */     this.out = os;
/*   82:     */     
/*   83:     */ 
/*   84:     */ 
/*   85: 244 */     this.excelDataBlocks = (this.requiredSize / 512);
/*   86: 245 */     this.numBigBlockDepotBlocks = 1;
/*   87:     */     
/*   88: 247 */     int blockChainLength = 109;
/*   89:     */     
/*   90: 249 */     int startTotalBlocks = this.excelDataBlocks + 8 + 8 + this.additionalPropertyBlocks + this.numSmallBlockDepotBlocks + this.numSmallBlockDepotChainBlocks + this.numRootEntryBlocks;
/*   91:     */     
/*   92:     */ 
/*   93:     */ 
/*   94:     */ 
/*   95:     */ 
/*   96:     */ 
/*   97:     */ 
/*   98: 257 */     int totalBlocks = startTotalBlocks + this.numBigBlockDepotBlocks;
/*   99:     */     
/*  100:     */ 
/*  101: 260 */     this.numBigBlockDepotBlocks = ((int)Math.ceil(totalBlocks / 128.0D));
/*  102:     */     
/*  103:     */ 
/*  104:     */ 
/*  105: 264 */     totalBlocks = startTotalBlocks + this.numBigBlockDepotBlocks;
/*  106:     */     
/*  107:     */ 
/*  108: 267 */     this.numBigBlockDepotBlocks = ((int)Math.ceil(totalBlocks / 128.0D));
/*  109:     */     
/*  110:     */ 
/*  111:     */ 
/*  112: 271 */     totalBlocks = startTotalBlocks + this.numBigBlockDepotBlocks;
/*  113: 275 */     if (this.numBigBlockDepotBlocks > blockChainLength - 1)
/*  114:     */     {
/*  115: 279 */       this.extensionBlock = 0;
/*  116:     */       
/*  117:     */ 
/*  118: 282 */       int bbdBlocksLeft = this.numBigBlockDepotBlocks - blockChainLength + 1;
/*  119:     */       
/*  120: 284 */       this.numExtensionBlocks = ((int)Math.ceil(bbdBlocksLeft / 127.0D));
/*  121:     */       
/*  122:     */ 
/*  123:     */ 
/*  124:     */ 
/*  125: 289 */       totalBlocks = startTotalBlocks + this.numExtensionBlocks + this.numBigBlockDepotBlocks;
/*  126:     */       
/*  127:     */ 
/*  128: 292 */       this.numBigBlockDepotBlocks = ((int)Math.ceil(totalBlocks / 128.0D));
/*  129:     */       
/*  130:     */ 
/*  131:     */ 
/*  132: 296 */       totalBlocks = startTotalBlocks + this.numExtensionBlocks + this.numBigBlockDepotBlocks;
/*  133:     */     }
/*  134:     */     else
/*  135:     */     {
/*  136: 302 */       this.extensionBlock = -2;
/*  137: 303 */       this.numExtensionBlocks = 0;
/*  138:     */     }
/*  139: 308 */     this.excelDataStartBlock = this.numExtensionBlocks;
/*  140:     */     
/*  141:     */ 
/*  142: 311 */     this.sbdStartBlock = -2;
/*  143: 312 */     if ((this.additionalPropertySets != null) && (this.numSmallBlockDepotBlocks != 0)) {
/*  144: 314 */       this.sbdStartBlock = (this.excelDataStartBlock + this.excelDataBlocks + this.additionalPropertyBlocks + 16);
/*  145:     */     }
/*  146: 322 */     this.sbdStartBlockChain = -2;
/*  147: 324 */     if (this.sbdStartBlock != -2) {
/*  148: 326 */       this.sbdStartBlockChain = (this.sbdStartBlock + this.numSmallBlockDepotBlocks);
/*  149:     */     }
/*  150: 330 */     if (this.sbdStartBlockChain != -2) {
/*  151: 332 */       this.bbdStartBlock = (this.sbdStartBlockChain + this.numSmallBlockDepotChainBlocks);
/*  152:     */     } else {
/*  153: 337 */       this.bbdStartBlock = (this.excelDataStartBlock + this.excelDataBlocks + this.additionalPropertyBlocks + 16);
/*  154:     */     }
/*  155: 344 */     this.rootStartBlock = (this.bbdStartBlock + this.numBigBlockDepotBlocks);
/*  156: 348 */     if (totalBlocks != this.rootStartBlock + this.numRootEntryBlocks)
/*  157:     */     {
/*  158: 350 */       logger.warn("Root start block and total blocks are inconsistent  generated file may be corrupt");
/*  159:     */       
/*  160: 352 */       logger.warn("RootStartBlock " + this.rootStartBlock + " totalBlocks " + totalBlocks);
/*  161:     */     }
/*  162:     */   }
/*  163:     */   
/*  164:     */   private void readAdditionalPropertySets(jxl.read.biff.CompoundFile readCompoundFile)
/*  165:     */     throws CopyAdditionalPropertySetsException, IOException
/*  166:     */   {
/*  167: 367 */     if (readCompoundFile == null) {
/*  168: 369 */       return;
/*  169:     */     }
/*  170: 372 */     this.additionalPropertySets = new ArrayList();
/*  171: 373 */     this.standardPropertySets = new HashMap();
/*  172: 374 */     int blocksRequired = 0;
/*  173:     */     
/*  174: 376 */     int numPropertySets = readCompoundFile.getNumberOfPropertySets();
/*  175: 378 */     for (int i = 0; i < numPropertySets; i++)
/*  176:     */     {
/*  177: 380 */       BaseCompoundFile.PropertyStorage ps = readCompoundFile.getPropertySet(i);
/*  178:     */       
/*  179: 382 */       boolean standard = false;
/*  180: 384 */       if (ps.name.equalsIgnoreCase("Root Entry"))
/*  181:     */       {
/*  182: 386 */         standard = true;
/*  183: 387 */         ReadPropertyStorage rps = new ReadPropertyStorage(ps, null, i);
/*  184: 388 */         this.standardPropertySets.put("Root Entry", rps);
/*  185:     */       }
/*  186: 392 */       for (int j = 0; (j < STANDARD_PROPERTY_SETS.length) && (!standard); j++) {
/*  187: 394 */         if (ps.name.equalsIgnoreCase(STANDARD_PROPERTY_SETS[j]))
/*  188:     */         {
/*  189: 397 */           BaseCompoundFile.PropertyStorage ps2 = readCompoundFile.findPropertyStorage(ps.name);
/*  190: 398 */           Assert.verify(ps2 != null);
/*  191: 400 */           if (ps2 == ps)
/*  192:     */           {
/*  193: 402 */             standard = true;
/*  194: 403 */             ReadPropertyStorage rps = new ReadPropertyStorage(ps, null, i);
/*  195: 404 */             this.standardPropertySets.put(STANDARD_PROPERTY_SETS[j], rps);
/*  196:     */           }
/*  197:     */         }
/*  198:     */       }
/*  199: 409 */       if (!standard) {
/*  200:     */         try
/*  201:     */         {
/*  202: 413 */           byte[] data = null;
/*  203: 414 */           if (ps.size > 0) {
/*  204: 416 */             data = readCompoundFile.getStream(i);
/*  205:     */           } else {
/*  206: 420 */             data = new byte[0];
/*  207:     */           }
/*  208: 422 */           ReadPropertyStorage rps = new ReadPropertyStorage(ps, data, i);
/*  209: 423 */           this.additionalPropertySets.add(rps);
/*  210: 425 */           if (data.length > 4096)
/*  211:     */           {
/*  212: 427 */             int blocks = getBigBlocksRequired(data.length);
/*  213: 428 */             blocksRequired += blocks;
/*  214:     */           }
/*  215:     */           else
/*  216:     */           {
/*  217: 432 */             int blocks = getSmallBlocksRequired(data.length);
/*  218: 433 */             this.numSmallBlocks += blocks;
/*  219:     */           }
/*  220:     */         }
/*  221:     */         catch (BiffException e)
/*  222:     */         {
/*  223: 438 */           logger.error(e);
/*  224: 439 */           throw new CopyAdditionalPropertySetsException();
/*  225:     */         }
/*  226:     */       }
/*  227:     */     }
/*  228: 444 */     this.additionalPropertyBlocks = blocksRequired;
/*  229:     */   }
/*  230:     */   
/*  231:     */   public void write()
/*  232:     */     throws IOException
/*  233:     */   {
/*  234: 454 */     writeHeader();
/*  235: 455 */     writeExcelData();
/*  236: 456 */     writeDocumentSummaryData();
/*  237: 457 */     writeSummaryData();
/*  238: 458 */     writeAdditionalPropertySets();
/*  239: 459 */     writeSmallBlockDepot();
/*  240: 460 */     writeSmallBlockDepotChain();
/*  241: 461 */     writeBigBlockDepot();
/*  242: 462 */     writePropertySets();
/*  243:     */   }
/*  244:     */   
/*  245:     */   private void writeAdditionalPropertySets()
/*  246:     */     throws IOException
/*  247:     */   {
/*  248: 473 */     if (this.additionalPropertySets == null) {
/*  249: 475 */       return;
/*  250:     */     }
/*  251: 478 */     for (Iterator i = this.additionalPropertySets.iterator(); i.hasNext();)
/*  252:     */     {
/*  253: 480 */       ReadPropertyStorage rps = (ReadPropertyStorage)i.next();
/*  254: 481 */       byte[] data = rps.data;
/*  255: 483 */       if (data.length > 4096)
/*  256:     */       {
/*  257: 485 */         int numBlocks = getBigBlocksRequired(data.length);
/*  258: 486 */         int requiredSize = numBlocks * 512;
/*  259:     */         
/*  260: 488 */         this.out.write(data, 0, data.length);
/*  261:     */         
/*  262: 490 */         byte[] padding = new byte[requiredSize - data.length];
/*  263: 491 */         this.out.write(padding, 0, padding.length);
/*  264:     */       }
/*  265:     */     }
/*  266:     */   }
/*  267:     */   
/*  268:     */   private void writeExcelData()
/*  269:     */     throws IOException
/*  270:     */   {
/*  271: 505 */     this.excelData.writeData(this.out);
/*  272:     */     
/*  273: 507 */     byte[] padding = new byte[this.requiredSize - this.size];
/*  274: 508 */     this.out.write(padding);
/*  275:     */   }
/*  276:     */   
/*  277:     */   private void writeDocumentSummaryData()
/*  278:     */     throws IOException
/*  279:     */   {
/*  280: 518 */     byte[] padding = new byte[4096];
/*  281:     */     
/*  282:     */ 
/*  283: 521 */     this.out.write(padding);
/*  284:     */   }
/*  285:     */   
/*  286:     */   private void writeSummaryData()
/*  287:     */     throws IOException
/*  288:     */   {
/*  289: 531 */     byte[] padding = new byte[4096];
/*  290:     */     
/*  291:     */ 
/*  292: 534 */     this.out.write(padding);
/*  293:     */   }
/*  294:     */   
/*  295:     */   private void writeHeader()
/*  296:     */     throws IOException
/*  297:     */   {
/*  298: 545 */     byte[] headerBlock = new byte[512];
/*  299: 546 */     byte[] extensionBlockData = new byte[512 * this.numExtensionBlocks];
/*  300:     */     
/*  301:     */ 
/*  302: 549 */     System.arraycopy(IDENTIFIER, 0, headerBlock, 0, IDENTIFIER.length);
/*  303:     */     
/*  304:     */ 
/*  305: 552 */     headerBlock[24] = 62;
/*  306: 553 */     headerBlock[26] = 3;
/*  307: 554 */     headerBlock[28] = -2;
/*  308: 555 */     headerBlock[29] = -1;
/*  309: 556 */     headerBlock[30] = 9;
/*  310: 557 */     headerBlock[32] = 6;
/*  311: 558 */     headerBlock[57] = 16;
/*  312:     */     
/*  313:     */ 
/*  314: 561 */     IntegerHelper.getFourBytes(this.numBigBlockDepotBlocks, headerBlock, 44);
/*  315:     */     
/*  316:     */ 
/*  317:     */ 
/*  318:     */ 
/*  319: 566 */     IntegerHelper.getFourBytes(this.sbdStartBlockChain, headerBlock, 60);
/*  320:     */     
/*  321:     */ 
/*  322:     */ 
/*  323:     */ 
/*  324: 571 */     IntegerHelper.getFourBytes(this.numSmallBlockDepotChainBlocks, headerBlock, 64);
/*  325:     */     
/*  326:     */ 
/*  327:     */ 
/*  328:     */ 
/*  329: 576 */     IntegerHelper.getFourBytes(this.extensionBlock, headerBlock, 68);
/*  330:     */     
/*  331:     */ 
/*  332:     */ 
/*  333:     */ 
/*  334: 581 */     IntegerHelper.getFourBytes(this.numExtensionBlocks, headerBlock, 72);
/*  335:     */     
/*  336:     */ 
/*  337:     */ 
/*  338:     */ 
/*  339: 586 */     IntegerHelper.getFourBytes(this.rootStartBlock, headerBlock, 48);
/*  340:     */     
/*  341:     */ 
/*  342:     */ 
/*  343:     */ 
/*  344:     */ 
/*  345: 592 */     int pos = 76;
/*  346:     */     
/*  347:     */ 
/*  348: 595 */     int blocksToWrite = Math.min(this.numBigBlockDepotBlocks, 109);
/*  349:     */     
/*  350:     */ 
/*  351: 598 */     int blocksWritten = 0;
/*  352: 600 */     for (int i = 0; i < blocksToWrite; i++)
/*  353:     */     {
/*  354: 602 */       IntegerHelper.getFourBytes(this.bbdStartBlock + i, headerBlock, pos);
/*  355:     */       
/*  356:     */ 
/*  357: 605 */       pos += 4;
/*  358: 606 */       blocksWritten++;
/*  359:     */     }
/*  360: 610 */     for (int i = pos; i < 512; i++) {
/*  361: 612 */       headerBlock[i] = -1;
/*  362:     */     }
/*  363: 615 */     this.out.write(headerBlock);
/*  364:     */     
/*  365:     */ 
/*  366: 618 */     pos = 0;
/*  367: 620 */     for (int extBlock = 0; extBlock < this.numExtensionBlocks; extBlock++)
/*  368:     */     {
/*  369: 622 */       blocksToWrite = Math.min(this.numBigBlockDepotBlocks - blocksWritten, 127);
/*  370: 625 */       for (int j = 0; j < blocksToWrite; j++)
/*  371:     */       {
/*  372: 627 */         IntegerHelper.getFourBytes(this.bbdStartBlock + blocksWritten + j, extensionBlockData, pos);
/*  373:     */         
/*  374:     */ 
/*  375: 630 */         pos += 4;
/*  376:     */       }
/*  377: 633 */       blocksWritten += blocksToWrite;
/*  378:     */       
/*  379:     */ 
/*  380: 636 */       int nextBlock = blocksWritten == this.numBigBlockDepotBlocks ? -2 : extBlock + 1;
/*  381:     */       
/*  382: 638 */       IntegerHelper.getFourBytes(nextBlock, extensionBlockData, pos);
/*  383: 639 */       pos += 4;
/*  384:     */     }
/*  385: 642 */     if (this.numExtensionBlocks > 0)
/*  386:     */     {
/*  387: 645 */       for (int i = pos; i < extensionBlockData.length; i++) {
/*  388: 647 */         extensionBlockData[i] = -1;
/*  389:     */       }
/*  390: 650 */       this.out.write(extensionBlockData);
/*  391:     */     }
/*  392:     */   }
/*  393:     */   
/*  394:     */   private void checkBbdPos()
/*  395:     */     throws IOException
/*  396:     */   {
/*  397: 662 */     if (this.bbdPos >= 512)
/*  398:     */     {
/*  399: 665 */       this.out.write(this.bigBlockDepot);
/*  400:     */       
/*  401:     */ 
/*  402: 668 */       this.bigBlockDepot = new byte[512];
/*  403: 669 */       this.bbdPos = 0;
/*  404:     */     }
/*  405:     */   }
/*  406:     */   
/*  407:     */   private void writeBlockChain(int startBlock, int numBlocks)
/*  408:     */     throws IOException
/*  409:     */   {
/*  410: 683 */     int blocksToWrite = numBlocks - 1;
/*  411: 684 */     int blockNumber = startBlock + 1;
/*  412: 686 */     while (blocksToWrite > 0)
/*  413:     */     {
/*  414: 688 */       int bbdBlocks = Math.min(blocksToWrite, (512 - this.bbdPos) / 4);
/*  415: 690 */       for (int i = 0; i < bbdBlocks; i++)
/*  416:     */       {
/*  417: 692 */         IntegerHelper.getFourBytes(blockNumber, this.bigBlockDepot, this.bbdPos);
/*  418: 693 */         this.bbdPos += 4;
/*  419: 694 */         blockNumber++;
/*  420:     */       }
/*  421: 697 */       blocksToWrite -= bbdBlocks;
/*  422: 698 */       checkBbdPos();
/*  423:     */     }
/*  424: 702 */     IntegerHelper.getFourBytes(-2, this.bigBlockDepot, this.bbdPos);
/*  425: 703 */     this.bbdPos += 4;
/*  426: 704 */     checkBbdPos();
/*  427:     */   }
/*  428:     */   
/*  429:     */   private void writeAdditionalPropertySetBlockChains()
/*  430:     */     throws IOException
/*  431:     */   {
/*  432: 714 */     if (this.additionalPropertySets == null) {
/*  433: 716 */       return;
/*  434:     */     }
/*  435: 719 */     int blockNumber = this.excelDataStartBlock + this.excelDataBlocks + 16;
/*  436: 720 */     for (Iterator i = this.additionalPropertySets.iterator(); i.hasNext();)
/*  437:     */     {
/*  438: 722 */       ReadPropertyStorage rps = (ReadPropertyStorage)i.next();
/*  439: 723 */       if (rps.data.length > 4096)
/*  440:     */       {
/*  441: 725 */         int numBlocks = getBigBlocksRequired(rps.data.length);
/*  442:     */         
/*  443: 727 */         writeBlockChain(blockNumber, numBlocks);
/*  444: 728 */         blockNumber += numBlocks;
/*  445:     */       }
/*  446:     */     }
/*  447:     */   }
/*  448:     */   
/*  449:     */   private void writeSmallBlockDepotChain()
/*  450:     */     throws IOException
/*  451:     */   {
/*  452: 738 */     if (this.sbdStartBlockChain == -2) {
/*  453: 740 */       return;
/*  454:     */     }
/*  455: 743 */     byte[] smallBlockDepotChain = new byte[this.numSmallBlockDepotChainBlocks * 512];
/*  456:     */     
/*  457:     */ 
/*  458: 746 */     int pos = 0;
/*  459: 747 */     int sbdBlockNumber = 1;
/*  460: 749 */     for (Iterator i = this.additionalPropertySets.iterator(); i.hasNext();)
/*  461:     */     {
/*  462: 751 */       ReadPropertyStorage rps = (ReadPropertyStorage)i.next();
/*  463: 753 */       if ((rps.data.length <= 4096) && (rps.data.length != 0))
/*  464:     */       {
/*  465: 756 */         int numSmallBlocks = getSmallBlocksRequired(rps.data.length);
/*  466: 757 */         for (int j = 0; j < numSmallBlocks - 1; j++)
/*  467:     */         {
/*  468: 759 */           IntegerHelper.getFourBytes(sbdBlockNumber, smallBlockDepotChain, pos);
/*  469:     */           
/*  470:     */ 
/*  471: 762 */           pos += 4;
/*  472: 763 */           sbdBlockNumber++;
/*  473:     */         }
/*  474: 767 */         IntegerHelper.getFourBytes(-2, smallBlockDepotChain, pos);
/*  475: 768 */         pos += 4;
/*  476: 769 */         sbdBlockNumber++;
/*  477:     */       }
/*  478:     */     }
/*  479: 773 */     this.out.write(smallBlockDepotChain);
/*  480:     */   }
/*  481:     */   
/*  482:     */   private void writeSmallBlockDepot()
/*  483:     */     throws IOException
/*  484:     */   {
/*  485: 783 */     if (this.additionalPropertySets == null) {
/*  486: 785 */       return;
/*  487:     */     }
/*  488: 788 */     byte[] smallBlockDepot = new byte[this.numSmallBlockDepotBlocks * 512];
/*  489:     */     
/*  490:     */ 
/*  491: 791 */     int pos = 0;
/*  492: 793 */     for (Iterator i = this.additionalPropertySets.iterator(); i.hasNext();)
/*  493:     */     {
/*  494: 795 */       ReadPropertyStorage rps = (ReadPropertyStorage)i.next();
/*  495: 797 */       if (rps.data.length <= 4096)
/*  496:     */       {
/*  497: 799 */         int smallBlocks = getSmallBlocksRequired(rps.data.length);
/*  498: 800 */         int length = smallBlocks * 64;
/*  499: 801 */         System.arraycopy(rps.data, 0, smallBlockDepot, pos, rps.data.length);
/*  500: 802 */         pos += length;
/*  501:     */       }
/*  502:     */     }
/*  503: 806 */     this.out.write(smallBlockDepot);
/*  504:     */   }
/*  505:     */   
/*  506:     */   private void writeBigBlockDepot()
/*  507:     */     throws IOException
/*  508:     */   {
/*  509: 818 */     this.bigBlockDepot = new byte[512];
/*  510: 819 */     this.bbdPos = 0;
/*  511: 822 */     for (int i = 0; i < this.numExtensionBlocks; i++)
/*  512:     */     {
/*  513: 824 */       IntegerHelper.getFourBytes(-3, this.bigBlockDepot, this.bbdPos);
/*  514: 825 */       this.bbdPos += 4;
/*  515: 826 */       checkBbdPos();
/*  516:     */     }
/*  517: 829 */     writeBlockChain(this.excelDataStartBlock, this.excelDataBlocks);
/*  518:     */     
/*  519:     */ 
/*  520:     */ 
/*  521:     */ 
/*  522: 834 */     int summaryInfoBlock = this.excelDataStartBlock + this.excelDataBlocks + this.additionalPropertyBlocks;
/*  523: 838 */     for (int i = summaryInfoBlock; i < summaryInfoBlock + 7; i++)
/*  524:     */     {
/*  525: 840 */       IntegerHelper.getFourBytes(i + 1, this.bigBlockDepot, this.bbdPos);
/*  526: 841 */       this.bbdPos += 4;
/*  527: 842 */       checkBbdPos();
/*  528:     */     }
/*  529: 846 */     IntegerHelper.getFourBytes(-2, this.bigBlockDepot, this.bbdPos);
/*  530: 847 */     this.bbdPos += 4;
/*  531: 848 */     checkBbdPos();
/*  532: 851 */     for (int i = summaryInfoBlock + 8; i < summaryInfoBlock + 15; i++)
/*  533:     */     {
/*  534: 853 */       IntegerHelper.getFourBytes(i + 1, this.bigBlockDepot, this.bbdPos);
/*  535: 854 */       this.bbdPos += 4;
/*  536: 855 */       checkBbdPos();
/*  537:     */     }
/*  538: 859 */     IntegerHelper.getFourBytes(-2, this.bigBlockDepot, this.bbdPos);
/*  539: 860 */     this.bbdPos += 4;
/*  540: 861 */     checkBbdPos();
/*  541:     */     
/*  542:     */ 
/*  543: 864 */     writeAdditionalPropertySetBlockChains();
/*  544: 866 */     if (this.sbdStartBlock != -2)
/*  545:     */     {
/*  546: 869 */       writeBlockChain(this.sbdStartBlock, this.numSmallBlockDepotBlocks);
/*  547:     */       
/*  548:     */ 
/*  549: 872 */       writeBlockChain(this.sbdStartBlockChain, this.numSmallBlockDepotChainBlocks);
/*  550:     */     }
/*  551: 877 */     for (int i = 0; i < this.numBigBlockDepotBlocks; i++)
/*  552:     */     {
/*  553: 879 */       IntegerHelper.getFourBytes(-3, this.bigBlockDepot, this.bbdPos);
/*  554: 880 */       this.bbdPos += 4;
/*  555: 881 */       checkBbdPos();
/*  556:     */     }
/*  557: 885 */     writeBlockChain(this.rootStartBlock, this.numRootEntryBlocks);
/*  558: 888 */     if (this.bbdPos != 0)
/*  559:     */     {
/*  560: 890 */       for (int i = this.bbdPos; i < 512; i++) {
/*  561: 892 */         this.bigBlockDepot[i] = -1;
/*  562:     */       }
/*  563: 894 */       this.out.write(this.bigBlockDepot);
/*  564:     */     }
/*  565:     */   }
/*  566:     */   
/*  567:     */   private int getBigBlocksRequired(int length)
/*  568:     */   {
/*  569: 907 */     int blocks = length / 512;
/*  570:     */     
/*  571: 909 */     return length % 512 > 0 ? blocks + 1 : blocks;
/*  572:     */   }
/*  573:     */   
/*  574:     */   private int getSmallBlocksRequired(int length)
/*  575:     */   {
/*  576: 921 */     int blocks = length / 64;
/*  577:     */     
/*  578: 923 */     return length % 64 > 0 ? blocks + 1 : blocks;
/*  579:     */   }
/*  580:     */   
/*  581:     */   private void writePropertySets()
/*  582:     */     throws IOException
/*  583:     */   {
/*  584: 933 */     byte[] propertySetStorage = new byte[512 * this.numRootEntryBlocks];
/*  585:     */     
/*  586: 935 */     int pos = 0;
/*  587: 936 */     int[] mappings = null;
/*  588:     */     int newMapping;
/*  589:     */     Iterator i;
/*  590: 939 */     if (this.additionalPropertySets != null)
/*  591:     */     {
/*  592: 941 */       mappings = new int[this.numPropertySets];
/*  593: 944 */       for (int i = 0; i < STANDARD_PROPERTY_SETS.length; i++)
/*  594:     */       {
/*  595: 946 */         ReadPropertyStorage rps = (ReadPropertyStorage)this.standardPropertySets.get(STANDARD_PROPERTY_SETS[i]);
/*  596: 949 */         if (rps != null) {
/*  597: 951 */           mappings[rps.number] = i;
/*  598:     */         } else {
/*  599: 955 */           logger.warn("Standard property set " + STANDARD_PROPERTY_SETS[i] + " not present in source file");
/*  600:     */         }
/*  601:     */       }
/*  602: 961 */       newMapping = STANDARD_PROPERTY_SETS.length;
/*  603: 962 */       for (i = this.additionalPropertySets.iterator(); i.hasNext();)
/*  604:     */       {
/*  605: 964 */         ReadPropertyStorage rps = (ReadPropertyStorage)i.next();
/*  606: 965 */         mappings[rps.number] = newMapping;
/*  607: 966 */         newMapping++;
/*  608:     */       }
/*  609:     */     }
/*  610: 970 */     int child = 0;
/*  611: 971 */     int previous = 0;
/*  612: 972 */     int next = 0;
/*  613:     */     
/*  614:     */ 
/*  615: 975 */     int size = 0;
/*  616:     */     Iterator i;
/*  617: 977 */     if (this.additionalPropertySets != null)
/*  618:     */     {
/*  619: 980 */       size += getBigBlocksRequired(this.requiredSize) * 512;
/*  620:     */       
/*  621:     */ 
/*  622: 983 */       size += getBigBlocksRequired(4096) * 512;
/*  623: 984 */       size += getBigBlocksRequired(4096) * 512;
/*  624: 987 */       for (i = this.additionalPropertySets.iterator(); i.hasNext();)
/*  625:     */       {
/*  626: 989 */         ReadPropertyStorage rps = (ReadPropertyStorage)i.next();
/*  627: 990 */         if (rps.propertyStorage.type != 1) {
/*  628: 992 */           if (rps.propertyStorage.size >= 4096) {
/*  629: 994 */             size += getBigBlocksRequired(rps.propertyStorage.size) * 512;
/*  630:     */           } else {
/*  631: 999 */             size += getSmallBlocksRequired(rps.propertyStorage.size) * 64;
/*  632:     */           }
/*  633:     */         }
/*  634:     */       }
/*  635:     */     }
/*  636:1007 */     BaseCompoundFile.PropertyStorage ps = new BaseCompoundFile.PropertyStorage(this, "Root Entry");
/*  637:1008 */     ps.setType(5);
/*  638:1009 */     ps.setStartBlock(this.sbdStartBlock);
/*  639:1010 */     ps.setSize(size);
/*  640:1011 */     ps.setPrevious(-1);
/*  641:1012 */     ps.setNext(-1);
/*  642:1013 */     ps.setColour(0);
/*  643:     */     
/*  644:1015 */     child = 1;
/*  645:1016 */     if (this.additionalPropertySets != null)
/*  646:     */     {
/*  647:1018 */       ReadPropertyStorage rps = (ReadPropertyStorage)this.standardPropertySets.get("Root Entry");
/*  648:     */       
/*  649:1020 */       child = mappings[rps.propertyStorage.child];
/*  650:     */     }
/*  651:1022 */     ps.setChild(child);
/*  652:     */     
/*  653:1024 */     System.arraycopy(ps.data, 0, propertySetStorage, pos, 128);
/*  654:     */     
/*  655:     */ 
/*  656:1027 */     pos += 128;
/*  657:     */     
/*  658:     */ 
/*  659:     */ 
/*  660:1031 */     ps = new BaseCompoundFile.PropertyStorage(this, "Workbook");
/*  661:1032 */     ps.setType(2);
/*  662:1033 */     ps.setStartBlock(this.excelDataStartBlock);
/*  663:     */     
/*  664:1035 */     ps.setSize(this.requiredSize);
/*  665:     */     
/*  666:     */ 
/*  667:     */ 
/*  668:1039 */     previous = 3;
/*  669:1040 */     next = -1;
/*  670:1042 */     if (this.additionalPropertySets != null)
/*  671:     */     {
/*  672:1044 */       ReadPropertyStorage rps = (ReadPropertyStorage)this.standardPropertySets.get("Workbook");
/*  673:     */       
/*  674:1046 */       previous = rps.propertyStorage.previous != -1 ? mappings[rps.propertyStorage.previous] : -1;
/*  675:     */       
/*  676:1048 */       next = rps.propertyStorage.next != -1 ? mappings[rps.propertyStorage.next] : -1;
/*  677:     */     }
/*  678:1052 */     ps.setPrevious(previous);
/*  679:1053 */     ps.setNext(next);
/*  680:1054 */     ps.setChild(-1);
/*  681:     */     
/*  682:1056 */     System.arraycopy(ps.data, 0, propertySetStorage, pos, 128);
/*  683:     */     
/*  684:     */ 
/*  685:1059 */     pos += 128;
/*  686:     */     
/*  687:     */ 
/*  688:1062 */     ps = new BaseCompoundFile.PropertyStorage(this, "\005SummaryInformation");
/*  689:1063 */     ps.setType(2);
/*  690:1064 */     ps.setStartBlock(this.excelDataStartBlock + this.excelDataBlocks);
/*  691:1065 */     ps.setSize(4096);
/*  692:     */     
/*  693:1067 */     previous = 1;
/*  694:1068 */     next = 3;
/*  695:1070 */     if (this.additionalPropertySets != null)
/*  696:     */     {
/*  697:1072 */       ReadPropertyStorage rps = (ReadPropertyStorage)this.standardPropertySets.get("\005SummaryInformation");
/*  698:1075 */       if (rps != null)
/*  699:     */       {
/*  700:1077 */         previous = rps.propertyStorage.previous != -1 ? mappings[rps.propertyStorage.previous] : -1;
/*  701:     */         
/*  702:1079 */         next = rps.propertyStorage.next != -1 ? mappings[rps.propertyStorage.next] : -1;
/*  703:     */       }
/*  704:     */     }
/*  705:1084 */     ps.setPrevious(previous);
/*  706:1085 */     ps.setNext(next);
/*  707:1086 */     ps.setChild(-1);
/*  708:     */     
/*  709:1088 */     System.arraycopy(ps.data, 0, propertySetStorage, pos, 128);
/*  710:     */     
/*  711:     */ 
/*  712:1091 */     pos += 128;
/*  713:     */     
/*  714:     */ 
/*  715:1094 */     ps = new BaseCompoundFile.PropertyStorage(this, "\005DocumentSummaryInformation");
/*  716:1095 */     ps.setType(2);
/*  717:1096 */     ps.setStartBlock(this.excelDataStartBlock + this.excelDataBlocks + 8);
/*  718:1097 */     ps.setSize(4096);
/*  719:1098 */     ps.setPrevious(-1);
/*  720:1099 */     ps.setNext(-1);
/*  721:1100 */     ps.setChild(-1);
/*  722:     */     
/*  723:1102 */     System.arraycopy(ps.data, 0, propertySetStorage, pos, 128);
/*  724:     */     
/*  725:     */ 
/*  726:1105 */     pos += 128;
/*  727:1110 */     if (this.additionalPropertySets == null)
/*  728:     */     {
/*  729:1112 */       this.out.write(propertySetStorage);
/*  730:1113 */       return;
/*  731:     */     }
/*  732:1116 */     int bigBlock = this.excelDataStartBlock + this.excelDataBlocks + 16;
/*  733:1117 */     int smallBlock = 0;
/*  734:1119 */     for (Iterator i = this.additionalPropertySets.iterator(); i.hasNext();)
/*  735:     */     {
/*  736:1121 */       ReadPropertyStorage rps = (ReadPropertyStorage)i.next();
/*  737:     */       
/*  738:1123 */       int block = rps.data.length > 4096 ? bigBlock : smallBlock;
/*  739:     */       
/*  740:     */ 
/*  741:1126 */       ps = new BaseCompoundFile.PropertyStorage(this, rps.propertyStorage.name);
/*  742:1127 */       ps.setType(rps.propertyStorage.type);
/*  743:1128 */       ps.setStartBlock(block);
/*  744:1129 */       ps.setSize(rps.propertyStorage.size);
/*  745:     */       
/*  746:     */ 
/*  747:1132 */       previous = rps.propertyStorage.previous != -1 ? mappings[rps.propertyStorage.previous] : -1;
/*  748:     */       
/*  749:1134 */       next = rps.propertyStorage.next != -1 ? mappings[rps.propertyStorage.next] : -1;
/*  750:     */       
/*  751:1136 */       child = rps.propertyStorage.child != -1 ? mappings[rps.propertyStorage.child] : -1;
/*  752:     */       
/*  753:     */ 
/*  754:1139 */       ps.setPrevious(previous);
/*  755:1140 */       ps.setNext(next);
/*  756:1141 */       ps.setChild(child);
/*  757:     */       
/*  758:1143 */       System.arraycopy(ps.data, 0, propertySetStorage, pos, 128);
/*  759:     */       
/*  760:     */ 
/*  761:1146 */       pos += 128;
/*  762:1148 */       if (rps.data.length > 4096) {
/*  763:1150 */         bigBlock += getBigBlocksRequired(rps.data.length);
/*  764:     */       } else {
/*  765:1154 */         smallBlock += getSmallBlocksRequired(rps.data.length);
/*  766:     */       }
/*  767:     */     }
/*  768:1158 */     this.out.write(propertySetStorage);
/*  769:     */   }
/*  770:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.CompoundFile
 * JD-Core Version:    0.7.0.1
 */