package com.example.excelParser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.w3c.dom.Document;

public class MyExcelWebViewer {

	private String source;
	public MyExcelWebViewer(String source) {
		this.source = source;
	}

	public String convert() throws Exception {

		Document doc = ExcelToHtmlConverter.process(new File(source));
//		FileWriter out = new FileWriter(target);
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		DOMSource domSource = new DOMSource(doc);
		StreamResult streamResult = new StreamResult(out);
		
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer serializer = tf.newTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.setOutputProperty(OutputKeys.METHOD, "html");
		serializer.transform(domSource, streamResult);
		out.close();
		
		String content = new String(out.toByteArray());
		return content;
	}
}
