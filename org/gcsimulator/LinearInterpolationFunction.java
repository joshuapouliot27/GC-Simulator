/*     */ package org.gcsimulator;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ 
/*     */ class LinearInterpolationFunction
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private static final long CLASS_VERSION = 1L;
/*     */   public double[][] dDataArray;
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException
/*     */   {
/*  19 */     out.writeLong(1L);
/*     */     
/*  21 */     out.writeObject(this.dDataArray);
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
/*     */   {
/*  26 */     long lVersion = in.readLong();
/*     */     
/*  28 */     if (lVersion >= 1L)
/*     */     {
/*  30 */       this.dDataArray = ((double[][])in.readObject());
/*     */     }
/*     */   }
/*     */   
/*     */   class DataPointComparator implements Comparator<double[]>
/*     */   {
/*     */     DataPointComparator() {}
/*     */     
/*     */     public int compare(double[] arg0, double[] arg1) {
/*  39 */       if (arg0[0] > arg1[0])
/*     */       {
/*  41 */         return 1;
/*     */       }
/*  43 */       if (arg0[0] < arg1[0])
/*     */       {
/*  45 */         return -1;
/*     */       }
/*     */       
/*  48 */       return 0;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public LinearInterpolationFunction(double[][] dataPoints)
/*     */   {
/*  55 */     Comparator<double[]> byXVal = new DataPointComparator();
/*     */     
/*  57 */     Arrays.sort(dataPoints, byXVal);
/*     */     
/*  59 */     this.dDataArray = dataPoints;
/*     */   }
/*     */   
/*     */ 
/*     */   public double getAt(double x)
/*     */   {
/*  65 */     if (x < this.dDataArray[0][0])
/*  66 */       return extrapolateBefore(x);
/*  67 */     if (x > this.dDataArray[(this.dDataArray.length - 1)][0]) {
/*  68 */       return extrapolateAfter(x);
/*     */     }
/*  70 */     int i = 0;
/*  71 */     while (x > this.dDataArray[i][0])
/*     */     {
/*  73 */       i++;
/*  74 */       if (i >= this.dDataArray.length) {
/*     */         break;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  80 */     double y = 0.0D;
/*     */     
/*  82 */     if (i >= this.dDataArray.length)
/*     */     {
/*  84 */       y = this.dDataArray[(this.dDataArray.length - 1)][1];
/*     */     }
/*  86 */     else if (i == 0)
/*     */     {
/*  88 */       y = this.dDataArray[0][1];
/*     */     }
/*     */     else
/*     */     {
/*  92 */       double dXValAfter = this.dDataArray[i][0];
/*  93 */       double dXValBefore = this.dDataArray[(i - 1)][0];
/*  94 */       double dXPosition = (x - dXValBefore) / (dXValAfter - dXValBefore);
/*  95 */       double dYValAfter = this.dDataArray[i][1];
/*  96 */       double dYValBefore = this.dDataArray[(i - 1)][1];
/*     */       
/*  98 */       y = dXPosition * (dYValAfter - dYValBefore) + dYValBefore;
/*     */     }
/*     */     
/* 101 */     return y;
/*     */   }
/*     */   
/*     */ 
/*     */   public double extrapolateBefore(double x)
/*     */   {
/* 107 */     double dSlope = (this.dDataArray[1][1] - this.dDataArray[0][1]) / (this.dDataArray[1][0] - this.dDataArray[0][0]);
/* 108 */     double dIntercept = this.dDataArray[0][1] - dSlope * this.dDataArray[0][0];
/*     */     
/* 110 */     return dSlope * x + dIntercept;
/*     */   }
/*     */   
/*     */ 
/*     */   public double extrapolateAfter(double x)
/*     */   {
/* 116 */     double dSlope = (this.dDataArray[(this.dDataArray.length - 1)][1] - this.dDataArray[(this.dDataArray.length - 2)][1]) / (this.dDataArray[(this.dDataArray.length - 1)][0] - this.dDataArray[(this.dDataArray.length - 2)][0]);
/* 117 */     double dIntercept = this.dDataArray[(this.dDataArray.length - 1)][1] - dSlope * this.dDataArray[(this.dDataArray.length - 1)][0];
/*     */     
/* 119 */     return dSlope * x + dIntercept;
/*     */   }
/*     */ }


/* Location:              /Users/joshua/Downloads/GCSimulator.jar!/org/gcsimulator/LinearInterpolationFunction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */