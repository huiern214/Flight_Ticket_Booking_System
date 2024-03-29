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


function Orders () {
  const [showAll, setShowAll] = useState(false); // Initialize showAll state
  const [showAll2, setShowAll2] = useState(false); // Initialize showAll state
  const currentDateTime = new Date(); // Get the current date and time
  const userId = useSelector((state) => state.user.userId);
  const [orders, setOrders] = useState([]);
  const [waitingList, setWaitingList] = useState([]);

  function getStatus(booking) {
    const bookingDateTime = new Date(`${booking.flight.flightDepartureDate}T${booking.flight.flightDepartureTime}`);
    
    if (bookingDateTime < currentDateTime) {
      return 'Completed';
    } else {
      return 'Upcoming'; 
    }
  }

  function getStatus2(booking) {
    const bookingDateTime = new Date(`${booking.flight.flightDepartureDate}T${booking.flight.flightDepartureTime}`);
    
    if (bookingDateTime < currentDateTime) {
      return 'Expired';
    } else {
      return 'Waiting'; 
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

  // Get all waiting list for a user
  const fetchAllWaitingListForUser = async() => {
    try {
      const response = await api.get(`/order/getAllWaitingListsByUserId/${userId}`);
      setWaitingList(response.data.queue);
    } catch (error) {
      console.error('Error fetching waiting list data:', error);
    }
  };

  useEffect(() => {
    fetchAllOrdersForUser();
    fetchAllWaitingListForUser();
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
            {(orders.length === 0 || orders === null || !Array.isArray(orders)) ? null : 
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
                  <Link href={`/orders/${booking.order.orderId}`}>Details</Link>
                  </TableCell>
              </TableRow>
              ))}
            </TableBody>}
          </Table>
          {(orders.length === 0 || orders === null || !Array.isArray(orders)) ? <Box m={'3%'}>No orders.</Box> : 
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
          </Box>}
        </TableContainer>
      </Box>
      <Box item
      minWidth={'80%'}
      maxWidth={'80%'}
      xs={8}
      sm={8}
      md={7}
      margin={'10%'}
      marginTop={'2%'}>
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
            {(waitingList.length === 0 || waitingList === null || !Array.isArray(waitingList)) ? null :
              <TableBody> 
              {(waitingList.slice(0, showAll ? orders.length : 5).map((waiting, index) =>
                <TableRow key={waiting.order.orderId}>
                    <TableCell>{waiting.order.orderId}</TableCell>
                    <TableCell>{waiting.flight.flightId}</TableCell>
                    <TableCell>{waiting.flight.flightDepartureDate} {waiting.flight.flightDepartureTime}</TableCell>
                    <TableCell>{waiting.flight.flightArrivalDate} {waiting.flight.flightArrivalTime}</TableCell>
                    <TableCell>{waiting.order.orderTotalPrice}</TableCell>
                    <TableCell>
                      <span className={getStatus2(waiting) === 'Waiting' ? 'css-wu11c1' : 'css-rpg9ag'}>
                        {getStatus2(waiting)}
                      </span>
                    </TableCell>
                    <TableCell>{waiting.passenger.passengerFirstName}</TableCell>
                    <TableCell>
                    <Link href={`/waitingList/${waiting.order.orderId}`}>Details</Link>
                    </TableCell>
                </TableRow>
              ))}
              </TableBody>
            }
          </Table>
          {(waitingList.length === 0 || waitingList === null || !Array.isArray(waitingList)) ? <Box m={'3%'}>No waiting list.</Box> :
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
          }
        </TableContainer>
      </Box>
    </Grid>
  );
};

export default Orders;
