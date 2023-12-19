import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import SignInSide from './components/Login/SignInSide';
import SignUpSide from './components/Login/SignUpSide';
import Home from './components/Home/Home';
import WithNavBar from './components/WithNavBar';
import Orders from './components/Orders/Orders';
import Footer from './components/Footer/Footer';
import NotFound404 from './components/Not Found 404/NotFound404';
import Flights from './components/Flights/Flights';
import Checkout from './components/Checkout/Checkout';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes >
          <Route path="/signin" element={<SignInSide />} />
          <Route path="/signup" element={<SignUpSide />} />

          <Route element={<WithNavBar />}>
            <Route path="/" element={<Home />} />
            <Route path="/flights" element={<Flights />} />
            <Route path="/checkout/:flightId" element={<Checkout />}></Route>
            <Route path="/orders" element={<Orders />} />
            <Route path="/profile" element={<Home />} />
            {/* 404 Not Found */}
            <Route path="*" element={<NotFound404 />} />
          </Route>
        </Routes>
        <Footer />
      </BrowserRouter>
    </div>
  );
}

export default App;
