# FlyEase 
A user-friendly web application for simplified flight ticket booking, streamlining your travel plans.

## Getting Started
To run the web application locally, follow these steps:

**Server**  
1. Run \server\src\main\java\com\flyease\server\ `ServerApplication.java`  
2. Access the server in your web browser at `http://localhost:8080`.

**Database**
1. Install sqlite3
   - Download sqlilte3 from [here](#[SQLite Download Page](https://www.sqlite.org/download.html)). Or from below
   - For Windows: [tools](https://www.sqlite.org/2023/sqlite-tools-win32-x86-3420000.zip) 
   - Set the path variable on your computer by adding the sqlite folder to the path [Video tutorial](#[(35) How to install SQLite on Windows 10 | 2022 | Amit Thinks - YouTube](https://www.youtube.com/watch?v=L3FwRRx6bqo))

2. Useful extensions on VSCode: 
  - SQLite3 Editor (ID: yy0931.vscode-sqlite3-editor)
  - SQLite (ID: alexcvzz.vscode-sqlite)
  
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
- Visit `http://localhost:8080` to view the server 
- Visit `http://localhost:3000` to view the client
