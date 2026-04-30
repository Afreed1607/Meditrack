# MediTrack - Java Project Setup Instructions

## System Requirements

Before you begin, ensure you have the following installed on your system:

- **Java Development Kit (JDK)**: Java 21 or higher
- **Maven**: Version 3.8.0 or later (for building the project)
- **Git**: For version control
- **Windows PowerShell** or any command prompt

## Step 1: Install Java JDK

### For Windows:
1. Download Java 21 from [oracle.com](https://www.oracle.com/java/technologies/downloads/)
2. Run the installer and follow the installation wizard
3. Once installed, verify the installation:

```bash
java -version
```

You should see output like:
```
java version "21.0.x" ...
Java(TM) SE Runtime Environment ...
```

### Setting JAVA_HOME Environment Variable:
1. Open System Properties (right-click "This PC" → Properties)
2. Click "Advanced system settings"
3. Go to "Environment Variables"
4. Click "New" under System Variables
5. Variable name: `JAVA_HOME`
6. Variable value: `C:\Program Files\Java\jdk-21` (adjust if different)
7. Click OK and restart your terminal

## Step 2: Install Maven

1. Download Maven from [maven.apache.org](https://maven.apache.org/download.cgi)
2. Extract to a folder (e.g., `C:\apache-maven-3.9.x`)
3. Set M2_HOME environment variable similar to JAVA_HOME
4. Verify installation:

```bash
mvn -version
```

## Step 3: Clone or Download the Project

```bash
cd C:\Users\USER\IdeaProjects
```

If using Git:
```bash
git clone <repository-url> MediTrack
cd MediTrack
```

## Step 4: Build the Project

Navigate to the project directory and run:

```bash
mvn clean compile
```

This command will:
- Clean any previous build artifacts
- Download all dependencies
- Compile all Java source files

## Step 5: Run the Application

### Run the Application:
```bash
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.Main"
```

### Run with Sample Data Loaded:
```bash
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.Main" -Dexec.args="--loadData"
```

### Run Manual Tests:
```bash
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.test.TestRunner"
```

## Alternative: Direct Java Compilation

If you prefer not to use Maven:

### 1. Compile:
```bash
cd C:\Users\USER\IdeaProjects\MediTrack
javac -d target/classes -encoding UTF-8 src/main/java/com/airtribe/meditrack/**/*.java
```

### 2. Run Application:
```bash
java -cp target/classes com.airtribe.meditrack.Main
```

### 3. Run Tests:
```bash
java -cp target/classes com.airtribe.meditrack.test.TestRunner
```

## Project Structure

```
MediTrack/
├── src/
│   └── main/java/com/airtribe/meditrack/
│       ├── Main.java (Application entry point)
│       ├── constants/ (Application constants)
│       ├── entity/ (Data models)
│       ├── service/ (Business logic layer)
│       ├── util/ (Utility classes)
│       ├── interfaces/ (Contracts/Interfaces)
│       ├── enums/ (Enumerations)
│       ├── exception/ (Custom exceptions)
│       └── test/ (Manual tests)
├── docs/ (Documentation)
├── pom.xml (Maven configuration)
└── README.md (Project overview)
```

## Troubleshooting

### Issue: "javac: command not found"
**Solution**: Make sure JAVA_HOME is properly set and the PATH includes `%JAVA_HOME%\bin`

### Issue: "mvn: command not found"
**Solution**: Ensure Maven is installed and M2_HOME is set correctly with PATH including `%M2_HOME%\bin`

### Issue: "Cannot find symbol" during compilation
**Solution**: Make sure all Java files are in the correct package structure. The package should match the directory hierarchy.

### Issue: "No such file or directory" for CSV files
**Solution**: Create a `data/` directory in the project root:
```bash
mkdir data
```

## IDE Setup (IntelliJ IDEA)

1. Open IntelliJ IDEA
2. Go to File → Open → Select the MediTrack folder
3. If prompted, select "Use default project structure"
4. Configure JDK: File → Project Structure → Project → Set JDK to 21
5. Run Main.java by right-clicking and selecting "Run"

## IDE Setup (Eclipse)

1. Open Eclipse
2. File → Import → Existing Projects into Workspace
3. Select MediTrack folder
4. Configure JDK: Window → Preferences → Java → Installed JREs → Add JDK 21
5. Right-click project → Build Project
6. Run as Java Application

## Verification

After successful setup, you should be able to:
- See the MediTrack menu when running the application
- Register doctors and patients
- Create and manage appointments
- Generate bills and perform searches

##  Common Commands

### Clean build:
```bash
mvn clean
```

### Compile only:
```bash
mvn compile
```

### Run tests:
```bash
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.test.TestRunner"
```

### Package as JAR:
```bash
mvn package
```

### Run JAR file:
```bash
java -jar target/meditrack-1.0.jar
```

## Support

If you encounter any issues:
1. Check that all files are in the correct package structure
2. Verify Java and Maven versions
3. Ensure all required permissions for file I/O operations
4. Check the console output for specific error messages

---

**Project Status**: Ready for Development and Testing  
**Last Updated**: May 1, 2026  
**Java Version**: 21

