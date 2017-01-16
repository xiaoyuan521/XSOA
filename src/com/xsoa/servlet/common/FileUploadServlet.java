package com.xsoa.servlet.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.framework.core.BaseServlet;
import com.framework.session.SessionAttribute;
import com.xsoa.common.FileService;
import com.xsoa.pojo.basetable.Pojo_YHXX;

/**
 * Servlet implementation class FileUploadServlet
 */
@WebServlet("/FileUploadServlet")
public class FileUploadServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public static final String JSON = "application/json";

	/**
	 * @see BaseServlet#BaseServlet()
	 */
	public FileUploadServlet() {
		super();
	}

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		Pojo_YHXX beanUser = (Pojo_YHXX) getSessionObject(SessionAttribute.LOGIN_USER);
		String filename = beanUser.getYHXX_YHID() + ".JPG";
		String FileDir = "/project/upload/pics/";// 文件路径结构
		String responseString = FileDir + "/" + filename;
		String projpath = request.getSession().getServletContext().getRealPath("/bin/upload/pics/");
		if (ServletFileUpload.isMultipartContent(request)) {
			ServletFileUpload upload = new ServletFileUpload();
			try {

				FileItemIterator iter = upload.getItemIterator(request);
				while (iter.hasNext()) {
					FileItemStream item = iter.next();
					InputStream input = item.openStream();
					if (item.isFormField()) {
						// String fileName = item.getFieldName();
						// String value = filename; //
						// 上传文件原名:Streams.asString(input);
					} else {
						// String fileDir = this.getUploadPath() + FileDir;
						String fileDir = projpath;
						File dstFile = new File(fileDir);
						if (!dstFile.exists()) {
							dstFile.mkdirs();
						}
						File dst = new File(dstFile.getPath() + "/" + filename);
						new FileService().saveUploadFile(input, dst);
						responseString = filename;
					}
				}

			} catch (Exception e) {
				// responseString = RESP_ERROR;
				e.printStackTrace();
			}

			double randomNum = Math.random();
			responseString = responseString + "?id=" + randomNum;
			response.setContentType(JSON);
			byte[] responseBytes = responseString.getBytes();
			response.setContentLength(responseBytes.length);
			ServletOutputStream output = response.getOutputStream();
			output.write(responseBytes);
			output.flush();

		}
	}

}
