import React, { useEffect, useState } from 'react';
import api from '../../api/axiosConfig';
import { useSelector } from 'react-redux';
import { Grid } from '@mui/material';
import { Card, Box, Typography, Button } from '@mui/material';
import { useNavigate, useParams } from 'react-router-dom';
import Passenger from './Passenger/Passenger';
import { toast } from 'react-toastify';

function OrderDetails() {
  const [order, setOrder] = useState([]);
  const userId = useSelector((state) => state.user.userId);
  const params = useParams();
  const orderId = params.orderId;
  const navigate = useNavigate();
  

  const deleteOrder = async () => {
    try {
        const response = await api.delete(`/order/deleteOrder/${orderId}`);
        if (response.status === 200) {
            console.log(response);
            toast.success('Cancel Order Successfully!');
            navigate(`/orders`, { replace: true });
        }
    } catch (error) {
        console.log(error);
    }
  };

  const fetchOrder = async () => {
    try {
        if (userId === null) {
          return (
            <Grid container justifyContent={"center"} alignItems={"center"}>
                <Typography item m={'10%'} variant="h5" color="primary" align="center">
                  Please login to view your order
                </Typography>
            </Grid>
          );
        }
  
        const response = await api.get(`/order/getOrderDetails/${orderId}`);
        // const response = await api.get(`/user/${userId}`);
        if (response.status === 200) {
          const orderDetails = response.data;
          console.log(orderDetails);
          setOrder([orderDetails]);
        }
      } catch (error) {
        console.log(error);
      }
  };

  useEffect(() => {
    fetchOrder();
  }, [orderId]); 

  return (
    <Grid container justifyContent={'center'} sx={{ backgroundColor: '#F5F5F5' }}>
        {(order.length === 0) ? (
            <Grid container justifyContent={"center"} alignItems={"center"} key={orderId}>
                <Typography item m={'10%'} variant="h5" color="primary" align="center">
                    Loading order...
                </Typography>
            </Grid>
        ) : (
            <Grid container justifyContent={'center'} sx={{ backgroundColor: '#F5F5F5' }}>
                {(order.map((orderDetails, index) =>
                <Grid item xs={12} md={7} m={"2%"} key={index}>
                    <Grid item xs={12} md={12} mb={"2%"}>
                        <Card>
                            <Box pt={3} px={2}>
                                <Typography variant="h6" fontWeight="bold">
                                Order Information
                                </Typography>
                            </Box>
                            <Box pt={1} pb={2} px={2} display={'flex'} flexDirection={'column'}>
                                <Box mb={1} lineHeight={0}>
                                    <Typography variant="caption" fontWeight="bold" color="rgb(123, 128, 154)">
                                        Order ID:&nbsp;&nbsp;&nbsp;
                                        <Typography variant="caption" fontWeight="normal" color={"black"}>
                                            {orderDetails.order.orderId}
                                        </Typography>
                                    </Typography>
                                </Box>
                                <Box mb={1} lineHeight={0}>
                                    <Typography variant="caption" fontWeight="bold" color="rgb(123, 128, 154)">
                                        Flight ID:&nbsp;&nbsp;&nbsp;
                                        <Typography variant="caption" fontWeight="normal" color={"black"}>
                                            {orderDetails.order.flightId}
                                        </Typography>
                                    </Typography>
                                </Box>
                                <Box mb={1} lineHeight={0}>
                                    <Typography variant="caption" fontWeight="bold" color="rgb(123, 128, 154)">
                                        Departure:&nbsp;&nbsp;&nbsp;
                                        <Typography variant="caption" fontWeight="normal" color={"black"}>
                                            {orderDetails.flight.flightDepartureDate} {orderDetails.flight.flightDepartureTime}
                                        </Typography>
                                    </Typography>    
                                </Box>
                                <Box mb={1} lineHeight={0}>
                                    <Typography variant="caption" fontWeight="bold" color="rgb(123, 128, 154)">
                                        Arrvial:&nbsp;&nbsp;&nbsp;
                                        <Typography variant="caption" fontWeight="normal" color={"black"}>
                                            {orderDetails.flight.flightArrivalDate} {orderDetails.flight.flightArrivalTime}
                                        </Typography>
                                    </Typography>    
                                </Box>               
                                <Box mb={1} lineHeight={0}>
                                    <Typography variant="caption" fontWeight="bold" color="rgb(123, 128, 154)">
                                        Order Price:&nbsp;&nbsp;&nbsp;
                                        <Typography variant="caption" fontWeight="normal" color={"black"}>
                                            {orderDetails.order.orderTotalPrice}
                                        </Typography>
                                    </Typography>    
                                </Box>   
                                <Box mb={1} lineHeight={0}>
                                    <Typography variant="caption" fontWeight="bold" color="rgb(123, 128, 154)">
                                        Order Timestamp:&nbsp;&nbsp;&nbsp;
                                        <Typography variant="caption" fontWeight="normal" color={"black"}>
                                            {new Date(orderDetails.order.orderTimestamp)
                                                .toLocaleDateString('en-US', {
                                                    year: 'numeric',
                                                    month: 'long',
                                                    day: 'numeric',
                                                    hour: 'numeric',
                                                    minute: 'numeric',
                                                    second: 'numeric',
                                                    hour12: false,
                                                })}
                                            {}
 
                                            
                                        </Typography>
                                    </Typography>    
                                </Box>   
                            </Box>
                        </Card>
                    </Grid>
                    <Grid item xs={12} md={12} mb={"2%"}>
                        <Passenger
                            passenger={orderDetails.passenger}
                        />
                    </Grid>
                    <Button
                        variant="contained"
                        size="large"
                        sx={{
                            width: 'fit-content',
                            border: '0',
                            outline: '0',
                            fontWeight: '600',
                            fontSize: '0.875rem',
                            lineHeight: '1.75',
                            textTransform: 'none',
                            backgroundColor: '#F44336', // red color codes: , #FFCDD2. white color codes: , #F44336
                            color: '#FFFFF',
                            '&:hover': {
                                backgroundColor: '#F44336',
                            },
                        }}
                        onClick={deleteOrder}
                    >Cancel Order</Button>
                </Grid>
                ))}
            </Grid>
        )}
    </Grid>
  );
}
export default OrderDetails;
