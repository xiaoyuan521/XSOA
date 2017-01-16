package com.xsoa.common;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Title 文件操作共通类
 * @Package com.xsoa.service.common
 * @author ljg
 * @date 2015-1-19 上午10:43:10
 * @version V1.00.00
 */
public class FileService {

   public static final int BUF_SIZE = 2 * 1024;

   /**
    * 保存上传文件
    *
    * @param
    * @author ljg
    * @date 2015-1-19 上午10:44:28
    * @version V1.00.00
    */

public void saveUploadFile(InputStream input, File dst) throws IOException {
      OutputStream out = null;
      try {
         /*if (dst.exists()) {
            out = new BufferedOutputStream(new FileOutputStream(dst, true), BUF_SIZE);
         } else {
            out = new BufferedOutputStream(new FileOutputStream(dst), BUF_SIZE);
         }*/
//    	  if (dst.exists()) {
//    		  dst.delete();
//    	  }

    	 out = new BufferedOutputStream(new FileOutputStream(dst), BUF_SIZE);
         byte[] buffer = new byte[BUF_SIZE];
         int len = 0;
         while ((len = input.read(buffer)) > 0) {
            out.write(buffer, 0, len);
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         if (null != input) {
            try {
               input.close();
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
         if (null != out) {
            try {
               out.close();
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
      }
   }
}
