package mx.com.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class StreamApplication {

	//STREAMS
	
	//se declara una lista de la clase usuarios
	private static List<User> users;
	
	public static void main(String[] args) {
		//SpringApplication.run(StreamApplication.class, args);
		
		setUpUser();//se manda a traer la lista del metodo 
		Stream stream = Stream.of(users);//se crea el objeto de stream con la lista dentro
		users.stream();
		//al objeto lista se le dice que va a ser leida a traves de un stream
		//y a su vez, al atributo nombre se le pasa el apellido
		users.stream().forEach(
				e -> e.setNombre(e.getNombre() + " Apellido") );
		
		imprimirLista();
		
		//se crea el metodo de lectura donde se crea la lista de Strings 
		//a esta lista se le pasa el usuario envuelto en un stream que va a 
		// guardar los datos en un map y este map va a contener la funcion lambda
		//y la lambda va a obtener el nombre que se guardo en la lista
		List<String> listaString = users.stream().map(
				e -> e.getNombre()).collect(Collectors.toList());
		
		//con este método for each que viene de stream, se va a leer la lista y se imprime
		listaString.stream().forEach(
				e -> System.out.println(e));
	}
	
	//con este metodo se llena la lista con su id el nombre del User
	public static void setUpUser() {
		
		users = new ArrayList<>();
		users.add(new User(1,"Arturo"));
		users.add(new User(2,"ANgel"));
		users.add(new User(3,"Juan"));
		users.add(new User(4,"America"));
		users.add(new User(5,"Valentina"));
		
		
	}
	
	//Este metodo imprime la lista a traves de un stream que lleva adentro 
	//una funcion lambda
	public static void imprimirLista() {
		users.stream().forEach(
				e -> System.out.println(
						e.getId() 
						+ " - " 
						+ e.getNombre()));
	}

}
