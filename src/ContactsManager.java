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
		readFile();
		Scanner in = new Scanner(System.in);
		while (true) {
			buildMenu();
			String input = in.nextLine();
			switch (input) {
				case "1" -> showContacts();
				case "2" -> addContacts();
				case "3" -> showContacts(searchContacts());
				case "4" -> deleteContact();
				case "5" -> exitApp();
			}
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
			System.out.println(stringList.get(i));
			String[] splitList = stringList.get(i).split("\\|");
			contactList.add(new Contact(splitList[0].trim(), splitList[1].trim()));
		}
	}

	public static void showContacts() {
		for (Contact contacts : contactList) {
			System.out.printf("Name %s and Ph# %s\n", contacts.getName(), contacts.getPhoneNum());
		}
	}

	public static void showContacts(int i) {
		if (i != -1) {
			System.out.printf("Name %s and Ph# %s\n", contactList.get(i).getName(), contactList.get(i).getPhoneNum());
		} else {
			System.out.println("Contact not found.");
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
		System.out.println();
		System.out.println("What would you like to do?");
		System.out.println("1. View contacts.");
		System.out.println("2. Add a new contact.");
		System.out.println("3. Search a contact by name.");
		System.out.println("4. Delete an existing contact.");
		System.out.println("5. Exit.");
		System.out.print("Enter an option (1, 2, 3, 4 or 5):");
		System.out.println();
	}

	public static void exitApp() {
		System.out.println("Terminating Application...");
		System.exit(0);
	}

	public static int searchContacts() {
		int index = -1;
		Scanner in = new Scanner(System.in);
		System.out.println();
		System.out.println("Please enter a contact name to search for");
		String input = in.nextLine();
		for (int i = 0; i < contactList.size(); i++)
			if (contactList.get(i).getName().equals(input)) {
				index = i;
				break;
			}
		return index;
	}

	public static void deleteContact() throws IOException{
		int index = -1;
		Scanner in = new Scanner(System.in);
		System.out.println();
		System.out.println("Please enter a contact name to delete");
		String input = in.nextLine();
		for (int i = 0; i < contactList.size(); i++)
			if (contactList.get(i).getName().equals(input)) {
				index = i;
				break;
			}
		if (index == -1) {
			System.out.println("Sorry, contact does not exist");
		} else {
			contactList.remove(index);
			System.out.println("Contact has been removed");
			updateFile();
		}
	}
}
