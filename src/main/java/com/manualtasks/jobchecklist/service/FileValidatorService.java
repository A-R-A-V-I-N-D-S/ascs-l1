package com.manualtasks.jobchecklist.service;

import static com.manualtasks.jobchecklist.util.ClassDataUtils.*;

import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

@Service
public class FileValidatorService {

	public boolean isSheetValid(Sheet sheet) {
		if (sheet.getSheetName().equals(CHECKLIST_SHEET_NAME)) {

		} else {

		}
		return true;
	}

}
