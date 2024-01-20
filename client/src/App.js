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
import Profile from './components/Profile/Profile';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import OrderDetails from './components/OrderDetails/OrderDetails';
// import { useSelector } from 'react-redux';
// import { Navigate } from 'react-router';

function App() {

  // const permission = useSelector((state) => state.user.permission);

  // const renderRestrictedRoute = (component, path) => {
  //   if (permission === 'admin') {
  //     return <Route path={path} element={component} />;
  //   }
  //   return <Navigate to="/" replace />;
  // };

  return (
    <div className="App">
      <BrowserRouter>
        <ToastContainer />
        <Routes >
          <Route path="/signin" element={<SignInSide />} />
          <Route path="/signup" element={<SignUpSide />} />

          <Route element={<WithNavBar />}>
            <Route path="/" element={<Home />} />
            <Route path="/flights" element={<Flights />} />
            <Route path="/checkout/:flightId" element={<Checkout />}></Route>
            <Route path="/orders" element={<Orders />} />
            <Route path="/orders/:orderId" element={<OrderDetails />} />
            <Route path="/profile" element={<Profile />} />
            {/* 404 Not Found */}
            <Route path="*" element={<NotFound404 />} />
          </Route>

          {/* {renderRestrictedRoute(<FlightManagement />, '/flight_management')} */}
        </Routes>
        <Footer />
      </BrowserRouter>
    </div>
  );
}

export default App;
