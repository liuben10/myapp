package com.example;

import com.example.domain.PersonData;
import com.example.domain.tables.Person;
import com.example.domain.tables.records.PersonRecord;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.InsertResultStep;
import org.jooq.InsertValuesStepN;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@SpringBootApplication
@Slf4j
public class DemoApplication {

	@Autowired
	DSLContext dslContext;

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello World!";
	}

	@RequestMapping("/bootstrap")
	@ResponseBody
	String create() {
		PersonRecord personRecord = dslContext.newRecord(Person.PERSON);
		personRecord.setId(1);
		personRecord.setName("foo");
		final InsertValuesStepN<PersonRecord> insertStep = dslContext.insertInto(personRecord.getTable()).values(personRecord.getId(), personRecord.getName());
		final String sql = insertStep.getSQL();
		final InsertResultStep<PersonRecord> resultStep = insertStep.returning(Person.PERSON.ID);
		log.info("sql={}, result={}", sql, resultStep.fetchOne().getId());
		return sql;
	}

	@RequestMapping("/persons")
	@ResponseBody
	List<PersonData> getPersons() {
		List<PersonData> persons = new ArrayList<>();
		final Result<Record> records = dslContext.select().from(Person.PERSON).fetch();
		
		for (Record record : records) {
			persons.add(new PersonData((String) record.get("name")));
		}
		return persons;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
