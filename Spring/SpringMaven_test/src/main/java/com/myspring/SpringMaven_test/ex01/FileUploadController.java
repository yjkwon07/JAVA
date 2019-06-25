package com.myspring.SpringMaven_test.ex01;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


@Controller("fileupload")
public class FileUploadController  {
	private static final String CURR_IMAGE_REPO_PATH = "c:\\spring\\image_repo";
	private static final Logger loger = LoggerFactory.getLogger(FileDownloadController.class);
	@RequestMapping(value="/form.do")
	// ���ε�â�� uploadForm.jsp�� ��ȯ�Ѵ�.
	public String form() {
	    return "/uploadForm";
	  }
	
	@RequestMapping(value="/upload.do",method = RequestMethod.POST)
	public ModelAndView upload(MultipartHttpServletRequest multipartRequest,HttpServletResponse response)
	  throws Exception{
		multipartRequest.setCharacterEncoding("utf-8");
		
		// �Ű����� ������ ���� ������ ������ Map�� �����Ѵ�.
		Map map = new HashMap();
		Enumeration enu=multipartRequest.getParameterNames();
		
		// ���۵� �Ű����� ���� key/value�� map�� �����Ѵ�.
		while(enu.hasMoreElements()){
			String name=(String)enu.nextElement();
			String value=multipartRequest.getParameter(name);
			//System.out.println(name+", "+value);
			map.put(name,value);
		}
		// ������ ���ε��� �� ��ȯ�� ���� �̸��� ����� fileList�� �ٽ� map�� �ٽ� �����Ѵ�.
		List fileList= fileProcess(multipartRequest);
		map.put("fileList", fileList);
		ModelAndView mav = new ModelAndView();
		// map�� ���â���� �������Ѵ�.
		mav.addObject("map", map);
		mav.setViewName("/result");
		return mav;
	}
	
	private List<String> fileProcess(MultipartHttpServletRequest multipartRequest) throws Exception{
		List<String> fileList= new ArrayList<String>();
		// ÷�ε� ���� �̸��� �����´�.
		Iterator<String> fileNames = multipartRequest.getFileNames();
		while(fileNames.hasNext()){
			String fileName = fileNames.next();
			
			// ���� �̸��� ���� MultipartFile ��ü�� �����´�.
			MultipartFile mFile = multipartRequest.getFile(fileName);
			
			// ���� ���� �̸��� �����´�.
			String originalFileName = mFile.getOriginalFilename();
			
			
			// ���� �̸��� �ϳ��� fileList�� �����Ѵ�.
			fileList.add(originalFileName);
			File file = new File(CURR_IMAGE_REPO_PATH +"\\"+ fileName);
			if(mFile.getSize()!=0){ //File Null Check
				if(! file.exists()){ //��λ� ������ �������� ���� ���
					if(file.getParentFile().mkdirs()){ //��ο� �ش��ϴ� ���丮���� ����
						file.createNewFile(); //���� ���� ����
					}
				}
				// �ӽ÷� ����� multipartFile�� ���� ���Ϸ� ����
				mFile.transferTo(new File(CURR_IMAGE_REPO_PATH +"\\"+ originalFileName)); 
				loger.debug("mFile :" + mFile.getOriginalFilename());
			}
		}
		// ÷���� ���� �̸��� ����� fileList�� ��ȯ�Ѵ�.
		return fileList;
	}
}
