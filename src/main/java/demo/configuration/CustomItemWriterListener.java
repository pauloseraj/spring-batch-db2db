package demo.configuration;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;

import demo.model.Person;

public class CustomItemWriterListener implements ItemWriteListener<Person> {

	@Override
	public void beforeWrite(List<? extends Person> items) {

		System.out.println("ItemWriteListener - beforeWrite");
	}

	@Override
	public void afterWrite(List<? extends Person> items) {
		
		for (Person person : items) {
			System.out.println(person.getFirstName());
		}
		
		System.out.println("ItemWriteListener - afterWrite");
	}

	@Override
	public void onWriteError(Exception exception, List<? extends Person> items) {
		System.out.println("ItemWriteListener - onWriteError");
	}

}