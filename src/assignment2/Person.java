package assignment2;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Person {



    private int age;
    private String name;
    private String country;

    public Person(int age, String name, String country) {
        this.age = age;
        this.name = name;
        this.country = country;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return name;

    }





    public static void main(String[] args) {

        List<Person> people = Arrays.asList(
                new Person(20, "John", "USA"),
                new Person(35, "Sam", "Italy"),
                new Person(15, "Jamie", "England"),
                new Person(30, "Robert", "Italy"),
                new Person(20, "James", "Ireland"),
                new Person(25, "Peter", "USA"),
                new Person(5, "Jessica", "Norway"),
                new Person(40, "Roger", "Netherlands"),
                new Person(50, "Jim", "USA")

        );

        // Question1
        // To calculate the average of all the people
        System.out.println("\n Question 1:  "); // Println prints in new line only, no need of "\n"
        double average=  people.stream()
                .mapToInt(x-> x.getAge())
                .average().orElse(0);
        System.out.println("The average is: "+average);

        // Question2
        // Create a list of all the people who are either greater than 20 or contain any vowel in their name (uppercase or lowercase)
        System.out.println("\nQuestion 2: ");
        people.stream()
                .filter(x-> x.name.toLowerCase().contains("a") || x.name.toLowerCase().contains("e")|| x.name.contains("i") || x.name.toLowerCase().contains("o")||x.name.toLowerCase().contains("u")|| x.age>20 )
                .collect(Collectors.toList()) // You can simply ignore this step because if you collect and then or simply print it's one and the same thing
                .forEach(x->System.out.println(x));;

        //Question3
        //Create a list of people,
        // sorted in ascending order on the basis of age,
        // if age is the same then sort in descending order of name
        System.out.println("\nQuestion 3: ");
        // to get the Age and Names in Function which can later by used in comparator
        final Function<Person,Integer> byAge=person -> person.getAge();
        final Function<Person,String> byTheirName=person -> person.getName();

        people.stream()
                //.sorted(Person::getSorted)
//                 .sorted(Comparator.comparing(byAge).thenComparing(byTheirName)) // the logic is slightly incorrect, if the ages are same then you sort in descneding order by name
                
            // The following is the correct one
                .sorted(Comparator.comparing(byAge).thenComparing(byTheirName, Comparator.reverseOrder()))
                .collect(Collectors.toList()) // Again, it's a redundant step
                .forEach(x->System.out.println(x+" "+x.age));


        //Question 4
        //Q4. Create a map from this people list where the key is country name and
        // value is count which means a map will tell how many people live in a particular country

        //people.stream().map()
        System.out.println("\nQuestion 4: ");



        Map<String,Integer> getCountryCount=people
                .stream()
                .collect(Collectors.toMap(x->x.country,x->1,Integer::sum));
        
        // Although, the above solution is correct, but the following one is more descriptive
        
        Map<String,Long> getCountryCount2=people
                .stream()
                .collect(Collectors.groupingBy(Person::getCountry, Collectors.counting()));


        System.out.println(getCountryCount);
        
        //Question 5
        //Create a map which stores avg age of people per country
        // (key should be country and value should be average age i.e, double)

        System.out.println("\nQuestion 5:");
        
        Map<String,Double> getCountryAgeAverage=
                people.stream().collect(Collectors.groupingBy(x->x.country,Collectors.averagingDouble(x->x.age)));

        System.out.println(getCountryAgeAverage);



        //Question 6
        //Print the oldest person in every country

        System.out.println("\nQuestion 6:");

        Map<String,Integer> getAverageAgeCountry=people
                .stream()
                .collect(Collectors.toMap(y->y.country,z->z.getAge(),Integer::max));

        System.out.println(getAverageAgeCountry);
        
        // You had to print oldest person for every country, not the age of the oldest person in every country
        // Following is the correct solution

        System.out.println(people.stream()
                .collect(Collectors.groupingBy
                        (Person::getCountry, Collectors.maxBy(Comparator.comparing(Person::getAge)))));

    }

    
}



