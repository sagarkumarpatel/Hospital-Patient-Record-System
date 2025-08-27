🏥 Hospital Patient Record System (Console-Based Java Project)



A simple Java console application to manage hospital patient records.
This system helps hospitals add, search, update, delete, and view patient records easily.
All data is stored in a CSV file (patients.csv) so records persist even after closing the program.

🚀 Features

✅ Add new patient records
✅ Search by ID or Name
✅ Update existing patient details
✅ Delete patient records
✅ View all patients in a table-like format
✅ Data persistence using CSV file

📂 Project Structure
Hospital-Patient-Record-System/
│── HospitalSystem.java   # Main Java source file
│── patients.csv          # Data file (auto-created)
│── README.md             # Documentation (this file)

⚡ Getting Started
1️⃣ Clone the repository
git clone https://github.com/your-username/Hospital-Patient-Record-System.git
cd Hospital-Patient-Record-System

2️⃣ Compile the program
javac HospitalSystem.java

3️⃣ Run the program
java HospitalSystem

📖 Usage
Main Menu
===== Hospital Patient Record System =====
1. Add Patient Record
2. Search Patient Record
3. Update Patient Record
4. Delete Patient Record
5. View All Records
6. Exit
Enter your choice:

Example: Adding a Patient
--- Add Patient ---
Name: John Doe
Age: 30
Gender: Male
Disease: Flu
Contact: 9876543210
Patient added: ID: 1 | Name: John Doe | Age: 30 | Gender: Male | Disease: Flu | Contact: 9876543210

Example: Viewing All Patients
--- All Patients ---
ID: 1 | Name: John Doe | Age: 30 | Gender: Male | Disease: Flu | Contact: 9876543210

📊 Example patients.csv File
id,name,age,gender,disease,contact
1,John Doe,30,Male,Flu,9876543210
2,Jane Smith,25,Female,Asthma,9123456789

🌟 Future Enhancements

Switch from CSV to Database (MySQL/SQLite) for better scalability

Add user authentication (login system)

Add sorting & filtering (e.g., sort by age, filter by disease)

Build a GUI version using JavaFX or Swing

🤝 Contributing

Contributions are welcome!
If you’d like to improve this project:

Fork this repository

Create a new branch (feature-xyz)

Commit your changes

Open a Pull Request 🚀


👨‍💻 Author

Developed by Sagar Kumar Patel using Java programming as a console-based learning project.