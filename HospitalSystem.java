import java.io.*;
import java.util.*;

/**
 * HospitalSystem.java
 * Simple console-based Hospital Patient Record System using CSV file storage.
 *
 * Usage:
 *   javac HospitalSystem.java
 *   java HospitalSystem
 *
 * The program will create/read "patients.csv" in the same folder.
 */
public class HospitalSystem {

    // --- Model: Patient ---
    static class Patient {
        int id;
        String name;
        int age;
        String gender;
        String disease;
        String contact;

        Patient(int id, String name, int age, String gender, String disease, String contact) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.disease = disease;
            this.contact = contact;
        }

        // CSV line (no commas escaping; keep fields simple)
        String toCSV() {
            return id + "," + name + "," + age + "," + gender + "," + disease + "," + contact;
        }

        static Patient fromCSV(String line) {
            String[] p = line.split(",", -1);
            if (p.length < 6) return null;
            try {
                int id = Integer.parseInt(p[0]);
                String name = p[1];
                int age = Integer.parseInt(p[2]);
                String gender = p[3];
                String disease = p[4];
                String contact = p[5];
                return new Patient(id, name, age, gender, disease, contact);
            } catch (Exception e) {
                return null;
            }
        }

        public String toString() {
            return String.format("ID: %d | Name: %s | Age: %d | Gender: %s | Disease: %s | Contact: %s",
                    id, name, age, gender, disease, contact);
        }
    }

    // --- Manager: holds patients and handles file I/O ---
    static class PatientManager {
        private List<Patient> patients = new ArrayList<>();
        private String filename;
        private int nextId = 1;

        PatientManager(String filename) {
            this.filename = filename;
            loadFromFile();
        }

        private void loadFromFile() {
            File f = new File(filename);
            if (!f.exists()) return;
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line;
                boolean firstLine = true;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;
                    // skip header if exists
                    if (firstLine && line.toLowerCase().startsWith("id,")) {
                        firstLine = false;
                        continue;
                    }
                    Patient p = Patient.fromCSV(line);
                    if (p != null) {
                        patients.add(p);
                        if (p.id >= nextId) nextId = p.id + 1;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error loading file: " + e.getMessage());
            }
        }

        private void saveToFile() {
            try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
                pw.println("id,name,age,gender,disease,contact");
                for (Patient p : patients) {
                    pw.println(p.toCSV());
                }
            } catch (Exception e) {
                System.out.println("Error saving file: " + e.getMessage());
            }
        }

        Patient addPatient(String name, int age, String gender, String disease, String contact) {
            Patient p = new Patient(nextId++, name, age, gender, disease, contact);
            patients.add(p);
            saveToFile();
            return p;
        }

        Patient findById(int id) {
            for (Patient p : patients) if (p.id == id) return p;
            return null;
        }

        List<Patient> findByName(String partialName) {
            List<Patient> res = new ArrayList<>();
            String q = partialName.toLowerCase();
            for (Patient p : patients) {
                if (p.name.toLowerCase().contains(q)) res.add(p);
            }
            return res;
        }

        boolean updatePatient(int id, String name, Integer age, String gender, String disease, String contact) {
            Patient p = findById(id);
            if (p == null) return false;
            if (name != null) p.name = name;
            if (age != null) p.age = age;
            if (gender != null) p.gender = gender;
            if (disease != null) p.disease = disease;
            if (contact != null) p.contact = contact;
            saveToFile();
            return true;
        }

        boolean deletePatient(int id) {
            Iterator<Patient> it = patients.iterator();
            while (it.hasNext()) {
                if (it.next().id == id) {
                    it.remove();
                    saveToFile();
                    return true;
                }
            }
            return false;
        }

        List<Patient> getAll() {
            return new ArrayList<>(patients);
        }
    }

    // --- Console UI ---
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PatientManager pm = new PatientManager("patients.csv");

        while (true) {
            printMenu();
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    addPatientUI(sc, pm);
                    break;
                case "2":
                    searchPatientUI(sc, pm);
                    break;
                case "3":
                    updatePatientUI(sc, pm);
                    break;
                case "4":
                    deletePatientUI(sc, pm);
                    break;
                case "5":
                    viewAllUI(pm);
                    break;
                case "6":
                    System.out.println("Goodbye!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
            System.out.println(); // blank line for readability
        }
    }

    private static void printMenu() {
        System.out.println("===== Hospital Patient Record System =====");
        System.out.println("1. Add Patient Record");
        System.out.println("2. Search Patient Record");
        System.out.println("3. Update Patient Record");
        System.out.println("4. Delete Patient Record");
        System.out.println("5. View All Records");
        System.out.println("6. Exit");
    }

    private static void addPatientUI(Scanner sc, PatientManager pm) {
        System.out.println("--- Add Patient ---");
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        int age = readInt(sc, "Age: ");
        System.out.print("Gender: ");
        String gender = sc.nextLine().trim();
        System.out.print("Disease: ");
        String disease = sc.nextLine().trim();
        System.out.print("Contact: ");
        String contact = sc.nextLine().trim();

        Patient p = pm.addPatient(name, age, gender, disease, contact);
        System.out.println("Patient added: " + p);
    }

    private static void searchPatientUI(Scanner sc, PatientManager pm) {
        System.out.println("--- Search Patient ---");
        System.out.println("a) By ID   b) By Name");
        System.out.print("Choose (a/b): ");
        String opt = sc.nextLine().trim().toLowerCase();
        if (opt.equals("a")) {
            int id = readInt(sc, "Enter ID: ");
            Patient p = pm.findById(id);
            if (p != null) System.out.println(p);
            else System.out.println("No patient with ID " + id);
        } else {
            System.out.print("Enter name or part of name: ");
            String name = sc.nextLine().trim();
            List<Patient> res = pm.findByName(name);
            if (res.isEmpty()) System.out.println("No matches.");
            else {
                for (Patient p : res) System.out.println(p);
            }
        }
    }

    private static void updatePatientUI(Scanner sc, PatientManager pm) {
        System.out.println("--- Update Patient ---");
        int id = readInt(sc, "Enter patient ID to update: ");
        Patient p = pm.findById(id);
        if (p == null) {
            System.out.println("No patient with ID " + id);
            return;
        }
        System.out.println("Current: " + p);
        System.out.println("Press Enter without typing to keep existing value.");

        System.out.print("New name (" + p.name + "): ");
        String name = sc.nextLine();
        name = name.trim().isEmpty() ? null : name.trim();

        System.out.print("New age (" + p.age + "): ");
        String ageStr = sc.nextLine().trim();
        Integer age = ageStr.isEmpty() ? null : tryParseInt(ageStr);

        System.out.print("New gender (" + p.gender + "): ");
        String gender = sc.nextLine();
        gender = gender.trim().isEmpty() ? null : gender.trim();

        System.out.print("New disease (" + p.disease + "): ");
        String disease = sc.nextLine();
        disease = disease.trim().isEmpty() ? null : disease.trim();

        System.out.print("New contact (" + p.contact + "): ");
        String contact = sc.nextLine();
        contact = contact.trim().isEmpty() ? null : contact.trim();

        boolean ok = pm.updatePatient(id, name, age, gender, disease, contact);
        if (ok) System.out.println("Patient updated.");
        else System.out.println("Update failed.");
    }

    private static void deletePatientUI(Scanner sc, PatientManager pm) {
        System.out.println("--- Delete Patient ---");
        int id = readInt(sc, "Enter patient ID to delete: ");
        Patient p = pm.findById(id);
        if (p == null) {
            System.out.println("No patient with ID " + id);
            return;
        }
        System.out.println("Found: " + p);
        System.out.print("Are you sure? (y/N): ");
        String confirm = sc.nextLine().trim().toLowerCase();
        if (confirm.equals("y") || confirm.equals("yes")) {
            boolean ok = pm.deletePatient(id);
            if (ok) System.out.println("Deleted.");
            else System.out.println("Delete failed.");
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    private static void viewAllUI(PatientManager pm) {
        System.out.println("--- All Patients ---");
        List<Patient> all = pm.getAll();
        if (all.isEmpty()) {
            System.out.println("No records.");
            return;
        }
        for (Patient p : all) System.out.println(p);
    }

    // --- small helpers ---
    private static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static Integer tryParseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
