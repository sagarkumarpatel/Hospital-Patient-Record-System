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
Name: Bikram Patel
Age: 17
Gender: Male
Disease: Chlorea
Contact: 9812345678
Patient added: ID: 1 | Name: Bikram Patel | Age: 17 | Gender: Male | Disease: Chlorea | Contact: 9812345678

Example: Viewing All Patients
--- All Patients ---
ID: 1 | Name: Bikram Patel | Age: 17 | Gender: Male | Disease: Chlorea | Contact: 9812345678

📊 Example patients.csv File
id,name,age,gender,disease,contact
1,Bikram Patel,17,Male,Chlorea,9812345678
2,Sagar Kumar Patel,20,Male,Fever,912345687

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