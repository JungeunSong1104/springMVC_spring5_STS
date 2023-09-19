package com.office.library.book.admin.util;

import java.io.File;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service//컨트롤러에서 실행되는 서비스 객체니까 서비스로 함
public class UploadFileService {
	public String upload(MultipartFile file) {//스프링이 제공하는 클래스인 MultipartFile타입의 file을 업로드함
		boolean result = false;

		String fileOriName = file.getOriginalFilename();//사용자가 업로드한 파일이름을 그대로 가져와서 문자열에 저장
		String fileExtension = fileOriName.substring(fileOriName.lastIndexOf("."), fileOriName.length());
		//.뒤의 내용을 가져옴 예를들어 file.txt다 그럼 txt를 가져오는것 확장자를 가져와서 저장

		String uploadDir = "C:\\library\\upload\\";

		UUID uuid = UUID.randomUUID();//자바에서 제공하는 기본적인 클래스 java.util에서 제공함
		String uniqueName = uuid.toString().replaceAll("-", "");
		//uuid안에 -가 들어가있는데 모든 -를 지워버림
		//랜덤한 유니크한 이름을 저장해줌

		File saveFile = new File(uploadDir + "\\" + uniqueName + fileExtension);
		//파일 형태의 saveFile객체 만들어줌 파일을 다룰 수 있는 객체를 만든다는뜻
		//fileExtension : 확장자
		//file.txt로 파일을 올렸다 그럼 일련의 과정을 거쳐 WFWEFWEFWEF이런 랜덤한 이름을
		//파일의 이름으로 사용하는것 WFWEFWEFWEF.txt
		//이런과정을 거치는 이유는 사용자가 파일을 올릴 때 이름이 겹치는 걸 방지하기 위해서

		if(!saveFile.exists()) {//saveFile : 해당파일이 없다면
			saveFile.mkdirs();//새로운디렉토리를 만듦
		}

		try {
			file.transferTo(saveFile);//file.transferTo메서드를 이용해서 서버에 파일을 저장
			result = true;//result는 초기값을 false를 갖고있기 때문에 true로 변경되서 정상적으로 파일이 업로드 됐다면
		}catch(Exception e) {
			e.printStackTrace();
		}

		if(result) {
			System.out.println("[UploadFileService] FILE UPLOAD SUCCESS");
			return uniqueName + fileExtension;
			// true로 변경돼서 정상적으로 파일이 업로드 됐다면 유니크네임과 확장자를 결합해서 리턴해줌
		}else {
			System.out.println("[UploadFileService] FILE UPLOAD FAIL");
			return null;
		}
	}
}
