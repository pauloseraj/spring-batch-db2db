package demo.configuration;

import org.springframework.batch.item.ItemProcessor;

import demo.model.Person;

public class ValidationProcessor implements ItemProcessor<Person,Person> 
{
    public Person process(Person person) throws Exception 
    {
        if (person.getFirstName().startsWith("H")){
            System.out.println("First Name start with H  : " +person.getFirstName());
            return null;
        } 
         
		/*
		 * try { if(Integer.valueOf(employee.getId()) <= 0) {
		 * System.out.println("Invalid employee id : " + employee.getId()); return null;
		 * } } catch (NumberFormatException e) {
		 * System.out.println("Invalid employee id : " + employee.getId()); return null;
		 * }
		 */
        return person;
    }
}