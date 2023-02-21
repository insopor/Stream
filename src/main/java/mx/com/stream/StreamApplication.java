package mx.com.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
		
		//FILER
		//filter es para fil;tros sobre nuestras colecciones 
		System.out.println("--------------------Filters----------------------");
		//mandamos a traer la lista llena
		setUpUser();
		//hacemos una nueva lista y la envolvemos en el stream
		List<User> userFilter = users.stream()
				.filter(e -> e.getNombre() != "Arturo")//a esta lista le digo qu eimprima todos los diferentes a arturo
				.filter(e -> e.getId() < 6)// y que imprima tambien la lista en donde los id's sean menores a 6
				.collect(Collectors.toList());//le decimos que recolecte la lista
		
		//usamos la lista filtrada y la recorremos para imprimirla y ver el resultado con las condiciones expuestas arriba 
		userFilter.stream().forEach(
				e -> System.out.println(
						e.getId()
						+ " - "
						+ e.getNombre())
				);
				
		//METODO FIND FIRST -  EL PRIMERO QUE ENCUENTRE
		System.out.println("--------------------Find First----------------------");
		setUpUser();//llenamos la lista de nuevo
		//envolvemos la lista en el stream y filtramos que imprima el primer nombre que sea igual al que buscamos
		//si no hay ninguno, entonces puede imprimir un nulo
		User user = users.stream()
				.filter(e -> e.getNombre().equals("Arturo"))
				.findFirst()
				.orElse(null);
		
		System.out.println(
				user.getId()
				+ " - "
				+ user.getNombre());
		
		//FLATMAP
		System.out.println("--------------------Flat Map----------------------");
		//se crea una lista que contiene un arra de lustas de String 
		//y estas dos listas contienen nombres
		List<List<String>> nombresVariasListas = new ArrayList<List<String>>(
				Arrays.asList(
						new ArrayList<String>(Arrays.asList("Arturo", "Maria", "Pedro")),
						new ArrayList<String>(Arrays.asList("Monica", "Pablo"))
						)
				);
		//se crea una lista de Strings y se envuelve en el Stream para manejarla con flatmap 
		//y se recolecta la lusta y todo su contenido
		List<String> nombreUnicaLista = nombresVariasListas.stream()
				.flatMap(
						e -> e.stream())
				.collect(
						Collectors.toList());
		//se imprime la lista contenida en el stream
		nombreUnicaLista.stream().forEach(
				e -> System.out.println(e));
		
		//METODO PEEK
		System.out.println("--------------------Peek----------------------");
		//se carga otra vez la lista de usuarios 
		setUpUser();
		//DATO: los metodos como Collector y forEach, son métodos finales que no permiten mas métodos 
		//que se podrian colocar delante de ellos
		List<User> userPeek = users.stream()
				.peek(
						e -> e.setNombre(e.getNombre() + " Apellido"))
				.collect(
						Collectors.toList());
		//a diferencia de otros metodoss, peek aun soporta mas metodos a su continuacion
		
		userPeek.stream().forEach(
				e -> System.out.println(e.getNombre()));
		
		//METODO COUNT
		System.out.println("--------------------COUNT----------------------");
		setUpUser();
		//el metodo count cuenta sobre la lista 
		//esta vez se filtro que contrara los ID´s que sonmayor a 3
		long numeroFiltrado = users.stream()
				.filter( e -> e.getId() > 3)
				.count();
		
		System.out.println(numeroFiltrado);
		
		//SKIP Y LIMIT
		System.out.println("--------------------SKIP Y LIMIT----------------------");
		//array de abecedario
		String[] abc = {
				"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"
		};
		
		//metemos el array del abecedario en el stream
		//y lo filtramos para que del arreglo se salte las primeras 2 posiciones y despues limite la salida a las 
		//posteriores 4posiciones
		List<String> abcFilter = Arrays.stream(abc)
				.skip(2)
				.limit(4)
				.collect(Collectors.toList());
		
		//skip es para evitar las posiciones 
		//limit es para limitar el numero de letras que vana a ser mostradas
		
		abcFilter.stream().forEach(
				e -> System.out.println(e));
		
		//SORT
		System.out.println("--------------------SORT----------------------");
		//ordenamientos
		setUpUser();//se instancia de nuevo la lista de nombres
		
		//la funcion regresa la lista ordenada alfabeticamente
		users = users.stream()
		.sorted(Comparator.comparing(User::getNombre))//esta lista se compara con la funcion sorted y 
		//dentro se le mete una funcion para comparar los nombres de la lista y asi salgan ordenados
		.collect(Collectors.toList());
		
		//se imprime la lista ordenada
		imprimirLista();
		
		//MINIMO Y MAXIMO
		System.out.println("--------------------MINIMO Y MAXIMO----------------------");
		//estas funciones de minimo y maximo nos devuelven esos valores 
		setUpUser();
		User userMin = users.stream()
				.min(Comparator.comparing(User::getId))
				.orElse(null);
		System.out.println(userMin.getId());
		User userMax = users.stream()
				.max(Comparator.comparing(User::getId))
				.orElse(null);
		System.out.println(userMax.getId());
		
		//DISTINCT
		System.out.println("--------------------DISTINCT----------------------");
		
		String[] abcDist = {
				"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"
				//parametros repetidos para comprobar 
				,"a","b","c","d","e","f"
		};
		
		List<String> listaDist = Arrays.stream(abcDist)
				.distinct().collect(Collectors.toList());
		
		listaDist.stream().forEach(
				e -> System.out.println(e));
		
		
		//ALLMATCH, ANYMATCH, NONEMATCH
		System.out.println("--------------------ALLMATCH, ANYMATCH, NONEMATCH----------------------");
		
		List<Integer> listaNum = Arrays.asList(100,200,300,400,500);
		
		//aqui le que si todos los valores son mayores a 301
		boolean allMAtch = listaNum.stream().allMatch(e -> e > 301);
		System.out.println(allMAtch);
		
		//aqui le digo si talgun valor supera los 301
		boolean anyMAtch = listaNum.stream().anyMatch(e -> e > 301);
		System.out.println(anyMAtch);
		
		//aqui le digo si tninguno supera los 501
		boolean noneMAtch = listaNum.stream().noneMatch(e -> e > 501);
		System.out.println(noneMAtch);
		
		//SUM AVERAGE RANGE
		System.out.println("--------------------SUM AVERAGE RANGE----------------------");
		setUpUser();
		//con este metodo saco el promedio de los numeros que se encuentran como ID en la lista
		double resultAverage = users.stream()
				.mapToInt(User::getId)
				.average()
				.orElse(0);
		System.out.println(resultAverage);
		
		//con este metodo, sumo todos los ID's
		double resultSum = users.stream()
				.mapToInt(User::getId)
				.sum();
		System.out.println(resultSum);
		
		//con este metodo saco el rango y lo sumo 
		System.out.println(
				IntStream.range(0,4).sum());
		
		
	}
	
	
	
	//con este metodo se llena la lista con su id el nombre del User
	public static void setUpUser() {
		
		users = new ArrayList<>();
		//users.add(new User(1,"Arturo"));
		users.add(new User(2,"ANgel"));
		users.add(new User(3,"Juan"));
		users.add(new User(4,"America"));
		users.add(new User(5,"Valentina"));
		users.add(new User(5,"Arturo"));
		
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
