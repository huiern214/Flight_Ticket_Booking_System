import React from 'react';
import ReactDOM from 'react-dom/client';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import './index.css';
import App from './App';
import { CssBaseline } from '@mui/material';

const theme = createTheme({
  palette: {
    primary: {
      main: 'hsl(225, 50%, 48%)', // blue
    },
    secondary: {
      main: 'hsl(180, 17%, 95%)', // light grey
    },
  },
  typography: {
    fontFamily: [
      // 'Roboto',
      'sans-serif',
      // 'Arial',
    ].join(','),
    useNextVariants: true,
  },
});

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <App />
    </ThemeProvider>
  </React.StrictMode>
);