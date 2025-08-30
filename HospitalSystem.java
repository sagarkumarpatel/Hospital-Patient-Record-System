import java.io.*;
import java.util.*;

public class HospitalSystem {

    // --- Model: Patient ---
    static class Patient {
        int id;
        String name;
        int age;
        String gender;
        String disease;
        String contact;
        String ward;
        String doctorAssigned;

        Patient(int id, String name, int age, String gender, String disease, String contact, String ward, String doctorAssigned) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.disease = disease;
            this.contact = contact;
            this.ward = ward;
            this.doctorAssigned = doctorAssigned;
        }

        // CSV line
        String toCSV() {
            return id + "," + name + "," + age + "," + gender + "," + disease + "," + contact + "," + ward + "," + doctorAssigned;
        }

        static Patient fromCSV(String line) {
            String[] p = line.split(",", -1);
            if (p.length < 8) return null;
            try {
                int id = Integer.parseInt(p[0]);
                String name = p[1];
                int age = Integer.parseInt(p[2]);
                String gender = p[3];
                String disease = p[4];
                String contact = p[5];
                String ward = p[6];
                String doctor = p[7];
                return new Patient(id, name, age, gender, disease, contact, ward, doctor);
            } catch (Exception e) {
                return null;
            }
        }

        public String toString() {
            return String.format("ID: %d | Name: %s | Age: %d | Gender: %s | Disease: %s | Contact: %s | Ward: %s | Doctor: %s",
                    id, name, age, gender, disease, contact, ward, doctorAssigned);
        }
    }

    // --- Manager ---
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
                pw.println("id,name,age,gender,disease,contact,ward,doctor");
                for (Patient p : patients) {
                    pw.println(p.toCSV());
                }
            } catch (Exception e) {
                System.out.println("Error saving file: " + e.getMessage());
            }
        }

        Patient addPatient(String name, int age, String gender, String disease, String contact, String ward, String doctor) {
            Patient p = new Patient(nextId++, name, age, gender, disease, contact, ward, doctor);
            patients.add(p);
            saveToFile();
            return p;
        }

        Patient findById(int id) {
            for (Patient p : patients) if (p.id == id) return p;
            return null;
        }

        List<Patient> findByName(String query) {
            return searchField(query, p -> p.name);
        }

        List<Patient> findByDisease(String query) {
            return searchField(query, p -> p.disease);
        }

        List<Patient> findByWard(String query) {
            return searchField(query, p -> p.ward);
        }

        List<Patient> findByDoctor(String query) {
            return searchField(query, p -> p.doctorAssigned);
        }

        private List<Patient> searchField(String query, java.util.function.Function<Patient, String> fieldGetter) {
            List<Patient> res = new ArrayList<>();
            String q = query.toLowerCase();
            for (Patient p : patients) {
                if (fieldGetter.apply(p).toLowerCase().contains(q)) res.add(p);
            }
            return res;
        }

        boolean updatePatient(int id, String name, Integer age, String gender, String disease, String contact, String ward, String doctor) {
            Patient p = findById(id);
            if (p == null) return false;
            if (name != null) p.name = name;
            if (age != null) p.age = age;
            if (gender != null) p.gender = gender;
            if (disease != null) p.disease = disease;
            if (contact != null) p.contact = contact;
            if (ward != null) p.ward = ward;
            if (doctor != null) p.doctorAssigned = doctor;
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
                case "1": addPatientUI(sc, pm); break;
                case "2": searchPatientUI(sc, pm); break;
                case "3": updatePatientUI(sc, pm); break;
                case "4": deletePatientUI(sc, pm); break;
                case "5": viewAllUI(pm); break;
                case "6": System.out.println("Goodbye!"); sc.close(); return;
                default: System.out.println("Invalid choice. Try again.");
            }
            System.out.println();
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
        System.out.print("Ward: ");
        String ward = sc.nextLine().trim();
        System.out.print("Doctor Assigned: ");
        String doctor = sc.nextLine().trim();

        Patient p = pm.addPatient(name, age, gender, disease, contact, ward, doctor);
        System.out.println("Patient added: " + p);
    }

    private static void searchPatientUI(Scanner sc, PatientManager pm) {
        System.out.println("--- Search Patient ---");
        System.out.println("a) By ID");
        System.out.println("b) By Name");
        System.out.println("c) By Disease");
        System.out.println("d) By Ward");
        System.out.println("e) By Doctor Assigned");
        System.out.print("Choose (a-e): ");
        String opt = sc.nextLine().trim().toLowerCase();

        switch (opt) {
            case "a":
                int id = readInt(sc, "Enter ID: ");
                Patient p = pm.findById(id);
                System.out.println(p != null ? p : "No patient with ID " + id);
                break;
            case "b":
                listResults(pm.findByName(readLine(sc, "Enter name or part of name: ")));
                break;
            case "c":
                listResults(pm.findByDisease(readLine(sc, "Enter disease: ")));
                break;
            case "d":
                listResults(pm.findByWard(readLine(sc, "Enter ward: ")));
                break;
            case "e":
                listResults(pm.findByDoctor(readLine(sc, "Enter doctor name: ")));
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static void listResults(List<Patient> list) {
        if (list.isEmpty()) System.out.println("No matches.");
        else list.forEach(System.out::println);
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

        String name = readOptional(sc, "New name (" + p.name + "): ");
        String ageStr = readOptional(sc, "New age (" + p.age + "): ");
        Integer age = ageStr.isEmpty() ? null : tryParseInt(ageStr);
        String gender = readOptional(sc, "New gender (" + p.gender + "): ");
        String disease = readOptional(sc, "New disease (" + p.disease + "): ");
        String contact = readOptional(sc, "New contact (" + p.contact + "): ");
        String ward = readOptional(sc, "New ward (" + p.ward + "): ");
        String doctor = readOptional(sc, "New doctor (" + p.doctorAssigned + "): ");

        boolean ok = pm.updatePatient(id, 
            name.isEmpty() ? null : name, 
            age, 
            gender.isEmpty() ? null : gender,
            disease.isEmpty() ? null : disease,
            contact.isEmpty() ? null : contact,
            ward.isEmpty() ? null : ward,
            doctor.isEmpty() ? null : doctor
        );

        System.out.println(ok ? "Patient updated." : "Update failed.");
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
            System.out.println(ok ? "Deleted." : "Delete failed.");
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    private static void viewAllUI(PatientManager pm) {
        System.out.println("--- All Patients ---");
        List<Patient> all = pm.getAll();
        if (all.isEmpty()) System.out.println("No records.");
        else all.forEach(System.out::println);
    }

    // --- helpers ---
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

    private static String readLine(Scanner sc, String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private static String readOptional(Scanner sc, String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private static Integer tryParseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
