package sec01.ex01;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet("/upload.do")
public class FileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		System.out.println("FileUpload 서블릿 초기됨 됨");
	}

	public void destroy() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		File serverRepo = new File("C:\\file_repo");
		//System.out.println("서버에 저장될 경로 : " + serverRepo);

		DiskFileItemFactory factory=new DiskFileItemFactory();

		//파일 용량 크기 설정
		factory.setSizeThreshold(1024 * 1024);
		
		// ServletFileUpload 파일을 올리는 행위를 하는 클래스
		
		ServletFileUpload upload =new ServletFileUpload(factory);
		
//		System.out.println("upload 객체" + upload);
		
		
		try {
			List<FileItem> fileItemList = upload.parseRequest(request);
			
//			System.out.println("fileItem items란 : " +fileItemList);
//			
//			System.out.println("items 크기 : " + fileItemList.size());
			
			for(int i=0 ; i<fileItemList.size() ; i++) {
				 //System.out.println( fileItemList.get(i));
				 
				
				//// FileItem 은 인코딩 타입이 multipart/form-data 일 때 , POST로 요청시 받을 수 있는 항목 클래스
				FileItem fileItem= fileItemList.get(i);
				
				
				//System.out.println("각 파일 아이템의 사이즈 : "   + fileItem.getSize() );
				
				if(fileItem.getSize()>0) {
					// 폼필드 내용만 가져옴
					if (fileItem.isFormField()) {
						//System.out.println("여기는 폼필드");
						System.out.println(fileItem.getFieldName()+ "=" + fileItem.getString("utf-8"));
					}else {
						//System.out.println("여기는 폼필드가 아닌 내용");
						//System.out.println("파일명 :" + fileItem.getName());
						String uploadFileName=fileItem.getName();
						
						//System.out.println("업로드할 파일명" + uploadFileName);
						int idx=uploadFileName.lastIndexOf("\\");
						//System.out.println(idx);
						String fileName = uploadFileName.substring(idx + 1);
						//System.out.println(fileName);
						System.out.println("최종 업로드할 파일명 : " + fileName);
						File uploadFile = new File(serverRepo + "\\" + fileName);
//						System.out.println("서버에 올라갈 경로" + uploadFile);
						fileItem.write(uploadFile);
						
					}
				}
				
				
				
				 
			}
			
			
			
		} catch (Exception e) {
			
			System.out.println("파일 업로드시 예외 발생");
		}
		
		
		
		
	}

}
