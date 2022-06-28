import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContactsManager {
	static String directory = "data";
	static String filename = "contacts.txt";
	static Path dataDirectory;
	static Path dataFile;
	static Path contactPath;
	static List<Contact> contactList;


	public static void main(String[] args) throws IOException {

		createDirandFiles();
		buildMenu();
		Scanner in = new Scanner(System.in);
		String input = in.nextLine();
		switch (input) {
			case "1" -> showContacts();
			case "2" -> addContacts();
			case "3" -> System.out.println("Something");
			case "4" -> System.out.println("Something else");
			case "5" -> exitApp();
		}

	}


	public static void createDirandFiles() throws IOException {
		dataDirectory = Paths.get(directory);
		dataFile = Paths.get(directory, filename);
		if (Files.notExists(dataDirectory)) {
			Files.createDirectories(dataDirectory);
		}
		if (! Files.exists(dataFile)) {
			Files.createFile(dataFile);
		}
	}

	public static void readFile() throws IOException{
		contactPath = Paths.get("data", "contacts.txt");
		contactList = new ArrayList<>();
		List<String> stringList = Files.readAllLines(contactPath);
		for (int i = 0; i < stringList.size(); i += 1) {
			System.out.println((i + 1) + ": " + stringList.get(i));
			String [] splitList = stringList.get(i).split("|");
			contactList.add(new Contact(splitList[0].trim(), splitList[1].trim()));
		}
	}

	public static void showContacts() throws IOException {
		for (Contact contacts : contactList) {
			System.out.printf("Name %s and Ph# %s\n", contacts.getName(), contacts.getPhoneNum());
		}
	}

	public static void addContacts() throws IOException {
		Scanner in = new Scanner(System.in);
		System.out.println("What is the contact's name?");
		String cName = in.nextLine();
		System.out.println("What is the contact's phone number?");
		String cPhone = in.nextLine();
		contactList.add(new Contact(cName.trim(), cPhone.trim()));
		updateFile();
	}

	public static void updateFile() throws IOException{
		List<String> updater = new ArrayList<>();
		for (Contact contacts : contactList) {
			updater.add(contacts.getName() + "|" + contacts.getPhoneNum());
		}
		Files.write(contactPath, updater);
	}

	public static void buildMenu() {
		System.out.println("1. View contacts.");
		System.out.println("2. Add a new contact.");
		System.out.println("3. Search a contact by name.");
		System.out.println("4. Delete an existing contact.");
		System.out.println("5. Exit.");
		System.out.print("Enter an option (1, 2, 3, 4 or 5):");
	}

	public static void exitApp() {
		System.out.println("Terminating Application...");
		System.exit(0);
	}
}
