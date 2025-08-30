🏥 Hospital Patient Record System (Console-Based Java Project)

A simple Java console application to manage hospital patient records.
This system allows hospitals to add, search, update, delete, and view patient records with ease.
All data is stored in a CSV file (patients.csv) so records persist even after closing the program.

🚀 Features

✅ Add new patient records
✅ Search by ID
✅ Search by Name
✅ Search by Disease
✅ Search by Ward
✅ Search by Doctor Assigned
✅ Update existing patient details
✅ Delete patient records
✅ View all patients in a clean table-like format
✅ Data persistence using CSV file

📂 Project Structure
Hospital-Patient-Record-System/
│── HospitalSystem.java   # Main Java source file
│── patients.csv          # Data file (auto-created)
│── README.md             # Documentation (this file)

⚡ Getting Started
1️⃣ Clone the repository
git clone https://github.com/sagarkumarpatel/Hospital-Patient-Record-System
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
Disease: Cholera
Ward: General
Doctor Assigned: Dr. Mehta
Contact: 9812345678

✅ Patient added successfully!

Example: Searching by Disease
--- Search by Disease ---
Enter Disease: Fever

ID: 2 | Name: Sagar Kumar Patel | Age: 20 | Gender: Male | Disease: Fever | Ward: ICU | Doctor: Dr. Sharma | Contact: 912345678

Example: Viewing All Patients
--- All Patients ---
ID: 1 | Name: Bikram Patel | Age: 17 | Gender: Male | Disease: Cholera | Ward: General | Doctor: Dr. Mehta | Contact: 9812345678
ID: 2 | Name: Sagar Kumar Patel | Age: 20 | Gender: Male | Disease: Fever | Ward: ICU | Doctor: Dr. Sharma | Contact: 912345678

📊 Example patients.csv File
id,name,age,gender,disease,ward,doctor,contact
1,Bikram Patel,17,Male,Cholera,General,Dr. Mehta,9812345678
2,Sagar Kumar Patel,20,Male,Fever,ICU,Dr. Sharma,912345678

🌟 Future Enhancements

Switch from CSV to Database (MySQL/SQLite) for better scalability

Add user authentication (login system)

Add sorting & filtering (e.g., sort by age, filter by ward)

Build a GUI version using JavaFX or Swing

🤝 Contributing

Contributions are welcome! 🚀

If you’d like to improve this project:

Fork this repository

Create a new branch (feature-xyz)

Commit your changes

Open a Pull Request

👨‍💻 Author

Developed by Sagar Kumar Patel ✨
A console-based project built using Java programming for learning & practice.