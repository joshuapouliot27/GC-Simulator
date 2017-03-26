/*     */ package org.gcsimulator;
/*     */ 
/*     */ import java.awt.Image;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.datatransfer.Clipboard;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.awt.datatransfer.UnsupportedFlavorException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ImageSelection
/*     */   implements Transferable
/*     */ {
/*     */   private Image image;
/*     */   
/*     */   public static void copyImageToClipboard(Image image)
/*     */   {
/*  80 */     ImageSelection imageSelection = new ImageSelection(image);
/*  81 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*  82 */     toolkit.getSystemClipboard().setContents(imageSelection, null);
/*     */   }
/*     */   
/*     */   public ImageSelection(Image image) {
/*  86 */     this.image = image;
/*     */   }
/*     */   
/*     */   public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException
/*     */   {
/*  91 */     if (!flavor.equals(DataFlavor.imageFlavor)) {
/*  92 */       throw new UnsupportedFlavorException(flavor);
/*     */     }
/*  94 */     return this.image;
/*     */   }
/*     */   
/*     */   public boolean isDataFlavorSupported(DataFlavor flavor)
/*     */   {
/*  99 */     return flavor.equals(DataFlavor.imageFlavor);
/*     */   }
/*     */   
/*     */   public DataFlavor[] getTransferDataFlavors() {
/* 103 */     return new DataFlavor[] {
/* 104 */       DataFlavor.imageFlavor };
/*     */   }
/*     */ }


/* Location:              /Users/joshua/Downloads/GCSimulator.jar!/org/gcsimulator/ImageSelection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */