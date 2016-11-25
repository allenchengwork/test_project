package com.maplebox.nail.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.mail.internet.InternetAddress;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.maplebox.core.TestUtil;
import com.maplebox.nail.config.TransactionConfig;
import com.maplebox.nail.event.SendEmailEvent;
import com.maplebox.nail.service.EmailService;
import com.maplebox.nail.service.EmployeeService;
import com.maplebox.nail.table.Employee;
import com.maplebox.nail.task.TestTask;
import com.maplebox.nail.validator.EmployeeValidator;
import com.maplebox.nail.validator.EmployeeValidator.EmployeeUpdate;

import freemarker.template.Configuration;
import freemarker.template.Template;

@RestController
@RequestMapping(value = "/test")
public class TestController {
	static Logger log = LoggerFactory.getLogger(TestController.class);
	
	@Value("${myvalue}")
	private String myValue;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private Configuration freemakerConfig;
	
	@Autowired
	private EmployeeValidator employeeValidator;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private TestTask testTask;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(employeeValidator);
	}
	
	@GetMapping(path = "index")
	public String index() {
		TestUtil.test();
		return myValue;
	}
	
	@RequestMapping(value = "json", method = RequestMethod.GET)
	public Item json() {
		log.debug("Test json");
		return new Item("Allen", "Hello");
	}
	
	@RequestMapping(value = "exp", method = RequestMethod.GET)
	public String exp() throws Exception {
		log.debug("Test exp");
		if (true) throw new Exception("Test Exception Handler");
		return "exp";
	}
	
	@RequestMapping(value = "post", method = RequestMethod.POST)
	public Item post() {
		log.debug("Test json");
		return new Item("Allen", "Hello");
	}
	
	@RequestMapping(value = "employee/{id}", method = RequestMethod.GET)
	public Object employee(
			@PathVariable int id) {
		return employeeService.findOne(id);
	}
	
	@RequestMapping(value = "employee", method = RequestMethod.POST)
	public Object employee(@Validated(value = EmployeeUpdate.class) @RequestBody Employee employee) throws Exception {
		log.debug("post Employee = {}", employee);
		employeeService.save(employee);
		return employee;
	}
	
	@RequestMapping(value = "employee", method = RequestMethod.GET)
	public Object service() {
		long start = System.currentTimeMillis();
		List<Employee> list = employeeService.findList();
		long end = System.currentTimeMillis();
		log.debug("time = {}", (end-start));
		log.debug("size = {}", list.size());
		return list;
	}
	
	@RequestMapping(value = "save", method = RequestMethod.GET)
	public Object save() throws Exception {
		Employee employee = new Employee();
		employee.setName("Testxxxx");
		return employeeService.save(employee);
	}
	
	@RequestMapping(value = "template", method = RequestMethod.GET, produces = "text/html; charset=UTF-8")
	public String template() throws Exception {
		Template template = freemakerConfig.getTemplate("mail.ftl");
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("employee", employeeService.findOne(10));
		
		StringWriter sw = new StringWriter();
		try (BufferedWriter bw = new BufferedWriter(sw)) {
			template.process(data, bw);
			return sw.toString();
		}
	}
	
	@RequestMapping(value = "email", method = RequestMethod.GET)
	public String email() throws Exception {
		List<InternetAddress> listFrom = Lists.newArrayList(
			new InternetAddress("allen@arcrma.com", "Allen")
		);
		List<InternetAddress> listTo = Lists.newArrayList(
			new InternetAddress("davidlin@arcrma.com", "ARCRMA-DavidLin")
		);
		emailService.sendEmail(listFrom, listTo);
		
		return "success";
	}
	
	@RequestMapping(value = "downloadFile", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> downloadFile() throws Exception {
		File file = new File("D:\\output.txt");
		
		HttpHeaders respHeaders = new HttpHeaders();
	    respHeaders.setContentType(MediaType.TEXT_PLAIN);
	    respHeaders.setContentLength(file.length());
	    respHeaders.setContentDispositionFormData("attachment", "file.txt");
	    
		InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
	    return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
	}
	
	@RequestMapping(value = "downloadImage", method = RequestMethod.GET)
	public ResponseEntity<byte[]> download() throws Exception {
		File file = new File("D:\\圖片 123.jpg");
		
		String fileNameEncode = URLEncoder.encode("圖片 123", Charsets.UTF_8.name());
		
		HttpHeaders respHeaders = new HttpHeaders();
	    respHeaders.setContentType(MediaType.IMAGE_JPEG);
	    respHeaders.setContentLength(file.length());
	    respHeaders.setContentDispositionFormData("attachment", fileNameEncode+".jpg");
	    return new ResponseEntity<byte[]>(Files.toByteArray(file), respHeaders, HttpStatus.OK);
	}
	
	//208004
	@RequestMapping(value = "employeeAll", method = RequestMethod.GET)
	public String employeeAll() throws Exception {
		log.debug("post Employee");
		long start = System.currentTimeMillis();
		int row = 100;
		List<Employee> listEmployee = new ArrayList<>();
		for (int i = 0; i < row; i++) {
			Employee employee = new Employee();
			employee.setName(UUID.randomUUID().toString());
			listEmployee.add(employee);
			
			if (listEmployee.size() > (row / 10)) {
				log.debug("save = {}", i);
				employeeService.save(listEmployee);
				listEmployee.clear();
			}
		}
		long end = System.currentTimeMillis();
		return "time = "+(end-start);
	}
	
	@RequestMapping(value = "event", method = RequestMethod.GET)
	public String event() throws UnsupportedEncodingException {
		List<InternetAddress> listFrom = Lists.newArrayList(
			new InternetAddress("allen@arcrma.com", "Allen")
		);
		List<InternetAddress> listTo = Lists.newArrayList(
			new InternetAddress("davidlin@arcrma.com", "ARCRMA-DavidLin")
		);
		eventPublisher.publishEvent(new SendEmailEvent("test", listFrom, listTo));
		eventPublisher.publishEvent(new SendEmailEvent("hello", listFrom, listTo));
		return "success";
	}
	
	@RequestMapping(value = "uploadFile", method = RequestMethod.POST)
	public String uploadFile(@RequestParam("file") MultipartFile file) throws FileNotFoundException, IOException {
		try (
			OutputStream out = IOUtils.buffer(new FileOutputStream(new File("D:/JavaAPI/uploadFile.jpg")));
			InputStream in = IOUtils.buffer(file.getInputStream());
		) {
			IOUtils.copy(in, out);
		}
		
		return "success";
	}
	
	@GetMapping(path = "testTask")
	public void testTask() {
		testTask.taskSchedule("0/5 * * * * MON-FRI");
	}
	
	public static class Item {
		private String name;
		private String value;
		
		public Item(String name, String value) {
			super();
			this.name = name;
			this.value = value;
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
}
