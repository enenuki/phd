/*    1:     */ package org.apache.xml.serializer.utils;
/*    2:     */ 
/*    3:     */ import java.util.Arrays;
/*    4:     */ 
/*    5:     */ public class XMLChar
/*    6:     */ {
/*    7:  56 */   private static final byte[] CHARS = new byte[65536];
/*    8:     */   public static final int MASK_VALID = 1;
/*    9:     */   public static final int MASK_SPACE = 2;
/*   10:     */   public static final int MASK_NAME_START = 4;
/*   11:     */   public static final int MASK_NAME = 8;
/*   12:     */   public static final int MASK_PUBID = 16;
/*   13:     */   public static final int MASK_CONTENT = 32;
/*   14:     */   public static final int MASK_NCNAME_START = 64;
/*   15:     */   public static final int MASK_NCNAME = 128;
/*   16:     */   
/*   17:     */   static
/*   18:     */   {
/*   19:  98 */     CHARS[9] = 35;
/*   20:  99 */     CHARS[10] = 19;
/*   21: 100 */     CHARS[13] = 19;
/*   22: 101 */     CHARS[32] = 51;
/*   23: 102 */     CHARS[33] = 49;
/*   24: 103 */     CHARS[34] = 33;
/*   25: 104 */     Arrays.fill(CHARS, 35, 38, (byte)49);
/*   26: 105 */     CHARS[38] = 1;
/*   27: 106 */     Arrays.fill(CHARS, 39, 45, (byte)49);
/*   28: 107 */     Arrays.fill(CHARS, 45, 47, (byte)-71);
/*   29: 108 */     CHARS[47] = 49;
/*   30: 109 */     Arrays.fill(CHARS, 48, 58, (byte)-71);
/*   31: 110 */     CHARS[58] = 61;
/*   32: 111 */     CHARS[59] = 49;
/*   33: 112 */     CHARS[60] = 1;
/*   34: 113 */     CHARS[61] = 49;
/*   35: 114 */     CHARS[62] = 33;
/*   36: 115 */     Arrays.fill(CHARS, 63, 65, (byte)49);
/*   37: 116 */     Arrays.fill(CHARS, 65, 91, (byte)-3);
/*   38: 117 */     Arrays.fill(CHARS, 91, 93, (byte)33);
/*   39: 118 */     CHARS[93] = 1;
/*   40: 119 */     CHARS[94] = 33;
/*   41: 120 */     CHARS[95] = -3;
/*   42: 121 */     CHARS[96] = 33;
/*   43: 122 */     Arrays.fill(CHARS, 97, 123, (byte)-3);
/*   44: 123 */     Arrays.fill(CHARS, 123, 183, (byte)33);
/*   45: 124 */     CHARS['·'] = -87;
/*   46: 125 */     Arrays.fill(CHARS, 184, 192, (byte)33);
/*   47: 126 */     Arrays.fill(CHARS, 192, 215, (byte)-19);
/*   48: 127 */     CHARS['×'] = 33;
/*   49: 128 */     Arrays.fill(CHARS, 216, 247, (byte)-19);
/*   50: 129 */     CHARS['÷'] = 33;
/*   51: 130 */     Arrays.fill(CHARS, 248, 306, (byte)-19);
/*   52: 131 */     Arrays.fill(CHARS, 306, 308, (byte)33);
/*   53: 132 */     Arrays.fill(CHARS, 308, 319, (byte)-19);
/*   54: 133 */     Arrays.fill(CHARS, 319, 321, (byte)33);
/*   55: 134 */     Arrays.fill(CHARS, 321, 329, (byte)-19);
/*   56: 135 */     CHARS[329] = 33;
/*   57: 136 */     Arrays.fill(CHARS, 330, 383, (byte)-19);
/*   58: 137 */     CHARS[383] = 33;
/*   59: 138 */     Arrays.fill(CHARS, 384, 452, (byte)-19);
/*   60: 139 */     Arrays.fill(CHARS, 452, 461, (byte)33);
/*   61: 140 */     Arrays.fill(CHARS, 461, 497, (byte)-19);
/*   62: 141 */     Arrays.fill(CHARS, 497, 500, (byte)33);
/*   63: 142 */     Arrays.fill(CHARS, 500, 502, (byte)-19);
/*   64: 143 */     Arrays.fill(CHARS, 502, 506, (byte)33);
/*   65: 144 */     Arrays.fill(CHARS, 506, 536, (byte)-19);
/*   66: 145 */     Arrays.fill(CHARS, 536, 592, (byte)33);
/*   67: 146 */     Arrays.fill(CHARS, 592, 681, (byte)-19);
/*   68: 147 */     Arrays.fill(CHARS, 681, 699, (byte)33);
/*   69: 148 */     Arrays.fill(CHARS, 699, 706, (byte)-19);
/*   70: 149 */     Arrays.fill(CHARS, 706, 720, (byte)33);
/*   71: 150 */     Arrays.fill(CHARS, 720, 722, (byte)-87);
/*   72: 151 */     Arrays.fill(CHARS, 722, 768, (byte)33);
/*   73: 152 */     Arrays.fill(CHARS, 768, 838, (byte)-87);
/*   74: 153 */     Arrays.fill(CHARS, 838, 864, (byte)33);
/*   75: 154 */     Arrays.fill(CHARS, 864, 866, (byte)-87);
/*   76: 155 */     Arrays.fill(CHARS, 866, 902, (byte)33);
/*   77: 156 */     CHARS[902] = -19;
/*   78: 157 */     CHARS[903] = -87;
/*   79: 158 */     Arrays.fill(CHARS, 904, 907, (byte)-19);
/*   80: 159 */     CHARS[907] = 33;
/*   81: 160 */     CHARS[908] = -19;
/*   82: 161 */     CHARS[909] = 33;
/*   83: 162 */     Arrays.fill(CHARS, 910, 930, (byte)-19);
/*   84: 163 */     CHARS[930] = 33;
/*   85: 164 */     Arrays.fill(CHARS, 931, 975, (byte)-19);
/*   86: 165 */     CHARS[975] = 33;
/*   87: 166 */     Arrays.fill(CHARS, 976, 983, (byte)-19);
/*   88: 167 */     Arrays.fill(CHARS, 983, 986, (byte)33);
/*   89: 168 */     CHARS[986] = -19;
/*   90: 169 */     CHARS[987] = 33;
/*   91: 170 */     CHARS[988] = -19;
/*   92: 171 */     CHARS[989] = 33;
/*   93: 172 */     CHARS[990] = -19;
/*   94: 173 */     CHARS[991] = 33;
/*   95: 174 */     CHARS[992] = -19;
/*   96: 175 */     CHARS[993] = 33;
/*   97: 176 */     Arrays.fill(CHARS, 994, 1012, (byte)-19);
/*   98: 177 */     Arrays.fill(CHARS, 1012, 1025, (byte)33);
/*   99: 178 */     Arrays.fill(CHARS, 1025, 1037, (byte)-19);
/*  100: 179 */     CHARS[1037] = 33;
/*  101: 180 */     Arrays.fill(CHARS, 1038, 1104, (byte)-19);
/*  102: 181 */     CHARS[1104] = 33;
/*  103: 182 */     Arrays.fill(CHARS, 1105, 1117, (byte)-19);
/*  104: 183 */     CHARS[1117] = 33;
/*  105: 184 */     Arrays.fill(CHARS, 1118, 1154, (byte)-19);
/*  106: 185 */     CHARS[1154] = 33;
/*  107: 186 */     Arrays.fill(CHARS, 1155, 1159, (byte)-87);
/*  108: 187 */     Arrays.fill(CHARS, 1159, 1168, (byte)33);
/*  109: 188 */     Arrays.fill(CHARS, 1168, 1221, (byte)-19);
/*  110: 189 */     Arrays.fill(CHARS, 1221, 1223, (byte)33);
/*  111: 190 */     Arrays.fill(CHARS, 1223, 1225, (byte)-19);
/*  112: 191 */     Arrays.fill(CHARS, 1225, 1227, (byte)33);
/*  113: 192 */     Arrays.fill(CHARS, 1227, 1229, (byte)-19);
/*  114: 193 */     Arrays.fill(CHARS, 1229, 1232, (byte)33);
/*  115: 194 */     Arrays.fill(CHARS, 1232, 1260, (byte)-19);
/*  116: 195 */     Arrays.fill(CHARS, 1260, 1262, (byte)33);
/*  117: 196 */     Arrays.fill(CHARS, 1262, 1270, (byte)-19);
/*  118: 197 */     Arrays.fill(CHARS, 1270, 1272, (byte)33);
/*  119: 198 */     Arrays.fill(CHARS, 1272, 1274, (byte)-19);
/*  120: 199 */     Arrays.fill(CHARS, 1274, 1329, (byte)33);
/*  121: 200 */     Arrays.fill(CHARS, 1329, 1367, (byte)-19);
/*  122: 201 */     Arrays.fill(CHARS, 1367, 1369, (byte)33);
/*  123: 202 */     CHARS[1369] = -19;
/*  124: 203 */     Arrays.fill(CHARS, 1370, 1377, (byte)33);
/*  125: 204 */     Arrays.fill(CHARS, 1377, 1415, (byte)-19);
/*  126: 205 */     Arrays.fill(CHARS, 1415, 1425, (byte)33);
/*  127: 206 */     Arrays.fill(CHARS, 1425, 1442, (byte)-87);
/*  128: 207 */     CHARS[1442] = 33;
/*  129: 208 */     Arrays.fill(CHARS, 1443, 1466, (byte)-87);
/*  130: 209 */     CHARS[1466] = 33;
/*  131: 210 */     Arrays.fill(CHARS, 1467, 1470, (byte)-87);
/*  132: 211 */     CHARS[1470] = 33;
/*  133: 212 */     CHARS[1471] = -87;
/*  134: 213 */     CHARS[1472] = 33;
/*  135: 214 */     Arrays.fill(CHARS, 1473, 1475, (byte)-87);
/*  136: 215 */     CHARS[1475] = 33;
/*  137: 216 */     CHARS[1476] = -87;
/*  138: 217 */     Arrays.fill(CHARS, 1477, 1488, (byte)33);
/*  139: 218 */     Arrays.fill(CHARS, 1488, 1515, (byte)-19);
/*  140: 219 */     Arrays.fill(CHARS, 1515, 1520, (byte)33);
/*  141: 220 */     Arrays.fill(CHARS, 1520, 1523, (byte)-19);
/*  142: 221 */     Arrays.fill(CHARS, 1523, 1569, (byte)33);
/*  143: 222 */     Arrays.fill(CHARS, 1569, 1595, (byte)-19);
/*  144: 223 */     Arrays.fill(CHARS, 1595, 1600, (byte)33);
/*  145: 224 */     CHARS[1600] = -87;
/*  146: 225 */     Arrays.fill(CHARS, 1601, 1611, (byte)-19);
/*  147: 226 */     Arrays.fill(CHARS, 1611, 1619, (byte)-87);
/*  148: 227 */     Arrays.fill(CHARS, 1619, 1632, (byte)33);
/*  149: 228 */     Arrays.fill(CHARS, 1632, 1642, (byte)-87);
/*  150: 229 */     Arrays.fill(CHARS, 1642, 1648, (byte)33);
/*  151: 230 */     CHARS[1648] = -87;
/*  152: 231 */     Arrays.fill(CHARS, 1649, 1720, (byte)-19);
/*  153: 232 */     Arrays.fill(CHARS, 1720, 1722, (byte)33);
/*  154: 233 */     Arrays.fill(CHARS, 1722, 1727, (byte)-19);
/*  155: 234 */     CHARS[1727] = 33;
/*  156: 235 */     Arrays.fill(CHARS, 1728, 1743, (byte)-19);
/*  157: 236 */     CHARS[1743] = 33;
/*  158: 237 */     Arrays.fill(CHARS, 1744, 1748, (byte)-19);
/*  159: 238 */     CHARS[1748] = 33;
/*  160: 239 */     CHARS[1749] = -19;
/*  161: 240 */     Arrays.fill(CHARS, 1750, 1765, (byte)-87);
/*  162: 241 */     Arrays.fill(CHARS, 1765, 1767, (byte)-19);
/*  163: 242 */     Arrays.fill(CHARS, 1767, 1769, (byte)-87);
/*  164: 243 */     CHARS[1769] = 33;
/*  165: 244 */     Arrays.fill(CHARS, 1770, 1774, (byte)-87);
/*  166: 245 */     Arrays.fill(CHARS, 1774, 1776, (byte)33);
/*  167: 246 */     Arrays.fill(CHARS, 1776, 1786, (byte)-87);
/*  168: 247 */     Arrays.fill(CHARS, 1786, 2305, (byte)33);
/*  169: 248 */     Arrays.fill(CHARS, 2305, 2308, (byte)-87);
/*  170: 249 */     CHARS[2308] = 33;
/*  171: 250 */     Arrays.fill(CHARS, 2309, 2362, (byte)-19);
/*  172: 251 */     Arrays.fill(CHARS, 2362, 2364, (byte)33);
/*  173: 252 */     CHARS[2364] = -87;
/*  174: 253 */     CHARS[2365] = -19;
/*  175: 254 */     Arrays.fill(CHARS, 2366, 2382, (byte)-87);
/*  176: 255 */     Arrays.fill(CHARS, 2382, 2385, (byte)33);
/*  177: 256 */     Arrays.fill(CHARS, 2385, 2389, (byte)-87);
/*  178: 257 */     Arrays.fill(CHARS, 2389, 2392, (byte)33);
/*  179: 258 */     Arrays.fill(CHARS, 2392, 2402, (byte)-19);
/*  180: 259 */     Arrays.fill(CHARS, 2402, 2404, (byte)-87);
/*  181: 260 */     Arrays.fill(CHARS, 2404, 2406, (byte)33);
/*  182: 261 */     Arrays.fill(CHARS, 2406, 2416, (byte)-87);
/*  183: 262 */     Arrays.fill(CHARS, 2416, 2433, (byte)33);
/*  184: 263 */     Arrays.fill(CHARS, 2433, 2436, (byte)-87);
/*  185: 264 */     CHARS[2436] = 33;
/*  186: 265 */     Arrays.fill(CHARS, 2437, 2445, (byte)-19);
/*  187: 266 */     Arrays.fill(CHARS, 2445, 2447, (byte)33);
/*  188: 267 */     Arrays.fill(CHARS, 2447, 2449, (byte)-19);
/*  189: 268 */     Arrays.fill(CHARS, 2449, 2451, (byte)33);
/*  190: 269 */     Arrays.fill(CHARS, 2451, 2473, (byte)-19);
/*  191: 270 */     CHARS[2473] = 33;
/*  192: 271 */     Arrays.fill(CHARS, 2474, 2481, (byte)-19);
/*  193: 272 */     CHARS[2481] = 33;
/*  194: 273 */     CHARS[2482] = -19;
/*  195: 274 */     Arrays.fill(CHARS, 2483, 2486, (byte)33);
/*  196: 275 */     Arrays.fill(CHARS, 2486, 2490, (byte)-19);
/*  197: 276 */     Arrays.fill(CHARS, 2490, 2492, (byte)33);
/*  198: 277 */     CHARS[2492] = -87;
/*  199: 278 */     CHARS[2493] = 33;
/*  200: 279 */     Arrays.fill(CHARS, 2494, 2501, (byte)-87);
/*  201: 280 */     Arrays.fill(CHARS, 2501, 2503, (byte)33);
/*  202: 281 */     Arrays.fill(CHARS, 2503, 2505, (byte)-87);
/*  203: 282 */     Arrays.fill(CHARS, 2505, 2507, (byte)33);
/*  204: 283 */     Arrays.fill(CHARS, 2507, 2510, (byte)-87);
/*  205: 284 */     Arrays.fill(CHARS, 2510, 2519, (byte)33);
/*  206: 285 */     CHARS[2519] = -87;
/*  207: 286 */     Arrays.fill(CHARS, 2520, 2524, (byte)33);
/*  208: 287 */     Arrays.fill(CHARS, 2524, 2526, (byte)-19);
/*  209: 288 */     CHARS[2526] = 33;
/*  210: 289 */     Arrays.fill(CHARS, 2527, 2530, (byte)-19);
/*  211: 290 */     Arrays.fill(CHARS, 2530, 2532, (byte)-87);
/*  212: 291 */     Arrays.fill(CHARS, 2532, 2534, (byte)33);
/*  213: 292 */     Arrays.fill(CHARS, 2534, 2544, (byte)-87);
/*  214: 293 */     Arrays.fill(CHARS, 2544, 2546, (byte)-19);
/*  215: 294 */     Arrays.fill(CHARS, 2546, 2562, (byte)33);
/*  216: 295 */     CHARS[2562] = -87;
/*  217: 296 */     Arrays.fill(CHARS, 2563, 2565, (byte)33);
/*  218: 297 */     Arrays.fill(CHARS, 2565, 2571, (byte)-19);
/*  219: 298 */     Arrays.fill(CHARS, 2571, 2575, (byte)33);
/*  220: 299 */     Arrays.fill(CHARS, 2575, 2577, (byte)-19);
/*  221: 300 */     Arrays.fill(CHARS, 2577, 2579, (byte)33);
/*  222: 301 */     Arrays.fill(CHARS, 2579, 2601, (byte)-19);
/*  223: 302 */     CHARS[2601] = 33;
/*  224: 303 */     Arrays.fill(CHARS, 2602, 2609, (byte)-19);
/*  225: 304 */     CHARS[2609] = 33;
/*  226: 305 */     Arrays.fill(CHARS, 2610, 2612, (byte)-19);
/*  227: 306 */     CHARS[2612] = 33;
/*  228: 307 */     Arrays.fill(CHARS, 2613, 2615, (byte)-19);
/*  229: 308 */     CHARS[2615] = 33;
/*  230: 309 */     Arrays.fill(CHARS, 2616, 2618, (byte)-19);
/*  231: 310 */     Arrays.fill(CHARS, 2618, 2620, (byte)33);
/*  232: 311 */     CHARS[2620] = -87;
/*  233: 312 */     CHARS[2621] = 33;
/*  234: 313 */     Arrays.fill(CHARS, 2622, 2627, (byte)-87);
/*  235: 314 */     Arrays.fill(CHARS, 2627, 2631, (byte)33);
/*  236: 315 */     Arrays.fill(CHARS, 2631, 2633, (byte)-87);
/*  237: 316 */     Arrays.fill(CHARS, 2633, 2635, (byte)33);
/*  238: 317 */     Arrays.fill(CHARS, 2635, 2638, (byte)-87);
/*  239: 318 */     Arrays.fill(CHARS, 2638, 2649, (byte)33);
/*  240: 319 */     Arrays.fill(CHARS, 2649, 2653, (byte)-19);
/*  241: 320 */     CHARS[2653] = 33;
/*  242: 321 */     CHARS[2654] = -19;
/*  243: 322 */     Arrays.fill(CHARS, 2655, 2662, (byte)33);
/*  244: 323 */     Arrays.fill(CHARS, 2662, 2674, (byte)-87);
/*  245: 324 */     Arrays.fill(CHARS, 2674, 2677, (byte)-19);
/*  246: 325 */     Arrays.fill(CHARS, 2677, 2689, (byte)33);
/*  247: 326 */     Arrays.fill(CHARS, 2689, 2692, (byte)-87);
/*  248: 327 */     CHARS[2692] = 33;
/*  249: 328 */     Arrays.fill(CHARS, 2693, 2700, (byte)-19);
/*  250: 329 */     CHARS[2700] = 33;
/*  251: 330 */     CHARS[2701] = -19;
/*  252: 331 */     CHARS[2702] = 33;
/*  253: 332 */     Arrays.fill(CHARS, 2703, 2706, (byte)-19);
/*  254: 333 */     CHARS[2706] = 33;
/*  255: 334 */     Arrays.fill(CHARS, 2707, 2729, (byte)-19);
/*  256: 335 */     CHARS[2729] = 33;
/*  257: 336 */     Arrays.fill(CHARS, 2730, 2737, (byte)-19);
/*  258: 337 */     CHARS[2737] = 33;
/*  259: 338 */     Arrays.fill(CHARS, 2738, 2740, (byte)-19);
/*  260: 339 */     CHARS[2740] = 33;
/*  261: 340 */     Arrays.fill(CHARS, 2741, 2746, (byte)-19);
/*  262: 341 */     Arrays.fill(CHARS, 2746, 2748, (byte)33);
/*  263: 342 */     CHARS[2748] = -87;
/*  264: 343 */     CHARS[2749] = -19;
/*  265: 344 */     Arrays.fill(CHARS, 2750, 2758, (byte)-87);
/*  266: 345 */     CHARS[2758] = 33;
/*  267: 346 */     Arrays.fill(CHARS, 2759, 2762, (byte)-87);
/*  268: 347 */     CHARS[2762] = 33;
/*  269: 348 */     Arrays.fill(CHARS, 2763, 2766, (byte)-87);
/*  270: 349 */     Arrays.fill(CHARS, 2766, 2784, (byte)33);
/*  271: 350 */     CHARS[2784] = -19;
/*  272: 351 */     Arrays.fill(CHARS, 2785, 2790, (byte)33);
/*  273: 352 */     Arrays.fill(CHARS, 2790, 2800, (byte)-87);
/*  274: 353 */     Arrays.fill(CHARS, 2800, 2817, (byte)33);
/*  275: 354 */     Arrays.fill(CHARS, 2817, 2820, (byte)-87);
/*  276: 355 */     CHARS[2820] = 33;
/*  277: 356 */     Arrays.fill(CHARS, 2821, 2829, (byte)-19);
/*  278: 357 */     Arrays.fill(CHARS, 2829, 2831, (byte)33);
/*  279: 358 */     Arrays.fill(CHARS, 2831, 2833, (byte)-19);
/*  280: 359 */     Arrays.fill(CHARS, 2833, 2835, (byte)33);
/*  281: 360 */     Arrays.fill(CHARS, 2835, 2857, (byte)-19);
/*  282: 361 */     CHARS[2857] = 33;
/*  283: 362 */     Arrays.fill(CHARS, 2858, 2865, (byte)-19);
/*  284: 363 */     CHARS[2865] = 33;
/*  285: 364 */     Arrays.fill(CHARS, 2866, 2868, (byte)-19);
/*  286: 365 */     Arrays.fill(CHARS, 2868, 2870, (byte)33);
/*  287: 366 */     Arrays.fill(CHARS, 2870, 2874, (byte)-19);
/*  288: 367 */     Arrays.fill(CHARS, 2874, 2876, (byte)33);
/*  289: 368 */     CHARS[2876] = -87;
/*  290: 369 */     CHARS[2877] = -19;
/*  291: 370 */     Arrays.fill(CHARS, 2878, 2884, (byte)-87);
/*  292: 371 */     Arrays.fill(CHARS, 2884, 2887, (byte)33);
/*  293: 372 */     Arrays.fill(CHARS, 2887, 2889, (byte)-87);
/*  294: 373 */     Arrays.fill(CHARS, 2889, 2891, (byte)33);
/*  295: 374 */     Arrays.fill(CHARS, 2891, 2894, (byte)-87);
/*  296: 375 */     Arrays.fill(CHARS, 2894, 2902, (byte)33);
/*  297: 376 */     Arrays.fill(CHARS, 2902, 2904, (byte)-87);
/*  298: 377 */     Arrays.fill(CHARS, 2904, 2908, (byte)33);
/*  299: 378 */     Arrays.fill(CHARS, 2908, 2910, (byte)-19);
/*  300: 379 */     CHARS[2910] = 33;
/*  301: 380 */     Arrays.fill(CHARS, 2911, 2914, (byte)-19);
/*  302: 381 */     Arrays.fill(CHARS, 2914, 2918, (byte)33);
/*  303: 382 */     Arrays.fill(CHARS, 2918, 2928, (byte)-87);
/*  304: 383 */     Arrays.fill(CHARS, 2928, 2946, (byte)33);
/*  305: 384 */     Arrays.fill(CHARS, 2946, 2948, (byte)-87);
/*  306: 385 */     CHARS[2948] = 33;
/*  307: 386 */     Arrays.fill(CHARS, 2949, 2955, (byte)-19);
/*  308: 387 */     Arrays.fill(CHARS, 2955, 2958, (byte)33);
/*  309: 388 */     Arrays.fill(CHARS, 2958, 2961, (byte)-19);
/*  310: 389 */     CHARS[2961] = 33;
/*  311: 390 */     Arrays.fill(CHARS, 2962, 2966, (byte)-19);
/*  312: 391 */     Arrays.fill(CHARS, 2966, 2969, (byte)33);
/*  313: 392 */     Arrays.fill(CHARS, 2969, 2971, (byte)-19);
/*  314: 393 */     CHARS[2971] = 33;
/*  315: 394 */     CHARS[2972] = -19;
/*  316: 395 */     CHARS[2973] = 33;
/*  317: 396 */     Arrays.fill(CHARS, 2974, 2976, (byte)-19);
/*  318: 397 */     Arrays.fill(CHARS, 2976, 2979, (byte)33);
/*  319: 398 */     Arrays.fill(CHARS, 2979, 2981, (byte)-19);
/*  320: 399 */     Arrays.fill(CHARS, 2981, 2984, (byte)33);
/*  321: 400 */     Arrays.fill(CHARS, 2984, 2987, (byte)-19);
/*  322: 401 */     Arrays.fill(CHARS, 2987, 2990, (byte)33);
/*  323: 402 */     Arrays.fill(CHARS, 2990, 2998, (byte)-19);
/*  324: 403 */     CHARS[2998] = 33;
/*  325: 404 */     Arrays.fill(CHARS, 2999, 3002, (byte)-19);
/*  326: 405 */     Arrays.fill(CHARS, 3002, 3006, (byte)33);
/*  327: 406 */     Arrays.fill(CHARS, 3006, 3011, (byte)-87);
/*  328: 407 */     Arrays.fill(CHARS, 3011, 3014, (byte)33);
/*  329: 408 */     Arrays.fill(CHARS, 3014, 3017, (byte)-87);
/*  330: 409 */     CHARS[3017] = 33;
/*  331: 410 */     Arrays.fill(CHARS, 3018, 3022, (byte)-87);
/*  332: 411 */     Arrays.fill(CHARS, 3022, 3031, (byte)33);
/*  333: 412 */     CHARS[3031] = -87;
/*  334: 413 */     Arrays.fill(CHARS, 3032, 3047, (byte)33);
/*  335: 414 */     Arrays.fill(CHARS, 3047, 3056, (byte)-87);
/*  336: 415 */     Arrays.fill(CHARS, 3056, 3073, (byte)33);
/*  337: 416 */     Arrays.fill(CHARS, 3073, 3076, (byte)-87);
/*  338: 417 */     CHARS[3076] = 33;
/*  339: 418 */     Arrays.fill(CHARS, 3077, 3085, (byte)-19);
/*  340: 419 */     CHARS[3085] = 33;
/*  341: 420 */     Arrays.fill(CHARS, 3086, 3089, (byte)-19);
/*  342: 421 */     CHARS[3089] = 33;
/*  343: 422 */     Arrays.fill(CHARS, 3090, 3113, (byte)-19);
/*  344: 423 */     CHARS[3113] = 33;
/*  345: 424 */     Arrays.fill(CHARS, 3114, 3124, (byte)-19);
/*  346: 425 */     CHARS[3124] = 33;
/*  347: 426 */     Arrays.fill(CHARS, 3125, 3130, (byte)-19);
/*  348: 427 */     Arrays.fill(CHARS, 3130, 3134, (byte)33);
/*  349: 428 */     Arrays.fill(CHARS, 3134, 3141, (byte)-87);
/*  350: 429 */     CHARS[3141] = 33;
/*  351: 430 */     Arrays.fill(CHARS, 3142, 3145, (byte)-87);
/*  352: 431 */     CHARS[3145] = 33;
/*  353: 432 */     Arrays.fill(CHARS, 3146, 3150, (byte)-87);
/*  354: 433 */     Arrays.fill(CHARS, 3150, 3157, (byte)33);
/*  355: 434 */     Arrays.fill(CHARS, 3157, 3159, (byte)-87);
/*  356: 435 */     Arrays.fill(CHARS, 3159, 3168, (byte)33);
/*  357: 436 */     Arrays.fill(CHARS, 3168, 3170, (byte)-19);
/*  358: 437 */     Arrays.fill(CHARS, 3170, 3174, (byte)33);
/*  359: 438 */     Arrays.fill(CHARS, 3174, 3184, (byte)-87);
/*  360: 439 */     Arrays.fill(CHARS, 3184, 3202, (byte)33);
/*  361: 440 */     Arrays.fill(CHARS, 3202, 3204, (byte)-87);
/*  362: 441 */     CHARS[3204] = 33;
/*  363: 442 */     Arrays.fill(CHARS, 3205, 3213, (byte)-19);
/*  364: 443 */     CHARS[3213] = 33;
/*  365: 444 */     Arrays.fill(CHARS, 3214, 3217, (byte)-19);
/*  366: 445 */     CHARS[3217] = 33;
/*  367: 446 */     Arrays.fill(CHARS, 3218, 3241, (byte)-19);
/*  368: 447 */     CHARS[3241] = 33;
/*  369: 448 */     Arrays.fill(CHARS, 3242, 3252, (byte)-19);
/*  370: 449 */     CHARS[3252] = 33;
/*  371: 450 */     Arrays.fill(CHARS, 3253, 3258, (byte)-19);
/*  372: 451 */     Arrays.fill(CHARS, 3258, 3262, (byte)33);
/*  373: 452 */     Arrays.fill(CHARS, 3262, 3269, (byte)-87);
/*  374: 453 */     CHARS[3269] = 33;
/*  375: 454 */     Arrays.fill(CHARS, 3270, 3273, (byte)-87);
/*  376: 455 */     CHARS[3273] = 33;
/*  377: 456 */     Arrays.fill(CHARS, 3274, 3278, (byte)-87);
/*  378: 457 */     Arrays.fill(CHARS, 3278, 3285, (byte)33);
/*  379: 458 */     Arrays.fill(CHARS, 3285, 3287, (byte)-87);
/*  380: 459 */     Arrays.fill(CHARS, 3287, 3294, (byte)33);
/*  381: 460 */     CHARS[3294] = -19;
/*  382: 461 */     CHARS[3295] = 33;
/*  383: 462 */     Arrays.fill(CHARS, 3296, 3298, (byte)-19);
/*  384: 463 */     Arrays.fill(CHARS, 3298, 3302, (byte)33);
/*  385: 464 */     Arrays.fill(CHARS, 3302, 3312, (byte)-87);
/*  386: 465 */     Arrays.fill(CHARS, 3312, 3330, (byte)33);
/*  387: 466 */     Arrays.fill(CHARS, 3330, 3332, (byte)-87);
/*  388: 467 */     CHARS[3332] = 33;
/*  389: 468 */     Arrays.fill(CHARS, 3333, 3341, (byte)-19);
/*  390: 469 */     CHARS[3341] = 33;
/*  391: 470 */     Arrays.fill(CHARS, 3342, 3345, (byte)-19);
/*  392: 471 */     CHARS[3345] = 33;
/*  393: 472 */     Arrays.fill(CHARS, 3346, 3369, (byte)-19);
/*  394: 473 */     CHARS[3369] = 33;
/*  395: 474 */     Arrays.fill(CHARS, 3370, 3386, (byte)-19);
/*  396: 475 */     Arrays.fill(CHARS, 3386, 3390, (byte)33);
/*  397: 476 */     Arrays.fill(CHARS, 3390, 3396, (byte)-87);
/*  398: 477 */     Arrays.fill(CHARS, 3396, 3398, (byte)33);
/*  399: 478 */     Arrays.fill(CHARS, 3398, 3401, (byte)-87);
/*  400: 479 */     CHARS[3401] = 33;
/*  401: 480 */     Arrays.fill(CHARS, 3402, 3406, (byte)-87);
/*  402: 481 */     Arrays.fill(CHARS, 3406, 3415, (byte)33);
/*  403: 482 */     CHARS[3415] = -87;
/*  404: 483 */     Arrays.fill(CHARS, 3416, 3424, (byte)33);
/*  405: 484 */     Arrays.fill(CHARS, 3424, 3426, (byte)-19);
/*  406: 485 */     Arrays.fill(CHARS, 3426, 3430, (byte)33);
/*  407: 486 */     Arrays.fill(CHARS, 3430, 3440, (byte)-87);
/*  408: 487 */     Arrays.fill(CHARS, 3440, 3585, (byte)33);
/*  409: 488 */     Arrays.fill(CHARS, 3585, 3631, (byte)-19);
/*  410: 489 */     CHARS[3631] = 33;
/*  411: 490 */     CHARS[3632] = -19;
/*  412: 491 */     CHARS[3633] = -87;
/*  413: 492 */     Arrays.fill(CHARS, 3634, 3636, (byte)-19);
/*  414: 493 */     Arrays.fill(CHARS, 3636, 3643, (byte)-87);
/*  415: 494 */     Arrays.fill(CHARS, 3643, 3648, (byte)33);
/*  416: 495 */     Arrays.fill(CHARS, 3648, 3654, (byte)-19);
/*  417: 496 */     Arrays.fill(CHARS, 3654, 3663, (byte)-87);
/*  418: 497 */     CHARS[3663] = 33;
/*  419: 498 */     Arrays.fill(CHARS, 3664, 3674, (byte)-87);
/*  420: 499 */     Arrays.fill(CHARS, 3674, 3713, (byte)33);
/*  421: 500 */     Arrays.fill(CHARS, 3713, 3715, (byte)-19);
/*  422: 501 */     CHARS[3715] = 33;
/*  423: 502 */     CHARS[3716] = -19;
/*  424: 503 */     Arrays.fill(CHARS, 3717, 3719, (byte)33);
/*  425: 504 */     Arrays.fill(CHARS, 3719, 3721, (byte)-19);
/*  426: 505 */     CHARS[3721] = 33;
/*  427: 506 */     CHARS[3722] = -19;
/*  428: 507 */     Arrays.fill(CHARS, 3723, 3725, (byte)33);
/*  429: 508 */     CHARS[3725] = -19;
/*  430: 509 */     Arrays.fill(CHARS, 3726, 3732, (byte)33);
/*  431: 510 */     Arrays.fill(CHARS, 3732, 3736, (byte)-19);
/*  432: 511 */     CHARS[3736] = 33;
/*  433: 512 */     Arrays.fill(CHARS, 3737, 3744, (byte)-19);
/*  434: 513 */     CHARS[3744] = 33;
/*  435: 514 */     Arrays.fill(CHARS, 3745, 3748, (byte)-19);
/*  436: 515 */     CHARS[3748] = 33;
/*  437: 516 */     CHARS[3749] = -19;
/*  438: 517 */     CHARS[3750] = 33;
/*  439: 518 */     CHARS[3751] = -19;
/*  440: 519 */     Arrays.fill(CHARS, 3752, 3754, (byte)33);
/*  441: 520 */     Arrays.fill(CHARS, 3754, 3756, (byte)-19);
/*  442: 521 */     CHARS[3756] = 33;
/*  443: 522 */     Arrays.fill(CHARS, 3757, 3759, (byte)-19);
/*  444: 523 */     CHARS[3759] = 33;
/*  445: 524 */     CHARS[3760] = -19;
/*  446: 525 */     CHARS[3761] = -87;
/*  447: 526 */     Arrays.fill(CHARS, 3762, 3764, (byte)-19);
/*  448: 527 */     Arrays.fill(CHARS, 3764, 3770, (byte)-87);
/*  449: 528 */     CHARS[3770] = 33;
/*  450: 529 */     Arrays.fill(CHARS, 3771, 3773, (byte)-87);
/*  451: 530 */     CHARS[3773] = -19;
/*  452: 531 */     Arrays.fill(CHARS, 3774, 3776, (byte)33);
/*  453: 532 */     Arrays.fill(CHARS, 3776, 3781, (byte)-19);
/*  454: 533 */     CHARS[3781] = 33;
/*  455: 534 */     CHARS[3782] = -87;
/*  456: 535 */     CHARS[3783] = 33;
/*  457: 536 */     Arrays.fill(CHARS, 3784, 3790, (byte)-87);
/*  458: 537 */     Arrays.fill(CHARS, 3790, 3792, (byte)33);
/*  459: 538 */     Arrays.fill(CHARS, 3792, 3802, (byte)-87);
/*  460: 539 */     Arrays.fill(CHARS, 3802, 3864, (byte)33);
/*  461: 540 */     Arrays.fill(CHARS, 3864, 3866, (byte)-87);
/*  462: 541 */     Arrays.fill(CHARS, 3866, 3872, (byte)33);
/*  463: 542 */     Arrays.fill(CHARS, 3872, 3882, (byte)-87);
/*  464: 543 */     Arrays.fill(CHARS, 3882, 3893, (byte)33);
/*  465: 544 */     CHARS[3893] = -87;
/*  466: 545 */     CHARS[3894] = 33;
/*  467: 546 */     CHARS[3895] = -87;
/*  468: 547 */     CHARS[3896] = 33;
/*  469: 548 */     CHARS[3897] = -87;
/*  470: 549 */     Arrays.fill(CHARS, 3898, 3902, (byte)33);
/*  471: 550 */     Arrays.fill(CHARS, 3902, 3904, (byte)-87);
/*  472: 551 */     Arrays.fill(CHARS, 3904, 3912, (byte)-19);
/*  473: 552 */     CHARS[3912] = 33;
/*  474: 553 */     Arrays.fill(CHARS, 3913, 3946, (byte)-19);
/*  475: 554 */     Arrays.fill(CHARS, 3946, 3953, (byte)33);
/*  476: 555 */     Arrays.fill(CHARS, 3953, 3973, (byte)-87);
/*  477: 556 */     CHARS[3973] = 33;
/*  478: 557 */     Arrays.fill(CHARS, 3974, 3980, (byte)-87);
/*  479: 558 */     Arrays.fill(CHARS, 3980, 3984, (byte)33);
/*  480: 559 */     Arrays.fill(CHARS, 3984, 3990, (byte)-87);
/*  481: 560 */     CHARS[3990] = 33;
/*  482: 561 */     CHARS[3991] = -87;
/*  483: 562 */     CHARS[3992] = 33;
/*  484: 563 */     Arrays.fill(CHARS, 3993, 4014, (byte)-87);
/*  485: 564 */     Arrays.fill(CHARS, 4014, 4017, (byte)33);
/*  486: 565 */     Arrays.fill(CHARS, 4017, 4024, (byte)-87);
/*  487: 566 */     CHARS[4024] = 33;
/*  488: 567 */     CHARS[4025] = -87;
/*  489: 568 */     Arrays.fill(CHARS, 4026, 4256, (byte)33);
/*  490: 569 */     Arrays.fill(CHARS, 4256, 4294, (byte)-19);
/*  491: 570 */     Arrays.fill(CHARS, 4294, 4304, (byte)33);
/*  492: 571 */     Arrays.fill(CHARS, 4304, 4343, (byte)-19);
/*  493: 572 */     Arrays.fill(CHARS, 4343, 4352, (byte)33);
/*  494: 573 */     CHARS[4352] = -19;
/*  495: 574 */     CHARS[4353] = 33;
/*  496: 575 */     Arrays.fill(CHARS, 4354, 4356, (byte)-19);
/*  497: 576 */     CHARS[4356] = 33;
/*  498: 577 */     Arrays.fill(CHARS, 4357, 4360, (byte)-19);
/*  499: 578 */     CHARS[4360] = 33;
/*  500: 579 */     CHARS[4361] = -19;
/*  501: 580 */     CHARS[4362] = 33;
/*  502: 581 */     Arrays.fill(CHARS, 4363, 4365, (byte)-19);
/*  503: 582 */     CHARS[4365] = 33;
/*  504: 583 */     Arrays.fill(CHARS, 4366, 4371, (byte)-19);
/*  505: 584 */     Arrays.fill(CHARS, 4371, 4412, (byte)33);
/*  506: 585 */     CHARS[4412] = -19;
/*  507: 586 */     CHARS[4413] = 33;
/*  508: 587 */     CHARS[4414] = -19;
/*  509: 588 */     CHARS[4415] = 33;
/*  510: 589 */     CHARS[4416] = -19;
/*  511: 590 */     Arrays.fill(CHARS, 4417, 4428, (byte)33);
/*  512: 591 */     CHARS[4428] = -19;
/*  513: 592 */     CHARS[4429] = 33;
/*  514: 593 */     CHARS[4430] = -19;
/*  515: 594 */     CHARS[4431] = 33;
/*  516: 595 */     CHARS[4432] = -19;
/*  517: 596 */     Arrays.fill(CHARS, 4433, 4436, (byte)33);
/*  518: 597 */     Arrays.fill(CHARS, 4436, 4438, (byte)-19);
/*  519: 598 */     Arrays.fill(CHARS, 4438, 4441, (byte)33);
/*  520: 599 */     CHARS[4441] = -19;
/*  521: 600 */     Arrays.fill(CHARS, 4442, 4447, (byte)33);
/*  522: 601 */     Arrays.fill(CHARS, 4447, 4450, (byte)-19);
/*  523: 602 */     CHARS[4450] = 33;
/*  524: 603 */     CHARS[4451] = -19;
/*  525: 604 */     CHARS[4452] = 33;
/*  526: 605 */     CHARS[4453] = -19;
/*  527: 606 */     CHARS[4454] = 33;
/*  528: 607 */     CHARS[4455] = -19;
/*  529: 608 */     CHARS[4456] = 33;
/*  530: 609 */     CHARS[4457] = -19;
/*  531: 610 */     Arrays.fill(CHARS, 4458, 4461, (byte)33);
/*  532: 611 */     Arrays.fill(CHARS, 4461, 4463, (byte)-19);
/*  533: 612 */     Arrays.fill(CHARS, 4463, 4466, (byte)33);
/*  534: 613 */     Arrays.fill(CHARS, 4466, 4468, (byte)-19);
/*  535: 614 */     CHARS[4468] = 33;
/*  536: 615 */     CHARS[4469] = -19;
/*  537: 616 */     Arrays.fill(CHARS, 4470, 4510, (byte)33);
/*  538: 617 */     CHARS[4510] = -19;
/*  539: 618 */     Arrays.fill(CHARS, 4511, 4520, (byte)33);
/*  540: 619 */     CHARS[4520] = -19;
/*  541: 620 */     Arrays.fill(CHARS, 4521, 4523, (byte)33);
/*  542: 621 */     CHARS[4523] = -19;
/*  543: 622 */     Arrays.fill(CHARS, 4524, 4526, (byte)33);
/*  544: 623 */     Arrays.fill(CHARS, 4526, 4528, (byte)-19);
/*  545: 624 */     Arrays.fill(CHARS, 4528, 4535, (byte)33);
/*  546: 625 */     Arrays.fill(CHARS, 4535, 4537, (byte)-19);
/*  547: 626 */     CHARS[4537] = 33;
/*  548: 627 */     CHARS[4538] = -19;
/*  549: 628 */     CHARS[4539] = 33;
/*  550: 629 */     Arrays.fill(CHARS, 4540, 4547, (byte)-19);
/*  551: 630 */     Arrays.fill(CHARS, 4547, 4587, (byte)33);
/*  552: 631 */     CHARS[4587] = -19;
/*  553: 632 */     Arrays.fill(CHARS, 4588, 4592, (byte)33);
/*  554: 633 */     CHARS[4592] = -19;
/*  555: 634 */     Arrays.fill(CHARS, 4593, 4601, (byte)33);
/*  556: 635 */     CHARS[4601] = -19;
/*  557: 636 */     Arrays.fill(CHARS, 4602, 7680, (byte)33);
/*  558: 637 */     Arrays.fill(CHARS, 7680, 7836, (byte)-19);
/*  559: 638 */     Arrays.fill(CHARS, 7836, 7840, (byte)33);
/*  560: 639 */     Arrays.fill(CHARS, 7840, 7930, (byte)-19);
/*  561: 640 */     Arrays.fill(CHARS, 7930, 7936, (byte)33);
/*  562: 641 */     Arrays.fill(CHARS, 7936, 7958, (byte)-19);
/*  563: 642 */     Arrays.fill(CHARS, 7958, 7960, (byte)33);
/*  564: 643 */     Arrays.fill(CHARS, 7960, 7966, (byte)-19);
/*  565: 644 */     Arrays.fill(CHARS, 7966, 7968, (byte)33);
/*  566: 645 */     Arrays.fill(CHARS, 7968, 8006, (byte)-19);
/*  567: 646 */     Arrays.fill(CHARS, 8006, 8008, (byte)33);
/*  568: 647 */     Arrays.fill(CHARS, 8008, 8014, (byte)-19);
/*  569: 648 */     Arrays.fill(CHARS, 8014, 8016, (byte)33);
/*  570: 649 */     Arrays.fill(CHARS, 8016, 8024, (byte)-19);
/*  571: 650 */     CHARS[8024] = 33;
/*  572: 651 */     CHARS[8025] = -19;
/*  573: 652 */     CHARS[8026] = 33;
/*  574: 653 */     CHARS[8027] = -19;
/*  575: 654 */     CHARS[8028] = 33;
/*  576: 655 */     CHARS[8029] = -19;
/*  577: 656 */     CHARS[8030] = 33;
/*  578: 657 */     Arrays.fill(CHARS, 8031, 8062, (byte)-19);
/*  579: 658 */     Arrays.fill(CHARS, 8062, 8064, (byte)33);
/*  580: 659 */     Arrays.fill(CHARS, 8064, 8117, (byte)-19);
/*  581: 660 */     CHARS[8117] = 33;
/*  582: 661 */     Arrays.fill(CHARS, 8118, 8125, (byte)-19);
/*  583: 662 */     CHARS[8125] = 33;
/*  584: 663 */     CHARS[8126] = -19;
/*  585: 664 */     Arrays.fill(CHARS, 8127, 8130, (byte)33);
/*  586: 665 */     Arrays.fill(CHARS, 8130, 8133, (byte)-19);
/*  587: 666 */     CHARS[8133] = 33;
/*  588: 667 */     Arrays.fill(CHARS, 8134, 8141, (byte)-19);
/*  589: 668 */     Arrays.fill(CHARS, 8141, 8144, (byte)33);
/*  590: 669 */     Arrays.fill(CHARS, 8144, 8148, (byte)-19);
/*  591: 670 */     Arrays.fill(CHARS, 8148, 8150, (byte)33);
/*  592: 671 */     Arrays.fill(CHARS, 8150, 8156, (byte)-19);
/*  593: 672 */     Arrays.fill(CHARS, 8156, 8160, (byte)33);
/*  594: 673 */     Arrays.fill(CHARS, 8160, 8173, (byte)-19);
/*  595: 674 */     Arrays.fill(CHARS, 8173, 8178, (byte)33);
/*  596: 675 */     Arrays.fill(CHARS, 8178, 8181, (byte)-19);
/*  597: 676 */     CHARS[8181] = 33;
/*  598: 677 */     Arrays.fill(CHARS, 8182, 8189, (byte)-19);
/*  599: 678 */     Arrays.fill(CHARS, 8189, 8400, (byte)33);
/*  600: 679 */     Arrays.fill(CHARS, 8400, 8413, (byte)-87);
/*  601: 680 */     Arrays.fill(CHARS, 8413, 8417, (byte)33);
/*  602: 681 */     CHARS[8417] = -87;
/*  603: 682 */     Arrays.fill(CHARS, 8418, 8486, (byte)33);
/*  604: 683 */     CHARS[8486] = -19;
/*  605: 684 */     Arrays.fill(CHARS, 8487, 8490, (byte)33);
/*  606: 685 */     Arrays.fill(CHARS, 8490, 8492, (byte)-19);
/*  607: 686 */     Arrays.fill(CHARS, 8492, 8494, (byte)33);
/*  608: 687 */     CHARS[8494] = -19;
/*  609: 688 */     Arrays.fill(CHARS, 8495, 8576, (byte)33);
/*  610: 689 */     Arrays.fill(CHARS, 8576, 8579, (byte)-19);
/*  611: 690 */     Arrays.fill(CHARS, 8579, 12293, (byte)33);
/*  612: 691 */     CHARS[12293] = -87;
/*  613: 692 */     CHARS[12294] = 33;
/*  614: 693 */     CHARS[12295] = -19;
/*  615: 694 */     Arrays.fill(CHARS, 12296, 12321, (byte)33);
/*  616: 695 */     Arrays.fill(CHARS, 12321, 12330, (byte)-19);
/*  617: 696 */     Arrays.fill(CHARS, 12330, 12336, (byte)-87);
/*  618: 697 */     CHARS[12336] = 33;
/*  619: 698 */     Arrays.fill(CHARS, 12337, 12342, (byte)-87);
/*  620: 699 */     Arrays.fill(CHARS, 12342, 12353, (byte)33);
/*  621: 700 */     Arrays.fill(CHARS, 12353, 12437, (byte)-19);
/*  622: 701 */     Arrays.fill(CHARS, 12437, 12441, (byte)33);
/*  623: 702 */     Arrays.fill(CHARS, 12441, 12443, (byte)-87);
/*  624: 703 */     Arrays.fill(CHARS, 12443, 12445, (byte)33);
/*  625: 704 */     Arrays.fill(CHARS, 12445, 12447, (byte)-87);
/*  626: 705 */     Arrays.fill(CHARS, 12447, 12449, (byte)33);
/*  627: 706 */     Arrays.fill(CHARS, 12449, 12539, (byte)-19);
/*  628: 707 */     CHARS[12539] = 33;
/*  629: 708 */     Arrays.fill(CHARS, 12540, 12543, (byte)-87);
/*  630: 709 */     Arrays.fill(CHARS, 12543, 12549, (byte)33);
/*  631: 710 */     Arrays.fill(CHARS, 12549, 12589, (byte)-19);
/*  632: 711 */     Arrays.fill(CHARS, 12589, 19968, (byte)33);
/*  633: 712 */     Arrays.fill(CHARS, 19968, 40870, (byte)-19);
/*  634: 713 */     Arrays.fill(CHARS, 40870, 44032, (byte)33);
/*  635: 714 */     Arrays.fill(CHARS, 44032, 55204, (byte)-19);
/*  636: 715 */     Arrays.fill(CHARS, 55204, 55296, (byte)33);
/*  637: 716 */     Arrays.fill(CHARS, 57344, 65534, (byte)33);
/*  638:     */   }
/*  639:     */   
/*  640:     */   public static boolean isSupplemental(int c)
/*  641:     */   {
/*  642: 730 */     return (c >= 65536) && (c <= 1114111);
/*  643:     */   }
/*  644:     */   
/*  645:     */   public static int supplemental(char h, char l)
/*  646:     */   {
/*  647: 741 */     return (h - 55296) * 1024 + (l - 56320) + 65536;
/*  648:     */   }
/*  649:     */   
/*  650:     */   public static char highSurrogate(int c)
/*  651:     */   {
/*  652: 750 */     return (char)((c - 65536 >> 10) + 55296);
/*  653:     */   }
/*  654:     */   
/*  655:     */   public static char lowSurrogate(int c)
/*  656:     */   {
/*  657: 759 */     return (char)((c - 65536 & 0x3FF) + 56320);
/*  658:     */   }
/*  659:     */   
/*  660:     */   public static boolean isHighSurrogate(int c)
/*  661:     */   {
/*  662: 768 */     return (55296 <= c) && (c <= 56319);
/*  663:     */   }
/*  664:     */   
/*  665:     */   public static boolean isLowSurrogate(int c)
/*  666:     */   {
/*  667: 777 */     return (56320 <= c) && (c <= 57343);
/*  668:     */   }
/*  669:     */   
/*  670:     */   public static boolean isValid(int c)
/*  671:     */   {
/*  672: 792 */     return ((c < 65536) && ((CHARS[c] & 0x1) != 0)) || ((65536 <= c) && (c <= 1114111));
/*  673:     */   }
/*  674:     */   
/*  675:     */   public static boolean isInvalid(int c)
/*  676:     */   {
/*  677: 802 */     return !isValid(c);
/*  678:     */   }
/*  679:     */   
/*  680:     */   public static boolean isContent(int c)
/*  681:     */   {
/*  682: 811 */     return ((c < 65536) && ((CHARS[c] & 0x20) != 0)) || ((65536 <= c) && (c <= 1114111));
/*  683:     */   }
/*  684:     */   
/*  685:     */   public static boolean isMarkup(int c)
/*  686:     */   {
/*  687: 822 */     return (c == 60) || (c == 38) || (c == 37);
/*  688:     */   }
/*  689:     */   
/*  690:     */   public static boolean isSpace(int c)
/*  691:     */   {
/*  692: 832 */     return (c <= 32) && ((CHARS[c] & 0x2) != 0);
/*  693:     */   }
/*  694:     */   
/*  695:     */   public static boolean isNameStart(int c)
/*  696:     */   {
/*  697: 843 */     return (c < 65536) && ((CHARS[c] & 0x4) != 0);
/*  698:     */   }
/*  699:     */   
/*  700:     */   public static boolean isName(int c)
/*  701:     */   {
/*  702: 854 */     return (c < 65536) && ((CHARS[c] & 0x8) != 0);
/*  703:     */   }
/*  704:     */   
/*  705:     */   public static boolean isNCNameStart(int c)
/*  706:     */   {
/*  707: 865 */     return (c < 65536) && ((CHARS[c] & 0x40) != 0);
/*  708:     */   }
/*  709:     */   
/*  710:     */   public static boolean isNCName(int c)
/*  711:     */   {
/*  712: 876 */     return (c < 65536) && ((CHARS[c] & 0x80) != 0);
/*  713:     */   }
/*  714:     */   
/*  715:     */   public static boolean isPubid(int c)
/*  716:     */   {
/*  717: 887 */     return (c < 65536) && ((CHARS[c] & 0x10) != 0);
/*  718:     */   }
/*  719:     */   
/*  720:     */   public static boolean isValidName(String name)
/*  721:     */   {
/*  722: 901 */     if (name.length() == 0) {
/*  723: 902 */       return false;
/*  724:     */     }
/*  725: 903 */     char ch = name.charAt(0);
/*  726: 904 */     if (!isNameStart(ch)) {
/*  727: 905 */       return false;
/*  728:     */     }
/*  729: 906 */     for (int i = 1; i < name.length(); i++)
/*  730:     */     {
/*  731: 907 */       ch = name.charAt(i);
/*  732: 908 */       if (!isName(ch)) {
/*  733: 909 */         return false;
/*  734:     */       }
/*  735:     */     }
/*  736: 912 */     return true;
/*  737:     */   }
/*  738:     */   
/*  739:     */   public static boolean isValidNCName(String ncName)
/*  740:     */   {
/*  741: 928 */     if (ncName.length() == 0) {
/*  742: 929 */       return false;
/*  743:     */     }
/*  744: 930 */     char ch = ncName.charAt(0);
/*  745: 931 */     if (!isNCNameStart(ch)) {
/*  746: 932 */       return false;
/*  747:     */     }
/*  748: 933 */     for (int i = 1; i < ncName.length(); i++)
/*  749:     */     {
/*  750: 934 */       ch = ncName.charAt(i);
/*  751: 935 */       if (!isNCName(ch)) {
/*  752: 936 */         return false;
/*  753:     */       }
/*  754:     */     }
/*  755: 939 */     return true;
/*  756:     */   }
/*  757:     */   
/*  758:     */   public static boolean isValidNmtoken(String nmtoken)
/*  759:     */   {
/*  760: 953 */     if (nmtoken.length() == 0) {
/*  761: 954 */       return false;
/*  762:     */     }
/*  763: 955 */     for (int i = 0; i < nmtoken.length(); i++)
/*  764:     */     {
/*  765: 956 */       char ch = nmtoken.charAt(i);
/*  766: 957 */       if (!isName(ch)) {
/*  767: 958 */         return false;
/*  768:     */       }
/*  769:     */     }
/*  770: 961 */     return true;
/*  771:     */   }
/*  772:     */   
/*  773:     */   public static boolean isValidIANAEncoding(String ianaEncoding)
/*  774:     */   {
/*  775: 979 */     if (ianaEncoding != null)
/*  776:     */     {
/*  777: 980 */       int length = ianaEncoding.length();
/*  778: 981 */       if (length > 0)
/*  779:     */       {
/*  780: 982 */         char c = ianaEncoding.charAt(0);
/*  781: 983 */         if (((c >= 'A') && (c <= 'Z')) || ((c >= 'a') && (c <= 'z')))
/*  782:     */         {
/*  783: 984 */           for (int i = 1; i < length; i++)
/*  784:     */           {
/*  785: 985 */             c = ianaEncoding.charAt(i);
/*  786: 986 */             if (((c < 'A') || (c > 'Z')) && ((c < 'a') || (c > 'z')) && ((c < '0') || (c > '9')) && (c != '.') && (c != '_') && (c != '-')) {
/*  787: 989 */               return false;
/*  788:     */             }
/*  789:     */           }
/*  790: 992 */           return true;
/*  791:     */         }
/*  792:     */       }
/*  793:     */     }
/*  794: 996 */     return false;
/*  795:     */   }
/*  796:     */   
/*  797:     */   public static boolean isValidJavaEncoding(String javaEncoding)
/*  798:     */   {
/*  799:1008 */     if (javaEncoding != null)
/*  800:     */     {
/*  801:1009 */       int length = javaEncoding.length();
/*  802:1010 */       if (length > 0)
/*  803:     */       {
/*  804:1011 */         for (int i = 1; i < length; i++)
/*  805:     */         {
/*  806:1012 */           char c = javaEncoding.charAt(i);
/*  807:1013 */           if (((c < 'A') || (c > 'Z')) && ((c < 'a') || (c > 'z')) && ((c < '0') || (c > '9')) && (c != '.') && (c != '_') && (c != '-')) {
/*  808:1016 */             return false;
/*  809:     */           }
/*  810:     */         }
/*  811:1019 */         return true;
/*  812:     */       }
/*  813:     */     }
/*  814:1022 */     return false;
/*  815:     */   }
/*  816:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.utils.XMLChar
 * JD-Core Version:    0.7.0.1
 */