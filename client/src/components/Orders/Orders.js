import React, { useEffect } from 'react';
import { useState } from 'react';
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Typography,
  Link,
  Box,
  Grid,
  Button,
} from '@mui/material';
import './Orders.css'
import { KeyboardArrowRight } from '@mui/icons-material';
import { useSelector } from 'react-redux';
import api from '../../api/axiosConfig';

// const bookingsData = [
//   {
//     order_id: 1,
//     flight_id: 1,
//     date: '2023-12-20', time: '10:00:00',
//     passengers: 2,
//   },
//   {
//     order_id: 2,
//     flight_id: 2,
//     date: '2023-12-15', time: '14:30:00',
//     passengers: 4,
//   },
//   // Add more booking data objects as needed
// ];

const waitingListData = [
  {
    id: 1,
    date: '2023-12-05',
    time: '09:15 AM',
    passengers: 40,
    totalSeats: 40,
  },
  {
    id: 2,
    date: '2023-12-07',
    time: '11:45 AM',
    passengers: 39,
    totalSeats: 40,
  },
  // Add more waiting list data objects as needed
];

function Orders () {
  const [showAll, setShowAll] = useState(false); // Initialize showAll state
  const [showAll2, setShowAll2] = useState(false); // Initialize showAll state
  const currentDateTime = new Date(); // Get the current date and time
  const userId = useSelector((state) => state.user.userId);
  const [orders, setOrders] = useState([]);

  function getStatus(booking) {
    const bookingDateTime = new Date(`${booking.flight.flightDepartureDate}T${booking.flight.flightDepartureTime}`);
    
    if (bookingDateTime < currentDateTime) {
      return 'Completed';
    } else {
      return 'Upcoming'; 
    }
  }

  // Get all orders for a user
  const fetchAllOrdersForUser = async() => {
    try {
      const response = await api.get(`/order/getAllOrdersByUserId/${userId}`); 
      setOrders(response.data);
    } catch (error) {
      console.error('Error fetching order data:', error);
    }
  };

  useEffect(() => {
    fetchAllOrdersForUser();
  }, []);


  // Function to toggle the showAll state
  const handleShowAllClick = () => {
    setShowAll(!showAll);
  };
  const handleShowAllClick2 = () => {
    setShowAll2(!showAll2);
  };

  return (
    <Grid container sx={{height:'100%', backgroundColor: '#F5F5F5'}}>
      <Box item
        minWidth={'80%'}
        maxWidth={'80%'}
        xs={8}
        sm={8}
        md={7}
        margin={'10%'}
        mb={'0%'}
        mt={'5%'}
        sx= {{
            '@media (max-width:680px)': {
            marginTop: '15%',
          }
        }}
        >

        {/* Table for flights */}
        <TableContainer component={Paper} style={{
          backgroundColor: 'rgb(255, 255, 255)',
          color: 'rgb(17, 25, 39)',
          transition: 'box-shadow 300ms cubic-bezier(0.4, 0, 0.2, 1) 0ms',
          border: '1px solid rgb(242, 244, 247)',
          backgroundImage: 'none',
          // overflow: 'hidden',
          borderRadius: '20px',
        }}>
          <Typography variant="h6" m={'4% 1% 2% 3%'} fontWeight='700' lineHeight={'1.2'} fontSize={'1.0625rem'}>
              Bookings
          </Typography>
          <Table>
            <TableHead>
              <TableRow sx={{
                bgcolor: 'rgb(248, 249, 250)', 
                lineHeight: '1',
                color: 'rgb(47, 55, 70)',
                '& th': {fontWeight: '550'}
              }}>
                <TableCell>Order ID</TableCell>
                <TableCell>Flight ID</TableCell>
                <TableCell>Departure</TableCell>
                <TableCell>Arrival</TableCell>
                <TableCell>Price</TableCell>
                <TableCell>Status</TableCell>
                <TableCell>Passenger</TableCell>
                <TableCell>Details</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {(orders.slice(0, showAll ? orders.length : 5).map((booking, index) =>
              <TableRow key={booking.order.orderId}>
                  <TableCell>{booking.order.orderId}</TableCell>
                  <TableCell>{booking.flight.flightId}</TableCell>
                  <TableCell>{booking.flight.flightDepartureDate} {booking.flight.flightDepartureTime}</TableCell>
                  <TableCell>{booking.flight.flightArrivalDate} {booking.flight.flightArrivalTime}</TableCell>
                  <TableCell>{booking.order.orderTotalPrice}</TableCell>
                  <TableCell>
                    <span className={getStatus(booking) === 'Completed' ? 'css-wu11c1' : 'css-rpg9ag'}>
                      {getStatus(booking)}
                    </span>
                  </TableCell>
                  <TableCell>{booking.passenger.passengerFirstName}</TableCell>
                  <TableCell>
                  <Link href={`/orders/${booking.id}`}>Details</Link>
                  </TableCell>
              </TableRow>
              ))}
            </TableBody>
          </Table>
          <Box p={'2%'} sx={{display: 'flex', justifyContent: 'flex-end'}}>
            <Button
              fullWidth={true}
              sx={{
                width: 'fit-content',
                border: '0',
                outline: '0',
                fontWeight: '600',
                fontSize: '0.875rem',
                lineHeight: '1.75',
                textTransform: 'none',
              }}
              onClick={handleShowAllClick} // Toggle showAll state on button click
            >
              <span>See {showAll ? 'Less' : 'More'}</span>
              <KeyboardArrowRight sx={{ marginLeft: '0.2rem' }} />
            </Button>
          </Box>
        </TableContainer>
      </Box>
      <Box item
      minWidth={'80%'}
      maxWidth={'80%'}
      xs={8}
      sm={8}
      md={7}
      margin={'10%'}
      marginTop={'5%'}>
        <TableContainer component={Paper} style={{
          backgroundColor: 'rgb(255, 255, 255)',
          color: 'rgb(17, 25, 39)',
          transition: 'box-shadow 300ms cubic-bezier(0.4, 0, 0.2, 1) 0ms',
          border: '1px solid rgb(242, 244, 247)',
          backgroundImage: 'none',
          borderRadius: '20px',
        }}>
          <Typography variant="h6" m={'4% 1% 2% 3%'} fontWeight='700' lineHeight={'1.2'} fontSize={'1.0625rem'}>
             Waiting List
          </Typography>  
          <Table>
            <TableHead>
              <TableRow sx={{
                bgcolor: 'rgb(248, 249, 250)', 
                lineHeight: '1',
                color: 'rgb(47, 55, 70)',
                '& th': {fontWeight: '550'}
              }}>
                <TableCell>#</TableCell>
                <TableCell>Date</TableCell>
                <TableCell>Time</TableCell>
                <TableCell>No. of Passengers</TableCell>
                <TableCell>Details</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {(waitingListData.slice(0, showAll2 ? waitingListData.length : 1).map((item, index) => 
                <TableRow key={item.id}>
                  <TableCell>{item.id}</TableCell>
                  <TableCell>{item.date}</TableCell>
                  <TableCell>{item.time}</TableCell>
                  <TableCell>{item.passengers} / {item.totalSeats}</TableCell>
                  <TableCell>
                    <Link href="#">Details</Link>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
          <Box p={'2%'} sx={{display: 'flex', justifyContent: 'flex-end'}}>
            <Button
              fullWidth={true}
              sx={{
                width: 'fit-content',
                border: '0',
                outline: '0',
                fontWeight: '600',
                fontSize: '0.875rem',
                lineHeight: '1.75',
                textTransform: 'none',
              }}
              onClick={handleShowAllClick2} // Toggle showAll state on button click
            >
              <span>See {showAll2 ? 'Less' : 'More'}</span>
              <KeyboardArrowRight sx={{ marginLeft: '0.2rem' }} />
            </Button>
          </Box>
        </TableContainer>
      </Box>
    </Grid>
  );
};

export default Orders;
