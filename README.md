# FlyEase 
A user-friendly web application for simplified flight ticket booking, streamlining your travel plans.

## Getting Started
To run the web application locally, follow the [Installation Guide](./Installation_Guide.pdf) these steps:

**Server**  
1. Run /server/src/main/java/com/flyease/server/`ServerApplication.java`  
2. Access the server in your web browser at `http://localhost:8080`.

**Database**
1. Install sqlite3 from [SQLite Download Page](https://www.sqlite.org/download.html). 
2. Useful extensions on VSCode: 
  - SQLite3 Editor (ID: yy0931.vscode-sqlite3-editor)
  - SQLite (ID: alexcvzz.vscode-sqlite)
3. After installing sqlite3 and the extensions, the .sqlite3 file `/server/database/data.sqlite3` can be viewed and edited.
  
**Client**  
1. Install [Node.js]("https://nodejs.org/en/download")  
2. Open terminal
  ```
    cd client
    npm start
  ```
  if there is an error (below), run `npm install react-scripts --save` and try again
  ```
    'react-scripts' is not recognized as an internal or external command, operable program or batch file.
  ```
3. Access the web application in your web browser at `http://localhost:3000`.  
4. Extensions for VSCode:
   - ES7+ React/Redux/React-Native snippets (ID: dsznajder.es7-react-js-snippets)  
   
## Usage
- For testing API purposes, you can use Postman to access the server at http://localhost:8080 and the available API endpoints can be referred to in the corresponding controllers.
- To access the client side of our Flight Ticket Booking Website, please visit http://localhost:3000. For GUI Preview, can refer [here](./GUI_Preview.pdf).