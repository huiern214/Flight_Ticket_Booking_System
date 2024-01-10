// {
//     "order": {
//       "userId": 1,
//       "flightId": 1,
//       "orderTotalPrice": 100.00,
//       "orderPaymentMethod": "FPX",
//       "orderTotalPassengers": 1
//     },
//     "passengers": [
//       {
//         "passengerFirstName": "John",
//         "passengerLastName": "Doe",
//         "passengerDob": "2000-01-01",
//         "passengerGender": "male",
//         "passengerEmail": "johndoe@gmail",
//         "passengerPhoneNo": "1234567890"
//       }
//     ]
//   }

import React, { useEffect, useState } from 'react';
import api from '../../api/axiosConfig';
import { useSelector } from 'react-redux';
import { Button, Grid, Input, TextField } from '@mui/material';
import Passenger from './Passenger';
import { Card, Box, Typography } from '@mui/material';
import { useParams } from 'react-router-dom';
import NumberInput from './NumberInput';

function Checkout() {
  const params = useParams();
  const flightId = params.flightId;
  const [availableSeats, setAvailableSeats] = useState(null);
  const [numberOfPassengers, setNumberOfPassengers] = useState(1);

  const userId = useSelector((state) => state.user.userId);
  const [flightInfo, setFlightInfo] = useState(null);
  
  const [passengerList, setPassengerList] = useState([]); // State to store passenger list

  const handlePassengerListUpdate = (updatedList) => {
    setPassengerList(updatedList);
  };

  const handleCheckout = () => {
    // console.log(numberOfPassengers, passengerList))
    // if (numberOfPassengers !== passenger_list_length) {
    //   alert('Please fill in all passenger information.');
    //   return;
    // } else if (numberOfPassengers > availableSeats) {
    if (numberOfPassengers > availableSeats) {
      alert('Number of passengers exceeds available seats.');
      return;
    } else {
      const order = {
        "order": {
        "userId": userId,
        "flightId": flightId,
        "orderTotalPrice": flightInfo.flightPrice * numberOfPassengers,
        "orderPaymentMethod": "FPX",
        "orderTotalPassengers": numberOfPassengers
        },
        "passengers": passengerList
      }
      console.log(order);
    }
  };

  useEffect(() => {
    fetchFlightInfo(flightId);
  }, [flightId]);

  const fetchFlightInfo = async (flightId) => {
    try {
      const response = await api.get(`/flight/getFlightById/${flightId}`);
      if (response.status === 200) {
        setFlightInfo(response.data);
      }

      const response2 = await api.get(`/flight/getNumAvailableSeats/${flightId}`);
      if (response2.status === 200) {
        setAvailableSeats(response2.data);
      }
    } catch (error) {
      console.log(error);
    }
  };

  if (flightInfo === null) {
    return (
      <Typography m={'10%'} variant="h5" color="primary" align="center">
        Loading flight...
      </Typography>
    );
  } else if (availableSeats === 0 || availableSeats === null) {
    return (
      <Typography m={'10%'} variant="h5" color="primary" align="center">
        No available seats
      </Typography>
    );
  } else if (userId === null) {
    return (
      <Grid container justifyContent={"center"} alignItems={"center"}>
        <Typography item m={'10%'} variant="h5" color="primary" align="center">
            Please login before proceed to checkout.
        </Typography>
        <Button item variant="contained" color="primary" href="/signin">
            Login
        </Button>
      </Grid>
    );
  }

  return (
    <Grid container justifyContent={'center'} sx={{ backgroundColor: '#F5F5F5' }}>
        {/* Flight Information */}
        <Grid item xs={12} md={7} m={"2%"}>
            <Card>
                <Box pt={3} px={2}>
                    <Typography variant="h6" fontWeight="bold">
                    Flight Information
                    </Typography>
                </Box>
                <Box pt={1} pb={2} px={2} display={'flex'} flexDirection={'column'}>
                    <Box mb={1} lineHeight={0}>
                        <Typography variant="caption" fontWeight="bold" color="rgb(123, 128, 154)">
                            Flight ID:&nbsp;&nbsp;&nbsp;
                            <Typography variant="caption" fontWeight="normal" color={"black"}>
                                {flightInfo.flightId}
                            </Typography>
                        </Typography>
                    </Box>
                    <Box mb={1} lineHeight={0}>
                        <Typography variant="caption" fontWeight="bold" color="rgb(123, 128, 154)">
                            Name:&nbsp;&nbsp;&nbsp;
                            <Typography variant="caption" fontWeight="normal" color={"black"}>
                                {flightInfo.flightDepartureDate}
                            </Typography>
                        </Typography>
                    </Box>
                    <Box mb={1} lineHeight={0}>
                        <Typography variant="caption" fontWeight="bold" color="rgb(123, 128, 154)">
                            Departure Time:&nbsp;&nbsp;&nbsp;
                            <Typography variant="caption" fontWeight="normal" color={"black"}>
                                {flightInfo.flightDepartureTime}
                            </Typography>
                        </Typography>    
                    </Box>               
                    <Box mb={1} lineHeight={0}>
                        <Typography variant="caption" fontWeight="bold" color="rgb(123, 128, 154)">
                            Duration:&nbsp;&nbsp;&nbsp;
                            <Typography variant="caption" fontWeight="normal" color={"black"}>
                                1 hour
                            </Typography>
                        </Typography>    
                    </Box>               
                    <Box mb={1} lineHeight={0}>
                        <Typography variant="caption" fontWeight="bold" color="rgb(123, 128, 154)">
                            Price:&nbsp;&nbsp;&nbsp;
                            <Typography variant="caption" fontWeight="normal" color={"black"}>
                                RM {flightInfo.flightPrice}
                            </Typography>
                        </Typography>
                    </Box> 
                    <Box mb={1} lineHeight={0}>
                        <Typography variant="caption" fontWeight="bold" color="rgb(123, 128, 154)">
                            Seats:&nbsp;&nbsp;&nbsp;
                            <Typography variant="caption" fontWeight="normal" color={"black"}>
                                {flightInfo.flightTotalPassengers} / {flightInfo.flightTotalSeats}
                            </Typography>
                        </Typography>
                    </Box>
                </Box>
            </Card>
        </Grid>

        <Grid item xs={12} md={7} m={"2%"}>
          <Card>
            <Box pt={3} px={2}>
                <Typography variant="h6" fontWeight="bold">
                Passenger Information
                </Typography>
                <Typography variant="caption" fontWeight="bold" color="rgb(123, 128, 154)">
                  (Left seats: {availableSeats === null ? 'Loading...' : availableSeats})
                </Typography>
            </Box>
            <Grid item m={"3%"}>
              <NumberInput
                item
                id='number-input'
                min={1}
                max={availableSeats}
                defaultValue={numberOfPassengers}
                onChange={(event) => {
                  setNumberOfPassengers(event.target.value);
                }}
              />
            </Grid>
            <Passenger
              numOfPassenger={numberOfPassengers} // Adjust this value as needed
              onUpdatePassengerList={handlePassengerListUpdate} // Pass a callback function to update the passenger list
            />
          </Card>
        </Grid>
        <Grid item xs={12} md={7} m={"2%"}>
          <Card>
            <Box pt={3} px={2}>
              <Typography m="3%" variant="h6" fontWeight="bold">
                Payment
              </Typography>
              <Typography m="2%" variant="h6" fontWeight="bold" color="rgb(123, 128, 154)" fontSize={"1rem"}>
                Total Price: {flightInfo.flightPrice} x {numberOfPassengers} = {flightInfo.flightPrice * numberOfPassengers}
              </Typography>
              <Typography m="2%" variant="h6" fontWeight="bold" color="rgb(123, 128, 154)" fontSize={"1rem"}>
                Payment Method: FPX
              </Typography>
            </Box>
            <Button m="5%" onClick={handleCheckout} variant='contained' fullWidth>Checkout</Button>
          </Card>
        </Grid>
      </Grid>
  );
}

export default Checkout;


