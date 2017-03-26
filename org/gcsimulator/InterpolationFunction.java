/*     */ package org.gcsimulator;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ 
/*     */ 
/*     */ class InterpolationFunction
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private static final long CLASS_VERSION = 1L;
/*     */   public double[][] dInterpolationParameters;
/*     */   public double[] dRanges;
/*  18 */   public boolean bLinear = false;
/*  19 */   public LinearInterpolationFunction lifTwoPoint = null;
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException
/*     */   {
/*  23 */     out.writeLong(1L);
/*     */     
/*  25 */     out.writeObject(this.dInterpolationParameters);
/*  26 */     out.writeObject(this.dRanges);
/*  27 */     out.writeBoolean(this.bLinear);
/*  28 */     out.writeObject(this.lifTwoPoint);
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
/*     */   {
/*  33 */     long lVersion = in.readLong();
/*     */     
/*  35 */     if (lVersion >= 1L)
/*     */     {
/*  37 */       this.dInterpolationParameters = ((double[][])in.readObject());
/*  38 */       this.dRanges = ((double[])in.readObject());
/*  39 */       this.bLinear = in.readBoolean();
/*  40 */       this.lifTwoPoint = ((LinearInterpolationFunction)in.readObject());
/*     */     }
/*     */   }
/*     */   
/*     */   class DataPointComparator implements Comparator<double[]>
/*     */   {
/*     */     DataPointComparator() {}
/*     */     
/*     */     public int compare(double[] arg0, double[] arg1) {
/*  49 */       if (arg0[0] > arg1[0])
/*     */       {
/*  51 */         return 1;
/*     */       }
/*  53 */       if (arg0[0] < arg1[0])
/*     */       {
/*  55 */         return -1;
/*     */       }
/*     */       
/*  58 */       return 0;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public InterpolationFunction(double[][] dataPoints)
/*     */   {
/*  66 */     if (dataPoints.length == 2)
/*     */     {
/*  68 */       this.bLinear = true;
/*  69 */       this.lifTwoPoint = new LinearInterpolationFunction(dataPoints);
/*  70 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  76 */     Comparator<double[]> byXVal = new DataPointComparator();
/*     */     
/*  78 */     Arrays.sort(dataPoints, byXVal);
/*     */     
/*  80 */     int m = (dataPoints.length - 1) * 4;
/*  81 */     int n = (dataPoints.length - 1) * 4;
/*     */     
/*  83 */     double[][] dMatrix = new double[n][m + 1];
/*     */     
/*     */ 
/*  86 */     int iRowPos = 0;
/*     */     
/*  88 */     for (int i = 0; i < dataPoints.length - 1; i++)
/*     */     {
/*     */ 
/*  91 */       dMatrix[iRowPos][(i * 4)] = 1.0D;
/*  92 */       dMatrix[(iRowPos + 1)][(i * 4)] = 1.0D;
/*     */       
/*     */ 
/*  95 */       dMatrix[iRowPos][(i * 4 + 1)] = dataPoints[i][0];
/*  96 */       dMatrix[(iRowPos + 1)][(i * 4 + 1)] = dataPoints[(i + 1)][0];
/*     */       
/*     */ 
/*  99 */       dMatrix[iRowPos][(i * 4 + 2)] = Math.pow(dataPoints[i][0], 2.0D);
/* 100 */       dMatrix[(iRowPos + 1)][(i * 4 + 2)] = Math.pow(dataPoints[(i + 1)][0], 2.0D);
/*     */       
/*     */ 
/* 103 */       dMatrix[iRowPos][(i * 4 + 3)] = Math.pow(dataPoints[i][0], 3.0D);
/* 104 */       dMatrix[(iRowPos + 1)][(i * 4 + 3)] = Math.pow(dataPoints[(i + 1)][0], 3.0D);
/*     */       
/*     */ 
/* 107 */       dMatrix[iRowPos][n] = dataPoints[i][1];
/* 108 */       dMatrix[(iRowPos + 1)][n] = dataPoints[(i + 1)][1];
/*     */       
/* 110 */       iRowPos += 2;
/*     */     }
/*     */     
/*     */ 
/* 114 */     for (int i = 0; i < dataPoints.length - 2; i++)
/*     */     {
/*     */ 
/* 117 */       dMatrix[iRowPos][(i * 4 + 1)] = 1.0D;
/*     */       
/*     */ 
/* 120 */       dMatrix[iRowPos][(i * 4 + 2)] = (2.0D * dataPoints[(i + 1)][0]);
/*     */       
/*     */ 
/* 123 */       dMatrix[iRowPos][(i * 4 + 3)] = (3.0D * Math.pow(dataPoints[(i + 1)][0], 2.0D));
/*     */       
/*     */ 
/* 126 */       dMatrix[iRowPos][(i * 4 + 5)] = -1.0D;
/*     */       
/*     */ 
/* 129 */       dMatrix[iRowPos][(i * 4 + 6)] = (-2.0D * dataPoints[(i + 1)][0]);
/*     */       
/*     */ 
/* 132 */       dMatrix[iRowPos][(i * 4 + 7)] = (-3.0D * Math.pow(dataPoints[(i + 1)][0], 2.0D));
/*     */       
/* 134 */       iRowPos++;
/*     */     }
/*     */     
/*     */ 
/* 138 */     for (int i = 0; i < dataPoints.length - 2; i++)
/*     */     {
/*     */ 
/* 141 */       dMatrix[iRowPos][(i * 4 + 2)] = 2.0D;
/*     */       
/*     */ 
/* 144 */       dMatrix[iRowPos][(i * 4 + 3)] = (6.0D * dataPoints[(i + 1)][0]);
/*     */       
/*     */ 
/* 147 */       dMatrix[iRowPos][(i * 4 + 6)] = -2.0D;
/*     */       
/*     */ 
/* 150 */       dMatrix[iRowPos][(i * 4 + 7)] = (-6.0D * dataPoints[(i + 1)][0]);
/*     */       
/* 152 */       iRowPos++;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 159 */     dMatrix[iRowPos][2] = 2.0D;
/*     */     
/*     */ 
/* 162 */     dMatrix[iRowPos][3] = (6.0D * dataPoints[0][0]);
/*     */     
/*     */ 
/* 165 */     dMatrix[iRowPos][6] = -2.0D;
/*     */     
/*     */ 
/* 168 */     dMatrix[iRowPos][7] = (-6.0D * dataPoints[1][0]);
/*     */     
/* 170 */     iRowPos++;
/*     */     
/*     */ 
/*     */ 
/* 174 */     dMatrix[iRowPos][((dataPoints.length - 2) * 4 + 2)] = 2.0D;
/*     */     
/*     */ 
/* 177 */     dMatrix[iRowPos][((dataPoints.length - 2) * 4 + 3)] = (6.0D * dataPoints[(dataPoints.length - 1)][0]);
/*     */     
/*     */ 
/* 180 */     dMatrix[iRowPos][((dataPoints.length - 3) * 4 + 2)] = -2.0D;
/*     */     
/*     */ 
/* 183 */     dMatrix[iRowPos][((dataPoints.length - 3) * 4 + 3)] = (-6.0D * dataPoints[(dataPoints.length - 2)][0]);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 190 */     int i = 0;
/* 191 */     int j = 0;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 196 */     while ((i < n) && (j < m))
/*     */     {
/*     */ 
/* 199 */       int k = i;
/*     */       
/* 201 */       while ((k < n) && (dMatrix[k][j] == 0.0D)) {
/* 202 */         k++;
/*     */       }
/*     */       
/* 205 */       if (k < n)
/*     */       {
/*     */ 
/* 208 */         if (k != i) {
/* 209 */           swap(dMatrix, i, k, j);
/*     */         }
/*     */         
/* 212 */         if (dMatrix[i][j] != 1.0D) {
/* 213 */           divide(dMatrix, i, j);
/*     */         }
/*     */         
/*     */ 
/* 217 */         eliminate(dMatrix, i, j);
/* 218 */         i++;
/*     */       }
/* 220 */       j++;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 225 */     this.dRanges = new double[dataPoints.length - 2];
/* 226 */     this.dInterpolationParameters = new double[dataPoints.length - 1][4];
/*     */     
/* 228 */     for (i = 0; i < this.dInterpolationParameters.length; i++)
/*     */     {
/* 230 */       this.dInterpolationParameters[i][0] = dMatrix[(i * 4)][m];
/* 231 */       this.dInterpolationParameters[i][1] = dMatrix[(i * 4 + 1)][m];
/* 232 */       this.dInterpolationParameters[i][2] = dMatrix[(i * 4 + 2)][m];
/* 233 */       this.dInterpolationParameters[i][3] = dMatrix[(i * 4 + 3)][m];
/*     */     }
/*     */     
/* 236 */     for (i = 0; i < this.dRanges.length; i++)
/*     */     {
/* 238 */       this.dRanges[i] = dataPoints[(i + 1)][0];
/*     */     }
/*     */   }
/*     */   
/*     */   public double getAt(double x)
/*     */   {
/* 244 */     if (this.bLinear)
/*     */     {
/* 246 */       return this.lifTwoPoint.getAt(x);
/*     */     }
/*     */     
/* 249 */     int i = 0;
/* 250 */     while (x > this.dRanges[i])
/*     */     {
/* 252 */       i++;
/* 253 */       if (i >= this.dRanges.length) {
/*     */         break;
/*     */       }
/*     */     }
/*     */     
/* 258 */     double y = this.dInterpolationParameters[i][0] + this.dInterpolationParameters[i][1] * x + this.dInterpolationParameters[i][2] * Math.pow(x, 2.0D) + this.dInterpolationParameters[i][3] * Math.pow(x, 3.0D);
/* 259 */     return y;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static void swap(double[][] A, int i, int k, int j)
/*     */   {
/* 266 */     int m = A[0].length - 1;
/*     */     
/* 268 */     for (int q = j; q <= m; q++)
/*     */     {
/* 270 */       double temp = A[i][q];
/* 271 */       A[i][q] = A[k][q];
/* 272 */       A[k][q] = temp;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static void divide(double[][] A, int i, int j)
/*     */   {
/* 281 */     int m = A[0].length - 1;
/*     */     
/* 283 */     for (int q = j + 1; q <= m; q++) {
/* 284 */       A[i][q] /= A[i][j];
/*     */     }
/* 286 */     A[i][j] = 1.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static void eliminate(double[][] A, int i, int j)
/*     */   {
/* 294 */     int n = A.length;
/* 295 */     int m = A[0].length - 1;
/*     */     
/* 297 */     for (int p = 0; p < n; p++)
/*     */     {
/* 299 */       if ((p != i) && (A[p][j] != 0.0D))
/*     */       {
/* 301 */         for (int q = j + 1; q <= m; q++)
/*     */         {
/* 303 */           A[p][q] -= A[p][j] * A[i][q];
/*     */         }
/* 305 */         A[p][j] = 0.0D;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/joshua/Downloads/GCSimulator.jar!/org/gcsimulator/InterpolationFunction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */