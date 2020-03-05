package demo.processor;

import org.springframework.batch.item.ItemProcessor;

import demo.model.Employee;
import demo.model.Person;

public class PersonItemProcessor implements ItemProcessor<Person, Employee> {
    @Override
    public Employee process(final Person person) throws Exception {
    	
        final String firstName = person.getFirstName().toUpperCase();
        final String lastName = person.getLastName().toUpperCase();

        final Employee transformedPerson = new Employee();
        transformedPerson.setFirstName(firstName);
        transformedPerson.setLastName(lastName);
        transformedPerson.setId(person.getId());
       // transformedPerson.setProcessed(1);

        return transformedPerson;
    }
}
