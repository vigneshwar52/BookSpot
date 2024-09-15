# Bookspot

Bookspot is a Jetpack Compose application that uses the Retrofit library to make network API calls to fetch book data such as author, title, ISBN, description, and page count from a local server. The server is running using Flask libraries written in Python, which handle read, write, and delete operations. Additionally, the app includes RoomDB to store this data internally and manage all CRUD operations.

## Features

- Fetch book data from a local server
- Display book details including author, title, ISBN, description, and page count
- Perform CRUD operations (Create, Read, Update, Delete) on book data
- Store book data locally using RoomDB

## Technologies Used

- **Jetpack Compose**: For building the UI
- **Retrofit**: For making network API calls
- **RoomDB**: For local data storage and CRUD operations
- **Flask**: For the backend server handling API requests
- **Python**: For writing the backend scripts

## Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/vigneshwar52/AndroidPreparations.git
   ```

2. Navigate to the project directory:
   ```sh
   cd AndroidPreparations
   ```

3. Open the project in Android Studio.

4. Build and run the project on an Android device or emulator.

## Backend Setup

Here are the commands to install Python and Flask:

1. **To install Python** (for Ubuntu/Debian-based Linux systems):
   ```bash
   sudo apt update
   sudo apt install python3
   sudo apt install python3-pip
   ```
   For Windows or macOS, you can download Python from the official site: [https://www.python.org/downloads/](https://www.python.org/downloads/)

2. **To install Flask using pip**:
   ```bash
   pip install Flask
   ```

These commands will get Python and Flask up and running on your system.

3. Run the Flask server:
   ```sh
   python app.py
   ```

## Usage

1. Launch the Bookspot app on your Android device.
2. Enter the IP address of your local server in the provided field.
3. Fetch and display book data.
4. Perform CRUD operations on the book data.

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request.

---

Feel free to customize this README further to suit your project's needs. Let me know if you need any more help!
