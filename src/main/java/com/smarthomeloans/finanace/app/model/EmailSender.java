package com.smarthomeloans.finanace.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailSender {
	
	private String from;
	private String to;
	private String sub;
	private String body;

	
    }
